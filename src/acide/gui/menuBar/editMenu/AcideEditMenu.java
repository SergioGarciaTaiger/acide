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
 *      -Version from 0.12 to 0.16
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
package acide.gui.menuBar.editMenu;

import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;

import javax.swing.ImageIcon;
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
import acide.gui.mainWindow.AcideMainWindow;
import acide.gui.menuBar.editMenu.changeCaseMenu.AcideChangeCaseMenu;
import acide.gui.menuBar.editMenu.listeners.AcideRedoAction;
import acide.gui.menuBar.editMenu.listeners.AcideUndoAction;
import acide.gui.menuBar.editMenu.utils.AcideUndoManager;
import acide.gui.menuBar.listeners.AcideMenuBarMouseClickListener;
import acide.language.AcideLanguageManager;
import acide.log.AcideLog;
import acide.resources.AcideResourceManager;
import acide.utils.IconsUtils;

/**
 * ACIDE - A Configurable IDE edit menu.
 * 
 * @version 0.14
 * @see JMenu
 */
public class AcideEditMenu extends JMenu {

	/**
	 * ACIDE - A Configurable IDE edit menu class serial version UID.
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * ACIDE - A Configurable IDE file menu name.
	 */
	public final static String EDIT_MENU_NAME = "Edit";
	/**
	 * ACIDE - A Configurable IDE edit menu undo menu item name.
	 */
	public static final String UNDO_NAME = "Undo";
	/**
	 * ACIDE - A Configurable IDE edit menu redo menu item name.
	 */
	public static final String REDO_NAME = "Redo";
	/**
	 * ACIDE - A Configurable IDE edit menu copy menu item name.
	 */
	public static final String COPY_NAME = "Copy";
	/**
	 * ACIDE - A Configurable IDE edit menu paste menu item name.
	 */
	public static final String PASTE_NAME = "Paste";
	/**
	 * ACIDE - A Configurable IDE edit menu cut menu item name.
	 */
	public static final String CUT_NAME = "Cut";
	/**
	 * ACIDE - A Configurable IDE edit menu cut menu item name.
	 */
	public static final String TOGGLE_COMMENT_NAME = "Toggle Comment";
	/**
	 * ACIDE - A Configurable IDE edit menu cut menu item name.
	 */
	public static final String MAKE_COMMENT_NAME = "Make Comment";
	/**
	 * ACIDE - A Configurable IDE edit menu cut menu item name.
	 */
	public static final String RELEASE_COMMENT_NAME = "Release Comment";
	/**
	 * ACIDE - A Configurable IDE edit menu select all menu item name.
	 */
	public static final String SELECT_ALL_NAME = "Select All";
	/**
	 * ACIDE - A Configurable IDE edit menu change case menu item name.
	 */
	public static final String CHANGE_CASE_NAME = "Change Case";
	/**
	 * ACIDE - A Configurable IDE edit menu go to line menu item name.
	 */
	public static final String GO_TO_LINE_NAME = "Go To Line";
	/**
	 * ACIDE - A Configurable IDE edit menu search menu item name.
	 */
	public static final String SEARCH_NAME = "Search";
	/**
	 * ACIDE - A Configurable IDE edit menu replace menu item name.
	 */
	public static final String REPLACE_NAME = "Replace";
	/**
	 * ACIDE - A Configurable IDE edit menu undo menu item.
	 */
	private JMenuItem _undoMenuItem;
	/**
	 * ACIDE - A Configurable IDE edit menu undo menu item has been inserted.
	 */
	private boolean _undoInserted;
	/**
	 * ACIDE - A Configurable IDE edit menu redo menu item.
	 */
	private JMenuItem _redoMenuItem;
	/**
	 * ACIDE - A Configurable IDE edit menu redo menu item has been inserted.
	 */
	private boolean _redoInserted;
	
