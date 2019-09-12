/*
 * ACIDE - A Configurable IDE
 * Official web site: http://acide.sourceforge.net
 * 
 * Copyright (C) 2007-2013  
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
package acide.configuration.menu;

import java.io.FileInputStream;
import java.io.FileOutputStream;

import javax.swing.JOptionPane;

import acide.configuration.menu.items.AcideMenuItemsManager;
import acide.gui.menuBar.configurationMenu.AcideConfigurationMenu;
import acide.gui.menuBar.configurationMenu.consoleMenu.AcideConsoleMenu;
import acide.gui.menuBar.configurationMenu.databasePanelMenu.AcideDatabasePanelMenu;
import acide.gui.menuBar.configurationMenu.databasePanelMenu.showDetails.AcideShowDetailsMenu;
import acide.gui.menuBar.configurationMenu.fileEditor.AcideFileEditorMenu;
import acide.gui.menuBar.configurationMenu.grammarMenu.AcideGrammarMenu;
import acide.gui.menuBar.configurationMenu.graphPanelMenu.AcideGraphPanelArrowColorMenu;
import acide.gui.menuBar.configurationMenu.graphPanelMenu.AcideGraphPanelArrowShapeMenu;
import acide.gui.menuBar.configurationMenu.graphPanelMenu.AcideGraphPanelMenu;
import acide.gui.menuBar.configurationMenu.graphPanelMenu.AcideGraphPanelNodeShapeMenu;
import acide.gui.menuBar.configurationMenu.languageMenu.AcideLanguageMenu;
import acide.gui.menuBar.configurationMenu.lexiconMenu.AcideLexiconMenu;
import acide.gui.menuBar.configurationMenu.menuMenu.AcideMenuMenu;
import acide.gui.menuBar.configurationMenu.toolBarMenu.AcideToolBarMenu;
import acide.gui.menuBar.editMenu.AcideEditMenu;
import acide.gui.menuBar.editMenu.changeCaseMenu.AcideChangeCaseMenu;
import acide.gui.menuBar.fileMenu.AcideFileMenu;
import acide.gui.menuBar.helpMenu.AcideHelpMenu;
import acide.gui.menuBar.projectMenu.AcideProjectMenu;
import acide.gui.menuBar.viewMenu.AcideViewMenu;
import acide.language.AcideLanguageManager;
import acide.log.AcideLog;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

/**
 * ACIDE - A Configurable IDE console menu items configuration.
 * 
 * @version 0.11
 */
public class AcideMenuItemsConfiguration {
	
	/**
	 * ACIDE - A Configurable IDE menu items configuration default path.
	 */
	public static final String DEFAULT_PATH = "./configuration/menu/";
	
	/**
	 * ACIDE - A Configurable IDE menu items configuration default name.
	 */
	public static final String DEFAULT_NAME = "default.xml";
	/**
	 * ACIDE - A Configurable IDE menu configuration unique class instance;
	 */
	private static AcideMenuItemsConfiguration _instance;
	
	/**
	 * ACIDE - A Configurable IDE menu items list manager.
	 */
	private AcideMenuItemsManager _itemsManager;
	
	/**
	 * Creates a new menu items configuration.
	 */
	public AcideMenuItemsConfiguration() {
		super();
	}

	/**
	 * Creates a new menu items configuration.
	 * @param manager
	 * 			the manager to set
	 */
	public AcideMenuItemsConfiguration(AcideMenuItemsManager manager){
		_itemsManager = new AcideMenuItemsManager(manager);
	}
	
	/**
	 * Resets the menu items configuration and set the manager given as parameter.
	 * @param manager
	 * 			the manager to set
	 */
	public void reset(AcideMenuItemsManager manager){
		_itemsManager = new AcideMenuItemsManager(manager);
	}
	
	/**
	 * Returns the ACIDE - A Configurable IDE menu items configuration unique class
	 * instance.
	 * 
	 * @return the ACIDE - A Configurable IDE menu items configuration unique class
	 *         instance
	 */
	public static AcideMenuItemsConfiguration getInstance() {

		if (_instance == null)
			_instance = new AcideMenuItemsConfiguration();
		return _instance;
	}
	
	/**
	 * Loads the ACIDE - A Configurable IDE menu items configuration from a XML
	 * file which is located in a path given as a parameter.
	 * 
	 * @param path
	 *            File path of the file to extract the configuration from.
	 */
	public void load(String path) {

		// If the name is already set by the user
		if ((path != null) && (!path.trim().equalsIgnoreCase(""))) {
			try {

				// Creates the XStream object
				XStream x = new XStream(new DomDriver());

				// Creates the file input stream
				FileInputStream fileInputStream = new FileInputStream(path);

				// Gets the lexicon configuration from the XML file
				AcideMenuItemsConfiguration menuItemsConfiguration = 
						(AcideMenuItemsConfiguration) x.fromXML(fileInputStream);

	
				// Gets the commands manager
				AcideMenuItemsManager itemsManager = menuItemsConfiguration
						.getMenuItemsManager();

				// Closes the file input stream
				fileInputStream.close();

				// Stores the items manager
				_itemsManager = itemsManager;

			} catch (Exception exception) {

				// Displays an error message
				JOptionPane.showMessageDialog(null, AcideLanguageManager
						.getInstance().getLabels().getString("s2151")
						+ path
						+ AcideLanguageManager.getInstance().getLabels()
								.getString("s957")
						+ DEFAULT_PATH
						+ DEFAULT_NAME);

				// If the file does not exist, loads the default configuration
				load(DEFAULT_PATH + DEFAULT_NAME);

				// Updates the log
				AcideLog.getLog().info(
						AcideLanguageManager.getInstance().getLabels()
								.getString("s2153")
								+ " " + path);
			}
		}
	}
	
	/**
	 * Returns the ACIDE - A Configurable IDE menu items list
	 * manager.
	 * 
	 * @return the ACIDE - A Configurable IDE menu items list
	 *         manager.
	 */
	public AcideMenuItemsManager getMenuItemsManager() {
		return _itemsManager;
	}
	
	/**
	 * Sets a new value to the ACIDE - A Configurable IDE menu items list
	 *  manager.
	 * 
	 * @param itemsManager
	 *            new value to set.
	 */
	public void setMenuItemsManager(AcideMenuItemsManager itemsManager) {
		_itemsManager = itemsManager;
	}
	
	/**
	 * Saves the ACIDE - A Configurable IDE menu items configuration into a XML
	 * file in a defined path given default, returning true if the
	 * operation succeed or false in other case.
	 * 
	 * @return true if the operation succeed or false in other case.
	 */
	public boolean save() {

			// Creates the XStream object to write in the XML file
			XStream xStream = new XStream(new DomDriver());

			try {

				// Creates the file output stream
				FileOutputStream fileOutputStream = new FileOutputStream(DEFAULT_PATH + DEFAULT_NAME);

				// Parses it to XML
				xStream.toXML(this, fileOutputStream);

				// Closes the file output stream
				fileOutputStream.close();

			} catch (Exception exception) {

				// Updates the log
				AcideLog.getLog().error(exception.getMessage());
				exception.printStackTrace();
				return false;
			}

		return true;
	}
	
	/**
	 * Saves the ACIDE - A Configurable IDE menu items configuration into a XML
	 * file in a defined path given, returning true if the
	 * operation succeed or false in other case.
	 * 
	 * @param path where menu items configuration is saved.
	 * @return true if the operation succeed or false in other case.
	 */
	public boolean save(String path) {

			// Creates the XStream object to write in the XML file
			XStream xStream = new XStream(new DomDriver());

			try {

				// Creates the file output stream
				FileOutputStream fileOutputStream = new FileOutputStream(path);

				// Parses it to XML
				xStream.toXML(this, fileOutputStream);

				// Closes the file output stream
				fileOutputStream.close();

			} catch (Exception exception) {

				// Updates the log
				AcideLog.getLog().error(exception.getMessage());
				exception.printStackTrace();
				return false;
			}

		return true;
	}

	/**
	 * Return if the menu items configuration has the item with the name given as parameter
	 * @param item
	 * 			the name of item to find
	 * @return
	 * 			if has the item with the name given as parameter
	 */
	public boolean hasItem(String item){
		return _itemsManager.hasItem(item);
	}
	
