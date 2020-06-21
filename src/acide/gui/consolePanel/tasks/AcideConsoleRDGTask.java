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
package acide.gui.consolePanel.tasks;

import java.awt.EventQueue;
import java.util.Date;

import acide.configuration.project.AcideProjectConfiguration;
import acide.gui.graphCanvas.tasks.AcideGraphCanvasParseTask;
import acide.gui.mainWindow.AcideMainWindow;
/**
 * ACIDE - A Configurable IDE console RDG task.
 * @see Runnable
 */
public class AcideConsoleRDGTask implements Runnable {
	/**
	 * ACIDE - A Configurable IDE rdg task unique class instance.
	 */
	private static AcideConsoleRDGTask _instance;
	/**
	 * ACIDE - A Configurable IDE indication of complete task.
	 */
	private boolean _taskDone;
	/**
	 * ACIDE - A Configurable IDE flag to generate the graph.
	 */
	private boolean _generateGraph;
	
	/**
	 * Returns the unique ACIDE - A Configurable IDE rdg task class instance.
	 * 
	 * @return the unique ACIDE - A Configurable IDE rdg task class instance.
	 */
	public static AcideConsoleRDGTask getInstance(){
		if(_instance==null)
			_instance=new AcideConsoleRDGTask();
		return _instance;
	}
	
	/**
	 * Creates a new ACIDE - A Configurable IDE rdg task.
	 */
	private AcideConsoleRDGTask(){
		_taskDone=true;
		_generateGraph=false;
	}
	
	/*
	 * (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		_taskDone=false;
		final String []result = new String[1];
		AcideMainWindow.getInstance().getConsolePanel().getProcessThread().getOutputGobbler().waitForTaskDone(1000);
		//gets the result of the command from the console
		if(AcideMainWindow.getInstance().getConsolePanel().getProcessThread().getOutputGobbler().get_sendToConsole()){
			//String []output = AcideMainWindow.getInstance().getConsolePanel().getContent().toString().split("DES>");
			String []output = AcideMainWindow.getInstance().getConsolePanel().getContent().toString().split("$eot");
			result[0] = output[output.length-2];
			result[0] = result[0].replaceAll("/rdg", "");
		}else
			result[0] = AcideMainWindow.getInstance().getConsolePanel().getProcessThread().getOutputGobbler().getText().toString();
		if(AcideProjectConfiguration.getInstance().isGraphPanelShowed()) {
			try {
				EventQueue.invokeLater(new AcideGraphCanvasParseTask(result[0]
						, AcideGraphCanvasParseTask.PARSE_RDG));
			} catch (Exception e) {
				e.printStackTrace();
			}
			endTask();
		}
		
	}
	/**
	 * finalizes the execution of the rdg task.
	 */
	private synchronized void endTask(){
		//updates the indication of the finalization
		_taskDone=true;
		//wakes all the waiting threads
		notifyAll();
	}
	
	/**
	 * Waits for the finalization of the execution of the thread.
	 * @param millis max time to wait in milliseconds.
	 * @return true if the execution has finalized, false otherwise.
	 */
	public synchronized boolean waitForTaskDone(long millis){
		//gets the start time to uses as reference
		Date startingToWaitAt=new Date();
		//while task is not complete and the time passed is less than the max waits 100 milliseconds.
		while(!_taskDone && (new Date().getTime()-startingToWaitAt.getTime())<millis){
			try {
				wait(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		//returns the finalization of the task
		return _taskDone;
	}
	
	/**
	 * return the ACIDE - A Configurable IDE flag to generate the graph.
	 * @return the ACIDE - A Configurable IDE flag to generate the graph.
	 */
	public boolean isGenerateGraph() {
		return _generateGraph;
	}
	
	/**
	 * Sets a new value to the ACIDE - A Configurable IDE flag to generate the graph.
	 * 
	 * @param generateGraph new value to set.
	 */
	public void setGenerateGraph(boolean generateGraph) {
		this._generateGraph = generateGraph;
	}

	/**
	 * return the ACIDE - A Configurable IDE task done flag.
	 * @return the ACIDE - A Configurable IDE task done flag.
	 */
	public boolean is_taskDone() {
		return _taskDone;
	}

	/**
	 * Sets a new value to the ACIDE - A Configurable IDE task done flag.
	 * 
	 * @param generateGraph new value to set.
	 */
	public void set_taskDone(boolean _taskDone) {
		this._taskDone = _taskDone;
	}

}
