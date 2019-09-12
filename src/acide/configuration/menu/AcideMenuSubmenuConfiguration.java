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

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.JOptionPane;

import acide.configuration.menu.items.AcideMenuItemsManager;
import acide.language.AcideLanguageManager;

/**
 * ACIDE - A Configurable IDE menu item configuration.
 * 
 * @version 0.11
 * @see Serializable
 */
public class AcideMenuSubmenuConfiguration extends AcideMenuObjectConfiguration {
	
	/**
	 * ACIDE - A Configurable IDE menu item configuration class serial
	 * version UID.
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * ACIDE - A Configurable IDE menu items list manager.
	 */
	private AcideMenuItemsManager _itemsManager;
	
	/**
	 * Creates a new ACIDE - A Configurable IDE lexicon menu submenu configuration.
	 * @param name
	 * 		the name for the submenu
	 * 
	 */
	public AcideMenuSubmenuConfiguration(String name) {
		super(name);
		
		_itemsManager = new AcideMenuItemsManager();
	}
	
	/**
	 * Creates a new ACIDE - A Configurable IDE lexicon menu submenu configuration.
	 * @param copy
	 * 			the configuration 
	 */
	public AcideMenuSubmenuConfiguration(AcideMenuSubmenuConfiguration copy){
		super(copy);
		_itemsManager = new AcideMenuItemsManager(copy.getItemsManager());
	}
	
	/**
	 * Creates a new ACIDE - A Configurable IDE lexicon menu submenu configuration.
	 * @param copy
	 * 			the configuration 
	 */
	public AcideMenuSubmenuConfiguration(AcideMenuItemsManager copy){
		super();
		_itemsManager = new AcideMenuItemsManager(copy);
	}

	/**
	 * Gets the ACIDE - A Configurable IDE menu item configuration items manager.
	 * 
	 * @return the items manager.
	 */
	public AcideMenuItemsManager getItemsManager() {
		return _itemsManager;
	}

	/**
	 * Sets a new value to the ACIDE - A Configurable IDE menu item configuration parameter.
	 * 
	 * @param itemsManager
	 * 		 items Manager.
	 */
	public void setItemsManager(AcideMenuItemsManager itemsManager) {
		_itemsManager = itemsManager;
	}
	
	
	/**
	 * Returns the object at the position given as a parameter.
	 * 
	 * @param index
	 *            position to get.
	 * @return the object at the position given as a parameter.
	 */
	public AcideMenuObjectConfiguration getObjectAt(int index) {
		return _itemsManager.getObjectAt(index);
	}
	
	/**
	 * Inserts a new object to the items list.
	 * 
	 * @param object
	 *            new object to insert.
	 */
	public void insertObject(AcideMenuObjectConfiguration object) {
		_itemsManager.insertItem(object);
	}
	
