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
package acide.gui.menuBar.configurationMenu.debugPanelMenu;

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
 * ACIDE - A Configurable IDE arrow shape menu.
 * 
 * @see JMenu
 * @version 0.16
 */
public class AcideDebugPanelArrowShapeMenu extends JMenu {
	/**
	 * ACIDE - A Configurable IDE graph panel arrow shape menu class serial version UID.
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * ACIDE - A Configurable IDE graph panel arrow shape menu arrow shape menu name.
	 */
	public static final String ARROW_SHAPE_MENU_NAME = "Arrow Shape";
	/**
	 * ACIDE - A Configurable IDE graph panel arrow shape menu arrow shape line menu item name.
	 */
	public static final String ARROW_SHAPE_LINE = "Line";
	/**
	 * ACIDE - A Configurable IDE graph panel arrow shape menu arrow shape polygon menu item name.
	 */
	public static final String ARROW_SHAPE_POLYGON = "Polygon";
	/**
	 * ACIDE - A Configurable IDE console line shape item.
	 */
	private JCheckBoxMenuItem _arrowShapeLineMenuItem;
	/**
	 * ACIDE - A Configurable IDE square shape item has been inserted.
	 */
	private boolean _arrowShapeLineInserted;
	/**
	 * ACIDE - A Configurable IDE polygon shape item.
	 */
	private JCheckBoxMenuItem _arrowShapePolygonMenuItem;
	/**
	 * ACIDE - A Configurable IDE polygon shape item has been inserted.
	 */
	private boolean _arrowShapePolygonInserted;
	/**
	 * ACIDE - A Configurable IDE node shape configuration menu.
	 */
	private AcideMenuSubmenuConfiguration _ArrowShapeSubmenuConfiguration;
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
	
