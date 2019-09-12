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
package acide.gui.menuBar.configurationMenu.grammarMenu;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import javax.swing.ImageIcon;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JSeparator;
import javax.swing.KeyStroke;

import acide.configuration.menu.AcideInsertedItem;
import acide.configuration.menu.AcideInsertedItemListener;
import acide.configuration.menu.AcideInsertedMenu;
import acide.configuration.menu.AcideMenuConfiguration;
import acide.configuration.menu.AcideMenuItemConfiguration;
import acide.configuration.menu.AcideMenuItemsConfiguration;
import acide.configuration.menu.AcideMenuObjectConfiguration;
import acide.configuration.menu.AcideMenuSubmenuConfiguration;
import acide.gui.fileEditor.fileEditorPanel.AcideFileEditorPanel;
import acide.gui.mainWindow.AcideMainWindow;
import acide.gui.menuBar.configurationMenu.AcideConfigurationMenu;
import acide.gui.menuBar.listeners.AcideMenuBarMouseClickListener;
import acide.language.AcideLanguageManager;
import acide.log.AcideLog;
import acide.resources.AcideResourceManager;
import acide.utils.IconsUtils;

/**
 * ACIDE - A Configurable IDE grammar menu.
 * 
 * @version 0.11
 * @see ActionListener
 */
public class AcideGrammarMenu extends JMenu {

	/**
	 * ACIDE - A Configurable IDE grammar menu class serial version UID.
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * ACIDE - A Configurable IDE grammar menu name.
	 */
	public final static String GRAMMAR_MENU_NAME = "Grammar";
	/**
	 * ACIDE - A Configurable IDE grammar menu new grammar menu item name.
	 */
	public static final String NEW_GRAMMAR_NAME = "New Grammar";
	/**
	 * ACIDE - A Configurable IDE grammar menu load grammar menu item name.
	 */
	public static final String LOAD_GRAMMAR_NAME = "Load Grammar";
	/**
	 * ACIDE - A Configurable IDE grammar menu modify grammar menu item name.
	 */
	public static final String MODIFY_GRAMMAR_NAME = "Modify Grammar";
	/**
	 * ACIDE - A Configurable IDE grammar menu save grammar menu item name.
	 */
	public static final String SAVE_GRAMMAR_NAME = "Save Grammar";
	/**
	 * ACIDE - A Configurable IDE grammar menu save grammar as menu item name.
	 */
	public static final String SAVE_GRAMMAR_AS_NAME = "Save Grammar As";
	/**
	 * ACIDE - A Configurable IDE grammar menu set paths menu item name.
	 */
	public static final String SET_PATHS_NAME = "Set Paths";
	/**
	 * ACIDE - A Configurable IDE grammar menu suto-Analysis menu item name.
	 */
	public static final String AUTO_ANALYSIS_NAME = "Auto-Analysis";
	/**
	 * ACIDE - A Configurable IDE grammar menu new grammar menu item.
	 */
	private JMenuItem _newGrammarMenuItem;
	/**
	 * ACIDE - A Configurable IDE grammar menu new grammar menu item has been inserted.
	 */
	private boolean _newGrammarInserted;
	/**
	 * ACIDE - A Configurable IDE grammar menu load grammar menu item.
	 */
	private JMenuItem _loadGrammarMenuItem;
	/**
	 * ACIDE - A Configurable IDE grammar menu load grammar menu item has been inserted.
	 */
	private boolean _loadGrammarInserted;
	/**
	 * ACIDE - A Configurable IDE grammar menu modify menu item.
	 */
	private JMenuItem _modifyGrammarMenuItem;
	/**
	 * ACIDE - A Configurable IDE grammar menu modify grammar menu item has been inserted.
	 */
	private boolean _modifyGrammarInserted;
	/**
	 * ACIDE - A Configurable IDE grammar menu save menu item.
	 */
	private JMenuItem _saveGrammarMenuItem;
	/**
	 * ACIDE - A Configurable IDE grammar menu save grammar menu item has been inserted.
	 */
	private boolean _saveGrammarInserted;
	/**
	 * ACIDE - A Configurable IDE grammar menu save grammar as menu item.
	 */
	private JMenuItem _saveGrammarAsMenuItem;
	/**
	 * ACIDE - A Configurable IDE grammar menu save grammar  as menu item has been inserted.
	 */
	private boolean _saveGrammarAsInserted;
	/**
	 * ACIDE - A Configurable IDE grammar menu set paths menu item.
	 */
	private JMenuItem _setPathsMenuItem;
	/**
	 * ACIDE - A Configurable IDE grammar menu set paths menu item has been inserted.
	 */
	private boolean _setPathsInserted;
	/**
	 * ACIDE - A Configurable IDE grammar menu auto analysis check box menu
	 * item.
	 */
	private JCheckBoxMenuItem _autoAnalysisCheckBoxMenuItem;
	/**
	 * ACIDE - A Configurable IDE grammar menu save grammar as set paths
	 * separator.
	 */
	private JSeparator _saveGrammarAsSetPathsSeparator;
	/**
	 * ACIDE - A Configurable IDE grammar menu set paths auto analysis
	 * separator.
	 */
	private JSeparator _setPathsAutoAnalyisisSeparator;
	/**
	 * ACIDE - A Configurable IDE grammar menu configuration menu.
	 */
	private AcideMenuSubmenuConfiguration _grammarSubmenuConfiguration;
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
	 * Creates a new ACIDE - A Configurable IDE grammar menu.
	 */
	public AcideGrammarMenu() {
		
		_newGrammarInserted = false;
		_loadGrammarInserted = false;
		_modifyGrammarInserted = false;
		_saveGrammarInserted = false;
		_saveGrammarAsInserted = false;
		_setPathsInserted = false;
		
		_insertedItems = new HashMap<String, AcideInsertedItem>();
		
		_insertedMenus = new HashMap<String, AcideInsertedMenu>();
		
		_insertedObjects = new ArrayList<AcideMenuObjectConfiguration>();

		// Builds the menu components
		buildComponents();

		// Adds the components to the menu
		addComponents();

		// Sets the text of the grammar menu components
		setTextOfMenuComponents();
	}

