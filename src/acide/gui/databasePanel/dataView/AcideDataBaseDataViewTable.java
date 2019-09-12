/*
 * ACIDE - A Configurable IDE
 * Official web site: http://acide.sourceforge.net
 * 
 * Copyright (C) 2007-2014  
 * Authors:
 * 		- Fernando Sáenz Pérez (Team Director).
 *      - Version from 0.1 to 0.6:
 *      	- Diego Cardiel Freire.
 *			- Juan José Ortiz Sánchez.
 *          - Delfín Rupérez Cañas.
 *      - Version 0.7:
 *          - Miguel Martín Lázaro.
 *      - Version 0.8:
 *      	- Javier Salcedo Gómez.
 *      - Version from 0.9 to 0.11:
 *      	- Pablo Gutiérrez García-Pardo.
 *      	- Elena Tejeiro Pérez de Ágreda.
 *      	- Andrés Vicente del Cura.
 *      - Version from 0.12 to 0.16
 *      	- Semíramis Gutiérrez Quintana
 *      	- Juan Jesús Marqués Ortiz
 *      	- Fernando Ordás Lorente
 *      - Version 0.17
 *      	- Sergio Domínguez Fuentes
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package acide.gui.databasePanel.dataView;

import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.ClipboardOwner;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.Locale;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.JTable;
import javax.swing.KeyStroke;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import javax.swing.undo.CannotRedoException;
import javax.swing.undo.CannotUndoException;
import acide.gui.databasePanel.utils.DataViewTableSelection;
import acide.language.AcideLanguageManager;

/**
 * ACIDE - A Configurable IDE data base data view table.
 * 
 * @version 0.15
 * @see JTable
 */
public class AcideDataBaseDataViewTable extends JTable implements ClipboardOwner {

	private static final long serialVersionUID = 1L;
	public AcideDatabaseDataView _dataView;
	public MyPopUpMenu _popUp = new MyPopUpMenu();
	public int _recordIndex;
	private static boolean processed=false;
	private Clipboard systemClipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
	
	
	public AcideDataBaseDataViewTable(DefaultTableModel model, AcideDatabaseDataView view){
		super(model);
		_dataView = view;
		setRowSorter(new FilterOnlyTableRowSorter<TableModel>(model));
		setCellSelectionEnabled(true);
		MyListener tableListener = new MyListener();
		addMouseListener(tableListener);
		addMouseMotionListener(tableListener);
	}
	
