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
package acide.gui.debugPanel.listeners;

import java.awt.Cursor;
import java.util.ArrayList;
import java.util.LinkedList;

import javax.swing.JComboBox;
import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import acide.gui.databasePanel.dataView.menuBar.editMenu.gui.AcideDataViewReplaceWindow;
import acide.gui.debugPanel.utils.AcideDebugPanelHighLighter;
import acide.gui.mainWindow.AcideMainWindow;
import acide.process.console.DesDatabaseManager;

/**
 * 
 * ACIDE - A Configurable IDE debug panel tabbed pane change listener
 * 
 * @version 0.15
 * @see ChangeListener
 */
public class AcideDebugPanelTabbedPaneChangeListener implements ChangeListener {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * javax.swing.event.ChangeListener#stateChanged(javax.swing.event.ChangeEvent
	 * )
	 */
	@Override
	public void stateChanged(ChangeEvent ev) {
		
		// Puts the wait cursor
		AcideDataViewReplaceWindow.getInstance().setCursor(
			Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
					
		// Gets the selected tab
		JTabbedPane sourceTabbedPane = (JTabbedPane) ev.getSource();
		// Checks if the selected tab is the trace datalog tab
		if (sourceTabbedPane.getSelectedIndex() == AcideMainWindow
				.getInstance().getDebugPanel().getTraceDatalogPanelIndex()) {
			// Gets the highlighter
			AcideDebugPanelHighLighter highLighter = AcideMainWindow
					.getInstance().getDebugPanel().getTraceDatalogPanel()
					.getHighLighter();
			// Unhighlights the previous highlights
			highLighter.unHighLight();
			// Checks if the highlight is activated an if there is a sellected
			// node on the canvas
			if (highLighter.isHighlight()
					&& AcideMainWindow.getInstance().getDebugPanel()
							.getTraceDatalogPanel().getCanvas()
							.getSelectedNode() != null) {
				// hihglights the selected node
				highLighter.highLight(AcideMainWindow.getInstance()
						.getDebugPanel().getTraceDatalogPanel().getCanvas()
						.getSelectedNode().getLabel());

			}

		}
		// Checks if the selected tab is the trace SQL tab
		if (sourceTabbedPane.getSelectedIndex() == AcideMainWindow
				.getInstance().getDebugPanel().getTraceSQLPanelIndex()) {
			// Gets the highlighter
			AcideDebugPanelHighLighter highLighter = AcideMainWindow
					.getInstance().getDebugPanel().getTraceDatalogPanel()
					.getHighLighter();
			// Unighlits the preivous highlight
			highLighter.unHighLight();
			// Checks if the highlight is activated an if there is a sellected
			// node on the canvas
			if (highLighter.isHighlight()
					&& AcideMainWindow.getInstance().getDebugPanel()
							.getTraceSQLPanel().getCanvas().getSelectedNode() != null) {
				// hihglights the selected node
				highLighter.highLight(AcideMainWindow.getInstance()
						.getDebugPanel().getTraceSQLPanel().getCanvas()
						.getSelectedNode().getLabel());

			}
			// Gets the views from the database
			LinkedList<String> l = DesDatabaseManager.getInstance()
					.executeCommand("/tapi /list_views");
			ArrayList<String> views = new ArrayList<String>();
			views.add("          ");
			// Parses the output from the database
			for (String s : l) {
				// Checks if the output is an error output
				if (s.equals("$error")) {
					//Resets the list of views
					views = new ArrayList<String>();
					views.add("          ");
					break;
				}
				// Checks if the output has ended
				if (s.equals("$eot"))
					break;
				//Adds the actual's view name to the list
				views.add(s);
			}
			// Gets the trace datalog panel list of views
			JComboBox viewBox = AcideMainWindow.getInstance()
					.getDebugPanel().getTraceSQLPanel().getViewBox();
			// Removes the previous views
			viewBox.removeAllItems();
			// Adds the new views
			for (String item : views)
				viewBox.addItem(item);
			
			// Puts the default cursor
			AcideDataViewReplaceWindow.getInstance().setCursor(
				Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
						

		}

	}
}
