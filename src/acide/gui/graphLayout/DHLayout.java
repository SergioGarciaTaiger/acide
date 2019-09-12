/*
 * ACIDE - A Configurable IDE
 * Official web site: http://acide.sourceforge.net
 * 
 * Copyright (C) 2007-2013  
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

import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

import acide.gui.graphUtils.DirectedWeightedGraph;
import acide.gui.graphUtils.DirectedWeightedLink;
import acide.gui.graphUtils.Node;

/**
 * ACIDE - A Configurable IDE DH layout
 * 
 * @version 0.15
 * @see GraphLayout
 * 
 */
public class DHLayout implements GraphLayout {
	/**
	 * Number of iterations of the ACIDE - A Configurable IDE DH layout outer
	 * loop.
	 */
	private final int M = 100;
	/**
	 * Number of iterations of the ACIDE - A Configurable IDE DH layout inner
	 * loop.
	 */
	private final int T = 100;
	/**
	 * Delta coefficient for the temperature of the ACIDE - A Configurable IDE
	 * DH layout.
	 */
	private final double DELTA = 0.75;
	/**
	 * CR coefficient for the ACIDE - A Configurable IDE DH layout node
	 * potential.
	 */
	private final double CR = 3;
	/**
	 * CB coefficient for the ACIDE - A Configurable IDE DH layout node
	 * potential.
	 */
	private final double CB = 4;
	/**
	 * CC coefficient for the ACIDE - A Configurable IDE DH layout node
	 * potential.
	 */
	private final double CC = 4;
	/**
	 * CEN coefficient for the ACIDE - A Configurable IDE DH layout node
	 * potential.
	 */
	private final double CEN = 5;
	/**
	 * CE coefficient for the ACIDE - A Configurable IDE DH layout link
	 * potential.
	 */
	private final double CE = 1;
	/**
	 * grid for the position of the nodes in the ACIDE - A Configurable IDE DH
	 * layout.
	 */
	private final int _margin = 30;
	/**
	 * Grid for the position of the nodes in the ACIDE - A Configurable IDE DH
	 * layout.
	 */
	private int[][] _grid;
	/**
	 * Grid cell size of the ACIDE - A Configurable IDE DH layout.
	 */
	private float grid_cell = 2.2f;

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
		float reductionX = ((float) (width - 2 * _margin)) / width;
		float reductionY = ((float) (height - 2 * _margin)) / height;
		_grid = new int[(int) (width * reductionX / (grid_cell * nodeSize)) + 1][(int) (height
				* reductionY / (grid_cell * nodeSize)) + 1];
		while (graph.getNumNodes() > _grid.length * _grid[0].length * 0.8) {
			_grid = new int[(int) Math.round(1.5 * _grid.length)][(int) Math
					.round(1.5 * _grid[0].length)];
		}
		// calculates the position of the nodes in the grid
		for (int i = 0; i < graph.getNumNodes(); i++) {
			Node n = graph.get_nodes().get(i);
			n.setX((i / _grid[0].length) * grid_cell * nodeSize);
			n.setY((i % _grid[0].length) * grid_cell * nodeSize);
			_grid[(i / _grid[0].length)][(i % _grid[0].length)]++;
		}
		double temperatureX = _grid.length / 2;
		double temperatureY = _grid[0].length / 2;
		long startTime = new Date().getTime();
		long currentTime = startTime;
		Random random1 = new Random();
		Random random2 = new Random();
		for (int i = 0; i < M && (currentTime - startTime) < 1000; i++) {
			for (int j = 0; j < T && (currentTime - startTime) < 1000; j++) {
				if (i + j % 100 == 0) {
					random2.setSeed(random2.nextLong());
					random1.setSeed(System.currentTimeMillis());
				}
				double antPotential = Double.POSITIVE_INFINITY;
				for (int k = 0; k < graph.get_nodes().size(); k++) {
					Node n = graph.get_nodes().get(k);
					double xCoordinateAnt = n.getX();
					double yCoordinateAnt = n.getY();
					int indx = (int) (n.getX() / (grid_cell * nodeSize));
					int indy = (int) (n.getY() / (grid_cell * nodeSize));
					int posX = 0;
					int posY = 0;
					int loopcount = 0;
					do {
						if (loopcount == 100) {
							posX = indx;
							posY = indy;
							break;
						}
						posX = (int) Math.max(0, Math.min(Math.round(indx
								+ random1.nextDouble() * temperatureX
								* (random1.nextDouble() < 0.5 ? 1 : -1)),
								_grid.length - 1));
						posY = (int) Math.max(0, Math.min(Math.round(indy
								+ random2.nextDouble() * temperatureY
								* (random2.nextDouble() < 0.5 ? 1 : -1)),
								_grid[0].length - 1));
						loopcount++;
					} while (_grid[posX][posY] > 0);
					_grid[indx][indy]--;
					_grid[posX][posY]++;
					n.setX((float) (posX * grid_cell * nodeSize));
					n.setY((float) (posY * grid_cell * nodeSize));
					double newPotential = calculateGraphPotential(graph, width,
							height, nodeSize);
					if (antPotential < newPotential) {
						double prob = 1 - (Math.pow(
								Math.E,
								(antPotential - newPotential)
										/ Math.min(temperatureX, temperatureY)));
						if ((random1.nextDouble() + random2.nextDouble()) / 2 < prob) {
							n.setX((float) xCoordinateAnt);
							n.setY((float) yCoordinateAnt);
							_grid[indx][indy]++;
							_grid[posX][posY]--;
						} else
							antPotential = newPotential;
					} else
						antPotential = newPotential;
				}
				currentTime = new Date().getTime();

			}
			temperatureX = (int) (temperatureX * DELTA);
			temperatureY = (int) (temperatureY * DELTA);
		}
		for (Node n : graph.get_nodes()) {
			n.setX(n.getX() + _margin);
			n.setY(n.getY() + _margin);
		}
	}

	/**
	 * Calculates the potential for the ACIDE - A Configurable IDE DH layout
	 * directed weighted graph for a concrete position of its nodes.
	 * 
	 * @param graph
	 *            directed weighted graph.
	 * @param width
	 *            width of the ACIDE - A Configurable IDE graph canvas.
	 * @param height
	 *            height of the ACIDE - A Configurable IDE graph canvas.
	 * @param nodeSize
	 *            size of the nodes in the ACIDE - A Configurable IDE graph
	 *            canvas.
	 * @return the potential of graph.
	 */
	private double calculateGraphPotential(DirectedWeightedGraph graph,
			int width, int height, int nodeSize) {
		// initial potential
		double potential = 0;
		// for each node
		for (Node node : graph.get_nodes())
			// calculates the potential of the node
			potential += calculateNodePotential(node, graph.get_nodes(),
					graph.get_links(), nodeSize, height, width);

		// for each link
		for (DirectedWeightedLink link : graph.get_links())
			// calculates the potential of the link
			potential += CE * caluclateLinkLengthPotential(link, nodeSize);

		double d = calculateLinkscrossPotentia(graph.get_links(), height, width);
		potential += CC * d;
		// returns the potential
		return potential;
	}

	/**
	 * Calculates the potential for the ACIDE - A Configurable IDE DH layout
	 * directed weighted graph based on the link linongitude.
	 * 
	 * @param link
	 *            the link to calculate the potential.
	 * @param nodeSize
	 * @return the potential of the link.
	 */
	private double caluclateLinkLengthPotential(DirectedWeightedLink link,
			int nodeSize) {
		// gets the length of the link and returns the length raised to the
		// power of two
		double length = Math.sqrt(Math.pow(link.getDestiny().getX()
				- link.getOrigin().getX(), 2)
				+ Math.pow(link.getDestiny().getY() - link.getOrigin().getY(),
						2))
				/ nodeSize;
		return Math.pow(length, 2);
	}

	/**
	 * Calculates the potential for a node of the ACIDE - A Configurable IDE DH
	 * layout directed weighted graph.
	 * 
	 * @param node
	 *            the link to calculate the potential.
	 * @param nodes
	 *            the nodes of the graph.
	 * @param links
	 *            the links of the graph.
	 * @param nodeSize
	 *            the size of the nodes in the ACIDE - A Configurable IDE graph
	 *            canvas.
	 * @param height
	 *            height of the ACIDE - A Configurable IDE graph canvas.
	 * @param nodeSize
	 *            size of the nodes in the ACIDE - A Configurable IDE graph
	 *            canvas.
	 * @return the potential of the node
	 */
	private double calculateNodePotential(Node node, ArrayList<Node> nodes,
			ArrayList<DirectedWeightedLink> links, int nodeSize, int height,
			int width) {
		// calculates the potential of the node based on the distance
		// between the node and the rest of the nodes
		double nodePotential = calculateNodesPotential(node, nodes, nodeSize);
		// calculates the potential of the node based on the distance
		// between the node and the borders of the graph.
		double borderPotential = caluclateBordersPotential(node, height, width,
				nodeSize);
		// calculates the potential of the node based on the distance
		// between the node and the links of the graph
		double nodeLinksPotential = calulateNodeLinksPotential(node, links,
				nodeSize);

		// return the total potential for the node
		return CR * nodePotential + CB * borderPotential + CEN
				* nodeLinksPotential;
	}

	/**
	 * Calculates the potential for a node of the ACIDE - A Configurable IDE DH
	 * layout directed weighted graph based on the minimal distance to the
	 * links.
	 * 
	 * @param node
	 *            a node of the graph.
	 * @param links
	 *            the links of the graph.
	 * @param nodeSize
	 *            the size of the nodes.
	 * @return the potential for a node based on the minimal distance to the
	 *         links.
	 */
	private double calulateNodeLinksPotential(Node node,
			ArrayList<DirectedWeightedLink> links, int nodeSize) {
		// sets the inital potential
		double potential = 0;
		// for each link
		for (DirectedWeightedLink link : links) {
			// checks if the origin and the destiny are the same node
			if (!node.equals(link.getOrigin())
					&& !node.equals(link.getDestiny())) {
				double minDinstance = Double.POSITIVE_INFINITY;
				// gets the longitude of the links
				double signedLinkLengthX = link.getDestiny().getX()
						- link.getOrigin().getX();
				double signedLinkLengthY = link.getDestiny().getY()
						- link.getOrigin().getY();
				int numPartitions = 10;
				// tour over the the links
				for (int i = 0; i < numPartitions; i++) {
					// gets the points of the links to compare
					double linkPointX = link.getOrigin().getX()
							+ signedLinkLengthX * i / numPartitions;
					double linkPointY = link.getOrigin().getY()
							+ signedLinkLengthY * i / numPartitions;
					// checks if the two points are the same
					if (node.getX() != linkPointX || node.getY() != linkPointY)
						// get the minimal distance between the links
						minDinstance = Math
								.min(minDinstance,
										Math.sqrt(Math.pow(
												linkPointX - node.getX(), 2)
												+ Math.pow(
														linkPointY
																- node.getY(),
														2))
												/ nodeSize);
					else
						minDinstance = Math.min(minDinstance, 0.1);
				}
				// checks the minimal distance on the destiny point of the links
				minDinstance = Math.min(
						minDinstance,
						Math.sqrt(Math.pow(
								link.getDestiny().getX() - node.getX(), 2)
								+ Math.pow(
										link.getDestiny().getY() - node.getY(),
										2))
								/ nodeSize);
				// updates the potential
				potential += 1 / Math.pow(minDinstance, 2);
			}

		}
		return potential;
	}

	/**
	 * Calculates the potential for a node of the ACIDE - A Configurable IDE DH
	 * layout directed weighted graph based number of intersections on the graph
	 * 
	 * @param links
	 *            the links of the graph.
	 * @param height
	 *            the height size of the canvas.
	 * @param width
	 *            the width size of the canvas.
	 * @return the number of crosses between the links.
	 */
	private double calculateLinkscrossPotentia(
			ArrayList<DirectedWeightedLink> links, int height, int width) {
		int numIntersections = 0;
		ArrayList<String> points = new ArrayList<String>();
		for (int i = 0; i < links.size() - 1; i++)
			for (int j = i + 1; j < links.size(); j++) {
				// Gets the links
				DirectedWeightedLink l1 = links.get(i);
				DirectedWeightedLink l2 = links.get(j);
				Node origin1 = l1.getOrigin();
				Node destiny1 = l1.getDestiny();
				Node origin2 = l2.getOrigin();
				Node destiny2 = l2.getDestiny();
				double ma, mb;
				// calculates the slope of the first line
				if (origin1.equals(destiny1)
						|| origin1.getX() == destiny1.getX())
					ma = Double.MAX_VALUE;
				else
					ma = ((double) (destiny1.getY() - origin1.getY()))
							/ ((double) (destiny1.getX() - origin1.getX()));
				// calculates the slope of the second line
				if (origin2.equals(destiny2)
						|| origin2.getX() == destiny2.getX())
					mb = Double.MAX_VALUE;
				else
					mb = ((double) (destiny2.getY() - origin2.getY()))
							/ ((double) (destiny2.getX() - origin2.getX()));
				if (Math.abs(ma - mb) < 0.0001d) {
					continue;
				}
				// gets x coordinate of the intersection point of the two lines
				double px = (ma * origin1.getX() - origin1.getY() - mb
						* origin2.getX() + origin2.getY())
						/ (ma - mb);
				if (origin1.equals(destiny1)
						|| origin1.getX() == destiny1.getX())
					continue;
				if (origin2.equals(destiny2)
						|| origin2.getX() == destiny2.getX())
					continue;
				// gets y coordinate of the intersection point of the two lines
				double py = ma * (px - origin1.getX()) + origin1.getY();
				if ((Math.abs(px - origin1.getX()) < 0.0001d && Math.abs(py
						- origin1.getY()) < 0.0001d)
						|| (Math.abs(px - origin2.getX()) < 0.0001d && Math
								.abs(py - origin2.getY()) < 0.0001d)
						|| (Math.abs(px - destiny1.getX()) < 0.0001d && Math
								.abs(py - destiny1.getY()) < 0.0001d)
						|| (Math.abs(px - destiny2.getX()) < 0.0001d && Math
								.abs(py - destiny2.getY()) < 0.0001d))
					continue;
				if (origin1.equals(destiny1)
						|| origin1.getX() == destiny1.getX())
					py = mb * (px - origin2.getX()) + origin2.getY();
				// checks if the intersection point is in the canvas and if the
				// point is not calculated before
				if (px > _margin
						&& px < width - _margin
						&& py > _margin
						&& py < height - _margin
						&& !points.contains(Math.round(px) + ","
								+ Math.round(py))) {

					points.add(Math.round(px) + "," + Math.round(py));
					numIntersections++;
				}

			}
		// returns the number of intersections
		return numIntersections;
	}

	/**
	 * Calculates the potential for a node of the ACIDE - A Configurable IDE DH
	 * layout directed weighted graph based on the distance between a node and
	 * the borders of the canvas.
	 * 
	 * @param node
	 *            the objective node to calculate the potential.
	 * @param height
	 *            the height size of the canvas.
	 * @param width
	 *            the width size of the canvas.
	 * @param nodeSize
	 *            the size of the nodes.
	 * @return the potential of the node based on the distance between this and
	 *         the borders of the canvas.
	 */
	private double caluclateBordersPotential(Node node, int height, int width,
			int nodeSize) {
		// gets the distance to the left border
		double distanceToLeftBorder = Math.abs((_margin + node.getX())
				/ nodeSize);
		// gets the distance to the right border
		double distanceToRightBorder = Math.abs(((width - _margin) - node
				.getX()) / nodeSize);
		// gets the distance to the up border
		double distanceToUpBorder = Math
				.abs((_margin + node.getY()) / nodeSize);
		// gets the distance to the down border
		double distanceToDownBorder = Math.abs(((height - _margin) - node
				.getY()) / nodeSize);
		// calculates the potential
		double potential = 1 / Math.pow(distanceToUpBorder, 2) + 1
				/ Math.pow(distanceToDownBorder, 2) + 1
				/ Math.pow(distanceToRightBorder, 2) + 1
				/ Math.pow(distanceToLeftBorder, 2);
		return potential;
	}

	/**
	 * Calculates the potential for a node of the ACIDE - A Configurable IDE DH
	 * layout directed weighted graph based distance between a node and rest the
	 * nodes.
	 * 
	 * @param node
	 *            the base node of the graph
	 * @param nodes
	 *            the nodes of the graph
	 * @param nodeSize
	 *            the size of the nodes.
	 * @return the potential for a node of the ACIDE - A Configurable IDE DH
	 *         layout directed weighted graph based distance between a node and
	 *         rest the nodes.
	 */
	private double calculateNodesPotential(Node node, ArrayList<Node> nodes,
			int nodeSize) {
		// Sets the initial potential
		double potential = 0;
		for (int i = 0; i < nodes.size(); i++) {
			Node n = nodes.get(i);
			// Checks that the two nodes are not the same.
			if (!node.equals(n)) {
				// calculates the distance between the nodes
				double distance = Math.sqrt(Math.pow(node.getX() - n.getX(), 2)
						+ Math.pow(node.getY() - n.getY(), 2))
						/ nodeSize;
				// updates the potential
				potential += 1 / Math.pow(distance, 2);
			}
		}
		// returns the potential
		return potential;
	}

}
