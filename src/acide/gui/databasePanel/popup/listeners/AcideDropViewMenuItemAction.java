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
 *      - Version from 0.12 to 0.16
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
package acide.gui.databasePanel.popup.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import acide.gui.mainWindow.AcideMainWindow;
import acide.gui.databasePanel.utils.DataBasePanelUtils;
import acide.language.AcideLanguageManager;
import acide.process.console.AcideDatabaseManager;

/**
 * ACIDE - A Configurable IDE drop view action.
 * 
 * @version 0.15
 * @see ActionListener
 */
public class AcideDropViewMenuItemAction implements ActionListener {

	public void actionPerformed(ActionEvent e) {
		
		String view = "", database= "";
		
		int response = JOptionPane.showConfirmDialog(null,AcideLanguageManager.getInstance()
				.getLabels().getString("s2138"), AcideLanguageManager.getInstance()
				.getLabels().getString("s2050"), JOptionPane.YES_NO_OPTION);
		
		if ( response == 0){
		
			view = AcideMainWindow.getInstance().getDataBasePanel().getTree().getSelectionPath().getLastPathComponent().toString();
			
			if ( view.contains("("))
				view = view.substring(0, view.indexOf("("));
			
			database = AcideMainWindow.getInstance().getDataBasePanel().getTree().getSelectionPath().getParentPath().getParentPath().getLastPathComponent().toString();
			
			String result = "";
			
			result = AcideDatabaseManager.getInstance().dropView(database, view);
			
			if (result != "success")
				JOptionPane.showMessageDialog(null,result, AcideLanguageManager.getInstance()
						.getLabels().getString("s2050"), JOptionPane.ERROR_MESSAGE);
			else
				//AcideMainWindow.getInstance().getDataBasePanel().updateDataBaseTree();
				DataBasePanelUtils.updateDataBasePanelView();
		}
	}
}
