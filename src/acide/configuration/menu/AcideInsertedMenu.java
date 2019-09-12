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

import java.util.HashMap;
import java.util.Iterator;

import javax.swing.JMenu;

import acide.gui.menuBar.listeners.AcideMenuBarMouseClickListener;
import acide.utils.IconsUtils;

/**
 * ACIDE - A Configurable IDE inserted menu.
 * 
 * @version 0.11
 * @see JMenu
 */
public class AcideInsertedMenu  extends JMenu {
	/**
	 * ACIDE - A Configurable IDE inserted menu class serial version UID.
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * ACIDE - A Configurable IDE file menu name.
	 */
	private String _menuName;
	/**
	 * ACIDE - A Configurable IDE inserted items.
	 */
	private HashMap<String, AcideInsertedItem> _items;
	/**
	 * ACIDE - A Configurable IDE inserted submenus.
	 */
	private HashMap<String, AcideInsertedMenu> _subMenus;
	
	/**
	 * ACIDE - A Configurable IDE menu configuration.
	 */
	private AcideMenuSubmenuConfiguration _submenuConfiguration;
	
	/**
	 * Creates a new ACIDE - A Configurable IDE inserted menu.
	 * 
	 * @param configuration
	 * 				the menu configuration to set
	 */
	public AcideInsertedMenu(AcideMenuSubmenuConfiguration configuration) {
		
		//Sets the configuration
		_submenuConfiguration = configuration;
		
		//Sets the menu name
		_menuName = _submenuConfiguration.getName();
		
		setName(_menuName);
		
		//Creates the hashmap for inserted items
		_items = new HashMap<String, AcideInsertedItem>();
		
		//Creates the hashmap for inserted submenus
		_subMenus = new HashMap<String, AcideInsertedMenu>();

		// Builds the menu components
		buildComponents();

		// Adds the components to the menu
		addComponents();

		// Sets the text of the edit menu components
		setTextOfMenuComponents();
	}
	
	/**
	 * Builds the ACIDE - A Configurable IDE edit menu components.
	 */
	private void buildComponents() {
		//Gets the iterator for the menu configuration
		Iterator<Object> it = _submenuConfiguration.getItemsManager().managerIterator();
		
		//For each object of the menu, inserts it in the correct hashmap
		while (it.hasNext()){
			AcideMenuObjectConfiguration ob = (AcideMenuObjectConfiguration) it.next();
			//If is a submenu, inserts it in submenus hashmap
			if (ob.isSubmenu()){
				AcideMenuSubmenuConfiguration obMenu = (AcideMenuSubmenuConfiguration) ob;
				_subMenus.put(obMenu.getName(), new AcideInsertedMenu(obMenu));
			//If is a item, inserts it in items hashmap
			} else{
				AcideMenuItemConfiguration obItem = (AcideMenuItemConfiguration) ob;
				_items.put(obItem.getName(), new AcideInsertedItem(IconsUtils.getIcon(
							obItem.getImage()), obItem));
			}
		}
	}
	
	/**
	 * Adds the component to the menu
	 */
	private void addComponents() {
		//Gets the iterator for the menu configuration
		Iterator<Object> it = _submenuConfiguration.getItemsManager().managerIterator();
		
		//For each object of the menu, gets it from the correct hashmap
		while (it.hasNext()){
			AcideMenuObjectConfiguration ob = (AcideMenuObjectConfiguration) it.next();
			//If is a submenu, gets it from submenus hashmap
			if (ob.isSubmenu()){
				add(_subMenus.get(ob.getName()));
			//If is a item, gets it from items hashmap
			} else{
				add(_items.get(ob.getName()));
			}
		}
	}
	
	/**
	 * Set the text of menu components according to the selected language
	 */
	public void setTextOfMenuComponents(){
		//Gets the iterator for the menu configuration
		Iterator<Object> it = _submenuConfiguration.getItemsManager().managerIterator();
		
		//For each object of the menu, gets it from the correct hashmap
		while (it.hasNext()){
			AcideMenuObjectConfiguration ob = (AcideMenuObjectConfiguration) it.next();
			if (ob.isSubmenu()){
				AcideInsertedMenu menu = _subMenus.get(ob.getName());
				menu.setText(menu.getMenuName());
			} else{
				AcideInsertedItem item = _items.get(ob.getName());
				item.setText(item.getItemName());
			}
		}
	}
	
	/**
	 * Updates the components visibility according to the menu configuration
	 */
	public void updateComponentsVisibility(){
		//Gets the iterator for the menu configuration
		Iterator<Object> it = _submenuConfiguration.getItemsManager().managerIterator();
		
		//For each object of the menu, gets it from the correct hashmap
		while (it.hasNext()){
			AcideMenuObjectConfiguration ob = (AcideMenuObjectConfiguration) it.next();
			if (ob.isSubmenu()){
				//If is submenu, updates its components visibility
				_subMenus.get(ob.getName()).updateComponentsVisibility();
				_subMenus.get(ob.getName()).setVisible(ob.isVisible());
			}else{
				_items.get(ob.getName()).setVisible(ob.isVisible());
			}
		}
	}

	/**
	 * Returns the ACIDE - A Configurable IDE inserted menu name.
	 * 
	 * @return the ACIDE - A Configurable IDE inserted menu name.
	 */
	public String getMenuName() {
		return _menuName;
	}

	/**
	 * Sets a new value to the ACIDE - A Configurable IDE inserted menu name.
	 * 
	 * @param menuName
	 * 			inserted menu name.
	 */
	public void setMenuName(String menuName) {
		this._menuName = menuName;
	}

	/**
	 * Sets the listeners for the menu components
	 */
	public void setListeners() {
		//Gets the iterator for the menu configuration
		Iterator<Object> it = _submenuConfiguration.getItemsManager().managerIterator();
		//For each object of the menu, gets it from the correct hashmap
		while (it.hasNext()){
			AcideMenuObjectConfiguration ob = (AcideMenuObjectConfiguration) it.next();
			if (ob.isSubmenu()){
				//If is submenu, sets its components listeners
				_subMenus.get(ob.getName()).addMouseListener(new AcideMenuBarMouseClickListener());
				_subMenus.get(ob.getName()).setListeners();
			}else{
				AcideInsertedItem aux = _items.get(ob.getName());
				aux.addActionListener(new AcideInsertedItemListener(aux));
			}
		}
	}
	

}
