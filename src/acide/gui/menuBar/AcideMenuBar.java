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
package acide.gui.menuBar;

import java.awt.HeadlessException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import javax.swing.JMenuBar;
import javax.swing.JOptionPane;

import acide.configuration.icons.AcideMenuIconsConfiguration;
import acide.configuration.menu.AcideInsertedMenu;
import acide.configuration.menu.AcideMenuConfiguration;
import acide.configuration.menu.AcideMenuItemConfiguration;
import acide.configuration.menu.AcideMenuItemsConfiguration;
import acide.configuration.menu.AcideMenuObjectConfiguration;
import acide.configuration.menu.AcideMenuSubmenuConfiguration;
import acide.configuration.project.AcideProjectConfiguration;
import acide.gui.fileEditor.fileEditorManager.listeners.AcideFileEditorManagerChangeListener;
import acide.gui.mainWindow.AcideMainWindow;
import acide.gui.menuBar.configurationMenu.AcideConfigurationMenu;
import acide.gui.menuBar.configurationMenu.consoleMenu.AcideConsoleMenu;
import acide.gui.menuBar.configurationMenu.databasePanelMenu.AcideDatabasePanelMenu;
import acide.gui.menuBar.configurationMenu.fileEditor.AcideFileEditorMenu;
import acide.gui.menuBar.configurationMenu.grammarMenu.AcideGrammarMenu;
import acide.gui.menuBar.configurationMenu.graphPanelMenu.AcideGraphPanelArrowColorMenu;
import acide.gui.menuBar.configurationMenu.graphPanelMenu.AcideGraphPanelMenu;
import acide.gui.menuBar.configurationMenu.graphPanelMenu.AcideGraphPanelNodeShapeMenu;
import acide.gui.menuBar.configurationMenu.languageMenu.AcideLanguageMenu;
import acide.gui.menuBar.configurationMenu.lexiconMenu.AcideLexiconMenu;
import acide.gui.menuBar.configurationMenu.menuMenu.AcideMenuMenu;
import acide.gui.menuBar.configurationMenu.toolBarMenu.AcideToolBarMenu;
import acide.gui.menuBar.editMenu.AcideEditMenu;
import acide.gui.menuBar.fileMenu.AcideFileMenu;
import acide.gui.menuBar.helpMenu.AcideHelpMenu;
import acide.gui.menuBar.listeners.AcideMenuBarMouseClickListener;
import acide.gui.menuBar.projectMenu.AcideProjectMenu;
import acide.gui.menuBar.viewMenu.AcideViewMenu;
import acide.language.AcideLanguageManager;
import acide.log.AcideLog;
import acide.resources.AcideResourceManager;

/**
 * ACIDE - A Configurable IDE menu bar.
 * 
 * @version 0.11
 */
public class AcideMenuBar extends JMenuBar {

	/**
	 * ACIDE - A Configurable IDE menu bar serial version UID.
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * ACIDE - A Configurable IDE menu bar file menu.
	 */
	private AcideFileMenu _fileMenu;
	/**
	 * ACIDE - A Configurable IDE menu bar file menu has been inserted.
	 */
	private boolean _fileInserted;
	/**
	 * ACIDE - A Configurable IDE menu bar edit menu.
	 */
	private AcideEditMenu _editMenu;
	/**
	 * ACIDE - A Configurable IDE menu bar edit menu has been inserted.
	 */
	private boolean _editInserted;
	/**
	 * ACIDE - A Configurable IDE menu bar project menu.
	 */
	private AcideProjectMenu _projectMenu;
	/**
	 * ACIDE - A Configurable IDE menu bar project menu has been inserted.
	 */
	private boolean _projectInserted;
	/**
	 * ACIDE - A Configurable IDE menu bar view menu.
	 */
	private AcideViewMenu _viewMenu;
	/**
	 * ACIDE - A Configurable IDE menu bar view menu has been inserted.
	 */
	private boolean _viewInserted;
	/**
	 * ACIDE - A Configurable IDE menu bar configuration menu.
	 */
	private AcideConfigurationMenu _configurationMenu;
	/**
	 * ACIDE - A Configurable IDE menu bar configuration menu has been inserted.
	 */
	private boolean _configurationInserted;
	/**
	 * ACIDE - A Configurable IDE menu bar help menu.
	 */
	private AcideHelpMenu _helpMenu;
	/**
	 * ACIDE - A Configurable IDE menu bar help menu has been inserted.
	 */
	private boolean _helpInserted;
	/**
	 * ACIDE - A Configurable IDE menu bar flag which indicates if the console
	 * is focused.
	 */
	private boolean _isConsoleFocused;
	/**
	 * ACIDE - A Configurable IDE inserted menus hashmap.
	 */
	private HashMap<String, AcideInsertedMenu> _insertedMenus;

	/**
	 * ACIDE - A Configurable IDE array list of inserted objects.
	 */
	private ArrayList<AcideMenuObjectConfiguration> _insertedObjects;

	/**
	 * Creates a new ACIDE - A Configurable IDE menu bar.
	 */
	public AcideMenuBar() {

		super();

		_fileInserted = false;
		_editInserted = false;
		_projectInserted = false;
		_viewInserted = false;
		_configurationInserted = false;
		_helpInserted = false;

		// Creates the hashmap for inserted menus
		_insertedMenus = new HashMap<String, AcideInsertedMenu>();

		// Creates the array list for the inserted objects
		_insertedObjects = new ArrayList<AcideMenuObjectConfiguration>();

		// Updates the log
		AcideLog.getLog()
				.info(AcideLanguageManager.getInstance().getLabels()
						.getString("s68"));

		// Loads the menu configuration
		loadMenuConfiguration();

		// Build the menu configuration
		buildMenuConfiguration();

		// Build the menu components
		buildComponents();

		// Adds the components to the menu
		addComponents();

		// Sets the text for the menu bar components
		setTextOfMenuComponents();

		// Builds the menu bar
		updateComponentsVisibility();

		// Updates the log
		AcideLog.getLog()
				.info(AcideLanguageManager.getInstance().getLabels()
						.getString("s69"));
	}

	/**
	 * Applies the configuration to the menu bar after a modification
	 */
	public void modifyMenuBar() {

		removeAll();

		_fileInserted = false;
		_editInserted = false;
		_projectInserted = false;
		_viewInserted = false;
		_configurationInserted = false;
		_helpInserted = false;

		_insertedMenus = new HashMap<String, AcideInsertedMenu>();

		_insertedObjects = new ArrayList<AcideMenuObjectConfiguration>();

		// Build the menu configuration
		buildMenuConfiguration();

		// Build the menu components
		buildComponents();

		// Adds the components to the menu
		addComponents();

		// Sets the text for the menu bar components
		setTextOfMenuComponents();

		// Set the listeners for the menu bar components
		setListeners();

		// Builds the menu bar
		updateComponentsVisibility();

		// Updates the log
		AcideLog.getLog()
				.info(AcideLanguageManager.getInstance().getLabels()
						.getString("s69"));
	}

	/**
	 * Checks that the menu configuration has the correct format
	 */
	private void buildMenuConfiguration() {

		buildFileMenuConfiguration();

		buildEditMenuConfiguration();

		buildProjectMenuConfiguration();

		buildViewMenuConfiguration();

		buildConfigurationMenuConfiguration();

		buildHelpMenuConfiguration();

	}

	/**
	 * Checks that the help menu configuration has the correct format
	 */
	private void buildHelpMenuConfiguration() {

		if (!AcideMenuItemsConfiguration.getInstance().hasSubmenu(
				AcideHelpMenu.HELP_MENU_NAME)) {
			AcideMenuSubmenuConfiguration help = AcideMenuItemsConfiguration
					.getInstance().getHelpDefaultSubmenu();

			AcideMenuItemConfiguration showHelp = help
					.getItem(AcideHelpMenu.SHOW_HELP_NAME);
			showHelp.setVisible(AcideMenuConfiguration.getInstance()
					.getIsDisplayed(AcideHelpMenu.SHOW_HELP_NAME));

			AcideMenuItemConfiguration showAboutUs = help
					.getItem(AcideHelpMenu.SHOW_ABOUT_US_NAME);
			showAboutUs.setVisible(AcideMenuConfiguration.getInstance()
					.getIsDisplayed(AcideHelpMenu.SHOW_ABOUT_US_NAME));

		} else {

			AcideMenuSubmenuConfiguration help = AcideMenuItemsConfiguration
					.getInstance().getSubmenu(AcideHelpMenu.HELP_MENU_NAME);

			AcideMenuItemConfiguration showHelp;
			if (help.hasItem(AcideHelpMenu.SHOW_HELP_NAME)) {
				showHelp = help.getItem(AcideHelpMenu.SHOW_HELP_NAME);
				AcideMenuConfiguration.getInstance().setIsDisplayed(
						AcideHelpMenu.SHOW_HELP_NAME, showHelp.isVisible());
				AcideMenuItemsConfiguration.getInstance()
						.getSubmenu(AcideHelpMenu.HELP_MENU_NAME)
						.getItemsManager()
						.onlyOne(AcideHelpMenu.SHOW_HELP_NAME);

			} else {
				showHelp = new AcideMenuItemConfiguration(
						AcideHelpMenu.SHOW_HELP_NAME);
				showHelp.setImage("./resources/icons/menu/help/help.png");
				showHelp.setCommand("$SHOW_HELP");
				showHelp.setVisible(AcideMenuConfiguration.getInstance()
						.getIsDisplayed(AcideHelpMenu.SHOW_HELP_NAME));
				help.insertObject(showHelp);
			}
			showHelp.setErasable(false);
			showHelp.setParameter("None");
			// showHelp.setCommand("");

			AcideMenuItemConfiguration showAboutUs;
			if (help.hasItem(AcideHelpMenu.SHOW_ABOUT_US_NAME)) {
				showAboutUs = help.getItem(AcideHelpMenu.SHOW_ABOUT_US_NAME);
				AcideMenuConfiguration.getInstance().setIsDisplayed(
						AcideHelpMenu.SHOW_ABOUT_US_NAME,
						showAboutUs.isVisible());
				AcideMenuItemsConfiguration.getInstance()
						.getSubmenu(AcideHelpMenu.HELP_MENU_NAME)
						.getItemsManager()
						.onlyOne(AcideHelpMenu.SHOW_ABOUT_US_NAME);
			} else {
				showAboutUs = new AcideMenuItemConfiguration(
						AcideHelpMenu.SHOW_ABOUT_US_NAME);
				showAboutUs.setImage("./resources/icons/menu/help/aboutUs.png");
				showAboutUs.setCommand("$SHOW_ABOUT_US");
				showAboutUs.setVisible(AcideMenuConfiguration.getInstance()
						.getIsDisplayed(AcideHelpMenu.SHOW_ABOUT_US_NAME));
				help.insertObject(showAboutUs);
			}
			showAboutUs.setErasable(false);
			showAboutUs.setParameter("None");
			// showAboutUs.setCommand("");

			AcideMenuItemsConfiguration.getInstance().onlyOne(
					AcideHelpMenu.HELP_MENU_NAME);

		}
	}

