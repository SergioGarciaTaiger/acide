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
 * ACIDE - A Configurable IDE constraints window FD panel.
 * 
 * @version 0.16
 * @see JPanel
 */
public class AcideFunctionalDependencyPanel extends JPanel{

	private static final long serialVersionUID = 1L;
	
	private int _totalTables;
	
	private int _currentTable;
	
	private boolean _newTable;
	
	private JTable _tableRight;
	
	private JTable _tableLeft;

	private String _tableName;
	
	private String _database;
	
	private JScrollPane _tableScrollPaneR, _tableScrollPaneL;
	
	private LinkedList<String> _names;
	
	private LinkedList<String> _types;
	
	private HashMap<String, Boolean> _fdKeysRight, _fdKeysLeft;
	
	private LinkedList<String> _fdList;
	
	private HashMap<String, JTable> _tablesFDRight, _tablesFDLeft;
	
	private JPanel _navigationPanel;
	
	private JLabel _numberTable;
	
	private JButton _previousButton;
	
	private JButton _nextButton;
	
	private JPanel _buttonPanel;
	
	private JButton _applyButton;
	
	private JButton _closeButton;
	
	private JButton _dropFD;

	private String _oldRestriction;
		
		
	public AcideFunctionalDependencyPanel(String database, String name){
			
		_currentTable = 0;
		
		_totalTables = 0;
			
		_newTable = false;
		
		_tableName = name;
		
		_database = database;
		
		_oldRestriction = "";
		
		_names = new LinkedList<String>();
		
		_types = new LinkedList<String>();
		
		_fdKeysRight = new HashMap<String, Boolean>();
		
		_fdKeysLeft = new HashMap<String, Boolean>();
		
		_tablesFDRight = new HashMap<String,JTable>();
		
		_tablesFDLeft = new HashMap<String,JTable>();
		
		_fdList = new LinkedList<String>();
		
		// Initializes the values of some components
		initializeValues();
		
		// Builds the components of the panel
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
		
		for(int i=0;i<_names.size();i++){
			_fdKeysRight.put(_names.get(i), Boolean.FALSE);
			_fdKeysLeft.put(_names.get(i), Boolean.FALSE);
		}
		
	}

	/**
	 * Assigns a TRUE value to each attribute that is a functional dependency
	 * @param fd
	 */
	private void setFDKeys(String fd) {
		
		String[] names = fd.split(" -> ");
		
		// Gets and stores in hash the attributes located on the left side of the current dependency 
		String nameL = names[0];
		nameL = nameL.substring(nameL.indexOf("[")+1, nameL.indexOf("]"));
		
		if(nameL.contains(",")){
			String[] namesL = nameL.split(",");
			for(int i=0;i<namesL.length;i++){
				String name = namesL[i];
				_fdKeysLeft.put(name, Boolean.TRUE);

			}
		} else{
			_fdKeysLeft.put(nameL, Boolean.TRUE);
		}
		
		// Gets and stores in hash the attributes located on the right side of the current dependency 
		String nameR = names[1];
		nameR = nameR.substring(nameR.indexOf("[")+1, nameR.indexOf("]"));
		
		if(nameR.contains(",")){
			String[] namesR = nameR.split(",");
			for(int i=0;i<namesR.length;i++){
				String name = namesR[i];
				_fdKeysRight.put(name, Boolean.TRUE);
			}
		} else {
			_fdKeysRight.put(nameR, Boolean.TRUE);
		}
	
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
		
		_dropFD = new JButton(AcideLanguageManager
				.getInstance().getLabels().getString("s2050")+" FD");
		
		// Creates the FD tables
		buildTables();

		// Creates the first table to be shown
		buildFirstTable();
		
        _tableScrollPaneL = new JScrollPane(_tableLeft);
        
        _tableScrollPaneR = new JScrollPane(_tableRight);
		
	}
	
