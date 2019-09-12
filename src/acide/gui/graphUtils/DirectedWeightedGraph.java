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
package acide.gui.graphUtils;

import java.util.ArrayList;
/**
 * ACIDE - A Configurable IDE directed weighted graph.
 * 
 * @version 0.12
 */
public class DirectedWeightedGraph {

	/**
	 * ACIDE - A Configurable IDE list of nodes.
	 */
	ArrayList<Node> _nodes;
	
	/**
	 * ACIDE - A Configurable IDE list of links.
	 */
	ArrayList<DirectedWeightedLink> _links;
	
	/**
	 * ACIDE - A Configurable IDE number of nodes.
	 */
	private int numNodes = 0;
	/**
	 * ACIDE - A Configurable IDE number of links.
	 */
	private int numLinks = 0;
	
	/**
	 * Creates a ACIDE - A Configurable IDE directed weighted graph.
	 * 
	 */
	public DirectedWeightedGraph(){
		_nodes = new ArrayList<Node>();
		_links = new ArrayList<DirectedWeightedLink>();
	}
	
	
	/**
	 * Adds a node to the ACIDE - A Configurable IDE directed weighted graph.
	 * @param node the {@link Node} to add to the graph .
	 * @return true if the node is the node is added to the graph ,false otherwise.
	 */
	public boolean addNode(Node node){
		if(_nodes.contains(node))
			return false;
		else{
			numNodes++;
			node.setId(numNodes);
			_nodes.add(node);
			return true;
		}
		
	}
	
	/**
	 *  Adds a node to the ACIDE - A Configurable IDE directed weighted graph at the selected position.
	 * @param index the selected position for the node
	 * @param node node the {@link Node} to add to the graph 
	 * @return true if the node is the node is added to the graph ,false otherwise.
	 */
	public boolean addNode(int index, Node node){
		if(_nodes.contains(node))
			return false;
		else{
			numNodes++;
			node.setId(numNodes);
			_nodes.add(index,node);
			return true;
		}
	}
	
	/**
	 * Adds a Link to the ACIDE - A Configurable IDE directed weighted graph.
	 * @param link  the {@link DirectedWeightedLink} to add to the graph.
	 * @return true if the link is the node is added to the graph ,false otherwise.
	 */
	public boolean addLink(DirectedWeightedLink link){
		if((_links.contains(link)))			
				return false;
		else{
			numLinks++;
			link.setId(numLinks);
			_links.add(link);
			return true;
		}
	}
	
	/**
	 * Checks if a node is in the ACIDE - A Configurable IDE directed weighted graph.
	 * @param node the {@link Node} whose presence in this graph is to be tested.
	 * @return true if the graph contains the node.
	 */
	public boolean isNodeInGraph(Node node){
		return _nodes.contains(node);
	}
	
	/**
	 * Checks if a link is in the ACIDE - A Configurable IDE directed weighted graph.
	 * @param link the {@link DirectedWeightedLink} whose presence in this graph is to be tested. 
	 * @return true if the graph contains the link.
	 */
	public boolean isLinkInGraph(DirectedWeightedLink link){
		return _links.contains(link);
	}
	
	/**
	 * Removes the {@link Node} and all the {@link DirectedWeightedLink} connected to it.  
	 * @param node the {@link Node} to be removed.
	 * @return true if it has been successfully removed of the graph.
	 */
	public boolean removeNode(Node node){
		if(_nodes.contains(node)){
			for(int i=_links.size();i>=0;i--){
				DirectedWeightedLink link = _links.get(i);
				if(link.getOrigin().equals(node) || link.getDestiny().equals(node))
					_links.remove(i);
			}
			return _nodes.remove(node);
		}else
			return false;
	}
	
	/**
	 * Removes a link from the ACIDE - A Configurable IDE directed weighted graph.
	 * @param link the {@link DirectedWeightedLink} to be removed
	 * @return true if it has been successfully removed of the graph.
	 */
	public boolean removeLink(DirectedWeightedLink link){
		if (_links.contains(link))
			return _links.remove(link);
		else
			return false;
	}

	/**
	 * Returns the list of nodes of the ACIDE - A Configurable IDE directed weighted graph.
	 * @return the list of nodes of the ACIDE - A Configurable IDE directed weighted graph.
	 */
	public ArrayList<Node> get_nodes() {
		return _nodes;
	}

	/**
	 * Sets a new list of nodes to the ACIDE - A Configurable IDE directed weighted graph.
	 * @param _nodes the new value to set.
	 */
	public void set_nodes(ArrayList<Node> _nodes) {
		this._nodes = _nodes;
	}

	/**
	 * Returns the list of links of the ACIDE - A Configurable IDE directed weighted graph.
	 * @return the list of links of the ACIDE - A Configurable IDE directed weighted graph.
	 */
	public ArrayList<DirectedWeightedLink> get_links() {
		return _links;
	}

	/**
	 * Sets a new list of links to the ACIDE - A Configurable IDE directed weighted graph.
	 * @param _links the new value to set.
	 */
	public void set_links(ArrayList<DirectedWeightedLink> _links) {
		this._links = _links;
	}

	/**
	 * Returns the number of nodes of the ACIDE - A Configurable IDE directed weighted graph.
	 * @return the number of nodes of the ACIDE - A Configurable IDE directed weighted graph.
	 */
	public int getNumNodes() {
		return numNodes;
	}

	/**
	 * Sets a new value to the number of nodes of the ACIDE - A Configurable IDE directed weighted graph.
	 * @param numNodes the new value to set.
	 */
	public void setNumNodes(int numNodes) {
		this.numNodes = numNodes;
	}

	/**
	 * Returns the number of links of the ACIDE - A Configurable IDE directed weighted graph.
	 * @return the number of links of the ACIDE - A Configurable IDE directed weighted graph.
	 */
	public int getNumLinks() {
		return numLinks;
	}

	/**
	 * Sets a new value to the number of links of the ACIDE - A Configurable IDE directed weighted graph.
	 * @param numLinks the new value to set.
	 */
	public void setNumLinks(int numLinks) {
		this.numLinks = numLinks;
	}
	
}
