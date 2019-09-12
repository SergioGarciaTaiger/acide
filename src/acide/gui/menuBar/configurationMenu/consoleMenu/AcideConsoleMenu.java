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
package acide.gui.menuBar.configurationMenu.consoleMenu;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;

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
import acide.gui.menuBar.configurationMenu.AcideConfigurationMenu;
import acide.gui.menuBar.listeners.AcideMenuBarMouseClickListener;
import acide.language.AcideLanguageManager;
import acide.log.AcideLog;
import acide.resources.AcideResourceManager;
import acide.utils.IconsUtils;

/**
 * ACIDE - A Configurable IDE console menu.
 * 
 * @version 0.11
 * @see JMenu
 */
public class AcideConsoleMenu extends JMenu {

	/**
	 * ACIDE - A Configurable IDE console menu class serial version UID.
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * ACIDE - A Configurable IDE console menu name.
	 */
	public final static String CONSOLE_MENU_NAME = "Shell";
	/**
	 * ACIDE - A Configurable IDE console menu configure menu item name.
	 */
	public static final String CONFIGURE_NAME = "Configure";
	/**
	 * ACIDE - A Configurable IDE console menu external command menu item name.
	 */
	public static final String EXTERNAL_COMMAND_NAME = "External Command";
	/**
	 * ACIDE - A Configurable IDE console menu console display options menu item
	 * name.
	 */
	public static final String CONSOLE_DISPLAY_OPTIONS_NAME = "Console Display Options";
	/**
	 * ACIDE - A Configurable IDE console menu console line wrapping menu item
	 * name.
	 */
	public static final String CONSOLE_LINE_WRAPPING_NAME = "Console Line Wrapping";
	/**
	 * ACIDE - A Configurable IDE console menu save console content into file
	 * menu item name.
	 */
	public static final String SAVE_CONSOLE_CONTENT_INTO_FILE_NAME = "Save Console Content Into File";
	/**
	 * ACIDE - A Configurable IDE console menu document console lexicon menu
	 * item name.
	 */
	public static final String DOCUMENT_CONSOLE_LEXICON_NAME = "Document Console Lexicon";
	/**
	 * ACIDE - A Configurable IDE console menu search console menu item name.
	 */
	public static final String SEARCH_CONSOLE_NAME = "Search Console";
	/**
	 * ACIDE - A Configurable IDE console menu close console menu item name.
	 */
	public static final String CLOSE_CONSOLE_NAME = "Close Console";
	/**
	 * ACIDE - A Configurable IDE console menu configure menu item.
	 */
	private JMenuItem _configureMenuItem;
	/**
	 * ACIDE - A Configurable IDE console menu configure menu item has been inserted.
	 */
	private boolean _configureInserted;
	/**
	 * ACIDE - A Configurable IDE console menu external command menu item.
	 */
	private JMenuItem _externalCommandMenuItem;
	/**
	 * ACIDE - A Configurable IDE console menu external command menu item has been inserted.
	 */
	private boolean _externalCommandInserted;
	/**
	 * ACIDE - A Configurable IDE console menu console display options menu
	 * item.
	 */
	private JMenuItem _consoleDisplayOptionsMenuItem;
	/**
	 * ACIDE - A Configurable IDE console menu display options menu item has been inserted.
	 */
	private boolean _consoleDisplayOptionsInserted;
	/**
	 * ACIDE - A Configurable IDE console line wrapping check box menu item.
	 */
	private JCheckBoxMenuItem _consoleLineWrappingCheckBoxMenuItem;
	/**
	 * ACIDE - A Configurable IDE console line wrapping check box menu item has been inserted.
	 */
	private boolean _consoleLineWrappingInserted;
	/**
	 * ACIDE - A Configurable IDE console menu document lexicon menu item.
	 */
	private JMenuItem _documentLexiconMenuItem;
	/**
	 * ACIDE - A Configurable IDE console menu document lexicon menu item has been inserted.
	 */
	private boolean _documentLexiconInserted;
	/**
	 * ACIDE - A Configurable IDE console menu search console menu item.
	 */
	private JMenuItem _searchConsoleMenuItem;
	/**
	 * ACIDE - A Configurable IDE console menu search console menu item has been inserted.
	 */
	private boolean _searchConsoleInserted;
	/**
	 * ACIDE - A Configurable IDE console menu close console menu item.
	 */
	private JMenuItem _closeConsoleMenuItem;
	/**
	 * ACIDE - A Configurable IDE console menu close console menu item has been inserted.
	 */
	private boolean _closeConsoleInserted;
	/**
	 * ACIDE - A Configurable IDE console menu save console content into file
	 * menu item.
	 */
	private JMenuItem _saveConsoleContentIntoFile;
	/**
	 * ACIDE - A Configurable IDE console menu save console content menu item has been inserted.
	 */
	private boolean _saveConsoleContentInserted;
	/**
	 * ACIDE - A Configurable IDE console menu search close console separator.
	 */
	private JSeparator _searchCloseConsoleSeparator;
	/**
	 * ACIDE - A Configurable IDE console menu external command display options
	 * separator.
	 */
	private JSeparator _externalCommandDisplayOptionsSeparator;
	/**
	 * ACIDE - A Configurable IDE console menu document console lexicon search
	 * console separator.
	 */
	private JSeparator _documentConsoleLexiconSearchConsoleSeparator;
	/**
	 * ACIDE - A Configurable IDE console menu configuration menu.
	 */
	private AcideMenuSubmenuConfiguration _consoleSubmenuConfiguration;
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
	public AcideConsoleMenu() {
		
		_configureInserted = false;
		_externalCommandInserted = false;
		_consoleDisplayOptionsInserted = false;
		_documentLexiconInserted = false;
		_searchConsoleInserted = false;
		_closeConsoleInserted = false;
		_saveConsoleContentInserted = false;
		_consoleLineWrappingInserted = false;
		
		_insertedItems = new HashMap<String, AcideInsertedItem>();
		
		_insertedMenus = new HashMap<String, AcideInsertedMenu>();
		
		_insertedObjects = new ArrayList<AcideMenuObjectConfiguration>();

		// Builds the menu components
		buildComponents();

		// Adds the components to the menu
		addComponents();

		// Sets the text of the console menu components
		setTextOfMenuComponents();
	}