	/**
	 * Builds generic tables with values by default. 
	 */
	public void buildTableLR(){
		
			initializeAttributes();
			
			// Builds the table located on the left side of the panel
			MyTableModel modelLeft = new MyTableModel();
			modelLeft.buildL();
			
			_tableLeft = new JTable(modelLeft);
	
			_tableLeft.setPreferredScrollableViewportSize(new Dimension(300, 70));
			_tableLeft.setFillsViewportHeight(true);
	        
	        //Fiddle with the names column's cell editors/renderers.
	        setUpCheckBoxColumn(_tableLeft, _tableLeft.getColumnModel().getColumn(2));
	        
	        _tableLeft.getColumnModel().getColumn(2).setMaxWidth(30);
	        
	        _tableLeft.getColumnModel().getColumn(2).setResizable(false);
	        
	        TableCellRenderer rendererFromHeaderLeft = _tableLeft.getTableHeader().getDefaultRenderer();
	        JLabel headerLabelLeft = (JLabel) rendererFromHeaderLeft;
	        headerLabelLeft.setHorizontalAlignment(JLabel.CENTER);
	        
			// Builds the table located on the right side of the panel
			MyTableModel modelRight = new MyTableModel();
			modelRight.buildR();
			
			_tableRight = new JTable(modelRight);
	
			_tableRight.setPreferredScrollableViewportSize(new Dimension(300, 70));
			_tableRight.setFillsViewportHeight(true);
			
			_tableRight.getColumnModel().getColumn(2).setMaxWidth(30);
	        
			_tableRight.getColumnModel().getColumn(2).setResizable(false);
	        
	        //Fiddle with the names column's cell editors/renderers.
	        setUpCheckBoxColumn(_tableRight, _tableRight.getColumnModel().getColumn(2));
	        
	        TableCellRenderer rendererFromHeaderRight = _tableRight.getTableHeader().getDefaultRenderer();
	        JLabel headerLabelRight = (JLabel) rendererFromHeaderRight;
	        headerLabelRight.setHorizontalAlignment(JLabel.CENTER);
	}
	
