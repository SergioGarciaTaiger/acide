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
package acide.gui.menuBar.configurationMenu.menuMenu;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import javax.swing.ImageIcon;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

import acide.configuration.menu.AcideInsertedItem;
import acide.configuration.menu.AcideInsertedItemListener;
import acide.configuration.menu.AcideInsertedMenu;
import acide.configuration.menu.AcideMenuConfiguration;
import acide.configuration.menu.AcideMenuItemConfiguration;
import acide.configuration.menu.AcideMenuItemsConfiguration;
import acide.configuration.menu.AcideMenuObjectConfiguration;
import acide.configuration.menu.AcideMenuSubmenuConfiguration;
import acide.gui.menuBar.configurationMenu.AcideConfigurationMenu;
import acide.gui.menuBar.configurationMenu.menuMenu.listeners.AcideLoadMenuMenuItemListener;
import acide.gui.menuBar.configurationMenu.menuMenu.listeners.AcideModifyMenuMenuItemListener;
import acide.gui.menuBar.configurationMenu.menuMenu.listeners.AcideNewMenuMenuItemListener;
import acide.gui.menuBar.configurationMenu.menuMenu.listeners.AcideSaveMenuAsMenuItemListener;
import acide.gui.menuBar.configurationMenu.menuMenu.listeners.AcideSaveMenuMenuItemListener;
import acide.gui.menuBar.listeners.AcideMenuBarMouseClickListener;
import acide.language.AcideLanguageManager;
import acide.log.AcideLog;
import acide.resources.AcideResourceManager;
import acide.utils.IconsUtils;

/**
 * ACIDE - A Configurable IDE menu menu.
 * 
 * @version 0.11
 * @see JMenu
 */
public class AcideMenuMenu extends JMenu {

	/**
	 * ACIDE - A Configurable IDE menu menu class serial version UID.
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * ACIDE - A Configurable IDE menu menu name.
	 */
	public final static String MENU_MENU_NAME = "Menu";
	/**
	 * ACIDE - A Configurable IDE menu menu new menu menu item name.
	 */
	public static final String NEW_MENU_NAME = "New Menu";
	/**
	 * ACIDE - A Configurable IDE menu menu new menu menu item name.
	 */
	public static final String LOAD_MENU_NAME = "Load Menu";
	/**
	 * ACIDE - A Configurable IDE menu menu modify menu menu item name.
	 */
	public static final String MODIFY_MENU_NAME = "Modify Menu";
	/**
	 * ACIDE - A Configurable IDE menu menu save menu menu item name.
	 */
	public static final String SAVE_MENU_NAME = "Save Menu";
	/**
	 * ACIDE - A Configurable IDE menu menu save menu as menu item name.
	 */
	public static final String SAVE_MENU_AS_NAME = "Save Menu As";
	/**
	 * ACIDE - A Configurable IDE menu menu new menu menu item.
	 */
	private JMenuItem _newMenuMenuItem;
	/**
	 * ACIDE - A Configurable IDE menu menu new menu menu item has been inserted.
	 */
	private boolean _newMenuInserted;
	/**
	 * ACIDE - A Configurable IDE menu menu load menu menu item.
	 */
	private JMenuItem _loadMenuMenuItem;
	/**
	 * ACIDE - A Configurable IDE menu menu load menu menu item has been inserted.
	 */
	private boolean _loadMenuInserted;
	/**
	 * ACIDE - A Configurable IDE menu menu modify menu menu item.
	 */
	private JMenuItem _modifyMenuMenuItem;
	/**
	 * ACIDE - A Configurable IDE menu menu modify menu menu item has been inserted.
	 */
	private boolean _modifyMenuInserted;
	/**
	 * ACIDE - A Configurable IDE menu menu save menu menu item.
	 */
	private JMenuItem _saveMenuMenuItem;
	/**
	 * ACIDE - A Configurable IDE menu menu save menu menu item has been inserted.
	 */
	private boolean _saveMenuInserted;
	/**
	 * ACIDE - A Configurable IDE menu menu save menu as menu item.
	 */
	private JMenuItem _saveMenuAsMenuItem;
	/**
	 * ACIDE - A Configurable IDE menu menu save menu as menu item has been inserted.
	 */
	private boolean _saveMenuAsInserted;
	/**
	 * ACIDE - A Configurable IDE menu menu configuration menu.
	 */
	private AcideMenuSubmenuConfiguration _menuSubmenuConfiguration;
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
	 * Creates a new ACIDE - A Configurable IDE menu menu.
	 */
	public AcideMenuMenu() {
		
		_newMenuInserted = false;
		_loadMenuInserted = false;
		_modifyMenuInserted = false;
		_saveMenuInserted = false;
		_saveMenuAsInserted = false;
		
		_insertedItems = new HashMap<String, AcideInsertedItem>();
		
		_insertedMenus = new HashMap<String, AcideInsertedMenu>();
		
		_insertedObjects = new ArrayList<AcideMenuObjectConfiguration>();

		// Builds the menu components
		buildComponents();

		// Adds the components to the menu
		addComponents();

		// Sets the text of the menu menu components
		setTextOfMenuComponents();
	}

