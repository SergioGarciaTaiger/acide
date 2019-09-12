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
package acide.gui.menuBar.configurationMenu.fileEditor;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import javax.swing.ImageIcon;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;

import acide.configuration.menu.AcideInsertedItem;
import acide.configuration.menu.AcideInsertedItemListener;
import acide.configuration.menu.AcideInsertedMenu;
import acide.configuration.menu.AcideMenuConfiguration;
import acide.configuration.menu.AcideMenuItemConfiguration;
import acide.configuration.menu.AcideMenuItemsConfiguration;
import acide.configuration.menu.AcideMenuObjectConfiguration;
import acide.configuration.menu.AcideMenuSubmenuConfiguration;
import acide.configuration.workbench.AcideWorkbenchConfiguration;
import acide.gui.menuBar.configurationMenu.AcideConfigurationMenu;
import acide.gui.menuBar.listeners.AcideMenuBarMouseClickListener;
import acide.language.AcideLanguageManager;
import acide.log.AcideLog;
import acide.resources.AcideResourceManager;
import acide.utils.IconsUtils;

/**
 * ACIDE - A Configurable IDE file editor menu.
 * 
 * @version 0.13
 * @see JMenu
 */
public class AcideFileEditorMenu extends JMenu {

	/**
	 * ACIDE - A Configurable IDE file editor menu serial version UID.
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * ACIDE - A Configurable IDE grammar menu name.
	 */
	public final static String FILE_EDITOR_MENU_NAME = "File Editor";
	/**
	 * ACIDE - A Configurable IDE file editor menu file editor display options
	 * menu item name.
	 */
	public static final String FILE_EDITOR_DISPLAY_OPTIONS_NAME = "File Editor Display Options";
	/**
	 * ACIDE - A Configurable IDE file editor menu file editor preferences
	 * menu item name.
	 */
	public static final String PREFERENCES_NAME = "Preferences";
	/**
	 * ACIDE - A Configurable IDE file editor menu automatic indent menu item
	 * name.
	 */
	public static final String AUTOMATIC_INDENT_NAME = "Automatic Indent";
	/**
	 * ACIDE - A Configurable IDE file editor menu line wrapping menu item name.
	 */
	public static final String LINE_WRAPPING_NAME = "Line Wrapping";
	/**
	 * ACIDE - A Configurable IDE file editor menu maximum lines to console menu
	 * item name.
	 */
	public static final String MAXIMUM_LINES_TO_CONSOLE_NAME = "Maximum Lines To Console";
	/**
	 * ACIDE - A Configurable IDE file editor send to console confirmation menu item name.
	 */
	public static final String SEND_TO_CONSOLE_CONFIRMATION_NAME = "Send To Console Confirmation";
	/**
	 * ACIDE - A Configurable IDE file editor menu file editor preferences
	 * menu item.
	 */
	private JMenuItem _preferencesMenuItem;
	/**
	 * ACIDE - A Configurable IDE file editor menu 
	 * preferences menu item has been inserted.
	 */
	private boolean _preferencesInserted;
	/**
	 * ACIDE - A Configurable IDE file editor menu file editor display options
	 * menu item.
	 */
	private JMenuItem _fileEditorDisplayOptionsMenuItem;
	/**
	 * ACIDE - A Configurable IDE file editor menu 
	 * display options menu item has been inserted.
	 */
	private boolean _fileEditorDisplayOptionsInserted;
	/**
	 * ACIDE - A Configurable IDE file editor menu automatic indent menu item.
	 */
	private JCheckBoxMenuItem _automaticIndentCheckBoxMenuItem;
	/**
	 * ACIDE - A Configurable IDE file editor menu 
	 * automatic indent menu item has been inserted.
	 */
	private boolean _automaticIndentInserted;
	/**
	 * ACIDE - A Configurable IDE file editor menu maximum lines to console menu
	 * item.
	 */
	private JMenuItem _maximumLinesToConsoleMenuItem;
	/**
	 * ACIDE - A Configurable IDE file editor menu 
	 * maximum lines menu item has been inserted.
	 */
	private boolean _maximumLinesInserted;
	/**
	 * ACIDE - A Configurable IDE file editor menu line wrapping menu item.
	 */
	private JCheckBoxMenuItem _lineWrappingCheckBoxMenuItem;
	/**
	 * ACIDE - A Configurable IDE file editor menu 
	 * line wrapping menu item has been inserted.
	 */
	private boolean _lineWrappingInserted;
	/**
	 * ACIDE - A Configurable IDE file editor send to console confirmation menu item.
	 */
	private JCheckBoxMenuItem _sendToConsoleConfirmationMenuItem;
	/**
	 * ACIDE - A Configurable IDE file editor menu 
	 * send console confirmation menu item has been inserted.
	 */
	private boolean _sendToConsoleConfirmationInserted;
	/**
	 * ACIDE - A Configurable IDE file editor menu configuration menu.
	 */
	private AcideMenuSubmenuConfiguration _fileEditorSubmenuConfiguration;
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
	 * Creates a new ACIDE - A Configurable IDE file editor menu.
	 */
	public AcideFileEditorMenu() {
		
		_preferencesInserted = false;
		_fileEditorDisplayOptionsInserted = false;
		_automaticIndentInserted = false;
		_maximumLinesInserted = false;
		_sendToConsoleConfirmationInserted = false;
		_lineWrappingInserted = false;
		
		_insertedItems = new HashMap<String, AcideInsertedItem>();
		
		_insertedMenus = new HashMap<String, AcideInsertedMenu>();
		
		_insertedObjects = new ArrayList<AcideMenuObjectConfiguration>();

		// Builds the menu components
		buildComponents();

		// Adds the components to the menu
		addComponents();

		// Sets the text of the file editor menu components
		setTextOfMenuComponets();
	}

