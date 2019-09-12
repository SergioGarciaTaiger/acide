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

package acide.gui.databasePanel.constraintsMenu;

import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.LinkedList;

import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

import acide.gui.databasePanel.AcideDataBasePanel;
import acide.gui.mainWindow.AcideMainWindow;
import acide.language.AcideLanguageManager;
import acide.process.console.AcideDatabaseManager;

/**
 * ACIDE - A Configurable IDE constraints window FK panel.
 * 
 * @version 0.16
 * @see JPanel
 */
public class AcideForeignKeyPanel extends JPanel{

	private static final long serialVersionUID = 1L;
	
	private int _totalTables;
	
	private int _currentTable;
	
	private JTable _table;

	private String _tableNameS, _tableNameT;
	
	private String _database;
	
	private JScrollPane _tableScrollPane;
	
	private LinkedList<String> _names;
	
	private LinkedList<String> _types;
	
	private HashMap<String, Boolean> _foreignKeys;
	
	private LinkedList<String> _fkList;
	
	private HashMap<String, JTable> _tablesFK;
	
	private HashMap<String, LinkedList<String>> _fkKeys;
	
	private JPanel _navigationPanel;
	
	private JLabel _numberTable;
	
	private JButton _previousButton;
	
	private JButton _nextButton;
	
	private JPanel _buttonPanel;
	
	private JButton _closeButton;
	
	private JButton _dropFK;
	
	private JButton _referencedTables;
	
	private String _oldRestriction;
	

	public AcideForeignKeyPanel(String database, String table){
		
		_currentTable = 0;
		
		_totalTables = 0;
		
		_tableNameS = table;
		
		_tableNameT = "";
		
		_database = database;
		
		_oldRestriction = "";
		
		_names = new LinkedList<String>();
		
		_types = new LinkedList<String>();
		
		_fkKeys = new HashMap<String, LinkedList<String>>();
		
		_foreignKeys = new HashMap<String, Boolean>();
		
		_tablesFK = new HashMap<String,JTable>();
		
		_fkList = new LinkedList<String>();
		
		// Initializes the values of some components 
		initializeValues();
		
		// Builds the components of the panel
		buildComponents();
		
		// Adds the components to the panel
		addComponents();

		// Sets the action listeners of the panel components
		setListeners();
		
		// Sets a label by default
		setButtonTitle();
		
	}
	
	/**
	 * Sets a label on the _referencedTables button depending on the existence of foreign keys
	 */
	private void setButtonTitle() {
		
		if (!_fkList.isEmpty() && _currentTable <_totalTables){
			String fk = _fkList.get(_currentTable);
			if(_fkKeys.containsKey(fk)){
				if (!_fkKeys.get(fk).isEmpty())
					_referencedTables.setText(AcideLanguageManager.getInstance().getLabels().getString("s2307"));
			}
		} else	_referencedTables.setText(AcideLanguageManager.getInstance().getLabels().getString("s2308"));
	}

	/**
	 * Adds the attributes and their types to their corresponding lists.
	 */
	private void initializeValues() {
		
		AcideDatabaseManager des = AcideDatabaseManager.getInstance();
		LinkedList<String> names;
		
		names = des.getFields(_database, _tableNameS);
		for(int i=0;i<names.size();i++){
			String[] aux = names.get(i).split(":");
			_names.add(aux[0]);
			_types.add(aux[1]);
		}
		
	}
	
	/**
	 * Assigns by default a FALSE value to each attribute.
	 */
	private void initializeAttributes() {
		
		for(int i=0;i<_names.size();i++)
			_foreignKeys.put(_names.get(i), Boolean.FALSE);
	}
	
	/**
	 * Assigns a TRUE value to each attribute that is a candidate key.
	 * @param fk
	 */
	private void setForeignKeys(String fk) {
			
		if(fk.contains(",")){
			String[] namesFK = fk.split(",");
			for(int j=0;j<namesFK.length;j++){
				String name = namesFK[j];
				_foreignKeys.put(name, Boolean.TRUE);
			}
		} else _foreignKeys.put(fk, Boolean.TRUE);
									
	}

	private void buildComponents() {
		
		_navigationPanel = new JPanel();
		
		_previousButton = new JButton(AcideLanguageManager
				.getInstance().getLabels().getString("s2077"));
		
		_nextButton = new JButton(AcideLanguageManager
				.getInstance().getLabels().getString("s2076"));
		
		_numberTable = new JLabel();
		
		_buttonPanel = new JPanel();
		
		_closeButton = new JButton(AcideLanguageManager
				.getInstance().getLabels().getString("s1048"));
		
		_dropFK = new JButton(AcideLanguageManager
				.getInstance().getLabels().getString("s2050")+" FK");
		
		_referencedTables = new JButton(AcideLanguageManager.getInstance().getLabels().getString("s2308"));

		// Creates the FK tables
		buildTables();
		
		// Creates the first table to be shown
		buildFirstTable();
		
        _tableScrollPane = new JScrollPane(_table);
		
	}

