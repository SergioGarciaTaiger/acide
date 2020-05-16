/*
 * ACIDE - A Configurable IDE
 * Official web site: http://acide.sourceforge.net
 * 
 * Copyright (C) 2007-2013  
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
 *      -Version 0.17
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
package acide.gui.debugPanel.debugCanvas;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Stroke;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelListener;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.JSpinner;
import javax.swing.event.ChangeListener;

import acide.gui.databasePanel.dataView.menuBar.editMenu.gui.AcideDataViewReplaceWindow;
import acide.gui.debugPanel.debugCanvas.exceptions.AcideDebugCanvasParseInputEqualsErrorException;
import acide.gui.debugPanel.debugCanvas.listeners.AcideDebugCanvasMouseListener;
import acide.gui.debugPanel.debugCanvas.listeners.AcideDebugCanvasMouseMotionListener;
import acide.gui.debugPanel.debugCanvas.listeners.AcideDebugCanvasMouseWheelListener;
import acide.gui.debugPanel.debugSQLPanel.AcideDebugSQLPanel;
import acide.gui.debugPanel.traceDatalogPanel.AcideTraceDatalogPanel;
import acide.gui.debugPanel.traceSQLPanel.AcideTraceSQLPanel;
import acide.gui.graphCanvas.AcideGraphCanvas;
import acide.gui.graphLayout.TreeLayout;
import acide.gui.graphUtils.DirectedWeightedGraph;
import acide.gui.graphUtils.DirectedWeightedLink;
import acide.gui.graphUtils.Node;
import acide.gui.mainWindow.AcideMainWindow;
import acide.resources.AcideResourceManager;
import acide.resources.exception.MissedPropertyException;

/**
 * ACIDE - A Configurable IDE explorer panel.
 * 
 * @version 0.15
 * @see AcideGraphCanvas
 */
public class AcideDebugCanvas extends AcideGraphCanvas {

	/**
	 * ACIDE - A Configurable IDE debug canvas class serial version UID.
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * ACIDE - A Configurable IDE debug canvas selected node.
	 */
	private Node _selectedNode;
	/**
	 * ACIDE - A Configurable IDE debug canvas root node.
	 */
	private Node _rootNode;
	/**
	 * ACIDE - A Configurable IDE debug canvas selected node color.
	 */
	private Color _selectedTraceNodeColor = Color.GREEN;
	/**
	 * ACIDE - A Configurable IDE debug canvas path's graph.
	 */
	private ArrayList<Node> _pathGraph;
	/**
	 * ACIDE - A Configurable IDE debug canvas path's graph index.
	 */
	private int _pathGraphindex;
	/**
	 * ACIDE - A Configurable IDE debug canvas flag to enable the draw.
	 */
	private boolean _enableDraw;

	// public MyPopUpMenu _popUp = new MyPopUpMenu();

	/**
	 * Creates a new ACIDE - A Configurable IDE debug canvas.
	 */
	public AcideDebugCanvas() {
		super();
		_enableDraw = true;
		// Removes the mouse listeners of the super class
		for (MouseListener listener : this.getMouseListeners())
			this.removeMouseListener(listener);

		this.addMouseListener(new AcideDebugCanvasMouseListener(this));

		// Removes the mouse motion listeners of the super class
		for (MouseMotionListener listener : this.getMouseMotionListeners())
			this.removeMouseMotionListener(listener);

		this.addMouseMotionListener(new AcideDebugCanvasMouseMotionListener(
				this));
		// Removes the mouse wheel listeners of the super class
		for (MouseWheelListener listener : this.getMouseWheelListeners())
			this.removeMouseWheelListener(listener);
		// Adds the mouse wheel listener
		this.addMouseWheelListener(new AcideDebugCanvasMouseWheelListener(this));

		try {
			// gets the size of the nodes
			this._nodeSize = Integer.parseInt(AcideResourceManager
					.getInstance().getProperty("debugPanel.nodeSize"));
		} catch (NumberFormatException e) {

		} catch (MissedPropertyException e) {
			// sets the default size of the nodes
			this._nodeSize = 20;
			// saves the size of the nodes
			AcideResourceManager.getInstance().setProperty(
					"debugPanel.nodeSize", Integer.toString(this._nodeSize));
		}
	}

