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
package acide.gui.menuBar.configurationMenu.databasePanelMenu.showDetails;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import javax.swing.JCheckBoxMenuItem;
import javax.swing.JMenu;

import acide.configuration.menu.AcideInsertedItem;
import acide.configuration.menu.AcideInsertedItemListener;
import acide.configuration.menu.AcideInsertedMenu;
import acide.configuration.menu.AcideMenuConfiguration;
import acide.configuration.menu.AcideMenuItemConfiguration;
import acide.configuration.menu.AcideMenuItemsConfiguration;
import acide.configuration.menu.AcideMenuObjectConfiguration;
import acide.configuration.menu.AcideMenuSubmenuConfiguration;
import acide.gui.menuBar.configurationMenu.AcideConfigurationMenu;
import acide.gui.menuBar.configurationMenu.databasePanelMenu.AcideDatabasePanelMenu;
import acide.gui.menuBar.listeners.AcideMenuBarMouseClickListener;
import acide.language.AcideLanguageManager;
import acide.log.AcideLog;
import acide.resources.AcideResourceManager;
import acide.resources.exception.MissedPropertyException;
import acide.utils.IconsUtils;

/**
 * ACIDE - A Configurable IDE show details menu.
 * 
 * @version 0.14
 */
public class AcideShowDetailsMenu extends JMenu{

	/**
	 * ACIDE - A Configurable IDE database panel menu class serial version UID.
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * ACIDE - A Configurable IDE menu unique class instance.
	 */
	private static AcideShowDetailsMenu _instance;
	/**
	 * ACIDE - A Configurable IDE show details name.
	 */
	public static final String SHOW_DETAILS_NAME = "Show Details";
	/**
	 * ACIDE - A Configurable IDE show name menu item name.
	 */
	public final static String SHOW_NAME_NAME = "Name";
	/**
	 * ACIDE - A Configurable IDE show name fields menu item name.
	 */
	public static final String SHOW_NAME_FIELDS_NAME = "Name Fields";
	/**
	 * ACIDE - A Configurable IDE show name fields types menu item name.
	 */
	public static final String SHOW_NAME_FIELDS_TYPES_NAME = "Name Fields Types";

	/**
	 * ACIDE - A Configurable IDE name menu item.
	 */
	private JCheckBoxMenuItem _nameMenuItem;
	/**
	 * ACIDE - A Configurable IDE name menu item has been inserted.
	 */
	private boolean _nameInserted;
	/**
	 * ACIDE - A Configurable IDE name fields menu item.
	 */
	private JCheckBoxMenuItem _nameFieldsMenuItem;	
	/**
	 * ACIDE - A Configurable IDE name fields menu item has been inserted.
	 */
	private boolean _nameFieldsInserted;
	/**
	 * ACIDE - A Configurable IDE name fields types menu item.
	 */
	private JCheckBoxMenuItem _nameFieldsTypesMenuItem;	
	/**
	 * ACIDE - A Configurable IDE name fields types menu item has been inserted.
	 */
	private boolean _nameFieldsTypesInserted;
	/**
	 * ACIDE - A Configurable IDE database menu configuration menu.
	 */
	private AcideMenuSubmenuConfiguration _showDetailsSubmenuConfiguration;
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
	 * Returns the unique ACIDE - A Configurable IDE main window class instance.
	 * 
	 * @return the unique ACIDE - A Configurable IDE main window class instance.
	 */
	public static AcideShowDetailsMenu getInstance() {
		if (_instance == null)
			_instance = new AcideShowDetailsMenu();
		return _instance;
	}

	/**
	 * Creates a new ACIDE - A Configurable IDE show details menu.
	 */
	public AcideShowDetailsMenu() {
		
		_nameInserted = false;
		_nameFieldsInserted = false;
		_nameFieldsTypesInserted = false;
		
		_insertedItems = new HashMap<String, AcideInsertedItem>();
		
		_insertedMenus = new HashMap<String, AcideInsertedMenu>();
		
		_insertedObjects = new ArrayList<AcideMenuObjectConfiguration>();

		// Builds the menu components
		buildComponents();

		// Adds the components to the menu
		addComponents();

		// Sets the text of the database panel menu components
		setTextOfMenuComponents();
		
		//Sets menu items activation values 
		setActiveItemMenu();
		
	}