	/**
	 * ACIDE - A Configurable IDE edit menu search menu item.
	 */
	private JMenuItem _searchMenuItem;
	/**
	 * ACIDE - A Configurable IDE edit menu search menu item has been inserted.
	 */
	private boolean _searchInserted;
	/**
	 * ACIDE - A Configurable IDE edit menu paste menu item.
	 */
	private JMenuItem _pasteMenuItem;
	/**
	 * ACIDE - A Configurable IDE edit menu paste menu item has been inserted.
	 */
	private boolean _pasteInserted;
	/**
	 * ACIDE - A Configurable IDE edit menu copy menu item.
	 */
	private JMenuItem _copyMenuItem;
	/**
	 * ACIDE - A Configurable IDE edit menu copymenu item has been inserted.
	 */
	private boolean _copyInserted;
	/**
	 * ACIDE - A Configurable IDE edit menu cut menu item.
	 */
	private JMenuItem _cutMenuItem;
	/**
	 * ACIDE - A Configurable IDE edit menu cut menu item has been inserted.
	 */
	private boolean _cutInserted;
	/**
	 * ACIDE - A Configurable IDE edit menu select all menu item.
	 */
	private JMenuItem _selectAllMenuItem;
	/**
	 * ACIDE - A Configurable IDE edit menu select all menu item has been inserted.
	 */
	private boolean _selectAllInserted;
	/**
	 * ACIDE - A Configurable IDE edit menu change case menu item.
	 */
	private AcideChangeCaseMenu _changeCaseMenu;
	/**
	 * ACIDE - A Configurable IDE edit menu change case menu item has been inserted.
	 */
	private boolean _changeCaseInserted;
	/**
	 * ACIDE - A Configurable IDE edit menu go to line menu item.
	 */
	private JMenuItem _goToLineMenuItem;
	/**
	 * ACIDE - A Configurable IDE edit menu go to line menu item has been inserted.
	 */
	private boolean _goToLineInserted;
	/**
	 * ACIDE - A Configurable IDE edit menu replace menu item.
	 */
	private JMenuItem _replaceMenuItem;
	/**
	 * ACIDE - A Configurable IDE edit menu replace menu item has been inserted.
	 */
	private boolean _replaceInserted;
	/**
	 * ACIDE - A Configurable IDE edit menu redo copy separator.
	 */
	private JSeparator _redoCopySeparator;
	/**
	 * ACIDE - A Configurable IDE edit menu select all go to line separator.
	 */
	private JSeparator _selectAllGoToLineSeparator;
	/**
	 * ACIDE - A Configurable IDE edit menu go to line search separator.
	 */
	private JSeparator _goToLineSearchSeparator;
	/**
	 * ACIDE - A Configurable IDE edit menu cut toggle separator.
	 */
	private JSeparator _cutToggleSeparator;
	/**
	 * ACIDE - A Configurable IDE edit menu toggle select all separator.
	 */
	private JSeparator _toggleSelectAllSeparator;
	/**
	 * ACIDE - A Configurable IDE edit menu undo action.
	 */
	private AcideUndoAction _undoAction;
	/**
	 * ACIDE - A Configurable IDE edit menu redo action.
	 */
	private AcideRedoAction _redoAction;
	/**
	 * ACIDE - A Configurable IDE edit menu configuration menu.
	 */
	private AcideMenuSubmenuConfiguration _editSubmenuConfiguration;
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
	 * ACIDE - A Configurable IDE edit menu toggle comment menu item.
	 */
	private JMenuItem _toggleCommentMenuItem;
	/**
	 * ACIDE - A Configurable IDE edit menu make comment menu item.
	 */
	private JMenuItem _makeCommentMenuItem;
	/**
	 * ACIDE - ACIDE - A Configurable IDE edit menu release comment menu item.
	 */
	private JMenuItem _releaseCommentMenuItem;

	/**
	 * ACIDE - A Configurable IDE edit menu toggle menu item has been inserted.
	 */
	private boolean _toggleInserted;
	/**
	 * ACIDE - A Configurable IDE edit menu make comment menu item has been inserted.
	 */
	private boolean _makeCommentInserted;
	/**
	 * ACIDE - A Configurable IDE edit menu release comment menu item has been inserted.
	 */
	private boolean _releaseCommentInserted;
	
	/**
	 * Creates a new ACIDE - A Configurable IDE edit menu.
	 */
	public AcideEditMenu() {
		
		_undoInserted = false;
		_redoInserted = false;
		_searchInserted = false;
		_pasteInserted = false;
		_copyInserted = false;
		_cutInserted = false;
		_selectAllInserted = false;
		_changeCaseInserted = false;
		_goToLineInserted = false;
		_replaceInserted = false;
		_toggleInserted = false;
		_makeCommentInserted = false;
		_releaseCommentInserted = false;
		
		_insertedItems = new HashMap<String, AcideInsertedItem>();
		
		_insertedMenus = new HashMap<String, AcideInsertedMenu>();
		
		_insertedObjects = new ArrayList<AcideMenuObjectConfiguration>();

		// Builds the menu components
		buildComponents();

		// Adds the components to the menu
		addComponents();

		// Sets the text of the edit menu components
		setTextOfMenuComponents();
	}

	/**
	 * Adds the components to the ACIDE - A Configurable IDE edit menu.
	 */
	private void addComponents() {
		
		Iterator<Object> it = AcideMenuItemsConfiguration.getInstance()
				.getMenuItemsManager().getSubmenu(EDIT_MENU_NAME).getItemsManager().managerIterator();
		
		while (it.hasNext()){
			AcideMenuObjectConfiguration ob = (AcideMenuObjectConfiguration) it.next();
			String name = ob.getName();
			if (name.equals(UNDO_NAME)){
				// Adds the undo menu item to the menu
				add(_undoMenuItem);
				_undoInserted = true;
			}else if (name.equals(REDO_NAME)){
				// Adds the redo menu item to the menu
				add(_redoMenuItem);
				_redoInserted = true;
				// Adds the redo copy menu item to the menu
				add(_redoCopySeparator);
			}else if (name.equals(COPY_NAME)){
				// Adds the copy menu item to the menu
				add(_copyMenuItem);
				_copyInserted = true;
			}else if (name.equals(PASTE_NAME)){
				// Adds the paste menu item to the menu
				add(_pasteMenuItem);
				_pasteInserted = true;
			}else if (name.equals(CUT_NAME)){
				// Adds the cut menu item to the menu
				add(_cutMenuItem);
				_cutInserted = true;
				add(_cutToggleSeparator);
			}else if (name.equals(TOGGLE_COMMENT_NAME)){
				// Adds the toggle comment menu item to the menu
				add(_toggleCommentMenuItem);
				_toggleInserted = true;
			}else if (name.equals(MAKE_COMMENT_NAME)){
				// Adds the make comment menu item to the menu
				add(_makeCommentMenuItem);
				_makeCommentInserted = true;
			}else if (name.equals(RELEASE_COMMENT_NAME)){
				// Adds the release comment menu item to the menu
				add(_releaseCommentMenuItem);
				_releaseCommentInserted = true;
				add(_toggleSelectAllSeparator);
			}else if (name.equals(SELECT_ALL_NAME)){
				// Adds the select all menu item to the menu
				add(_selectAllMenuItem);
				_selectAllInserted = true;
				add(_selectAllGoToLineSeparator);
			}else if (name.equals(CHANGE_CASE_NAME)){
				// Adds the change case menu item to the menu
				add(_changeCaseMenu);
				_changeCaseInserted = true;
			}else if (name.equals(GO_TO_LINE_NAME)){
				// Adds the go to line menu item to the menu
				add(_goToLineMenuItem);
				_goToLineInserted = true;
				// Adds the go to line search menu item to the menu
				add(_goToLineSearchSeparator);
			}else if (name.equals(SEARCH_NAME)){
				// Adds the search menu item to the menu
				add(_searchMenuItem);
				_searchInserted = true;
			}else if (name.equals(REPLACE_NAME)){
				// Adds the replace menu item to the menu
				add(_replaceMenuItem);
				_replaceInserted = true;
			}else {
				if (ob.isSubmenu()){
					add(_insertedMenus.get(ob.getName()));
				}else{
					add(_insertedItems.get(ob.getName()));
				}
			}
		}
		
		if (!_undoInserted)
			add(_undoMenuItem);
		if (!_redoInserted)
			add(_redoMenuItem);
		if (!_copyInserted)
			add(_copyMenuItem);
		if (!_pasteInserted)
			add(_pasteMenuItem);
		if (!_cutInserted)
			add(_cutMenuItem);
		if (!_toggleInserted)
			add(_toggleCommentMenuItem);
		if (!_makeCommentInserted)
			add(_makeCommentMenuItem);
		if (!_releaseCommentInserted)
			add(_releaseCommentMenuItem);
		if (!_selectAllInserted)
			add(_selectAllMenuItem);
		if (!_changeCaseInserted)
			add(_changeCaseMenu);
		if (!_goToLineInserted)
			add(_goToLineMenuItem);
		if (!_searchInserted)
			add(_searchMenuItem);
		if (!_replaceInserted)
			add(_replaceMenuItem);
		
	}

