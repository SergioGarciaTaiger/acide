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
package acide.configuration.menu.items;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;

import acide.configuration.menu.AcideMenuItemConfiguration;
import acide.configuration.menu.AcideMenuObjectConfiguration;
import acide.configuration.menu.AcideMenuObjectConfigurationWithNumber;
import acide.configuration.menu.AcideMenuSubmenuConfiguration;
import acide.configuration.utils.ObjectList;

/**
 * ACIDE - A Configurable IDE menu items manager.
 * 
 * @version 0.11
 * @see Serializable
 */
public class AcideMenuItemsManager implements Serializable{
	
	/**
	 * ACIDE - A Configurable IDE menu items manager class serial
	 * version UID.
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * ACIDE - A Configurable IDE menu items manager object list.
	 */
	private ObjectList _list;
	
	/**
	 * Creates a new ACIDE - A Configurable IDE menu items manager.
	 */
	public AcideMenuItemsManager() {

		super();

		// Creates the object list
		_list = new ObjectList();
	}
	
	/**
	 * Creates a new ACIDE - A Configurable IDE menu items manager.
	 * @param copy
	 * 		the value to set to menu items manager 
	 */
	public AcideMenuItemsManager(AcideMenuItemsManager copy){
		super();
		
		_list = new ObjectList(copy.getList());
		
		
	}
	
	/**
	 * Returns the item at the position given as a parameter.
	 * 
	 * @param index
	 *            position to get.
	 * @return the item at the position given as a parameter.
	 */
	public AcideMenuObjectConfiguration getObjectAt(int index) {
		return (AcideMenuObjectConfiguration) _list.getObjectAt(index);
	}
	
	/**
	 * Gets the position of the menu object given as parameter
	 * @param configuration
	 * 		the menu object we want to know its position
	 * @return 
	 * 		the position of the menu object given as parameter
	 */
	public int getPosition(AcideMenuObjectConfiguration configuration) {
		 
		for (int index = 0; index < getSize(); index++) {
			
			// Gets the object at the index
			AcideMenuObjectConfiguration itemAtIndex = getObjectAt(index);

			//If its name is which we want, return it
			if (itemAtIndex.getName().equals(configuration.getName()))
				return index;
		}
		
		return -1;
	}
	
	/**
	 * Gets the object with the name given as parameter
	 * @param object
	 * 		the name of the object we want to get.
	 * @return
	 * 		the object with the name given as parameter
	 */
	public AcideMenuObjectConfiguration getObject(String object){
		for (int index = 0; index < getSize(); index++) {
			
			// Gets the object at the index
			AcideMenuObjectConfiguration itemAtIndex = getObjectAt(index);

			//If its name is which we want, return it
			if (itemAtIndex.getName().equals(object))
				return itemAtIndex;
		}
		
		return null;
	}
	
	/**
	 * Gets the item with the name given as parameter
	 * @param item
	 * 		the name of the item we want to get.
	 * @return
	 * 		the item with the name given as parameter
	 */
	public AcideMenuItemConfiguration getItem(String item){
	
		for (int index = 0; index < getSize(); index++) {
			
			// Gets the object at the index
			AcideMenuObjectConfiguration itemAtIndex = getObjectAt(index);

			//If its name is which we want, return it
			if (itemAtIndex.isItem() && itemAtIndex.getName().equals(item))
				return (AcideMenuItemConfiguration) itemAtIndex;
		}
		
		return null;	
	}
	
	/**
	 * Gets the submenu with the name given as parameter
	 * @param submenu
	 * 		the name of the submenu we want to get.
	 * @return
	 * 		the submenu with the name given as parameter
	 */
	public AcideMenuSubmenuConfiguration getSubmenu(String submenu){
		
		for (int index = 0; index < getSize(); index++) {
			
			// Gets the object at the index
			AcideMenuObjectConfiguration itemAtIndex = getObjectAt(index);

			//If its name is which we want, return it
			if (itemAtIndex.isSubmenu() && itemAtIndex.getName().equals(submenu))
				return (AcideMenuSubmenuConfiguration) itemAtIndex;
		}
		
		return null;	
	}
	
