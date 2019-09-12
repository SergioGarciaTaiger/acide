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

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;

import acide.gui.debugPanel.debugCanvas.tasks.AcideDebugCanvasParseTask;
import acide.gui.debugPanel.traceDatalogPanel.AcideTraceDatalogPanel;
import acide.gui.graphCanvas.AcideGraphCanvas.CanvasPanel;
import acide.gui.graphCanvas.tasks.AcideGraphCanvasParseTask;
import acide.gui.mainWindow.AcideMainWindow;
import acide.process.console.DesDatabaseManager;

/**
 * /** ACIDE - A Configurable IDE trace datalog panel refresh button listener.
 * 
 * @see ActionListener
 * @version 0.15
 * 
 */
public class AcideTraceDatalogPanelRefreshListener implements ActionListener {
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent arg0) {
		AcideMainWindow.getInstance().getDebugPanel()
		.getTraceDatalogPanel()
		.getHighLighter().unHighLight();
		//Gets the query
		String consult = AcideMainWindow.getInstance().getDebugPanel()
				.getTraceDatalogPanel().getQuery();
		//Gets the trace datalog output for the query
		LinkedList<String> l = DesDatabaseManager.getInstance()
				.executeCommand("/tapi /trace_datalog " + consult);
		String result = "";
		for (String s : l) {
			result += s + "\n";
		}
		//Parses the result and generates the graph
		final Thread t = new Thread(new AcideDebugCanvasParseTask(
				result, AcideGraphCanvasParseTask.PARSE_TAPI_PDG,
				AcideMainWindow.getInstance().getDebugPanel()
						.getTraceDatalogPanel().getCanvas(),AcideDebugCanvasParseTask.DESTINY_PATH,consult,false));
		
		try{
			
			l = DesDatabaseManager.getInstance()
					.executeCommand("/tapi /pdg " + consult.substring(0, consult.lastIndexOf("(")));
			}
			catch(Exception ie){
				ie.printStackTrace();
				}
		
		result = "";
		for (String s : l) {
			result += s + "\n";
		}
		
		new Thread(new AcideDebugCanvasParseTask(
				result, AcideGraphCanvasParseTask.PARSE_TAPI_PDG,
				AcideMainWindow.getInstance().getDebugPanel()
						.getTraceDatalogPanel().getCanvas(),AcideDebugCanvasParseTask.DESTINY_MAIN,consult,false)).start();
		t.start();
		//highlights the lines on the file editor
		if(AcideMainWindow.getInstance().getDebugPanel()
						.getTraceDatalogPanel().getShowRulesMenuItem().isSelected())
		new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					t.join(5000);
					// gets the selected node
					String selected = AcideMainWindow.getInstance()
							.getDebugPanel().getTraceDatalogPanel()
							.getCanvas().getRootNode()
							.getLabel();
					// hihghlights the text of the definition of the selected node
					AcideMainWindow.getInstance().getDebugPanel()
							.getTraceDatalogPanel()
							.getHighLighter().highLight(selected);

				} catch (Exception e) {
				}

			}
		}).start();

		AcideTraceDatalogPanel._canvas.setZoom(1, CanvasPanel.TraceData);
		}

}
