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
package acide.gui.graphCanvas.listeners;

import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

import acide.gui.graphCanvas.AcideGraphCanvas;
import acide.gui.graphCanvas.AcideGraphCanvas.CanvasPanel;

/**
 * ACIDE - A Configurable IDE graph canvas mouse wheel listener.
 * 
 * @version 0.12
 * @see {@link #MouseWheelListener}
 * @see {@link #AcideGraphCanvas}
 */
public class AcideGraphCanvasMouseWheelListener implements MouseWheelListener {

	/**
	 * zoom the graph when you roll the wheel.
	 */
	public void mouseWheelMoved(MouseWheelEvent ev) {
		//gets the canvas
		AcideGraphCanvas canvas = AcideGraphCanvas.getInstance();
		//checks if the graph exists and if the control key is pressed
		if(canvas.get_graph()!=null && ev.isControlDown()){
			//gets the coordinates of the event
			int difx=ev.getX()-canvas.getX0();
			int dify=ev.getY()-canvas.getY0();	
			//checks the wheel rotation direction
			if(ev.getWheelRotation()<0){
				//zooms in the canvas and move the graph
				canvas.zoomIn(CanvasPanel.Graph);
				int difx2=ev.getX()-canvas.getX0();
				int dify2=ev.getY()-canvas.getY0();
				canvas.moveGraph(difx2-difx, dify2-dify);
			}else{
				//zooms out the canvas and move the graph
				canvas.zoomOut(CanvasPanel.Graph);
				int difx2=ev.getX()-canvas.getX0();
				int dify2=ev.getY()-canvas.getY0();
				canvas.moveGraph(difx2-difx, dify2-dify);
			}
			//repaint the canvas
			canvas.repaint();
		}
	}

}
