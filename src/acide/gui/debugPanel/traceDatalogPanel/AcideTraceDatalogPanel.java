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
package acide.gui.debugPanel.traceDatalogPanel;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.AbstractButton;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;

import acide.gui.debugPanel.debugCanvas.AcideDebugCanvas;
import acide.gui.debugPanel.traceDatalogPanel.listeners.AcideTraceDatalogPanelFirstNodeListener;
import acide.gui.debugPanel.traceDatalogPanel.listeners.AcideTraceDatalogPanelLastNodeListener;
import acide.gui.debugPanel.traceDatalogPanel.listeners.AcideTraceDatalogPanelLocateListener;
import acide.gui.debugPanel.traceDatalogPanel.listeners.AcideTraceDatalogPanelNexNodeListener;
import acide.gui.debugPanel.traceDatalogPanel.listeners.AcideTraceDatalogPanelPreviousNodeListener;
import acide.gui.debugPanel.traceDatalogPanel.listeners.AcideTraceDatalogPanelRefreshListener;
import acide.gui.debugPanel.traceDatalogPanel.listeners.AcideTraceDatalogPanelShowLabelsListener;
import acide.gui.debugPanel.traceDatalogPanel.listeners.AcideTraceDatalogPanelShowRulesListener;
import acide.gui.debugPanel.traceDatalogPanel.listeners.AcideTraceDatalogPanelZoomInListener;
import acide.gui.debugPanel.traceDatalogPanel.listeners.AcideTraceDatalogPanelZoomOutListener;
import acide.gui.debugPanel.traceDatalogPanel.listeners.AcideTraceDatalogPanelZoomSpinnerListener;
import acide.gui.debugPanel.utils.AcideDebugPanelHighLighter;
import acide.gui.debugPanel.utils.AcideQueryDialog;
import acide.language.AcideLanguageManager;

/**
 * ACIDE - A Configurable IDE trace datalog panel.
 * 
 * @version 0.15
 * @see JPanel
 */
public class AcideTraceDatalogPanel extends JPanel {

	/**
	 * ACIDE - A Configurable IDE trace datalog panel class serial version UID.
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * ACIDE - A Configurable IDE trace datalog panel debug canvas.
	 */
	public static AcideDebugCanvas _canvas;
	/**
	 * ACIDE - A Configurable IDE trace datalog panel button panel.
	 */
	private JPanel _mainButtonPanel;
	/**
	 * ACIDE - A Configurable IDE trace datalog panel zoom spinner.
	 */
	private static JSpinner _zoomSpinner;
	/**
	 * ACIDE - A Configurable IDE trace datalog panel show label check box.
	 */
	private JCheckBox _showLabelsMenuItem;
	/**
	 * ACIDE - A Configurable IDE trace datalog panel query button.
	 */
	private JButton _datalogQueryButton;
	/**
	 * ACIDE - A Configurable IDE trace datalog panel query.
	 */
	private String _query;
	/**
	 * ACIDE - A Configurable IDE trace datalog panel show rules check box.
	 */
	private JCheckBox _showRulesMenuItem;
	/**
	 * ACIDE - A Configurable IDE trace datalog panel high lighter.
	 */
	private AcideDebugPanelHighLighter _highLighter;
	/**
	 * ACIDE - A Configurable IDE trace datalog panel locate buttton.
	 */
	private JButton _locateButton;
	/**
	 * ACIDE - A Configurable IDE trace datalog panel to the first button icon
	 */
	private final static ImageIcon TO_THE_FIRST_IMAGE = new ImageIcon(
			"./resources/icons/menu/configuration/menu/double_left_arrow.png");
	/**
	 * ACIDE - A Configurable IDE trace datalog panel to the last button icon
	 */
	private final static ImageIcon TO_THE_LAST_IMAGE = new ImageIcon(
			"./resources/icons/menu/configuration/menu/double_right_arrow.png");
	/**
	 * ACIDE - A Configurable IDE trace datalog panel to the right button icon
	 */
	private final static ImageIcon TO_THE_RIGHT_IMAGE = new ImageIcon(
			"./resources/icons/menu/configuration/menu/right_arrow.png");
	/**
	 * ACIDE - A Configurable IDE trace datalog panel to the left button icon
	 */
	private final static ImageIcon TO_THE_LEFT_IMAGE = new ImageIcon(
			"./resources/icons/menu/configuration/menu/left_arrow.png");
	/**
	 * ACIDE - A Configurable IDE trace datalog panel add button icon
	 */
	private final static ImageIcon ADD_IMAGE = new ImageIcon(
			"./resources/icons/graph/add.png");
	/**
	 * ACIDE - A Configurable IDE trace datalog panel minus button icon
	 */
	private final static ImageIcon MINUS_IMAGE = new ImageIcon(
			"./resources/icons/graph/minus.png");
	/**
	 * ACIDE - A Configurable IDE trace datalog panel refresh button icon
	 */
	private final static ImageIcon REFRESH_IMAGE = new ImageIcon(
			"./resources/icons/panels/refresh.png");
	
