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

import acide.configuration.menu.items.AcideMenuItemsManager;
import acide.gui.toolBarPanel.consolePanelToolBar.utils.AcideParameterType;

/**
 * ACIDE - A Configurable IDE menu object configuration.
 * 
 * @version 0.11
 */
public class AcideMenuObjectConfigurationWithNumber {
	
	/**
	 * ACIDE - A Configurable IDE menu object configuration.
	 */
	private AcideMenuObjectConfiguration _ob;
	
	/**
	 * ACIDE - A Configurable IDE menu object configuration row.
	 */
	private int _row;
	
	/**
	 * ACIDE - A Configurable IDE menu object configuration parameter type.
	 */
	private AcideParameterType _parameterType;
	
	/**
	 * Creates a new menu object configuration with number.
	 * @param ob
	 * 		the object to set as configuration.
	 * @param row
	 * 		the row which has the object in the table of menu configuration.
	 */
	public AcideMenuObjectConfigurationWithNumber(AcideMenuObjectConfiguration ob, int row){
		//Sets the configuration
		this._ob = ob;
		
		//Sets the row
		this._row = row;
		if (this._ob.isItem()){
			AcideMenuItemConfiguration itemAux = (AcideMenuItemConfiguration) this._ob;
			String paramAux = itemAux.getParameter();
			//Sets the parameter type
			paramAux = paramAux.toLowerCase();
			if ((paramAux.matches("none")) || (paramAux.matches("ninguno")))
				_parameterType = AcideParameterType.NONE;
			if ((paramAux.matches("text")) || (paramAux.matches("texto")))
				_parameterType = AcideParameterType.TEXT;
			if ((paramAux.matches("file")) || (paramAux.matches("archivo")))
				_parameterType = AcideParameterType.FILE;
			if ((paramAux.matches("directory")) || (paramAux.matches("directorio")))
				_parameterType = AcideParameterType.DIRECTORY;
		}
	}
	
	
	/**
	 * Gets the parameter type of the menu object
	 * @return the parameter type of the menu object
	 */
	public AcideParameterType getParameterType() {
		if (this._ob.isItem()){
			AcideMenuItemConfiguration itemAux = (AcideMenuItemConfiguration) this._ob;
			String paramAux = itemAux.getParameter();
			paramAux = paramAux.toLowerCase();
			if ((paramAux.matches("none")) || (paramAux.matches("ninguno")))
				_parameterType = AcideParameterType.NONE;
			if ((paramAux.matches("text")) || (paramAux.matches("texto")))
				_parameterType = AcideParameterType.TEXT;
			if ((paramAux.matches("file")) || (paramAux.matches("archivo")))
				_parameterType = AcideParameterType.FILE;
			if ((paramAux.matches("directory")) || (paramAux.matches("directorio")))
				_parameterType = AcideParameterType.DIRECTORY;
		}
		return _parameterType;
	}


	/**
	 * Sets the parameter type for the menu object configuration
	 * @param _parameterType
	 * 		the new value for the parameter type
	 */
	public void setParameterType(AcideParameterType _parameterType) {	
		this._parameterType = _parameterType;
		if (this._ob.isItem()){
			AcideMenuItemConfiguration itemAux = (AcideMenuItemConfiguration) this._ob;
			itemAux.setParameter(_parameterType.toString());
		}
	}


	/**
	 * Gets the configuration of the menu object
	 * @return the configuration of the menu object
	 */
	public AcideMenuObjectConfiguration getOb() {
		return _ob;
	}

	/**
	 * Sets the parameter type for the menu object configuration
	 * @param _parameterType
	 * 		the new value for the parameter type
	 */
	public void setOb(AcideMenuObjectConfiguration ob) {
		this._ob = ob;
	}

	/**
	 * Gets the row of the menu object
	 * @return the row of the menu object
	 */
	public int getRow() {
		return _row;
	}

	/**
	 * Sets the row for the menu object configuration
	 * @param _parameterType
	 * 		the new value for the row
	 */
	public void setRow(int row) {
		this._row = row;
	}
	
	/**
	 * Gets the image of the menu object
	 * @return the image of the menu object
	 */
	public String getImage(){
		return _ob.getImage();
	}
	
	/**
	 * Sets the image for the menu object configuration
	 * @param _parameterType
	 * 		the new value for the image
	 */
	public void setImage(String image){
		_ob.setImage(image);
	}
	
	/**
	 * Gets the name of the menu object
	 * @return the name of the menu object
	 */
	public String getName() {
		return _ob.getName();
	}
	
