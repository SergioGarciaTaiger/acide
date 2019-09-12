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

import javax.swing.JCheckBoxMenuItem;
import javax.swing.JMenu;

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
 * ACIDE - A Configurable IDE graph panel node shape menu.
 * 
 * @see JMenu
 */
public class AcideGraphPanelNodeShapeMenu extends JMenu {

	/**
	 * ACIDE - A Configurable IDE graph panel node shape menu class serial version UID.
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * ACIDE - A Configurable IDE node shape menu node shape menu name.
	 */
	public static final String NODE_SHAPE_MENU_NAME = "Node Shape";
	/**
	 * ACIDE - A Configurable IDE node shape menu node shape square name.
	 */
	public static final String NODE_SHAPE_SQUARE = "Square";
	/**
	 * ACIDE - A Configurable IDE node shape menu node circle menu name.
	 */
	public static final String NODE_SHAPE_CIRCLE = "Circle";
	/**
	 * ACIDE - A Configurable IDE console square shape item.
	 */
	private JCheckBoxMenuItem _nodeShapeSquareMenuItem;
	/**
	 * ACIDE - A Configurable IDE square shape item has been inserted.
	 */
	private boolean _nodeShapeSquareInserted;
	/**
	 * ACIDE - A Configurable IDE circle shape item.
	 */
	private JCheckBoxMenuItem _nodeShapeCircleMenuItem;
	/**
	 * ACIDE - A Configurable IDE circle shape item has been inserted.
	 */
	private boolean _nodeShapeCircleInserted;
	/**
	 * ACIDE - A Configurable IDE node shape configuration menu.
	 */
	private AcideMenuSubmenuConfiguration _NodeShapeSubmenuConfiguration;
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
	 * Creates a new ACIDE - A Configurable IDE node shape menu.
	 */
	
