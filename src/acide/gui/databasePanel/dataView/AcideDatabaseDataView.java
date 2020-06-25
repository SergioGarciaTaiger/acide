/*
 * ACIDE - A Configurable IDE
 * Official web site: http://acide.sourceforge.net
 * 
 * Copyright (C) 2007-2014  
 * Authors:
 * 		- Fernando S�enz P�rez (Team Director).
 *      - Version from 0.1 to 0.6:
 *      	- Diego Cardiel Freire.
 *			- Juan Jos� Ortiz S�nchez.
 *          - Delf�n Rup�rez Ca�as.
 *      - Version 0.7:
 *          - Miguel Mart�n L�zaro.
 *      - Version 0.8:
 *      	- Javier Salcedo G�mez.
 *      - Version from 0.9 to 0.11:
 *      	- Pablo Guti�rrez Garc�a-Pardo.
 *      	- Elena Tejeiro P�rez de �greda.
 *      	- Andr�s Vicente del Cura.
 *      - Version from 0.12 to 0.16
 *      	- Sem�ramis Guti�rrez Quintana
 *      	- Juan Jes�s Marqu�s Ortiz
 *      	- Fernando Ord�s Lorente
 *      - Version 0.17
 *      	- Sergio Dom�nguez Fuentes
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

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingUtilities;
import javax.swing.event.TableModelEvent;
import javax.swing.event.UndoableEditEvent;
import javax.swing.event.UndoableEditListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import javax.swing.undo.AbstractUndoableEdit;
import javax.swing.undo.CannotUndoException;

import acide.gui.databasePanel.dataView.commandBar.AcideDataViewCommandBarPanel;
import acide.gui.databasePanel.dataView.listeners.AcideDatabaseDataViewWindowListener;
import acide.gui.databasePanel.dataView.menuBar.AcideDataViewMenuBar;
import acide.gui.mainWindow.AcideMainWindow;
import acide.language.AcideLanguageManager;
import acide.process.console.AcideDatabaseManager;
/**
 * ACIDE - A Configurable IDE database view.
 * 
 * @version 0.16
 * @see JFrame
 */
@SuppressWarnings({ "rawtypes", "unchecked" })
public class AcideDatabaseDataView extends JFrame{

	//public enum types{INT,STRING};
	private static final long serialVersionUID = 1L;
	protected AcideDataBaseDataViewTable _jTable;
	protected JPanel _statusBar;

	private String _database;
	private String _table;

	protected String _totalRecords;
	protected int _totalColumns=0;

	protected int _width = 250;
	protected int _height = 280;

	protected Vector<types> _tipos = new Vector<>();

	protected boolean _isAscending=false;
	protected boolean _isDescending=false;
	protected Vector<TableColumn> _columns;

	private JLabel _record;
	protected Boolean _isFilter=false;
	protected JScrollPane _scrollPane;
	private JLabel _filterText;

	private JLabel _hiddenColumns;
	private boolean _isHidden=false;
	
	private AcideDataViewMenuBar _menu;
	private AcideDataViewCommandBarPanel _panelIcons;


	protected Boolean _isReadOnly=false;
	private String _searchField;
	private int _countIndex;
	private JButton _irA;
	protected JvUndoManager _undoManager;
	private JTextField _recordIndexT;
	
	private HashMap<String,String> orderColumns = new HashMap<>();
	private HashMap<String,Boolean> activeColumns = new HashMap<>();
	private Vector<String> orderNameColumns = new Vector<>();

	private boolean _isTable=true;
	
	protected String _nameColumnSorted="";
	
	
	/**
	 * ACIDE - A Configurable IDE main window image icon.
	 */
	private static final ImageIcon _ICON = new ImageIcon("./resources/images/icon.png");

	public AcideDataBaseDataViewTable get_jTable() {
		return _jTable;
	}

	public AcideDatabaseDataView(String db, String tabla){
		setVisible(false);
		setAlwaysOnTop(false);
		AcideMainWindow.getInstance().setEnabled(false);
		setIconImage(_ICON.getImage());

		this._database = db;
		this._table = tabla;
		
		_undoManager = new JvUndoManager();
		
		_menu = new AcideDataViewMenuBar(this); 
		
		this.setJMenuBar(_menu);
		
		_panelIcons = new AcideDataViewCommandBarPanel();
		_panelIcons.buildAcideToolBarPanel(this);
		
		this.getContentPane().add(_panelIcons, BorderLayout.NORTH);	
		
		_isTable = AcideDatabaseManager.getInstance().isTable(db,tabla);
		
		if(_isTable)
			this.setTitle(tabla+": " +AcideLanguageManager.getInstance().getLabels().getString("s2063"));
		else
			this.setTitle(tabla+": " +AcideLanguageManager.getInstance().getLabels().getString("s2156"));
		
		this.setBackground(Color.gray);	
		this.setDefaultCloseOperation(2);	

		addWindowListener(new AcideDatabaseDataViewWindowListener());
		this.setLocationRelativeTo(AcideMainWindow.getInstance());
		
	}

