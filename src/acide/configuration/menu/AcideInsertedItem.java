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
package acide.configuration.menu;

import javax.swing.ImageIcon;
import javax.swing.JMenuItem;

/**
 * ACIDE - A Configurable IDE inserted item.
 * 
 * @version 0.11
 * @see JMenuItem
 */
public class AcideInsertedItem extends JMenuItem{
	/**
	 * ACIDE - A Configurable IDE inserted item class serial version UID.
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * ACIDE - A Configurable IDE file item name.
	 */
	private String _itemName;
	
	/**
	 * ACIDE - A Configurable IDE file item configuration.
	 */
	private AcideMenuItemConfiguration _itemConfiguration;
	
	/**
	 * Creates a new ACIDE - A Configurable IDE inserted item.
	 */
	public AcideInsertedItem(AcideMenuItemConfiguration ob) {
		super();
		
		_itemConfiguration = ob;
		
		_itemName = _itemConfiguration.getName();
		
		setName(_itemName);
	}
	
	/**
	 * Creates a new ACIDE - A Configurable IDE inserted item.
	 * 
	 * @param icon 
	 * 			the icon for the inserted item
	 * 
	 * @param ob
	 * 			the item configuration for the inserted item		
	 */
	public AcideInsertedItem(ImageIcon icon, AcideMenuItemConfiguration ob) {
		super(icon);
		
		_itemConfiguration = ob;
		
		_itemName = _itemConfiguration.getName();
		
		setName(_itemName);
	}

	/**
	 * Returns the ACIDE - A Configurable IDE inserted item name.
	 * 
	 * @return the ACIDE - A Configurable IDE inserted item name.
	 */
	public String getItemName() {
		return _itemName;
	}

	/**
	 * Sets a new value to the ACIDE - A Configurable IDE inserted item name.
	 * 
	 * @param itemName
	 * 			inserted item name.
	 */
	public void setItemName(String itemName) {
		this._itemName = itemName;
	}

	/**
	 * Returns the ACIDE - A Configurable IDE inserted item configuration.
	 * 
	 * @return the ACIDE - A Configurable IDE inserted item configuration.
	 */
	public AcideMenuItemConfiguration getItemConfiguration() {
		return _itemConfiguration;
	}

	/**
	 * Sets a new value to the ACIDE - A Configurable IDE inserted item configuration.
	 * 
	 * @param inserted item configuration.
	 */
	public void setItemConfiguration(AcideMenuItemConfiguration _itemConfiguration) {
		this._itemConfiguration = _itemConfiguration;
	}
	
	
}
