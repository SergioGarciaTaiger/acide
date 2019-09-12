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
package acide.gui.menuBar.configurationMenu;

import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import javax.swing.ImageIcon;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JSeparator;

import acide.configuration.menu.AcideInsertedItem;
import acide.configuration.menu.AcideInsertedItemListener;
import acide.configuration.menu.AcideInsertedMenu;
import acide.configuration.menu.AcideMenuItemConfiguration;
import acide.configuration.menu.AcideMenuItemsConfiguration;
import acide.configuration.menu.AcideMenuObjectConfiguration;
import acide.configuration.menu.AcideMenuSubmenuConfiguration;
import acide.gui.menuBar.configurationMenu.consoleMenu.AcideConsoleMenu;
import acide.gui.menuBar.configurationMenu.databasePanelMenu.AcideDatabasePanelMenu;
import acide.gui.menuBar.configurationMenu.debugPanelMenu.AcideDebugPanelMenu;
import acide.gui.menuBar.configurationMenu.fileEditor.AcideFileEditorMenu;
import acide.gui.menuBar.configurationMenu.grammarMenu.AcideGrammarMenu;
import acide.gui.menuBar.configurationMenu.graphPanelMenu.AcideGraphPanelMenu;
import acide.gui.menuBar.configurationMenu.languageMenu.AcideLanguageMenu;
import acide.gui.menuBar.configurationMenu.lexiconMenu.AcideLexiconMenu;
import acide.gui.menuBar.configurationMenu.menuMenu.AcideMenuMenu;
import acide.gui.menuBar.configurationMenu.toolBarMenu.AcideToolBarMenu;
import acide.gui.menuBar.listeners.AcideMenuBarMouseClickListener;
import acide.language.AcideLanguageManager;
import acide.utils.IconsUtils;

/**
 * ACIDE - A Configurable IDE configuration menu.
 * 
 * @version 0.11
 * @see ActionListener
 */
public class AcideConfigurationMenu extends JMenu {

	/**
	 * ACIDE - A Configurable IDE configuration menu class serial version UID.
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * ACIDE - A Configurable IDE file menu name.
	 */
	public final static String CONFIGURATION_MENU_NAME = "Configuration";
	/**
	 * ACIDE - A Configurable IDE configuration menu lexicon menu item name.
	 */
	public static final String LEXICON_NAME = "Lexicon";
	/**
	 * ACIDE - A Configurable IDE configuration menu grammar menu item name.
	 */
	public static final String GRAMMAR_NAME = "Grammar";
	/**
	 * ACIDE - A Configurable IDE configuration menu language menu item name.
	 */
	public static final String LANGUAGE_NAME = "Language";
	/**
	 * ACIDE - A Configurable IDE configuration menu file editor menu item name.
	 */
	public static final String FILE_EDITOR_NAME = "File Editor";
	/**
	 * ACIDE - A Configurable IDE configuration menu menu menu item name.
	 */
	public static final String MENU_NAME = "Menu";
	/**
	 * ACIDE - A Configurable IDE configuration menu console menu item name.
	 */
	public static final String CONSOLE_NAME = "Shell";
	/**
	 * ACIDE - A Configurable IDE configuration menu database panel menu item
	 * name.
	 */
	public static final String DATABASE_PANEL_NAME = "Database Panel";
	/**
	 * ACIDE - A Configurable IDE configuration menu toolbar menu item name.
	 */
	public static final String TOOLBAR_NAME = "Tool Bar";
	/**
	 * ACIDE - A Configurable IDE configuration menu compiler menu item name.
	 */
	public static final String COMPILER_NAME = "Compiler";
	/**
	 * ACIDE - A Configurable IDE configuration menu graph panel menu item name.
	 */
	public static final String GRAPH_PANEL_NAME = "Graph Panel";
	/**
	 * ACIDE - A Configurable IDE configuration menu console line wrapping menu
	 * item name.
	 */
	public static final String CONSOLE_LINE_WRAPPING_NAME = "Console Line Wrapping";

