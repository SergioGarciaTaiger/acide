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

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;

import javax.swing.ImageIcon;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

import acide.gui.databasePanel.AcideDataBasePanel;
import acide.gui.databasePanel.Nodes.NodeTable;
import acide.gui.databasePanel.dataView.AcideDatabaseDataView;
import acide.gui.databasePanel.popup.listeners.AcideConstraintDefinitionMenuItemAction;
import acide.gui.databasePanel.popup.listeners.AcideDropTableMenuItemAction;
import acide.gui.databasePanel.popup.listeners.AcideOpenDesginViewMenuItemAction;
import acide.gui.databasePanel.popup.listeners.AcideRenameTableMenuItemAction;
import acide.gui.databasePanel.utils.AcideDatabaseCopyTableOption;
import acide.gui.mainWindow.AcideMainWindow;
import acide.language.AcideLanguageManager;
import acide.process.console.AcideDatabaseManager;

/**
 * ACIDE - A Configurable IDE data base panel table popup menu.
 * 
 * @version 0.16
 * @see JPopupMenu
 */
public class AcideDataBasePanelTablePopupMenu extends JPopupMenu {
	/**
	 * 	 * ACIDE - A Configurable IDE explorer panel class serial version UID.
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * ACIDE - A Configurable IDE Databases node popup menu set as default database menu
	 * item image icon
	 */
	private static final ImageIcon DROP= new ImageIcon(
			"./resources/icons/database/dropTable.png");
	/**
	 * ACIDE - A Configurable IDE Databases node popup menu new database menu
	 * item image icon.
	 */
	private static final ImageIcon RENAME = new ImageIcon(
			"./resources/icons/database/renameTable.png");
	/**
	 * ACIDE - A Configurable IDE  Databases node popup menu refresh databases
	 * menu item image icon.
	 */
	private static final ImageIcon COPY = new ImageIcon(
			"./resources/icons/database/copy.png");
	
	
	/**
	 * ACIDE - A Configurable IDE explorer panel popup menu set main file menu
	 * item.
	 */
	private JMenuItem _dropTable;
	
	/**
	 * ACIDE - A Configurable IDE explorer panel popup menu set compilable file
	 * menu item.
	 */
	private JMenuItem _renameTable;
	
	/**
	 * ACIDE - A Configurable IDE explorer panel popup menu unset main file menu
	 * item.
	 */
	private JMenuItem _copyTable;
	
	/**
	 * ACIDE - A Configurable IDE explorer panel popup menu unset compilable
	 * file menu item.
	 */
	private JMenuItem _designViewTable;
	
	/**
	 * ACIDE - A Configurable IDE explorer panel popup menu unset compilable
	 * file menu item.
	 */
	private JMenuItem _dataViewTable;
	

	private JMenuItem _constraintsWindow;
	
	
	public AcideDataBasePanelTablePopupMenu() {
		
		buildComponents();
		addComponents();
		setListeners();
	}

	/**
	 * Adds the components to the ACIDE - A Configurable IDE explorer panel
	 * popup menu.
	 */
	private void addComponents() {


		add(_dropTable);
		
		add(_renameTable);
		
		add(new Separator());

		add(_copyTable);
		
		add(new Separator());
		
		add(_designViewTable);
		
		add(_dataViewTable);
		
		add(new Separator());
		
		add( _constraintsWindow);


	}
	
	/**
	 * Builds the ACIDE - A Configurable IDE explorer panel popup menu
	 * components.
	 */
	private void buildComponents() {
	

		
		_dropTable = new JMenuItem(AcideLanguageManager.getInstance()
				.getLabels().getString("s2050"),DROP);
	
		_renameTable = new JMenuItem(AcideLanguageManager
				.getInstance().getLabels().getString("s2051"),RENAME);

		_copyTable = new JMenuItem(AcideLanguageManager
				.getInstance().getLabels().getString("s2052"),COPY);
		
		_designViewTable  = new JMenuItem(AcideLanguageManager
				.getInstance().getLabels().getString("s2054")); 	
		
		_dataViewTable  = new JMenuItem(AcideLanguageManager
				.getInstance().getLabels().getString("s2055"));
		
		_constraintsWindow = new JMenuItem(AcideLanguageManager
				.getInstance().getLabels().getString("s2300"));
		 

	}

	/**
	 * Sets the ACIDE - A Configurable IDE explorer panel popup menu menu item
	 * listeners.
	 */
	private void setListeners() {
	
		_renameTable.addActionListener(new AcideRenameTableMenuItemAction());

		_copyTable.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				AcideDatabaseCopyTableOption.getInstance().setVisible(true);
				
			}
		});
		
		_dropTable.addActionListener(new AcideDropTableMenuItemAction());
		
		_dataViewTable.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				AcideDataBasePanel panel = AcideMainWindow.getInstance().getDataBasePanel();
				NodeTable g = (NodeTable) panel.getTree().getSelectionPath().getLastPathComponent();
				String table =g.toString();
				String db=g.getParent().getParent().toString();
	
				if (table.contains("("))
					table =   table.substring(0,table.indexOf("("));
					
				AcideDatabaseDataView panelDv  = AcideMainWindow.getInstance().getDataBasePanel().getDataView(db, table);	

				LinkedList<String> info = AcideDatabaseManager.getInstance().getSelectAll(db, table);
				if(!info.isEmpty())
					panelDv.build(info);
			}
		});
		
		_designViewTable.addActionListener(new AcideOpenDesginViewMenuItemAction());
		
		_constraintsWindow.addActionListener(new AcideConstraintDefinitionMenuItemAction());
		
	}
}
