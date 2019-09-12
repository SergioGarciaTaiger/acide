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

import java.util.Iterator;
import java.util.Vector;

import javax.swing.JPopupMenu;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

import acide.gui.databasePanel.Nodes.NodeICT.tipoRestriccion;
import acide.gui.databasePanel.popup.AcideDataBasePanelTablePopupMenu;
import acide.language.AcideLanguageManager;
import acide.process.console.AcideDatabaseManager;

/**
 * ACIDE - A Configurable IDE nodetable.
 * 
 * @version 0.16
 * @see DefaultMutableTreeNode
 */
public class NodeTable extends AcideDataBaseNodes {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public NodeTable(String text,DefaultTreeModel _treeModel) {
		super(text,_treeModel);
	}
	
	@Override
	public boolean update() {
		boolean result = false;
		Vector<String> childs = getChilds();
		if(isBuilt()){
			String s = toString();
			if( s.contains("("))
				s = s.substring(0, s.indexOf("("));
			
			Vector<Boolean> inforest = AcideDatabaseManager.getInstance().infoRestrictions(getParent().getParent().toString(), s );				
						
			//añadimos el nodo de PK
			if(inforest.get(1) && !contains(childs,"PK")){
				NodeICT pk = new NodeICT(AcideLanguageManager.getInstance().getLabels()
						.getString("s2031"),tipoRestriccion.PK,_treeModel);
				pk.setAllowsChildren(false);
				_treeModel.insertNodeInto(pk,this,getChildCount());
				result = true;
			} else if (!inforest.get(1) && contains(childs,"PK")){
						_treeModel.removeNodeFromParent(getChildIC("PK"));
					}
			
			//añadimos el  nodo de FK
			if(inforest.get(3)&& !contains(childs,"FK")){
				NodeICT fk = new NodeICT(AcideLanguageManager.getInstance().getLabels()
						.getString("s2032"),tipoRestriccion.FK,_treeModel);
				fk.setAllowsChildren(false);
				_treeModel.insertNodeInto(fk,this,getChildCount());
				result = true;
			}	else if (!inforest.get(3) && contains(childs,"FK")){
				_treeModel.removeNodeFromParent(getChildIC("FK"));
			}
			
			if(inforest.get(2)&& !contains(childs,"CK")){
				//añadimos el  nodo de CK
				NodeICT ck = new NodeICT(AcideLanguageManager.getInstance().getLabels()
						.getString("s2033"),tipoRestriccion.CK,_treeModel);
				ck.setAllowsChildren(false);
				_treeModel.insertNodeInto(ck,this,getChildCount());
				result = true;
			} else if (!inforest.get(2) && contains(childs,"CK")){
				_treeModel.removeNodeFromParent(getChildIC("CK"));
			}
			
			if(inforest.get(4)&& !contains(childs,"FD")){
				//añadimos el nodo de FD 
				NodeICT fd = new NodeICT(AcideLanguageManager.getInstance().getLabels()
						.getString("s2034"),tipoRestriccion.FD,_treeModel);
				fd.setAllowsChildren(false);
				_treeModel.insertNodeInto(fd,this,getChildCount());
				result = true;
			} else if (!inforest.get(4) && contains(childs,"FD")){
				_treeModel.removeNodeFromParent(getChildIC("FD"));
			}
			
			if(inforest.get(5)&& !contains(childs,"IC")){
				//añadimos el nodo de IC
				NodeICT ic = new NodeICT(AcideLanguageManager.getInstance().getLabels()
						.getString("s2035"),tipoRestriccion.IC,_treeModel);
				ic.setAllowsChildren(false);
				_treeModel.insertNodeInto(ic,this,getChildCount());	
				result = true;
			} else if (!inforest.get(5) && contains(childs,"IC")){
				_treeModel.removeNodeFromParent(getChildIC("IC"));
			}
			
			if(inforest.get(0)&& !contains(childs,"NN")){
				//añadimos el nodo de NL
				NodeICT nl = new NodeICT(AcideLanguageManager.getInstance().getLabels()
						.getString("s2109"), tipoRestriccion.NN,_treeModel);
				nl.setAllowsChildren(false);
				_treeModel.insertNodeInto(nl,this,getChildCount());
				result = true;
			} else if (!inforest.get(0) && contains(childs,"NN")){
				_treeModel.removeNodeFromParent(getChildIC("NN"));
			}
		}
		else ((DefaultTreeModel) _treeModel).nodeChanged(this);
		return result;
	}


	private boolean contains(Vector<String> tables, String child) {
		Iterator<?> it = tables.iterator();
		boolean found=false;
		while(it.hasNext() && !found){
			String table = (String) it.next();
			if(table.contains("("))
				table = table.substring(0,table.indexOf("("));
						
			found = table.equals(child);
		}
		return found;
	}
	
	@Override
	public JPopupMenu getPopUp() {
		return new AcideDataBasePanelTablePopupMenu();
	}
	
}