	/**
	 * Return if the menu items configuration has the submenu with the name given as parameter
	 * @param submenu
	 * 			the name of submenu to find
	 * @return
	 * 		if has the submenu given as parameter
	 */
	public boolean hasSubmenu(String submenu){
		return _itemsManager.hasSubmenu(submenu);
	}
	
	/**
	 * Return the object with the name given as parameter
	 * @param object
	 * 		the name of the object we want to find
	 * @return
	 * 		the object with the name given as parameter
	 */
	public AcideMenuObjectConfiguration getObject(String object){
		return _itemsManager.getObject(object);
	}
	
	/**
	 * Return the item with the name given as parameter
	 * @param item
	 * 		the name of the item we want to find
	 * @return
	 * 		the item with the name given as parameter
	 */
	public AcideMenuItemConfiguration getItem(String item){
		return _itemsManager.getItem(item);
	}
	
	/**
	 * Return the submenu with the name given as parameter
	 * @param submenu
	 * 		the name of the submenu we want to find
	 * @return
	 * 		the object with the submenu given as parameter
	 */
	public AcideMenuSubmenuConfiguration getSubmenu(String submenu){
		return _itemsManager.getSubmenu(submenu);
	}
	
	/**
	 * Insert an object into the objectlist at the position given as a
	 * parameter and shift the element currently at that position (if any)
	 *  and any subsequent elements to the right (add one to their indices). 
	 * 
	 * @param index
	 *            index to insert.
	 * @param object
	 *            object to insert.
	 */
	public void insertAt(int index, Object object){
		_itemsManager.insertAt(index, object);
	}
	
	/**
	 * Insert an object into the objectlist at the end of the list. 
	 * 
	 * @param object
	 *            object to insert.
	 */
	public void insertObject(Object object){
		_itemsManager.insertItem((AcideMenuObjectConfiguration) object);
	}
	
	/**
	 * Deletes an object given as a parameter.
	 * 
	 * @param object
	 *            object to delete.
	 */
	public void deleteObject(String object) {
		_itemsManager.deleteObject(object);
	}
	
	/**
	 * Deletes a item given as a parameter.
	 * 
	 * @param item
	 *            item to delete.
	 */
	public void deleteItem(String item) {
		_itemsManager.deleteItem(item);
	}
	
	/**
	 * Deletes a submenu given as a parameter.
	 * 
	 * @param submenu
	 *            submenu to delete.
	 */
	public void deleteSubmenu(String submenu) {
		_itemsManager.deleteSubmenu(submenu);
	}
	
	/**
	 * Gets the default menu bar for ACIDE - A Configurable IDE.
	 * @return
	 * 		the configuration for the default menu bar.
	 */
	public AcideMenuSubmenuConfiguration getDefaultMenuBar(){
		
		AcideMenuSubmenuConfiguration result = new AcideMenuSubmenuConfiguration("Default");
		result.setErasable(false);
		result.setVisible(true);
		
		//FILE
		AcideMenuSubmenuConfiguration file = getFileDefaultSubmenu();
		result.insertObject(file);
		
		//EDIT 
		AcideMenuSubmenuConfiguration edit = getEditDefaultSubmenu();
		result.insertObject(edit);
		
		//PROJECT
		AcideMenuSubmenuConfiguration project = getProjectDefaultSubmenu();
		result.insertObject(project);
		
		//VIEW 
		AcideMenuSubmenuConfiguration view = getViewDefaultSubmenu();
		result.insertObject(view);
		
		//CONFIGURATION
		AcideMenuSubmenuConfiguration configuration = getConfigurationDefaultSubmenu();
		result.insertObject(configuration);
		
		//HELP
		AcideMenuSubmenuConfiguration help = getHelpDefaultSubmenu();
		result.insertObject(help);
		
		return result;
	}

	/**
	 * Gets the default file menu for ACIDE - A Configurable IDE.
	 * @return
	 * 		the configuration for the default file menu.
	 */
	public AcideMenuSubmenuConfiguration getFileDefaultSubmenu() {
		AcideMenuSubmenuConfiguration result = new AcideMenuSubmenuConfiguration(AcideFileMenu.FILE_MENU_NAME);
		result.setErasable(false);
		result.setVisible(true);
		
		//NEW FILE
		AcideMenuItemConfiguration newFile = new AcideMenuItemConfiguration(AcideFileMenu.NEW_FILE_NAME);
		newFile.setErasable(false);
		newFile.setVisible(true);
		newFile.setImage("./resources/icons/menu/file/newFile.png");
		newFile.setCommand("$NEW_FILE");
		result.insertObject(newFile);
		
		//OPEN FILE
		AcideMenuItemConfiguration openFile = new AcideMenuItemConfiguration(AcideFileMenu.OPEN_FILE_NAME);
		openFile.setErasable(false);
		openFile.setVisible(true);
		openFile.setImage("./resources/icons/menu/file/openFile.png");
		openFile.setCommand("$OPEN_FILE");
		result.insertObject(openFile);
		
		//OPEN RECENT FILES
		AcideMenuSubmenuConfiguration openRecentFiles = new AcideMenuSubmenuConfiguration(AcideFileMenu.OPEN_RECENT_FILES_NAME);
		openRecentFiles.setErasable(false);
		openRecentFiles.setVisible(true);
		result.insertObject(openRecentFiles);
		
		//OPEN ALL FILES
		AcideMenuItemConfiguration openAllFiles = new AcideMenuItemConfiguration(AcideFileMenu.OPEN_ALL_FILES_NAME);
		openAllFiles.setErasable(false);
		openAllFiles.setVisible(true);
		openAllFiles.setImage("./resources/icons/menu/file/openAllFiles.png");
		openAllFiles.setCommand("$OPEN_ALL_FILES");
		result.insertObject(openAllFiles);
		
		//CLOSE FILE
		AcideMenuItemConfiguration closeFile = new AcideMenuItemConfiguration(AcideFileMenu.CLOSE_FILE_NAME);
		closeFile.setErasable(false);
		closeFile.setVisible(true);
		closeFile.setImage("./resources/icons/menu/file/closeFile.png");
		closeFile.setCommand("$CLOSE_FILE");
		result.insertObject(closeFile);
		
		//CLOSE ALL FILES
		AcideMenuItemConfiguration closeAllFiles = new AcideMenuItemConfiguration(AcideFileMenu.CLOSE_ALL_FILES_NAME);
		closeAllFiles.setErasable(false);
		closeAllFiles.setVisible(true);
		closeAllFiles.setImage("./resources/icons/menu/file/closeAllFiles.png");
		closeAllFiles.setCommand("$CLOSE_ALL_FILES");
		result.insertObject(closeAllFiles);
		
		//SAVE FILE
		AcideMenuItemConfiguration saveFile = new AcideMenuItemConfiguration(AcideFileMenu.SAVE_FILE_NAME);
		saveFile.setErasable(false);
		saveFile.setVisible(true);
		saveFile.setImage("./resources/icons/menu/file/saveFile.png");
		saveFile.setCommand("$SAVE_FILE");
		result.insertObject(saveFile);
		
		//SAVE FILE AS
		AcideMenuItemConfiguration saveFileAs = new AcideMenuItemConfiguration(AcideFileMenu.SAVE_FILE_AS_NAME);
		saveFileAs.setErasable(false);
		saveFileAs.setVisible(true);
		saveFileAs.setImage("./resources/icons/menu/file/saveFileAs.png");
		saveFileAs.setCommand("$SAVE_FILE_AS");
		result.insertObject(saveFileAs);
		
		//SAVE ALL FILES
		AcideMenuItemConfiguration saveAllFiles = new AcideMenuItemConfiguration(AcideFileMenu.SAVE_ALL_FILES_NAME);
		saveAllFiles.setErasable(false);
		saveAllFiles.setVisible(true);
		saveAllFiles.setImage("./resources/icons/menu/file/saveAllFiles.png");
		saveAllFiles.setCommand("$SAVE_ALL_FILES");
		result.insertObject(saveAllFiles);
		
		//PRINT FILE
		AcideMenuItemConfiguration printFile = new AcideMenuItemConfiguration(AcideFileMenu.PRINT_FILE_NAME);
		printFile.setErasable(false);
		printFile.setVisible(true);
		printFile.setImage("./resources/icons/menu/file/printFile.png");
		printFile.setCommand("$PRINT_FILE");
		result.insertObject(printFile);
		
		//EXIT
		AcideMenuItemConfiguration exit = new AcideMenuItemConfiguration(AcideFileMenu.EXIT_NAME);
		exit.setErasable(false);
		exit.setVisible(true);
		exit.setImage("./resources/icons/menu/file/exit.png");
		exit.setCommand("$EXIT_FILE");
		result.insertObject(exit);
		
		return result;
	}