	// builds the refresh button
	public static JButton refreshDatalog = new JButton();
			
	/**
	 * Creates a new ACIDE - A Configurable IDE trace datalog panel.
	 */
	public AcideTraceDatalogPanel() {
		// Sets the layout of the panel
		setLayout(new BorderLayout());
		// Builds the canvas
		buildCanvas();
		// Builds the button panel
		buildButtons();
		// Adds the canvas
		add(_canvas, BorderLayout.CENTER);
		// Adds the button panel
		add(_mainButtonPanel, BorderLayout.SOUTH);
		// Builds the high lighter
		setHighLighter(new AcideDebugPanelHighLighter());
	}

	/**
	 * Builds the canvas of the the ACIDE - A Configurable IDE trace datalog
	 * panel.
	 */
	public void buildCanvas() {
		this._canvas = new AcideDebugCanvas();
		_canvas.setBounds(this.getBounds());
		_canvas.setVisible(true);
		_canvas.repaint();
		
	}

	
		
	
	/**
	 * Builds the buttons for the ACIDE - A Configurable IDE trace datalog
	 * panel.
	 */
	public void buildButtons() {
		// builds the button panel
		_mainButtonPanel = new JPanel();
		// adds the layout to the button panel
		_mainButtonPanel.setLayout(new BorderLayout());
		// creates the sub button panel 1
		JPanel subButtonPanel1 = new JPanel();
		// adds the layout to the sub button panel
		subButtonPanel1.setLayout(new FlowLayout());
		// adds the sub button panel to the main button panel
		_mainButtonPanel.add(subButtonPanel1, BorderLayout.NORTH);
		refreshDatalog.setIcon(REFRESH_IMAGE);
		refreshDatalog.setPreferredSize(new Dimension((int) (1.5 * refreshDatalog.getIcon()
				.getIconWidth()), (int) refreshDatalog.getPreferredSize().getHeight()));
		// adds the action listener to the refresh button
		refreshDatalog.addActionListener(new AcideTraceDatalogPanelRefreshListener());
		//sets tooltip button 
		refreshDatalog.setToolTipText(AcideLanguageManager.getInstance().getLabels().getString("s2044"));
		//unable the button
		refreshDatalog.setEnabled(false);
		// adds the refresh button
		subButtonPanel1.add(refreshDatalog);
		// creates the spinner model for the zoom spinner
		SpinnerModel model = new SpinnerNumberModel(
				(int) _canvas.getZoom() * 100, 0, Integer.MAX_VALUE, 1);
		// creates the zoom spinner with the model
		_zoomSpinner = new JSpinner(model);
		((JSpinner.DefaultEditor) _zoomSpinner.getEditor()).getTextField()
				.setColumns(4);
		// adds the action listener to the zoom spinner
		_zoomSpinner
				.addChangeListener(new AcideTraceDatalogPanelZoomSpinnerListener());
		// adds the zoom spinner to the button panel
		subButtonPanel1.add(_zoomSpinner);
		// creates the zoom in button
		JButton zoomin = new JButton(ADD_IMAGE);
		zoomin.setPreferredSize(new Dimension((int) (1.5 * zoomin.getIcon()
				.getIconWidth()), (int) zoomin.getPreferredSize().getHeight()));
		// adds the zoom in action listener
		zoomin.addActionListener(new AcideTraceDatalogPanelZoomInListener());
		// adds the zoom in button to the button panel
		subButtonPanel1.add(zoomin);
		// creates the zoom out button
		JButton zoomout = new JButton(MINUS_IMAGE);
		zoomout.setPreferredSize(new Dimension((int) (1.5 * zoomout.getIcon()
				.getIconWidth()), (int) zoomout.getPreferredSize().getHeight()));
		// adds the zoom out action listener
		zoomout.addActionListener(new AcideTraceDatalogPanelZoomOutListener());
		// adds the zoom out button to the button panel
		subButtonPanel1.add(zoomout);
		// builds the show labels check box
		_showLabelsMenuItem = new JCheckBox();
		// sets the default selected option
		_showLabelsMenuItem.setSelected(true);
		// sets the text of the show labels check box
		_showLabelsMenuItem.setText(AcideLanguageManager.getInstance()
				.getLabels().getString("s2263"));
		_showLabelsMenuItem
				.setFont(_showLabelsMenuItem.getFont().deriveFont(10f));
		// adds the action listener to the show labels check box
		_showLabelsMenuItem
				.addActionListener(new AcideTraceDatalogPanelShowLabelsListener());
		// adds the show labels check box to the button panel
		subButtonPanel1.add(_showLabelsMenuItem);
		// creates the sub button panel 2
		JPanel subButtonPanel2 = new JPanel();
		// adds the layout to the sub button panel
		subButtonPanel2.setLayout(new FlowLayout());
		// adds the sub button panel to the main button panel
		_mainButtonPanel.add(subButtonPanel2, BorderLayout.SOUTH);

		_datalogQueryButton = new JButton(AcideLanguageManager.getInstance()
				.getLabels().getString("s2278"));

		_datalogQueryButton.addActionListener(new ActionListener() {
			/*
			 * (non-Javadoc)
			 * 
			 * @see
			 * java.awt.event.ActionListener#actionPerformed(java.awt.event.
			 * ActionEvent)
			 */
			@Override
			public void actionPerformed(ActionEvent e) {
				new AcideQueryDialog(AcideLanguageManager.getInstance()
						.getLabels().getString("s2278"),
						AcideQueryDialog.QUERY_TYPE_DATALOG);
			}
		});
		subButtonPanel2.add(_datalogQueryButton);
		// Creates the first node button
		JButton firstNodeButton = new JButton(TO_THE_FIRST_IMAGE);
		firstNodeButton.setPreferredSize(new Dimension(
				(int) (1.5 * firstNodeButton.getIcon().getIconWidth()),
				(int) firstNodeButton.getPreferredSize().getHeight()));
		// adds the action listener to the first node button
		firstNodeButton
				.addActionListener(new AcideTraceDatalogPanelFirstNodeListener());
		// adds the first node button to the button panel
		subButtonPanel2.add(firstNodeButton);
		// creates the previous node button
		JButton prevNodeButton = new JButton(TO_THE_LEFT_IMAGE);
		prevNodeButton.setPreferredSize(new Dimension(
				(int) (1.5 * prevNodeButton.getIcon().getIconWidth()),
				(int) prevNodeButton.getPreferredSize().getHeight()));
		// adds the action listener to the previous node button
		prevNodeButton
				.addActionListener(new AcideTraceDatalogPanelPreviousNodeListener());
		// adds the previous node button to the button panel
		subButtonPanel2.add(prevNodeButton);
		// creates the next node button
		JButton nextNodeButton = new JButton(TO_THE_RIGHT_IMAGE);
		nextNodeButton.setPreferredSize(new Dimension(
				(int) (1.5 * nextNodeButton.getIcon().getIconWidth()),
				(int) nextNodeButton.getPreferredSize().getHeight()));
		// adds the action listener to the next node button
		nextNodeButton
				.addActionListener(new AcideTraceDatalogPanelNexNodeListener());
		// adds the next node button to the button panel
		subButtonPanel2.add(nextNodeButton);
		// Creates the last node button
		JButton lastNodeButton = new JButton(TO_THE_LAST_IMAGE);
		lastNodeButton.setPreferredSize(new Dimension(
				(int) (1.5 * lastNodeButton.getIcon().getIconWidth()),
				(int) lastNodeButton.getPreferredSize().getHeight()));
		// adds the action listener to the last node button
		lastNodeButton
				.addActionListener(new AcideTraceDatalogPanelLastNodeListener());
		// adds the last node button to the button panel
		subButtonPanel2.add(lastNodeButton);
		// Creates locate button
		_locateButton = new JButton(AcideLanguageManager.getInstance()
				.getLabels().getString("s2295"));
		_locateButton.setFont(_locateButton.getFont().deriveFont(10f));
		// adds the action listener to the locate button
		_locateButton.addActionListener(new AcideTraceDatalogPanelLocateListener());
		// adds the locate button to the button panel
		subButtonPanel2.add(_locateButton);
		// Builds the show rules check box
		_showRulesMenuItem = new JCheckBox();
		// sets the default selected option
		_showRulesMenuItem.setSelected(false);
		// sets the text of the show rules check box
		_showRulesMenuItem.setText(AcideLanguageManager.getInstance()
				.getLabels().getString("s2279"));

		_showRulesMenuItem.setFont(_showRulesMenuItem.getFont().deriveFont(10f));

		_showRulesMenuItem
				.addActionListener(new AcideTraceDatalogPanelShowRulesListener());
		// showRulesMenuItem.addActionListener(arg0)
		subButtonPanel2.add(_showRulesMenuItem);

	}