	/**
	 * Adds the components to the ACIDE - A Configurable IDE grammar menu.
	 */
	private void addComponents() {
		
		Iterator<Object> it = AcideMenuItemsConfiguration.getInstance()
				.getMenuItemsManager().getSubmenu(AcideConfigurationMenu.CONFIGURATION_MENU_NAME)
				.getItemsManager().getSubmenu(GRAMMAR_MENU_NAME).getItemsManager().managerIterator();
		while (it.hasNext()){
			AcideMenuObjectConfiguration ob = (AcideMenuObjectConfiguration) it.next();
			String name = ob.getName();
			if (name.equals(NEW_GRAMMAR_NAME)){
				// Adds the new grammar menu item to the menu
				add(_newGrammarMenuItem);
				_newGrammarInserted = true;
			}else if (name.equals(LOAD_GRAMMAR_NAME)){
				// Adds the load grammar menu item to the menu
				add(_loadGrammarMenuItem);
				_loadGrammarInserted = true;
			}else if (name.equals(MODIFY_GRAMMAR_NAME)){
				// Adds the modify grammar menu item to the menu
				add(_modifyGrammarMenuItem);
				_modifyGrammarInserted = true;
			}else if (name.equals(SAVE_GRAMMAR_NAME)){
				// Adds the save grammar menu item to the menu
				add(_saveGrammarMenuItem);
				_saveGrammarInserted = true;
			}else if (name.equals(SAVE_GRAMMAR_AS_NAME)){
				// Adds the save grammar as menu item to the menu
				add(_saveGrammarAsMenuItem);
				_saveGrammarAsInserted = true;
				// Adds the save grammar as set paths separator to the menu
				add(_saveGrammarAsSetPathsSeparator);
			}else if (name.equals(SET_PATHS_NAME)){
				// Adds the set paths menu item to the menu
				add(_setPathsMenuItem);
				_setPathsInserted = true;
			}else if (name.equals(AUTO_ANALYSIS_NAME)){
				;
			}else {
				if (ob.isSubmenu()){
					add(_insertedMenus.get(ob.getName()));
				}else{
					add(_insertedItems.get(ob.getName()));
				}
			}
		}
		
		if (!_newGrammarInserted)
			add(_newGrammarMenuItem);
		if (!_loadGrammarInserted)
			add(_loadGrammarMenuItem);
		if (!_modifyGrammarInserted)
			add(_modifyGrammarMenuItem);
		if (!_saveGrammarInserted)
			add(_saveGrammarMenuItem);
		if (!_saveGrammarAsInserted)
			add(_saveGrammarAsMenuItem);
		if (!_setPathsInserted)
			add(_setPathsMenuItem);

		// Adds the set paths auto analysis separator to the menu
		//add(_setPathsAutoAnalyisisSeparator);

		// Adds the auto analysis check box menu item to the menu
		//add(_autoAnalysisCheckBoxMenuItem);
	}