	/**
	 * Deletes the submenu with the name given as parameter
	 * @param menu
	 * 		the name of submenu we want to delete.
	 */
	public void deleteSubmenu(String menu){
		_itemsManager.deleteSubmenu(menu);
	}

	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result
				+ ((_itemsManager == null) ? 0 : _itemsManager.hashCode());
		return result;
	}

	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		return true;
	}
	
	/**
	 * Returns if the menu has the item given as parameter
	 * @param item
	 * 		the item we want to find
	 * @return
	 * 		if the menu has the item given as parameter
	 */
	public boolean hasItem(String item){
		return _itemsManager.hasItem(item);
	}
	
	/**
	 * Returns if the menu has the item given as parameter in one of
	 * its internal submenus.
	 * @param name
	 * 		the item we want to find
	 * @return
	 * 		if the menu has the item given as parameter
	 */
	public boolean hasInternalItem(String name) {
		return _itemsManager.hasInternalItem(name);
	}
	
	/**
	 * Returns if the menu has the submenu given as parameter
	 * @param submenu
	 * 		the submenu we want to find
	 * @return
	 * 		if the menu has the submenu given as parameter
	 */
	public boolean hasSubmenu(String submenu){
		return _itemsManager.hasSubmenu(submenu);
	}
	
	/**
	 * Returns if the menu has the submenu given as parameter in one of
	 * its internal submenus.
	 * @param name
	 * 		the submenu we want to find
	 * @return
	 * 		if the menu has the submenu given as parameter
	 */
	public boolean hasInternalSubmenu(String name) {
		return _itemsManager.hasInternalSubmenu(name);
	}
	
	/**
	 * Returns if the menu has the object given as parameter in one of
	 * its internal submenus.
	 * @param name
	 * 		the object we want to find
	 * @return
	 * 		if the menu has the object given as parameter
	 */
	public boolean hasInternalObject(String name) {
		return _itemsManager.hasInternalObject(name);
	}
	
	/**
	 * Gets the object with the name given as parameter
	 * @param object
	 * 		the name of the object to get
	 * @return
	 * 		the object with the name given as parameter
	 */
	public AcideMenuObjectConfiguration getObject(String object){
		return _itemsManager.getObject(object);
	}
	
	/**
	 * Gets the item with the name given as parameter
	 * @param item
	 * 		the name of the item to get
	 * @return
	 * 		the item with the name given as parameter
	 */
	public AcideMenuItemConfiguration getItem(String item){
		return _itemsManager.getItem(item);
	}
	
	/**
	 * Gets the submenu with the name given as parameter
	 * @param submenu
	 * 		the name of the submenu to get
	 * @return
	 * 		the submenu with the name given as parameter
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

	
	public String toString() {
		//String aux = "Submenu ";
		String aux = super.toString();
		//aux = aux + " " + _itemsManager.toString();
		return aux;
	}

	/**
	 * Moves the object given as parameter to up in the list of this submenu
	 * @param menu
	 * 		the object we want to move
	 * @param path
	 * 		the path of the object we want to move
	 * @return
	 * 		if it has been possible.
	 */
	public boolean toUp(AcideMenuObjectConfiguration menu, String path) {
		
		String[] pathSplitted = path.split("/");
		AcideMenuSubmenuConfiguration parentMenu = this;
		int i = 1;
		
		//Obtains the parent of the object
		while (i < pathSplitted.length){
			String name = pathSplitted[i];
			parentMenu = parentMenu.getSubmenu(name);
			i++;
		}
		//Moves the object
		return parentMenu.getItemsManager().toUp(menu);
		
	}
	
	/**
	 * Moves the object given as parameter to down in the list of this submenu
	 * @param menu
	 * 		the object we want to move
	 * @param path
	 * 		the path of the object we want to move
	 * @return
	 * 		if it has been possible.
	 */
	public boolean toDown(AcideMenuObjectConfiguration menu, String path) {
		String[] pathSplitted = path.split("/");
		AcideMenuSubmenuConfiguration parentMenu = this;
		int i = 1;
		
		//Obtains the parent of the object
		while (i < pathSplitted.length){
			String name = pathSplitted[i];
			parentMenu = parentMenu.getSubmenu(name);
			i++;
		}
		//Moves the object
		return parentMenu.getItemsManager().toDown(menu);
		
	}

	/**
	 * Adds a new submenu to the menu
	 * @param configuration
	 * 		the selected object
	 * @param name
	 * 		the name of the new submenu
	 * @param path
	 * 		the path of the selected object
	 * @return
	 * 		if it has been possible.
	 */
	public boolean AddSubmenu(AcideMenuObjectConfiguration configuration, String name, String path) {
		String[] pathSplitted = path.split("/");
		//Creates the new submenu
		AcideMenuSubmenuConfiguration newSubmenu = new AcideMenuSubmenuConfiguration(name);
		AcideMenuSubmenuConfiguration parentMenu = this;
		
		//If this submenu already exists, displays an error message.
		if (parentMenu.hasInternalSubmenu(name)){
			// Displays a message
			JOptionPane.showMessageDialog(null, AcideLanguageManager
					.getInstance().getLabels().getString("s2225"));
			return false;
		}
		
		//If selected object is a submenu, inserts the new submenu inside
		if (configuration.isSubmenu()){
			parentMenu = (AcideMenuSubmenuConfiguration) configuration;
			parentMenu.getItemsManager().insertItem(newSubmenu);
			return true;
		}else {
			
			//Gets the submenu where to insert the new submenu
			int i = 1;
			while (i < pathSplitted.length){
				String n = pathSplitted[i];
				parentMenu = parentMenu.getSubmenu(n);
				i++;
			}
			
		}
		//Gets the position of the selected item and inserts the new submenu after it
		int position = parentMenu.getItemsManager().getPosition(configuration);
		parentMenu.getItemsManager().insertItemAt(position + 1, newSubmenu);
		return true;
	}

	/**
	 * Adds a new item to the menu
	 * @param configuration
	 * 		the selected object
	 * @param name
	 * 		the name of the new item
	 * @param path
	 * 		the path of the selected object
	 * @return
	 * 		if it has been possible.
	 */
	public boolean AddItem(AcideMenuObjectConfiguration configuration, String name, String path) {
		String[] pathSplitted = path.split("/");
		//Creates the new item
		AcideMenuItemConfiguration newSubmenu = new AcideMenuItemConfiguration(name);
		AcideMenuSubmenuConfiguration parentMenu = this;
		
		//If this item already exists, displays an error message.
		if (parentMenu.hasInternalItem(name)){
			// Displays a message
			JOptionPane.showMessageDialog(null, AcideLanguageManager
					.getInstance().getLabels().getString("s2226"));
			return false;
		}
		
		//If selected object is a submenu, inserts the new item inside
		if (configuration.isSubmenu()){
			parentMenu = (AcideMenuSubmenuConfiguration) configuration;
			parentMenu.getItemsManager().insertItem(newSubmenu);
			return true;	
		}else {
			//Gets the submenu where to insert the new item
			int i = 1;
			while (i < pathSplitted.length){
				String n = pathSplitted[i];
				parentMenu = parentMenu.getSubmenu(n);
				i++;
			}
		}
		//Gets the position of the selected item and inserts the new item after it
		int position = parentMenu.getItemsManager().getPosition(configuration);
		parentMenu.getItemsManager().insertItemAt(position + 1, newSubmenu);
		return true;
	}

	/**
	 * Sets visible all the objects inside the menu
	 */
	public void allVisibles() {
		//Sets visible this submenu
		this.setVisible(true);
		
		//Gets the manager iterator
		Iterator<Object> it = getItemsManager().getList().getList().iterator();
		while (it.hasNext()){
			AcideMenuObjectConfiguration ob = (AcideMenuObjectConfiguration) it.next();
			if (ob.isSubmenu()){
				//If is submenu, set visible all its internal objects
				AcideMenuSubmenuConfiguration subMenu = (AcideMenuSubmenuConfiguration) ob;
				subMenu.allVisibles();
			}
			ob.setVisible(true);
		}
	}

	/**
	 * Sets no visible all the objects inside the menu
	 */
	public void allNoVisibles() {
		//Gets the manager iterator
		Iterator<Object> it = getItemsManager().getList().getList().iterator();
		while (it.hasNext()){
			AcideMenuObjectConfiguration ob = (AcideMenuObjectConfiguration) it.next();
			if (ob.isSubmenu()){
				//If is submenu, sets no visible all its internal objects
				AcideMenuSubmenuConfiguration subMenu = (AcideMenuSubmenuConfiguration) ob;
				subMenu.allNoVisibles();
			}
			ob.setVisible(false);
		}
	}

	/**
	 * Deletes the menu object given as parameter
	 * @param submenu
	 * 			the object to delete
	 * @param path
	 * 			the path of the object to delete
	 * @return
	 * 		if it has been possible
	 */
	public boolean delete(AcideMenuObjectConfiguration submenu, String path) {
		String[] pathSplitted = path.split("/");
		AcideMenuSubmenuConfiguration parentMenu = this;
		int i = 1;
		//Gets the parent of the object ot delete
		while (i < pathSplitted.length){
			String name = pathSplitted[i];
			parentMenu = parentMenu.getSubmenu(name);
			i++;
		}
		parentMenu.getItemsManager().deleteObject(submenu.getName());
		return true;
	}
	
	/**
	 * Gets the internal submenu with the name given as parameter
	 * @param chosen
	 * 		the name of the submenu we want to get
	 * @return
	 * 		the submenu with the name given as parameter
	 */
	public AcideMenuSubmenuConfiguration getInternSubmenu(String chosen) {
		String[] pathSplitted = chosen.split("/");
		AcideMenuSubmenuConfiguration parentMenu = this;
		int i = 1;
		//Gets the parent menu of the submenu we want to get
		while (i < pathSplitted.length){
			String name = pathSplitted[i];
			parentMenu = parentMenu.getSubmenu(name);
			i++;
		}
		return parentMenu;
	}

	/**
	 * Gets the number of submenus.
	 * @return
	 * 		the number of submenus.
	 */
	public int getNumberSubmenus() {
		return _itemsManager.getNumberSubmenus();
	}

	/**
	 * Gets an array of strings with the names of the internal submenus
	 * unless the submenu given as parameter
	 * @param allLessThis
	 * 		the submenu we do not want to get in the array
	 * @return
	 * 		the array with the names of the submenus
	 */
	public String[] getSubmenusUnlessThis(AcideMenuSubmenuConfiguration allLessThis) {
		return _itemsManager.getSubmenusUnlessThis(allLessThis);
	}

	/**
	 * Gets an array with the paths of all the submenus inside this submenu
	 * @param root
	 * 		the root path
	 * @return
	 * 		the array with the paths
	 */
	public String[] getAllSubmenusWithPath(String root) {
		//Gets an array list with the paths 
		ArrayList<String> auxList = _itemsManager.getAllSubmenusWithPath(root);
		String[] result = new String[auxList.size()+ 1];
		
		//Converts the array list to an array.
		Iterator<String> it = auxList.iterator();
		int i = 1;
		result[0] = getName();
		while (it.hasNext()){
			result[i] = it.next();
			i++;
		}
		return result;
	}

	
}
