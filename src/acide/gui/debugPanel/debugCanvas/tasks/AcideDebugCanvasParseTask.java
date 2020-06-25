/*
 * ACIDE - A Configurable IDE
 * Official web site: http://acide.sourceforge.net
 * 
 * Copyright (C) 2007-2013  
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
package acide.gui.debugPanel.debugCanvas.tasks;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;

import acide.configuration.project.AcideProjectConfiguration;
import acide.gui.debugPanel.debugCanvas.AcideDebugCanvas;
import acide.gui.debugPanel.debugCanvas.exceptions.AcideDebugCanvasParseInputEqualsErrorException;
import acide.gui.debugPanel.debugSQLPanel.debugSQLConfiguration.AcideDebugConfiguration;
import acide.gui.debugPanel.utils.AcideDebugHelper;
import acide.gui.debugPanel.utils.AcideDebugPanelErrorMessageDialog;
import acide.gui.graphUtils.DirectedWeightedGraph;
import acide.gui.graphUtils.Node;
import acide.gui.mainWindow.AcideMainWindow;
import acide.language.AcideLanguageManager;

/**
 * ACIDE - A Configurable IDE debug canvas parse task.
 * 
 * @version 0.15
 * @see Runnable
 * 
 */
public class AcideDebugCanvasParseTask implements Runnable {
	/**
	 * ACIDE - A Configurable IDE debug canvas parse task class parse tapi constant.
	 */
	public static String PARSE_TAPI_RDG = "parse_tapi_rdg";
	/**
	 * ACIDE - A Configurable IDE debug canvas parse task class parse tapi constant.
	 */
	public static String PARSE_TAPI_PDG = "parse_tapi_pdg";
	/**
	 * ACIDE - A Configurable IDE debug canvas parse task class main destiny constant.
	 */
	public static String DESTINY_MAIN = "main";
	/**
	 * ACIDE - A Configurable IDE debug canvas parse task class path destiny constant.
	 */
	public static String DESTINY_PATH = "path";

	/**
	 * ACIDE - A Configurable IDE debug canvas parse task input to parse.
	 */
	private String _input;
	/**
	 * ACIDE - A Configurable IDE debug canvas parse task method.
	 */
	private String _method;
	/**
	 * ACIDE - A Configurable IDE debug canvas parse task destiny canvas.
	 */
	private AcideDebugCanvas _canvas;
	/**
	 * ACIDE - A Configurable IDE debug canvas parse task destiny graph type.
	 */
	private String _destiny;
	/**
	 * ACIDE - A Configurable IDE debug canvas parse success flag.
	 */
	private boolean _success;
	/**
	 * ACIDE - A Configurable IDE debug canvas show error messaage flag.
	 */
	private boolean _showErrorMessage;

	/**
	 * Creates a new ACIDE - A Configurable IDE debug canvas parse task.
	 * 
	 * @param input
	 *            text to parse.
	 * @param method
	 *            method to parse the input.
	 */
	public AcideDebugCanvasParseTask(String input, String method,
			AcideDebugCanvas canvas, String destiny, String query,boolean showErrorMessage) {
		this._input = input;
		this._method = method;
		this._canvas = canvas;
		this._destiny = destiny;
		this._showErrorMessage=showErrorMessage;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		InputStream in = new ByteArrayInputStream(_input.getBytes());
		if (!AcideProjectConfiguration.getInstance().isDebugPanelShowed()) {
			AcideMainWindow.getInstance().getDebugPanel().showDebugPanel();
			AcideProjectConfiguration.getInstance().setIsDebugPanelShowed(true);
			// Updates the show debug panel check box menu item state
			AcideMainWindow
					.getInstance()
					.getMenu()
					.getViewMenu()
					.getShowDebugPanelCheckBoxMenuItem()
					.setSelected(
							AcideProjectConfiguration.getInstance()
									.isDebugPanelShowed());

			// If it is not the default project
			if (!AcideProjectConfiguration.getInstance().isDefaultProject())

				// The project has been modified
				AcideProjectConfiguration.getInstance().setIsModified(true);
		}
		// // parses the result and generates the graph
		if (_method.equals(PARSE_TAPI_PDG) || _method.equals(PARSE_TAPI_RDG)) {
			if (_destiny.equals(DESTINY_PATH))
				try {
					// parses the input to obtain the graph
					ArrayList<Node> g = AcideDebugCanvas
							.parsePathGraphTapi(in);
					// sets the path graph
					_canvas.setPathGraph(g);
					// updates the success flag
					_success = true;
				} catch (AcideDebugCanvasParseInputEqualsErrorException e) {
					// sets empty graphs on the path and the main graph of the canvas
					_canvas.set_graph(new DirectedWeightedGraph());
					_canvas.setPathGraph(new ArrayList<Node>());
					_canvas.repaint();
					if(_showErrorMessage){
					// shows the error message
					new AcideDebugPanelErrorMessageDialog(AcideLanguageManager
							.getInstance().getLabels().getString("s157"),
							e.getMessage());
					}
					// updates the succes flag
					_success = false;
				}
			if (_destiny.equals(DESTINY_MAIN)){
				// parses the input to obtain the graph
				_canvas.set_graph(AcideDebugCanvas.parseGraphTapi(in));
				// sets the main graph
				_success = true;
			}
		}
		AcideDebugHelper.paintTrustedTables(_canvas);
	}

	/**
	 * Returns the new ACIDE - A Configurable IDE debug canvas parse task success flag.
	 * @return the new ACIDE - A Configurable IDE debug canvas parse task success flag.
	 */
	public boolean isSuccess() {
		return _success;
	}

	/**
	 * Sets a new value to the new ACIDE - A Configurable IDE debug canvas parse task success flag.
	 * @param success the new value to set
	 */
	public void setSuccess(boolean success) {
		this._success = success;
	}

}
