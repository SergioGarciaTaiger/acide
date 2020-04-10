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
package acide.gui.debugPanel.traceSQLPanel.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;

import acide.gui.debugPanel.debugCanvas.tasks.AcideDebugCanvasParseTask;
import acide.gui.debugPanel.helpers.AcideDebugHelper;
import acide.gui.debugPanel.traceSQLPanel.AcideTraceSQLPanel;
import acide.gui.graphCanvas.AcideGraphCanvas.CanvasPanel;
import acide.gui.graphCanvas.tasks.AcideGraphCanvasParseTask;
import acide.gui.mainWindow.AcideMainWindow;
import acide.process.console.DesDatabaseManager;

/**
 * /** ACIDE - A Configurable IDE trace SQL panel refresh button listener.
 * 
 * @see ActionListener
 * @version 0.15
 * 
 */
public class AcideTraceSQLPanelRefreshListener implements ActionListener {
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent arg0) {
		// Gets the query
		String consult = AcideMainWindow.getInstance().getDebugPanel()
				.getTraceSQLPanel().getQuery();
		// Gets the trace SQL output for query
		LinkedList<String> l = DesDatabaseManager.getInstance().executeCommand(
				"/tapi /trace_sql " + consult);
		StringBuilder result = new StringBuilder();
		for (String s : l) {
			result.append(s).append("\n");
		}
		// Parses the result and generates the path graph (/pdg -> /rdg v0.17)
		final Thread t = new Thread(new AcideDebugCanvasParseTask(result.toString(),
				AcideGraphCanvasParseTask.PARSE_TAPI_RDG, AcideMainWindow
						.getInstance().getDebugPanel().getTraceSQLPanel()
						.getCanvas(), AcideDebugCanvasParseTask.DESTINY_PATH,consult,false));
		result = new StringBuilder(AcideDebugHelper.obtainSQLResult(t, result.toString(), l, consult));
		// Parses the result and generates the graph (/pdg -> /rdg v0.17)
		new Thread(new AcideDebugCanvasParseTask(result.toString(),
				AcideGraphCanvasParseTask.PARSE_TAPI_RDG, AcideMainWindow
						.getInstance().getDebugPanel().getTraceSQLPanel()
						.getCanvas(), AcideDebugCanvasParseTask.DESTINY_MAIN,consult,false))
				.start();
		AcideTraceSQLPanel._canvas.setZoom(1, CanvasPanel.TraceSQL);
		}

}