	/**
	 * Checks that the configuration menu configuration has the correct format
	 */
	private void buildConfigurationMenuConfiguration() {

		if (!AcideMenuItemsConfiguration.getInstance().hasSubmenu(
				AcideConfigurationMenu.CONFIGURATION_MENU_NAME)) {

			AcideMenuSubmenuConfiguration configuration = AcideMenuItemsConfiguration
					.getInstance().getConfigurationDefaultSubmenu();

			AcideMenuSubmenuConfiguration lexicon = configuration
					.getSubmenu(AcideConfigurationMenu.LEXICON_NAME);

			AcideMenuItemConfiguration newLexicon = lexicon
					.getItem(AcideLexiconMenu.NEW_LEXICON_NAME);
			newLexicon.setVisible(AcideMenuConfiguration.getInstance()
					.getIsDisplayed(AcideLexiconMenu.NEW_LEXICON_NAME));

			AcideMenuItemConfiguration documentLexicon = lexicon
					.getItem(AcideLexiconMenu.DOCUMENT_LEXICON_NAME);
			documentLexicon.setVisible(AcideMenuConfiguration.getInstance()
					.getIsDisplayed(AcideLexiconMenu.DOCUMENT_LEXICON_NAME));

			AcideMenuItemConfiguration modifyLexicon = lexicon
					.getItem(AcideLexiconMenu.MODIFY_LEXICON_NAME);
			modifyLexicon.setVisible(AcideMenuConfiguration.getInstance()
					.getIsDisplayed(AcideLexiconMenu.MODIFY_LEXICON_NAME));

			AcideMenuItemConfiguration defaultLexicon = lexicon
					.getItem(AcideLexiconMenu.DEFAULT_LEXICONS_NAME);
			defaultLexicon.setVisible(AcideMenuConfiguration.getInstance()
					.getIsDisplayed(AcideLexiconMenu.DEFAULT_LEXICONS_NAME));

			AcideMenuSubmenuConfiguration grammar = configuration
					.getSubmenu(AcideConfigurationMenu.GRAMMAR_NAME);

			AcideMenuItemConfiguration newGrammar = grammar
					.getItem(AcideGrammarMenu.NEW_GRAMMAR_NAME);
			newGrammar.setVisible(AcideMenuConfiguration.getInstance()
					.getIsDisplayed(AcideGrammarMenu.NEW_GRAMMAR_NAME));

			AcideMenuItemConfiguration loadGrammar = grammar
					.getItem(AcideGrammarMenu.LOAD_GRAMMAR_NAME);
			loadGrammar.setVisible(AcideMenuConfiguration.getInstance()
					.getIsDisplayed(AcideGrammarMenu.LOAD_GRAMMAR_NAME));

			AcideMenuItemConfiguration modifyGrammar = grammar
					.getItem(AcideGrammarMenu.MODIFY_GRAMMAR_NAME);
			modifyGrammar.setVisible(AcideMenuConfiguration.getInstance()
					.getIsDisplayed(AcideGrammarMenu.MODIFY_GRAMMAR_NAME));

			AcideMenuItemConfiguration saveGrammar = grammar
					.getItem(AcideGrammarMenu.SAVE_GRAMMAR_NAME);
			saveGrammar.setVisible(AcideMenuConfiguration.getInstance()
					.getIsDisplayed(AcideGrammarMenu.SAVE_GRAMMAR_NAME));

			AcideMenuItemConfiguration saveGrammarAs = grammar
					.getItem(AcideGrammarMenu.SAVE_GRAMMAR_AS_NAME);
			saveGrammarAs.setVisible(AcideMenuConfiguration.getInstance()
					.getIsDisplayed(AcideGrammarMenu.SAVE_GRAMMAR_AS_NAME));

			AcideMenuItemConfiguration setPaths = grammar
					.getItem(AcideGrammarMenu.SET_PATHS_NAME);
			setPaths.setVisible(AcideMenuConfiguration.getInstance()
					.getIsDisplayed(AcideGrammarMenu.SET_PATHS_NAME));

			// AcideMenuItemConfiguration autoAnalysis =
			// grammar.getItem(AcideGrammarMenu.AUTO_ANALYSIS_NAME);
			// autoAnalysis.setVisible(AcideMenuConfiguration.getInstance()
			// .getIsDisplayed(AcideGrammarMenu.AUTO_ANALYSIS_NAME));

			AcideMenuItemConfiguration compiler = configuration
					.getItem(AcideConfigurationMenu.COMPILER_NAME);
			compiler.setVisible(AcideMenuConfiguration.getInstance()
					.getIsDisplayed(AcideConfigurationMenu.COMPILER_NAME));

			AcideMenuSubmenuConfiguration fileEditor = configuration
					.getSubmenu(AcideConfigurationMenu.FILE_EDITOR_NAME);

			AcideMenuItemConfiguration displayOptions = fileEditor
					.getItem(AcideFileEditorMenu.FILE_EDITOR_DISPLAY_OPTIONS_NAME);
			displayOptions
					.setVisible(AcideMenuConfiguration
							.getInstance()
							.getIsDisplayed(
									AcideFileEditorMenu.FILE_EDITOR_DISPLAY_OPTIONS_NAME));

			AcideMenuItemConfiguration automaticIndent = fileEditor
					.getItem(AcideFileEditorMenu.AUTOMATIC_INDENT_NAME);
			automaticIndent.setVisible(AcideMenuConfiguration.getInstance()
					.getIsDisplayed(AcideFileEditorMenu.AUTOMATIC_INDENT_NAME));

			AcideMenuItemConfiguration lineWrapping = fileEditor
					.getItem(AcideFileEditorMenu.LINE_WRAPPING_NAME);
			lineWrapping.setVisible(AcideMenuConfiguration.getInstance()
					.getIsDisplayed(AcideFileEditorMenu.LINE_WRAPPING_NAME));

			AcideMenuItemConfiguration maximumLines = fileEditor
					.getItem(AcideFileEditorMenu.MAXIMUM_LINES_TO_CONSOLE_NAME);
			maximumLines.setVisible(AcideMenuConfiguration.getInstance()
					.getIsDisplayed(
							AcideFileEditorMenu.MAXIMUM_LINES_TO_CONSOLE_NAME));

			AcideMenuItemConfiguration sendConsoleConfirmation = fileEditor
					.getItem(AcideFileEditorMenu.SEND_TO_CONSOLE_CONFIRMATION_NAME);
			sendConsoleConfirmation
					.setVisible(AcideMenuConfiguration
							.getInstance()
							.getIsDisplayed(
									AcideFileEditorMenu.SEND_TO_CONSOLE_CONFIRMATION_NAME));

			AcideMenuSubmenuConfiguration console = configuration
					.getSubmenu(AcideConfigurationMenu.CONSOLE_NAME);

			AcideMenuItemConfiguration configure = console
					.getItem(AcideConsoleMenu.CONFIGURE_NAME);
			configure.setVisible(AcideMenuConfiguration.getInstance()
					.getIsDisplayed(AcideConsoleMenu.CONFIGURE_NAME));

			AcideMenuItemConfiguration externalCommand = console
					.getItem(AcideConsoleMenu.EXTERNAL_COMMAND_NAME);
			externalCommand.setVisible(AcideMenuConfiguration.getInstance()
					.getIsDisplayed(AcideConsoleMenu.EXTERNAL_COMMAND_NAME));

			AcideMenuItemConfiguration consoleDisplayOptions = console
					.getItem(AcideConsoleMenu.CONSOLE_DISPLAY_OPTIONS_NAME);
			consoleDisplayOptions.setVisible(AcideMenuConfiguration
					.getInstance().getIsDisplayed(
							AcideConsoleMenu.CONSOLE_DISPLAY_OPTIONS_NAME));

			AcideMenuItemConfiguration consoleLineWrapping = console
					.getItem(AcideConsoleMenu.CONSOLE_LINE_WRAPPING_NAME);
			consoleLineWrapping.setVisible(AcideMenuConfiguration
					.getInstance().getIsDisplayed(
							AcideConsoleMenu.CONSOLE_LINE_WRAPPING_NAME));
			
			AcideMenuItemConfiguration saveContent = console
					.getItem(AcideConsoleMenu.SAVE_CONSOLE_CONTENT_INTO_FILE_NAME);
			saveContent
					.setVisible(AcideMenuConfiguration
							.getInstance()
							.getIsDisplayed(
									AcideConsoleMenu.SAVE_CONSOLE_CONTENT_INTO_FILE_NAME));

			AcideMenuItemConfiguration documentConsole = console
					.getItem(AcideConsoleMenu.DOCUMENT_CONSOLE_LEXICON_NAME);
			documentConsole.setVisible(AcideMenuConfiguration.getInstance()
					.getIsDisplayed(
							AcideConsoleMenu.DOCUMENT_CONSOLE_LEXICON_NAME));

			AcideMenuItemConfiguration searchConsole = console
					.getItem(AcideConsoleMenu.SEARCH_CONSOLE_NAME);
			searchConsole.setVisible(AcideMenuConfiguration.getInstance()
					.getIsDisplayed(AcideConsoleMenu.SEARCH_CONSOLE_NAME));

			AcideMenuItemConfiguration closeConsole = console
					.getItem(AcideConsoleMenu.CLOSE_CONSOLE_NAME);
			closeConsole.setVisible(AcideMenuConfiguration.getInstance()
					.getIsDisplayed(AcideConsoleMenu.CLOSE_CONSOLE_NAME));

			AcideMenuSubmenuConfiguration database = configuration
					.getSubmenu(AcideConfigurationMenu.DATABASE_PANEL_NAME);

			AcideMenuItemConfiguration des = database
					.getItem(AcideDatabasePanelMenu.DES_PANEL_NAME);
			des.setVisible(AcideMenuConfiguration.getInstance().getIsDisplayed(
					AcideDatabasePanelMenu.DES_PANEL_NAME));

			AcideMenuItemConfiguration odbc = database
					.getItem(AcideDatabasePanelMenu.ODBC_PANEL_NAME);
			odbc.setVisible(AcideMenuConfiguration.getInstance()
					.getIsDisplayed(AcideDatabasePanelMenu.ODBC_PANEL_NAME));
			
			AcideMenuSubmenuConfiguration graph = configuration
					.getSubmenu(AcideGraphPanelMenu.GRAPH_MENU_NAME);
			
			AcideMenuItemConfiguration nodeColor = graph
					.getItem(AcideGraphPanelMenu.NODE_COLOR_NAME);
			nodeColor.setVisible(AcideMenuConfiguration.getInstance()
					.getIsDisplayed(AcideGraphPanelMenu.NODE_COLOR_NAME));
			
			AcideMenuSubmenuConfiguration nodeShape = graph
					.getSubmenu(AcideGraphPanelNodeShapeMenu.NODE_SHAPE_MENU_NAME);
			AcideMenuItemConfiguration shapeCircle = nodeShape
					.getItem(AcideGraphPanelNodeShapeMenu.NODE_SHAPE_CIRCLE);
			shapeCircle.setVisible(AcideMenuConfiguration.getInstance()
					.getIsDisplayed(AcideGraphPanelNodeShapeMenu.NODE_SHAPE_CIRCLE));
			
			AcideMenuItemConfiguration shapeSquare = nodeShape
					.getItem(AcideGraphPanelNodeShapeMenu.NODE_SHAPE_SQUARE);
			shapeSquare.setVisible(AcideMenuConfiguration.getInstance()
					.getIsDisplayed(AcideGraphPanelNodeShapeMenu.NODE_SHAPE_SQUARE));
			
			AcideMenuSubmenuConfiguration arrowColor = graph
					.getSubmenu(AcideGraphPanelMenu.ARROW_COLOR_NAME);
			AcideMenuItemConfiguration colorDirect = arrowColor
					.getItem(AcideGraphPanelArrowColorMenu.ARROW_COLOR_DIRECT);
			colorDirect.setVisible(AcideMenuConfiguration.getInstance()
					.getIsDisplayed(AcideGraphPanelArrowColorMenu.ARROW_COLOR_DIRECT));
			AcideMenuItemConfiguration colorInverse = arrowColor
					.getItem(AcideGraphPanelArrowColorMenu.ARROW_COLOR_INVERSE);
			colorInverse.setVisible(AcideMenuConfiguration.getInstance()
					.getIsDisplayed(AcideGraphPanelArrowColorMenu.ARROW_COLOR_DIRECT));
			
			AcideMenuSubmenuConfiguration language = configuration
					.getSubmenu(AcideConfigurationMenu.LANGUAGE_NAME);

			AcideMenuSubmenuConfiguration menu = configuration
					.getSubmenu(AcideConfigurationMenu.MENU_NAME);

			AcideMenuItemConfiguration newMenu = menu
					.getItem(AcideMenuMenu.NEW_MENU_NAME);
			newMenu.setVisible(AcideMenuConfiguration.getInstance()
					.getIsDisplayed(AcideMenuMenu.NEW_MENU_NAME));

			AcideMenuItemConfiguration loadMenu = menu
					.getItem(AcideMenuMenu.LOAD_MENU_NAME);
			loadMenu.setVisible(AcideMenuConfiguration.getInstance()
					.getIsDisplayed(AcideMenuMenu.LOAD_MENU_NAME));

			AcideMenuItemConfiguration modifyMenu = menu
					.getItem(AcideMenuMenu.MODIFY_MENU_NAME);
			modifyMenu.setVisible(AcideMenuConfiguration.getInstance()
					.getIsDisplayed(AcideMenuMenu.MODIFY_MENU_NAME));

			AcideMenuItemConfiguration saveMenu = menu
					.getItem(AcideMenuMenu.SAVE_MENU_NAME);
			saveMenu.setVisible(AcideMenuConfiguration.getInstance()
					.getIsDisplayed(AcideMenuMenu.SAVE_MENU_NAME));

			AcideMenuItemConfiguration saveMenuAs = menu
					.getItem(AcideMenuMenu.SAVE_MENU_AS_NAME);
			saveMenuAs.setVisible(AcideMenuConfiguration.getInstance()
					.getIsDisplayed(AcideMenuMenu.SAVE_MENU_AS_NAME));

			AcideMenuSubmenuConfiguration toolbar = configuration
					.getSubmenu(AcideConfigurationMenu.TOOLBAR_NAME);

			AcideMenuItemConfiguration newToolbar = toolbar
					.getItem(AcideToolBarMenu.NEW_TOOLBAR_NAME);
			newToolbar.setVisible(AcideMenuConfiguration.getInstance()
					.getIsDisplayed(AcideToolBarMenu.NEW_TOOLBAR_NAME));

			AcideMenuItemConfiguration loadToolbar = toolbar
					.getItem(AcideToolBarMenu.LOAD_TOOLBAR_NAME);
			loadToolbar.setVisible(AcideMenuConfiguration.getInstance()
					.getIsDisplayed(AcideToolBarMenu.LOAD_TOOLBAR_NAME));

			AcideMenuItemConfiguration modifyToolbar = toolbar
					.getItem(AcideToolBarMenu.MODIFY_TOOLBAR_NAME);
			modifyToolbar.setVisible(AcideMenuConfiguration.getInstance()
					.getIsDisplayed(AcideToolBarMenu.MODIFY_TOOLBAR_NAME));

			AcideMenuItemConfiguration saveToolbar = toolbar
					.getItem(AcideToolBarMenu.SAVE_TOOLBAR_NAME);
			saveToolbar.setVisible(AcideMenuConfiguration.getInstance()
					.getIsDisplayed(AcideToolBarMenu.SAVE_TOOLBAR_NAME));

			AcideMenuItemConfiguration saveToolbarAs = toolbar
					.getItem(AcideToolBarMenu.SAVE_TOOLBAR_AS_NAME);
			saveToolbarAs.setVisible(AcideMenuConfiguration.getInstance()
					.getIsDisplayed(AcideToolBarMenu.SAVE_TOOLBAR_AS_NAME));

		} else {

			AcideMenuSubmenuConfiguration configuration = AcideMenuItemsConfiguration
					.getInstance().getSubmenu(
							AcideConfigurationMenu.CONFIGURATION_MENU_NAME);

			if (!configuration.hasSubmenu(AcideLexiconMenu.LEXICON_MENU_NAME)) {

				AcideMenuSubmenuConfiguration lexicon = AcideMenuItemsConfiguration
						.getInstance().getLexiconDefaultSubmenu();

				AcideMenuItemConfiguration newLexicon = lexicon
						.getItem(AcideLexiconMenu.NEW_LEXICON_NAME);
				newLexicon.setVisible(AcideMenuConfiguration.getInstance()
						.getIsDisplayed(AcideLexiconMenu.NEW_LEXICON_NAME));

				AcideMenuItemConfiguration documentLexicon = lexicon
						.getItem(AcideLexiconMenu.DOCUMENT_LEXICON_NAME);
				documentLexicon
						.setVisible(AcideMenuConfiguration.getInstance()
								.getIsDisplayed(
										AcideLexiconMenu.DOCUMENT_LEXICON_NAME));

				AcideMenuItemConfiguration modifyLexicon = lexicon
						.getItem(AcideLexiconMenu.MODIFY_LEXICON_NAME);
				modifyLexicon.setVisible(AcideMenuConfiguration.getInstance()
						.getIsDisplayed(AcideLexiconMenu.MODIFY_LEXICON_NAME));

				AcideMenuItemConfiguration defaultLexicon = lexicon
						.getItem(AcideLexiconMenu.DEFAULT_LEXICONS_NAME);
				defaultLexicon
						.setVisible(AcideMenuConfiguration.getInstance()
								.getIsDisplayed(
										AcideLexiconMenu.DEFAULT_LEXICONS_NAME));

			} else {

				AcideMenuSubmenuConfiguration lexicon = configuration
						.getSubmenu(AcideLexiconMenu.LEXICON_MENU_NAME);

				AcideMenuItemConfiguration newLexicon;
				if (lexicon.hasItem(AcideLexiconMenu.NEW_LEXICON_NAME)) {
					newLexicon = lexicon
							.getItem(AcideLexiconMenu.NEW_LEXICON_NAME);
					AcideMenuConfiguration.getInstance().setIsDisplayed(
							AcideLexiconMenu.NEW_LEXICON_NAME,
							newLexicon.isVisible());
					AcideMenuItemsConfiguration
							.getInstance()
							.getSubmenu(
									AcideConfigurationMenu.CONFIGURATION_MENU_NAME)
							.getSubmenu(AcideConfigurationMenu.LEXICON_NAME)
							.getItemsManager()
							.onlyOne(AcideLexiconMenu.NEW_LEXICON_NAME);

				} else {
					newLexicon = new AcideMenuItemConfiguration(
							AcideLexiconMenu.NEW_LEXICON_NAME);
					newLexicon.setVisible(AcideMenuConfiguration.getInstance()
							.getIsDisplayed(AcideLexiconMenu.NEW_LEXICON_NAME));
					newLexicon.setCommand("$NEW_LEXICON");
					lexicon.insertObject(newLexicon);
				}
				newLexicon.setErasable(false);
				newLexicon.setParameter("None");
				// newLexicon.setCommand("");

				AcideMenuItemConfiguration documentLexicon;
				if (lexicon.hasItem(AcideLexiconMenu.DOCUMENT_LEXICON_NAME)) {
					documentLexicon = lexicon
							.getItem(AcideLexiconMenu.DOCUMENT_LEXICON_NAME);
					AcideMenuConfiguration.getInstance().setIsDisplayed(
							AcideLexiconMenu.DOCUMENT_LEXICON_NAME,
							documentLexicon.isVisible());
					AcideMenuItemsConfiguration
							.getInstance()
							.getSubmenu(
									AcideConfigurationMenu.CONFIGURATION_MENU_NAME)
							.getSubmenu(AcideConfigurationMenu.LEXICON_NAME)
							.getItemsManager()
							.onlyOne(AcideLexiconMenu.DOCUMENT_LEXICON_NAME);

				} else {
					documentLexicon = new AcideMenuItemConfiguration(
							AcideLexiconMenu.DOCUMENT_LEXICON_NAME);
					documentLexicon.setVisible(AcideMenuConfiguration
							.getInstance().getIsDisplayed(
									AcideLexiconMenu.DOCUMENT_LEXICON_NAME));
					documentLexicon.setCommand("$DOCUMENT_LEXICON");
					lexicon.insertObject(documentLexicon);
				}
				documentLexicon.setErasable(false);
				documentLexicon.setParameter("None");
				// documentLexicon.setCommand("");

				AcideMenuItemConfiguration modifyLexicon;
				if (lexicon.hasItem(AcideLexiconMenu.MODIFY_LEXICON_NAME)) {
					modifyLexicon = lexicon
							.getItem(AcideLexiconMenu.MODIFY_LEXICON_NAME);
					AcideMenuConfiguration.getInstance().setIsDisplayed(
							AcideLexiconMenu.MODIFY_LEXICON_NAME,
							documentLexicon.isVisible());
					AcideMenuItemsConfiguration
							.getInstance()
							.getSubmenu(
									AcideConfigurationMenu.CONFIGURATION_MENU_NAME)
							.getSubmenu(AcideConfigurationMenu.LEXICON_NAME)
							.getItemsManager()
							.onlyOne(AcideLexiconMenu.MODIFY_LEXICON_NAME);
				} else {
					modifyLexicon = new AcideMenuItemConfiguration(
							AcideLexiconMenu.MODIFY_LEXICON_NAME);
					modifyLexicon.setVisible(AcideMenuConfiguration
							.getInstance().getIsDisplayed(
									AcideLexiconMenu.MODIFY_LEXICON_NAME));
					modifyLexicon.setCommand("$MODIFY_LEXICON");
					lexicon.insertObject(modifyLexicon);
				}
				modifyLexicon.setErasable(false);
				modifyLexicon.setParameter("None");
				// modifyLexicon.setCommand("");

				AcideMenuItemConfiguration defaultLexicon;
				if (lexicon.hasItem(AcideLexiconMenu.DEFAULT_LEXICONS_NAME)) {
					defaultLexicon = lexicon
							.getItem(AcideLexiconMenu.DEFAULT_LEXICONS_NAME);
					AcideMenuConfiguration.getInstance().setIsDisplayed(
							AcideLexiconMenu.DEFAULT_LEXICONS_NAME,
							defaultLexicon.isVisible());
					AcideMenuItemsConfiguration
							.getInstance()
							.getSubmenu(
									AcideConfigurationMenu.CONFIGURATION_MENU_NAME)
							.getSubmenu(AcideConfigurationMenu.LEXICON_NAME)
							.getItemsManager()
							.onlyOne(AcideLexiconMenu.DEFAULT_LEXICONS_NAME);
				} else {
					defaultLexicon = new AcideMenuItemConfiguration(
							AcideLexiconMenu.DEFAULT_LEXICONS_NAME);
					defaultLexicon.setVisible(AcideMenuConfiguration
							.getInstance().getIsDisplayed(
									AcideLexiconMenu.DEFAULT_LEXICONS_NAME));
					defaultLexicon.setCommand("$DEFAULT_LEXICON");
					lexicon.insertObject(defaultLexicon);
				}
				defaultLexicon.setErasable(false);
				defaultLexicon.setParameter("None");
				// defaultLexicon.setCommand("");

				AcideMenuItemsConfiguration
						.getInstance()
						.getSubmenu(
								AcideConfigurationMenu.CONFIGURATION_MENU_NAME)
						.getItemsManager()
						.onlyOne(AcideConfigurationMenu.LEXICON_NAME);

			}

			if (!configuration.hasSubmenu(AcideGrammarMenu.GRAMMAR_MENU_NAME)) {

				AcideMenuSubmenuConfiguration grammar = AcideMenuItemsConfiguration
						.getInstance().getGrammarDefaultSubmenu();

				AcideMenuItemConfiguration newGrammar = grammar
						.getItem(AcideGrammarMenu.NEW_GRAMMAR_NAME);
				newGrammar.setVisible(AcideMenuConfiguration.getInstance()
						.getIsDisplayed(AcideGrammarMenu.NEW_GRAMMAR_NAME));

				AcideMenuItemConfiguration loadGrammar = grammar
						.getItem(AcideGrammarMenu.LOAD_GRAMMAR_NAME);
				loadGrammar.setVisible(AcideMenuConfiguration.getInstance()
						.getIsDisplayed(AcideGrammarMenu.LOAD_GRAMMAR_NAME));

				AcideMenuItemConfiguration modifyGrammar = grammar
						.getItem(AcideGrammarMenu.MODIFY_GRAMMAR_NAME);
				modifyGrammar.setVisible(AcideMenuConfiguration.getInstance()
						.getIsDisplayed(AcideGrammarMenu.MODIFY_GRAMMAR_NAME));

				AcideMenuItemConfiguration saveGrammar = grammar
						.getItem(AcideGrammarMenu.SAVE_GRAMMAR_NAME);
				saveGrammar.setVisible(AcideMenuConfiguration.getInstance()
						.getIsDisplayed(AcideGrammarMenu.SAVE_GRAMMAR_NAME));

				AcideMenuItemConfiguration saveGrammarAs = grammar
						.getItem(AcideGrammarMenu.SAVE_GRAMMAR_AS_NAME);
				saveGrammarAs.setVisible(AcideMenuConfiguration.getInstance()
						.getIsDisplayed(AcideGrammarMenu.SAVE_GRAMMAR_AS_NAME));

				AcideMenuItemConfiguration setPaths = grammar
						.getItem(AcideGrammarMenu.SET_PATHS_NAME);
				setPaths.setVisible(AcideMenuConfiguration.getInstance()
						.getIsDisplayed(AcideGrammarMenu.SET_PATHS_NAME));

				// AcideMenuItemConfiguration autoAnaylisis =
				// grammar.getItem(AcideGrammarMenu.AUTO_ANALYSIS_NAME);
				// autoAnaylisis.setVisible(AcideMenuConfiguration.getInstance()
				// .getIsDisplayed(AcideGrammarMenu.AUTO_ANALYSIS_NAME));

			} else {

				AcideMenuSubmenuConfiguration grammar = configuration
						.getSubmenu(AcideGrammarMenu.GRAMMAR_MENU_NAME);

				AcideMenuItemConfiguration newGrammar;
				if (grammar.hasItem(AcideGrammarMenu.NEW_GRAMMAR_NAME)) {
					newGrammar = grammar
							.getItem(AcideGrammarMenu.NEW_GRAMMAR_NAME);
					AcideMenuConfiguration.getInstance().setIsDisplayed(
							AcideGrammarMenu.NEW_GRAMMAR_NAME,
							newGrammar.isVisible());
					AcideMenuItemsConfiguration
							.getInstance()
							.getSubmenu(
									AcideConfigurationMenu.CONFIGURATION_MENU_NAME)
							.getSubmenu(AcideConfigurationMenu.GRAMMAR_NAME)
							.getItemsManager()
							.onlyOne(AcideGrammarMenu.NEW_GRAMMAR_NAME);

				} else {
					newGrammar = new AcideMenuItemConfiguration(
							AcideGrammarMenu.NEW_GRAMMAR_NAME);
					newGrammar.setVisible(AcideMenuConfiguration.getInstance()
							.getIsDisplayed(AcideGrammarMenu.NEW_GRAMMAR_NAME));
					newGrammar.setCommand("$NEW_GRAMMAR");
					grammar.insertObject(newGrammar);
				}
				newGrammar.setErasable(false);
				newGrammar.setParameter("None");
				// newGrammar.setCommand("");

				AcideMenuItemConfiguration loadGrammar;
				if (grammar.hasItem(AcideGrammarMenu.LOAD_GRAMMAR_NAME)) {
					loadGrammar = grammar
							.getItem(AcideGrammarMenu.LOAD_GRAMMAR_NAME);
					AcideMenuConfiguration.getInstance().setIsDisplayed(
							AcideGrammarMenu.LOAD_GRAMMAR_NAME,
							loadGrammar.isVisible());
					AcideMenuItemsConfiguration
							.getInstance()
							.getSubmenu(
									AcideConfigurationMenu.CONFIGURATION_MENU_NAME)
							.getSubmenu(AcideConfigurationMenu.GRAMMAR_NAME)
							.getItemsManager()
							.onlyOne(AcideGrammarMenu.LOAD_GRAMMAR_NAME);
				} else {
					loadGrammar = new AcideMenuItemConfiguration(
							AcideGrammarMenu.LOAD_GRAMMAR_NAME);
					loadGrammar
							.setVisible(AcideMenuConfiguration.getInstance()
									.getIsDisplayed(
											AcideGrammarMenu.LOAD_GRAMMAR_NAME));
					loadGrammar.setCommand("$LOAD_GRAMMAR");
					grammar.insertObject(loadGrammar);
				}
				loadGrammar.setErasable(false);
				loadGrammar.setParameter("None");
				// loadGrammar.setCommand("");

				AcideMenuItemConfiguration modifyGrammar;
				if (grammar.hasItem(AcideGrammarMenu.MODIFY_GRAMMAR_NAME)) {
					modifyGrammar = grammar
							.getItem(AcideGrammarMenu.MODIFY_GRAMMAR_NAME);
					AcideMenuConfiguration.getInstance().setIsDisplayed(
							AcideGrammarMenu.MODIFY_GRAMMAR_NAME,
							modifyGrammar.isVisible());
					AcideMenuItemsConfiguration
							.getInstance()
							.getSubmenu(
									AcideConfigurationMenu.CONFIGURATION_MENU_NAME)
							.getSubmenu(AcideConfigurationMenu.GRAMMAR_NAME)
							.getItemsManager()
							.onlyOne(AcideGrammarMenu.MODIFY_GRAMMAR_NAME);
				} else {
					modifyGrammar = new AcideMenuItemConfiguration(
							AcideGrammarMenu.MODIFY_GRAMMAR_NAME);
					modifyGrammar.setVisible(AcideMenuConfiguration
							.getInstance().getIsDisplayed(
									AcideGrammarMenu.MODIFY_GRAMMAR_NAME));
					modifyGrammar.setCommand("$MODIFY_GRAMMAR");
					grammar.insertObject(modifyGrammar);
				}
				modifyGrammar.setErasable(false);
				modifyGrammar.setParameter("None");
				// modifyGrammar.setCommand("");

				AcideMenuItemConfiguration saveGrammar;
				if (grammar.hasItem(AcideGrammarMenu.SAVE_GRAMMAR_NAME)) {
					saveGrammar = grammar
							.getItem(AcideGrammarMenu.SAVE_GRAMMAR_NAME);
					AcideMenuConfiguration.getInstance().setIsDisplayed(
							AcideGrammarMenu.SAVE_GRAMMAR_NAME,
							saveGrammar.isVisible());
					AcideMenuItemsConfiguration
							.getInstance()
							.getSubmenu(
									AcideConfigurationMenu.CONFIGURATION_MENU_NAME)
							.getSubmenu(AcideConfigurationMenu.GRAMMAR_NAME)
							.getItemsManager()
							.onlyOne(AcideGrammarMenu.SAVE_GRAMMAR_NAME);
				} else {
					saveGrammar = new AcideMenuItemConfiguration(
							AcideGrammarMenu.SAVE_GRAMMAR_NAME);
					saveGrammar
							.setVisible(AcideMenuConfiguration.getInstance()
									.getIsDisplayed(
											AcideGrammarMenu.SAVE_GRAMMAR_NAME));
					saveGrammar.setCommand("$SAVE_GRAMMAR");
					grammar.insertObject(saveGrammar);
				}
				saveGrammar.setErasable(false);
				saveGrammar.setParameter("None");
				// saveGrammar.setCommand("");

				AcideMenuItemConfiguration saveGrammarAs;
				if (grammar.hasItem(AcideGrammarMenu.SAVE_GRAMMAR_AS_NAME)) {
					saveGrammarAs = grammar
							.getItem(AcideGrammarMenu.SAVE_GRAMMAR_AS_NAME);
					AcideMenuConfiguration.getInstance().setIsDisplayed(
							AcideGrammarMenu.SAVE_GRAMMAR_AS_NAME,
							saveGrammarAs.isVisible());
					AcideMenuItemsConfiguration
							.getInstance()
							.getSubmenu(
									AcideConfigurationMenu.CONFIGURATION_MENU_NAME)
							.getSubmenu(AcideConfigurationMenu.GRAMMAR_NAME)
							.getItemsManager()
							.onlyOne(AcideGrammarMenu.SAVE_GRAMMAR_AS_NAME);
				} else {
					saveGrammarAs = new AcideMenuItemConfiguration(
							AcideGrammarMenu.SAVE_GRAMMAR_AS_NAME);
					saveGrammarAs.setVisible(AcideMenuConfiguration
							.getInstance().getIsDisplayed(
									AcideGrammarMenu.SAVE_GRAMMAR_AS_NAME));
					saveGrammarAs.setCommand("$SAVE_GRAMMAR_AS");
					grammar.insertObject(saveGrammarAs);
				}
				saveGrammarAs.setErasable(false);
				saveGrammarAs.setParameter("None");
				// saveGrammarAs.setCommand("");

				AcideMenuItemConfiguration setPaths;
				if (grammar.hasItem(AcideGrammarMenu.SET_PATHS_NAME)) {
					setPaths = grammar.getItem(AcideGrammarMenu.SET_PATHS_NAME);
					AcideMenuConfiguration.getInstance().setIsDisplayed(
							AcideGrammarMenu.SET_PATHS_NAME,
							setPaths.isVisible());
					AcideMenuItemsConfiguration
							.getInstance()
							.getSubmenu(
									AcideConfigurationMenu.CONFIGURATION_MENU_NAME)
							.getSubmenu(AcideConfigurationMenu.GRAMMAR_NAME)
							.getItemsManager()
							.onlyOne(AcideGrammarMenu.SET_PATHS_NAME);
				} else {
					setPaths = new AcideMenuItemConfiguration(
							AcideGrammarMenu.SET_PATHS_NAME);
					setPaths.setVisible(AcideMenuConfiguration.getInstance()
							.getIsDisplayed(AcideGrammarMenu.SET_PATHS_NAME));
					setPaths.setCommand("$SET_PATHS");
					grammar.insertObject(setPaths);
				}
				setPaths.setErasable(false);
				setPaths.setParameter("None");
				// setPaths.setCommand("");

				// AcideMenuItemConfiguration autoAnalysis;
				// if (grammar.hasItem(AcideGrammarMenu.AUTO_ANALYSIS_NAME)){
				// autoAnalysis =
				// grammar.getItem(AcideGrammarMenu.AUTO_ANALYSIS_NAME);
				// AcideMenuConfiguration.getInstance().setIsDisplayed(AcideGrammarMenu.AUTO_ANALYSIS_NAME,
				// autoAnalysis.isVisible());
				//
				// }else{
				// autoAnalysis = new
				// AcideMenuItemConfiguration(AcideGrammarMenu.AUTO_ANALYSIS_NAME);
				// autoAnalysis.setVisible(AcideMenuConfiguration.getInstance()
				// .getIsDisplayed(AcideGrammarMenu.AUTO_ANALYSIS_NAME));
				// autoAnalysis.setCommand("$AUTO_ANALYSIS");
				// grammar.insertObject(autoAnalysis);
				// }
				// autoAnalysis.setErasable(false);
				// autoAnalysis.setParameter("None");
				// //autoAnalysis.setCommand("");

				AcideMenuItemsConfiguration
						.getInstance()
						.getSubmenu(
								AcideConfigurationMenu.CONFIGURATION_MENU_NAME)
						.getItemsManager()
						.onlyOne(AcideConfigurationMenu.GRAMMAR_NAME);
			}

			AcideMenuItemConfiguration compiler;
			if (configuration.hasItem(AcideConfigurationMenu.COMPILER_NAME)) {
				compiler = configuration
						.getItem(AcideConfigurationMenu.COMPILER_NAME);
				AcideMenuConfiguration.getInstance().setIsDisplayed(
						AcideConfigurationMenu.COMPILER_NAME,
						compiler.isVisible());
				AcideMenuItemsConfiguration
						.getInstance()
						.getSubmenu(
								AcideConfigurationMenu.CONFIGURATION_MENU_NAME)
						.getItemsManager()
						.onlyOne(AcideConfigurationMenu.COMPILER_NAME);

			} else {
				compiler = new AcideMenuItemConfiguration(
						AcideConfigurationMenu.COMPILER_NAME);
				compiler.setImage("./resources/icons/menu/configuration/compiler.png");
				compiler.setCommand("$COMPILER");
				compiler.setVisible(AcideMenuConfiguration.getInstance()
						.getIsDisplayed(AcideConfigurationMenu.COMPILER_NAME));
				configuration.insertObject(compiler);
			}
			compiler.setErasable(false);
			compiler.setParameter("None");
			// compiler.setCommand("");

			if (!configuration
					.hasSubmenu(AcideFileEditorMenu.FILE_EDITOR_MENU_NAME)) {

				AcideMenuSubmenuConfiguration fileEditor = AcideMenuItemsConfiguration
						.getInstance().getFileEditorDefaultSubmenu();

				AcideMenuItemConfiguration displayOptions = fileEditor
						.getItem(AcideFileEditorMenu.FILE_EDITOR_DISPLAY_OPTIONS_NAME);
				displayOptions
						.setVisible(AcideMenuConfiguration
								.getInstance()
								.getIsDisplayed(
										AcideFileEditorMenu.FILE_EDITOR_DISPLAY_OPTIONS_NAME));

				AcideMenuItemConfiguration automaticIndent = fileEditor
						.getItem(AcideFileEditorMenu.AUTOMATIC_INDENT_NAME);
				automaticIndent.setVisible(AcideMenuConfiguration.getInstance()
						.getIsDisplayed(
								AcideFileEditorMenu.AUTOMATIC_INDENT_NAME));

				AcideMenuItemConfiguration lineWrapping = fileEditor
						.getItem(AcideFileEditorMenu.LINE_WRAPPING_NAME);
				lineWrapping
						.setVisible(AcideMenuConfiguration.getInstance()
								.getIsDisplayed(
										AcideFileEditorMenu.LINE_WRAPPING_NAME));

				AcideMenuItemConfiguration maximumLines = fileEditor
						.getItem(AcideFileEditorMenu.MAXIMUM_LINES_TO_CONSOLE_NAME);
				maximumLines
						.setVisible(AcideMenuConfiguration
								.getInstance()
								.getIsDisplayed(
										AcideFileEditorMenu.MAXIMUM_LINES_TO_CONSOLE_NAME));

				AcideMenuItemConfiguration sendConfirmation = fileEditor
						.getItem(AcideFileEditorMenu.SEND_TO_CONSOLE_CONFIRMATION_NAME);
				sendConfirmation
						.setVisible(AcideMenuConfiguration
								.getInstance()
								.getIsDisplayed(
										AcideFileEditorMenu.SEND_TO_CONSOLE_CONFIRMATION_NAME));

			} else {

				AcideMenuSubmenuConfiguration fileEditor = configuration
						.getSubmenu(AcideFileEditorMenu.FILE_EDITOR_MENU_NAME);

				AcideMenuItemConfiguration displayOptions;
				if (fileEditor
						.hasItem(AcideFileEditorMenu.FILE_EDITOR_DISPLAY_OPTIONS_NAME)) {
					displayOptions = fileEditor
							.getItem(AcideFileEditorMenu.FILE_EDITOR_DISPLAY_OPTIONS_NAME);
					AcideMenuConfiguration
							.getInstance()
							.setIsDisplayed(
									AcideFileEditorMenu.FILE_EDITOR_DISPLAY_OPTIONS_NAME,
									displayOptions.isVisible());
					AcideMenuItemsConfiguration
							.getInstance()
							.getSubmenu(
									AcideConfigurationMenu.CONFIGURATION_MENU_NAME)
							.getSubmenu(
									AcideFileEditorMenu.FILE_EDITOR_MENU_NAME)
							.getItemsManager()
							.onlyOne(
									AcideFileEditorMenu.FILE_EDITOR_DISPLAY_OPTIONS_NAME);

				} else {
					displayOptions = new AcideMenuItemConfiguration(
							AcideFileEditorMenu.FILE_EDITOR_DISPLAY_OPTIONS_NAME);
					displayOptions
							.setImage("./resources/icons/menu/configuration/fileEditor/fileEditorDisplayOptions.png");
					displayOptions.setCommand("$FILE_EDITOR_DISPLAY_OPTIONS");
					displayOptions
							.setVisible(AcideMenuConfiguration
									.getInstance()
									.getIsDisplayed(
											AcideFileEditorMenu.FILE_EDITOR_DISPLAY_OPTIONS_NAME));
					fileEditor.insertObject(displayOptions);
				}
				displayOptions.setErasable(false);
				displayOptions.setParameter("None");
				// displayOptions.setCommand("");

				AcideMenuItemConfiguration automaticIndent;
				if (fileEditor
						.hasItem(AcideFileEditorMenu.AUTOMATIC_INDENT_NAME)) {
					automaticIndent = fileEditor
							.getItem(AcideFileEditorMenu.AUTOMATIC_INDENT_NAME);
					AcideMenuConfiguration.getInstance().setIsDisplayed(
							AcideFileEditorMenu.AUTOMATIC_INDENT_NAME,
							automaticIndent.isVisible());
					AcideMenuItemsConfiguration
							.getInstance()
							.getSubmenu(
									AcideConfigurationMenu.CONFIGURATION_MENU_NAME)
							.getSubmenu(
									AcideFileEditorMenu.FILE_EDITOR_MENU_NAME)
							.getItemsManager()
							.onlyOne(AcideFileEditorMenu.AUTOMATIC_INDENT_NAME);
				} else {
					automaticIndent = new AcideMenuItemConfiguration(
							AcideFileEditorMenu.AUTOMATIC_INDENT_NAME);
					automaticIndent
							.setImage("./resources/icons/menu/configuration/fileEditor/automaticIndent.png");
					automaticIndent.setCommand("$AUTOMATIC_INDENT");
					automaticIndent.setVisible(AcideMenuConfiguration
							.getInstance().getIsDisplayed(
									AcideFileEditorMenu.AUTOMATIC_INDENT_NAME));
					fileEditor.insertObject(automaticIndent);
				}
				automaticIndent.setErasable(false);
				automaticIndent.setParameter("None");
				// automaticIndent.setCommand("");

				AcideMenuItemConfiguration lineWrapping;
				if (fileEditor.hasItem(AcideFileEditorMenu.LINE_WRAPPING_NAME)) {
					lineWrapping = fileEditor
							.getItem(AcideFileEditorMenu.LINE_WRAPPING_NAME);
					AcideMenuConfiguration.getInstance().setIsDisplayed(
							AcideFileEditorMenu.LINE_WRAPPING_NAME,
							lineWrapping.isVisible());
					AcideMenuItemsConfiguration
							.getInstance()
							.getSubmenu(
									AcideConfigurationMenu.CONFIGURATION_MENU_NAME)
							.getSubmenu(
									AcideFileEditorMenu.FILE_EDITOR_MENU_NAME)
							.getItemsManager()
							.onlyOne(AcideFileEditorMenu.LINE_WRAPPING_NAME);
				} else {
					lineWrapping = new AcideMenuItemConfiguration(
							AcideFileEditorMenu.LINE_WRAPPING_NAME);
					lineWrapping
							.setImage("./resources/icons/menu/configuration/fileEditor/lineWrapping.png");
					lineWrapping.setCommand("$LINE_WRAPPING");
					lineWrapping.setVisible(AcideMenuConfiguration
							.getInstance().getIsDisplayed(
									AcideFileEditorMenu.LINE_WRAPPING_NAME));
					fileEditor.insertObject(lineWrapping);
				}
				lineWrapping.setErasable(false);
				lineWrapping.setParameter("None");
				// lineWrapping.setCommand("");

				AcideMenuItemConfiguration maximumLines;
				if (fileEditor
						.hasItem(AcideFileEditorMenu.MAXIMUM_LINES_TO_CONSOLE_NAME)) {
					maximumLines = fileEditor
							.getItem(AcideFileEditorMenu.MAXIMUM_LINES_TO_CONSOLE_NAME);
					AcideMenuConfiguration.getInstance().setIsDisplayed(
							AcideFileEditorMenu.MAXIMUM_LINES_TO_CONSOLE_NAME,
							maximumLines.isVisible());
					AcideMenuItemsConfiguration
							.getInstance()
							.getSubmenu(
									AcideConfigurationMenu.CONFIGURATION_MENU_NAME)
							.getSubmenu(
									AcideFileEditorMenu.FILE_EDITOR_MENU_NAME)
							.getItemsManager()
							.onlyOne(
									AcideFileEditorMenu.MAXIMUM_LINES_TO_CONSOLE_NAME);
				} else {
					maximumLines = new AcideMenuItemConfiguration(
							AcideFileEditorMenu.MAXIMUM_LINES_TO_CONSOLE_NAME);
					maximumLines
							.setImage("./resources/icons/menu/configuration/fileEditor/maximumLinesToConsole.png");
					maximumLines.setCommand("$MAXIMUM_LINES");
					maximumLines
							.setVisible(AcideMenuConfiguration
									.getInstance()
									.getIsDisplayed(
											AcideFileEditorMenu.MAXIMUM_LINES_TO_CONSOLE_NAME));
					fileEditor.insertObject(maximumLines);
				}
				maximumLines.setErasable(false);
				maximumLines.setParameter("None");
				// maximumLines.setCommand("");

				AcideMenuItemConfiguration sendConfirmation;
				if (fileEditor
						.hasItem(AcideFileEditorMenu.SEND_TO_CONSOLE_CONFIRMATION_NAME)) {
					sendConfirmation = fileEditor
							.getItem(AcideFileEditorMenu.SEND_TO_CONSOLE_CONFIRMATION_NAME);
					AcideMenuConfiguration
							.getInstance()
							.setIsDisplayed(
									AcideFileEditorMenu.SEND_TO_CONSOLE_CONFIRMATION_NAME,
									sendConfirmation.isVisible());
					AcideMenuItemsConfiguration
							.getInstance()
							.getSubmenu(
									AcideConfigurationMenu.CONFIGURATION_MENU_NAME)
							.getSubmenu(
									AcideFileEditorMenu.FILE_EDITOR_MENU_NAME)
							.getItemsManager()
							.onlyOne(
									AcideFileEditorMenu.SEND_TO_CONSOLE_CONFIRMATION_NAME);
				} else {
					sendConfirmation = new AcideMenuItemConfiguration(
							AcideFileEditorMenu.SEND_TO_CONSOLE_CONFIRMATION_NAME);
					sendConfirmation
							.setImage("./resources/icons/menu/configuration/fileEditor/sendToConsoleConfirmation.png");
					sendConfirmation.setCommand("$SEND_CONSOLE_CONFIRMATION");
					sendConfirmation
							.setVisible(AcideMenuConfiguration
									.getInstance()
									.getIsDisplayed(
											AcideFileEditorMenu.SEND_TO_CONSOLE_CONFIRMATION_NAME));
					fileEditor.insertObject(sendConfirmation);
				}
				sendConfirmation.setErasable(false);
				sendConfirmation.setParameter("None");
				// sendConfirmation.setCommand("");

				AcideMenuItemsConfiguration
						.getInstance()
						.getSubmenu(
								AcideConfigurationMenu.CONFIGURATION_MENU_NAME)
						.getItemsManager()
						.onlyOne(AcideConfigurationMenu.FILE_EDITOR_NAME);

			}

			if (!configuration.hasSubmenu(AcideConsoleMenu.CONSOLE_MENU_NAME)) {

				AcideMenuSubmenuConfiguration console = AcideMenuItemsConfiguration
						.getInstance().getConsoleDefaultSubmenu();

				AcideMenuItemConfiguration configure = console
						.getItem(AcideConsoleMenu.CONFIGURE_NAME);
				configure.setVisible(AcideMenuConfiguration.getInstance()
						.getIsDisplayed(AcideConsoleMenu.CONFIGURE_NAME));

				AcideMenuItemConfiguration externalCommand = console
						.getItem(AcideConsoleMenu.EXTERNAL_COMMAND_NAME);
				externalCommand
						.setVisible(AcideMenuConfiguration.getInstance()
								.getIsDisplayed(
										AcideConsoleMenu.EXTERNAL_COMMAND_NAME));

				AcideMenuItemConfiguration consoleDisplayOptions = console
						.getItem(AcideConsoleMenu.CONSOLE_DISPLAY_OPTIONS_NAME);
				consoleDisplayOptions.setVisible(AcideMenuConfiguration
						.getInstance().getIsDisplayed(
								AcideConsoleMenu.CONSOLE_DISPLAY_OPTIONS_NAME));
				
				AcideMenuItemConfiguration consoleLineWrapping = console
						.getItem(AcideConsoleMenu.CONSOLE_LINE_WRAPPING_NAME);
				consoleLineWrapping.setVisible(AcideMenuConfiguration
						.getInstance().getIsDisplayed(
								AcideConsoleMenu.CONSOLE_LINE_WRAPPING_NAME));

				AcideMenuItemConfiguration saveContent = console
						.getItem(AcideConsoleMenu.SAVE_CONSOLE_CONTENT_INTO_FILE_NAME);
				saveContent
						.setVisible(AcideMenuConfiguration
								.getInstance()
								.getIsDisplayed(
										AcideConsoleMenu.SAVE_CONSOLE_CONTENT_INTO_FILE_NAME));

				AcideMenuItemConfiguration documentConsole = console
						.getItem(AcideConsoleMenu.DOCUMENT_CONSOLE_LEXICON_NAME);
				documentConsole
						.setVisible(AcideMenuConfiguration
								.getInstance()
								.getIsDisplayed(
										AcideConsoleMenu.DOCUMENT_CONSOLE_LEXICON_NAME));

				AcideMenuItemConfiguration searchConsole = console
						.getItem(AcideConsoleMenu.SEARCH_CONSOLE_NAME);
				searchConsole.setVisible(AcideMenuConfiguration.getInstance()
						.getIsDisplayed(AcideConsoleMenu.SEARCH_CONSOLE_NAME));

				AcideMenuItemConfiguration closeConsole = console
						.getItem(AcideConsoleMenu.CLOSE_CONSOLE_NAME);
				closeConsole.setVisible(AcideMenuConfiguration.getInstance()
						.getIsDisplayed(AcideConsoleMenu.CLOSE_CONSOLE_NAME));

			} else {

				AcideMenuSubmenuConfiguration console = configuration
						.getSubmenu(AcideConsoleMenu.CONSOLE_MENU_NAME);

				AcideMenuItemConfiguration configure;
				if (console.hasItem(AcideConsoleMenu.CONFIGURE_NAME)) {
					configure = console
							.getItem(AcideConsoleMenu.CONFIGURE_NAME);
					AcideMenuConfiguration.getInstance().setIsDisplayed(
							AcideConsoleMenu.CONFIGURE_NAME,
							configure.isVisible());
					AcideMenuItemsConfiguration
							.getInstance()
							.getSubmenu(
									AcideConfigurationMenu.CONFIGURATION_MENU_NAME)
							.getSubmenu(AcideConsoleMenu.CONSOLE_MENU_NAME)
							.getItemsManager()
							.onlyOne(AcideConsoleMenu.CONFIGURE_NAME);
				} else {
					configure = new AcideMenuItemConfiguration(
							AcideConsoleMenu.CONFIGURE_NAME);
					configure
							.setImage("./resources/icons/menu/configuration/console/configure.png");
					configure.setCommand("$CONFIGURE_CONSOLE");
					configure.setVisible(AcideMenuConfiguration.getInstance()
							.getIsDisplayed(AcideConsoleMenu.CONFIGURE_NAME));
					console.insertObject(configure);
				}
				configure.setErasable(false);
				configure.setParameter("None");
				// configure.setCommand("");

				AcideMenuItemConfiguration externalCommand;
				if (console.hasItem(AcideConsoleMenu.EXTERNAL_COMMAND_NAME)) {
					externalCommand = console
							.getItem(AcideConsoleMenu.EXTERNAL_COMMAND_NAME);
					AcideMenuConfiguration.getInstance().setIsDisplayed(
							AcideConsoleMenu.EXTERNAL_COMMAND_NAME,
							externalCommand.isVisible());
					AcideMenuItemsConfiguration
							.getInstance()
							.getSubmenu(
									AcideConfigurationMenu.CONFIGURATION_MENU_NAME)
							.getSubmenu(AcideConsoleMenu.CONSOLE_MENU_NAME)
							.getItemsManager()
							.onlyOne(AcideConsoleMenu.EXTERNAL_COMMAND_NAME);
				} else {
					externalCommand = new AcideMenuItemConfiguration(
							AcideConsoleMenu.EXTERNAL_COMMAND_NAME);
					externalCommand
							.setImage("./resources/icons/menu/configuration/console/externalCommand.png");
					externalCommand.setCommand("$EXTERNAL_COMMAND");
					externalCommand.setVisible(AcideMenuConfiguration
							.getInstance().getIsDisplayed(
									AcideConsoleMenu.EXTERNAL_COMMAND_NAME));
					console.insertObject(externalCommand);
				}
				externalCommand.setErasable(false);
				externalCommand.setParameter("None");
				// externalCommand.setCommand("");

				AcideMenuItemConfiguration consoleDisplayOptions;
				if (console
						.hasItem(AcideConsoleMenu.CONSOLE_DISPLAY_OPTIONS_NAME)) {
					consoleDisplayOptions = console
							.getItem(AcideConsoleMenu.CONSOLE_DISPLAY_OPTIONS_NAME);
					AcideMenuConfiguration.getInstance().setIsDisplayed(
							AcideConsoleMenu.CONSOLE_DISPLAY_OPTIONS_NAME,
							consoleDisplayOptions.isVisible());
					AcideMenuItemsConfiguration
							.getInstance()
							.getSubmenu(
									AcideConfigurationMenu.CONFIGURATION_MENU_NAME)
							.getSubmenu(AcideConsoleMenu.CONSOLE_MENU_NAME)
							.getItemsManager()
							.onlyOne(
									AcideConsoleMenu.CONSOLE_DISPLAY_OPTIONS_NAME);
				} else {
					consoleDisplayOptions = new AcideMenuItemConfiguration(
							AcideConsoleMenu.CONSOLE_DISPLAY_OPTIONS_NAME);
					consoleDisplayOptions
							.setImage("./resources/icons/menu/configuration/console/consoleDisplayOptions.png");
					consoleDisplayOptions
							.setCommand("$CONSOLE_DISPLAY_OPTIONS");
					consoleDisplayOptions
							.setVisible(AcideMenuConfiguration
									.getInstance()
									.getIsDisplayed(
											AcideConsoleMenu.CONSOLE_DISPLAY_OPTIONS_NAME));
					console.insertObject(consoleDisplayOptions);
				}
				consoleDisplayOptions.setErasable(false);
				consoleDisplayOptions.setParameter("None");
				// consoleDisplayOptions.setCommand("");
				
				AcideMenuItemConfiguration consoleLineWrapping;
				if (console
						.hasItem(AcideConsoleMenu.CONSOLE_LINE_WRAPPING_NAME)) {
					consoleLineWrapping = console
							.getItem(AcideConsoleMenu.CONSOLE_LINE_WRAPPING_NAME);
					AcideMenuConfiguration.getInstance().setIsDisplayed(
							AcideConsoleMenu.CONSOLE_LINE_WRAPPING_NAME,
							consoleLineWrapping.isVisible());
					AcideMenuItemsConfiguration
							.getInstance()
							.getSubmenu(
									AcideConfigurationMenu.CONFIGURATION_MENU_NAME)
							.getSubmenu(AcideConsoleMenu.CONSOLE_MENU_NAME)
							.getItemsManager()
							.onlyOne(
									AcideConsoleMenu.CONSOLE_LINE_WRAPPING_NAME);
				} else {
					consoleLineWrapping = new AcideMenuItemConfiguration(
							AcideConsoleMenu.CONSOLE_LINE_WRAPPING_NAME);
					consoleLineWrapping
							.setImage("./resources/icons/menu/configuration/console/lineWrapping.png");
					consoleLineWrapping
							.setCommand("$CONSOLE_LINE_WRAPPING");
					consoleLineWrapping
							.setVisible(AcideMenuConfiguration
									.getInstance()
									.getIsDisplayed(
											AcideConsoleMenu.CONSOLE_LINE_WRAPPING_NAME));
					console.insertObject(consoleLineWrapping);
				}
				consoleLineWrapping.setErasable(false);
				consoleLineWrapping.setParameter("None");
				// consoleLineWrapping.setCommand("");

				AcideMenuItemConfiguration saveContent;
				if (console
						.hasItem(AcideConsoleMenu.SAVE_CONSOLE_CONTENT_INTO_FILE_NAME)) {
					saveContent = console
							.getItem(AcideConsoleMenu.SAVE_CONSOLE_CONTENT_INTO_FILE_NAME);
					AcideMenuConfiguration
							.getInstance()
							.setIsDisplayed(
									AcideConsoleMenu.SAVE_CONSOLE_CONTENT_INTO_FILE_NAME,
									saveContent.isVisible());
					AcideMenuItemsConfiguration
							.getInstance()
							.getSubmenu(
									AcideConfigurationMenu.CONFIGURATION_MENU_NAME)
							.getSubmenu(AcideConsoleMenu.CONSOLE_MENU_NAME)
							.getItemsManager()
							.onlyOne(
									AcideConsoleMenu.SAVE_CONSOLE_CONTENT_INTO_FILE_NAME);
				} else {
					saveContent = new AcideMenuItemConfiguration(
							AcideConsoleMenu.SAVE_CONSOLE_CONTENT_INTO_FILE_NAME);
					saveContent
							.setImage("./resources/icons/menu/configuration/console/saveContentIntoFile.png");
					saveContent.setCommand("$SAVE_CONSOLE_CONTENT");
					saveContent
							.setVisible(AcideMenuConfiguration
									.getInstance()
									.getIsDisplayed(
											AcideConsoleMenu.SAVE_CONSOLE_CONTENT_INTO_FILE_NAME));
					console.insertObject(saveContent);
				}
				saveContent.setErasable(false);
				saveContent.setParameter("None");
				// saveContent.setCommand("");

				AcideMenuItemConfiguration documentConsole;
				if (console
						.hasItem(AcideConsoleMenu.DOCUMENT_CONSOLE_LEXICON_NAME)) {
					documentConsole = console
							.getItem(AcideConsoleMenu.DOCUMENT_CONSOLE_LEXICON_NAME);
					AcideMenuConfiguration.getInstance().setIsDisplayed(
							AcideConsoleMenu.DOCUMENT_CONSOLE_LEXICON_NAME,
							documentConsole.isVisible());
					AcideMenuItemsConfiguration
							.getInstance()
							.getSubmenu(
									AcideConfigurationMenu.CONFIGURATION_MENU_NAME)
							.getSubmenu(AcideConsoleMenu.CONSOLE_MENU_NAME)
							.getItemsManager()
							.onlyOne(
									AcideConsoleMenu.DOCUMENT_CONSOLE_LEXICON_NAME);
				} else {
					documentConsole = new AcideMenuItemConfiguration(
							AcideConsoleMenu.DOCUMENT_CONSOLE_LEXICON_NAME);
					documentConsole
							.setImage("./resources/icons/menu/configuration/console/documentLexicon.png");
					documentConsole.setCommand("$DOCUMENT_CONSOLE");
					documentConsole
							.setVisible(AcideMenuConfiguration
									.getInstance()
									.getIsDisplayed(
											AcideConsoleMenu.DOCUMENT_CONSOLE_LEXICON_NAME));
					console.insertObject(documentConsole);
				}
				documentConsole.setErasable(false);
				documentConsole.setParameter("None");
				// documentConsole.setCommand("");

				AcideMenuItemConfiguration searchConsole;
				if (console.hasItem(AcideConsoleMenu.SEARCH_CONSOLE_NAME)) {
					searchConsole = console
							.getItem(AcideConsoleMenu.SEARCH_CONSOLE_NAME);
					AcideMenuConfiguration.getInstance().setIsDisplayed(
							AcideConsoleMenu.SEARCH_CONSOLE_NAME,
							searchConsole.isVisible());
					AcideMenuItemsConfiguration
							.getInstance()
							.getSubmenu(
									AcideConfigurationMenu.CONFIGURATION_MENU_NAME)
							.getSubmenu(AcideConsoleMenu.CONSOLE_MENU_NAME)
							.getItemsManager()
							.onlyOne(AcideConsoleMenu.SEARCH_CONSOLE_NAME);
				} else {
					searchConsole = new AcideMenuItemConfiguration(
							AcideConsoleMenu.SEARCH_CONSOLE_NAME);
					searchConsole
							.setImage("./resources/icons/menu/edit/search.png");
					searchConsole.setCommand("$SEARCH_CONSOLE");
					searchConsole.setVisible(AcideMenuConfiguration
							.getInstance().getIsDisplayed(
									AcideConsoleMenu.SEARCH_CONSOLE_NAME));
					console.insertObject(searchConsole);
				}
				searchConsole.setErasable(false);
				searchConsole.setParameter("None");
				// searchConsole.setCommand("");

				AcideMenuItemConfiguration closeConsole;
				if (console.hasItem(AcideConsoleMenu.CLOSE_CONSOLE_NAME)) {
					closeConsole = console
							.getItem(AcideConsoleMenu.CLOSE_CONSOLE_NAME);
					AcideMenuConfiguration.getInstance().setIsDisplayed(
							AcideConsoleMenu.CLOSE_CONSOLE_NAME,
							closeConsole.isVisible());
					AcideMenuItemsConfiguration
							.getInstance()
							.getSubmenu(
									AcideConfigurationMenu.CONFIGURATION_MENU_NAME)
							.getSubmenu(AcideConsoleMenu.CONSOLE_MENU_NAME)
							.getItemsManager()
							.onlyOne(AcideConsoleMenu.CLOSE_CONSOLE_NAME);
				} else {
					closeConsole = new AcideMenuItemConfiguration(
							AcideConsoleMenu.CLOSE_CONSOLE_NAME);
					closeConsole
							.setImage("./resources/icons/menu/configuration/console/closeConsole.png");
					closeConsole.setCommand("$CLOSE_CONSOLE");
					closeConsole.setVisible(AcideMenuConfiguration
							.getInstance().getIsDisplayed(
									AcideConsoleMenu.CLOSE_CONSOLE_NAME));
					console.insertObject(closeConsole);
				}
				closeConsole.setErasable(false);
				closeConsole.setParameter("None");
				// closeConsole.setCommand("");

				AcideMenuItemsConfiguration
						.getInstance()
						.getSubmenu(
								AcideConfigurationMenu.CONFIGURATION_MENU_NAME)
						.getItemsManager()
						.onlyOne(AcideConfigurationMenu.CONSOLE_NAME);

			}

			if (!configuration
					.hasSubmenu(AcideDatabasePanelMenu.DATABASE_MENU_NAME)) {

				AcideMenuSubmenuConfiguration database = AcideMenuItemsConfiguration
						.getInstance().getDatabaseDefaultSubmenu();

				AcideMenuItemConfiguration des = database
						.getItem(AcideDatabasePanelMenu.DES_PANEL_NAME);
				des.setVisible(AcideMenuConfiguration.getInstance()
						.getIsDisplayed(AcideDatabasePanelMenu.DES_PANEL_NAME));

				AcideMenuItemConfiguration odbc = database
						.getItem(AcideDatabasePanelMenu.ODBC_PANEL_NAME);
				odbc.setVisible(AcideMenuConfiguration.getInstance()
						.getIsDisplayed(AcideDatabasePanelMenu.ODBC_PANEL_NAME));

			} else {

				AcideMenuSubmenuConfiguration database = configuration
						.getSubmenu(AcideDatabasePanelMenu.DATABASE_MENU_NAME);

				AcideMenuItemConfiguration des;
				if (database.hasItem(AcideDatabasePanelMenu.DES_PANEL_NAME)) {
					des = database
							.getItem(AcideDatabasePanelMenu.DES_PANEL_NAME);
					AcideMenuConfiguration.getInstance().setIsDisplayed(
							AcideDatabasePanelMenu.DES_PANEL_NAME,
							des.isVisible());
					AcideMenuItemsConfiguration
							.getInstance()
							.getSubmenu(
									AcideConfigurationMenu.CONFIGURATION_MENU_NAME)
							.getSubmenu(
									AcideDatabasePanelMenu.DATABASE_MENU_NAME)
							.getItemsManager()
							.onlyOne(AcideDatabasePanelMenu.DES_PANEL_NAME);
				} else {
					des = new AcideMenuItemConfiguration(
							AcideDatabasePanelMenu.DES_PANEL_NAME);
					//des.setImage("./resources/icons/menu/configuration/databasePanel/des.png");
					des.setCommand("$DES_PANEL");
					des.setVisible(AcideMenuConfiguration.getInstance()
							.getIsDisplayed(
									AcideDatabasePanelMenu.DES_PANEL_NAME));
					database.insertObject(des);
				}
				des.setErasable(false);
				des.setParameter("None");
				// des.setCommand("");

				AcideMenuItemConfiguration odbc;
				if (database.hasItem(AcideDatabasePanelMenu.ODBC_PANEL_NAME)) {
					odbc = database
							.getItem(AcideDatabasePanelMenu.ODBC_PANEL_NAME);
					AcideMenuConfiguration.getInstance().setIsDisplayed(
							AcideDatabasePanelMenu.ODBC_PANEL_NAME,
							odbc.isVisible());
					AcideMenuItemsConfiguration
							.getInstance()
							.getSubmenu(
									AcideConfigurationMenu.CONFIGURATION_MENU_NAME)
							.getSubmenu(
									AcideDatabasePanelMenu.DATABASE_MENU_NAME)
							.getItemsManager()
							.onlyOne(AcideDatabasePanelMenu.ODBC_PANEL_NAME);
				} else {
					odbc = new AcideMenuItemConfiguration(
							AcideDatabasePanelMenu.ODBC_PANEL_NAME);
					//odbc.setImage("./resources/icons/menu/configuration/databasePanel/odbc.png");
					odbc.setCommand("$ODBC_PANEL");
					odbc.setVisible(AcideMenuConfiguration.getInstance()
							.getIsDisplayed(
									AcideDatabasePanelMenu.ODBC_PANEL_NAME));
					database.insertObject(odbc);
				}
				odbc.setErasable(false);
				odbc.setParameter("None");
				// odbc.setCommand("");

				AcideMenuItemsConfiguration
						.getInstance()
						.getSubmenu(
								AcideConfigurationMenu.CONFIGURATION_MENU_NAME)
						.getItemsManager()
						.onlyOne(AcideConfigurationMenu.DATABASE_PANEL_NAME);

			}
			//TODO Juan
			if(!configuration.hasSubmenu(AcideGraphPanelMenu.GRAPH_MENU_NAME)){
				AcideMenuSubmenuConfiguration graph = AcideMenuItemsConfiguration
						.getInstance().getGraphDefaultSubmenu();
				
				AcideMenuItemConfiguration nodeColor = graph
						.getItem(AcideGraphPanelMenu.NODE_COLOR_NAME);
				nodeColor.setVisible(AcideMenuConfiguration.getInstance()
						.getIsDisplayed(AcideGraphPanelMenu.NODE_COLOR_NAME));
				
				AcideMenuSubmenuConfiguration nodeShape = AcideMenuItemsConfiguration
						.getInstance().getNodeShapeDefaultSubmenu();
				nodeShape.setVisible(AcideMenuConfiguration.getInstance()
						.getIsDisplayed(AcideGraphPanelMenu.NODE_SHAPE_NAME));
				
				AcideMenuSubmenuConfiguration arrowColor = AcideMenuItemsConfiguration
						.getInstance().getArrowColorDefaultSubmenu();
				arrowColor.setVisible(AcideMenuConfiguration.getInstance()
						.getIsDisplayed(AcideGraphPanelMenu.ARROW_COLOR_NAME));
				
			}else{
				AcideMenuSubmenuConfiguration graph = configuration
						.getSubmenu(AcideGraphPanelMenu.GRAPH_MENU_NAME);
				
				AcideMenuItemsConfiguration
				.getInstance()
				.getSubmenu(
						AcideConfigurationMenu.CONFIGURATION_MENU_NAME)
						.getItemsManager()
						.onlyOne(AcideGraphPanelMenu.GRAPH_MENU_NAME);
				
				AcideMenuItemConfiguration nodeColor;
				if(graph.hasItem(AcideGraphPanelMenu.NODE_COLOR_NAME)){
					nodeColor = graph
							.getItem(AcideGraphPanelMenu.NODE_COLOR_NAME);
					nodeColor.setVisible(AcideMenuConfiguration.getInstance()
							.getIsDisplayed(AcideGraphPanelMenu.NODE_COLOR_NAME));
					AcideMenuConfiguration.getInstance()
					.setIsDisplayed(AcideGraphPanelMenu.NODE_COLOR_NAME,
							nodeColor.isVisible());
					AcideMenuItemsConfiguration
					.getInstance()
					.getSubmenu(
							AcideConfigurationMenu.CONFIGURATION_MENU_NAME)
					.getSubmenu(AcideGraphPanelMenu.GRAPH_MENU_NAME)
					.getItemsManager()
					.onlyOne(AcideGraphPanelMenu.NODE_COLOR_NAME);
				}else{
					nodeColor = new AcideMenuItemConfiguration(
							AcideGraphPanelMenu.NODE_COLOR_NAME);
					nodeColor.setVisible(AcideMenuConfiguration.getInstance()
							.getIsDisplayed(AcideGraphPanelMenu.NODE_COLOR_NAME));
					graph.insertObject(nodeColor);
					AcideMenuItemsConfiguration
					.getInstance()
					.getSubmenu(
							AcideConfigurationMenu.CONFIGURATION_MENU_NAME)
					.getSubmenu(AcideGraphPanelMenu.GRAPH_MENU_NAME)
					.getItemsManager()
					.onlyOne(AcideGraphPanelMenu.NODE_COLOR_NAME);
				}
				nodeColor.setErasable(false);
				nodeColor.setParameter("None");
				
	
			}
			
			
			AcideMenuItemsConfiguration
			.getInstance()
			.getSubmenu(
					AcideConfigurationMenu.CONFIGURATION_MENU_NAME)
			.getItemsManager()
			.onlyOne(AcideConfigurationMenu.GRAPH_PANEL_NAME);

			if (!configuration.hasSubmenu(AcideLanguageMenu.LANGUAGE_MENU_NAME)) {

				AcideMenuSubmenuConfiguration language = AcideMenuItemsConfiguration
						.getInstance().getLanguageDefaultSubmenu();

			} else {

				AcideMenuSubmenuConfiguration language = configuration
						.getSubmenu(AcideLanguageMenu.LANGUAGE_MENU_NAME);

				AcideMenuItemsConfiguration
						.getInstance()
						.getSubmenu(
								AcideConfigurationMenu.CONFIGURATION_MENU_NAME)
						.getItemsManager()
						.onlyOne(AcideConfigurationMenu.LANGUAGE_NAME);

			}

			if (!configuration.hasSubmenu(AcideMenuMenu.MENU_MENU_NAME)) {

				AcideMenuSubmenuConfiguration menu = AcideMenuItemsConfiguration
						.getInstance().getMenuDefaultSubmenu();

				AcideMenuItemConfiguration newMenu = menu
						.getItem(AcideMenuMenu.NEW_MENU_NAME);
				newMenu.setVisible(AcideMenuConfiguration.getInstance()
						.getIsDisplayed(AcideMenuMenu.NEW_MENU_NAME));

				AcideMenuItemConfiguration loadMenu = menu
						.getItem(AcideMenuMenu.LOAD_MENU_NAME);
				loadMenu.setVisible(AcideMenuConfiguration.getInstance()
						.getIsDisplayed(AcideMenuMenu.LOAD_MENU_NAME));

				AcideMenuItemConfiguration modifyMenu = menu
						.getItem(AcideMenuMenu.MODIFY_MENU_NAME);
				modifyMenu.setVisible(AcideMenuConfiguration.getInstance()
						.getIsDisplayed(AcideMenuMenu.MODIFY_MENU_NAME));

				AcideMenuItemConfiguration saveMenu = menu
						.getItem(AcideMenuMenu.SAVE_MENU_NAME);
				saveMenu.setVisible(AcideMenuConfiguration.getInstance()
						.getIsDisplayed(AcideMenuMenu.SAVE_MENU_NAME));

				AcideMenuItemConfiguration saveMenuAs = menu
						.getItem(AcideMenuMenu.SAVE_MENU_AS_NAME);
				saveMenuAs.setVisible(AcideMenuConfiguration.getInstance()
						.getIsDisplayed(AcideMenuMenu.SAVE_MENU_AS_NAME));

			} else {

				AcideMenuSubmenuConfiguration menu = configuration
						.getSubmenu(AcideMenuMenu.MENU_MENU_NAME);

				AcideMenuItemConfiguration newMenu;
				if (menu.hasItem(AcideMenuMenu.NEW_MENU_NAME)) {
					newMenu = menu.getItem(AcideMenuMenu.NEW_MENU_NAME);
					AcideMenuConfiguration.getInstance().setIsDisplayed(
							AcideMenuMenu.NEW_MENU_NAME, newMenu.isVisible());
					AcideMenuItemsConfiguration
							.getInstance()
							.getSubmenu(
									AcideConfigurationMenu.CONFIGURATION_MENU_NAME)
							.getSubmenu(AcideMenuMenu.MENU_MENU_NAME)
							.getItemsManager()
							.onlyOne(AcideMenuMenu.NEW_MENU_NAME);
				} else {
					newMenu = new AcideMenuItemConfiguration(
							AcideMenuMenu.NEW_MENU_NAME);
					newMenu.setCommand("$NEW_MENU");
					newMenu.setVisible(AcideMenuConfiguration.getInstance()
							.getIsDisplayed(AcideMenuMenu.NEW_MENU_NAME));
					menu.insertObject(newMenu);
				}
				newMenu.setErasable(false);
				newMenu.setParameter("None");
				// newMenu.setCommand("");

				AcideMenuItemConfiguration loadMenu;
				if (menu.hasItem(AcideMenuMenu.LOAD_MENU_NAME)) {
					loadMenu = menu.getItem(AcideMenuMenu.LOAD_MENU_NAME);
					AcideMenuConfiguration.getInstance().setIsDisplayed(
							AcideMenuMenu.LOAD_MENU_NAME, loadMenu.isVisible());
					AcideMenuItemsConfiguration
							.getInstance()
							.getSubmenu(
									AcideConfigurationMenu.CONFIGURATION_MENU_NAME)
							.getSubmenu(AcideMenuMenu.MENU_MENU_NAME)
							.getItemsManager()
							.onlyOne(AcideMenuMenu.LOAD_MENU_NAME);
				} else {
					loadMenu = new AcideMenuItemConfiguration(
							AcideMenuMenu.LOAD_MENU_NAME);
					loadMenu.setCommand("$LOAD_MENU");
					loadMenu.setVisible(AcideMenuConfiguration.getInstance()
							.getIsDisplayed(AcideMenuMenu.LOAD_MENU_NAME));
					menu.insertObject(loadMenu);
				}
				loadMenu.setErasable(false);
				loadMenu.setParameter("None");
				// loadMenu.setCommand("");

				AcideMenuItemConfiguration modifyMenu;
				if (menu.hasItem(AcideMenuMenu.MODIFY_MENU_NAME)) {
					modifyMenu = menu.getItem(AcideMenuMenu.MODIFY_MENU_NAME);
					AcideMenuConfiguration.getInstance().setIsDisplayed(
							AcideMenuMenu.MODIFY_MENU_NAME,
							modifyMenu.isVisible());
					AcideMenuItemsConfiguration
							.getInstance()
							.getSubmenu(
									AcideConfigurationMenu.CONFIGURATION_MENU_NAME)
							.getSubmenu(AcideMenuMenu.MENU_MENU_NAME)
							.getItemsManager()
							.onlyOne(AcideMenuMenu.MODIFY_MENU_NAME);
				} else {
					modifyMenu = new AcideMenuItemConfiguration(
							AcideMenuMenu.MODIFY_MENU_NAME);
					modifyMenu.setCommand("$MODIFY_MENU");
					modifyMenu.setVisible(AcideMenuConfiguration.getInstance()
							.getIsDisplayed(AcideMenuMenu.MODIFY_MENU_NAME));
					menu.insertObject(modifyMenu);
				}
				modifyMenu.setErasable(false);
				modifyMenu.setParameter("None");
				// modifyMenu.setCommand("");

				AcideMenuItemConfiguration saveMenu;
				if (menu.hasItem(AcideMenuMenu.SAVE_MENU_NAME)) {
					saveMenu = menu.getItem(AcideMenuMenu.SAVE_MENU_NAME);
					AcideMenuConfiguration.getInstance().setIsDisplayed(
							AcideMenuMenu.SAVE_MENU_NAME, saveMenu.isVisible());
					AcideMenuItemsConfiguration
							.getInstance()
							.getSubmenu(
									AcideConfigurationMenu.CONFIGURATION_MENU_NAME)
							.getSubmenu(AcideMenuMenu.MENU_MENU_NAME)
							.getItemsManager()
							.onlyOne(AcideMenuMenu.SAVE_MENU_NAME);
				} else {
					saveMenu = new AcideMenuItemConfiguration(
							AcideMenuMenu.SAVE_MENU_NAME);
					saveMenu.setCommand("$SAVE_MENU");
					saveMenu.setVisible(AcideMenuConfiguration.getInstance()
							.getIsDisplayed(AcideMenuMenu.SAVE_MENU_NAME));
					menu.insertObject(saveMenu);
				}
				saveMenu.setErasable(false);
				saveMenu.setParameter("None");
				// saveMenu.setCommand("");

				AcideMenuItemConfiguration saveMenuAs;
				if (menu.hasItem(AcideMenuMenu.SAVE_MENU_AS_NAME)) {
					saveMenuAs = menu.getItem(AcideMenuMenu.SAVE_MENU_AS_NAME);
					AcideMenuConfiguration.getInstance().setIsDisplayed(
							AcideMenuMenu.SAVE_MENU_AS_NAME,
							saveMenuAs.isVisible());
					AcideMenuItemsConfiguration
							.getInstance()
							.getSubmenu(
									AcideConfigurationMenu.CONFIGURATION_MENU_NAME)
							.getSubmenu(AcideMenuMenu.MENU_MENU_NAME)
							.getItemsManager()
							.onlyOne(AcideMenuMenu.SAVE_MENU_AS_NAME);
				} else {
					saveMenuAs = new AcideMenuItemConfiguration(
							AcideMenuMenu.SAVE_MENU_AS_NAME);
					saveMenuAs.setCommand("$SAVE_MENU_AS");
					saveMenuAs.setVisible(AcideMenuConfiguration.getInstance()
							.getIsDisplayed(AcideMenuMenu.SAVE_MENU_AS_NAME));
					menu.insertObject(saveMenuAs);
				}
				saveMenuAs.setErasable(false);
				saveMenuAs.setParameter("None");
				// saveMenuAs.setCommand("");

				AcideMenuItemsConfiguration
						.getInstance()
						.getSubmenu(
								AcideConfigurationMenu.CONFIGURATION_MENU_NAME)
						.getItemsManager()
						.onlyOne(AcideConfigurationMenu.MENU_NAME);

			}

			if (!configuration.hasSubmenu(AcideToolBarMenu.TOOLBAR_MENU_NAME)) {

				AcideMenuSubmenuConfiguration toolbar = AcideMenuItemsConfiguration
						.getInstance().getToolbarDefaultSubmenu();

				AcideMenuItemConfiguration newToolbar = toolbar
						.getItem(AcideToolBarMenu.NEW_TOOLBAR_NAME);
				newToolbar.setVisible(AcideMenuConfiguration.getInstance()
						.getIsDisplayed(AcideToolBarMenu.NEW_TOOLBAR_NAME));

				AcideMenuItemConfiguration loadToolbar = toolbar
						.getItem(AcideToolBarMenu.LOAD_TOOLBAR_NAME);
				loadToolbar.setVisible(AcideMenuConfiguration.getInstance()
						.getIsDisplayed(AcideToolBarMenu.LOAD_TOOLBAR_NAME));

				AcideMenuItemConfiguration modifyToolbar = toolbar
						.getItem(AcideToolBarMenu.MODIFY_TOOLBAR_NAME);
				modifyToolbar.setVisible(AcideMenuConfiguration.getInstance()
						.getIsDisplayed(AcideToolBarMenu.MODIFY_TOOLBAR_NAME));

				AcideMenuItemConfiguration saveToolbar = toolbar
						.getItem(AcideToolBarMenu.SAVE_TOOLBAR_NAME);
				saveToolbar.setVisible(AcideMenuConfiguration.getInstance()
						.getIsDisplayed(AcideToolBarMenu.SAVE_TOOLBAR_NAME));

				AcideMenuItemConfiguration saveToolbarAs = toolbar
						.getItem(AcideToolBarMenu.SAVE_TOOLBAR_AS_NAME);
				saveToolbarAs.setVisible(AcideMenuConfiguration.getInstance()
						.getIsDisplayed(AcideToolBarMenu.SAVE_TOOLBAR_AS_NAME));

			} else {

				AcideMenuSubmenuConfiguration toolbar = configuration
						.getSubmenu(AcideToolBarMenu.TOOLBAR_MENU_NAME);

				AcideMenuItemConfiguration newToolbar;
				if (toolbar.hasItem(AcideToolBarMenu.NEW_TOOLBAR_NAME)) {
					newToolbar = toolbar
							.getItem(AcideToolBarMenu.NEW_TOOLBAR_NAME);
					AcideMenuConfiguration.getInstance().setIsDisplayed(
							AcideToolBarMenu.NEW_TOOLBAR_NAME,
							newToolbar.isVisible());
					AcideMenuItemsConfiguration
							.getInstance()
							.getSubmenu(
									AcideConfigurationMenu.CONFIGURATION_MENU_NAME)
							.getSubmenu(AcideToolBarMenu.TOOLBAR_MENU_NAME)
							.getItemsManager()
							.onlyOne(AcideToolBarMenu.NEW_TOOLBAR_NAME);

				} else {
					newToolbar = new AcideMenuItemConfiguration(
							AcideToolBarMenu.NEW_TOOLBAR_NAME);
					newToolbar.setCommand("$NEW_TOOLBAR");
					newToolbar.setVisible(AcideMenuConfiguration.getInstance()
							.getIsDisplayed(AcideToolBarMenu.NEW_TOOLBAR_NAME));
					toolbar.insertObject(newToolbar);
				}
				newToolbar.setErasable(false);
				newToolbar.setParameter("None");
				// newToolbar.setCommand("");

				AcideMenuItemConfiguration loadToolbar;
				if (toolbar.hasItem(AcideToolBarMenu.LOAD_TOOLBAR_NAME)) {
					loadToolbar = toolbar
							.getItem(AcideToolBarMenu.LOAD_TOOLBAR_NAME);
					AcideMenuConfiguration.getInstance().setIsDisplayed(
							AcideToolBarMenu.LOAD_TOOLBAR_NAME,
							loadToolbar.isVisible());
					AcideMenuItemsConfiguration
							.getInstance()
							.getSubmenu(
									AcideConfigurationMenu.CONFIGURATION_MENU_NAME)
							.getSubmenu(AcideToolBarMenu.TOOLBAR_MENU_NAME)
							.getItemsManager()
							.onlyOne(AcideToolBarMenu.LOAD_TOOLBAR_NAME);
				} else {
					loadToolbar = new AcideMenuItemConfiguration(
							AcideToolBarMenu.LOAD_TOOLBAR_NAME);
					loadToolbar.setCommand("$LOAD_TOOLBAR");
					loadToolbar
							.setVisible(AcideMenuConfiguration.getInstance()
									.getIsDisplayed(
											AcideToolBarMenu.LOAD_TOOLBAR_NAME));
					toolbar.insertObject(loadToolbar);
				}
				loadToolbar.setErasable(false);
				loadToolbar.setParameter("None");
				// loadToolbar.setCommand("");

				AcideMenuItemConfiguration modifyToolbar;
				if (toolbar.hasItem(AcideToolBarMenu.MODIFY_TOOLBAR_NAME)) {
					modifyToolbar = toolbar
							.getItem(AcideToolBarMenu.MODIFY_TOOLBAR_NAME);
					AcideMenuConfiguration.getInstance().setIsDisplayed(
							AcideToolBarMenu.MODIFY_TOOLBAR_NAME,
							modifyToolbar.isVisible());
					AcideMenuItemsConfiguration
							.getInstance()
							.getSubmenu(
									AcideConfigurationMenu.CONFIGURATION_MENU_NAME)
							.getSubmenu(AcideToolBarMenu.TOOLBAR_MENU_NAME)
							.getItemsManager()
							.onlyOne(AcideToolBarMenu.MODIFY_TOOLBAR_NAME);
				} else {
					modifyToolbar = new AcideMenuItemConfiguration(
							AcideToolBarMenu.MODIFY_TOOLBAR_NAME);
					modifyToolbar.setCommand("$MODIFY_TOOLBAR");
					modifyToolbar.setVisible(AcideMenuConfiguration
							.getInstance().getIsDisplayed(
									AcideToolBarMenu.MODIFY_TOOLBAR_NAME));
					toolbar.insertObject(modifyToolbar);
				}
				modifyToolbar.setErasable(false);
				modifyToolbar.setParameter("None");
				// modifyToolbar.setCommand("");

				AcideMenuItemConfiguration saveToolbar;
				if (toolbar.hasItem(AcideToolBarMenu.SAVE_TOOLBAR_NAME)) {
					saveToolbar = toolbar
							.getItem(AcideToolBarMenu.SAVE_TOOLBAR_NAME);
					AcideMenuConfiguration.getInstance().setIsDisplayed(
							AcideToolBarMenu.SAVE_TOOLBAR_NAME,
							saveToolbar.isVisible());
					AcideMenuItemsConfiguration
							.getInstance()
							.getSubmenu(
									AcideConfigurationMenu.CONFIGURATION_MENU_NAME)
							.getSubmenu(AcideToolBarMenu.TOOLBAR_MENU_NAME)
							.getItemsManager()
							.onlyOne(AcideToolBarMenu.SAVE_TOOLBAR_NAME);
				} else {
					saveToolbar = new AcideMenuItemConfiguration(
							AcideToolBarMenu.SAVE_TOOLBAR_NAME);
					saveToolbar.setCommand("$SAVE_TOOLBAR");
					saveToolbar
							.setVisible(AcideMenuConfiguration.getInstance()
									.getIsDisplayed(
											AcideToolBarMenu.SAVE_TOOLBAR_NAME));
					toolbar.insertObject(saveToolbar);
				}
				saveToolbar.setErasable(false);
				saveToolbar.setParameter("None");
				// saveToolbar.setCommand("");

				AcideMenuItemConfiguration saveToolbarAs;
				if (toolbar.hasItem(AcideToolBarMenu.SAVE_TOOLBAR_AS_NAME)) {
					saveToolbarAs = toolbar
							.getItem(AcideToolBarMenu.SAVE_TOOLBAR_AS_NAME);
					AcideMenuConfiguration.getInstance().setIsDisplayed(
							AcideToolBarMenu.SAVE_TOOLBAR_AS_NAME,
							saveToolbarAs.isVisible());
					AcideMenuItemsConfiguration
							.getInstance()
							.getSubmenu(
									AcideConfigurationMenu.CONFIGURATION_MENU_NAME)
							.getSubmenu(AcideToolBarMenu.TOOLBAR_MENU_NAME)
							.getItemsManager()
							.onlyOne(AcideToolBarMenu.SAVE_TOOLBAR_AS_NAME);
				} else {
					saveToolbarAs = new AcideMenuItemConfiguration(
							AcideToolBarMenu.SAVE_TOOLBAR_AS_NAME);
					saveToolbarAs.setCommand("$SAVE_TOOLBAR_AS");
					saveToolbarAs.setVisible(AcideMenuConfiguration
							.getInstance().getIsDisplayed(
									AcideToolBarMenu.SAVE_TOOLBAR_AS_NAME));
					toolbar.insertObject(saveToolbarAs);
				}
				saveToolbarAs.setErasable(false);
				saveToolbarAs.setParameter("None");
				// saveToolbarAs.setCommand("");

				AcideMenuItemsConfiguration
						.getInstance()
						.getSubmenu(
								AcideConfigurationMenu.CONFIGURATION_MENU_NAME)
						.getItemsManager()
						.onlyOne(AcideConfigurationMenu.TOOLBAR_NAME);

			}

			AcideMenuItemsConfiguration.getInstance().onlyOne(
					AcideConfigurationMenu.CONFIGURATION_MENU_NAME);
		}

	}