	/**
	 * Return if the menu object is an item.
	 * @return
	 * 		if the menu object is an item.
	 */
	public boolean isItem() {
		AcideMenuItemConfiguration aux = new AcideMenuItemConfiguration("prueba");
		if (_ob.getClass() != aux.getClass())
			return false;
		return true;
	}
	
	/**
	 * Return if the menu object is a submenu.
	 * @return
	 * 		if the menu object is a submenu.
	 */
	public boolean isSubmenu() {
		AcideMenuSubmenuConfiguration  aux = new AcideMenuSubmenuConfiguration("prueba");
		if (_ob.getClass() != aux.getClass())
			return false;
		return true;
	}
	
	/**
	 * Return a string that indicates if is an item or submenu.
	 * @return
	 * 		"Item" if is an item
	 * 		"Submenu" if is a submenu.
	 */
	public String type(){
		if (isItem())
			return "Item";
		else
			return "Submenu";
	}
	
	/**
	 * Gets the path of the menu object.
	 * @return
	 * 		the path of the object.
	 */
	public String path(){
		return _ob.path();
	}
	
	/**
	 * Sets the path of the menu object.
	 * @param path
	 * 		the new path for the menu object.
	 */
	public void setPath(String path){
		_ob.setPath(path);
	}

	/**
	 * Sets a new value to the ACIDE - A Configurable IDE menu object configuration name.
	 * 
	 * @param name
	 *            new value to set.
	 */
	public void setName(String name) {
		_ob.setName(name);
	}

	/**
	 * Returns the ACIDE - A Configurable IDE menu object configuration visible flag.
	 * 
	 * @return the ACIDE - A Configurable IDE menu object configuration visible flag.
	 */
	public boolean isVisible() {
		return _ob.isVisible();
	}

	/**
	 * Sets a new value to the ACIDE - A Configurable IDE menu object configuration visible flag.
	 * 
	 * @param visible
	 *            new value to set.
	 */
	public void setVisible(boolean visible) {
		_ob.setVisible(visible);
	}

	/**
	 * Returns the ACIDE - A Configurable IDE menu object configuration erasable flag.
	 * 
	 * @return the ACIDE - A Configurable IDE menu object configuration erasable flag.
	 */
	public boolean isErasable() {
		return _ob.isErasable();
	}

	/**
	 * Sets a new value to the ACIDE - A Configurable IDE menu object configuration erasable flag.
	 * 
	 * @param erasable
	 *            new value to set.
	 */
	public void setErasable(boolean erasable) {
		_ob.setErasable(erasable);
	}
	
	/**
	 * Gets the ACIDE - A Configurable IDE menu item configuration command.
	 * 
	 * @return the command.
	 */
	public String getCommand() {
		if (isItem()){
			AcideMenuItemConfiguration aux = (AcideMenuItemConfiguration) _ob;
			return aux.getCommand();
		}else {
			return "-";
		}
	}

	/**
	 * Sets a new value to the ACIDE - A Configurable IDE menu item configuration command.
	 * 
	 * @param _command
	 * 			inserted menu command.
	 */
	public void setCommand(String _command) {
		if (isItem()){
			AcideMenuItemConfiguration aux = (AcideMenuItemConfiguration) _ob;
			aux.setCommand(_command);
		}
	}

	/**
	 * Gets the ACIDE - A Configurable IDE menu item configuration parameter.
	 * 
	 * @return the parameter.
	 */
	public String getParameter() {
		if (isItem()){
			AcideMenuItemConfiguration aux = (AcideMenuItemConfiguration) _ob;
			return aux.getParameter();
		}else {
			return "-";
		}
	}

	/**
	 * Sets a new value to the ACIDE - A Configurable IDE menu item configuration parameter.
	 * 
	 * @param parameter
	 * 		 menu parameter.
	 */
	public void setParameter(String _parameter) {
		if (isItem()){
			AcideMenuItemConfiguration aux = (AcideMenuItemConfiguration) _ob;
			aux.setParameter(_parameter);
		}
	}
	
	/**
	 * Gets the ACIDE - A Configurable IDE menu item configuration items manager.
	 * 
	 * @return the items manager.
	 */
	public AcideMenuItemsManager getItemsManager() {
		if (isSubmenu()){
			AcideMenuSubmenuConfiguration aux = (AcideMenuSubmenuConfiguration) _ob;
			return aux.getItemsManager();
		}
		return null;
	}