	/**
	 * Adds the components to the ACIDE - A Configurable IDE file editor menu
	 */
	private void addComponents() {
		
		Iterator<Object> it = AcideMenuItemsConfiguration.getInstance()
				.getMenuItemsManager().getSubmenu(AcideConfigurationMenu.CONFIGURATION_MENU_NAME)
				.getItemsManager().getSubmenu(FILE_EDITOR_MENU_NAME).getItemsManager().managerIterator();
		while (it.hasNext()){
			AcideMenuObjectConfiguration ob = (AcideMenuObjectConfiguration) it.next();
			String name = ob.getName();
			if (name.equals(PREFERENCES_NAME)){
				// Adds the preferences menu item to the menu
				add(_preferencesMenuItem);
				_preferencesInserted = true;
			}else if (name.equals(FILE_EDITOR_DISPLAY_OPTIONS_NAME)){
				// Adds the file editor display options menu item to the menu
				add(_fileEditorDisplayOptionsMenuItem);
				_fileEditorDisplayOptionsInserted = true;
			}else if (name.equals(AUTOMATIC_INDENT_NAME)){
				// Adds the automatic indent check box menu item to the menu
				add(_automaticIndentCheckBoxMenuItem);
				_automaticIndentInserted = true;
			}else if (name.equals(LINE_WRAPPING_NAME)){
				// Adds the line wrapping check box menu item to the menu
				add(_lineWrappingCheckBoxMenuItem);
				_lineWrappingInserted = true;
			}else if (name.equals(MAXIMUM_LINES_TO_CONSOLE_NAME)){
				// Adds the maximum lines to console menu item to the menu
				add(_maximumLinesToConsoleMenuItem);
				_maximumLinesInserted = true;
			}else if (name.equals(SEND_TO_CONSOLE_CONFIRMATION_NAME)){
				// Adds the send to console confirmation menu item to the menu
				add(_sendToConsoleConfirmationMenuItem);
				_sendToConsoleConfirmationInserted = true;
			}else {
				if (ob.isSubmenu()){
					add(_insertedMenus.get(ob.getName()));
				}else{
					add(_insertedItems.get(ob.getName()));
				}
			}
		}
		
		if (!_preferencesInserted)
			add(_preferencesMenuItem);
		if (!_fileEditorDisplayOptionsInserted)
			add(_fileEditorDisplayOptionsMenuItem);
		if (!_automaticIndentInserted)
			add(_automaticIndentCheckBoxMenuItem);
		if (!_lineWrappingInserted)
			add(_lineWrappingCheckBoxMenuItem);
		if (!_maximumLinesInserted)
			add(_maximumLinesToConsoleMenuItem);
		if (!_sendToConsoleConfirmationInserted)
			add(_sendToConsoleConfirmationMenuItem);
		
	}

