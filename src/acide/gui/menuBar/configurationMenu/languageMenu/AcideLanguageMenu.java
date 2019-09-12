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
package acide.gui.menuBar.configurationMenu.languageMenu;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.HashMap;

import java.util.Locale;
import java.util.ResourceBundle;

import javax.swing.JMenu;
import javax.swing.JMenuItem;

import acide.configuration.menu.AcideInsertedItem;
import acide.configuration.menu.AcideInsertedMenu;
import acide.configuration.menu.AcideMenuObjectConfiguration;
import acide.configuration.menu.AcideMenuSubmenuConfiguration;
import acide.configuration.project.AcideProjectConfiguration;
import acide.gui.mainWindow.AcideMainWindow;
import acide.gui.menuBar.configurationMenu.languageMenu.listeners.LanguageMenuItemListener;
import acide.gui.menuBar.editMenu.gui.AcideReplaceWindow;
import acide.gui.menuBar.editMenu.gui.AcideSearchWindow;
import acide.language.AcideLanguageManager;
import acide.log.AcideLog;
import acide.resources.AcideResourceManager;
import acide.utils.IconsUtils;

/**
 * ACIDE - A Configurable IDE language menu.
 * 
 * @version 0.11
 * @see JMenu
 */
public class AcideLanguageMenu extends JMenu {

	/**
	 * ACIDE - A Configurable IDE language menu class serial version UID.
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * ACIDE - A Configurable IDE language menu name.
	 */
	public final static String LANGUAGE_MENU_NAME = "Language";
	/**
	 * ACIDE - A Configurable IDE language menu configuration menu.
	 */
	private AcideMenuSubmenuConfiguration _languageSubmenuConfiguration;
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
	 * ACIDE - A Configurable IDE array list of language items.
	 */
	private HashMap<String,JMenuItem> _languageItems;


	/**
	 * Creates a new ACIDE - A Configurable IDE language menu.
	 */
	public AcideLanguageMenu() {
		
		_insertedItems = new HashMap<String, AcideInsertedItem>();
		
		_insertedMenus = new HashMap<String, AcideInsertedMenu>();
		
		_insertedObjects = new ArrayList<AcideMenuObjectConfiguration>();
		
		_languageItems = new HashMap<String,JMenuItem>();

		

        build();
		
	}

	

	

	/**
	 * Sets the text of the ACIDE - A Configurable IDE language menu components
	 * with the labels in the selected language to display.
	 */
	public void setTextOfMenuComponents() {

		
	}

	/**
	 * Updates the ACIDE - A Configurable IDE language menu components
	 * visibility with the menu configuration.
	 */
	public void updateComponentsVisibility() {

		
	
	}
	
	/**
	 * Gets if the menu name given as parameter is original
	 * @param name
	 * 		the name we want to check
	 * @return
	 * 		if the name given as parameter is original
	 */
	public boolean isOriginal(String name){
			return true;
	}

	/**
	 * Sets the ACIDE - A Configurable IDE language menu menu item listeners.
	 */
	public void setListeners() {
		for(JMenuItem languageItem : _languageItems.values())
			languageItem.addActionListener(new LanguageMenuItemListener(languageItem.getText()));
		
	}


	/**
	 * Changes the language to display in the application and reset all the
	 * components with the new language.
	 * 
	 * @param selectedLanguage
	 *            new language to set.
	 */
	public void changeLanguage(String selectedLanguage) {

		// Applies the selected language in the ACIDE - A Configurable IDE main
		// window components
		applyLanguage(selectedLanguage);

		// Updates the menu
		AcideMainWindow.getInstance().getMenu().configure();

		// If it is not the default project
		if (!AcideProjectConfiguration.getInstance().isDefaultProject()) {

			// The project configuration has been modified
			AcideProjectConfiguration.getInstance().setIsModified(true);
		}
	}

