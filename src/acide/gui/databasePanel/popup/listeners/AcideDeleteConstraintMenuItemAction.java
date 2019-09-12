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

import javax.swing.JOptionPane;
import javax.swing.tree.TreePath;

import acide.gui.databasePanel.AcideDataBasePanel;
import acide.gui.mainWindow.AcideMainWindow;
import acide.language.AcideLanguageManager;
import acide.process.console.AcideDatabaseManager;

/**
 * ACIDE - A Configurable IDE delete constraint menu item.
 * 
 * @version 0.16
 * @see ActionListener
 */
public class AcideDeleteConstraintMenuItemAction implements ActionListener{

	private String _restriction,type_restriction, table;

	public void actionPerformed(ActionEvent e) {
		
		int response = JOptionPane.showConfirmDialog(null,AcideLanguageManager.getInstance()
				.getLabels().getString("s2106"), AcideLanguageManager.getInstance()
				.getLabels().getString("s2050"), JOptionPane.YES_NO_OPTION);
		
		if (response == 0){
			
			TreePath path = AcideMainWindow.getInstance().getDataBasePanel().getTree().getSelectionPath();
			
			type_restriction = path.getParentPath().getLastPathComponent().toString();
			
			table = path.getParentPath().getParentPath().getLastPathComponent().toString();
			
			if (table.contains("("))
				table = table.substring(0,table.indexOf("("));
			
			processRestriction();
			
			String result = AcideDatabaseManager.getInstance().dropRestriction(_restriction);
			
			if (result.contains("$success")){
				AcideDataBasePanel panel = AcideMainWindow.getInstance().getDataBasePanel();
				panel.updateDataBaseTree();
			}
			else {
				JOptionPane.showMessageDialog(null,result, AcideLanguageManager.getInstance()
						.getLabels().getString("s157"), JOptionPane.OK_OPTION);
			}
		}
	}
	
	private void processRestriction(){
		TreePath path = AcideMainWindow.getInstance().getDataBasePanel().getTree().getSelectionPath();
		
		String res = path.getLastPathComponent().toString();
		_restriction = res.trim();
		
		if(type_restriction.equalsIgnoreCase("FK")){
			_restriction = _restriction.replace(".", ",");
			_restriction = _restriction.replace("->", ",");
			_restriction = ":-"+type_restriction+"("+_restriction+")";

		}else if(type_restriction.equalsIgnoreCase("IC"))
				_restriction = _restriction.substring(0, _restriction.indexOf("."));
		
			 else _restriction = ":-"+type_restriction+"("+table+","+_restriction+")";
		
	}
}
