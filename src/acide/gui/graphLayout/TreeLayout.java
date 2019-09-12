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
package acide.gui.graphLayout;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeSet;

import javax.swing.JTree;

import acide.gui.graphUtils.DirectedWeightedGraph;
import acide.gui.graphUtils.DirectedWeightedLink;
import acide.gui.graphUtils.Node;

public class TreeLayout implements GraphLayout {

	public static final String DIRECT_MODE = "direct";
	public static final String INVERSE_MODE = "inverse";

	private Node _root;
	private String _mode;
	/**
	 * ACIDE - A Configurable IDE tree layout margin between the nodes and the
	 * borders of the canvas.
	 */
	private final int MARGIN = 30;

	public TreeLayout(Node root, String mode) {
		this._root = root;
		this._mode = mode;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * acide.gui.graphLayout.GraphLayout#calculateNodesPosition(acide.gui.graphUtils
	 * .DirectedWeightedGraph, int, int, int)
	 */
	@Override
	public void calculateNodesPosition(DirectedWeightedGraph graph, int width,
			int height, int nodeSize) {
		if (graph.get_nodes().size() == 0)
			return;
		for (Node n : graph.get_nodes()) {
			n.setPosicionado(false);
			if (_root.getLabel().equals(n.getLabel()))
				_root = n;
		}

		int[] maxDepth = new int[1];
		maxDepth[0] = 0;
		int disp = setNodePosition(_root, graph, maxDepth, 0, 0);
		for (int i = 0; i < graph.get_nodes().size(); i++) {
			Node n = graph.get_nodes().get(i);
			if (!n.isPosicionado()) {
				int[] tempMaxDepth = new int[1];
				tempMaxDepth[0] = 0;
				disp = setNodePosition(n, graph, tempMaxDepth, 0, disp);
				if (maxDepth[0] < tempMaxDepth[0])
					maxDepth[0] = tempMaxDepth[0];
			}
		}
		int xdiv = (width - 2 * MARGIN) / disp;
		int ydiv = (height - 2 * MARGIN) / maxDepth[0];
		if (Math.min(xdiv, ydiv) * 0.5 < nodeSize) {
			xdiv = xdiv * (int) (2 * nodeSize / Math.min(xdiv, ydiv));
			ydiv = ydiv * (int) (2 * nodeSize / Math.min(xdiv, ydiv));
		}
		for (int i = 0; i < graph.get_nodes().size(); i++) {
			Node n = graph.get_nodes().get(i);
			n.setX(n.getX() * xdiv + MARGIN);
			n.setY(n.getY() * ydiv + MARGIN);

		}
	}

	/**
	 * 
	 * @param node
	 * @param graph
	 * @param maxDepth
	 * @param level
	 * @param disp
	 * @return
	 */
	private int setNodePosition(Node node, DirectedWeightedGraph graph,
			int[] maxDepth, int level, int disp) {
		if (node.isPosicionado())
			return disp;
		else {
			node.setPosicionado(true);
			if (maxDepth[0] < level)
				maxDepth[0] = level;
			ArrayList<DirectedWeightedLink> links = graph.get_links();
			ArrayList<Node> children = new ArrayList<Node>();
			if (!links.isEmpty()) {
				for (DirectedWeightedLink l : links) {
					if (l.getOrigin().equals(null))
						children.add(l.getDestiny());
					if (_mode.equals(INVERSE_MODE)
							&& l.getDestiny().equals(node)
							&& !l.getOrigin().equals(node)
							&& !l.getOrigin().isPosicionado())
						children.add(l.getOrigin());
					else if (_mode.equals(DIRECT_MODE)
							&& l.getOrigin().equals(node)
							&& !l.getDestiny().equals(node)
							&& !l.getDestiny().isPosicionado())
						children.add(l.getDestiny());
				}
			}
			if (children.size() == 0) {
				node.setX(disp);
				node.setY(level);
				return disp + 1;
			} else {
				int numChildren = children.size();
				if (numChildren % 2 == 0) {
					for (int i = 0; i < numChildren / 2; i++)
						disp = setNodePosition(children.get(i), graph,
								maxDepth, level + 1, disp);
					node.setX(disp);
					node.setY(level);
					disp++;
					for (int i = numChildren / 2; i < numChildren; i++)
						disp = setNodePosition(children.get(i), graph,
								maxDepth, level + 1, disp);
				} else {
					int half = Math.round(numChildren / 2f);
					for (int i = 0; i < half - 1; i++)
						disp = setNodePosition(children.get(i), graph,
								maxDepth, level + 1, disp);
					disp = setNodePosition(children.get(half - 1), graph,
							maxDepth, level + 1, disp);
					node.setX(children.get(half - 1).getX());
					node.setY(level);
					for (int i = half; i < numChildren; i++)
						disp = setNodePosition(children.get(i), graph,
								maxDepth, level + 1, disp);
				}
				return disp;
			}
		}

	}

}
