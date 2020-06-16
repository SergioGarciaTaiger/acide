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
package acide.gui.graphCanvas;

import java.awt.BasicStroke;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Stroke;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import javax.swing.JSpinner;
import javax.swing.event.ChangeListener;

import acide.gui.databasePanel.dataView.menuBar.editMenu.gui.AcideDataViewReplaceWindow;
import acide.gui.debugPanel.debugSQLPanel.AcideDebugSQLPanel;
import acide.gui.debugPanel.traceDatalogPanel.AcideTraceDatalogPanel;
import acide.gui.debugPanel.traceSQLPanel.AcideTraceSQLPanel;
import acide.gui.graphCanvas.listeners.AchideGraphCanvasMouseListener;
import acide.gui.graphCanvas.listeners.AcideGraphCanvasMouseMotionListener;
import acide.gui.graphCanvas.listeners.AcideGraphCanvasMouseWheelListener;
import acide.gui.graphLayout.DHLayout;
import acide.gui.graphLayout.GraphLayout;
import acide.gui.graphLayout.TreeLayout;
import acide.gui.graphUtils.DirectedWeightedGraph;
import acide.gui.graphUtils.DirectedWeightedLink;
import acide.gui.graphUtils.Node;
import acide.gui.mainWindow.AcideMainWindow;
import acide.resources.AcideResourceManager;
import acide.resources.exception.MissedPropertyException;

/**
 * ACIDE - A Configurable IDE graph canvas.
 * 
 * @version 0.12
 * @see {@link #Canvas}.
 */
public class AcideGraphCanvas extends Canvas {

	/**
	 * ACIDE - A Configurable IDE graph canvas class serial version UID.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * ACIDE - A Configurable IDE graph canvas class node circle shape value.
	 */
	public static final int NODE_CIRCLE = 0;
	/**
	 * ACIDE - A Configurable IDE graph canvas class node square shape value.
	 */
	public static final int NODE_SQUARE = 1;
	/**
	 * ACIDE - A Configurable IDE graph canvas class arrow line shape value.
	 */
	public static final int ARROW_LINE = 0;
	/**
	 * ACIDE - A Configurable IDE graph canvas class node polygon shape value.
	 */
	public static final int ARROW_POLYGON = 1;
	/**
	 * ACIDE - A Configurable IDE graph canvas graph to display.
	 */
	protected DirectedWeightedGraph _graph;
	/**
	 * ACIDE - A Configurable IDE graph canvas width of the canvas.
	 */
	protected int _width = 400;
	/**
	 * ACIDE - A Configurable IDE graph canvas height of the canvas.
	 */
	protected int _height = 600;
	/**
	 * ACIDE - A Configurable IDE graph canvas x coordinate of the center.
	 */
	private int X0 = _width / 2;
	/**
	 * ACIDE - A Configurable IDE graph canvas y coordinate of the center.
	 */
	private int Y0 = _height / 2;
	/**
	 * ACIDE - A Configurable IDE graph canvas zoom of the canvas.
	 */
	protected double _zoom = 1;
	/**
	 * ACIDE - A Configurable IDE graph canvas long of the links to be
	 * displayed.
	 */
	private int _linkLong = 100;
	/**
	 * ACIDE - A Configurable IDE graph canvas size of the nodes to be
	 * displayed.
	 */
	protected int _nodeSize = 20;
	/**
	 * ACIDE - A Configurable IDE graph canvas size of the stroke.
	 */
	protected final int _strokeSize = 2;
	/**
	 * ACIDE - A Configurable IDE graph canvas rise of the zoom.
	 */
	private float _inc = (float) 1.2;
	/**
	 * ACIDE - A Configurable IDE graph canvas layout of the graph.
	 */
	protected GraphLayout _layout;
	/**
	 * ACIDE - A Configurable IDE graph canvas shape of the node.
	 */
	protected int _nodeShape;
	/**
	 * ACIDE - A Configurable IDE graph canvas shape of the arrow tip.
	 */
	private int _arrowShape = ARROW_POLYGON;
	/**
	 * ACIDE - A Configurable IDE graph canvas color of the node.
	 */
	protected Color _nodeColor = Color.GRAY;
	/**
	 * ACIDE - A Configurable IDE graph canvas color of positive arrows.
	 */
	protected Color _linkColor1 = Color.BLUE;
	/**
	 * ACIDE - A Configurable IDE graph canvas color of negative arrows.
	 */
	protected Color _linkColor2 = Color.RED;
	/**
	 * ACIDE - A Configurable IDE graph canvas sets if the labels of the nodes
	 * are showed.
	 */
	protected boolean _showingLabels = true;
	/**
	 * ACIDE - A Configurable IDE graph canvas class instance.
	 */
	private static AcideGraphCanvas _instance;

