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
package acide.gui.menuBar.editMenu.changeCaseMenu;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

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
import acide.gui.menuBar.editMenu.AcideEditMenu;
import acide.gui.menuBar.listeners.AcideMenuBarMouseClickListener;
import acide.language.AcideLanguageManager;
import acide.log.AcideLog;
import acide.resources.AcideResourceManager;
import acide.utils.IconsUtils;

/**
 * ACIDE - A Configurable IDE change case menu.
 * 
 * @version 0.14
 * @see JMenu
 */
public class AcideChangeCaseMenu extends JMenu{

	/**
	 * ACIDE - A Configurable IDE change case menu serial version UID.
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * ACIDE - A Configurable IDE change case menu name.
	 */
	public final static String CHANGE_CASE_MENU_NAME = "Change Case";
	/**
	 * ACIDE - A Configurable IDE change case menu upper case menu item name.
	 */
	public static final String UPPER_CASE_NAME = "Upper Case";
	/**
	 * ACIDE - A Configurable IDE change case menu lower case menu item name.
	 */
	public static final String LOWER_CASE_NAME = "Lower Case";
	/**
	 * ACIDE - A Configurable IDE change case menu invert case menu item name.
	 */
	public static final String INVERT_CASE_NAME = "Invert Case";
	/**
	 * ACIDE - A Configurable IDE change case menu capitalize menu item name.
	 */
	public static final String CAPITALIZE_NAME = "Capitalize";
	/**
	 * ACIDE - A Configurable IDE change case menu upper case menu item.
	 */
	private JMenuItem _upperCaseMenuItem;
	/**
	 * ACIDE - A Configurable IDE change case menu upper case menu item has been inserted.
	 */
	private boolean _upperCaseInserted;
	/**
	 * ACIDE - A Configurable IDE change case menu lower case menu item.
	 */
	private JMenuItem _lowerCaseMenuItem;
	/**
	 * ACIDE - A Configurable IDE change case menu lower case menu item has been inserted.
	 */
	private boolean _lowerCaseInserted;
	/**
	 * ACIDE - A Configurable IDE change case menu capitalize menu item.
	 */
	private JMenuItem _capitalizeMenuItem;
	/**
	 * ACIDE - A Configurable IDE change case menu capitalize menu item has been inserted.
	 */
	private boolean _capitalizeInserted;
	/**
	 * ACIDE - A Configurable IDE change case menu invert case menu item.
	 */
	private JMenuItem _invertCaseMenuItem;
	/**
	 * ACIDE - A Configurable IDE change case menu invert case menu item has been inserted.
	 */
	private boolean _invertCaseInserted;
	/**
	 * ACIDE - A Configurable IDE change case menu configuration menu.
	 */
	private AcideMenuSubmenuConfiguration _changeCaseSubmenuConfiguration;
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
	 * Creates a new ACIDE - A Configurable IDE change case menu.
	 */
	public AcideChangeCaseMenu() {
		
		_upperCaseInserted = false;
		_lowerCaseInserted = false;
		_capitalizeInserted = false;
		_invertCaseInserted = false;
		
		_insertedItems = new HashMap<String, AcideInsertedItem>();
		
		_insertedMenus = new HashMap<String, AcideInsertedMenu>();
		
		_insertedObjects = new ArrayList<AcideMenuObjectConfiguration>();

		// Builds the menu components
		buildComponents();

		// Adds the components to the menu
		addComponents();

		// Sets the text of the change case menu components
		setTextOfMenuComponets();
	}

	/**
	 * Adds the components to the ACIDE - A Configurable IDE change case menu
	 */
	private void addComponents() {
		
		Iterator<Object> it = AcideMenuItemsConfiguration.getInstance()
				.getMenuItemsManager().getSubmenu(AcideEditMenu.EDIT_MENU_NAME)
				.getItemsManager().getSubmenu(CHANGE_CASE_MENU_NAME).getItemsManager().managerIterator();
		while (it.hasNext()){
			AcideMenuObjectConfiguration ob = (AcideMenuObjectConfiguration) it.next();
			String name = ob.getName();
			if (name.equals(UPPER_CASE_NAME)){
				// Adds the upper case menu item to the menu
				add(_upperCaseMenuItem);
				_upperCaseInserted = true;
			}else if (name.equals(LOWER_CASE_NAME)){
				// Adds the lower case menu item to the menu
				add(_lowerCaseMenuItem);
				_lowerCaseInserted = true;
			}else if (name.equals(CAPITALIZE_NAME)){
				// Adds capitalize menu item to the menu
				add(_capitalizeMenuItem);
				_capitalizeInserted = true;
			}else if (name.equals(INVERT_CASE_NAME)){
				// Adds the invert case menu item to the menu
				add(_invertCaseMenuItem);
				_invertCaseInserted = true;
			}else {
				if (ob.isSubmenu()){
					add(_insertedMenus.get(ob.getName()));
				}else{
					add(_insertedItems.get(ob.getName()));
				}
			}
		}
		
		if (!_upperCaseInserted)
			add(_upperCaseMenuItem);
		if (!_lowerCaseInserted)
			add(_lowerCaseMenuItem);
		if (!_capitalizeInserted)
			add(_capitalizeMenuItem);
		if (!_invertCaseInserted)
			add(_invertCaseMenuItem);
		
	}