	/**
	 * Builds the ACIDE - A Configurable IDE grammar menu components.
	 */
	private void buildComponents() {
		
		if (!AcideMenuItemsConfiguration.getInstance().getMenuItemsManager()
				.getSubmenu(AcideConfigurationMenu.CONFIGURATION_MENU_NAME).hasSubmenu(GRAMMAR_MENU_NAME)){
			AcideMenuItemsConfiguration.getInstance().getMenuItemsManager()
				.getSubmenu(AcideConfigurationMenu.CONFIGURATION_MENU_NAME)
					.insertObject(new AcideMenuSubmenuConfiguration(GRAMMAR_MENU_NAME));
		}
		
		Iterator<Object> it = AcideMenuItemsConfiguration.getInstance()
				.getMenuItemsManager().getSubmenu(AcideConfigurationMenu.CONFIGURATION_MENU_NAME)
				.getItemsManager().getSubmenu(GRAMMAR_MENU_NAME).getItemsManager().managerIterator();
		
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

		// Creates the new grammar menu item
		ImageIcon icon = IconsUtils.getIcon(AcideMenuItemsConfiguration.getInstance()
				.getMenuItemsManager().getSubmenu(AcideConfigurationMenu.CONFIGURATION_MENU_NAME)
				.getSubmenu(GRAMMAR_MENU_NAME).getItem(NEW_GRAMMAR_NAME).getImage());
		
		if (icon != null)
			_newGrammarMenuItem = new JMenuItem(icon);
		else
			_newGrammarMenuItem = new JMenuItem();

		// Sets the new grammar menu item name
		_newGrammarMenuItem.setName(NEW_GRAMMAR_NAME);

		// Creates the load grammar menu item
		icon = IconsUtils.getIcon(AcideMenuItemsConfiguration.getInstance()
				.getMenuItemsManager().getSubmenu(AcideConfigurationMenu.CONFIGURATION_MENU_NAME)
				.getSubmenu(GRAMMAR_MENU_NAME).getItem(LOAD_GRAMMAR_NAME).getImage());
		
		if (icon != null)
			_loadGrammarMenuItem = new JMenuItem(icon);
		else
			_loadGrammarMenuItem = new JMenuItem();
	
		// Sets the load grammar menu item name
		_loadGrammarMenuItem.setName(LOAD_GRAMMAR_NAME);

		// Creates the modify grammar menu item
		icon = IconsUtils.getIcon(AcideMenuItemsConfiguration.getInstance()
				.getMenuItemsManager().getSubmenu(AcideConfigurationMenu.CONFIGURATION_MENU_NAME)
				.getSubmenu(GRAMMAR_MENU_NAME).getItem(MODIFY_GRAMMAR_NAME).getImage());
		
		if (icon != null)
			_modifyGrammarMenuItem = new JMenuItem(icon);
		else
			_modifyGrammarMenuItem = new JMenuItem();

		// Sets the modify grammar menu item name
		_modifyGrammarMenuItem.setName(MODIFY_GRAMMAR_NAME);

		// Creates the save grammar menu item
		icon = IconsUtils.getIcon(AcideMenuItemsConfiguration.getInstance()
				.getMenuItemsManager().getSubmenu(AcideConfigurationMenu.CONFIGURATION_MENU_NAME)
				.getSubmenu(GRAMMAR_MENU_NAME).getItem(SAVE_GRAMMAR_NAME).getImage());
		
		if (icon != null)
			_saveGrammarMenuItem = new JMenuItem(icon);
		else
			_saveGrammarMenuItem = new JMenuItem();

		// Sets the save grammar menu item name
		_saveGrammarMenuItem.setName(SAVE_GRAMMAR_NAME);

		// Disables the save grammar menu item
		_saveGrammarMenuItem.setEnabled(false);

		// Creates the save grammar as menu item
		icon = IconsUtils.getIcon(AcideMenuItemsConfiguration.getInstance()
				.getMenuItemsManager().getSubmenu(AcideConfigurationMenu.CONFIGURATION_MENU_NAME)
				.getSubmenu(GRAMMAR_MENU_NAME).getItem(SAVE_GRAMMAR_AS_NAME).getImage());
		
		if (icon != null)
			_saveGrammarAsMenuItem = new JMenuItem(icon);
		else
			_saveGrammarAsMenuItem = new JMenuItem();

		// Sets the save grammar as menu item name
		_saveGrammarAsMenuItem.setName(SAVE_GRAMMAR_AS_NAME);

		// Creates the save grammar as set paths separator
		_saveGrammarAsSetPathsSeparator = new JSeparator();
		
		// Creates the set paths menu item
		icon = IconsUtils.getIcon(AcideMenuItemsConfiguration.getInstance()
				.getMenuItemsManager().getSubmenu(AcideConfigurationMenu.CONFIGURATION_MENU_NAME)
				.getSubmenu(GRAMMAR_MENU_NAME).getItem(SET_PATHS_NAME).getImage());
		
		if (icon != null)
			_setPathsMenuItem = new JMenuItem(icon);
		else
			_setPathsMenuItem = new JMenuItem();

		// Sets the set paths menu item name
		_setPathsMenuItem.setName(SET_PATHS_NAME);

		// Creates the auto analysis check box menu item
		_autoAnalysisCheckBoxMenuItem = new JCheckBoxMenuItem(
				AcideLanguageManager.getInstance().getLabels()
						.getString("s911"));

		// Creates the set paths auto analysis separator
		_setPathsAutoAnalyisisSeparator = new JSeparator();
		
		// Sets the auto analysis check box menu item name
		_autoAnalysisCheckBoxMenuItem.setName(AUTO_ANALYSIS_NAME);

		// Sets the auto analysis check box menu item as not selected
		_autoAnalysisCheckBoxMenuItem.setSelected(false);
	}

