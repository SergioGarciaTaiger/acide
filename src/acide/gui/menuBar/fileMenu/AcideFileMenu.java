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
package acide.gui.menuBar.fileMenu;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;

import javax.swing.ImageIcon;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JSeparator;
import javax.swing.KeyStroke;

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
import acide.configuration.workbench.AcideWorkbenchConfiguration;
import acide.files.AcideFileManager;
import acide.files.project.AcideProjectFileType;
import acide.gui.mainWindow.AcideMainWindow;
import acide.gui.menuBar.fileMenu.recentFilesMenu.AcideRecentFilesMenu;
import acide.gui.menuBar.listeners.AcideMenuBarMouseClickListener;
import acide.language.AcideLanguageManager;
import acide.log.AcideLog;
import acide.resources.AcideResourceManager;
import acide.utils.IconsUtils;

/**
 * ACIDE - A Configurable IDE file menu.
 * 
 * @version 0.11
 * @see JMenu
 */
public class AcideFileMenu extends JMenu {

	/**
	 * ACIDE - A Configurable IDE file menu class serial version UID.
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * ACIDE - A Configurable IDE file menu name.
	 */
	public final static String FILE_MENU_NAME = "File";
	/**
	 * ACIDE - A Configurable IDE file menu new file menu item name.
	 */
	public final static String NEW_FILE_NAME = "New file";
	/**
	 * ACIDE - A Configurable IDE file menu open file menu item name.
	 */
	public final static String OPEN_FILE_NAME = "Open File";
	/**
	 * ACIDE - A Configurable IDE file menu open recent files menu item name.
	 */
	public final static String OPEN_RECENT_FILES_NAME = "Open Recent Files";
	/**
	 * ACIDE - A Configurable IDE file menu open all files menu item name.
	 */
	public final static String OPEN_ALL_FILES_NAME = "Open All Files";
	/**
	 * ACIDE - A Configurable IDE file menu close file menu item name.
	 */
	public final static String CLOSE_FILE_NAME = "Close File";
	/**
	 * ACIDE - A Configurable IDE file menu close all files menu item name.
	 */
	public final static String CLOSE_ALL_FILES_NAME = "Close All Files";
	/**
	 * ACIDE - A Configurable IDE file menu save file menu item name.
	 */
	public final static String SAVE_FILE_NAME = "Save File";
	/**
	 * ACIDE - A Configurable IDE file menu save all files menu item name.
	 */
	public final static String SAVE_ALL_FILES_NAME = "Save All Files";
	/**
	 * ACIDE - A Configurable IDE file menu save file menu item name.
	 */
	public final static String SAVE_FILE_AS_NAME = "Save File As";
	/**
	 * ACIDE - A Configurable IDE file menu print file menu item name.
	 */
	public final static String PRINT_FILE_NAME = "Print File";
	/**
	 * ACIDE - A Configurable IDE file menu exit menu item name.
	 */
	public final static String EXIT_NAME = "Exit File";
	/**
	 * ACIDE - A Configurable IDE file menu new file menu item.
	 */
	private JMenuItem _newFileMenuItem;
	/**
	 * ACIDE - A Configurable IDE file menu new file menu item has been inserted.
	 */
	private boolean _newFileInserted;
	/**
	 * ACIDE - A Configurable IDE file menu open file menu item.
	 */
	private JMenuItem _openFileMenuItem;
	/**
	 * ACIDE - A Configurable IDE file menu open file menu item has been inserted.
	 */
	private boolean _openFileInserted;
	/**
	 * ACIDE - A Configurable IDE file menu open recent files menu.
	 */
	private AcideRecentFilesMenu _openRecentFilesMenu;
	/**
	 * ACIDE - A Configurable IDE file menu open recent files menu item has been inserted.
	 */
	private boolean _openRecentFilesInserted;
	/**
	 * ACIDE - A Configurable IDE file menu open all files menu item.
	 */
	private JMenuItem _openAllFilesMenuItem;
	/**
	 * ACIDE - A Configurable IDE file menu open all files menu item has been inserted.
	 */
	private boolean _openAllFilesInserted;
	/**
	 * ACIDE - A Configurable IDE file menu close file menu item.
	 */
	private JMenuItem _closeFileMenuItem;
	/**
	 * ACIDE - A Configurable IDE file menu close file menu item has been inserted.
	 */
	private boolean _closeFileInserted;
	/**
	 * ACIDE - A Configurable IDE file menu close all files menu item.
	 */
	private JMenuItem _closeAllFilesMenuItem;
	/**
	 * ACIDE - A Configurable IDE file menu close all files menu item has been inserted.
	 */
	private boolean _closeAllFilesInserted;
	/**
	 * ACIDE - A Configurable IDE file menu save file as menu item.
	 */
	private JMenuItem _saveFileAsMenuItem;
	/**
	 * ACIDE - A Configurable IDE file menu save file as menu item has been inserted.
	 */
	private boolean _saveFileAsInserted;
	/**
	 * ACIDE - A Configurable IDE file menu save file menu item.
	 */
	private JMenuItem _saveFileMenuItem;
	/**
	 * ACIDE - A Configurable IDE file menu save file menu item has been inserted.
	 */
	private boolean _saveFileInserted;
	/**
	 * ACIDE - A Configurable IDE file menu save all file menu item.
	 */
	private JMenuItem _saveAllFilesMenuItem;
	/**
	 * ACIDE - A Configurable IDE file menu save all file menu item has been inserted.
	 */
	private boolean _saveAllFilesInserted;
	/**
	 * ACIDE - A Configurable IDE file menu print file menu item.
	 */
	private JMenuItem _printFileMenuItem;
	/**
	 * ACIDE - A Configurable IDE file menu print file menu item has been inserted.
	 */
	private boolean _printFileInserted;
	/**
	 * ACIDE - A Configurable IDE file menu exit menu item.
	 */
	private JMenuItem _exitMenuItem;
	/**
	 * ACIDE - A Configurable IDE file menu exit menu item has been inserted.
	 */
	private boolean _exitInserted;
	/**
	 * ACIDE - A Configurable IDE file menu close all files save file separator.
	 */
	private JSeparator _closeAllFilesSaveFileSeparator;
	/**
	 * ACIDE - A Configurable IDE file menu save all files print file separator.
	 */
	private JSeparator _saveAllFilesPrintFileSeparator;
	/**
	 * ACIDE - A Configurable IDE file menu print file exit separator.
	 */
	private JSeparator _printFileExitSeparator;
	/**
	 * ACIDE - A Configurable IDE file menu configuration menu.
	 */
	private AcideMenuSubmenuConfiguration _fileSubmenuConfiguration;
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
	 * Creates a new ACIDE - A Configurable IDE file menu.
	 */
	public AcideFileMenu() {
		
		_openFileInserted = false;
		_openRecentFilesInserted = false;
		_openAllFilesInserted = false;
		_closeFileInserted = false;
		_closeAllFilesInserted = false;
		_saveFileAsInserted = false;
		_saveFileInserted = false;
		_saveAllFilesInserted = false;
		_printFileInserted = false;
		_exitInserted = false;

		_insertedItems = new HashMap<String, AcideInsertedItem>();
		
		_insertedMenus = new HashMap<String, AcideInsertedMenu>();
		
		_insertedObjects = new ArrayList<AcideMenuObjectConfiguration>();
		
		// Builds the menu components
		buildComponents();

		// Adds the components to the menu
		addComponents();

		// Sets the text of the file menu components
		setTextOfMenuComponents();
	}