	/**
	 * Checks that the view menu configuration has the correct format
	 */
	private void buildViewMenuConfiguration() {

		if (!AcideMenuItemsConfiguration.getInstance().hasSubmenu(
				AcideViewMenu.VIEW_MENU_NAME)) {
			
			AcideMenuSubmenuConfiguration view = AcideMenuItemsConfiguration
					.getInstance().getViewDefaultSubmenu();

			AcideMenuItemConfiguration showLog = view
					.getItem(AcideViewMenu.SHOW_LOG_TAB_NAME);
			showLog.setVisible(AcideMenuConfiguration.getInstance()
					.getIsDisplayed(AcideViewMenu.SHOW_LOG_TAB_NAME));

			AcideMenuItemConfiguration showExplorer = view
					.getItem(AcideViewMenu.SHOW_EXPLORER_PANEL_NAME);
			showExplorer.setVisible(AcideMenuConfiguration.getInstance()
					.getIsDisplayed(AcideViewMenu.SHOW_EXPLORER_PANEL_NAME));

			AcideMenuItemConfiguration showConsole = view
					.getItem(AcideViewMenu.SHOW_CONSOLE_PANEL_NAME);
			showConsole.setVisible(AcideMenuConfiguration.getInstance()
					.getIsDisplayed(AcideViewMenu.SHOW_CONSOLE_PANEL_NAME));

			AcideMenuItemConfiguration showDatabase = view
					.getItem(AcideViewMenu.SHOW_DATA_BASE_PANEL_NAME);
			showDatabase.setVisible(AcideMenuConfiguration.getInstance()
					.getIsDisplayed(AcideViewMenu.SHOW_DATA_BASE_PANEL_NAME));

			AcideMenuItemConfiguration showGraph = view
					.getItem(AcideViewMenu.SHOW_GRAPH_PANEL_NAME);
			showGraph.setVisible(AcideMenuConfiguration.getInstance()
					.getIsDisplayed(AcideViewMenu.SHOW_GRAPH_PANEL_NAME));
			
			AcideMenuItemConfiguration showDebug= view
					.getItem(AcideViewMenu.SHOW_DEBUG_PANEL_NAME);
			showDebug.setVisible(AcideMenuConfiguration.getInstance()
					.getIsDisplayed(AcideViewMenu.SHOW_DEBUG_PANEL_NAME));
			
			AcideMenuItemConfiguration showAssertedDatabase= view
					.getItem(AcideViewMenu.SHOW_ASSERTED_DATABASE_PANEL_NAME);
			showAssertedDatabase.setVisible(AcideMenuConfiguration.getInstance()
					.getIsDisplayed(AcideViewMenu.SHOW_ASSERTED_DATABASE_PANEL_NAME));

		} else {

			AcideMenuSubmenuConfiguration view = AcideMenuItemsConfiguration
					.getInstance().getSubmenu(AcideViewMenu.VIEW_MENU_NAME);

			AcideMenuItemConfiguration showLog;
			if (view.hasItem(AcideViewMenu.SHOW_LOG_TAB_NAME)) {
				showLog = view.getItem(AcideViewMenu.SHOW_LOG_TAB_NAME);
				AcideMenuConfiguration.getInstance().setIsDisplayed(
						AcideViewMenu.SHOW_LOG_TAB_NAME, showLog.isVisible());
				AcideMenuItemsConfiguration.getInstance()
						.getSubmenu(AcideViewMenu.VIEW_MENU_NAME)
						.getItemsManager()
						.onlyOne(AcideViewMenu.SHOW_LOG_TAB_NAME);
			} else {
				showLog = new AcideMenuItemConfiguration(
						AcideViewMenu.SHOW_LOG_TAB_NAME);
				showLog.setImage("./resources/icons/menu/view/showLogTab.png");
				showLog.setCommand("$SHOW_LOG_TAB");
				showLog.setVisible(AcideMenuConfiguration.getInstance()
						.getIsDisplayed(AcideViewMenu.SHOW_LOG_TAB_NAME));
				view.insertObject(showLog);
			}
			showLog.setErasable(false);
			showLog.setParameter("None");
			// showLog.setCommand("");

			AcideMenuItemConfiguration showExplorer;
			if (view.hasItem(AcideViewMenu.SHOW_EXPLORER_PANEL_NAME)) {
				showExplorer = view
						.getItem(AcideViewMenu.SHOW_EXPLORER_PANEL_NAME);
				AcideMenuConfiguration.getInstance().setIsDisplayed(
						AcideViewMenu.SHOW_EXPLORER_PANEL_NAME,
						showExplorer.isVisible());
				AcideMenuItemsConfiguration.getInstance()
						.getSubmenu(AcideViewMenu.VIEW_MENU_NAME)
						.getItemsManager()
						.onlyOne(AcideViewMenu.SHOW_EXPLORER_PANEL_NAME);

			} else {
				showExplorer = new AcideMenuItemConfiguration(
						AcideViewMenu.SHOW_EXPLORER_PANEL_NAME);
				showExplorer
						.setImage("./resources/icons/menu/view/showExplorerPanel.png");
				showExplorer.setCommand("$SHOW_EXPLORER_PANEL");
				showExplorer
						.setVisible(AcideMenuConfiguration.getInstance()
								.getIsDisplayed(
										AcideViewMenu.SHOW_EXPLORER_PANEL_NAME));
				view.insertObject(showExplorer);
			}
			showExplorer.setErasable(false);
			showExplorer.setParameter("None");
			// showExplorer.setCommand("");

			AcideMenuItemConfiguration showConsole;
			if (view.hasItem(AcideViewMenu.SHOW_CONSOLE_PANEL_NAME)) {
				showConsole = view
						.getItem(AcideViewMenu.SHOW_CONSOLE_PANEL_NAME);
				AcideMenuConfiguration.getInstance().setIsDisplayed(
						AcideViewMenu.SHOW_CONSOLE_PANEL_NAME,
						showConsole.isVisible());
				AcideMenuItemsConfiguration.getInstance()
						.getSubmenu(AcideViewMenu.VIEW_MENU_NAME)
						.getItemsManager()
						.onlyOne(AcideViewMenu.SHOW_CONSOLE_PANEL_NAME);
			} else {
				showConsole = new AcideMenuItemConfiguration(
						AcideViewMenu.SHOW_CONSOLE_PANEL_NAME);
				showConsole
						.setImage("./resources/icons/menu/view/showConsolePanel.png");
				showConsole.setCommand("$SHOW_CONSOLE_PANEL");
				showConsole.setVisible(AcideMenuConfiguration.getInstance()
						.getIsDisplayed(AcideViewMenu.SHOW_CONSOLE_PANEL_NAME));
				view.insertObject(showConsole);
			}
			showConsole.setErasable(false);
			showConsole.setParameter("None");
			// showConsole.setCommand("");

			AcideMenuItemConfiguration showDatabase;
			if (view.hasItem(AcideViewMenu.SHOW_DATA_BASE_PANEL_NAME)) {
				showDatabase = view
						.getItem(AcideViewMenu.SHOW_DATA_BASE_PANEL_NAME);
				AcideMenuConfiguration.getInstance().setIsDisplayed(
						AcideViewMenu.SHOW_DATA_BASE_PANEL_NAME,
						showDatabase.isVisible());
				AcideMenuItemsConfiguration.getInstance()
						.getSubmenu(AcideViewMenu.VIEW_MENU_NAME)
						.getItemsManager()
						.onlyOne(AcideViewMenu.SHOW_DATA_BASE_PANEL_NAME);
			} else {
				showDatabase = new AcideMenuItemConfiguration(
						AcideViewMenu.SHOW_DATA_BASE_PANEL_NAME);
				showDatabase
						.setImage("./resources/icons/menu/view/showDataBasePanel.png");
				showDatabase.setCommand("$SHOW_DATABASE_PANEL");
				showDatabase
						.setVisible(AcideMenuConfiguration
								.getInstance()
								.getIsDisplayed(
										AcideViewMenu.SHOW_DATA_BASE_PANEL_NAME));
				view.insertObject(showDatabase);
			}
			showDatabase.setErasable(false);
			showDatabase.setParameter("None");
			// showDatabase.setCommand("");

//			AcideMenuItemsConfiguration.getInstance().onlyOne(
//					AcideViewMenu.VIEW_MENU_NAME);
			
			AcideMenuItemConfiguration showGraph;
			if (view.hasItem(AcideViewMenu.SHOW_GRAPH_PANEL_NAME)) {
				showGraph = view
						.getItem(AcideViewMenu.SHOW_GRAPH_PANEL_NAME);
				AcideMenuConfiguration.getInstance().setIsDisplayed(
						AcideViewMenu.SHOW_GRAPH_PANEL_NAME,
						showGraph.isVisible());
				AcideMenuItemsConfiguration.getInstance()
						.getSubmenu(AcideViewMenu.VIEW_MENU_NAME)
						.getItemsManager()
						.onlyOne(AcideViewMenu.SHOW_GRAPH_PANEL_NAME);
			} else {
				showGraph = new AcideMenuItemConfiguration(
						AcideViewMenu.SHOW_GRAPH_PANEL_NAME);
				showGraph
						.setImage("./resources/icons/menu/view/showGraphPanel.png");
				showGraph.setCommand("$SHOW_GRAPH_PANEL");
				showGraph.setVisible(AcideMenuConfiguration.getInstance()
								.getIsDisplayed(AcideViewMenu.SHOW_GRAPH_PANEL_NAME));
				view.insertObject(showGraph);
			}
			showGraph.setErasable(false);
			showGraph.setParameter("None");
			// showGraphbase.setCommand("");
			
			AcideMenuItemConfiguration showDebug;
			if (view.hasItem(AcideViewMenu.SHOW_DEBUG_PANEL_NAME)) {
				showDebug = view
						.getItem(AcideViewMenu.SHOW_DEBUG_PANEL_NAME);
				AcideMenuConfiguration.getInstance().setIsDisplayed(
						AcideViewMenu.SHOW_DEBUG_PANEL_NAME,
						showDebug.isVisible());
				AcideMenuItemsConfiguration.getInstance()
						.getSubmenu(AcideViewMenu.VIEW_MENU_NAME)
						.getItemsManager()
						.onlyOne(AcideViewMenu.SHOW_DEBUG_PANEL_NAME);
			} else {
				showDebug = new AcideMenuItemConfiguration(
						AcideViewMenu.SHOW_DEBUG_PANEL_NAME);
				showDebug
						.setImage("./resources/icons/menu/view/showDebugPanel.png");
				showDebug.setCommand("$SHOW_DEBUG_PANEL");
				showDebug.setVisible(AcideMenuConfiguration.getInstance()
								.getIsDisplayed(AcideViewMenu.SHOW_DEBUG_PANEL_NAME));
				view.insertObject(showDebug);
			}
			showDebug.setErasable(false);
			showDebug.setParameter("None");
			// showDebug.setCommand("");

			
			AcideMenuItemConfiguration showAssertedDatabase;
			if (view.hasItem(AcideViewMenu.SHOW_ASSERTED_DATABASE_PANEL_NAME)) {
				showAssertedDatabase = view
						.getItem(AcideViewMenu.SHOW_ASSERTED_DATABASE_PANEL_NAME);
				AcideMenuConfiguration.getInstance().setIsDisplayed(
						AcideViewMenu.SHOW_ASSERTED_DATABASE_PANEL_NAME,
						showAssertedDatabase.isVisible());
				AcideMenuItemsConfiguration.getInstance()
						.getSubmenu(AcideViewMenu.VIEW_MENU_NAME)
						.getItemsManager()
						.onlyOne(AcideViewMenu.SHOW_ASSERTED_DATABASE_PANEL_NAME);
			} else {
				showAssertedDatabase = new AcideMenuItemConfiguration(
						AcideViewMenu.SHOW_ASSERTED_DATABASE_PANEL_NAME);
				showAssertedDatabase
						.setImage("./resources/icons/menu/view/showAssertedDatabasePanel.png");
				showAssertedDatabase.setCommand("$SHOW_ASSERTED_DATABASE_PANEL");
				showAssertedDatabase.setVisible(AcideMenuConfiguration.getInstance()
								.getIsDisplayed(AcideViewMenu.SHOW_ASSERTED_DATABASE_PANEL_NAME));
				view.insertObject(showAssertedDatabase);
			}
			showAssertedDatabase.setErasable(false);
			showAssertedDatabase.setParameter("None");
			// showAssertedDatabase.setCommand("");
			
			AcideMenuItemsConfiguration.getInstance().onlyOne(
					AcideViewMenu.VIEW_MENU_NAME);

		}

	}