	/**
	 * Adds the components to the ACIDE - A Configurable IDE menu menu.
	 */
	private void addComponents() {

		Iterator<Object> it = AcideMenuItemsConfiguration.getInstance()
				.getMenuItemsManager().getSubmenu(AcideConfigurationMenu.CONFIGURATION_MENU_NAME)
				.getItemsManager().getSubmenu(MENU_MENU_NAME).getItemsManager().managerIterator();
				;
		while (it.hasNext()){
			AcideMenuObjectConfiguration ob = (AcideMenuObjectConfiguration) it.next();
			String name = ob.getName();
			if (name.equals(NEW_MENU_NAME)){
				// Adds the new menu menu item
				add(_newMenuMenuItem);
				_newMenuInserted = true;
			}else if (name.equals(LOAD_MENU_NAME)){
				// Adds the load menu menu item
				add(_loadMenuMenuItem);
				_loadMenuInserted = true;
			}else if (name.equals(MODIFY_MENU_NAME)){
				// Adds the modify menu menu item
				add(_modifyMenuMenuItem);
				_modifyMenuInserted = true;
			}else if (name.equals(SAVE_MENU_NAME)){
				// Adds the save menu menu item
				add(_saveMenuMenuItem);
				_saveMenuInserted = true;
			}else if (name.equals(SAVE_MENU_AS_NAME)){
				// Adds the save menu as menu item
				add(_saveMenuAsMenuItem);
				_saveMenuAsInserted = true;
			}else {
				if (ob.isSubmenu()){
					add(_insertedMenus.get(ob.getName()));
				}else{
					add(_insertedItems.get(ob.getName()));
				}
			}
		}
		
		if (!_newMenuInserted)
			add(_newMenuMenuItem);
		if (!_loadMenuInserted)
			add(_loadMenuMenuItem);
		if (!_modifyMenuInserted)
			add(_modifyMenuMenuItem);
		if (!_saveMenuInserted)
			add(_saveMenuMenuItem);
		if (!_saveMenuAsInserted)
			add(_saveMenuAsMenuItem);
	
	}