	/**
	 * Activates the menu item that was active the last time the ACIDE - A Configurable IDE
	 * run.
	 */
	private void setActiveItemMenu() {
		try {
			String activeItem = AcideResourceManager.getInstance().getProperty("databasePanelMenuConfiguration.showDetails");
					
			if(activeItem.equals("Name"))
				_nameMenuItem.setSelected(true);
			else if(activeItem.equals("NameFields"))
				_nameFieldsMenuItem.setSelected(true);
			else if(activeItem.equals("NameFieldsTypes"))
				_nameFieldsTypesMenuItem.setSelected(true);
		
		} catch (MissedPropertyException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Adds the components to the ACIDE - A Configurable IDE database panel menu.
	 */
	private void addComponents() {
		
		Iterator<Object> it = AcideMenuItemsConfiguration.getInstance()
				.getMenuItemsManager().getSubmenu(AcideConfigurationMenu.CONFIGURATION_MENU_NAME)
				.getSubmenu(AcideDatabasePanelMenu.DATABASE_MENU_NAME)
				.getItemsManager().getSubmenu(SHOW_DETAILS_NAME).getItemsManager().managerIterator();
		while (it.hasNext()){
			AcideMenuObjectConfiguration ob = (AcideMenuObjectConfiguration) it.next();
			String name = ob.getName();
			if (name.equals(SHOW_NAME_NAME)){
				// Adds the name menu item
				add(_nameMenuItem);
				_nameInserted = true;
			}else if (name.equals(SHOW_NAME_FIELDS_NAME)){
				// Adds the name fields menu item
				add(_nameFieldsMenuItem);
				_nameFieldsInserted = true;
			}else if (name.equals(SHOW_NAME_FIELDS_TYPES_NAME)){
				// Adds the name fields types menu item
				add(_nameFieldsTypesMenuItem);
				_nameFieldsTypesInserted = true;
			}else {
				if (ob.isSubmenu()){
					add(_insertedMenus.get(ob.getName()));
				}else{
					add(_insertedItems.get(ob.getName()));
				}
			}
		}

		if (!_nameInserted)
			add(_nameMenuItem);
		if (!_nameFieldsInserted)
			add(_nameFieldsMenuItem);
		if (!_nameFieldsTypesInserted)
			add(_nameFieldsTypesMenuItem);
		
		}

	/**
	 * Builds the ACIDE - A Configurable IDE components.
	 */
	private void buildComponents() {
		
		if (!AcideMenuItemsConfiguration.getInstance().getMenuItemsManager().getSubmenu(AcideConfigurationMenu.CONFIGURATION_MENU_NAME)
				.getSubmenu(AcideDatabasePanelMenu.DATABASE_MENU_NAME).hasSubmenu(SHOW_DETAILS_NAME)){
			AcideMenuItemsConfiguration.getInstance().getMenuItemsManager().getSubmenu(AcideConfigurationMenu.CONFIGURATION_MENU_NAME)
				.getSubmenu(AcideDatabasePanelMenu.DATABASE_MENU_NAME)
					.insertObject(new AcideMenuSubmenuConfiguration(SHOW_DETAILS_NAME));
		}
		
		Iterator<Object> it = AcideMenuItemsConfiguration.getInstance()
				.getMenuItemsManager().getSubmenu(AcideConfigurationMenu.CONFIGURATION_MENU_NAME)
				.getSubmenu(AcideDatabasePanelMenu.DATABASE_MENU_NAME)
				.getItemsManager().getSubmenu(SHOW_DETAILS_NAME).getItemsManager().managerIterator();
		
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
		

		_nameMenuItem = new JCheckBoxMenuItem();

		// Sets the name menu item name
		_nameMenuItem.setName(SHOW_NAME_NAME);
		
		_nameMenuItem.setSelected(false);


		_nameFieldsMenuItem = new JCheckBoxMenuItem();

		// Sets the name fields menu item name
		_nameFieldsMenuItem.setName(SHOW_NAME_FIELDS_NAME);
		
		_nameFieldsMenuItem.setSelected(false);
		
		
		_nameFieldsTypesMenuItem = new JCheckBoxMenuItem();
		
		// Sets the name fields types menu item name
		_nameFieldsTypesMenuItem.setName(SHOW_NAME_FIELDS_TYPES_NAME);
		
		_nameFieldsTypesMenuItem.setSelected(false);
	}

	/**
	 * Sets the text of the ACIDE - A Configurable IDE database panel menu components
	 * with the labels in the selected language to display.
	 */
	public void setTextOfMenuComponents() {

		// Sets the name menu item text
		_nameMenuItem.setText(AcideLanguageManager.getInstance()
				.getLabels().getString("s2272"));

		// Sets the name fields menu item text
		_nameFieldsMenuItem.setText(AcideLanguageManager.getInstance()
				.getLabels().getString("s2273"));
		
		// Sets the name fields types menu item text
		_nameFieldsTypesMenuItem.setText(AcideLanguageManager.getInstance()
				.getLabels().getString("s2274"));
		
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
	 * Updates the ACIDE - A Configurable IDE console menu components visibility
	 * with the menu configuration.
	 */
	public void updateComponentsVisibility() {

		AcideMenuItemConfiguration nameConfiguration;
		AcideMenuItemConfiguration nameFieldsConfiguration;
		AcideMenuItemConfiguration nameFieldsTypesConfiguration;

		_showDetailsSubmenuConfiguration = AcideMenuItemsConfiguration.getInstance()
				.getSubmenu(AcideConfigurationMenu.CONFIGURATION_MENU_NAME)
				.getSubmenu(AcideDatabasePanelMenu.DATABASE_MENU_NAME).getSubmenu(SHOW_DETAILS_NAME);
		
		// Sets the name menu item to visible or not visible
		nameConfiguration = _showDetailsSubmenuConfiguration.getItem(SHOW_NAME_NAME);
		_nameMenuItem.setVisible(nameConfiguration.isVisible());
		
		// Sets the name fields menu item to visible or not visible
		nameFieldsConfiguration = _showDetailsSubmenuConfiguration.getItem(SHOW_NAME_FIELDS_NAME);
		_nameFieldsMenuItem.setVisible(nameFieldsConfiguration.isVisible());
		
		// Sets the name fields types menu item to visible or not visible
		nameFieldsTypesConfiguration = _showDetailsSubmenuConfiguration.getItem(SHOW_NAME_FIELDS_TYPES_NAME);
		_nameFieldsTypesMenuItem.setVisible(nameFieldsTypesConfiguration.isVisible());

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
		
		// Sets the console menu to visible or not visible
		_showDetailsSubmenuConfiguration.setVisible(_nameMenuItem.isVisible()
				|| _nameFieldsMenuItem.isVisible()
				|| _nameFieldsTypesMenuItem.isVisible());
		_showDetailsSubmenuConfiguration.setErasable(false);
		
						
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
		if (!(name.equals(SHOW_NAME_NAME))
			&& !(name.equals(SHOW_NAME_FIELDS_NAME))
			&& !(name.equals(SHOW_NAME_FIELDS_TYPES_NAME))){
			return true;
		}else{
			return false;
		}
	}

	/**
	 * Sets ACIDE - A Configurable IDE database panel menu item listeners.
	 */
	public void setListeners() {

		// Sets the name menu item action listener
		_nameMenuItem
			.addActionListener(new AcideInsertedItemListener(
				AcideMenuItemsConfiguration.getInstance()
				.getSubmenu(AcideConfigurationMenu.CONFIGURATION_MENU_NAME)
				.getSubmenu(AcideDatabasePanelMenu.DATABASE_MENU_NAME)
				.getSubmenu(SHOW_DETAILS_NAME).getItem(SHOW_NAME_NAME)));

		// Sets the name fields menu item action listener
		_nameFieldsMenuItem
		.addActionListener(new AcideInsertedItemListener(
			AcideMenuItemsConfiguration.getInstance()
			.getSubmenu(AcideConfigurationMenu.CONFIGURATION_MENU_NAME)
			.getSubmenu(AcideDatabasePanelMenu.DATABASE_MENU_NAME)
			.getSubmenu(SHOW_DETAILS_NAME).getItem(SHOW_NAME_FIELDS_NAME)));
		
		// Sets the name fields menu item action listener
		_nameFieldsTypesMenuItem
		.addActionListener(new AcideInsertedItemListener(
			AcideMenuItemsConfiguration.getInstance()
			.getSubmenu(AcideConfigurationMenu.CONFIGURATION_MENU_NAME)
			.getSubmenu(AcideDatabasePanelMenu.DATABASE_MENU_NAME)
			.getSubmenu(SHOW_DETAILS_NAME).getItem(SHOW_NAME_FIELDS_TYPES_NAME)));
				
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
	 * Gets the Name checkbox menu item
	 * @return nameMenuItem
	 */
	public JCheckBoxMenuItem getNameMenuItem() {
		return _nameMenuItem;
	}

	/**
	 * Gets the Name Fields checkbox menu item
	 * @return nameFieldsMenuItem
	 */
	public JCheckBoxMenuItem getNameFieldsMenuItem() {
		return _nameFieldsMenuItem;
	}

	/**
	 * Gets the Name Fields Types checkbox menu item
	 * @return nameFieldsTypesMenuItem
	 */
	public JCheckBoxMenuItem getNameFieldsTypesMenuItem() {
		return _nameFieldsTypesMenuItem;
	}

}