	/**
	 * Adds the components to the ACIDE - A Configurable IDE console menu.
	 */
	private void addComponents() {
		
		Iterator<Object> it = AcideMenuItemsConfiguration.getInstance()
				.getMenuItemsManager().getSubmenu(AcideConfigurationMenu.CONFIGURATION_MENU_NAME)
				.getItemsManager().getSubmenu(CONSOLE_MENU_NAME).getItemsManager().managerIterator();
		while (it.hasNext()){
			AcideMenuObjectConfiguration ob = (AcideMenuObjectConfiguration) it.next();
			String name = ob.getName();
			if (name.equals(CONFIGURE_NAME)){
				// Adds the configure menu item
				add(_configureMenuItem);
				_configureInserted = true;
			} else if (name.equals(EXTERNAL_COMMAND_NAME)){
				// Adds the external command menu item
				add(_externalCommandMenuItem);
				_externalCommandInserted = true;
				// Adds the external command display options separator
				add(_externalCommandDisplayOptionsSeparator);
			} else if (name.equals(CONSOLE_DISPLAY_OPTIONS_NAME)){
				// Adds the console display options menu item
				add(_consoleDisplayOptionsMenuItem);
				_consoleDisplayOptionsInserted = true;
			} else if (name.equals(CONSOLE_LINE_WRAPPING_NAME)) {
				// Adds the console line wrapping menu item
				add(_consoleLineWrappingCheckBoxMenuItem);
//				_consoleLineWrappingCheckBoxMenuItem.setSelected(true);
				_consoleLineWrappingInserted = true;
			} else if (name.equals(SAVE_CONSOLE_CONTENT_INTO_FILE_NAME)){
				// Adds the save console content into file menu item
				add(_saveConsoleContentIntoFile);
				_saveConsoleContentInserted = true;
			} else if (name.equals(DOCUMENT_CONSOLE_LEXICON_NAME)){
				// Adds the document lexicon menu item
				add(_documentLexiconMenuItem);
				_documentLexiconInserted = true;
				// Adds the document console lexicon search console separator
				add(_documentConsoleLexiconSearchConsoleSeparator);
			} else if (name.equals(SEARCH_CONSOLE_NAME)){
				// Adds the search console menu item
				add(_searchConsoleMenuItem);
				_searchConsoleInserted = true;
				// Adds the separator
				add(_searchCloseConsoleSeparator);
			} else if (name.equals(CLOSE_CONSOLE_NAME)){
				// Adds the close console menu item
				add(_closeConsoleMenuItem);
				_closeConsoleInserted = true;
			} else {
				if (ob.isSubmenu()){
					add(_insertedMenus.get(ob.getName()));
				}else{
					add(_insertedItems.get(ob.getName()));
				}
			}
		}
		
		if (!_configureInserted)
			add(_configureMenuItem);
		if (!_externalCommandInserted)
			add(_externalCommandMenuItem);
		if (!_consoleDisplayOptionsInserted)
			add(_consoleDisplayOptionsMenuItem);
		if (!_consoleLineWrappingInserted)
			add(_consoleLineWrappingCheckBoxMenuItem);
		if (!_saveConsoleContentInserted)
			add(_saveConsoleContentIntoFile);
		if (!_documentLexiconInserted)
			add(_documentLexiconMenuItem);
		if (!_searchConsoleInserted)
			add(_searchConsoleMenuItem);
		if (!_closeConsoleInserted)
			add(_closeConsoleMenuItem);
	}

