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

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.tree.TreePath;

import acide.gui.databasePanel.AcideDataBasePanel;
import acide.gui.databasePanel.constraintsMenu.AcideConstraintDefinitionWindow;
import acide.gui.mainWindow.AcideMainWindow;

/**
 * ACIDE - A Configurable IDE modify constraint type menu item.
 * 
 * @version 0.16
 * @see ActionListener
 */
public class AcideModifyConstraintMenuItemAction implements ActionListener {
	

	public void actionPerformed(ActionEvent e) {
		
		AcideDataBasePanel panel = AcideMainWindow.getInstance().getDataBasePanel();
		
		TreePath tree = panel.getTree().getSelectionPath();
		
		String database = tree.getParentPath().getParentPath().getParentPath().getParentPath()
				.getLastPathComponent().toString();
		
		String table = tree.getParentPath().getParentPath().getLastPathComponent().toString();
		
		if (table.contains("("))
			table = table.substring(0, table.indexOf("("));
		
		AcideConstraintDefinitionWindow panelCW  = panel.getConstraintWindow(database, table);
		getOption(panelCW);
	
	}
	
	/**
	 * Gets the ACIDE - A Configurable IDE constraint type to modify.
	 * @param panelCW 
	 *
	 */
	public void getOption(AcideConstraintDefinitionWindow panelCW){
		
		TreePath path = AcideMainWindow.getInstance().getDataBasePanel().getTree().getSelectionPath();
		
		String type_restriction = path.getParentPath().getLastPathComponent().toString();
		
		if(type_restriction.equals("PK"))
			panelCW.getTabbedPane().setSelectedIndex(0);
		else if(type_restriction.equals("CK"))
			panelCW.getTabbedPane().setSelectedIndex(1);
		else if(type_restriction.equals("FK"))
			panelCW.getTabbedPane().setSelectedIndex(2);
		else if(type_restriction.equals("NN"))
			panelCW.getTabbedPane().setSelectedIndex(3);
		else if(type_restriction.equals("FD")) 
			panelCW.getTabbedPane().setSelectedIndex(4);
		else if(type_restriction.equals("IC")) 
			panelCW.getTabbedPane().setSelectedIndex(5);
		
	}
	

}
