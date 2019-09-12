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
package acide.gui.menuBar.viewMenu;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import javax.swing.ImageIcon;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;

import acide.configuration.menu.AcideInsertedItem;
import acide.configuration.menu.AcideInsertedItemListener;
import acide.configuration.menu.AcideInsertedMenu;
import acide.configuration.menu.AcideMenuConfiguration;
import acide.configuration.menu.AcideMenuItemConfiguration;
import acide.configuration.menu.AcideMenuItemsConfiguration;
import acide.configuration.menu.AcideMenuObjectConfiguration;
import acide.configuration.menu.AcideMenuSubmenuConfiguration;
import acide.gui.menuBar.listeners.AcideMenuBarMouseClickListener;
import acide.gui.menuBar.viewMenu.listeners.AcideShowAcideDataBasePanelMenuItemListener;
import acide.language.AcideLanguageManager;
import acide.log.AcideLog;
import acide.resources.AcideResourceManager;
import acide.utils.IconsUtils;

/**
 * ACIDE - A Configurable IDE view menu.
 * 
 * @version 0.11
 */
public class AcideViewMenu extends JMenu {

	/**
	 * ACIDE - A Configurable IDE view menu class serial version UID.
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * ACIDE - A Configurable IDE view menu name.
	 */
	public final static String VIEW_MENU_NAME = "View";
	/**
	 * ACIDE - A Configurable IDE view menu show log tab menu item name.
	 */
	public static final String SHOW_LOG_TAB_NAME = "Show Log Tab";
	/**
	 * ACIDE - A Configurable IDE view menu show explorer panel menu item name.
	 */
	public static final String SHOW_EXPLORER_PANEL_NAME = "Show Explorer Panel";
	/**
	 * ACIDE - A Configurable IDE view menu show console panel menu item name.
	 */
	public static final String SHOW_CONSOLE_PANEL_NAME = "Show Console Panel";
	/**
	 * ACIDE - A Configurable IDE view menu show data base panel menu item name.
	 */
	public static final String SHOW_DATA_BASE_PANEL_NAME = "Show Data Base Panel";
		/**
	 * ACIDE - A Configurable IDE view menu show data base panel menu item name.
	 */
	public static final String REFRESH_DATA_BASE_PANEL_NAME = "Refresh Data Base Panel";
	/**
	 * ACIDE - A Configurable IDE view menu graph panel menu item name.
	 */
	public static final String SHOW_GRAPH_PANEL_NAME = "Show Graph Panel";
	/**
	 * ACIDE - A Configurable IDE view menu debug panel menu item name.
	 */
	public static final String SHOW_DEBUG_PANEL_NAME = "Show Debug Panel";
	/**
	 * ACIDE - A Configurable IDE view menu asserted database panel menu item name.
	 */
	public static final String SHOW_ASSERTED_DATABASE_PANEL_NAME = "Show Asserted Database Panel";
	/**
	 * ACIDE - A Configurable IDE view menu show log tab menu item.
	 */
	private JMenuItem _showLogTabMenuItem;
	/**
	 * ACIDE - A Configurable IDE view menu show log menu item has been inserted.
	 */
	private boolean _showLogInserted;
	/**
	 * ACIDE - A Configurable IDE view menu show explorer panel check box menu
	 * item.
	 */
	private JCheckBoxMenuItem _showExplorerPanelCheckBoxMenuItem;
	/**
	 * ACIDE - A Configurable IDE view menu show explorar menu item has been inserted.
	 */
	private boolean _showExplorerPanelInserted;
	/**
	 * ACIDE - A Configurable IDE view menu show console panel check box menu.
	 * item.
	 */
	private JCheckBoxMenuItem _showConsolePanelCheckBoxMenuItem;
	/**
	 * ACIDE - A Configurable IDE view menu show console panel menu item has been inserted.
	 */
	private boolean _showConsolePanelInserted;
	/**
	 * ACIDE - A Configurable IDE view menu show data base panel check box menu.
	 * item.
	 */
	private JCheckBoxMenuItem _showDataBaseCheckBoxMenuItem;
	/**
	 * ACIDE - A Configurable IDE view menu show database menu item has been inserted.
	 */
	private boolean _showDatabaseInserted;
	/** 
	 * ACIDE - A Configurable IDE view menu show graph panel check box menu.
	 */
	private JCheckBoxMenuItem _showGraphPanelCheckBoxMenuItem;
	/**
	 * ACIDE - A Configurable IDE view menu show graph panel menu item has been inserted.
	 */
	private boolean _showGraphPanelInserted;
	/**
	 * ACIDE - A Configurable IDE view menu show graph panel check box menu.
	 */
	private JCheckBoxMenuItem _showDebugPanelCheckBoxMenuItem;
	/**
	 * ACIDE - A Configurable IDE view menu show graph panel menu item has been inserted.
	 */
	private boolean _showDebugPanelInserted;
	/**
	 * ACIDE - A Configurable IDE view menu show graph panel check box menu.
	 */
	private JCheckBoxMenuItem _showAssertedDatabasePanelCheckBoxMenuItem;
	/**
	 * ACIDE - A Configurable IDE view menu show graph panel menu item has been inserted.
	 */
	private boolean _showAssertedDatabasePanelInserted;
	/**
	 * ACIDE - A Configurable IDE view menu configuration menu.
	 */
	private AcideMenuSubmenuConfiguration _viewSubmenuConfiguration;
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
	 * Creates a new ACIDE - A Configurable IDE view menu.
	 */
	public AcideViewMenu() {
		
		_showLogInserted = false;
		_showExplorerPanelInserted = false;
		_showConsolePanelInserted = false;
		_showDatabaseInserted = false;
		_showGraphPanelInserted = false;
		_showDebugPanelInserted = false;
		_showAssertedDatabasePanelInserted = false;
		
		_insertedItems = new HashMap<String, AcideInsertedItem>();
		
		_insertedMenus = new HashMap<String, AcideInsertedMenu>();
		
		_insertedObjects = new ArrayList<AcideMenuObjectConfiguration>();

		// Builds the menu components
		buildComponents();

		// Adds the components to the menu
		addComponents();

		// Sets the text of the view menu components
		setTextOfMenuComponents();
	}

