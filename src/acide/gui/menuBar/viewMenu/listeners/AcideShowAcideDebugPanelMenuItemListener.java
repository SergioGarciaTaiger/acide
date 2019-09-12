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
package acide.gui.menuBar.viewMenu.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import acide.configuration.project.AcideProjectConfiguration;
import acide.gui.mainWindow.AcideMainWindow;

/**
 * ACIDE - A Configurable IDE show acide debug panel menu item listener.
 * 
 * @version 0.13
 * @see ActionListener
 */
public class AcideShowAcideDebugPanelMenuItemListener implements ActionListener {

	/*
	 * (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent arg0) {
		action(arg0);
	}
	
	public static void action(ActionEvent arg0) {
		// If the show debug panel check box menu item is selected
		if (AcideMainWindow.getInstance().getMenu().getViewMenu()
				.getShowDebugPanelCheckBoxMenuItem().isSelected()) {

			// Shows the debug panel
			AcideMainWindow.getInstance().getDebugPanel()
				.showDebugPanel();

		} else {

			// Disposes the debug panel
			AcideMainWindow.getInstance().getDebugPanel()
				.disposeDebugPanel();
		}

		AcideProjectConfiguration.getInstance().setIsDebugPanelShowed(
			AcideMainWindow.getInstance().getMenu().getViewMenu()
				.getShowDebugPanelCheckBoxMenuItem().isSelected());

		// If it is not the default project
		if (!AcideProjectConfiguration.getInstance().isDefaultProject())

			// The project has been modified
			AcideProjectConfiguration.getInstance().setIsModified(true);
		}
	
}
