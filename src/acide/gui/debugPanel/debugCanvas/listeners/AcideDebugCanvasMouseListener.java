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
		if (ev.getClickCount() >= 2) {
			// Gets the graph of the canvas
			DirectedWeightedGraph graph = _canvas.get_graph();
			// Gets the nodes of the graph
			ArrayList<Node> nodes = graph.get_nodes();
			boolean changed = false;
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
					changed = _canvas.setSelectedNode(n);
					// Repaints the graph
					_canvas.repaint();
					// Gets the selected node name
					String selected = n.getLabel();
					// Gets the highlighter
					AcideDebugPanelHighLighter highLighter = AcideMainWindow
							.getInstance().getDebugPanel()
							.getTraceDatalogPanel().getHighLighter();
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
						LinkedList<String> databases = AcideDatabaseManager
								.getInstance().getDatabases();
						boolean found = false;
						String query = _canvas.getSelectedNode().getLabel();
						query = query.substring(0, query.lastIndexOf("/"));

						String database = null;
						String view = null;
						String table = null;
						boolean viewFound = false;
						boolean tableFound = false;

						for (int i = 0; i < databases.size() && !found; i++) {
							database = databases.get(i);
							// Searches on the databases if the selected node
							// corresponds to a view of the database
							LinkedList<String> views = AcideDatabaseManager
									.getInstance().getViews(database);
							for (int j = 0; j < views.size() && !found; j++) {
								view = views.get(j);
								found = view.startsWith(query);
							}
							if (found)
								viewFound = true;
							// Searches on the databases if the selected node
							// corresponds to a table of the database
							LinkedList<String> tables = AcideDatabaseManager
									.getInstance().getTables(database);
							for (int j = 0; j < tables.size() && !found; j++) {
								table = tables.get(j);
								found = table.startsWith(query);
							}
							if (found && !viewFound)
								tableFound = true;
						}

					}
				}
			}
			// If the selected node has changed
			if (changed) {
				AcideMainWindow
						.getInstance()
						.getDebugPanel()
						.setCursor(
								Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				// Checks if the selected tab is the trace datalog panel
				if (AcideMainWindow.getInstance().getDebugPanel()
						.getTraceDatalogPanelIndex() == AcideMainWindow
						.getInstance().getDebugPanel().getTabbedPane()
						.getSelectedIndex()) {
					// Gets the open databases
					LinkedList<String> databases = AcideDatabaseManager
							.getInstance().getDatabases();
					boolean found = false;
					String query = _canvas.getSelectedNode().getLabel();

					String database = null;
					String table = null;
					// Searches on the databases if the selected node
					// corresponds to a table of the database
					for (int i = 0; i < databases.size() && !found; i++) {
						database = databases.get(i);
						LinkedList<String> tables = AcideDatabaseManager
								.getInstance().getTables(database);
						for (int j = 0; j < tables.size() && !found; j++) {
							table = tables.get(j);
							String name = query.substring(0,
									query.lastIndexOf("/"));
							String arity = query.substring(query
									.lastIndexOf("/") + 1);
							if (table.startsWith(name)) {
								found |= table.split("\\x2C").length == Integer
										.parseInt(arity);
							}
						}
					}
					if (found) {
						// open a new dataview dialog with the data of the
						// selected node
						AcideMainWindow
								.getInstance()
								.getDataBasePanel()
								.getDataView(
										database,
										query.substring(0,
												query.lastIndexOf("/")));
					}else{
						// creates the data view
						AcideTraceDatalogDataView dataview = new AcideTraceDatalogDataView();
						// gets the name of the datalog predicate
						String name = query.substring(0,
								query.lastIndexOf("/"));
						// gets the arity of the datalog predicate
						int arity = Integer.parseInt(query.substring(query.lastIndexOf("/")+1));
						// creates the instruction to send
						String instruction = "/tapi ";
						// adds the name to the instruction
						instruction += name+"(";
						// adds the variables to the instruction
						for(int i=0; i<arity;i++){
							if(i+1<arity)
								instruction+="X"+i+",";
							else
								instruction+="X"+i;							
						}
						// ends the instruction
						instruction+=")";
						// sends the instuction to the database manager
						LinkedList<String> result = AcideDatabaseManager.getInstance().executeCommand(instruction);
						if(!iserror(result)){
							// gets the /list_et result
							result = AcideDatabaseManager.getInstance().executeCommand("/tapi /list_et "+query);
							// parses the result
							LinkedList<String> data = parseListEt(result,name);
							// sets the name to the data view
							dataview.set_name(name);
							dataview.setIsReadOnly(true);
							// sets the columns of the data view
							dataview.setTotalColumns(arity);	
							// builds the data view with the list_et data
							dataview.build(data);
							// sets the title of the data view
							dataview.setTitle(name+dataview.getTitle());
						}
					}
				}
				// Checks if the selected tab is the trace SQL panel
				if (AcideMainWindow.getInstance().getDebugPanel()
						.getTraceSQLPanelIndex() == AcideMainWindow
						.getInstance().getDebugPanel().getTabbedPane()
						.getSelectedIndex()
						&& AcideMainWindow.getInstance().getDebugPanel()
								.getTraceSQLPanel().getShowSQLMenuItem()
								.isSelected()) {
					// Gets the open databases
					LinkedList<String> databases = AcideDatabaseManager
							.getInstance().getDatabases();
					// creates the found flag
					boolean found = false;
					// gets the query from the selected node
					String query = _canvas.getSelectedNode().getLabel();
					query = query.substring(0, query.lastIndexOf("/"));

					String database = null;
					String view = null;
					String table = null;
					boolean viewFound = false;
					boolean tableFound = false;

					for (int i = 0; i < databases.size() && !found; i++) {
						database = databases.get(i);
						// Searches on the databases if the selected node
						// corresponds to a view of the database
						LinkedList<String> views = AcideDatabaseManager
								.getInstance().getViews(database);
						for (int j = 0; j < views.size() && !found; j++) {
							view = views.get(j);
							found = view.startsWith(query);
						}
						if (found)
							viewFound = true;
						// Searches on the databases if the selected node
						// corresponds to a table of the database
						LinkedList<String> tables = AcideDatabaseManager
								.getInstance().getTables(database);
						for (int j = 0; j < tables.size() && !found; j++) {
							table = tables.get(j);
							found = table.startsWith(query);
						}
						if (found && !viewFound)
							tableFound = true;
					}
					// open a new dataview dialog with the data of the
					// selected node
					if (found) {
						if (viewFound) {
							AcideMainWindow.getInstance().getDataBasePanel()
									.getDataView(database, query);
						}
						if (tableFound) {
							AcideMainWindow.getInstance().getDataBasePanel()
									.getDataView(database, query);
						}
					}
					AcideDebugHelper.selectSQLTEXT(query);

				}
				// updates the cursor
				AcideMainWindow.getInstance().getDebugPanel()
						.setCursor(Cursor.getDefaultCursor());
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