	/**
	 * Checks that the project menu configuration has the correct format
	 */
	private void buildProjectMenuConfiguration() {

		if (!AcideMenuItemsConfiguration.getInstance().hasSubmenu(
				AcideProjectMenu.PROJECT_MENU_NAME)) {
			AcideMenuSubmenuConfiguration project = AcideMenuItemsConfiguration
					.getInstance().getProjectDefaultSubmenu();

			AcideMenuItemConfiguration newProject = project
					.getItem(AcideProjectMenu.NEW_PROJECT_NAME);
			newProject.setVisible(AcideMenuConfiguration.getInstance()
					.getIsDisplayed(AcideProjectMenu.NEW_PROJECT_NAME));

			AcideMenuItemConfiguration openProject = project
					.getItem(AcideProjectMenu.OPEN_PROJECT_NAME);
			openProject.setVisible(AcideMenuConfiguration.getInstance()
					.getIsDisplayed(AcideProjectMenu.OPEN_PROJECT_NAME));

			AcideMenuSubmenuConfiguration openRecentProjects = project
					.getSubmenu(AcideProjectMenu.OPEN_RECENT_PROJECTS_NAME);
			openRecentProjects
					.setVisible(AcideMenuConfiguration.getInstance()
							.getIsDisplayed(
									AcideProjectMenu.OPEN_RECENT_PROJECTS_NAME));

			AcideMenuItemConfiguration closeProject = project
					.getItem(AcideProjectMenu.CLOSE_PROJECT_NAME);
			closeProject.setVisible(AcideMenuConfiguration.getInstance()
					.getIsDisplayed(AcideProjectMenu.CLOSE_PROJECT_NAME));

			AcideMenuItemConfiguration saveProject = project
					.getItem(AcideProjectMenu.SAVE_PROJECT_NAME);
			saveProject.setVisible(AcideMenuConfiguration.getInstance()
					.getIsDisplayed(AcideProjectMenu.SAVE_PROJECT_NAME));

			AcideMenuItemConfiguration saveProjectAs = project
					.getItem(AcideProjectMenu.SAVE_PROJECT_AS_NAME);
			saveProjectAs.setVisible(AcideMenuConfiguration.getInstance()
					.getIsDisplayed(AcideProjectMenu.SAVE_PROJECT_AS_NAME));

			AcideMenuItemConfiguration addOpenedFiles = project
					.getItem(AcideProjectMenu.ADD_OPENED_FILES_NAME);
			addOpenedFiles.setVisible(AcideMenuConfiguration.getInstance()
					.getIsDisplayed(AcideProjectMenu.ADD_OPENED_FILES_NAME));

			AcideMenuItemConfiguration newProjectFile = project
					.getItem(AcideProjectMenu.NEW_PROJECT_FILE_NAME);
			newProjectFile.setVisible(AcideMenuConfiguration.getInstance()
					.getIsDisplayed(AcideProjectMenu.NEW_PROJECT_FILE_NAME));

			AcideMenuItemConfiguration addFile = project
					.getItem(AcideProjectMenu.ADD_FILE_NAME);
			addFile.setVisible(AcideMenuConfiguration.getInstance()
					.getIsDisplayed(AcideProjectMenu.ADD_FILE_NAME));

			AcideMenuItemConfiguration removeFile = project
					.getItem(AcideProjectMenu.REMOVE_FILE_NAME);
			removeFile.setVisible(AcideMenuConfiguration.getInstance()
					.getIsDisplayed(AcideProjectMenu.REMOVE_FILE_NAME));

			AcideMenuItemConfiguration deleteFile = project
					.getItem(AcideProjectMenu.DELETE_FILE_NAME);
			deleteFile.setVisible(AcideMenuConfiguration.getInstance()
					.getIsDisplayed(AcideProjectMenu.DELETE_FILE_NAME));

			AcideMenuItemConfiguration addFolder = project
					.getItem(AcideProjectMenu.ADD_FOLDER_NAME);
			addFolder.setVisible(AcideMenuConfiguration.getInstance()
					.getIsDisplayed(AcideProjectMenu.ADD_FOLDER_NAME));

			AcideMenuItemConfiguration removeFolder = project
					.getItem(AcideProjectMenu.REMOVE_FOLDER_NAME);
			removeFolder.setVisible(AcideMenuConfiguration.getInstance()
					.getIsDisplayed(AcideProjectMenu.REMOVE_FOLDER_NAME));

			AcideMenuItemConfiguration compile = project
					.getItem(AcideProjectMenu.COMPILE_NAME);
			compile.setVisible(AcideMenuConfiguration.getInstance()
					.getIsDisplayed(AcideProjectMenu.COMPILE_NAME));

			AcideMenuItemConfiguration execute = project
					.getItem(AcideProjectMenu.EXECUTE_NAME);
			execute.setVisible(AcideMenuConfiguration.getInstance()
					.getIsDisplayed(AcideProjectMenu.EXECUTE_NAME));

			AcideMenuItemConfiguration setCompilable = project
					.getItem(AcideProjectMenu.SET_COMPILABLE_FILE_NAME);
			setCompilable.setVisible(AcideMenuConfiguration.getInstance()
					.getIsDisplayed(AcideProjectMenu.SET_COMPILABLE_FILE_NAME));

			AcideMenuItemConfiguration unsetCompilable = project
					.getItem(AcideProjectMenu.UNSET_COMPILABLE_FILE_NAME);
			unsetCompilable
					.setVisible(AcideMenuConfiguration
							.getInstance()
							.getIsDisplayed(
									AcideProjectMenu.UNSET_COMPILABLE_FILE_NAME));

			AcideMenuItemConfiguration setMain = project
					.getItem(AcideProjectMenu.SET_MAIN_FILE_NAME);
			setMain.setVisible(AcideMenuConfiguration.getInstance()
					.getIsDisplayed(AcideProjectMenu.SET_MAIN_FILE_NAME));

			AcideMenuItemConfiguration unsetMain = project
					.getItem(AcideProjectMenu.UNSET_MAIN_FILE_NAME);
			unsetMain.setVisible(AcideMenuConfiguration.getInstance()
					.getIsDisplayed(AcideProjectMenu.UNSET_MAIN_FILE_NAME));

		} else {

			AcideMenuSubmenuConfiguration project = AcideMenuItemsConfiguration
					.getInstance().getSubmenu(
							AcideProjectMenu.PROJECT_MENU_NAME);

			AcideMenuItemConfiguration newProject;
			if (project.hasItem(AcideProjectMenu.NEW_PROJECT_NAME)) {
				newProject = project.getItem(AcideProjectMenu.NEW_PROJECT_NAME);
				AcideMenuConfiguration.getInstance().setIsDisplayed(
						AcideProjectMenu.NEW_PROJECT_NAME,
						newProject.isVisible());
				AcideMenuItemsConfiguration.getInstance()
						.getSubmenu(AcideProjectMenu.PROJECT_MENU_NAME)
						.getItemsManager()
						.onlyOne(AcideProjectMenu.NEW_PROJECT_NAME);

			} else {
				newProject = new AcideMenuItemConfiguration(
						AcideProjectMenu.NEW_PROJECT_NAME);
				newProject
						.setImage("./resources/icons/menu/project/newProject.png");
				newProject.setCommand("$NEW_PROJECT");
				newProject.setVisible(AcideMenuConfiguration.getInstance()
						.getIsDisplayed(AcideProjectMenu.NEW_PROJECT_NAME));
				project.insertObject(newProject);
			}
			newProject.setErasable(false);
			newProject.setParameter("None");
			// newProject.setCommand("");

			AcideMenuItemConfiguration openProject;
			if (project.hasItem(AcideProjectMenu.OPEN_PROJECT_NAME)) {
				openProject = project
						.getItem(AcideProjectMenu.OPEN_PROJECT_NAME);
				AcideMenuConfiguration.getInstance().setIsDisplayed(
						AcideProjectMenu.OPEN_PROJECT_NAME,
						openProject.isVisible());
				AcideMenuItemsConfiguration.getInstance()
						.getSubmenu(AcideProjectMenu.PROJECT_MENU_NAME)
						.getItemsManager()
						.onlyOne(AcideProjectMenu.OPEN_PROJECT_NAME);

			} else {
				openProject = new AcideMenuItemConfiguration(
						AcideProjectMenu.OPEN_PROJECT_NAME);
				openProject
						.setImage("./resources/icons/menu/project/openProject.png");
				openProject.setCommand("$OPEN_PROJECT");
				openProject.setVisible(AcideMenuConfiguration.getInstance()
						.getIsDisplayed(AcideProjectMenu.OPEN_PROJECT_NAME));
				project.insertObject(openProject);
			}
			openProject.setErasable(false);
			openProject.setParameter("None");
			// openProject.setCommand("");

			AcideMenuSubmenuConfiguration openRecentProjects;
			if (project.hasSubmenu(AcideProjectMenu.OPEN_RECENT_PROJECTS_NAME)) {
				openRecentProjects = project
						.getSubmenu(AcideProjectMenu.OPEN_RECENT_PROJECTS_NAME);
				AcideMenuConfiguration.getInstance().setIsDisplayed(
						AcideProjectMenu.OPEN_RECENT_PROJECTS_NAME,
						openRecentProjects.isVisible());
				AcideMenuItemsConfiguration.getInstance()
						.getSubmenu(AcideProjectMenu.PROJECT_MENU_NAME)
						.getItemsManager()
						.onlyOne(AcideProjectMenu.OPEN_RECENT_PROJECTS_NAME);
			} else {
				openRecentProjects = new AcideMenuSubmenuConfiguration(
						AcideProjectMenu.OPEN_RECENT_PROJECTS_NAME);
				openRecentProjects.setVisible(AcideMenuConfiguration
						.getInstance().getIsDisplayed(
								AcideProjectMenu.OPEN_RECENT_PROJECTS_NAME));
				project.insertObject(openRecentProjects);
			}
			openRecentProjects.setErasable(false);

			AcideMenuItemConfiguration closeProject;
			if (project.hasItem(AcideProjectMenu.CLOSE_PROJECT_NAME)) {
				closeProject = project
						.getItem(AcideProjectMenu.CLOSE_PROJECT_NAME);
				AcideMenuConfiguration.getInstance().setIsDisplayed(
						AcideProjectMenu.CLOSE_PROJECT_NAME,
						closeProject.isVisible());
				AcideMenuItemsConfiguration.getInstance()
						.getSubmenu(AcideProjectMenu.PROJECT_MENU_NAME)
						.getItemsManager()
						.onlyOne(AcideProjectMenu.CLOSE_PROJECT_NAME);
			} else {
				closeProject = new AcideMenuItemConfiguration(
						AcideProjectMenu.CLOSE_PROJECT_NAME);
				closeProject
						.setImage("./resources/icons/menu/project/closeProject.png");
				closeProject.setCommand("$CLOSE_PROJECT");
				closeProject.setVisible(AcideMenuConfiguration.getInstance()
						.getIsDisplayed(AcideProjectMenu.CLOSE_PROJECT_NAME));
				project.insertObject(closeProject);
			}
			closeProject.setErasable(false);
			closeProject.setParameter("None");
			// closeProject.setCommand("");

			AcideMenuItemConfiguration saveProject;
			if (project.hasItem(AcideProjectMenu.SAVE_PROJECT_NAME)) {
				saveProject = project
						.getItem(AcideProjectMenu.SAVE_PROJECT_NAME);
				AcideMenuConfiguration.getInstance().setIsDisplayed(
						AcideProjectMenu.SAVE_PROJECT_NAME,
						saveProject.isVisible());
				AcideMenuItemsConfiguration.getInstance()
						.getSubmenu(AcideProjectMenu.PROJECT_MENU_NAME)
						.getItemsManager()
						.onlyOne(AcideProjectMenu.SAVE_PROJECT_NAME);
			} else {
				saveProject = new AcideMenuItemConfiguration(
						AcideProjectMenu.SAVE_PROJECT_NAME);
				saveProject
						.setImage("./resources/icons/menu/project/saveProject.png");
				saveProject.setCommand("$SAVE_PROJECT");
				saveProject.setVisible(AcideMenuConfiguration.getInstance()
						.getIsDisplayed(AcideProjectMenu.SAVE_PROJECT_NAME));
				project.insertObject(saveProject);
			}
			saveProject.setErasable(false);
			saveProject.setParameter("None");
			// saveProject.setCommand("");

			AcideMenuItemConfiguration saveProjectAs;
			if (project.hasItem(AcideProjectMenu.SAVE_PROJECT_AS_NAME)) {
				saveProjectAs = project
						.getItem(AcideProjectMenu.SAVE_PROJECT_AS_NAME);
				AcideMenuConfiguration.getInstance().setIsDisplayed(
						AcideProjectMenu.SAVE_PROJECT_AS_NAME,
						saveProjectAs.isVisible());
				AcideMenuItemsConfiguration.getInstance()
						.getSubmenu(AcideProjectMenu.PROJECT_MENU_NAME)
						.getItemsManager()
						.onlyOne(AcideProjectMenu.SAVE_PROJECT_AS_NAME);
			} else {
				saveProjectAs = new AcideMenuItemConfiguration(
						AcideProjectMenu.SAVE_PROJECT_AS_NAME);
				saveProjectAs
						.setImage("./resources/icons/menu/project/saveProjectAs.png");
				saveProjectAs.setCommand("$SAVE_PROJECT_AS");
				saveProjectAs.setVisible(AcideMenuConfiguration.getInstance()
						.getIsDisplayed(AcideProjectMenu.SAVE_PROJECT_AS_NAME));
				project.insertObject(saveProjectAs);
			}
			saveProjectAs.setErasable(false);
			saveProjectAs.setParameter("None");
			// saveProjectAs.setCommand("");

			AcideMenuItemConfiguration addOpenedFiles;
			if (project.hasItem(AcideProjectMenu.ADD_OPENED_FILES_NAME)) {
				addOpenedFiles = project
						.getItem(AcideProjectMenu.ADD_OPENED_FILES_NAME);
				AcideMenuConfiguration.getInstance().setIsDisplayed(
						AcideProjectMenu.ADD_OPENED_FILES_NAME,
						addOpenedFiles.isVisible());
				AcideMenuItemsConfiguration.getInstance()
						.getSubmenu(AcideProjectMenu.PROJECT_MENU_NAME)
						.getItemsManager()
						.onlyOne(AcideProjectMenu.ADD_OPENED_FILES_NAME);
			} else {
				addOpenedFiles = new AcideMenuItemConfiguration(
						AcideProjectMenu.ADD_OPENED_FILES_NAME);
				addOpenedFiles
						.setImage("./resources/icons/menu/project/addOpenedFiles.png");
				addOpenedFiles.setCommand("$ADD_OPENED_FILES");
				addOpenedFiles
						.setVisible(AcideMenuConfiguration.getInstance()
								.getIsDisplayed(
										AcideProjectMenu.ADD_OPENED_FILES_NAME));
				project.insertObject(addOpenedFiles);
			}
			addOpenedFiles.setErasable(false);
			addOpenedFiles.setParameter("None");
			// addOpenedFiles.setCommand("");

			AcideMenuItemConfiguration newProjectFile;
			if (project.hasItem(AcideProjectMenu.NEW_PROJECT_FILE_NAME)) {
				newProjectFile = project
						.getItem(AcideProjectMenu.NEW_PROJECT_FILE_NAME);
				AcideMenuConfiguration.getInstance().setIsDisplayed(
						AcideProjectMenu.NEW_PROJECT_FILE_NAME,
						newProjectFile.isVisible());
				AcideMenuItemsConfiguration.getInstance()
						.getSubmenu(AcideProjectMenu.PROJECT_MENU_NAME)
						.getItemsManager()
						.onlyOne(AcideProjectMenu.NEW_PROJECT_FILE_NAME);
			} else {
				newProjectFile = new AcideMenuItemConfiguration(
						AcideProjectMenu.NEW_PROJECT_FILE_NAME);
				newProjectFile
						.setImage("./resources/icons/menu/project/newFile.png");
				newProjectFile.setCommand("$NEW_PROJECT_FILE");
				newProjectFile
						.setVisible(AcideMenuConfiguration.getInstance()
								.getIsDisplayed(
										AcideProjectMenu.NEW_PROJECT_FILE_NAME));
				project.insertObject(newProjectFile);
			}
			newProjectFile.setErasable(false);
			newProjectFile.setParameter("None");
			// newProjectFile.setCommand("");

			AcideMenuItemConfiguration addFile;
			if (project.hasItem(AcideProjectMenu.ADD_FILE_NAME)) {
				addFile = project.getItem(AcideProjectMenu.ADD_FILE_NAME);
				AcideMenuConfiguration.getInstance().setIsDisplayed(
						AcideProjectMenu.ADD_FILE_NAME, addFile.isVisible());
				AcideMenuItemsConfiguration.getInstance()
						.getSubmenu(AcideProjectMenu.PROJECT_MENU_NAME)
						.getItemsManager()
						.onlyOne(AcideProjectMenu.ADD_FILE_NAME);
			} else {
				addFile = new AcideMenuItemConfiguration(
						AcideProjectMenu.ADD_FILE_NAME);
				addFile.setImage("./resources/icons/menu/project/addFile.png");
				addFile.setCommand("$ADD_FILE");
				addFile.setVisible(AcideMenuConfiguration.getInstance()
						.getIsDisplayed(AcideProjectMenu.ADD_FILE_NAME));
				project.insertObject(addFile);
			}
			addFile.setErasable(false);
			addFile.setParameter("None");
			// addFile.setCommand("");

			AcideMenuItemConfiguration removeFile;
			if (project.hasItem(AcideProjectMenu.REMOVE_FILE_NAME)) {
				removeFile = project.getItem(AcideProjectMenu.REMOVE_FILE_NAME);
				AcideMenuConfiguration.getInstance().setIsDisplayed(
						AcideProjectMenu.REMOVE_FILE_NAME,
						removeFile.isVisible());
				AcideMenuItemsConfiguration.getInstance()
						.getSubmenu(AcideProjectMenu.PROJECT_MENU_NAME)
						.getItemsManager()
						.onlyOne(AcideProjectMenu.REMOVE_FILE_NAME);
			} else {
				removeFile = new AcideMenuItemConfiguration(
						AcideProjectMenu.REMOVE_FILE_NAME);
				removeFile
						.setImage("./resources/icons/menu/project/removeFile.png");
				removeFile.setCommand("$REMOVE_FILE");
				removeFile.setVisible(AcideMenuConfiguration.getInstance()
						.getIsDisplayed(AcideProjectMenu.REMOVE_FILE_NAME));
				project.insertObject(removeFile);
			}
			removeFile.setErasable(false);
			removeFile.setParameter("None");
			// removeFile.setCommand("");

			AcideMenuItemConfiguration deleteFile;
			if (project.hasItem(AcideProjectMenu.DELETE_FILE_NAME)) {
				deleteFile = project.getItem(AcideProjectMenu.DELETE_FILE_NAME);
				AcideMenuConfiguration.getInstance().setIsDisplayed(
						AcideProjectMenu.DELETE_FILE_NAME,
						deleteFile.isVisible());
				AcideMenuItemsConfiguration.getInstance()
						.getSubmenu(AcideProjectMenu.PROJECT_MENU_NAME)
						.getItemsManager()
						.onlyOne(AcideProjectMenu.DELETE_FILE_NAME);
			} else {
				deleteFile = new AcideMenuItemConfiguration(
						AcideProjectMenu.DELETE_FILE_NAME);
				deleteFile
						.setImage("./resources/icons/menu/project/deleteFile.png");
				deleteFile.setCommand("$DELETE_FILE");
				deleteFile.setVisible(AcideMenuConfiguration.getInstance()
						.getIsDisplayed(AcideProjectMenu.DELETE_FILE_NAME));
				project.insertObject(deleteFile);
			}
			deleteFile.setErasable(false);
			deleteFile.setParameter("None");
			// deleteFile.setCommand("");

			AcideMenuItemConfiguration addFolder;
			if (project.hasItem(AcideProjectMenu.ADD_FOLDER_NAME)) {
				addFolder = project.getItem(AcideProjectMenu.ADD_FOLDER_NAME);
				AcideMenuConfiguration.getInstance()
						.setIsDisplayed(AcideProjectMenu.ADD_FOLDER_NAME,
								addFolder.isVisible());
				AcideMenuItemsConfiguration.getInstance()
						.getSubmenu(AcideProjectMenu.PROJECT_MENU_NAME)
						.getItemsManager()
						.onlyOne(AcideProjectMenu.ADD_FOLDER_NAME);
			} else {
				addFolder = new AcideMenuItemConfiguration(
						AcideProjectMenu.ADD_FOLDER_NAME);
				addFolder
						.setImage("./resources/icons/menu/project/addFolder.png");
				addFolder.setCommand("$ADD_FOLDER");
				addFolder.setVisible(AcideMenuConfiguration.getInstance()
						.getIsDisplayed(AcideProjectMenu.ADD_FOLDER_NAME));
				project.insertObject(addFolder);
			}
			addFolder.setErasable(false);
			addFolder.setParameter("None");
			// addFolder.setCommand("");

			AcideMenuItemConfiguration removeFolder;
			if (project.hasItem(AcideProjectMenu.REMOVE_FOLDER_NAME)) {
				removeFolder = project
						.getItem(AcideProjectMenu.REMOVE_FOLDER_NAME);
				AcideMenuConfiguration.getInstance().setIsDisplayed(
						AcideProjectMenu.REMOVE_FOLDER_NAME,
						removeFolder.isVisible());
				AcideMenuItemsConfiguration.getInstance()
						.getSubmenu(AcideProjectMenu.PROJECT_MENU_NAME)
						.getItemsManager()
						.onlyOne(AcideProjectMenu.REMOVE_FOLDER_NAME);
			} else {
				removeFolder = new AcideMenuItemConfiguration(
						AcideProjectMenu.REMOVE_FOLDER_NAME);
				removeFolder
						.setImage("./resources/icons/menu/project/removeFolder.png");
				removeFolder.setCommand("$REMOVE_FOLDER");
				removeFolder.setVisible(AcideMenuConfiguration.getInstance()
						.getIsDisplayed(AcideProjectMenu.REMOVE_FOLDER_NAME));
				project.insertObject(removeFolder);
			}
			removeFolder.setErasable(false);
			removeFolder.setParameter("None");
			// removeFolder.setCommand("");

			AcideMenuItemConfiguration compile;
			if (project.hasItem(AcideProjectMenu.COMPILE_NAME)) {
				compile = project.getItem(AcideProjectMenu.COMPILE_NAME);
				AcideMenuConfiguration.getInstance().setIsDisplayed(
						AcideProjectMenu.COMPILE_NAME, compile.isVisible());
				AcideMenuItemsConfiguration.getInstance()
						.getSubmenu(AcideProjectMenu.PROJECT_MENU_NAME)
						.getItemsManager()
						.onlyOne(AcideProjectMenu.COMPILE_NAME);
			} else {
				compile = new AcideMenuItemConfiguration(
						AcideProjectMenu.COMPILE_NAME);
				compile.setImage("./resources/icons/menu/project/compile.png");
				compile.setCommand("$COMPILE");
				compile.setVisible(AcideMenuConfiguration.getInstance()
						.getIsDisplayed(AcideProjectMenu.COMPILE_NAME));
				project.insertObject(compile);
			}
			compile.setErasable(false);
			compile.setParameter("None");
			// compile.setCommand("");

			AcideMenuItemConfiguration execute;
			if (project.hasItem(AcideProjectMenu.EXECUTE_NAME)) {
				execute = project.getItem(AcideProjectMenu.EXECUTE_NAME);
				AcideMenuConfiguration.getInstance().setIsDisplayed(
						AcideProjectMenu.EXECUTE_NAME, execute.isVisible());
				AcideMenuItemsConfiguration.getInstance()
						.getSubmenu(AcideProjectMenu.PROJECT_MENU_NAME)
						.getItemsManager()
						.onlyOne(AcideProjectMenu.EXECUTE_NAME);
			} else {
				execute = new AcideMenuItemConfiguration(
						AcideProjectMenu.EXECUTE_NAME);
				execute.setImage("./resources/icons/menu/project/execute.png");
				execute.setCommand("$EXECUTE");
				execute.setVisible(AcideMenuConfiguration.getInstance()
						.getIsDisplayed(AcideProjectMenu.EXECUTE_NAME));
				project.insertObject(execute);
			}
			execute.setErasable(false);
			execute.setParameter("None");
			// execute.setCommand("");

			AcideMenuItemConfiguration setCompilableFile;
			if (project.hasItem(AcideProjectMenu.SET_COMPILABLE_FILE_NAME)) {
				setCompilableFile = project
						.getItem(AcideProjectMenu.SET_COMPILABLE_FILE_NAME);
				AcideMenuConfiguration.getInstance().setIsDisplayed(
						AcideProjectMenu.SET_COMPILABLE_FILE_NAME,
						setCompilableFile.isVisible());
				AcideMenuItemsConfiguration.getInstance()
						.getSubmenu(AcideProjectMenu.PROJECT_MENU_NAME)
						.getItemsManager()
						.onlyOne(AcideProjectMenu.SET_COMPILABLE_FILE_NAME);
			} else {
				setCompilableFile = new AcideMenuItemConfiguration(
						AcideProjectMenu.SET_COMPILABLE_FILE_NAME);
				setCompilableFile
						.setImage("./resources/icons/menu/project/setCompilable.png");
				setCompilableFile.setCommand("$SET_COMPILABLE_FILE");
				setCompilableFile.setVisible(AcideMenuConfiguration
						.getInstance().getIsDisplayed(
								AcideProjectMenu.SET_COMPILABLE_FILE_NAME));
				project.insertObject(setCompilableFile);
			}
			setCompilableFile.setErasable(false);
			setCompilableFile.setParameter("None");
			// setCompilableFile.setCommand("");

			AcideMenuItemConfiguration unsetCompilableFile;
			if (project.hasItem(AcideProjectMenu.UNSET_COMPILABLE_FILE_NAME)) {
				unsetCompilableFile = project
						.getItem(AcideProjectMenu.UNSET_COMPILABLE_FILE_NAME);
				AcideMenuConfiguration.getInstance().setIsDisplayed(
						AcideProjectMenu.UNSET_COMPILABLE_FILE_NAME,
						unsetCompilableFile.isVisible());
				AcideMenuItemsConfiguration.getInstance()
						.getSubmenu(AcideProjectMenu.PROJECT_MENU_NAME)
						.getItemsManager()
						.onlyOne(AcideProjectMenu.UNSET_COMPILABLE_FILE_NAME);
			} else {
				unsetCompilableFile = new AcideMenuItemConfiguration(
						AcideProjectMenu.UNSET_COMPILABLE_FILE_NAME);
				unsetCompilableFile
						.setImage("./resources/icons/menu/project/unsetCompilable.png");
				unsetCompilableFile.setCommand("$UNSET_COMPILABLE_FILE");
				unsetCompilableFile.setVisible(AcideMenuConfiguration
						.getInstance().getIsDisplayed(
								AcideProjectMenu.UNSET_COMPILABLE_FILE_NAME));
				project.insertObject(unsetCompilableFile);
			}
			unsetCompilableFile.setErasable(false);
			unsetCompilableFile.setParameter("None");
			// unsetCompilableFile.setCommand("");

			AcideMenuItemConfiguration setMainFile;
			if (project.hasItem(AcideProjectMenu.SET_MAIN_FILE_NAME)) {
				setMainFile = project
						.getItem(AcideProjectMenu.SET_MAIN_FILE_NAME);
				AcideMenuConfiguration.getInstance().setIsDisplayed(
						AcideProjectMenu.SET_MAIN_FILE_NAME,
						setMainFile.isVisible());
				AcideMenuItemsConfiguration.getInstance()
						.getSubmenu(AcideProjectMenu.PROJECT_MENU_NAME)
						.getItemsManager()
						.onlyOne(AcideProjectMenu.SET_MAIN_FILE_NAME);
			} else {
				setMainFile = new AcideMenuItemConfiguration(
						AcideProjectMenu.SET_MAIN_FILE_NAME);
				setMainFile
						.setImage("./resources/icons/menu/project/setMain.png");
				setMainFile.setCommand("$SET_MAIN_FILE");
				setMainFile.setVisible(AcideMenuConfiguration.getInstance()
						.getIsDisplayed(AcideProjectMenu.SET_MAIN_FILE_NAME));
				project.insertObject(setMainFile);
			}
			setMainFile.setErasable(false);
			setMainFile.setParameter("None");
			// setMainFile.setCommand("");

			AcideMenuItemConfiguration unsetMainFile;
			if (project.hasItem(AcideProjectMenu.UNSET_MAIN_FILE_NAME)) {
				unsetMainFile = project
						.getItem(AcideProjectMenu.UNSET_MAIN_FILE_NAME);
				AcideMenuConfiguration.getInstance().setIsDisplayed(
						AcideProjectMenu.UNSET_MAIN_FILE_NAME,
						unsetMainFile.isVisible());
				AcideMenuItemsConfiguration.getInstance()
						.getSubmenu(AcideProjectMenu.PROJECT_MENU_NAME)
						.getItemsManager()
						.onlyOne(AcideProjectMenu.UNSET_MAIN_FILE_NAME);
			} else {
				unsetMainFile = new AcideMenuItemConfiguration(
						AcideProjectMenu.UNSET_MAIN_FILE_NAME);
				unsetMainFile
						.setImage("./resources/icons/menu/project/unsetMain.png");
				unsetMainFile.setCommand("$UNSET_MAIN_FILE");
				unsetMainFile.setVisible(AcideMenuConfiguration.getInstance()
						.getIsDisplayed(AcideProjectMenu.UNSET_MAIN_FILE_NAME));
				project.insertObject(unsetMainFile);
			}
			unsetMainFile.setErasable(false);
			unsetMainFile.setParameter("None");
			// unsetMainFile.setCommand("");

			AcideMenuItemsConfiguration.getInstance().onlyOne(
					AcideProjectMenu.PROJECT_MENU_NAME);

		}

	}