	protected void validar(){
		_jTable.repaint();
	}

	public void build(LinkedList<String> info){	
		
 		if(_scrollPane!=null){
			_scrollPane.remove(_jTable);
			this.getContentPane().remove(_scrollPane);
		}
		buildData(info);
	
		_scrollPane = new JScrollPane(_jTable);
		_scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		_scrollPane.setSize(_totalColumns*20+_width, Integer.valueOf(_totalRecords)*5+_height);
		_scrollPane.setViewportView(_jTable);

		this.getContentPane().add(_scrollPane, BorderLayout.CENTER);
		setAlwaysOnTop(true);
		
		if(_statusBar==null){
			_statusBar = buildStatusBar();
			this.getContentPane().add(_statusBar, BorderLayout.SOUTH);
		}
		
		if(!_isReadOnly){
			_jTable.setSurrendersFocusOnKeystroke(true); 
			final AcideDatabaseDataView dataView = this;
			_jTable.getTableHeader().addMouseListener(new MouseAdapter() {

				@Override
				public void mouseClicked(MouseEvent mouseEvent) {
					TableColumnModel columnModel = _jTable.getColumnModel();
					int viewColumn = columnModel.getColumnIndexAtX(mouseEvent.getX());
					int column = _jTable.convertColumnIndexToModel(viewColumn);
					
					if (SwingUtilities.isRightMouseButton(mouseEvent)){
						AcideDataViewColumnPopUp popUp = new AcideDataViewColumnPopUp(dataView,column);
						popUp.show(mouseEvent.getComponent(), mouseEvent.getX(), mouseEvent.getY() );
					}
					else {
						if(column==0){
							_jTable.selectAll();
						} else{
							if (column>0 && !_isFilter) {
								Vector<?> data = ((MyTableModel) _jTable.getModel()).getDataVector();
								_isAscending = !_isAscending;
								_nameColumnSorted = ((MyTableModel)_jTable.getModel()).getColumnName(column);
								updateSortByWindow();
								Collections.sort(data, new ColumnSorter(column,_isAscending,_tipos));
								((MyTableModel) _jTable.getModel()).fireTableStructureChanged();
							}
						}
					}

				}
			});
		}
		
		updateRecord();
		
		int height =Integer.valueOf(_totalRecords)*5+_height; 
		int screenSize = Toolkit.getDefaultToolkit().getScreenSize().height;
		if(height > Toolkit.getDefaultToolkit().getScreenSize().height) height = screenSize*9/10;  
		if ((this.getWidth()>_width)|| (this.getHeight()>height))
			this.setSize(getWidth(), getHeight());
		else this.setSize(_totalColumns*20+_width, height);	
		


	}

	/**
	 * Initializes the by default sorting values for each column
	 */
	private void initializateSortValues() {
		String sort =AcideLanguageManager.getInstance().getLabels().getString("s2083");
		Vector<String> list = getColumnsNames();
		
		for(int i=0;i<list.size();i++){
			activeColumns.put(list.get(i), Boolean.FALSE);
			orderColumns.put(list.get(i), sort);
		}
	}

	
	/**
	 * Updates the sorting values of a given column
	 */
	protected void updateSortByWindow() {
		
		String sort = "";
		
		initializateSortValues();
		
		// Sets the order of the columns of the table as default value
		setOrderNameColumns(getColumnsNames());
		
		// Sets a TRUE value to the given column which is the sorting value source
		activeColumns.put(_nameColumnSorted, Boolean.TRUE);
		
		if (_isAscending)
			sort =AcideLanguageManager.getInstance().getLabels().getString("s2083");
		else
			sort = AcideLanguageManager.getInstance().getLabels().getString("s2084");
		
		// Sets the sort type to the given column is the sorting value source
		orderColumns.put(_nameColumnSorted, sort);
	}

	/**
	 * Returns the column name that is the sorting value source
	 * @return
	 */
	public String getNameColumnSorted() {
		return _nameColumnSorted;
	}

	public void insertValues() {

		int numToAdd = _jTable.getRowCount() - ((MyTableModel) _jTable.getModel()).getOldData().size();
		int[] filas = new int[numToAdd];
		for (int fil=0; fil<filas.length;fil++){
		filas[fil] = _jTable.getRowCount()-numToAdd;
		Vector<String> dataColumns = getDataColumns(filas).get(fil);
		
		if(!hasData(dataColumns)) return;
		
		setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
		
		String info = AcideDatabaseManager.getInstance().insertValues(_table,dataColumns);
		
		if(info.equals("success")){			
			_totalRecords = String.valueOf(Integer.valueOf(_totalRecords)+1);
			_record.setText(AcideLanguageManager.getInstance().getLabels().getString("s2129")+" "+_jTable.getSelectedRow()+1+" "+
					AcideLanguageManager.getInstance().getLabels().getString("s2130")+ _totalRecords);
			_statusBar.validate();
		} else {
			this.setAlwaysOnTop(false);
			if(info.contains("Input syntax error"))
				JOptionPane.showMessageDialog(null, AcideLanguageManager.getInstance().getLabels().getString("s2257"),
						AcideLanguageManager.getInstance().getLabels().getString("s2040"), JOptionPane.WARNING_MESSAGE);
			else JOptionPane.showMessageDialog(null,info);
			for(int i =1; i<_columns.size(); i++){
				_jTable.getModel().setValueAt("", _jTable.getRowCount()-1, i);	
			}
			this.setAlwaysOnTop(true);
		}
		numToAdd--;
		}
		refresh();
		setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
	}

