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
 *      -Version from 0.12 to 0.16
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
package acide.gui.menuBar.configurationMenu.toolBarMenu;

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
import acide.gui.menuBar.listeners.AcideMenuBarMouseClickListener;
import acide.language.AcideLanguageManager;
import acide.log.AcideLog;
import acide.resources.AcideResourceManager;
import acide.utils.IconsUtils;

/**
 * ACIDE - A Configurable IDE tool bar menu.
 * 
 * @version 0.11
 * @see JMenu
 */
public class AcideToolBarMenu extends JMenu {

	/**
	 * ACIDE - A Configurable IDE tool bar menu tool bar menu class serial
	 * version UID.
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * ACIDE - A Configurable IDE toolbar menu name.
	 */
	public final static String TOOLBAR_MENU_NAME = "Tool Bar";
	/**
	 * ACIDE - A Configurable IDE tool bar menu new tool bar menu item name.
	 */
	public static final String NEW_TOOLBAR_NAME = "New Toolbar";
	/**
	 * ACIDE - A Configurable IDE tool bar menu load tool bar menu item name.
	 */
	public static final String LOAD_TOOLBAR_NAME = "Load Toolbar";
	/**
	 * ACIDE - A Configurable IDE tool bar menu modify tool bar menu item name.
	 */
	public static final String MODIFY_TOOLBAR_NAME = "Modify Toolbar";
	/**
	 * ACIDE - A Configurable IDE tool bar menu save tool bar menu item name.
	 */
	public static final String SAVE_TOOLBAR_NAME = "Save Toolbar";
	/**
	 * ACIDE - A Configurable IDE tool bar menu save tool bar as menu item name.
	 */
	public static final String SAVE_TOOLBAR_AS_NAME = "Save Toolbar As";
	/**
	 * ACIDE - A Configurable IDE tool bar menu new tool bar menu item.
	 */
	private JMenuItem _newToolBarMenuItem;
	/**
	 * ACIDE - A Configurable IDE toolbar menu new toolbar menu item has been inserted.
	 */
	private boolean _newToolbarInserted;
	/**
	 * ACIDE - A Configurable IDE tool bar menu load tool bar menu item.
	 */
	private JMenuItem _loadToolBarMenuItem;
	/**
	 * ACIDE - A Configurable IDE toolbar menu load toolbar menu item has been inserted.
	 */
	private boolean _loadToolbarInserted;
	/**
	 * ACIDE - A Configurable IDE tool bar menu modify tool bar menu item.
	 */
	private JMenuItem _modifyToolBarMenuItem;
	/**
	 * ACIDE - A Configurable IDE toolbar menu modify toolbar menu item has been inserted.
	 */
	private boolean _modifyToolbarInserted;
	/**
	 * ACIDE - A Configurable IDE tool bar menu save tool bar menu item.
	 */
	private JMenuItem _saveToolBarMenuItem;
	/**
	 * ACIDE - A Configurable IDE toolbar menu save toolbar menu item has been inserted.
	 */
	private boolean _saveToolbarInserted;
	/**
	 * ACIDE - A Configurable IDE tool bar menu save tool bar as menu item.
	 */
	private JMenuItem _saveToolBarAsMenuItem;
	/**
	 * ACIDE - A Configurable IDE toolbar menu save toolbar as menu item has been inserted.
	 */
	private boolean _saveToolbarAsInserted;
	/**
	 * ACIDE - A Configurable IDE toolbar menu configuration menu.
	 */
	private AcideMenuSubmenuConfiguration _toolbarSubmenuConfiguration;
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
	 * Creates a new ACIDE - A Configurable IDE tool bar menu.
	 */
	public AcideToolBarMenu() {
		
		_newToolbarInserted = false;
		_loadToolbarInserted = false;
		_modifyToolbarInserted = false;
		_saveToolbarInserted = false;
		_saveToolbarAsInserted = false;
		
		_insertedItems = new HashMap<String, AcideInsertedItem>();
		
		_insertedMenus = new HashMap<String, AcideInsertedMenu>();
		
		_insertedObjects = new ArrayList<AcideMenuObjectConfiguration>();

		// Builds the menu components
		buildComponents();

		// Adds the components to the menu
		addComponents();

		// Sets the text of the tool bar menu components
		setTextOfMenuComponents();
	}