	/**
	 * Sets the text of the ACIDE - A Configurable IDE grammar menu components
	 * with the labels in the selected language to display.
	 */
	public void setTextOfMenuComponents() {

		// Sets the new grammar menu item text
		_newGrammarMenuItem.setText(AcideLanguageManager.getInstance()
				.getLabels().getString("s30"));

		// Sets the new grammar menu item accelerator
		_newGrammarMenuItem.setAccelerator(KeyStroke.getKeyStroke(
				KeyEvent.VK_T, ActionEvent.CTRL_MASK + ActionEvent.SHIFT_MASK));

		// Sets the load grammar menu item text
		_loadGrammarMenuItem.setText(AcideLanguageManager.getInstance()
				.getLabels().getString("s226"));

		// Sets the modify grammar menu item text
		_modifyGrammarMenuItem.setText(AcideLanguageManager.getInstance()
				.getLabels().getString("s227"));

		// Sets the save grammar menu item text
		_saveGrammarMenuItem.setText(AcideLanguageManager.getInstance()
				.getLabels().getString("s251"));

		// Sets the save grammar as menu item text
		_saveGrammarAsMenuItem.setText(AcideLanguageManager.getInstance()
				.getLabels().getString("s285"));

		// Sets the set paths menu item text
		_setPathsMenuItem.setText(AcideLanguageManager.getInstance()
				.getLabels().getString("s912"));

		// Sets the auto analysis check box menu item text
		_autoAnalysisCheckBoxMenuItem.setText(AcideLanguageManager
				.getInstance().getLabels().getString("s911"));
		
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
		if (!(name.equals(NEW_GRAMMAR_NAME))
			&& !(name.equals(LOAD_GRAMMAR_NAME))
			&& !(name.equals(MODIFY_GRAMMAR_NAME))
			&& !(name.equals(SAVE_GRAMMAR_NAME))
			&& !(name.equals(SAVE_GRAMMAR_AS_NAME))
			&& !(name.equals(SET_PATHS_NAME))
			//&& !(name.equals(AUTO_ANALYSIS_NAME))
			){
			return true;
		}else {
			return false;
		}
	}