	/**
	 * Adds the components to the ACIDE - A Configurable IDE file menu.
	 */
	private void addComponents() {
		
		Iterator<Object> it = AcideMenuItemsConfiguration.getInstance()
				.getMenuItemsManager().getSubmenu(FILE_MENU_NAME).getItemsManager().managerIterator();
		
		while (it.hasNext()){
			AcideMenuObjectConfiguration ob = (AcideMenuObjectConfiguration) it.next();
			String name = ob.getName();
			if (name.equals(NEW_FILE_NAME)){
				// Adds the new file menu item to the file menu
				add(_newFileMenuItem);
				_newFileInserted = true;
			}else if (name.equals(OPEN_FILE_NAME)){
				// Adds the open file menu item to the file menu
				add(_openFileMenuItem);
				_openFileInserted = true;
			}else if (name.equals(OPEN_RECENT_FILES_NAME)){
				// Adds the open recent files menu item to the file menu
				add(_openRecentFilesMenu);
				_openRecentFilesInserted = true;
			}else if (name.equals(OPEN_ALL_FILES_NAME)){
				// Adds the open all files menu item to the file menu
				add(_openAllFilesMenuItem);
				_openAllFilesInserted = true;
			} else if (name.equals(CLOSE_FILE_NAME)){
				// Adds the close file menu item to the file menu
				add(_closeFileMenuItem);
				_closeFileInserted = true;
			}else if (name.equals(CLOSE_ALL_FILES_NAME)){
				// Adds the close all files menu item to the file menu
				add(_closeAllFilesMenuItem);
				_closeAllFilesInserted = true;
				// Adds the close all files save file separator to the file menu
				add(_closeAllFilesSaveFileSeparator);
			}else if (name.equals(SAVE_FILE_NAME)){
				// Adds the save file menu item to the file menu
				add(_saveFileMenuItem);
				_saveFileInserted = true;
			}else if (name.equals(SAVE_FILE_AS_NAME)){
				// Adds the save file as menu item to the file menu
				add(_saveFileAsMenuItem);
				_saveFileAsInserted = true;
			}else if (name.equals(SAVE_ALL_FILES_NAME)){
				// Adds the save all files menu item to the file menu
				add(_saveAllFilesMenuItem);
				_saveAllFilesInserted = true;
				// Adds the save all files print file separator to the file
				add(_saveAllFilesPrintFileSeparator);
			}else if (name.equals(PRINT_FILE_NAME)){
				// Adds the print file menu item to the file menu
				add(_printFileMenuItem);
				_printFileInserted = true;
				// Adds the print file exit separator to the file menu
				add(_printFileExitSeparator);
			}else if (name.equals(EXIT_NAME)){
				// Adds the exit menu item to the file menu
				add(_exitMenuItem);
				_exitInserted = true;
			} else {
				if (ob.isSubmenu()){
					add(_insertedMenus.get(ob.getName()));
				}else{
					add(_insertedItems.get(ob.getName()));
				}
			}
		}
		
		
		if (!_newFileInserted)
			add(_newFileMenuItem);
		if (!_openFileInserted)
			add(_openFileMenuItem);
		if (!_openRecentFilesInserted)
			add(_openRecentFilesMenu);
		if (!_openAllFilesInserted)
			add(_openAllFilesMenuItem);
		if (!_closeFileInserted)
			add(_closeFileMenuItem);
		if (!_closeAllFilesInserted)
			add(_closeAllFilesMenuItem);
		if (!_saveFileInserted)
			add(_saveFileMenuItem);
		if (!_saveFileAsInserted)
			add(_saveFileAsMenuItem);
		if (!_saveAllFilesInserted)
			add(_saveAllFilesMenuItem);
		if (!_printFileInserted)
			add(_printFileMenuItem);
		if (!_exitInserted)
			add(_exitMenuItem);
		
	}

