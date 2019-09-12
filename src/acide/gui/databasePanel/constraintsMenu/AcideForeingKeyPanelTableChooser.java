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
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;

import acide.gui.mainWindow.AcideMainWindow;
import acide.language.AcideLanguageManager;
import acide.process.console.AcideDatabaseManager;

/**
 * ACIDE - A Configurable IDE constraints window table chooser for FK panel.
 * 
 * @version 0.16
 * @see JFrame
 */
public class AcideForeingKeyPanelTableChooser extends JFrame{

	private static final long serialVersionUID = 1L;
	
	private static final String WINDOW_ICON = "./resources/images/icon.png";
	
	private JComboBox _tableCombo;
	
	private JPanel _buttonPanel;
	
	private JButton _okButton;
	
	private JButton _closeButton;
	
	private String _database;
	
	private String _tableNameS;
	
	private String _rowS;
	
	private String _tableNameT;
	
	private Object _panel;
	
	/**
	 * Creates a panel that allows choosing a referenced table for a given foreign key
	 * @param database
	 * @param tableNameS - name of the table to which the current foreign key belong
	 * @param rowS - the current foreign key 
	 * @param panel - the panel that made the call of this method
	 * 
	 */
	public AcideForeingKeyPanelTableChooser(String database, String tableNameS, String rowS, AcideForeignKeyPanel panel){
		
		_database = database;
		
		_tableNameS = tableNameS;
		
		_rowS = rowS;
		
		_tableNameT = "";
		
		_panel = panel;
		
		buildWindow();
		
	}

	/**
	 * Creates a panel that allows choosing a referenced table for a given foreign key
	 * @param database
	 * @param tableNameS - name of the table to which the current foreign key belong
	 * @param panel - the panel that made the call of this method
	 * 
	 */
	public AcideForeingKeyPanelTableChooser(String database, String tableNameS, AcideForeignKeyTargetTable panel){
		
		_database = database;
		
		_tableNameS = tableNameS;
		
		_tableNameT = "";
		
		_panel = panel;
		
		buildWindow();
		
	}

	private void buildWindow(){
		
		// Builds the window components
		buildComponents();
		
		// Builds the combobox component
		buildComboBox();
		
		// Sets the listeners of the window components
		setListeners();

		// Adds the components to the window
		addComponents();
		
		// Sets the look and feel for the window
		setLookAndFeel();
	}
	
	@SuppressWarnings("rawtypes")
	private void buildComponents() {

		_tableCombo = new JComboBox();
		
		_buttonPanel = new JPanel();
		
		_okButton = new JButton(AcideLanguageManager.getInstance().getLabels().getString("s154"));
		
		_closeButton = new JButton(AcideLanguageManager.getInstance().getLabels().getString("s162"));
	}

	private void setLookAndFeel() {

		setTitle(AcideLanguageManager.getInstance().getLabels().getString("s2311"));
		
		setIconImage(new ImageIcon(WINDOW_ICON).getImage());
		
		Dimension d = new Dimension(300, 100);
		
		setPreferredSize(d);
		
		setResizable(true);

		pack();

		setLocationRelativeTo(null);

		setVisible(true);

		AcideMainWindow.getInstance().setEnabled(false);
	}


	private void addComponents() {
		
		_buttonPanel.add(_okButton);
		
		_buttonPanel.add(_closeButton);
		
		add(_tableCombo, BorderLayout.CENTER);
		
		add(_buttonPanel, BorderLayout.SOUTH);
		
	}


	private void setListeners() {
	
		_okButton.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				_tableNameT = _tableCombo.getSelectedItem().toString();
				
				if(_panel instanceof AcideForeignKeyPanel){
					if (!_tableNameT.equals("No Tables") || !_tableNameT.isEmpty()){
                		new AcideForeignKeyTargetTable(_database, _tableNameS, _rowS, null, _tableNameT, true, (AcideForeignKeyPanel) _panel);
					}
				}
					
				else if(_panel instanceof AcideForeignKeyTargetTable)
                		((AcideForeignKeyTargetTable)_panel).buildNewTable(_tableNameT);
				
				dispose();
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
	 * Builds a combobox and fills it with the names of all the tables existing in the current database
	 * The source table is not included
	 */

	private void buildComboBox() {
		
		AcideDatabaseManager des = AcideDatabaseManager.getInstance();
		
		LinkedList<String> tablesList = des.getTables(_database);
	
		for(int i=0; i< tablesList.size();i++){
			String table = tablesList.get(i);
			table = table.substring(0, table.indexOf("("));
			if(!table.equals(_tableNameS))
				_tableCombo.addItem(table);
		}
		
		// The data base only contains the source table so it is not possible to reference another table
		if (_tableCombo.getItemCount() == 0)
				_tableCombo.addItem(AcideLanguageManager.getInstance().getLabels().getString("s2312"));
	}
	

}