	/**
	 * Checks that the edit menu configuration has the correct format
	 */
	private void buildEditMenuConfiguration() {
		if (!AcideMenuItemsConfiguration.getInstance().hasSubmenu(
				AcideEditMenu.EDIT_MENU_NAME)) {
			AcideMenuSubmenuConfiguration edit = AcideMenuItemsConfiguration
					.getInstance().getEditDefaultSubmenu();

			AcideMenuItemConfiguration undo = edit
					.getItem(AcideEditMenu.UNDO_NAME);
			undo.setVisible(AcideMenuConfiguration.getInstance()
					.getIsDisplayed(AcideEditMenu.UNDO_NAME));

			AcideMenuItemConfiguration redo = edit
					.getItem(AcideEditMenu.REDO_NAME);
			redo.setVisible(AcideMenuConfiguration.getInstance()
					.getIsDisplayed(AcideEditMenu.REDO_NAME));

			AcideMenuItemConfiguration copy = edit
					.getItem(AcideEditMenu.COPY_NAME);
			copy.setVisible(AcideMenuConfiguration.getInstance()
					.getIsDisplayed(AcideEditMenu.COPY_NAME));

			AcideMenuItemConfiguration paste = edit
					.getItem(AcideEditMenu.PASTE_NAME);
			paste.setVisible(AcideMenuConfiguration.getInstance()
					.getIsDisplayed(AcideEditMenu.PASTE_NAME));

			AcideMenuItemConfiguration cut = edit
					.getItem(AcideEditMenu.CUT_NAME);
			cut.setVisible(AcideMenuConfiguration.getInstance().getIsDisplayed(
					AcideEditMenu.CUT_NAME));

			AcideMenuItemConfiguration selectAll = edit
					.getItem(AcideEditMenu.SELECT_ALL_NAME);
			selectAll.setVisible(AcideMenuConfiguration.getInstance()
					.getIsDisplayed(AcideEditMenu.SELECT_ALL_NAME));

			AcideMenuItemConfiguration goToLine = edit
					.getItem(AcideEditMenu.GO_TO_LINE_NAME);
			goToLine.setVisible(AcideMenuConfiguration.getInstance()
					.getIsDisplayed(AcideEditMenu.GO_TO_LINE_NAME));

			AcideMenuItemConfiguration search = edit
					.getItem(AcideEditMenu.SEARCH_NAME);
			search.setVisible(AcideMenuConfiguration.getInstance()
					.getIsDisplayed(AcideEditMenu.SEARCH_NAME));

			AcideMenuItemConfiguration replace = edit
					.getItem(AcideEditMenu.REPLACE_NAME);
			replace.setVisible(AcideMenuConfiguration.getInstance()
					.getIsDisplayed(AcideEditMenu.REPLACE_NAME));

		} else {

			AcideMenuSubmenuConfiguration edit = AcideMenuItemsConfiguration
					.getInstance().getSubmenu(AcideEditMenu.EDIT_MENU_NAME);

			AcideMenuItemConfiguration undo;
			if (edit.hasItem(AcideEditMenu.UNDO_NAME)) {
				undo = edit.getItem(AcideEditMenu.UNDO_NAME);
				AcideMenuConfiguration.getInstance().setIsDisplayed(
						AcideEditMenu.UNDO_NAME, undo.isVisible());
				AcideMenuItemsConfiguration.getInstance()
						.getSubmenu(AcideEditMenu.EDIT_MENU_NAME)
						.getItemsManager().onlyOne(AcideEditMenu.UNDO_NAME);

			} else {
				undo = new AcideMenuItemConfiguration(AcideEditMenu.UNDO_NAME);
				undo.setImage("./resources/icons/menu/edit/undo.png");
				undo.setCommand("$UNDO");
				undo.setVisible(AcideMenuConfiguration.getInstance()
						.getIsDisplayed(AcideEditMenu.UNDO_NAME));
				edit.insertObject(undo);
			}
			undo.setErasable(false);
			undo.setParameter("None");
			// undo.setCommand("");

			AcideMenuItemConfiguration redo;
			if (edit.hasItem(AcideEditMenu.REDO_NAME)) {
				redo = edit.getItem(AcideEditMenu.REDO_NAME);
				AcideMenuConfiguration.getInstance().setIsDisplayed(
						AcideEditMenu.REDO_NAME, redo.isVisible());
				AcideMenuItemsConfiguration.getInstance()
						.getSubmenu(AcideEditMenu.EDIT_MENU_NAME)
						.getItemsManager().onlyOne(AcideEditMenu.REDO_NAME);

			} else {
				redo = new AcideMenuItemConfiguration(AcideEditMenu.REDO_NAME);
				redo.setImage("./resources/icons/menu/edit/redo.png");
				redo.setCommand("$REDO");
				redo.setVisible(AcideMenuConfiguration.getInstance()
						.getIsDisplayed(AcideEditMenu.REDO_NAME));
				edit.insertObject(redo);
			}
			redo.setErasable(false);
			redo.setParameter("None");
			// redo.setCommand("");

			AcideMenuItemConfiguration copy;
			if (edit.hasItem(AcideEditMenu.COPY_NAME)) {
				copy = edit.getItem(AcideEditMenu.COPY_NAME);
				AcideMenuConfiguration.getInstance().setIsDisplayed(
						AcideEditMenu.COPY_NAME, copy.isVisible());
				AcideMenuItemsConfiguration.getInstance()
						.getSubmenu(AcideEditMenu.EDIT_MENU_NAME)
						.getItemsManager().onlyOne(AcideEditMenu.COPY_NAME);

			} else {
				copy = new AcideMenuItemConfiguration(AcideEditMenu.COPY_NAME);
				copy.setImage("./resources/icons/menu/edit/copy.png");
				copy.setCommand("$COPY");
				copy.setVisible(AcideMenuConfiguration.getInstance()
						.getIsDisplayed(AcideEditMenu.COPY_NAME));
				edit.insertObject(copy);
			}
			copy.setErasable(false);
			copy.setParameter("None");
			// copy.setCommand("");

			AcideMenuItemConfiguration paste;
			if (edit.hasItem(AcideEditMenu.PASTE_NAME)) {
				paste = edit.getItem(AcideEditMenu.PASTE_NAME);
				AcideMenuConfiguration.getInstance().setIsDisplayed(
						AcideEditMenu.PASTE_NAME, paste.isVisible());
				AcideMenuItemsConfiguration.getInstance()
						.getSubmenu(AcideEditMenu.EDIT_MENU_NAME)
						.getItemsManager().onlyOne(AcideEditMenu.PASTE_NAME);
			} else {
				paste = new AcideMenuItemConfiguration(AcideEditMenu.PASTE_NAME);
				paste.setImage("./resources/icons/menu/edit/paste.png");
				paste.setCommand("$PASTE");
				paste.setVisible(AcideMenuConfiguration.getInstance()
						.getIsDisplayed(AcideEditMenu.PASTE_NAME));
				edit.insertObject(paste);
			}
			paste.setErasable(false);
			paste.setParameter("None");
			// paste.setCommand("");

			AcideMenuItemConfiguration cut;
			if (edit.hasItem(AcideEditMenu.CUT_NAME)) {
				cut = edit.getItem(AcideEditMenu.CUT_NAME);
				AcideMenuConfiguration.getInstance().setIsDisplayed(
						AcideEditMenu.CUT_NAME, cut.isVisible());
				AcideMenuItemsConfiguration.getInstance()
						.getSubmenu(AcideEditMenu.EDIT_MENU_NAME)
						.getItemsManager().onlyOne(AcideEditMenu.CUT_NAME);
			} else {
				cut = new AcideMenuItemConfiguration(AcideEditMenu.CUT_NAME);
				cut.setImage("./resources/icons/menu/edit/cut.png");
				cut.setCommand("%CUT");
				cut.setVisible(AcideMenuConfiguration.getInstance()
						.getIsDisplayed(AcideEditMenu.CUT_NAME));
				edit.insertObject(cut);
			}
			cut.setErasable(false);
			cut.setParameter("None");
			// cut.setCommand("");

			AcideMenuItemConfiguration selectAll;
			if (edit.hasItem(AcideEditMenu.SELECT_ALL_NAME)) {
				selectAll = edit.getItem(AcideEditMenu.SELECT_ALL_NAME);
				AcideMenuConfiguration.getInstance().setIsDisplayed(
						AcideEditMenu.SELECT_ALL_NAME, selectAll.isVisible());
				AcideMenuItemsConfiguration.getInstance()
						.getSubmenu(AcideEditMenu.EDIT_MENU_NAME)
						.getItemsManager()
						.onlyOne(AcideEditMenu.SELECT_ALL_NAME);
			} else {
				selectAll = new AcideMenuItemConfiguration(
						AcideEditMenu.SELECT_ALL_NAME);
				selectAll.setImage("./resources/icons/menu/edit/selectAll.png");
				selectAll.setCommand("$SELECT_ALL");
				selectAll.setVisible(AcideMenuConfiguration.getInstance()
						.getIsDisplayed(AcideEditMenu.SELECT_ALL_NAME));
				edit.insertObject(selectAll);
			}
			selectAll.setErasable(false);
			selectAll.setParameter("None");
			// selectAll.setCommand("");

			AcideMenuItemConfiguration goToLine;
			if (edit.hasItem(AcideEditMenu.GO_TO_LINE_NAME)) {
				goToLine = edit.getItem(AcideEditMenu.GO_TO_LINE_NAME);
				AcideMenuConfiguration.getInstance().setIsDisplayed(
						AcideEditMenu.GO_TO_LINE_NAME, goToLine.isVisible());
				AcideMenuItemsConfiguration.getInstance()
						.getSubmenu(AcideEditMenu.EDIT_MENU_NAME)
						.getItemsManager()
						.onlyOne(AcideEditMenu.GO_TO_LINE_NAME);
			} else {
				goToLine = new AcideMenuItemConfiguration(
						AcideEditMenu.GO_TO_LINE_NAME);
				goToLine.setImage("./resources/icons/menu/edit/goToLine.png");
				goToLine.setCommand("$GO_TO_LINE");
				goToLine.setVisible(AcideMenuConfiguration.getInstance()
						.getIsDisplayed(AcideEditMenu.GO_TO_LINE_NAME));
				edit.insertObject(goToLine);
			}
			goToLine.setErasable(false);
			goToLine.setParameter("None");
			// goToLine.setCommand("");

			AcideMenuItemConfiguration search;
			if (edit.hasItem(AcideEditMenu.SEARCH_NAME)) {
				search = edit.getItem(AcideEditMenu.SEARCH_NAME);
				AcideMenuConfiguration.getInstance().setIsDisplayed(
						AcideEditMenu.SEARCH_NAME, search.isVisible());
				AcideMenuItemsConfiguration.getInstance()
						.getSubmenu(AcideEditMenu.EDIT_MENU_NAME)
						.getItemsManager().onlyOne(AcideEditMenu.SEARCH_NAME);
			} else {
				search = new AcideMenuItemConfiguration(
						AcideEditMenu.SEARCH_NAME);
				search.setImage("./resources/icons/menu/edit/search.png");
				search.setCommand("$SEARCH");
				search.setVisible(AcideMenuConfiguration.getInstance()
						.getIsDisplayed(AcideEditMenu.SEARCH_NAME));
				edit.insertObject(search);
			}
			search.setErasable(false);
			search.setParameter("None");
			// search.setCommand("");

			AcideMenuItemConfiguration replace;
			if (edit.hasItem(AcideEditMenu.REPLACE_NAME)) {
				replace = edit.getItem(AcideEditMenu.REPLACE_NAME);
				AcideMenuConfiguration.getInstance().setIsDisplayed(
						AcideEditMenu.REPLACE_NAME, replace.isVisible());
				AcideMenuItemsConfiguration.getInstance()
						.getSubmenu(AcideEditMenu.EDIT_MENU_NAME)
						.getItemsManager().onlyOne(AcideEditMenu.REPLACE_NAME);
			} else {
				replace = new AcideMenuItemConfiguration(
						AcideEditMenu.REPLACE_NAME);
				replace.setImage("./resources/icons/menu/edit/replace.png");
				replace.setCommand("$REPLACE");
				replace.setVisible(AcideMenuConfiguration.getInstance()
						.getIsDisplayed(AcideEditMenu.REPLACE_NAME));
				edit.insertObject(replace);
			}
			replace.setErasable(false);
			replace.setParameter("None");
			// replace.setCommand("");

			AcideMenuItemsConfiguration.getInstance().onlyOne(
					AcideEditMenu.EDIT_MENU_NAME);
		}

	}