	/**
	 * 
	 * Creates a new ACIDE - A Configurable IDE graph panel.
	 * 
	 * @param width
	 *            the width of the canvas,
	 * @param height
	 *            the height of the canvas.
	 */
	public AcideDebugCanvas(int width, int height) {
		super(width, height);

		_enableDraw = true;
		// Removes the mouse listeners of the super class
		for (MouseListener listener : this.getMouseListeners())
			this.removeMouseListener(listener);

		this.addMouseListener(new AcideDebugCanvasMouseListener(this));

		// Removes the mouse motion listeners of the super class
		for (MouseMotionListener listener : this.getMouseMotionListeners())
			this.removeMouseMotionListener(listener);

		this.addMouseMotionListener(new AcideDebugCanvasMouseMotionListener(
				this));
		// Removes the mouse wheel listeners of the super class
		for (MouseWheelListener listener : this.getMouseWheelListeners())
			this.removeMouseWheelListener(listener);
		// Adds the mouse wheel listener
		this.addMouseWheelListener(new AcideDebugCanvasMouseWheelListener(this));

		try {
			// gets the size of the nodes
			this._nodeSize = Integer.parseInt(AcideResourceManager
					.getInstance().getProperty("debugPanel.nodeSize"));
		} catch (NumberFormatException e) {

		} catch (MissedPropertyException e) {
			// sets the default size of the nodes
			this._nodeSize = 20;
			// saves the size of the nodes
			AcideResourceManager.getInstance().setProperty(
					"debugPanel.nodeSize", Integer.toString(this._nodeSize));
		}
	}

	/**
	 * Returns the ACIDE - A Configurable IDE debug canvas graph.
	 * 
	 * @return the ACIDE - A Configurable IDE debug canvas graph.
	 */
	public DirectedWeightedGraph get_graph() {
		return _graph;
	}

