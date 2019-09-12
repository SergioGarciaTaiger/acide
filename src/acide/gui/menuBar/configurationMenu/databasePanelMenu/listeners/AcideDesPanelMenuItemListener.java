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
package acide.gui.menuBar.configurationMenu.databasePanelMenu.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;

import javax.swing.JOptionPane;

import acide.configuration.project.AcideProjectConfiguration;
import acide.gui.mainWindow.AcideMainWindow;
import acide.language.AcideLanguageManager;
import acide.process.console.AcideDatabaseManager;
import acide.process.console.DesDatabaseManager;

/**
 * ACIDE - A Configurable IDE des panel menu item listener.
 * 
 * @version 0.16
 * @see ActionListener
 */
public class AcideDesPanelMenuItemListener implements ActionListener{
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
		
		
		//Gets console name
		String shellPath = AcideProjectConfiguration.getInstance()
				.getShellPath();
		
		//LinkedList<String> result = AcideDatabaseManager.getInstance().executeCommand("/tapi /test_tapi");
		//if it the console is des, then we can connect
		// KO we will change it for: if (/tapi /test_tapi) == $succes 
		if (shellPath.endsWith("\\des.exe") || shellPath.endsWith("/des")) {
		//for (String s : result){if (s.equals("$succes")){	
			AcideMainWindow.getInstance().getMenu()
			.getConfigurationMenu().getDatabasePanelMenu()
				.getOdbcPanelMenuItem().setSelected(false);
			
			AcideMainWindow.getInstance().getMenu()
				.getConfigurationMenu().getDatabasePanelMenu()
					.getDesPanelMenuItem().setSelected(true);
			
			AcideDatabaseManager.setInstance(new DesDatabaseManager());
			
			AcideMainWindow.getInstance().getDataBasePanel().updateDataBaseTreeOnChange();
			
			AcideMainWindow.getInstance().getDataBasePanel().updateDataBaseTreeOnChange();
			
		} else {
				
			AcideMainWindow.getInstance().getMenu()
			.getConfigurationMenu().getDatabasePanelMenu()
				.getOdbcPanelMenuItem().setSelected(true);
			
			AcideMainWindow.getInstance().getMenu()
				.getConfigurationMenu().getDatabasePanelMenu()
					.getDesPanelMenuItem().setSelected(false);	
			
			// Displays an error message
			JOptionPane.showMessageDialog(null,
					AcideLanguageManager.getInstance().getLabels()
							.getString("s2301"), "Error",
					JOptionPane.ERROR_MESSAGE);
		}
		
		AcideMainWindow.getInstance().getMenu().getConfigurationMenu().getDatabasePanelMenu()
		.setShowDetailsEnabled(true);
		
	}//}
}