	/**
	 * Adds the components to the ACIDE - A Configurable IDE view menu.
	 */
	private void addComponents() {
		
		Iterator<Object> it = AcideMenuItemsConfiguration.getInstance()
				.getMenuItemsManager().getSubmenu(VIEW_MENU_NAME).getItemsManager().managerIterator();
		while (it.hasNext()){
			AcideMenuObjectConfiguration ob = (AcideMenuObjectConfiguration) it.next();
			String name = ob.getName();
			if (name.equals(SHOW_LOG_TAB_NAME)){
				// Adds the show log tab menu item to the menu
				add(_showLogTabMenuItem);
				_showLogInserted = true;
			} else if (name.equals(SHOW_EXPLORER_PANEL_NAME)){
				// Adds the show explorer panel check box menu item to the menu
				add(_showExplorerPanelCheckBoxMenuItem);
				_showExplorerPanelInserted = true;
			} else if (name.equals(SHOW_CONSOLE_PANEL_NAME)){
				// Adds the show console panel check box menu item to the menu
				add(_showConsolePanelCheckBoxMenuItem);
				_showConsolePanelInserted = true;
			} else if (name.equals(SHOW_DATA_BASE_PANEL_NAME)){
				//Adds the show data base panel check box menu item to the menu
				add(_showDataBaseCheckBoxMenuItem);
				_showDatabaseInserted = true;
			} else if (name.equals(SHOW_GRAPH_PANEL_NAME)) {
				//Adds the show graph panel check box menu item to the menu
				add(_showGraphPanelCheckBoxMenuItem);
				_showGraphPanelInserted = true;
			} else if (name.equals(SHOW_DEBUG_PANEL_NAME)) {
				//Adds the show graph panel check box menu item to the menu
				add(_showDebugPanelCheckBoxMenuItem);
				_showDebugPanelInserted = true;
			} else if (name.equals(SHOW_ASSERTED_DATABASE_PANEL_NAME)) { 
				//Adds the show asserted database panel check box menu item to the menu
				add(_showAssertedDatabasePanelCheckBoxMenuItem);
				_showAssertedDatabasePanelInserted = true;
			} else {
				if (ob.isSubmenu()){
					add(_insertedMenus.get(ob.getName()));
				}else{
					add(_insertedItems.get(ob.getName()));
				}
			}
		}
		
		if (!_showLogInserted)
			add(_showLogTabMenuItem);
		if (!_showExplorerPanelInserted)
			add(_showExplorerPanelCheckBoxMenuItem);
		if (!_showConsolePanelInserted)
			add(_showConsolePanelCheckBoxMenuItem);
		if (!_showDatabaseInserted)
			add(_showDataBaseCheckBoxMenuItem);
		if (!_showGraphPanelInserted)
			add(_showGraphPanelCheckBoxMenuItem);
		if (!_showDebugPanelInserted)
			add(_showDebugPanelCheckBoxMenuItem);
		if (!_showAssertedDatabasePanelInserted)
			add(_showAssertedDatabasePanelCheckBoxMenuItem);
		
		
					
	}

