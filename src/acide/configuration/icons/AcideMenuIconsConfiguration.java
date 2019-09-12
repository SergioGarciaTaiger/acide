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

import java.io.FileInputStream;
import java.io.FileOutputStream;

import javax.swing.JOptionPane;

import acide.language.AcideLanguageManager;
import acide.log.AcideLog;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

/**
 * ACIDE - A Configurable IDE console menu icons configuration.
 * 
 * @version 0.11
 */
public class AcideMenuIconsConfiguration {

	/**
	 * ACIDE - A Configurable IDE menu icons configuration default path.
	 */
	public static final String DEFAULT_PATH = "./resources/icons/added/";		
	/**
	 * ACIDE - A Configurable IDE menu icons configuration default name.
	 */
	public static final String DEFAULT_NAME = "addedIcons.xml";
	/**
	 * ACIDE - A Configurable IDE menu icons configuration unique class instance;
	 */
	private static AcideMenuIconsConfiguration _instance;
	/**
	 * ACIDE - A Configurable IDE menu items list manager.
	 */
	private AcideMenuIconsManager _iconsManager;
	
	public AcideMenuIconsConfiguration() {
		super();
	}
	
	/**
	 * Returns the ACIDE - A Configurable IDE menu items configuration unique class
	 * instance.
	 * 
	 * @return the ACIDE - A Configurable IDE menu items configuration unique class
	 *         instance
	 */
	public static AcideMenuIconsConfiguration getInstance() {

		if (_instance == null)
			_instance = new AcideMenuIconsConfiguration();
		return _instance;
	}
	
	/**
	 * Loads the ACIDE - A Configurable IDE menu items configuration from a XML
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
				AcideMenuIconsConfiguration menuIconsConfiguration = 
						(AcideMenuIconsConfiguration) x.fromXML(fileInputStream);

	
				// Gets the commands manager
				AcideMenuIconsManager iconsManager = menuIconsConfiguration
						.getMenuIconsManager();

				// Closes the file input stream
				fileInputStream.close();

				// Stores the items manager
				_iconsManager = iconsManager;

			} catch (Exception exception) {

				// Displays an error message
				JOptionPane.showMessageDialog(null, AcideLanguageManager
						.getInstance().getLabels().getString("s2151")
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
								.getString("s2153")
								+ " " + path);
			}
		}
	}

	/**
	 * Returns the ACIDE - A Configurable IDE menu icons list
	 * manager.
	 * 
	 * @return the ACIDE - A Configurable IDE menu icons list
	 *         manager.
	 */
	public AcideMenuIconsManager getMenuIconsManager() {
		return _iconsManager;
	}
	
	/**
	 * Sets a new value to the ACIDE - A Configurable IDE menu icons list
	 *  manager.
	 * 
	 * @param iconsManager
	 *            new value to set.
	 */
	public void setMenuIconsManager(AcideMenuIconsManager iconsManager) {
		_iconsManager = iconsManager;
	}
	
	/**
	 * Saves the ACIDE - A Configurable IDE menu icons configuration into a XML
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
	
	/**
	 * Saves the ACIDE - A Configurable IDE menu icons configuration into a XML
	 * file in a defined path given, returning true if the
	 * operation succeed or false in other case.
	 * 
	 * @param path where menu items configuration is saved.
	 * @return true if the operation succeed or false in other case.
	 */
	public boolean save(String path) {

			// Creates the XStream object to write in the XML file
			XStream xStream = new XStream(new DomDriver());

			try {

				// Creates the file output stream
				FileOutputStream fileOutputStream = new FileOutputStream(path);

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
	
	/**
	 * Returns if the ACIDE - A Configurable IDE menu icons
	 * configuration has a icon.
	 * 
	 * @return the icon to find.
	 */
	public boolean hasIcon(String icon){
		return _iconsManager.hasIcon(icon);
	}
	
	/**
	 * Returns  the ACIDE - A Configurable IDE added icon
	 * with this name.
	 * 
	 * @param the name of the icon we want to find.
	 * @return the added icon we find.
	 */
	public AcideAddedIcon getObject(String object){
		return _iconsManager.getObject(object);
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
		_iconsManager.insertAt(index, object);
	}
	
	/**
	 * Insert an object into the objectlist at the end. 
	 * 
	 * @param object
	 *            object to insert.
	 */
	public void insertObject(Object object){
		_iconsManager.insertIcon((AcideAddedIcon) object);
	}
	
	/**
	 * Deletes an object given as a parameter.
	 * 
	 * @param object
	 *            object to delete.
	 */
	public void deleteObject(String object) {
		_iconsManager.deleteIcon(object);
	}


}
