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

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;

import javax.swing.JOptionPane;

import acide.files.AcideFileManager;
import acide.files.utils.AcideFileOperation;
import acide.files.utils.AcideFileTarget;
import acide.files.utils.AcideFileType;
import acide.gui.mainWindow.AcideMainWindow;
import acide.gui.mainWindow.utils.AcideLastElementOnFocus;
import acide.language.AcideLanguageManager;
import acide.process.console.AcideDatabaseManager;

/**																
 * ACIDE - A Configurable IDE inserted menu item listener.											
 *					
 * @version 0.11
 * @see ActionListener																														
 */
public class AcideInsertedItemListener implements ActionListener {
	/**
	 * ACIDE - A Configurable IDE inserted item listener inserted item.
	 */
	private AcideInsertedItem item;
	
	/**
	 * Creates a new ACIDE - A Configurable IDE inserted item listener.
	 * 
	 * @param item
	 * 			inserted item 
	 */
	public AcideInsertedItemListener (AcideInsertedItem item){
		this.item = item;
	}
	
	/**
	 * Creates a new ACIDE - A Configurable IDE inserted item listener.
	 * 
	 * @param item
	 * 			inserted item configuration
	 */
	public AcideInsertedItemListener(AcideMenuItemConfiguration item) {
		this.item = new AcideInsertedItem(item);
	}
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent
	 * )
	 */
	public void actionPerformed(ActionEvent actionEvent) {
		String parameter = item.getItemConfiguration().getParameter().toLowerCase();
		
		//Gets the command of the inserted item
		String command = item.getItemConfiguration().getCommand();
	/*	LinkedList<String> test = AcideDatabaseManager.getInstance()
				.executeCommand("/tapi /test_tapi");*/
		
		if (/*(test.equals("$succes")) ||*/ (command.startsWith("$"))){
		//If command starts with $ it is a default command
	//	if (command.startsWith("$")){
		
			
			//Execute the default action for the command
			AcideInsertedItemListenersManager.getInstance().executeAction(command, actionEvent);
			
		} else {
			
			// Performs the action depending of the parameter type
			String none = AcideLanguageManager.getInstance().getLabels().getString("s1005").toLowerCase();
			if (parameter.equals(none)){
				// Performs the none action
				noneAction();
			} else{ 
				String text = AcideLanguageManager.getInstance().getLabels().getString("s1006").toLowerCase();
				if (parameter.equals(text)){
					// Performs the text action
					textAction();
				} else {
					String file = AcideLanguageManager.getInstance().getLabels().getString("s1007").toLowerCase();
					if (parameter.equals(file)){
						// Performs the file action
						fileAction();
					} else{
						String dir = AcideLanguageManager.getInstance().getLabels().getString("s1008").toLowerCase();
						if (parameter.equals(dir)){
							// Performs the directory action
							directoryAction();
						}
					}
				}
			}
		}

	//}
		// Sets the focus in the last element on focus in ACIDE - A
		// Configurable IDE
		AcideLastElementOnFocus.setFocusOnLastElementOnFocus(AcideMainWindow.getInstance()
							.getLastElementOnFocus());
		
	}
	
	/**
	 * Asks to the user about the extra parameter type directory to execute
	 * the command in the ACIDE - A Configurable IDE console panel.
	 */
	private void directoryAction() {

		// Ask the path to the user
		String absolutePath = AcideFileManager.getInstance().askForFile(
				AcideFileOperation.OPEN, AcideFileTarget.FILES,
				AcideFileType.DIRECTORY, "", null);

		if (absolutePath != null) {

			// Executes the command that corresponds
			AcideMainWindow.getInstance().getConsolePanel()
					.executeCommand(item.getItemConfiguration().getCommand(), absolutePath);
		}
	}

	/**
	 * Asks to the user about the extra parameter type file to execute the
	 * command in the ACIDE - A Configurable IDE console panel.
	 */
	private void fileAction() {

		// Ask the path to the user
		String absolutePath = AcideFileManager.getInstance().askForFile(
				AcideFileOperation.OPEN, AcideFileTarget.FILES,
				AcideFileType.FILE, "", null);

		if (absolutePath != null) {
			// Executes the command that corresponds
			AcideMainWindow.getInstance().getConsolePanel().executeCommand(
					item.getItemConfiguration().getCommand(), absolutePath);
		}
	}

	/**
	 * Asks to the user about the extra parameter type text to execute the
	 * command in the ACIDE - A Configurable IDE console panel.
	 */
	private void textAction() {

		// Ask to the user for the text
		String text = JOptionPane.showInputDialog(
				null,
				AcideLanguageManager.getInstance().getLabels()
						.getString("s1009"));

		if (text != null && !text.equals("null")) {

			// Executes the command that corresponds
			AcideMainWindow.getInstance().getConsolePanel().executeCommand(
					item.getItemConfiguration().getCommand(), text);
		}
	}

	/**
	 * Executes the command in the ACIDE - A Configurable IDE console panel
	 * without any kind of extra parameter.
	 */
	private void noneAction() {

		// Executes the command that corresponds
		AcideMainWindow.getInstance().getConsolePanel()
			.executeCommand(item.getItemConfiguration().getCommand(), "");
	}
}
