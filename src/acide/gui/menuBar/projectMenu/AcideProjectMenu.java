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
package acide.gui.menuBar.projectMenu;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import javax.swing.ImageIcon;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JSeparator;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;

import acide.configuration.grammar.AcideGrammarConfiguration;
import acide.configuration.lexicon.AcideLexiconConfiguration;
import acide.configuration.menu.AcideInsertedItem;
import acide.configuration.menu.AcideInsertedItemListener;
import acide.configuration.menu.AcideInsertedMenu;
import acide.configuration.menu.AcideMenuConfiguration;
import acide.configuration.menu.AcideMenuItemConfiguration;
import acide.configuration.menu.AcideMenuItemsConfiguration;
import acide.configuration.menu.AcideMenuObjectConfiguration;
import acide.configuration.menu.AcideMenuSubmenuConfiguration;
import acide.configuration.project.AcideProjectConfiguration;
import acide.configuration.toolBar.AcideToolBarConfiguration;
import acide.configuration.workbench.AcideWorkbenchConfiguration;
import acide.files.AcideFileManager;
import acide.files.project.AcideProjectFile;
import acide.gui.fileEditor.fileEditorPanel.AcideFileEditorPanel;
import acide.gui.mainWindow.AcideMainWindow;
import acide.gui.menuBar.configurationMenu.menuMenu.gui.AcideMenuConfigurationWindow;
import acide.gui.menuBar.configurationMenu.toolBarMenu.gui.AcideToolBarConfigurationWindow;
import acide.gui.menuBar.listeners.AcideMenuBarMouseClickListener;
import acide.gui.menuBar.projectMenu.recentProjectsMenu.AcideRecentProjectsMenu;
import acide.language.AcideLanguageManager;
import acide.log.AcideLog;
import acide.resources.AcideResourceManager;
import acide.resources.exception.MissedPropertyException;
import acide.utils.IconsUtils;

/**
 * ACIDE - A Configurable IDE project menu.
 * 
 * @version 0.11
 * @see JMenu
 */
public class AcideProjectMenu extends JMenu {

	/**
	 * ACIDE - A Configurable IDE project menu class serial version UID.
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * ACIDE - A Configurable IDE project menu name.
	 */
	public final static String PROJECT_MENU_NAME = "Project";
	/**
	 * ACIDE - A Configurable IDE project menu new project menu item name.
	 */
	public static final String NEW_PROJECT_NAME = "New Project";
	/**
	 * ACIDE - A Configurable IDE project menu open project menu item name.
	 */
	public static final String OPEN_PROJECT_NAME = "Open Project";
	/**
	 * ACIDE - A Configurable IDE project menu open recent projects menu name.
	 */
	public static final String OPEN_RECENT_PROJECTS_NAME = "Open Recent Projects";
	/**
	 * ACIDE - A Configurable IDE project menu save project menu item name.
	 */
	public static final String SAVE_PROJECT_NAME = "Save Project";
	/**
	 * ACIDE - A Configurable IDE project menu save project as menu item image
	 * icon.
	 */
	public static final String SAVE_PROJECT_AS_NAME = "Save Project As";
	/**
	 * ACIDE - A Configurable IDE project menu add opened files menu item name.
	 */
	public static final String ADD_OPENED_FILES_NAME = "Add Opened Files";
	/**
	 * ACIDE - A Configurable IDE project menu new project file menu item name.
	 */
	public static final String NEW_PROJECT_FILE_NAME = "New Project File";
	/**
	 * ACIDE - A Configurable IDE project menu add file menu item name.
	 */
	public static final String ADD_FILE_NAME = "Add File";
	/**
	 * ACIDE - A Configurable IDE project menu add folder menu item name.
	 */
	public static final String ADD_FOLDER_NAME = "Add Folder";
	/**
	 * ACIDE - A Configurable IDE project menu remove folder menu item name.
	 */
	public static final String REMOVE_FOLDER_NAME = "Remove Folder";
	/**
	 * ACIDE - A Configurable IDE project menu remove file menu item name.
	 */
	public static final String REMOVE_FILE_NAME = "Remove File";
	/**
	 * ACIDE - A Configurable IDE project menu delete file menu item name.
	 */
	public static final String DELETE_FILE_NAME = "Delete File";
	/**
	 * ACIDE - A Configurable IDE project menu close project menu item name.
	 */
	public static final String CLOSE_PROJECT_NAME = "Close Project";
	/**
	 * ACIDE - A Configurable IDE project menu compile menu item name.
	 */
	public static final String COMPILE_NAME = "Compile";
	/**
	 * ACIDE - A Configurable IDE project menu execute menu item name.
	 */
	public static final String EXECUTE_NAME = "Execute";
	/**
	 * ACIDE - A Configurable IDE project menu set main file menu item name.
	 */
	public static final String SET_MAIN_FILE_NAME = "Set Main File";
	/**
	 * ACIDE - A Configurable IDE project menu unset main file menu item name.
	 */
	public static final String UNSET_MAIN_FILE_NAME = "Unset Main File";
	/**
	 * ACIDE - A Configurable IDE project menu set compilable file menu item
	 * name.
	 */
	public static final String SET_COMPILABLE_FILE_NAME = "Set Compilable File";
	/**
	 * ACIDE - A Configurable IDE project menu unset compilable file menu item
	 * name.
	 */
	public static final String UNSET_COMPILABLE_FILE_NAME = "Unset Compilable File";

	/**
	 * ACIDE - A Configurable IDE project menu add opened files menu item image.
	 */
	public static final ImageIcon ADD_OPENED_FILES_IMAGE = new ImageIcon(
			"./resources/icons/menu/project/addOpenedFiles.png");
	/**
	 * ACIDE - A Configurable IDE project menu new project menu item.
	 */
	private JMenuItem _newProjectMenuItem;
	/**
	 * ACIDE - A Configurable IDE project menu new project menu item has been inserted.
	 */
	private boolean _newProjectInserted;
	/**
	 * ACIDE - A Configurable IDE project menu open project menu item.
	 */
	private JMenuItem _openProjectMenuItem;
	/**
	 * ACIDE - A Configurable IDE project menu open project menu item has been inserted.
	 */
	private boolean _openProjectInserted;
	/**
	 * ACIDE - A Configurable IDE file menu open recent files menu.
	 */
	private AcideRecentProjectsMenu _openRecentProjectsMenu;
	/**
	 * ACIDE - A Configurable IDE project menu open recent projects menu item has been inserted.
	 */
	private boolean _openRecentProjectsInserted;
	/**
	 * ACIDE - A Configurable IDE project menu save project menu item.
	 */
	private JMenuItem _saveProjectMenuItem;
	/**
	 * ACIDE - A Configurable IDE project menu save project menu item has been inserted.
	 */
	private boolean _saveProjectInserted;
	/**
	 * ACIDE - A Configurable IDE project menu new project file menu item.
	 */
	private JMenuItem _newProjectFileMenuItem;
	/**
	 * ACIDE - A Configurable IDE project menu new project file menu item has been inserted.
	 */
	private boolean _newProjectFileInserted;
	/**
	 * ACIDE - A Configurable IDE project menu save as project menu item.
	 */
	private JMenuItem _saveProjectAsMenuItem;
	/**
	 * ACIDE - A Configurable IDE project menu save project as menu item has been inserted.
	 */
	private boolean _saveProjectAsInserted;
	/**
	 * ACIDE - A Configurable IDE project menu add opened files menu item.
	 */
	private JMenuItem _addOpenedFilesMenuItem;
	/**
	 * ACIDE - A Configurable IDE project menu add opened files menu item has been inserted.
	 */
	private boolean _addOpenedFilesInserted;
	/**
	 * ACIDE - A Configurable IDE project menu add file menu item.
	 */
	private JMenuItem _addFileMenuItem;
	/**
	 * ACIDE - A Configurable IDE project menu add file menu item has been inserted.
	 */
	private boolean _addFileInserted;
	/**
	 * ACIDE - A Configurable IDE project menu close project menu item.
	 */
	private JMenuItem _closeProjectMenuItem;
	/**
	 * ACIDE - A Configurable IDE project menu close project menu item has been inserted.
	 */
	private boolean _closeProjectInserted;
	/**
	 * ACIDE - A Configurable IDE project menu remove file menu item.
	 */
	private JMenuItem _removeFileMenuItem;
	/**
	 * ACIDE - A Configurable IDE project menu remove file menu item has been inserted.
	 */
	private boolean _removeFileInserted;
	/**
	 * ACIDE - A Configurable IDE project menu delete file menu item.
	 */
	private JMenuItem _deleteFileMenuItem;
	/**
	 * ACIDE - A Configurable IDE project menu delete file menu item has been inserted.
	 */
	private boolean _deleteFileInserted;
	/**
	 * ACIDE - A Configurable IDE project menu add folder menu item.
	 */
	private JMenuItem _addFolderMenuItem;
	/**
	 * ACIDE - A Configurable IDE project menu add folder menu item has been inserted.
	 */
	private boolean _addFolderInserted;
	/**
	 * ACIDE - A Configurable IDE project menu remove folder menu item.
	 */
	private JMenuItem _removeFolderMenuItem;
	/**
	 * ACIDE - A Configurable IDE project menu remove folder menu item has been inserted.
	 */
	private boolean _removeFolderInserted;
	/**
	 * ACIDE - A Configurable IDE project menu set compilable menu item.
	 */
	private JMenuItem _setCompilableFileMenuItem;
	/**
	 * ACIDE - A Configurable IDE project menu set compilable menu item has been inserted.
	 */
	private boolean _setCompilableInserted;
	/**
	 * ACIDE - A Configurable IDE project menu unset compilable menu item.
	 */
	private JMenuItem _unsetCompilableFileMenuItem;
	/**
	 * ACIDE - A Configurable IDE project menu unset compilable menu item has been inserted.
	 */
	private boolean _unsetCompilableInserted;
	/**
	 * ACIDE - A Configurable IDE project menu set main menu item.
	 */
	private JMenuItem _setMainFileMenuItem;
	/**
	 * ACIDE - A Configurable IDE project menu set main file menu item has been inserted.
	 */
	private boolean _setMainFileInserted;
	/**
	 * ACIDE - A Configurable IDE project menu unset main menu item.
	 */
	private JMenuItem _unsetMainFileMenuItem;
	/**
	 * ACIDE - A Configurable IDE project menu unset main file menu item has been inserted.
	 */
	private boolean _unsetMainFileInserted;
	/**
	 * ACIDE - A Configurable IDE project menu compile menu item.
	 */
	private JMenuItem _compileMenuItem;
	/**
	 * ACIDE - A Configurable IDE project menu compile menu item has been inserted.
	 */
	private boolean _compileInserted;
	/**
	 * ACIDE - A Configurable IDE project menu execute menu item.
	 */
	private JMenuItem _executeMenuItem;
	/**
	 * ACIDE - A Configurable IDE project menu execute menu item has been inserted.
	 */
	private boolean _executeInserted;
	/**
	 * ACIDE - A Configurable IDE project menu close project save project
	 * separator.
	 */
	private JSeparator _closeProjectSaveProjectSeparator;
	/**
	 * ACIDE - A Configurable IDE project menu save project as new project file
	 * separator.
	 */
	private JSeparator _saveProjectAsNewProjectFileSeparator;
	/**
	 * ACIDE - A Configurable IDE project menu remove folder compile separator.
	 */
	private JSeparator _removeFolderCompileSeparator;
	/**
	 * ACIDE - A Configurable IDE project menu execute set compilable file
	 * separator.
	 */
	private JSeparator _executeSetCompilableFileSeparator;
	/**
	 * ACIDE - A Configurable IDE project menu configuration menu.
	 */
	private AcideMenuSubmenuConfiguration _projectSubmenuConfiguration;
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
	 * Creates a new ACIDE - A Configurable IDE project menu.
	 */
	public AcideProjectMenu() {
		
		_newProjectInserted = false;
		_openProjectInserted = false;
		_openRecentProjectsInserted = false;
		_saveProjectInserted = false;
		_newProjectFileInserted = false;
		_saveProjectAsInserted = false;
		_addOpenedFilesInserted = false;
		_addFileInserted = false;
		_closeProjectInserted = false;
		_removeFileInserted = false;
		_deleteFileInserted = false;
		_addFolderInserted = false;
		_removeFolderInserted = false;
		_setCompilableInserted = false;
		_unsetCompilableInserted = false;
		_setMainFileInserted = false;
		_unsetMainFileInserted = false;
		_compileInserted = false;
		_executeInserted = false;
		
		_insertedItems = new HashMap<String, AcideInsertedItem>();
		
		_insertedMenus = new HashMap<String, AcideInsertedMenu>();
		
		_insertedObjects = new ArrayList<AcideMenuObjectConfiguration>();

		// Builds the menu components
		buildComponents();

		// Adds the components to the menu
		addComponents();

		// Sets the text of the project menu components
		setTextOfMenuComponents();
	}