	/**
	 * Gets the default edit menu for ACIDE - A Configurable IDE.
	 * @return
	 * 		the configuration for the default edit menu.
	 */
	public AcideMenuSubmenuConfiguration getEditDefaultSubmenu() {
		AcideMenuSubmenuConfiguration result = new AcideMenuSubmenuConfiguration(AcideEditMenu.EDIT_MENU_NAME);
		result.setErasable(false);
		result.setVisible(true);
		
		//UNDO
		AcideMenuItemConfiguration undo = new AcideMenuItemConfiguration(AcideEditMenu.UNDO_NAME);
		undo.setErasable(false);
		undo.setVisible(true);
		undo.setImage("./resources/icons/menu/edit/undo.png");
		undo.setCommand("$UNDO");
		result.insertObject(undo);
		
		//REDO
		AcideMenuItemConfiguration redo = new AcideMenuItemConfiguration(AcideEditMenu.REDO_NAME);
		redo.setErasable(false);
		redo.setVisible(true);
		redo.setImage("./resources/icons/menu/edit/redo.png");
		redo.setCommand("$REDO");
		result.insertObject(redo);
		
		//COPY
		AcideMenuItemConfiguration copy = new AcideMenuItemConfiguration(AcideEditMenu.COPY_NAME);
		copy.setErasable(false);
		copy.setVisible(true);
		copy.setImage("./resources/icons/menu/edit/copy.png");
		copy.setCommand("$COPY");
		result.insertObject(copy);
		
		//PASTE
		AcideMenuItemConfiguration paste = new AcideMenuItemConfiguration(AcideEditMenu.PASTE_NAME);
		paste.setErasable(false);
		paste.setVisible(true);
		paste.setImage("./resources/icons/menu/edit/paste.png");
		paste.setCommand("$PASTE");
		result.insertObject(paste);
		
		//CUT
		AcideMenuItemConfiguration cut = new AcideMenuItemConfiguration(AcideEditMenu.CUT_NAME);
		cut.setErasable(false);
		cut.setVisible(true);
		cut.setImage("./resources/icons/menu/edit/cut.png");
		cut.setCommand("$CUT");
		result.insertObject(cut);
		
		//TOGGLE
		AcideMenuItemConfiguration toggleComment = new AcideMenuItemConfiguration(AcideEditMenu.TOGGLE_COMMENT_NAME);
		toggleComment.setErasable(false);
		toggleComment.setVisible(true);
		toggleComment.setCommand("$TOGGLE_COMMENT");
		result.insertObject(toggleComment);
		
		//MAKE COMMENT
		AcideMenuItemConfiguration makeComment = new AcideMenuItemConfiguration(AcideEditMenu.MAKE_COMMENT_NAME);
		makeComment.setErasable(false);
		makeComment.setVisible(true);
		makeComment.setCommand("$MAKE_COMMENT");
		result.insertObject(cut);
		
		//RELEASE COMMENT
		AcideMenuItemConfiguration releaseComment = new AcideMenuItemConfiguration(AcideEditMenu.RELEASE_COMMENT_NAME);
		releaseComment.setErasable(false);
		releaseComment.setVisible(true);
		releaseComment.setCommand("$RELEASE_COMMENT");
		result.insertObject(cut);
		
		//CHANGE CASE
		AcideMenuSubmenuConfiguration changeCase = getChangeCaseDefaultSubmenu();
		result.insertObject(changeCase);
		
		//SELECT ALL
		AcideMenuItemConfiguration selectAll = new AcideMenuItemConfiguration(AcideEditMenu.SELECT_ALL_NAME);
		selectAll.setErasable(false);
		selectAll.setVisible(true);
		selectAll.setImage("./resources/icons/menu/edit/selectAll.png");
		selectAll.setCommand("$SELECT_ALL");
		result.insertObject(selectAll);
		
		//GO TO LINE
		AcideMenuItemConfiguration goToLine = new AcideMenuItemConfiguration(AcideEditMenu.GO_TO_LINE_NAME);
		goToLine.setErasable(false);
		goToLine.setVisible(true);
		goToLine.setImage("./resources/icons/menu/edit/goToLine.png");
		goToLine.setCommand("$GO_TO_LINE");
		result.insertObject(goToLine);
		
		//SEARCH
		AcideMenuItemConfiguration search = new AcideMenuItemConfiguration(AcideEditMenu.SEARCH_NAME);
		search.setErasable(false);
		search.setVisible(true);
		search.setImage("./resources/icons/menu/edit/search.png");
		search.setCommand("$SEARCH");
		result.insertObject(search);
		
		//REPLACE
		AcideMenuItemConfiguration replace = new AcideMenuItemConfiguration(AcideEditMenu.REPLACE_NAME);
		replace.setErasable(false);
		replace.setVisible(true);
		replace.setImage("./resources/icons/menu/edit/replace.png");
		replace.setCommand("$REPLACE");
		result.insertObject(replace);
		
		return result;
	}
	
	/**
	 * Gets the default change case menu for ACIDE - A Configurable IDE.
	 * @return
	 * 		the configuration for the default change case menu.
	 */
	public AcideMenuSubmenuConfiguration getChangeCaseDefaultSubmenu() {
		AcideMenuSubmenuConfiguration result = new AcideMenuSubmenuConfiguration(AcideChangeCaseMenu.CHANGE_CASE_MENU_NAME);
		result.setErasable(false);
		result.setVisible(true);
		
		//UPPER CASE
		AcideMenuItemConfiguration upperCase = new AcideMenuItemConfiguration(AcideChangeCaseMenu.UPPER_CASE_NAME);
		upperCase.setErasable(false);
		upperCase.setVisible(true);
		upperCase.setCommand("$UPPER_CASE");
		result.insertObject(upperCase);
		
		//LOWER CASE
		AcideMenuItemConfiguration lowerCase = new AcideMenuItemConfiguration(AcideChangeCaseMenu.LOWER_CASE_NAME);
		lowerCase.setErasable(false);
		lowerCase.setVisible(true);
		lowerCase.setCommand("$LOWER_CASE");
		result.insertObject(lowerCase);
		
		//CAPITALIZE
		AcideMenuItemConfiguration capitalize = new AcideMenuItemConfiguration(AcideChangeCaseMenu.CAPITALIZE_NAME);
		capitalize.setErasable(false);
		capitalize.setVisible(true);
		capitalize.setCommand("$CAPITALIZE");
		result.insertObject(capitalize);
		
		//INVERT CASE
		AcideMenuItemConfiguration invertCase = new AcideMenuItemConfiguration(AcideChangeCaseMenu.INVERT_CASE_NAME);
		invertCase.setErasable(false);
		invertCase.setVisible(true);
		invertCase.setCommand("$INVERT_CASE");
		result.insertObject(invertCase);
		
		return result;
	}


