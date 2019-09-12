/*
 * ACIDE - A Configurable IDE
 * Official web site: http://acide.sourceforge.net
 * 
 * Copyright (C) 2007-2014  
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
 *      -Version from 0.12 to 0.16
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
package acide.gui.menuBar.projectMenu.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import acide.files.AcideFileExtensionFilterManager;
import acide.files.AcideFileManager;
import acide.files.utils.AcideFileOperation;
import acide.files.utils.AcideFileTarget;
import acide.files.utils.AcideFileType;
import acide.gui.mainWindow.AcideMainWindow;
import acide.language.AcideLanguageManager;

/**
 * ACIDE -A Configurable IDE project menu save project as menu item listener.
 * 
 * @version 0.11
 * @see ActionListener
 */
public class AcideSaveProjectAsMenuItemListener implements ActionListener {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent
	 * )
	 */
	@Override
	public void actionPerformed(ActionEvent actionEvent) {

		action(actionEvent);
	}
	
	public static void action(ActionEvent actionEvent){
		
		AcideFileExtensionFilterManager[] extensions={new AcideFileExtensionFilterManager(
				new String[] { "acideProject" },
				AcideLanguageManager.getInstance().getLabels()
						.getString("s328"))};
		// Asks for the file path to the user
		String filePath = AcideFileManager.getInstance().askForFileWhitFilters(
				AcideFileOperation.SAVE,
				AcideFileTarget.PROJECTS,
				AcideFileType.FILE,
				"",extensions);

		if (filePath != null) {
			
			// After askForFileWithFilters the filePath contains a fake
			// extension
			if (filePath.endsWith(".acideproject"))

				// We remove the fake extension
				filePath = filePath.substring(0, filePath.length()
						- (".acideproject").length());

			// Add the real extension if the name does not contain it
			if (!filePath.endsWith(".acideProject"))

				// Adds it
				filePath = filePath + ".acideProject";

			// Saves the project as
			AcideMainWindow.getInstance().getMenu().getProjectMenu()
					.saveProjectAs(filePath);
		}
	}
}