	/**
	 * Checks that the file menu configuration has the correct format
	 */
	private void buildFileMenuConfiguration() {

		if (!AcideMenuItemsConfiguration.getInstance().hasSubmenu(
				AcideFileMenu.FILE_MENU_NAME)) {
			AcideMenuSubmenuConfiguration file = AcideMenuItemsConfiguration
					.getInstance().getFileDefaultSubmenu();

			AcideMenuItemConfiguration newFile = file
					.getItem(AcideFileMenu.NEW_FILE_NAME);
			newFile.setVisible(AcideMenuConfiguration.getInstance()
					.getIsDisplayed(AcideFileMenu.NEW_FILE_NAME));

			AcideMenuItemConfiguration openFile = file
					.getItem(AcideFileMenu.OPEN_FILE_NAME);
			openFile.setVisible(AcideMenuConfiguration.getInstance()
					.getIsDisplayed(AcideFileMenu.OPEN_FILE_NAME));

			AcideMenuSubmenuConfiguration openRecentFiles = file
					.getSubmenu(AcideFileMenu.OPEN_RECENT_FILES_NAME);
			openRecentFiles.setVisible(AcideMenuConfiguration.getInstance()
					.getIsDisplayed(AcideFileMenu.OPEN_RECENT_FILES_NAME));

			AcideMenuItemConfiguration openAllFiles = file
					.getItem(AcideFileMenu.OPEN_ALL_FILES_NAME);
			openAllFiles.setVisible(AcideMenuConfiguration.getInstance()
					.getIsDisplayed(AcideFileMenu.OPEN_ALL_FILES_NAME));

			AcideMenuItemConfiguration closeFile = file
					.getItem(AcideFileMenu.CLOSE_FILE_NAME);
			closeFile.setVisible(AcideMenuConfiguration.getInstance()
					.getIsDisplayed(AcideFileMenu.CLOSE_FILE_NAME));

			AcideMenuItemConfiguration closeAllFiles = file
					.getItem(AcideFileMenu.CLOSE_ALL_FILES_NAME);
			closeAllFiles.setVisible(AcideMenuConfiguration.getInstance()
					.getIsDisplayed(AcideFileMenu.CLOSE_ALL_FILES_NAME));

			AcideMenuItemConfiguration saveFile = file
					.getItem(AcideFileMenu.SAVE_FILE_NAME);
			saveFile.setVisible(AcideMenuConfiguration.getInstance()
					.getIsDisplayed(AcideFileMenu.SAVE_FILE_NAME));

			AcideMenuItemConfiguration saveFileAs = file
					.getItem(AcideFileMenu.SAVE_FILE_AS_NAME);
			saveFileAs.setVisible(AcideMenuConfiguration.getInstance()
					.getIsDisplayed(AcideFileMenu.SAVE_FILE_AS_NAME));

			AcideMenuItemConfiguration saveAllFiles = file
					.getItem(AcideFileMenu.SAVE_ALL_FILES_NAME);
			saveAllFiles.setVisible(AcideMenuConfiguration.getInstance()
					.getIsDisplayed(AcideFileMenu.SAVE_ALL_FILES_NAME));

			AcideMenuItemConfiguration printFile = file
					.getItem(AcideFileMenu.PRINT_FILE_NAME);
			printFile.setVisible(AcideMenuConfiguration.getInstance()
					.getIsDisplayed(AcideFileMenu.PRINT_FILE_NAME));

			AcideMenuItemConfiguration exit = file
					.getItem(AcideFileMenu.EXIT_NAME);
			exit.setVisible(AcideMenuConfiguration.getInstance()
					.getIsDisplayed(AcideFileMenu.EXIT_NAME));

			// Insert file submenu configuration to the manager
			AcideMenuItemsConfiguration.getInstance().insertObject(file);

		} else {

			AcideMenuSubmenuConfiguration file = AcideMenuItemsConfiguration
					.getInstance().getSubmenu(AcideFileMenu.FILE_MENU_NAME);

			AcideMenuItemConfiguration newFile;
			if (file.hasItem(AcideFileMenu.NEW_FILE_NAME)) {
				newFile = file.getItem(AcideFileMenu.NEW_FILE_NAME);
				AcideMenuConfiguration.getInstance().setIsDisplayed(
						AcideFileMenu.NEW_FILE_NAME, newFile.isVisible());

				AcideMenuItemsConfiguration.getInstance()
						.getSubmenu(AcideFileMenu.FILE_MENU_NAME)
						.getItemsManager().onlyOne(AcideFileMenu.NEW_FILE_NAME);

			} else {
				newFile = new AcideMenuItemConfiguration(
						AcideFileMenu.NEW_FILE_NAME);
				newFile.setImage("./resources/icons/menu/file/newFile.png");
				newFile.setCommand("$NEW_FILE");
				newFile.setVisible(AcideMenuConfiguration.getInstance()
						.getIsDisplayed(AcideFileMenu.NEW_FILE_NAME));
				file.insertObject(newFile);
			}
			newFile.setErasable(false);
			newFile.setParameter("None");
			// newFile.setCommand("");

			AcideMenuItemConfiguration openFile;
			if (file.hasItem(AcideFileMenu.OPEN_FILE_NAME)) {
				openFile = file.getItem(AcideFileMenu.OPEN_FILE_NAME);
				AcideMenuConfiguration.getInstance().setIsDisplayed(
						AcideFileMenu.OPEN_FILE_NAME, openFile.isVisible());
				AcideMenuItemsConfiguration.getInstance()
						.getSubmenu(AcideFileMenu.FILE_MENU_NAME)
						.getItemsManager()
						.onlyOne(AcideFileMenu.OPEN_FILE_NAME);
			} else {
				openFile = new AcideMenuItemConfiguration(
						AcideFileMenu.OPEN_FILE_NAME);
				openFile.setImage("./resources/icons/menu/file/openFile.png");
				openFile.setCommand("$OPEN_FILE");
				openFile.setVisible(AcideMenuConfiguration.getInstance()
						.getIsDisplayed(AcideFileMenu.OPEN_FILE_NAME));
				file.insertObject(openFile);
			}
			openFile.setErasable(false);
			openFile.setParameter("None");
			// openFile.setCommand("");

			AcideMenuSubmenuConfiguration openRecentFiles;
			if (file.hasSubmenu(AcideFileMenu.OPEN_RECENT_FILES_NAME)) {
				openRecentFiles = file
						.getSubmenu(AcideFileMenu.OPEN_RECENT_FILES_NAME);
				AcideMenuConfiguration.getInstance().setIsDisplayed(
						AcideFileMenu.OPEN_RECENT_FILES_NAME,
						openRecentFiles.isVisible());
				AcideMenuItemsConfiguration.getInstance()
						.getSubmenu(AcideFileMenu.FILE_MENU_NAME)
						.getItemsManager()
						.onlyOne(AcideFileMenu.OPEN_RECENT_FILES_NAME);

			} else {
				openRecentFiles = new AcideMenuSubmenuConfiguration(
						AcideFileMenu.OPEN_RECENT_FILES_NAME);
				openRecentFiles.setVisible(AcideMenuConfiguration.getInstance()
						.getIsDisplayed(AcideFileMenu.OPEN_RECENT_FILES_NAME));
				file.insertObject(openRecentFiles);
			}
			openRecentFiles.setErasable(false);

			AcideMenuItemConfiguration openAllFiles;
			if (file.hasItem(AcideFileMenu.OPEN_ALL_FILES_NAME)) {
				openAllFiles = file.getItem(AcideFileMenu.OPEN_ALL_FILES_NAME);
				AcideMenuConfiguration.getInstance().setIsDisplayed(
						AcideFileMenu.OPEN_ALL_FILES_NAME,
						openAllFiles.isVisible());
				AcideMenuItemsConfiguration.getInstance()
						.getSubmenu(AcideFileMenu.FILE_MENU_NAME)
						.getItemsManager()
						.onlyOne(AcideFileMenu.OPEN_ALL_FILES_NAME);

			} else {
				openAllFiles = new AcideMenuItemConfiguration(
						AcideFileMenu.OPEN_ALL_FILES_NAME);
				openAllFiles
						.setImage("./resources/icons/menu/file/openAllFiles.png");
				openAllFiles.setCommand("$OPEN_ALL_FILES");
				openAllFiles.setVisible(AcideMenuConfiguration.getInstance()
						.getIsDisplayed(AcideFileMenu.OPEN_ALL_FILES_NAME));
				file.insertObject(openAllFiles);
			}
			openAllFiles.setErasable(false);
			openAllFiles.setParameter("None");
			// openAllFiles.setCommand("");

			AcideMenuItemConfiguration closeFile;
			if (file.hasItem(AcideFileMenu.CLOSE_FILE_NAME)) {
				closeFile = file.getItem(AcideFileMenu.CLOSE_FILE_NAME);
				AcideMenuConfiguration.getInstance().setIsDisplayed(
						AcideFileMenu.CLOSE_FILE_NAME, closeFile.isVisible());
				AcideMenuItemsConfiguration.getInstance()
						.getSubmenu(AcideFileMenu.FILE_MENU_NAME)
						.getItemsManager()
						.onlyOne(AcideFileMenu.CLOSE_FILE_NAME);

			} else {
				closeFile = new AcideMenuItemConfiguration(
						AcideFileMenu.CLOSE_FILE_NAME);
				closeFile.setImage("./resources/icons/menu/file/closeFile.png");
				closeFile.setCommand("$CLOSE_FILE");
				closeFile.setVisible(AcideMenuConfiguration.getInstance()
						.getIsDisplayed(AcideFileMenu.CLOSE_FILE_NAME));
				file.insertObject(closeFile);
			}
			closeFile.setErasable(false);
			closeFile.setParameter("None");
			// closeFile.setCommand("");

			AcideMenuItemConfiguration closeAllFiles;
			if (file.hasItem(AcideFileMenu.CLOSE_ALL_FILES_NAME)) {
				closeAllFiles = file
						.getItem(AcideFileMenu.CLOSE_ALL_FILES_NAME);
				AcideMenuConfiguration.getInstance().setIsDisplayed(
						AcideFileMenu.CLOSE_ALL_FILES_NAME,
						closeAllFiles.isVisible());
				AcideMenuItemsConfiguration.getInstance()
						.getSubmenu(AcideFileMenu.FILE_MENU_NAME)
						.getItemsManager()
						.onlyOne(AcideFileMenu.CLOSE_ALL_FILES_NAME);

			} else {
				closeAllFiles = new AcideMenuItemConfiguration(
						AcideFileMenu.CLOSE_ALL_FILES_NAME);
				closeAllFiles
						.setImage("./resources/icons/menu/file/closeAllFiles.png");
				closeAllFiles.setCommand("$CLOSE_ALL_FILES");
				closeAllFiles.setVisible(AcideMenuConfiguration.getInstance()
						.getIsDisplayed(AcideFileMenu.CLOSE_ALL_FILES_NAME));
				file.insertObject(closeAllFiles);
			}
			closeAllFiles.setErasable(false);
			closeAllFiles.setParameter("None");
			// closeAllFiles.setCommand("");

			AcideMenuItemConfiguration saveFile;
			if (file.hasItem(AcideFileMenu.SAVE_FILE_NAME)) {
				saveFile = file.getItem(AcideFileMenu.SAVE_FILE_NAME);
				AcideMenuConfiguration.getInstance().setIsDisplayed(
						AcideFileMenu.SAVE_FILE_NAME, saveFile.isVisible());
				AcideMenuItemsConfiguration.getInstance()
						.getSubmenu(AcideFileMenu.FILE_MENU_NAME)
						.getItemsManager()
						.onlyOne(AcideFileMenu.SAVE_FILE_NAME);
			} else {
				saveFile = new AcideMenuItemConfiguration(
						AcideFileMenu.SAVE_FILE_NAME);
				saveFile.setImage("./resources/icons/menu/file/saveFile.png");
				saveFile.setCommand("$SAVE_FILE");
				saveFile.setVisible(AcideMenuConfiguration.getInstance()
						.getIsDisplayed(AcideFileMenu.SAVE_FILE_NAME));
				file.insertObject(saveFile);
			}
			saveFile.setErasable(false);
			saveFile.setParameter("None");
			// saveFile.setCommand("");

			AcideMenuItemConfiguration saveFileAs;
			if (file.hasItem(AcideFileMenu.SAVE_FILE_AS_NAME)) {
				saveFileAs = file.getItem(AcideFileMenu.SAVE_FILE_AS_NAME);
				AcideMenuConfiguration.getInstance()
						.setIsDisplayed(AcideFileMenu.SAVE_FILE_AS_NAME,
								saveFileAs.isVisible());
				AcideMenuItemsConfiguration.getInstance()
						.getSubmenu(AcideFileMenu.FILE_MENU_NAME)
						.getItemsManager()
						.onlyOne(AcideFileMenu.SAVE_FILE_AS_NAME);
			} else {
				saveFileAs = new AcideMenuItemConfiguration(
						AcideFileMenu.SAVE_FILE_AS_NAME);
				saveFileAs
						.setImage("./resources/icons/menu/file/saveFileAs.png");
				saveFileAs.setCommand("$SAVE_FILE_AS");
				saveFileAs.setVisible(AcideMenuConfiguration.getInstance()
						.getIsDisplayed(AcideFileMenu.SAVE_FILE_AS_NAME));
				file.insertObject(saveFileAs);
			}
			saveFileAs.setErasable(false);
			saveFileAs.setParameter("None");
			// saveFileAs.setCommand("");

			AcideMenuItemConfiguration saveAllFiles;
			if (file.hasItem(AcideFileMenu.SAVE_ALL_FILES_NAME)) {
				saveAllFiles = file.getItem(AcideFileMenu.SAVE_ALL_FILES_NAME);
				AcideMenuConfiguration.getInstance().setIsDisplayed(
						AcideFileMenu.SAVE_ALL_FILES_NAME,
						saveAllFiles.isVisible());
				AcideMenuItemsConfiguration.getInstance()
						.getSubmenu(AcideFileMenu.FILE_MENU_NAME)
						.getItemsManager()
						.onlyOne(AcideFileMenu.SAVE_ALL_FILES_NAME);
			} else {
				saveAllFiles = new AcideMenuItemConfiguration(
						AcideFileMenu.SAVE_ALL_FILES_NAME);
				saveAllFiles
						.setImage("./resources/icons/menu/file/saveAllFiles.png");
				saveAllFiles.setCommand("$SAVE_ALL_FILES");
				saveAllFiles.setVisible(AcideMenuConfiguration.getInstance()
						.getIsDisplayed(AcideFileMenu.SAVE_ALL_FILES_NAME));
				file.insertObject(saveAllFiles);
			}
			saveAllFiles.setErasable(false);
			saveAllFiles.setParameter("None");
			// saveAllFiles.setCommand("");

			AcideMenuItemConfiguration printFile;
			if (file.hasItem(AcideFileMenu.PRINT_FILE_NAME)) {
				printFile = file.getItem(AcideFileMenu.PRINT_FILE_NAME);
				AcideMenuConfiguration.getInstance().setIsDisplayed(
						AcideFileMenu.PRINT_FILE_NAME, printFile.isVisible());
				AcideMenuItemsConfiguration.getInstance()
						.getSubmenu(AcideFileMenu.FILE_MENU_NAME)
						.getItemsManager()
						.onlyOne(AcideFileMenu.PRINT_FILE_NAME);

			} else {
				printFile = new AcideMenuItemConfiguration(
						AcideFileMenu.PRINT_FILE_NAME);
				printFile.setImage("./resources/icons/menu/file/printFile.png");
				printFile.setCommand("$PRINT_FILE");
				printFile.setVisible(AcideMenuConfiguration.getInstance()
						.getIsDisplayed(AcideFileMenu.PRINT_FILE_NAME));
				file.insertObject(printFile);
			}
			printFile.setErasable(false);
			printFile.setParameter("None");
			// printFile.setCommand("");

			AcideMenuItemConfiguration exit;
			if (file.hasItem(AcideFileMenu.EXIT_NAME)) {
				exit = file.getItem(AcideFileMenu.EXIT_NAME);
				AcideMenuConfiguration.getInstance().setIsDisplayed(
						AcideFileMenu.EXIT_NAME, exit.isVisible());
				AcideMenuItemsConfiguration.getInstance()
						.getSubmenu(AcideFileMenu.FILE_MENU_NAME)
						.getItemsManager().onlyOne(AcideFileMenu.EXIT_NAME);

			} else {
				exit = new AcideMenuItemConfiguration(AcideFileMenu.EXIT_NAME);
				exit.setImage("./resources/icons/menu/file/exit.png");
				exit.setCommand("$EXIT_FILE");
				exit.setVisible(AcideMenuConfiguration.getInstance()
						.getIsDisplayed(AcideFileMenu.EXIT_NAME));
				file.insertObject(exit);
			}
			exit.setErasable(false);
			exit.setParameter("None");
			// exit.setCommand("");

			AcideMenuItemsConfiguration.getInstance().onlyOne(
					AcideFileMenu.FILE_MENU_NAME);
		}
	}