	/**
	 * Builds the ACIDE - A Configurable IDE file menu components.
	 */
	private void buildComponents() {
		
		if (!AcideMenuItemsConfiguration.getInstance().getMenuItemsManager().hasSubmenu(FILE_MENU_NAME)){
			AcideMenuItemsConfiguration.getInstance()
				.insertObject(new AcideMenuSubmenuConfiguration(FILE_MENU_NAME));
		}
		Iterator<Object> it = AcideMenuItemsConfiguration.getInstance()
				.getMenuItemsManager().getSubmenu(FILE_MENU_NAME).getItemsManager().managerIterator();
		
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
		
		// Creates the new file menu item
		ImageIcon icon = IconsUtils.getIcon(AcideMenuItemsConfiguration.getInstance()
				.getMenuItemsManager().getSubmenu(FILE_MENU_NAME)
				.getItem(NEW_FILE_NAME).getImage());
		
		if (icon != null)
			_newFileMenuItem = new JMenuItem(icon);
		else
			_newFileMenuItem = new JMenuItem();

		// Sets the new file menu item name
		_newFileMenuItem.setName(NEW_FILE_NAME);

		
		// Creates the open file menu item
		icon = IconsUtils.getIcon(AcideMenuItemsConfiguration.getInstance()
				.getMenuItemsManager().getSubmenu(FILE_MENU_NAME)
				.getItem(OPEN_FILE_NAME).getImage());
		
		if (icon != null)
			_openFileMenuItem = new JMenuItem(icon);
		else
			_openFileMenuItem = new JMenuItem();
			

		// Sets the open file menu item name
		_openFileMenuItem.setName(OPEN_FILE_NAME);

		// Creates the open recent files menu item
		_openRecentFilesMenu = new AcideRecentFilesMenu();

		// Sets the open recent files menu item name
		_openRecentFilesMenu.setName(OPEN_RECENT_FILES_NAME);

		// Creates the open all files menu item
		icon = IconsUtils.getIcon(AcideMenuItemsConfiguration.getInstance()
				.getMenuItemsManager().getSubmenu(FILE_MENU_NAME)
				.getItem(OPEN_ALL_FILES_NAME).getImage());
		
		if (icon != null)
			_openAllFilesMenuItem = new JMenuItem(icon);
		else
			_openAllFilesMenuItem = new JMenuItem();

		// Sets the open all files menu item name
		_openAllFilesMenuItem.setName(OPEN_ALL_FILES_NAME);

		// Creates the close file menu item
		icon = IconsUtils.getIcon(AcideMenuItemsConfiguration.getInstance()
				.getMenuItemsManager().getSubmenu(FILE_MENU_NAME)
				.getItem(CLOSE_FILE_NAME).getImage());
		
		if (icon != null)
			_closeFileMenuItem = new JMenuItem(icon);
		else
			_closeFileMenuItem = new JMenuItem();

		// Sets the close file menu item name
		_closeFileMenuItem.setName(CLOSE_FILE_NAME);

		// Creates the all files menu item
		icon = IconsUtils.getIcon(AcideMenuItemsConfiguration.getInstance()
				.getMenuItemsManager().getSubmenu(FILE_MENU_NAME)
				.getItem(CLOSE_ALL_FILES_NAME).getImage());
		
		if (icon != null)
			_closeAllFilesMenuItem = new JMenuItem(icon);
		else
			_closeAllFilesMenuItem = new JMenuItem();

		// Sets the close all files menu item name
		_closeAllFilesMenuItem.setName(CLOSE_ALL_FILES_NAME);

		// Creates the close all files save file separator
		_closeAllFilesSaveFileSeparator = new JSeparator();

		// Creates the save file as menu item
		icon = IconsUtils.getIcon(AcideMenuItemsConfiguration.getInstance()
				.getMenuItemsManager().getSubmenu(FILE_MENU_NAME)
				.getItem(SAVE_FILE_AS_NAME).getImage());
		
		if (icon != null)
			_saveFileAsMenuItem = new JMenuItem(icon);
		else
			_saveFileAsMenuItem = new JMenuItem();

		// Sets the save file as menu item name
		_saveFileAsMenuItem.setName(SAVE_FILE_AS_NAME);

		// Creates the save all files menu item
		icon = IconsUtils.getIcon(AcideMenuItemsConfiguration.getInstance()
				.getMenuItemsManager().getSubmenu(FILE_MENU_NAME)
				.getItem(SAVE_ALL_FILES_NAME).getImage());
		
		if (icon != null)
			_saveAllFilesMenuItem = new JMenuItem(icon);
		else
			_saveAllFilesMenuItem = new JMenuItem();

		// Sets the save all files menu item name
		_saveAllFilesMenuItem.setName(SAVE_ALL_FILES_NAME);

		// Creates the save all files print file separator
		_saveAllFilesPrintFileSeparator = new JSeparator();

		// Creates the save file menu item
		icon = IconsUtils.getIcon(AcideMenuItemsConfiguration.getInstance()
				.getMenuItemsManager().getSubmenu(FILE_MENU_NAME)
				.getItem(SAVE_FILE_NAME).getImage());
		
		if (icon != null)
			_saveFileMenuItem = new JMenuItem(icon);
		else
			_saveFileMenuItem = new JMenuItem();

		// Sets the save file menu item name
		_saveFileMenuItem.setName(SAVE_FILE_NAME);

		// Creates the print file menu item
		icon = IconsUtils.getIcon(AcideMenuItemsConfiguration.getInstance()
				.getMenuItemsManager().getSubmenu(FILE_MENU_NAME)
				.getItem(PRINT_FILE_NAME).getImage());
		
		if (icon != null)
			_printFileMenuItem = new JMenuItem(icon);
		else
			_printFileMenuItem = new JMenuItem();

		// Sets the print file menu item name
		_printFileMenuItem.setName(PRINT_FILE_NAME);

		// Creates the print file exit separator
		_printFileExitSeparator = new JSeparator();

		// Creates the exit menu item
		icon = IconsUtils.getIcon(AcideMenuItemsConfiguration.getInstance()
				.getMenuItemsManager().getSubmenu(FILE_MENU_NAME)
				.getItem(EXIT_NAME).getImage());
		
		if (icon != null)
			_exitMenuItem = new JMenuItem(icon);
		else
			_exitMenuItem = new JMenuItem();

		// Sets the exit menu item name
		_exitMenuItem.setName(EXIT_NAME);
	}

