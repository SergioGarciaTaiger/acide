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
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.HashMap;
import java.util.LinkedList;

import javax.swing.DefaultCellEditor;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
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
 * ACIDE - A Configurable IDE constraints window FK target panel.
 * 
 * @version 0.16
 * @see JFrame
 */
public class AcideForeignKeyTargetTable extends JFrame{

	private static final long serialVersionUID = 1L;
	
	private static final String WINDOW_ICON = "./resources/images/icon.png";
	
	private int _totalTables;
	
	private int _currentTable;
	
	private boolean _newTable;
	
	private JTable _table;
	
	private String _fkSource;

	private String _tableNameS, _tableNameT;
	
	private String _database;
	
	private JScrollPane _tableScrollPane;
	
	private LinkedList<String> _names;
	
	private LinkedList<String> _types;
	
	private HashMap<String, Boolean> _fkTargets;
	
	private LinkedList<String> _fkList;
	
	private HashMap<String, JTable> _tablesFK;
	
	private JPanel _navigationPanel;
	
	private JLabel _numberTable;
	
	private JButton _previousButton;
	
	private JButton _nextButton;
	
	private JPanel _buttonPanel;
	
	private JButton _applyButton;
	
	private JButton _closeButton;
	
	
	private JButton _dropFK;

	private String _oldRestriction;
	
	private boolean _firstTable;
	
	private AcideForeignKeyPanel _fkPanel;
	

	public AcideForeignKeyTargetTable(String database, String nameS, String fk, LinkedList<String> fkList
			, String nameT, boolean firstTable, AcideForeignKeyPanel fkPanel){
		
		_currentTable = 0;
		
		_totalTables = 0;
			
		_newTable = false;
		
		_database = database;
		
		_tableNameS = nameS;
		
		_tableNameT = nameT;
		
		_fkSource = fk;
		
		_firstTable = firstTable;
		
		_fkPanel = fkPanel;
		
		_oldRestriction = "";
		
		_names = new LinkedList<String>();
		
		_types = new LinkedList<String>();
		
		_fkTargets = new HashMap<String, Boolean>();
		
		_tablesFK = new HashMap<String,JTable>();
		
		_fkList = new LinkedList<String>();
		
		// Initialize the possible existing foreign keys
		initializeFKeys(fkList);
		
		// Builds the panel components
		buildComponents();

		// Sets the look and feel of the panel
		setLookAndFeel();
		
		// Sets the action listeners of the window components
		setListeners();
		
		setVisible(true);
	}

	/**
	 * Sets the list of referenced tables given by the parent panel for the current foreign key
	 * @param fkList LinkedList<String>
	 */
	public void initializeFKeys(LinkedList<String> fkList) {
		
		if (fkList != null)
			_fkList = fkList;
	}

	private void buildComponents() {
		
		_navigationPanel = new JPanel();
		
		_previousButton = new JButton(AcideLanguageManager
				.getInstance().getLabels().getString("s2077"));
		
		_nextButton = new JButton(AcideLanguageManager
				.getInstance().getLabels().getString("s2076"));
		
		_numberTable = new JLabel();
		
		_buttonPanel = new JPanel();
		
		_applyButton = new JButton(AcideLanguageManager
				.getInstance().getLabels().getString("s335"));
		
		_closeButton = new JButton(AcideLanguageManager
				.getInstance().getLabels().getString("s1048"));
		
		_dropFK = new JButton(AcideLanguageManager
				.getInstance().getLabels().getString("s2050")+" FK");
		
		// Creates the referenced tables
		buildTables();
		
		// Creates the first table to be shown
		buildFirstTable();
		
        _tableScrollPane = new JScrollPane(_table);

	}
	
	/**
	 * Builds the first table that will be shown in the panel
	 */
	private void buildFirstTable() {
		
		// There are no referenced attributes yet for the source foreign key
		if(_firstTable){
			setFieldsTable();
			initializeAttributes();
			buildTable();
			_numberTable.setText(String.valueOf(_currentTable+1)+ ' ' +AcideLanguageManager
					.getInstance().getLabels().getString("s2303")+ ' ' +String.valueOf(_totalTables+1));
			_oldRestriction = "";
		}
		else{ 	
				String nameT = _fkList.get(0);
				_tableNameT = nameT.substring(0, nameT.indexOf("."));
				_table = _tablesFK.get(_fkList.get(0));
				_oldRestriction = _fkList.get(0);
				_numberTable.setText(String.valueOf(_currentTable+1)+ ' ' +AcideLanguageManager
						.getInstance().getLabels().getString("s2303")+ ' ' +String.valueOf(_totalTables));	
		}
		
	}

