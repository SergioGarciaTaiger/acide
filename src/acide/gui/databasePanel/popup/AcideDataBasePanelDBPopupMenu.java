/*
 * ACIDE - A Configurable IDE
 * Official web site: http://acide.sourceforge.net
 * 
 * Copyright (C) 2007-2013  
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

import acide.gui.databasePanel.popup.listeners.AcideCloseDatabaseConnectionMenuItemAction;
import acide.gui.databasePanel.popup.listeners.AcideExecuteQueryMenuItemAction;
import acide.gui.databasePanel.popup.listeners.AcideRefreshDataBasesMenuItemAction;
import acide.gui.databasePanel.popup.listeners.AcideSetDefaultMenuItemAction;
import acide.language.AcideLanguageManager;

/**
 * ACIDE - A Configurable IDE data base panel DB popup menu.
 * 
 * @version 0.11
 * @see JPopupMenu
 */
public class AcideDataBasePanelDBPopupMenu extends JPopupMenu {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * ACIDE - A Configurable IDE Databases node popup menu new database menu
	 * item image icon.
	 */
	private static final ImageIcon EXECUTE_QUERY = new ImageIcon(
			"./resources/icons/database/executeQuery.png");
	/**
	 * ACIDE - A Configurable IDE  Databases node popup menu refresh databases
	 * menu item image icon.
	 */
	private static final ImageIcon REFRESH_DATABASE = new ImageIcon(
			"./resources/icons/database/refreshDatabase.png");
	
	/**
	 * ACIDE - A Configurable IDE  Databases node popup menu close databases
	 * menu item image icon.
	 */
	private static final ImageIcon CLOSE_DATABASE = new ImageIcon(
			"./resources/icons/database/closeDatabase.png");
	
	
	/**
	 * ACIDE - A Configurable IDE database panel popup menu set as default menu item.
	 */
	private JMenuItem _setAsDefault;
	

	/**
	 * ACIDE - A Configurable IDE explorer panel popup menu add file menu item.
	 */
	private JMenuItem _refreshDataBase;
	
	/**
	 * 
	 * ACIDE - A Configurable IDE explorer panel popup menu remove file menu
	 * item.
	 */
	private JMenuItem _executeQuery;
	
	/**
	 * ACIDE - A Configurable IDE explorer panel popup menu new project file
	 * menu item.
	 */
	private JMenuItem _closeDataBase;
	
	
	public AcideDataBasePanelDBPopupMenu() {
		super();
		buildComponents();
		addComponents();
		setListeners();
	}

	/**
	 * Adds the components to the ACIDE - A Configurable IDE explorer panel
	 * popup menu.
	 */
	private void addComponents() {
		
		add(_setAsDefault);

		add(_closeDataBase);

		add(_refreshDataBase);

		add(_executeQuery);

	}

	/**
	 * Builds the ACIDE - A Configurable IDE explorer panel popup menu
	 * components.
	 */
	private void buildComponents() {
	
		_setAsDefault = new JMenuItem(AcideLanguageManager.getInstance().getLabels().getString("s2042"));
		
		_closeDataBase = new JMenuItem(AcideLanguageManager
				.getInstance().getLabels().getString("s2045"),CLOSE_DATABASE);

		_refreshDataBase = new JMenuItem(AcideLanguageManager.getInstance()
				.getLabels().getString("s2044"),REFRESH_DATABASE);

		_executeQuery = new JMenuItem(AcideLanguageManager.getInstance()
				.getLabels().getString("s2046"),EXECUTE_QUERY);
	}

	/**
	 * Sets the ACIDE - A Configurable IDE explorer panel popup menu menu item
	 * listeners.
	 */
	private void setListeners() {
		
		_setAsDefault.addActionListener(new AcideSetDefaultMenuItemAction());
	
		_closeDataBase.addActionListener(new AcideCloseDatabaseConnectionMenuItemAction());

		_refreshDataBase.addActionListener(new AcideRefreshDataBasesMenuItemAction());

		_executeQuery.addActionListener(new AcideExecuteQueryMenuItemAction());
	}
	public JMenuItem get_setAsDefault() {
		return _setAsDefault;
	}
	public void set_setAsDefault(JMenuItem setAsDefault) {
		_setAsDefault = setAsDefault;
	}
	public JMenuItem get_refreshDataBase() {
		return _refreshDataBase;
	}

	public void set_refreshDataBase(JMenuItem refreshDataBase) {
		_refreshDataBase = refreshDataBase;
	}

	public JMenuItem get_executeQuery() {
		return _executeQuery;
	}

	public void set_executeQuery(JMenuItem executeQuery) {
		_executeQuery = executeQuery;
	}

	public JMenuItem get_closeDataBase() {
		return _closeDataBase;
	}

	public void set_closeDataBase(JMenuItem closeDataBase) {
		_closeDataBase = closeDataBase;
	}
}
