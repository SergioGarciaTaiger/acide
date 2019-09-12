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

/**
 * ACIDE - A Configurable IDE added icon.
 * 
 * @version 0.11
 *  @see Serializable
 */
public class AcideAddedIcon implements Serializable {
	/**
	 * ACIDE - A Configurable IDE added icon class serial
	 * version UID.
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * ACIDE - A Configurable IDE added icon absolute path.
	 */
	private String _absolute;
	/**
	 * ACIDE - A Configurable IDE added icon relative path.
	 */
	private String _relative;
	/**
	 * ACIDE - A Configurable IDE added icon name.
	 */
	private String _name;
	
	/**
	 * Creates a new ACIDE - A Configurable IDE added icon.
	 */
	public AcideAddedIcon(String absolute, String relative, String name) {

		super();
		
		// Sets the relative path
		_relative = relative;
		
		//Sets the absolute path
		_absolute = absolute;
		
		//Sets the name
		_name = name;
	}

	/**
	 * Returns the ACIDE - A Configurable IDE added icon absolute path.
	 * 
	 * @return the ACIDE - A Configurable IDE added icon absolute path.
	 */
	public String getAbsolute() {
		return _absolute;
	}

	/**
	 * Sets a new value to the ACIDE - A Configurable IDE added icon absolute path.
	 * 
	 * @param added icon absolute path.
	 */
	public void setAbsolute(String absolute) {
		this._absolute = absolute;
	}

	/**
	 * Returns the ACIDE - A Configurable IDE added icon relative path.
	 * 
	 * @return the ACIDE - A Configurable IDE added icon relative path.
	 */
	public String getRelative() {
		return _relative;
	}

	/**
	 * Sets a new value to the ACIDE - A Configurable IDE added icon relative path.
	 * 
	 * @param added icon relative path.
	 */
	public void setRelative(String relative) {
		this._relative = relative;
	}
	
	/**
	 * Returns the ACIDE - A Configurable IDE added icon name.
	 * 
	 * @return the ACIDE - A Configurable IDE added icon name.
	 */
	public String getName() {
		return _name;
	}

	/**
	 * Sets a new value to the ACIDE - A Configurable IDE added icon name.
	 * 
	 * @param added icon name.
	 */
	public void setName(String name) {
		this._name = name;
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
		AcideAddedIcon other = (AcideAddedIcon) obj;
		if (_absolute == null) {
			if (other._absolute != null)
				return false;
		} else if (!_absolute.equals(other._absolute))
			return false;
		return true;
	}
	
	

}
