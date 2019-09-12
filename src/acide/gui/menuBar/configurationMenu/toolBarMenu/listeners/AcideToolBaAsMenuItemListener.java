/*
 * ACIDE - A Configurable IDE
 * Official web site: http://acide.sourceforge.net
 * 
 * Copyright (C) 2007-2014  
 * Authors:
 * 		- Fernando S�enz P�rez (Team Director).
 *      - Version from 0.1 to 0.6:
 *      	- Diego Cardiel Freire.
 *			- Juan Jos� Ortiz S�nchez.
 *          - Delf�n Rup�rez Ca�as.
 *      - Version 0.7:
 *          - Miguel Mart�n L�zaro.
 *      - Version 0.8:
 *      	- Javier Salcedo G�mez.
 *      - Version from 0.9 to 0.11:
 *      	- Pablo Guti�rrez Garc�a-Pardo.
 *      	- Elena Tejeiro P�rez de �greda.
 *      	- Andr�s Vicente del Cura.
 *      -Version from 0.12 to 0.16
 *      	- Sem�ramis Guti�rrez Quintana
 *      	- Juan Jes�s Marqu�s Ortiz
 *      	- Fernando Ord�s Lorente
 *      - Version 0.17
 *      	- Sergio Dom�nguez Fuentes
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
package acide.gui.menuBar.configurationMenu.toolBarMenu.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import acide.files.AcideFileExtensionFilterManager;
import acide.files.AcideFileManager;
import acide.files.bytes.AcideByteFileManager;
import acide.files.utils.AcideFileOperation;
import acide.files.utils.AcideFileTarget;
import acide.files.utils.AcideFileType;
import acide.gui.mainWindow.AcideMainWindow;
import acide.gui.menuBar.configurationMenu.toolBarMenu.gui.AcideToolBarConfigurationWindow;
import acide.language.AcideLanguageManager;
import acide.log.AcideLog;
import acide.resources.AcideResourceManager;

/**
 * ACIDE - A Configurable IDE tool bar menu save as tool bar listener.
 * 
 * @version 0.11
 * @see ActionListener
 */
public class AcideToolBaAsMenuItemListener implements ActionListener {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent actionEvent) {
		action(actionEvent);
	}
	
	public static void action(ActionEvent actionEvent){

		try {

			// Asks the the file to the user
			String absolutePath = AcideFileManager.getInstance().askForFile(
					AcideFileOperation.SAVE,
					AcideFileTarget.FILES,
					AcideFileType.FILE,
					"./configuration/toolbar/",
					new AcideFileExtensionFilterManager(
							new String[] { "toolbarConfig" }, AcideLanguageManager
									.getInstance().getLabels().getString("s904")));

			if (absolutePath != null) {

				// If it does not contains the valid extension
				if (!absolutePath.endsWith(".toolbarConfig"))

					// Adds it
					absolutePath += ".toolbarConfig";

				// Gets the ACIDE - A Configurable IDE current tool bar
				// configuration
				String currentToolBarConfiguration = AcideResourceManager
						.getInstance().getProperty("currentToolBarConfiguration");

				// Copies the files
				AcideByteFileManager.getInstance().copy(
						currentToolBarConfiguration, absolutePath);

				// Updates the ACIDE - A Configurable IDE current tool bar
				// configuration
				AcideResourceManager.getInstance().setProperty(
						"currentToolBarConfiguration", absolutePath);

				// Disables the save tool bar menu item
				AcideMainWindow.getInstance().getMenu().getConfigurationMenu()
						.getToolBarMenu().getSaveToolBarMenuItem()
						.setEnabled(false);

				// The changes are saved
				AcideToolBarConfigurationWindow.setAreChangesSaved(true);

				// Updates the log
				AcideLog.getLog().info(
						AcideLanguageManager.getInstance().getLabels()
								.getString("s900")
								+ absolutePath
								+ AcideLanguageManager.getInstance()
										.getLabels().getString("s901"));
			}
		} catch (Exception exception) {

			// Displays an error message
			JOptionPane.showMessageDialog(
					null,
					exception.getMessage(),
					AcideLanguageManager.getInstance().getLabels()
							.getString("s903"), JOptionPane.ERROR_MESSAGE);

			// Updates the log
			AcideLog.getLog().error(exception.getMessage());
		}
	}
}