	/**
	 * Adds the components to the ACIDE - A Configurable IDE tool bar menu.
	 */
	private void addComponents() {
		
		Iterator<Object> it = AcideMenuItemsConfiguration.getInstance()
				.getMenuItemsManager().getSubmenu(AcideConfigurationMenu.CONFIGURATION_MENU_NAME)
				.getItemsManager().getSubmenu(TOOLBAR_MENU_NAME).getItemsManager().managerIterator();
		while (it.hasNext()){
			AcideMenuObjectConfiguration ob = (AcideMenuObjectConfiguration) it.next();
			String name = ob.getName();
			if (name.equals(NEW_TOOLBAR_NAME)){
				// Adds the new tool bar menu item to the menu
				add(_newToolBarMenuItem);
				_newToolbarInserted = true;
			}else if (name.equals(LOAD_TOOLBAR_NAME)){
				// Adds the load tool bar menu item to the menu
				add(_loadToolBarMenuItem);
				_loadToolbarInserted = true;
			}else if (name.equals(MODIFY_TOOLBAR_NAME)){
				// Adds the modify tool bar menu item to the menu
				add(_modifyToolBarMenuItem);
				_modifyToolbarInserted = true;
			}else if (name.equals(SAVE_TOOLBAR_NAME)){
				// Adds the save tool bar menu item to the menu
				add(_saveToolBarMenuItem);
				_saveToolbarInserted = true;
			}else if (name.equals(SAVE_TOOLBAR_AS_NAME)){
				// Adds the save tool bar as menu item to the menu
				add(_saveToolBarAsMenuItem);
				_saveToolbarAsInserted = true;
			}else {
				if (ob.isSubmenu()){
					add(_insertedMenus.get(ob.getName()));
				}else{
					add(_insertedItems.get(ob.getName()));
				}
			}
		}

		if (!_newToolbarInserted)
			add(_newToolBarMenuItem);
		if (!_loadToolbarInserted)
			add(_loadToolBarMenuItem);
		if (!_modifyToolbarInserted)
			add(_modifyToolBarMenuItem);
		if (!_saveToolbarInserted)
			add(_saveToolBarMenuItem);
		if (!_saveToolbarAsInserted)
			add(_saveToolBarAsMenuItem);
	}