	public enum CanvasPanel { TraceData, TraceSQL, DebugSQL, Graph }
	
	/**
	 * 
	 * @return the instance of ACIDE - A Configurable IDE graph canvas.
	 */
	public static AcideGraphCanvas getInstance() {
		if (_instance == null)
			_instance = new AcideGraphCanvas();
		return _instance;
	}

	/**
	 * Creates a new ACIDE - A Configurable IDE graph canvas.
	 */
	public AcideGraphCanvas() {
		// creates the canvas
		super();
		// sets the size of the canvas
		this.setSize(_width, _height);
		// gets the center of the canvas
		X0 = _width / 2;
		Y0 = _height / 2;
		// adds the listeners of the canvas
		this.addMouseMotionListener(new AcideGraphCanvasMouseMotionListener());
		this.addMouseWheelListener(new AcideGraphCanvasMouseWheelListener());
		this.addMouseListener(new AchideGraphCanvasMouseListener(this));
		// sets the layout
		this._layout = new DHLayout();
		// creates a new graph
		this._graph = new DirectedWeightedGraph();
		try {
			// gets the size of the nodes
			this._nodeSize = Integer.parseInt(AcideResourceManager
					.getInstance().getProperty("graphPanel.nodeSize"));
		} catch (NumberFormatException e) {

		} catch (MissedPropertyException e) {
			// sets the default size of the nodes
			this._nodeSize = 20;
			// saves the size of the nodes
			AcideResourceManager.getInstance().setProperty(
					"graphPanel.nodeSize", Integer.toString(this._nodeSize));
		}
	}

	/**
	 * Creates a new ACIDE - A Configurable IDE graph canvas.
	 * 
	 * @param width
	 *            width of the canvas.
	 * @param height
	 *            height of the canvas.
	 */
	public AcideGraphCanvas(int width, int height) {
		// creates the canvas
		super();
		// sets the size of the canvas
		this._width = width;
		this._height = height;
		// gets the center of the canvas
		this.setSize(width, height);
		this.X0 = this._width / 2;
		this.Y0 = this._height / 2;
		// adds the listeners of the canvas
		this.addMouseMotionListener(new AcideGraphCanvasMouseMotionListener());
		this.addMouseWheelListener(new AcideGraphCanvasMouseWheelListener());
		// sets the layout
		this._layout = new DHLayout();
		// creates a new graph
		this._graph = new DirectedWeightedGraph();
		try {
			// gets the size of the nodes
			this._nodeSize = Integer.parseInt(AcideResourceManager
					.getInstance().getProperty("graphPanel.nodeSize"));
		} catch (NumberFormatException e) {

		} catch (MissedPropertyException e) {
			// sets the default size of the nodes
			this._nodeSize = 20;
			// saves the size of the nodes
			AcideResourceManager.getInstance().setProperty(
					"graphPanel.nodeSize", Integer.toString(this._nodeSize));
		}
	}