	/**
	 * 
	 * Sets a new value to the ACIDE - A Configurable IDE graph canvas graph.
	 * 
	 * @param _graph
	 *            the new value to set.
	 */
	public void set_graph(DirectedWeightedGraph _graph) {
		this._zoom = 1;
		this._graph = _graph;
		_enableDraw = false;
		Node rootCandiate = null;
		// Checks the what of the nodes of the graph matches to the root node
		// of the path's graph
		for (int i = 0; i < _graph.get_nodes().size(); i++) {
			if (i == 0)
				rootCandiate = _graph.get_nodes().get(0);
			if (_rootNode.getLabel().equals(
					_graph.get_nodes().get(i).getLabel())
					|| _graph.get_nodes().get(i).getLabel()
							.matches(_rootNode.getLabel() + "(/\\p{Digit}+)*"))
				rootCandiate = _graph.get_nodes().get(i);
		}
		// Marks the root candidate as the selected node
		_selectedNode = rootCandiate;
		// Aplies the layout
		this._layout = new TreeLayout(_selectedNode, TreeLayout.INVERSE_MODE);
		_layout.calculateNodesPosition(_graph, getWidth(), getHeight(),
				_nodeSize);
		_enableDraw = true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see acide.gui.graphCanvas.AcideGraphCanvas#paint(java.awt.Graphics)
	 */
	@Override
	public void paint(Graphics gorig) {
		if(_width <= 1 || _height <= 1){
			_width = 1024;
			_height = 728;
		}
		// creates a new image with the dimension of the canvas
		Image image = new BufferedImage(_width, _height,
				BufferedImage.TYPE_INT_ARGB);
		// gets the image graphics
		Graphics g = image.getGraphics();
		// fill the graphics whit white color
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, _width, _height);
		// checks if the graph exist
		if (_graph == null)
			return;
		if (_enableDraw) {
			// gets the links list of the graph
			ArrayList<DirectedWeightedLink> links = _graph.get_links();
			for (int i = 0; i < links.size(); i++) {
				// gets the origin an the destiny coordinates of the link
				DirectedWeightedLink link = links.get(i);
				int xOrig = (int) link.getOrigin().getX();
				int yOrig = (int) link.getOrigin().getY();
				int xDest = (int) link.getDestiny().getX();
				int yDest = (int) link.getDestiny().getY();
				// saves the default stroke of the graphics
				Stroke trazo = ((Graphics2D) g).getStroke();
				// checks the weight of the link and set the color and the shape
				// to
				// draw the line
				if (link.getWeight() >= 0) {
					g.setColor(_linkColor1);
					((Graphics2D) g).setStroke(new BasicStroke(_strokeSize));
				} else {
					g.setColor(_linkColor2);
					float dash[] = { 5 };
					((Graphics2D) g).setStroke(new BasicStroke(_strokeSize,
							BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 5.0f,
							dash, 0.0f));
				}
				// draws a line from the origin to the destiny of the link
				g.drawLine(xOrig + (int) (_nodeSize * _zoom / 2), yOrig
						+ (int) (_nodeSize * _zoom / 2), xDest
						+ (int) (_nodeSize * _zoom / 2), yDest
						+ (int) (_nodeSize * _zoom / 2));
				// restarts the stroke value
				((Graphics2D) g).setStroke(trazo);
				// draws the tip of the link
				drawTriangle(link, g.getColor(), g);

			}
			// gets the nodes of the graph
			ArrayList<Node> nodes = _graph.get_nodes();
			// sets the font to the labels
			Font f = g.getFont().deriveFont(Font.LAYOUT_NO_LIMIT_CONTEXT);
			f = f.deriveFont(f.getSize() * (float) _zoom);
			g.setFont(f);
			for (int i = 0; i < nodes.size(); i++) {
				Node n = nodes.get(i);
				// saves the default stroke
				Stroke trazo = ((Graphics2D) g).getStroke();
				if (n.getNodeColor().equals(Color.WHITE)) { // adaptation 0.17 - debug SQL
					n.setNodeColor(_nodeColor);
					g.setColor(n.getNodeColor());
				} else
					g.setColor(n.getNodeColor());
				if (n.equals(_selectedNode) && _selectedNode.getNodeColor().equals(Color.GRAY))
					g.setColor(_selectedTraceNodeColor);
				/*
				 * else g.setColor(_nodeColor);
				 */

				// draws the node
				if (_nodeShape == NODE_CIRCLE)
					g.fillOval((int) n.getX(), (int) n.getY(),
							(int) (_nodeSize * _zoom),
							(int) (_nodeSize * _zoom));
				if (_nodeShape == NODE_SQUARE)
					g.fillRect((int) n.getX(), (int) n.getY(),
							(int) (_nodeSize * _zoom),
							(int) (_nodeSize * _zoom));
				// draws the label of the node
				if (_showingLabels) {
					Rectangle2D rect = g.getFontMetrics().getStringBounds(
							n.getLabel(), g);
					g.setColor(Color.WHITE);
					g.fillRect(
							(int) n.getX()
									- (int) ((rect.getWidth() / 2 - (_nodeSize * _zoom) / 2)),
							(int) n.getY() - (5 + (int) rect.getHeight()),
							(int) rect.getWidth(), (int) rect.getHeight());
					g.setColor(Color.BLACK);
					g.drawString(
							n.getLabel(),
							(int) n.getX()
									- (int) ((rect.getWidth() / 2 - (_nodeSize * _zoom) / 2)),
							(int) n.getY() - 5);
				}
				// draws the node border
				g.setColor(Color.BLACK);
				((Graphics2D) g).setStroke(new BasicStroke(_strokeSize));
				if (_nodeShape == NODE_CIRCLE)
					g.drawOval((int) n.getX(), (int) n.getY(),
							(int) (_nodeSize * _zoom),
							(int) (_nodeSize * _zoom));
				if (_nodeShape == NODE_SQUARE)
					g.drawRect((int) n.getX(), (int) n.getY(),
							(int) (_nodeSize * _zoom),
							(int) (_nodeSize * _zoom));
				((Graphics2D) g).setStroke(trazo);
			}
		}
		// draws the image with the graph in to the canvas graphics
		gorig.drawImage(image, 0, 0, _width, _height, null);
	}

	/**
	 * Returns the ACIDE - A Configurable IDE debug canvas root node.
	 * 
	 * @return the ACIDE - A Configurable IDE debug canvas root node.
	 */
	public Node getRootNode() {
		return _rootNode;
	}

	/**
	 * Sets a new value to the ACIDE - A Configurable IDE debug canvas root
	 * node.
	 * 
	 * @param rootNode
	 *            the new value to set
	 */
	public void setRootNode(Node rootNode) {
		this._rootNode = rootNode;
	}

	/**
	 * Returns the ACIDE - A Configurable IDE debug canvas selected node.
	 * 
	 * @return the ACIDE - A Configurable IDE debug canvas selected node.
	 */
	public Node getSelectedNode() {
		return _selectedNode;
	}

	private void updateSelectedNode(Node selectedNode, int selectedNodeIndex) {
		setSelectedNode(selectedNode);
		_pathGraphindex = selectedNodeIndex;
	}

