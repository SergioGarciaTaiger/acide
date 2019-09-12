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
package acide.gui.debugPanel.utils;

import java.util.Enumeration;

import javax.swing.JTree;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;

/**
 *
 * ACIDE - A Configurable IDE tree utils.
 *
 * @version 0.16
 *
 */
public class TreeUtils {

	/**
	 * @param root The root node of the tree.
	 * @param query the the label of the target node.
	 * @return the path from the root node to the selected node if exists, null otherwise.
	 */
	public static TreePath searchForNode(TreeNode node, String query) {
		//Searches for the target node at the level
		if (node.toString().startsWith(query))
			return new TreePath(node);
		if (node.isLeaf())
			return null;
		Enumeration<TreeNode> e = (Enumeration<TreeNode>)node.children();
		// Searches for the target node on the children of the node 
		while (e.hasMoreElements()) {
			TreePath path = searchForNode(e.nextElement(), query);
			if (path != null) {
				TreePath parentPath = new TreePath(node);
				for (int i = 0; i < path.getPathCount(); i++)
					parentPath = parentPath.pathByAddingChild(path
							.getPathComponent(i));
				return parentPath;
			}
		}
		return null;
	}
	
	public static TreePath searchForNodeV2(TreeNode node, String query,JTree tree, TreePath actualPath, boolean isRoot) {
		//Searches for the target node at the level
		if (node.toString().startsWith(query))
			return actualPath.pathByAddingChild(node);
		if(node.isLeaf()){
			return null;
		}
		Enumeration<TreeNode> e = (Enumeration<TreeNode>)node.children();
		if(!e.hasMoreElements()){
			TreePath tempPath = actualPath.pathByAddingChild(node);
			tree.setSelectionPath(tempPath);
			e = (Enumeration<TreeNode>)node.children();
		}
		// Searches for the target node on the children of the node 
		while (e.hasMoreElements()) {
			TreePath path =  null;
			if(isRoot)
				path = searchForNodeV2(e.nextElement(), query,tree,actualPath,false);
			else
				path = searchForNodeV2(e.nextElement(), query,tree,actualPath.pathByAddingChild(node),false);
			if (path != null) {				
				return path;
			}
		}
		return null;
	}
	
}
