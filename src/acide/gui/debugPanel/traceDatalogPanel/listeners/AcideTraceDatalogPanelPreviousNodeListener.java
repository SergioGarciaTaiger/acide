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
package acide.gui.debugPanel.traceDatalogPanel.listeners;

import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import acide.gui.debugPanel.debugCanvas.AcideDebugCanvas;
import acide.gui.debugPanel.utils.AcideDebugPanelHighLighter;
import acide.gui.mainWindow.AcideMainWindow;

/**
 * ACIDE - A Configurable IDE trace datalog previous node listener.
 * 
 * @see AcitonListener
 * @version 0.15
 * 
 */
public class AcideTraceDatalogPanelPreviousNodeListener implements
		ActionListener {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		AcideMainWindow.getInstance().getDebugPanel()
				.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
		try {
			// Gets the canvas
			AcideDebugCanvas canvas = AcideMainWindow.getInstance()
					.getDebugPanel().getTraceDatalogPanel().getCanvas();
			// Updates the selected node
			canvas.retardSelectedNode();
			canvas.repaint();
			String selected = canvas.getSelectedNode().getLabel();
			// Updates the highlights
			AcideDebugPanelHighLighter highLighter = AcideMainWindow
					.getInstance().getDebugPanel().getTraceDatalogPanel()
					.getHighLighter();
			highLighter.resetLines();
			highLighter.unHighLight();
			highLighter.highLight(selected);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		AcideMainWindow.getInstance().getDebugPanel()
				.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
	}
}
