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
 * ACIDE - A Configurable IDE constraints window PK panel.
 * 
 * @version 0.16
 * @see JPanel
 */
public class AcidePrimaryKeyPanel extends JPanel{

	private static final long serialVersionUID = 1L;
	
	private JTable _table;

	private String _tableName;
	
	private String _database;
	
	private JScrollPane _tableScrollPane;
	
	private LinkedList<String> _names;
	
	private LinkedList<String> _types;
	
	private HashMap<String, Boolean> _primaryKeys;
	
	private String _oldRestriction;
	
	private JPanel _buttonPanel;
	
	private JButton _applyButton;
	
	private JButton _closeButton;
	
	
	public AcidePrimaryKeyPanel(String database, String name){
			
		_tableName = name;
		
		_database = database;
		
		_names = new LinkedList<String>();
		
		_types = new LinkedList<String>();
		
		_primaryKeys = new HashMap<String, Boolean>();
		
		_oldRestriction = "";
		
		// Initializes the values of some components 
		initializeAttributes();
		
		// Builds the panel components
		buildComponents();
		
		// Adds the components to the window
		addComponents();

		// Sets the action listeners of the window components
		setListeners();
	}

	/**
	 * Initializes the table with the possible existing not primary keys
	 */
	private void initializeAttributes() {
		
		AcideDatabaseManager des = AcideDatabaseManager.getInstance();

		clearNames(des);
		 
		 for(int i=0;i<_names.size();i++){
			 _primaryKeys.put(_names.get(i), Boolean.FALSE);
		 }
		
		 setPrimKeys(des);
	}

	/**
	 * Adds the attributes and their types to their corresponding lists.
	 */
	private void clearNames(AcideDatabaseManager des) {
		
		LinkedList<String> names = des.getFields(_database, _tableName);
		
		for(int i=0;i<names.size();i++){
			String[] aux = names.get(i).split(":");
			_names.add(aux[0]);
			_types.add(aux[1]);
		}
		
	}
	
	/**
	 * Assigns a TRUE value to each attribute that is a primary key
	 * @param database
	 */
	private void setPrimKeys(AcideDatabaseManager des) {
		
		LinkedList<String> keys = des.getPrimKey(_database, _tableName);
		
		if (!keys.isEmpty()){
			for(int i=0;i<keys.size();i++){
				String aux = keys.get(i);
				aux = aux.substring(aux.indexOf("[")+1, aux.indexOf("]"));
				if (aux.contains(",")){
					String[] aux2 = aux.split(",");
					for(int j=0;j<aux2.length;j++)
						_primaryKeys.put(aux2[j], Boolean.TRUE);
						
				} 
				else _primaryKeys.put(aux, Boolean.TRUE);	
			}
		}
	}

	
	private void buildComponents() {
		
		_buttonPanel = new JPanel();
		
		_applyButton = new JButton(AcideLanguageManager
				.getInstance().getLabels().getString("s335"));
		
		_closeButton = new JButton(AcideLanguageManager
				.getInstance().getLabels().getString("s1048"));
		
		// Creates the table to be shown
		buildTable();
		
        _tableScrollPane = new JScrollPane(_table);

	}
	
	
	private void addComponents() {
		
		setLayout(new BorderLayout());

		add(_tableScrollPane, BorderLayout.CENTER);
		
		_buttonPanel.add(_applyButton);
		
		_buttonPanel.add(_closeButton);

		add(_buttonPanel, BorderLayout.SOUTH);
	}

	
	/**
	 * Builds a table with primary keys
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
			_oldRestriction = restriction;
			String command = ":-pk("+ _tableName + ",[" + restriction + "])";
			String result = "";
			
			setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
			
			if(!restriction.isEmpty())
				result = des.createRestriction(command);
			else
				result = dropPrimKeys();
				
			if (result.contains("$success")){
				AcideDataBasePanel panel = AcideMainWindow.getInstance().getDataBasePanel();
				panel.updateDataBaseTree();
				JOptionPane.showMessageDialog(null,AcideLanguageManager
						.getInstance().getLabels().getString("s2315"), AcideLanguageManager
						.getInstance().getLabels().getString("s2316"),JOptionPane.INFORMATION_MESSAGE);
			}
			else if (result.contains("Syntax error")){
				JOptionPane.showMessageDialog(null, AcideLanguageManager.getInstance().getLabels().getString("s2314"));
			}
			else {
				JOptionPane.showMessageDialog(null,result, AcideLanguageManager.getInstance()
						.getLabels().getString("s157"), JOptionPane.OK_OPTION);
			}
			
			
			setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		}
			
	}
	
	/**
	 * Deletes the existing primary key
	 * @return result
	 */
	private String dropPrimKeys() {
		AcideDatabaseManager des = AcideDatabaseManager.getInstance();
		LinkedList<String> primKeys = des.getPrimKey(_database, _tableName);
		String result = "";
		
		if (!primKeys.isEmpty()){
			for(int i=0;i<primKeys.size();i++){
				String command = ":-pk("+ _tableName + "," + primKeys.get(i) + ")";
				result = des.dropRestriction(command);
			}
		}
		
		return result;
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
	 * Updates the panel by rebuilding the structures that deal with not null restriction values
	 */
    public void updatePanel() {
    	
    	AcideDatabaseManager des = AcideDatabaseManager.getInstance();
    	
		 for(int i=0;i<_names.size();i++){
			 _primaryKeys.put(_names.get(i), Boolean.FALSE);
		 }
    	
		 setPrimKeys(des);
		 
		((MyTableModel) _table.getModel()).fireTableDataChanged();
	
		_oldRestriction = getRestriction();
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
				,AcideLanguageManager.getInstance().getLabels().getString("s2031")};
		
		private Object[][] data;
		
		
		public void build(){
			
			data = new Object[_names.size()][headers.length];
			
			for(int i=0; i<_names.size(); i++){
				data[i][0] = _names.get(i);
				data[i][1] = _types.get(i);
				data[i][2] = _primaryKeys.get(_names.get(i));
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
