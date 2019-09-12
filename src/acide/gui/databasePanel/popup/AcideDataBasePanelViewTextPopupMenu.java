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

import acide.language.AcideLanguageManager;

/**
 * ACIDE - A Configurable IDE data base panel view text popup menu.
 * 
 * @version 0.11
 * @see JPopupMenu
 */
public class AcideDataBasePanelViewTextPopupMenu extends JPopupMenu {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * ACIDE - A Configurable IDE constraint node popup menu drop constraint menu item
	 * image icon.
	 */
	private static final ImageIcon COPY = new ImageIcon(
			"./resources/icons/database/copy.png");
	/**
	 * ACIDE - A Configurable IDE constraint node popup menu modify constraint menu item
	 * image icon.
	 */
	private static final ImageIcon SHOW_EDIT = new ImageIcon(
			"./resources/icons/database/modify.png");	
	/**
	 * ACIDE - A Configurable IDE constraint node popup menu drop constraint
	 * file menu item.
	 */
	private JMenuItem _copy;
	
	/**
	 * ACIDE - A Configurable IDE constraint node popup menu modify constraint
	 * file menu item.
	 */
	private JMenuItem _show;
	

	public AcideDataBasePanelViewTextPopupMenu() {
		super();
		buildComponents();
		addComponents();
		setListeners();
		
	}
	/**
	 * Adds the components to the ACIDE - A Configurable IDE constraint node
	 * popup menu.
	 */
	private void addComponents() {
		add(_show);
		
		add(_copy);

		
	}
	/**
	 * Builds the ACIDE - A Configurable IDE constraint node popup menu
	 * components.
	 */
	private void buildComponents() {
		
		_copy= new JMenuItem(AcideLanguageManager
				.getInstance().getLabels().getString("s2052"),COPY);
		
		_show = new JMenuItem(AcideLanguageManager
				.getInstance().getLabels().getString("s2140"),SHOW_EDIT);
	
	}

	/**
	 * Sets the ACIDE - A Configurable IDE constraint node popup menu menu item
	 * listeners.
	 */
	private void setListeners() {
	}
	
}