	/**
	 * Gets the default project menu for ACIDE - A Configurable IDE.
	 * @return
	 * 		the configuration for the default project menu.
	 */
	public AcideMenuSubmenuConfiguration getProjectDefaultSubmenu() {
		AcideMenuSubmenuConfiguration result = new AcideMenuSubmenuConfiguration(AcideProjectMenu.PROJECT_MENU_NAME);
		result.setErasable(false);
		result.setVisible(true);
		
		//NEW PROJECT
		AcideMenuItemConfiguration newProject = new AcideMenuItemConfiguration(AcideProjectMenu.NEW_PROJECT_NAME);
		newProject.setErasable(false);
		newProject.setVisible(true);
		newProject.setImage("./resources/icons/menu/project/newProject.png");
		newProject.setCommand("$NEW_PROJECT");
		result.insertObject(newProject);
		
		//OPEN PROJECT
		AcideMenuItemConfiguration openProject = new AcideMenuItemConfiguration(AcideProjectMenu.OPEN_PROJECT_NAME);
		openProject.setErasable(false);
		openProject.setVisible(true);
		openProject.setImage("./resources/icons/menu/project/openProject.png");
		openProject.setCommand("$OPEN_PROJECT");
		result.insertObject(openProject);
		
		//OPEN RECENT PROJECTS
		AcideMenuSubmenuConfiguration openRecentProjects = new AcideMenuSubmenuConfiguration(AcideProjectMenu.OPEN_RECENT_PROJECTS_NAME);
		openRecentProjects.setErasable(false);
		openRecentProjects.setVisible(true);
		result.insertObject(openRecentProjects);
		
		//CLOSE PROJECT
		AcideMenuItemConfiguration closeProject = new AcideMenuItemConfiguration(AcideProjectMenu.CLOSE_PROJECT_NAME);
		closeProject.setErasable(false);
		closeProject.setVisible(true);
		closeProject.setImage("./resources/icons/menu/project/closeProject.png");
		closeProject.setCommand("$CLOSE_PROJECT");
		result.insertObject(closeProject);
		
		//SAVE PROJECT
		AcideMenuItemConfiguration saveProject = new AcideMenuItemConfiguration(AcideProjectMenu.SAVE_PROJECT_NAME);
		saveProject.setErasable(false);
		saveProject.setVisible(true);
		saveProject.setImage("./resources/icons/menu/project/saveProject.png");
		saveProject.setCommand("$SAVE_PROJECT");
		result.insertObject(saveProject);
		
		//SAVE PROJECT AS
		AcideMenuItemConfiguration saveProjectAs = new AcideMenuItemConfiguration(AcideProjectMenu.SAVE_PROJECT_AS_NAME);
		saveProjectAs.setErasable(false);
		saveProjectAs.setVisible(true);
		saveProjectAs.setImage("./resources/icons/menu/project/saveProjectAs.png");
		saveProjectAs.setCommand("$SAVE_PROJECT_AS");
		result.insertObject(saveProjectAs);
		
		//ADD OPPENED FILES
		AcideMenuItemConfiguration addOppenedFiles = new AcideMenuItemConfiguration(AcideProjectMenu.ADD_OPENED_FILES_NAME);
		addOppenedFiles.setErasable(false);
		addOppenedFiles.setVisible(true);
		addOppenedFiles.setImage("./resources/icons/menu/project/addOpenedFiles.png");
		addOppenedFiles.setCommand("$ADD_OPENED_FILES");
		result.insertObject(addOppenedFiles);
		
		//NEW PROJECT FILE
		AcideMenuItemConfiguration newProjectFile = new AcideMenuItemConfiguration(AcideProjectMenu.NEW_PROJECT_FILE_NAME);
		newProjectFile.setErasable(false);
		newProjectFile.setVisible(true);
		newProjectFile.setImage("./resources/icons/menu/project/newFile.png");
		newProjectFile.setCommand("$NEW_PROJECT_FILE");
		result.insertObject(newProjectFile);
		
		//ADD FILE
		AcideMenuItemConfiguration addFile = new AcideMenuItemConfiguration(AcideProjectMenu.ADD_FILE_NAME);
		addFile.setErasable(false);
		addFile.setVisible(true);
		addFile.setImage("./resources/icons/menu/project/addFile.png");
		addFile.setCommand("$ADD_FILE");
		result.insertObject(addFile);
		
		//REMOVE FILE
		AcideMenuItemConfiguration removeFile = new AcideMenuItemConfiguration(AcideProjectMenu.REMOVE_FILE_NAME);
		removeFile.setErasable(false);
		removeFile.setVisible(true);
		removeFile.setImage("./resources/icons/menu/project/removeFile.png");
		removeFile.setCommand("$REMOVE_FILE");
		result.insertObject(removeFile);
		
		//DELETE FILE
		AcideMenuItemConfiguration deleteFile = new AcideMenuItemConfiguration(AcideProjectMenu.DELETE_FILE_NAME);
		deleteFile.setErasable(false);
		deleteFile.setVisible(true);
		deleteFile.setImage("./resources/icons/menu/project/deleteFile.png");
		deleteFile.setCommand("$DELETE_FILE");
		result.insertObject(deleteFile);
		
		//ADD FOLDER
		AcideMenuItemConfiguration addFolder = new AcideMenuItemConfiguration(AcideProjectMenu.ADD_FOLDER_NAME);
		addFolder.setErasable(false);
		addFolder.setVisible(true);
		addFolder.setImage("./resources/icons/menu/project/addFolder.png");
		addFolder.setCommand("$ADD_FOLDER");
		result.insertObject(addFolder);
		
		//REMOVE FOLDER
		AcideMenuItemConfiguration removeFolder = new AcideMenuItemConfiguration(AcideProjectMenu.REMOVE_FOLDER_NAME);
		removeFolder.setErasable(false);
		removeFolder.setVisible(true);
		removeFolder.setImage("./resources/icons/menu/project/removeFolder.png");
		removeFolder.setCommand("$REMOVE_FOLDER");
		result.insertObject(removeFolder);
		
		//COMPILE
		AcideMenuItemConfiguration compile = new AcideMenuItemConfiguration(AcideProjectMenu.COMPILE_NAME);
		compile.setErasable(false);
		compile.setVisible(true);
		compile.setImage("./resources/icons/menu/project/compile.png");
		compile.setCommand("$COMPILE");
		result.insertObject(compile);
		
		//EXECUTE
		AcideMenuItemConfiguration execute = new AcideMenuItemConfiguration(AcideProjectMenu.EXECUTE_NAME);
		execute.setErasable(false);
		execute.setVisible(true);
		execute.setImage("./resources/icons/menu/project/execute.png");
		execute.setCommand("$EXECUTE");
		result.insertObject(execute);
		
		//SET COMPILABLE FILE
		AcideMenuItemConfiguration setCompilableFile = new AcideMenuItemConfiguration(AcideProjectMenu.SET_COMPILABLE_FILE_NAME);
		setCompilableFile.setErasable(false);
		setCompilableFile.setVisible(true);
		setCompilableFile.setImage("./resources/icons/menu/project/setCompilable.png");
		setCompilableFile.setCommand("$SET_COMPILABLE_FILE");
		result.insertObject(setCompilableFile);
		
		//UNSET COMPILABLE FILE
		AcideMenuItemConfiguration unsetCompilableFile = new AcideMenuItemConfiguration(AcideProjectMenu.UNSET_COMPILABLE_FILE_NAME);
		unsetCompilableFile.setErasable(false);
		unsetCompilableFile.setVisible(true);
		unsetCompilableFile.setImage("./resources/icons/menu/project/unsetCompilable.png");
		unsetCompilableFile.setCommand("$UNSET_COMPILABLE_FILE");
		result.insertObject(unsetCompilableFile);
		
		//SET MAIN FILE
		AcideMenuItemConfiguration setMainFile = new AcideMenuItemConfiguration(AcideProjectMenu.SET_MAIN_FILE_NAME);
		setMainFile.setErasable(false);
		setMainFile.setVisible(true);
		setMainFile.setImage("./resources/icons/menu/project/setMain.png");
		setMainFile.setCommand("$SET_MAIN_FILE");
		result.insertObject(setMainFile);
		
		//UNSET MAIN FILE
		AcideMenuItemConfiguration unsetMainFile = new AcideMenuItemConfiguration(AcideProjectMenu.UNSET_MAIN_FILE_NAME);
		unsetMainFile.setErasable(false);
		unsetMainFile.setVisible(true);
		unsetMainFile.setImage("./resources/icons/menu/project/unsetMain.png");
		unsetMainFile.setCommand("$UNSET_MAIN_FILE");
		result.insertObject(unsetMainFile);
		
		return result;
	}

