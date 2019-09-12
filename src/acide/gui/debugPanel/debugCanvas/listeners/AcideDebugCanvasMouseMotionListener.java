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
package acide.gui.debugPanel.debugCanvas.listeners;

import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

import acide.gui.debugPanel.debugCanvas.AcideDebugCanvas;
import acide.gui.graphUtils.DirectedWeightedGraph;
import acide.gui.graphUtils.Node;

/**
 * ACIDE - A Configurable IDE debug canvas mouse motion listener.
 * 
 * @version 0.15
 * @see MouseMotionListener
 */
public class AcideDebugCanvasMouseMotionListener implements MouseMotionListener {
	/**
	 * ACIDE - A Configurable IDE debug canvas mouse motion listener target canvas.
	 */
	AcideDebugCanvas _canvas;
	/**
	 * ACIDE - A Configurable IDE debug canvas mouse motion listener x position of the mouse.
	 */
	private int x=-1;
	/**
	 * ACIDE - A Configurable IDE debug canvas mouse motion listener y position of the mouse.
	 */
	private int y=-1;
	
	private Node _selected =null;
	
	/**
	 * ACIDE - A Configurable IDE debug canvas mouse motion listener the mouse was clicking a node.
	 */
	private boolean _nodeClicked=false;
	/**
	 * Creates a new ACIDE - A Configurable IDE debug canvas mouse motion listener.
	 * @param canvas the canvas to listen.
	 */
	public AcideDebugCanvasMouseMotionListener(AcideDebugCanvas canvas){
		this._canvas=canvas;
	}
	
	/*
	 * (non-Javadoc)
	 * @see java.awt.event.MouseMotionListener#mouseDragged(java.awt.event.MouseEvent)
	 */
	@Override
	public void mouseDragged(MouseEvent ev) {
		if(x!=-1 || y!=-1){
			int difx=ev.getX()-x;
			int dify=ev.getY()-y;
			
			DirectedWeightedGraph graph = _canvas.get_graph();
			ArrayList<Node> nodes = graph.get_nodes();
			if(!_nodeClicked)
				for(Node n:nodes){
					//checks if the node is clicked
					if(ev.getX()>=n.getX() && ev.getX()<=n.getX()+(int)(_canvas.getNodeSize()*_canvas.getZoom()) &&
							ev.getY()>=n.getY() && ev.getY()<=n.getY()+(int)(_canvas.getNodeSize()*_canvas.getZoom())){
						_selected = n;
						_nodeClicked=true;
						break;
					}
					
					//checks if the labels of the nodes are shown and if they are checks if the label is clicked
					if(!_nodeClicked && _canvas.isShowingLabels()){
						Graphics g = _canvas.getGraphics();
						Rectangle2D rect = g.getFontMetrics().getStringBounds(n.getLabel(), g);
						int posxRect=(int)n.getX()-(int)((rect.getWidth()/2-(_canvas.getNodeSize()*_canvas.getZoom())/2));
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
			_canvas.repaint();
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

	/*
	 * (non-Javadoc)
	 * @see java.awt.event.MouseMotionListener#mouseMoved(java.awt.event.MouseEvent)
	 */
	@Override
	public void mouseMoved(MouseEvent arg0) {
		this.x=-1;
		this.y=-1;		
		_nodeClicked=false;
		_selected =null;

	}

}