	/**
	 * Builds the ACIDE - A Configurable IDE change case menu components.
	 */
	private void buildComponents() {
		
		if (!AcideMenuItemsConfiguration.getInstance().getMenuItemsManager()
				.getSubmenu(AcideEditMenu.EDIT_MENU_NAME).hasSubmenu(CHANGE_CASE_MENU_NAME)){
			AcideMenuItemsConfiguration.getInstance().getMenuItemsManager()
				.getSubmenu(AcideEditMenu.EDIT_MENU_NAME)
					.insertObject(new AcideMenuSubmenuConfiguration(CHANGE_CASE_MENU_NAME));
		}
		
		Iterator<Object> it = AcideMenuItemsConfiguration.getInstance()
				.getMenuItemsManager().getSubmenu(AcideEditMenu.EDIT_MENU_NAME)
				.getItemsManager().getSubmenu(CHANGE_CASE_MENU_NAME).getItemsManager().managerIterator();
		
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
		
		// Creates the upper case menu item
		_upperCaseMenuItem = new JMenuItem();
				
		// Sets the upper case menu item name
		_upperCaseMenuItem.setName(UPPER_CASE_NAME);

		// Creates the lower case menu item
		_lowerCaseMenuItem = new JMenuItem();
		
		// Sets the lower case menu item name
		_lowerCaseMenuItem.setName(LOWER_CASE_NAME);

		//Creates the capitalize menu item
		_capitalizeMenuItem = new JMenuItem();

		// Sets the capitalize menu item name
		_capitalizeMenuItem.setName(CAPITALIZE_NAME);

		// Creates the invert case menu item
		_invertCaseMenuItem = new JMenuItem();

		// Sets the invert case menu item name
		_invertCaseMenuItem.setName(INVERT_CASE_NAME);
	}