	/**
	 * Sorts the tuples in the table in a way specified by the given command
	 * @param command
	 */
	public void orderColumns(String command) {
		
		AcideDatabaseManager des = AcideDatabaseManager.getInstance();
		
		LinkedList<String> res = des.orderColumns(_table, command);
		
		if (res.get(0).equals("answer")){
			res.remove(0);
			build(res);
		}
		
		((MyTableModel) _jTable.getModel()).updateOldData();
		_jTable.repaint();
		
		Dimension d = _jTable.getSize();
		_jTable.setSize(d.width, d.height+1);
		_jTable.setSize(d.width, d.height-1);
		
	}

	public void deleteRow(){
		setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
		String info;
		int[] selectedRows = _jTable.getSelectedRows();
		Vector<Vector<String>> dataColumns = getDataColumns(selectedRows);
		info = AcideDatabaseManager.getInstance().deleteRow(_database,_table,dataColumns,getColumnsNames());
		if(info.equals("success")){	
			for(int i =0; i< selectedRows.length; i++){
				if(i==0)
					((DefaultTableModel) _jTable.getModel()).removeRow(selectedRows[i]);
				else
					((DefaultTableModel) _jTable.getModel()).removeRow(selectedRows[i]-1);
			}
			refresh();
		} else {
			setAlwaysOnTop(false);
			JOptionPane.showMessageDialog(null, info);
		}
		setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
	}

	private boolean hasData(Vector<String> dataColumns) {
		boolean res = false;
		int i =0;
		while(i<dataColumns.size() && !res ){
			String data = dataColumns.get(i);
			res = data!=null && !(data.equals("''") || data.equals(""));
			i++;
		}
		return res;
	}

	protected Vector<Vector<String>> getDataColumns(int[] row) {
		Vector<Vector<String>> result = new Vector<Vector<String>>();
		int columns = _jTable.getColumnCount();
		for(int h =0; h<row.length; h++){
			int fila = row[h];
			Vector<String> info = new Vector<String>();
			for(int i = 1; i< columns; i++){
				String infoColumn = (String) _jTable.getValueAt(fila, i);
				String data = null;
				if(infoColumn!=null && !infoColumn.equals(""))
					data = getPrimitiveTypeData(infoColumn.trim(),i);			
				info.add(data);
			}	
			result.add(info);
		}

		return result;
	}			

