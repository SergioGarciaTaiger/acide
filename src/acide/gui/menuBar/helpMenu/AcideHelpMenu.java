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
package acide.gui.menuBar.helpMenu;

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
import acide.gui.menuBar.listeners.AcideMenuBarMouseClickListener;
import acide.language.AcideLanguageManager;
import acide.log.AcideLog;
import acide.resources.AcideResourceManager;
import acide.utils.IconsUtils;

/**
 * ACIDE - A Configurable IDE help menu.
 * 
 * @version 0.11
 * @see JMenu
 */
public class AcideHelpMenu extends JMenu {

	/**
	 * ACIDE - A Configurable IDE help menu class serial version UID.
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * ACIDE - A Configurable IDE help menu name.
	 */
	public final static String HELP_MENU_NAME = "Help";
	/**
	 * ACIDE - A Configurable IDE help menu show help menu item name.
	 */
	public final static String SHOW_HELP_NAME = "Show Help";
	/**
	 * ACIDE - A Configurable IDE help menu show about us menu item name.
	 */
	public final static String SHOW_ABOUT_US_NAME = "Show About Us";
	/**
	 * ACIDE - A Configurable IDE help menu show help menu item image icon.
	 */
	private final static ImageIcon SHOW_HELP_IMAGE = new ImageIcon(
			"./resources/icons/menu/help/help.png");
	/**
	 * ACIDE - A Configurable IDE help menu show about us menu item image icon.
	 */
	private final static ImageIcon SHOW_ABOUT_US_IMAGE = new ImageIcon(
			"./resources/icons/menu/help/aboutUs.png");
	/**
	 * ACIDE - A Configurable IDE help menu show help menu item.
	 */
	private JMenuItem _showHelpMenuItem;
	/**
	 * ACIDE - A Configurable IDE help menu show help menu item has been inserted.
	 */
	private boolean _showHelpInserted;
	/**
	 * ACIDE - A Configurable IDE help menu show about us menu item.
	 */
	private JMenuItem _showAboutUsMenuItem;
	/**
	 * ACIDE - A Configurable IDE help menu show about us menu item has been inserted.
	 */
	private boolean _showAboutUsInserted;
	/**
	 * ACIDE - A Configurable IDE help menu show help show about us separator.
	 */
	private JSeparator _showHelpShowAboutUsSeparator;
	/**
	 * ACIDE - A Configurable IDE help menu configuration menu.
	 */
	private AcideMenuSubmenuConfiguration _helpSubmenuConfiguration;
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
	 * Creates a new ACIDE - A Configurable IDE help menu.
	 */
	public AcideHelpMenu() {
		
		_showAboutUsInserted = false;
		_showHelpInserted = false;
		
		_insertedItems = new HashMap<String, AcideInsertedItem>();
		
		_insertedMenus = new HashMap<String, AcideInsertedMenu>();
		
		_insertedObjects = new ArrayList<AcideMenuObjectConfiguration>();

		// Builds the menu components
		buildComponents();

		// Adds the components to the menu
		addComponents();

		// Sets the text of the help menu components
		setTextOfMenuComponents();
	}

	/**
	 * Adds the components to the ACIDE - A Configurable IDE help menu.
	 */
	private void addComponents() {
		
		Iterator<Object> it = AcideMenuItemsConfiguration.getInstance()
				.getMenuItemsManager().getSubmenu(HELP_MENU_NAME).getItemsManager().managerIterator();
		while (it.hasNext()){
			AcideMenuObjectConfiguration ob = (AcideMenuObjectConfiguration) it.next();
			String name = ob.getName();
			if (name.equals(SHOW_HELP_NAME)){
				// Adds the show help menu item to the menu
				add(_showHelpMenuItem);
				_showHelpInserted = true;
				// Adds the show help show about us separator to the menu
				add(_showHelpShowAboutUsSeparator);
			}else if (name.equals(SHOW_ABOUT_US_NAME)){
				// Adds the show about us menu item to the menu
				add(_showAboutUsMenuItem);
				_showAboutUsInserted = true;
			}else {
				if (ob.isSubmenu()){
					add(_insertedMenus.get(ob.getName()));
				}else{
					add(_insertedItems.get(ob.getName()));
				}
			}
		}

		if (!_showHelpInserted)
			add(_showHelpMenuItem);
		if (!_showAboutUsInserted)
			add(_showAboutUsMenuItem);
		
	}