	/**
	 * Builds the ACIDE - A Configurable IDE tool bar menu components.
	 */
	private void buildComponents() {
		
		if (!AcideMenuItemsConfiguration.getInstance().getMenuItemsManager()
				.getSubmenu(AcideConfigurationMenu.CONFIGURATION_MENU_NAME).hasSubmenu(TOOLBAR_MENU_NAME)){
			AcideMenuItemsConfiguration.getInstance().getMenuItemsManager()
				.getSubmenu(AcideConfigurationMenu.CONFIGURATION_MENU_NAME)
					.insertObject(new AcideMenuSubmenuConfiguration(TOOLBAR_MENU_NAME));
		}
		
		Iterator<Object> it = AcideMenuItemsConfiguration.getInstance()
				.getMenuItemsManager().getSubmenu(AcideConfigurationMenu.CONFIGURATION_MENU_NAME)
				.getItemsManager().getSubmenu(TOOLBAR_MENU_NAME).getItemsManager().managerIterator();
		
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

		// Creates the new tool bar menu item
		ImageIcon icon = IconsUtils.getIcon(AcideMenuItemsConfiguration.getInstance()
				.getMenuItemsManager().getSubmenu(AcideConfigurationMenu.CONFIGURATION_MENU_NAME)
				.getSubmenu(TOOLBAR_MENU_NAME).getItem(NEW_TOOLBAR_NAME).getImage());
		
		if (icon != null)
			_newToolBarMenuItem = new JMenuItem(icon);
		else
			_newToolBarMenuItem = new JMenuItem();

		// Sets the new tool bar menu item name
		_newToolBarMenuItem.setName(NEW_TOOLBAR_NAME);

		// Creates the load tool bar menu item
		icon = IconsUtils.getIcon(AcideMenuItemsConfiguration.getInstance()
				.getMenuItemsManager().getSubmenu(AcideConfigurationMenu.CONFIGURATION_MENU_NAME)
				.getSubmenu(TOOLBAR_MENU_NAME).getItem(LOAD_TOOLBAR_NAME).getImage());
		
		if (icon != null)
			_loadToolBarMenuItem = new JMenuItem(icon);
		else
			_loadToolBarMenuItem = new JMenuItem();

		// Sets the load tool bar menu item name
		_loadToolBarMenuItem.setName(LOAD_TOOLBAR_NAME);

		// Creates the modify tool bar menu item
		icon = IconsUtils.getIcon(AcideMenuItemsConfiguration.getInstance()
				.getMenuItemsManager().getSubmenu(AcideConfigurationMenu.CONFIGURATION_MENU_NAME)
				.getSubmenu(TOOLBAR_MENU_NAME).getItem(MODIFY_TOOLBAR_NAME).getImage());
		
		if (icon != null)
			_modifyToolBarMenuItem = new JMenuItem(icon);
		else
			_modifyToolBarMenuItem = new JMenuItem();

		// Sets the modify tool bar menu item name
		_modifyToolBarMenuItem.setName(MODIFY_TOOLBAR_NAME);

		// Creates the save tool bar menu item
		icon = IconsUtils.getIcon(AcideMenuItemsConfiguration.getInstance()
				.getMenuItemsManager().getSubmenu(AcideConfigurationMenu.CONFIGURATION_MENU_NAME)
				.getSubmenu(TOOLBAR_MENU_NAME).getItem(SAVE_TOOLBAR_NAME).getImage());
	
		if (icon != null)
			_saveToolBarMenuItem = new JMenuItem(icon);
		else
			_saveToolBarMenuItem = new JMenuItem();

		// Sets the save tool bar menu item name
		_saveToolBarMenuItem.setName(SAVE_TOOLBAR_NAME);

		// Disables the save tool bar menu item
		_saveToolBarMenuItem.setEnabled(false);

		// Creates the save tool bar as menu item
		icon = IconsUtils.getIcon(AcideMenuItemsConfiguration.getInstance()
				.getMenuItemsManager().getSubmenu(AcideConfigurationMenu.CONFIGURATION_MENU_NAME)
				.getSubmenu(TOOLBAR_MENU_NAME).getItem(SAVE_TOOLBAR_AS_NAME).getImage());
		
		if (icon != null)
			_saveToolBarAsMenuItem = new JMenuItem(icon);
		else
			_saveToolBarAsMenuItem = new JMenuItem();

		// Sets the save tool bar as menu item name
		_saveToolBarAsMenuItem.setName(SAVE_TOOLBAR_AS_NAME);
	}

