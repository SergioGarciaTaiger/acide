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
package acide.gui.databasePanel.utils;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Locale;
import java.util.Map;

import javax.swing.ButtonGroup;
import javax.swing.DefaultCellEditor;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.KeyStroke;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

import acide.gui.mainWindow.AcideMainWindow;
import acide.language.AcideLanguageManager;
import acide.process.console.AcideDatabaseManager;

/**
 * ACIDE - A Configurable IDE design view.
 * 
 * @version 0.11
 * @see JFrame
 */
@SuppressWarnings("rawtypes")
public class AcideDataBaseDesignView extends JFrame {
	
	private static final long serialVersionUID = 1L;

	private JTable _jTable;
	
	private String _database;
	private String _table;
	
	private int _totalColumns=4;
	
	private JButton _okButton;
	
	private JButton _cancelButton;
	
	private String _recordIndex = "1";
	
	private int _minimumLenght = 200;
	
	private HashMap<String, AcideInfoDatabaseField> _initialStatus;
	
	private AcideDatabaseManager _manager;
	
	private boolean _new;
	
	/**
	 * ACIDE - A Configurable IDE main window image icon.
	 */
	private static final ImageIcon ICON = new ImageIcon("./resources/images/icon.png");
	
	
	public AcideDataBaseDesignView(String database, String table){
		
		AcideMainWindow.getInstance().setEnabled(false);
		
		setDefaultCloseOperation(2);
		
		AcideMainWindow.getInstance().setEnabled(false);
		setIconImage(ICON.getImage());

		
		_database = database;
		int index = table.indexOf("(");
		if (index > 0)
			_table = table.substring(0,index);
		else
			_table = table;
		
		setTitle(AcideLanguageManager.getInstance().getLabels().getString("s2054")+" - "+_table);
		
		_manager = AcideDatabaseManager.getInstance();
		
		_okButton = new JButton(AcideLanguageManager.getInstance().getLabels().getString("s154"));
		if (AcideLanguageManager.getInstance().getCurrentLocale().equals(new Locale("en", "EN"))){
			_okButton.setMnemonic('O');
		} else {
			_okButton.setMnemonic('A');	
		}
		_okButton.addActionListener(new AcideOkDesignViewAction());
		
		_cancelButton = new JButton(AcideLanguageManager.getInstance().getLabels().getString("s162"));
		_cancelButton.setMnemonic('C');
		_cancelButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				closeWindow();
			}
		});
		
		getInitialStatus();
		
		build();
		
		setLookAndFeel();
		AcideMainWindow.getInstance().setEnabled(true);
		
		
	}


	private void setLookAndFeel() {

		this.setVisible(true);
	}


	private void getInitialStatus() {
		
		_initialStatus = new HashMap<String, AcideInfoDatabaseField>();
		
		if (_manager.isTable(_database, _table)){
			_new = false;
			
			LinkedList<String> fieldsInfo = _manager.getFields(_database, _table);
			LinkedList<String> pkInfo = _manager.getPrimKey(_database, _table);
			LinkedList<String> nnInfo = _manager.getNullables(_database, _table);
			
			
			String name ="";
			
			for (int i = 0; i<fieldsInfo.size(); i++){
				
				AcideInfoDatabaseField field = new AcideInfoDatabaseField();
				
				name = fieldsInfo.get(i);
				String fullInfo = name;
				
				int index = name.indexOf(":");
				
				name = name.substring(0, index);
				
				String type = fullInfo.substring(fullInfo.indexOf("(")+1, fullInfo.lastIndexOf(")"));
				
				if (type.contains("varchar")){
					field.set_type(AcideInfoDatabaseField.types.STRING);
				}else{
					field.set_type(AcideInfoDatabaseField.types.INT);
				}
				
				boolean found = false;
				int j = 0;
				while (j<pkInfo.size() && !found){
					String pk = pkInfo.get(j);
					if (pk.contains(name+",") || pk.contains(","+name)  || pk.contains("["+name) ){
						found = true;
					}
					j++;
				}
				
				if (found){
					field.set_pk(true);
				}else{
					field.set_pk(false);
				}
				found = false;
				j = 0;
				while (j<nnInfo.size() && !found){
					String nn = nnInfo.get(j);
					if (nn.contains(name+",") || nn.contains(","+name)  || nn.contains("["+name) ){
						found = true;
					}
					j++;
				}
				if (found){
					field.set_nn(true);
				}else
					field.set_nn(false);
						
					
				
				_initialStatus.put(name, field);
				
			}

		}else{
			_new = true;
		}
		
		
	}
	private void build(){
				
		Iterator<?> it = _initialStatus.entrySet().iterator();
		
		int i = 0;
		
		AcideLanguageManager laManager = AcideLanguageManager.getInstance();
		
		// First column is for the icons
		Object[] dataModel = new String[] {"",  laManager.getLabels().getString("s2184")
				, laManager.getLabels().getString("s2185"), laManager.getLabels().getString("s2187"), laManager.getLabels().getString("s2186")};
		
		
		final Object[][] data;
		data = new Object[_initialStatus.size()][_totalColumns+1];
		for (int k = 0; k<_initialStatus.size(); k++){
			for(int l=0;l<3;l++){
				data[k][l] = "";
			}
			for(int l=3;l<5;l++){
				data[k][l] = false;
			}
		}
		
		while(it.hasNext()){
			Map.Entry e = (Map.Entry)it.next();
			String entry = (String) e.getKey();
			AcideInfoDatabaseField info = (AcideInfoDatabaseField) e.getValue();
			data[i][0] = "";
			data[i][1] = entry;
			data[i][2] = info.get_type();
			data[i][3] = info.is_pk();
			data[i][4] = info.is_nn();
			i++;
		}

		DefaultTableModel model = new DefaultTableModel(data, dataModel);
	     
	      _jTable = new JTable(model){

	            private static final long serialVersionUID = 1L;

	            /*@Override
	            public Class getColumnClass(int column) {
	            return getValueAt(0, column).getClass();
	            }*/
				@SuppressWarnings("unchecked")
				@Override
	            public Class getColumnClass(int column) {
	                switch (column) {
	                    case 0:
	                        return JLabel.class;
	                    case 1:
	                        return String.class;
	                    case 3:
	                        return Boolean.class;
	                    case 4:
	                        return Boolean.class;
	                    case 5:
	                        return Boolean.class;
	                    default: return String.class;
	                }
	            }
	            
	            public boolean isCellEditable(int f, int c){
	            	
	            	if(getValueAt(f,3).equals(Boolean.TRUE) && c == 4) { 
	                    return false; 
	                } else if (c == 0) return false;
	            	
	                return true;
	                 
	            }
	         

	            
	        };
	       
	        if (_jTable.getRowCount() == 0)
	        	insertValues();
	        
	        for (int h = 0 ; h< _jTable.getColumnCount()-1; h++){
	        	_jTable.getColumn(_jTable.getColumnName(h)).setWidth(_jTable.getColumn(_jTable.getColumnName(h)).getMaxWidth());
	        	
	        }
	        
	        _jTable.setPreferredScrollableViewportSize(_jTable.getPreferredSize());
	        JScrollPane scrollPane = new JScrollPane(_jTable);
	       // getContentPane().add(scrollPane);
	        
	        _jTable.setSurrendersFocusOnKeystroke(true); 

	        
	        _jTable.addMouseListener(new MouseAdapter() {
				
				@Override
				public void mouseClicked(MouseEvent e) {								
					int row = _jTable.getSelectedRow();
					int col = _jTable.getSelectedColumn();
					int recordIndexAux = Integer.valueOf(_recordIndex);
					_recordIndex = String.valueOf(row+1);
					recordIndexAux = Integer.valueOf(_recordIndex);
					if (_jTable.getValueAt(row, col) == null){
						_jTable.setEditingColumn(col);
						_jTable.setEditingRow(row);
					}
					if(recordIndexAux==_jTable.getRowCount()&& row<=_jTable.getRowCount()-1 && col == 0){
						insertValues();
					}else{
						if (col == 0){
							_jTable.setRowSelectionInterval(row, row);
						}
					}
				}
				
				

				@Override
				public void mouseEntered(MouseEvent arg0) {
					Cursor handCursor = new Cursor(Cursor.HAND_CURSOR);
					_jTable.setCursor(handCursor);				
				}

				@Override
				public void mouseExited(MouseEvent arg0) {
					setCursor(null);				
				}
			});
	        
	        
	        _jTable.addKeyListener(new KeyListener() {
				
				public void keyTyped(KeyEvent arg0) {
					
				}

				public void keyReleased(KeyEvent arg0) {
					// TODO Auto-generated method stub
					
				}
				
				public void keyPressed(KeyEvent arg0) {
					if (arg0.getKeyCode() == KeyEvent.VK_DELETE){
						
						int row = _jTable.getSelectedRow();
	
						DefaultTableModel dtm = (DefaultTableModel) _jTable.getModel(); 
						dtm.removeRow(row);
						
						if (row == 0){
							insertValues();
						}
					}
				}
					
			});
	        
	        _jTable.setSurrendersFocusOnKeystroke(true); 
				        
	        
	        
	        String[] option = {"STRING","INT"};
	        
	        JComboBox options = new JComboBox(option);
	        
	        _jTable.getColumnModel().getColumn(2).setCellEditor(new DefaultCellEditor(options));
	        
	        _jTable.getColumnModel().getColumn(0).setCellRenderer(new RenderLabel());
	        _jTable.getColumnModel().getColumn(0).setPreferredWidth(8);
	        
			_jTable.getInputMap(JTable.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT).put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER,0), "selectNextColumnCell");
			
			_jTable.setPreferredScrollableViewportSize(_jTable.getPreferredSize());	
	        
	        scrollPane.setViewportView(_jTable);
	        
			this.getContentPane().setLayout(new BorderLayout());
			
			getContentPane().add(scrollPane, BorderLayout.CENTER);
			
			JPanel panelButtons = new JPanel();
			ButtonGroup grupo = new ButtonGroup();
			
			grupo.add(_okButton);
			grupo.add(_cancelButton);
			
			panelButtons.add(_okButton);
			panelButtons.add(_cancelButton);
			
			
			getContentPane().add(panelButtons, BorderLayout.SOUTH);
			
	        int x = 460, y = 0;
	        
	        y = 60 * _jTable.getRowCount();
	        if (y < _minimumLenght)
	        	y = _minimumLenght;
	        
	        //1 : horizontal
	        //2 : vertical
	        setSize(x,y);
	        
	        this.setLocationRelativeTo(null);
	        

	       
	}
	
	
	public void insertValues() {
		
//		Vector<String> dataColumns = getDataColumns(_jTable.getRowCount()-1);
//		if(!hasData(dataColumns)) return;
//		String info = AcideDatabaseManager.getInstance().insertValues(table,dataColumns);
//		if(info.equals("success")){			
//			String[] dataLastRow = new String[dataColumns.size()];
//			dataLastRow[0] = null;
//			for(int h =1; h<dataColumns.size(); h++){						
//				dataLastRow[h]="";
//			}
//			((DefaultTableModel) _jTable.getModel()).insertRow(_jTable.getRowCount(), dataLastRow);		
//			_jTable.setPreferredScrollableViewportSize(_jTable.getPreferredSize());		
//			totalRecords = String.valueOf(Integer.valueOf(totalRecords)+1);
//			
//			record.setText(AcideLanguageManager.getInstance().getLabels().getString("s2129")+" "+recordIndex+" "+
//					   AcideLanguageManager.getInstance().getLabels().getString("s2130")+ totalRecords);
//			statusBar.validate();
//		} else {
//			this.setAlwaysOnTop(false);
//			JOptionPane.showMessageDialog(null,info);
//			for(int i =1; i<columns.size(); i++){
//				_jTable.getModel().setValueAt("", _jTable.getRowCount()-1, i);	
//			}
//			this.setAlwaysOnTop(true);
//		}
//		updateWindows();
//		validar();
	
		Object[] dataLastRow = new Object[5];
		//dataLastRow[0] = null;
		for(int h =0; h<3; h++){						
			dataLastRow[h]="";
		}
		for(int h =3; h<5; h++){						
			dataLastRow[h]=false;
		}
		((DefaultTableModel) _jTable.getModel()).insertRow(_jTable.getRowCount(), dataLastRow);		
		_jTable.setPreferredScrollableViewportSize(_jTable.getPreferredSize());	
		
		_jTable.validate();
		_jTable.repaint();


	}
	private class AcideOkDesignViewAction implements ActionListener{

		public void actionPerformed(ActionEvent arg0) {
			

			setCursor(new Cursor(Cursor.WAIT_CURSOR));
				
			int j = 0;
			for (int i = 0; i<_jTable.getRowCount() ; i++){
				if (!_jTable.getValueAt(i, 1).equals(""))
					j++;
			}
			
			if (!_new){
				
				if (j != _initialStatus.size()){
					//Check if the table is empty
					boolean empty = AcideDatabaseManager.getInstance().isEmpty(_database, _table);
					if (!empty){
						JOptionPane.showMessageDialog(AcideMainWindow.getInstance(), AcideLanguageManager.getInstance().getLabels().getString("s2196"), 
									AcideLanguageManager.getInstance().getLabels().getString("s157"), JOptionPane.ERROR_MESSAGE);
						return;						
					}
				}
				
				//Borrar la tabla
				LinkedList<String> res = AcideDatabaseManager.getInstance().dropTable(_database, _table);
				Iterator<String> it =  res.iterator();
				
				String result ="";
				
				boolean found = false;
				
				while (it.hasNext() || !found){
				
					result=it.next();
					
					found = result.contains("$success") || result.contains("KO") || result.contains("Dangling");
				}
				
				if (!(result.contains("$success") || result.contains("KO"))){
					restore();
					JOptionPane.showMessageDialog(AcideMainWindow.getInstance(), AcideLanguageManager.getInstance().getLabels().getString("s2197"), 
							AcideLanguageManager.getInstance().getLabels().getString("s157"), JOptionPane.ERROR_MESSAGE);
					closeWindow();
					return;
				}
				
			}else{
				if (j==0){ //No hay ninguna fila
					JOptionPane.showMessageDialog(AcideMainWindow.getInstance(), AcideLanguageManager.getInstance().getLabels().getString("s2212"), 
							AcideLanguageManager.getInstance().getLabels().getString("s157"), JOptionPane.ERROR_MESSAGE);
					
					return;
				}
				
			}

			if(issueCommands()){
				AcideMainWindow.getInstance().getDataBasePanel().updateDataBaseTree();
				closeWindow();
			}
			
			setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
		
		}

		private boolean issueCommands() {
			// CREATE TABLE
			boolean error = false;
			
			LinkedList<String> results = null;
			
			String sql = "CREATE TABLE "+ _table+" (";
			
			for (int i = 0; i< _jTable.getRowCount() ; i++){
				sql += _jTable.getValueAt(i, 1) +" " + _jTable.getValueAt(i, 2) + ", "; 
			}
			
			sql = sql.substring(0, sql.length()-2) + ")";
			
			results = AcideDatabaseManager.getInstance().executeCommand(sql);
			
			
			if (results.size()==0){
				// PK
				LinkedList<String> pks = new LinkedList<String>();
				
				
				for (int i = 0; i< _jTable.getRowCount() ; i++){
					if((Boolean) _jTable.getValueAt(i, 3))
						pks.add((String) _jTable.getValueAt(i, 1));
				}
				
				if(pks.size()>0){
					boolean result =_manager.pk(_database, _table, pks);
					
					if(!result){
						error = true;
						
						JOptionPane.showMessageDialog(AcideMainWindow.getInstance(), AcideLanguageManager.getInstance().getLabels().getString("s2198"), 
								 AcideLanguageManager.getInstance().getLabels().getString("s157"), JOptionPane.ERROR_MESSAGE);
						
					
						AcideDatabaseManager.getInstance().dropTable(_database, _table);
						restore();
					}
				}
				
				// NN
				LinkedList<String> nns = new LinkedList<String>();
				
				
				for (int i = 0; i< _jTable.getRowCount() ; i++){
					if((Boolean) _jTable.getValueAt(i, 4))
						nns.add((String) _jTable.getValueAt(i, 1));
				}
				
				if(nns.size()>0){
					if(!AcideDatabaseManager.getInstance().nn(_database, _table, nns)){
						error = true;
						
						JOptionPane.showMessageDialog(AcideMainWindow.getInstance(), AcideLanguageManager.getInstance().getLabels().getString("s2199"), 
								 AcideLanguageManager.getInstance().getLabels().getString("s157"), JOptionPane.ERROR_MESSAGE);
					
						AcideDatabaseManager.getInstance().dropTable(_database, _table);
						restore();
					}
				}
			}
			else{
				error = true;
				
				JOptionPane.showMessageDialog(AcideMainWindow.getInstance(), AcideLanguageManager.getInstance().getLabels().getString("s2215"), 
						 AcideLanguageManager.getInstance().getLabels().getString("s157"), JOptionPane.ERROR_MESSAGE);
			
				AcideDatabaseManager.getInstance().dropTable(_database, _table);
				restore();
			}
			
			return !error;
			
		}

		private void restore() {
			
			if (!_new){
				// CREATE TABLE
				String sql = "CREATE TABLE "+ _table+" (";
				
				Iterator<?> it = _initialStatus.entrySet().iterator();
				
				while(it.hasNext()){
					Map.Entry e = (Map.Entry)it.next();
					String entry = (String) e.getKey();
					AcideInfoDatabaseField info = (AcideInfoDatabaseField) e.getValue();
					sql += entry +" "+ info.get_type()+ ", ";
				}
				
				sql = sql.substring(0, sql.length()-2) + ")";
				
				AcideDatabaseManager.getInstance().executeCommand(sql);
				
				// PK
				LinkedList<String> pks = new LinkedList<String>();
				it = _initialStatus.entrySet().iterator();
				
				while(it.hasNext()){
					Map.Entry e = (Map.Entry)it.next();
					String entry = (String) e.getKey();
					AcideInfoDatabaseField info = (AcideInfoDatabaseField) e.getValue();
					if (info.is_pk())
						pks.add(entry);
				}
				
				if (pks.size()>0)
					AcideDatabaseManager.getInstance().pk(_database, _table, pks);
				
				
				// NN
				
				LinkedList<String> nns = new LinkedList<String>();
				it = _initialStatus.entrySet().iterator();
				
				while(it.hasNext()){
					Map.Entry e = (Map.Entry)it.next();
					String entry = (String) e.getKey();
					AcideInfoDatabaseField info = (AcideInfoDatabaseField) e.getValue();
					if (info.is_nn())
						pks.add(entry);
				}
				
				if(nns.size()>0)
					AcideDatabaseManager.getInstance().nn(_database, _table, nns);
			}
			else{
				AcideDatabaseManager.getInstance().dropTable(_database, _table);
			}
				
		}
		
	}
	
	public void setDefaultCloseOperation(int option){
		closeWindow();
	}
	
	private void closeWindow(){
			
		AcideMainWindow.getInstance().setEnabled(true);		
		dispose();
		AcideMainWindow.getInstance().setAlwaysOnTop(true);
		AcideMainWindow.getInstance().setAlwaysOnTop(false);
	}
	
	class RenderLabel implements TableCellRenderer {
		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column)  {
	    	JLabel label =new JLabel();
	    	//if(value==null){
	    		if(table.getRowCount()>1 && row==_jTable.getSelectedRow() && column==0){
	    			label.setIcon(new ImageIcon("./resources/icons/database/row.png"));
				}				
				if((row==table.getRowCount()-1 && column==0)){
					label.setIcon(new ImageIcon("./resources/icons/database/newRow.png"));
				}	
//	    	} else {
//
//			}
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
	

}
