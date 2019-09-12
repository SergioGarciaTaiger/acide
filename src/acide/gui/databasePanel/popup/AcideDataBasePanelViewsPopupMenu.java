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

import acide.gui.databasePanel.popup.listeners.AcideCreateViewMenuItemAction;
import acide.gui.databasePanel.popup.listeners.AcidePasteViewMenuItemAction;
import acide.gui.menuBar.configurationMenu.databasePanelMenu.showDetails.AcideShowDetailsMenu;
import acide.language.AcideLanguageManager;

/**
 * ACIDE - A Configurable IDE  data base panel views popup menu.
 * 
 * @version 0.13
 * @see JPopupMenu
 */
public class AcideDataBasePanelViewsPopupMenu extends JPopupMenu {	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private static AcideDataBasePanelViewsPopupMenu _instance;
	/**
	 * ACIDE - A Configurable IDE views node popup menu create view  menu item
	 * image icon.
	 */
	private static final ImageIcon CREATE_VIEW = new ImageIcon(
			"./resources/icons/database/createView.png");
	
	private static final ImageIcon PASTE= new ImageIcon(
	"./resources/icons/database/paste.png");
	
	/**
	 * ACIDE - A Configurable IDE explorer panel popup menu unset compilable
	 * file menu item.
	 */
	private JMenuItem _pasteView;
	
	/**
	 * ACIDE - A Configurable IDE views node popup menu create view menu
	 * item.
	 */
	private JMenuItem _createView;
	
	/**
	 * ACIDE - A Configurable IDE views node popup menu show details menu.
	 */
	private AcideShowDetailsMenu _showDetails;
	
	public static AcideDataBasePanelViewsPopupMenu getInstance() {
		if (_instance == null)
			_instance = new AcideDataBasePanelViewsPopupMenu();
		return _instance;
	}

	public AcideDataBasePanelViewsPopupMenu() {
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

		add(_createView);
		
		add(new Separator());
		
		add(_pasteView);
		
		add(new Separator());
		
		add(_showDetails);
		

	}
	/**
	 * Builds the ACIDE - A Configurable IDE explorer panel popup menu
	 * components.
	 */
	private void buildComponents() {

		_createView = new JMenuItem(AcideLanguageManager.getInstance()
				.getLabels().getString("s2061"), CREATE_VIEW);
		
		_pasteView= new JMenuItem(AcideLanguageManager.getInstance()
				.getLabels().getString("s2053"),PASTE);
		
		
		_showDetails = new AcideShowDetailsMenu();
		
		_showDetails.setText(AcideLanguageManager.getInstance()
				.getLabels().getString("s2271"));
	}

	/**
	 * Sets the ACIDE - A Configurable IDE explorer panel popup menu menu item
	 * listeners.
	 */
	private void setListeners() {
	
		_createView.addActionListener(new AcideCreateViewMenuItemAction());
		
		_pasteView.addActionListener(new AcidePasteViewMenuItemAction());

		_showDetails.setListeners();
	}
	
	public JMenuItem get_CreateTableWithDatalog() {
		return _createView;
	}
	public void set_CreateTableWithDatalog(JMenuItem createTableWithDatalog) {
		_createView = createTableWithDatalog;
	}

	public AcideShowDetailsMenu getShowDetails() {
		return _showDetails;
	}
	
}
