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
package acide.gui.menuBar.fileMenu.listeners;

import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import acide.files.AcideFileManager;
import acide.files.utils.AcideFileOperation;
import acide.files.utils.AcideFileTarget;
import acide.files.utils.AcideFileType;
import acide.gui.mainWindow.AcideMainWindow;
import acide.language.AcideLanguageManager;
import acide.log.AcideLog;

/**
 * ACIDE - A Configurable IDE file menu open file menu item listener.
 * 
 * @version 0.11
 * @see ActionListener
 */
public class AcideOpenFileMenuItemListener implements ActionListener {

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
	
		// Ask the path to the user
		String[] absolutePaths = AcideFileManager.getInstance().askForFiles(
				AcideFileOperation.OPEN, AcideFileTarget.FILES,
				AcideFileType.FILE, "", null);

		if (absolutePaths != null) {
			
			//Put the wait cursor
			int oldCursor = AcideMainWindow.getInstance().getCursor().getType();
			AcideMainWindow.getInstance().setCursor(
					Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));

			AcideMainWindow.getInstance().getGlassPane()
					.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
			AcideMainWindow.getInstance().getGlassPane().setVisible(true);
			
			// Opens all the selected files
			for (int index = 0; index < absolutePaths.length; index++) {


				// Opens the file
				AcideMainWindow.getInstance().getMenu().getFileMenu()
						.openFile(absolutePaths[index]);
				
			}
			
			//Get back the old cursor
			AcideMainWindow.getInstance().setCursor(
					Cursor.getPredefinedCursor(oldCursor));

			AcideMainWindow.getInstance().getGlassPane()
					.setCursor(Cursor.getPredefinedCursor(oldCursor));
			AcideMainWindow.getInstance().getGlassPane().setVisible(false);
			
		} else {

			// Updates the log
			AcideLog.getLog().info(
					AcideLanguageManager.getInstance().getLabels()
							.getString("s83"));
		}
		
		
	}
}