	/**
	 * Adds the components the ACIDE - A Configurable IDE project menu.
	 */
	private void addComponents() {
		
		Iterator<Object> it = AcideMenuItemsConfiguration.getInstance()
				.getMenuItemsManager().getSubmenu(PROJECT_MENU_NAME).getItemsManager().managerIterator();
		while (it.hasNext()){
			AcideMenuObjectConfiguration ob = (AcideMenuObjectConfiguration) it.next();
			String name = ob.getName();
			if (name.equals(NEW_PROJECT_NAME)){
				// Adds the new project menu item to the menu
				add(_newProjectMenuItem);
				_newProjectInserted = true;
			}else if (name.equals(OPEN_PROJECT_NAME)){
				// Adds the open project menu item to the menu
				add(_openProjectMenuItem);
				_openProjectInserted = true;
			}else if (name.equals(OPEN_RECENT_PROJECTS_NAME)){
				// Adds the open recent projects menu item to the file menu
				add(_openRecentProjectsMenu);
				_openRecentProjectsInserted = true;
			}else if (name.equals(CLOSE_PROJECT_NAME)){
				// Adds the close project menu item to the menu
				add(_closeProjectMenuItem);
				_closeProjectInserted = true;
			}else if (name.equals(SAVE_PROJECT_NAME)){
				// Adds the save project menu item to the menu
				add(_saveProjectMenuItem);
				_saveProjectInserted = true;
			}else if (name.equals(SAVE_PROJECT_AS_NAME)){
				// Adds the save project as menu item to the menu
				add(_saveProjectAsMenuItem);
				_saveProjectAsInserted = true;
				// Adds the save project as new project file separator
				add(_saveProjectAsNewProjectFileSeparator);
			}else if (name.equals(ADD_OPENED_FILES_NAME)){
				// Adds the add opened files menu item
				add(_addOpenedFilesMenuItem);
				_addOpenedFilesInserted = true;
			}else if (name.equals(NEW_PROJECT_FILE_NAME)){
				// Adds the new project file menu item to the menu
				add(_newProjectFileMenuItem);
				_newProjectFileInserted = true;
			}else if (name.equals(ADD_FILE_NAME)){
				// Adds the add file menu item to the menu
				add(_addFileMenuItem);
				_addFileInserted = true;
			}else if (name.equals(REMOVE_FILE_NAME)){
				// Adds the remove file menu item to the menu
				add(_removeFileMenuItem);
				_removeFileInserted = true;
			}else if (name.equals(DELETE_FILE_NAME)){
				// Adds the delete file menu item to the menu
				add(_deleteFileMenuItem);
				_deleteFileInserted = true;
			}else if (name.equals(ADD_FOLDER_NAME)){
				// Adds the add folder menu item to the menu
				add(_addFolderMenuItem);
				_addFolderInserted = true;
			}else if (name.equals(REMOVE_FOLDER_NAME)){
				// Adds the remove folder menu item to the menu
				add(_removeFolderMenuItem);
				_removeFolderInserted = true;
				// Adds the remove folder compile separator to the menu
				add(_removeFolderCompileSeparator);
			}else if (name.equals(COMPILE_NAME)){
				// Adds the compile menu item to the menu
				add(_compileMenuItem);
				_compileInserted = true;
			}else if (name.equals(EXECUTE_NAME)){
				// Adds the execute menu item to the menu
				add(_executeMenuItem);
				_executeInserted = true;
				// Adds the execute set compilable file separator to the menu
				add(_executeSetCompilableFileSeparator);
			}else if (name.equals(SET_COMPILABLE_FILE_NAME)){
				// Adds the set compilable file menu item to the menu
				add(_setCompilableFileMenuItem);
				_setCompilableInserted = true;
			}else if (name.equals(UNSET_COMPILABLE_FILE_NAME)){
				// Adds the unset compilable file menu item to the menu
				add(_unsetCompilableFileMenuItem);
				_unsetCompilableInserted = true;
			}else if (name.equals(SET_MAIN_FILE_NAME)){
				// Adds the set main file menu item to the menu
				add(_setMainFileMenuItem);
				_setMainFileInserted = true;
			}else if (name.equals(UNSET_MAIN_FILE_NAME)){
				// Adds the unset main file menu item to the menu
				add(_unsetMainFileMenuItem);
				_unsetMainFileInserted = true;
			}else {
				if (ob.isSubmenu()){
					add(_insertedMenus.get(ob.getName()));
				}else{
					add(_insertedItems.get(ob.getName()));
				}
			}
		}
		
		if (!_newProjectInserted)
			add(_newProjectMenuItem);
		if (!_openProjectInserted)
			add(_openProjectMenuItem);
		if(!_openRecentProjectsInserted)
			add(_openRecentProjectsMenu);
		if (!_closeProjectInserted)
			add(_closeProjectMenuItem);
		if (!_saveProjectInserted)
			add(_saveProjectMenuItem);
		if (!_saveProjectAsInserted)
			add(_saveProjectAsMenuItem);
		if (!_addOpenedFilesInserted)
			add(_addOpenedFilesMenuItem);
		if (!_newProjectFileInserted)
			add(_newProjectFileMenuItem);
		if (!_addFileInserted)
			add(_addFileMenuItem);
		if (!_removeFileInserted)
			add(_removeFileMenuItem);
		if (!_deleteFileInserted)
			add(_deleteFileMenuItem);
		if(!_addFolderInserted)
			add(_addFolderMenuItem);
		if (!_removeFolderInserted)
			add(_removeFolderMenuItem);
		if (!_compileInserted)
			add(_compileMenuItem);
		if (!_executeInserted)
			add(_executeMenuItem);
		if (!_setCompilableInserted)
			add(_setCompilableFileMenuItem);
		if (!_unsetCompilableInserted)
			add(_unsetCompilableFileMenuItem);
		if (!_setMainFileInserted)
			add(_setMainFileMenuItem);
		if (!_unsetMainFileInserted)
			add(_unsetMainFileMenuItem);
		
	}

	/**
	 * Builds the ACIDE - A Configurable IDE project menu components.
	 */
	private void buildComponents() {
		
		if (!AcideMenuItemsConfiguration.getInstance().getMenuItemsManager().hasSubmenu(PROJECT_MENU_NAME)){
			AcideMenuItemsConfiguration.getInstance()
				.insertObject(new AcideMenuSubmenuConfiguration(PROJECT_MENU_NAME));
		}
		
		Iterator<Object> it = AcideMenuItemsConfiguration.getInstance()
				.getMenuItemsManager().getSubmenu(PROJECT_MENU_NAME).getItemsManager().managerIterator();
		
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

		// Creates the new project menu item
		ImageIcon icon = IconsUtils.getIcon(AcideMenuItemsConfiguration.getInstance()
				.getMenuItemsManager().getSubmenu(PROJECT_MENU_NAME)
				.getItem(NEW_PROJECT_NAME).getImage());
		
		if (icon != null)
			_newProjectMenuItem = new JMenuItem(icon);
		else
			_newProjectMenuItem = new JMenuItem();

		// Sets the new project menu item name
		_newProjectMenuItem.setName(NEW_PROJECT_NAME);

		// Creates the open project menu item
		icon = IconsUtils.getIcon(AcideMenuItemsConfiguration.getInstance()
				.getMenuItemsManager().getSubmenu(PROJECT_MENU_NAME)
				.getItem(OPEN_PROJECT_NAME).getImage());
		
		if (icon != null)
			_openProjectMenuItem = new JMenuItem(icon);
		else
			_openProjectMenuItem = new JMenuItem();

		// Sets the open project menu item name
		_openProjectMenuItem.setName(OPEN_PROJECT_NAME);

		// Creates the open recent projects menu
		_openRecentProjectsMenu = new AcideRecentProjectsMenu();

		// Sets the open recent projects menu name
		_openRecentProjectsMenu.setName(OPEN_RECENT_PROJECTS_NAME);

		// Creates the close project save project separator
		_closeProjectSaveProjectSeparator = new JSeparator();

		// Creates the close project menu item
		icon = IconsUtils.getIcon(AcideMenuItemsConfiguration.getInstance()
				.getMenuItemsManager().getSubmenu(PROJECT_MENU_NAME)
				.getItem(CLOSE_PROJECT_NAME).getImage());
		
		if (icon != null)
			_closeProjectMenuItem = new JMenuItem(icon);
		else
			_closeProjectMenuItem = new JMenuItem();

		// Sets the close project menu item name
		_closeProjectMenuItem.setName(CLOSE_PROJECT_NAME);

		// Creates the save project menu item
		icon = IconsUtils.getIcon(AcideMenuItemsConfiguration.getInstance()
				.getMenuItemsManager().getSubmenu(PROJECT_MENU_NAME)
				.getItem(SAVE_PROJECT_NAME).getImage());
		
		if (icon != null)
			_saveProjectMenuItem = new JMenuItem(icon);
		else
			_saveProjectMenuItem = new JMenuItem();

		// Sets the save project menu item name
		_saveProjectMenuItem.setName(SAVE_PROJECT_NAME);

		// Creates the save project as menu item
		icon = IconsUtils.getIcon(AcideMenuItemsConfiguration.getInstance()
				.getMenuItemsManager().getSubmenu(PROJECT_MENU_NAME)
				.getItem(SAVE_PROJECT_AS_NAME).getImage());
		
		if (icon != null)
			_saveProjectAsMenuItem = new JMenuItem(icon);
		else
			_saveProjectAsMenuItem = new JMenuItem();

		// Sets the save project as menu item name
		_saveProjectAsMenuItem.setName(SAVE_PROJECT_AS_NAME);

		// Creates the save project as new project file separator
		_saveProjectAsNewProjectFileSeparator = new JSeparator();

		// Creates the add opened files menu item
		icon = IconsUtils.getIcon(AcideMenuItemsConfiguration.getInstance()
				.getMenuItemsManager().getSubmenu(PROJECT_MENU_NAME)
				.getItem(ADD_OPENED_FILES_NAME).getImage());
		
		if (icon != null)
			_addOpenedFilesMenuItem = new JMenuItem(icon);
		else
			_addOpenedFilesMenuItem = new JMenuItem();

		// Sets the add opened files menu item name
		_addOpenedFilesMenuItem.setName(ADD_OPENED_FILES_NAME);

		// Creates the new project file menu item
		icon = IconsUtils.getIcon(AcideMenuItemsConfiguration.getInstance()
				.getMenuItemsManager().getSubmenu(PROJECT_MENU_NAME)
				.getItem(NEW_PROJECT_FILE_NAME).getImage());
		
		if (icon != null)
			_newProjectFileMenuItem = new JMenuItem(icon);
		else
			_newProjectFileMenuItem = new JMenuItem();

		// Sets the new project file menu item name
		_newProjectFileMenuItem.setName(NEW_PROJECT_FILE_NAME);

		// Creates the add file menu item
		icon = IconsUtils.getIcon(AcideMenuItemsConfiguration.getInstance()
				.getMenuItemsManager().getSubmenu(PROJECT_MENU_NAME)
				.getItem(ADD_FILE_NAME).getImage());
		
		if (icon != null)
			_addFileMenuItem = new JMenuItem(icon);
		else
			_addFileMenuItem = new JMenuItem();

		// Sets the add file menu item name
		_addFileMenuItem.setName(ADD_FILE_NAME);

		// Creates the remove file menu item
		icon = IconsUtils.getIcon(AcideMenuItemsConfiguration.getInstance()
				.getMenuItemsManager().getSubmenu(PROJECT_MENU_NAME)
				.getItem(REMOVE_FILE_NAME).getImage());
		
		if (icon != null)
			_removeFileMenuItem = new JMenuItem(icon);
		else
			_removeFileMenuItem = new JMenuItem();

		// Sets the remove file menu item name
		_removeFileMenuItem.setName(REMOVE_FILE_NAME);

		// Creates the delete file menu item
		icon = IconsUtils.getIcon(AcideMenuItemsConfiguration.getInstance()
				.getMenuItemsManager().getSubmenu(PROJECT_MENU_NAME)
				.getItem(DELETE_FILE_NAME).getImage());
		
		if (icon != null)
			_deleteFileMenuItem = new JMenuItem(icon);
		else
			_deleteFileMenuItem = new JMenuItem();

		// Sets the delete file menu item name
		_deleteFileMenuItem.setName(DELETE_FILE_NAME);

		// Creates the add folder menu item
		icon = IconsUtils.getIcon(AcideMenuItemsConfiguration.getInstance()
				.getMenuItemsManager().getSubmenu(PROJECT_MENU_NAME)
				.getItem(ADD_FOLDER_NAME).getImage());
		
		if (icon != null)
			_addFolderMenuItem = new JMenuItem(icon);
		else
			_addFolderMenuItem = new JMenuItem();

		// Sets the add folder menu item name
		_addFolderMenuItem.setName(ADD_FOLDER_NAME);

		// Creates the remove folder menu item
		icon = IconsUtils.getIcon(AcideMenuItemsConfiguration.getInstance()
				.getMenuItemsManager().getSubmenu(PROJECT_MENU_NAME)
				.getItem(REMOVE_FOLDER_NAME).getImage());
		
		if (icon != null)
			_removeFolderMenuItem = new JMenuItem(icon);
		else
			_removeFolderMenuItem = new JMenuItem();

		// Sets the remove folder menu item name
		_removeFolderMenuItem.setName(REMOVE_FOLDER_NAME);

		// Creates the remove folder compile separator
		_removeFolderCompileSeparator = new JSeparator();

		// Creates the compile menu item
		icon = IconsUtils.getIcon(AcideMenuItemsConfiguration.getInstance()
				.getMenuItemsManager().getSubmenu(PROJECT_MENU_NAME)
				.getItem(COMPILE_NAME).getImage());
		
		if (icon != null)
			_compileMenuItem = new JMenuItem(icon);
		else
			_compileMenuItem = new JMenuItem();

		// Sets the compile menu item name
		_compileMenuItem.setName(COMPILE_NAME);

		// Creates the execute menu item
		icon = IconsUtils.getIcon(AcideMenuItemsConfiguration.getInstance()
				.getMenuItemsManager().getSubmenu(PROJECT_MENU_NAME)
				.getItem(EXECUTE_NAME).getImage());
		
		if (icon != null)
			_executeMenuItem = new JMenuItem(icon);
		else
			_executeMenuItem = new JMenuItem();
		
		_executeMenuItem = new JMenuItem();

		// Sets the execute menu item name
		_executeMenuItem.setName(EXECUTE_NAME);

		// Creates the execute set compilable file separator
		_executeSetCompilableFileSeparator = new JSeparator();

		// Creates the set compilable file menu item
		icon = IconsUtils.getIcon(AcideMenuItemsConfiguration.getInstance()
				.getMenuItemsManager().getSubmenu(PROJECT_MENU_NAME)
				.getItem(SET_COMPILABLE_FILE_NAME).getImage());
		
		if (icon != null)
			_setCompilableFileMenuItem = new JMenuItem(icon);
		else
			_setCompilableFileMenuItem = new JMenuItem();

		// Sets the set compilable file menu item name
		_setCompilableFileMenuItem.setName(SET_COMPILABLE_FILE_NAME);

		// Creates the unset compilable file menu item
		icon = IconsUtils.getIcon(AcideMenuItemsConfiguration.getInstance()
				.getMenuItemsManager().getSubmenu(PROJECT_MENU_NAME)
				.getItem(UNSET_COMPILABLE_FILE_NAME).getImage());
		
		if (icon != null)
			_unsetCompilableFileMenuItem = new JMenuItem(icon);
		else
			_unsetCompilableFileMenuItem = new JMenuItem();

		// Sets the unset compilable file menu item name
		_unsetCompilableFileMenuItem.setName(UNSET_COMPILABLE_FILE_NAME);

		// Creates the set main file menu item
		icon = IconsUtils.getIcon(AcideMenuItemsConfiguration.getInstance()
				.getMenuItemsManager().getSubmenu(PROJECT_MENU_NAME)
				.getItem(SET_MAIN_FILE_NAME).getImage());
		
		if (icon != null)
			_setMainFileMenuItem = new JMenuItem(icon);
		else
			_setMainFileMenuItem = new JMenuItem();

		// Sets the set main file menu item name
		_setMainFileMenuItem.setName(SET_MAIN_FILE_NAME);

		// Creates the unset main file menu item
		icon = IconsUtils.getIcon(AcideMenuItemsConfiguration.getInstance()
				.getMenuItemsManager().getSubmenu(PROJECT_MENU_NAME)
				.getItem(UNSET_MAIN_FILE_NAME).getImage());
		
		if (icon != null)
			_unsetMainFileMenuItem = new JMenuItem(icon);
		else
			_unsetMainFileMenuItem = new JMenuItem();

		// Sets the unset main file menu item name
		_unsetMainFileMenuItem.setName(UNSET_MAIN_FILE_NAME);
		
		
	}

