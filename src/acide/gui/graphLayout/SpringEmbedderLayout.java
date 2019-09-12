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

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

import acide.gui.graphUtils.DirectedWeightedGraph;
import acide.gui.graphUtils.DirectedWeightedLink;
import acide.gui.graphUtils.Node;


/**
 * ACIDE - A Configurable IDE spring embedder layout
 * 
 * @version 0.12
 *
 * @see {@link #GraphLayout}
 */
public class SpringEmbedderLayout implements GraphLayout {
	/**
	 * ACIDE - A Configurable IDE spring embedder layout c1 constant.
	 */
	private final double c1=2;
	/**
	 * ACIDE - A Configurable IDE spring embedder layout c2 constant.
	 */
	private final double c2=1;
	/**
	 * ACIDE - A Configurable IDE spring embedder layout c3 constant.
	 */
	private final double c3=10;
	/**
	 * ACIDE - A Configurable IDE spring embedder layout c4 constant.
	 */
	private final double c4=0.05;
	/**
	 * ACIDE - A Configurable IDE spring embedder layout number of loops
	 */
	private int M=1000;
	/**
	 * ACIDE - A Configurable IDE spring embedder layout margin
	 * between the nodes and the borders of the canvas.
	 */
	private final int margin=30;
	/**
	 * ACIDE - A Configurable IDE spring embedder layout grid 
	 * to put the nodes.
	 */
	private int [][]grid;
	
	/**
	 * Creates a new A Configurable IDE spring embedder layout.
	 */
	public SpringEmbedderLayout(){
		
	}
	/**
	 * Creates a new A Configurable IDE spring embedder layout.
	 * @param M max number of iteration of the {@link calculateNodesPosition} loop.
	 */
	public SpringEmbedderLayout(int M){
		this.M=Math.max(this.M, M);
	}
	/**
	 * Creates a map with the neighbors of each node in the graph.
	 * @param links the links of the graph.
	 * @return the map of the neighbors of each node.
	 */
	private HashMap<Node, ArrayList<Node>> genrateNeighborsMap(ArrayList<DirectedWeightedLink> links){
		HashMap<Node, ArrayList<Node>> map = new HashMap<Node, ArrayList<Node>>();
		//go throw each link
		for(DirectedWeightedLink l:links){
			//if the map don't contains the origin or the destiny node adds it to the map
			if(!map.containsKey(l.getOrigin()))
				map.put(l.getOrigin(), new ArrayList<Node>());
			if(!map.containsKey(l.getDestiny()))
				map.put(l.getDestiny(), new ArrayList<Node>());
			//if the origin and the destiny are not the same node adds it to the list of neighbors
			if(!l.getOrigin().equals(l.getDestiny())){
				if(!map.get(l.getOrigin()).contains(l.getDestiny()))
					map.get(l.getOrigin()).add(l.getDestiny());
				if(!map.get(l.getDestiny()).contains(l.getOrigin()))
					map.get(l.getDestiny()).add(l.getOrigin());
			}
		}
		return map;
	}
	
