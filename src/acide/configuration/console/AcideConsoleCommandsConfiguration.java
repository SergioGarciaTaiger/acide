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
package acide.configuration.console;

import java.io.FileInputStream;
import java.io.FileOutputStream;

import javax.swing.JOptionPane;

import acide.configuration.console.commands.AcideConsoleCommandsManager;
import acide.language.AcideLanguageManager;
import acide.log.AcideLog;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;


/**
 * ACIDE - A Configurable IDE console commands configuration.
 * 
 * @version 0.11
 */
public class AcideConsoleCommandsConfiguration {

	/**
	 * ACIDE - A Configurable IDE console configuration default path.
	 */
	public static final String DEFAULT_PATH = "./configuration/console/";
	/**
	 * ACIDE - A Configurable IDE console commands configuration default name.
	 */
	public static final String DEFAULT_NAME = "default.xml";
	/**
	 * ACIDE - A Configurable IDE historical commands list manager.
	 */
	private AcideConsoleCommandsManager _commandsManager;
	
	/**
	 * Creates a new ACIDE - A Configurable IDE console commands configuration.
	 */
	public AcideConsoleCommandsConfiguration() {
		super();
	}
	
	/**
	 * Loads the ACIDE - A Configurable IDE console commands configuration from a XML
	 * file which is located in a path given as a parameter.
	 * 
	 * @param path
	 *            File path of the file to extract the configuration from.
	 */
	public void load(String path) {

		// If the name is already set by the user
		if ((path != null) && (!path.trim().equalsIgnoreCase(""))) {
			try {

				// Creates the XStream object
				XStream x = new XStream(new DomDriver());

				// Creates the file input stream
				FileInputStream fileInputStream = new FileInputStream(path);

				// Gets the lexicon configuration from the XML file
				AcideConsoleCommandsConfiguration consoleCommandsConfiguration = (AcideConsoleCommandsConfiguration) x
						.fromXML(fileInputStream);

	
				// Gets the commands manager
				AcideConsoleCommandsManager commandsManager = consoleCommandsConfiguration
						.getConsoleCommandsManager();

				// Closes the file input stream
				fileInputStream.close();

				// Stores the commands manager
				_commandsManager = commandsManager;

			} catch (Exception exception) {

				// Displays an error message
				JOptionPane.showMessageDialog(null, AcideLanguageManager
						.getInstance().getLabels().getString("s2150")
						+ path
						+ AcideLanguageManager.getInstance().getLabels()
								.getString("s957")
						+ DEFAULT_PATH
						+ DEFAULT_NAME);

				// If the file does not exist, loads the default configuration
				load(DEFAULT_PATH + DEFAULT_NAME);

				// Updates the log
				AcideLog.getLog().info(
						AcideLanguageManager.getInstance().getLabels()
								.getString("s2152")
								+ " " + path);
			}
		}
	}
	
	/**
	 * Returns the ACIDE - A Configurable IDE historical commands list
	 * manager.
	 * 
	 * @return the ACIDE - A Configurable IDE historical commands list
	 *         manager.
	 */
	public AcideConsoleCommandsManager getConsoleCommandsManager() {
		return _commandsManager;
	}
	

	/**
	 * Sets a new value to the ACIDE - A Configurable IDE historical commands list
	 *  manager.
	 * 
	 * @param commandsManager
	 *            new value to set.
	 */
	public void setConsoleCommandsManager(AcideConsoleCommandsManager commandsManager) {
		_commandsManager = commandsManager;
	}
	
	/**
	 * Saves the ACIDE - A Configurable IDE commands history into a XML
	 * file in a defined path given default, returning true if the
	 * operation succeed or false in other case.
	 * 
	 * @return true if the operation succeed or false in other case.
	 */
	public boolean save() {

			// Creates the XStream object to write in the XML file
			XStream xStream = new XStream(new DomDriver());

			try {

				// Creates the file output stream
				FileOutputStream fileOutputStream = new FileOutputStream(DEFAULT_PATH + DEFAULT_NAME);

				// Parses it to XML
				xStream.toXML(this, fileOutputStream);

				// Closes the file output stream
				fileOutputStream.close();

			} catch (Exception exception) {

				// Updates the log
				AcideLog.getLog().error(exception.getMessage());
				exception.printStackTrace();
				return false;
			}

		return true;
	}
	
}
