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
package acide.gui.menuBar.configurationMenu.lexiconMenu;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

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
import acide.gui.menuBar.configurationMenu.AcideConfigurationMenu;
import acide.gui.menuBar.listeners.AcideMenuBarMouseClickListener;
import acide.language.AcideLanguageManager;
import acide.log.AcideLog;
import acide.resources.AcideResourceManager;
import acide.utils.IconsUtils;

/**
 * ACIDE - A Configurable IDE lexicon menu.
 * 
 * @version 0.11
 * @see JMenu
 */
public class AcideLexiconMenu extends JMenu {

	/**
	 * ACIDE - A Configurable IDE lexicon menu class serial version UID.
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * ACIDE - A Configurable IDE grammar menu name.
	 */
	public final static String LEXICON_MENU_NAME = "Lexicon";
	/**
	 * ACIDE - A Configurable IDE lexicon menu new lexicon menu item name.
	 */
	public static final String NEW_LEXICON_NAME = "New Lexicon";
	/**
	 * ACIDE - A Configurable IDE lexicon menu modify lexicon menu item name.
	 */
	public static final String MODIFY_LEXICON_NAME = "Modify Lexicon";
	/**
	 * ACIDE - A Configurable IDE lexicon menu document lexicon menu item name.
	 */
	public static final String DOCUMENT_LEXICON_NAME = "Document Lexicon";
	/**
	 * ACIDE - A Configurable IDE lexicon menu default lexicons menu item name.
	 */
	public static final String DEFAULT_LEXICONS_NAME = "Default Lexicons";
	/**
	 * ACIDE - A Configurable IDE lexicon menu new lexicon menu item.
	 */
	private JMenuItem _newLexiconMenuItem;
	/**
	 * ACIDE - A Configurable IDE lexicon menu new lexicon menu item has been inserted.
	 */
	private boolean _newLexiconInserted;
	/**
	 * ACIDE - A Configurable IDE lexicon menu document lexicon menu item.
	 */
	private JMenuItem _documentLexiconMenuItem;
	/**
	 * ACIDE - A Configurable IDE lexicon menu document lexicon menu item has been inserted.
	 */
	private boolean _documentLexiconInserted;
	/**
	 * ACIDE - A Configurable IDE lexicon menu modify lexicon menu item.
	 */
	private JMenuItem _modifyLexiconMenuItem;
	/**
	 * ACIDE - A Configurable IDE lexicon menu modify lexicon menu item has been inserted.
	 */
	private boolean _modifyLexiconInserted;
	/**
	 * ACIDE - A Configurable IDE lexicon menu save lexicon as Predetermine
	 * lexicon separator.
	 */
	private JSeparator _separator;
	/**
	 * ACIDE - A Configurable IDE lexicon menu default lexicons menu item.
	 */
	private JMenuItem _defaultLexiconsMenuItem;
	/**
	 * ACIDE - A Configurable IDE lexicon menu default lexicons menu item has been inserted.
	 */
	private boolean _defaultLexiconsInserted;
	/**
	 * ACIDE - A Configurable IDE lexicon menu configuration menu.
	 */
	private AcideMenuSubmenuConfiguration _lexiconSubmenuConfiguration;
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
	 * Creates a new ACIDE - A Configurable IDE lexicon menu.
	 */
	public AcideLexiconMenu() {
		
		_newLexiconInserted = false;
		_documentLexiconInserted = false;
		_modifyLexiconInserted = false;
		_defaultLexiconsInserted = false;
		
		_insertedItems = new HashMap<String, AcideInsertedItem>();
		
		_insertedMenus = new HashMap<String, AcideInsertedMenu>();
		
		_insertedObjects = new ArrayList<AcideMenuObjectConfiguration>();

		// Builds the menu components
		buildComponents();

		// Adds the components to the menu
		addComponents();

		// Sets the text of the lexicon menu components
		setTextOfMenuComponents();
	}