	/**
	 * Sets a new value to the ACIDE - A Configurable IDE debug canvas selected
	 * node.
	 * 
	 * @param selectedNode
	 *            the new value to set
	 * @return true if the selected node has changed, false otherwise.
	 */
	public boolean setSelectedNode(Node selectedNode) {
		boolean found = false;
		// Checks if the selected node matches with any node of the path's graph
		for (int i = 0; i < _pathGraph.size() && !found; i++)
			if (selectedNode.getLabel().equals(_pathGraph.get(i).getLabel())
					|| selectedNode.getLabel().matches(
							_pathGraph.get(i).getLabel() + "(/\\p{Digit}+)*")) {
				_pathGraphindex = i;
				found = true;
			}
		if (found) {
			// Searches for the selected node on the graph
			for (int i = 0; i < _graph.get_nodes().size(); i++)
				if (selectedNode.getLabel().equals(
						_graph.get_nodes().get(i).getLabel())
						|| _graph
								.get_nodes()
								.get(i)
								.getLabel()
								.matches(
										selectedNode.getLabel()
												+ "(/\\p{Digit}+)*"))
					// Updates the selected node
					this._selectedNode = _graph.get_nodes().get(i);
			// Updates the nodes on the asserted database panel
			if (AcideMainWindow.getInstance().isAssertedDatabaseOpened()) {
				if (AcideMainWindow.getInstance().getAssertedDatabasePanel()
						.getNumberOfPredicatesCheckBox().isSelected()) {
					AcideMainWindow.getInstance().getAssertedDatabasePanel()
							.updateSelectedNode();
				}
			}
		}
		return found;
	}

	/**
	 * Advances the position of the the ACIDE - A Configurable IDE debug canvas
	 * selected node.
	 */
	public void advanceSelectedNode() {

		if (_pathGraphindex + 1 < _pathGraph.size() && _pathGraph.size() > 0) {
			_pathGraphindex++;
			updateSelectedNode(_pathGraph.get(_pathGraphindex), _pathGraphindex);
		}

	}

	/**
	 * Retards the position of the the ACIDE - A Configurable IDE debug canvas
	 * selected node.
	 */
	public void retardSelectedNode() {

		if (_pathGraphindex > 0 && _pathGraph.size() > 0) {
			_pathGraphindex--;
			updateSelectedNode(_pathGraph.get(_pathGraphindex), _pathGraphindex);
		}
	}

	/**
	 * Returns the ACIDE - A Configurable IDE debug canvas selected node color.
	 * 
	 * @return the ACIDE - A Configurable IDE debug canvas selected node color.
	 */
	public Color getSelectedNodeColor() {
		return _selectedTraceNodeColor;
	}

	/**
	 * Sets a new value to the the ACIDE - A Configurable IDE debug canvas
	 * selected node color.
	 * 
	 * @param selectedNodeColor
	 *            the new value to selected node color
	 */
	public void setSelectedNodeColor(Color selectedNodeColor) {
		this._selectedTraceNodeColor = selectedNodeColor;
	}

	/**
	 * Sets a new node color to the the ACIDE - A Configurable IDE debug canvas
	 * selected node color.
	 * 
	 * @param colorNode
	 *            the new color to the node
	 */
	public void setColorSelectedNode(Color colorNode) {
		getSelectedNode().setNodeColor(colorNode);
	}

	/**
	 * Sets a new node color to the the ACIDE - A Configurable IDE debug canvas
	 * selected node color.
	 * 
	 * @param colorNode
	 *            the new color to all the nodes
	 */
	public void setColorNodes(Color colorNode) {
		// gets the nodes of the graph
		ArrayList<Node> nodes = _graph.get_nodes();
		for (int i = 0; i < nodes.size(); i++) {
			Node n = nodes.get(i);
			n.setNodeColor(colorNode);
		}
	}
	
	/**
	 * Returns the ACIDE - A Configurable IDE debug canvas path graph.
	 * 
	 * @return the ACIDE - A Configurable IDE debug canvas path graph.
	 */
	public ArrayList<Node> getPathGraph() {
		return _pathGraph;
	}