	/**
	 * Returns the ACIDE - A Configurable IDE trace datalog panel canvas.
	 * 
	 * @return the ACIDE - A Configurable IDE trace datalog panel canvas.
	 */
	public AcideDebugCanvas getCanvas() {
		return _canvas;
	}

	/**
	 * Sets a new value to the the ACIDE - A Configurable IDE trace datalog
	 * panel canvas.
	 * 
	 * @param canvas
	 *            the new value to set
	 */
	public void setCanvas(AcideDebugCanvas canvas) {
		this._canvas = canvas;
	}

	/**
	 * Returns the ACIDE - A Configurable IDE trace datalog panel show rules
	 * item.
	 * 
	 * @return the ACIDE - A Configurable IDE trace datalog panel show rules
	 *         item.
	 */
	public JCheckBox getShowRulesMenuItem() {
		return _showRulesMenuItem;
	}

	/**
	 * Sets a new value to the the ACIDE - A Configurable IDE show rules item.
	 * 
	 * @param showRulesMenuItem
	 *            the new value to set
	 */
	public void setShowRulesMenuItem(JCheckBox showRulesMenuItem) {
		this._showRulesMenuItem = showRulesMenuItem;
	}

	/**
	 * Returns the the ACIDE - A Configurable IDE trace datalog panel query
	 * text.
	 * 
	 * @return the the ACIDE - A Configurable IDE trace datalog panel query
	 *         text.
	 */
	public String getQuery() {
		return _query;
	}