	/**
	 * Returns the ACIDE - A Configurable IDE graph canvas graph.
	 * 
	 * @return the ACIDE - A Configurable IDE graph canvas graph.
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
		this.setZoom(1, CanvasPanel.Graph);
		this._graph = _graph;
		Node root = null;
		int numElemGraph = this._graph.get_nodes().size();

		if (numElemGraph > 0) {
			int[] outgoingLinks = new int[numElemGraph];
			int[] incomingLinks = new int[numElemGraph];
			DirectedWeightedLink link;
			Node nodeOrigin;
			Node nodeDestiny;

			for (int i = 0; i < _graph.get_links().size(); i++) {
				link = _graph.get_links().get(i);
				nodeOrigin = link.getOrigin();
				nodeDestiny = link.getDestiny();

				if (!nodeOrigin.equals(nodeDestiny)) {
					outgoingLinks[_graph.get_nodes().indexOf(nodeOrigin)]++;
					incomingLinks[_graph.get_nodes().indexOf(nodeDestiny)]++;
				}
			}

			// We want to select the node with less incoming connections and
			// more outgoing connections
			int rootIndex = 0;
			for (int i = 1; i < numElemGraph; i++) {
				// Less i incoming -> rootIndex = i;
				if (incomingLinks[i] < incomingLinks[rootIndex])
					rootIndex = i;
				// Same i incoming & more i outgoing -> rootIndex = i;
				else if (incomingLinks[i] == incomingLinks[rootIndex]
						&& outgoingLinks[i] > outgoingLinks[rootIndex])
					rootIndex = i;
			}
			root = _graph.get_nodes().get(rootIndex);

			// Aplies the layout
			this._layout = new TreeLayout(root, TreeLayout.INVERSE_MODE);
			// updates the nodes distribution
			_layout.calculateNodesPosition(this._graph, this._width,
					this._height, this._nodeSize);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.awt.Component#setBounds(int, int, int, int)
	 */
	@Override
	public void setBounds(int x, int y, int width, int height) {
		super.setBounds(x, y, width, height);
		if (X0 == width / 2 && Y0 == height / 2) {
			this._width = width;
			this._height = height;
			// X0 = width / 2;
			// Y0 = height / 2;
		} else {
			this._width = width;
			this._height = height;
			X0 = width / 2;
			Y0 = height / 2;

		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.awt.Component#setBounds(java.awt.Rectangle)
	 */
	@Override
	public void setBounds(Rectangle r) {
		this.setBounds(r.x, r.y, r.width, r.height);
		// X0 = r.width / 2;
		// Y0 = r.height / 2;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.awt.Canvas#paint(java.awt.Graphics)
	 */
	@Override
	public void paint(Graphics gorig) {
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
			// checks the weight of the link and set the color and the shape to
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
			g.setColor(_nodeColor);
			// draws the node
			if (_nodeShape == NODE_CIRCLE)
				g.fillOval((int) n.getX(), (int) n.getY(),
						(int) (_nodeSize * _zoom), (int) (_nodeSize * _zoom));
			if (_nodeShape == NODE_SQUARE)
				g.fillRect((int) n.getX(), (int) n.getY(),
						(int) (_nodeSize * _zoom), (int) (_nodeSize * _zoom));
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
						(int) (_nodeSize * _zoom), (int) (_nodeSize * _zoom));
			if (_nodeShape == NODE_SQUARE)
				g.drawRect((int) n.getX(), (int) n.getY(),
						(int) (_nodeSize * _zoom), (int) (_nodeSize * _zoom));
			((Graphics2D) g).setStroke(trazo);
		}
		// draws the image with the graph in to the canvas graphics
		gorig.drawImage(image, 0, 0, _width, _height, null);
	}

	/**
	 * 
	 * Draws the ACIDE - A Configurable IDE graph canvas arrowhead of the link.
	 * 
	 * @param link
	 *            to owns the arrowhead.
	 * @param color
	 *            of the arrowhead.
	 * @param g
	 *            Graphics where the arrowhead will be draw.
	 */
	protected void drawTriangle(DirectedWeightedLink link, Color color,
			Graphics g) {
		double ang = 0.0, angSep = 0.0;
		double tx, ty;
		int dist = 0;
		Point point1 = null, point2 = null;

		// calculates the point to calculate the angle between the origin and
		// the end of the arc
		point1 = new Point((int) link.getOrigin().getX() + _nodeSize / 2,
				(int) link.getOrigin().getY() + _nodeSize / 2);
		int xd = (int) link.getDestiny().getX() + _nodeSize / 2;
		int yd = (int) link.getDestiny().getY() + _nodeSize / 2;
		point2 = new Point(xd, yd);
		if (link.getOrigin().getX() == link.getDestiny().getX()
				&& link.getOrigin().getY() == link.getDestiny().getY()) {
			point1 = new Point((int) link.getOrigin().getX() - _nodeSize * 2,
					(int) link.getOrigin().getY() + _nodeSize * 3);
			point2 = new Point((int) link.getOrigin().getX(), (int) link
					.getOrigin().getY() + _nodeSize);
		}
		xd += _nodeSize * _zoom / 2;

		dist = 15;

		ty = -(point1.y - point2.y);
		tx = (point1.x - point2.x);

		ang = Math.atan(ty / tx);

		if (tx < 0) {
			ang += Math.PI;
		}

		// calculates the three points of the tip
		Point p1 = new Point(), p2 = new Point(), poitn = point2;
		angSep = 25.0;

		poitn.x = (int) ((link.getDestiny().getX() + _nodeSize * _zoom / 2) + Math
				.cos(ang) * (_nodeSize * _zoom / 2));
		poitn.y = (int) ((link.getDestiny().getY() + _nodeSize * _zoom / 2) - Math
				.sin(ang) * (_nodeSize * _zoom / 2));

		p1.x = (int) (poitn.x + dist * _zoom
				* Math.cos(ang - Math.toRadians(angSep)));
		p1.y = (int) (poitn.y - dist * _zoom
				* Math.sin(ang - Math.toRadians(angSep)));
		p2.x = (int) (poitn.x + dist * _zoom
				* Math.cos(ang + Math.toRadians(angSep)));
		p2.y = (int) (poitn.y - dist * _zoom
				* Math.sin(ang + Math.toRadians(angSep)));

		Graphics2D g2D = (Graphics2D) g;

		// draws the tip of the arc
		g.setColor(color);
		g2D.setStroke(new BasicStroke(_strokeSize));
		if (_arrowShape == ARROW_LINE) {
			g.drawLine(p1.x, p1.y, (int) (poitn.x), (int) (poitn.y));
			g.drawLine(p2.x, p2.y, (int) (poitn.x), (int) (poitn.y));
		}
		if (_arrowShape == ARROW_POLYGON) {
			int[] x = { poitn.x, p1.x, p2.x };
			int[] y = { poitn.y, p1.y, p2.y };
			g.fillPolygon(x, y, 3);

		}
		if (link.getOrigin().getX() == link.getDestiny().getX()
				&& link.getOrigin().getY() == link.getDestiny().getY())
			g.drawOval((int) (p1.x + 8 * _zoom), (int) (p1.y - 6 * _zoom),
					(int) (poitn.x + 10 * _zoom - p1.x), (int) (poitn.y + 30
							* _zoom - p1.y));
	}

	/**
	 * Calculates the ACIDE - A Configurable IDE graph canvas graph x coordinate
	 * of the node i of the graph.
	 * 
	 * @param i
	 *            position of the node in the graph.
	 * @param rads
	 *            angle of separation between nodes.
	 * @return the ACIDE - A Configurable IDE graph canvas graph x coordinate of
	 *         the node i of the graph.
	 */
	protected int caluclaXNodo(int i, double rads) {
		return Math
				.max(0,
						Math.min(
								i != 0 ? (int) (X0 + _linkLong
										* Math.cos(i * rads))
										: (int) (X0 + _linkLong
												* Math.cos(0.1 * rads)), _width));
	}

	/**
	 * Calculates the ACIDE - A Configurable IDE graph canvas graph y coordinate
	 * of the node i of the graph.
	 * 
	 * @param i
	 *            position of the node in the graph.
	 * @param rads
	 *            angle of separation between nodes.
	 * @return the ACIDE - A Configurable IDE graph canvas graph y coordinate of
	 *         the node i of the graph.
	 */
	protected int caluclaYNodo(int i, double rads) {
		return Math.max(0, Math.min(
				i != 0 ? (int) (Y0 + _linkLong * Math.sin(i * rads))
						: (int) (Y0 + _linkLong * Math.cos(0.1 * rads)),
				_height));
	}

	/**
	 * Increases the zoom of the ACIDE - A Configurable IDE graph canvas graph.
	 * @param canvasPanel TODO
	 */
	public void zoomIn(CanvasPanel panel) {
		ArrayList<Node> nodes = _graph.get_nodes();
		this.setZoom(_zoom * _inc, panel );
		for (Node n : nodes) {
			float difx = n.getX() - X0;
			float dify = n.getY() - Y0;
			difx = difx * _inc;
			dify = dify * _inc;
			n.setX(X0 + difx);
			n.setY(Y0 + dify);
		}
	}

	/**
	 * Decreases the zoom of the ACIDE - A Configurable IDE graph canvas graph.
	 * @param panel TODO
	 */
	public void zoomOut(CanvasPanel panel) {
		ArrayList<Node> nodes = _graph.get_nodes();

		this.setZoom(_zoom / _inc, panel);
		for (Node n : nodes) {
			float difx = n.getX() - X0;
			float dify = n.getY() - Y0;
			difx = difx / _inc;
			dify = dify / _inc;
			n.setX(X0 + difx);
			n.setY(Y0 + dify);
		}
	}

	/**
	 * Moves the nodes of the ACIDE - A Configurable IDE graph canvas graph.
	 * 
	 * @param x
	 *            the horizontal shift of the nodes.
	 * @param y
	 *            the vertical shift of the nodes.
	 */
	public void moveGraph(int x, int y) {
		ArrayList<Node> nodes = _graph.get_nodes();
		for (Node n : nodes) {
			n.setX(n.getX() + x);
			n.setY(n.getY() + y);
		}
	}

	/**
	 * Returns the nodes of the ACIDE - A Configurable IDE graph canvas node
	 * size.
	 * 
	 * @return the nodes of the ACIDE - A Configurable IDE graph canvas node
	 *         size.
	 */
	public int getNodeSize() {
		return _nodeSize;
	}

	/**
	 * Returns the nodes of the ACIDE - A Configurable IDE graph canvas zoom.
	 * 
	 * @return the nodes of the ACIDE - A Configurable IDE graph canvas zoom.
	 */
	public double getZoom() {
		return _zoom;
	}

	/**
	 * Sets a new value to the ACIDE - A Configurable IDE graph canvas zoom.
	 * 
	 * @param zoom
	 *            new value to set.
	 * @param canvasPanel TODO
	 */
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
		case Graph:
			s = AcideMainWindow.getInstance().getGraphPanel().getZoomSpinner();
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

	/**
	 * Returns the value of the ACIDE - A Configurable IDE graph canvas x
	 * coordinate of the center.
	 * 
	 * @return the value of the ACIDE - A Configurable IDE graph canvas x
	 *         coordinate of the center.
	 */
	public int getX0() {
		return X0;
	}

	/**
	 * Sets a new value to the ACIDE - A Configurable IDE graph canvas x
	 * coordinate of the center.
	 * 
	 * @param x0
	 *            the new value to set.
	 */
	public void setX0(int x0) {
		X0 = x0;
	}

	/**
	 * Returns the value of the ACIDE - A Configurable IDE graph canvas y
	 * coordinate of the center.
	 * 
	 * @return the value of the ACIDE - A Configurable IDE graph canvas y
	 *         coordinate of the center.
	 */
	public int getY0() {
		return Y0;
	}

	/**
	 * Sets a new value to the ACIDE - A Configurable IDE graph canvas y
	 * coordinate of the center.
	 * 
	 * @param x0
	 *            the new value to set.
	 */
	public void setY0(int y0) {
		Y0 = y0;
	}

	/**
	 * Returns the ACIDE - A Configurable IDE graph canvas layout.
	 * 
	 * @return the layout of the ACIDE - A Configurable IDE canvas.
	 */
	public GraphLayout getLayout() {
		return _layout;
	}

	/**
	 * Sets the new value to the ACIDE - A Configurable IDE graph canvas layout.
	 * 
	 * @param layout
	 *            the new value to set.
	 */
	public void setLayout(GraphLayout layout) {
		this._layout = layout;
	}

	/**
	 * Returns value of the ACIDE - A Configurable IDE graph canvas node shape.
	 * 
	 * @return the node shape of the ACIDE - A Configurable IDE canvas.
	 */
	public int getNodeShape() {
		return _nodeShape;
	}

	/**
	 * Sets the new value to the ACIDE - A Configurable IDE graph canvas node
	 * shape.
	 * 
	 * @param nodeShape
	 *            the new value to set.
	 */
	public void setNodeShape(int nodeShape) {
		this._nodeShape = nodeShape;
	}

	/**
	 * Returns the value of the ACIDE - A Configurable IDE graph canvas arrow
	 * shape.
	 * 
	 * @return the arrow shape of the ACIDE - A Configurable IDE canvas.
	 */
	public int getArrowShape() {
		return _arrowShape;
	}

	/**
	 * Sets the new value to the ACIDE - A Configurable IDE graph canvas arrow
	 * shape.
	 * 
	 * @param arrowShape
	 *            the new value to set.
	 */
	public void setArrowShape(int arrowShape) {
		this._arrowShape = arrowShape;
	}

	/**
	 * Returns the value of the ACIDE - A Configurable IDE graph canvas node
	 * color.
	 * 
	 * @return the node shape of the ACIDE - A Configurable IDE canvas.
	 */
	public Color getNodeColor() {
		return _nodeColor;
	}

	/**
	 * Sets the new value to the ACIDE - A Configurable IDE graph canvas node
	 * color.
	 * 
	 * @param nodeColor
	 *            the new value to set.
	 */
	public void setNodeColor(Color nodeColor) {
		this._nodeColor = nodeColor;
	}

	/**
	 * Returns the value of the ACIDE - A Configurable IDE graph canvas positive
	 * link color.
	 * 
	 * @return the positive link color of the ACIDE - A Configurable IDE canvas.
	 */
	public Color getLinkColor1() {
		return _linkColor1;
	}

	/**
	 * Sets the new value to the ACIDE - A Configurable IDE graph canvas
	 * positive link color.
	 * 
	 * @param linkColor1
	 *            the new value to set.
	 */
	public void setLinkColor1(Color linkColor1) {
		this._linkColor1 = linkColor1;
	}

	/**
	 * Returns the value of the ACIDE - A Configurable IDE graph canvas negative
	 * link color.
	 * 
	 * @return the negative link color of the ACIDE - A Configurable IDE canvas.
	 */
	public Color getLinkColor2() {
		return _linkColor2;
	}

	/**
	 * Sets the new value to the ACIDE - A Configurable IDE graph canvas
	 * negative link color.
	 * 
	 * @param linkColor2
	 *            the new value to set.
	 */
	public void setLinkColor2(Color linkColor2) {
		this._linkColor2 = linkColor2;
	}

	/**
	 * Sets the new value to the ACIDE - A Configurable IDE graph canvas node
	 * size.
	 * 
	 * @param nodeSize
	 *            the new value to set.
	 */
	public void setNodeSize(int nodeSize) {
		this._nodeSize = nodeSize;
		AcideResourceManager.getInstance().setProperty("graphPanel.nodeSize",
				Integer.toString(this._nodeSize));
	}

	/**
	 * Returns the value of the ACIDE - A Configurable IDE graph canvas showing
	 * labels.
	 * 
	 * @return the negative link color of the ACIDE - A Configurable IDE canvas.
	 */
	public boolean isShowingLabels() {
		return _showingLabels;
	}

	/**
	 * Sets the new value to the ACIDE - A Configurable IDE graph canvas showing
	 * labels.
	 * 
	 * @param showingLabels
	 *            the new value to set.
	 */
	public void setShowingLabels(boolean showingLabels) {
		this._showingLabels = showingLabels;
	}

	/**
	 * Parses the ACIDE - A Configurable IDE graph canvas from a input stream
	 * generated by the instruction /tapi /pdg.
	 * 
	 * @param input
	 *            input stream to parse.
	 * @return the {@link #DirectedWeightedGraph} generated by parsing the input
	 *         stream.
	 */
	public static DirectedWeightedGraph parseGraphTapi(InputStream input) {
		// creates a new graph
		DirectedWeightedGraph g = new DirectedWeightedGraph();
		DirectedWeightedLink link = null;
		// creates the list of links
		ArrayList<DirectedWeightedLink> links = new ArrayList<DirectedWeightedLink>();
		HashMap<String, Node> nodes = new HashMap<String, Node>();
		// creates the scanner with the input
		Scanner reader = new Scanner(input);
		String line = "";
		boolean parseLinks = false;

		// Puts the wait cursor
		AcideDataViewReplaceWindow.getInstance().setCursor(
				Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));

		try {
			// reads the next line
			while (reader.hasNextLine()
					&& !((line = reader.nextLine().replaceAll("\\p{Space}", ""))
							.equals("$eot"))
					&& !(line.replaceAll("\\p{Space}", "").equals("$error"))) {
				if (line.equals("$")) {
					parseLinks = true;
					continue;
				}
				// checks if the line is a + or a -
				if (parseLinks) {
					if (line.equals("+") || line.equals("-")) {
						if (link != null)
							throw new Exception(
									"entrada incorrecta. No se puede a�adir un nuevo arco");
						// creates a new link
						link = new DirectedWeightedLink();
						link.setOrigin(null);
						link.setDestiny(null);
						// sets the weight of the link
						if (line.equals("+"))
							link.setWheight(1);
						else
							link.setWheight(-1);
					} else {
						// creates a new node
						// gets the node of the graph
						Node n = nodes.get(line);
						// if the actual link destiny is null set the node as
						// destiny
						if (link.getDestiny() == null)
							link.setDestiny(n);
						// if the actual link origin is null set the node as
						// origin
						else if (link.getOrigin() == null) {
							link.setOrigin(n);

							links.add(link);
							link = null;
						}
					}

				} else {
					Node n = new Node();
					n.setLabel(line);
					n.setPosicionado(false);
					nodes.put(n.getLabel(), n);
				}
			}
			reader.close();
			// adds the nodes to the graph
			for (Node n : nodes.values())
				g.addNode(n);
			// adds the links to the graph
			for (DirectedWeightedLink l : links)
				g.addLink(l);

		} catch (Exception e) {
			e.printStackTrace();
			// resets the graph
			g = new DirectedWeightedGraph();
		} finally {
			// closes the reader
			reader.close();
		}

		// Puts the default cursor
		AcideDataViewReplaceWindow.getInstance().setCursor(
				Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));

