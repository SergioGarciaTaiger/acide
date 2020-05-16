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

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import acide.gui.mainWindow.AcideMainWindow;
import acide.gui.databasePanel.utils.DataBasePanelUtils;
import acide.language.AcideLanguageManager;
import acide.process.console.AcideDatabaseManager;

/**
 * ACIDE - A Configurable IDE paste view.
 * 
 * @version 0.16
 * @see ActionListener
 */
public class AcidePasteViewMenuItemAction implements ActionListener {

	public void actionPerformed(ActionEvent e) {
		try {
			Toolkit toolkit = Toolkit.getDefaultToolkit();
			Clipboard clipboard = toolkit.getSystemClipboard();
			String oldView = (String) clipboard.getData(DataFlavor.stringFlavor);
			String newView = (String)JOptionPane.showInputDialog(null,
					AcideLanguageManager.getInstance().getLabels().getString("s2119"), 
					AcideLanguageManager.getInstance().getLabels().getString("s2139"),
					JOptionPane.PLAIN_MESSAGE, null,null,oldView); 
			
			String database = AcideMainWindow.getInstance().getDataBasePanel().getTree().getSelectionPath().getParentPath().getLastPathComponent().toString();
			
			if ((newView != null) && (newView.length() > 0)) {				
				
				String result = AcideDatabaseManager.getInstance().pasteView(database, newView, oldView);
				if (result!= "success")
					JOptionPane.showMessageDialog(null,result,
						    "Error",JOptionPane.ERROR_MESSAGE);
				else
					DataBasePanelUtils.updateDataBasePanelView();
			}
		} catch (Exception e2) {
			e2.printStackTrace();
		} 
	}
}