	/**
	 * Sets a new value to the the the ACIDE - A Configurable IDE trace datalog
	 * panel query text.
	 * 
	 * @param query
	 *            the new value to set.
	 */
	public void setQuery(String query) {
		this._query = query;
	}

	/**
	 * Returns the the ACIDE - A Configurable IDE trace datalog panel
	 * highligther.
	 * 
	 * @return the the ACIDE - A Configurable IDE trace datalog panel
	 *         highligther.
	 */
	public AcideDebugPanelHighLighter getHighLighter() {
		return _highLighter;
	}

	/**
	 * Sets a new value to the the the ACIDE - A Configurable IDE trace datalog
	 * panel highligther.
	 * 
	 * @param highLighter
	 *            the new value to set.
	 */
	public void setHighLighter(AcideDebugPanelHighLighter highLighter) {
		this._highLighter = highLighter;
	}

	/**
	 * Returns the ACIDE - A Configurable IDE trace datalog panel zoom spinner.
	 * 
	 * @return the ACIDE - A Configurable IDE trace datalog panel zoom spinner.
	 */
	public static JSpinner getZoomSpinner() {
		return _zoomSpinner;
	}

	/**
	 * Sets a new value to the ACIDE - A Configurable IDE trace datalog panel
	 * zoom spinner.
	 * 
	 * @param zoomSpinner
	 *            the new value to set
	 */
	public void setZoomSpinner(JSpinner zoomSpinner) {
		this._zoomSpinner = zoomSpinner;
	}

	/**
	 * Returns the ACIDE - A Configurable IDE trace datalog canvas.
	 * 
	 * @return the ACIDE - A Configurable IDE trace datalog canvas.
	 */
	public AcideDebugCanvas getDebugDatalogCanvas() {
		return _canvas;
	}

	/**
	 * Sets the text of the components assigned to the ACIDE - A Configurable
	 * IDE trace datalog panel.
	 */
	public void setComponentsText() {
		_datalogQueryButton.setText(AcideLanguageManager.getInstance()
				.getLabels().getString("s2278"));
		_showLabelsMenuItem.setText(AcideLanguageManager.getInstance()
				.getLabels().getString("s2263"));
		_showRulesMenuItem.setText(AcideLanguageManager.getInstance()
				.getLabels().getString("s2279"));
		_locateButton.setText(AcideLanguageManager.getInstance()
				.getLabels().getString("s2295"));
	}

	/**
	 * Returns the ACIDE - A Configurable IDE trace datalog show labels.
	 * 
	 * @return the ACIDE - A Configurable IDE trace datalog show labels.
	 */	
	public JCheckBox getShowLabelsMenuItem() {
		return _showLabelsMenuItem;
	}

}