	/**
	 * Builds the ACIDE - A Configurable IDE components.
	 */
	private void buildComponents() {
		
		if (!AcideMenuItemsConfiguration.getInstance().getMenuItemsManager()
				.getSubmenu(AcideConfigurationMenu.CONFIGURATION_MENU_NAME).hasSubmenu(CONSOLE_MENU_NAME)){
			AcideMenuItemsConfiguration.getInstance().getMenuItemsManager()
				.getSubmenu(AcideConfigurationMenu.CONFIGURATION_MENU_NAME)
					.insertObject(new AcideMenuSubmenuConfiguration(CONSOLE_MENU_NAME));
		}
		
		Iterator<Object> it = AcideMenuItemsConfiguration.getInstance()
				.getMenuItemsManager().getSubmenu(AcideConfigurationMenu.CONFIGURATION_MENU_NAME)
				.getItemsManager().getSubmenu(CONSOLE_MENU_NAME).getItemsManager().managerIterator();
		
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

		// Creates the configure menu item
		ImageIcon icon = IconsUtils.getIcon(AcideMenuItemsConfiguration.getInstance()
				.getMenuItemsManager().getSubmenu(AcideConfigurationMenu.CONFIGURATION_MENU_NAME)
				.getSubmenu(CONSOLE_MENU_NAME).getItem(CONFIGURE_NAME).getImage());
		
		if (icon != null)
			_configureMenuItem = new JMenuItem(icon);
		else
			_configureMenuItem = new JMenuItem();

		// Sets the configure menu item name
		_configureMenuItem.setName(CONFIGURE_NAME);

		// Creates the external command menu item
		icon = IconsUtils.getIcon(AcideMenuItemsConfiguration.getInstance()
				.getMenuItemsManager().getSubmenu(AcideConfigurationMenu.CONFIGURATION_MENU_NAME)
				.getSubmenu(CONSOLE_MENU_NAME).getItem(EXTERNAL_COMMAND_NAME).getImage());
		
		if (icon != null)
			_externalCommandMenuItem = new JMenuItem(icon);
		else
			_externalCommandMenuItem = new JMenuItem();

		// Sets the external command menu item name
		_externalCommandMenuItem.setName(EXTERNAL_COMMAND_NAME);

		// Creates the external command display options separator
		_externalCommandDisplayOptionsSeparator = new JSeparator();

		// Creates the console display options menu item
		icon = IconsUtils.getIcon(AcideMenuItemsConfiguration.getInstance()
				.getMenuItemsManager().getSubmenu(AcideConfigurationMenu.CONFIGURATION_MENU_NAME)
				.getSubmenu(CONSOLE_MENU_NAME).getItem(CONSOLE_DISPLAY_OPTIONS_NAME).getImage());
		
		if (icon != null)
			_consoleDisplayOptionsMenuItem = new JMenuItem(icon);
		else
			_consoleDisplayOptionsMenuItem = new JMenuItem();

		// Sets the console display options menu item name
		_consoleDisplayOptionsMenuItem.setName(CONSOLE_DISPLAY_OPTIONS_NAME);
		
		// Creates the console line wrapping menu item
		icon = IconsUtils.getIcon(AcideMenuItemsConfiguration.getInstance()
				.getMenuItemsManager()
				.getSubmenu(AcideConfigurationMenu.CONFIGURATION_MENU_NAME)
				.getSubmenu(CONSOLE_MENU_NAME)
				.getItem(CONSOLE_LINE_WRAPPING_NAME).getImage());

		if (icon != null)
			_consoleLineWrappingCheckBoxMenuItem = new JCheckBoxMenuItem(icon);
		else
			_consoleLineWrappingCheckBoxMenuItem = new JCheckBoxMenuItem();

		// Sets the console line wrapping menu item name
		_consoleLineWrappingCheckBoxMenuItem.setName(CONSOLE_LINE_WRAPPING_NAME);

		// Creates the save console content into file menu item
		icon = IconsUtils.getIcon(AcideMenuItemsConfiguration.getInstance()
				.getMenuItemsManager().getSubmenu(AcideConfigurationMenu.CONFIGURATION_MENU_NAME)
				.getSubmenu(CONSOLE_MENU_NAME).getItem(SAVE_CONSOLE_CONTENT_INTO_FILE_NAME).getImage());
		
		if (icon != null)
			_saveConsoleContentIntoFile = new JMenuItem(icon);
		else
			_saveConsoleContentIntoFile = new JMenuItem();

		// Sets the save console content into file menu item name
		_saveConsoleContentIntoFile
				.setName(SAVE_CONSOLE_CONTENT_INTO_FILE_NAME);

		// Creates the document lexicon menu item
		icon = IconsUtils.getIcon(AcideMenuItemsConfiguration.getInstance()
				.getMenuItemsManager().getSubmenu(AcideConfigurationMenu.CONFIGURATION_MENU_NAME)
				.getSubmenu(CONSOLE_MENU_NAME).getItem(DOCUMENT_CONSOLE_LEXICON_NAME).getImage());
		
		if (icon != null)
			_documentLexiconMenuItem = new JMenuItem(icon);
		else
			_documentLexiconMenuItem = new JMenuItem();
		
		// Sets the document lexicon menu item name
		_documentLexiconMenuItem.setName(DOCUMENT_CONSOLE_LEXICON_NAME);

		// Creates the display options search console separator
		_documentConsoleLexiconSearchConsoleSeparator = new JSeparator();

		// Creates the search console menu item
		icon = IconsUtils.getIcon(AcideMenuItemsConfiguration.getInstance()
				.getMenuItemsManager().getSubmenu(AcideConfigurationMenu.CONFIGURATION_MENU_NAME)
				.getSubmenu(CONSOLE_MENU_NAME).getItem(SEARCH_CONSOLE_NAME).getImage());
		
		if (icon != null)
			_searchConsoleMenuItem = new JMenuItem(icon);
		else
			_searchConsoleMenuItem = new JMenuItem();

		// Sets the search console menu item name
		_searchConsoleMenuItem.setName(SEARCH_CONSOLE_NAME);

		// Sets the search console menu item accelerator
		if (AcideLanguageManager.getInstance().getCurrentLocale()
				.equals(new Locale("en", "EN"))
			|| AcideLanguageManager.getInstance().getCurrentLocale()
			.equals(new Locale("fr", "FR")))
			_searchConsoleMenuItem.setAccelerator(KeyStroke.getKeyStroke(
					KeyEvent.VK_F, ActionEvent.CTRL_MASK
							+ ActionEvent.SHIFT_MASK));
		else
			_searchConsoleMenuItem.setAccelerator(KeyStroke.getKeyStroke(
					KeyEvent.VK_B, ActionEvent.CTRL_MASK
							+ ActionEvent.SHIFT_MASK));

		// Creates the search console separator
		_searchCloseConsoleSeparator = new JSeparator();

		// Creates the close console menu item
		icon = IconsUtils.getIcon(AcideMenuItemsConfiguration.getInstance()
				.getMenuItemsManager().getSubmenu(AcideConfigurationMenu.CONFIGURATION_MENU_NAME)
				.getSubmenu(CONSOLE_MENU_NAME).getItem(CLOSE_CONSOLE_NAME).getImage());
		
		if (icon != null)
			_closeConsoleMenuItem = new JMenuItem(icon);
		else
			_closeConsoleMenuItem = new JMenuItem();

		// Sets the close console menu item
		_closeConsoleMenuItem.setName(CLOSE_CONSOLE_NAME);
	}

