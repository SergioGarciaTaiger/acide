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
package acide.gui.graphPanel.listeners;

import acide.gui.consolePanel.tasks.AcideConsolePDGTask;
import acide.gui.consolePanel.tasks.AcideConsoleRDGTask;
import acide.gui.graphCanvas.AcideGraphCanvas.CanvasPanel;
import acide.gui.graphPanel.AcideGraphPanel;
import acide.gui.mainWindow.AcideMainWindow;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * ACIDE - A Configurable IDE graph refresh button listener.
 * 
 * @see ActionListener
 */
public class AcideGraphPanelRefreshListener implements ActionListener {
	/*
	 * (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		//saves the send to console flag
		final boolean sendToConsole = AcideMainWindow
				.getInstance().getConsolePanel()
				.getProcessThread().getOutputGobbler()
				.get_sendToConsole();
		//updates the send to console to hide the output of the pdg command
		AcideMainWindow
				.getInstance().getConsolePanel()
				.getProcessThread().getOutputGobbler()
				.set_sendToConsole(false);

		//calls to the /pdg command and waits to the finalization
		String command;
		if (AcideMainWindow.getInstance().getGraphPanel().getRDG().isSelected()) {
			//sets the flag to generate the graph to true.
			AcideConsolePDGTask.getInstance().setGenerateGraph(false);
			//sets the flag to generate the graph to true.
			AcideConsoleRDGTask.getInstance().setGenerateGraph(true);
			command = "/rdg";
			AcideMainWindow
					.getInstance().getConsolePanel()
					.sendCommandToConsole(command, "");
			AcideMainWindow.getInstance().getConsolePanel()
					.getProcessThread().getOutputGobbler()
					.waitForTaskDone(1000);
			EventQueue.invokeLater(new Runnable() {

				@Override
				public void run() {
					//waits to the finalization of the generation of the graph,
					//restore the send to console flag and updates the generate graph flag.
					AcideConsoleRDGTask.getInstance().run();
					AcideConsoleRDGTask.getInstance().waitForTaskDone(1000);
					AcideMainWindow
							.getInstance().getConsolePanel()
							.getProcessThread().getOutputGobbler()
							.set_sendToConsole(sendToConsole);
					AcideConsoleRDGTask.getInstance().setGenerateGraph(false);
				}
			});

			AcideGraphPanel._canvas.setZoom(1, CanvasPanel.Graph);
		}
		else{
			//sets the flag to generate the graph to true.
			AcideConsoleRDGTask.getInstance().setGenerateGraph(false);
			//sets the flag to generate the graph to true.
			AcideConsolePDGTask.getInstance().setGenerateGraph(true);
			command = "/pdg";
			AcideMainWindow
					.getInstance().getConsolePanel()
					.sendCommandToConsole(command, "");
			AcideMainWindow.getInstance().getConsolePanel()
					.getProcessThread().getOutputGobbler()
					.waitForTaskDone(1000);
			EventQueue.invokeLater(new Runnable() {

				@Override
				public void run() {
					//waits to the finalization of the generation of the graph,
					//restore the send to console flag and updates the generate graph flag.
					AcideConsolePDGTask.getInstance().waitForTaskDone(1000);
					AcideMainWindow
							.getInstance().getConsolePanel()
							.getProcessThread().getOutputGobbler()
							.set_sendToConsole(sendToConsole);
					AcideConsolePDGTask.getInstance().setGenerateGraph(false);
				}
			});

			AcideGraphPanel._canvas.setZoom(1, CanvasPanel.Graph);
		}
	}

}