	/**
	 * Applies the language to display in the application and reset all the
	 * components with the new language.
	 * 
	 * @param selectedLanguage
	 *            new language to set.
	 */
	public void applyLanguage(String selectedLanguage) {

		// Updates the ACIDE - A Configurable IDE language
		AcideResourceManager.getInstance().setProperty("language",
				selectedLanguage);

		// Updates the log
		AcideLog.getLog().info(
				AcideLanguageManager.getInstance().getLabels()
						.getString("s100"));

		try {

			// Sets the ACIDE - A Configurable IDE language
			AcideLanguageManager.getInstance().setLanguage(
					AcideResourceManager.getInstance().getProperty("language"));
		} catch (Exception exception) {

			// Updates the log
			AcideLog.getLog().error(exception.getMessage());
			exception.printStackTrace();
		}

		// Resets the text of the menu bar
		AcideMainWindow.getInstance().getMenu().setTextOfMenuComponents();

		// Updates the lexicon and grammar menu
		updateLexiconGrammarMenu();

		// Resets the tool bar panel
		AcideMainWindow.getInstance().buildToolBarPanel();

		// Updates the state of the file menu buttons
		AcideMainWindow.getInstance().getToolBarPanel().getMenuBarToolBar()
				.updateStateOfFileButtons();
		
		// Updates the popup menus
		updatesPopupMenus();

		// Updates the search and replace window
		updateSearchReplaceWindow();

		// Updates the status bar
		updateStatusBar();
		
		// Updates the database tree
		updateDataBaseTree();
		
		// Updates the explorer panel menu bar
		AcideMainWindow.getInstance().getExplorerPanel().setTextMenuBar();
		
		// Updates the database panel menu bar
		AcideMainWindow.getInstance().getDataBasePanel().setTextMenuBar();
		
		// Updates the console panel menu bar
		AcideMainWindow.getInstance().getConsolePanel().setTextMenuBar();
		
		// Updates the graph panel menu bar
		AcideMainWindow.getInstance().getGraphPanel().setTextMenuBar();
		
		//Updates the debug panel menu bar
		AcideMainWindow.getInstance().getDebugPanel().setTextMenuBar();

		// Validates the changes in the main window
		AcideMainWindow.getInstance().validate();

		// Repaints the main window
		AcideMainWindow.getInstance().repaint();
	}

	

	/**
	 * Updates the ACIDE - A Configurable IDE lexicon and grammar menus.
	 */
	public void updateLexiconGrammarMenu() {

		// If there are opened file editors
		if (AcideMainWindow.getInstance().getFileEditorManager()
				.getNumberOfFileEditorPanels() > 0) {

			// Enables the lexicon menu in the configuration menu
			AcideMainWindow.getInstance().getMenu().getConfigurationMenu()
					.getLexiconMenu().enableMenu();

			// Enables the grammar menu in the configuration menu
			AcideMainWindow.getInstance().getMenu().getConfigurationMenu()
					.getGrammarMenu().enableMenu();

		} else {

			// Disables the lexicon menu in the configuration menu
			AcideMainWindow.getInstance().getMenu().getConfigurationMenu()
					.getLexiconMenu().disableMenu();

			// Disables the grammar menu in the configuration menu
			AcideMainWindow.getInstance().getMenu().getConfigurationMenu()
					.getGrammarMenu().disableMenu();
		}
	}

	/**
	 * Updates the ACIDE - A Configurable IDE search and replace windows.
	 */
	public void updateSearchReplaceWindow() {

		// Resets the search window
		AcideSearchWindow.getInstance().initialize();

		// Validates the changes in the search window
		AcideSearchWindow.getInstance().validate();

		// Repaints the search window
		AcideSearchWindow.getInstance().repaint();

		// Resets the replace window
		AcideReplaceWindow.getInstance().initialize();

		// Validates the changes in the replace window
		AcideReplaceWindow.getInstance().validate();

		// Repaints the replace window
		AcideReplaceWindow.getInstance().repaint();
	}