	/**
	 * ACIDE - A Configurable IDE configuration menu debug panel menu item name.
	 */	
	public static final String DEBUG_PANEL_NAME = "Debug Panel";
	/**
	 * ACIDE - A Configurable IDE configuration menu compiler menu item image
	 * icon.
	 */
	public static final ImageIcon COMPILER_IMAGE = new ImageIcon(
			"./resources/icons/menu/configuration/compiler.png");
	/**
	 * ACIDE - A Configurable IDE configuration menu menu menu item.
	 */
	private AcideMenuMenu _menuMenu;
	/**
	 * ACIDE - A Configurable IDE configuration menu menu menu item has been
	 * inserted.
	 */
	private boolean _menuInserted;
	/**
	 * ACIDE - A Configurable IDE configuration menu language menu item.
	 */
	private AcideLanguageMenu _languageMenu;
	/**
	 * ACIDE - A Configurable IDE configuration language menu menu item has been
	 * inserted.
	 */
	private boolean _languageInserted;
	/**
	 * ACIDE - A Configurable IDE configuration menu file editor menu item.
	 */
	private AcideFileEditorMenu _fileEditorMenu;
	/**
	 * ACIDE - A Configurable IDE configuration file editor menu menu item has
	 * been inserted.
	 */
	private boolean _fileEditorInserted;
	/**
	 * ACIDE - A Configurable IDE configuration menu console menu item.
	 */
	private AcideConsoleMenu _consoleMenu;
	/**
	 * ACIDE - A Configurable IDE configuration console menu menu item has been
	 * inserted.
	 */
	private boolean _consoleInserted;
	/**
	 * ACIDE - A Configurable IDE configuration menu graph panel menu.
	 */
	private AcideGraphPanelMenu _graphPanelMenu;

	/**
	 * ACIDE - A Configurable IDE configuration graph panel menu menu item has
	 * been inserted.
	 */
	private boolean _graphPanelInserted;
	/**
	 * ACIDE - A Configurable IDE configuration menu debug panel menu.
	 */
	private AcideDebugPanelMenu _debugPanelMenu;
	/**
	 * ACIDE - A Configurable IDE configuration debug panel menu menu has
	 * been inserted.
	 */
	private boolean _debugPanelInserted;

	/**
	 * ACIDE - A Configurable IDE configuration menu database panel menu item.
	 */
	private AcideDatabasePanelMenu _databasePanelMenu;
	/**
	 * ACIDE - A Configurable IDE configuration database panel menu menu item
	 * has been inserted.
	 */
	private boolean _databasePanelInserted;
	/**
	 * ACIDE - A Configurable IDE configuration menu tool bar menu item.
	 */
	private AcideToolBarMenu _toolBarMenu;
	/**
	 * ACIDE - A Configurable IDE configuration toolbar menu menu item has been
	 * inserted.
	 */
	private boolean _toolbarInserted;
	/**
	 * ACIDE - A Configurable IDE configuration menu lexicon menu item.
	 */
	private AcideLexiconMenu _lexiconMenu;
	/**
	 * ACIDE - A Configurable IDE configuration lexicon menu menu item has been
	 * inserted.
	 */
	private boolean _lexiconInserted;
	/**
	 * ACIDE - A Configurable IDE configuration menu grammar menu item.
	 */
	private AcideGrammarMenu _grammarMenu;
	/**
	 * ACIDE - A Configurable IDE configuration grammar menu menu item has been
	 * inserted.
	 */
	private boolean _grammarInserted;
	/**
	 * ACIDE - A Configurable IDE configuration menu compiler menu item.
	 */
	private JMenuItem _compilerMenuItem;
	/**
	 * ACIDE - A Configurable IDE configuration compiler menu item has been
	 * inserted.
	 */
	private boolean _compilerInserted;
	/**
	 * ACIDE - A Configurable IDE configuration menu compiler file editor
	 * separator.
	 */
	private JSeparator _compilerFileEditorSeparator;
	/**
	 * ACIDE - A Configurable IDE configuration menu console language separator.
	 */
	private JSeparator _debugLanguageSeparator;