	/**
	 * Sets the text of the ACIDE - A Configurable IDE console menu components
	 * with the labels in the selected language to display.
	 */
	public void setTextOfMenuComponents() {

		// Sets the configure menu item text
		_configureMenuItem.setText(AcideLanguageManager.getInstance()
				.getLabels().getString("s333"));

		// Sets the external command menu item text
		_externalCommandMenuItem.setText(AcideLanguageManager.getInstance()
				.getLabels().getString("s341"));

		// Sets the console display options menu item text
		_consoleDisplayOptionsMenuItem.setText(AcideLanguageManager
				.getInstance().getLabels().getString("s977"));
		
		// Sets the console display options menu item text
		_consoleLineWrappingCheckBoxMenuItem.setText(AcideLanguageManager
				.getInstance().getLabels().getString("s2014"));

		// Sets the save console content into file menu item text
		_saveConsoleContentIntoFile.setText(AcideLanguageManager.getInstance()
				.getLabels().getString("s2013"));

		// Sets the document lexicon menu item text
		_documentLexiconMenuItem.setText(AcideLanguageManager.getInstance()
				.getLabels().getString("s1093"));

		// Sets the search console menu item text
		_searchConsoleMenuItem.setText(AcideLanguageManager.getInstance()
				.getLabels().getString("s556"));

		// Sets the close console menu item text
		_closeConsoleMenuItem.setText(AcideLanguageManager.getInstance()
				.getLabels().getString("s1099"));
		
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

		AcideMenuItemConfiguration configureConfiguration;
		AcideMenuItemConfiguration externalCommandConfiguration;
		AcideMenuItemConfiguration consoleDisplayOptionsConfiguration;
		AcideMenuItemConfiguration consoleLineWrappingConfiguration;
		AcideMenuItemConfiguration saveConsoleContentIntoFileConfiguration;
		AcideMenuItemConfiguration documentLexiconConfiguration;
		AcideMenuItemConfiguration searchConsoleConfiguration;
		AcideMenuItemConfiguration closeConsoleConfiguration;

		_consoleSubmenuConfiguration = AcideMenuItemsConfiguration.getInstance()
					.getSubmenu(AcideConfigurationMenu.CONFIGURATION_MENU_NAME).getSubmenu(CONSOLE_MENU_NAME);
		
		// Sets the configure menu item to visible or not visible
		configureConfiguration = _consoleSubmenuConfiguration.getItem(CONFIGURE_NAME);
		_configureMenuItem.setVisible(configureConfiguration.isVisible());
		
		// Sets the external command menu item to visible or not visible
		externalCommandConfiguration = _consoleSubmenuConfiguration.getItem(EXTERNAL_COMMAND_NAME);
		_externalCommandMenuItem.setVisible(externalCommandConfiguration.isVisible());
			
		// Sets the console display options menu item to visible or not visible
		consoleDisplayOptionsConfiguration = _consoleSubmenuConfiguration.getItem(CONSOLE_DISPLAY_OPTIONS_NAME);
		_consoleDisplayOptionsMenuItem.setVisible(consoleDisplayOptionsConfiguration.isVisible());
		
		// Sets the console line wrapping menu item to visible or not visible
		consoleLineWrappingConfiguration = _consoleSubmenuConfiguration.getItem(CONSOLE_LINE_WRAPPING_NAME);
		_consoleLineWrappingCheckBoxMenuItem.setVisible(consoleLineWrappingConfiguration.isVisible());

		// Sets the save console content into file menu item to visible or not
		// visible
		saveConsoleContentIntoFileConfiguration = _consoleSubmenuConfiguration.getItem(SAVE_CONSOLE_CONTENT_INTO_FILE_NAME);
		_saveConsoleContentIntoFile.setVisible(saveConsoleContentIntoFileConfiguration.isVisible());
			
		// Sets the document lexicon menu item to visible or not visible
		documentLexiconConfiguration = _consoleSubmenuConfiguration.getItem(DOCUMENT_CONSOLE_LEXICON_NAME);
		_documentLexiconMenuItem.setVisible(documentLexiconConfiguration.isVisible());
			
		// Sets the external command display options separator to visible or not
		// visible
		_externalCommandDisplayOptionsSeparator
				.setVisible((_configureMenuItem.isVisible() || _externalCommandMenuItem.isVisible())
						&& (_consoleDisplayOptionsMenuItem.isVisible()
								|| _consoleLineWrappingCheckBoxMenuItem.isVisible()
								|| _saveConsoleContentIntoFile.isVisible() || 
								_documentLexiconMenuItem.isVisible()));
		
		// Sets the search console menu item to visible or not visible
		searchConsoleConfiguration = _consoleSubmenuConfiguration.getItem(SEARCH_CONSOLE_NAME);
		_searchConsoleMenuItem.setVisible(searchConsoleConfiguration.isVisible());

		// Sets the document console lexicon search console separator to visible
		// or not visible
		_documentConsoleLexiconSearchConsoleSeparator
		.setVisible((_configureMenuItem.isVisible()
				|| _externalCommandMenuItem.isVisible()
				|| _consoleDisplayOptionsMenuItem.isVisible()
				|| _consoleLineWrappingCheckBoxMenuItem.isVisible()
				|| _saveConsoleContentIntoFile.isVisible() || _documentLexiconMenuItem.isVisible())
				&& _searchConsoleMenuItem.isVisible());
			
		// Sets the close console menu item to visible or not visible
		closeConsoleConfiguration = _consoleSubmenuConfiguration.getItem(CLOSE_CONSOLE_NAME);
		_closeConsoleMenuItem.setVisible(closeConsoleConfiguration.isVisible());

		// Sets the search close console separator to visible or not visible
		_searchCloseConsoleSeparator.setVisible(_configureMenuItem.isVisible()
				|| _externalCommandMenuItem.isVisible()
				|| _consoleDisplayOptionsMenuItem.isVisible()
				|| _consoleLineWrappingCheckBoxMenuItem.isVisible()
				|| _saveConsoleContentIntoFile.isVisible()
				|| _documentLexiconMenuItem.isVisible()
				|| _searchConsoleMenuItem.isVisible()
				|| _closeConsoleMenuItem.isVisible());

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
		_consoleSubmenuConfiguration.setVisible(_configureMenuItem.isVisible()
				|| _externalCommandMenuItem.isVisible()
				|| _consoleDisplayOptionsMenuItem.isVisible()
				|| _consoleLineWrappingCheckBoxMenuItem.isVisible());
		_consoleSubmenuConfiguration.setErasable(false);
		
						
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
		if (!(name.equals(CONFIGURE_NAME))
			&& !(name.equals(EXTERNAL_COMMAND_NAME))
			&& !(name.equals(CONSOLE_DISPLAY_OPTIONS_NAME))
			&& !(name.equals(CONSOLE_LINE_WRAPPING_NAME))
			&& !(name.equals(SAVE_CONSOLE_CONTENT_INTO_FILE_NAME))
			&& !(name.equals(DOCUMENT_CONSOLE_LEXICON_NAME))
			&& !(name.equals(SEARCH_CONSOLE_NAME))
			&& !(name.equals(CLOSE_CONSOLE_NAME))){
			return true;
		}else {
			return false;
		}
	}