	/**
	 * Builds the ACIDE - A Configurable IDE file editor menu components.
	 */
	private void buildComponents() {
		
		if (!AcideMenuItemsConfiguration.getInstance().getMenuItemsManager()
				.getSubmenu(AcideConfigurationMenu.CONFIGURATION_MENU_NAME).hasSubmenu(FILE_EDITOR_MENU_NAME)){
			AcideMenuItemsConfiguration.getInstance().getMenuItemsManager()
				.getSubmenu(AcideConfigurationMenu.CONFIGURATION_MENU_NAME)
					.insertObject(new AcideMenuSubmenuConfiguration(FILE_EDITOR_MENU_NAME));
		}
		
		Iterator<Object> it = AcideMenuItemsConfiguration.getInstance()
				.getMenuItemsManager().getSubmenu(AcideConfigurationMenu.CONFIGURATION_MENU_NAME)
				.getItemsManager().getSubmenu(FILE_EDITOR_MENU_NAME).getItemsManager().managerIterator();
		
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
		
		// Creates the file editor preferences menu item
			ImageIcon icon = IconsUtils.getIcon(AcideMenuItemsConfiguration.getInstance()
						.getMenuItemsManager().getSubmenu(AcideConfigurationMenu.CONFIGURATION_MENU_NAME)
						.getSubmenu(FILE_EDITOR_MENU_NAME).getItem(FILE_EDITOR_DISPLAY_OPTIONS_NAME).getImage());
				
				if (icon != null)
					_preferencesMenuItem = new JMenuItem(icon);
				else
					_preferencesMenuItem = new JMenuItem();
				
				// Sets the preferences menu item name
				_preferencesMenuItem
						.setName(PREFERENCES_NAME);

		// Creates the file editor display options menu item
		   icon = IconsUtils.getIcon(AcideMenuItemsConfiguration.getInstance()
				.getMenuItemsManager().getSubmenu(AcideConfigurationMenu.CONFIGURATION_MENU_NAME)
				.getSubmenu(FILE_EDITOR_MENU_NAME).getItem(FILE_EDITOR_DISPLAY_OPTIONS_NAME).getImage());
		
		if (icon != null)
			_fileEditorDisplayOptionsMenuItem = new JMenuItem(icon);
		else
			_fileEditorDisplayOptionsMenuItem = new JMenuItem();
		
		// Sets the file editor display options menu item name
		_fileEditorDisplayOptionsMenuItem
				.setName(FILE_EDITOR_DISPLAY_OPTIONS_NAME);

		// Creates the automatic indent check box menu item
		icon = IconsUtils.getIcon(AcideMenuItemsConfiguration.getInstance()
				.getMenuItemsManager().getSubmenu(AcideConfigurationMenu.CONFIGURATION_MENU_NAME)
				.getSubmenu(FILE_EDITOR_MENU_NAME).getItem(AUTOMATIC_INDENT_NAME).getImage());
		
		if (icon != null)
			_automaticIndentCheckBoxMenuItem = new JCheckBoxMenuItem(icon);
		else
			_automaticIndentCheckBoxMenuItem = new JCheckBoxMenuItem();

		// Sets the automatic indent check box menu item name
		_automaticIndentCheckBoxMenuItem.setName(AUTOMATIC_INDENT_NAME);

		// Creates the maximum lines to console menu item
		icon = IconsUtils.getIcon(AcideMenuItemsConfiguration.getInstance()
				.getMenuItemsManager().getSubmenu(AcideConfigurationMenu.CONFIGURATION_MENU_NAME)
				.getSubmenu(FILE_EDITOR_MENU_NAME).getItem(MAXIMUM_LINES_TO_CONSOLE_NAME).getImage());
		
		if (icon != null)
			_maximumLinesToConsoleMenuItem = new JMenuItem(icon);
		else
			_maximumLinesToConsoleMenuItem = new JMenuItem();

		// Creates the maximum lines to console menu item name
		_maximumLinesToConsoleMenuItem.setName(MAXIMUM_LINES_TO_CONSOLE_NAME);

		// Creates the line wrapping check box menu item
		icon = IconsUtils.getIcon(AcideMenuItemsConfiguration.getInstance()
				.getMenuItemsManager().getSubmenu(AcideConfigurationMenu.CONFIGURATION_MENU_NAME)
				.getSubmenu(FILE_EDITOR_MENU_NAME).getItem(LINE_WRAPPING_NAME).getImage());
		
		if (icon != null)
			_lineWrappingCheckBoxMenuItem = new JCheckBoxMenuItem(icon);
		else
			_lineWrappingCheckBoxMenuItem = new JCheckBoxMenuItem();

		// Sets the line wrapping check box menu item accelerator
		_lineWrappingCheckBoxMenuItem.setAccelerator(KeyStroke.getKeyStroke(
				KeyEvent.VK_A, ActionEvent.CTRL_MASK + ActionEvent.SHIFT_MASK));

		// Sets the line wrapping check box menu item name
		_lineWrappingCheckBoxMenuItem.setName(LINE_WRAPPING_NAME);
		
		// Creates the send to console confirmation menu item
		icon = IconsUtils.getIcon(AcideMenuItemsConfiguration.getInstance()
				.getMenuItemsManager().getSubmenu(AcideConfigurationMenu.CONFIGURATION_MENU_NAME)
				.getSubmenu(FILE_EDITOR_MENU_NAME).getItem(SEND_TO_CONSOLE_CONFIRMATION_NAME).getImage());
		
		if (icon != null)
			_sendToConsoleConfirmationMenuItem = new JCheckBoxMenuItem(icon);
		else
			_sendToConsoleConfirmationMenuItem = new JCheckBoxMenuItem();
		
		// Creates the send to console confirmation menu item name
		_sendToConsoleConfirmationMenuItem.setName(SEND_TO_CONSOLE_CONFIRMATION_NAME);
	}