	/**
	 * Builds the ACIDE - A Configurable IDE view menu components.
	 */
	private void buildComponents() {
		
		if (!AcideMenuItemsConfiguration.getInstance().getMenuItemsManager().hasSubmenu(VIEW_MENU_NAME)){
			AcideMenuItemsConfiguration.getInstance()
				.insertObject(new AcideMenuSubmenuConfiguration(VIEW_MENU_NAME));
		}
		
		Iterator<Object> it = AcideMenuItemsConfiguration.getInstance()
				.getMenuItemsManager().getSubmenu(VIEW_MENU_NAME).getItemsManager().managerIterator();
		
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


		// Creates the show log tab menu item
		ImageIcon icon = IconsUtils.getIcon(AcideMenuItemsConfiguration.getInstance()
				.getMenuItemsManager().getSubmenu(VIEW_MENU_NAME)
				.getItem(SHOW_LOG_TAB_NAME).getImage());
		
		if (icon != null)
			_showLogTabMenuItem = new JMenuItem(icon);
		else
			_showLogTabMenuItem = new JMenuItem();

		// Sets the show log tab menu item name
		_showLogTabMenuItem.setName(SHOW_LOG_TAB_NAME);

		// Creates the show explorer panel check box menu item
		icon = IconsUtils.getIcon(AcideMenuItemsConfiguration.getInstance()
				.getMenuItemsManager().getSubmenu(VIEW_MENU_NAME)
				.getItem(SHOW_EXPLORER_PANEL_NAME).getImage());
		
		if (icon != null)
			_showExplorerPanelCheckBoxMenuItem = new JCheckBoxMenuItem(icon);
		else
			_showExplorerPanelCheckBoxMenuItem = new JCheckBoxMenuItem();

		// Sets the show explorer panel check box menu item name
		_showExplorerPanelCheckBoxMenuItem.setName(SHOW_EXPLORER_PANEL_NAME);

		// Sets the show explorer panel check box menu item as not selected
		_showExplorerPanelCheckBoxMenuItem.setSelected(true);

		// Creates the show console panel check box menu item
		icon = IconsUtils.getIcon(AcideMenuItemsConfiguration.getInstance()
				.getMenuItemsManager().getSubmenu(VIEW_MENU_NAME)
				.getItem(SHOW_CONSOLE_PANEL_NAME).getImage());
		
		if (icon != null)
			_showConsolePanelCheckBoxMenuItem = new JCheckBoxMenuItem(icon);
		else
			_showConsolePanelCheckBoxMenuItem = new JCheckBoxMenuItem();
		
		// Sets the show console panel check box menu item name
		_showConsolePanelCheckBoxMenuItem.setName(SHOW_CONSOLE_PANEL_NAME);

		// Sets the show console panel check box menu item as not selected
		_showConsolePanelCheckBoxMenuItem.setSelected(true);
		
		// Creates the show data base panel check box menu item
		icon = IconsUtils.getIcon(AcideMenuItemsConfiguration.getInstance()
				.getMenuItemsManager().getSubmenu(VIEW_MENU_NAME)
				.getItem(SHOW_DATA_BASE_PANEL_NAME).getImage());
		
		if (icon != null)
			_showDataBaseCheckBoxMenuItem = new JCheckBoxMenuItem(icon);
		else
			_showDataBaseCheckBoxMenuItem = new JCheckBoxMenuItem();
		
		// Sets the show data base panel check box menu item name
		_showDataBaseCheckBoxMenuItem.setName(SHOW_DATA_BASE_PANEL_NAME);

		// Sets the show data base panel check box menu item as not selected
		_showDataBaseCheckBoxMenuItem.setSelected(false);
		
		// Creates the show graph panel check menu item		
		icon = IconsUtils.getIcon(AcideMenuItemsConfiguration.getInstance()
				.getMenuItemsManager().getSubmenu(VIEW_MENU_NAME)
				.getItem(SHOW_GRAPH_PANEL_NAME).getImage());
		
		if (icon != null)
			_showGraphPanelCheckBoxMenuItem = new JCheckBoxMenuItem(icon);
		else
			_showGraphPanelCheckBoxMenuItem = new JCheckBoxMenuItem();

		// Sets the show graph panel check box menu item name
		_showGraphPanelCheckBoxMenuItem.setName(SHOW_GRAPH_PANEL_NAME);

		//Sets the show graph panel check box menu item as (not) selected
		_showGraphPanelCheckBoxMenuItem.setSelected(true);
		
		// Creates the show debug panel check menu item		
		icon = IconsUtils.getIcon(AcideMenuItemsConfiguration.getInstance()
				.getMenuItemsManager().getSubmenu(VIEW_MENU_NAME)
				.getItem(SHOW_DEBUG_PANEL_NAME).getImage());
		
		if (icon != null)
			_showDebugPanelCheckBoxMenuItem = new JCheckBoxMenuItem(icon);
		else
			_showDebugPanelCheckBoxMenuItem = new JCheckBoxMenuItem();

		// Sets the show debug panel check box menu item name
		_showDebugPanelCheckBoxMenuItem.setName(SHOW_DEBUG_PANEL_NAME);

		//Sets the show debug panel check box menu item as (not) selected
		_showDebugPanelCheckBoxMenuItem.setSelected(true);
		
		// Creates the show debug panel check menu item
		icon = IconsUtils.getIcon(AcideMenuItemsConfiguration.getInstance()
				.getMenuItemsManager().getSubmenu(VIEW_MENU_NAME)
				.getItem(SHOW_ASSERTED_DATABASE_PANEL_NAME).getImage());

		if (icon != null)
			_showAssertedDatabasePanelCheckBoxMenuItem = new JCheckBoxMenuItem(icon);
		else
			_showAssertedDatabasePanelCheckBoxMenuItem = new JCheckBoxMenuItem();

		// Sets the show asserted database panel check box menu item name
		_showAssertedDatabasePanelCheckBoxMenuItem.setName(SHOW_ASSERTED_DATABASE_PANEL_NAME);

		// Sets the show asserted database panel check box menu item as (not) selected
		_showAssertedDatabasePanelCheckBoxMenuItem.setSelected(false);

	}