	/**
	 * Adds the attributes and their types to their corresponding lists.
	 */
	private void setFieldsTable() {
		
		AcideDatabaseManager des = AcideDatabaseManager.getInstance();
		
		_names.clear();
		_types.clear();
		
		LinkedList<String> names = des.getFields(_database, _tableNameT);
		
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
		
		_fkTargets.clear();
		
		for(int i=0;i<_names.size();i++)
			_fkTargets.put(_names.get(i), Boolean.FALSE);
		
	}
	
	/**
	 * Assigns a TRUE value to each attribute that is a referenced attribute of the current foreign key
	 * @param fk
	 */
	private void setForeignKeys(String fk) {
		if(fk.contains(",")){
			String[] namesFK = fk.split(",");
			for(int i=0;i<namesFK.length;i++){
				String name = namesFK[i];
				_fkTargets.put(name, Boolean.TRUE);
			}
		} else _fkTargets.put(fk, Boolean.TRUE);
	}

	/**
	 * Build tables for each existing referenced attribute of the current foreign key and stores them in a hash
	 */
	private void buildTables() {
		
		_totalTables = _fkList.size();
		
		for(int i=0;i<_totalTables;i++){
			
			String nameT = _fkList.get(i);
			nameT = nameT.trim();
			
			// Gets the referenced table name of the current foreign key
			_tableNameT = nameT.substring(0, nameT.indexOf("."));
			
			// Sets the corresponding fields of the current referenced table
			setFieldsTable();
			
			// Initializes the fields with default values
			initializeAttributes();
			
			// Gets the referenced attributes of the current foreign key
			String fk = nameT.substring(nameT.indexOf("[")+1, nameT.indexOf("]"));
			
			// Sets the referenced attributes of the current foreign key
			setForeignKeys(fk); 
			
			// Builds a table for the current referenced table
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
	        _tablesFK.put(_fkList.get(i), table);
		}
		
	}

	private void setLookAndFeel(){
		
		setTitle(AcideLanguageManager.getInstance().getLabels().getString("s2310")+' '+ _fkSource
				+' '+ AcideLanguageManager.getInstance().getLabels().getString("s2313")+' '+ _tableNameT);
		
		setIconImage(new ImageIcon(WINDOW_ICON).getImage());
		
		setLayout(new BorderLayout());
		
		_navigationPanel.add(_previousButton);
		
		_navigationPanel.add(_numberTable);

		_navigationPanel.add(_nextButton);
		
		add(_navigationPanel, BorderLayout.NORTH);
		
		add(_tableScrollPane, BorderLayout.CENTER);
		
		_buttonPanel.add(_dropFK);
		
		_buttonPanel.add(_applyButton);
		
		_buttonPanel.add(_closeButton);

		add(_buttonPanel, BorderLayout.SOUTH);

		setLocationRelativeTo(null);
		
		setResizable(true);

		pack();
	}