	private void addComponents() {
		
		setLayout(new BorderLayout());
		
		_buttonPanel.add(_dropFK);
		
		_buttonPanel.add(_closeButton);
		
		JLabel blank = new JLabel("           ");
		
		_buttonPanel.add(blank);
		
		_buttonPanel.add(_referencedTables);
		
		_navigationPanel.add(_previousButton);
		
		_navigationPanel.add(_numberTable);

		_navigationPanel.add(_nextButton);
		
		add(_navigationPanel, BorderLayout.NORTH);
		
		add(_buttonPanel, BorderLayout.SOUTH);
		
		add(_tableScrollPane, BorderLayout.CENTER);

	}

	/**
	 * Builds a generic table with values by default
	 */
	private void buildTable() {
		
		initializeAttributes();
		
		MyTableModel model = new MyTableModel();
		model.build();
		
		_table = new JTable(model);

		_table.setPreferredScrollableViewportSize(new Dimension(500, 70));
		_table.setFillsViewportHeight(true);
        
        //Fiddle with the names column's cell editors/renderers.
        setUpCheckBoxColumn(_table, _table.getColumnModel().getColumn(2));
        
        TableCellRenderer rendererFromHeader = _table.getTableHeader().getDefaultRenderer();
        JLabel headerLabel = (JLabel) rendererFromHeader;
        headerLabel.setHorizontalAlignment(JLabel.CENTER);
       
	}
	
	/**
	 * Builds the first table that will be shown in the panel
	 */
	public void buildFirstTable() {
		
		//Builds an empty table if there are no foreign keys 
		if (_totalTables == 0){
			buildTable();
			_numberTable.setText(String.valueOf(_currentTable+1)+ ' ' +AcideLanguageManager
					.getInstance().getLabels().getString("s2303")+ ' ' +String.valueOf(_totalTables+1));
			_oldRestriction = "";
		}
		else{
			_table = _tablesFK.get(_fkList.get(0));
			_oldRestriction = _fkList.get(0);
			_currentTable = 0;
			_numberTable.setText(String.valueOf(_currentTable+1)+ ' ' +AcideLanguageManager
					.getInstance().getLabels().getString("s2303")+ ' ' +String.valueOf(_totalTables));
		}

	}
	
	/**
	 * Build tables for each existing foreign key and stores them in a hash
	 */
	private void buildTables() {
		
		AcideDatabaseManager des = AcideDatabaseManager.getInstance();
		
		LinkedList<String> list = des.getForeignKey(_database, _tableNameS);
		
		for(int i=0;i<list.size();i++){
		
			String aux = list.get(i);
			String[] aux2 = aux.split("->");
			
			//Attribute's name of the source table 
			String keyS = aux2[0];
			keyS = keyS.substring(keyS.indexOf("[")+1, keyS.indexOf("]"));

			// Adds each foreign key to a list
			if (!_fkList.contains((String) keyS))
				_fkList.add(keyS);
			
			// Updates its list of referenced tables
			String keyT = aux2[1];
			LinkedList<String> listTarget = null;
			
			if (!_fkKeys.containsKey(keyS))
				listTarget  = new LinkedList<String>();
			else 
				listTarget = _fkKeys.get(keyS);
			
			// Stores the foreign key and its corresponding list of referenced tables in a hash 
			listTarget.add(keyT);
			_fkKeys.put(keyS,listTarget);
			
			// Initializes the attributes with default values
			initializeAttributes();
			
			// Sets the foreign key
			setForeignKeys(keyS);
			
			// Builds a table for the current foreign key
			MyTableModel model = new MyTableModel();
			model.build();
			
			JTable table = new JTable(model);

			table.setPreferredScrollableViewportSize(new Dimension(500, 70));
			table.setFillsViewportHeight(true);
	        
	        //Fiddle with the names column's cell editors/renderers.
	        setUpCheckBoxColumn(table, table.getColumnModel().getColumn(2));
	        
	        TableCellRenderer rendererFromHeader = table.getTableHeader().getDefaultRenderer();
	        JLabel headerLabel = (JLabel) rendererFromHeader;
	        headerLabel.setHorizontalAlignment(JLabel.CENTER);
	        
	     // Stores the table in a hash
	        _tablesFK.put(keyS, table);
		}
		
		_totalTables = _fkList.size();
		
	}