	/**
	 * Builds the ACIDE - A Configurable IDE edit menu components.
	 */
	private void buildComponents() {
		
		if (!AcideMenuItemsConfiguration.getInstance().getMenuItemsManager().hasSubmenu(EDIT_MENU_NAME)){
			AcideMenuItemsConfiguration.getInstance()
				.insertObject(new AcideMenuSubmenuConfiguration(EDIT_MENU_NAME));
		}
		
		Iterator<Object> it = AcideMenuItemsConfiguration.getInstance()
				.getMenuItemsManager().getSubmenu(EDIT_MENU_NAME).getItemsManager().managerIterator();
		
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

		// Creates the undo action
		_undoAction = new AcideUndoAction("Undo");

		// Creates the redo action
		_redoAction = new AcideRedoAction("Redo");

		// Creates the undo menu item
		ImageIcon icon = IconsUtils.getIcon(AcideMenuItemsConfiguration.getInstance()
				.getMenuItemsManager().getSubmenu(EDIT_MENU_NAME)
				.getItem(UNDO_NAME).getImage());
		
		if (icon != null)
			_undoMenuItem = new JMenuItem(icon);
		else
			_undoMenuItem = new JMenuItem();

		// Sets the undo menu item name
		_undoMenuItem.setName(UNDO_NAME);

		// Creates the redo menu item
		 icon = IconsUtils.getIcon(AcideMenuItemsConfiguration.getInstance()
					.getMenuItemsManager().getSubmenu(EDIT_MENU_NAME)
					.getItem(REDO_NAME).getImage());

		 if (icon != null)
			_redoMenuItem = new JMenuItem(icon);
		 else
			_redoMenuItem = new JMenuItem();

		// Sets the redo menu item name
		_redoMenuItem.setName(REDO_NAME);

		// Creates the redo copy separator
		_redoCopySeparator = new JSeparator();

		// Creates the copy menu item
		 icon = IconsUtils.getIcon(AcideMenuItemsConfiguration.getInstance()
					.getMenuItemsManager().getSubmenu(EDIT_MENU_NAME)
					.getItem(COPY_NAME).getImage());
		 
		 if (icon != null)
			 _copyMenuItem = new JMenuItem(icon);
		 else
			 _copyMenuItem = new JMenuItem();


		// Sets the copy menu item name
		_copyMenuItem.setName(COPY_NAME);

		// Creates the paste menu item
		 icon = IconsUtils.getIcon(AcideMenuItemsConfiguration.getInstance()
					.getMenuItemsManager().getSubmenu(EDIT_MENU_NAME)
					.getItem(PASTE_NAME).getImage());
		 
		 if (icon != null)
			 _pasteMenuItem = new JMenuItem(icon);
		 else
			 _pasteMenuItem = new JMenuItem();

		// Sets the paste menu item name
		_pasteMenuItem.setName(PASTE_NAME);

		// Creates the cut menu item
		 icon = IconsUtils.getIcon(AcideMenuItemsConfiguration.getInstance()
					.getMenuItemsManager().getSubmenu(EDIT_MENU_NAME)
					.getItem(CUT_NAME).getImage());
		 
		 if (icon != null)
			 _cutMenuItem = new JMenuItem(icon);
		 else
			 _cutMenuItem = new JMenuItem();

		// Sets the cut  menu item name
		_cutMenuItem.setName(CUT_NAME);
		
		_cutToggleSeparator = new JSeparator();

		// Creates the toggle Comment menu item
		_toggleCommentMenuItem = new JMenuItem();

		// Sets the toggle comment menu item name
		_toggleCommentMenuItem.setName(TOGGLE_COMMENT_NAME);
		
		// Creates the make Comment menu item
		_makeCommentMenuItem = new JMenuItem();

		// Sets the make comment menu item name
		_makeCommentMenuItem.setName(MAKE_COMMENT_NAME);
		
		// Creates the release Comment menu item
		_releaseCommentMenuItem = new JMenuItem();

		// Sets the release comment menu item name
		_releaseCommentMenuItem.setName(RELEASE_COMMENT_NAME);
		
		_toggleSelectAllSeparator = new JSeparator();

		// Creates the select all menu item
		 icon = IconsUtils.getIcon(AcideMenuItemsConfiguration.getInstance()
					.getMenuItemsManager().getSubmenu(EDIT_MENU_NAME)
					.getItem(SELECT_ALL_NAME).getImage());
		 
		 if (icon != null)
			 _selectAllMenuItem = new JMenuItem(icon);
		 else
			 _selectAllMenuItem = new JMenuItem();

		// Sets the select all menu item
		_selectAllMenuItem.setName(SELECT_ALL_NAME);
		
		// Creates the select all go to line copy separator
		_selectAllGoToLineSeparator = new JSeparator();
		
		// Creates the change case menu
		_changeCaseMenu = new AcideChangeCaseMenu();

		// Sets the change case menu name
		_changeCaseMenu.setName(CHANGE_CASE_NAME);

		// Creates the go to line menu item
		 icon = IconsUtils.getIcon(AcideMenuItemsConfiguration.getInstance()
					.getMenuItemsManager().getSubmenu(EDIT_MENU_NAME)
					.getItem(GO_TO_LINE_NAME).getImage());
		 
		 if (icon != null)
			 _goToLineMenuItem = new JMenuItem(icon);
		 else
			 _goToLineMenuItem = new JMenuItem();

		// Sets the go to line menu item name
		_goToLineMenuItem.setName(GO_TO_LINE_NAME);

		// Creates the go to line search separator
		_goToLineSearchSeparator = new JSeparator();

		// Creates the search menu item
		 icon = IconsUtils.getIcon(AcideMenuItemsConfiguration.getInstance()
					.getMenuItemsManager().getSubmenu(EDIT_MENU_NAME)
					.getItem(SEARCH_NAME).getImage());
		 
		 if (icon != null)
			 _searchMenuItem = new JMenuItem(icon);
		 else
			 _searchMenuItem = new JMenuItem();

		// Sets the search menu item name
		_searchMenuItem.setName(SEARCH_NAME);

		// Creates the replace menu item
		 icon = IconsUtils.getIcon(AcideMenuItemsConfiguration.getInstance()
					.getMenuItemsManager().getSubmenu(EDIT_MENU_NAME)
					.getItem(REPLACE_NAME).getImage());
		 
		 if (icon != null)
			 _replaceMenuItem = new JMenuItem(icon);
		 else
			 _replaceMenuItem = new JMenuItem();

		// Sets the replace menu item name
		_replaceMenuItem.setName(REPLACE_NAME);
	}

