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
package acide.configuration.console.commands;

import java.io.Serializable;

import acide.configuration.utils.ObjectList;

/**
 * ACIDE - A Configurable IDE console commands history manager.
 * 
 * @version 0.11
 * @see Serializable
 */

public class AcideConsoleCommandsManager implements Serializable{
	
	/**
	 * ACIDE - A Configurable IDE console commands history manager class serial
	 * version UID.
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * ACIDE - A Configurable IDE console commands history manager object list.
	 */
	private ObjectList _list;
	
	/**
	 * Creates a new ACIDE - A Configurable IDE console commands history manager.
	 */
	public AcideConsoleCommandsManager() {

		super();

		// Creates the object list
		_list = new ObjectList();
	}
	
	/**
	 * Returns the command at the position given as a parameter.
	 * 
	 * @param index
	 *            position to get.
	 * @return the command at the position given as a parameter.
	 */
	public String getCommandAt(int index) {
		return (String) _list.getObjectAt(index);
	}
	
	/**
	 * Insert a new command.
	 * 
	 * @param command
	 *            new value to set.
	 */
	public void setCommand(String command) {
		_list.insert(_list.size(), command);
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
	 * Inserts a new command in the ACIDE - A Configurable IDE console
	 * commands history manager list.
	 * 
	 * @param command
	 *            new command to insert.
	 */
	public void insertCommand(String command) {

		boolean found = false;

		for (int index = 0; index < getSize(); index++) {

			// Gets the delimiter at the index
			String commandAtIndex = getCommandAt(index);

			if (commandAtIndex.equals(command))
				found = true;
		}

		if (!found)
			setCommand(command);
	}
	
	/**
	 * Deletes a command given as a parameter.
	 * 
	 * @param command
	 *            command to delete.
	 */
	public void deleteCommand(String command) {

		for (int index = 0; index < this.getSize(); index++) {

			String s1 = this.getCommandAt(index);
			if (s1.equals(command))
				_list.removeAt(index);
		}
	}
	
	/**
	 * Returns the ACIDE - A Configurable IDE console commands history object
	 * list.
	 * 
	 * @return the ACIDE - A Configurable IDE console commands history object
	 *         list.
	 */
	public ObjectList getList() {
		return _list;
	}
	
	/**
	 * Sets a new value to the ACIDE - A Configurable IDE commands history
	 *  object list.
	 * 
	 * @return the object list.
	 */
	public void setList(ObjectList list) {
		_list = list;
	}

}
