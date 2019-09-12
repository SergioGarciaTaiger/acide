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
package acide.configuration.icons;

import java.io.Serializable;
import java.util.Iterator;

import acide.configuration.utils.ObjectList;

/**
 * ACIDE - A Configurable IDE console menu icons manager.
 * 
 * @version 0.11
 *  @see Serializable
 */
public class AcideMenuIconsManager implements Serializable {
	/**
	 * ACIDE - A Configurable IDE menu icons manager class serial
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
	public AcideMenuIconsManager() {

		super();

		// Creates the object list
		_list = new ObjectList();
	}

	
	/**
	 * Returns the icon at the position given as a parameter.
	 * 
	 * @param index
	 *            position to get.
	 * @return the icon at the position given as a parameter.
	 */
	public AcideAddedIcon getObjectAt(int index) {
		return (AcideAddedIcon) _list.getObjectAt(index);
	}
	
	/**
	 * Returns the icon with the absolute path given as a parameter.
	 * 
	 * @param object with the absolute path to get.
	 * @return the icon with the absolute path to get.
	 */
	public AcideAddedIcon getObject(String object){
		for (int index = 0; index < getSize(); index++) {
			
			// Gets the object at the index
			AcideAddedIcon itemAtIndex = getObjectAt(index);

			//If it absolute path is equal to the param, return it
			if (itemAtIndex.getAbsolute().equals(object))
				return itemAtIndex;
		}
		
		return null;
	}
	
	/**
	 * Returns the number of added icons with the same name.
	 * 
	 * @param object with the name to get.
	 * @return number of added icons with the same name.
	 */
	public int numberOf(String name) {
		int ret = 0;
		for (int index = 0; index < getSize(); index++) {
			
			// Gets the object at the index
			AcideAddedIcon itemAtIndex = getObjectAt(index);

			//If it has the same name, adds one to the result
			if (itemAtIndex.getName().equals(name))
				ret++;
		}
		
		return ret;
	}
		
	/**
	 * Insert a new item.
	 * 
	 * @param icon
	 *            new value to set.
	 */
	public void insertIcon(AcideAddedIcon icon) {
		_list.insert(_list.size(), icon);
	}
	
	/**
	 * Returns the list size.
	 * 
	 * @return the list size.
	 */
	public int getSize() {
		return _list.size();
	}
	
	/**
	 * Inserts a new icon in the ACIDE - A Configurable IDE 
	 * menu icons manager list.
	 * 
	 * @param icon
	 *            new icon to insert.
	 */
	public void insertIconNotExists(AcideAddedIcon icon) {

		boolean found = false;

		for (int index = 0; index < getSize(); index++) {

			// Gets the delimiter at the index
			AcideAddedIcon iconAtIndex = getObjectAt(index);

			if (iconAtIndex.equals(icon))
				found = true;
		}

		if (!found)
			insertIcon(icon);
	}
	
	/**
	 * Returns if the ACIDE - A Configurable IDE menu icons
	 * manager has a icon.
	 * 
	 * @return the icon to find.
	 */
	public boolean hasIcon(String item){
		boolean found = false;
		
		for (int index = 0; index < getSize(); index++) {
			
			// Gets the object at the index
			AcideAddedIcon iconAtIndex = getObjectAt(index);

			if (iconAtIndex.getAbsolute().equals(item))
				found = true;
		}
		
		return found;
	}	
	
	/**
	 * Deletes an object given as a parameter.
	 * 
	 * @param object
	 *            object to delete.
	 */
	public void deleteIcon(String object) {

		for (int index = 0; index < this.getSize(); index++) {

			AcideAddedIcon s1 = this.getObjectAt(index);
			if (s1.getAbsolute().equals(object))
				_list.removeAt(index);
		}
	}
	

	/**
	 * Returns the ACIDE - A Configurable IDE menu icons object
	 * list.
	 * 
	 * @return the ACIDE - A Configurable IDE menu icons object
	 *         list.
	 */
	public ObjectList getList() {
		return _list;
	}
	

	
	/**
	 * Sets a new value to the ACIDE - A Configurable IDE menu icons
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

	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((_list == null) ? 0 : _list.hashCode());
		return result;
	}


	/**
	 * Returns if the param is equal to this object.
	 * 
	 * @return the object to compare.
	 */
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AcideMenuIconsManager other = (AcideMenuIconsManager) obj;
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
	 * Returns the manager iterator.
	 * 
	 * @return the manager iterator.
	 */
	public Iterator<Object> managerIterator(){
		Iterator<Object> it = getList().getList().iterator();
		return it;
	}


}
