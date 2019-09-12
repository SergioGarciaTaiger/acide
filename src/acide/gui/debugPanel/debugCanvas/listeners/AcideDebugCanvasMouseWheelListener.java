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

import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

import acide.gui.debugPanel.debugCanvas.AcideDebugCanvas;
import acide.gui.graphCanvas.AcideGraphCanvas.CanvasPanel;
import acide.gui.graphUtils.DirectedWeightedGraph;

/**
 * ACIDE - A Configurable IDE debug canvas mouse wheel listener.
 * 
 * @version 0.15
 * @see MouseWheelListener
 */
public class AcideDebugCanvasMouseWheelListener implements MouseWheelListener {
	/**
	 * ACIDE - A Configurable IDE debug canvas mouse wheel listener target canvas.
	 */
	AcideDebugCanvas _canvas;
	/**
	 * Creathes a new ACIDE - A Configurable IDE debug canvas mouse wheel listener.
	 * @param canvas the canvas to listen
	 */
	public AcideDebugCanvasMouseWheelListener(AcideDebugCanvas canvas){
		this._canvas=canvas;
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see java.awt.event.MouseWheelListener#mouseWheelMoved(java.awt.event.
	 * MouseWheelEvent)
	 */
	@Override
	public void mouseWheelMoved(MouseWheelEvent ev) {

		// checks if the graph exists and if the control key is pressed
		DirectedWeightedGraph canvasGraph = _canvas.get_graph();
		if (canvasGraph != null && ev.isControlDown()) {
			
			//We search the name of canvas parent
			CanvasPanel panelParent;
			String panel = _canvas.getParent().getClass().getName();
			if (panel.equals("acide.gui.debugPanel.traceSQLPanel.AcideTraceSQLPanel")) 
				panelParent = CanvasPanel.TraceSQL;
			else if (panel.equals("acide.gui.debugPanel.debugSQLPanel.AcideDebugSQLPanel"))
				panelParent = CanvasPanel.DebugSQL;
			else if (panel.equals("acide.gui.debugPanel.traceDatalogPanel.AcideTraceDatalogPanel"))
				panelParent = CanvasPanel.TraceData;
			else panelParent = null;

			// gets the coordinates of the event
			int difx = ev.getX() - _canvas.getX0();
			int dify = ev.getY() - _canvas.getY0();
			// checks the wheel rotation direction
			if (ev.getWheelRotation() < 0) {
				// zooms in the canvas and move the graph
				_canvas.zoomIn(panelParent);
				int difx2 = ev.getX() - _canvas.getX0();
				int dify2 = ev.getY() - _canvas.getY0();
				_canvas.moveGraph(difx2 - difx, dify2 - dify);
			} else {
				// zooms out the canvas and move the graph
				_canvas.zoomOut(panelParent);
				int difx2 = ev.getX() - _canvas.getX0();
				int dify2 = ev.getY() - _canvas.getY0();
				_canvas.moveGraph(difx2 - difx, dify2 - dify);
			}
			// repaint the canvas
			_canvas.repaint();
		}
	}

}