	/**
	 * Gets the default view menu for ACIDE - A Configurable IDE.
	 * @return
	 * 		the configuration for the default view menu.
	 */
	public AcideMenuSubmenuConfiguration getViewDefaultSubmenu() {
		AcideMenuSubmenuConfiguration result = new AcideMenuSubmenuConfiguration(AcideViewMenu.VIEW_MENU_NAME);
		result.setErasable(false);
		result.setVisible(true);
		
		//SHOW LOG TAB
		AcideMenuItemConfiguration showLogTab = new AcideMenuItemConfiguration(AcideViewMenu.SHOW_LOG_TAB_NAME);
		showLogTab.setErasable(false);
		showLogTab.setVisible(true);
		showLogTab.setImage("./resources/icons/menu/view/showLogTab.png");
		showLogTab.setCommand("$SHOW_LOG_TAB");
		result.insertObject(showLogTab);
		
		//SHOW EXPLORER PANEL
		AcideMenuItemConfiguration showExplorerPanel = new AcideMenuItemConfiguration(AcideViewMenu.SHOW_EXPLORER_PANEL_NAME);
		showExplorerPanel.setErasable(false);
		showExplorerPanel.setVisible(true);
		showExplorerPanel.setImage("./resources/icons/menu/view/showExplorerPanel.png");
		showExplorerPanel.setCommand("$SHOW_EXPLORER_PANEL");
		result.insertObject(showExplorerPanel);
		
		//SHOW CONSOLE PANEL
		AcideMenuItemConfiguration showConsolePanel = new AcideMenuItemConfiguration(AcideViewMenu.SHOW_CONSOLE_PANEL_NAME);
		showConsolePanel.setErasable(false);
		showConsolePanel.setVisible(true);
		showConsolePanel.setImage("./resources/icons/menu/view/showConsolePanel.png");
		showConsolePanel.setCommand("$SHOW_CONSOLE_PANEL");
		result.insertObject(showConsolePanel);
		
		//SHOW DATABASE PANEL
		AcideMenuItemConfiguration showDatabasePanel = new AcideMenuItemConfiguration(AcideViewMenu.SHOW_DATA_BASE_PANEL_NAME);
		showDatabasePanel.setErasable(false);
		showDatabasePanel.setVisible(true);
		showDatabasePanel.setImage("./resources/icons/menu/view/showDataBasePanel.png");
		showDatabasePanel.setCommand("$SHOW_DATABASE_PANEL");
		result.insertObject(showDatabasePanel);
		
		//SHOW GRAPH PANEL 
		AcideMenuItemConfiguration showGraphPanel = new AcideMenuItemConfiguration(AcideViewMenu.SHOW_GRAPH_PANEL_NAME);
		showGraphPanel.setErasable(false);
		showGraphPanel.setVisible(true);
		showGraphPanel.setImage("./resources/icons/menu/view/showGraphPanel.png");
		showGraphPanel.setCommand("$SHOW_GRAPH_PANEL");
		result.insertObject(showGraphPanel);
		
		
		// SHOW DEBUG PANEL
		AcideMenuItemConfiguration showDebugPanel = new AcideMenuItemConfiguration(
				AcideViewMenu.SHOW_DEBUG_PANEL_NAME);
		showDebugPanel.setErasable(false);
		showDebugPanel.setVisible(true);
		showDebugPanel
				.setImage("./resources/icons/menu/view/showDebugPanel.png");
		showDebugPanel.setCommand("$SHOW_DEBUG_PANEL");
		result.insertObject(showDebugPanel);


		// SHOW GRAPH PANEL
		AcideMenuItemConfiguration showAssertedDatabasePanel = new AcideMenuItemConfiguration(
				AcideViewMenu.SHOW_ASSERTED_DATABASE_PANEL_NAME);
		showAssertedDatabasePanel.setErasable(false);
		showAssertedDatabasePanel.setVisible(true);
		showAssertedDatabasePanel
				.setImage("./resources/icons/menu/view/showAssertedDatabasePanel.png");
		showAssertedDatabasePanel.setCommand("$SHOW_ASSERTED_DATABASE_PANEL");
		result.insertObject(showAssertedDatabasePanel);
		
		return result;
	}

	/**
	 * Gets the default configuration menu for ACIDE - A Configurable IDE.
	 * @return
	 * 		the configuration for the default configuration menu.
	 */
	public AcideMenuSubmenuConfiguration getConfigurationDefaultSubmenu() {
		AcideMenuSubmenuConfiguration result = new AcideMenuSubmenuConfiguration(AcideConfigurationMenu.CONFIGURATION_MENU_NAME);
		result.setErasable(false);
		result.setVisible(true);
		
		//LEXICON
		AcideMenuSubmenuConfiguration lexicon = getLexiconDefaultSubmenu();
		result.insertObject(lexicon);
		
		//GRAMMAR
		AcideMenuSubmenuConfiguration grammar = getGrammarDefaultSubmenu();
		result.insertObject(grammar);
		
		//COMPILER
		AcideMenuItemConfiguration compiler = new AcideMenuItemConfiguration(AcideConfigurationMenu.COMPILER_NAME);
		compiler.setErasable(false);
		compiler.setVisible(true);
		compiler.setImage("./resources/icons/menu/configuration/compiler.png");
		compiler.setCommand("$COMPILER");
		result.insertObject(compiler);
		
		//FILE EDITOR
		AcideMenuSubmenuConfiguration fileEditor = getFileEditorDefaultSubmenu();
		result.insertObject(fileEditor);
		
		//CONSOLE
		AcideMenuSubmenuConfiguration console = getConsoleDefaultSubmenu();
		result.insertObject(console);
		
		//DATABASE
		AcideMenuSubmenuConfiguration database = getDatabaseDefaultSubmenu();
		result.insertObject(database);
		
		//GRAPH PANEL
		AcideMenuSubmenuConfiguration graph = getGraphDefaultSubmenu();
		result.insertObject(graph);
		
		//LANGUAGE
		AcideMenuSubmenuConfiguration language = getLanguageDefaultSubmenu();
		result.insertObject(language);
		
		//MENU
		AcideMenuSubmenuConfiguration menu = getMenuDefaultSubmenu();
		result.insertObject(menu);
		
		//TOOL BAR
		AcideMenuSubmenuConfiguration toolbar = getToolbarDefaultSubmenu();
		result.insertObject(toolbar);
		
		return result;
	}

	/**
	 * Gets the default toolbar menu for ACIDE - A Configurable IDE.
	 * @return
	 * 		the configuration for the default toolbar menu.
	 */
	public AcideMenuSubmenuConfiguration getToolbarDefaultSubmenu() {
		AcideMenuSubmenuConfiguration result = new AcideMenuSubmenuConfiguration(AcideToolBarMenu.TOOLBAR_MENU_NAME);
		result.setErasable(false);
		result.setVisible(true);
		
		//NEW TOOLBAR
		AcideMenuItemConfiguration newToolbar = new AcideMenuItemConfiguration(AcideToolBarMenu.NEW_TOOLBAR_NAME);
		newToolbar.setErasable(false);
		newToolbar.setVisible(true);
		newToolbar.setCommand("$NEW_TOOLBAR");
		result.insertObject(newToolbar);
		
		//LOAD TOOLBAR
		AcideMenuItemConfiguration loadToolbar = new AcideMenuItemConfiguration(AcideToolBarMenu.LOAD_TOOLBAR_NAME);
		loadToolbar.setErasable(false);
		loadToolbar.setVisible(true);
		loadToolbar.setCommand("$LOAD_TOOLBAR");
		result.insertObject(loadToolbar);
		
		//MODIFY TOOLBAR
		AcideMenuItemConfiguration modifyToolbar = new AcideMenuItemConfiguration(AcideToolBarMenu.MODIFY_TOOLBAR_NAME);
		modifyToolbar.setErasable(false);
		modifyToolbar.setVisible(true);
		modifyToolbar.setCommand("$MODIFY_TOOLBAR");
		result.insertObject(modifyToolbar);
		
		//SAVE TOOLBAR
		AcideMenuItemConfiguration saveToolbar = new AcideMenuItemConfiguration(AcideToolBarMenu.SAVE_TOOLBAR_NAME);
		saveToolbar.setErasable(false);
		saveToolbar.setVisible(true);
		saveToolbar.setCommand("$SAVE_TOOLBAR");
		result.insertObject(saveToolbar);
		
		//SAVE TOOLBAR AS
		AcideMenuItemConfiguration saveToolbarAs = new AcideMenuItemConfiguration(AcideToolBarMenu.SAVE_TOOLBAR_AS_NAME);
		saveToolbarAs.setErasable(false);
		saveToolbarAs.setVisible(true);
		saveToolbarAs.setCommand("$SAVE_TOOLBAR_AS");
		result.insertObject(saveToolbarAs);
		
		return result;
	}