	/**
	 * Adds the components to the ACIDE - A Configurable IDE menu.
	 */
	private void addComponents() {

		Iterator<Object> it = AcideMenuItemsConfiguration.getInstance()
				.getMenuItemsManager().managerIterator();

		while (it.hasNext()) {
			AcideMenuObjectConfiguration ob = (AcideMenuObjectConfiguration) it
					.next();
			String name = ob.getName();
			if (name.equals(AcideFileMenu.FILE_MENU_NAME)) {
				// Adds the file menu to the menu
				add(_fileMenu);
				_fileInserted = true;
			} else if (name.equals(AcideEditMenu.EDIT_MENU_NAME)) {
				// Adds the edit menu to the menu
				add(_editMenu);
				_editInserted = true;
			} else if (name.equals(AcideProjectMenu.PROJECT_MENU_NAME)) {
				// Adds the project menu to the menu
				add(_projectMenu);
				_projectInserted = true;
			} else if (name.equals(AcideViewMenu.VIEW_MENU_NAME)) {
				// Adds the view menu to the menu
				add(_viewMenu);
				_viewInserted = true;
			} else if (name
					.equals(AcideConfigurationMenu.CONFIGURATION_MENU_NAME)) {
				// Adds the configuration menu to the menu
				add(_configurationMenu);
				_configurationInserted = true;
			} else if (name.equals(AcideHelpMenu.HELP_MENU_NAME)) {
				// Adds the help menu to the menu
				add(_helpMenu);
				_helpInserted = true;
			} else {
				if (ob.isSubmenu()) {
					add(_insertedMenus.get(ob.getName()));
				}
				// else{
				// add(_insertedItems.get(ob.getName()));
				// }
			}
		}

		if (!_fileInserted)
			add(_fileMenu);
		if (!_editInserted)
			add(_editMenu);
		if (!_projectInserted)
			add(_projectMenu);
		if (!_viewInserted)
			add(_viewMenu);
		if (!_configurationInserted)
			add(_configurationMenu);
		if (!_helpInserted)
			add(_helpMenu);
	}

