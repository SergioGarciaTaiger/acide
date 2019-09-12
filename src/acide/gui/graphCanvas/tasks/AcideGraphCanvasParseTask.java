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
package acide.gui.graphCanvas.tasks;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import acide.configuration.project.AcideProjectConfiguration;
import acide.gui.debugPanel.debugCanvas.exceptions.AcideDebugCanvasParseInputEqualsErrorException;
import acide.gui.graphCanvas.AcideGraphCanvas;
import acide.gui.mainWindow.AcideMainWindow;
/**
 * ACIDE - A Configurable IDE graph canvas parse task.
 * 
 */
public class AcideGraphCanvasParseTask implements Runnable {
	/**
	 * parse the /rdg command output method.
	 */
	public static String PARSE_RDG="parse_rdg";
	/**
	 * parse the /pdg command output method.
	 */
	public static String PARSE_PDG="parse_pdg";
	/**
	 * parse the /tapi /rdg command output method.
	 */
	public static String PARSE_TAPI_RDG="parse_tapi_rdg";
	/**
	 * parse the /tapi /pdg command output method.
	 */
	public static String PARSE_TAPI_PDG="parse_tapi_pdg";
	/**
	 * ACIDE - A Configurable IDE graph canvas parse task input to parse.
	 */
	private String _input;
	/**
	 * ACIDE - A Configurable IDE graph canvas parse task method
	 */
	private String _method;
	/**
	 * Creates a new ACIDE - A Configurable IDE graph canvas parse task. 
	 * @param input text to parse.
	 * @param method method to parse the input.
	 */
	public AcideGraphCanvasParseTask(String input,String method){
		this._input=input;
		this._method=method;
	}
	/*
	 * (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		//Shows the graph panel
		InputStream in =  new ByteArrayInputStream(_input.getBytes());
		if (!AcideProjectConfiguration.getInstance().isGraphPanelShowed()) {
			AcideMainWindow.getInstance().getGraphPanel().showGraphPanel();
			AcideProjectConfiguration.getInstance().setIsGraphPanelShowed(true);
			// Updates the show graph panel check box menu item state
			AcideMainWindow
					.getInstance()
					.getMenu()
					.getViewMenu()
					.getShowGraphPanelCheckBoxMenuItem()
					.setSelected(
							AcideProjectConfiguration.getInstance()
									.isGraphPanelShowed());
			
			// If it is not the default project
			if (!AcideProjectConfiguration.getInstance().isDefaultProject())

				// The project has been modified
				AcideProjectConfiguration.getInstance().setIsModified(true);
		}
		//parses the result and generates the graph
		AcideGraphCanvas canvas = AcideGraphCanvas.getInstance();
		if(_method.equals(PARSE_PDG) || _method.equals(PARSE_RDG))
			canvas.set_graph(AcideGraphCanvas.parseGraph(in));
		if(_method.equals(PARSE_TAPI_PDG) || _method.equals(PARSE_TAPI_RDG))
			canvas.set_graph(AcideGraphCanvas.parseGraphTapi(in));
		canvas.repaint();

	}

}
