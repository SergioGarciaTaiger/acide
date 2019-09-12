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
package acide.gui.databasePanel.popup.listeners;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.NoSuchElementException;

import javax.swing.JOptionPane;
import javax.swing.tree.DefaultMutableTreeNode;

import acide.gui.databasePanel.AcideDataBasePanel;
import acide.gui.mainWindow.AcideMainWindow;
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
					updateView();
			}
		} catch (Exception e2) {
			e2.printStackTrace();
		} 
	}
	
	/**
	 * Update only the Views node
	 */
	private void updateView(){
		
		AcideDataBasePanel panel = AcideMainWindow.getInstance().getDataBasePanel();
		
		DefaultMutableTreeNode nodeBase = (DefaultMutableTreeNode) panel.getTree().getModel()
				.getChild(panel.getTree().getModel().getRoot(), 0);
		
		try{
			DefaultMutableTreeNode nodoDes = (DefaultMutableTreeNode) nodeBase.getFirstChild();
			DefaultMutableTreeNode nodoTables = (DefaultMutableTreeNode) nodoDes.getFirstChild();
			panel.updateDataBaseTree((DefaultMutableTreeNode) nodoTables.getNextSibling());
		} catch (NoSuchElementException e){
				
			}
	}
}
