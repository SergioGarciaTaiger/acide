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
 * ACIDE - A Configurable IDE menu item configuration.
 * 
 * @version 0.11
 * @see Serializable
 * @see AcideMenuObjectConfiguration
 */
public class AcideMenuItemConfiguration extends AcideMenuObjectConfiguration {
	
	/**
	 * ACIDE - A Configurable IDE menu item configuration class serial
	 * version UID.
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * ACIDE - A Configurable IDE menu item configuration command.
	 */
	private String _command;
	
	/**
	 * ACIDE - A Configurable IDE menu item configuration flag have parameter.
	 */
	private String _parameter;
	
	/**
	 * Creates a new ACIDE - A Configurable IDE lexicon menu item configuration.
	 * @param name
	 * 		the name for the item
	 * 
	 */
	public AcideMenuItemConfiguration(String name) {
		super(name);
		
		//Command is empty by default
		_command = "";
		
		//Parameter is false by default
		_parameter = "None";
	}
	
	/**
	 * Creates a new ACIDE - A Configurable IDE lexicon menu item configuration.
	 * @param copy
	 * 			the configuration 
	 */
	public AcideMenuItemConfiguration(AcideMenuItemConfiguration copy){
		super(copy);
		_command = copy.getCommand();
		_parameter = copy.getParameter();
	}

	/**
	 * Gets the ACIDE - A Configurable IDE menu item configuration command.
	 * 
	 * @return the command.
	 */
	public String getCommand() {
		return _command;
	}

	/**
	 * Sets a new value to the ACIDE - A Configurable IDE menu item configuration command.
	 * 
	 * @param command
	 * 			inserted menu command.
	 */
	public void setCommand(String command) {
		this._command = command;
	}

	/**
	 * Gets the ACIDE - A Configurable IDE menu item configuration parameter.
	 * 
	 * @return the parameter.
	 */
	public String getParameter() {
		return _parameter;
	}

	/**
	 * Sets a new value to the ACIDE - A Configurable IDE menu item configuration parameter.
	 * 
	 * @param parameter
	 * 			inserted menu parameter.
	 */
	public void setParameter(String parameter) {
		this._parameter = parameter;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result
				+ ((_command == null) ? 0 : _command.hashCode());
		result = prime * result + Integer.parseInt(_parameter);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		return true;
	}
	
	public String toString() {
		//String aux = "Item ";
		String aux = super.toString();
//		aux = " ," + _command;
//		if (_parameter){
//			aux = ", yP"; 
//		}else{
//			aux = ", nP";
//		}
		return aux;
	}

}