	/**
	 * Returns the list size.
	 * 
	+ * @return the list size.
	 */
	public int getSize() {
		return _list.size();
	}
	
	/**
	 * Inserts a new item in the ACIDE - A Configurable IDE console
	 * menu items manager list.
	 * 
	 * @param item
	 *            new item to insert.
	 */
	public void insertItem(String item) {

		boolean found = false;

		for (int index = 0; index < getSize(); index++) {

			// Gets the delimiter at the index
			AcideMenuObjectConfiguration itemAtIndex = getObjectAt(index);

			//If its name is which we want, set found to true
			if (itemAtIndex.equals(item))
				found = true;
		}

		//If not exists the item, inserts it
		if (!found)
			insertItem(item);
	}
	
	/**
	 * Gets if the items manager has the item with the name given as parameter
	 * @param item
	 * 		the name of the item we want to check 
	 * @return
	 * 		if the item exists in this items manager
	 */
	public boolean hasItem(String item){
		boolean found = false;
		
		for (int index = 0; index < getSize(); index++) {
			
			// Gets the object at the index
			AcideMenuObjectConfiguration itemAtIndex = getObjectAt(index);

			//If it is an item and its name is equals to the given parameter, set found to true
			if (itemAtIndex.isItem() && itemAtIndex.getName().equals(item))
				found = true;
		}
		
		//Return if it was found
		return found;
	}
	
	/**
	 * Gets if the items manager has the submenu with the name given as parameter
	 * @param submenu
	 * 		the name of the submenu we want to check 
	 * @return
	 * 		if the submenu exists in this items manager
	 */
	public boolean hasSubmenu(String submenu){
		boolean found = false;
		
		for (int index = 0; index < getSize(); index++) {
			
			// Gets the object at the index
			AcideMenuObjectConfiguration itemAtIndex = getObjectAt(index);

			//If it is an submenu and its name is equals to the given parameter, set found to true
			if (itemAtIndex.isSubmenu() && itemAtIndex.getName().equals(submenu))
				found = true;
		}
		
		//Return if it was found
		return found;
	}
	
	/**
	 * Gets if the items manager has internally the object with the name given as parameter
	 * @param submenu
	 * 		the name of the object we want to check 
	 * @return
	 * 		if the object exists internally in this items manager
	 */
	public boolean hasInternalObject(String submenu) {
		boolean found = false;
		
		for (int index = 0; index < getSize(); index++) {
			
			// Gets the object at the index
			AcideMenuObjectConfiguration itemAtIndex = getObjectAt(index);

			//If its name is equals to the given as parameter, set found to true
			if (itemAtIndex.getName().equals(submenu))
				found = true;
			
			//If it is a submenu, checks internally
			if (itemAtIndex.isSubmenu()){
				AcideMenuSubmenuConfiguration s1 = (AcideMenuSubmenuConfiguration) itemAtIndex;
				found = found || s1.hasInternalObject(submenu);
			}
		}
		
		return found;
	}
	
	/**
	 * Gets if the items manager has internally the submenu with the name given as parameter
	 * @param submenu
	 * 		the name of the submenu we want to check 
	 * @return
	 * 		if the submenu exists internally in this items manager
	 */
	public boolean hasInternalSubmenu(String submenu) {
		boolean found = false;
		
		for (int index = 0; index < getSize(); index++) {
			
			// Gets the object at the index
			AcideMenuObjectConfiguration itemAtIndex = getObjectAt(index);

			//If its name is equals to the given as parameter and is a submenu, set found to true
			if (itemAtIndex.isSubmenu() && itemAtIndex.getName().equals(submenu))
				found = true;
			
			//If it is a submenu, checks internally
			if (itemAtIndex.isSubmenu()){
				AcideMenuSubmenuConfiguration s1 = (AcideMenuSubmenuConfiguration) itemAtIndex;
				found = found || s1.hasInternalSubmenu(submenu);
			}
		}
		
		return found;
	}
	
