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
package acide.gui.menuBar.configurationMenu.graphPanelMenu;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import javax.swing.JMenu;
import javax.swing.JMenuItem;

import acide.configuration.menu.AcideInsertedItem;
import acide.configuration.menu.AcideInsertedItemListener;
import acide.configuration.menu.AcideInsertedMenu;
import acide.configuration.menu.AcideMenuItemConfiguration;
import acide.configuration.menu.AcideMenuItemsConfiguration;
import acide.configuration.menu.AcideMenuObjectConfiguration;
import acide.configuration.menu.AcideMenuSubmenuConfiguration;
import acide.gui.menuBar.configurationMenu.AcideConfigurationMenu;
import acide.language.AcideLanguageManager;

/**
 * ACIDE - A Configurable IDE graph panel arrow color menu.
 * 
 *
 * @see JMenu
 */
public class AcideGraphPanelArrowColorMenu extends JMenu {

	/**
	 * ACIDE - A Configurable IDE arrow color menu serial version UID.
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * ACIDE - A Configurable IDE arrow color menu arrow color menu name.
	 */
	public static final String ARROW_COLOR_MENU_NAME = "Arrow Color";
	/**
	 * ACIDE - A Configurable IDE arrow color menu arrow color direct menu item name.
	 */
	public static final String ARROW_COLOR_DIRECT = "Direct";
	/**
	 * ACIDE - A Configurable IDE arrow color menu arrow color inverse menu item name.
	 */
	public static final String ARROW_COLOR_INVERSE = "Inverse";	
	/**
	 * ACIDE - A Configurable IDE console direct color item.
	 */
	private JMenuItem _arrowColorDirectMenuItem;
	/**
	 * ACIDE - A Configurable IDE direct color item has been inserted.
	 */
	private boolean _arrowColorDirectInserted;
	/**
	 * ACIDE - A Configurable IDE console inverse color item.
	 */
	private JMenuItem _arrowColorInverseMenuItem;
	/**
	 * ACIDE - A Configurable IDE inverse color item has been inserted.
	 */
	private boolean _arrowColorInverseInserted;
	/**
	 * ACIDE - A Configurable IDE arrow color configuration menu.
	 */
	private AcideMenuSubmenuConfiguration _ArrowColorSubmenuConfiguration;
	/**
	 * ACIDE - A Configurable IDE inserted menus hashmap.
	 */
	private HashMap<String, AcideInsertedMenu> _insertedMenus;
	/**
	 * ACIDE - A Configurable IDE inserted items hashmap.
	 */
	private HashMap<String, AcideInsertedItem> _insertedItems;
	/**
	 * ACIDE - A Configurable IDE array list of inserted objects.
	 */
	private ArrayList<AcideMenuObjectConfiguration> _insertedObjects;

	/**
	 * Creates a new ACIDE - A Configurable IDE arrow color menu.
	 */
	public AcideGraphPanelArrowColorMenu(){
		
		_arrowColorDirectInserted=false;
		_arrowColorInverseInserted=false;
		
		_insertedItems = new HashMap<String, AcideInsertedItem>();
		
		_insertedMenus = new HashMap<String, AcideInsertedMenu>();
		
		_insertedObjects = new ArrayList<AcideMenuObjectConfiguration>();
		
		// Builds the menu components
		buildComponents();

		// Adds the components to the menu
		addComponents();

		// Sets the text of the database panel menu components
		setTextOfMenuComponents();
		
	}

	/**
	 * Builds the ACIDE - A Configurable IDE arrow color menu components.
	 */
	private void buildComponents() {
		if (!AcideMenuItemsConfiguration.getInstance().getMenuItemsManager()
				.getSubmenu(AcideConfigurationMenu.CONFIGURATION_MENU_NAME)
				.getSubmenu(AcideGraphPanelMenu.GRAPH_MENU_NAME)
				.hasSubmenu(ARROW_COLOR_MENU_NAME)){
			AcideMenuItemsConfiguration.getInstance().getMenuItemsManager()
				.getSubmenu(AcideConfigurationMenu.CONFIGURATION_MENU_NAME)
				.getSubmenu(AcideGraphPanelMenu.GRAPH_MENU_NAME)
					.insertObject(new AcideMenuSubmenuConfiguration(ARROW_COLOR_MENU_NAME));
		}
		
		Iterator<Object> it = AcideMenuItemsConfiguration.getInstance()
				.getMenuItemsManager().getSubmenu(AcideConfigurationMenu.CONFIGURATION_MENU_NAME)
				.getSubmenu(AcideGraphPanelMenu.GRAPH_MENU_NAME)
				.getItemsManager().getSubmenu(ARROW_COLOR_MENU_NAME).getItemsManager().managerIterator();
		
		while (it.hasNext()){
			AcideMenuObjectConfiguration ob = (AcideMenuObjectConfiguration) it.next();
			String name = ob.getName();
			if (isOriginal(name)){
				_insertedObjects.add(ob);
				if (ob.isSubmenu()){
					AcideMenuSubmenuConfiguration obSubmenu = (AcideMenuSubmenuConfiguration) ob;
					_insertedMenus.put(ob.getName(), new AcideInsertedMenu(obSubmenu));
				}else {
					//TODO insert with the icon

					AcideMenuItemConfiguration obItem = (AcideMenuItemConfiguration) ob;
					_insertedItems.put(obItem.getName(), new AcideInsertedItem(obItem));
				}
			}
			
				
		}
		
		//TODO add an icon to the item
		// Creates the direct color menu item
		_arrowColorDirectMenuItem = new JMenuItem();
		
		_arrowColorDirectMenuItem.setName(ARROW_COLOR_DIRECT);
		
		//TODO add an icon to the item
		// Creates the inverse color menu item
		_arrowColorInverseMenuItem = new JMenuItem();
		
		_arrowColorInverseMenuItem.setName(ARROW_COLOR_DIRECT);
	
		setListeners();
	}
	/**
	 * Gets if the menu name given as parameter is original
	 * 
	 * @param name
	 *            the name we want to check
	 * @return if the name given as parameter is original
	 */
	private boolean isOriginal(String name) {
		if(!name.equals(ARROW_COLOR_MENU_NAME)
				&& !name.equals(ARROW_COLOR_DIRECT)
				&& !name.equals(ARROW_COLOR_INVERSE))
			return true;
		return false;
	}
	