	/**
	 * Sets the text of the ACIDE - A Configurable IDE tool bar menu components
	 * with the labels in the selected language to display.
	 */
	public void setTextOfMenuComponents() {

		// Sets the new tool bar menu item text
		_newToolBarMenuItem.setText(AcideLanguageManager.getInstance()
				.getLabels().getString("s280"));

		// Sets the load tool bar menu item text
		_loadToolBarMenuItem.setText(AcideLanguageManager.getInstance()
				.getLabels().getString("s281"));

		// Sets the modify tool bar menu item text
		_modifyToolBarMenuItem.setText(AcideLanguageManager.getInstance()
				.getLabels().getString("s282"));

		// Sets the save tool bar menu item text
		_saveToolBarMenuItem.setText(AcideLanguageManager.getInstance()
				.getLabels().getString("s283"));

		// Sets the save tool bar as menu item text
		_saveToolBarAsMenuItem.setText(AcideLanguageManager.getInstance()
				.getLabels().getString("s284"));
		
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
	 * Gets if the menu name given as parameter is original
	 * @param name
	 * 		the name we want to check
	 * @return
	 * 		if the name given as parameter is original
	 */
	public boolean isOriginal(String name){
		if (!(name.equals(NEW_TOOLBAR_NAME))
			&& !(name.equals(LOAD_TOOLBAR_NAME))
			&& !(name.equals(MODIFY_TOOLBAR_NAME))
			&& !(name.equals(SAVE_TOOLBAR_NAME))
			&& !(name.equals(SAVE_TOOLBAR_AS_NAME))){
			return true;
		}else {
			return false;
		}
	}

	/**
	 * Sets the ACIDE - A Configurable IDE tool bar menu menu item listeners.
	 */
	public void setListeners() {

		// Sets the new tool bar menu item action listener
		_newToolBarMenuItem
				//.addActionListener(new AcideNewToolBarMenuItemListener());
			.addActionListener(new AcideInsertedItemListener(
				AcideMenuItemsConfiguration.getInstance()
				.getSubmenu(AcideConfigurationMenu.CONFIGURATION_MENU_NAME)
				.getSubmenu(TOOLBAR_MENU_NAME).getItem(NEW_TOOLBAR_NAME)));

		// Sets the load tool bar menu item action listener
		_loadToolBarMenuItem
				//.addActionListener(new AcideLoadToolBarMenuItemListener());
			.addActionListener(new AcideInsertedItemListener(
				AcideMenuItemsConfiguration.getInstance()
				.getSubmenu(AcideConfigurationMenu.CONFIGURATION_MENU_NAME)
				.getSubmenu(TOOLBAR_MENU_NAME).getItem(LOAD_TOOLBAR_NAME)));

		// Sets the modify tool bar menu item action listener
		_modifyToolBarMenuItem
				//.addActionListener(new AcideModifyToolBarMenuItemListener());
			.addActionListener(new AcideInsertedItemListener(
				AcideMenuItemsConfiguration.getInstance()
				.getSubmenu(AcideConfigurationMenu.CONFIGURATION_MENU_NAME)
				.getSubmenu(TOOLBAR_MENU_NAME).getItem(MODIFY_TOOLBAR_NAME)));

		// Sets the save tool bar menu item action listener
		_saveToolBarMenuItem
				//.addActionListener(new AcideSaveToolBarMenuItemListener());
			.addActionListener(new AcideInsertedItemListener(
				AcideMenuItemsConfiguration.getInstance()
				.getSubmenu(AcideConfigurationMenu.CONFIGURATION_MENU_NAME)
				.getSubmenu(TOOLBAR_MENU_NAME).getItem(SAVE_TOOLBAR_NAME)));

		// Sets the save tool bar as menu item action listener
		_saveToolBarAsMenuItem
				//.addActionListener(new AcideToolBaAsMenuItemListener());
			.addActionListener(new AcideInsertedItemListener(
				AcideMenuItemsConfiguration.getInstance()
				.getSubmenu(AcideConfigurationMenu.CONFIGURATION_MENU_NAME)
				.getSubmenu(TOOLBAR_MENU_NAME).getItem(SAVE_TOOLBAR_AS_NAME)));
		
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
	 * Updates the ACIDE - A Configurable IDE tool bar menu components
	 * visibility with the menu configuration.
	 */
	public void updateComponentsVisibility() {

		AcideMenuItemConfiguration newToolbarConfiguration;
		AcideMenuItemConfiguration loadToolbarConfiguration;
		AcideMenuItemConfiguration modifyToolbarConfiguration;
		AcideMenuItemConfiguration saveToolbarConfiguration;
		AcideMenuItemConfiguration saveToolbarAsConfiguration;

		_toolbarSubmenuConfiguration = AcideMenuItemsConfiguration.getInstance()
					.getSubmenu(AcideConfigurationMenu.CONFIGURATION_MENU_NAME).getSubmenu(TOOLBAR_MENU_NAME);
		
		// Sets the new tool bar menu item to visible or not visible
		newToolbarConfiguration = _toolbarSubmenuConfiguration.getItem(NEW_TOOLBAR_NAME);
		_newToolBarMenuItem.setVisible(newToolbarConfiguration.isVisible());
			
		// Sets the load tool bar menu item to visible or not visible
		loadToolbarConfiguration = _toolbarSubmenuConfiguration.getItem(LOAD_TOOLBAR_NAME);
		_loadToolBarMenuItem.setVisible(loadToolbarConfiguration.isVisible());
			
		// Sets the modify tool bar menu item to visible or not visible
		modifyToolbarConfiguration = _toolbarSubmenuConfiguration.getItem(MODIFY_TOOLBAR_NAME);
		_modifyToolBarMenuItem.setVisible(modifyToolbarConfiguration.isVisible());

		// Sets the save tool bar menu item to visible or not visible
		saveToolbarConfiguration = _toolbarSubmenuConfiguration.getItem(SAVE_TOOLBAR_NAME);
		_saveToolBarMenuItem.setVisible(saveToolbarConfiguration.isVisible());

		// Sets the save as tool bar menu item to visible or not visible
		saveToolbarAsConfiguration = _toolbarSubmenuConfiguration.getItem(SAVE_TOOLBAR_AS_NAME);
		_saveToolBarAsMenuItem.setVisible(saveToolbarAsConfiguration.isVisible());
			

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
		
		// Sets the tool bar menu to visible or not visible
		_toolbarSubmenuConfiguration.setVisible(_newToolBarMenuItem.isVisible()
				|| _loadToolBarMenuItem.isVisible()
				|| _modifyToolBarMenuItem.isVisible()
				|| _saveToolBarMenuItem.isVisible()
				|| _saveToolBarAsMenuItem.isVisible());
		
		_toolbarSubmenuConfiguration.setErasable(false);
		
		
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
	 * Returns the ACIDE - A Configurable IDE tool bar menu new tool bar menu
	 * item.
	 * 
	 * @return the ACIDE - A Configurable IDE tool bar menu new tool bar menu
	 *         item.
	 */
	public JMenuItem getNewToolBarMenuItem() {
		return _newToolBarMenuItem;
	}

	/**
	 * Returns the ACIDE - A Configurable IDE tool bar menu load tool bar menu
	 * item.
	 * 
	 * @return the ACIDE - A Configurable IDE tool bar menu load tool bar menu
	 *         item.
	 */
	public JMenuItem getLoadToolBarMenuItem() {
		return _loadToolBarMenuItem;
	}

	/**
	 * Returns the ACIDE - A Configurable IDE tool bar menu modify tool bar menu
	 * item.
	 * 
	 * @return the ACIDE - A Configurable IDE tool bar menu modify tool bar menu
	 *         item.
	 */
	public JMenuItem getModifyToolBarMenuItem() {
		return _modifyToolBarMenuItem;
	}

	/**
	 * Returns the ACIDE - A Configurable IDE tool bar menu save tool bar menu
	 * item
	 * 
	 * @return the ACIDE - A Configurable IDE tool bar menu save tool bar menu
	 *         item
	 */
	public JMenuItem getSaveToolBarMenuItem() {
		return _saveToolBarMenuItem;
	}

	/**
	 * Returns the ACIDE - A Configurable IDE tool bar menu save tool bar as
	 * menu item.
	 * 
	 * @return the ACIDE - A Configurable IDE tool bar menu save tool bar as
	 *         menu item.
	 */
	public JMenuItem getSaveToolBarAsMenuItem() {
		return _saveToolBarAsMenuItem;
	}
}