	/**
	 * ACIDE - A Configurable IDE configuration submenu.
	 */
	private AcideMenuSubmenuConfiguration _configurationSubmenuConfiguration;
	/**
	 * ACIDE - A Configurable IDE hashmap with the inserted menus.
	 */
	private HashMap<String, AcideInsertedMenu> _insertedMenus;
	/**
	 * ACIDE - A Configurable IDE hashmap with the inserted items.
	 */
	private HashMap<String, AcideInsertedItem> _insertedItems;
	/**
	 * ACIDE - A Configurable IDE array list with the inserted objects.
	 */
	private ArrayList<AcideMenuObjectConfiguration> _insertedObjects;

	/**
	 * Creates a new ACIDE - A Configurable IDE configuration menu.
	 */
	public AcideConfigurationMenu() {

		_menuInserted = false;
		_languageInserted = false;
		_fileEditorInserted = false;
		_consoleInserted = false;
		_databasePanelInserted = false;
		_graphPanelInserted = false;
		_toolbarInserted = false;
		_lexiconInserted = false;
		_grammarInserted = false;
		_compilerInserted = false;
		_graphPanelInserted = false;
		_debugPanelInserted = false;

		_insertedItems = new HashMap<String, AcideInsertedItem>();

		_insertedMenus = new HashMap<String, AcideInsertedMenu>();

		_insertedObjects = new ArrayList<AcideMenuObjectConfiguration>();

		// Builds the menu components
		buildComponents();

		// Adds the components to the menu
		addComponents();

		// Sets the text of the configuration menu components
		setTextOfMenuComponents();
	}

	/**
	 * Adds the components to the ACIDE - A Configurable IDE configuration menu.
	 */
	private void addComponents() {
		
		Iterator<Object> it = AcideMenuItemsConfiguration.getInstance()
				.getMenuItemsManager().getSubmenu(CONFIGURATION_MENU_NAME).getItemsManager().managerIterator();
		while (it.hasNext()){
			AcideMenuObjectConfiguration ob = (AcideMenuObjectConfiguration) it.next();
			String name = ob.getName();
			if (name.equals(LEXICON_NAME)){
				// Adds the lexicon menu to the menu
				add(_lexiconMenu);
				_lexiconInserted = true;
			}else if (name.equals(GRAMMAR_NAME)){
				// Adds the grammar menu to the menu
				add(_grammarMenu);
				_grammarInserted = true;
			}else if (name.equals(COMPILER_NAME)){
				// Adds the compiler menu item to the menu
				add(_compilerMenuItem);
				_compilerInserted = true;
				// Adds the compiler file editor separator
				add(_compilerFileEditorSeparator);
			}else if (name.equals(FILE_EDITOR_NAME)){
				// Adds the file editor menu to the menu
				add(_fileEditorMenu);
				_fileEditorInserted = true;
			}else if (name.equals(CONSOLE_NAME)){
				// Adds the console menu to the menu
				add(_consoleMenu);
				_consoleInserted = true;
			}else if (name.equals(DATABASE_PANEL_NAME)){
				// Adds the database panel menu to the menu
				add(_databasePanelMenu);
				_databasePanelInserted = true;				
			}else if(name.equals(GRAPH_PANEL_NAME)){
				// Adds the database panel menu to the menu
				add(_graphPanelMenu);
				_graphPanelInserted = true;				
			}else if(name.equals(DEBUG_PANEL_NAME)){
				add(_debugPanelMenu);
				_debugPanelInserted = true;
				// Adds the console language separator to the menu
				add(_debugLanguageSeparator);
			}else if (name.equals(LANGUAGE_NAME)){
				// Adds the language menu to the menu
				add(_languageMenu);
				_languageInserted = true;
			}else if (name.equals(MENU_NAME)){
				// Adds the menu menu to the menu
				add(_menuMenu);
				_menuInserted = true;
			}else if (name.equals(TOOLBAR_NAME)){
				// Adds the tool bar menu to the menu
				add(_toolBarMenu);
				_toolbarInserted = true;
			}else {				
				if (ob.isSubmenu()){
					add(_insertedMenus.get(ob.getName()));
				}else{
					add(_insertedItems.get(ob.getName()));
				}
			}
		}

		if (!_lexiconInserted)
			add(_lexiconMenu);
		if (!_grammarInserted)
			add(_grammarMenu);
		if (!_compilerInserted)
			add(_compilerMenuItem);
		if (!_fileEditorInserted)
			add(_fileEditorMenu);
		if (!_consoleInserted)
			add(_consoleMenu);
		if (!_databasePanelInserted)
			add(_databasePanelMenu);
		if(!_graphPanelInserted)
			add(_graphPanelMenu);
		if (!_languageInserted)
			add(_languageMenu);
		if (!_menuInserted)
			add(_menuMenu);
		 if (!_toolbarInserted)
			 add(_toolBarMenu);
		 if(!_debugPanelInserted)
			 add(_debugPanelMenu);
		
	}