	/**
	 * Builds the ACIDE - A Configurable IDE menu components.
	 */
	private void buildComponents() {

		Iterator<Object> it = AcideMenuItemsConfiguration.getInstance()
				.getMenuItemsManager().managerIterator();

		while (it.hasNext()) {
			AcideMenuObjectConfiguration ob = (AcideMenuObjectConfiguration) it
					.next();
			String name = ob.getName();
			if (isOriginal(name)) {
				_insertedObjects.add(ob);
				AcideMenuItemsConfiguration.getInstance().onlyOne(name);
				if (ob.isSubmenu()) {
					AcideMenuSubmenuConfiguration obSubmenu = (AcideMenuSubmenuConfiguration) ob;
					_insertedMenus.put(ob.getName(), new AcideInsertedMenu(
							obSubmenu));
				}
				// else {
				// AcideMenuItemConfiguration obItem =
				// (AcideMenuItemConfiguration) ob;
				// _insertedItems.put(ob.getName(), new
				// AcideInsertedItem(obItem));
				// }
			}
		}

		// Creates the file menu
		_fileMenu = new AcideFileMenu();

		// Sets the file menu name
		_fileMenu.setName("fileMenu");

		// Creates the edit menu
		_editMenu = new AcideEditMenu();

		// Sets the edit menu name
		_editMenu.setName("editMenu");

		// Creates the project menu
		_projectMenu = new AcideProjectMenu();

		// Sets the project menu name
		_projectMenu.setName("projectMenu");

		// Creates the view menu
		_viewMenu = new AcideViewMenu();

		// Sets the view menu name
		_viewMenu.setName("viewMenu");

		// Creates the configuration menu
		_configurationMenu = new AcideConfigurationMenu();

		// Sets the configuration menu name
		_configurationMenu.setName("configurationMenu");

		// Creates the help menu
		_helpMenu = new AcideHelpMenu();

		// Sets the help menu name
		_helpMenu.setName("helpMenu");
	}

	/**
	 * Sets the text of the ACIDE - A Configurable IDE menu bar components with
	 * the labels in the selected language to display.
	 */
	public void setTextOfMenuComponents() {

		// Sets the file menu text
		_fileMenu.setText(AcideLanguageManager.getInstance().getLabels()
				.getString("s1"));

		// Sets the file menu items text
		_fileMenu.setTextOfMenuComponents();

		// Sets the edit menu text
		_editMenu.setText(AcideLanguageManager.getInstance().getLabels()
				.getString("s2"));

		// Sets the edit menu items text
		_editMenu.setTextOfMenuComponents();

		// Sets the project menu text
		_projectMenu.setText(AcideLanguageManager.getInstance().getLabels()
				.getString("s3"));

		// Sets the project menu items text
		_projectMenu.setTextOfMenuComponents();

		// Sets the view menu text
		_viewMenu.setText(AcideLanguageManager.getInstance().getLabels()
				.getString("s4"));

		// Sets the view menu items text
		_viewMenu.setTextOfMenuComponents();

		// Sets the configuration menu text
		_configurationMenu.setText(AcideLanguageManager.getInstance()
				.getLabels().getString("s5"));

		// Sets the configuration menu items text
		_configurationMenu.setTextOfMenuComponents();

		// Sets the help menu text
		_helpMenu.setText(AcideLanguageManager.getInstance().getLabels()
				.getString("s7"));

		// Sets the help menu items text
		_helpMenu.setTextOfMenuComponents();

		Iterator<AcideMenuObjectConfiguration> it = _insertedObjects.iterator();
		while (it.hasNext()) {
			AcideMenuObjectConfiguration ob = it.next();
			if (ob.isSubmenu()) {
				_insertedMenus.get(ob.getName()).setText(ob.getName());
				_insertedMenus.get(ob.getName()).setTextOfMenuComponents();
			}
			// else{
			// _insertedItems.get(ob.getName()).setText(ob.getName());
			// }
		}
	}

	/**
	 * <p>
	 * Configures the ACIDE - A Configurable IDE menu bar enabling or disabling
	 * its menu items.
	 * </p>
	 * <p>
	 * It is called by the {@link AcideFileEditorManagerChangeListener}.
	 * </p>
	 */
	public void configure() {

		// Configures the file menu
		AcideMainWindow.getInstance().getMenu().getFileMenu().configure();

		// Configures the edit menu
		AcideMainWindow.getInstance().getMenu().getEditMenu().configure();

		// Configures the project menu
		AcideMainWindow.getInstance().getMenu().getProjectMenu().configure();

		// Configures the file editor menu
		AcideMainWindow.getInstance().getMenu().getConfigurationMenu()
				.getFileEditorMenu().configure();

		// Configures the lexicon menu
		AcideMainWindow.getInstance().getMenu().getConfigurationMenu()
				.getLexiconMenu().configure();

		// Configures the grammar menu
		AcideMainWindow.getInstance().getMenu().getConfigurationMenu()
				.getGrammarMenu().configure();
	}

	/**
	 * Updates the ACIDE - A Configurable IDE menu bar components visibility
	 * with the menu configuration.
	 */
	public void updateComponentsVisibility() {

		// Builds the file menu
		_fileMenu.updateComponentsVisibiliy();

		// Sets the file menu as visible or not visible
		_fileMenu.setVisible(AcideMenuItemsConfiguration.getInstance()
				.getSubmenu(AcideFileMenu.FILE_MENU_NAME).isVisible());

		// Builds the edit menu
		_editMenu.updateComponentsVisibility();

		// Sets the edit menu as visible or not visible
		_editMenu.setVisible(AcideMenuItemsConfiguration.getInstance()
				.getSubmenu(AcideEditMenu.EDIT_MENU_NAME).isVisible());

		// Builds the project menu
		_projectMenu.updateComponentsVisibility();

		// Sets the project menu as visible or not visible
		_projectMenu.setVisible(AcideMenuItemsConfiguration.getInstance()
				.getSubmenu(AcideProjectMenu.PROJECT_MENU_NAME).isVisible());

		// Builds the view menu
		_viewMenu.updateComponentsVisibility();

		// Sets the view menu as visible or not visible
		_viewMenu.setVisible(AcideMenuItemsConfiguration.getInstance()
				.getSubmenu(AcideViewMenu.VIEW_MENU_NAME).isVisible());

		// Builds the configuration menu
		_configurationMenu.updateComponentsVisibility();

		// Sets the edit menu as always visible
		_configurationMenu.setVisible(true);

		// Builds the help menu
		_helpMenu.updateComponentsVisibility();

		// Sets the help menu as visible or not visible
		_helpMenu.setVisible(AcideMenuItemsConfiguration.getInstance()
				.getSubmenu(AcideHelpMenu.HELP_MENU_NAME).isVisible());

		Iterator<AcideMenuObjectConfiguration> it = _insertedObjects.iterator();
		while (it.hasNext()) {
			AcideMenuObjectConfiguration ob = it.next();
			if (ob.isSubmenu()) {
				_insertedMenus.get(ob.getName()).updateComponentsVisibility();
				_insertedMenus.get(ob.getName()).setVisible(ob.isVisible());
			}
			// else{
			// _insertedItems.get(ob.getName()).setVisible(ob.isVisible());
			// }
		}

		// Shows the menu bar
		setVisible(true);
	}

	/**
	 * Loads the ACIDE - A Configurable IDE menu configuration.
	 */
	private void loadMenuConfiguration() {

		String currentMenuConfiguration = null;

		try {

			// Gets the ACIDE - A Configurable IDE current menu configuration
			currentMenuConfiguration = AcideResourceManager.getInstance()
					.getProperty("currentMenuConfiguration");

			// Sets the new menu item list
			AcideMenuConfiguration.getInstance()
					.setMenuElementList(
							AcideMenuConfiguration.getInstance()
									.loadMenuConfigurationFile(
											currentMenuConfiguration));

			// Updates the ACIDE - A Configurable IDE current menu configuration
			AcideResourceManager.getInstance().setProperty(
					"currentMenuConfiguration", currentMenuConfiguration);

			// Updates the log
			AcideLog.getLog().info(
					AcideLanguageManager.getInstance().getLabels()
							.getString("s70")
							+ " " + currentMenuConfiguration);
		} catch (Exception exception) {

			// Updates the log
			AcideLog.getLog().info(
					AcideLanguageManager.getInstance().getLabels()
							.getString("s71")
							+ exception.getMessage());

			// Gets the name
			String name;
			int index = currentMenuConfiguration.lastIndexOf("\\");
			if (index == -1)
				index = currentMenuConfiguration.lastIndexOf("/");
			name = ".\\configuration\\menu\\"
					+ currentMenuConfiguration.substring(index + 1,
							currentMenuConfiguration.length());

			try {

				// Sets the new menu item list
				AcideMenuConfiguration.getInstance().setMenuElementList(
						AcideMenuConfiguration.getInstance()
								.loadMenuConfigurationFile(name));

				// Updates the ACIDE - A Configurable IDE current menu
				// configuration
				AcideResourceManager.getInstance().setProperty(
						"currentMenuConfiguration", name);

				// Updates the ACIDE - A Configurable IDE menu configuration
				AcideProjectConfiguration.getInstance().setMenuConfiguration(
						name);

				// Updates the log
				AcideLog.getLog().info(
						AcideLanguageManager.getInstance().getLabels()
								.getString("s70")
								+ " " + name);

				// Displays an error message
				JOptionPane.showMessageDialog(null, AcideLanguageManager
						.getInstance().getLabels().getString("s956")
						+ currentMenuConfiguration
						+ AcideLanguageManager.getInstance().getLabels()
								.getString("s957") + name);
			} catch (Exception exception1) {
				try {

					// Updates the log
					AcideLog.getLog().error(exception1.getMessage());
					exception1.printStackTrace();

					// Loads the new menu item list
					AcideMenuConfiguration
							.getInstance()
							.setMenuElementList(
									AcideMenuConfiguration
											.getInstance()
											.loadMenuConfigurationFile(
													"./configuration/menu/defaultAllOn.menuConfig"));

					// Updates the ACIDE - A Configurable IDE current menu
					// configuration
					AcideResourceManager.getInstance().setProperty(
							"currentMenuConfiguration",
							"./configuration/menu/defaultAllOn.menuConfig");

					// Displays an error message
					JOptionPane.showMessageDialog(null, AcideLanguageManager
							.getInstance().getLabels().getString("s956")
							+ currentMenuConfiguration
							+ AcideLanguageManager.getInstance().getLabels()
									.getString("s959"));
				} catch (HeadlessException exception2) {

					// Updates the log
					AcideLog.getLog().error(exception2.getMessage());
					exception2.printStackTrace();
				} catch (Exception exception2) {

					// Updates the log
					AcideLog.getLog().error(exception2.getMessage());
					exception2.printStackTrace();
				}
			}
		}

		String currentMenuNewConfiguration = null;

		try {
			// Gets the ACIDE - A Configurable IDE current menu new
			// configuration
			currentMenuNewConfiguration = AcideResourceManager.getInstance()
					.getProperty("currentMenuNewConfiguration");

			// Sets the new menu item manager
			AcideMenuItemsConfiguration.getInstance().load(
					currentMenuNewConfiguration);

			// Updates the ACIDE - A Configurable IDE current menu new
			// configuration
			AcideResourceManager.getInstance().setProperty(
					"currentMenuNewConfiguration", currentMenuNewConfiguration);

			// Updates the log
			AcideLog.getLog().info(
					AcideLanguageManager.getInstance().getLabels()
							.getString("s70")
							+ " " + currentMenuNewConfiguration);

		} catch (Exception exception) {

			// Updates the log
			AcideLog.getLog().info(
					AcideLanguageManager.getInstance().getLabels()
							.getString("s71")
							+ exception.getMessage());

			// Gets the name
			String name;
			int index = currentMenuNewConfiguration.lastIndexOf("\\");
			if (index == -1)
				index = currentMenuNewConfiguration.lastIndexOf("/");
			name = ".\\configuration\\menu\\"
					+ currentMenuNewConfiguration.substring(index + 1,
							currentMenuNewConfiguration.length());

			try {

				// Sets the new menu item manager
				AcideMenuItemsConfiguration.getInstance().load(name);

				// Updates the ACIDE - A Configurable IDE current menu
				// new configuration
				AcideResourceManager.getInstance().setProperty(
						"currentMenuNewConfiguration", name);

				// Updates the ACIDE - A Configurable IDE menu new configuration
				AcideProjectConfiguration.getInstance()
						.setMenuNewConfiguration(name);

				// Updates the log
				AcideLog.getLog().info(
						AcideLanguageManager.getInstance().getLabels()
								.getString("s70")
								+ " " + name);

				// Displays an error message
				JOptionPane.showMessageDialog(null, AcideLanguageManager
						.getInstance().getLabels().getString("s956")
						+ currentMenuNewConfiguration
						+ AcideLanguageManager.getInstance().getLabels()
								.getString("s957") + name);
			} catch (Exception exception1) {
				try {

					// Updates the log
					AcideLog.getLog().error(exception1.getMessage());
					exception1.printStackTrace();

					// Loads the new menu item manager
					AcideMenuItemsConfiguration.getInstance().load(
							"./configuration/menu/defaultAllOn.xml");

					// Updates the ACIDE - A Configurable IDE current menu
					// new configuration
					AcideResourceManager.getInstance().setProperty(
							"currentMenuNewConfiguration",
							"./configuration/menu/defaultAllOn.xml");

					// Displays an error message
					JOptionPane.showMessageDialog(null, AcideLanguageManager
							.getInstance().getLabels().getString("s956")
							+ currentMenuNewConfiguration
							+ AcideLanguageManager.getInstance().getLabels()
									.getString("s959"));
				} catch (HeadlessException exception2) {

					// Updates the log
					AcideLog.getLog().error(exception2.getMessage());
					exception2.printStackTrace();
				} catch (Exception exception2) {

					// Updates the log
					AcideLog.getLog().error(exception2.getMessage());
					exception2.printStackTrace();
				}
			}
		}

		AcideMenuIconsConfiguration.getInstance().load(
				AcideMenuIconsConfiguration.DEFAULT_PATH
						+ AcideMenuIconsConfiguration.DEFAULT_NAME);

	}

	/**
	 * Gets if the menu name given as parameter is original
	 * 
	 * @param name
	 *            the name we want to check
	 * @return if the name given as parameter is original
	 */
	public boolean isOriginal(String name) {
		if ((name != AcideFileMenu.FILE_MENU_NAME)
				&& (name != AcideEditMenu.EDIT_MENU_NAME)
				&& (name != AcideProjectMenu.PROJECT_MENU_NAME)
				&& (name != AcideViewMenu.VIEW_MENU_NAME)
				&& (name != AcideConfigurationMenu.CONFIGURATION_MENU_NAME)
				&& (name != AcideHelpMenu.HELP_MENU_NAME))
			return true;
		else
			return false;
	}

	/**
	 * Sets the ACIDE - A Configurable IDE menu bar listeners.
	 */
	public void setListeners() {

		// Sets the file menu mouse listener
		_fileMenu.addMouseListener(new AcideMenuBarMouseClickListener());

		// Sets the file menu items listeners
		_fileMenu.setListeners();

		// Sets the edit menu mouse listener
		_editMenu.addMouseListener(new AcideMenuBarMouseClickListener());

		// Sets the edit menu items listeners
		_editMenu.setListeners();

		// Sets the project menu mouse listener
		_projectMenu.addMouseListener(new AcideMenuBarMouseClickListener());

		// Sets the project menu items listeners
		_projectMenu.setListeners();

		// Sets the view menu mouse listener
		_viewMenu.addMouseListener(new AcideMenuBarMouseClickListener());

		// Sets the view menu items listeners
		_viewMenu.setListeners();

		// Sets the configuration menu mouse listener
		_configurationMenu
				.addMouseListener(new AcideMenuBarMouseClickListener());

		// Sets the configuration menu items listeners
		_configurationMenu.setListeners();

		// Sets the help menu menu mouse listener
		_helpMenu.addMouseListener(new AcideMenuBarMouseClickListener());

		// Sets the help menu items listeners
		_helpMenu.setListeners();

		Iterator<AcideMenuObjectConfiguration> it = _insertedObjects.iterator();
		while (it.hasNext()) {
			AcideMenuObjectConfiguration ob = it.next();
			if (ob.isSubmenu()) {
				_insertedMenus.get(ob.getName()).addMouseListener(
						new AcideMenuBarMouseClickListener());
				_insertedMenus.get(ob.getName()).setListeners();
			}
			// else{
			// AcideInsertedItem aux = _insertedItems.get(ob.getName());
			// aux.addActionListener((new AcideInsertedItemListener(aux)));
			// }
		}

		// Updates the log
		AcideLog.getLog()
				.info(AcideLanguageManager.getInstance().getLabels()
						.getString("s72"));
	}

	/**
	 * Returns the ACIDE - A Configurable IDE menu bar file menu.
	 * 
	 * @return the ACIDE - A Configurable IDE menu bar file menu.
	 */
	public AcideFileMenu getFileMenu() {
		return _fileMenu;
	}

	/**
	 * Returns the ACIDE - A Configurable IDE menu bar edit menu.
	 * 
	 * @return the ACIDE - A Configurable IDE menu bar edit menu.
	 */
	public AcideEditMenu getEditMenu() {
		return _editMenu;
	}

	/**
	 * Returns the ACIDE - A Configurable IDE menu project menu.
	 * 
	 * @return the ACIDE - A Configurable IDE menu project menu.
	 */
	public AcideProjectMenu getProjectMenu() {
		return _projectMenu;
	}

	/**
	 * Returns the ACIDE - A Configurable IDE menu bar configuration menu.
	 * 
	 * @return the ACIDE - A Configurable IDE menu bar configuration menu.
	 */
	public AcideConfigurationMenu getConfigurationMenu() {
		return _configurationMenu;
	}

	/**
	 * Returns the ACIDE - A Configurable IDE menu view menu.
	 * 
	 * @return the ACIDE - A Configurable IDE menu view menu.
	 */
	public AcideViewMenu getViewMenu() {
		return _viewMenu;
	}

	/**
	 * Returns the ACIDE - A Configurable IDE menu bar help menu.
	 * 
	 * @return the ACIDE - A Configurable IDE menu bar help menu.
	 */
	public AcideHelpMenu getHelpMenu() {
		return _helpMenu;
	}

	/**
	 * Returns the ACIDE - A Configurable IDE menu bar is shell focused flag.
	 * 
	 * @return the ACIDE - A Configurable IDE menu bar is shell focused flag.
	 */
	public boolean getIsConsoleFocused() {
		return _isConsoleFocused;
	}

	/**
	 * Sets a new value to the ACIDE - A Configurable IDE menu bar is console
	 * focused flag.
	 * 
	 * @param consoleIsFocused
	 *            new value to set.
	 */
	public void setIsConsoleFocused(boolean consoleIsFocused) {
		_isConsoleFocused = consoleIsFocused;
	}

	public AcideMenuBar getAcideMenuBar(){
		return this;
	}
}