	@Override
	protected boolean processKeyBinding(KeyStroke ks, KeyEvent event,int condition,boolean pressed){	
		setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
		int row = getSelectedRow();
		int col = getSelectedColumn();
		String data="";
		event.consume();
		switch (event.getKeyCode()) {
		
		case KeyEvent.VK_F3:
			if(event.isShiftDown()){
				if(!processed){					
					_dataView.previousSearchField();
					processed = true;
				} else
					processed = false;
			} else {
				if(!processed && _dataView.getSearchField()!=null)	{
					_dataView.nextSearchField();
					processed = true;
				} else processed = false;
			}
			
			break;
		case KeyEvent.VK_F5:
			if(!processed)	{	
				_dataView.refresh();
				processed = true;
			} else processed = false;
			break;
			
		case 127: //supr
			if(!processed && !_dataView.isReadOnly())	{
				_dataView.deleteRow();
				processed = true;
			} else{
				processed = false;
				}
			break;

		case KeyEvent.VK_C:
			if (event.isControlDown()){
				if(!processed){	
					int[] selectedRows = getSelectedRows();  
					int[] selectedColumns = getSelectedColumns();
					int initColumn = 0;
					int ini=0;
			        String[][] dataSelected = null;
			        if (selectedColumns[0]==0) { 
			        	dataSelected = new String[selectedRows.length][selectedColumns.length-1];
			        	ini =1;		
			        	} else dataSelected = new String[selectedRows.length][selectedColumns.length];
			      
			        for (int i=0; i<selectedRows.length;i++) {  
			            for (int j=ini; j<selectedColumns.length;j++) {  
			            		dataSelected[i][initColumn] = (String) getValueAt(selectedRows[i],selectedColumns[j]);
			            		initColumn++;
			            }
			            initColumn=0;
			        }  
			        
			        copyDataToSystemClipboard(dataSelected);
					processed = true;
				} else processed = false;
			}				
			break;
			
		case KeyEvent.VK_V:
			if (event.isControlDown() && !_dataView.isReadOnly()){
				if(!processed)	{	
					try {
						String[][] result = (String[][]) systemClipboard.getData(DataViewTableSelection.dataViewTableFlavor);
						
						int numRowsPaste = result.length;
						int numColumnsPaste = result[0].length; //Only an NxN matrix is allowed to copy
						
						int numRowsTable = getRowCount(); //Total number of rows of the grid
						int numColumnsTable = getColumnCount(); //Total number of columns of the grid
						
						int initRow = getSelectedRow();//Initial row to start to paste
						int initColumn = getSelectedColumn(); //Initial column to start to paste
						
						if (initColumn == 0) initColumn = 1; //If column 0 is selected start pasting from column 1
						
						DefaultTableModel model = (DefaultTableModel) getModel();
						//Need to check if there is enough space to paste
							if(numColumnsTable-initColumn>=numColumnsPaste){
								int initRowAux= initRow;
								int initColumnAux= initColumn;
					
								//Start pasting
								for(int i=0;i<numRowsPaste;i++){
									if ((initRowAux >= numRowsTable)){ //Last record
										Vector<String> newRow = new Vector<String>();
										model.insertRow(initRowAux, newRow);
									}
									for(int j=0;j<numColumnsPaste;j++){
										setValueAt(result[i][j], initRowAux, initColumnAux);
										initColumnAux++;
									}
									initColumnAux=initColumn;
									initRowAux++;
								}
								
								this._dataView.insertValues();
								Dimension d = getSize();
								setSize(d.width, d.height+1);
								setSize(d.width, d.height-1);
							} else{
								JOptionPane.showMessageDialog(null,AcideLanguageManager.getInstance()
								.getLabels().getString("s2251"), AcideLanguageManager.getInstance()
								.getLabels().getString("s2240"),
			                    JOptionPane.WARNING_MESSAGE);
						}
						
					} catch (UnsupportedFlavorException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					}
					
					processed = true;
				} else processed = false;
			}			
			break;
			
		case KeyEvent.VK_X:
			if (event.isControlDown() && !_dataView.isReadOnly()){
				if(!processed)	{	
					if(row>0 && col>0){
						data = (String) getValueAt(row, col);
						Toolkit toolkit = Toolkit.getDefaultToolkit();
						Clipboard clipboard = toolkit.getSystemClipboard();
						StringSelection strSel = new StringSelection(data);
						clipboard.setContents(strSel, null);
						setValueAt("", row, col);
					}	
					processed = true;
				} else processed = false;
			}			
			break;
			
		case KeyEvent.VK_F:
			if (AcideLanguageManager.getInstance().getCurrentLocale().equals(new Locale("en", "EN"))
					|| AcideLanguageManager.getInstance().getCurrentLocale().equals(new Locale("fr", "FR"))){
				if(event.isControlDown()){
					if(!processed){
						_dataView.setAlwaysOnTop(false);
						data = (String)JOptionPane.showInputDialog(null, "",AcideLanguageManager.getInstance().getLabels().getString("s26"),
				                JOptionPane.PLAIN_MESSAGE,new ImageIcon("./resources/icons/menu/edit/search.png"),null,"");
						if ((data != null) && (data.length() > 0))
							_dataView.search(data);	
						_dataView.setAlwaysOnTop(false);	
						processed = true;
					}	else processed = false;
				} 	
			}
			break;
			
		case KeyEvent.VK_B:
			if (AcideLanguageManager.getInstance().getCurrentLocale()
					.equals(new Locale("es", "ES"))){
				if(event.isControlDown()){
					if(!processed){
						_dataView.setAlwaysOnTop(false);
						data = (String)JOptionPane.showInputDialog(null, "",AcideLanguageManager.getInstance().getLabels().getString("s26"),
				                JOptionPane.PLAIN_MESSAGE,new ImageIcon("./resources/icons/menu/edit/search.png"),null,"");
						if ((data != null) && (data.length() > 0))
							_dataView.search(data);	
						_dataView.setAlwaysOnTop(false);	
						processed = true;
					} else processed = false;
					
				} 	
			}
			break;
		
		case KeyEvent.VK_Y:
			if (event.isControlDown() && !_dataView.isReadOnly()){
				if(!processed){		
					try {
						_dataView.getUndoManager().redo();
						_dataView.refresh();
					} catch (CannotRedoException ex) {}
					processed = true;
				} else processed = false;
			}
			break;
		
		case KeyEvent.VK_Z:
			if (event.isControlDown() && !_dataView.isReadOnly()){
				if(!processed)	{	
					try {
						_dataView.getUndoManager().undo();
						_dataView.refresh();
					} catch (CannotUndoException ex) {}
					processed = true;
				} else processed = false;
			}
			break;
			
		case KeyEvent.VK_F4:
			if (event.isAltDown()){
				if(!processed){	
					_dataView.closeWindow();
					processed = true;
				} else processed = false;
			}
			break;
		
		case KeyEvent.VK_UP:
			if(!this.isEditing()){
				if(!processed)	{	
					_dataView.previousRecord();
					processed = true;
				} else processed = false;
			}
			break;
			
		case KeyEvent.VK_DOWN:
			if(!this.isEditing()){
				if(!processed){		
					_dataView.nextRecord();
					processed = true;
				} else processed = false;
			}
			break;
			
		case KeyEvent.VK_END:
			if (event.isControlDown()){
				if(!processed)	{	
					_dataView.lastRecord();
					processed = true;
				} else processed = false;
			}
			if(!processed) {		
				if(row==getRowCount()-1 && !_dataView.isReadOnly())
					_dataView.insertValues();
				processed = true;
			} else processed = false;
			break;
			
		case 36:
			if (event.isControlDown()){
				if(!processed)	{	
					_dataView.firstRecord();
					processed = true;
				} else processed = false;
			}
			if(!processed){		
				if(row==getRowCount()-1 && !_dataView.isReadOnly())
					_dataView.insertValues();
				processed = true;
			} else
				processed = false;
			break;
			
		case KeyEvent.VK_TAB:
			if (event.isShiftDown()){
				if(!processed){	
					_dataView.previousField();
					processed = true;
				} else processed = false;
			} else {	
				if(!processed){	
					_dataView.nextField();
					processed = true;
				} else processed = false;
			}
			break;
			
		case KeyEvent.VK_ENTER:
			if(!processed)	{	
				if(row==getRowCount()-1 && !_dataView.isReadOnly())
					_dataView.insertValues();
				processed = true;
			} else processed = false;
			break;
			
		case KeyEvent.VK_ESCAPE:
			if(!processed){	
				if (isEditing()){
					row = getEditingRow();
					col = getEditingColumn();
					getCellEditor(row, col).cancelCellEditing();
				}
				processed = true;
			} else processed = false;
			break;
		}
		setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		return true;
	}
	