	/**
	 * Sets ACIDE - A Configurable IDE console menu item listeners.
	 */
	public void setListeners() {

		// Sets the configure menu item action listener
		_configureMenuItem
				//.addActionListener(new AcideConfigureMenuItemListener());
		.addActionListener(new AcideInsertedItemListener(
				AcideMenuItemsConfiguration.getInstance()
				.getSubmenu(AcideConfigurationMenu.CONFIGURATION_MENU_NAME)
				.getSubmenu(CONSOLE_MENU_NAME).getItem(CONFIGURE_NAME)));

		// Sets the external command menu item action listener
		_externalCommandMenuItem
				//.addActionListener(new AcideExternalCommandMenuItemListener());
			.addActionListener(new AcideInsertedItemListener(
				AcideMenuItemsConfiguration.getInstance()
				.getSubmenu(AcideConfigurationMenu.CONFIGURATION_MENU_NAME)
				.getSubmenu(CONSOLE_MENU_NAME).getItem(EXTERNAL_COMMAND_NAME)));

		// Sets the console display options menu item action listener
		_consoleDisplayOptionsMenuItem
				//.addActionListener(new AcideConsoleDisplayOptionsMenuItemListener());
			.addActionListener(new AcideInsertedItemListener(
				AcideMenuItemsConfiguration.getInstance()
				.getSubmenu(AcideConfigurationMenu.CONFIGURATION_MENU_NAME)
				.getSubmenu(CONSOLE_MENU_NAME).getItem(CONSOLE_DISPLAY_OPTIONS_NAME)));
		
		// Sets the console line wrapping menu item action listener
		_consoleLineWrappingCheckBoxMenuItem
				// .addActionListener(new
				// AcideConsoleDisplayOptionsMenuItemListener());
				.addActionListener(new AcideInsertedItemListener(
						AcideMenuItemsConfiguration
								.getInstance()
								.getSubmenu(
										AcideConfigurationMenu.CONFIGURATION_MENU_NAME)
								.getSubmenu(CONSOLE_MENU_NAME)
								.getItem(CONSOLE_LINE_WRAPPING_NAME)));

		// Sets the save console content into file menu item action listener
		_saveConsoleContentIntoFile
				//.addActionListener(new AcideSaveConsoleContentIntoFileMenuItemListener());
			.addActionListener(new AcideInsertedItemListener(
				AcideMenuItemsConfiguration.getInstance()
				.getSubmenu(AcideConfigurationMenu.CONFIGURATION_MENU_NAME)
				.getSubmenu(CONSOLE_MENU_NAME).getItem(SAVE_CONSOLE_CONTENT_INTO_FILE_NAME)));

		// Sets the document lexicon menu item action listener
		_documentLexiconMenuItem
				//.addActionListener(new AcideDocumentConsoleLexiconMenuItemListener());
			.addActionListener(new AcideInsertedItemListener(
				AcideMenuItemsConfiguration.getInstance()
				.getSubmenu(AcideConfigurationMenu.CONFIGURATION_MENU_NAME)
				.getSubmenu(CONSOLE_MENU_NAME).getItem(DOCUMENT_CONSOLE_LEXICON_NAME)));

		// Sets the search console menu item action listener
		_searchConsoleMenuItem
				//.addActionListener(new AcideSearchConsoleMenuItemListener());
			.addActionListener(new AcideInsertedItemListener(
				AcideMenuItemsConfiguration.getInstance()
				.getSubmenu(AcideConfigurationMenu.CONFIGURATION_MENU_NAME)
				.getSubmenu(CONSOLE_MENU_NAME).getItem(SEARCH_CONSOLE_NAME)));

		// Sets the close console menu item action listener
		_closeConsoleMenuItem
				//.addActionListener(new AcideCloseConsoleMenuItemListener());
			.addActionListener(new AcideInsertedItemListener(
				AcideMenuItemsConfiguration.getInstance()
				.getSubmenu(AcideConfigurationMenu.CONFIGURATION_MENU_NAME)
				.getSubmenu(CONSOLE_MENU_NAME).getItem(CLOSE_CONSOLE_NAME)));
		
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
	 * Returns the ACIDE - A Configurable IDE console menu external command menu
	 * item.
	 * 
	 * @return the ACIDE - A Configurable IDE console menu external command
	 *         menu. item
	 */
	public JMenuItem getExternalCommandMenuItem() {
		return _externalCommandMenuItem;
	}

	/**
	 * Returns the ACIDE - A Configurable IDE console menu configure menu item.
	 * 
	 * @return the ACIDE - A Configurable IDE console menu configure menu item.
	 */
	public JMenuItem getConfigureMenuItem() {
		return _configureMenuItem;
	}

	/**
	 * Returns the ACIDE - A Configurable IDE console menu console display
	 * options menu item.
	 * 
	 * @return the ACIDE - A Configurable IDE console menu console display
	 *         options menu item.
	 */
	public JMenuItem getConsoleDisplayOptionsMenuItem() {
		return _consoleDisplayOptionsMenuItem;
	}
	
	/**
	 * Returns the ACIDE - A Configurable IDE console line wrapping 
	 *  Check Box menu item.
	 * 
	 * @return the ACIDE - A Configurable IDE console line wrapping
	 *         CheckBox menu item.
	 */
	public JCheckBoxMenuItem getConsoleLineWrappingCheckBoxMenuItem() {
		return _consoleLineWrappingCheckBoxMenuItem;
	}

	/**
	 * Returns the ACIDE - A Configurable IDE console menu close console menu
	 * item.
	 * 
	 * @return the ACIDE - A Configurable IDE console menu close console menu
	 *         item.
	 */
	public JMenuItem getCloseConsoleMenuItem() {
		return _closeConsoleMenuItem;
	}

	/**
	 * Returns the ACIDE - A Configurable IDE console menu search console menu
	 * item.
	 * 
	 * @return the ACIDE - A Configurable IDE console menu search console menu
	 *         item.
	 */
	public JMenuItem getSearchConsoleMenuItem() {
		return _searchConsoleMenuItem;
	}

	/**
	 * Returns the ACIDE - A Configurable IDE console menu document console
	 * lexicon menu item.
	 * 
	 * @return the ACIDE - A Configurable IDE console menu document console
	 *         lexicon menu item.
	 */
	public JMenuItem getDocumentConsoleLexiconMenuItem() {
		return _documentLexiconMenuItem;
	}

	/**
	 * Returns the ACIDE - A Configurable IDE console menu save console content
	 * into file menu item.
	 * 
	 * @return the ACIDE - A Configurable IDE console menu save console content
	 *         into file menu item.
	 */
	public JMenuItem getSaveConsoleContentIntoFileMenuItem() {
		return _saveConsoleContentIntoFile;
	}
}