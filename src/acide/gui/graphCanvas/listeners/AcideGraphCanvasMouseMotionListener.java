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
package acide.gui.graphCanvas.listeners;

import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

import javax.swing.*;

import acide.gui.graphCanvas.AcideGraphCanvas;
import acide.gui.graphUtils.DirectedWeightedGraph;
import acide.gui.graphUtils.Node;

/**
 * ACIDE - A Configurable IDE graph canvas mouse motion listener.
 * 
 * @version 0.12
 * @see {@link #MouseMotionListener}
 */
public class AcideGraphCanvasMouseMotionListener implements MouseMotionListener {

	/**
	 * ACIDE - A Configurable IDE mouse motion listener x position of the mouse.
	 */
	private int x=-1;
	/**
	 * ACIDE - A Configurable IDE mouse motion listener y position of the mouse.
	 */
	private int y=-1;
	
	private Node _selected =null;
	
	private JPopupMenu popup;
	
	/**
	 * ACIDE - A Configurable IDE mouse motion listener the mouse was clicking a node.
	 */
	private boolean _nodeClicked=false;
	
	/**
	 * When you drag the mouse if you have clicked on a node of the graph it moves that node
	 * if you have clicked on an empty area it moves the all the nodes of the graph.
	 */
	public void mouseDragged(MouseEvent ev) {
		if(x!=-1 || y!=-1){
			int difx=ev.getX()-x;
			int dify=ev.getY()-y;
			AcideGraphCanvas canvas = AcideGraphCanvas.getInstance();
			DirectedWeightedGraph graph = canvas.get_graph();
			ArrayList<Node> nodes = graph.get_nodes();
			if(!_nodeClicked)
				for(Node n:nodes){
					//checks if the node is clicked
					if(ev.getX()>=n.getX() && ev.getX()<=n.getX()+(int)(canvas.getNodeSize()*canvas.getZoom()) &&
							ev.getY()>=n.getY() && ev.getY()<=n.getY()+(int)(canvas.getNodeSize()*canvas.getZoom())){
						_selected = n;
						_nodeClicked=true;
						break;
					}

					//checks if the labels of the nodes are shown and if they are checks if the label is clicked
					if(!_nodeClicked && canvas.isShowingLabels()){
						Graphics g = canvas.getGraphics();
						Rectangle2D rect = g.getFontMetrics().getStringBounds(n.getLabel(), g);
						int posxRect=(int)n.getX()-(int)((rect.getWidth()/2-(canvas.getNodeSize()*canvas.getZoom())/2));
						if(ev.getX()>=posxRect
								&& ev.getX()<=posxRect+rect.getWidth()
								&& ev.getY()>=(int)n.getY()- (5+(int)rect.getHeight())
								&& ev.getY()<=n.getY()){
							_selected = n;
							_nodeClicked=true;
							break;
						}
					}
				}
			//moves the selected node.
			if(_selected!=null)
				_selected.move(difx,dify);
			else
				//moves all the nodes.
				for(Node n:nodes)
					n.move(difx, dify);
			canvas.repaint();
		}
		//saves the new value of x and y coordinates.
		x=ev.getX();
		y=ev.getY();
		try {
			Thread.sleep(5);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	/**
	 * updates the position and set no node as selected
	 */
	public void mouseMoved(MouseEvent ev) {
		this.x=-1;
		this.y=-1;		
		_nodeClicked=false;
		_selected =null;
	}
	
	public void mousePresed(MouseEvent ev) {
		
		popup = new JPopupMenu(); //creamos el menu saliente
	       popup.add(new JMenuItem("Un elemento del menu")); //agregamos elemento
	       popup.add(new JMenuItem("Otro elemento del menu")); //.. y otro elemento
		
		mostrarPopupMenu(ev);
	}
	
	/* Este metodo se encarga de mostrar el menu saliente */
	   private void mostrarPopupMenu(MouseEvent e) {
	      
	       if (e.isPopupTrigger()) { //si se desea mostrar el menu saliente...
	           popup.show(e.getComponent(),
	                      e.getX(), e.getY()); //... mostramos el menu en la ubicacion del raton
	       }

	   }
	
}
