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
 *      -Version from 0.12 to 0.16
 *      	- Semíramis Gutiérrez Quintana
 *      	- Juan Jesús Marqués Ortiz
 *      	- Fernando Ordás Lorente
 *      -Version from 0.16
 *      	- Sergio Dominguez Fuentes
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
package acide.gui.menuBar.fileMenu.listeners;

import acide.configuration.grammar.AcideGrammarConfiguration;
import acide.configuration.lexicon.AcideLexiconConfiguration;
import acide.files.project.AcideProjectFileType;
import acide.gui.mainWindow.AcideMainWindow;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;

import javax.swing.JPanel;

import acide.language.AcideLanguageManager;
import acide.log.AcideLog;


/**
 * ACIDE - A Configurable IDE file menu new file menu item listener.
 * 
 * @version 0.11
 * @see ActionListener
 */
public class AcideNewFileMenuItemListener implements ActionListener {
	static Calendar dateOld = Calendar.getInstance();
	static Calendar dateNew = Calendar.getInstance();
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent
	 * )
	 */
	@Override
	public void actionPerformed(ActionEvent actionEvent) {
		action(actionEvent);

	}
	
	private static int _rep=0;
	
	public static void initReps(){
		_rep = 0;
	}
	
	/*
	 * Function for create a new file
	 *--there are an error in the code; when you execute the order, 
	 *--the event crate 3 events. The var rep is a solution to "jump" this error
	 *--but when you enter File->New, there aren't any error, and the command doesn't run
	 * */
	public static void action(ActionEvent actionEvent) {
	
		// Updates the tabbed pane in the file editor manager
		String name = AcideLanguageManager.getInstance().getLabels().getString("s79");

		int index = 1;
		if (_rep != 0) {
			dateNew = Calendar.getInstance();
			if (dateOld.get(Calendar.MINUTE) != dateNew.get(Calendar.MINUTE)
					|| (dateOld.get(Calendar.SECOND) != dateNew.get(Calendar.SECOND))) {
				int newDoc = AcideMainWindow.getInstance()
						.getFileEditorManager()
						.getFileEditorPanelAt(name + index);
				while ((newDoc != -1) && (index < 25)) {
					index += 1;
					newDoc = AcideMainWindow.getInstance()
							.getFileEditorManager()
							.getFileEditorPanelAt(name + index);
				}
				dateOld = Calendar.getInstance();
				createNewDoc(name + index);
			}
		}
		else{
			int newDoc = AcideMainWindow.getInstance()
					.getFileEditorManager()
					.getFileEditorPanelAt(name + index);
			while ((newDoc != -1) && (index < 25)) {
				index += 1;
				newDoc = AcideMainWindow.getInstance()
						.getFileEditorManager()
						.getFileEditorPanelAt(name + index);
			}
			dateOld = Calendar.getInstance();
			createNewDoc(name + index);
			_rep = 1;
		}

		// Updates the log
		AcideLog.getLog().info(AcideLanguageManager.getInstance().getLabels().getString("s80"));
	}

	private static void createNewDoc(String name) {
				// Creates the lexicon configuration
				AcideLexiconConfiguration lexiconConfiguration = new AcideLexiconConfiguration();

				// Loads the lexicon configuration
				lexiconConfiguration.load(AcideLexiconConfiguration.DEFAULT_PATH
						+ AcideLexiconConfiguration.DEFAULT_NAME);

				// Creates the current grammar configuration
				AcideGrammarConfiguration currentGrammarConfiguration = new AcideGrammarConfiguration();

				// Sets the current grammar configuration path
				currentGrammarConfiguration
						.setPath(AcideGrammarConfiguration.DEFAULT_FILE);

				// Creates the previous grammar configuration
				AcideGrammarConfiguration previousGrammarConfiguration = new AcideGrammarConfiguration();

				// Sets the previous grammar configuration path
				previousGrammarConfiguration.setPath(AcideGrammarConfiguration.DEFAULT_FILE);

		
		
	AcideMainWindow
		.getInstance()
		.getFileEditorManager()
		.updateTabbedPane(name, "", true,
				AcideProjectFileType.NORMAL, 0, 0, 1,
				lexiconConfiguration, currentGrammarConfiguration,
				previousGrammarConfiguration);
			
	}
}