	/**
	 * Sets the text of the ACIDE - A Configurable IDE edit menu components with
	 * the labels in the selected language to display.
	 */
	public void setTextOfMenuComponents() {

		// Sets the undo menu item text
		_undoMenuItem.setText(AcideLanguageManager.getInstance().getLabels()
				.getString("s21"));

		// Sets the undo menu item accelerator
		_undoMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Z,
				ActionEvent.CTRL_MASK));

		// Sets the redo menu item text
		_redoMenuItem.setText(AcideLanguageManager.getInstance().getLabels()
				.getString("s22"));

		// Sets the redo menu item accelerator
		_redoMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Y,
				ActionEvent.CTRL_MASK));

		// Sets the copy menu item text
		_copyMenuItem.setText(AcideLanguageManager.getInstance().getLabels()
				.getString("s23"));

		// Sets the copy menu item accelerator
		_copyMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C,
				ActionEvent.CTRL_MASK));

		// Sets the cut menu item text
		_cutMenuItem.setText(AcideLanguageManager.getInstance().getLabels()
				.getString("s24"));

		// Sets the cut menu item accelerator
		_cutMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X,
				ActionEvent.CTRL_MASK));
		
		// Sets the toggle menu item text
		_toggleCommentMenuItem.setText(AcideLanguageManager.getInstance().getLabels()
						.getString("s2248"));
		
		// Sets the make comment menu item text
		_makeCommentMenuItem.setText(AcideLanguageManager.getInstance().getLabels()
						.getString("s2269"));
		
		// Sets the release comment menu item text
		_releaseCommentMenuItem.setText(AcideLanguageManager.getInstance().getLabels()
						.getString("s2270"));

		// Sets the paste menu item text
		_pasteMenuItem.setText(AcideLanguageManager.getInstance().getLabels()
				.getString("s25"));

		// Sets the paste menu item accelerator
		_pasteMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V,
				ActionEvent.CTRL_MASK));

		// Sets the search menu item text
		_searchMenuItem.setText(AcideLanguageManager.getInstance().getLabels()
				.getString("s26"));

		// Sets the search menu item accelerator
		if (AcideLanguageManager.getInstance().getCurrentLocale()
				.equals(new Locale("en", "EN"))
				|| AcideLanguageManager.getInstance().getCurrentLocale()
				.equals(new Locale("fr", "FR")))
			_searchMenuItem.setAccelerator(KeyStroke.getKeyStroke(
					KeyEvent.VK_F, ActionEvent.CTRL_MASK));
		else
			_searchMenuItem.setAccelerator(KeyStroke.getKeyStroke(
					KeyEvent.VK_B, ActionEvent.CTRL_MASK));

		// Sets the replace menu item text
		_replaceMenuItem.setText(AcideLanguageManager.getInstance().getLabels()
				.getString("s27"));

		// Sets the replace menu item accelerator
		if (AcideLanguageManager.getInstance().getCurrentLocale()
				.equals(new Locale("en", "EN"))
				|| AcideLanguageManager.getInstance().getCurrentLocale()
				.equals(new Locale("fr", "FR")))
			_replaceMenuItem.setAccelerator(KeyStroke.getKeyStroke(
					KeyEvent.VK_R, ActionEvent.CTRL_MASK));
		else
			_replaceMenuItem.setAccelerator(KeyStroke.getKeyStroke(
					KeyEvent.VK_L, ActionEvent.CTRL_MASK));

		// Sets the select all menu item text
		_selectAllMenuItem.setText(AcideLanguageManager.getInstance()
				.getLabels().getString("s190"));

		// Sets the select all menu item accelerator
		_selectAllMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E,
				ActionEvent.CTRL_MASK));

		// Sets the change case menu text
		_changeCaseMenu.setText(AcideLanguageManager.getInstance()
						.getLabels().getString("s2264"));
		
		// Sets the change case menu items text
		_changeCaseMenu.setTextOfMenuComponets();
				
		// Sets the go to line menu item text
		_goToLineMenuItem.setText(AcideLanguageManager.getInstance()
				.getLabels().getString("s222"));
		
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
	 * Updates the ACIDE - A Configurable IDE edit menu components visibility
	 * with the menu configuration.
	 */
	public void updateComponentsVisibility() {

		AcideMenuItemConfiguration undoConfiguration;
		AcideMenuItemConfiguration redoConfiguration;
		AcideMenuItemConfiguration copyConfiguration;
		AcideMenuItemConfiguration pasteConfiguration;
		AcideMenuItemConfiguration cutConfiguration;
		AcideMenuItemConfiguration selectAllConfiguration;
		AcideMenuItemConfiguration goToLineConfiguration;
		AcideMenuItemConfiguration searchConfiguration;
		AcideMenuItemConfiguration replaceConfiguration;
		AcideMenuItemConfiguration toggleConfiguration;
		AcideMenuItemConfiguration makeCommentConfiguration;
		AcideMenuItemConfiguration releaseCommentConfiguration;
		
		_editSubmenuConfiguration = AcideMenuItemsConfiguration.getInstance().getSubmenu(EDIT_MENU_NAME);
			
		// Sets the undo menu item to visible or not visible
		undoConfiguration = _editSubmenuConfiguration.getItem(UNDO_NAME);
		_undoMenuItem.setVisible(undoConfiguration.isVisible());
			
		// Sets the redo menu item to visible or not visible
		redoConfiguration = _editSubmenuConfiguration.getItem(REDO_NAME);
		_redoMenuItem.setVisible(redoConfiguration.isVisible());
			
		// Sets the copy menu item to visible or not visible
		copyConfiguration = _editSubmenuConfiguration.getItem(COPY_NAME);
		_copyMenuItem.setVisible(copyConfiguration.isVisible());

		// Sets the paste menu item to visible or not visible
		pasteConfiguration = _editSubmenuConfiguration.getItem(PASTE_NAME);
		_pasteMenuItem.setVisible(pasteConfiguration.isVisible());

		// Sets the cut menu item to visible or not visible
		cutConfiguration = _editSubmenuConfiguration.getItem(CUT_NAME);
		_cutMenuItem.setVisible(cutConfiguration.isVisible());

		// Sets the toggle comment menu item to visible or not visible
		toggleConfiguration = _editSubmenuConfiguration.getItem(TOGGLE_COMMENT_NAME);
		_toggleCommentMenuItem.setVisible(toggleConfiguration.isVisible());
		
		// Sets the make comment menu item to visible or not visible
		makeCommentConfiguration = _editSubmenuConfiguration.getItem(MAKE_COMMENT_NAME);
		_makeCommentMenuItem.setVisible(makeCommentConfiguration.isVisible());
		
		// Sets the release comment menu item to visible or not visible
		releaseCommentConfiguration = _editSubmenuConfiguration.getItem(RELEASE_COMMENT_NAME);
		_releaseCommentMenuItem.setVisible(releaseCommentConfiguration.isVisible());
				
		// Sets the select all menu item to visible or not visible
		selectAllConfiguration = _editSubmenuConfiguration.getItem(SELECT_ALL_NAME);
		_selectAllMenuItem.setVisible(selectAllConfiguration.isVisible());
		
		// Builds the change case menu
		_changeCaseMenu.updateComponentsVisibility();
		
		// Sets the change case menu to visible or not visible
		_changeCaseMenu.setVisible((AcideMenuItemsConfiguration.getInstance()
						.getSubmenu(EDIT_MENU_NAME))
						.getSubmenu(AcideChangeCaseMenu.CHANGE_CASE_MENU_NAME).isVisible());

		// Sets the redo copy separator to visible or not visible
		_redoCopySeparator.setVisible((_undoMenuItem.isVisible() || _redoMenuItem.isVisible())
				&& (_copyMenuItem.isVisible()
						|| _pasteMenuItem.isVisible()
						|| _cutMenuItem.isVisible() || _selectAllMenuItem.isVisible()));

		// Sets the go to line menu item to visible or not visible
		goToLineConfiguration = _editSubmenuConfiguration.getItem(GO_TO_LINE_NAME);
		_goToLineMenuItem.setVisible(goToLineConfiguration.isVisible());

		// Set the select all go to line separator to visible or not visible
		_selectAllGoToLineSeparator.setVisible((_undoMenuItem.isVisible()
				|| _redoMenuItem.isVisible()
				|| _copyMenuItem.isVisible()
				|| _pasteMenuItem.isVisible()
				|| _changeCaseMenu.isVisible()
				|| _cutMenuItem.isVisible() || _selectAllMenuItem.isVisible())
				&& _goToLineMenuItem.isVisible());
			
		// Sets the search menu item to visible or not visible
		searchConfiguration = _editSubmenuConfiguration.getItem(SEARCH_NAME);
		_searchMenuItem.setVisible(searchConfiguration.isVisible());

		// Sets the replace menu item to visible or not visible
		replaceConfiguration = _editSubmenuConfiguration.getItem(REPLACE_NAME);
		_replaceMenuItem.setVisible(replaceConfiguration.isVisible());

		// Sets go to line search separator to visible or not visible
		_goToLineSearchSeparator.setVisible((_undoMenuItem.isVisible()
				|| _redoMenuItem.isVisible()
				|| _copyMenuItem.isVisible()
				|| _pasteMenuItem.isVisible()
				|| _changeCaseMenu.isVisible()
				|| _cutMenuItem.isVisible()
				|| _selectAllMenuItem.isVisible() || _goToLineMenuItem.isVisible())
				&& (_searchMenuItem.isVisible() || _replaceMenuItem.isVisible()));
		
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
		
		_editSubmenuConfiguration.setVisible(_undoMenuItem.isVisible()
				|| _redoMenuItem.isVisible()
				|| _copyMenuItem.isVisible()
				|| _pasteMenuItem.isVisible()
				|| _cutMenuItem.isVisible()
				|| _toggleCommentMenuItem.isVisible()
				|| _makeCommentMenuItem.isVisible()
				|| _releaseCommentMenuItem.isVisible()
				|| _selectAllMenuItem.isVisible()
				|| _changeCaseMenu.isVisible()
				|| _goToLineMenuItem.isVisible()
				|| _searchMenuItem.isVisible()
				|| _replaceMenuItem.isVisible());
		_editSubmenuConfiguration.setErasable(false);
		

		
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
		if (!(name.equals(UNDO_NAME))
			&& !(name.equals(REDO_NAME))
			&& !(name.equals(SEARCH_NAME))
			&& !(name.equals(REPLACE_NAME))
			&& !(name.equals(CUT_NAME))
			&& !(name.equals(TOGGLE_COMMENT_NAME))
			&& !(name.equals(MAKE_COMMENT_NAME))
			&& !(name.equals(RELEASE_COMMENT_NAME))
			&& !(name.equals(PASTE_NAME))
			&& !(name.equals(COPY_NAME))
			&& !(name.equals(SELECT_ALL_NAME))
			&& !(name.equals(CHANGE_CASE_NAME))
			&& !(name.equals(GO_TO_LINE_NAME))){
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Sets the ACIDE - A Configurable IDE edit menu menu item listeners.
	 */
	public void setListeners() {

		// Sets the undo menu item action listener
		_undoMenuItem
			//.addActionListener(_undoAction);
			.addActionListener(new AcideInsertedItemListener(
				AcideMenuItemsConfiguration.getInstance()
				.getSubmenu(EDIT_MENU_NAME).getItem(UNDO_NAME)));

		// Sets the redo menu item action listener
		_redoMenuItem//.addActionListener(_redoAction);
			.addActionListener(new AcideInsertedItemListener(
				AcideMenuItemsConfiguration.getInstance()
				.getSubmenu(EDIT_MENU_NAME).getItem(REDO_NAME)));

		// Sets the search menu item action listener
		_searchMenuItem//.addActionListener(new AcideSearchMenuItemListener());
			.addActionListener(new AcideInsertedItemListener(
				AcideMenuItemsConfiguration.getInstance()
				.getSubmenu(EDIT_MENU_NAME).getItem(SEARCH_NAME)));

		// Sets the replace menu item action listener
		_replaceMenuItem//.addActionListener(new AcideReplaceMenuItemListener());
			.addActionListener(new AcideInsertedItemListener(
				AcideMenuItemsConfiguration.getInstance()
				.getSubmenu(EDIT_MENU_NAME).getItem(REPLACE_NAME)));

		// Sets the cut menu item action listener
		_cutMenuItem//.addActionListener(new AcideCutMenuItemListener());
			.addActionListener(new AcideInsertedItemListener(
				AcideMenuItemsConfiguration.getInstance()
				.getSubmenu(EDIT_MENU_NAME).getItem(CUT_NAME)));
		
		// Sets the toggle comment menu item action listener
		_toggleCommentMenuItem//.addActionListener(new AcideCutMenuItemListener());
					.addActionListener(new AcideInsertedItemListener(
							AcideMenuItemsConfiguration.getInstance()
							.getSubmenu(EDIT_MENU_NAME).getItem(TOGGLE_COMMENT_NAME)));
		
		// Sets the Make Comment menu item action listener
		_makeCommentMenuItem//.addActionListener(new AcideCutMenuItemListener());
					.addActionListener(new AcideInsertedItemListener(
						AcideMenuItemsConfiguration.getInstance()
						.getSubmenu(EDIT_MENU_NAME).getItem(MAKE_COMMENT_NAME)));
		
		// Sets the Release Comment menu item action listener
		_releaseCommentMenuItem//.addActionListener(new AcideCutMenuItemListener());
					.addActionListener(new AcideInsertedItemListener(
						AcideMenuItemsConfiguration.getInstance()
						.getSubmenu(EDIT_MENU_NAME).getItem(RELEASE_COMMENT_NAME)));

		// Sets the paste menu item action listener
		_pasteMenuItem//.addActionListener(new AcidePasteMenuItemListener());
			.addActionListener(new AcideInsertedItemListener(
				AcideMenuItemsConfiguration.getInstance()
				.getSubmenu(EDIT_MENU_NAME).getItem(PASTE_NAME)));

		// Sets the copy menu item action listener
		_copyMenuItem//.addActionListener(new AcideCopyMenuItemListener());
			.addActionListener(new AcideInsertedItemListener(
				AcideMenuItemsConfiguration.getInstance()
				.getSubmenu(EDIT_MENU_NAME).getItem(COPY_NAME)));

		// Sets the select all menu item action listener
		_selectAllMenuItem
				//.addActionListener(new AcideSelectAllMenuItemListener());
			.addActionListener(new AcideInsertedItemListener(
				AcideMenuItemsConfiguration.getInstance()
				.getSubmenu(EDIT_MENU_NAME).getItem(SELECT_ALL_NAME)));
		
		//Sets the cange case menu items listeners
		_changeCaseMenu.setListeners();

		// Sets the go to line menu item action listener
		_goToLineMenuItem
				//.addActionListener(new AcideGoToLineMenuItemListener());
			.addActionListener(new AcideInsertedItemListener(
				AcideMenuItemsConfiguration.getInstance()
				.getSubmenu(EDIT_MENU_NAME).getItem(GO_TO_LINE_NAME)));
		
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
	 * Disables the ACIDE - A Configurable IDE edit menu paste menu item.
	 */
	public void disablePaste() {

		// Disables the paste menu item
		_pasteMenuItem.setEnabled(false);

		// Updates the log
		AcideLog.getLog()
				.info(AcideLanguageManager.getInstance().getLabels()
						.getString("s73"));
	}

	/**
	 * Enables the ACIDE - A Configurable IDE edit menu paste menu item.
	 */
	public void enablePaste() {

		// Enables the paste menu item
		_pasteMenuItem.setEnabled(true);

		// Updates the log
		AcideLog.getLog()
				.info(AcideLanguageManager.getInstance().getLabels()
						.getString("s74"));
	}

	/**
	 * Configures the ACIDE - A Configurable IDE edit menu menu item options.
	 */
	public void configure() {

		// Disables the copy menu item
		_copyMenuItem.setEnabled(false);

		// Disables the paste menu item
		_pasteMenuItem.setEnabled(false);

		// Disables the cut menu item
		_cutMenuItem.setEnabled(false);

		// Enables the toggle comment menu item
		_toggleCommentMenuItem.setEnabled(true);
		
		// Enables the make comment menu item
		_makeCommentMenuItem.setEnabled(true);
		
		// Enables the release comment menu item
		_releaseCommentMenuItem.setEnabled(true);
		
		// Enables the change case comment menu
		_changeCaseMenu.setEnabled(true);
				
		// Enables or disables the undo menu item
		_undoMenuItem.setEnabled(AcideUndoManager.getInstance().canUndo());

		// Enables or disables the the redo menu item
		_redoAction.setEnabled(AcideUndoManager.getInstance().canRedo());

		// If the system clipboard is not empty
		if (Toolkit.getDefaultToolkit().getSystemClipboard().getContents(null) != null) {

			// If the console panel does not have the focus in the window
			if (!AcideMainWindow.getInstance().getConsolePanel().getTextPane()
					.hasFocus())

				// Enables the paste menu item
				_pasteMenuItem.setEnabled(true);
			else
			// If the caret is after the prompt position
			if (AcideMainWindow.getInstance().getConsolePanel().getTextPane()
					.getSelectionStart() >= AcideMainWindow.getInstance()
					.getConsolePanel().getPromptCaretPosition())

				// Enables the paste menu item
				_pasteMenuItem.setEnabled(true);
		}

		// If there are opened editors
		if (AcideMainWindow.getInstance().getFileEditorManager()
				.getNumberOfFileEditorPanels() > 0) {

			// If the console panel has the focus and there is selected text
			if (AcideMainWindow.getInstance().getConsolePanel().getTextPane()
					.hasFocus()
					&& AcideMainWindow.getInstance().getConsolePanel()
							.getTextPane().getSelectedText() != null) {

				// Enables the copy menu item
				_copyMenuItem.setEnabled(true);

				// If the caret position is after the prompt position
				if (AcideMainWindow.getInstance().getConsolePanel()
						.getTextPane().getSelectionStart() >= AcideMainWindow
						.getInstance().getConsolePanel()
						.getPromptCaretPosition())

					// Enables the cut menu item
					_cutMenuItem.setEnabled(true);
				
				_toggleCommentMenuItem.setEnabled(true);
				
			} else

			// If the file editor text edition area has the focus and
			// there is something selected
			if (AcideMainWindow.getInstance().getFileEditorManager()
					.getSelectedFileEditorPanel().getActiveTextEditionArea()
					.hasFocus()
					&& AcideMainWindow.getInstance().getFileEditorManager()
							.getSelectedFileEditorPanel()
							.getActiveTextEditionArea().getSelectedText() != null) {

				// Enables the copy menu item
				_copyMenuItem.setEnabled(true);

				// Enables the cut menu item
				_cutMenuItem.setEnabled(true);
				
				// Enables the toggle Comment menu item
				_toggleCommentMenuItem.setEnabled(true);
				
			}

			// Enables the go to line menu item
			_goToLineMenuItem.setEnabled(true);

			// Enables the select all menu item
			_selectAllMenuItem.setEnabled(true);

			// Enables the search menu item
			_searchMenuItem.setEnabled(true);

			// Enables the replace menu item
			_replaceMenuItem.setEnabled(true);

		} else {

			// We can copy from the output
			if (AcideMainWindow.getInstance().getConsolePanel().getTextPane()
					.getSelectedText() != null) {

				// Enables the copy menu item
				_copyMenuItem.setEnabled(true);

				// If the caret position is after the prompt position
				if (AcideMainWindow.getInstance().getConsolePanel()
						.getTextPane().getSelectionStart() >= AcideMainWindow
						.getInstance().getConsolePanel()
						.getPromptCaretPosition())

					// Enables the cut menu item
					_cutMenuItem.setEnabled(true);
			}

			// Disables the go to line menu item
			_goToLineMenuItem.setEnabled(false);

			// Disables the select all menu item
			_selectAllMenuItem.setEnabled(false);

			// Disables the search menu item
			_searchMenuItem.setEnabled(false);

			// Disables the replace menu item
			_replaceMenuItem.setEnabled(false);
		}
	}

	/**
	 * Returns the ACIDE - A Configurable IDE edit menu search menu item.
	 * 
	 * @return the ACIDE - A Configurable IDE edit menu search menu item.
	 */
	public JMenuItem getSearchMenuItem() {
		return _searchMenuItem;
	}

	/**
	 * Returns the ACIDE - A Configurable IDE edit menu go to line menu item.
	 * 
	 * @return the ACIDE - A Configurable IDE edit menu go to line menu item.
	 */
	public JMenuItem getGoToLineMenuItem() {
		return _goToLineMenuItem;
	}

	/**
	 * Returns the ACIDE - A Configurable IDE edit menu undo menu item.
	 * 
	 * @return the ACIDE - A Configurable IDE edit menu undo menu item.
	 */
	public JMenuItem getUndoMenuItem() {
		return _undoMenuItem;
	}

	/**
	 * Returns the ACIDE - A Configurable IDE edit menu copy menu item.
	 * 
	 * @return the ACIDE - A Configurable IDE edit menu copy menu item.
	 */
	public JMenuItem getCopyMenuItem() {
		return _copyMenuItem;
	}

	/**
	 * Returns the ACIDE - A Configurable IDE edit menu cut menu item.
	 * 
	 * @return the ACIDE - A Configurable IDE edit menu cut menu item.
	 */
	public JMenuItem getCutMenuItem() {
		return _cutMenuItem;
	}

	/**
	 * Returns the ACIDE - A Configurable IDE edit menu paste menu item.
	 * 
	 * @return the ACIDE - A Configurable IDE edit menu paste menu item.
	 */
	public JMenuItem getPasteMenuItem() {
		return _pasteMenuItem;
	}

	/**
	 * Returns the ACIDE - A Configurable IDE edit menu replace menu item.
	 * 
	 * @return the ACIDE - A Configurable IDE edit menu replace menu item.
	 */
	public JMenuItem getReplaceMenuItem() {
		return _replaceMenuItem;
	}

	/**
	 * Returns the ACIDE - A Configurable IDE edit menu redo menu item.
	 * 
	 * @return the ACIDE - A Configurable IDE edit menu redo menu item.
	 */
	public JMenuItem getRedoMenuItem() {
		return _redoMenuItem;
	}

	/**
	 * Returns the ACIDE - A Configurable IDE edit menu select all menu item.
	 * 
	 * @return the ACIDE - A Configurable IDE edit menu select all menu item.
	 */
	public JMenuItem getSelectAllMenuItem() {
		return _selectAllMenuItem;
	}

	/**
	 * Returns the ACIDE - A Configurable IDE edit menu undo action.
	 * 
	 * @return the ACIDE - A Configurable IDE edit menu undo action.
	 */
	public AcideUndoAction getUndoAction() {
		return _undoAction;
	}

	/**
	 * Returns the ACIDE - A Configurable IDE edit menu redo action.
	 * 
	 * @return the ACIDE - A Configurable IDE edit menu redo action.
	 */
	public AcideRedoAction getRedoAction() {
		return _redoAction;
	}
	
	/**
	 * Returns the ACIDE - A Configurable IDE edit menu toggle comment menu item.
	 * 
	 * @return the ACIDE - A Configurable IDE edit menu toggle comment menu item.
	 */
	public JMenuItem getToggleCommentMenuItem() {
		return _toggleCommentMenuItem;
	}
	
	/**
	 * Returns the ACIDE - A Configurable IDE edit menu change case menu.
	 * 
	 * @return the ACIDE - A Configurable IDE edit menu change case menu.
	 */
	public AcideChangeCaseMenu getChangeCaseMenuItem() {
		return _changeCaseMenu;
	}

	/**
	 * Returns the ACIDE - A Configurable IDE edit menu make comment menu item.
	 * 
	 * @return the ACIDE - A Configurable IDE edit menu make comment menu item.
	 */
	public JMenuItem getMakeCommentMenuItem() {
		return _makeCommentMenuItem;
	}

	/**
	 * Returns the ACIDE - A Configurable IDE edit menu release comment menu item.
	 * 
	 * @return the ACIDE - A Configurable IDE edit menu release comment menu item.
	 */
	public JMenuItem getReleaseCommentMenuItem() {
		return _releaseCommentMenuItem;
	}

}