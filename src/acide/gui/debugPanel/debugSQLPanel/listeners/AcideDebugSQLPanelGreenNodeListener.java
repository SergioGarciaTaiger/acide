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
package acide.gui.debugPanel.debugSQLPanel.listeners;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Enumeration;

import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;

import acide.gui.databasePanel.utils.AcideTree;
import acide.gui.debugPanel.debugCanvas.AcideDebugCanvas;
import acide.gui.debugPanel.utils.AcideDebugPanelHighLighter;
import acide.gui.debugPanel.utils.TreeUtils;
import acide.gui.mainWindow.AcideMainWindow;
import acide.language.AcideLanguageManager;

/**
 * ACIDE - A Configurable IDE trace SQL color node listener.
 * 
 * @see AcitonListener
 * @version 0.17
 * 
 */
public class AcideDebugSQLPanelGreenNodeListener implements ActionListener {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		try {
			// Gets the canvas
			AcideDebugCanvas canvas = AcideMainWindow.getInstance()
					.getDebugPanel().getDebugSQLPanel().getCanvas();
			
			// Updates the selected node
			canvas.setColorSelectedNode(Color.GREEN);
			canvas.repaint();
			String selected = canvas.getSelectedNode().getLabel();
			
			// Updates the highlights
			AcideDebugPanelHighLighter highLighter = AcideMainWindow
					.getInstance().getDebugPanel().getDebugSQLPanel()
					.getHighLighter();
			highLighter.resetLines();
			highLighter.unHighLight();
			highLighter.highLight(selected);
			if (AcideMainWindow.getInstance().getDebugPanel()
					.getDebugSQLPanel().getShowSQLMenuItem().isSelected()) {
				AcideMainWindow
						.getInstance()
						.getDebugPanel()
						.setCursor(
								Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				// Selects the node on the database panel tree
				String query = canvas.getSelectedNode().getLabel();
				query = query.substring(0, query.lastIndexOf("/"));
				selectSQLTEXT(query);
				AcideMainWindow.getInstance().getDebugPanel()
						.setCursor(Cursor.getDefaultCursor());
			}
		} catch (Exception ex) {
			
			AcideMainWindow.getInstance().getDebugPanel()
					.setCursor(Cursor.getDefaultCursor());
		}

	}

	private void selectSQLTEXT(String query) {
		AcideTree tree = AcideMainWindow.getInstance().getDataBasePanel()
				.getTree();
		// Searches for the table/view node on the database tree
		TreePath path = TreeUtils.searchForNodeV2((TreeNode) tree.getModel()
				.getRoot(), query, tree, new TreePath((TreeNode) tree
				.getModel().getRoot()), true);
		if (path != null) {
			TreeNode node = (TreeNode) path.getLastPathComponent();
			tree.setSelectionPath(path);
			// Searches for the child node SQL Text on the children of the node
			// (only for views)
			Enumeration<TreeNode> e = node.children();
			while (e.hasMoreElements()) {
				TreeNode node2 = e.nextElement();
				if (node2.toString().equals(
						AcideLanguageManager.getInstance().getLabels()
								.getString("s2036"))) {
					path = path.pathByAddingChild(node2);
					// Adds the child node of the SQL Text node with the sql
					// definition of the view
					if (!node2.isLeaf()) {
						tree.setSelectionPath(path);
						path = path.pathByAddingChild(node2.getChildAt(0));
					}
				}
			}
			tree.setSelectionPath(path);
		}
	}

}