	/**
	 * Updates the ACIDE - A Configurable IDE status bar.
	 */
	public void updateStatusBar() {

		// Gets the current lines number message
		String currentNumLinesMessage = AcideMainWindow.getInstance()
				.getStatusBar().getNumberOfLinesMessage();

		// Gets the number of lines
		int lastIndexOfDoubleColon = currentNumLinesMessage.lastIndexOf(":");

		// If contains the last index of double colon
		if (lastIndexOfDoubleColon != -1) {

			// Gets the number of lines
			String numLines = currentNumLinesMessage
					.substring(lastIndexOfDoubleColon + 1,
							currentNumLinesMessage.length());

			// Updates the number of lines in the status bar
			String numLinesMessage = AcideLanguageManager.getInstance()
					.getLabels().getString("s1001")
					+ numLines;

			// Updates the number of lines message in the status bar
			AcideMainWindow.getInstance().getStatusBar()
					.setNumberOfLinesMessage(numLinesMessage);
		}
	}

	/**
	 * Updates all the ACIDE - A Configurable IDE popup menus.
	 */
	public void updatesPopupMenus() {

		// Resets the explorer panel popup menu
		AcideMainWindow.getInstance().getExplorerPanel().buildPopupMenu();

		// Resets the console panel popup menu
		AcideMainWindow.getInstance().getConsolePanel().buildPopupMenu();

		// Resets the status bar popup menu
		AcideMainWindow.getInstance().getStatusBar().buildPopupMenu();

		for (int index = 0; index < AcideMainWindow.getInstance()
				.getFileEditorManager().getNumberOfFileEditorPanels(); index++)

			// Updates the file editor panel popup menus
			AcideMainWindow.getInstance().getFileEditorManager()
					.getFileEditorPanelAt(index).buildPopupMenu();
	}
	
	private void updateDataBaseTree() {
		AcideMainWindow.getInstance().getDataBasePanel().updateDataBaseTree();		
	}
	

	/**
	 * Builds the ACIDE - A Configurable IDE language menu.
	 */
	public void build() {
		try{
			// Removes all the menu items
			removeAll();

			URL []urls={new File(AcideLanguageManager.BUNDLE_PATH).toURI().toURL()};
			ClassLoader loadder = new URLClassLoader(urls);
            //Gets the array of the with the language files                
			for (String filePath : AcideLanguageManager.getInstance().get_languageFiles()) {
            // If the file exists
			if (new File(AcideLanguageManager.BUNDLE_PATH+File.separator+filePath).exists()) {

				// Creates the menu item
				JMenuItem languageFileMenuItem = new JMenuItem(filePath.substring(0, filePath.lastIndexOf(".")));
                
				String iconPath = ResourceBundle.getBundle(filePath.substring(0, filePath.lastIndexOf("."))
									, Locale.ENGLISH, loadder).getString("sconfig4");
				if(iconPath!= null && !iconPath.equals("") && new File(new File(iconPath).toURI().toURL().getPath()).exists())
					languageFileMenuItem.setIcon(IconsUtils.getIcon(iconPath));
                                
				// Adds the action listener to the menu item
				languageFileMenuItem
						.addActionListener(null/*new AcideRecentFilesMenu.RecentFileMenuItemAction()*/);

				// Adds the launguage file menu item to the menu
				add(languageFileMenuItem);
				
				// Adds the launguage file menu item to the items list
				_languageItems.put(languageFileMenuItem.getText(),languageFileMenuItem);
			}
		}

		// Validates the changes in the menu
		revalidate();
            }catch(MalformedURLException e){
                AcideLog.getLog().error(e);
            }
		// Repaints the menu
		repaint();
	}



	
	/**
	 * Returns the ACIDE - A Configurable IDE language menu language items map.
	 * 
	 * @return the ACIDE - A Configurable IDE language menu language items map.
	 */
	public HashMap<String, JMenuItem> get_languageItems() {
		return _languageItems;
	}


	/**
	 * Sets a new value to the ACIDE - A Configurable IDE language menu language items map.
	 * 
	 * @param _languageItems new value to set.
	 */
	public void set_languageItems(HashMap<String, JMenuItem> _languageItems) {
		this._languageItems = _languageItems;
	}
}