	/**
	 * Builds the ACIDE - A Configurable IDE configuration menu components.
	 */
	private void buildComponents() {

		if (!AcideMenuItemsConfiguration.getInstance().getMenuItemsManager()
				.hasSubmenu(CONFIGURATION_MENU_NAME)) {
			AcideMenuItemsConfiguration.getInstance().insertObject(
					new AcideMenuSubmenuConfiguration(CONFIGURATION_MENU_NAME));
		}

		Iterator<Object> it = AcideMenuItemsConfiguration.getInstance()
				.getMenuItemsManager().getSubmenu(CONFIGURATION_MENU_NAME)
				.getItemsManager().managerIterator();

		while (it.hasNext()) {
			AcideMenuObjectConfiguration ob = (AcideMenuObjectConfiguration) it
					.next();
			String name = ob.getName();
			if (isOriginal(name)) {
				_insertedObjects.add(ob);
				if (ob.isSubmenu()) {
					AcideMenuSubmenuConfiguration obSubmenu = (AcideMenuSubmenuConfiguration) ob;
					_insertedMenus.put(ob.getName(), new AcideInsertedMenu(
							obSubmenu));
				} else {
					AcideMenuItemConfiguration obItem = (AcideMenuItemConfiguration) ob;
					_insertedItems.put(obItem.getName(), new AcideInsertedItem(
							IconsUtils.getIcon(obItem.getImage()), obItem));
				}
			}
		}

		// Creates the console menu
		_consoleMenu = new AcideConsoleMenu();

		// Sets the console menu name
		_consoleMenu.setName(CONSOLE_NAME);

		// Creates the graph panel menu
		_graphPanelMenu = new AcideGraphPanelMenu();

		// Sets the graph panel menu name
		_graphPanelMenu.setName(GRAPH_PANEL_NAME);

		// Creates the debug panel menu
		_debugPanelMenu = new AcideDebugPanelMenu();

		// Sets the debug panel menu name
		_debugPanelMenu.setName(DEBUG_PANEL_NAME);

		// Creates the database panel menu
		_databasePanelMenu = new AcideDatabasePanelMenu();

		// Sets the database panel menu name
		_databasePanelMenu.setName(DATABASE_PANEL_NAME);

		// Creates the file editor menu
		_fileEditorMenu = new AcideFileEditorMenu();

		// Sets the file editor menu menu name
		_fileEditorMenu.setName(FILE_EDITOR_NAME);

		// Creates the language menu
		_languageMenu = new AcideLanguageMenu();

		// Sets the language menu menu name
		_languageMenu.setName(LANGUAGE_NAME);

		// Creates the menu menu
		_menuMenu = new AcideMenuMenu();

		// Sets the menu menu menu name
		_menuMenu.setName(MENU_NAME);

		// Creates the tool bar menu
		_toolBarMenu = new AcideToolBarMenu();

		// Sets the tool bar menu menu name
		_toolBarMenu.setName(TOOLBAR_NAME);

		// Creates the lexicon menu
		_lexiconMenu = new AcideLexiconMenu();

		// Sets the lexicon menu menu name
		_lexiconMenu.setName(LEXICON_NAME);

		// Creates the grammar menu
		_grammarMenu = new AcideGrammarMenu();

		// Sets the grammar menu menu name
		_grammarMenu.setName(GRAMMAR_NAME);

		// Creates the compiler menu item
		ImageIcon icon = IconsUtils.getIcon(AcideMenuItemsConfiguration
				.getInstance().getMenuItemsManager()
				.getSubmenu(AcideConfigurationMenu.CONFIGURATION_MENU_NAME)
				.getItem(COMPILER_NAME).getImage());

		if (icon != null)
			_compilerMenuItem = new JMenuItem(icon);
		else
			_compilerMenuItem = new JMenuItem();

		// Sets the compiler menu menu name
		_compilerMenuItem.setName(COMPILER_NAME);

		// Creates the compiler file editor separator
		_compilerFileEditorSeparator = new JSeparator();

		// Creates the console language separator
		_debugLanguageSeparator = new JSeparator();
	}