	/**
	 * Gets the default menu menu for ACIDE - A Configurable IDE.
	 * @return
	 * 		the configuration for the default menu menu.
	 */
	public AcideMenuSubmenuConfiguration getMenuDefaultSubmenu() {
		AcideMenuSubmenuConfiguration result = new AcideMenuSubmenuConfiguration(AcideMenuMenu.MENU_MENU_NAME);
		result.setErasable(false);
		result.setVisible(true);
		
		//NEW MENU
		AcideMenuItemConfiguration newMenu = new AcideMenuItemConfiguration(AcideMenuMenu.NEW_MENU_NAME);
		newMenu.setErasable(false);
		newMenu.setVisible(true);
		newMenu.setCommand("$NEW_MENU");
		result.insertObject(newMenu);
		
		//LOAD MENU
		AcideMenuItemConfiguration loadMenu = new AcideMenuItemConfiguration(AcideMenuMenu.LOAD_MENU_NAME);
		loadMenu.setErasable(false);
		loadMenu.setVisible(true);
		loadMenu.setCommand("$LOAD_MENU");
		result.insertObject(loadMenu);
		
		//MODIFY MENU
		AcideMenuItemConfiguration modifyMenu = new AcideMenuItemConfiguration(AcideMenuMenu.MODIFY_MENU_NAME);
		modifyMenu.setErasable(false);
		modifyMenu.setVisible(true);
		modifyMenu.setCommand("$MODIFY_MENU");
		result.insertObject(modifyMenu);
		
		//SAVE MENU
		AcideMenuItemConfiguration saveMenu = new AcideMenuItemConfiguration(AcideMenuMenu.SAVE_MENU_NAME);
		saveMenu.setErasable(false);
		saveMenu.setVisible(true);
		saveMenu.setCommand("$SAVE_MENU");
		result.insertObject(saveMenu);
		
		//SAVE MENU AS
		AcideMenuItemConfiguration saveMenuAs = new AcideMenuItemConfiguration(AcideMenuMenu.SAVE_MENU_AS_NAME);
		saveMenuAs.setErasable(false);
		saveMenuAs.setVisible(true);
		saveMenuAs.setCommand("$SAVE_MENU_AS");
		result.insertObject(saveMenuAs);
		
		return result;
	}

	/**
	 * Gets the default language menu for ACIDE - A Configurable IDE.
	 * @return
	 * 		the configuration for the default language menu.
	 */
	public AcideMenuSubmenuConfiguration getLanguageDefaultSubmenu() {
		AcideMenuSubmenuConfiguration result = new AcideMenuSubmenuConfiguration(AcideLanguageMenu.LANGUAGE_MENU_NAME);
		result.setErasable(false);
		result.setVisible(true);
		
		return result;
	}
	
	public AcideMenuSubmenuConfiguration getNodeShapeDefaultSubmenu() {
		AcideMenuSubmenuConfiguration result = new AcideMenuSubmenuConfiguration(AcideGraphPanelNodeShapeMenu.NODE_SHAPE_MENU_NAME);
		result.setErasable(false);
		result.setVisible(true);
		
		AcideMenuItemConfiguration nodeShapeCircle = new AcideMenuItemConfiguration(AcideGraphPanelNodeShapeMenu.NODE_SHAPE_CIRCLE);
		nodeShapeCircle.setErasable(false);
		nodeShapeCircle.setVisible(true);
		//nodeShapeCircle.setImage("./resources/icons/menu/configuration/language/spanish.png");
		nodeShapeCircle.setCommand("NODES_SHAPE_CIRCLE");
		result.insertObject(nodeShapeCircle);
		
		AcideMenuItemConfiguration nodeShapeSquare = new AcideMenuItemConfiguration(AcideGraphPanelNodeShapeMenu.NODE_SHAPE_SQUARE);
		nodeShapeSquare.setErasable(false);
		nodeShapeSquare.setVisible(true);
		//nodeShapeCircle.setImage("./resources/icons/menu/configuration/language/spanish.png");
		nodeShapeSquare.setCommand("NODES_SHAPE_SQUARE");
		result.insertObject(nodeShapeSquare);
		
		return result;
	}
	
	public AcideMenuSubmenuConfiguration getArrowShapeDefaultSubmenu() {
		AcideMenuSubmenuConfiguration result = new AcideMenuSubmenuConfiguration(AcideGraphPanelArrowShapeMenu.ARROW_SHAPE_MENU_NAME);
		result.setErasable(false);
		result.setVisible(true);
		
		AcideMenuItemConfiguration arrowShapeLine = new AcideMenuItemConfiguration(AcideGraphPanelArrowShapeMenu.ARROW_SHAPE_LINE);
		arrowShapeLine.setErasable(false);
		arrowShapeLine.setVisible(true);
		//nodeShapeCircle.setImage("./resources/icons/menu/configuration/language/spanish.png");
		arrowShapeLine.setCommand("ARROW_SHAPE_LINE");
		result.insertObject(arrowShapeLine);
		
		AcideMenuItemConfiguration arrowShapePolygon = new AcideMenuItemConfiguration(AcideGraphPanelArrowShapeMenu.ARROW_SHAPE_POLYGON);
		arrowShapePolygon.setErasable(false);
		arrowShapePolygon.setVisible(true);
		//nodeShapeCircle.setImage("./resources/icons/menu/configuration/language/spanish.png");
		arrowShapePolygon.setCommand("ARROW_SHAPE_POLYGON");
		result.insertObject(arrowShapePolygon);
		
		return result;
	}
	
	public  AcideMenuSubmenuConfiguration getArrowColorDefaultSubmenu(){
		AcideMenuSubmenuConfiguration result = new AcideMenuSubmenuConfiguration(AcideGraphPanelArrowColorMenu.ARROW_COLOR_MENU_NAME);
		result.setErasable(false);
		result.setVisible(true);
		
		AcideMenuItemConfiguration arrowColorDirect = new AcideMenuItemConfiguration(AcideGraphPanelArrowColorMenu.ARROW_COLOR_DIRECT);
		arrowColorDirect.setErasable(false);
		arrowColorDirect.setVisible(true);
		//arrowColorDirect.setImage("./resources/icons/menu/configuration/language/spanish.png");
		arrowColorDirect.setCommand("ARROW_COLOR_DIRECT");
		result.insertObject(arrowColorDirect);
		
		AcideMenuItemConfiguration arrowColorInverse = new AcideMenuItemConfiguration(AcideGraphPanelArrowColorMenu.ARROW_COLOR_INVERSE);
		arrowColorInverse.setErasable(false);
		arrowColorInverse.setVisible(true);
		//arrowColorInverse.setImage("./resources/icons/menu/configuration/language/spanish.png");
		arrowColorInverse.setCommand("ARROW_COLOR_INVERSE");
		result.insertObject(arrowColorInverse);
		
		return result;
	}
	
	
	public AcideMenuSubmenuConfiguration getGraphDefaultSubmenu() {
		AcideMenuSubmenuConfiguration result = new AcideMenuSubmenuConfiguration(AcideGraphPanelMenu.GRAPH_MENU_NAME);
		result.setErasable(false);
		result.setVisible(true);
		
		//NODE SHAPE
		AcideMenuSubmenuConfiguration nodeShape = getNodeShapeDefaultSubmenu();
		result.insertObject(nodeShape);
		
		//NODE COLOR
		AcideMenuItemConfiguration nodeColor = new AcideMenuItemConfiguration(AcideGraphPanelMenu.NODE_COLOR_NAME);
		nodeColor.setErasable(false);
		nodeColor.setVisible(true);
		
		nodeColor.setCommand("$NODES_COLOR");
		result.insertObject(nodeColor);
		
		//ARROR COLOR
		AcideMenuItemConfiguration arrowColor = new AcideMenuItemConfiguration(AcideGraphPanelMenu.ARROW_COLOR_NAME);
		arrowColor.setErasable(false);
		arrowColor.setVisible(true);
		
		arrowColor.setCommand("$ENGLISH");
		result.insertObject(arrowColor);
		
		
		
		return result;
	}

	/**
	 * Gets the default database menu for ACIDE - A Configurable IDE.
	 * @return
	 * 		the configuration for the default database menu.
	 */
	public AcideMenuSubmenuConfiguration getDatabaseDefaultSubmenu() {
		AcideMenuSubmenuConfiguration result = new AcideMenuSubmenuConfiguration(AcideDatabasePanelMenu.DATABASE_MENU_NAME);
		result.setErasable(false);
		result.setVisible(true);
		
		//DES
		AcideMenuItemConfiguration des = new AcideMenuItemConfiguration(AcideDatabasePanelMenu.DES_PANEL_NAME);
		des.setErasable(false);
		des.setVisible(true);
		des.setImage("./resources/icons/menu/configuration/databasePanel/des.png");
		des.setCommand("$DES_PANEL");
		result.insertObject(des);
		
		//ODBC
		AcideMenuItemConfiguration odbc = new AcideMenuItemConfiguration(AcideDatabasePanelMenu.ODBC_PANEL_NAME);
		odbc.setErasable(false);
		odbc.setVisible(true);
		odbc.setImage("./resources/icons/menu/configuration/databasePanel/odbc.png");
		odbc.setCommand("$ODBC_PANEL");
		result.insertObject(odbc);
		
		//SHOW DETAILS
		AcideMenuSubmenuConfiguration showDetails = getShowDetailsDefaultSubmenu();
		result.insertObject(showDetails);
		
		return result;
	}

