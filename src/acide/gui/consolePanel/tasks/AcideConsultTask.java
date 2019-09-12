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
package acide.gui.consolePanel.tasks;

import java.awt.EventQueue;
import java.lang.reflect.InvocationTargetException;
import java.util.LinkedList;

import acide.gui.graphCanvas.tasks.AcideGraphCanvasParseTask;
import acide.process.console.AcideDatabaseManager;
import acide.process.console.DesDatabaseManager;

/**
 * 
 * ACIDE - A Configurable IDE consult task.
 * 
 * @version 0.15
 * 
 */
public class AcideConsultTask implements Runnable {

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		LinkedList<String> output = DesDatabaseManager.getInstance().executeCommand("/pdg");
		String result = "";
		for(String s: output)
			result+=s+System.getProperty("line.separator");
		try {
			System.out.println(result);
			EventQueue.invokeAndWait(new AcideGraphCanvasParseTask(result
					, AcideGraphCanvasParseTask.PARSE_PDG));
		} catch (InvocationTargetException e) {
			
			e.printStackTrace();
		} catch (InterruptedException e) {
			
			e.printStackTrace();
		}

	}

}