	/**
	 * Sets the text of the ACIDE - A Configurable IDE configuration menu
	 * components with the labels in the selected language to display.
	 */
	public void setTextOfMenuComponents() {

		// Sets the language menu text
		_languageMenu.setText(AcideLanguageManager.getInstance().getLabels()
				.getString("s6"));

		// Sets the language menu items text
		_languageMenu.setTextOfMenuComponents();

		// Sets the console menu text
		_consoleMenu.setText(AcideLanguageManager.getInstance().getLabels()
				.getString("s332"));

		// Sets the console menu items text
		_consoleMenu.setTextOfMenuComponents();

		// Sets the graph panel menu text
		_graphPanelMenu.setText(AcideLanguageManager.getInstance().getLabels()
				.getString("s2231"));

		// Sets the graph panel menu items text
		_graphPanelMenu.setTextOfMenuComponents();

		// Sets the debug panel menu text
		_debugPanelMenu.setText(AcideLanguageManager.getInstance().getLabels()
				.getString("s2243"));

		// Sets the debug panel menu items text
		_debugPanelMenu.setTextOfMenuComponents();

		// Sets the database panel menu text
		_databasePanelMenu.setText(AcideLanguageManager.getInstance()
				.getLabels().getString("s2159"));

		// Sets the console menu items text
		_databasePanelMenu.setTextOfMenuComponents();

		// Sets the file editor menu text
		_fileEditorMenu.setText(AcideLanguageManager.getInstance().getLabels()
				.getString("s1045"));

		// Sets the file editor menu items text
		_fileEditorMenu.setTextOfMenuComponets();

		// Sets the menu menu text
		_menuMenu.setText(AcideLanguageManager.getInstance().getLabels()
				.getString("s34"));

		// Sets the menu menu items text
		_menuMenu.setTextOfMenuComponents();

		// Sets the tool bar menu text
		_toolBarMenu.setText(AcideLanguageManager.getInstance().getLabels()
				.getString("s169"));

		// Sets the tool bar menu items text
		_toolBarMenu.setTextOfMenuComponents();

		// Sets the lexicon menu text
		_lexiconMenu.setText(AcideLanguageManager.getInstance().getLabels()
				.getString("s224"));

		// Sets the lexicon menu items text
		_lexiconMenu.setTextOfMenuComponents();

		// Sets the grammar menu text
		_grammarMenu.setText(AcideLanguageManager.getInstance().getLabels()
				.getString("s225"));

		// Sets the grammar menu items text
		_grammarMenu.setTextOfMenuComponents();

		// Sets the compiler menu item text
		_compilerMenuItem.setText(AcideLanguageManager.getInstance()
				.getLabels().getString("s240"));

		Iterator<AcideMenuObjectConfiguration> it = _insertedObjects.iterator();
		while (it.hasNext()) {
			AcideMenuObjectConfiguration ob = it.next();
			if (ob.isSubmenu()) {
				_insertedMenus.get(ob.getName()).setText(ob.getName());
				_insertedMenus.get(ob.getName()).setTextOfMenuComponents();
			} else {
				_insertedItems.get(ob.getName()).setText(ob.getName());
			}
		}
	}

