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

import java.util.Vector;

import javax.swing.JPopupMenu;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;

/**
 * ACIDE - A Configurable IDE data base nodes.
 * 
 * @version 0.16
 * @see DefaultMutableTreeNode
 */
public abstract class AcideDataBaseNodes extends DefaultMutableTreeNode {

	private static final long serialVersionUID = 1L;
	
	/**
	 * ACIDE - Indicates if the node is built  
	 */
	private boolean _isBuilt;
	
	/**
	 * ACIDE - Model of the tree  
	 */
	protected DefaultTreeModel _treeModel;
	
	
	public AcideDataBaseNodes(String text, DefaultTreeModel _treeModel){
		super(text);
		this._treeModel = _treeModel;
		
	}
	public boolean isBuilt() {
		return _isBuilt;
	}	
	
	public void setBuilt(boolean isBuilt) {
		this._isBuilt = isBuilt;
	}
	
	@Override
	public boolean isLeaf(){
		return false;
	}
		
	public Vector<String> getChilds(){
		Vector<String> childs = new Vector<String>();
		for(int childId = 0; childId < getChildCount(); childId++){
			TreeNode child = getChildAt(childId);
			String childS = child.toString();							
			childs.add(childS);
		}
		return childs;
	}
	
	public DefaultMutableTreeNode getChild(String child) {
		boolean found=false;
		int childNum=0;
		DefaultMutableTreeNode node=null;
		int childsCount = getChildCount() ;
		while( childNum < childsCount && !found){
			node = (DefaultMutableTreeNode) getChildAt(childNum);
			String nodeS = node.toString();
			if (nodeS.contains("("))
				found = nodeS.substring(0, nodeS.indexOf("(")).equals(child);
			else
				found = nodeS.equals(child);
			childNum++;
		}		
		return node;
	}	
	
	public DefaultMutableTreeNode getChildIC(String child) {
		boolean found=false;
		int childNum=0;
		DefaultMutableTreeNode node=null;
		int childsCount = getChildCount() ;
		while( childNum < childsCount && !found){
			node = (DefaultMutableTreeNode) getChildAt(childNum);
			String nodeS = node.toString();
			found = nodeS.equals(child);
			childNum++;
		}		
		return node;
	}	
	
	public boolean update(){return false;}
	
	public boolean update(boolean option){return false;};
	
	public JPopupMenu getPopUp(){return null;}

}