	/**
	 * Builds the ACIDE - A Configurable IDE help menu components.
	 */
	private void buildComponents() {
		
		if (!AcideMenuItemsConfiguration.getInstance().getMenuItemsManager().hasSubmenu(HELP_MENU_NAME)){
			AcideMenuItemsConfiguration.getInstance()
				.insertObject(new AcideMenuSubmenuConfiguration(HELP_MENU_NAME));
		}
		
		Iterator<Object> it = AcideMenuItemsConfiguration.getInstance()
				.getMenuItemsManager().getSubmenu(HELP_MENU_NAME).getItemsManager().managerIterator();
		
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

		// Creates the show help menu item
		ImageIcon icon = IconsUtils.getIcon(AcideMenuItemsConfiguration.getInstance()
				.getMenuItemsManager().getSubmenu(HELP_MENU_NAME)
				.getItem(SHOW_HELP_NAME).getImage());
	
		if (icon != null)
			_showHelpMenuItem = new JMenuItem(icon);
		else
			_showHelpMenuItem = new JMenuItem();

		// Sets the show help menu item name
		_showHelpMenuItem.setName(SHOW_HELP_NAME);

		// Creates the show help show about us separator
		_showHelpShowAboutUsSeparator = new JSeparator();

		// Creates the show about us menu item
		icon = IconsUtils.getIcon(AcideMenuItemsConfiguration.getInstance()
				.getMenuItemsManager().getSubmenu(HELP_MENU_NAME)
				.getItem(SHOW_ABOUT_US_NAME).getImage());
		
		if (icon != null)
			_showAboutUsMenuItem = new JMenuItem(icon);
		else
			_showAboutUsMenuItem = new JMenuItem();

		// Sets the show about us menu item name
		_showAboutUsMenuItem.setName(SHOW_ABOUT_US_NAME);
	}

	/**
	 * Sets the text of the ACIDE - A Configurable IDE help menu components with
	 * the labels in the selected language to display.
	 */
	public void setTextOfMenuComponents() {

		// Sets the show help menu item text
		_showHelpMenuItem.setText(AcideLanguageManager.getInstance()
				.getLabels().getString("s38"));

		// Sets the show help menu item accelerator
			_showHelpMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_H,
				ActionEvent.CTRL_MASK));
		
		// Sets the show about us menu item text
		_showAboutUsMenuItem.setText(AcideLanguageManager.getInstance()
				.getLabels().getString("s39"));
		
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
	 * Updates the ACIDE - A Configurable IDE help menu components visibility
	 * with the menu configuration.
	 */
	public void updateComponentsVisibility() {

		AcideMenuItemConfiguration showHelpConfiguration;
		AcideMenuItemConfiguration showAboutUsConfiguration;

		_helpSubmenuConfiguration = AcideMenuItemsConfiguration.getInstance().getSubmenu(HELP_MENU_NAME);
			
		// Sets the show help menu item to visible or not visible
		showHelpConfiguration = _helpSubmenuConfiguration.getItem(SHOW_HELP_NAME);
		_showHelpMenuItem.setVisible(showHelpConfiguration.isVisible());
			
		// Sets the show about us menu item to visible or not visible
		showAboutUsConfiguration = _helpSubmenuConfiguration.getItem(SHOW_ABOUT_US_NAME);
		_showAboutUsMenuItem.setVisible(showAboutUsConfiguration.isVisible());
			
		// Sets the show help show about us separator to visible or not visible
		_showHelpShowAboutUsSeparator.setVisible(_showHelpMenuItem.isVisible()
				&& _showAboutUsMenuItem.isVisible());
				
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
		
		// Sets the help menu as visible or not visible
		_helpSubmenuConfiguration.setVisible(_showHelpMenuItem.isVisible()
				|| _showAboutUsMenuItem.isVisible());
		
		_helpSubmenuConfiguration.setErasable(false);
		
		
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
		if (!(name.equals(SHOW_ABOUT_US_NAME))
			&& !(name.equals(SHOW_HELP_NAME))){
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Sets the ACIDE - A Configurable IDE help menu item listeners.
	 */
	public void setListeners() {

		// Sets the show help menu item action listener
		_showAboutUsMenuItem//.addActionListener(new AcideShowAboutUsMenuItemListener());
			.addActionListener(new AcideInsertedItemListener(
				AcideMenuItemsConfiguration.getInstance()
				.getSubmenu(HELP_MENU_NAME).getItem(SHOW_ABOUT_US_NAME)));

		// Sets the show about us menu item text
		_showHelpMenuItem//.addActionListener(new AcideShowHelpMenuItemListener());
			.addActionListener(new AcideInsertedItemListener(
				AcideMenuItemsConfiguration.getInstance()
				.getSubmenu(HELP_MENU_NAME).getItem(SHOW_HELP_NAME)));
		
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
	 * Returns the ACIDE - A Configurable IDE help menu show about us menu item.
	 * 
	 * @return the ACIDE - A Configurable IDE help menu show about us menu item.
	 */
	public JMenuItem getShowAboutUsMenuItem() {
		return _showAboutUsMenuItem;
	}

	/**
	 * Returns the ACIDE - A Configurable IDE help menu show help menu item.
	 * 
	 * @return the ACIDE - A Configurable IDE help menu show help menu item.
	 */
	public JMenuItem getShowHelpMenuItem() {
		return _showHelpMenuItem;
	}
	
	public void setEnabled(boolean enabled){
		_showAboutUsMenuItem.setEnabled(enabled);
		_showHelpMenuItem.setEnabled(enabled);
	}
}