	/**
	 * Builds the first tables that will be shown in the panel
	 */
	public void buildFirstTable() {
		
		//Builds empty tables if there are no FDs 
		if (_totalTables == 0){
			buildTableLR();
			_numberTable.setText(String.valueOf(_currentTable+1)+ ' ' +AcideLanguageManager
					.getInstance().getLabels().getString("s2303")+ ' ' +String.valueOf(_totalTables+1));
			_oldRestriction = "";
		}
		else{
			_tableLeft = _tablesFDLeft.get(_fdList.get(0));
			_tableRight = _tablesFDRight.get(_fdList.get(0));
			_oldRestriction = _fdList.get(0);
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
		
		add(_tableScrollPaneL, BorderLayout.WEST);
		
		add(_tableScrollPaneR, BorderLayout.EAST);

		_buttonPanel.add(_dropFD);
		
		_buttonPanel.add(_applyButton);
		
		_buttonPanel.add(_closeButton);

		add(_buttonPanel, BorderLayout.SOUTH);
		
	}

	/**
	 * Build tables for each existing functional dependency and stores them in a hash
	 */
	private void buildTables() {
		
		AcideDatabaseManager des = AcideDatabaseManager.getInstance();
		
		LinkedList<String> list = des.getFuncionDep(_database, _tableName);
		
		_totalTables = list.size();
		
		for(int i=0;i<_totalTables;i++){
			
			String fd = list.get(i);
			fd = fd.trim();

			// Adds each functional dependency to a list
			_fdList.add(fd);
			
			// Initializes the attributes with default values
			initializeAttributes();
			
			// Sets the functional dependencies
			setFDKeys(fd);
			
			// Builds a table for the current attributes located on the left side of the current FD
			MyTableModel modelLeft = new MyTableModel();
			modelLeft.buildL();
			
			JTable tableLeft = new JTable(modelLeft);
	
			tableLeft.setPreferredScrollableViewportSize(new Dimension(300, 70));
	        
	        //Fiddle with the names column's cell editors/renderers.
	        setUpCheckBoxColumn(tableLeft, tableLeft.getColumnModel().getColumn(2));
	        
	        tableLeft.getColumnModel().getColumn(2).setMaxWidth(30);
	        
	        tableLeft.getColumnModel().getColumn(2).setResizable(false);
	        
	        TableCellRenderer rendererFromHeaderLeft = tableLeft.getTableHeader().getDefaultRenderer();
	        JLabel headerLabelLeft = (JLabel) rendererFromHeaderLeft;
	        headerLabelLeft.setHorizontalAlignment(JLabel.CENTER);
	        
	        
	        // Builds a table for the current attributes located on the right side of the current FD
			MyTableModel modelRight = new MyTableModel();
			modelRight.buildR();
			
			JTable tableRight = new JTable(modelRight);
	
			tableRight.setPreferredScrollableViewportSize(new Dimension(300, 70));
			
			tableRight.getColumnModel().getColumn(2).setMaxWidth(30);
	        
			tableRight.getColumnModel().getColumn(2).setResizable(false);
	        
	        //Fiddle with the names column's cell editors/renderers.
	        setUpCheckBoxColumn(tableRight, tableRight.getColumnModel().getColumn(2));
	        
	        TableCellRenderer rendererFromHeaderRight = tableRight.getTableHeader().getDefaultRenderer();
	        JLabel headerLabelRight = (JLabel) rendererFromHeaderRight;
	        headerLabelRight.setHorizontalAlignment(JLabel.CENTER);
	        
	        // Stores the tables in a hash
	        _tablesFDLeft.put(fd, tableLeft);
	        _tablesFDRight.put(fd, tableRight);
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
				
				// Shows a table for each functional dependency until the first one in the list is reached
				if ((_totalTables> 0)&&(_currentTable>0)){
					
					getAcideFunctionalDependencyPanel().remove(_tableScrollPaneL);
					getAcideFunctionalDependencyPanel().remove(_tableScrollPaneR);
					
					_tableLeft = _tablesFDLeft.get(_fdList.get(_currentTable-1));
					_tableRight = _tablesFDRight.get(_fdList.get(_currentTable-1));
					
					_tableScrollPaneL = new JScrollPane(_tableLeft);
					_tableScrollPaneR = new JScrollPane(_tableRight);
					
					getAcideFunctionalDependencyPanel().add(_tableScrollPaneL, BorderLayout.WEST);
					getAcideFunctionalDependencyPanel().add(_tableScrollPaneR,BorderLayout.EAST);
					
					getAcideFunctionalDependencyPanel().validate();
					
					_currentTable--;
					
					_oldRestriction = _fdList.get(_currentTable);
					
					_numberTable.setText(String.valueOf(_currentTable+1)+ ' ' +AcideLanguageManager
							.getInstance().getLabels().getString("s2303")+ ' ' +String.valueOf(_totalTables));
				}
			}
			
		});
		