	/**
	 * Gets if the items manager has internally the item with the name given as parameter
	 * @param submenu
	 * 		the name of the item we want to check 
	 * @return
	 * 		if the item exists internally in this items manager
	 */
	public boolean hasInternalItem(String submenu) {
		boolean found = false;
		
		for (int index = 0; index < getSize(); index++) {
			
			// Gets the object at the index
			AcideMenuObjectConfiguration itemAtIndex = getObjectAt(index);

			//If its name is equals to the given as parameter and is an item, set found to true
			if (itemAtIndex.isItem() && itemAtIndex.getName().equals(submenu))
				found = true;
			
			//If it is a submenu, checks internally
			if (itemAtIndex.isSubmenu()){
				AcideMenuSubmenuConfiguration s1 = (AcideMenuSubmenuConfiguration) itemAtIndex;
				found = found || s1.hasInternalItem(submenu);
			}
		}
		
		return found;
	}
	
	
	/**
	 * Deletes an object given as a parameter.
	 * 
	 * @param object
	 *            object to delete.
	 */
	public void deleteObject(String object) {

		for (int index = 0; index < this.getSize(); index++) {

			AcideMenuObjectConfiguration s1 = this.getObjectAt(index);
			if (s1.getName().equals(object))
				_list.removeAt(index);
		}
	}
	
	/**
	 * Deletes a item given as a parameter.
	 * 
	 * @param item
	 *            item to delete.
	 */
	public void deleteItem(String item) {

		for (int index = 0; index < this.getSize(); index++) {

			AcideMenuObjectConfiguration s1 = this.getObjectAt(index);
			if (s1.isItem() && s1.getName().equals(item))
				_list.removeAt(index);
		}
	}
	
	/**
	 * Deletes a submenu given as a parameter.
	 * 
	 * @param submenu
	 *            submenu to delete.
	 */
	public void deleteSubmenu(String submenu) {

		for (int index = 0; index < this.getSize(); index++) {

			AcideMenuObjectConfiguration s1 = this.getObjectAt(index);
			if (s1.isSubmenu() && s1.getName().equals(submenu))
				_list.removeAt(index);
		}
	}
	
	/**
	 * Returns the ACIDE - A Configurable IDE menu items object
	 * list.
	 * 
	 * @return the ACIDE - A Configurable IDE menu items object
	 *         list.
	 */
	public ObjectList getList() {
		return _list;
	}
	
	/**
	 * Gets all the objects, also the internal ones, of the items manager
	 * @return
	 * 		all the objects, also the internal ones, of the items manager
	 */
	public ArrayList<Object> getAllObjects(){
		ArrayList<Object> items = _list.getList();
		ArrayList<Object> result = new ArrayList<Object>();
		
		AcideMenuObjectConfiguration aux;
		Iterator<Object> it = items.iterator();
		while (it.hasNext()){
			aux = (AcideMenuObjectConfiguration) it.next();
			result.add(aux);
			if (aux.isSubmenu()){
				AcideMenuSubmenuConfiguration auxSubmenu = (AcideMenuSubmenuConfiguration) aux;
				result.addAll(auxSubmenu.getItemsManager().getAllObjects());
			}
		}
		return result;
	}
	
	/**
	 * Gets all the objects with number of row, also the internal ones, of the items manager
	 * @return
	 * 		all the objects with number of row, also the internal ones, of the items manager
	 */
	public ArrayList<Object> getAllObjectsWithNumber(){
		ArrayList<Object> result = new ArrayList<Object>();
		ArrayList<Object> objects = getAllObjects();
		AcideMenuObjectConfiguration aux;
		int i = 1;
		Iterator<Object> it = objects.iterator();
		while (it.hasNext()){
			aux = (AcideMenuObjectConfiguration) it.next();
			AcideMenuObjectConfigurationWithNumber obConf = new
					 AcideMenuObjectConfigurationWithNumber(aux, i);
			result.add(obConf);
			i++;
		}
		return result;
	}
	