	/**
	 * Builds a generic table with values by default
	 */
	private void buildTable() {
		
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

	
	private void setUpCheckBoxColumn(JTable _table, TableColumn column) {
		
		JCheckBox cBox = new JCheckBox();
		cBox.setHorizontalAlignment(JCheckBox.CENTER);
		
		column.setCellEditor(new DefaultCellEditor(cBox));
	}

	
	private void setListeners() {
		
		addWindowListener(new WindowListener(){

			@Override
			public void windowActivated(WindowEvent arg0) {
			}

			@Override
			public void windowClosed(WindowEvent arg0) {
				_fkPanel.updatePanel();
			}

			@Override
			public void windowClosing(WindowEvent arg0) {
				_fkPanel.updatePanel();
			}

			@Override
			public void windowDeactivated(WindowEvent arg0) {	
			}

			@Override
			public void windowDeiconified(WindowEvent arg0) {				
			}

			@Override
			public void windowIconified(WindowEvent arg0) {	
			}

			@Override
			public void windowOpened(WindowEvent arg0) {
			}
			
		});
		
		_previousButton.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				
				// Shows a table for each referenced table until the first one in the list is reached
				if ((_totalTables> 0)&&(_currentTable>0)){
					
					getAcideForeignKeyTargetTable().remove(_tableScrollPane);
					
					_table = _tablesFK.get(_fkList.get(_currentTable-1));
					_tableScrollPane = new JScrollPane(_table);
					getAcideForeignKeyTargetTable().add(_tableScrollPane);
					getAcideForeignKeyTargetTable().validate();
					
					_currentTable--;
					
					_oldRestriction = _fkList.get(_currentTable);
					
					_numberTable.setText(String.valueOf(_currentTable+1)+ ' ' +AcideLanguageManager
							.getInstance().getLabels().getString("s2303")+ ' ' +String.valueOf(_totalTables));
				}
			}
		});
		
		_nextButton.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				
				// Shows a table for each referenced table until the last one in the list is reached
				if ((_totalTables> 0) && (_currentTable<_totalTables-1)){
					
					getAcideForeignKeyTargetTable().remove(_tableScrollPane);
					
					_table = _tablesFK.get(_fkList.get(_currentTable+1));
					_tableScrollPane = new JScrollPane(_table);
					getAcideForeignKeyTargetTable().add(_tableScrollPane);
					getAcideForeignKeyTargetTable().validate();
					
					_currentTable++;
					
					_oldRestriction = _fkList.get(_currentTable);
					
					_numberTable.setText(String.valueOf(_currentTable+1)+ ' ' +AcideLanguageManager
							.getInstance().getLabels().getString("s2303")+ ' ' +String.valueOf(_totalTables));
				}
				// No more referenced tables to be shown so allow choosing a new one
				else if (_currentTable == _totalTables-1){
					new AcideForeingKeyPanelTableChooser(_database, _tableNameS, getAcideForeignKeyTargetTable());
				}
			}
		});
		
		
		_dropFK.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {

				AcideDatabaseManager des = AcideDatabaseManager.getInstance();
				
				if (!_oldRestriction.isEmpty()){
					
					String oldRestrictionSimple = _oldRestriction.substring(_oldRestriction.indexOf("[")+1, _oldRestriction.indexOf("]"));
				
					String oldCommand = ":-fk("+ _tableNameS + ",[" + _fkSource + "]," + _tableNameT + ",["+ oldRestrictionSimple +"])";
					
					String result = des.dropRestriction(oldCommand);
					
					if (checkResult(result)) 
						dropRestrictionLocal();
				}
			}
		});
		
		_applyButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				saveChanges();
			}
		});


		_closeButton.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				dispose();
			}	
		});
		
	}
	
	/**
	 * Builds a table for new referenced table
	 * @param tableNameT
	 */
	public void buildNewTable(String tableNameT) {
		
		// Checks first if a valid referenced table has been chosen
		if (!tableNameT.equals("No Tables") || !tableNameT.isEmpty()){
			
			getAcideForeignKeyTargetTable().remove(_tableScrollPane);
			
			buildTables();
			
			_tableNameT = tableNameT;
			
			setFieldsTable();
			
			initializeAttributes();
			
			buildTable();
			
			_tableScrollPane = new JScrollPane(_table);
			getAcideForeignKeyTargetTable().add(_tableScrollPane);
			getAcideForeignKeyTargetTable().validate();
			
			_newTable = true;
			
			_currentTable = _totalTables;
			
			_oldRestriction = "";
			
			_numberTable.setText(String.valueOf(_currentTable+1)+ ' ' +AcideLanguageManager
					.getInstance().getLabels().getString("s2303")+ ' ' +String.valueOf(_totalTables+1));
		}
	}

    /**
     * Returns this window
     * @return AcideForeignKeyTargetTable window
     */
	public AcideForeignKeyTargetTable getAcideForeignKeyTargetTable() {
		return this;
	}
	
	/**
	 * Saves the modifications performed on the current shown referenced table
	 */
	public void saveChanges(){
		
		AcideDatabaseManager des = AcideDatabaseManager.getInstance();
		String restriction = getRestriction();
		String restrictionSimple = restriction;
		restriction = _tableNameT + ".[" + restriction + "]"; 
		
		
		String oldRestrictionSimple ="";
		
		if (!_oldRestriction.isEmpty())
			oldRestrictionSimple = _oldRestriction.substring(_oldRestriction.indexOf("[")+1, _oldRestriction.indexOf("]"));

		if (!restriction.equals(_oldRestriction)){
			String command = ":-fk("+ _tableNameS + ",[" + _fkSource + "]," + _tableNameT + ",["+ restrictionSimple +"])";
			String oldCommand = ":-fk("+ _tableNameS + ",[" + _fkSource + "]," + _tableNameT + ",["+ oldRestrictionSimple +"])";
			String result = "";
			
			setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
			
			if(!restrictionSimple.isEmpty()){
				
				if (!_fkList.contains(restriction)){
					
					//if it's the by default referenced table or a new one has been added
					if ((_newTable || _totalTables ==0)){
						result = des.createRestriction(command);
						if (checkResult(result))
							createRestrictionLocal(restriction, restrictionSimple);
					}
					else{
						result = des.dropRestriction(oldCommand);
						if (checkResult(result)){
							result = des.createRestriction(command);
							if (checkResult(result))
								updateRestrictionLocal(restriction, restrictionSimple);
							}
					}
				
				} else	{
							JOptionPane.showMessageDialog(null,AcideLanguageManager
									.getInstance().getLabels().getString("s2309"));
						}
			}	
			else{
					result = des.dropRestriction(oldCommand);
					if (checkResult(result))
						dropRestrictionLocal();
				}
			
			setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			
			_oldRestriction = restriction;
		}
			
	}
	
	/**
	 * Updates the parameters of the local structures that manage referenced attributes of the current FK after an insertion
	 * @param restriction String
	 */
	public void createRestrictionLocal(String restriction, String restrictionSimple) {
		
		_fkList.add(restriction);
		
		initializeAttributes();
		
		setForeignKeys(restrictionSimple);
		
    	((MyTableModel) _table.getModel()).fireTableDataChanged();
    	
    	_tablesFK.put(restriction, _table); 
    	
		_totalTables++;
		
		if (_newTable){ 
			_currentTable = _fkList.size()-1;
			_newTable = false;
		}
		
	}
	
	/**
	 * Updates the parameters of the local structures that manage referenced attributes of the current FK after a modification
	 * @param restriction String
	 */
	public void updateRestrictionLocal(String restriction, String restrictionSimple){

		_fkList.set(_currentTable, restriction);
		
		initializeAttributes();
		
		setForeignKeys(restrictionSimple);
		
    	((MyTableModel) _table.getModel()).fireTableDataChanged();
    	
    	_tablesFK.remove(_oldRestriction);
    	_tablesFK.put(restriction, _table);
    	
	}
	
	/**
	 * Updates the parameters of the local structures that manage referenced attributes of the current FK after a deletion
	 * @param restriction String
	 */
	private void dropRestrictionLocal() {
		
		if (_totalTables > 0){

			// Removes the referenced table name from the list
			_fkList.remove(_currentTable);
			
			// Remove its corresponding table from the hash
			_tablesFK.remove(_oldRestriction);
	
			// There are still referenced tables
			if (!_fkList.isEmpty()){
				_totalTables--;
				
				if(_currentTable>0)
					_previousButton.doClick();
				else{	
						_currentTable = -1;
						_nextButton.doClick();
					}
			} 
			else{// Just removed the only one referenced table so update the current foreign key in the parent panel
				 _fkPanel.updatePanel();
				 dispose();
			}
			
			_oldRestriction = "";
		
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
		
		private String[] headers = {AcideLanguageManager.getInstance().getLabels().getString("s2299")
				,AcideLanguageManager.getInstance().getLabels().getString("s2170")
				,AcideLanguageManager.getInstance().getLabels().getString("s2032")};
		
		private Object[][] data;
		
		
		public void build(){
			
			data = new Object[_names.size()][headers.length];
			
			for(int i=0; i<_names.size(); i++){
				data[i][0] = _names.get(i);
				data[i][1] = _types.get(i);
				data[i][2] = _fkTargets.get(_names.get(i));
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