	/**
	 * Builds the ACIDE - A Configurable IDE menu menu components.
	 */
	private void buildComponents() {
		
		if (!AcideMenuItemsConfiguration.getInstance().getMenuItemsManager()
				.getSubmenu(AcideConfigurationMenu.CONFIGURATION_MENU_NAME).hasSubmenu(MENU_MENU_NAME)){
			AcideMenuItemsConfiguration.getInstance().getMenuItemsManager()
				.getSubmenu(AcideConfigurationMenu.CONFIGURATION_MENU_NAME)
					.insertObject(new AcideMenuSubmenuConfiguration(MENU_MENU_NAME));
		}

		Iterator<Object> it = AcideMenuItemsConfiguration.getInstance()
				.getMenuItemsManager().getSubmenu(AcideConfigurationMenu.CONFIGURATION_MENU_NAME)
				.getItemsManager().getSubmenu(MENU_MENU_NAME).getItemsManager().managerIterator();
		
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
					_insertedItems.put(obItem.getName(), new AcideInsertedItem(IconsUtils.getIcon(
								obItem.getImage()), obItem));
				}
			}
		}
		
		// Creates the new menu menu item
		ImageIcon icon = IconsUtils.getIcon(AcideMenuItemsConfiguration.getInstance()
				.getMenuItemsManager().getSubmenu(AcideConfigurationMenu.CONFIGURATION_MENU_NAME)
				.getSubmenu(MENU_MENU_NAME).getItem(NEW_MENU_NAME).getImage());
		
		if (icon != null)
			_newMenuMenuItem = new JMenuItem(icon);
		else
			_newMenuMenuItem = new JMenuItem();

		// Sets the new menu menu item name
		_newMenuMenuItem.setName(NEW_MENU_NAME);

		// Creates the load menu menu item
		icon = IconsUtils.getIcon(AcideMenuItemsConfiguration.getInstance()
				.getMenuItemsManager().getSubmenu(AcideConfigurationMenu.CONFIGURATION_MENU_NAME)
				.getSubmenu(MENU_MENU_NAME).getItem(LOAD_MENU_NAME).getImage());
		
		if (icon != null)
			_loadMenuMenuItem = new JMenuItem(icon);
		else
			_loadMenuMenuItem = new JMenuItem();

		// Sets the load menu menu item name
		_loadMenuMenuItem.setName(LOAD_MENU_NAME);

		// Creates the modify menu menu item
		icon = IconsUtils.getIcon(AcideMenuItemsConfiguration.getInstance()
				.getMenuItemsManager().getSubmenu(AcideConfigurationMenu.CONFIGURATION_MENU_NAME)
				.getSubmenu(MENU_MENU_NAME).getItem(MODIFY_MENU_NAME).getImage());
		
		if (icon != null)
			_modifyMenuMenuItem = new JMenuItem(icon);
		else
			_modifyMenuMenuItem = new JMenuItem();

		// Sets the modify menu menu item name
		_modifyMenuMenuItem.setName(MODIFY_MENU_NAME);

		// Creates the save menu menu item
		icon = IconsUtils.getIcon(AcideMenuItemsConfiguration.getInstance()
				.getMenuItemsManager().getSubmenu(AcideConfigurationMenu.CONFIGURATION_MENU_NAME)
				.getSubmenu(MENU_MENU_NAME).getItem(SAVE_MENU_NAME).getImage());
		
		if (icon != null)
			_saveMenuMenuItem = new JMenuItem(icon);
		else
			_saveMenuMenuItem = new JMenuItem();

		// Sets the save menu menu item name
		_saveMenuMenuItem.setName(SAVE_MENU_NAME);

		// Disables the save menu menu item
		_saveMenuMenuItem.setEnabled(false);

		// Creates the save menu as menu item
		icon = IconsUtils.getIcon(AcideMenuItemsConfiguration.getInstance()
				.getMenuItemsManager().getSubmenu(AcideConfigurationMenu.CONFIGURATION_MENU_NAME)
				.getSubmenu(MENU_MENU_NAME).getItem(SAVE_MENU_AS_NAME).getImage());
		
		if (icon != null)
			_saveMenuAsMenuItem = new JMenuItem(icon);
		else
			_saveMenuAsMenuItem = new JMenuItem();

		// Sets the save menu as menu item name
		_saveMenuAsMenuItem.setName(SAVE_MENU_AS_NAME);
	}

	/**
	 * Sets the text of the ACIDE - A Configurable IDE menu menu components with
	 * the labels in the selected language to display.
	 */
	public void setTextOfMenuComponents() {

		// Sets the new menu menu item text
		_newMenuMenuItem.setText(AcideLanguageManager.getInstance().getLabels()
				.getString("s275"));

		// Sets the load menu menu item text
		_loadMenuMenuItem.setText(AcideLanguageManager.getInstance()
				.getLabels().getString("s276"));

		// Sets the modify menu menu item text
		_modifyMenuMenuItem.setText(AcideLanguageManager.getInstance()
				.getLabels().getString("s277"));

		// Sets the save menu menu item text
		_saveMenuMenuItem.setText(AcideLanguageManager.getInstance()
				.getLabels().getString("s278"));

		// Sets the save menu as menu item text
		_saveMenuAsMenuItem.setText(AcideLanguageManager.getInstance()
				.getLabels().getString("s279"));
		
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
	 * Updates the ACIDE - A Configurable IDE menu menu components visibility
	 * with the menu configuration.
	 */
	public void updateComponentsVisibility() {
		
		AcideMenuItemConfiguration newMenuConfiguration;
		AcideMenuItemConfiguration loadMenuConfiguration;
		AcideMenuItemConfiguration modifyMenuConfiguration;
		AcideMenuItemConfiguration saveMenuConfiguration;
		AcideMenuItemConfiguration saveMenuAsConfiguration;

		_menuSubmenuConfiguration = AcideMenuItemsConfiguration.getInstance()
					.getSubmenu(AcideConfigurationMenu.CONFIGURATION_MENU_NAME).getSubmenu(MENU_MENU_NAME);
		
		// Sets the new menu menu item to visible or not visible
		newMenuConfiguration = _menuSubmenuConfiguration.getItem(NEW_MENU_NAME);
		_newMenuMenuItem.setVisible(newMenuConfiguration.isVisible());
			
		// Sets the load menu menu item to visible or not visible
		loadMenuConfiguration = _menuSubmenuConfiguration.getItem(LOAD_MENU_NAME);
		_loadMenuMenuItem.setVisible(loadMenuConfiguration.isVisible());

		// Sets the modify menu menu item to visible or not visible
		modifyMenuConfiguration = _menuSubmenuConfiguration.getItem(MODIFY_MENU_NAME);
		_modifyMenuMenuItem.setVisible(modifyMenuConfiguration.isVisible());
			
		// Sets the save menu menu item to visible or not visible
		saveMenuConfiguration = _menuSubmenuConfiguration.getItem(SAVE_MENU_NAME);
		_saveMenuMenuItem.setVisible(saveMenuConfiguration.isVisible());

		// Sets the save as menu menu item to visible or not visible
		saveMenuAsConfiguration = _menuSubmenuConfiguration.getItem(SAVE_MENU_AS_NAME);
		_saveMenuAsMenuItem.setVisible(saveMenuAsConfiguration.isVisible());

		Iterator<AcideMenuObjectConfiguration> it = _insertedObjects.iterator();
		while (it.hasNext()){
			AcideMenuObjectConfiguration ob = it.next();
			if (ob.isSubmenu()){
				_insertedMenus.get(ob.getName()).updateComponentsVisibility();
				_insertedMenus.get(ob.getName()).setVisible(ob.isVisible());
			}else{
				_insertedItems.get(ob.getName()).setVisible(ob.isVisible());
			}
		}
		
		// Sets the menu menu to visible or not visible
		_menuSubmenuConfiguration.setVisible(_newMenuMenuItem.isVisible()
				|| _loadMenuMenuItem.isVisible()
				|| _modifyMenuMenuItem.isVisible()
				|| _saveMenuMenuItem.isVisible()
				|| _saveMenuAsMenuItem.isVisible());
		
		_menuSubmenuConfiguration.setErasable(false);
		
								
				try{			
					//Save the configuration for the menu that could have been modified
					AcideMenuConfiguration.getInstance()
						.saveMenuConfigurationFile("./configuration/menu/lastModified.menuConfig");
					
					// Gets the the ACIDE - A Configurable IDE current menu
					// configuration
					String currentMenuConfiguration = AcideResourceManager
							.getInstance().getProperty("currentMenuConfiguration");

					if (!currentMenuConfiguration
							.endsWith("lastModified.menuConfig")
							&& !currentMenuConfiguration
									.endsWith("newMenu.menuConfig")) {

						// Updates the the ACIDE - A Configurable IDE previous
						// menu
						// configuration
						AcideResourceManager.getInstance().setProperty(
								"previousMenuConfiguration",
								currentMenuConfiguration);
					}
					
					// Updates the the ACIDE - A Configurable IDE current menu
					// configuration
					AcideResourceManager.getInstance().setProperty(
							"currentMenuConfiguration", "./configuration/menu/lastModified.menuConfig");
				}		
				catch (Exception exception2) {

					// Updates the log
					AcideLog.getLog().error(exception2.getMessage());
					exception2.printStackTrace();
				}		
	}
	
	/**
	 * Gets if the menu name given as parameter is original
	 * @param name
	 * 		the name we want to check
	 * @return
	 * 		if the name given as parameter is original
	 */
	public boolean isOriginal(String name){
		if (!(name.equals(NEW_MENU_NAME))
			&& !(name.equals(LOAD_MENU_NAME))
			&& !(name.equals(MODIFY_MENU_NAME))
			&& !(name.equals(SAVE_MENU_NAME))
			&& !(name.equals(SAVE_MENU_AS_NAME))){
			return true;
		}else{
			return false;
		}
	}

	/**
	 * Set the ACIDE - A Configurable IDE menu menu item listeners.
	 */
	public void setListeners() {

		// Sets the new menu menu item action listener
		_newMenuMenuItem.addActionListener(new AcideNewMenuMenuItemListener());

		// Sets the load menu menu item action listener
		_loadMenuMenuItem
				.addActionListener(new AcideLoadMenuMenuItemListener());

		// Sets the modify menu menu item action listener
		_modifyMenuMenuItem
				.addActionListener(new AcideModifyMenuMenuItemListener());

		// Sets the save menu menu item action listener
		_saveMenuMenuItem
				.addActionListener(new AcideSaveMenuMenuItemListener());

		// Sets the save menu as menu item action listener
		_saveMenuAsMenuItem
				.addActionListener(new AcideSaveMenuAsMenuItemListener());
		
		Iterator<AcideMenuObjectConfiguration> it = _insertedObjects.iterator();
		while (it.hasNext()){
			AcideMenuObjectConfiguration ob = it.next();
			if (ob.isSubmenu()){
				_insertedMenus.get(ob.getName()).addMouseListener(new AcideMenuBarMouseClickListener());
				_insertedMenus.get(ob.getName()).setListeners();
			}else{
				AcideInsertedItem aux = _insertedItems.get(ob.getName());
				aux.addActionListener((new AcideInsertedItemListener(aux)));
			}
		}
	}

	/**
	 * Returns the ACIDE - A Configurable IDE menu menu new menu menu item.
	 * 
	 * @return the ACIDE - A Configurable IDE menu menu new menu menu item.
	 */
	public JMenuItem getNewMenuMenuItem() {
		return _newMenuMenuItem;
	}

	/**
	 * Returns the ACIDE - A Configurable IDE menu menu load menu menu item.
	 * 
	 * @return the ACIDE - A Configurable IDE menu menu load menu menu item.
	 */
	public JMenuItem getLoadMenuMenuItem() {
		return _loadMenuMenuItem;
	}

	/**
	 * Returns the ACIDE - A Configurable IDE menu menu modify menu menu item.
	 * 
	 * @return the ACIDE - A Configurable IDE menu menu modify menu menu item.
	 */
	public JMenuItem getModifyMenuMenuItem() {
		return _modifyMenuMenuItem;
	}

	/**
	 * Returns the ACIDE - A Configurable IDE menu menu save menu menu item.
	 * 
	 * @return the ACIDE - A Configurable IDE menu menu save menu menu item.
	 */
	public JMenuItem getSaveMenuMenuItem() {
		return _saveMenuMenuItem;
	}

	/**
	 * Returns the ACIDE - A Configurable IDE menu menu save menu as menu item
	 * 
	 * @return the ACIDE - A Configurable IDE menu menu save menu as menu item
	 */
	public JMenuItem getSaveMenuAsMenuItem() {
		return _saveMenuAsMenuItem;
	}
}