	/**
	 * Sets the text of the ACIDE - A Configurable IDE project menu components
	 * with the labels in the selected language to display.
	 */
	public void setTextOfMenuComponents() {

		// Disables the menu
		disableMenu();

		// Sets the new project menu item text
		_newProjectMenuItem.setText(AcideLanguageManager.getInstance()
				.getLabels().getString("s14"));

		// Sets the new project menu item accelerator
		_newProjectMenuItem.setAccelerator(KeyStroke.getKeyStroke(
				KeyEvent.VK_N, ActionEvent.ALT_MASK + ActionEvent.SHIFT_MASK));

		// Sets the open project menu item text
		_openProjectMenuItem.setText(AcideLanguageManager.getInstance()
				.getLabels().getString("s15"));

		// Sets the open project menu item accelerator
		_openProjectMenuItem.setAccelerator(KeyStroke.getKeyStroke(
				KeyEvent.VK_O, ActionEvent.ALT_MASK + ActionEvent.SHIFT_MASK));

		// Sets the open recent projects menu text
		_openRecentProjectsMenu.setText(AcideLanguageManager.getInstance()
				.getLabels().getString("s1078"));

		// Sets the save project menu item text
		_saveProjectMenuItem.setText(AcideLanguageManager.getInstance()
				.getLabels().getString("s16"));

		// Sets the save project menu item accelerator
		_saveProjectMenuItem.setAccelerator(KeyStroke.getKeyStroke(
				KeyEvent.VK_S, ActionEvent.ALT_MASK + ActionEvent.SHIFT_MASK));

		// Sets the add opened files menu item text
		_addOpenedFilesMenuItem.setText(AcideLanguageManager.getInstance()
				.getLabels().getString("s1094"));

		// Sets the new project file menu item text
		_newProjectFileMenuItem.setText(AcideLanguageManager.getInstance()
				.getLabels().getString("s947"));

		// Sets the add file menu item text
		_addFileMenuItem.setText(AcideLanguageManager.getInstance().getLabels()
				.getString("s17"));

		// Sets the add file menu item accelerator
		_addFileMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A,
				ActionEvent.ALT_MASK + ActionEvent.SHIFT_MASK));

		// Sets the remove file menu item text
		_removeFileMenuItem.setText(AcideLanguageManager.getInstance()
				.getLabels().getString("s218"));

		// Sets the delete file menu item text
		_deleteFileMenuItem.setText(AcideLanguageManager.getInstance()
				.getLabels().getString("s950"));

		// Sets the add folder menu item text
		_addFolderMenuItem.setText(AcideLanguageManager.getInstance()
				.getLabels().getString("s219"));

		// Sets the remove folder menu item text
		_removeFolderMenuItem.setText(AcideLanguageManager.getInstance()
				.getLabels().getString("s220"));

		// Sets the save project as menu item text
		_saveProjectAsMenuItem.setText(AcideLanguageManager.getInstance()
				.getLabels().getString("s926"));

		// Sets the close project menu item text
		_closeProjectMenuItem.setText(AcideLanguageManager.getInstance()
				.getLabels().getString("s228"));

		// Sets the set compilable file menu item text
		_setCompilableFileMenuItem.setText(AcideLanguageManager.getInstance()
				.getLabels().getString("s254"));

		// Sets the unset compilable file menu item text
		_unsetCompilableFileMenuItem.setText(AcideLanguageManager.getInstance()
				.getLabels().getString("s255"));

		// Sets the set main file menu item text
		_setMainFileMenuItem.setText(AcideLanguageManager.getInstance()
				.getLabels().getString("s256"));

		// Sets the unset main file menu item text
		_unsetMainFileMenuItem.setText(AcideLanguageManager.getInstance()
				.getLabels().getString("s952"));

		// Sets the compile menu item text
		_compileMenuItem.setText(AcideLanguageManager.getInstance().getLabels()
				.getString("s18"));

		// Sets the compile menu item accelerator
		_compileMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C,
				ActionEvent.ALT_MASK));

		// Sets the execute menu item text
		_executeMenuItem.setText(AcideLanguageManager.getInstance().getLabels()
				.getString("s19"));

		// Sets the execute menu item accelerator
		_executeMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E,
				ActionEvent.ALT_MASK));
		
		
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
	 * Updates the ACIDE - A Configurable IDE project menu components visibility
	 * with the menu configuration.
	 */
	public void updateComponentsVisibility() {

		AcideMenuItemConfiguration newProjectConfiguration;
		AcideMenuItemConfiguration openProjectConfiguration;
		AcideMenuSubmenuConfiguration openRecentProjectsConfiguration;
		AcideMenuItemConfiguration closeProjectConfiguration;
		AcideMenuItemConfiguration saveProjectConfiguration;
		AcideMenuItemConfiguration saveProjectAsConfiguration;
		AcideMenuItemConfiguration addOpenedFilesConfiguration;
		AcideMenuItemConfiguration newProjectFileConfiguration;
		AcideMenuItemConfiguration addFileConfiguration;
		AcideMenuItemConfiguration removeFileConfiguration;
		AcideMenuItemConfiguration deleteFileConfiguration;
		AcideMenuItemConfiguration addFolderConfiguration;
		AcideMenuItemConfiguration removeFolderConfiguration;
		AcideMenuItemConfiguration compileConfiguration;
		AcideMenuItemConfiguration executeConfiguration;
		AcideMenuItemConfiguration setCompilableConfiguration;
		AcideMenuItemConfiguration unsetCompilableConfiguration;
		AcideMenuItemConfiguration setMainFileConfiguration;
		AcideMenuItemConfiguration unsetMainFileConfiguration;
		
		_projectSubmenuConfiguration = AcideMenuItemsConfiguration.getInstance().getSubmenu(PROJECT_MENU_NAME);
		

		// Sets the new project menu item as visible or not visible
		newProjectConfiguration = _projectSubmenuConfiguration.getItem(NEW_PROJECT_NAME);
		_newProjectMenuItem.setVisible(newProjectConfiguration.isVisible());

		// Sets the open project menu item as visible or not visible
		openProjectConfiguration = _projectSubmenuConfiguration.getItem(OPEN_PROJECT_NAME);
		_openProjectMenuItem.setVisible(openProjectConfiguration.isVisible());

		// Sets the open recent projects menu as visible or not visible
		openRecentProjectsConfiguration = _projectSubmenuConfiguration.getSubmenu(OPEN_RECENT_PROJECTS_NAME);
		_openRecentProjectsMenu.setVisible(openRecentProjectsConfiguration.isVisible());

		// Sets the close project menu item as visible or not visible
		closeProjectConfiguration = _projectSubmenuConfiguration.getItem(CLOSE_PROJECT_NAME);
		_closeProjectMenuItem.setVisible(closeProjectConfiguration.isVisible());

		// Sets the save project menu item as visible or not visible
		saveProjectConfiguration = _projectSubmenuConfiguration.getItem(SAVE_PROJECT_NAME);
		_saveProjectMenuItem.setVisible(saveProjectConfiguration.isVisible());

		// Sets the save project as menu item as visible or not visible
		saveProjectAsConfiguration = _projectSubmenuConfiguration.getItem(SAVE_PROJECT_AS_NAME);
		_saveProjectAsMenuItem.setVisible(saveProjectAsConfiguration.isVisible());

		// Sets the close project save project separator to visible or not
		// visible
		_closeProjectSaveProjectSeparator.setVisible((_openProjectMenuItem.isVisible()
				|| _newProjectMenuItem.isVisible()
				|| _openRecentProjectsMenu.isVisible() || _closeProjectMenuItem.isVisible())
				&& (_saveProjectMenuItem.isVisible() || _saveProjectAsMenuItem.isVisible()));
			
		// Sets the add opened files menu item as visible or not visible
		addOpenedFilesConfiguration = _projectSubmenuConfiguration.getItem(ADD_OPENED_FILES_NAME);
		_addOpenedFilesMenuItem.setVisible(addOpenedFilesConfiguration.isVisible());

		// Sets the new project file menu item as visible or not visible
		newProjectFileConfiguration = _projectSubmenuConfiguration.getItem(NEW_PROJECT_FILE_NAME);
		_newProjectFileMenuItem.setVisible(newProjectFileConfiguration.isVisible());

		// Sets the add file menu item as visible or not visible
		addFileConfiguration = _projectSubmenuConfiguration.getItem(ADD_FILE_NAME);
		_addFileMenuItem.setVisible(addFileConfiguration.isVisible());

		// Sets the remove file menu item as visible or not visible
		removeFileConfiguration = _projectSubmenuConfiguration.getItem(REMOVE_FILE_NAME);
		_removeFileMenuItem.setVisible(removeFileConfiguration.isVisible());

		// Sets the delete file menu item as visible or not visible
		deleteFileConfiguration = _projectSubmenuConfiguration.getItem(DELETE_FILE_NAME);
		_deleteFileMenuItem.setVisible(deleteFileConfiguration.isVisible());

		// Sets the add folder menu item as visible or not visible
		addFolderConfiguration = _projectSubmenuConfiguration.getItem(ADD_FOLDER_NAME);
		_addFolderMenuItem.setVisible(addFolderConfiguration.isVisible());

		// Sets the remove folder menu item as visible or not visible
		removeFolderConfiguration = _projectSubmenuConfiguration.getItem(REMOVE_FOLDER_NAME);
		_removeFolderMenuItem.setVisible(removeFolderConfiguration.isVisible());

		// Sets the save project as new project file separator as visible or not
		// visible
		_saveProjectAsNewProjectFileSeparator
			.setVisible((_openProjectMenuItem.isVisible()
				|| _newProjectMenuItem.isVisible()
				|| _openRecentProjectsMenu.isVisible()
				|| _saveProjectMenuItem.isVisible()
				|| _closeProjectMenuItem.isVisible() || _saveProjectAsMenuItem.isVisible())
				&& (_addFileMenuItem.isVisible()
						|| _removeFileMenuItem.isVisible()
						|| _addFolderMenuItem.isVisible()
						|| _removeFolderMenuItem.isVisible()
						|| _deleteFileMenuItem.isVisible()
						|| _newProjectFileMenuItem.isVisible() || _addOpenedFilesMenuItem.isVisible()));
			
		// Sets the compile menu item as visible or not visible
		compileConfiguration = _projectSubmenuConfiguration.getItem(COMPILE_NAME);
		_compileMenuItem.setVisible(compileConfiguration.isVisible());

		// Sets the execute menu item as visible or not visible
		executeConfiguration = _projectSubmenuConfiguration.getItem(EXECUTE_NAME);
		_executeMenuItem.setVisible(executeConfiguration.isVisible());

		// Sets the remove folder compile separator to visible or not visible
		_removeFolderCompileSeparator
		.setVisible((_openProjectMenuItem.isVisible()
				|| _newProjectMenuItem.isVisible()
				|| _openRecentProjectsMenu.isVisible()
				|| _saveProjectMenuItem.isVisible()
				|| _saveProjectAsMenuItem.isVisible()
				|| _addFileMenuItem.isVisible()
				|| _removeFileMenuItem.isVisible()
				|| _addFolderMenuItem.isVisible()
				|| _removeFolderMenuItem.isVisible()
				|| _deleteFileMenuItem.isVisible()
				|| _newProjectFileMenuItem.isVisible() || _addOpenedFilesMenuItem.isVisible())
				&& (_compileMenuItem.isVisible()
						|| _executeMenuItem.isVisible()));

		// Sets the set compilable file menu item as visible or not visible
		setCompilableConfiguration = _projectSubmenuConfiguration.getItem(SET_COMPILABLE_FILE_NAME);
		_setCompilableFileMenuItem.setVisible(setCompilableConfiguration.isVisible());

		// Sets the unset compilable file menu item as visible or not visible
		unsetCompilableConfiguration = _projectSubmenuConfiguration.getItem(UNSET_COMPILABLE_FILE_NAME);
		_unsetCompilableFileMenuItem.setVisible(unsetCompilableConfiguration.isVisible());

		// Sets the set main file menu item as visible or not visible
		setMainFileConfiguration = _projectSubmenuConfiguration.getItem(SET_MAIN_FILE_NAME);
		_setMainFileMenuItem.setVisible(setMainFileConfiguration.isVisible());

		// Sets the unset main file menu item as visible or not visible
		unsetMainFileConfiguration = _projectSubmenuConfiguration.getItem(UNSET_MAIN_FILE_NAME);
		_unsetMainFileMenuItem.setVisible(unsetMainFileConfiguration.isVisible());

		// Sets the execute set compilable file separator to visible or not
		// visible
		_executeSetCompilableFileSeparator
		.setVisible((_openProjectMenuItem.isVisible()
				|| _newProjectMenuItem.isVisible()
				|| _openRecentProjectsMenu.isVisible()
				|| _saveProjectMenuItem.isVisible()
				|| _saveProjectAsMenuItem.isVisible()
				|| _addFileMenuItem.isVisible()
				|| _removeFileMenuItem.isVisible()
				|| _addFolderMenuItem.isVisible()
				|| _removeFolderMenuItem.isVisible()
				|| _deleteFileMenuItem.isVisible()
				|| _newProjectFileMenuItem.isVisible()
				|| _addOpenedFilesMenuItem.isVisible()
				|| _compileMenuItem.isVisible() || _executeMenuItem.isVisible())
				&& (_setCompilableFileMenuItem.isVisible()
						|| _unsetCompilableFileMenuItem.isVisible() || _setMainFileMenuItem.isVisible()));		

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
		
		// Sets the project menu as visible or not visible
		_projectSubmenuConfiguration.setVisible(_newProjectMenuItem.isVisible()
						|| _openProjectMenuItem.isVisible()
						|| _saveProjectMenuItem.isVisible()
						|| _closeProjectMenuItem.isVisible()
						|| _addFileMenuItem.isVisible()
						|| _removeFileMenuItem.isVisible()
						|| _deleteFileMenuItem.isVisible()
						|| _newProjectFileMenuItem.isVisible()
						|| _addFolderMenuItem.isVisible()
						|| _removeFolderMenuItem.isVisible()
						|| _compileMenuItem.isVisible()
						|| _executeMenuItem.isVisible());
		
		_projectSubmenuConfiguration.setErasable(false);
		
		
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
		if ((!name.equals(NEW_PROJECT_NAME))
			 && (!name.equals(OPEN_PROJECT_NAME))
			 && (!name.equals(OPEN_RECENT_PROJECTS_NAME))
			 && (!name.equals(CLOSE_PROJECT_NAME))
			 && (!name.equals(SAVE_PROJECT_NAME))
			 && (!name.equals(SAVE_PROJECT_AS_NAME))
			 && (!name.equals(ADD_OPENED_FILES_NAME))
			 && (!name.equals(NEW_PROJECT_FILE_NAME))
			 && (!name.equals(ADD_FILE_NAME))
			 && (!name.equals(REMOVE_FILE_NAME))
			 && (!name.equals(DELETE_FILE_NAME))
			 && (!name.equals(ADD_FOLDER_NAME))
			 && (!name.equals(REMOVE_FOLDER_NAME))
			 && (!name.equals(COMPILE_NAME))
			 && (!name.equals(EXECUTE_NAME))
			 && (!name.equals(SET_COMPILABLE_FILE_NAME))
			 && (!name.equals(UNSET_COMPILABLE_FILE_NAME))
			 && (!name.equals(SET_MAIN_FILE_NAME))
			 && (!name.equals(UNSET_MAIN_FILE_NAME))){
			return true;
		}else{
			return false;
		}
	
			
	}

	/**
	 * Sets the ACIDE - A Configurable IDE project menu listeners.
	 */
	public void setListeners() {

		// Sets the new project menu item action listener
		_newProjectMenuItem
				//.addActionListener(new AcideNewProjectMenuItemListener());
			.addActionListener(new AcideInsertedItemListener(
				AcideMenuItemsConfiguration.getInstance()
				.getSubmenu(PROJECT_MENU_NAME).getItem(NEW_PROJECT_NAME)));

		// Sets the open project menu item action listener
		_openProjectMenuItem
				//.addActionListener(new AcideOpenProjectMenuItemListener());
			.addActionListener(new AcideInsertedItemListener(
				AcideMenuItemsConfiguration.getInstance()
				.getSubmenu(PROJECT_MENU_NAME).getItem(OPEN_PROJECT_NAME)));

		// Sets the close project menu item action listener
		_closeProjectMenuItem
				//.addActionListener(new AcideCloseProjecMenuItemtListener());
			.addActionListener(new AcideInsertedItemListener(
				AcideMenuItemsConfiguration.getInstance()
				.getSubmenu(PROJECT_MENU_NAME).getItem(CLOSE_PROJECT_NAME)));

		// Sets the save project menu item action listener
		_saveProjectMenuItem
				//.addActionListener(new AcideSaveProjectMenuItemListener());
			.addActionListener(new AcideInsertedItemListener(
				AcideMenuItemsConfiguration.getInstance()
				.getSubmenu(PROJECT_MENU_NAME).getItem(SAVE_PROJECT_NAME)));

		// Sets the save project as menu item action listener
		_saveProjectAsMenuItem
				//.addActionListener(new AcideSaveProjectAsMenuItemListener());
			.addActionListener(new AcideInsertedItemListener(
				AcideMenuItemsConfiguration.getInstance()
				.getSubmenu(PROJECT_MENU_NAME).getItem(SAVE_PROJECT_AS_NAME)));

		// Sets the add opened files menu item action listener
		_addOpenedFilesMenuItem
				//.addActionListener(new AcideAddOpenedFilesMenuItemListener());
			.addActionListener(new AcideInsertedItemListener(
				AcideMenuItemsConfiguration.getInstance()
				.getSubmenu(PROJECT_MENU_NAME).getItem(ADD_OPENED_FILES_NAME)));

		// Sets the new project file menu item action listener
		_newProjectFileMenuItem
				//.addActionListener(new AcideNewProjectFileMenuItemListener());
			.addActionListener(new AcideInsertedItemListener(
				AcideMenuItemsConfiguration.getInstance()
				.getSubmenu(PROJECT_MENU_NAME).getItem(NEW_PROJECT_FILE_NAME)));

		// Sets the add file menu item action listener
		_addFileMenuItem//.addActionListener(new AcideAddFileMenuItemListener());
			.addActionListener(new AcideInsertedItemListener(
				AcideMenuItemsConfiguration.getInstance()
				.getSubmenu(PROJECT_MENU_NAME).getItem(ADD_FILE_NAME)));

		// Sets the remove file menu item action listener
		_removeFileMenuItem
				//.addActionListener(new AcideRemoveFileMenuItemListener());
			.addActionListener(new AcideInsertedItemListener(
				AcideMenuItemsConfiguration.getInstance()
				.getSubmenu(PROJECT_MENU_NAME).getItem(REMOVE_FILE_NAME)));

		// Sets the delete file menu item action listener
		_deleteFileMenuItem
				//.addActionListener(new AcideDeleteFileMenuItemListener());
			.addActionListener(new AcideInsertedItemListener(
				AcideMenuItemsConfiguration.getInstance()
				.getSubmenu(PROJECT_MENU_NAME).getItem(DELETE_FILE_NAME)));

		// Sets the add folder menu item action listener
		_addFolderMenuItem
				//.addActionListener(new AcideAddFolderMenuItemListener());
			.addActionListener(new AcideInsertedItemListener(
				AcideMenuItemsConfiguration.getInstance()
				.getSubmenu(PROJECT_MENU_NAME).getItem(ADD_FOLDER_NAME)));

		// Sets the remove folder menu item action listener
		_removeFolderMenuItem
				//.addActionListener(new AcideRemoveFolderMenuItemListener());
			.addActionListener(new AcideInsertedItemListener(
				AcideMenuItemsConfiguration.getInstance()
				.getSubmenu(PROJECT_MENU_NAME).getItem(REMOVE_FOLDER_NAME)));

		// Sets the compile menu item action listener
		_compileMenuItem//.addActionListener(new AcideCompileMenuItemListener());
			.addActionListener(new AcideInsertedItemListener(
				AcideMenuItemsConfiguration.getInstance()
				.getSubmenu(PROJECT_MENU_NAME).getItem(COMPILE_NAME)));

		// Sets the execute menu item action listener
		_executeMenuItem//.addActionListener(new AcideExecuteMenuItemListener());
			.addActionListener(new AcideInsertedItemListener(
				AcideMenuItemsConfiguration.getInstance()
				.getSubmenu(PROJECT_MENU_NAME).getItem(EXECUTE_NAME)));

		// Sets the set compilable file menu item action listener
		_setCompilableFileMenuItem
				//.addActionListener(new AcideSetCompilableFileMenuItemListener());
			.addActionListener(new AcideInsertedItemListener(
				AcideMenuItemsConfiguration.getInstance()
				.getSubmenu(PROJECT_MENU_NAME).getItem(SET_COMPILABLE_FILE_NAME)));

		// Sets the unset compilable file menu item action listener
		_unsetCompilableFileMenuItem
				//.addActionListener(new AcideUnsetCompilableFileMenuItemListener());
			.addActionListener(new AcideInsertedItemListener(
				AcideMenuItemsConfiguration.getInstance()
				.getSubmenu(PROJECT_MENU_NAME).getItem(UNSET_COMPILABLE_FILE_NAME)));

		// Sets the set main file menu item action listener
		_setMainFileMenuItem
				//.addActionListener(new AcideSetMainFileMenuItemListener());
			.addActionListener(new AcideInsertedItemListener(
				AcideMenuItemsConfiguration.getInstance()
				.getSubmenu(PROJECT_MENU_NAME).getItem(SET_MAIN_FILE_NAME)));

		// Sets the unset main file menu item action listener
		_unsetMainFileMenuItem
				//.addActionListener(new AcideUnsetMainFileMenuItemListener());
			.addActionListener(new AcideInsertedItemListener(
				AcideMenuItemsConfiguration.getInstance()
				.getSubmenu(PROJECT_MENU_NAME).getItem(UNSET_MAIN_FILE_NAME)));
		
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
	 * Enables the ACIDE - A Configurable IDE project menu.
	 */
	public void enableMenu() {

		// Enables the close project menu item
		_closeProjectMenuItem.setEnabled(true);

		// Enables the save project as menu item
		_saveProjectAsMenuItem.setEnabled(true);

		// Enables the add opened files menu item
		_addOpenedFilesMenuItem.setEnabled(true);

		// Enables the new project file menu item
		_newProjectFileMenuItem.setEnabled(true);

		// Enables the add file menu item
		_addFileMenuItem.setEnabled(true);

		// Enables the add folder menu item
		_addFolderMenuItem.setEnabled(true);

		// Enables the compile menu item
		_compileMenuItem.setEnabled(true);

		// Enables the execute menu item
		_executeMenuItem.setEnabled(true);
	}

	/**
	 * Disables the ACIDE - A Configurable IDE project menu.
	 */
	public void disableMenu() {

		// Disables the close project menu item
		_closeProjectMenuItem.setEnabled(false);

		// Disables the save project menu item
		_saveProjectMenuItem.setEnabled(false);

		// Disables the save project as menu item
		_saveProjectAsMenuItem.setEnabled(false);

		// Enables the add opened files menu item
		_addOpenedFilesMenuItem.setEnabled(false);

		// Disables the new project file menu item
		_newProjectFileMenuItem.setEnabled(false);

		// Disables the add file menu item
		_addFileMenuItem.setEnabled(false);

		// Disables the remove file menu item
		_removeFileMenuItem.setEnabled(false);

		// Disables the delete file menu item
		_deleteFileMenuItem.setEnabled(false);

		// Disables the add folder menu item
		_addFolderMenuItem.setEnabled(false);

		// Disables the remove folder menu item
		_removeFolderMenuItem.setEnabled(false);

		// Disables the compile menu item
		_compileMenuItem.setEnabled(false);

		// Disables the execute menu item
		_executeMenuItem.setEnabled(false);

		// Disables the set main file menu item
		_setMainFileMenuItem.setEnabled(false);

		// Disables the unset main file menu item
		_unsetMainFileMenuItem.setEnabled(false);

		// Disables the set compilable file menu item
		_setCompilableFileMenuItem.setEnabled(false);

		// Disables the unset compilable file menu item
		_unsetCompilableFileMenuItem.setEnabled(false);
	}

	/**
	 * Opens an ACIDE - A Configurable IDE project from a file given as a
	 * parameter.
	 * 
	 * @param filePath
	 *            file path which contains the project configuration file.
	 */
	public void openProject(final String filePath) {

		Thread thread = new Thread() {
			/*
			 * (non-Javadoc)
			 * 
			 * @see java.lang.Thread#run()
			 */
			@Override
			public synchronized void run() {

				try {
					SwingUtilities.invokeAndWait(new Runnable() {

						/*
						 * (non-Javadoc)
						 * 
						 * @see java.lang.Runnable#run()
						 */
						@Override
						public void run() {
							// Sets the wait cursor
							AcideMainWindow
									.getInstance()
									.setCursor(
											Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));

							// Updates the status message in the status bar
							AcideMainWindow.getInstance().getStatusBar()
									.setStatusMessage(" ");

							// Loads the file editor configuration
							loadProjectConfiguration(filePath);

							// Loads the language
							loadLanguage();

							// Loads the menu configuration
							loadMenuConfiguration();

							// Loads the console configuration
							loadConsoleConfiguration();

							// Loads the tool bar configuration
							loadToolBarConfiguration();

							// Loads the explorer configuration
							loadExplorerConfiguration();

							// Loads the file editor configuration
							loadFileEditorConfiguration();

							// Loads the main window configuration
							loadMainWindowConfiguration();

							// The project has not been modified
							AcideProjectConfiguration.getInstance()
									.setIsModified(false);

							// This is the first time that it is saved
							AcideProjectConfiguration.getInstance()
									.setIsFirstSave(true);

							// Enables the project menu
							AcideMainWindow.getInstance().getMenu()
									.getProjectMenu().enableMenu();

							// Enables the open all files menu item
							AcideMainWindow.getInstance().getMenu()
									.getFileMenu().getOpenAllFilesMenuItem()
									.setEnabled(true);

							// Updates the menu bar tool bar
							AcideMainWindow.getInstance().getToolBarPanel()
									.getMenuBarToolBar()
									.updateStateOfFileButtons();

							// Updates the changes in the main window
							AcideMainWindow.getInstance().validate();

							// Repaints the main window
							AcideMainWindow.getInstance().repaint();

							// Adds the project to the recent projects list
							AcideWorkbenchConfiguration.getInstance()
									.getRecentProjectsConfiguration()
									.addRecentProjectToList(filePath);

							// Updates the last opened project directory at
							// ACIDE - A
							// Configurable
							// IDE resource manager
							AcideResourceManager.getInstance().setProperty(
									"lastOpenedProjectDirectory",
									new File(filePath).getParent());

							// Sets the default cursor
							AcideMainWindow.getInstance().setCursor(
									Cursor.getDefaultCursor());
						}
					});
				} catch (InterruptedException exception) {

					// Updates the log
					AcideLog.getLog().info(exception.getMessage());
					exception.printStackTrace();
				} catch (InvocationTargetException exception) {

					// Updates the log
					AcideLog.getLog().info(exception.getMessage());
					exception.printStackTrace();
				}
			}
		};

		thread.start();
	}

	/**
	 * Checks if there are any modified opened file editors and asks to the user
	 * if wants to save them or not.
	 */
	public void saveModifiedOpenedFileEditors() {

		// If the file editor manager is modified
		if (AcideMainWindow.getInstance().getFileEditorManager().isModified()) {

			// Gets the number of file editor panels
			int numberOfFileEditorPanels = AcideMainWindow.getInstance()
					.getFileEditorManager().getNumberOfFileEditorPanels();

			// If there are opened file editor panels
			if (numberOfFileEditorPanels > 0) {

				int selectedFileEditorPanelIndex = AcideMainWindow
						.getInstance().getFileEditorManager()
						.getSelectedFileEditorPanelIndex();

				// Search for modified opened file editors
				for (int index = numberOfFileEditorPanels - 1; index >= 0; index--) {

					// If it is modified
					if (AcideMainWindow.getInstance().getFileEditorManager()
							.isRedButton(index)) {

						// Puts the focus on the current checked file
						// editor panel
						AcideMainWindow.getInstance().getFileEditorManager()
								.setSelectedFileEditorPanelAt(index);

						// Do you want to save it?
						int returnValue2 = JOptionPane.showConfirmDialog(null,
								AcideLanguageManager.getInstance().getLabels()
										.getString("s643"),
								AcideLanguageManager.getInstance().getLabels()
										.getString("s953"),
								JOptionPane.YES_NO_OPTION);

						// If it is OK
						if (returnValue2 == JOptionPane.OK_OPTION) {

							// Saves the file editor panel
							AcideMainWindow.getInstance().getMenu()
									.getFileMenu().saveFile(index);
						}
					}
				}

				// Restores the selected file editor panel
				AcideMainWindow
						.getInstance()
						.getFileEditorManager()
						.setSelectedFileEditorPanelAt(
								selectedFileEditorPanelIndex);
			}
		}

		// Gets the number of file editor panels
		int numberOfFileEditorPanels = AcideMainWindow.getInstance()
				.getFileEditorManager().getNumberOfFileEditorPanels();

		// Closes the file editor panels in the tabbed pane
		for (int index = 0; index < numberOfFileEditorPanels; index++) {

			// Sets the selected tab at index 0
			AcideMainWindow.getInstance().getFileEditorManager()
					.setSelectedFileEditorPanelAt(0);

			// Removes it from the tabbed pane
			AcideMainWindow.getInstance().getFileEditorManager()
					.getTabbedPane().remove(0);

			// Validates the changes in the tabbed pane
			AcideMainWindow.getInstance().getFileEditorManager()
					.getTabbedPane().validate();
		}
	}

	/**
	 * Loads the project explorer configuration.
	 */
	public void loadExplorerConfiguration() {

		// If it is the default project
		if (AcideProjectConfiguration.getInstance().isDefaultProject()) {

			// Sets the title with the <empty> tag
			AcideMainWindow.getInstance().setTitle(
					AcideLanguageManager.getInstance().getLabels()
							.getString("s425")
							+ " - <empty>");
		} else {

			// Removes all the nodes in the tree
			AcideMainWindow.getInstance().getExplorerPanel().getRoot()
					.removeAllChildren();

			// Creates the folder with the name of the project
			AcideProjectFile rootProjectFile = new AcideProjectFile();

			// Sets the absolute path
			rootProjectFile.setAbsolutePath(AcideProjectConfiguration
					.getInstance().getName());

			// Sets the name
			rootProjectFile.setName(AcideProjectConfiguration.getInstance()
					.getName());

			// It is directory
			rootProjectFile.setIsDirectory(true);

			// It has no parent
			rootProjectFile.setParent(null);

			// Sets the main window title
			AcideMainWindow.getInstance()
					.setTitle(
							AcideLanguageManager.getInstance().getLabels()
									.getString("s425")
									+ " - "
									+ AcideProjectConfiguration.getInstance()
											.getName());

			// Creates the root node of the explorer tree
			DefaultMutableTreeNode rootNode = new DefaultMutableTreeNode(
					rootProjectFile);

			// Allows children
			rootNode.setAllowsChildren(true);

			// Adds the root node to the tree
			AcideMainWindow.getInstance().getExplorerPanel().getRoot()
					.add(rootNode);

			// Creates the directory list
			ArrayList<DefaultMutableTreeNode> directoryList = new ArrayList<DefaultMutableTreeNode>();

			// Adds the associated project files to the explorer
			for (int index = 0; index < AcideProjectConfiguration.getInstance()
					.getNumberOfFilesFromList(); index++) {

				// Gets the file from the project configuration
				DefaultMutableTreeNode projectFileNode = new DefaultMutableTreeNode(
						AcideProjectConfiguration.getInstance()
								.getFileAt(index));

				// If it is directory
				if (AcideProjectConfiguration.getInstance().getFileAt(index)
						.isDirectory()) {

					// It can have files inside
					projectFileNode.setAllowsChildren(true);

					// Adds the file
					directoryList.add(projectFileNode);
				} else
					// Can't have files inside
					projectFileNode.setAllowsChildren(false);

				// If the file is in the same folder than the project folder
				if (AcideProjectConfiguration
						.getInstance()
						.getFileAt(index)
						.getParent()
						.equals(AcideProjectConfiguration.getInstance()
								.getName())) {
					// Adds the file
					rootNode.add(projectFileNode);
				} else {

					// Searches for it in the tree structure
					DefaultMutableTreeNode parentNode = AcideMainWindow
							.getInstance()
							.getExplorerPanel()
							.searchDirectoryList(
									directoryList,
									AcideProjectConfiguration.getInstance()
											.getFileAt(index).getParent());

					// Adds the file
					parentNode.add(projectFileNode);
				}
			}

			// Updates the explorer tree
			AcideMainWindow.getInstance().getExplorerPanel().getTreeModel()
					.reload();

			// Repaints the explorer tree
			AcideMainWindow.getInstance().getExplorerPanel().expandTree();

			// If there are files associated to the project
			if (AcideProjectConfiguration.getInstance()
					.getNumberOfFilesFromList() > 0) {

				// Enables the remove file menu item
				AcideMainWindow.getInstance().getExplorerPanel().getPopupMenu()
						.getRemoveFileMenuItem().setEnabled(true);

				// Enables the delete file menu item
				AcideMainWindow.getInstance().getExplorerPanel().getPopupMenu()
						.getDeleteFileMenuItem().setEnabled(true);
			} else {

				// Disables the remove file menu item
				AcideMainWindow.getInstance().getExplorerPanel().getPopupMenu()
						.getRemoveFileMenuItem().setEnabled(false);

				// Disables the delete file menu item
				AcideMainWindow.getInstance().getExplorerPanel().getPopupMenu()
						.getDeleteFileMenuItem().setEnabled(false);
			}
		}
	}

	/**
	 * Loads the project language.
	 */
	public void loadLanguage() {
		// Checks if the project language is in the loaded languages, if 
		// not loads the first available language
		if(AcideMainWindow.getInstance().getMenu().getConfigurationMenu()
				.getLanguageMenu().get_languageItems().containsKey(AcideProjectConfiguration
						.getInstance().getLanguageConfiguration()))
			AcideMainWindow.getInstance().getMenu().getConfigurationMenu()
			.getLanguageMenu().get_languageItems().get(AcideProjectConfiguration
						.getInstance().getLanguageConfiguration()).doClick();
		else
			if(!AcideMainWindow.getInstance().getMenu().getConfigurationMenu()
			.getLanguageMenu().get_languageItems().isEmpty())
				AcideMainWindow.getInstance().getMenu().getConfigurationMenu()
				.getLanguageMenu().get_languageItems().values().iterator().next().doClick();
	}

	/**
	 * Loads the project menu configuration.
	 */
	public void loadMenuConfiguration() {

		// Gets the menu configuration from the project configuration
		String menuConfiguration = AcideProjectConfiguration.getInstance()
				.getMenuConfiguration();

		try {

			// Loads the new menu item list
			AcideMenuConfiguration.getInstance().setMenuElementList(
					AcideMenuConfiguration.getInstance()
							.loadMenuConfigurationFile(menuConfiguration));

			// Updates the ACIDE - A Configurable IDE current menu configuration
			AcideResourceManager.getInstance().setProperty(
					"currentMenuConfiguration", menuConfiguration);
		} catch (Exception exception) {

			// If it is not the default project
			if (!AcideProjectConfiguration.getInstance().isDefaultProject())

				// The project has been modified
				AcideProjectConfiguration.getInstance().setIsModified(true);

			// Updates the log
			AcideLog.getLog().error(exception.getMessage());
			exception.printStackTrace();

			// Gets the name
			String name;
			int index = menuConfiguration.lastIndexOf("\\");
			if (index == -1)
				index = menuConfiguration.lastIndexOf("/");
			name = ".\\configuration\\menu\\"
					+ menuConfiguration.substring(index + 1,
							menuConfiguration.length());

			try {

				// Load the new menu item list
				AcideMenuConfiguration.getInstance().setMenuElementList(
						AcideMenuConfiguration.getInstance()
								.loadMenuConfigurationFile(menuConfiguration));

				// Updates the ACIDE - A Configurable IDE current menu
				// configuration
				AcideResourceManager.getInstance().setProperty(
						"currentMenuConfiguration", name);

				// Displays an error message
				JOptionPane.showMessageDialog(null, AcideLanguageManager
						.getInstance().getLabels().getString("s956")
						+ menuConfiguration
						+ AcideLanguageManager.getInstance().getLabels()
								.getString("s957") + name);

			} catch (Exception exception1) {

				try {

					// Loads the menu configuration
					AcideMenuConfiguration.getInstance().setMenuElementList(
							AcideMenuConfiguration.getInstance()
									.loadMenuConfigurationFile(
											menuConfiguration));
				} catch (Exception exception2) {

					// Updates the log
					AcideLog.getLog().error(exception2.getMessage());
					exception2.printStackTrace();
				}

				// Updates the ACIDE - A Configurable IDE current menu
				// configuration
				AcideResourceManager.getInstance().setProperty(
						"currentMenuConfiguration",
						"./configuration/menu/defaultAllOn.menuConfig");

				// Displays an error message
				JOptionPane.showMessageDialog(null, AcideLanguageManager
						.getInstance().getLabels().getString("s956")
						+ menuConfiguration
						+ AcideLanguageManager.getInstance().getLabels()
								.getString("s959"));

				// Updates the log
				AcideLog.getLog().error(exception1.getMessage());
				exception1.printStackTrace();
			}
		}

		// Builds the menu
		AcideMainWindow.getInstance().getMenu().updateComponentsVisibility();

		// Validates the changes in the main window
		AcideMainWindow.getInstance().validate();

		// Repaints the main window
		AcideMainWindow.getInstance().repaint();

		// Enables the save menu menu item
		AcideMainWindow.getInstance().getMenu().getConfigurationMenu()
				.getMenuMenu().getSaveMenuMenuItem().setEnabled(true);

		// The changes are saved
		AcideMenuConfigurationWindow.setChangesAreSaved(true);
	}

	/**
	 * Loads the console panel configuration associated to the project.
	 */
	public void loadConsoleConfiguration() {

		// Exits the console
		AcideMainWindow.getInstance().getConsolePanel().executeExitCommand();

		// Sets the shell directory in the resource manager from the
		AcideResourceManager.getInstance().setProperty(
				"consolePanel.shellDirectory",
				AcideProjectConfiguration.getInstance().getShellDirectory());

		// Sets the shell path in the resource manager
		AcideResourceManager.getInstance().setProperty(
				"consolePanel.shellPath",
				AcideProjectConfiguration.getInstance().getShellPath());

		// Sets the echo command in the resource manager
		AcideResourceManager.getInstance().setProperty(
				"consolePanel.isEchoCommand",
				String.valueOf(AcideProjectConfiguration.getInstance()
						.getIsEchoCommand()));

		// Sets the exit command in the resource manager
		AcideResourceManager.getInstance().setProperty(
				"consolePanel.exitCommand",
				AcideProjectConfiguration.getInstance().getExitCommand());

		// Sets the font name in the resource manager
		AcideResourceManager.getInstance().setProperty("consolePanel.fontName",
				AcideProjectConfiguration.getInstance().getFontName());

		// Sets the font style in the resource manager
		AcideResourceManager.getInstance().setProperty(
				"consolePanel.fontStyle",
				String.valueOf(AcideProjectConfiguration.getInstance()
						.getFontStyle()));

		// Sets the font size in the resource manager
		AcideResourceManager.getInstance().setProperty(
				"consolePanel.fontSize",
				String.valueOf(AcideProjectConfiguration.getInstance()
						.getFontSize()));

		// Sets the foreground color in the resource manager
		AcideResourceManager.getInstance().setProperty(
				"consolePanel.foregroundColor",
				Integer.toString(AcideProjectConfiguration.getInstance()
						.getForegroundColor().getRGB()));

		// Sets the background color in the resource manager
		AcideResourceManager.getInstance().setProperty(
				"consolePanel.backgroundColor",
				Integer.toString(AcideProjectConfiguration.getInstance()
						.getBackgroundColor().getRGB()));

		// Sets the buffer size in the resource manager
		AcideResourceManager.getInstance().setProperty(
				"consolePanel.bufferSize",
				Integer.toString(AcideProjectConfiguration.getInstance()
						.getBufferSize()));

		// Resets the console panel
		AcideMainWindow.getInstance().getConsolePanel().resetConsole();

		// Updates the look and feel
		AcideMainWindow.getInstance().getConsolePanel().setLookAndFeel();
	}

	/**
	 * Loads the project tool bar configuration.
	 */
	public void loadToolBarConfiguration() {

		try {

			// Gets the ACIDE - A Configurable IDE tool bar configuration
			String currentToolBarConfiguration = AcideProjectConfiguration
					.getInstance().getToolBarConfiguration();

			// Loads the ACIDE - A Configurable IDE tool bar configuration from
			// the current tool bar configuration
			AcideToolBarConfiguration.getInstance().load(
					currentToolBarConfiguration);

			// Updates the ACIDE - A Configurable IDE current tool bar
			// configuration
			AcideResourceManager.getInstance().setProperty(
					"currentToolBarConfiguration", currentToolBarConfiguration);
		} catch (Exception exception) {

			// Updates the log
			AcideLog.getLog().error(exception.getMessage());
			exception.printStackTrace();

			// Gets the ACIDE - A Configurable IDE tool bar configuration
			String currentToolBarConfiguration = AcideProjectConfiguration
					.getInstance().getToolBarConfiguration();

			// Gets the name
			String name = "";
			int index = currentToolBarConfiguration.lastIndexOf("\\");
			if (index == -1)
				index = currentToolBarConfiguration.lastIndexOf("/");
			name = ".\\configuration\\toolbar\\"
					+ currentToolBarConfiguration.substring(index + 1,
							currentToolBarConfiguration.length());
			try {

				// Loads the ACIDE - A Configurable IDE tool bar configuration
				// from the current tool bar configuration
				AcideToolBarConfiguration.getInstance().load(name);

				// If it is not the default project
				if (!AcideProjectConfiguration.getInstance().isDefaultProject())

					// The project has been modified
					AcideProjectConfiguration.getInstance().setIsModified(true);

				// Displays an error message
				JOptionPane.showMessageDialog(null, AcideLanguageManager
						.getInstance().getLabels().getString("s958")
						+ currentToolBarConfiguration
						+ AcideLanguageManager.getInstance().getLabels()
								.getString("s957") + name);

				// Updates the ACIDE - A Configurable IDE tool bar configuration
				AcideResourceManager.getInstance().setProperty(
						"currentToolBarConfiguration", name);
			} catch (Exception exception1) {

				// Updates the log
				AcideLog.getLog().error(exception1.getMessage());
				exception1.printStackTrace();

				try {

					// Loads the ACIDE - A Configurable IDE tool bar
					// configuration from the current tool bar configuration
					AcideToolBarConfiguration.getInstance().load(
							"./configuration/toolbar/default.toolbarConfig");

					// If it is not the default project
					if (!AcideProjectConfiguration.getInstance()
							.isDefaultProject())

						// The project has been modified
						AcideProjectConfiguration.getInstance().setIsModified(
								true);

				} catch (Exception exception2) {

					// Updates the log
					AcideLog.getLog().error(exception2.getMessage());
					exception2.printStackTrace();
				}

				// Displays an error message
				JOptionPane.showMessageDialog(null, AcideLanguageManager
						.getInstance().getLabels().getString("s958")
						+ currentToolBarConfiguration
						+ AcideLanguageManager.getInstance().getLabels()
								.getString("s959"));

				// Updates the ACIDE - A Configurable IDE tool bar configuration
				AcideResourceManager.getInstance().setProperty(
						"currentToolBarConfiguration",
						"./configuration/toolbar/default.toolbarConfig");
			}
		}

		// Builds the tool bar
		AcideMainWindow.getInstance().buildToolBarPanel();

		// Enables the save tool bar menu item
		AcideMainWindow.getInstance().getMenu().getConfigurationMenu()
				.getToolBarMenu().getSaveToolBarMenuItem().setEnabled(true);

		// The changes are saved
		AcideToolBarConfigurationWindow.setAreChangesSaved(true);
	}

	/**
	 * Loads the project configuration from the configuration file.
	 * 
	 * @param filePath
	 *            configuration file path.
	 */
	public void loadProjectConfiguration(String filePath) {

		// Loads the project configuration
		AcideProjectConfiguration.getInstance().load(filePath);

		try {

			// Updates the ACIDE - A Configurable IDE project configuration
			AcideResourceManager.getInstance().setProperty(
					"projectConfiguration",
					AcideProjectConfiguration.getInstance().getProjectPath());
		} catch (Exception exception) {

			// Updates the log
			AcideLog.getLog().error(exception.getMessage());
			exception.printStackTrace();
		}
	}

	/**
	 * Loads the project main window configuration.
	 */
	public void loadMainWindowConfiguration() {

		// If the ACIDE - A Configurable IDE explorer panel has not to be showed
		if (!AcideProjectConfiguration.getInstance().isExplorerPanelShowed()) {

			// Hides the explorer panel
			AcideMainWindow.getInstance().getExplorerPanel()
					.disposeExplorerPanel();
		} else {

			// Displays the explorer panel
			AcideMainWindow.getInstance().getExplorerPanel()
					.showExplorerPanel();
		}

		// Updates the show explorer panel check box menu item state
		AcideMainWindow
				.getInstance()
				.getMenu()
				.getViewMenu()
				.getShowExplorerPanelCheckBoxMenuItem()
				.setSelected(
						AcideProjectConfiguration.getInstance()
								.isExplorerPanelShowed());

		// If the ACIDE - A Configurable IDE console panel has not to be showed
		if (!AcideProjectConfiguration.getInstance().isConsolePanelShowed())

			// Hides the console panel
			AcideMainWindow.getInstance().getConsolePanel()
					.disposeConsolePanel();
		else

			// Shows the console panel
			AcideMainWindow.getInstance().getConsolePanel().showConsolePanel();

		// Updates the show console panel check box menu item state
		AcideMainWindow
				.getInstance()
				.getMenu()
				.getViewMenu()
				.getShowConsolePanelCheckBoxMenuItem()
				.setSelected(
						AcideProjectConfiguration.getInstance()
								.isConsolePanelShowed());
		
		// If the ACIDE - A Configurable IDE database panel must not be shown
		if (!AcideProjectConfiguration.getInstance().isDatabasePanelShowed())

			// Hides the database panel
			AcideMainWindow.getInstance().getDataBasePanel()
					.disposeDataBasePanel();
		else

			// Shows the database panel
			AcideMainWindow.getInstance().getDataBasePanel().showDataBasePanel();

		// Updates the show database panel check box menu item state
		AcideMainWindow
				.getInstance()
				.getMenu()
				.getViewMenu()
				.getShowDataBasePanelCheckBoxMenuItem()
				.setSelected(
						AcideProjectConfiguration.getInstance()
								.isDatabasePanelShowed());
		
		// If the ACIDE - A Configurable IDE graph panel has not to be showed
		if (!AcideProjectConfiguration.getInstance().isGraphPanelShowed())

			// Hides the graph panel
			AcideMainWindow.getInstance().getGraphPanel()
					.disposeGraphPanel();
		else

			// Shows the graph panel
			AcideMainWindow.getInstance().getGraphPanel().showGraphPanel();

		// Updates the show graph panel check box menu item state
		AcideMainWindow
				.getInstance()
				.getMenu()
				.getViewMenu()
				.getShowGraphPanelCheckBoxMenuItem()
				.setSelected(
						AcideProjectConfiguration.getInstance()
								.isGraphPanelShowed());
		
		// If the ACIDE - A Configurable IDE debug panel has not to be showed
		if (!AcideProjectConfiguration.getInstance().isDebugPanelShowed())

			// Hides the debug panel
			AcideMainWindow.getInstance().getDebugPanel().disposeDebugPanel();
		else

			// Shows the debug panel
			AcideMainWindow.getInstance().getDebugPanel().showDebugPanel();

		// Updates the show debug panel check box menu item state
		AcideMainWindow
				.getInstance()
				.getMenu()
				.getViewMenu()
				.getShowDebugPanelCheckBoxMenuItem()
				.setSelected(
						AcideProjectConfiguration.getInstance()
								.isDebugPanelShowed());

		// Sets the main window size
		AcideMainWindow.getInstance().setSize(
				AcideProjectConfiguration.getInstance().getWindowWidth(),
				AcideProjectConfiguration.getInstance().getWindowHeight());

		// Sets the main window location
		AcideMainWindow.getInstance().setLocation(
				AcideProjectConfiguration.getInstance().getXCoordinate(),
				AcideProjectConfiguration.getInstance().getYCoordinate());

		// Sets the vertical split pane divider location
		AcideMainWindow
				.getInstance()
				.getVerticalFilesSplitPane()
				.setDividerLocation(
						AcideProjectConfiguration.getInstance()
								.getVerticalFilesSplitPaneDividerLocation());
		
		// Sets the vertical database pane divider location
		AcideMainWindow
				.getInstance()
				.getVerticalDataBaseSplitPane()
				.setDividerLocation(
						AcideProjectConfiguration.getInstance()
								.getVerticalDataBaseSplitPaneDividerLocation());
		
		// Sets the vertical graph pane divider location
		AcideMainWindow
				.getInstance()
				.getVerticalSplitPane()
				.setDividerLocation(
						AcideProjectConfiguration.getInstance()
								.getVerticalGraphSplitPaneDividerLocation());
		
		// Sets the horizontal graph pane divider location
		AcideMainWindow
				.getInstance()
				.getHorizontalGraphSplitPane()
				.setDividerLocation(
						AcideProjectConfiguration.getInstance()
								.getHorizontalGraphSplitPaneDividerLocation());

		// Sets the horizontal split pane divider location
		AcideMainWindow
				.getInstance()
				.getHorizontalSplitPane()
				.setDividerLocation(
						AcideProjectConfiguration.getInstance()
								.getHorizontalSplitPanelDividerLocation());
	}

	/**
	 * Loads the project file editor configuration. SwingUtilities is used to
	 * wait until the end of the execution of all the previous events so it can
	 * add all the editors properly and safety.
	 */
	public void loadFileEditorConfiguration() {

		for (int index = 0; index < AcideProjectConfiguration.getInstance()
				.getNumberOfFilesFromList(); index++) {

			// If the file is not a directory
			if (!AcideProjectConfiguration.getInstance().getFileAt(index)
					.isDirectory()) {

				// Loads the file content
				String fileContent = null;
				fileContent = AcideFileManager.getInstance().load(
						AcideProjectConfiguration.getInstance()
								.getFileAt(index).getAbsolutePath());

				// If the file has to be opened
				if (AcideProjectConfiguration.getInstance().getFileAt(index)
						.isOpened()) {

					// Gets the predefined lexicon configuration
					AcideLexiconConfiguration lexiconConfiguration = AcideWorkbenchConfiguration
							.getInstance()
							.getLexiconAssignerConfiguration()
							.getPredifinedLexiconConfiguration(
									AcideProjectConfiguration.getInstance()
											.getFileAt(index).getAbsolutePath());

					// Creates the current grammar configuration
					AcideGrammarConfiguration currentGrammarConfiguration = new AcideGrammarConfiguration();

					// Sets the current grammar configuration path
					currentGrammarConfiguration
							.setPath(AcideGrammarConfiguration.DEFAULT_FILE);

					// Creates the previous grammar configuration
					AcideGrammarConfiguration previousGrammarConfiguration = new AcideGrammarConfiguration();

					// Sets the previous grammar configuration path
					previousGrammarConfiguration
							.setPath(AcideGrammarConfiguration.DEFAULT_FILE);

					// Updates the tabbed pane in the file editor
					// manager
					AcideMainWindow
							.getInstance()
							.getFileEditorManager()
							.updateTabbedPane(
									AcideProjectConfiguration.getInstance()
											.getFileAt(index).getAbsolutePath(),
									fileContent,
									true,
									AcideProjectConfiguration.getInstance()
											.getFileAt(index).getType(), 0, 0,
									1, lexiconConfiguration,
									currentGrammarConfiguration,
									previousGrammarConfiguration);
				}

				// The project configuration has been modified
				AcideProjectConfiguration.getInstance().setIsModified(false);
			}
		}
	}

	/**
	 * Closes the ACIDE - A Configurable IDE current project.
	 */
	public void closeProject() {

		try {
			// Enables the close all files menu item
			AcideMainWindow.getInstance().getMenu().getFileMenu()
					.getCloseAllFilesMenuItem().setEnabled(true);

			// Close all files in the project
			AcideMainWindow.getInstance().getMenu().getFileMenu()
					.getCloseAllFilesMenuItem().doClick();

			// Removes all the nodes in the explorer tree
			AcideMainWindow.getInstance().getExplorerPanel().getRoot()
					.removeAllChildren();

			// Reloads the explorer tree model
			AcideMainWindow.getInstance().getExplorerPanel().getTreeModel()
					.reload();

			// Disables the add file menu item in the explorer popup menu
			AcideMainWindow.getInstance().getExplorerPanel().getPopupMenu()
					.getAddFileMenuItem().setEnabled(false);

			// Disables the save project menu item in the explorer popup
			// menu
			AcideMainWindow.getInstance().getExplorerPanel().getPopupMenu()
					.getSaveProjectMenuItem().setEnabled(false);

			// Disables the remove file menu item in the explorer popup menu
			AcideMainWindow.getInstance().getExplorerPanel().getPopupMenu()
					.getRemoveFileMenuItem().setEnabled(false);

			// Disables the delete file menu item in the explorer popup menu
			AcideMainWindow.getInstance().getExplorerPanel().getPopupMenu()
					.getDeleteFileMenuItem().setEnabled(false);

			// Removes all the files related to the project
			AcideProjectConfiguration.getInstance().removeFiles();

			// Disables the project menu
			AcideMainWindow.getInstance().getMenu().getProjectMenu()
					.disableMenu();

			// Disables the open all files menu item
			AcideMainWindow.getInstance().getMenu().getFileMenu()
					.getOpenAllFilesMenuItem().setEnabled(false);

			// Disables the save project button in the menu bar tool bar
			AcideMainWindow.getInstance().getToolBarPanel().getMenuBarToolBar()
					.getSaveProjectButton().setEnabled(false);

			// Updates the status message in the status bar
			AcideMainWindow.getInstance().getStatusBar().setStatusMessage(" ");

			// Sets the default title to the project
			AcideMainWindow.getInstance().setTitle(
					AcideLanguageManager.getInstance().getLabels()
							.getString("s425")
							+ " - <empty>");

			// Updates the ACIDE - A Configurable IDE project configuration
			AcideResourceManager.getInstance().setProperty(
					"projectConfiguration",
					AcideProjectConfiguration.DEFAULT_PATH);

			// Sets the project name as empty
			AcideProjectConfiguration.getInstance().setName("");

			// Sets the project path

			// Updates the project configuration path with the ACIDE - A
			// Configurable IDE resource manager
			AcideProjectConfiguration.getInstance().setProjectPath(
					AcideResourceManager.getInstance().getProperty(
							"projectConfiguration"));

			// Closes the console panel
			AcideMainWindow.getInstance().getMenu().getConfigurationMenu()
					.getConsoleMenu().getCloseConsoleMenuItem().doClick();

			// The project has not been modified yet
			AcideProjectConfiguration.getInstance().setIsModified(false);

			// Updates the changes in the main window
			AcideMainWindow.getInstance().validate();

			// Repaints the main window
			AcideMainWindow.getInstance().repaint();

		} catch (MissedPropertyException exception) {

			// Updates the log
			AcideLog.getLog().error(exception.getMessage());
			exception.printStackTrace();
		}
	}

	/**
	 * Save the project configuration with other name into the file.
	 * 
	 * @param filePath
	 *            file path to save the new configuration.
	 */
	public void saveProjectAs(String filePath) {

		try {

			// Gets the new project name
			int lastIndexOfSlash = filePath.lastIndexOf("\\");
			if (lastIndexOfSlash == -1)
				lastIndexOfSlash = filePath.lastIndexOf("/");
			String newProjectName = filePath.substring(lastIndexOfSlash + 1,
					filePath.lastIndexOf("."));

			// Gets the current project name
			String currentProjectName = AcideProjectConfiguration.getInstance()
					.getName();

			// Sets the ACIDE - A Configurable IDE project configuration name
			AcideProjectConfiguration.getInstance().setName(newProjectName);

			// Sets the ACIDE - A Configurable IDE project configuration path
			AcideProjectConfiguration.getInstance().setProjectPath(filePath);

			// Updates the ACIDE - A Configurable IDE project configuration file
			// list
			AcideProjectConfiguration.getInstance()
					.updateFileListProjectConfiguration(currentProjectName,
							newProjectName);

			// Updates the explorer tree
			AcideMainWindow.getInstance().getExplorerPanel()
					.updateExplorerTree();

			// Updates the main window title
			AcideMainWindow.getInstance()
					.setTitle(
							AcideLanguageManager.getInstance().getLabels()
									.getString("s425")
									+ " - "
									+ AcideProjectConfiguration.getInstance()
											.getName());

			// Sets the ACIDE - A Configurable IDE language configuration
			AcideProjectConfiguration.getInstance().setLanguageConfiguration(
					AcideResourceManager.getInstance().getProperty("language"));

			// Sets the ACIDE - A Configurable current menu configuration
			AcideProjectConfiguration.getInstance().setMenuConfiguration(
					AcideResourceManager.getInstance().getProperty(
							"currentMenuConfiguration"));

			// Sets the ACIDE - A Configurable IDE current tool bar
			// configuration
			AcideProjectConfiguration.getInstance().setToolBarConfiguration(
					AcideResourceManager.getInstance().getProperty(
							"currentToolBarConfiguration"));

			// Sets the ACIDE - A Configurable IDE console panel shell path
			AcideProjectConfiguration.getInstance().setShellPath(
					AcideResourceManager.getInstance().getProperty(
							"consolePanel.shellPath"));

			// Sets the ACIDE - A Configurable IDE console panel shell
			// directory
			AcideProjectConfiguration.getInstance().setShellDirectory(
					AcideResourceManager.getInstance().getProperty(
							"consolePanel.shellDirectory"));

			// Sets the ACIDE - A Configurable IDE console panel exit
			// command
			AcideProjectConfiguration.getInstance().setExitCommand(
					AcideResourceManager.getInstance().getProperty(
							"consolePanel.exitCommand"));

			// Sets the ACIDE - A Configurable IDE console panel is echo
			// command
			AcideProjectConfiguration.getInstance().setIsEchoCommand(
					Boolean.parseBoolean(AcideResourceManager.getInstance()
							.getProperty("consolePanel.isEchoCommand")));

			// Sets the ACIDE - A Configurable IDE console panel foreground
			// color
			AcideProjectConfiguration.getInstance().setForegroundColor(
					new Color(Integer.parseInt(AcideResourceManager
							.getInstance().getProperty(
									"consolePanel.foregroundColor"))));

			// Sets the ACIDE - A Configurable IDE console panel background
			// color
			AcideProjectConfiguration.getInstance().setBackgroundColor(
					new Color(Integer.parseInt(AcideResourceManager
							.getInstance().getProperty(
									"consolePanel.backgroundColor"))));

			// Sets the ACIDE - A Configurable IDE console panel buffer
			// size
			AcideProjectConfiguration.getInstance().setBufferSize(
					Integer.parseInt(AcideResourceManager.getInstance()
							.getProperty("consolePanel.bufferSize")));

			// Sets the ACIDE - A Configurable IDE console panel font name
			AcideProjectConfiguration.getInstance().setFontName(
					AcideResourceManager.getInstance().getProperty(
							"consolePanel.fontName"));

			// Sets the ACIDE - A Configurable IDE console panel font style
			AcideProjectConfiguration.getInstance().setFontStyle(
					Integer.parseInt(AcideResourceManager.getInstance()
							.getProperty("consolePanel.fontStyle")));

			// Sets the ACIDE - A Configurable IDE console panel font size
			AcideProjectConfiguration.getInstance().setFontSize(
					Integer.parseInt(AcideResourceManager.getInstance()
							.getProperty("consolePanel.fontSize")));

			// It is the first time the project has not been saved
			AcideProjectConfiguration.getInstance().setIsFirstSave(true);

			// The project has not been modified yet
			AcideProjectConfiguration.getInstance().setIsModified(false);

			// Gets the file content
			String fileContent = AcideProjectConfiguration.getInstance().save();

			// Writes the file content on it
			AcideFileManager.getInstance().write(
					AcideProjectConfiguration.getInstance().getProjectPath(),
					fileContent);

			// Updates the ACIDE - A Configurable IDE project configuration
			AcideResourceManager.getInstance().setProperty(
					"projectConfiguration",
					AcideProjectConfiguration.getInstance().getProjectPath());

			// Adds the new project to the recent project list
			AcideWorkbenchConfiguration
					.getInstance()
					.getRecentProjectsConfiguration()
					.addRecentProjectToList(
							AcideProjectConfiguration.getInstance()
									.getProjectPath());

		} catch (Exception exception) {

			// Updates the log
			AcideLog.getLog().error(exception.getMessage());
			exception.printStackTrace();
		}
	}

	/**
	 * Updates the ACIDE - A Configurable IDE project menu.
	 */
	public void configure() {

		// If it is not the default project
		if (!AcideProjectConfiguration.getInstance().isDefaultProject()) {

			// Disables the remove file menu item
			_removeFileMenuItem.setEnabled(false);

			// Disables the delete file menu item
			_deleteFileMenuItem.setEnabled(false);

			// Disables the set compilable menu item
			_setCompilableFileMenuItem.setEnabled(false);

			// Disables the unset compilable menu item
			_unsetCompilableFileMenuItem.setEnabled(false);

			// Disables the set main menu item
			_setMainFileMenuItem.setEnabled(false);

			// Disables the unset main menu item
			_unsetMainFileMenuItem.setEnabled(false);

			// If there are opened file editors
			if (AcideMainWindow.getInstance().getFileEditorManager()
					.getSelectedFileEditorPanelIndex() != -1) {

				// Gets the selected file editor panel
				AcideFileEditorPanel selectedFileEditorPanel = AcideMainWindow
						.getInstance().getFileEditorManager()
						.getSelectedFileEditorPanel();

				// Checks if the selected file editor panel belongs to the
				// project
				AcideProjectFile projectFile = AcideProjectConfiguration
						.getInstance().getFileAt(
								selectedFileEditorPanel.getAbsolutePath());

				// If it is not the NEW FILE or the LOG TAB and belongs to the
				// project
				if (!AcideMainWindow.getInstance().getFileEditorManager()
						.getSelectedFileEditorPanel().isNewFile()
						&& !AcideMainWindow.getInstance()
								.getFileEditorManager()
								.getSelectedFileEditorPanel().isLogFile()
						&& projectFile != null) {

					// If it is not a MAIN FILE
					if (!selectedFileEditorPanel.isMainFile())

						// Enables the set main file menu item
						_setMainFileMenuItem.setEnabled(true);
					else
						// Enables the unset main file menu item
						_unsetMainFileMenuItem.setEnabled(true);

					// If it is not a COMPILABLE FILE or is COMPILABLE and a
					// MAIN FILE
					if (!selectedFileEditorPanel.isCompilableFile()
							|| (selectedFileEditorPanel.isCompilableFile() && selectedFileEditorPanel
									.isMainFile()))

						// Enables the set compilable file menu item
						_setCompilableFileMenuItem.setEnabled(true);

					// If it is COMPILABLE FILE and it is not a MAIN FILE
					if (selectedFileEditorPanel.isCompilableFile()
							&& !selectedFileEditorPanel.isMainFile())

						// Enables the unset compilable file menu item
						_unsetCompilableFileMenuItem.setEnabled(true);

					// Enables the delete file menu item
					_deleteFileMenuItem.setEnabled(true);

					// Enables the remove file menu item
					_removeFileMenuItem.setEnabled(true);

				} else

				// If the selected file editor panel does not belong to the
				// project
				if (projectFile == null) {

					// Disables the remove file menu item
					_removeFileMenuItem.setEnabled(false);

					// Enables the delete file menu item
					_deleteFileMenuItem.setEnabled(false);
				}
			}

			// Gets the tree path
			TreePath path = AcideMainWindow.getInstance().getExplorerPanel()
					.getTree().getSelectionPath();
			DefaultMutableTreeNode filePath;
			AcideProjectFile file = null;

			if (path != null) {

				// Gets the file path from the explorer tree
				filePath = (DefaultMutableTreeNode) path.getLastPathComponent();

				// Builds the project file
				file = (AcideProjectFile) filePath.getUserObject();

				// Enables or disables the remove folder menu item
				_removeFolderMenuItem.setEnabled(file.isDirectory());
			} else
				// Disables the remove folder menu item
				_removeFolderMenuItem.setEnabled(false);

			// Enables the project menu
			AcideMainWindow.getInstance().getMenu().getProjectMenu()
					.enableMenu();

			// Enables or disables the save project menu item
			_saveProjectMenuItem.setEnabled(AcideProjectConfiguration
					.getInstance().isModified());
		} else {

			// Disables the project menu
			AcideMainWindow.getInstance().getMenu().getProjectMenu()
					.disableMenu();

		}
	}

	/**
	 * Returns the ACIDE - A Configurable IDE project menu new project menu
	 * item.
	 * 
	 * @return the ACIDE - A Configurable IDE project menu new project menu
	 *         item.
	 */
	public JMenuItem getNewProjectMenuItem() {
		return _newProjectMenuItem;
	}

	/**
	 * Returns the ACIDE - A Configurable IDE project menu save project menu
	 * item.
	 * 
	 * @return the ACIDE - A Configurable IDE project menu save project menu
	 *         item.
	 */
	public JMenuItem getSaveProjectMenuItem() {
		return _saveProjectMenuItem;
	}

	/**
	 * Returns the ACIDE - A Configurable IDE project menu open project menu
	 * item.
	 * 
	 * @return the ACIDE - A Configurable IDE project menu open project menu
	 *         item.
	 */
	public JMenuItem getOpenProjectMenuItem() {
		return _openProjectMenuItem;
	}

	/**
	 * Returns the ACIDE - A Configurable IDE project menu new project file menu
	 * item.
	 * 
	 * @return the ACIDE - A Configurable IDE project menu new project file menu
	 *         item.
	 */
	public JMenuItem getNewProjectFileMenuItem() {
		return _newProjectFileMenuItem;
	}

	/**
	 * Returns the ACIDE - A Configurable IDE project menu add file menu item.
	 * 
	 * @return the ACIDE - A Configurable IDE project menu add file menu item.
	 */
	public JMenuItem getAddFileMenuItem() {
		return _addFileMenuItem;
	}

	/**
	 * Returns the ACIDE - A Configurable IDE project menu add folder menu item.
	 * 
	 * @return the ACIDE - A Configurable IDE project menu add folder menu item.
	 */
	public JMenuItem getAddFolderMenuItem() {
		return _addFolderMenuItem;
	}

	/**
	 * Returns the ACIDE - A Configurable IDE project menu compile menu item.
	 * 
	 * @return the ACIDE - A Configurable IDE project menu compile menu item.
	 */
	public JMenuItem getCompileMenuItem() {
		return _compileMenuItem;
	}

	/**
	 * Returns the ACIDE - A Configurable IDE project menu execute menu item.
	 * 
	 * @return the ACIDE - A Configurable IDE project menu execute menu item.
	 */
	public JMenuItem getExecuteMenuItem() {
		return _executeMenuItem;
	}

	/**
	 * Returns the ACIDE - A Configurable IDE project menu remove folder menu
	 * item.
	 * 
	 * @return the ACIDE - A Configurable IDE project menu remove folder menu
	 *         item.
	 */
	public JMenuItem getRemoveFolderMenuItem() {
		return _removeFolderMenuItem;
	}

	/**
	 * Returns the ACIDE - A Configurable IDE project menu remove file menu
	 * item.
	 * 
	 * @return the ACIDE - A Configurable IDE project menu remove file menu
	 *         item.
	 */
	public JMenuItem getRemoveFileMenuItem() {
		return _removeFileMenuItem;
	}

	/**
	 * Returns the ACIDE - A Configurable IDE project menu close project menu
	 * item.
	 * 
	 * @return the ACIDE - A Configurable IDE project menu close project menu
	 *         item.
	 */
	public JMenuItem getCloseProjectMenuItem() {
		return _closeProjectMenuItem;
	}

	/**
	 * Returns the ACIDE - A Configurable IDE project menu set compilable file
	 * menu item.
	 * 
	 * @return the ACIDE - A Configurable IDE project menu set compilable file
	 *         menu item.
	 */
	public JMenuItem getSetCompilableFileMenuItem() {
		return _setCompilableFileMenuItem;
	}

	/**
	 * Returns the ACIDE - A Configurable IDE project menu set main file menu
	 * item.
	 * 
	 * @return the ACIDE - A Configurable IDE project menu set main file menu
	 *         item.
	 */
	public JMenuItem getSetMainFileMenuItem() {
		return _setMainFileMenuItem;
	}

	/**
	 * Returns the ACIDE - A Configurable IDE project menu unset compilable file
	 * menu item.
	 * 
	 * @return the ACIDE - A Configurable IDE project menu unset compilable file
	 *         menu item.
	 */
	public JMenuItem getUnsetCompilableFileMenuItem() {
		return _unsetCompilableFileMenuItem;
	}

	/**
	 * Returns the ACIDE - A Configurable IDE project menu save as project menu
	 * item.
	 * 
	 * @return the ACIDE - A Configurable IDE project menu save as project menu
	 *         item.
	 */
	public JMenuItem getSaveAsProjectMenuItem() {
		return _saveProjectAsMenuItem;
	}

	/**
	 * Returns the ACIDE - A Configurable IDE project menu delete file menu
	 * item.
	 * 
	 * @return the ACIDE - A Configurable IDE project menu delete file menu
	 *         item.
	 */
	public JMenuItem getDeleteFileMenuItem() {
		return _deleteFileMenuItem;
	}

	/**
	 * Returns the ACIDE - A Configurable IDE project menu unset main file menu
	 * item.
	 * 
	 * @return the ACIDE - A Configurable IDE project menu unset main file menu
	 *         item.
	 */
	public JMenuItem getUnsetMainFileMenuItem() {
		return _unsetMainFileMenuItem;
	}

	/**
	 * Returns the ACIDE - A Configurable IDE project menu open recent projects
	 * menu.
	 * 
	 * @return the ACIDE - A Configurable IDE project menu open recent projects
	 *         menu.
	 */
	public AcideRecentProjectsMenu getOpenRecentProjectsMenu() {
		return _openRecentProjectsMenu;
	}

	/**
	 * Returns the ACIDE - A Configurable IDE project menu add opened files menu
	 * item.
	 * 
	 * @return the ACIDE - A Configurable IDE project menu add opened files menu
	 *         item.
	 */
	public JMenuItem getAddOpenedFilesMenuItem() {
		return _addOpenedFilesMenuItem;
	}
}