	public AcideDebugPanelArrowShapeMenu(){
		
		_arrowShapeLineInserted = false;
		_arrowShapePolygonInserted = false;
		
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
	 * Builds the ACIDE - A Configurable IDE arrow shape menu components.
	 */
	private void buildComponents() {
		if (!AcideMenuItemsConfiguration.getInstance().getMenuItemsManager()
				.getSubmenu(AcideConfigurationMenu.CONFIGURATION_MENU_NAME)
				.getSubmenu(AcideDebugPanelMenu.DEBUG_MENU_NAME)
				.hasSubmenu(ARROW_SHAPE_MENU_NAME)){
			AcideMenuItemsConfiguration.getInstance().getMenuItemsManager()
				.getSubmenu(AcideConfigurationMenu.CONFIGURATION_MENU_NAME)
					.insertObject(new AcideMenuSubmenuConfiguration(ARROW_SHAPE_MENU_NAME));
		}
		
		Iterator<Object> it = AcideMenuItemsConfiguration.getInstance()
				.getMenuItemsManager().getSubmenu(AcideConfigurationMenu.CONFIGURATION_MENU_NAME)
				.getSubmenu(AcideDebugPanelMenu.DEBUG_MENU_NAME)
				.getItemsManager().getSubmenu(ARROW_SHAPE_MENU_NAME).
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
					
					AcideMenuItemConfiguration obItem = (AcideMenuItemConfiguration) ob;
					_insertedItems.put(obItem.getName(), new AcideInsertedItem(obItem));
				}
			}
		}
		
		
	
		// Creates the polygon shape menu item
		_arrowShapePolygonMenuItem = new JCheckBoxMenuItem();
				
		// Sets the polygon shape menu item name
		_arrowShapePolygonMenuItem.setName(ARROW_SHAPE_POLYGON);
				
		_arrowShapePolygonMenuItem.setSelected(true);
		
	
		// Creates the line shape menu item
		_arrowShapeLineMenuItem = new JCheckBoxMenuItem();
		
		// Sets the circle shape menu item name
		_arrowShapeLineMenuItem.setName(ARROW_SHAPE_LINE);

		_arrowShapeLineMenuItem.setSelected(false);
			
		
		//sets the listeners to the components
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
		if(!(name.equals(ARROW_SHAPE_LINE) ||
				name.equals(ARROW_SHAPE_POLYGON)))
			return true;
		return false;
	}
	/**
	 * Adds the components to the ACIDE - A Configurable IDE arrow shape menu.
	 */
	private void addComponents() {
		
		Iterator<Object> it = AcideMenuItemsConfiguration.getInstance()
				.getMenuItemsManager().getSubmenu(AcideConfigurationMenu.CONFIGURATION_MENU_NAME)
				.getItemsManager()
				.getSubmenu(AcideDebugPanelMenu.DEBUG_MENU_NAME)
				.getSubmenu(ARROW_SHAPE_MENU_NAME).getItemsManager().managerIterator();
		while (it.hasNext()){
			AcideMenuObjectConfiguration ob = (AcideMenuObjectConfiguration) it.next();
			String name = ob.getName();
			if (name.equals(ARROW_SHAPE_LINE)){
				// Adds the arrow shape line menu item to the menu
				add(_arrowShapeLineMenuItem);
				_arrowShapeLineInserted = true;
			}else if (name.equals(ARROW_SHAPE_POLYGON)){
				// Adds the arrow shape polygon menu item to the menu
				add(_arrowShapePolygonMenuItem);
				_arrowShapePolygonInserted = true;
			}else {
				if (ob.isSubmenu()){
					add(_insertedMenus.get(ob.getName()));
				}else{
					add(_insertedItems.get(ob.getName()));
				}
			}
		}
		
		if (!_arrowShapePolygonInserted)
			add(_arrowShapePolygonMenuItem);
		if (!_arrowShapeLineInserted)
			add(_arrowShapeLineMenuItem);
		
	}
	/**
	 * Set the text of the  A Configurable IDE arrow shape configuration menu
	 */
	public void setTextOfMenuComponents() {
		//sets the text to the arrow shape line menu item
		_arrowShapeLineMenuItem.setText(AcideLanguageManager
				.getInstance().getLabels().getString("s2241"));
		//sets the text to the arrow shape polygon menu item
		_arrowShapePolygonMenuItem.setText(AcideLanguageManager
				.getInstance().getLabels().getString("s2242"));
		
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
	 * Returns the ACIDE - A Configurable IDE arrow shape menu line shape menu item.
	 * 
	 * @return the ACIDE - A Configurable IDE arrow shape menu line shape menu item.
	 */
	public JCheckBoxMenuItem get_arrowShapeLineMenuItem() {
		return _arrowShapeLineMenuItem;
	}
	/**
	 * Sets a new value to the ACIDE - A Configurable IDE arrow shape menu shape line menu item.
	 * 
	 * @param _arrowShapeLineMenuItem new value to set.
	 */
	public void set_arrowShapeLineMenuItem(JCheckBoxMenuItem _arrowShapeLineMenuItem) {
		this._arrowShapeLineMenuItem = _arrowShapeLineMenuItem;
	}
	/**
	 * Returns the ACIDE - A Configurable IDE arrow shape menu polygon shape menu item.
	 * 
	 * @return the ACIDE - A Configurable IDE arrow shape menu polygon shape menu item.
	 */
	public JCheckBoxMenuItem get_arrowShapePolygonMenuItem() {
		return _arrowShapePolygonMenuItem;
	}
	/**
	 * Sets a new value to the ACIDE - A Configurable IDE arrow shape menu shape polygon menu item.
	 * 
	 * @param _arrowShapePolygonMenuItem new value to set.
	 */
	public void set_arrowShapePolygonMenuItem(
			JCheckBoxMenuItem _arrowShapePolygonMenuItem) {
		this._arrowShapePolygonMenuItem = _arrowShapePolygonMenuItem;
	}
	/**
	 * Sets the ACIDE - A Configurable IDE arrow shape menu listeners.
	 */
	public void setListeners(){
		//sets the shape line menu item listener
		_arrowShapeLineMenuItem.addActionListener(new AcideInsertedItemListener(
				AcideMenuItemsConfiguration.getInstance()
				.getSubmenu(AcideConfigurationMenu.CONFIGURATION_MENU_NAME)
				.getSubmenu(AcideDebugPanelMenu.DEBUG_MENU_NAME)
				.getSubmenu(ARROW_SHAPE_MENU_NAME)
				.getItem(ARROW_SHAPE_LINE)));
		//sets the shape polygon menu item listener
		_arrowShapePolygonMenuItem.addActionListener(new AcideInsertedItemListener(
				AcideMenuItemsConfiguration.getInstance()
				.getSubmenu(AcideConfigurationMenu.CONFIGURATION_MENU_NAME)
				.getSubmenu(AcideDebugPanelMenu.DEBUG_MENU_NAME)
				.getSubmenu(ARROW_SHAPE_MENU_NAME)
				.getItem(ARROW_SHAPE_POLYGON)));

	}

}
