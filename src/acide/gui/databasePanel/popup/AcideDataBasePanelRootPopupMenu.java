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

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

import acide.gui.databasePanel.popup.listeners.AcideCloseDatabasePanelMenuItemAction;
import acide.gui.databasePanel.popup.listeners.AcideRefreshDataBasesMenuItemAction;
import acide.gui.databasePanel.utils.AcideOpenDatabaseWindow;
import acide.language.AcideLanguageManager;

/**
 * ACIDE - A Configurable IDE data base panel root popup menu.
 * 
 * @version 0.11
 * @see JPopupMenu
 */
public class AcideDataBasePanelRootPopupMenu extends JPopupMenu {
	/**
	 *  ACIDE - A Configurable IDE Databases node popup serial version UID.
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * ACIDE - A Configurable IDE Databases node popup menu new database menu
	 * item image icon.
	 */
	private static final ImageIcon OPEN_DATABASE = new ImageIcon(
			"./resources/icons/database/newDatabase.png");
	/**
	 * ACIDE - A Configurable IDE  Databases node popup menu refresh databases
	 * menu item image icon.
	 */
	private static final ImageIcon REFRESH_DATABASES = new ImageIcon(
			"./resources/icons/database/refreshDatabase.png");
	
	/**
	 * ACIDE - A Configurable IDE  Databases node popup menu close databases
	 * menu item image icon.
	 */
	private static final ImageIcon CLOSE_DATABASES = new ImageIcon(
			"./resources/icons/database/closeDatabase.png");
	
	/**
	 * ACIDE - A Configurable IDE Databases node popup menu set as default menu
	 * item.
	 */
	private JMenuItem _setAsDefault;
	/**
	 * ACIDE - A Configurable IDE  Databases node popup menu onew database menu
	 * item.
	 */
	private JMenuItem _openDataBase;
	/**
	 * ACIDE - A Configurable IDE  Databases node popup menu refresh databases menu
	 * item.
	 */
	private JMenuItem _refreshDataBases;
	
	/**
	 * ACIDE - A Configurable IDE Databases node popup menu close databases files
	 * menu item.
	 */
	private JMenuItem _closeDataBases;
	
	/**
	 * Creates a new ACIDE - A Configurable IDE explorer panel popup menu.
	 */
	public AcideDataBasePanelRootPopupMenu() {

		buildComponents();
		addComponents();
		setListeners();
	}
	private void addComponents() {


		add(_openDataBase);

		add(_refreshDataBases);
		
		add(_closeDataBases);
		
	}

	/**
	 * Builds the ACIDE - A Configurable IDE explorer panel popup menu
	 * components.
	 */
	private void buildComponents() {


	/*	_setAsDefault = new JMenuItem(AcideLanguageManager.getInstance()
				.getLabels().getString("s2042"));*/

		_openDataBase = new JMenuItem(AcideLanguageManager.getInstance()
				.getLabels().getString("s2043"), OPEN_DATABASE);

		_refreshDataBases = new JMenuItem(AcideLanguageManager.getInstance()
				.getLabels().getString("s2044"), REFRESH_DATABASES);

		_closeDataBases = new JMenuItem(AcideLanguageManager.getInstance()
				.getLabels().getString("s2045"), CLOSE_DATABASES);
	
	}

	/**
	 * Sets the ACIDE - A Configurable IDE explorer panel popup menu menu item
	 * listeners.
	 */
	private void setListeners() {
/*
		_setAsDefault
				.addActionListener(new AcideSetDefaultMenuItemAction());*/

		_openDataBase.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent arg0) {
						new AcideOpenDatabaseWindow();
					}
				});

		_refreshDataBases.addActionListener(new AcideRefreshDataBasesMenuItemAction());

		_closeDataBases.addActionListener(new AcideCloseDatabasePanelMenuItemAction());
 
		
	}
	public JMenuItem get_setAsDefault() {
		return _setAsDefault;
	}
	public void set_setAsDefault(JMenuItem setAsDefault) {
		_setAsDefault = setAsDefault;
	}
	public JMenuItem get_openDataBase() {
		return _openDataBase;
	}
	public void set_openDataBase(JMenuItem openDataBase) {
		_openDataBase = openDataBase;
	}
	public JMenuItem get_refreshDataBases() {
		return _refreshDataBases;
	}
	public void set_refreshDataBases(JMenuItem refreshDataBases) {
		_refreshDataBases = refreshDataBases;
	}
	public JMenuItem get_closeDataBases() {
		return _closeDataBases;
	}
	public void set_closeDataBases(JMenuItem closeDataBases) {
		_closeDataBases = closeDataBases;
	}

	
}