	/**
	 * Gets the default show details menu for ACIDE - A Configurable IDE.
	 * @return
	 * 		the configuration for the default show details menu.
	 */
	public AcideMenuSubmenuConfiguration getShowDetailsDefaultSubmenu() {
		AcideMenuSubmenuConfiguration result = new AcideMenuSubmenuConfiguration(AcideShowDetailsMenu.SHOW_DETAILS_NAME);
		result.setErasable(false);
		result.setVisible(true);
		
		//NAME
		AcideMenuItemConfiguration name = new AcideMenuItemConfiguration(AcideShowDetailsMenu.SHOW_NAME_NAME);
		name.setErasable(false);
		name.setVisible(true);
		name.setCommand("$SHOW_NAME");
		result.insertObject(name);
		
		//NAME FIELDS
		AcideMenuItemConfiguration nameFields = new AcideMenuItemConfiguration(AcideShowDetailsMenu.SHOW_NAME_FIELDS_NAME);
		nameFields.setErasable(false);
		nameFields.setVisible(true);
		nameFields.setCommand("$SHOW_NAME_FIELDS");
		result.insertObject(nameFields);
		
		//NAME FIELDS TYPES
		AcideMenuItemConfiguration nameFieldsTypes = new AcideMenuItemConfiguration(AcideShowDetailsMenu.SHOW_NAME_FIELDS_TYPES_NAME);
		nameFieldsTypes.setErasable(false);
		nameFieldsTypes.setVisible(true);;
		nameFieldsTypes.setCommand("$SHOW_NAME_FIELDS_TYPES");
		result.insertObject(nameFieldsTypes);
		
		return result;
	}
	/**
	 * Gets the default console menu for ACIDE - A Configurable IDE.
	 * @return
	 * 		the configuration for the default console menu.
	 */
	public AcideMenuSubmenuConfiguration getConsoleDefaultSubmenu() {
		AcideMenuSubmenuConfiguration result = new AcideMenuSubmenuConfiguration(AcideConsoleMenu.CONSOLE_MENU_NAME);
		result.setErasable(false);
		result.setVisible(true);
		
		//CONFIGURE
		AcideMenuItemConfiguration configure = new AcideMenuItemConfiguration(AcideConsoleMenu.CONFIGURE_NAME);
		configure.setErasable(false);
		configure.setVisible(true);
		configure.setImage("./resources/icons/menu/configuration/console/configure.png");
		configure.setCommand("$CONFIGURE_CONSOLE");
		result.insertObject(configure);
		
		//EXTERNAL COMMAND
		AcideMenuItemConfiguration externalCommand = new AcideMenuItemConfiguration(AcideConsoleMenu.EXTERNAL_COMMAND_NAME);
		externalCommand.setErasable(false);
		externalCommand.setVisible(true);
		externalCommand.setImage("./resources/icons/menu/configuration/console/externalCommand.png");
		externalCommand.setCommand("$EXTERNAL_COMMAND");
		result.insertObject(externalCommand);
		
		//CONSOLE DISPLAY OPTIONS
		AcideMenuItemConfiguration consoleDisplayOptions = new AcideMenuItemConfiguration(AcideConsoleMenu.CONSOLE_DISPLAY_OPTIONS_NAME);
		consoleDisplayOptions.setErasable(false);
		consoleDisplayOptions.setVisible(true);
		consoleDisplayOptions.setImage("./resources/icons/menu/configuration/console/consoleDisplayOptions.png");
		consoleDisplayOptions.setCommand("$CONSOLE_DISPLAY_OPTIONS");
		result.insertObject(consoleDisplayOptions);
		
		//SAVE CONSOLE CONTENT
		AcideMenuItemConfiguration saveConsoleContent = new AcideMenuItemConfiguration(AcideConsoleMenu.SAVE_CONSOLE_CONTENT_INTO_FILE_NAME);
		saveConsoleContent.setErasable(false);
		saveConsoleContent.setVisible(true);
		saveConsoleContent.setImage("./resources/icons/menu/configuration/console/saveContentIntoFile.png");
		saveConsoleContent.setCommand("$SAVE_CONSOLE_CONTENT");
		result.insertObject(saveConsoleContent);
		
		//DOCUMENT CONSOLE
		AcideMenuItemConfiguration documentConsole = new AcideMenuItemConfiguration(AcideConsoleMenu.DOCUMENT_CONSOLE_LEXICON_NAME);
		documentConsole.setErasable(false);
		documentConsole.setVisible(true);
		documentConsole.setImage("./resources/icons/menu/configuration/console/documentLexicon.png");
		documentConsole.setCommand("$DOCUMENT_CONSOLE");
		result.insertObject(documentConsole);
		
		//SEARCH CONSOLE
		AcideMenuItemConfiguration searchConsole = new AcideMenuItemConfiguration(AcideConsoleMenu.SEARCH_CONSOLE_NAME);
		searchConsole.setErasable(false);
		searchConsole.setVisible(true);
		searchConsole.setImage("./resources/icons/menu/edit/search.png");
		searchConsole.setCommand("$SEARCH_CONSOLE");
		result.insertObject(searchConsole);
		
		//CLOSE CONSOLE
		AcideMenuItemConfiguration closeConsole = new AcideMenuItemConfiguration(AcideConsoleMenu.CLOSE_CONSOLE_NAME);
		closeConsole.setErasable(false);
		closeConsole.setVisible(true);
		closeConsole.setImage("./resources/icons/menu/configuration/console/closeConsole.png");
		closeConsole.setCommand("$CLOSE_CONSOLE");
		result.insertObject(closeConsole);
		
		return result;
	}

	/**
	 * Gets the default file editor menu for ACIDE - A Configurable IDE.
	 * @return
	 * 		the configuration for the default file editor menu.
	 */
	public AcideMenuSubmenuConfiguration getFileEditorDefaultSubmenu() {
		AcideMenuSubmenuConfiguration result = new AcideMenuSubmenuConfiguration(AcideFileEditorMenu.FILE_EDITOR_MENU_NAME);
		result.setErasable(false);
		result.setVisible(true);
	
		
		//PREFERENCES
		AcideMenuItemConfiguration preferences = new AcideMenuItemConfiguration(AcideFileEditorMenu.PREFERENCES_NAME);
		preferences.setErasable(false);
		preferences.setVisible(true);
		preferences.setImage("./resources/icons/menu/configuration/fileEditor/fileEditorDisplayOptions.png");
		preferences.setCommand("$PREFERENCES");
		result.insertObject(preferences);
		
		//FILE EDITOR DISPLAY OPTIONS
		AcideMenuItemConfiguration displayOptions = new AcideMenuItemConfiguration(AcideFileEditorMenu.FILE_EDITOR_DISPLAY_OPTIONS_NAME);
		displayOptions.setErasable(false);
		displayOptions.setVisible(true);
		displayOptions.setImage("./resources/icons/menu/configuration/fileEditor/fileEditorDisplayOptions.png");
		displayOptions.setCommand("$FILE_EDITOR_DISPLAY_OPTIONS");
		result.insertObject(displayOptions);
		
		//AUTOMATIC INDENT
		AcideMenuItemConfiguration automaticIndent = new AcideMenuItemConfiguration(AcideFileEditorMenu.AUTOMATIC_INDENT_NAME);
		automaticIndent.setErasable(false);
		automaticIndent.setVisible(true);
		automaticIndent.setImage("./resources/icons/menu/configuration/fileEditor/automaticIndent.png");
		automaticIndent.setCommand("$AUTOMATIC_INDENT");
		result.insertObject(automaticIndent);
		
		//LINE WRAPPING
		AcideMenuItemConfiguration lineWrapping = new AcideMenuItemConfiguration(AcideFileEditorMenu.LINE_WRAPPING_NAME);
		lineWrapping.setErasable(false);
		lineWrapping.setVisible(true);
		lineWrapping.setImage("./resources/icons/menu/configuration/fileEditor/lineWrapping.png");
		lineWrapping.setCommand("$LINE_WRAPPING");
		result.insertObject(lineWrapping);
		
		//MAXIMUM LINES TO CONSOLE
		AcideMenuItemConfiguration maximumLines = new AcideMenuItemConfiguration(AcideFileEditorMenu.MAXIMUM_LINES_TO_CONSOLE_NAME);
		maximumLines.setErasable(false);
		maximumLines.setVisible(true);
		maximumLines.setImage("./resources/icons/menu/configuration/fileEditor/maximumLinesToConsole.png");
		maximumLines.setCommand("$MAXIMUM_LINES");
		result.insertObject(maximumLines);
		
		//SEND TO CONSOLE CONFIRMATION
		AcideMenuItemConfiguration sendConsoleConfirmation = new AcideMenuItemConfiguration(AcideFileEditorMenu.SEND_TO_CONSOLE_CONFIRMATION_NAME);
		sendConsoleConfirmation.setErasable(false);
		sendConsoleConfirmation.setVisible(true);
		sendConsoleConfirmation.setImage("./resources/icons/menu/configuration/fileEditor/sendToConsoleConfirmation.png");
		sendConsoleConfirmation.setCommand("$SEND_CONSOLE_CONFIRMATION");
		result.insertObject(sendConsoleConfirmation);
		
		return result;
	}