	/**
	 * Adds the components to the ACIDE - A Configurable IDE arrow color menu.
	 */
	private void addComponents() {
		
		Iterator<Object> it = AcideMenuItemsConfiguration.getInstance()
				.getMenuItemsManager().getSubmenu(AcideConfigurationMenu.CONFIGURATION_MENU_NAME)
				.getSubmenu(AcideGraphPanelMenu.GRAPH_MENU_NAME)
				.getItemsManager().getSubmenu(ARROW_COLOR_MENU_NAME).getItemsManager().managerIterator();
		while (it.hasNext()){
			AcideMenuObjectConfiguration ob = (AcideMenuObjectConfiguration) it.next();
			String name = ob.getName();
			if(name.equals(ARROW_COLOR_DIRECT)){
				add(_arrowColorDirectMenuItem);
				_arrowColorDirectInserted=true;
			}else if(name.equals(ARROW_COLOR_INVERSE)){
				add(_arrowColorInverseMenuItem);
				_arrowColorInverseInserted=true;
			}else {
				if (ob.isSubmenu()){
					add(_insertedMenus.get(ob.getName()));
				}else{
					add(_insertedItems.get(ob.getName()));
				}
			}
		}
		
		if(!_arrowColorDirectInserted)
			add(_arrowColorDirectMenuItem);
		
		if(!_arrowColorInverseInserted)
			add(_arrowColorInverseMenuItem);
	}

	/**
	 * Set the text of the  A Configurable IDE arrow color configuration menu
	 */
	public void setTextOfMenuComponents() {
		//sets the text to the arrow color direct menu item
		_arrowColorDirectMenuItem.setText(AcideLanguageManager
				.getInstance().getLabels().getString("s2238"));
		//sets the text to the arrow color inverse menu item
		_arrowColorInverseMenuItem.setText(AcideLanguageManager
				.getInstance().getLabels().getString("s2239"));
		
		Iterator<AcideMenuObjectConfiguration> it = _insertedObjects.iterator();
		while (it.hasNext()){
			AcideMenuObjectConfiguration ob = it.next();
			if (ob.isSubmenu()){
				_insertedMenus.get(ob.getName()).setText(ob.getName());
				_insertedMenus.get(ob.getName()).setTextOfMenuComponents();
			}else{
				_insertedItems.get(ob.getName()).setText(ob.getName());
			}
		}
	}
	
	/**
	 * Returns the ACIDE - A Configurable IDE arrow color menu direct color menu item.
	 * 
	 * @return the ACIDE - A Configurable IDE arrow color menu direct color menu item.
	 */
	public JMenuItem get_arrowColorDirectMenuItem() {
		return _arrowColorDirectMenuItem;
	}
	/**
	 * Sets a new value to the ACIDE - A Configurable IDE arrow color menu direct color menu item.
	 * 
	 * @param _arrowColorDirectMenuItem new value to set.
	 */
	public void set_arrowColorDirectMenuItem(JMenuItem _arrowColorDirectMenuItem) {
		this._arrowColorDirectMenuItem = _arrowColorDirectMenuItem;
	}
	/**
	 * Returns the ACIDE - A Configurable IDE arrow color menu inverse color menu item.
	 * 
	 * @return the ACIDE - A Configurable IDE arrow color menu inverse color menu item.
	 */
	public JMenuItem get_arrowColorInverseMenuItem() {
		return _arrowColorInverseMenuItem;
	}
	/**
	 * Sets a new value to the ACIDE - A Configurable IDE arrow color menu inverse color menu item.
	 * 
	 * @param _arrowColorInverseMenuItem new value to set.
	 */
	public void set_arrowColorInverseMenuItem(JMenuItem _arrowColorInverseMenuItem) {
		this._arrowColorInverseMenuItem = _arrowColorInverseMenuItem;
	}
	/**
	 * Sets the ACIDE - A Configurable IDE arrow color menu listeners.
	 */
	public void setListeners(){
		//sets the arrow color direct menu item listener
		_arrowColorDirectMenuItem.addActionListener(new AcideInsertedItemListener(
				AcideMenuItemsConfiguration.getInstance()
				.getSubmenu(AcideConfigurationMenu.CONFIGURATION_MENU_NAME)
				.getSubmenu(AcideGraphPanelMenu.GRAPH_MENU_NAME)
				.getSubmenu(ARROW_COLOR_MENU_NAME)
				.getItem(ARROW_COLOR_DIRECT)));
		//sets the arrow color inverse menu item listener
		_arrowColorInverseMenuItem.addActionListener(new AcideInsertedItemListener(
				AcideMenuItemsConfiguration.getInstance()
				.getSubmenu(AcideConfigurationMenu.CONFIGURATION_MENU_NAME)
				.getSubmenu(AcideGraphPanelMenu.GRAPH_MENU_NAME)
				.getSubmenu(ARROW_COLOR_MENU_NAME)
				.getItem(ARROW_COLOR_INVERSE)));
	}
}