	/**
	 * Sets a new value to the ACIDE - A Configurable IDE menu item configuration parameter.
	 * 
	 * @param itemsManager
	 * 		 items Manager.
	 */
	public void setItemsManager(AcideMenuItemsManager itemsManager) {
		if (isSubmenu()){
			AcideMenuSubmenuConfiguration aux = (AcideMenuSubmenuConfiguration) _ob;
			 aux.setItemsManager(itemsManager);
		}
	}
	
	
	/**
	 * Returns the object at the position given as a parameter.
	 * 
	 * @param index
	 *            position to get.
	 * @return the object at the position given as a parameter.
	 */
	public AcideMenuObjectConfiguration getObjectAt(int index) {
		if (isSubmenu()){
			AcideMenuSubmenuConfiguration aux = (AcideMenuSubmenuConfiguration) _ob;
			return aux.getObjectAt(index);
		}else {
			return null;
		}
	}
	
	/**
	 * Inserts a new object to the items list.
	 * 
	 * @param object
	 *            new object to insert.
	 */
	public void insertObject(AcideMenuObjectConfiguration object) {
		if (isSubmenu()){
			AcideMenuSubmenuConfiguration aux = (AcideMenuSubmenuConfiguration) _ob;
			aux.insertObject(object);
		}
	}
	
	/**
	 * Returns if the menu object has the item given as parameter
	 * @param item
	 * 		the item we want to find
	 * @return
	 * 		if the menu object has the item given as parameter
	 */
	public boolean hasItem(String item){
		if (isSubmenu()){
			AcideMenuSubmenuConfiguration aux = (AcideMenuSubmenuConfiguration) _ob;
			return aux.hasItem(item);
		}
		return false;
	}
	
	/**
	 * Returns if the menu object has the submenu given as parameter
	 * @param submenu
	 * 		the submenu we want to find
	 * @return
	 * 		if the menu object has the submenu given as parameter
	 */
	public boolean hasSubmenu(String submenu){
		if (isSubmenu()){
			AcideMenuSubmenuConfiguration aux = (AcideMenuSubmenuConfiguration) _ob;
			return aux.hasSubmenu(submenu);
		}
		return false;
	}
	
	/**
	 * Gets the object with the name given as parameter
	 * @param object
	 * 		the name of the object to get
	 * @return
	 * 		the object with the name given as parameter
	 */
	public AcideMenuObjectConfiguration getObject(String object){
		if (isSubmenu()){
			AcideMenuSubmenuConfiguration aux = (AcideMenuSubmenuConfiguration) _ob;
			return aux.getObject(object);
		}
		return null;
	}
	
	/**
	 * Gets the item with the name given as parameter
	 * @param item
	 * 		the name of the item to get
	 * @return
	 * 		the item with the name given as parameter
	 */
	public AcideMenuItemConfiguration getItem(String item){
		if (isSubmenu()){
			AcideMenuSubmenuConfiguration aux = (AcideMenuSubmenuConfiguration) _ob;
			return aux.getItem(item);
		}
		return null;
	}
	
	/**
	 * Gets the submenu with the name given as parameter
	 * @param submenu
	 * 		the name of the submenu to get
	 * @return
	 * 		the submenu with the name given as parameter
	 */
	public AcideMenuSubmenuConfiguration getSubmenu(String submenu){
		if (isSubmenu()){
			AcideMenuSubmenuConfiguration aux = (AcideMenuSubmenuConfiguration) _ob;
			return aux.getSubmenu(submenu);
		}
		return null;
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
		if (isSubmenu()){
			AcideMenuSubmenuConfiguration aux = (AcideMenuSubmenuConfiguration) _ob;
			aux.insertAt(index, object);
		}
	}



	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((_ob == null) ? 0 : _ob.hashCode());
		result = prime * result + _row;
		return result;
	}
	
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AcideMenuObjectConfigurationWithNumber other = (AcideMenuObjectConfigurationWithNumber) obj;
		if (getName() == null) {
			if (other.getName() != null)
				return false;
		} else if (!getName().equals(other.getName()))
			return false;
		if (getRow() != other.getRow())
			return false;
		return true;
	}

	@Override
	public String toString() {
		String aux = getRow() + ": " + getName();
//		if (_erasable){
//			aux = _name + " E";
//		}else {
//			aux = _name + " nE";
//		}
//		if (_visible){
//			aux = _name + " V";
//		}else {
//			aux = _name + " nV";
//		}

		return aux;
	}
	
	/**
	 * The total size of the menu with all its submenus and items.
	 * @return
	 * 	total size of the menu with all its submenus and items.
	 */
	public int totalSize(){
		int i = 0;
		if (isSubmenu()){
			i = getItemsManager().getAllObjects().size();
		}
		return i;
	}


}