	/**
	 * Gets the default grammar menu for ACIDE - A Configurable IDE.
	 * @return
	 * 		the configuration for the default grammar menu.
	 */
	public AcideMenuSubmenuConfiguration getGrammarDefaultSubmenu() {
		AcideMenuSubmenuConfiguration result = new AcideMenuSubmenuConfiguration(AcideGrammarMenu.GRAMMAR_MENU_NAME);
		result.setErasable(false);
		result.setVisible(true);
		
		//NEW GRAMMAR
		AcideMenuItemConfiguration newGrammar = new AcideMenuItemConfiguration(AcideGrammarMenu.NEW_GRAMMAR_NAME);
		newGrammar.setErasable(false);
		newGrammar.setVisible(true);
		newGrammar.setCommand("$NEW_GRAMMAR");
		result.insertObject(newGrammar);
		
		//LOAD GRAMMAR
		AcideMenuItemConfiguration loadGrammar = new AcideMenuItemConfiguration(AcideGrammarMenu.LOAD_GRAMMAR_NAME);
		loadGrammar.setErasable(false);
		loadGrammar.setVisible(true);
		loadGrammar.setCommand("$LOAD_GRAMMAR");
		result.insertObject(loadGrammar);
		
		//MODIFY GRAMMAR
		AcideMenuItemConfiguration modifyGrammar = new AcideMenuItemConfiguration(AcideGrammarMenu.MODIFY_GRAMMAR_NAME);
		modifyGrammar.setErasable(false);
		modifyGrammar.setVisible(true);
		modifyGrammar.setCommand("$MODIFY_GRAMMAR");
		result.insertObject(modifyGrammar);
		
		//SAVE GRAMMAR
		AcideMenuItemConfiguration saveGrammar = new AcideMenuItemConfiguration(AcideGrammarMenu.SAVE_GRAMMAR_NAME);
		saveGrammar.setErasable(false);
		saveGrammar.setVisible(true);
		saveGrammar.setCommand("$SAVE_GRAMMAR");
		result.insertObject(saveGrammar);
		
		//SAVE GRAMMAR AS
		AcideMenuItemConfiguration saveGrammarAs = new AcideMenuItemConfiguration(AcideGrammarMenu.SAVE_GRAMMAR_AS_NAME);
		saveGrammarAs.setErasable(false);
		saveGrammarAs.setVisible(true);
		saveGrammarAs.setCommand("$SAVE_GRAMMAR_AS");
		result.insertObject(saveGrammarAs);
		
		//SETH PATHS
		AcideMenuItemConfiguration setPaths = new AcideMenuItemConfiguration(AcideGrammarMenu.SET_PATHS_NAME);
		setPaths.setErasable(false);
		setPaths.setVisible(true);
		setPaths.setCommand("$SET_PATHS");
		result.insertObject(setPaths);
		
//		//AUTO ANALYSIS
//		AcideMenuItemConfiguration autoAnalysis = new AcideMenuItemConfiguration(AcideGrammarMenu.AUTO_ANALYSIS_NAME);
//		autoAnalysis.setErasable(false);
//		autoAnalysis.setVisible(true);
//      autoAnalysis.setCommand("$AUTO_ANALYSIS");
//		result.insertObject(autoAnalysis);
		
		return result;
	}

	/**
	 * Gets the default lexicon menu for ACIDE - A Configurable IDE.
	 * @return
	 * 		the configuration for the default lexicon menu.
	 */
	public AcideMenuSubmenuConfiguration getLexiconDefaultSubmenu() {
		AcideMenuSubmenuConfiguration result = new AcideMenuSubmenuConfiguration(AcideLexiconMenu.LEXICON_MENU_NAME);
		result.setErasable(false);
		result.setVisible(true);
		
		//NEW LEXICON
		AcideMenuItemConfiguration newLexicon = new AcideMenuItemConfiguration(AcideLexiconMenu.NEW_LEXICON_NAME);
		newLexicon.setErasable(false);
		newLexicon.setVisible(true);
		newLexicon.setCommand("$NEW_LEXICON");
		result.insertObject(newLexicon);
		
		//DOCUMENT LEXICON
		AcideMenuItemConfiguration documentLexicon = new AcideMenuItemConfiguration(AcideLexiconMenu.DOCUMENT_LEXICON_NAME);
		documentLexicon.setErasable(false);
		documentLexicon.setVisible(true);
		documentLexicon.setCommand("$DOCUMENT_LEXICON");
		result.insertObject(documentLexicon);
		
		//MODIFY LEXICON
		AcideMenuItemConfiguration modifyLexicon = new AcideMenuItemConfiguration(AcideLexiconMenu.MODIFY_LEXICON_NAME);
		modifyLexicon.setErasable(false);
		modifyLexicon.setVisible(true);
		modifyLexicon.setCommand("$MODIFY_LEXICON");
		result.insertObject(modifyLexicon);
		
		//DEFAULT LEXICONS
		AcideMenuItemConfiguration defaultLexicon = new AcideMenuItemConfiguration(AcideLexiconMenu.DEFAULT_LEXICONS_NAME);
		defaultLexicon.setErasable(false);
		defaultLexicon.setVisible(true);
		defaultLexicon.setCommand("$DEFAULT_LEXICON");
		result.insertObject(defaultLexicon);
		
		return result;
	}

	/**
	 * Gets the default help menu for ACIDE - A Configurable IDE.
	 * @return
	 * 		the configuration for the default help menu.
	 */
	public AcideMenuSubmenuConfiguration getHelpDefaultSubmenu() {
		AcideMenuSubmenuConfiguration result = new AcideMenuSubmenuConfiguration(AcideHelpMenu.HELP_MENU_NAME);
		result.setErasable(false);
		result.setVisible(true);
		
		//SHOW HELP
		AcideMenuItemConfiguration showHelp = new AcideMenuItemConfiguration(AcideHelpMenu.SHOW_HELP_NAME);
		showHelp.setErasable(false);
		showHelp.setVisible(true);
		showHelp.setImage("./resources/icons/menu/help/help.png");
		showHelp.setCommand("$SHOW_HELP");
		result.insertObject(showHelp);
		
		//SHOW ABOUT US
		AcideMenuItemConfiguration showAboutUs = new AcideMenuItemConfiguration(AcideHelpMenu.SHOW_ABOUT_US_NAME);
		showAboutUs.setErasable(false);
		showAboutUs.setVisible(true);
		showAboutUs.setImage("./resources/icons/menu/help/aboutUs.png");
		showAboutUs.setCommand("$SHOW_ABOUT_US");
		result.insertObject(showAboutUs);
		
		return result;
	}

	/**
	 * Sets the menu items configuration with only one instance of the menu object given as parameter
	 * @param fileMenuName
	 * 		the menu object to process
	 */
	public void onlyOne(String fileMenuName) {
		_itemsManager.onlyOne(fileMenuName);
	}

}