	/**
	 * Sets a new value to the ACIDE - A Configurable IDE menu items
	 *  object list.
	 * 
	 * @return the object list.
	 */
	public void setList(ObjectList list) {
		_list = list;
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
		_list.insertAt(index, object);
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
	public void insertItemAt(int index, AcideMenuObjectConfiguration object){
		_list.insertAt(index, object);
	}
	
	/**
	 * Insert a new item.
	 * 
	 * @param item
	 *            new value to set.
	 */
	public void insertItem(AcideMenuObjectConfiguration item) {
		_list.insert(_list.size(), item);
	}

	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((_list == null) ? 0 : _list.hashCode());
		return result;
	}


	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AcideMenuItemsManager other = (AcideMenuItemsManager) obj;
		if (_list == null) {
			if (other._list != null)
				return false;
		} else if (!_list.equals(other._list))
			return false;
		return true;
	}
	
	public String toString(){
		return _list.toString();
	}
	
	/**
	 * Gets the iterator of the manager
	 * @return the iterator of the manager
	 */
	public Iterator<Object> managerIterator(){
		Iterator<Object> it = getList().getList().iterator();
		return it;
	}

	/**
	 * Moves to up the object given as parameter
	 * @param menu
	 * 		the object to move
	 * @return
	 * 		if it has been possible
	 */
	public boolean toUp(AcideMenuObjectConfiguration menu) {
		int index = _list.indexOf(menu);
		if (index > 0){
			_list.removeAt(index);
			_list.insertAt(index - 1, menu);
			return true;
		}
		return false;
	}
	
	/**
	 * Moves to down the object given as parameter
	 * @param menu
	 * 		the object to move
	 * @return
	 * 		if it has been possible
	 */
	public boolean toDown(AcideMenuObjectConfiguration menu) {
		int index = _list.indexOf(menu);
		if (index < _list.size()- 1){
			_list.removeAt(index);
			_list.insertAt(index + 1, menu);
			return true;
		}
		return false;
	}

	/**
	 * Sets the items manager with only a copy of the object with the name given as parameter
	 * @param fileMenuName
	 * 		the name of the object to process
	 */
	public void onlyOne(String fileMenuName) {
		boolean found = false;
		for (int index = 0; index < this.getSize(); index++) {

			AcideMenuObjectConfiguration s1 = this.getObjectAt(index);
			if (s1.getName().equals(fileMenuName))
				if (!found){
					found = true;
				}else {
					_list.removeAt(index);
				}
		}
	}

	/**
	 * Gets the number of submenus in the items manager
	 * @return the number of submenus in the items manager
	 */
	public int getNumberSubmenus() {
		int result = 0;
		for (int index = 0; index < this.getSize(); index++) {
			AcideMenuObjectConfiguration s1 = this.getObjectAt(index);
			if (s1.isSubmenu())
				result++;
		}
		
		return result;
	}

	/**
	 * Gets an array of strings with the names of the internal submenus
	 * unless the submenu given as parameter
	 * @param allLessThis
	 * 		the submenu we do not want to get in the array
	 * @return
	 * 		the array with the names of the submenus
	 */
	public String[] getSubmenusUnlessThis(
			AcideMenuSubmenuConfiguration allLessThis) {
		int num = getNumberSubmenus() - 1;
		String[] submenus = new String[num];
		int i = 0;
		for (int index = 0; index < this.getSize(); index++) {
			AcideMenuObjectConfiguration s1 = this.getObjectAt(index);
			if ((s1.isSubmenu()) && !(s1.getName().equals(allLessThis.getName()))){
				submenus[i] = s1.getName();
				i++;
			}
		}
		
		return submenus;
	}

	/**
	 * Gets an array with the paths of all the submenus inside this submenu
	 * @param root
	 * 		the root path
	 * @return
	 * 		the array with the paths
	 */
	public ArrayList<String> getAllSubmenusWithPath(String root) {
		ArrayList<String> result = new ArrayList<String>();
		String aux = root;
		if (!root.equals(""))
			aux = root + "/";
		for (int index = 0; index < this.getSize(); index++) {
			AcideMenuObjectConfiguration s1 = this.getObjectAt(index);
			if (s1.isSubmenu()){
				AcideMenuSubmenuConfiguration s1SubMenu = (AcideMenuSubmenuConfiguration) s1;
				result.add(aux + s1.getName());
				result.addAll(s1SubMenu.getItemsManager().getAllSubmenusWithPath(aux + s1.getName()));
			}
		}
		
		return result;
	}



}
