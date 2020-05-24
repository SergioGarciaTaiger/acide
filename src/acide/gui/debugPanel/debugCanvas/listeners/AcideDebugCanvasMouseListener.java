/*
 * ACIDE - A Configurable IDE
 * Official web site: http://acide.sourceforge.net
 * 
 * Copyright (C) 2007-2014
 * Authors:
 * 		- Fernando S�enz P�rez (Team Director).
 *      - Version from 0.1 to 0.6:
 *      	- Diego Cardiel Freire.
 *			- Juan Jos� Ortiz S�nchez.
 *          - Delf�n Rup�rez Ca�as.
 *      - Version 0.7:
 *          - Miguel Mart�n L�zaro.
 *      - Version 0.8:
 *      	- Javier Salcedo G�mez.
 *      - Version from 0.9 to 0.11:
 *      	- Pablo Guti�rrez Garc�a-Pardo.
 *      	- Elena Tejeiro P�rez de �greda.
 *      	- Andr�s Vicente del Cura.
 *      - Version from 0.12 to 0.16
 *      	- Sem�ramis Guti�rrez Quintana
 *      	- Juan Jes�s Marqu�s Ortiz
 *      	- Fernando Ord�s Lorente
 *      - Version 0.17
 *      	- Sergio Dom�nguez Fuentes
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
package acide.gui.debugPanel.debugCanvas.listeners;

import java.awt.Cursor;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.LinkedList;

import acide.gui.debugPanel.debugCanvas.AcideDebugCanvas;
import acide.gui.debugPanel.debugCanvas.traceDatalogDataView.AcideTraceDatalogDataView;
import acide.gui.debugPanel.utils.AcideDebugHelper;
import acide.gui.debugPanel.utils.AcideDebugPanelHighLighter;
import acide.gui.graphUtils.DirectedWeightedGraph;
import acide.gui.graphUtils.Node;
import acide.gui.mainWindow.AcideMainWindow;
import acide.process.console.AcideDatabaseManager;

/**
 * 
 * ACIDE - A Configurable IDE debug canvas mouse listener.
 * 
 * @version 0.15
 * @see MouseAdapter
 */
public class AcideDebugCanvasMouseListener extends MouseAdapter {

	/**
	 * ACIDE - A Configurable IDE debug canvas mouse listener target canvas.
	 */
	private AcideDebugCanvas _canvas;

	/**
	 * 
	 * Creates a new ACIDE - A Configurable IDE debug canvas mouse listener.
	 * 
	 * @param canvas
	 *            the target canvas.
	 */
	public AcideDebugCanvasMouseListener(AcideDebugCanvas canvas) {
		this._canvas = canvas;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.awt.event.MouseAdapter#mouseClicked(java.awt.event.MouseEvent)
	 */
	@Override
	public void mouseClicked(MouseEvent ev) {
		try{
			// Checks the number of clicks
			if (ev.getClickCount() == 1) {
				// Gets the graph of the canvas
				DirectedWeightedGraph graph = _canvas.get_graph();
				// Gets the nodes of the graph
				ArrayList<Node> nodes = graph.get_nodes();
				// Searches if a node has been clicked
				for (Node n : nodes) {
					if (ev.getX() >= n.getX()
							&& ev.getX() <= n.getX()
									+ (int) (_canvas.getNodeSize() * _canvas
											.getZoom())
							&& ev.getY() >= n.getY()
							&& ev.getY() <= n.getY()
									+ (int) (_canvas.getNodeSize() * _canvas
											.getZoom())) {
						// Updates the selected node
						_canvas.setSelectedNode(n);
						// Repaints the graph
						_canvas.repaint();
						// Gets the selected node name
						String selected = n.getLabel();
						// Gets the highlighter
						AcideDebugPanelHighLighter highLighter = AcideMainWindow
								.getInstance().getDebugPanel()
								.getTraceDatalogPanel().getHighLighter();
						// Checks if the asserted database panel is open
						if (AcideMainWindow.getInstance()
								.isAssertedDatabaseOpened()) {
							// Updates the selected node on the asserted database
							// panel
							AcideMainWindow.getInstance()
									.getAssertedDatabasePanel().setSelectedNode(n);
							if (AcideMainWindow.getInstance()
									.getAssertedDatabasePanel()
									.getNumberOfPredicatesCheckBox().isSelected())
								AcideMainWindow.getInstance()
										.getAssertedDatabasePanel()
										.updateSelectedNode();
						}
						// Resets the highlights
						highLighter.resetLines();
						highLighter.unHighLight();
						// Highlights the lines corresponding to the new selected
						// node
						highLighter.highLight(selected);
						if (AcideMainWindow.getInstance().getDebugPanel()
								.getTraceSQLPanelIndex() == AcideMainWindow
								.getInstance().getDebugPanel().getTabbedPane()
								.getSelectedIndex()
								&& AcideMainWindow.getInstance().getDebugPanel()
										.getTraceSQLPanel().getShowSQLMenuItem()
										.isSelected()) {
							AcideMainWindow.getInstance().getDebugPanel()
							.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
							String query = _canvas.getSelectedNode().getLabel();
							query = query.substring(0, query.lastIndexOf("/"));
							AcideDebugHelper.selectSQLTEXT(query);
							AcideMainWindow.getInstance().getDebugPanel()
							.setCursor(Cursor.getDefaultCursor());
						}
					}
				}
			}
			// Checks the number of clicks
			if(ev.getClickCount() >= 2) {
				String view;
				if(_canvas.getSelectedNode().getLabel().contains("/")) {
					view = _canvas.getSelectedNode().getLabel().split("/")[0];
					AcideDebugHelper.showView(view);
				}
			}
		}catch(Exception ex){
			
			AcideMainWindow.getInstance().getDebugPanel()
			.setCursor(Cursor.getDefaultCursor());
		}

	}

	/**
	 * Checks if the result of a command send to the database manager is an error result
	 * @param result the result of the command
	 * @return true if the result is error, false otherwise
	 */
	private boolean iserror(LinkedList<String> result) {
		for(int i=0;i<result.size();i++)
			if(result.get(i).replaceAll("\\s", "").equals("$error"))
				return true;
		return false;
	}

	/**
	 * Parses the result of the /tapi /list_et 'query' command.
	 * @param input the result of the /tapi /lis_et command.
	 * @return the answers of the /tapi /list_et command
	 */
	private LinkedList<String> parseListEt(LinkedList<String> input,String command) {
		LinkedList<String> result = new LinkedList<String>();
		boolean answers = false;
		for(int i = 0; i<input.size(); i++){
			String s = input.get(i);
			// if error returns null
			if(s.equals("$error"))
				return null;
			// updates the answers flag
			if(s.equals("$answers")){
				answers = true;				
				continue;
			}
			// updates the answers flag
			if(s.equals("$calls")){
				answers = false;				
				continue;
			}
			if(answers){
				if(s.equals(command) )
					continue;			
				// adds the line to the result
				result.add(s);
			}
			
		}
		return result;
	}
}