	@Override
	public void calculateNodesPosition(DirectedWeightedGraph graph, int width,
			int height,int nodeSize) {
		HashMap<Node, ArrayList<Node>> map = genrateNeighborsMap(graph.get_links());
		ArrayList<Node> nodes = graph.get_nodes();
		//calculates the reduction factor to the size of the canvas with the margin
		float reductionX = ((float)(width-2*margin))/width;
		float reductionY = ((float)(height-2*margin))/height;
		//creates the grid to put the nodes
		grid = new int[(int) (width*reductionX/(1.2*nodeSize))+1][(int) (height*reductionY/(1.2*nodeSize))+1];
		//calculates the position of the nodes in the grid
		for(Node n: nodes){
			n.setX((int) (n.getX()*reductionX));
			n.setY((int) (n.getY()*reductionY));
			int indx=Math.min(Math.max((int) (n.getX()/(1.2*nodeSize)), 0),(int) (width*reductionX/(1.2*nodeSize)));
			int indy=Math.min(Math.max((int)  (n.getY()/(1.2*nodeSize)), 0),(int) (height*reductionY/(1.2*nodeSize)));
			grid[indx][indy] += 1;
			
		}
		//Initializes the condition  of the loop
		double difForce = Double.MAX_VALUE;
		long startTime = Calendar.getInstance().getTimeInMillis();
		long difTemp = 0;
		int count = 0;
		double difForceAnt=Math.PI;
		while(count < M && difTemp<1000){
			//Initializes the condition  of the iteration
			double maxForce = 0;
			double minForce = Double.MAX_VALUE;
			for(int j=0; j<nodes.size();j++){
				//calculates the force to the node and apply it to the node 
				Node nodo=nodes.get(j);
				double []force= new double[2];
				calculateForceForNode(nodo,map,nodes,force,nodeSize);
				float posX= Math.min(Math.max(nodo.getX()+(float)(c4*force[0]), 0), width*reductionX-(nodeSize));
				float posY= Math.min(Math.max(nodo.getY()+(float)(c4*force[1]), 0), height*reductionY-(nodeSize));
				
				//if the objective place to the node in the grid is not empty calculates a new random position
				while(grid[(int) (posX/(1.2*nodeSize))][(int) (posY/(1.2*nodeSize))]>0){
					posX=(int) (Math.random()*width*reductionX-nodeSize+1);
					posY=(int) (Math.random()*height*reductionY-nodeSize+1);
				}
				//updates the grid
				grid[(int) (nodo.getX()/(1.2*nodeSize))][(int) (nodo.getY()/(1.2*nodeSize))] --;
				//puts the node in the position of the gird and updates it
				nodo.setX((float) (((int) (posX/(1.2*nodeSize)))*1.2*nodeSize));
				nodo.setY((float) (((int) (posY/(1.2*nodeSize)))*1.2*nodeSize));
				grid[(int) (nodo.getX()/(1.2*nodeSize))][(int) (nodo.getY()/(1.2*nodeSize))] ++;
				//updates the max and the min force and the difference between forces
				if(j==0){
					maxForce=Math.max(Math.abs(force[0]), Math.abs(force[1]));
					minForce=Math.min(Math.abs(force[0]), Math.abs(force[1]));
				}else{
					maxForce=Math.max(maxForce, Math.max(Math.abs(force[0]), Math.abs(force[1])));
					minForce=Math.min(minForce, Math.min(Math.abs(force[0]), Math.abs(force[1])));
				}
			}
			difForce = Math.abs(maxForce-minForce);
			if((int)difForceAnt==(int)difForce)
				count++;
			else
				count=0;
			difForceAnt=difForce;
			difTemp = Calendar.getInstance().getTimeInMillis()-startTime;
		}
		for(Node n: nodes){
			//checks that there is only one node in the same position of the grid and change the node if there are more.
			if(grid[(int) (n.getX()/(1.2*nodeSize))][(int) (n.getY()/(1.2*nodeSize))] != 1){
				boolean placed = false;
				for(int i = 0; i<grid.length && !placed;i++){
					for(int j=0; j<grid[i].length && !placed;j++){
						if(grid[i][j]==0){
							grid[(int) (n.getX()/(1.2*nodeSize))][(int) (n.getY()/(1.2*nodeSize))] --;
							n.setX (i*(float)1.2*nodeSize);
							n.setY (j*(float)1.2*nodeSize);;
							grid[(int) (n.getX()/(1.2*nodeSize))][(int) (n.getY()/(1.2*nodeSize))] ++;
							placed = true;
						}
					}
				}
			}
			//moves the node positon to set the margin to the border.
			n.setX(n.getX()+margin);
			n.setY(n.getY()+margin);
		}

	}
	/**
	 * Calculates the force applied to a node.
	 * @param node The node to calculate the force to apply.
	 * @param map the neighbor map.
	 * @param nodes the nodes of the graph
	 * @param force array to save the x and the y component of the force
	 * @param nodeSize the size of the nodes in the canvas.
	 */
	private void calculateForceForNode(Node node,
			HashMap<Node, ArrayList<Node>> map, ArrayList<Node> nodes,
			double []force,int nodeSize) {
		double attractionForceX=0;
		double attractionForceY=0;
		double repulsionForceX=0;
		double repulsionForceY=0;
		double totalForceX=0;
		double totalForceY=0;
		for(int i=0; i<nodes.size();i++){
			Node n=nodes.get(i);
			//calculate the distance between the nodes
			double distance = Math.sqrt(Math.pow(node.getX()- n.getX(),2)+
					Math.pow(node.getY()- n.getY(),2))/nodeSize;
			if(distance>0){
				//if the nodes are neighbors increase the attraction force
				if(map.get(node).contains(n)){
					attractionForceX+=c1*Math.log(distance/c2)*
							((n.getX()-node.getX())/nodeSize);
					attractionForceY+=c1*Math.log(distance/c2)*
							((n.getY()-node.getY())/nodeSize);
				}
				//increase the repulsion force
				repulsionForceX+=((c3/distance)*
						((node.getX()-n.getX())/nodeSize));
					
				repulsionForceY+=((c3/distance)*
						((node.getY()-n.getY())/nodeSize));
				//adds the attraction and the repusion force to the total force.
				totalForceX+=attractionForceX+repulsionForceX;
				totalForceY+=attractionForceY+repulsionForceY;
				
			}
		}
		//updates the x and the y components of the force.
		force[0]=totalForceX;
		force[1]=totalForceY;
	}

}