	/**
	 * Sets the text of the ACIDE - A Configurable IDE change case menu
	 * components with the labels in the selected language to display.
	 */
	public void setTextOfMenuComponets() {
		
		// Sets the upper case menu item text
		_upperCaseMenuItem.setText(AcideLanguageManager
				.getInstance().getLabels().getString("s2265"));

		// Sets the upper case menu item accelerator
		_upperCaseMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_U,
				ActionEvent.CTRL_MASK));
		
		// Sets the lower case menu item text
		_lowerCaseMenuItem.setText(AcideLanguageManager
				.getInstance().getLabels().getString("s2266"));
		
		// Sets the lower case menu item accelerator
		_lowerCaseMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_L,
				ActionEvent.CTRL_MASK));

		// Sets the capitalize menu item text
		_capitalizeMenuItem.setText(AcideLanguageManager
				.getInstance().getLabels().getString("s2267"));
		
		// Sets the capitalize menu item accelerator
		_capitalizeMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_U,
				ActionEvent.SHIFT_MASK+ActionEvent.CTRL_MASK));

		// Sets the invert case menu item text
		_invertCaseMenuItem.setText(AcideLanguageManager
				.getInstance().getLabels().getString("s2268"));
		
		// Sets the invert case menu item accelerator
		_invertCaseMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_L,
				ActionEvent.SHIFT_MASK+ActionEvent.CTRL_MASK));
		
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
	 * Updates the ACIDE - A Configurable IDE change case menu components
	 * visibility with the menu configuration.
	 */
	public void updateComponentsVisibility() {
		
		AcideMenuItemConfiguration upperCaseConfiguration;
		AcideMenuItemConfiguration lowerCaseConfiguration;
		AcideMenuItemConfiguration capitalizeConfiguration;
		AcideMenuItemConfiguration invertCaseConfiguration;

		_changeCaseSubmenuConfiguration = AcideMenuItemsConfiguration.getInstance()
					.getSubmenu(AcideEditMenu.EDIT_MENU_NAME).getSubmenu(CHANGE_CASE_MENU_NAME);
		
		// Sets the upper case menu item to visible or not visible
		upperCaseConfiguration = _changeCaseSubmenuConfiguration.getItem(UPPER_CASE_NAME);
		_upperCaseMenuItem.setVisible(upperCaseConfiguration.isVisible());
				
		// Sets the lower case menu item to visible or not visible
		lowerCaseConfiguration = _changeCaseSubmenuConfiguration.getItem(LOWER_CASE_NAME);
		_lowerCaseMenuItem.setVisible(lowerCaseConfiguration.isVisible());
		
		// Sets the capitalize menu item to visible or not visible
		capitalizeConfiguration = _changeCaseSubmenuConfiguration.getItem(CAPITALIZE_NAME);
		_capitalizeMenuItem.setVisible(capitalizeConfiguration.isVisible());
		
		// Sets the invert case menu item to visible or not visible
		invertCaseConfiguration = _changeCaseSubmenuConfiguration.getItem(INVERT_CASE_NAME);
		_invertCaseMenuItem.setVisible(invertCaseConfiguration.isVisible());
			
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
		
		// Sets the change case menu to visible or not visible
		_changeCaseSubmenuConfiguration.setVisible(_upperCaseMenuItem.isVisible()
						|| _lowerCaseMenuItem.isVisible()
						|| _capitalizeMenuItem.isVisible()
						|| _invertCaseMenuItem.isVisible());

		_changeCaseSubmenuConfiguration.setErasable(false);
		
						
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
		if (!(name.equals(UPPER_CASE_NAME))
			&& !(name.equals(LOWER_CASE_NAME))
			&& !(name.equals(CAPITALIZE_NAME))
			&& !(name.equals(INVERT_CASE_NAME))){
			return true;
		}else {
			return false;
		}
	}

	/**
	 * Sets ACIDE - A Configurable IDE file editor menu item listeners.
	 */
	public void setListeners() {
		
		// Sets the upper case menu item action listener
		_upperCaseMenuItem
			.addActionListener(new AcideInsertedItemListener(
				AcideMenuItemsConfiguration.getInstance()
				.getSubmenu(AcideEditMenu.EDIT_MENU_NAME)
				.getSubmenu(CHANGE_CASE_MENU_NAME).getItem(UPPER_CASE_NAME)));
		
		// Sets the lower case menu item action listener
		_lowerCaseMenuItem
			.addActionListener(new AcideInsertedItemListener(
				AcideMenuItemsConfiguration.getInstance()
				.getSubmenu(AcideEditMenu.EDIT_MENU_NAME)
				.getSubmenu(CHANGE_CASE_MENU_NAME).getItem(LOWER_CASE_NAME)));

		// Sets the capitalize menu item action listener
		_capitalizeMenuItem
			.addActionListener(new AcideInsertedItemListener(
				AcideMenuItemsConfiguration.getInstance()
				.getSubmenu(AcideEditMenu.EDIT_MENU_NAME)
				.getSubmenu(CHANGE_CASE_MENU_NAME).getItem(CAPITALIZE_NAME)));

		// Sets the invert case menu item action listener
		_invertCaseMenuItem
			.addActionListener(new AcideInsertedItemListener(
				AcideMenuItemsConfiguration.getInstance()
				.getSubmenu(AcideEditMenu.EDIT_MENU_NAME)
				.getSubmenu(CHANGE_CASE_MENU_NAME).getItem(INVERT_CASE_NAME)));
		
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
	 * Returns the ACIDE - A Configurable IDE change case menu upper case menu item.
	 * 
	 * @return the ACIDE - A Configurable IDE change case menu upper case menu item.
	 */
	public JMenuItem getUppercaseMenuItem() {
		return _upperCaseMenuItem;
	}

	/**
	 * Returns the ACIDE - A Configurable IDE change case menu lower case menu item.
	 * 
	 * @return the ACIDE - A Configurable IDE change case menu lower case menu item.
	 */
	public JMenuItem getLowerCaseMenuItem() {
		return _lowerCaseMenuItem;
	}

	/**
	 * Returns the ACIDE - A Configurable IDE change case menu capitalize menu item.
	 * 
	 * @return the ACIDE - A Configurable IDE change case menu capitalize menu item.
	 */
	public JMenuItem getCapitalizeMenuItem() {
		return _capitalizeMenuItem;
	}

	/**
	 * Returns the ACIDE - A Configurable IDE change case menu invert case menu item.
	 * 
	 * @return the ACIDE - A Configurable IDE change case menu invert case menu item.
	 */
	public JMenuItem getInvertCaseMenuItem() {
		return _invertCaseMenuItem;
	}

}
