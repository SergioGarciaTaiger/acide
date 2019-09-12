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
package acide.gui.databasePanel.popup;

import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.JSeparator;
import javax.swing.tree.TreePath;

import acide.gui.databasePanel.AcideDataBasePanel;
import acide.gui.databasePanel.Nodes.AcideDataBaseNodes;
import acide.gui.mainWindow.AcideMainWindow;

import acide.language.AcideLanguageManager;
import acide.process.console.AcideDatabaseManager;


/**
 * ACIDE - A Configurable IDE data base panel columns popup menu.
 * 
 * @version 0.16
 * @see JPopupMenu
 */
public class AcideDataBasePanelColumnsPopupMenu extends JPopupMenu {
	/**
	 * 	 * ACIDE - A Configurable IDE explorer panel class serial version UID.
	 */
	private static final long serialVersionUID = 1L;
	
	private JMenuItem _addPrimaryKey;
	
	private JMenuItem _addCandidateKey;
	
	private JMenuItem _notNull;
	
	private JSeparator _separator;
	
	private JMenuItem _dropPrimaryKey;
	
	private JMenuItem _dropCandidateKey;
	
	private JMenuItem _dropNotNull;
	
	private String _table;
	
	private String _column;
	
	private AcideDataBaseNodes _tableNode;
	
	
	public AcideDataBasePanelColumnsPopupMenu(AcideDataBaseNodes tableNode, String column) {
		_tableNode = tableNode;
		
		String table = _tableNode.toString();
		
		if(table.contains("("))
			table = table.substring(0, table.indexOf("("));
		_table = table;
		
		if(column.contains(":"))
			column = column.substring(0,column.indexOf(":"));
		_column = column;
		
		TreePath path = AcideMainWindow.getInstance().getDataBasePanel().getTree().getSelectionPath();
		path.getParentPath().getParentPath().getParentPath().getParentPath().getLastPathComponent().toString();
		
		buildComponents();
		addComponents();
		setListeners();
	}


	private void addComponents() {
		
		add(_addPrimaryKey);
		
		add(_addCandidateKey);
		
		add(_notNull);
		
		add(_separator);
		
		add(_dropPrimaryKey);
		
		add(_dropCandidateKey);
		
		add(_dropNotNull);

	}
	

	private void buildComponents() {
		
		_addPrimaryKey  = new JMenuItem(AcideLanguageManager
				.getInstance().getLabels().getString("s2056"));
		
		_addCandidateKey  = new JMenuItem(AcideLanguageManager
				.getInstance().getLabels().getString("s2058"));
		
		_notNull = new JMenuItem(AcideLanguageManager
				.getInstance().getLabels().getString("s2294"));
		
		_separator = new JSeparator();
		
		_dropPrimaryKey = new JMenuItem(AcideLanguageManager
				.getInstance().getLabels().getString("s2296"));

		_dropCandidateKey = new JMenuItem(AcideLanguageManager
				.getInstance().getLabels().getString("s2297"));
		
		_dropNotNull = new JMenuItem(AcideLanguageManager
				.getInstance().getLabels().getString("s2298"));
	}

	
	private void setListeners() {
		
		_addPrimaryKey.addActionListener(new myActionListener(0,true));
		_addCandidateKey.addActionListener(new myActionListener(1,true));
		_notNull.addActionListener(new myActionListener(2,true));
		_dropPrimaryKey.addActionListener(new myActionListener(0,false));
		_dropCandidateKey.addActionListener(new myActionListener(1,false));
		_dropNotNull.addActionListener(new myActionListener(2,false));
		
	}
	
	public AcideDataBasePanelColumnsPopupMenu getAcideDataBasePanelColumnsPopupMenu(){
		return this;
	}
	

	private class myActionListener implements ActionListener{
		
		private int option;
		
		private boolean action;
		
		myActionListener(int option, boolean action){
			this.option = option;
			this.action = action;
		}

		@Override
		public void actionPerformed(ActionEvent e) {			
			
			AcideDatabaseManager des = AcideDatabaseManager.getInstance();
			
			String res = "";
			String restriction = getCommand(option);
			
			AcideDataBasePanel panel = AcideMainWindow.getInstance().getDataBasePanel();
			panel.getTree().setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
			
			if (action){
				res = des.createRestriction(restriction);
			}
			else{
				res = des.dropRestriction(restriction);
				}
			
			if (res.contains("$success")){
				panel.updateDataBaseTree();
			}
			else {
				JOptionPane.showMessageDialog(null,res, AcideLanguageManager.getInstance()
						.getLabels().getString("s157"), JOptionPane.OK_OPTION);
			}
			
			panel.getTree().setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		}
		
		
		private String getCommand(int option) {
			String command = "";
			
			switch (option) {
			
			case 0:
				command = ":-pk(" + _table + ",[" + _column + "])"; 
				break;
						
			case 1:
				command = ":-ck(" + _table + ",[" + _column + "])";
				break;
				
			case 2:
				command = ":-nn(" + _table + ",[" + _column + "])";
				break;
			}	
			
			return command;
		}
		
	}
}