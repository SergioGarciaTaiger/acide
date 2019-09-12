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

import javax.swing.ImageIcon;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

import acide.gui.databasePanel.popup.listeners.AcideCreateTableDatalogMenuItemAction;
import acide.gui.databasePanel.popup.listeners.AcideCreateTableDesignViewMenuItemAction;
import acide.gui.databasePanel.popup.listeners.AcideCreateTableSQLMenuItemAction;
import acide.gui.databasePanel.popup.listeners.AcidePasteTableMenuItemAction;
import acide.gui.mainWindow.AcideMainWindow;
import acide.gui.menuBar.configurationMenu.databasePanelMenu.showDetails.AcideShowDetailsMenu;
import acide.language.AcideLanguageManager;

/**
 * ACIDE - A Configurable IDE data base panel tables popup menu.
 * 
 * @version 0.15
 * @see JPopupMenu
 */
public class AcideDataBasePanelTablesPopupMenu extends JPopupMenu{	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	private static AcideDataBasePanelTablesPopupMenu _instance;

	/**
	 * ACIDE - A Configurable IDE tables node popup menu create table in design view menu
	 * item.
	 */
	private JMenuItem _createTableInDesignView;
	/**
	 * ACIDE - A Configurable IDE tables node popup menu create table with sql menu
	 * item.
	 */
	private JMenuItem _CreateTableWithSQL;
	/**
	 * ACIDE - A Configurable IDE tables node popup menu create table with datalog menu
	 * item.
	 */
	private JMenuItem _CreateTableWithDatalog;
	
	/**
	 * ACIDE - A Configurable IDE tables node popup menu show details.
	 * @Semi
	 */
	private AcideShowDetailsMenu _showDetails;
	
	/**
	 * ACIDE - A Configurable IDE  Databases node popup menu close databases
	 * menu item image icon.
	 */
	private static final ImageIcon PASTE = new ImageIcon(
			"./resources/icons/database/paste.png");
	
	/**
	 * ACIDE - A Configurable IDE explorer panel popup menu unset compilable
	 * file menu item.
	 */
	private JMenuItem _pasteTable;
	
	public static AcideDataBasePanelTablesPopupMenu getInstance() {
		if (_instance == null)
			_instance = new AcideDataBasePanelTablesPopupMenu();
		return _instance;
	}

	public AcideDataBasePanelTablesPopupMenu() {
		super();
		buildComponents();
		addComponents();
		setListeners();
	}
	/**
	 * Adds the components to the ACIDE - A Configurable IDE tables node
	 * popup menu.
	 */
	private void addComponents() {

		add(_CreateTableWithDatalog);

		add(_createTableInDesignView);

		add(_CreateTableWithSQL);
		
		add(new Separator());
		
		add(_pasteTable);
		
		add(new Separator());
		
		add(_showDetails);
		
	}
	/**
	 * Builds the ACIDE - A Configurable IDE explorer panel popup menu
	 * components.
	 */
	private void buildComponents() {

		_CreateTableWithDatalog = new JMenuItem(AcideLanguageManager.getInstance()
				.getLabels().getString("s2049"));

		_createTableInDesignView = new JMenuItem(AcideLanguageManager.getInstance()
				.getLabels().getString("s2047"));

		_CreateTableWithSQL = new JMenuItem(AcideLanguageManager.getInstance()
				.getLabels().getString("s2048"));
		
		_pasteTable = new JMenuItem(AcideLanguageManager
				.getInstance().getLabels().getString("s2053"),PASTE);
		
		AcideMainWindow.getInstance().getMenu().getConfigurationMenu().getDatabasePanelMenu()
						.getShowDetailsMenu();

		_showDetails = new AcideShowDetailsMenu();
		
		_showDetails.setText(AcideLanguageManager
				.getInstance().getLabels().getString("s2271"));
		

		
	}

	/**
	 * Sets the ACIDE - A Configurable IDE explorer panel popup menu menu item
	 * listeners.
	 */
	private void setListeners() {
		
		// Sets the delete file menu item action listener
		_CreateTableWithDatalog
				.addActionListener(new AcideCreateTableDatalogMenuItemAction());
		// Sets the add folder menu item action listener
		_createTableInDesignView
				.addActionListener(new AcideCreateTableDesignViewMenuItemAction());

		// Sets the remove folder menu item action listener
		_CreateTableWithSQL
				.addActionListener(new AcideCreateTableSQLMenuItemAction());
		
		_pasteTable.addActionListener(new AcidePasteTableMenuItemAction());
		
		_showDetails.setListeners();
	}
	
	public AcideShowDetailsMenu getShowDetails() {
		return _showDetails;
	}

	public void setShowDetails(AcideShowDetailsMenu _showDetails) {
		this._showDetails = _showDetails;
	}

	public JMenuItem get_createTableInDesignView() {
		return _createTableInDesignView;
	}
	public void set_createTableInDesignView(JMenuItem createTableInDesignView) {
		_createTableInDesignView = createTableInDesignView;
	}
	public JMenuItem get_CreateTableWithSQL() {
		return _CreateTableWithSQL;
	}
	public void set_CreateTableWithSQL(JMenuItem createTableWithSQL) {
		_CreateTableWithSQL = createTableWithSQL;
	}
	public JMenuItem get_CreateTableWithDatalog() {
		return _CreateTableWithDatalog;
	}
	public void set_CreateTableWithDatalog(JMenuItem createTableWithDatalog) {
		_CreateTableWithDatalog = createTableWithDatalog;
	}
	
}
