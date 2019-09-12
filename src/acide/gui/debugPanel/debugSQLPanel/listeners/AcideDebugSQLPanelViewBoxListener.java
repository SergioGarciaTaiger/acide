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

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;

import javax.swing.JComboBox;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;

import acide.gui.databasePanel.utils.AcideTree;
import acide.gui.debugPanel.debugCanvas.tasks.AcideDebugCanvasParseTask;
import acide.gui.debugPanel.debugSQLPanel.AcideDebugSQLPanel;
import acide.gui.debugPanel.utils.TreeUtils;
import acide.gui.graphCanvas.tasks.AcideGraphCanvasParseTask;
import acide.gui.mainWindow.AcideMainWindow;
import acide.process.console.DesDatabaseManager;

/**
 * 
 * ACIDE - A Configurable IDE trace SQL panel view box listener.
 * 
 * @version 0.15
 * @see ActionListener
 */
public class AcideDebugSQLPanelViewBoxListener implements ActionListener {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent ev) {
		@SuppressWarnings("unchecked")
		JComboBox cb = (JComboBox) ev.getSource();
		if (cb.getSelectedIndex() < 1)
			return;
		// Gets the label of the selected item
		String query = (String) cb.getSelectedItem();

		if (query != null) {
			//enable the refresh button
			AcideDebugSQLPanel.refreshSQL.setEnabled(true);
			// Updates the query
			AcideMainWindow.getInstance().getDebugPanel().getDebugSQLPanel()
					.setQuery(query);
			// Gets the trace SQL output for the trace sql query
			LinkedList<String> l = DesDatabaseManager.getInstance()
					.executeCommand("/tapi /trace_sql " + query);
			String result = "";
			for (String s : l) {
				result += s + "\n";
			}
			// Parses the output and generates the path grap (modifiaction 0.17 with /rdg)
			new Thread(new AcideDebugCanvasParseTask(result,
					AcideGraphCanvasParseTask.PARSE_TAPI_RDG, AcideMainWindow
							.getInstance().getDebugPanel().getDebugSQLPanel()
							.getCanvas(),
					AcideDebugCanvasParseTask.DESTINY_PATH,query,false)).start();

			// Gets the PDG output for the query
			l = DesDatabaseManager.getInstance().executeCommand(
					"/tapi /rdg " + query);
			result = "";
			for (String s : l) {
				result += s + "\n";
			}
			// Parses the result and generates the graph
			final Thread t = new Thread(new AcideDebugCanvasParseTask(result,
					AcideGraphCanvasParseTask.PARSE_TAPI_RDG, AcideMainWindow
							.getInstance().getDebugPanel().getDebugSQLPanel()
							.getCanvas(),
					AcideDebugCanvasParseTask.DESTINY_MAIN,query,false));
			t.start();
			if (AcideMainWindow.getInstance().getDebugPanel()
					.getDebugSQLPanel().getShowSQLMenuItem().isSelected()) {
				new Thread(new Runnable() {
					/*
					 * (non-Javadoc)
					 * 
					 * @see java.lang.Runnable#run()
					 */
					@Override
					public void run() {
						try {
							t.join();
							// gets the label of the root node
							String query = AcideMainWindow.getInstance()
									.getDebugPanel().getDebugSQLPanel()
									.getCanvas().getRootNode().getLabel();
							// gets the / separator
							if(query.indexOf('/')>=0)
								query = query.substring(0, query.lastIndexOf("/"));
							// gets the tree of the database panel
							AcideTree tree = AcideMainWindow.getInstance()
									.getDataBasePanel().getTree();
							// searches for the root node on the tree
							TreePath path = TreeUtils
									.searchForNode((TreeNode) tree.getModel()
											.getRoot(), query);
							// selects the node
							if (path != null)
								tree.setSelectionPath(path);
						} catch (Exception e) {
							
						}

					}
				}).start();
			}

		}

	}

}