	@Override
	public void lostOwnership(Clipboard clipboard, Transferable contents) {
		
	}
	
	public void copyDataToSystemClipboard(String[][] data) {  
        if (systemClipboard != null) {
        	DataViewTableSelection selectedData = new DataViewTableSelection(data);
        	systemClipboard.setContents(selectedData, this); 
        }
    }

	public void setSelectedField(int fila, int col) {
		if(col==0){
			changeSelection(fila, 1, true, false);
			changeSelection(fila, getColumnCount()-1, true, true);
		} else this.changeSelection(fila, col, false, false);
	}
	
	public void replaceText(String searchField, String textToReplace) {
		String value = (String) getValueAt(getSelectedRow(), getSelectedColumn());
		value = value.replaceAll(searchField, textToReplace);
		setValueAt(value, getSelectedRow(), getSelectedColumn());
	}
	
	public class MyListener extends java.awt.event.MouseAdapter {
	
		@Override
		public void mouseClicked(java.awt.event.MouseEvent e){
			boolean isFilter = AcideDataBaseDataViewTable.this._dataView.getIsFilter();
			if(e.isControlDown()){
				changeSelection(rowAtPoint(e.getPoint()), getColumnCount(), true, false);
			}else if(e.isShiftDown()){
				changeSelection(rowAtPoint(e.getPoint()), getColumnCount(), true, true);
			}else if(e.getButton()==MouseEvent.BUTTON3){ //menu contextual fila
				int fila = rowAtPoint(e.getPoint());
				changeSelection(fila, 1, false, false);
				changeSelection(fila, getColumnCount()-1, true, true);
				if(!_dataView.isReadOnly() && fila == getRowCount()-1) return;
				_popUp.setVisible(_popUp.isVisible());
				_popUp.show(e.getComponent(), e.getX(), e.getY() );
				
			} else {
				int row = getSelectedRow();
				int col = getSelectedColumn();
				if(!_dataView.isReadOnly() && _recordIndex+1==getRowCount() && row<getRowCount()-1){
					AcideDataBaseDataViewTable.this._dataView.insertValues();
				}else{
					if(!_dataView.isReadOnly() && !isFilter && row==getRowCount()-1 && col==0){		
						AcideDataBaseDataViewTable.this._dataView.insertValues();
					} else if(col==0){
						setColumnSelectionInterval(1,getColumnCount()-1);
					} else {
						AcideDataBaseDataViewTable.this._dataView.updateRecord();
					}
					AcideDataBaseDataViewTable.this._dataView.validar();					
				}
			}
			_recordIndex = getSelectedRow();
		}
		
