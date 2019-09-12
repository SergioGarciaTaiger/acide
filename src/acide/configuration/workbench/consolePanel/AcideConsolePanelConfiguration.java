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
package acide.configuration.workbench.consolePanel;

import acide.configuration.console.AcideConsoleCommandsConfiguration;
import acide.configuration.lexicon.AcideLexiconConfiguration;

/**
 * ACIDE - A Configurable IDE console panel configuration.
 * 
 * Stores the file editor configuration for an opened file in the file editor of
 * the application.
 * 
 * @version 0.11
 */
public class AcideConsolePanelConfiguration {

	/**
	 * ACIDE - A Configurable IDE console panel configuration lexicon
	 * configuration.
	 */
	private String _lexiconConfiguration;
	
	/**
	 * ACIDE - A Configurable IDE console panel history commands configuration
	 */
	private String _commandsConfiguration;
	
	/**
	 * ACIDE - A Configurable IDE console line wrapping flag
	 */
	private boolean _lineWrapping;

	/**
	 * Creates a new ACIDE - A Configurable IDE console panel configuration.
	 */
	public AcideConsolePanelConfiguration() {

		// Sets the default lexicon configuration
		_lexiconConfiguration = AcideLexiconConfiguration.DEFAULT_PATH
				+ AcideLexiconConfiguration.DEFAULT_NAME;
		
		// Sets the default console commands configuration
		_commandsConfiguration = AcideConsoleCommandsConfiguration.DEFAULT_PATH
				+ AcideConsoleCommandsConfiguration.DEFAULT_NAME;
		
		_lineWrapping = true;
	}

	/**
	 * Returns the ACIDE - A Configurable IDE console panel configuration
	 * lexicon configuration.
	 * 
	 * @return the ACIDE - A Configurable IDE console panel configuration
	 *         lexicon configuration.
	 */
	public String getLexiconConfiguration() {
		return _lexiconConfiguration;
	}

	/**
	 * Sets a new value for the ACIDE - A Configurable IDE console panel
	 * configuration lexicon configuration.
	 * 
	 * @param lexiconConfiguration
	 *            new value to set.
	 */
	public void setLexiconConfiguration(String lexiconConfiguration) {
		_lexiconConfiguration = lexiconConfiguration;
	}
	
	/**Returns the ACIDE - A Configurable IDE console panel history 
	 * commands configuration.
	 * 
	 * @return the ACIDE - A Configurable IDE console panel history
	 * 			commands configuration.
	 */
	public String getCommandsConfiguration() {
		return _commandsConfiguration;
	}
	
	/**
	 * Sets a new value for the ACIDE - A Configurable IDE console panel
	 * history commands configuration.
	 * 
	 * @param commandsConfiguration
	 *            new value to set.
	 */
	public void setComandsConfiguration(String commandsConfiguration) {
		_commandsConfiguration = commandsConfiguration;
	}
	
	/**
	 * Gets the ACIDE - A Configurable IDE console line wrapping flag
	 * that indicates if it is activated or not
	 * 
	 * @return
	 * 		console line wrapping flag
	 */
	public boolean getLineWrapping() {
		return _lineWrapping;
	}
	
	/**
	 * Sets the ACIDE - A Configurable IDE console line wrapping flag
	 * 
	 * @param lineWrapping
	 * 		new value of the console line wrapping flag
	 */
	public void setLineWrapping(boolean lineWrapping) {
		_lineWrapping = lineWrapping;
	}
}