	/**
	 * Sets the text of the ACIDE - A Configurable IDE file editor menu
	 * components with the labels in the selected language to display.
	 */
	public void setTextOfMenuComponets() {
		
		// Sets the preferences menu item text
		_preferencesMenuItem.setText(AcideLanguageManager
				.getInstance().getLabels().getString("s2245"));

		// Sets the file editor display options menu item text
		_fileEditorDisplayOptionsMenuItem.setText(AcideLanguageManager
				.getInstance().getLabels().getString("s1041"));

		// Sets the automatic indent check box menu item text
		_automaticIndentCheckBoxMenuItem.setText(AcideLanguageManager
				.getInstance().getLabels().getString("s1097"));

		// Sets the line wrapping check box menu item text
		_lineWrappingCheckBoxMenuItem.setText(AcideLanguageManager
				.getInstance().getLabels().getString("s2014"));

		// Sets the maximum lines to console menu item text
		_maximumLinesToConsoleMenuItem.setText(AcideLanguageManager
				.getInstance().getLabels().getString("s2007"));
		
		// Sets the send to console confirmation menu item text
		_sendToConsoleConfirmationMenuItem.setText(AcideLanguageManager
				.getInstance().getLabels().getString("s2103"));
		
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
	 * Updates the ACIDE - A Configurable IDE file editor menu components
	 * visibility with the menu configuration.
	 */
	public void updateComponentsVisibility() {
		
		AcideMenuItemConfiguration preferencesConfiguration;
		AcideMenuItemConfiguration fileEditorDisplayOptionsConfiguration;
		AcideMenuItemConfiguration automaticIndentConfiguration;
		AcideMenuItemConfiguration lineWrappingConfiguration;
		AcideMenuItemConfiguration maximumLinesToConsoleConfiguration;
		AcideMenuItemConfiguration sendToConsoleConfirmationConfiguration;

		_fileEditorSubmenuConfiguration = AcideMenuItemsConfiguration.getInstance()
					.getSubmenu(AcideConfigurationMenu.CONFIGURATION_MENU_NAME).getSubmenu(FILE_EDITOR_MENU_NAME);
		
		// Sets the preferences menu item to visible or not
		// visible
		preferencesConfiguration = _fileEditorSubmenuConfiguration.getItem(PREFERENCES_NAME);
		_preferencesMenuItem.setVisible(preferencesConfiguration.isVisible());
				
		// Sets the file editor display options menu item to visible or not
		// visible
		fileEditorDisplayOptionsConfiguration = _fileEditorSubmenuConfiguration.getItem(FILE_EDITOR_DISPLAY_OPTIONS_NAME);
		_fileEditorDisplayOptionsMenuItem.setVisible(fileEditorDisplayOptionsConfiguration.isVisible());
		
		// Sets the automatic indent check box menu item to visible or not
		// visible
		automaticIndentConfiguration = _fileEditorSubmenuConfiguration.getItem(AUTOMATIC_INDENT_NAME);
		_automaticIndentCheckBoxMenuItem.setVisible(automaticIndentConfiguration.isVisible());
		
		// Sets the line wrapping check box menu item to visible or not
		// visible
		lineWrappingConfiguration = _fileEditorSubmenuConfiguration.getItem(LINE_WRAPPING_NAME);
		_lineWrappingCheckBoxMenuItem.setVisible(lineWrappingConfiguration.isVisible());
			
		// Sets the maximum lines to console menu item to visible or not
		// visible
		maximumLinesToConsoleConfiguration = _fileEditorSubmenuConfiguration.getItem(MAXIMUM_LINES_TO_CONSOLE_NAME);
		_maximumLinesToConsoleMenuItem.setVisible(maximumLinesToConsoleConfiguration.isVisible());
			
		//Sets send to console confirmation menu item to visible or not
		// visible
		sendToConsoleConfirmationConfiguration = _fileEditorSubmenuConfiguration.getItem(SEND_TO_CONSOLE_CONFIRMATION_NAME);
		_sendToConsoleConfirmationMenuItem.setVisible(sendToConsoleConfirmationConfiguration.isVisible());

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
		
		// Sets the file editor menu to visible or not visible
		_fileEditorSubmenuConfiguration.setVisible(_automaticIndentCheckBoxMenuItem.isVisible()
						|| _preferencesMenuItem.isVisible()
						|| _fileEditorDisplayOptionsMenuItem.isVisible()
						|| _lineWrappingCheckBoxMenuItem.isVisible()
						|| _maximumLinesToConsoleMenuItem.isVisible()
						|| _sendToConsoleConfirmationMenuItem.isVisible());

		_fileEditorSubmenuConfiguration.setErasable(false);
		
						
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
		if (!(name.equals(FILE_EDITOR_DISPLAY_OPTIONS_NAME))
			&& !(name.equals(PREFERENCES_NAME))
			&& !(name.equals(LINE_WRAPPING_NAME))
			&& !(name.equals(AUTOMATIC_INDENT_NAME))
			&& !(name.equals(MAXIMUM_LINES_TO_CONSOLE_NAME))
			&& !(name.equals(SEND_TO_CONSOLE_CONFIRMATION_NAME))){
			return true;
		}else {
			return false;
		}
	}

	/**
	 * Sets ACIDE - A Configurable IDE file editor menu item listeners.
	 */
	public void setListeners() {
		
		// Sets the preferences menu item action listener
		_preferencesMenuItem
				//.addActionListener(new AcideFileEditorPreferencesMenuItemListener());
			.addActionListener(new AcideInsertedItemListener(
				AcideMenuItemsConfiguration.getInstance()
				.getSubmenu(AcideConfigurationMenu.CONFIGURATION_MENU_NAME)
				.getSubmenu(FILE_EDITOR_MENU_NAME).getItem(PREFERENCES_NAME)));
		
		// Sets the file editor display options menu item action listener
		_fileEditorDisplayOptionsMenuItem
				//.addActionListener(new AcideFileEditorDisplayOptionsMenuItemListener());
			.addActionListener(new AcideInsertedItemListener(
				AcideMenuItemsConfiguration.getInstance()
				.getSubmenu(AcideConfigurationMenu.CONFIGURATION_MENU_NAME)
				.getSubmenu(FILE_EDITOR_MENU_NAME).getItem(FILE_EDITOR_DISPLAY_OPTIONS_NAME)));

		// Sets the line wrapping check box menu item action listener
		_lineWrappingCheckBoxMenuItem
				//.addActionListener(new AcideLineWrappingMenuItemListener());
			.addActionListener(new AcideInsertedItemListener(
				AcideMenuItemsConfiguration.getInstance()
				.getSubmenu(AcideConfigurationMenu.CONFIGURATION_MENU_NAME)
				.getSubmenu(FILE_EDITOR_MENU_NAME).getItem(LINE_WRAPPING_NAME)));

		// Sets the automatic indent check box menu item action listener
		_automaticIndentCheckBoxMenuItem
				//.addActionListener(new AcideAutomaticIndentMenuItemListener());
			.addActionListener(new AcideInsertedItemListener(
				AcideMenuItemsConfiguration.getInstance()
				.getSubmenu(AcideConfigurationMenu.CONFIGURATION_MENU_NAME)
				.getSubmenu(FILE_EDITOR_MENU_NAME).getItem(AUTOMATIC_INDENT_NAME)));

		// Sets the maximum lines to console menu item action listener
		_maximumLinesToConsoleMenuItem
				//.addActionListener(new AcideMaximumLinesToConsoleMenuItemListener());
			.addActionListener(new AcideInsertedItemListener(
				AcideMenuItemsConfiguration.getInstance()
				.getSubmenu(AcideConfigurationMenu.CONFIGURATION_MENU_NAME)
				.getSubmenu(FILE_EDITOR_MENU_NAME).getItem(MAXIMUM_LINES_TO_CONSOLE_NAME)));
		
		// Sets the send to console confirmation menu item action listener
		_sendToConsoleConfirmationMenuItem
				//.addActionListener(new AcideSendToConsoleConfirmationMenuItemListener());
			.addActionListener(new AcideInsertedItemListener(
				AcideMenuItemsConfiguration.getInstance()
				.getSubmenu(AcideConfigurationMenu.CONFIGURATION_MENU_NAME)
				.getSubmenu(FILE_EDITOR_MENU_NAME).getItem(SEND_TO_CONSOLE_CONFIRMATION_NAME)));
		
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
	 * Configures the ACIDE - A Configurable IDE file editor menu.
	 */
	public void configure() {

		// Sets the automatic check box menu item state
		_automaticIndentCheckBoxMenuItem
				.setSelected(AcideWorkbenchConfiguration.getInstance()
						.getFileEditorConfiguration().getAutomaticIndent());

		// Sets the line wrapping check box menu item state
		_lineWrappingCheckBoxMenuItem.setSelected(AcideWorkbenchConfiguration
				.getInstance().getFileEditorConfiguration().getLineWrapping());
		
		// Sets the send to console confirmation check box menu item state
		_sendToConsoleConfirmationMenuItem.setSelected(AcideWorkbenchConfiguration
				.getInstance().getFileEditorConfiguration().getSendToConsoleConfirmation());
	}

	/**
	 * Returns the ACIDE - A Configurable IDE file editor menu file editor
	 * preferences menu item.
	 * 
	 * @return the ACIDE - A Configurable IDE file editor menu file editor
	 *         preferences menu item.
	 */
	public JMenuItem getFileEditorPreferencesMenuItem() {
		return _preferencesMenuItem;
	}

	/**
	 * Returns the ACIDE - A Configurable IDE file editor menu file editor
	 * display options menu item.
	 * 
	 * @return the ACIDE - A Configurable IDE file editor menu file editor
	 *         display options menu item.
	 */
	public JMenuItem getFileEditorDisplayOptionsMenuItem() {
		return _fileEditorDisplayOptionsMenuItem;
	}

	/**
	 * Returns the ACIDE - A Configurable IDE file editor menu file editor line
	 * wrapping check box menu item.
	 * 
	 * @return the ACIDE - A Configurable IDE file editor menu file editor line
	 *         wrapping check box menu item.
	 */
	public JCheckBoxMenuItem getLineWrappingCheckBoxMenuItem() {
		return _lineWrappingCheckBoxMenuItem;
	}

	/**
	 * Returns the ACIDE - A Configurable IDE file editor menu file editor
	 * automatic indent check box menu item.
	 * 
	 * @return the ACIDE - A Configurable IDE file editor menu file editor
	 *         automatic indent check box menu item.
	 */
	public JCheckBoxMenuItem getAutomaticIndentCheckBoxMenuItem() {
		return _automaticIndentCheckBoxMenuItem;
	}

	/**
	 * Returns the ACIDE - A Configurable IDE file editor menu maximum lines to
	 * console menu item.
	 * 
	 * @return the ACIDE - A Configurable IDE file editor menu maximum lines to
	 *         console menu item.
	 */
	public JMenuItem getMaximumLinesToConsoleMenuItem() {
		return _maximumLinesToConsoleMenuItem;
	}
	
	/**
	 * Returns the ACIDE - A Configurable IDE file editor send to console confirmation
	 * menu item.
	 * 
	 * @return the ACIDE - A Configurable IDE file editor send to console confirmation
	 * 		  menu item.
	 */
	public JCheckBoxMenuItem getSendToConsoleConfirmationCheckBoxMenuItem() {
		return _sendToConsoleConfirmationMenuItem;
	}
}