	/**
	 * Sets a new value to the the ACIDE - A Configurable IDE debug canvas path
	 * graph.
	 * 
	 * @param pathGraph
	 *            the new value to set.
	 */
	public void setPathGraph(ArrayList<Node> pathGraph) {
		// Updates the path graph
		this._pathGraph = pathGraph;
		// Sets a position for the nodes
		for (int i = 0; i < pathGraph.size(); i++) {
			if (i == 0)
				setRootNode(_pathGraph.get(i));
			Node n = pathGraph.get(i);
			n.setX(_nodeSize * i);
			n.setY(_nodeSize * i);
			n.setPosicionado(true);
		}
		_pathGraphindex = 0;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see acide.gui.graphCanvas.AcideGraphCanvas#setZoom(double)
	 */
	@Override
	public void setZoom(double zoom, CanvasPanel panel) {
		this._zoom = zoom;
		JSpinner s;
		switch (panel) {
		case DebugSQL:
			s = AcideDebugSQLPanel.getZoomSpinner();

			break;
		case TraceData:
			s = AcideTraceDatalogPanel.getZoomSpinner();

			break;
		case TraceSQL:
			s = AcideTraceSQLPanel.getZoomSpinner();

			break;
		default:
			s = AcideMainWindow.getInstance().getGraphPanel().getZoomSpinner();
			break;
		}

		ChangeListener cl = s.getChangeListeners()[0];
		s.removeChangeListener(cl);
		s.setValue((int) (zoom * 100));
		s.addChangeListener(cl);
	}

	public ArrayList<Node> get_pathGraph() {
		return _pathGraph;
	}

	public void set_pathGraph(ArrayList<Node> _pathGraph) {
		this._pathGraph = _pathGraph;
	}

	public int get_pathGraphindex() {
		return _pathGraphindex;
	}

	public void set_pathGraphindex(int _pathGraphindex) {
		this._pathGraphindex = _pathGraphindex;
	}

	/**
	 * Sets the new value to the ACIDE - A Configurable IDE debug canvas node
	 * size.
	 * 
	 * @param nodeSize
	 *            the new value to set.
	 */
	public void setNodeSize(int nodeSize) {
		this._nodeSize = nodeSize;
		AcideResourceManager.getInstance().setProperty("debugPanel.nodeSize",
				Integer.toString(this._nodeSize));
	}

	/**
	 * Parses the ACIDE - A Configurable IDE graph canvas from a input stream
	 * generated by the instruction /tapi /trace_datalog Query.
	 * 
	 * @param input
	 *            input stream to parse.
	 * @return the graph generated by parsing the input stream.
	 * @throws AcideDebugCanvasParseInputEqualsErrorException
	 */
	public static ArrayList<Node> parsePathGraphTapi(InputStream input)
			throws AcideDebugCanvasParseInputEqualsErrorException {
		// creates a new graph
		ArrayList<Node> g = new ArrayList<>();

		// creates the scanner with the input
		Scanner reader = new Scanner(input);
		String line = "";
		int countNodes = 0;

		// Puts the wait cursor
		AcideDataViewReplaceWindow.getInstance().setCursor(
				Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));

		try {
			// reads the next line
			while (reader.hasNextLine()
					&& !((line = reader.nextLine().replaceAll("\\p{Space}", ""))
							.equals("$eot"))
					&& !((line.replaceAll("\\p{Space}", "")).equals("$error"))) {

				if (!line.contains("$")) {

					// creates a new node
					Node n = new Node();
					// set the label
					n.setLabel(line);
					// sets the positioned label
					n.setPosicionado(false);

					g.add(n);
				} else
					throw new Exception(
							"wrong entry. Couldn't be able to add a new node");
			}

		} catch (Exception e) {

			// resets the graph
			g = new ArrayList<Node>();
		}
		// checks if the line is equals to "$error"
		if (line.replaceAll("\\p{Space}", "").equals("$error")) {
			// generates an empty graph
			g = new ArrayList<Node>();
			String errorMsg = "";

			// reads the output to get the error message from the console
			while (reader.hasNextLine()
					&& !((line = reader.nextLine())
							.replaceAll("\\p{Space}", "").equals("$eot"))) {
				if (!line.replaceAll("\\p{Space}", "").matches("\\d+")) {
					errorMsg += line;
				}
			}
			// Closes the reader
			reader.close();
			// thros a new Acide Debug Canvas Parse Input Equals Error Exception
			// with the message
			throw new AcideDebugCanvasParseInputEqualsErrorException(errorMsg);
		}
		// Closes the reader
		reader.close();

		// Puts the default cursor
		AcideDataViewReplaceWindow.getInstance().setCursor(
				Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));

		// returns the graph
		return g;
	}

}