		return g;
	}

	/**
	 * Parses the ACIDE - A Configurable IDE graph canvas from a input stream
	 * generated by the instruction /pdg.
	 * 
	 * @param input
	 *            input stream to parse.
	 * @return the {@link #DirectedWeightedGraph} generated by parsing the input
	 *         stream.
	 */
	public static DirectedWeightedGraph parseGraph(InputStream input) {
		// creates a new graph
		DirectedWeightedGraph g = new DirectedWeightedGraph();
		// creates the list of links
		ArrayList<DirectedWeightedLink> links = new ArrayList<DirectedWeightedLink>();
		// creates the map of nodes
		HashMap<String, Node> nodes = new HashMap<String, Node>();
		// creates the scanner with the input
		Scanner reader = new Scanner(input);
		String linea = "";
		try {
			// reads the next line
			while (reader.hasNextLine()
					&& !((linea = reader.nextLine()
							.replaceAll("\\p{Space}", "")).equals("$eot"))) {
				// checks if the line starts with nodes word
				if (linea.startsWith("Nodes:")) {
					// replaces the words that have no use for the parser
					linea = linea.replaceAll("Nodes:", "");
					linea = linea.replace("[", "");
					linea = linea.replace("]", "");
					// split the line to get the list of labels of the nodes
					String[] labels = linea.split(",");
					// crates a node for each label and adds it to the label
					for (int i = 0; i < labels.length; i++) {
						if (!labels[i].equals("")) {
							Node n = new Node();
							n.setLabel(labels[i]);
							n.setPosicionado(false);
							nodes.put(n.getLabel(), n);
						}
					}
				} else
				// checks if the line starts with arcs word
				if (linea.startsWith("Arcs:")) {
					// replaces the words that have no use for the parser
					linea = linea.replaceAll("Arcs:", "");
					linea = linea.replace("[", "");
					linea = linea.replace("]", "");
					// split the line to get the list of the links description
					String[] arcDesc = linea.split(",");
					int index = 0;
					for (int i = 0; i < arcDesc.length; i++) {
						DirectedWeightedLink l = new DirectedWeightedLink();
						// checks if the value of the link is positive or
						// negative and sets the weight
						if (arcDesc[i].contains("+")) {
							l.setWheight(1);
							index = arcDesc[i].indexOf("+");
						} else {
							l.setWheight(-1);
							index = arcDesc[i].indexOf("-");
						}
						// gets the labels of the nodes of the link, adds the
						// nodes to the link and adds the link
						// to the list
						if (index > 0) {
							l.setOrigin(nodes.get(arcDesc[i]
									.substring(index + 1)));
							l.setDestiny(nodes.get(arcDesc[i].substring(0,
									index)));
							links.add(l);
						}
					}
				}
			}
			// adds the nodes to the graph
			for (Node n : nodes.values())
				g.addNode(n);
			// adds the links to the graph
			for (DirectedWeightedLink l : links)
				g.addLink(l);
		} catch (Exception e) {
			e.printStackTrace();
			// resets the graph
			g = new DirectedWeightedGraph();
		} finally {
			// closes the reader
			reader.close();
		}
		return g;
	}

	/**
	 * Changes the new value to the ACIDE - A Configurable IDE graph canvas zoom
	 * 
	 * @param zoom
	 *            the new value to change.
	 */
	public void changeZoom(float zoom) {
		float prop = zoom / (float) this._zoom;
		ArrayList<Node> nodes = _graph.get_nodes();
		for (Node n : nodes) {
			float difx = n.getX() - X0;
			float dify = n.getY() - Y0;
			difx = difx * prop;
			dify = dify * prop;
			n.setX(X0 + difx);
			n.setY(Y0 + dify);
		}
		this._zoom = zoom;

	}

}