	/**
	 * Updates the ACIDE - A Configurable IDE configuration menu components
	 * visibility with the menu configuration.
	 */
	public void updateComponentsVisibility() {

		AcideMenuItemConfiguration compilerConfiguration;

		_configurationSubmenuConfiguration = AcideMenuItemsConfiguration
				.getInstance().getSubmenu(
						AcideConfigurationMenu.CONFIGURATION_MENU_NAME);

		// Builds the lexicon menu
		_lexiconMenu.build();

		// Sets the lexicon menu to visible or not visible
		_lexiconMenu.setVisible((AcideMenuItemsConfiguration.getInstance()
				.getSubmenu(CONFIGURATION_MENU_NAME)).getSubmenu(
				AcideLexiconMenu.LEXICON_MENU_NAME).isVisible());

		// Builds the grammar menu
		_grammarMenu.updateComponentsVisibility();

		// Sets the grammar menu to visible or not visible
		_grammarMenu.setVisible((AcideMenuItemsConfiguration.getInstance()
				.getSubmenu(CONFIGURATION_MENU_NAME)).getSubmenu(
				AcideGrammarMenu.GRAMMAR_MENU_NAME).isVisible());

		// Sets the compiler menu item to visible or not visible
		// Sets the show log tab menu item to visible or not visible
		compilerConfiguration = _configurationSubmenuConfiguration
				.getItem(COMPILER_NAME);
		_compilerMenuItem.setVisible(compilerConfiguration.isVisible());

		// Builds the file editor menu
		_fileEditorMenu.updateComponentsVisibility();

		// Sets the file editor menu to visible or not visible
		_fileEditorMenu.setVisible((AcideMenuItemsConfiguration.getInstance()
				.getSubmenu(CONFIGURATION_MENU_NAME)).getSubmenu(
				AcideFileEditorMenu.FILE_EDITOR_MENU_NAME).isVisible());

		// builds the console menu
		_consoleMenu.updateComponentsVisibility();

		// Sets the console menu to visible or not visible
		_consoleMenu.setVisible((AcideMenuItemsConfiguration.getInstance()
				.getSubmenu(CONFIGURATION_MENU_NAME)).getSubmenu(
				AcideConsoleMenu.CONSOLE_MENU_NAME).isVisible());

		// Sets the graph menu to visible or not visible
		_graphPanelMenu.setVisible((AcideMenuItemsConfiguration.getInstance()
				.getSubmenu(CONFIGURATION_MENU_NAME)).getSubmenu(
				AcideGraphPanelMenu.GRAPH_MENU_NAME).isVisible());

		// builds the graph panel menu
		_graphPanelMenu.updateComponentsVisibility();

		// Sets the debug menu to visible or not visible
		_debugPanelMenu.setVisible((AcideMenuItemsConfiguration.getInstance()
				.getSubmenu(CONFIGURATION_MENU_NAME)).getSubmenu(
				AcideDebugPanelMenu.DEBUG_MENU_NAME).isVisible());

		// builds the debug panel menu
		_debugPanelMenu.updateComponentsVisibility();

		// builds the database panel menu
		_databasePanelMenu.updateComponentsVisibility();

		// Sets the console menu to visible or not visible
		_databasePanelMenu.setVisible((AcideMenuItemsConfiguration
				.getInstance().getSubmenu(CONFIGURATION_MENU_NAME)).getSubmenu(
				AcideDatabasePanelMenu.DATABASE_MENU_NAME).isVisible());

		// Sets the compiler filer editor separator to visible or not visible
		_compilerFileEditorSeparator
				.setVisible((_lexiconMenu.getDocumentLexiconMenuItem()
						.isVisible()
						|| _lexiconMenu.getModifyLexiconMenuItem().isVisible()
						|| _grammarMenu.getNewGrammarMenuItem().isVisible()
						|| _grammarMenu.getLoadGrammarMenuItem().isVisible()
						|| _grammarMenu.getModifyGrammarMenuItem().isVisible()
						|| _compilerMenuItem.isVisible()
						|| _lexiconMenu.getNewLexiconMenuItem().isVisible() || _grammarMenu
						.getSaveGrammarMenuItem().isVisible())
						&& (_consoleMenu.getConfigureMenuItem().isVisible() || _consoleMenu
								.getExternalCommandMenuItem().isVisible()));

		// Builds the language menu
		_languageMenu.updateComponentsVisibility();

		// Sets the language menu to visible or not visible
		_languageMenu.setVisible((AcideMenuItemsConfiguration.getInstance()
				.getSubmenu(CONFIGURATION_MENU_NAME)).getSubmenu(
				AcideLanguageMenu.LANGUAGE_MENU_NAME).isVisible());

		// Builds the menu menu
		_menuMenu.updateComponentsVisibility();

		// Sets the menu menu to visible or not visible
		_menuMenu.setVisible((AcideMenuItemsConfiguration.getInstance()
				.getSubmenu(CONFIGURATION_MENU_NAME)).getSubmenu(
				AcideMenuMenu.MENU_MENU_NAME).isVisible());

		// Builds the tool bar menu
		_toolBarMenu.updateComponentsVisibility();

		// Sets the tool bar menu to visible or not visible
		_toolBarMenu.setVisible((AcideMenuItemsConfiguration.getInstance()
				.getSubmenu(CONFIGURATION_MENU_NAME)).getSubmenu(
				AcideToolBarMenu.TOOLBAR_MENU_NAME).isVisible());

		// Sets the console language separator to visible or not visible
		_debugLanguageSeparator
				.setVisible((_lexiconMenu.getDocumentLexiconMenuItem()
						.isVisible()
						|| _lexiconMenu.getModifyLexiconMenuItem().isVisible()
						|| _grammarMenu.getNewGrammarMenuItem().isVisible()
						|| _grammarMenu.getLoadGrammarMenuItem().isVisible()
						|| _grammarMenu.getModifyGrammarMenuItem().isVisible()
						|| _compilerMenuItem.isVisible()
						|| _consoleMenu.getConfigureMenuItem().isVisible()
						|| _consoleMenu.getExternalCommandMenuItem()
								.isVisible()
						|| _consoleMenu.getConsoleDisplayOptionsMenuItem()
								.isVisible()
						|| _lexiconMenu.getNewLexiconMenuItem().isVisible() || _grammarMenu
						.getSaveGrammarMenuItem().isVisible())
						|| _graphPanelMenu.isVisible()
						|| _debugPanelMenu.isVisible()
						&& (_menuMenu.isVisible() || _toolBarMenu.isVisible()));

		_configurationSubmenuConfiguration.setVisible(true);
		_configurationSubmenuConfiguration.setErasable(false);

		Iterator<AcideMenuObjectConfiguration> it = _insertedObjects.iterator();
		while (it.hasNext()) {
			AcideMenuObjectConfiguration ob = it.next();
			if (ob.isSubmenu()) {
				_insertedMenus.get(ob.getName()).updateComponentsVisibility();
				_insertedMenus.get(ob.getName()).setVisible(ob.isVisible());
			} else {
				_insertedItems.get(ob.getName()).setVisible(ob.isVisible());
			}
		}
	}