	/**
	 * Adds the components to the ACIDE - A Configurable IDE lexicon menu.
	 */
	private void addComponents() {
		
		Iterator<Object> it = AcideMenuItemsConfiguration.getInstance()
				.getMenuItemsManager().getSubmenu(AcideConfigurationMenu.CONFIGURATION_MENU_NAME)
				.getItemsManager().getSubmenu(LEXICON_MENU_NAME).getItemsManager().managerIterator();
		while (it.hasNext()){
			AcideMenuObjectConfiguration ob = (AcideMenuObjectConfiguration) it.next();
			String name = ob.getName();
			if (name.equals(NEW_LEXICON_NAME)){
				// Adds the new lexicon menu item to the menu
				add(_newLexiconMenuItem);
				_newLexiconInserted = true;
			}else if (name.equals(DOCUMENT_LEXICON_NAME)){
				// Adds the document lexicon menu item to the menu
				add(_documentLexiconMenuItem);
				_documentLexiconInserted = true;
			}else if (name.equals(MODIFY_LEXICON_NAME)){
				// Adds the modify lexicon menu item to the menu
				add(_modifyLexiconMenuItem);
				_modifyLexiconInserted = true;
				// Adds the modify lexicon default lexicons separator
				add(_separator);
			}else if (name.equals(DEFAULT_LEXICONS_NAME)){
				// Adds the default lexicons menu item
				add(_defaultLexiconsMenuItem);
				_defaultLexiconsInserted = true;
			}else {
				if (ob.isSubmenu()){
					add(_insertedMenus.get(ob.getName()));
				}else{
					add(_insertedItems.get(ob.getName()));
				}
			}
		}
		
		if (!_newLexiconInserted)
			add(_newLexiconMenuItem);
		if (!_documentLexiconInserted)
			add(_documentLexiconMenuItem);
		if (!_modifyLexiconInserted)
			add(_modifyLexiconMenuItem);
		if (!_defaultLexiconsInserted)
			add(_defaultLexiconsMenuItem);
		
	}