		@Override
		public void mouseEntered(MouseEvent arg0) {
			Cursor handCursor = new Cursor(Cursor.HAND_CURSOR);
			setCursor(handCursor);				
		}

		@Override
		public void mouseExited(MouseEvent arg0) {
			setCursor(null);				
		}
		
		@Override
		public void mouseDragged(MouseEvent arg0){
			int column =getSelectedColumn();
			if (column ==0 ) setColumnSelectionInterval(1,getColumnCount()-1);
		}
		
		@Override
		public void mousePressed(MouseEvent arg0) {	
			int column =getSelectedColumn();
			if (column ==0 ) setColumnSelectionInterval(1,getColumnCount()-1);

		}

	}
	
	public static class MyPopUpMenu extends JPopupMenu{
		private static final long serialVersionUID = 1L;
		
		private static final ImageIcon DROP= new ImageIcon("./resources/icons/database/dropTable.png");
		
		private JMenuItem _dropRow;
		
		public MyPopUpMenu(){
			super();
			_dropRow = new JMenuItem(AcideLanguageManager.getInstance().getLabels().getString("s2050"), DROP);
			add(_dropRow);
			_dropRow.addActionListener(new ActionListener() {				
				@Override
				public void actionPerformed(ActionEvent e) {
					((AcideDataBaseDataViewTable)MyPopUpMenu.this.getInvoker())._dataView.deleteRow();
				}
			});
		}
	}
	
	public class FilterOnlyTableRowSorter<M extends TableModel> extends TableRowSorter<M>{

		public FilterOnlyTableRowSorter(){
			super();
		}
		
		public FilterOnlyTableRowSorter( M model ){
			super( model );
		}
		
		public void toggleSortOrder( int col ){} // no sorting when user selects column header
	}


}