	public AcideGraphPanelNodeShapeMenu(){
		
		_nodeShapeCircleInserted = false;
		_nodeShapeSquareInserted = false;
		
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
	 * Builds the ACIDE - A Configurable IDE node shape menu components.
	 */
	private void buildComponents() {
		if (!AcideMenuItemsConfiguration.getInstance().getMenuItemsManager()
				.getSubmenu(AcideConfigurationMenu.CONFIGURATION_MENU_NAME)
				.getSubmenu(AcideGraphPanelMenu.GRAPH_MENU_NAME)
				.hasSubmenu(NODE_SHAPE_MENU_NAME)){
			AcideMenuItemsConfiguration.getInstance().getMenuItemsManager()
				.getSubmenu(AcideConfigurationMenu.CONFIGURATION_MENU_NAME)
					.insertObject(new AcideMenuSubmenuConfiguration(NODE_SHAPE_MENU_NAME));
		}
		
		Iterator<Object> it = AcideMenuItemsConfiguration.getInstance()
				.getMenuItemsManager().getSubmenu(AcideConfigurationMenu.CONFIGURATION_MENU_NAME)
				.getSubmenu(AcideGraphPanelMenu.GRAPH_MENU_NAME)
				.getItemsManager().getSubmenu(NODE_SHAPE_MENU_NAME).
				getItemsManager().managerIterator();
		
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
		// Creates the circle shape menu item
		_nodeShapeCircleMenuItem = new JCheckBoxMenuItem();
		
		// Sets the circle shape menu item name
		_nodeShapeCircleMenuItem.setName(NODE_SHAPE_CIRCLE);

		_nodeShapeCircleMenuItem.setSelected(true);
			
		//TODO add an icon to the item
		// Creates the circle shape menu item
		_nodeShapeSquareMenuItem = new JCheckBoxMenuItem();
				
		// Sets the circle shape menu item name
		_nodeShapeSquareMenuItem.setName(NODE_SHAPE_SQUARE);
		
		_nodeShapeSquareMenuItem.setSelected(false);
		
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
		if(!(name.equals(NODE_SHAPE_CIRCLE) ||
				name.equals(NODE_SHAPE_SQUARE)))
			return true;
		return false;
	}
	/**
	 * Adds the components to the ACIDE - A Configurable IDE node shape menu.
	 */
	private void addComponents() {
		
		Iterator<Object> it = AcideMenuItemsConfiguration.getInstance()
				.getMenuItemsManager().getSubmenu(AcideConfigurationMenu.CONFIGURATION_MENU_NAME)
				.getItemsManager()
				.getSubmenu(AcideGraphPanelMenu.GRAPH_MENU_NAME)
				.getSubmenu(NODE_SHAPE_MENU_NAME).getItemsManager().managerIterator();
		while (it.hasNext()){
			AcideMenuObjectConfiguration ob = (AcideMenuObjectConfiguration) it.next();
			String name = ob.getName();
			if (name.equals(NODE_SHAPE_CIRCLE)){
				// Adds the file editor display options menu item to the menu
				add(_nodeShapeCircleMenuItem);
				_nodeShapeCircleInserted = true;
			}else if (name.equals(NODE_SHAPE_SQUARE)){
				// Adds the automatic indent check box menu item to the menu
				add(_nodeShapeSquareMenuItem);
				_nodeShapeSquareInserted = true;
			}else {
				if (ob.isSubmenu()){
					add(_insertedMenus.get(ob.getName()));
				}else{
					add(_insertedItems.get(ob.getName()));
				}
			}
		}
		
		if (!_nodeShapeCircleInserted)
			add(_nodeShapeCircleMenuItem);
		if (!_nodeShapeSquareInserted)
			add(_nodeShapeSquareMenuItem);
	}
	/**
	 * Set the text of the  A Configurable IDE node shape configuration menu
	 */
	public void setTextOfMenuComponents() {
		//sets the text of the shape circle menu item
		_nodeShapeCircleMenuItem.setText(AcideLanguageManager
				.getInstance().getLabels().getString("s2236"));
		//sets the text of the shape square menu item
		_nodeShapeSquareMenuItem.setText(AcideLanguageManager
				.getInstance().getLabels().getString("s2237"));
		
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
	 * Returns the ACIDE - A Configurable IDE node shape menu square shape menu item.
	 * 
	 * @return the ACIDE - A Configurable IDE node shape menu square shape menu item.
	 */
	public JCheckBoxMenuItem get_nodeShapeSquareMenuItem() {
		return _nodeShapeSquareMenuItem;
	}

	/**
	 * Sets a new value to the ACIDE - A Configurable IDE node shape menu shape square menu item.
	 * 
	 * @param _nodeShapeSquareMenuItem new value to set.
	 */
	public void set_nodeShapeSquareMenuItem(
			JCheckBoxMenuItem _nodeShapeSquareMenuItem) {
		this._nodeShapeSquareMenuItem = _nodeShapeSquareMenuItem;
	}

	/**
	 * Returns the ACIDE - A Configurable IDE node shape menu circle shape menu item.
	 * 
	 * @return the ACIDE - A Configurable IDE node shape menu circle shape menu item.
	 */
	public JCheckBoxMenuItem get_nodeShapeCircleMenuItem() {
		return _nodeShapeCircleMenuItem;
	}
	/**
	 * Sets a new value to the ACIDE - A Configurable IDE node shape menu shape circle menu item.
	 * 
	 * @param _nodeShapeCircleMenuItem new value to set.
	 */
	public void set_nodeShapeCircleMenuItem(
			JCheckBoxMenuItem _nodeShapeCircleMenuItem) {
		this._nodeShapeCircleMenuItem = _nodeShapeCircleMenuItem;
	}

	/**
	 * Sets the ACIDE - A Configurable IDE node shape menu listeners.
	 */
	public void setListeners(){
		//sets the shape circle menu item listener
		_nodeShapeCircleMenuItem.addActionListener(new AcideInsertedItemListener(
				AcideMenuItemsConfiguration.getInstance()
				.getSubmenu(AcideConfigurationMenu.CONFIGURATION_MENU_NAME)
				.getSubmenu(AcideGraphPanelMenu.GRAPH_MENU_NAME)
				.getSubmenu(AcideGraphPanelNodeShapeMenu.NODE_SHAPE_MENU_NAME)
				.getItem(NODE_SHAPE_CIRCLE)));
		//sets the shape square menu item listener
		_nodeShapeSquareMenuItem.addActionListener(new AcideInsertedItemListener(
				AcideMenuItemsConfiguration.getInstance()
				.getSubmenu(AcideConfigurationMenu.CONFIGURATION_MENU_NAME)
				.getSubmenu(AcideGraphPanelMenu.GRAPH_MENU_NAME)
				.getSubmenu(AcideGraphPanelNodeShapeMenu.NODE_SHAPE_MENU_NAME)
				.getItem(NODE_SHAPE_SQUARE)));


	}


}