		_nextButton.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {

				// Shows a table for each functional dependency until the last one in the list is reached
				if ((_totalTables> 0) && (_currentTable<_totalTables-1)){
					
					getAcideFunctionalDependencyPanel().remove(_tableScrollPaneL);
					getAcideFunctionalDependencyPanel().remove(_tableScrollPaneR);
					
					_tableLeft = _tablesFDLeft.get(_fdList.get(_currentTable+1));
					_tableRight = _tablesFDRight.get(_fdList.get(_currentTable+1));
					
					_tableScrollPaneL = new JScrollPane(_tableLeft);
					_tableScrollPaneR = new JScrollPane(_tableRight);
					
					getAcideFunctionalDependencyPanel().add(_tableScrollPaneL, BorderLayout.WEST);
					getAcideFunctionalDependencyPanel().add(_tableScrollPaneR,BorderLayout.EAST);
					getAcideFunctionalDependencyPanel().validate();
					
					_currentTable++;
					
					_oldRestriction = _fdList.get(_currentTable);
					
					_numberTable.setText(String.valueOf(_currentTable+1)+ ' ' +AcideLanguageManager
							.getInstance().getLabels().getString("s2303")+ ' ' +String.valueOf(_totalTables));
				}
				// No more FDs to be shown so create and show a by default table to allow creating a new FD
				else if (_currentTable == _totalTables-1){
					
					buildTableLR();
					
					getAcideFunctionalDependencyPanel().remove(_tableScrollPaneL);
					getAcideFunctionalDependencyPanel().remove(_tableScrollPaneR);
					
					_tableScrollPaneL = new JScrollPane(_tableLeft);
					_tableScrollPaneR = new JScrollPane(_tableRight);
					
					getAcideFunctionalDependencyPanel().add(_tableScrollPaneL, BorderLayout.WEST);
					getAcideFunctionalDependencyPanel().add(_tableScrollPaneR,BorderLayout.EAST);
					getAcideFunctionalDependencyPanel().validate();
					
					_newTable = true;
					
					_currentTable = _totalTables;
					
					_oldRestriction = "";
					
					_numberTable.setText(String.valueOf(_currentTable+1)+ ' ' +AcideLanguageManager
							.getInstance().getLabels().getString("s2303")+ ' ' +String.valueOf(_totalTables+1));
			
				}
			}
			
		});
		
		
		_dropFD.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {

				AcideDatabaseManager des = AcideDatabaseManager.getInstance();
				
				if (_oldRestriction.contains("->")){
					String oldFD = _oldRestriction;
					String[] old = oldFD.split(" -> ");
					
					String oldL = old[0];
					String oldR = old[1];
					
					String oldCommand = ":-fd("+ _tableName + "," + oldL + "," + oldR + ")";
					
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
		String oldCommand ="";

		if (!restriction.equals(_oldRestriction)){
			
			if(!_oldRestriction.isEmpty()){
				
				String oldFD = _oldRestriction;
				String[] old = oldFD.split(" -> ");
				
				String oldL = old[0];
				String oldR = old[1];
				
				oldCommand = ":-fd("+ _tableName + "," + oldL + "," + oldR + ")";
			}
			
			String result = "";
			
			if(!restriction.isEmpty()){
				
				String[] res = restriction.split(" -> ");
				
				String resL = res[0];
				String resR = res[1];
				
				String command = ":-fd("+ _tableName + "," + resL + "," + resR +")";
				
				if (!_fdList.contains(restriction)){
					
					setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
					
					//if it's the by default table or a new one has been added
					if ((_newTable || _totalTables ==0)){
						result = des.createRestriction(command);
						if (checkResult(result)){
							createRestrictionLocal(restriction);
						}
					}
					else{
						result = des.dropRestriction(oldCommand);
						if (checkResult(result)){
							result = des.createRestriction(command);
							if (checkResult(result)){
								updateRestrictionLocal(restriction);
							}
						}
					}
				
				} else	{
							JOptionPane.showMessageDialog(null,"This functional dependency already exists");
						}
			}	
			else{
					result = des.dropRestriction(oldCommand);
					if (checkResult(result)){
						dropRestrictionLocal();
					}
				}
			
			_oldRestriction = restriction;
			
			setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
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
	 * Updates the parameters of the local structures that manage functional dependencies after a deletion
	 */
	private void dropRestrictionLocal() {
		
		if (_totalTables > 0){
			
			// Removes the FD from the list
			_fdList.remove(_currentTable);
			
			// Remove its corresponding tables from the hashes
			_tablesFDLeft.remove(_oldRestriction);
			_tablesFDRight.remove(_oldRestriction);
	
			// There are still FDs
			if (!_fdList.isEmpty()){
				_totalTables--;
				
				if(_currentTable>0)
					_previousButton.doClick();
				else{	
						_currentTable = -1;
						_nextButton.doClick();
					}
			} 
			else{// Just removed the only one existing FD so create a by default table and show it
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
	 * Updates the parameters of the local structures that manage functional dependencies after an insertion
	 * @param restriction String
	 */
	public void createRestrictionLocal(String restriction) {
		
		_fdList.add(restriction);
		
		initializeAttributes();
		
		setFDKeys(restriction);
		
    	((MyTableModel) _tableLeft.getModel()).fireTableDataChanged();
    	((MyTableModel) _tableRight.getModel()).fireTableDataChanged();
    	
    	_tablesFDLeft.put(restriction, _tableLeft); 
    	_tablesFDRight.put(restriction, _tableRight);
    	
		_totalTables++;
		
		//If the restriction is being created in a new table
		if (_newTable){ 
			
			_currentTable = _fdList.size()-1;
			_newTable = false;
		}
		
	}
	
	/**
	 * Updates the parameters of the local structures that manage functional dependencies after a modification
	 * @param restriction String
	 */
	public void updateRestrictionLocal(String restriction){

		_fdList.set(_currentTable, restriction);
		
		initializeAttributes();
		
		setFDKeys(restriction);
		
    	((MyTableModel) _tableLeft.getModel()).fireTableDataChanged();
    	((MyTableModel) _tableRight.getModel()).fireTableDataChanged();
    	
    	_tablesFDLeft.remove(_oldRestriction);
    	_tablesFDRight.remove(_oldRestriction);
    	
    	_tablesFDLeft.put(restriction, _tableLeft);
    	_tablesFDRight.put(restriction, _tableRight);
    	
	}

	/**
	 * Builds a restriction by gathering the attributes of the tables that have a check of validity
	 * @return String restriction
	 */
	public String getRestriction(){
		
		String restrictionL = "";
		String restrictionR = "";
		String restriction = "";

		for(int i=0; i<_tableLeft.getModel().getRowCount();i++){
			boolean marked = (Boolean) _tableLeft.getModel().getValueAt(i, 2);
			if(marked){
				String name = (String) _tableLeft.getModel().getValueAt(i, 0);
				restrictionL = restrictionL + name + ",";
			}
		}
		
		if (restrictionL.endsWith(","))
			restrictionL = "["+ restrictionL.substring(0, restrictionL.lastIndexOf(","))+"]";
		
		for(int j=0; j<_tableRight.getModel().getRowCount();j++){
			boolean marked = (Boolean) _tableRight.getModel().getValueAt(j, 2);
			if(marked){
				String name = (String) _tableRight.getModel().getValueAt(j, 0);
				restrictionR = restrictionR + name + ",";
			}
		}
		
		if (restrictionR.endsWith(","))
			restrictionR = "["+ restrictionR.substring(0, restrictionR.lastIndexOf(","))+"]";
		
		restriction = restrictionL + " -> " + restrictionR;
		
		return restriction;
	}
	
	/**
	 * Updates the panel by rebuilding the structures that deal with functional dependencies values
	 */
    public void updatePanel() {
   	
    	_fdList.clear();
    	
    	_tablesFDLeft.clear();
    	_tablesFDRight.clear();
    	
    	buildTables();
    	
    	buildFirstTable();
    	
    	remove(_tableScrollPaneL);
    	remove(_tableScrollPaneR);
	
		_tableScrollPaneL = new JScrollPane(_tableLeft);
		_tableScrollPaneR = new JScrollPane(_tableRight);
		
		add(_tableScrollPaneL, BorderLayout.WEST);
		add(_tableScrollPaneR, BorderLayout.EAST);
		
		validate();
	}
    
    /**
     * Returns this panel
     * @return AcideFunctionalDependencyPanel panel
     */
    public AcideFunctionalDependencyPanel getAcideFunctionalDependencyPanel(){
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
				,AcideLanguageManager.getInstance().getLabels().getString("s2034")};
		
		private Object[][] data;
		
		
		public void buildL(){
			
			data = new Object[_names.size()][headers.length];
			
			for(int i=0; i<_names.size(); i++){
				data[i][0] = _names.get(i);
				data[i][1] = _types.get(i);
				data[i][2] = _fdKeysLeft.get(_names.get(i));
			}
			
		}
		
		public void buildR(){
			
			data = new Object[_names.size()][headers.length];
			
			for(int i=0; i<_names.size(); i++){
				data[i][0] = _names.get(i);
				data[i][1] = _types.get(i);
				data[i][2] = _fdKeysRight.get(_names.get(i));
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
        }
	}

}