	/**
	 * Builds the ACIDE - A Configurable IDE lexicon menu components.
	 */
	private void buildComponents() {
		
		if (!AcideMenuItemsConfiguration.getInstance().getMenuItemsManager()
				.getSubmenu(AcideConfigurationMenu.CONFIGURATION_MENU_NAME).hasSubmenu(LEXICON_MENU_NAME)){
			AcideMenuItemsConfiguration.getInstance().getMenuItemsManager()
				.getSubmenu(AcideConfigurationMenu.CONFIGURATION_MENU_NAME)
					.insertObject(new AcideMenuSubmenuConfiguration(LEXICON_MENU_NAME));
		}

		Iterator<Object> it = AcideMenuItemsConfiguration.getInstance()
				.getMenuItemsManager().getSubmenu(AcideConfigurationMenu.CONFIGURATION_MENU_NAME)
				.getItemsManager().getSubmenu(LEXICON_MENU_NAME).getItemsManager().managerIterator();
		
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
					if (obItem.getImage().equals(""))
						_insertedItems.put(obItem.getName(), new AcideInsertedItem(obItem));
					else 
						_insertedItems.put(obItem.getName(), new AcideInsertedItem(IconsUtils.getIcon(
								obItem.getImage()), obItem));
				}
			}
		}
		
		// Creates the new lexicon menu item
		ImageIcon icon = IconsUtils.getIcon(AcideMenuItemsConfiguration.getInstance()
				.getMenuItemsManager().getSubmenu(AcideConfigurationMenu.CONFIGURATION_MENU_NAME)
				.getSubmenu(LEXICON_MENU_NAME).getItem(NEW_LEXICON_NAME).getImage());
		
		if (icon != null)
			_newLexiconMenuItem = new JMenuItem(icon);
		else
			_newLexiconMenuItem = new JMenuItem();

		// Sets the new lexicon menu item name
		_newLexiconMenuItem.setName(NEW_LEXICON_NAME);

		// Creates the document lexicon menu item
		icon = IconsUtils.getIcon(AcideMenuItemsConfiguration.getInstance()
				.getMenuItemsManager().getSubmenu(AcideConfigurationMenu.CONFIGURATION_MENU_NAME)
				.getSubmenu(LEXICON_MENU_NAME).getItem(DOCUMENT_LEXICON_NAME).getImage());
		
		if (icon != null)
			_documentLexiconMenuItem = new JMenuItem(icon);
		else
			_documentLexiconMenuItem = new JMenuItem();

		// Sets the document lexicon menu item name
		_documentLexiconMenuItem.setName(DOCUMENT_LEXICON_NAME);

		// Creates the modify lexicon menu item
		icon = IconsUtils.getIcon(AcideMenuItemsConfiguration.getInstance()
				.getMenuItemsManager().getSubmenu(AcideConfigurationMenu.CONFIGURATION_MENU_NAME)
				.getSubmenu(LEXICON_MENU_NAME).getItem(MODIFY_LEXICON_NAME).getImage());
		
		if (icon != null)
			_modifyLexiconMenuItem = new JMenuItem(icon);
		else
			_modifyLexiconMenuItem = new JMenuItem();

		// Sets the modify lexicon menu item name
		_modifyLexiconMenuItem.setName(MODIFY_LEXICON_NAME);

		// Creates the modify lexicon default lexicons separator
		_separator = new JSeparator();

		// Creates the default lexicons menu item
		icon = IconsUtils.getIcon(AcideMenuItemsConfiguration.getInstance()
				.getMenuItemsManager().getSubmenu(AcideConfigurationMenu.CONFIGURATION_MENU_NAME)
				.getSubmenu(LEXICON_MENU_NAME).getItem(DEFAULT_LEXICONS_NAME).getImage());
		
		if (icon != null)
			_defaultLexiconsMenuItem = new JMenuItem(icon);
		else
			_defaultLexiconsMenuItem = new JMenuItem();

		// Sets the default lexicons menu item name
		_defaultLexiconsMenuItem.setName(DEFAULT_LEXICONS_NAME);
	}

	/**
	 * Sets the text of the ACIDE - A Configurable IDE lexicon menu components
	 * with the labels in the selected language to display.
	 */
	public void setTextOfMenuComponents() {

		// Sets the new lexicon menu item text
		_newLexiconMenuItem.setText(AcideLanguageManager.getInstance()
				.getLabels().getString("s249"));

		// Sets the document lexicon menu item text
		_documentLexiconMenuItem.setText(AcideLanguageManager.getInstance()
				.getLabels().getString("s1093"));

		// Sets the document lexicon menu item accelerator
		_documentLexiconMenuItem.setAccelerator(KeyStroke.getKeyStroke(
				KeyEvent.VK_L, ActionEvent.CTRL_MASK + ActionEvent.SHIFT_MASK));

		// Sets the modify lexicon menu item text
		_modifyLexiconMenuItem.setText(AcideLanguageManager.getInstance()
				.getLabels().getString("s29"));

		// Sets the modify lexicon menu item accelerator
		_modifyLexiconMenuItem.setAccelerator(KeyStroke.getKeyStroke(
				KeyEvent.VK_X, ActionEvent.CTRL_MASK + ActionEvent.SHIFT_MASK));

		// Sets the default lexicons menu item text
		_defaultLexiconsMenuItem.setText(AcideLanguageManager.getInstance()
				.getLabels().getString("s1080"));
		
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
		if (!(name.equals(NEW_LEXICON_NAME))
			&& !(name.equals(MODIFY_LEXICON_NAME))
			&& !(name.equals(DOCUMENT_LEXICON_NAME))
			&& !(name.equals(DEFAULT_LEXICONS_NAME))){
			return true;
		}else {
			return false;
		}
	}

	/**
	 * Sets the ACIDE - A Configurable IDE lexicon menu menu item listeners.
	 */
	public void setListeners() {

		// Sets the new lexicon menu item action listener
		_newLexiconMenuItem
				//.addActionListener(new AcideNewLexiconMenuItemListener());
			.addActionListener(new AcideInsertedItemListener(
				AcideMenuItemsConfiguration.getInstance()
				.getSubmenu(AcideConfigurationMenu.CONFIGURATION_MENU_NAME)
				.getSubmenu(LEXICON_MENU_NAME).getItem(NEW_LEXICON_NAME)));

		// Sets the modify lexicon menu item action listener
		_modifyLexiconMenuItem
				//.addActionListener(new AcideModifyLexiconMenuItemListener());
			.addActionListener(new AcideInsertedItemListener(
				AcideMenuItemsConfiguration.getInstance()
				.getSubmenu(AcideConfigurationMenu.CONFIGURATION_MENU_NAME)
				.getSubmenu(LEXICON_MENU_NAME).getItem(MODIFY_LEXICON_NAME)));

		// Sets the document lexicon menu item action listener
		_documentLexiconMenuItem
				//.addActionListener(new AcideDocumentLexiconMenuItemListener());
		.addActionListener(new AcideInsertedItemListener(
				AcideMenuItemsConfiguration.getInstance()
				.getSubmenu(AcideConfigurationMenu.CONFIGURATION_MENU_NAME)
				.getSubmenu(LEXICON_MENU_NAME).getItem(DOCUMENT_LEXICON_NAME)));

		// Sets the default lexicons menu item action listener
		_defaultLexiconsMenuItem
				//.addActionListener(new AcideDefaultLexiconsMenuItemListener());
			.addActionListener(new AcideInsertedItemListener(
				AcideMenuItemsConfiguration.getInstance()
				.getSubmenu(AcideConfigurationMenu.CONFIGURATION_MENU_NAME)
				.getSubmenu(LEXICON_MENU_NAME).getItem(DEFAULT_LEXICONS_NAME)));
		
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
	 * Builds the ACIDE - A Configurable IDE lexicon menu.
	 */
	public void build() {
		
		AcideMenuItemConfiguration newLexiconConfiguration;
		AcideMenuItemConfiguration documentLexiconConfiguration;
		AcideMenuItemConfiguration modifyLexiconConfiguration;
		AcideMenuItemConfiguration defaultLexiconsConfiguration;

		_lexiconSubmenuConfiguration = AcideMenuItemsConfiguration.getInstance()
					.getSubmenu(AcideConfigurationMenu.CONFIGURATION_MENU_NAME).getSubmenu(LEXICON_MENU_NAME);
			
		// Sets the new lexicon menu item to visible or not visible
		newLexiconConfiguration = _lexiconSubmenuConfiguration.getItem(NEW_LEXICON_NAME);
		_newLexiconMenuItem.setVisible(newLexiconConfiguration.isVisible());
			
		// Sets the document lexicon menu item to visible or not visible
		documentLexiconConfiguration = _lexiconSubmenuConfiguration.getItem(DOCUMENT_LEXICON_NAME);
		_documentLexiconMenuItem.setVisible(documentLexiconConfiguration.isVisible());

		// Sets the modify lexicon menu item to visible or not visible
		modifyLexiconConfiguration = _lexiconSubmenuConfiguration.getItem(MODIFY_LEXICON_NAME);
		_modifyLexiconMenuItem.setVisible(modifyLexiconConfiguration.isVisible());

		// Sets the default lexicons menu item to visible or not visible
		defaultLexiconsConfiguration = _lexiconSubmenuConfiguration.getItem(DEFAULT_LEXICONS_NAME);
		_defaultLexiconsMenuItem.setVisible(defaultLexiconsConfiguration.isVisible());
			
		// Sets the save lexicon as default lexicons separator to visible or
		// not visible
		_separator.setVisible(_newLexiconMenuItem.isVisible()
				|| _documentLexiconMenuItem.isVisible()
				|| _modifyLexiconMenuItem.isVisible()
				|| _defaultLexiconsMenuItem.isVisible());
		
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
		
		// Sets the lexicon menu to visible or not visible
		_lexiconSubmenuConfiguration.setVisible(_documentLexiconMenuItem.isVisible()
				|| _modifyLexiconMenuItem.isVisible()
				|| _newLexiconMenuItem.isVisible()
				|| _defaultLexiconsMenuItem.isVisible());
		_lexiconSubmenuConfiguration.setErasable(false);

		
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
	 * Enables the ACIDE - A Configurable IDE lexicon menu.
	 */
	public void enableMenu() {

		// Enables the new lexicon menu item
		_newLexiconMenuItem.setEnabled(true);

		// Enables the document lexicon menu item
		_documentLexiconMenuItem.setEnabled(true);

		// Enables the modify lexicon menu item
		_modifyLexiconMenuItem.setEnabled(true);

		// Enables the default lexicons menu item
		_defaultLexiconsMenuItem.setEnabled(true);
	}

	/**
	 * Disables the ACIDE - A Configurable IDE lexicon menu.
	 */
	public void disableMenu() {

		// Disables the new lexicon menu item
		_newLexiconMenuItem.setEnabled(false);

		// Disables the document lexicon menu item
		_documentLexiconMenuItem.setEnabled(false);

		// Disables the modify lexicon menu item
		_modifyLexiconMenuItem.setEnabled(false);

		// Enables the default lexicons menu item
		_defaultLexiconsMenuItem.setEnabled(true);
	}

	/**
	 * Documents the ACIDE - A Configurable IDE lexicon configuration in the
	 * selected file editor panel.
	 * 
	 * @param absolutePath new lexicon configuration to set.
	 */
	public void documentLexicon(String absolutePath) {

		// Loads the lexicon configuration
		AcideMainWindow.getInstance().getFileEditorManager()
				.getSelectedFileEditorPanel().getLexiconConfiguration()
				.load(absolutePath);

		// Resets the selected file editor text edition area
		AcideMainWindow.getInstance().getFileEditorManager()
				.getSelectedFileEditorPanel().resetStyledDocument();

		// Updates the lexicon message status bar
		AcideMainWindow
				.getInstance()
				.getStatusBar()
				.setLexiconMessage(
						AcideLanguageManager.getInstance().getLabels()
								.getString("s449")
								+ " "
								+ AcideMainWindow.getInstance()
										.getFileEditorManager()
										.getSelectedFileEditorPanel()
										.getLexiconConfiguration().getName());
	}

	/**
	 * Configures the ACIDE - A Configurable IDE lexicon menu.
	 */
	public void configure(){
		
		if (AcideMainWindow.getInstance().getFileEditorManager()
				.getNumberOfFileEditorPanels() > 0) {

			// Enables the lexicon menu
			enableMenu();

		} else {

			// Disables the lexicon menu
			disableMenu();
		}
	}
	
	/**
	 * Returns the ACIDE - A Configurable IDE lexicon menu new lexicon menu item
	 * 
	 * @return the ACIDE - A Configurable IDE lexicon menu new lexicon menu item
	 */
	public JMenuItem getNewLexiconMenuItem() {
		return _newLexiconMenuItem;
	}

	/**
	 * Returns the ACIDE - A Configurable IDE lexicon menu document lexicon menu
	 * item.
	 * 
	 * @return the ACIDE - A Configurable IDE lexicon menu document lexicon menu
	 *         item.
	 */
	public JMenuItem getDocumentLexiconMenuItem() {
		return _documentLexiconMenuItem;
	}

	/**
	 * Returns the ACIDE - A Configurable IDE lexicon menu modify lexicon menu
	 * item.
	 * 
	 * @return the ACIDE - A Configurable IDE lexicon menu modify lexicon menu
	 *         item.
	 */
	public JMenuItem getModifyLexiconMenuItem() {
		return _modifyLexiconMenuItem;
	}
}