	/**
	 * Gets if the menu name given as parameter is original
	 * 
	 * @param name
	 *            the name we want to check
	 * @return if the name given as parameter is original
	 */
	public boolean isOriginal(String name) {
		if (!(name.equals(LEXICON_NAME)) && !(name.equals(GRAMMAR_NAME))
				&& !(name.equals(FILE_EDITOR_NAME))
				&& !(name.equals(CONSOLE_NAME))
				&& !(name.equals(DATABASE_PANEL_NAME))
				&& !(name.equals(MENU_NAME)) && !(name.equals(LANGUAGE_NAME))
				&& !(name.equals(TOOLBAR_NAME))
				&& !(name.equals(COMPILER_NAME))
				&& !(name.equals(GRAPH_PANEL_NAME))
				&& !(name.equals(DEBUG_PANEL_NAME))) {
			return true;
		} else
			return false;
	}

	/**
	 * Sets the ACIDE - A Configurable IDE configuration menu menu item
	 * listeners.
	 */
	public void setListeners() {

		// Sets the lexicon menu listeners
		_lexiconMenu.setListeners();

		// Sets the grammar menu listeners
		_grammarMenu.setListeners();

		// Sets the file editor menu listeners
		_fileEditorMenu.setListeners();

		// Sets the console menu listeners
		_consoleMenu.setListeners();

		// Sets the graph menu listeners
		_graphPanelMenu.setListeners();
		
		// Sets the debug menu listeners
		_debugPanelMenu.setListeners();

		// Sets the database menu listeners
		_databasePanelMenu.setListeners();

		// Sets the menu menu listeners
		_menuMenu.setListeners();

		// Sets the language menu listeners
		_languageMenu.setListeners();

		// Sets the tool bar menu listeners
		_toolBarMenu.setListeners();

		// Sets the compiler menu item listener
		_compilerMenuItem

		.addActionListener(new AcideInsertedItemListener(
				AcideMenuItemsConfiguration
						.getInstance()
						.getSubmenu(
								AcideConfigurationMenu.CONFIGURATION_MENU_NAME)
						.getItem(COMPILER_NAME)));

		Iterator<AcideMenuObjectConfiguration> it = _insertedObjects.iterator();
		while (it.hasNext()) {
			AcideMenuObjectConfiguration ob = it.next();
			if (ob.isSubmenu()) {
				_insertedMenus.get(ob.getName()).addMouseListener(
						new AcideMenuBarMouseClickListener());
				_insertedMenus.get(ob.getName()).setListeners();
			} else {
				AcideInsertedItem aux = _insertedItems.get(ob.getName());
				aux.addActionListener((new AcideInsertedItemListener(aux)));
			}
		}
	}

