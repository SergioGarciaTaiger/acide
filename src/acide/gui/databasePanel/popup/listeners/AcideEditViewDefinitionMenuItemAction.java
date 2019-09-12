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

import acide.gui.databasePanel.utils.AcideEnterTextWindow;
import acide.gui.mainWindow.AcideMainWindow;
import acide.language.AcideLanguageManager;
import acide.process.console.AcideDatabaseManager;

/**
 * ACIDE - A Configurable IDE edit view definition action.
 * 
 * @version 0.15
 * @see ActionListener
 */
public class AcideEditViewDefinitionMenuItemAction implements ActionListener {
	
	private boolean _editable = false;
	private String _type ="";
	
	public AcideEditViewDefinitionMenuItemAction(boolean editable, String type){
		_editable = editable;
		_type = type;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		
		TreePath path = AcideMainWindow.getInstance().getDataBasePanel().getTree().getSelectionPath();
		
		String database = path.getParentPath().getParentPath().getParentPath().getParentPath().getLastPathComponent().toString();
		
		String node = "";
		String text = "";
		
		//The very view made the call
		if (_type.equals("View")){
			
			//Get the view name
			node = path.getLastPathComponent().toString();
		 
			if (node.contains("("))
				node = node.substring(node.indexOf("("));
				
		
			//Get the type of the view
			String type = AcideDatabaseManager.getInstance().getViewType(database,node);
		
			if (type.equals("SQL"))
				{ text = AcideDatabaseManager.getInstance().getSQLText(database, node);
		      new AcideEnterTextWindow(text, AcideLanguageManager.getInstance().getLabels().getString("s2036"), _editable);  
				}else if (type.equals("RA")){
					text = AcideDatabaseManager.getInstance().getRAText(database, node);
					new AcideEnterTextWindow(text, AcideLanguageManager.getInstance().getLabels().getString("s2288"), _editable);
				}else { 
					text = AcideDatabaseManager.getInstance().getDatalogText(database, node);
					new AcideEnterTextWindow(text, AcideLanguageManager.getInstance().getLabels().getString("s2037"), _editable);
				}
		} 
		//Some of the children's view made the call
		else {
			    //Need to know the view which is my parent
				node = path.getParentPath().getParentPath().getLastPathComponent().toString();
				
				if (node.contains("("))
					node = node.substring(node.indexOf("("));
				
				if (_type.equals("SQL"))
				{ text = AcideDatabaseManager.getInstance().getSQLText(database, node);
					new AcideEnterTextWindow(text, AcideLanguageManager.getInstance().getLabels().getString("s2036"), _editable);  
				}else if (_type.equals("RA")){
					text = AcideDatabaseManager.getInstance().getRAText(database, node);
					new AcideEnterTextWindow(text, AcideLanguageManager.getInstance().getLabels().getString("s2288"), _editable);
				}else if (_type.equals("DATALOG")){ 
					text = AcideDatabaseManager.getInstance().getDatalogText(database, node);
					new AcideEnterTextWindow(text, AcideLanguageManager.getInstance().getLabels().getString("s2037"), _editable);
				}
			
		}
	}
}
