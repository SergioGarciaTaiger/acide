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
package acide.gui.databasePanel.Nodes;

import java.util.LinkedList;
import java.util.Vector;

import javax.swing.JPopupMenu;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

import acide.gui.databasePanel.AcideDataBasePanel;
import acide.gui.databasePanel.popup.AcideDataBasePanelViewsPopupMenu;
import acide.gui.mainWindow.AcideMainWindow;
import acide.language.AcideLanguageManager;
import acide.process.console.AcideDatabaseManager;
/**
 * ACIDE - A Configurable IDE node views.
 * 
 * @version 0.16
 * @see DefaultMutableTreeNode
 */
public class NodeViews extends AcideDataBaseNodes {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public NodeViews(String text,DefaultTreeModel _treeModel) {
		super(text,_treeModel);
	}

	@Override
	public boolean update() {
		this.setUserObject(AcideLanguageManager.getInstance().getLabels().getString("s2027"));	
		if(isBuilt()){
			setAllowsChildren(false);
			AcideDataBasePanel panel = AcideMainWindow.getInstance().getDataBasePanel();
			
			LinkedList<String> vistas = AcideDatabaseManager.getInstance().getViews(getParent().toString());
				
			if (AcideDatabaseManager.getInstance().currentDB().equals("$des")){
			
				Vector<String> cleanViews = clearList(vistas);
				Vector<String> cleanTypeViews = clearOnlyTypes(vistas);
				
				setAllowsChildren(true);
				
				for(int j=0; j< cleanViews.size(); j++){
	
					NodeView tableN = null; 
					if (panel.isNameTables()) tableN=new NodeView(cleanViews.get(j),_treeModel);
					else if (panel.isNameFieldsTables()) tableN=new NodeView(cleanTypeViews.get(j),_treeModel);
						else tableN = new NodeView(vistas.get(j),_treeModel);
					tableN.setAllowsChildren(false);
					_treeModel.insertNodeInto(tableN, this, this.getChildCount());
				}
			} else{
					setAllowsChildren(true);
				
					for(int j=0; j< vistas.size(); j++){
	
						NodeView tableN = new NodeView(vistas.get(j),_treeModel);
						tableN.setAllowsChildren(false);
						_treeModel.insertNodeInto(tableN, this, this.getChildCount());
					}
			}
			
			_treeModel.nodeStructureChanged(this);
		}		
		return true;
	}

	private Vector<String> clearList(LinkedList<String> tables) {
		Vector<String> result = new  Vector<String>();
		for(int childId = 0; childId < tables.size(); childId++){
			String childS = tables.get(childId);		
			if(childS.contains("("))
				childS = childS.substring(0,childS.indexOf("("));
			result.add(childS);
		}
		return result;
	}
	
	private Vector<String> clearOnlyTypes(LinkedList<String> views) {
		Vector<String> result = new  Vector<String>();
		for(int childId = 0; childId < views.size(); childId++){
			String childClear = "";
			String[] child = views.get(childId).split(",");
			for(int i=0;i<child.length;i++){
				if (i==(child.length-1)) childClear = childClear + child[i].substring(0, child[i].indexOf(":"))+")";
				else childClear = childClear + child[i].substring(0, child[i].indexOf(":"))+",";
			}
			result.add(childClear);
			
		}
		return result;
	}
	
	@Override
	public JPopupMenu getPopUp() {
		return  AcideDataBasePanelViewsPopupMenu.getInstance();
	}
}
