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
package acide.gui.menuBar.configurationMenu.consoleMenu.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import acide.configuration.workbench.AcideWorkbenchConfiguration;
import acide.gui.mainWindow.AcideMainWindow;
import acide.log.AcideLog;

/**																
 * ACIDE - A Configurable IDE configure console line wrapping menu item listener.											
 *					
 * @version 0.13
 * @see ActionListener																														
 */
public class AcideConsoleLineWrappingMenuItemListener implements ActionListener {

	/*
	 * (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent actionEvent) {
		action(actionEvent);
	}
	
	public static void action(ActionEvent actionEvent) {
		
		// Updates the ACIDE - A Configurable IDE workbench console
		// configuration
		boolean lineWrapping = AcideWorkbenchConfiguration.getInstance()
				.getConsolePanelConfiguration()
				.getLineWrapping();
		
		//Sets the new value of the console line wrapping
		AcideWorkbenchConfiguration
				.getInstance()
				.getConsolePanelConfiguration()
				.setLineWrapping(!lineWrapping);
		
		//Changes the console line wrapping check box menu item
		AcideMainWindow.getInstance().getMenu()
			.getConfigurationMenu().getConsoleMenu()
			.getConsoleLineWrappingCheckBoxMenuItem().setSelected(!lineWrapping);
		
		//Changes the console line wrapping pop up menu
		AcideMainWindow.getInstance().getConsolePanel().getPopupMenu()
			.getLineWrappingMenuItem().setSelected(!lineWrapping);

		try {
			
			//Keeps the split panel divider location
			int div = AcideMainWindow
					.getInstance()
					.getSpecificSplitPane(
							AcideMainWindow.getInstance().getConsolePanel()
									.getSplitContainer()).getDividerLocation();

			//Changes the size in 1 to make the changes visible
			if (lineWrapping)
				div++;
			else
				div--;

			//Sets the new value of the split panel divider location
			AcideMainWindow
					.getInstance()
					.getSpecificSplitPane(
							AcideMainWindow.getInstance().getConsolePanel()
									.getSplitContainer())
					.setDividerLocation(div);
				
		} catch (Exception exception) {

			// Updates the log
			AcideLog.getLog().error(exception.getMessage());
			exception.printStackTrace();
		}
		
	}

}