	/**
	 * Sets the text of the ACIDE - A Configurable IDE file menu components with
	 * the labels in the selected language to display.
	 */
	public void setTextOfMenuComponents() {

		// Sets the new file menu item text
		_newFileMenuItem.setText(AcideLanguageManager.getInstance().getLabels()
				.getString("s8"));

		// Sets the new file menu item accelerator
		_newFileMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N,
				ActionEvent.CTRL_MASK));

		// Sets the open file menu item text
		_openFileMenuItem.setText(AcideLanguageManager.getInstance()
				.getLabels().getString("s9"));

		// Sets the open file menu item accelerator
		if (AcideLanguageManager.getInstance().getCurrentLocale()
				.equals(new Locale("en", "EN"))
			|| AcideLanguageManager.getInstance().getCurrentLocale()
				.equals(new Locale("fr", "FR")))
			_openFileMenuItem.setAccelerator(KeyStroke.getKeyStroke(
					KeyEvent.VK_O, ActionEvent.CTRL_MASK));
		else
			_openFileMenuItem.setAccelerator(KeyStroke.getKeyStroke(
					KeyEvent.VK_A, ActionEvent.CTRL_MASK));

		// Sets the open recent files menu item text
		_openRecentFilesMenu.setText(AcideLanguageManager.getInstance()
				.getLabels().getString("s1038"));

		// Sets the open all files menu item text
		_openAllFilesMenuItem.setText(AcideLanguageManager.getInstance()
				.getLabels().getString("s1004"));

		// Sets the close file menu item text
		_closeFileMenuItem.setText(AcideLanguageManager.getInstance()
				.getLabels().getString("s238"));

		// Sets the close all files menu item text
		_closeAllFilesMenuItem.setText(AcideLanguageManager.getInstance()
				.getLabels().getString("s239"));

		// Sets the save file as menu item text
		_saveFileAsMenuItem.setText(AcideLanguageManager.getInstance()
				.getLabels().getString("s10"));

		// Sets the save file menu item text
		_saveFileMenuItem.setText(AcideLanguageManager.getInstance()
				.getLabels().getString("s617"));

		// Sets the save file menu item accelerator
		if (AcideLanguageManager.getInstance().getCurrentLocale()
				.equals(new Locale("en", "EN"))
			|| AcideLanguageManager.getInstance().getCurrentLocale()
				.equals(new Locale("fr", "FR")))
			_saveFileMenuItem.setAccelerator(KeyStroke.getKeyStroke(
					KeyEvent.VK_S, ActionEvent.CTRL_MASK));
		else
			_saveFileMenuItem.setAccelerator(KeyStroke.getKeyStroke(
					KeyEvent.VK_G, ActionEvent.CTRL_MASK));

		// Sets the save all files menu item text
		_saveAllFilesMenuItem.setText(AcideLanguageManager.getInstance()
				.getLabels().getString("s217"));

		// Sets the save all files menu item accelerator
		if (AcideLanguageManager.getInstance().getCurrentLocale()
				.equals(new Locale("en", "EN"))
			|| AcideLanguageManager.getInstance().getCurrentLocale()
				.equals(new Locale("fr", "FR")))
			_saveAllFilesMenuItem.setAccelerator(KeyStroke.getKeyStroke(
					KeyEvent.VK_S, ActionEvent.CTRL_MASK
							+ ActionEvent.SHIFT_MASK));
		else
			_saveAllFilesMenuItem.setAccelerator(KeyStroke.getKeyStroke(
					KeyEvent.VK_G, ActionEvent.CTRL_MASK
							+ ActionEvent.SHIFT_MASK));

		// Sets the print file menu item text
		_printFileMenuItem.setText(AcideLanguageManager.getInstance()
				.getLabels().getString("s624"));

		// Sets the print file menu item accelerator
		_printFileMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P,
				ActionEvent.CTRL_MASK));

		// Sets the exit menu item text
		_exitMenuItem.setText(AcideLanguageManager.getInstance().getLabels()
				.getString("s13"));

		// Sets the exit file menu item accelerator
		_exitMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X,
				ActionEvent.ALT_MASK));

		// Disables the open all files menu item
		_openAllFilesMenuItem.setEnabled(false);

		// Disables the close file menu item
		_closeFileMenuItem.setEnabled(false);

		// Disables the close all files menu item
		_closeAllFilesMenuItem.setEnabled(false);

		// Disables the save file as menu item
		_saveFileAsMenuItem.setEnabled(false);

		// Disables the save file menu item
		_saveFileMenuItem.setEnabled(false);

		// Disables the save all files menu item
		_saveAllFilesMenuItem.setEnabled(false);

		// Disables the print file menu item
		_printFileMenuItem.setEnabled(false);
		
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
	 * Updates the ACIDE - A Configurable IDE file menu components visibility
	 * with the menu configuration.
	 */
	public void updateComponentsVisibiliy() {
		
		AcideMenuItemConfiguration newFileConfiguration;
		AcideMenuItemConfiguration openFileConfiguration;
		AcideMenuSubmenuConfiguration openRecentFilesConfiguration;
		AcideMenuItemConfiguration openAllFilesConfiguration;
		AcideMenuItemConfiguration closeFileConfiguration;
		AcideMenuItemConfiguration closeAllFilesConfiguration;
		AcideMenuItemConfiguration saveFileConfiguration;
		AcideMenuItemConfiguration saveFileAsConfiguration;
		AcideMenuItemConfiguration saveAllFilesConfiguration;
		AcideMenuItemConfiguration printFileConfiguration;
		AcideMenuItemConfiguration exitConfiguration;
		
		_fileSubmenuConfiguration = AcideMenuItemsConfiguration.getInstance().getSubmenu(FILE_MENU_NAME);
			
		// Sets the new file menu item as visible or not visible
		newFileConfiguration = _fileSubmenuConfiguration.getItem(NEW_FILE_NAME);
		_newFileMenuItem.setVisible(newFileConfiguration.isVisible());	
			
		// Sets the open file menu item as visible or not visible
		openFileConfiguration = _fileSubmenuConfiguration.getItem(OPEN_FILE_NAME);
		_openFileMenuItem.setVisible(openFileConfiguration.isVisible());

		// Sets the open recent files menu item as visible or not visible
		openRecentFilesConfiguration = _fileSubmenuConfiguration.getSubmenu(OPEN_RECENT_FILES_NAME);
		_openRecentFilesMenu.setVisible(openRecentFilesConfiguration.isVisible());
			
		// Sets the open all files menu item as visible or not visible
		openAllFilesConfiguration = _fileSubmenuConfiguration.getItem(OPEN_ALL_FILES_NAME);
		_openAllFilesMenuItem.setVisible(openAllFilesConfiguration.isVisible());
			
		// Sets the close file menu item as visible or not visible
		closeFileConfiguration = _fileSubmenuConfiguration.getItem(CLOSE_FILE_NAME);
		_closeFileMenuItem.setVisible(closeFileConfiguration.isVisible());
			
		// Sets the close all files menu item as visible or not visible
		closeAllFilesConfiguration = _fileSubmenuConfiguration.getItem(CLOSE_ALL_FILES_NAME);
		_closeAllFilesMenuItem.setVisible(closeAllFilesConfiguration.isVisible());
		
		// Sets the save file menu item as visible or not visible
		saveFileConfiguration = _fileSubmenuConfiguration.getItem(SAVE_FILE_NAME);
		_saveFileMenuItem.setVisible(saveFileConfiguration.isVisible());
			
		// Sets the save file as menu item as visible or not visible
		saveFileAsConfiguration = _fileSubmenuConfiguration.getItem(SAVE_FILE_AS_NAME);
		_saveFileAsMenuItem.setVisible(saveFileAsConfiguration.isVisible());
			
		// Sets the save all files menu item as visible or not visible
		saveAllFilesConfiguration = _fileSubmenuConfiguration.getItem(SAVE_ALL_FILES_NAME);
		_saveAllFilesMenuItem.setVisible(saveAllFilesConfiguration.isVisible());
			
		// Sets the close all files save file separator
		// to visible or not visible
		_closeAllFilesSaveFileSeparator.setVisible((_newFileMenuItem.isVisible()
				|| _openFileMenuItem.isVisible()
				|| _closeFileMenuItem.isVisible() || _closeAllFilesMenuItem.isVisible())
				&& (_saveFileMenuItem.isVisible()
					|| _saveFileAsMenuItem.isVisible() || _saveAllFilesMenuItem.isVisible()));
			
		// Sets the print file menu item as visible or not visible
		printFileConfiguration = _fileSubmenuConfiguration.getItem(PRINT_FILE_NAME);
		_printFileMenuItem.setVisible(printFileConfiguration.isVisible());

			
		// Sets the save all files print file separator to visible or not
		// visible
		_saveAllFilesPrintFileSeparator.setVisible((_newFileMenuItem.isVisible()
				|| _openFileMenuItem.isVisible()
				|| _closeFileMenuItem.isVisible()
				|| _closeAllFilesMenuItem.isVisible()
				|| _saveFileMenuItem.isVisible()
				|| _saveFileAsMenuItem.isVisible() || _saveAllFilesMenuItem.isVisible())
				&& (_printFileMenuItem.isVisible()));
			
		// Sets the exit menu item as visible or not visible
		exitConfiguration = _fileSubmenuConfiguration.getItem(EXIT_NAME);
		_exitMenuItem.setVisible(exitConfiguration.isVisible());
			
		// Sets the print file exit separator to visible or not visible
		_printFileExitSeparator.setVisible((_newFileMenuItem.isVisible()
				|| _saveFileAsMenuItem.isVisible()
				|| _saveFileMenuItem.isVisible()
				|| _saveAllFilesMenuItem.isVisible()
				|| _printFileMenuItem.isVisible()
				|| _closeFileMenuItem.isVisible() || _closeAllFilesMenuItem.isVisible())
				&& _exitMenuItem.isVisible());
		

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
		
		_fileSubmenuConfiguration.setVisible(_newFileMenuItem.isVisible()
				|| _openFileMenuItem.isVisible() || _openAllFilesMenuItem.isVisible()
				|| _saveFileAsMenuItem.isVisible() || _saveFileMenuItem.isVisible()
				|| _saveAllFilesMenuItem.isVisible() || _printFileMenuItem.isVisible()
				|| _exitMenuItem.isVisible());
		_fileSubmenuConfiguration.setErasable(false);
		
		
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
		if (!(name.equals(NEW_FILE_NAME))
			&& !(name.equals(OPEN_FILE_NAME)) 
			&& !(name.equals(OPEN_ALL_FILES_NAME))
			&& !(name.equals(OPEN_RECENT_FILES_NAME))
			&& !(name.equals(SAVE_FILE_AS_NAME))
			&& !(name.equals(SAVE_FILE_NAME))
			&& !(name.equals(PRINT_FILE_NAME))
			&& !(name.equals(EXIT_NAME))
			&& !(name.equals(CLOSE_FILE_NAME))
			&& !(name.equals(CLOSE_ALL_FILES_NAME))
			&& !(name.equals(SAVE_ALL_FILES_NAME))){
			return true;
		}else {
			return false;
		}
	}

	/**
	 * Sets the ACIDE - A Configurable IDE file menu item listeners.
	 */
	public void setListeners() {

		// Sets the new file menu item action listener
		_newFileMenuItem.addActionListener(new AcideInsertedItemListener(
				AcideMenuItemsConfiguration.getInstance()
				.getSubmenu(FILE_MENU_NAME).getItem(NEW_FILE_NAME)));

		// Sets the open file menu item action listener
		_openFileMenuItem
				//.addActionListener(new AcideOpenFileMenuItemListener());
				.addActionListener(new AcideInsertedItemListener(
				AcideMenuItemsConfiguration.getInstance()
				.getSubmenu(FILE_MENU_NAME).getItem(OPEN_FILE_NAME)));

		// Sets the open all files menu item action listener
		_openAllFilesMenuItem
				//.addActionListener(new AcideOpenAllFilesMenuItemListener());
				.addActionListener(new AcideInsertedItemListener(
				AcideMenuItemsConfiguration.getInstance()
				.getSubmenu(FILE_MENU_NAME).getItem(OPEN_ALL_FILES_NAME)));

		// Sets the save file as menu item action listener
		_saveFileAsMenuItem
				//.addActionListener(new AcideSaveFileAsMenuItemListener());
				.addActionListener(new AcideInsertedItemListener(
				AcideMenuItemsConfiguration.getInstance()
				.getSubmenu(FILE_MENU_NAME).getItem(SAVE_FILE_AS_NAME)));

		// Sets the save file menu item action listener
		_saveFileMenuItem
				//.addActionListener(new AcideSaveFileMenuItemListener());
				.addActionListener(new AcideInsertedItemListener(
				AcideMenuItemsConfiguration.getInstance()
				.getSubmenu(FILE_MENU_NAME).getItem(SAVE_FILE_NAME)));

		// Sets the print file menu item action listener
		_printFileMenuItem
				//.addActionListener(new AcidePrintFileMenuItemListener());
				.addActionListener(new AcideInsertedItemListener(
				AcideMenuItemsConfiguration.getInstance()
				.getSubmenu(FILE_MENU_NAME).getItem(PRINT_FILE_NAME)));

		// Sets the exit menu item action listener
		_exitMenuItem
			//.addActionListener(new AcideExitMenuItemListener());
			.addActionListener(new AcideInsertedItemListener(
			AcideMenuItemsConfiguration.getInstance()
			.getSubmenu(FILE_MENU_NAME).getItem(EXIT_NAME)));

		// Sets the close file menu item action listener
		_closeFileMenuItem
				//.addActionListener(new AcideCloseFileMenuItemListener());
			.addActionListener(new AcideInsertedItemListener(
			AcideMenuItemsConfiguration.getInstance()
			.getSubmenu(FILE_MENU_NAME).getItem(CLOSE_FILE_NAME)));

		// Sets the close all files menu item action listener
		_closeAllFilesMenuItem
				//.addActionListener(new AcideCloseAllFilesMenuItemListener());
				.addActionListener(new AcideInsertedItemListener(
				AcideMenuItemsConfiguration.getInstance()
				.getSubmenu(FILE_MENU_NAME).getItem(CLOSE_ALL_FILES_NAME)));

		// Sets the save all files menu item action listener
		_saveAllFilesMenuItem
				//.addActionListener(new AcideSaveAllFilesMenuItemListener());
				.addActionListener(new AcideInsertedItemListener(
				AcideMenuItemsConfiguration.getInstance()
				.getSubmenu(FILE_MENU_NAME).getItem(SAVE_ALL_FILES_NAME)));
		
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
	 * Saves the file the opened in the editor depending on the status of it. If
	 * it is red it will save it as. If it is green it will just save it.
	 * 
	 * @param index
	 *            tab index
	 */
	public void saveFile(int index) {

		// If it is the NEW FILE
		if (AcideMainWindow
				.getInstance()
				.getFileEditorManager()
				.getSelectedFileEditorPanel()
				.getAbsolutePath()
				.equals(AcideLanguageManager.getInstance().getLabels()
						.getString("s79"))) {

			// Enables the save file as menu item
			_saveFileAsMenuItem.setEnabled(true);

			// Performs the save file as menu item action
			_saveFileAsMenuItem.doClick();
		} else {

			// Enables the save file menu item
			_saveFileMenuItem.setEnabled(true);

			// Performs the save file menu item action
			_saveFileMenuItem.doClick();
		}

		// Updates the file disk copy
		AcideMainWindow
				.getInstance()
				.getFileEditorManager()
				.getFileEditorPanelAt(index)
				.setFileDiskCopy(
						AcideMainWindow.getInstance().getFileEditorManager()
								.getSelectedFileEditorPanel()
								.getTextEditionAreaContent());
	}

	/**
	 * Opens file in the file editor it is not already opened yet.
	 * 
	 * If so, puts the focus on its text edition area
	 * 
	 * @param filePath
	 *            path of the file to be opened
	 */
	public void openFile(final String filePath) {

		
		// Loads the file content
		String fileContent = null;
		fileContent = AcideFileManager.getInstance().load(filePath);

		// If the file content is not empty
		if (fileContent != null) {

			// Gets the file project index
			int fileProjectIndex = AcideProjectConfiguration.getInstance()
					.getIndexOfFile(filePath);

			// Gets the predefined lexicon configuration
			AcideLexiconConfiguration lexiconConfiguration = AcideWorkbenchConfiguration
					.getInstance().getLexiconAssignerConfiguration()
					.getPredifinedLexiconConfiguration(filePath);

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

			// It is a normal file
			AcideProjectFileType fileType = AcideProjectFileType.NORMAL;

			// If belongs to the project
			if (fileProjectIndex != -1) {

				// Gets its type
				fileType = AcideProjectConfiguration.getInstance()
						.getFileAt(fileProjectIndex).getType();

				// Sets the new file state to opened in the project
				// configuration
				AcideProjectConfiguration.getInstance()
						.getFileAt(fileProjectIndex).setIsOpened(true);

				// If it is not the default project
				if (!AcideProjectConfiguration.getInstance().isDefaultProject())

					// The project has been modified
					AcideProjectConfiguration.getInstance().setIsModified(true);
			}

			// Updates the tabbed pane in the file editor manager
			AcideMainWindow
					.getInstance()
					.getFileEditorManager()
					.updateTabbedPane(filePath, fileContent, true, fileType, 0,
							0, 1, lexiconConfiguration,
							currentGrammarConfiguration,
							previousGrammarConfiguration);

			// Updates the log
			AcideLog.getLog().info(
					AcideLanguageManager.getInstance().getLabels()
							.getString("s84")
							+ filePath);

			// Updates the log
			AcideLog.getLog().info(
					AcideLanguageManager.getInstance().getLabels()
							.getString("s85")
							+ filePath
							+ AcideLanguageManager.getInstance().getLabels()
									.getString("s86"));

		} else {

			// EMPTY FILE

			// Updates the log
			AcideLog.getLog().info(
					AcideLanguageManager.getInstance().getLabels()
							.getString("s88"));
		}

		// Adds the file to the recent files list
		AcideWorkbenchConfiguration.getInstance().getRecentFilesConfiguration()
				.addRecentFileToList(filePath);
	}

	/**
	 * Closes a tab in the file editor manager specified by a parameter.
	 * 
	 * @param fileEditorIndex
	 *            file editor index to close.
	 */
	public boolean closeFile(int fileEditorIndex) {

		// Is the file modified?
		if (AcideMainWindow.getInstance().getFileEditorManager()
				.isRedButton(fileEditorIndex)) {

			// Asks the user if he wants to save it
			int returnValue = JOptionPane.showConfirmDialog(
					null,
					AcideLanguageManager.getInstance().getLabels()
							.getString("s643"), AcideLanguageManager
							.getInstance().getLabels().getString("s994"),
					JOptionPane.YES_NO_CANCEL_OPTION);

			// If it is not the cancel option and the closed option
			if (returnValue != JOptionPane.CANCEL_OPTION
					&& returnValue != JOptionPane.CLOSED_OPTION) {

				// If it is ok
				if (returnValue == JOptionPane.OK_OPTION) {

					// Saves the file
					AcideMainWindow.getInstance().getMenu().getFileMenu()
							.saveFile(fileEditorIndex);
				}

				// If it is not the default project
				if (!AcideProjectConfiguration.getInstance().isDefaultProject()) {

					// Gets the file project index
					int fileProjectIndex = AcideProjectConfiguration
							.getInstance().getIndexOfFile(
									AcideMainWindow
											.getInstance()
											.getFileEditorManager()
											.getFileEditorPanelAt(
													fileEditorIndex)
											.getAbsolutePath());

					// If it belongs to the project
					if (fileProjectIndex != -1) {

						// Sets the file as not opened in the project
						// configuration
						AcideProjectConfiguration.getInstance()
								.getFileAt(fileProjectIndex).setIsOpened(false);

						// Sets the project to modified
						AcideProjectConfiguration.getInstance().setIsModified(
								true);
					}
				}

				// Removes the tab from the tabbed pane
				AcideMainWindow.getInstance().getFileEditorManager()
						.removeTab(fileEditorIndex);

			} else
				return false;

		} else {

			// Is not modified

			// If it is not the default project
			if (!AcideProjectConfiguration.getInstance().isDefaultProject()) {

				// Gets the file project index
				int fileProjectIndex = AcideProjectConfiguration.getInstance()
						.getIndexOfFile(
								AcideMainWindow.getInstance()
										.getFileEditorManager()
										.getFileEditorPanelAt(fileEditorIndex)
										.getAbsolutePath());

				// If it belongs to the project
				if (fileProjectIndex != -1) {

					// Sets the file as not opened in the project
					// configuration
					AcideProjectConfiguration.getInstance()
							.getFileAt(fileProjectIndex).setIsOpened(false);

					// Sets the project to modified
					AcideProjectConfiguration.getInstance().setIsModified(true);
				}
			}

			// Removes the tab from the tabbed pane
			AcideMainWindow.getInstance().getFileEditorManager()
					.removeTab(fileEditorIndex);
		}

		// If there are opened file editor panels
		if (AcideMainWindow.getInstance().getFileEditorManager()
				.getNumberOfFileEditorPanels() > 0) {

			// Updates the selected file editor index
			AcideMainWindow
					.getInstance()
					.getFileEditorManager()
					.updateRelatedComponentsAt(
							AcideMainWindow.getInstance()
									.getFileEditorManager()
									.getSelectedFileEditorPanelIndex());
		}

		return true;
	}

	/**
	 * Configures the ACIDE - A Configurable IDE file menu.
	 */
	public void configure() {

		// Enables the new file menu item
		_newFileMenuItem.setEnabled(true);

		// Enables the open file menu item
		_openFileMenuItem.setEnabled(true);

		// Enables the open recent files menu item
		_openRecentFilesMenu.setEnabled(true);

		// Enables or disables the open all files menu item
		_openAllFilesMenuItem.setEnabled(!AcideProjectConfiguration
				.getInstance().isDefaultProject());

		// If there are opened file editors
		if (AcideMainWindow.getInstance().getFileEditorManager()
				.getNumberOfFileEditorPanels() > 0) {

			// Enables the close file menu item
			_closeFileMenuItem.setEnabled(true);

			// Enables the close all files menu item
			_closeAllFilesMenuItem.setEnabled(true);

			// Enables or disables the save file menu item if the selected
			// file
			// editor is modified or not or it is the NEW FILE
			_saveFileMenuItem.setEnabled(AcideMainWindow.getInstance()
					.getFileEditorManager().isRedButton()
					|| AcideMainWindow
							.getInstance()
							.getFileEditorManager()
							.getSelectedFileEditorPanel()
							.getAbsolutePath()
							.equals(AcideLanguageManager.getInstance()
									.getLabels().getString("s79")));

			// Enables the save file as menu item
			_saveFileAsMenuItem.setEnabled(true);

			// Checks the opened file editors
			boolean isAnyModified = false;
			for (int index = 0; index < AcideMainWindow.getInstance()
					.getFileEditorManager().getNumberOfFileEditorPanels(); index++) {

				// If any of them is modified
				if (AcideMainWindow.getInstance().getFileEditorManager()
						.isRedButton(index))
					isAnyModified = true;
			}

			// Disables or enables the save all files menu item
			_saveAllFilesMenuItem.setEnabled(isAnyModified);

			// Enables the print file menu item
			_printFileMenuItem.setEnabled(true);

		} else {

			// Disables the close file menu item
			_closeFileMenuItem.setEnabled(false);

			// Disables the close all files menu item
			_closeAllFilesMenuItem.setEnabled(false);

			// Disables the save file as menu item
			_saveFileAsMenuItem.setEnabled(false);

			// Disables the save file menu item
			_saveFileMenuItem.setEnabled(false);

			// Disables the save all files as menu item
			_saveAllFilesMenuItem.setEnabled(false);

			// Disables the print file menu item
			_printFileMenuItem.setEnabled(false);
		}

		// Enables the exit menu item
		_exitMenuItem.setEnabled(true);
	}

	/**
	 * Enables the ACIDE - A Configurable IDE file menu.
	 */
	public void enableMenu() {

		// Enables the close file menu item
		_closeFileMenuItem.setEnabled(true);

		// Enables the close all files menu item
		_closeAllFilesMenuItem.setEnabled(true);

		// Enables the save file as menu item
		_saveFileAsMenuItem.setEnabled(true);

		// Disables the save file menu item
		_saveFileMenuItem.setEnabled(false);

		// Enables the save all files as menu item
		_saveAllFilesMenuItem.setEnabled(true);

		// Enables the print file menu item
		_printFileMenuItem.setEnabled(true);
	}

	/**
	 * Disables the ACIDE - A Configurable IDE file menu.
	 */
	public void disableMenu() {

		// Disables the close file menu item
		_closeFileMenuItem.setEnabled(false);

		// Disables the close all files menu item
		_closeAllFilesMenuItem.setEnabled(false);

		// Disables the save file as menu item
		_saveFileAsMenuItem.setEnabled(false);

		// Disables the save file menu item
		_saveFileMenuItem.setEnabled(false);

		// Disables the save all files menu item
		_saveAllFilesMenuItem.setEnabled(false);

		// Disables the print file menu item
		_printFileMenuItem.setEnabled(false);
	}

	/**
	 * Enables the ACIDE - A Configurable IDE file menu save file as menu item.
	 */
	public void enableSaveFileAs() {

		// Enables the save file as menu item
		_saveFileAsMenuItem.setEnabled(true);

		// Updates the log
		AcideLog.getLog()
				.info(AcideLanguageManager.getInstance().getLabels()
						.getString("s75"));
	}

	/**
	 * Returns the ACIDE - A Configurable IDE file menu new file menu item.
	 * 
	 * @return the ACIDE - A Configurable IDE file menu new file menu item
	 */
	public JMenuItem getNewFileMenuItem() {
		return _newFileMenuItem;
	}

	/**
	 * Returns the ACIDE - A Configurable IDE file menu exit menu item.
	 * 
	 * @return the ACIDE - A Configurable IDE file menu exit menu item
	 */
	public JMenuItem getExitMenuItem() {
		return _exitMenuItem;
	}

	/**
	 * Returns the ACIDE - A Configurable IDE file menu save file as menu item.
	 * 
	 * @return the ACIDE - A Configurable IDE file menu save file as menu item.
	 */
	public JMenuItem getSaveFileAsMenuItem() {
		return _saveFileAsMenuItem;
	}

	/**
	 * Returns the ACIDE - A Configurable IDE file menu save file menu item.
	 * 
	 * @return the ACIDE - A Configurable IDE file menu save file menu item
	 */
	public JMenuItem getSaveFileMenuItem() {
		return _saveFileMenuItem;
	}

	/**
	 * Returns the ACIDE - A Configurable IDE file menu save all files menu
	 * item.
	 * 
	 * @return the ACIDE - A Configurable IDE file menu save all files menu item
	 */
	public JMenuItem getSaveAllFilesMenuItem() {
		return _saveAllFilesMenuItem;
	}

	/**
	 * Returns the ACIDE - A Configurable IDE file menu print file menu item.
	 * 
	 * @return the ACIDE - A Configurable IDE file menu print file menu item
	 */
	public JMenuItem getPrintFileMenuItem() {
		return _printFileMenuItem;
	}

	/**
	 * Returns the ACIDE - A Configurable IDE file menu close all files menu
	 * item.
	 * 
	 * @return the ACIDE - A Configurable IDE file menu close all files menu
	 *         item
	 */
	public JMenuItem getCloseAllFilesMenuItem() {
		return _closeAllFilesMenuItem;
	}

	/**
	 * Returns the ACIDE - A Configurable IDE file menu close file menu item.
	 * 
	 * @return the ACIDE - A Configurable IDE file menu close file menu item
	 */
	public JMenuItem getCloseFileMenuItem() {
		return _closeFileMenuItem;
	}

	/**
	 * Returns the ACIDE - A Configurable IDE file menu open file menu item.
	 * 
	 * @return the ACIDE - A Configurable IDE file menu open file menu item
	 */
	public JMenuItem getOpenFileMenuItem() {
		return _openFileMenuItem;
	}

	/**
	 * Returns the ACIDE - A Configurable IDE file menu open recent files menu.
	 * 
	 * @return the ACIDE - A Configurable IDE file menu open recent files menu
	 */
	public AcideRecentFilesMenu getOpenRecentFilesMenu() {
		return _openRecentFilesMenu;
	}

	/**
	 * Returns the ACIDE - A Configurable IDE file menu open all files menu
	 * item.
	 * 
	 * @return the ACIDE - A Configurable IDE file menu open all files menu item
	 */
	public JMenuItem getOpenAllFilesMenuItem() {
		return _openAllFilesMenuItem;
	}
}