	private void setUpCheckBoxColumn(JTable _table, TableColumn column) {
		
		JCheckBox cBox = new JCheckBox();
		cBox.setHorizontalAlignment(JCheckBox.CENTER);
		
		column.setCellEditor(new DefaultCellEditor(cBox));
	}
	
	
	private void setListeners() {
		
		_previousButton.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				
				// Shows a table for each foreign key until the first one in the list is reached
				if ((_totalTables> 0)&&(_currentTable>0)){
					
					getAcideForeignKeyPanel().remove(_tableScrollPane);
					
					_table = _tablesFK.get(_fkList.get(_currentTable-1));
					_tableScrollPane = new JScrollPane(_table);
					getAcideForeignKeyPanel().add(_tableScrollPane);
					getAcideForeignKeyPanel().validate();
					
					_currentTable--;
					
					_oldRestriction = _fkList.get(_currentTable);
					
					setButtonTitle();
					
					_numberTable.setText(String.valueOf(_currentTable+1)+ ' ' +AcideLanguageManager
							.getInstance().getLabels().getString("s2303")+ ' ' +String.valueOf(_totalTables));
				}
			}
			
		});
		
		_nextButton.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				
				// Shows a table for each foreign key until the last one in the list is reached
				if ((_totalTables> 0) && (_currentTable<_totalTables-1)){
					
					getAcideForeignKeyPanel().remove(_tableScrollPane);
					
					_table = _tablesFK.get(_fkList.get(_currentTable+1));
					_tableScrollPane = new JScrollPane(_table);
					getAcideForeignKeyPanel().add(_tableScrollPane);
					getAcideForeignKeyPanel().validate();
					
					_currentTable++;
					
					_oldRestriction = _fkList.get(_currentTable);
					
					setButtonTitle();
					
					_numberTable.setText(String.valueOf(_currentTable+1)+ ' ' +AcideLanguageManager
							.getInstance().getLabels().getString("s2303")+ ' ' +String.valueOf(_totalTables));
				}
				// No more foreign keys to be shown so create and show a by default table to allow creating a new FK
				else if (_currentTable == _totalTables-1){
					
					buildTable();
					
					getAcideForeignKeyPanel().remove(_tableScrollPane);
					
					_tableScrollPane = new JScrollPane(_table);
					getAcideForeignKeyPanel().add(_tableScrollPane);
					getAcideForeignKeyPanel().validate();
					
					_currentTable = _totalTables;
					
					_oldRestriction = "";
					
					setButtonTitle();
					
					_numberTable.setText(String.valueOf(_currentTable+1)+ ' ' +AcideLanguageManager
							.getInstance().getLabels().getString("s2303")+ ' ' +String.valueOf(_totalTables+1));
			
				}
			}
			
		});
		
		_dropFK.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				
				saveChanges();
			}
			
		});

		_closeButton.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				closeWindow();	
			}
			
		});
		
		_referencedTables.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				
				String fk = "";
				
				// If there are no referenced tables associated to the current foreign key
	            if (_referencedTables.getText().equals(AcideLanguageManager.getInstance().getLabels().getString("s2308"))){
	            	fk = getRestriction();
	            	if (!fk.isEmpty())
	            		// First choose a table to be referenced from a list
	            		new AcideForeingKeyPanelTableChooser(_database, _tableNameS,fk, getAcideForeignKeyPanel());
	            	else 
	            		JOptionPane.showMessageDialog(null, AcideLanguageManager.getInstance().getLabels().getString("s2314"));
	            	
	            	_tableNameT = "";
	            }
	            else {	
	            		fk = _fkList.get(_currentTable);
	            		LinkedList<String> fkList = _fkKeys.get(fk);
	            		// Shows a new panel containing the referenced tables associated to the current foreign key
	            		new AcideForeignKeyTargetTable(_database, _tableNameS, fk, fkList, "", false, getAcideForeignKeyPanel());
	            }
	
			}
			
		});
		
	}
	
	private void closeWindow() {
		// Enables the main window again
		AcideMainWindow.getInstance().setEnabled(true);

		// Closes the window
		((AcideConstraintDefinitionWindow)getParent().getParent().getParent().getParent().getParent()).dispose();

		// Brings the main window to the front
		AcideMainWindow.getInstance().setAlwaysOnTop(true);

		// But not permanently
		AcideMainWindow.getInstance().setAlwaysOnTop(false);
		
	}
	
	/**
	 * Saves the deletions performed on the current shown table
	 */
	public void saveChanges() {
		
		AcideDatabaseManager des = AcideDatabaseManager.getInstance();
		String restriction = getRestriction();
		boolean success = false;

		if (restriction.equals(_oldRestriction) && _fkList.contains((String) restriction)){
			
			LinkedList<String> resList = _fkKeys.get(restriction);
			
			// Delete all referenced tables associated to the current foreign key
			for(int i = 0; i<resList.size(); i++){
				
				String res = resList.get(i);
				String tableT = res.substring(0,res.indexOf("."));
				String nameT = res.substring(res.indexOf(".")+1, res.length());
				
				String command = ":-fk("+_tableNameS+",["+ restriction +"],"+ tableT + "," + nameT + ")";
				
				setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				
				String result = des.dropRestriction(command);
				
				success = true & checkResult(result);
				
				setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
				}
						
			}
			
			if (success){
				JOptionPane.showMessageDialog(null,AcideLanguageManager
						.getInstance().getLabels().getString("s2315"), AcideLanguageManager
						.getInstance().getLabels().getString("s2316"),JOptionPane.INFORMATION_MESSAGE);
				updatePanel();
			}
		
	}
	
	/**
	 * Builds a restriction by gathering the attributes of the table that have a check of validity
	 * @return String restriction
	 */
	public String getRestriction(){
		String restriction = "";

		for(int i=0; i<_table.getModel().getRowCount();i++){
			boolean marked = (Boolean) _table.getModel().getValueAt(i, 2);
			if(marked){
				String name = (String) _table.getModel().getValueAt(i, 0);
				restriction = restriction + name + ",";
			}
		}
		
		if (restriction.endsWith(","))
			restriction = restriction.substring(0, restriction.lastIndexOf(","));
		
		return restriction;
	}
	
	/**
	 * Checks if the operation has been done successfully and shows a message
	 * @param result
	 * @return boolean res
	 */
	public boolean checkResult(String result){
		
		boolean res = false;
		
		if (result.contains("$success")){
			AcideDataBasePanel panel = AcideMainWindow.getInstance().getDataBasePanel();
			panel.updateDataBaseTree();
			JOptionPane.showMessageDialog(null,AcideLanguageManager
					.getInstance().getLabels().getString("s2315"), AcideLanguageManager
					.getInstance().getLabels().getString("s2316"),JOptionPane.INFORMATION_MESSAGE);
			res = true;
		}
		else if (result.contains("Syntax error")){
			JOptionPane.showMessageDialog(null, AcideLanguageManager.getInstance().getLabels().getString("s2314"));
		}
		else {
			JOptionPane.showMessageDialog(null,result, AcideLanguageManager.getInstance()
					.getLabels().getString("s157"), JOptionPane.OK_OPTION);
		}
		
		return res;
	}

	/**
	 * Updates the panel by rebuilding the structures that deal with foreign keys values
	 */
    public void updatePanel() {
       	
    	_fkList.clear();
    	
    	_tablesFK.clear();
    	
    	_fkKeys.clear();
    	
    	buildTables();
    	
    	buildFirstTable();
    	
    	remove(_tableScrollPane);
	
		_tableScrollPane = new JScrollPane(_table);
		
		add(_tableScrollPane);
		
		validate();
		
		setButtonTitle();
	}
	
    /**
     * Returns this panel
     * @return AcideForeignKeyPanel panel
     */
	public AcideForeignKeyPanel getAcideForeignKeyPanel(){
		return this;
	}
	
	/**
	 * Returns the current referenced table name
	 * @return String _tableNameT
	 */
	public String getTableNameT() {
		return _tableNameT;
	}

	/**
	 * Sets the current referenced table name
	 * @param tableNameT String
	 */
	public void setTableNameT(String tableNameT) {
		_tableNameT = tableNameT;
	}
	
    /**
     * Private class implemented to build the grid of a table with specific values
     * @see AbstractTableModel
     */
    @SuppressWarnings("unchecked")
	private class MyTableModel extends AbstractTableModel{
		
		private static final long serialVersionUID = 1L;
		
		private String[] headers; 
		
		private Object[][] data;
		
		
		void build(){
			String[] cols = {AcideLanguageManager.getInstance().getLabels().getString("s2299")
					,AcideLanguageManager.getInstance().getLabels().getString("s2170")
					,AcideLanguageManager.getInstance().getLabels().getString("s2032")};
			headers = cols;
			data = new Object[_names.size()][headers.length];
			
			for(int i=0; i<_names.size(); i++){
				data[i][0] = _names.get(i);
				data[i][1] = _types.get(i);
				data[i][2] = _foreignKeys.get(_names.get(i));
			}
		}
		
		public int getColumnCount() {
		    return headers.length;
		}

		public int getRowCount() {
		    return data.length;
		}

		public String getColumnName(int col) {
		    return headers[col];
		}

		public Object getValueAt(int row, int col) {
		    return data[row][col];
		}
		
		public Class getColumnClass(int c) {
            return getValueAt(0, c).getClass();
        }
        
        public boolean isCellEditable(int row, int col) {
           
        	return (col == 2);
        }
        
        public void setValueAt(Object value, int row, int col) {	
            data[row][col] = value;
            fireTableCellUpdated(row, col);
        }
        
        public void fireTableDataChanged(){
        	build();
        }
	}	
	
}