	private void buildData(LinkedList<String> info) {
		_isFilter= false;
		_isAscending=false;
		_isDescending=false;
		Vector<String> columnasN = new Vector<String>();
		_totalColumns=0;
		String Indexinfo;
		int index=0;		
		String[] dataModel;
		Object[][] data;

		int i =0;
		int rows=0; //sacamos el numero de filas que tenemos
		while(i<info.size()){
			if(info.get(i).contains("$")) rows++;
			i++;
		}
		if(rows==0) info.add("$");
		if(_database.contains("$des")){
			Indexinfo = info.get(index);
			int inicio = Indexinfo.indexOf(".");
			//añadimos las columnas
			while(!Indexinfo.equals("$")){								  
				String nameColumn = Indexinfo.substring(inicio+1);
				_totalColumns++;
				columnasN.add(nameColumn);
				index++;
				int a =  info.get(index).indexOf("(");
				String tipo = "";
				if (a > 0)
					tipo = info.get(index).substring(0, info.get(index).indexOf("("));
				else
					tipo = info.get(index);
				setType(tipo);
				index++;
				Indexinfo = info.get(index);
			}
			index++;

			dataModel = new String[_totalColumns+1];		  
			dataModel[0]="";		 
			for(int h=1;h<columnasN.size()+1;h++){	
				String c = columnasN.get(h-1);
				dataModel[h]=c;
			}		 
			if(!_isReadOnly && _isTable){ 
				data = new Object[rows+1][_totalColumns+1]; 
				if (info.size()>0)
					data[rows][_totalColumns] ="";
			}
			else{
				data = new Object[rows][_totalColumns+1]; 
				if(rows==0)
					data[rows][_totalColumns] ="";
				else
					data[rows-1][_totalColumns] ="";
			}

			//a�adimos las filas
			for(int row = 0; row < rows; row++) {	  	    
				for(int column = 1; column < _totalColumns+1; column++) {
					String infoColumn= info.get(index);    			
					inicio=infoColumn.indexOf("'");
					if(inicio>=0){
						int fin = infoColumn.lastIndexOf("'");
						infoColumn = infoColumn.substring(inicio+1,fin);
					}
					if(infoColumn.trim().equals("null")) infoColumn="";
					data[row][column] = infoColumn;      
					index++;
				}
				index++;
			}	      
			_totalRecords = String.valueOf(rows);
			if(!_isReadOnly && _isTable)
				if (info.size()>0)
					for(int column=1;column<_totalColumns+1;column++)
						data[rows][column] ="";	
		}
		else{
			index = _table.indexOf("(");
			if (index >0)
				_table=_table.substring(_table.indexOf("("),_table.length());		
			LinkedList<String> fields = AcideDatabaseManager.getInstance().getFields(_database, _table);
			int inicio = 0;
			while (inicio < fields.size()){
				String nameColumn = fields.get(inicio);
				String type = "";
				String name = "";
				int index2 = nameColumn.indexOf(":");
				if (index2 > 0){
					name = nameColumn.substring(0, index2);
					type = nameColumn.substring(index2+1);
				}
				columnasN.add(name);
				setType(type);
				inicio++;
				_totalColumns++;
			}
			index=1;

			dataModel = new String[_totalColumns+1];		  
			dataModel[0]="";		 
			for(int h=1;h<columnasN.size()+1;h++){	
				String c = columnasN.get(h-1);
				dataModel[h]=c;
			}		 
			
			if(!_isReadOnly && _isTable){ 
				  data = new Object[rows+1][_totalColumns+1]; 
				  if (info.size()>0)
					  data[rows][_totalColumns] ="";
			  }
			  else{
				  data = new Object[rows][_totalColumns+1]; 
				  data[rows-1][_totalColumns] ="";
			  }

			index++;
			 for(int row = 0; row < rows; row++) {	  	    
		    	  for(int column = 1; column < _totalColumns+1; column++) {
		    		 
		    		String infoColumn = "";

    				infoColumn= info.get(index);    			
    				inicio=infoColumn.indexOf("'");

	    			if(inicio>=0){
	    				int fin = infoColumn.lastIndexOf("'");
	    				infoColumn = infoColumn.substring(inicio+1,fin);
	    			}
	    			if(infoColumn.trim().equals("null")) infoColumn="";
	    				data[row][column] = infoColumn;      
	        			index++;
		    	  }
		    	  index++;
		      }	      
		      _totalRecords = String.valueOf(rows);
		      if(!_isReadOnly && _isTable)
		    	  if (info.size()>0)
				      for(int column=1;column<_totalColumns+1;column++)
				    	  data[rows][column] ="";	
		}

		MyTableModel model = new  MyTableModel();
		model.setDataVector(data,dataModel);

		_jTable = new AcideDataBaseDataViewTable(model,this);

		_jTable.setAutoscrolls(true);
		
		_jTable.getColumnModel().getColumn(0).setMinWidth(20);
		
		_jTable.getColumnModel().getColumn(0).setMaxWidth(20);
		
		_jTable.getColumnModel().getColumn(0).setResizable(false);

		_jTable.setDefaultRenderer (Object.class, new RenderLabel());

		model.addUndoableEditListener(_undoManager);

		setColumns();
		
		_jTable.setSelectedField(0, 1);
	}

	private void setType(String tipo) {
		if(tipo.equalsIgnoreCase("string")|| tipo.equalsIgnoreCase("varchar"))
			_tipos.add(types.STRING);
		else _tipos.add(types.INT);              
	}

	private void setColumns() {
		_columns= new Vector<TableColumn>();
		for(int i =0; i<_jTable.getColumnCount();i++){
			_columns.add(_jTable.getColumnModel().getColumn(i));
		}
	}

	public String getPrimitiveTypeData(String infoColumn, int col) {
		String data = null;
		types tdata = _tipos.get(col-1);
		switch(tdata){
		case STRING:
			data =infoColumn.equals("")?infoColumn: "'"+infoColumn+"'";
			break;
		default:
			data = infoColumn;
			break;
		}
		return data;
	}