	/**
	 * Sets the text of the ACIDE - A Configurable IDE view menu components with
	 * the labels in the selected language to display.
	 */
	public void setTextOfMenuComponents() {

		// Sets the show log tab menu item text
		_showLogTabMenuItem.setText(AcideLanguageManager.getInstance()
				.getLabels().getString("s28"));

		// Sets the show log tab menu item accelerator
		_showLogTabMenuItem.setAccelerator(KeyStroke.getKeyStroke(
				KeyEvent.VK_L, ActionEvent.ALT_MASK + ActionEvent.SHIFT_MASK));

		// Sets the show explorer panel check box menu item text
		_showExplorerPanelCheckBoxMenuItem.setText(AcideLanguageManager
				.getInstance().getLabels().getString("s221"));

		// Sets the show console panel check box menu item text
		_showConsolePanelCheckBoxMenuItem.setText(AcideLanguageManager
				.getInstance().getLabels().getString("s223"));
		
		// Sets the show data base panel check box menu item text
		_showDataBaseCheckBoxMenuItem.setText(AcideLanguageManager
				.getInstance().getLabels().getString("s2023"));
		
		//Sets the show graph panel check box menu item text
		_showGraphPanelCheckBoxMenuItem.setText(AcideLanguageManager
				.getInstance().getLabels().getString("s2231"));
		
		//Sets the show debug panel check box menu item text
		_showDebugPanelCheckBoxMenuItem.setText(AcideLanguageManager
				.getInstance().getLabels().getString("s2243"));
		
		//Sets the show debug panel check box menu item text
		_showAssertedDatabasePanelCheckBoxMenuItem.setText(AcideLanguageManager
				.getInstance().getLabels().getString("s2280"));
		
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
	 * Updates the ACIDE - A Configurable IDE view menu components visibiliy
	 * with the menu configuration.
	 */
	public void updateComponentsVisibility() {

		AcideMenuItemConfiguration showLogTabConfiguration;
		AcideMenuItemConfiguration showExplorerPanelConfiguration;
		AcideMenuItemConfiguration showConsolePanelConfiguration;
		AcideMenuItemConfiguration showDatabaseConfiguration;
		AcideMenuItemConfiguration showGraphPanelConfiguration;
		AcideMenuItemConfiguration showDebugPanelConfiguration;
		AcideMenuItemConfiguration showAssertedDatabasePanelConfiguration;

		_viewSubmenuConfiguration = AcideMenuItemsConfiguration.getInstance().getSubmenu(VIEW_MENU_NAME);
			
		// Sets the show log tab menu item to visible or not visible
		showLogTabConfiguration = _viewSubmenuConfiguration.getItem(SHOW_LOG_TAB_NAME);
		_showLogTabMenuItem.setVisible(showLogTabConfiguration.isVisible());

		// Sets the show explorer panel check box menu item as visible or not visible
		showExplorerPanelConfiguration = _viewSubmenuConfiguration.getItem(SHOW_EXPLORER_PANEL_NAME);
		_showExplorerPanelCheckBoxMenuItem.setVisible(showExplorerPanelConfiguration.isVisible());

		// Sets the console panel check box menu item as visible or not visible
		showConsolePanelConfiguration = _viewSubmenuConfiguration.getItem(SHOW_CONSOLE_PANEL_NAME);
		_showConsolePanelCheckBoxMenuItem.setVisible(showConsolePanelConfiguration.isVisible());

		// Sets the console data base check box menu item as visible or not visible
		showDatabaseConfiguration = _viewSubmenuConfiguration.getItem(SHOW_DATA_BASE_PANEL_NAME);
		_showDataBaseCheckBoxMenuItem.setVisible(showDatabaseConfiguration.isVisible());
		
		// Sets the graph panel check box menu item as visible or not visible
		showGraphPanelConfiguration = _viewSubmenuConfiguration.getItem(SHOW_GRAPH_PANEL_NAME);
		_showGraphPanelCheckBoxMenuItem.setVisible(showGraphPanelConfiguration.isVisible());
		
		// Sets the graph panel check box menu item as visible or not visible
		showDebugPanelConfiguration = _viewSubmenuConfiguration.getItem(SHOW_DEBUG_PANEL_NAME);
		_showDebugPanelCheckBoxMenuItem.setVisible(showDebugPanelConfiguration.isVisible());
		
		// Sets the graph panel check box menu item as visible or not visible
		showAssertedDatabasePanelConfiguration = _viewSubmenuConfiguration.getItem(SHOW_ASSERTED_DATABASE_PANEL_NAME);
		_showAssertedDatabasePanelCheckBoxMenuItem.setVisible(showAssertedDatabasePanelConfiguration.isVisible());
			
			
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
		
		// Sets the view menu as visible or not visible
		_viewSubmenuConfiguration.setVisible(_showLogTabMenuItem.isVisible()
			|| _showExplorerPanelCheckBoxMenuItem.isVisible()
			|| _showConsolePanelCheckBoxMenuItem.isVisible()
			|| _showDataBaseCheckBoxMenuItem.isVisible()
			|| _showGraphPanelCheckBoxMenuItem.isVisible()
			|| _showDebugPanelCheckBoxMenuItem.isVisible()
			|| _showAssertedDatabasePanelCheckBoxMenuItem.isVisible()
				);
		
		_viewSubmenuConfiguration.setErasable(false);
		
			
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
		if (!(name.equals(SHOW_LOG_TAB_NAME))
			&& !(name.equals(SHOW_EXPLORER_PANEL_NAME))
			&& !(name.equals(SHOW_CONSOLE_PANEL_NAME))
			&& !(name.equals(SHOW_DATA_BASE_PANEL_NAME))
			&& !(name.equals(SHOW_GRAPH_PANEL_NAME))
			&& !(name.equals(SHOW_DEBUG_PANEL_NAME))
			&& !(name.equals(SHOW_ASSERTED_DATABASE_PANEL_NAME))
			) {
			return true;
		}else{
			return false;
		}
	}

	/**
	 * Sets the ACIDE - A Configurable IDE view menu menu item listeners.
	 */
	public void setListeners() {

		// Sets the show log tab menu item action listener
		_showLogTabMenuItem
				//.addActionListener(new AcideShowAcideLogTabMenuItemListener());
			.addActionListener(new AcideInsertedItemListener(
				AcideMenuItemsConfiguration.getInstance()
				.getSubmenu(VIEW_MENU_NAME).getItem(SHOW_LOG_TAB_NAME)));

		// Sets the show explorer panel check box menu item action listener
		_showExplorerPanelCheckBoxMenuItem
				//.addActionListener(new AcideShowAcideExplorerPanelMenuItemListener());
			.addActionListener(new AcideInsertedItemListener(
				AcideMenuItemsConfiguration.getInstance()
				.getSubmenu(VIEW_MENU_NAME).getItem(SHOW_EXPLORER_PANEL_NAME)));

		// Sets the show console panel check box menu item action listener
		_showConsolePanelCheckBoxMenuItem
				//.addActionListener(new AcideShowAcideConsolePanelMenuItemListener());
			.addActionListener(new AcideInsertedItemListener(
				AcideMenuItemsConfiguration.getInstance()
				.getSubmenu(VIEW_MENU_NAME).getItem(SHOW_CONSOLE_PANEL_NAME)));
		
		// Sets the show data base panel check box menu item action listener
		_showDataBaseCheckBoxMenuItem
				//.addActionListener(new AcideShowAcideDataBasePanelMenuItemListener());
			.addActionListener(new AcideInsertedItemListener(
				AcideMenuItemsConfiguration.getInstance()
				.getSubmenu(VIEW_MENU_NAME).getItem(SHOW_DATA_BASE_PANEL_NAME)));
		
		// Sets the graph panel check box menu item action listener
		_showGraphPanelCheckBoxMenuItem
				//.addActionListener(new AcideShowAcideDataBasePanelMenuItemListener());
			.addActionListener(new AcideInsertedItemListener(
				AcideMenuItemsConfiguration.getInstance()
				.getSubmenu(VIEW_MENU_NAME).getItem(SHOW_GRAPH_PANEL_NAME)));

		// Sets the graph panel check box menu item action listener
		_showDebugPanelCheckBoxMenuItem
				//.addActionListener(new AcideShowAcideDataBasePanelMenuItemListener());
				.addActionListener(new AcideInsertedItemListener(
					AcideMenuItemsConfiguration.getInstance()
					.getSubmenu(VIEW_MENU_NAME).getItem(SHOW_DEBUG_PANEL_NAME)));
		
		// Sets the graph panel check box menu item action listener
		_showAssertedDatabasePanelCheckBoxMenuItem
		// .addActionListener(new AcideShowAcideDataBasePanelMenuItemListener());
				.addActionListener(new AcideInsertedItemListener(
						AcideMenuItemsConfiguration.getInstance()
								.getSubmenu(VIEW_MENU_NAME)
								.getItem(SHOW_ASSERTED_DATABASE_PANEL_NAME)));
		
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
	 * Returns the ACIDE - A Configurable IDE view menu show log tab menu item.
	 * 
	 * @return the ACIDE - A Configurable IDE view menu show log tab menu item.
	 */
	public JMenuItem getShowLogTabMenuItem() {
		return _showLogTabMenuItem;
	}

	/**
	 * Returns the ACIDE - A Configurable IDE view menu show explorer panel
	 * check box menu item.
	 * 
	 * @return the ACIDE - A Configurable IDE view menu show explorer panel
	 *         check box menu item.
	 */
	public JCheckBoxMenuItem getShowExplorerPanelCheckBoxMenuItem() {
		return _showExplorerPanelCheckBoxMenuItem;
	}

	/**
	 * Returns the ACIDE - A Configurable IDE view menu show output panel check
	 * box menu item.
	 * 
	 * @return the ACIDE - A Configurable IDE view menu show output panel check
	 *         box menu item.
	 */
	public JCheckBoxMenuItem getShowConsolePanelCheckBoxMenuItem() {
		return _showConsolePanelCheckBoxMenuItem;
	}
	
	/**
	 * Returns the ACIDE - A Configurable IDE view menu show output panel check
	 * box menu item.
	 * 
	 * @return the ACIDE - A Configurable IDE view menu show output panel check
	 *         box menu item.
	 */
	public JCheckBoxMenuItem getShowDataBasePanelCheckBoxMenuItem() {
		return _showDataBaseCheckBoxMenuItem;
	}
	
	/**
	 * Returns the ACIDE - A Configurable IDE view menu show output panel check
	 * box menu item.
	 * 
	 * @return the ACIDE - A Configurable IDE view menu show output panel check
	 *         box menu item.
	 */
	public JCheckBoxMenuItem getShowGraphPanelCheckBoxMenuItem() {
		return _showGraphPanelCheckBoxMenuItem;
	}
	
	/**
	 * Returns the ACIDE - A Configurable IDE view menu show output panel check
	 * box menu item.
	 * 
	 * @return the ACIDE - A Configurable IDE view menu show output panel check
	 *         box menu item.
	 */
	public JCheckBoxMenuItem getShowDebugPanelCheckBoxMenuItem() {
		return _showDebugPanelCheckBoxMenuItem;
	}
	
	/**
	 * Returns the ACIDE - A Configurable IDE view menu show output panel check
	 * box menu item.
	 * 
	 * @return the ACIDE - A Configurable IDE view menu show output panel check
	 *         box menu item.
	 */
	public JCheckBoxMenuItem getShowAssertedDatabasePanelCheckBoxMenuItem() {
		return _showAssertedDatabasePanelCheckBoxMenuItem;
	}
}