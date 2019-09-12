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
package acide.gui.menuBar.configurationMenu.databasePanelMenu;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import javax.swing.ImageIcon;
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
import acide.gui.menuBar.configurationMenu.databasePanelMenu.showDetails.AcideShowDetailsMenu;
import acide.gui.menuBar.listeners.AcideMenuBarMouseClickListener;
import acide.language.AcideLanguageManager;
import acide.log.AcideLog;
import acide.resources.AcideResourceManager;
import acide.utils.IconsUtils;

/**
 * ACIDE - A Configurable IDE database panel menu.
 * 
 * @version 0.13
 * @see JMenu
 */
public class AcideDatabasePanelMenu extends JMenu{

	/**
	 * ACIDE - A Configurable IDE database panel menu class serial version UID.
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * ACIDE - A Configurable IDE database menu name.
	 */
	public final static String DATABASE_MENU_NAME = "Database Panel";
	/**
	 * ACIDE - A Configurable IDE database panel menu Des Panel menu item name.
	 */
	public static final String DES_PANEL_NAME = "Des Panel";
	/**
	 * ACIDE - A Configurable IDE database panel menu Odbc Panel menu item name.
	 */
	public static final String ODBC_PANEL_NAME = "Odbc Panel";
	/**
	 * ACIDE - A Configurable IDE database panel menu Odbc Panel menu item name.
	 */
	public static final String SHOW_DETAILS_NAME = "Show Details";
	/**
	 * ACIDE - A Configurable IDE console menu des panel menu item.
	 */
	private JCheckBoxMenuItem _desPanelMenuItem;
	/**
	 * ACIDE - A Configurable IDE database menu des panel menu item has been inserted.
	 */
	private boolean _desPanelInserted;
	/**
	 * ACIDE - A Configurable IDE console menu odbc panel menu item.
	 */
	private JCheckBoxMenuItem _odbcPanelMenuItem;	
	/**
	 * ACIDE - A Configurable IDE database menu odbc panel menu item has been inserted.
	 */
	private boolean _odbcPanelInserted;
	/**
	 * ACIDE - A Configurable IDE database menu show details menu item.
	 */
	private AcideShowDetailsMenu _showDetailsMenu;
	/**
	 * ACIDE - A Configurable IDE database menu show details menu item has been inserted.
	 */
	private boolean _showDetailsInserted;
	/**
	 * ACIDE - A Configurable IDE database menu configuration menu.
	 */
	private AcideMenuSubmenuConfiguration _databasePanelSubmenuConfiguration;
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
	 * Creates a new ACIDE - A Configurable IDE console menu.
	 */
	public AcideDatabasePanelMenu() {
		
		_desPanelInserted = false;
		_odbcPanelInserted = false;
		_showDetailsInserted = false;
		
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
	 * Adds the components to the ACIDE - A Configurable IDE database panel menu.
	 */
	private void addComponents() {
		
		Iterator<Object> it = AcideMenuItemsConfiguration.getInstance()
				.getMenuItemsManager().getSubmenu(AcideConfigurationMenu.CONFIGURATION_MENU_NAME)
				.getItemsManager().getSubmenu(DATABASE_MENU_NAME).getItemsManager().managerIterator();
		while (it.hasNext()){
			AcideMenuObjectConfiguration ob = (AcideMenuObjectConfiguration) it.next();
			String name = ob.getName();
			if (name.equals(DES_PANEL_NAME)){
				// Adds the des panel menu item
				add(_desPanelMenuItem);
				_desPanelInserted = true;
			}else if (name.equals(ODBC_PANEL_NAME)){
				// Adds the odbc panel menu item
				add(_odbcPanelMenuItem);
				_odbcPanelInserted = true;
			}else if (name.equals(SHOW_DETAILS_NAME)){
				// Adds the show details menu item
				add(_showDetailsMenu);
				_showDetailsInserted = true;
			}else {
				if (ob.isSubmenu()){
					add(_insertedMenus.get(ob.getName()));
				}else{
					add(_insertedItems.get(ob.getName()));
				}
			}
		}

		if (!_desPanelInserted)
			add(_desPanelMenuItem);
		if (!_odbcPanelInserted)
			add(_odbcPanelMenuItem);
		if (!_showDetailsInserted)
			add(_showDetailsMenu);
		
		}

	/**
	 * Builds the ACIDE - A Configurable IDE components.
	 */
	private void buildComponents() {
		
		if (!AcideMenuItemsConfiguration.getInstance().getMenuItemsManager()
				.getSubmenu(AcideConfigurationMenu.CONFIGURATION_MENU_NAME).hasSubmenu(DATABASE_MENU_NAME)){
			AcideMenuItemsConfiguration.getInstance().getMenuItemsManager()
				.getSubmenu(AcideConfigurationMenu.CONFIGURATION_MENU_NAME)
					.insertObject(new AcideMenuSubmenuConfiguration(DATABASE_MENU_NAME));
		}
		
		Iterator<Object> it = AcideMenuItemsConfiguration.getInstance()
				.getMenuItemsManager().getSubmenu(AcideConfigurationMenu.CONFIGURATION_MENU_NAME)
				.getItemsManager().getSubmenu(DATABASE_MENU_NAME).getItemsManager().managerIterator();
		
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

		// Creates the des panel menu item
		ImageIcon icon = IconsUtils.getIcon(AcideMenuItemsConfiguration.getInstance()
				.getMenuItemsManager().getSubmenu(AcideConfigurationMenu.CONFIGURATION_MENU_NAME)
				.getSubmenu(DATABASE_MENU_NAME).getItem(DES_PANEL_NAME).getImage());
		
		if (icon != null)
			_desPanelMenuItem = new JCheckBoxMenuItem(icon);
		else
			_desPanelMenuItem = new JCheckBoxMenuItem();

		// Sets the configure menu item name
		_desPanelMenuItem.setName(DES_PANEL_NAME);
		
		_desPanelMenuItem.setSelected(true);

		// Creates the odbc panel menu item
		icon = IconsUtils.getIcon(AcideMenuItemsConfiguration.getInstance()
				.getMenuItemsManager().getSubmenu(AcideConfigurationMenu.CONFIGURATION_MENU_NAME)
				.getSubmenu(DATABASE_MENU_NAME).getItem(ODBC_PANEL_NAME).getImage());
		
		if (icon != null)
			_odbcPanelMenuItem = new JCheckBoxMenuItem(icon);
		else
			_odbcPanelMenuItem = new JCheckBoxMenuItem();

		// Sets the odbc panel menu item name
		_odbcPanelMenuItem.setName(ODBC_PANEL_NAME);
		
		_odbcPanelMenuItem.setSelected(false);
		
		//Creates the show details menu
		_showDetailsMenu = new AcideShowDetailsMenu();
		
		//Sets the show details menu name
		_showDetailsMenu.setName(SHOW_DETAILS_NAME);
	}

	/**
	 * Sets the text of the ACIDE - A Configurable IDE database panel menu components
	 * with the labels in the selected language to display.
	 */
	public void setTextOfMenuComponents() {

		// Sets the des panel menu item text
		_desPanelMenuItem.setText(AcideLanguageManager.getInstance()
				.getLabels().getString("s2160"));

		// Sets the odbc panel menu item text
		_odbcPanelMenuItem.setText(AcideLanguageManager.getInstance()
				.getLabels().getString("s2161"));
		
		// Sets the show details menu text
		_showDetailsMenu.setText(AcideLanguageManager.getInstance()
				.getLabels().getString("s2271"));
		
		// Sets the show details menu items text
		_showDetailsMenu.setTextOfMenuComponents();
		
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

		AcideMenuItemConfiguration desConfiguration;
		AcideMenuItemConfiguration odbcConfiguration;

		_databasePanelSubmenuConfiguration = AcideMenuItemsConfiguration.getInstance()
					.getSubmenu(AcideConfigurationMenu.CONFIGURATION_MENU_NAME).getSubmenu(DATABASE_MENU_NAME);
		
		// Sets the des panel menu item to visible or not visible
		desConfiguration = _databasePanelSubmenuConfiguration.getItem(DES_PANEL_NAME);
		_desPanelMenuItem.setVisible(desConfiguration.isVisible());
		
		// Sets the odbc panel menu item to visible or not visible
		odbcConfiguration = _databasePanelSubmenuConfiguration.getItem(ODBC_PANEL_NAME);
		_odbcPanelMenuItem.setVisible(odbcConfiguration.isVisible());
		
		// builds the show details menu
		_showDetailsMenu.updateComponentsVisibility();

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
		_databasePanelSubmenuConfiguration.setVisible(_desPanelMenuItem.isVisible()
				|| _odbcPanelMenuItem.isVisible()
				|| _showDetailsMenu.isVisible());
		_databasePanelSubmenuConfiguration.setErasable(false);
		
						
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
		if (/*!(name.equals(DES_PANEL_NAME))
			&&*/ !(name.equals(ODBC_PANEL_NAME))
			&& !(name.equals(SHOW_DETAILS_NAME))){
			return true;
		}else{
			return false;
		}
	}

	/**
	 * Sets ACIDE - A Configurable IDE database panel menu item listeners.
	 */
	public void setListeners() {

		// Sets the des panel menu item action listener
		_desPanelMenuItem
				//.addActionListener(new AcideDesPanelMenuItemListener());
			.addActionListener(new AcideInsertedItemListener(
				AcideMenuItemsConfiguration.getInstance()
				.getSubmenu(AcideConfigurationMenu.CONFIGURATION_MENU_NAME)
				.getSubmenu(DATABASE_MENU_NAME).getItem(DES_PANEL_NAME)));

		// Sets the external command menu item action listener
		_odbcPanelMenuItem
				//.addActionListener(new AcideOdbcPanelMenuItemListener());
			.addActionListener(new AcideInsertedItemListener(
				AcideMenuItemsConfiguration.getInstance()
				.getSubmenu(AcideConfigurationMenu.CONFIGURATION_MENU_NAME)
				.getSubmenu(DATABASE_MENU_NAME).getItem(ODBC_PANEL_NAME)));
		
		// Sets the show details menu items listeners
		_showDetailsMenu.setListeners();
				
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
	 * Returns the ACIDE - A Configurable IDE database panel menu des panel menu
	 * item.
	 * 
	 * @return the ACIDE - A Configurable IDE database panel menu des panel
	 *         menu. item
	 */
	public JCheckBoxMenuItem getDesPanelMenuItem() {
		return _desPanelMenuItem;
	}

	/**
	 * Returns the ACIDE - A Configurable IDE database panel menu odbc panel menu item.
	 * 
	 * @return the ACIDE - A Configurable IDE database panel menu odbc panel menu item.
	 */
	public JCheckBoxMenuItem getOdbcPanelMenuItem() {
		return _odbcPanelMenuItem;
	}

	/**
	 * Returns the ACIDE - A Configurable IDE database panel menu show details menu.
	 * 
	 * @return the ACIDE - A Configurable IDE database panel menu show details menu.
	 */
	public AcideShowDetailsMenu getShowDetailsMenu() {
		return _showDetailsMenu;
	}
	
	public void setShowDetailsEnabled(boolean b){
		
		_showDetailsMenu.setVisible(b);
	}
}