	protected JPanel buildStatusBar() {
		JPanel panel = new JPanel();
		JPanel panelAux = new JPanel();
		_filterText = new JLabel();
		_hiddenColumns = new JLabel();
		_record = new JLabel();
		
		_recordIndexT = new JTextField(4);
		_recordIndexT.setText(String.valueOf(_jTable.getSelectedRow()+1));
		_recordIndexT.setSize(_width, 5);
		_irA = new JButton( AcideLanguageManager.getInstance().getLabels().getString("s2132"));
		_irA.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				System.out.println("go to record");
				goToRecord(Integer.valueOf(_recordIndexT.getText()));		

			}
		});
		_recordIndexT.addKeyListener(new KeyAdapter() {			

			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_ENTER && !_recordIndexT.getText().equals("")){
					goToRecord(Integer.valueOf(_recordIndexT.getText()));
				}		
			}			
		});
		
		panel.add(_filterText);
		panel.add(_record,BorderLayout.WEST);	
		panel.add(_irA, BorderLayout.CENTER);
		panel.add(_recordIndexT, BorderLayout.EAST);
		panel.setSize(_width, 8);
		panelAux.setLayout(new BorderLayout());
		panelAux.add(_hiddenColumns,BorderLayout.CENTER);
		panelAux.add(panel,BorderLayout.SOUTH);
		
		return panelAux;
	}

	public void orderAscending(int index){
		if(index>0 && !_isAscending){
			Vector<?> data = ((MyTableModel) _jTable.getModel()).getDataVector();
			_isAscending = !_isAscending;
			Collections.sort(data, new ColumnSorter(index,_isAscending,_tipos));
			((MyTableModel) _jTable.getModel()).fireTableStructureChanged();
			_jTable.repaint();
		}
	}

	public void orderDescending(int index){
		if(index>0 &&!_isDescending){
			Vector data = ((MyTableModel) _jTable.getModel()).getDataVector();
			_isAscending = !_isAscending;
			Collections.sort(data, new ColumnSorter(index,_isAscending,_tipos));
			((MyTableModel) _jTable.getModel()).fireTableStructureChanged();
			_jTable.repaint();
		}			
	}

	public void search(String data) {
		_searchField =data;
		search(0, 0);
	}	

	public void replace(String _textToSearch, String _textToReplace) {
		setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
		_searchField = _textToSearch;
		int fila = _jTable.getSelectedRow();
		int col = _jTable.getSelectedColumn();
		replace(fila,col,_textToReplace);
		setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
	}	

	public int replaceAll(String _textToSearch, String _textToReplace) {
		_searchField = _textToSearch;
		return replaceAll(_textToReplace);
	}

	public void ShowColumns(Vector<Boolean> columnsVisibility) {
		boolean hasHiddenColumns = false;
		for(int i=1;i<=columnsVisibility.size();i++){
			boolean columnVisibility = columnsVisibility.get(i-1);
			if(!columnVisibility){				
				_jTable.removeColumn(_columns.get(i));
				hasHiddenColumns = true;
			} else {
				if(_jTable.convertColumnIndexToView(i)==-1)				
					_jTable.addColumn(_columns.get(i));
			}
		}

		setIsHidden(hasHiddenColumns);
	}

	public Vector<types> getTipos() {
		return _tipos;
	}
	public int getTotalColumns() {
		return _totalColumns;
	}

	public void setTotalColumns(int totalColumns) {
		this._totalColumns = totalColumns;
	}

	public Vector<String> getColumnsNames() {
		Vector<String> result = new Vector<String>();
		for(int i=1;i<=_totalColumns;i++){
			result.add((String) _columns.get(i).getIdentifier());
		}
		return result;
	}

	public Vector<Boolean> getColumnsVisibility() {
		Vector<Boolean> result = new Vector<Boolean>();
		for(int i=1;i<=_totalColumns;i++){
			int index=_jTable.convertColumnIndexToView(i);
			result.add(index!=-1);
		}
		return result;
	}

	public AcideDataBaseDataViewTable getTable() {
		return _jTable;
	}

	public JvUndoManager getUndoManager() { 
		return _undoManager;
	}

	public void setUndoManager(JvUndoManager undoManager) {
		this._undoManager = undoManager;
	}

	public void previousRecord() {
		int index =_jTable.getSelectedRow();
		index--;	
		if(index>=0){
			_jTable.setSelectedField(index, _jTable.getSelectedColumn());
			updateRecord();
		}
	}

	public void nextRecord() {
		int index =_jTable.getSelectedRow();
		index++;	
		if(index<=Integer.valueOf(_totalRecords)){	
			_jTable.setSelectedField(index, _jTable.getSelectedColumn());
			updateRecord();
		}
	}

	public void lastRecord() {
		_jTable.setSelectedField(Integer.valueOf(_totalRecords), _jTable.getSelectedColumn());
		updateRecord();				
	}

	public void firstRecord() {
		_jTable.setSelectedField(1, _jTable.getSelectedColumn());
		updateRecord();		
	}

	private void replace(int row, int column, String textToReplace) {
		setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
		boolean found=false;
		int fila=row;
		int col=column+1;
		while(!found &&fila< _jTable.getRowCount()){			
			while(!found &&  col<_jTable.getColumnCount()){
				String num = (String) _jTable.getValueAt(fila,col);
				if (num.toLowerCase().contains(_searchField.toLowerCase())) {
					int[] filas = new int[1];
					filas[0] = fila;
					Vector<String> infoRow = getDataColumns(filas).get(0);
					_jTable.setSelectedField(fila, col);
					_jTable.replaceText(_searchField,textToReplace);
					Vector<String> newInfoRow = getDataColumns(filas).get(0);
					found=true;
					String result = AcideDatabaseManager.getInstance().updateField(_table,_database,getColumnsNames(),infoRow,newInfoRow);
					if(!result.equals("ok")){
						_jTable.replaceText(textToReplace,_searchField);
						JOptionPane.showMessageDialog(null, AcideLanguageManager.getInstance().getLabels().getString("s2178"));	  
						return;
					}
				}
				col++;
			}
			col=1;
			fila++;
		}
		((MyTableModel) _jTable.getModel()).updateOldData();
		validar();
		setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
	}	

	private int replaceAll(String textToReplace) {
		int countReplacement = 0;
		int countCell =5;
		for(int fila =0; fila< _jTable.getRowCount(); fila++){			
			for(int col=1; col<_jTable.getColumnCount(); col++){
				countCell++;
				String num = (String) _jTable.getValueAt(fila,col);
				if (num.toLowerCase().contains(_searchField.toLowerCase())) {
					int[] filas = new int[1];
					filas[0] = fila;
					Vector<String> infoRow = getDataColumns(filas).get(0);
					_jTable.setSelectedField(fila, col);
					_jTable.replaceText(_searchField,textToReplace);
					Vector<String> newInfoRow = getDataColumns(filas).get(0);
					
					setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
					String result = AcideDatabaseManager.getInstance().updateField(_table,_database,getColumnsNames(),infoRow,newInfoRow);
					if(!result.equals("ok")){
						_jTable.replaceText(textToReplace,_searchField);
						JOptionPane.showMessageDialog(this, result);
						validar();
						countReplacement--;
						return countReplacement;
					}
					countReplacement++;
				}
				_countIndex = (countCell*100)/(_jTable.getColumnCount()*_jTable.getRowCount());	            
			}			
		}
		((MyTableModel) _jTable.getModel()).updateOldData();
		setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		return countReplacement;
		
	}	

	private void search(int row, int column) {
		boolean found=false;
		int fila=row;
		int col=column+1;
		while(!found &&fila< _jTable.getRowCount()){			
			while(!found &&  col<_jTable.getColumnCount()){
				String num = (String) _jTable.getValueAt(fila,col);
				if (num.toLowerCase().contains(_searchField.toLowerCase())) {
					_jTable.setSelectedField(fila, col);
					found=true;
				}
				col++;
			}
			col=1;
			fila++;
		}
		validar();
	}	

	private void backSearch(int row, int column) {
		boolean found=false;
		int fila=row;
		int col=column-1;
		while(!found &&fila>=0){			
			while(!found &&  col>0){
				String num = (String) _jTable.getValueAt(fila,col);
				if (num.toLowerCase().contains(_searchField.toLowerCase())) {
					_jTable.setSelectedField(fila, col);
					found=true;
				}
				col--;
			}
			col=_jTable.getColumnCount()-1;
			fila--;
		}
		validar();
	}	

	public void updateRecord(){
		int index= _jTable.getSelectedRow()+1;
		if(Integer.valueOf(_totalRecords)==0) index=0;
		if(Integer.valueOf(_totalRecords)>0){
			_record.setText(AcideLanguageManager.getInstance().getLabels().getString("s2129")+" "+index+" "+
					AcideLanguageManager.getInstance().getLabels().getString("s2130")+ _totalRecords);
		_irA.setVisible(true);
		_recordIndexT.setVisible(true);
		} else {
			_record.setText(AcideLanguageManager.getInstance().getLabels().getString("s2131"));
			_irA.setVisible(false);
			_recordIndexT.setVisible(false);
		}
		_record.validate();
		validar();			
	}

	public void nextSearchField() {
		int fila = _jTable.getSelectedRow();
		int col = _jTable.getSelectedColumn();
		search(fila,col);

	}

	public void previousSearchField() {
		int fila = _jTable.getSelectedRow();
		int col = _jTable.getSelectedColumn();
		backSearch(fila,col);
	}

	public void previousField() {
		int col = _jTable.getSelectedColumn();
		int fila = _jTable.getSelectedRow();
		if(col>1) col--;
		else{
			col = _jTable.getColumnCount()-1;
			if(fila>0)
				fila--;
		}
		_jTable.setSelectedField(fila, col);
	}

	public void nextField() {
		int col = _jTable.getSelectedColumn();
		int fila = _jTable.getSelectedRow();
		if(col<_jTable.getColumnCount()-1) col++;
		else{
			col = 1;
			if(fila<_jTable.getRowCount()-1)
				fila++;
		}
		_jTable.setSelectedField(fila, col);		
	}

	public void setIsFilter(Boolean status) {
		if(status) {
			_filterText.setText(AcideLanguageManager.getInstance().getLabels().getString("s2155"));
			_filterText.setForeground(Color.red);
		} else _filterText.setText("");
		_isFilter = status;
	}

	public boolean getIsFilter(){
		return _isFilter;
	}
	
	public Vector<TableColumn> get_columns() {
		return _columns;
	}
	
	public boolean getIsHidden() {
		return _isHidden;
	}

	public void setIsHidden(boolean status) {
		if(status){	
			_hiddenColumns.setText("  "+AcideLanguageManager.getInstance().getLabels().getString("s2247"));
			_hiddenColumns.setForeground(Color.red);
		} else _hiddenColumns.setText("");
		_isHidden = status;
	}

	@Override
	public void setDefaultCloseOperation(int i){		
		closeWindow();
	}

	public void closeWindow() {
		AcideMainWindow.getInstance().setEnabled(true);	
		dispose();
		this.transferFocusBackward();
		AcideMainWindow.getInstance().setAlwaysOnTop(true);
		AcideMainWindow.getInstance().setAlwaysOnTop(false);
	}

	/**
	 * Returns a hash containing the sorting type of each column
	 * @return orderColumns
	 */
	public HashMap<String, String> getOrderColumns() {
		return orderColumns;
	}
	
	public String getTableName() {
		return _table;
	}

	public String getDatabase() {
		return _database;
	}

	public void refresh() {
		LinkedList<String> info = AcideDatabaseManager.getInstance().getSelectAll(_database,_table);
		build(info);	
		((MyTableModel) _jTable.getModel()).updateOldData();
		updateRecord();
		setIsFilter(false);
		setIsHidden(false);
	}

	public void goToRecord(Integer index) {
		String recordIndex = String.valueOf(index);
		if(Integer.valueOf(index)<=Integer.valueOf(_totalRecords)){
			_record.setText(AcideLanguageManager.getInstance().getLabels().getString("s2129")+" "+index+" "+
					AcideLanguageManager.getInstance().getLabels().getString("s2130")+ _totalRecords);
			_record.validate();
			_jTable.clearSelection();
			_jTable.setSelectedField(Integer.valueOf(recordIndex)-1, _jTable.getSelectedColumn());
			_recordIndexT.setText("");
		} else {
			setAlwaysOnTop(false);
			JOptionPane.showMessageDialog(null,  AcideLanguageManager.getInstance().getLabels().getString("s2134"));
		}	
	}

	public class RenderLabel implements TableCellRenderer {
		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column)  {
			JLabel label =new JLabel();
			if(value==null){
				if(table.getRowCount()>1 && row==_jTable.getSelectedRow() && column==0){
					label.setIcon(new ImageIcon("./resources/icons/database/row.png"));
				}				
				if((row==table.getRowCount()-1 && column==0) && !_isFilter && !_isReadOnly){
					label.setIcon(new ImageIcon("./resources/icons/database/newRow.png"));
				}	
			} else {
				String data = (String) table.getValueAt(row, column);
				label.setText(data);	
			}
			if(isSelected && column!=0){
				label.setOpaque(true);
				label.setBackground(table.getSelectionBackground());
				label.setForeground(table.getSelectionForeground());
			} else {
				label.setBackground(table.getBackground());
				label.setForeground(table.getForeground());	
			} 
			return label;
		}	
	}

	public AcideDataViewMenuBar getAcideDataViewMenuBar() {		
		return _menu;
	}

	public Boolean getIsReadOnly() {
		return _isReadOnly;
	}

	
	public void setIsReadOnly(Boolean isReadOnly) {
		this._isReadOnly = isReadOnly;
		if(isReadOnly){
			this.remove(_menu);
			AcideDataViewMenuBar menuReadOnly = new AcideDataViewMenuBar(this); 
			menuReadOnly.isReadOnly(false);
			this.setJMenuBar(menuReadOnly);
			
			this.getContentPane().remove(_panelIcons);
			AcideDataViewCommandBarPanel panelIconsReadOnly = new AcideDataViewCommandBarPanel();
			panelIconsReadOnly.buildAcideToolBarPanel(this);
			panelIconsReadOnly.isReadOnly(false);
			this.getContentPane().add(panelIconsReadOnly, BorderLayout.NORTH);

			setTitle(getTitle()+" ("+AcideLanguageManager.getInstance().getLabels().getString("s2229")+")");
		}
	}

	public int getIndex() {
		return _countIndex;
	}

	public void update() {
		this._menu.setTextOfMenuComponents();
		this._panelIcons.setToolsTips();
		this._undoManager.updateNames();

		updateRecord();

		_irA.setText(AcideLanguageManager.getInstance().getLabels().getString("s2132"));

		if(AcideDatabaseManager.getInstance().isTable(_database,_table))
			this.setTitle(_table+": " +AcideLanguageManager.getInstance().getLabels().getString("s2063"));
		else
			this.setTitle(_table+": " +AcideLanguageManager.getInstance().getLabels().getString("s2156"));
		validar();

	}


	public int get_totalRecords() {
		return Integer.parseInt(_totalRecords);
	}

	public JScrollPane getSrollPane(){
		return this._scrollPane;
	}

	public void selectCurrentRecord() {
		_jTable.setSelectedField(_jTable.getSelectedRow(),0);
	}

	public void selectAllRecords() {
		_jTable.selectAll();
	}
	

	public String getSearchField() {
		return _searchField;
	}

	public boolean isReadOnly() {
		return _isReadOnly;
	}

	/**
	 * Returns the columns names sorted
	 * @return
	 */
	public Vector<String> getOrderNameColumns() {
		return orderNameColumns;
	}

	/**
	 * Sets the order in which the columns names are sorted
	 * @param orderNameColumns
	 */
	public void setOrderNameColumns(Vector<String> orderNameColumns) {
		this.orderNameColumns = orderNameColumns;
	}

	/**
	 * Returns a hash containing all the columns participating in current the sorting value
	 * @return activeColumns
	 */
	public HashMap<String,Boolean> getActiveColumns() {
		return activeColumns;
	}

	/**
	 * Sets all the columns participating in current the sorting value
	 * @param activeColumns
	 */
	public void setActiveColumns(HashMap<String,Boolean> activeColumns) {
		this.activeColumns = activeColumns;
	}

	
	public class MyTableModel extends DefaultTableModel {

		private static final long serialVersionUID = 1L;
		Vector<Vector> oldData = new Vector<Vector>();

		public MyTableModel(){
			super();
		}

		@Override
		public void setDataVector(Object [][] d, Object[] f){
			super.setDataVector(d, f);
			for(int i =0; i< d.length-1; i++){
				Object[] data = d[i];
				Vector<String> aux = new Vector<String>();
				for(int j =1; j<data.length; j++){
					String aux2 = getPrimitiveTypeData(((String) data[j]).trim(),j);
					if(aux2.equals("''")) 
						aux2="";
					aux.add(aux2);
				}
				oldData.add(aux);
			}
		}

		
		public void fireTableChanged(TableModelEvent e){
			int col = e.getColumn();
			int fila = e.getFirstRow();
			if((col > 0 && col < _columns.size()) && !equals(col,fila))
				if(fila < getRowCount()-1){
					int[] filas = new int[1];
					filas[0]=fila;
					String res = AcideDatabaseManager.getInstance().updateField(_table, _database, getColumnsNames(),oldData.get(fila),getDataColumns(filas).get(0));
					if(!res.equals("ok")){
						AcideDatabaseDataView.this.setAlwaysOnTop(false);
						if (res.contains("Unknown column")) 
							JOptionPane.showMessageDialog(null, AcideLanguageManager.getInstance().getLabels().getString("s2257")+ res.substring(14),
									AcideLanguageManager.getInstance().getLabels().getString("s2040"), JOptionPane.WARNING_MESSAGE);
						else JOptionPane.showMessageDialog(null, res);						
						AcideDatabaseDataView.this.setAlwaysOnTop(true);
						this.setValueAt(oldData.get(fila).get(col-1),fila, col);
					}
					else updateOldData();
				}
			
		}
		
		@Override
		public void fireTableDataChanged() {
			super.fireTableDataChanged();
		}

		private boolean equals(int col, int fila) {
			if(fila>=oldData.size()) return false;
			String dataOld = (String) oldData.get(fila).get(col-1);
			String dataNew = (String) getValueAt(fila, col);
			return ((dataOld==null && dataNew.equals(""))|| dataNew.equals(dataOld) );
		}

		public void updateOldData(){
			Object[] newData = getDataVector().toArray();
			oldData = new Vector<Vector>();
			for(int i=0; i<newData.length-1; i++){
				Vector aux = new Vector();
				Object[] datos = ((Vector) newData[i]).toArray();
				for(int j =1; j<datos.length; j++){
					aux.add(getPrimitiveTypeData(((String) datos[j]).trim(),j));
				}
				oldData.add(aux);
			}
		}

		@Override
		public boolean isCellEditable(int row, int col) {
			if(_isReadOnly) return false;
			else return col>0;
		}

		public void removeRow(){
			int filas = _jTable.getRowCount();
			for(int i=0; i<filas;i++)
				removeRow(0);
		} 

		public void addUndoableEditListener(UndoableEditListener listener){
			listenerList.add(UndoableEditListener.class, listener);
		}

		@Override
		public void setValueAt(Object value, int row, int column){
			setValueAt(value, row, column, true);
		}

		public void setValueAt(Object value, int row, int column, boolean undoable){
			UndoableEditListener listeners[] = getListeners(UndoableEditListener.class);
			if (undoable == false || listeners == null) {
				super.setValueAt(value, row, column);
				return;
			}

			Object oldValue = getValueAt(row, column);
			super.setValueAt(value, row, column);
			JvCellEdit cellEdit = new JvCellEdit(this, oldValue, value, row, column);
			UndoableEditEvent editEvent = new UndoableEditEvent(this, cellEdit);
			for (UndoableEditListener listener : listeners)
				listener.undoableEditHappened(editEvent);
		}
		
		public Vector<Vector> getOldData(){
			return oldData;
		}
	}

	public class JvCellEdit extends AbstractUndoableEdit{

		private static final long serialVersionUID = 1L;
		protected MyTableModel tableModel;
		protected Object oldValue;
		protected Object newValue;
		protected int row;
		protected int column;


		public JvCellEdit(MyTableModel tableModel, Object oldValue, Object newValue, int row, int column){
			this.tableModel = tableModel;
			this.oldValue = oldValue;
			this.newValue = newValue;
			this.row = row;
			this.column = column;
		}


		@Override
		public String getPresentationName(){
			return "Cell Edit";
		}


		@Override
		public void undo() throws CannotUndoException{
			super.undo();

			tableModel.setValueAt(oldValue, row, column, false);
		}


		@Override
		public void redo() throws CannotUndoException{
			super.redo();
			tableModel.setValueAt(newValue, row, column, false);
		}
	}
	
}