	/**
	 * Sets the ACIDE - A Configurable IDE grammar menu item listeners.
	 */
	public void setListeners() {

		// Sets the new grammar menu item action listener
		_newGrammarMenuItem
				//.addActionListener(new AcideNewGrammarMenuItemListener());
			.addActionListener(new AcideInsertedItemListener(
				AcideMenuItemsConfiguration.getInstance()
				.getSubmenu(AcideConfigurationMenu.CONFIGURATION_MENU_NAME)
				.getSubmenu(GRAMMAR_MENU_NAME).getItem(NEW_GRAMMAR_NAME)));

		// Sets the load grammar menu item action listener
		_loadGrammarMenuItem
				//.addActionListener(new AcideLoadGrammarMenuItemListener());
			.addActionListener(new AcideInsertedItemListener(
				AcideMenuItemsConfiguration.getInstance()
				.getSubmenu(AcideConfigurationMenu.CONFIGURATION_MENU_NAME)
				.getSubmenu(GRAMMAR_MENU_NAME).getItem(LOAD_GRAMMAR_NAME)));

		// Sets the modify grammar menu item action listener
		_modifyGrammarMenuItem
				//.addActionListener(new AcideModifyGrammaMenuItemrListener());
			.addActionListener(new AcideInsertedItemListener(
				AcideMenuItemsConfiguration.getInstance()
				.getSubmenu(AcideConfigurationMenu.CONFIGURATION_MENU_NAME)
				.getSubmenu(GRAMMAR_MENU_NAME).getItem(MODIFY_GRAMMAR_NAME)));

		// Sets the save grammar menu item action listener
		_saveGrammarMenuItem
				//.addActionListener(new AcideSaveGrammarMenuItemListener());
			.addActionListener(new AcideInsertedItemListener(
				AcideMenuItemsConfiguration.getInstance()
				.getSubmenu(AcideConfigurationMenu.CONFIGURATION_MENU_NAME)
				.getSubmenu(GRAMMAR_MENU_NAME).getItem(SAVE_GRAMMAR_NAME)));

		// Sets the save grammar as menu item action listener
		_saveGrammarAsMenuItem
				//.addActionListener(new AcideSaveAsGrammarMenuItemListener());
			.addActionListener(new AcideInsertedItemListener(
				AcideMenuItemsConfiguration.getInstance()
				.getSubmenu(AcideConfigurationMenu.CONFIGURATION_MENU_NAME)
				.getSubmenu(GRAMMAR_MENU_NAME).getItem(SAVE_GRAMMAR_AS_NAME)));

		// Sets the set paths menu item action listener
		_setPathsMenuItem
				//.addActionListener(new AcideSetPathsMenuItemListener());
			.addActionListener(new AcideInsertedItemListener(
				AcideMenuItemsConfiguration.getInstance()
				.getSubmenu(AcideConfigurationMenu.CONFIGURATION_MENU_NAME)
				.getSubmenu(GRAMMAR_MENU_NAME).getItem(SET_PATHS_NAME)));

		// Sets the auto analysis check box menu item action listener
//		_autoAnalysisCheckBoxMenuItem
//				//.addActionListener(new AcideAutoAnalysisMenuItemListener());
//			.addActionListener(new AcideInsertedItemListener(
//				AcideMenuItemsConfiguration.getInstance()
//				.getSubmenu(AcideConfigurationMenu.CONFIGURATION_MENU_NAME)
//				.getSubmenu(GRAMMAR_MENU_NAME).getItem(AUTO_ANALYSIS_NAME)));
		
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
	 * Updates the ACIDE - A Configurable IDE grammar menu components visibility
	 * with the menu configuration.
	 */
	public void updateComponentsVisibility() {
		
		AcideMenuItemConfiguration newGrammarConfiguration;
		AcideMenuItemConfiguration loadGrammarConfiguration;
		AcideMenuItemConfiguration modifyGrammarConfiguration;
		AcideMenuItemConfiguration saveGrammarConfiguration;
		AcideMenuItemConfiguration saveGrammarAsConfiguration;
		AcideMenuItemConfiguration setPathsConfiguration;
		
		_grammarSubmenuConfiguration = AcideMenuItemsConfiguration.getInstance()
					.getSubmenu(AcideConfigurationMenu.CONFIGURATION_MENU_NAME).getSubmenu(GRAMMAR_MENU_NAME);
			
		
		// Sets the new grammar menu item to visible or not visible
		newGrammarConfiguration = _grammarSubmenuConfiguration.getItem(NEW_GRAMMAR_NAME);
		_newGrammarMenuItem.setVisible(newGrammarConfiguration.isVisible());
			
		// Sets the load grammar menu item to visible or not visible
		loadGrammarConfiguration = _grammarSubmenuConfiguration.getItem(LOAD_GRAMMAR_NAME);
		_loadGrammarMenuItem.setVisible(loadGrammarConfiguration.isVisible());

		// Sets the modify grammar menu item to visible or not visible
		modifyGrammarConfiguration = _grammarSubmenuConfiguration.getItem(MODIFY_GRAMMAR_NAME);
		_modifyGrammarMenuItem.setVisible(modifyGrammarConfiguration.isVisible());

		// Sets the save grammar menu item to visible or not visible
		saveGrammarConfiguration = _grammarSubmenuConfiguration.getItem(SAVE_GRAMMAR_NAME);
		_saveGrammarMenuItem.setVisible(saveGrammarConfiguration.isVisible());

		// Sets the save grammar as menu item to visible or not visible
		saveGrammarAsConfiguration = _grammarSubmenuConfiguration.getItem(SAVE_GRAMMAR_AS_NAME);
		_saveGrammarAsMenuItem.setVisible(saveGrammarAsConfiguration.isVisible());

		// Sets the set paths menu item to visible or not visible
		setPathsConfiguration = _grammarSubmenuConfiguration.getItem(SET_PATHS_NAME);
		_setPathsMenuItem.setVisible(setPathsConfiguration.isVisible());

		// Sets the save grammar as set paths separator as visible or not
		// visible
		_saveGrammarAsSetPathsSeparator.setVisible((_newGrammarMenuItem.isVisible()
				|| _loadGrammarMenuItem.isVisible()
				|| _modifyGrammarMenuItem.isVisible()
				|| _saveGrammarMenuItem.isVisible() || _saveGrammarAsMenuItem.isVisible())
				&& (_setPathsMenuItem.isVisible()));
			
//		// Sets the auto analysis check box menu item to visible or not visible
//		autoAnalysisConfiguration = _grammarSubmenuConfiguration.getItem(AUTO_ANALYSIS_NAME);
//		_autoAnalysisCheckBoxMenuItem.setVisible(autoAnalysisConfiguration.isVisible());

		// Sets the set paths auto analysis separator to visible or not visible
		_setPathsAutoAnalyisisSeparator.setVisible((_newGrammarMenuItem.isVisible()
				|| _loadGrammarMenuItem.isVisible()
				|| _modifyGrammarMenuItem.isVisible()
				|| _saveGrammarMenuItem.isVisible()
				|| _saveGrammarAsMenuItem.isVisible() || _setPathsMenuItem.isVisible())
				&& (_autoAnalysisCheckBoxMenuItem.isVisible()));
		
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
		
		// Sets the grammar menu to visible or not visible
		_grammarSubmenuConfiguration.setVisible(_newGrammarMenuItem.isVisible()
						|| _loadGrammarMenuItem.isVisible()
						|| _modifyGrammarMenuItem.isVisible()
						|| _saveGrammarMenuItem.isVisible()
						|| _saveGrammarAsMenuItem.isVisible());
		_grammarSubmenuConfiguration.setErasable(false);
		
				
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
	 * Enables the ACIDE - A Configurable IDE grammar menu.
	 */
	public void enableMenu() {

		// Enables the new grammar menu item
		_newGrammarMenuItem.setEnabled(true);
		
		// Enables the load grammar menu item
		_loadGrammarMenuItem.setEnabled(true);
		
		// Enables the modify grammar menu item
		_modifyGrammarMenuItem.setEnabled(true);
		
		// Enables the save grammar menu item
		_saveGrammarMenuItem.setEnabled(true);
		
		// Enables the save grammar as menu item
		_saveGrammarAsMenuItem.setEnabled(true);
		
		// Enables the set paths menu item
		_setPathsMenuItem.setEnabled(true);
		
		// Enables the auto analysis check box menu item
		_autoAnalysisCheckBoxMenuItem.setEnabled(true);
	}
	
	/**
	 * Disables the ACIDE - A Configurable IDE grammar menu.
	 */
	public void disableMenu() {

		// Disables the new grammar menu item
		_newGrammarMenuItem.setEnabled(false);
		
		// Disables the load grammar menu item
		_loadGrammarMenuItem.setEnabled(false);
		
		// Disables the modify grammar menu item
		_modifyGrammarMenuItem.setEnabled(false);
		
		// Disables the save grammar menu item
		_saveGrammarMenuItem.setEnabled(false);
		
		// Disables the save grammar as menu item
		_saveGrammarAsMenuItem.setEnabled(false);
		
		// Enables the set paths menu item
		_setPathsMenuItem.setEnabled(true);
		
		// Enables the auto analysis check box menu item
		_autoAnalysisCheckBoxMenuItem.setEnabled(true);
	}
	
	/**
	 * Configures the ACIDE - A Configurable IDE grammar menu.
	 */
	public void configure(){
		
		if (AcideMainWindow.getInstance().getFileEditorManager()
				.getNumberOfFileEditorPanels() > 0) {

			// Enables the grammar menu
			enableMenu();

			// Gets the selected file editor panel
			AcideFileEditorPanel selectedFileEditorPanel = AcideMainWindow
					.getInstance().getFileEditorManager()
					.getSelectedFileEditorPanel();

			// If the current grammar configuration is lastModified or
			// newGrammar
			if (selectedFileEditorPanel.getCurrentGrammarConfiguration()
					.getName().matches("newGrammar")
					|| selectedFileEditorPanel.getCurrentGrammarConfiguration()
							.getName().matches("lastModified"))

				// Enables the save grammar menu
				_saveGrammarMenuItem
						.setEnabled(true);
			else
				// Disables the save grammar menu
				_saveGrammarMenuItem
						.setEnabled(false);
		} else {

			// Disables the grammar menu
			disableMenu();
		}
	}
	
	/**
	 * Returns the ACIDE - A Configurable IDE grammar menu new grammar menu
	 * item.
	 * 
	 * @return the ACIDE - A Configurable IDE grammar menu new grammar menu
	 *         item.
	 */
	public JMenuItem getNewGrammarMenuItem() {
		return _newGrammarMenuItem;
	}

	/**
	 * Returns the ACIDE - A Configurable IDE grammar menu load grammar menu
	 * item.
	 * 
	 * @return the ACIDE - A Configurable IDE grammar menu load grammar menu
	 *         item.
	 */
	public JMenuItem getLoadGrammarMenuItem() {
		return _loadGrammarMenuItem;
	}

	/**
	 * Returns the ACIDE - A Configurable IDE grammar menu modify grammar menu
	 * item.
	 * 
	 * @return the ACIDE - A Configurable IDE grammar menu modify grammar menu
	 *         item.
	 */
	public JMenuItem getModifyGrammarMenuItem() {
		return _modifyGrammarMenuItem;
	}

	/**
	 * Returns the ACIDE - A Configurable IDE grammar menu save grammar menu
	 * item.
	 * 
	 * @return the ACIDE - A Configurable IDE grammar menu save grammar menu
	 *         item.
	 */
	public JMenuItem getSaveGrammarMenuItem() {
		return _saveGrammarMenuItem;
	}

	/**
	 * Returns the ACIDE - A Configurable IDE grammar menu save grammar as menu
	 * item.
	 * 
	 * @return the ACIDE - A Configurable IDE grammar menu save grammar as menu
	 *         item.
	 */
	public JMenuItem getSaveGrammarAsMenuItem() {
		return _saveGrammarAsMenuItem;
	}
}