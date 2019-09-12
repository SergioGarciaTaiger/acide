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
 * ACIDE - A Configurable IDE constraints window CK panel.
 * 
 * @version 0.16
 * @see JPanel
 */
public class AcideCandidateKeyPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	
	private int _totalTables;
	
	private int _currentTable;
	
	private boolean _newTable;
	
	private JTable _table;

	private String _tableName;
	
	private String _database;
	
	private JScrollPane _tableScrollPane;
	
	private LinkedList<String> _names;
	
	private LinkedList<String> _types;
	
	private HashMap<String, Boolean> _candidateKeys;
	
	private LinkedList<String> _ckList;
	
	private HashMap<String, JTable> _tablesCK;
	
	private JPanel _navigationPanel;
	
	private JLabel _numberTable;
	
	private JButton _previousButton;
	
	private JButton _nextButton;
	
	private JPanel _buttonPanel;
	
	private JButton _applyButton;
	
	private JButton _closeButton;
	
	private JButton _dropCK;

	private String _oldRestriction;
	
	
	public AcideCandidateKeyPanel(String database, String name){
		
		_currentTable = 0;
		
		_totalTables = 0;
			
		_newTable = false;
		
		_tableName = name;
		
		_database = database;
		
		_oldRestriction = "";
		
		_names = new LinkedList<String>();
		
		_types = new LinkedList<String>();
		
		_candidateKeys = new HashMap<String, Boolean>();
		
		_tablesCK = new HashMap<String,JTable>();
		
		_ckList = new LinkedList<String>();
		
		// Initializes the values of some components 
		initializeValues();
		
		// Builds the panel components
		buildComponents();
		
		// Adds the components to the panel
		addComponents();

		// Sets the action listeners of the panel components
		setListeners();
	}
	
	/**
	 * Adds the attributes and their types to their corresponding lists.
	 */
	private void initializeValues() {
		
		AcideDatabaseManager des = AcideDatabaseManager.getInstance();

		LinkedList<String> names = des.getFields(_database, _tableName);
		
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
			_candidateKeys.put(_names.get(i), Boolean.FALSE);
	}

	/**
	 * Assigns a TRUE value to each attribute that is a candidate key
	 * @param ck
	 */
	private void setCandidateKeys(String ck) {
		
		if(ck.contains(",")){
			String[] namesCK = ck.split(",");
			for(int i=0;i<namesCK.length;i++){
				String name = namesCK[i];
				_candidateKeys.put(name, Boolean.TRUE);
			}
		} else _candidateKeys.put(ck, Boolean.TRUE);
	
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
		
		_dropCK = new JButton(AcideLanguageManager
				.getInstance().getLabels().getString("s2050")+" CK");
		
		// Creates the CK tables
		buildTables();
		
		// Creates the first table to be shown
		buildFirstTable();
		
        _tableScrollPane = new JScrollPane(_table);
		
	}
	
	/**
	 * Builds a generic table with values by default
	 */
	public void buildTable(){
		
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
		
		//Builds an empty table if there are no candidate keys 
		if (_totalTables == 0){
			buildTable();
			_numberTable.setText(String.valueOf(_currentTable+1)+ ' ' +AcideLanguageManager
					.getInstance().getLabels().getString("s2303")+ ' ' +String.valueOf(_totalTables+1));
			_oldRestriction = "";
		}
		else{
			_table = _tablesCK.get(_ckList.get(0));
			_oldRestriction = _ckList.get(0);
			_currentTable = 0;
			_numberTable.setText(String.valueOf(_currentTable+1)+ ' ' +AcideLanguageManager
					.getInstance().getLabels().getString("s2303")+ ' ' +String.valueOf(_totalTables));
		}

	}

	private void addComponents() {
		
		setLayout(new BorderLayout());
		
		_navigationPanel.add(_previousButton);
		
		_navigationPanel.add(_numberTable);

		_navigationPanel.add(_nextButton);
		
		add(_navigationPanel, BorderLayout.NORTH);
		
		add(_tableScrollPane, BorderLayout.CENTER);
		
		_buttonPanel.add(_dropCK);
		
		_buttonPanel.add(_applyButton);
		
		_buttonPanel.add(_closeButton);
	
		add(_buttonPanel, BorderLayout.SOUTH);	
	
	}

	/**
	 * Build tables for each existing candidate key and stores them in a hash
	 */
	private void buildTables() {
		
		AcideDatabaseManager des = AcideDatabaseManager.getInstance();
		
		LinkedList<String> list = des.getCandidateKey(_database, _tableName);
		
		_totalTables = list.size();
		
		for(int i=0;i<_totalTables;i++){
			
			// Adds each candidate key to a list
			String ck = list.get(i);
			ck = ck.substring(ck.indexOf("[")+1, ck.indexOf("]"));
			_ckList.add(ck);
			
			// Initializes the attributes with default values
			initializeAttributes();
			
			// Sets the candidate key
			setCandidateKeys(ck);
			
			// Builds a table for the current candidate key
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
	        _tablesCK.put(ck, table);
		}
		
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
				
				// Shows a table for each candidate key until the first one in the list is reached
				if ((_totalTables> 0)&&(_currentTable>0)){
					
					getAcideCandidateKeyPanel().remove(_tableScrollPane);
					
					_table = _tablesCK.get(_ckList.get(_currentTable-1));
					_tableScrollPane = new JScrollPane(_table);
					getAcideCandidateKeyPanel().add(_tableScrollPane);
					getAcideCandidateKeyPanel().validate();
					
					_currentTable--;
					
					_oldRestriction = _ckList.get(_currentTable);
					
					_numberTable.setText(String.valueOf(_currentTable+1)+ ' ' +AcideLanguageManager
							.getInstance().getLabels().getString("s2303")+ ' ' +String.valueOf(_totalTables));
				}
			}
			
		});
		
		_nextButton.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				
				// Shows a table for each candidate key until the last one in the list is reached
				if ((_totalTables> 0) && (_currentTable<_totalTables-1)){
					
					getAcideCandidateKeyPanel().remove(_tableScrollPane);
					
					_table = _tablesCK.get(_ckList.get(_currentTable+1));
					_tableScrollPane = new JScrollPane(_table);
					getAcideCandidateKeyPanel().add(_tableScrollPane);
					getAcideCandidateKeyPanel().validate();
					
					_currentTable++;
					
					_oldRestriction = _ckList.get(_currentTable);
					
					_numberTable.setText(String.valueOf(_currentTable+1)+ ' ' +AcideLanguageManager
							.getInstance().getLabels().getString("s2303")+ ' ' +String.valueOf(_totalTables));
				}
				// No more candidate keys to be shown so create and show a by default table to allow creating a new CK
				else if (_currentTable == _totalTables-1){
					
					buildTable();
					
					getAcideCandidateKeyPanel().remove(_tableScrollPane);
					
					_tableScrollPane = new JScrollPane(_table);
					getAcideCandidateKeyPanel().add(_tableScrollPane);
					getAcideCandidateKeyPanel().validate();
					
					_newTable = true;
					
					_currentTable = _totalTables;
					
					_oldRestriction = "";
					
					_numberTable.setText(String.valueOf(_currentTable+1)+ ' ' +AcideLanguageManager
							.getInstance().getLabels().getString("s2303")+ ' ' +String.valueOf(_totalTables+1));
			
				}
			}
			
		});
		
		_dropCK.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {

				AcideDatabaseManager des = AcideDatabaseManager.getInstance();
				
				String oldCommand = ":-ck("+ _tableName + ",[" + _oldRestriction + "])";
				
				String result = des.dropRestriction(oldCommand);
				
				if (checkResult(result)) 
			
					dropRestrictionLocal();
				
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
				closeWindow();	
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
	 * Saves the modifications performed on the current shown table
	 */
	public void saveChanges(){
		
		AcideDatabaseManager des = AcideDatabaseManager.getInstance();
		String restriction = getRestriction();

		if (!restriction.equals(_oldRestriction)){
			String command = ":-ck("+ _tableName + ",[" + restriction + "])";
			String oldCommand = ":-ck("+ _tableName + ",[" + _oldRestriction + "])";
			String result = "";
			
			setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
			if(!restriction.isEmpty()){
				
				if (!_ckList.contains(restriction)){
					
					//if it's the by default table or a new one has been added
					if ((_newTable || _totalTables ==0)){
						result = des.createRestriction(command);
						if (checkResult(result))
							createRestrictionLocal(restriction);
						setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
					}
					else{
						result = des.dropRestriction(oldCommand);
						if (checkResult(result)){
							result = des.createRestriction(command);
							if (checkResult(result))
								updateRestrictionLocal(restriction);
							setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
							}
					}
				
				} else	{	setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
							JOptionPane.showMessageDialog(null,AcideLanguageManager
									.getInstance().getLabels().getString("s2304"));
						}
			}	
			else{
					result = des.dropRestriction(oldCommand);
					if (checkResult(result))
						dropRestrictionLocal();
					setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
				}
			
			_oldRestriction = restriction;
		}
			
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
	 * Updates the parameters of the local structures that manage candidate keys after a deletion
	 */
	private void dropRestrictionLocal() {
		
		if (_totalTables > 0){
			
			// Removes the CK from the list
			_ckList.remove(_currentTable);
			
			// Remove its corresponding table from the hash
			_tablesCK.remove(_oldRestriction);
	
			// There are still CKs
			if (!_ckList.isEmpty()){
				_totalTables--;
				
				if(_currentTable>0)
					_previousButton.doClick();
				else{	
						_currentTable = -1;
						_nextButton.doClick();
					}
			} 
			else{// Just removed the only one existing CK so create a by default table and show it
				_nextButton.doClick();
				 _totalTables = 0;
				 _currentTable = 0;
				 _numberTable.setText(String.valueOf(_currentTable+1)+ ' ' +AcideLanguageManager
							.getInstance().getLabels().getString("s2303")+ ' ' +String.valueOf(_totalTables+1));
			}
			
			_oldRestriction = "";
		
		}
		
	}
	
	/**
	 * Updates the parameters of the local structures that manage candidate keys after an insertion
	 * @param restriction String
	 */
	public void createRestrictionLocal(String restriction) {
		
		_ckList.add(restriction);
		
		initializeAttributes();
		
		setCandidateKeys(restriction);
		
    	((MyTableModel) _table.getModel()).fireTableDataChanged();
    	
    	_tablesCK.put(restriction, _table); 
    	
		_totalTables++;
		
		//If the restriction is being created in a new table
		if (_newTable){ 
			_currentTable = _ckList.size()-1;
			_newTable = false;
		}
		
	}
	
	/**
	 * Updates the parameters of the local structures that manage candidate keys after a modification on the current CK 
	 * @param restriction String
	 */
	public void updateRestrictionLocal(String restriction){
		
		_ckList.set(_currentTable, restriction);
		
		initializeAttributes();
		
		setCandidateKeys(restriction);
		
    	((MyTableModel) _table.getModel()).fireTableDataChanged();
    	
    	_tablesCK.remove(_oldRestriction);
    	_tablesCK.put(restriction, _table);
    	
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
	 * Updates the panel by rebuilding the structures that deal with candidate keys values
	 */
    public void updatePanel() {
   	
    	_ckList.clear();
    	
    	_tablesCK.clear();
    	
    	buildTables();
    	
    	buildFirstTable();
    	
    	remove(_tableScrollPane);
	
		_tableScrollPane = new JScrollPane(_table);
		
		add(_tableScrollPane);
		
		validate();
    
  
	}
    
    /**
     * Returns this panel
     * @return AcideCandidateKeyPanel panel
     */
    public AcideCandidateKeyPanel getAcideCandidateKeyPanel(){
    	return this;
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
				,AcideLanguageManager.getInstance().getLabels().getString("s2033")};
		
		private Object[][] data;
		
		
		public void build(){
			
			data = new Object[_names.size()][headers.length];
			
			for(int i=0; i<_names.size(); i++){
				data[i][0] = _names.get(i);
				data[i][1] = _types.get(i);
				data[i][2] = _candidateKeys.get(_names.get(i));
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
