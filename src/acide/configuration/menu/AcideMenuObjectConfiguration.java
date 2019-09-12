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

/**
 * ACIDE - A Configurable IDE menu object configuration.
 * 
 * @version 0.11
 * @see Serializable
 */
public class AcideMenuObjectConfiguration  implements Serializable {
	
	/**
	 * ACIDE - A Configurable IDE menu object configuration class serial
	 * version UID.
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * ACIDE - A Configurable IDE menu object configuration name.
	 */
	private String _name;
	
	/**
	 * ACIDE - A Configurable IDE menu object configuration flag that
	 * indicates if it is visible or not.
	 */
	private boolean _visible;
	
	/**
	 * ACIDE - A Configurable IDE menu object configuration flag that
	 * indicates if it is erasable or not.
	 */
	private boolean _erasable;
	/**
	 * ACIDE - A Configurable IDE menu object configuration image.
	 */
	private String _image;
	
	/**
	 * Creates a new ACIDE - A Configurable IDE menu object configuration.
	 * @param name
	 * 			the name for the menu object
	 */
	public AcideMenuObjectConfiguration(String name) {

		super();
		
		// Sets the name
		_name = name;
		
		//It is visible by default
		_visible = true;
		
		//It is erasable by default
		_erasable = true;
		
		//Image is empty by default
		_image = "";
	}

	/**
	 * Creates a new ACIDE - A Configurable IDE menu object configuration.
	 */
	public AcideMenuObjectConfiguration() {
		_name = "Default";
	}
	
	public AcideMenuObjectConfiguration(AcideMenuObjectConfiguration copy){
		_name = copy.getName();
		_visible = copy.isVisible();
		_erasable = copy.isErasable();
		_image = copy.getImage();
	}

	/**
	 * Returns the ACIDE - A Configurable IDE menu object configuration  
	 * name.
	 * 
	 * @return the ACIDE - A Configurable IDE menu object configuration  
	 * name.
	 */
	public String getName() {
		return _name;
	}
	
	/**
	 * Return if the menu object is an item.
	 * @return
	 * 		if the menu object is an item.
	 */
	public boolean isItem() {
		AcideMenuItemConfiguration aux = new AcideMenuItemConfiguration("prueba");
		if (getClass() != aux.getClass())
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
		if (getClass() != aux.getClass())
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
		return "";
	}
	
	/**
	 * Sets the path of the menu object.
	 * @param path
	 * 		the new path for the menu object.
	 */
	public void setPath(String path){
		;
	}

	/**
	 * Sets a new value to the ACIDE - A Configurable IDE menu object configuration name.
	 * 
	 * @param name
	 *            new value to set.
	 */
	public void setName(String name) {
		_name = name;
	}

	/**
	 * Returns the ACIDE - A Configurable IDE menu object configuration visible flag.
	 * 
	 * @return the ACIDE - A Configurable IDE menu object configuration visible flag.
	 */
	public boolean isVisible() {
		return _visible;
	}

	/**
	 * Sets a new value to the ACIDE - A Configurable IDE menu object configuration visible flag.
	 * 
	 * @param visible
	 *            new value to set.
	 */
	public void setVisible(boolean visible) {
		_visible = visible;
	}

	/**
	 * Returns the ACIDE - A Configurable IDE menu object configuration erasable flag.
	 * 
	 * @return the ACIDE - A Configurable IDE menu object configuration erasable flag.
	 */
	public boolean isErasable() {
		return _erasable;
	}

	/**
	 * Sets a new value to the ACIDE - A Configurable IDE menu object configuration erasable flag.
	 * 
	 * @param erasable
	 *            new value to set.
	 */
	public void setErasable(boolean erasable) {
		_erasable = erasable;
	}

	/**
	 * Returns the ACIDE - A Configurable IDE menu object configuration image.
	 * 
	 * @return the ACIDE - A Configurable IDE menu object configuration image.
	 */
	public String getImage() {
		return _image;
	}

	/**
	 * Sets a new value to the ACIDE - A Configurable IDE menu object configuration image.
	 * 
	 * @param image
	 *            new value to set.
	 */
	public void setImage(String image) {
		this._image = image;
	}

	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((_name == null) ? 0 : _name.hashCode());
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
		AcideMenuObjectConfiguration other = (AcideMenuObjectConfiguration) obj;
		if (_name == null) {
			if (other._name != null)
				return false;
		} else if (!_name.equals(other._name))
			return false;
		return true;
	}

	@Override
	public String toString() {
		String aux = _name;

		return aux;
	}
	
	

}