	/**
	 * Returns the ACIDE - A Configurable IDE configuration menu grammar menu.
	 * 
	 * @return the ACIDE - A Configurable IDE configuration menu grammar menu.
	 */
	public AcideGrammarMenu getGrammarMenu() {
		return _grammarMenu;
	}

	/**
	 * Returns the ACIDE - A Configurable IDE configuration menu menu menu.
	 * 
	 * @return the ACIDE - A Configurable IDE configuration menu menu menu.
	 */
	public AcideMenuMenu getMenuMenu() {
		return _menuMenu;
	}

	/**
	 * Returns the ACIDE - A Configurable IDE configuration menu lexicon menu.
	 * 
	 * @return the ACIDE - A Configurable IDE configuration menu lexicon menu.
	 */
	public AcideLexiconMenu getLexiconMenu() {
		return _lexiconMenu;
	}

	/**
	 * Returns the ACIDE - A Configurable IDE configuration menu console menu.
	 * 
	 * @return the ACIDE - A Configurable IDE configuration menu console menu.
	 */
	public AcideConsoleMenu getConsoleMenu() {
		return _consoleMenu;
	}

	/**
	 * Returns the ACIDE - A Configurable IDE configuration menu graph menu.
	 * 
	 * @return the ACIDE - A Configurable IDE configuration menu graph menu.
	 */
	public AcideGraphPanelMenu getGraphPanelMenu() {
		return _graphPanelMenu;
	}
	
	/**
	 * Returns the ACIDE - A Configurable IDE configuration menu debug menu.
	 * 
	 * @return the ACIDE - A Configurable IDE configuration menu debug menu.
	 */	
	public AcideDebugPanelMenu getDebugPanelMenu(){
		return _debugPanelMenu;
	}

	/**
	 * Returns the ACIDE - A Configurable IDE configuration menu database menu.
	 * 
	 * @return the ACIDE - A Configurable IDE configuration menu database menu.
	 */
	public AcideDatabasePanelMenu getDatabasePanelMenu() {
		return _databasePanelMenu;
	}

	/**
	 * Returns the ACIDE - A Configurable IDE configuration menu file editor
	 * menu.
	 * 
	 * @return the ACIDE - A Configurable IDE configuration menu file editor
	 *         menu.
	 */
	public AcideFileEditorMenu getFileEditorMenu() {
		return _fileEditorMenu;
	}

	/**
	 * Returns the ACIDE - A Configurable IDE configuration menu tool bar menu.
	 * 
	 * @return the ACIDE - A Configurable IDE configuration menu tool bar menu.
	 */
	public AcideToolBarMenu getToolBarMenu() {
		return _toolBarMenu;
	}

	/**
	 * Returns the ACIDE - A Configurable IDE configuration menu language menu.
	 * 
	 * @return the ACIDE - A Configurable IDE configuration menu language menu.
	 */
	public AcideLanguageMenu getLanguageMenu() {
		return _languageMenu;
	}

	/**
	 * Returns the ACIDE - A Configurable IDE configuration menu compiler menu
	 * item.
	 * 
	 * @return the ACIDE - A Configurable IDE configuration menu compiler menu
	 *         item.
	 */
	public JMenuItem getCompilerMenuItem() {
		return _compilerMenuItem;
	}
}