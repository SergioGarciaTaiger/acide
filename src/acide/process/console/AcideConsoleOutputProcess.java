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
 *      -Version from 0.12 to 0.16
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
package acide.process.console;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Date;

import javax.swing.JTextPane;

import acide.gui.consolePanel.AcideConsolePanel;
import acide.log.AcideLog;

/**
 * <p>
 * Handles the ACIDE - A Configurable IDE output process thread.
 * </p>
 * <p>
 * Prints the output of the ACIDE - A Configurable IDE console panel process
 * into the text pane of the ACIDE - A Configurable IDE console panel.
 * </p>
 * 
 * @version 0.11
 * @see Thread
 */
public class AcideConsoleOutputProcess extends Thread {

	/**
	 * ACIDE - A Configurable IDE input stream.
	 */
	private InputStream _inputStream;
	/**
	 * ACIDE - A Configurable IDE console panel.
	 */
	private AcideConsolePanel _consolePanel;
	/**
	 * ACIDE - A Configurable IDE
	 */
	private StringBuffer _stringBuffer;

	/**
	 * ACIDE - boolean to select the output
	 */
	private boolean _sendToConsole;

	/**
	 * ACIDE- text to display
	 */
	private String _text;
	/**
	 * ACIDE - A Configurable IDE indication of complete task.
	 */
	private boolean _taskDone;


	/**
	 * Creates a new ACIDE - A Configurable IDE console process thread.
	 * 
	 * @param inputStream
	 *            input stream.
	 * @param consolePanel
	 *            ACIDE - A Configurable IDE console panel.
	 * @see InputStream
	 * @see AcideConsolePanel
	 */
	public AcideConsoleOutputProcess(InputStream inputStream,AcideConsolePanel consolePanel) {

		// Stores the input stream
		_inputStream = inputStream;

		// Stores the console panel
		_consolePanel = consolePanel;

		_sendToConsole=true;

		_text="KO";
		
		_taskDone=true;
	}

	public AcideConsoleOutputProcess(InputStream errorStream,JTextPane consolePanel) {}

	public AcideConsoleOutputProcess() {}

	public void set_sendToConsole(boolean sendToConsole) {
		_sendToConsole = sendToConsole;
	}

	public synchronized boolean waitForOKResult(long millis) {
		Date startingToWaitAt=new Date();
		boolean received=false;
		while (!received && (new Date().getTime()-startingToWaitAt.getTime())<millis){
			try {
				wait(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			received= !_text.equalsIgnoreCase("KO");
		}
		return received;
	}

	public synchronized boolean waitForDebuggingStart(long millis){
		Date startingToWaitAt=new Date();
		boolean received=false;
		while (!received && (new Date().getTime()-startingToWaitAt.getTime())<millis){
			try {
				wait(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			received= !_text.equalsIgnoreCase("KO") && (_text.contains("$eot") ||
					_text.contains("Debugging view") || _text.contains("Buggy view"));
		}
		return received;
	}

	private synchronized void newTextReady(){
		notifyAll();
	}

	public boolean get_sendToConsole(){
		return _sendToConsole;
	}

	public String getText() {
		return _text;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Thread#run()
	 */
	@Override
	public  void run() {
		_taskDone = false;
		try {
			// Creates the input stream reader
			InputStreamReader inputStreamReader = new InputStreamReader(
					_inputStream);

			// Creates the buffered reader
			BufferedReader bufferedReader = new BufferedReader(
					inputStreamReader);

			// Creates the string buffer
			_stringBuffer = new StringBuffer(1024);

			int character = 0;

			boolean hasNewText = false;

			while ((character = bufferedReader.read()) != -1) {
				// If it is not the carriage return
				if (character != 13){
					_stringBuffer.append((char) character);
					hasNewText = true;
				}
				// When the buffer reader is empty
				if (!bufferedReader.ready() && hasNewText) {
					if(_sendToConsole){
						// Adds the text to the console panel
						_consolePanel.addText(_stringBuffer.toString());
						//System.out.println(_stringBuffer.toString());
					}
					else{
						_text=_stringBuffer.toString();
						newTextReady();
					}
					// Clears the buffer
					_stringBuffer.delete(0, _stringBuffer.length());					
					hasNewText = false;
				}
			}
		} catch (Exception exception) {
			// Updates the log
			AcideLog.getLog().error(exception.getMessage());
			exception.printStackTrace();
		}
		endTask();
	}
	/**
	 * finalizes the execution.
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
	 * Returns the ACIDE - A Configurable IDE output process string buffer.
	 * @return the ACIDE - A Configurable IDE output process string buffer.
	 */
	public StringBuffer getStringBuffer(){
		return _stringBuffer;
	}
	
	/**
	 * Sets a new value to the ACIDE - A Configurable IDE output process string buffer.
	 * @param text the new value to set.
	 */
	public void set_text(String text) {
		this._text=text;
	}
}
