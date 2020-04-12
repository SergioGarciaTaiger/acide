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
package acide.gui.debugPanel.traceSQLPanel;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.util.ArrayList;
import java.util.LinkedList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;

import acide.gui.debugPanel.debugCanvas.AcideDebugCanvas;
import acide.gui.debugPanel.traceSQLPanel.listeners.AcideTraceSQLPanelNodeListener;
import acide.gui.debugPanel.traceSQLPanel.listeners.AcideTraceSQLPanelLastNodeListener;
import acide.gui.debugPanel.traceSQLPanel.listeners.AcideTraceSQLPanelNexNodeListener;
import acide.gui.debugPanel.traceSQLPanel.listeners.AcideTraceSQLPanelPreviousNodeListener;
import acide.gui.debugPanel.traceSQLPanel.listeners.AcideTraceSQLPanelRefreshListener;
import acide.gui.debugPanel.traceSQLPanel.listeners.AcideTraceSQLPanelShowLabelsListener;
import acide.gui.debugPanel.traceSQLPanel.listeners.AcideTraceSQLPanelViewBoxListener;
import acide.gui.debugPanel.traceSQLPanel.listeners.AcideTraceSQLPanelZoomInListener;
import acide.gui.debugPanel.traceSQLPanel.listeners.AcideTraceSQLPanelZoomOutListener;
import acide.gui.debugPanel.traceSQLPanel.listeners.AcideTraceSQLPanelZoomSpinnerListener;
import acide.gui.debugPanel.utils.AcideDebugPanelHighLighter;
import acide.gui.mainWindow.AcideMainWindow;
import acide.language.AcideLanguageManager;
import acide.process.console.DesDatabaseManager;

/**
 * 
 * ACIDE - A Configurable IDE trace SQL panel.
 * 
 * @version 0.15
 * @see JPanel
 */
public class AcideTraceSQLPanel extends JPanel {

	/**
	 * ACIDE - A Configurable IDE trace SQL panel class serial version UID.
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * ACIDE - A Configurable IDE trace SQL panel debug canvas.
	 */
	public static AcideDebugCanvas _canvas;
	/**
	 * ACIDE - A Configurable IDE trace SQL panel button panel.
	 */
	private JPanel _mainButtonPanel;
	/**
	 * ACIDE - A Configurable IDE trace SQL panel zoom spinner.
	 */
	private static JSpinner _zoomSpinner;
	/**
	 * ACIDE - A Configurable IDE trace SQL panel show label check box.
	 */
	private JCheckBox _showLabelsMenuItem;
	/**
	 * ACIDE - A Configurable IDE trace SQL panel show rules check box.
	 */
	private JCheckBox _showSQLMenuItem;

	/**
	 * ACIDE - A Configurable IDE trace SQL panel view box.
	 */
	private JComboBox _viewBox;
	/**
	 * ACIDE - A Configurable IDE trace SQL panel query.
	 */
	private String _query;
	/**
	 * ACIDE - A Configurable IDE trace SQL panel high lighter.
	 */
	private AcideDebugPanelHighLighter _highLighter;
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
	 * ACIDE - A Configurable IDE trace SQL panel to the right button icon
	 */
	private final static ImageIcon TO_THE_RIGHT_IMAGE = new ImageIcon(
			"./resources/icons/menu/configuration/menu/right_arrow.png");
	/**
	 * ACIDE - A Configurable IDE trace SQL panel to the left button icon
	 */
	private final static ImageIcon TO_THE_LEFT_IMAGE = new ImageIcon(
			"./resources/icons/menu/configuration/menu/left_arrow.png");
	/**
	 * ACIDE - A Configurable IDE trace SQL panel add button icon
	 */
	private final static ImageIcon ADD_IMAGE = new ImageIcon("./resources/icons/graph/add.png");
	/**
	 * ACIDE - A Configurable IDE trace SQL panel minus button icon
	 */
	private final static ImageIcon MINUS_IMAGE = new ImageIcon("./resources/icons/graph/minus.png");
	/**
	 * ACIDE - A Configurable IDE trace SQL panel refresh button icon
	 */
	private final static ImageIcon REFRESH_IMAGE = new ImageIcon("./resources/icons/panels/refresh.png");
	
	// builds the refresh button
	public static JButton refreshSQL = new JButton();
	
	private static AcideMainWindow acideWindow;
			

	public AcideTraceSQLPanel() {
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

		setHighLighter(new AcideDebugPanelHighLighter());
		
		//AcideTraceSQLPanel.acideWindow = AcideMainWindow.getInstance();

	}

	/**
	 * Builds the buttons for the ACIDE - A Configurable IDE trace SQL panel.
	 */
	private void buildButtons() {
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
		refreshSQL.setIcon(REFRESH_IMAGE);
		refreshSQL.setPreferredSize(new Dimension((int) (1.5 * refreshSQL.getIcon()
				.getIconWidth()), (int) refreshSQL.getPreferredSize().getHeight()));
		// adds the action listener to the refresh button
		refreshSQL.addActionListener(new AcideTraceSQLPanelRefreshListener());
		//sets tooltip button 
		refreshSQL.setToolTipText(AcideLanguageManager.getInstance().getLabels().getString("s2044"));
		//unable the button
		refreshSQL.setEnabled(false);
		// adds the refresh button
		subButtonPanel1.add(refreshSQL);
		// creates the spinner model for the zoom spinner
		SpinnerModel model = new SpinnerNumberModel(
				(int) _canvas.getZoom() * 100, 0, Integer.MAX_VALUE, 1);
		// creates the zoom spinner with the model
		_zoomSpinner = new JSpinner(model);
		((JSpinner.DefaultEditor) _zoomSpinner.getEditor()).getTextField()
				.setColumns(4);
		// adds the action listener to the zoom spinner
		_zoomSpinner
				.addChangeListener(new AcideTraceSQLPanelZoomSpinnerListener());
		subButtonPanel1.add(_zoomSpinner);
		// creates the zoom in button
		JButton zoomin = new JButton(ADD_IMAGE);
		zoomin.setPreferredSize(new Dimension((int) (1.5 * zoomin.getIcon()
				.getIconWidth()), (int) zoomin.getPreferredSize().getHeight()));
		// adds the zoom in action listener
		zoomin.addActionListener(new AcideTraceSQLPanelZoomInListener());
		// adds the zoom in button to the button panel
		subButtonPanel1.add(zoomin);
		// creates the zoom out button
		JButton zoomout = new JButton(MINUS_IMAGE);
		zoomout.setPreferredSize(new Dimension((int) (1.5 * zoomout.getIcon()
				.getIconWidth()), (int) zoomout.getPreferredSize().getHeight()));
		// adds the zoom out action listener
		zoomout.addActionListener(new AcideTraceSQLPanelZoomOutListener());
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
				.addActionListener(new AcideTraceSQLPanelShowLabelsListener());
		// adds the show labels check box to the button panel
		subButtonPanel1.add(_showLabelsMenuItem);
		// creates the sub button panel 2
		JPanel subButtonPanel2 = new JPanel();
		// adds the layout to the sub button panel
		subButtonPanel2.setLayout(new FlowLayout());
		// adds the sub button panel to the main button panel
		_mainButtonPanel.add(subButtonPanel2, BorderLayout.SOUTH);
		// Builds the view label
		JLabel viewLabel = new JLabel(AcideLanguageManager.getInstance().getLabels().getString("s2287"));
		viewLabel.setFont(viewLabel.getFont().deriveFont(10f));
		subButtonPanel2.add(viewLabel);
		// Builds the views combo box
		_viewBox = new JComboBox();
		_viewBox.addItem("          ");
		// Adds the listeners to the views combo box
		_viewBox.addActionListener(new AcideTraceSQLPanelViewBoxListener());
		_viewBox.addFocusListener(new FocusAdapter() {
			/*
			 * (non-Javadoc)
			 * @see java.awt.event.FocusAdapter#focusGained(java.awt.event.FocusEvent)
			 */
			@Override
			public void focusGained(FocusEvent e) {
				
				// Puts the wait cursor
				//AcideTraceSQLPanel.acideWindow.setCursor(new Cursor(Cursor.WAIT_CURSOR));
				
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
						views.add(AcideLanguageManager.getInstance()
								.getLabels().getString("s2287"));
						break;
					}
					//Checks if the output has ended
					if (s.equals("$eot"))
						break;
					// Adds the actual's view name to the list
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
				viewBox.setPopupVisible(false);
				viewBox.setPopupVisible(true);
				
				// Puts the default cursor
				//AcideTraceSQLPanel.acideWindow.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));

			}
		});
		subButtonPanel2.add(_viewBox);
		// Creates the first node button
				JButton firstNodeButton = new JButton(TO_THE_FIRST_IMAGE);
				firstNodeButton.setPreferredSize(new Dimension(
						(int) (1.5 * firstNodeButton.getIcon().getIconWidth()),
						(int) firstNodeButton.getPreferredSize().getHeight()));
				// adds the action listener to the first node button
				firstNodeButton
						.addActionListener(new AcideTraceSQLPanelNodeListener());
				// adds the firs node button to the button panel
				subButtonPanel2.add(firstNodeButton);
		// creates the previous node button
		JButton prevNodeButton = new JButton(TO_THE_LEFT_IMAGE);
		prevNodeButton.setPreferredSize(new Dimension((int) (1.5 * prevNodeButton.getIcon()
				.getIconWidth()), (int) prevNodeButton.getPreferredSize().getHeight()));
		// adds the action listener to the previous node button
		prevNodeButton
				.addActionListener(new AcideTraceSQLPanelPreviousNodeListener());
		// adds the previous node button to the button panel
		subButtonPanel2.add(prevNodeButton);
		// creates the next node button
		JButton nextNodeButton = new JButton(TO_THE_RIGHT_IMAGE);
		nextNodeButton.setPreferredSize(new Dimension((int) (1.5 * nextNodeButton.getIcon()
				.getIconWidth()), (int) nextNodeButton.getPreferredSize().getHeight()));
		// adds the action listener to the next node button
		nextNodeButton
				.addActionListener(new AcideTraceSQLPanelNexNodeListener());
		// adds the next node button to the button panel
		subButtonPanel2.add(nextNodeButton);
		// Creates the last node button
				JButton lastNodeButton = new JButton(TO_THE_LAST_IMAGE);
				lastNodeButton.setPreferredSize(new Dimension(
						(int) (1.5 * lastNodeButton.getIcon().getIconWidth()),
						(int) lastNodeButton.getPreferredSize().getHeight()));
				// adds the action listener to the last node button
				lastNodeButton
						.addActionListener(new AcideTraceSQLPanelLastNodeListener());
				// adds the last node button to the button panel
				subButtonPanel2.add(lastNodeButton);
		// Builds the show rules check box
		_showSQLMenuItem = new JCheckBox();
		// sets the default selected option
		_showSQLMenuItem.setSelected(false);
		// sets the text of the show rules check box
		_showSQLMenuItem.setText(AcideLanguageManager.getInstance().getLabels()
				.getString("s2141"));

		_showSQLMenuItem.setFont(_showSQLMenuItem.getFont().deriveFont(10f));
		// showRulesMenuItem.addActionListener(arg0)
		subButtonPanel2.add(_showSQLMenuItem);
	}

	/**
	 * Builds the canvas of the the ACIDE - A Configurable IDE trace SQL panel.
	 */
	private void buildCanvas() {
		this._canvas = new AcideDebugCanvas();
		_canvas.setBounds(this.getBounds());
		_canvas.setVisible(true);
		_canvas.repaint();
	}

	/**
	 * Returns the ACIDE - A Configurable IDE debug panel trace SQL panel
	 * canvas.
	 * 
	 * @return the ACIDE - A Configurable IDE debug panel trace SQL panel
	 *         canvas.
	 */
	public AcideDebugCanvas getCanvas() {
		return _canvas;
	}

	/**
	 * Sets a new value to the ACIDE - A Configurable IDE trace SQL panel
	 * canvas.
	 * 
	 * @param canvas
	 *            new value to set.
	 */
	public void setCanvas(AcideDebugCanvas canvas) {
		this._canvas = canvas;
	}

	/**
	 * Returns the ACIDE - A Configurable IDE debug panel trace SQL panel view
	 * box.
	 * 
	 * @return the ACIDE - A Configurable IDE debug panel trace SQL panel view
	 *         box.
	 */
	public JComboBox getViewBox() {
		return _viewBox;
	}

	/**
	 * Sets a new value to the ACIDE - A Configurable IDE trace SQL panel view
	 * box.
	 * 
	 * @param viewBox
	 *            new value to set.
	 */
	public void setViewBox(JComboBox viewBox) {
		this._viewBox = viewBox;
	}

	/**
	 * Returns the ACIDE - A Configurable IDE debug panel trace SQL panel zoom
	 * spinner.
	 * 
	 * @return the ACIDE - A Configurable IDE debug panel trace SQL panel zoom
	 *         spinner.
	 */
	public static JSpinner getZoomSpinner() {
		return _zoomSpinner;
	}

	/**
	 * Sets a new value to the ACIDE - A Configurable IDE trace SQL panel zoom
	 * spinner.
	 * 
	 * @param zoomSpinner
	 *            new value to set.
	 */
	public void setZoomSpinner(JSpinner zoomSpinner) {
		this._zoomSpinner = zoomSpinner;
	}

	/**
	 * Returns the ACIDE - A Configurable IDE debug panel trace SQL panel show
	 * labels item.
	 * 
	 * @return the ACIDE - A Configurable IDE debug panel trace SQL panel show
	 *         labels item.
	 */
	public JCheckBox getShowLabelsMenuItem() {
		return _showLabelsMenuItem;
	}

	/**
	 * Sets a new value to the ACIDE - A Configurable IDE trace SQL panel show
	 * labels item.
	 * 
	 * @param showLabelsMenuItem
	 *            new value to set.
	 */
	public void setShowLabelsMenuItem(JCheckBox showLabelsMenuItem) {
		this._showLabelsMenuItem = showLabelsMenuItem;
	}

	/**
	 * Returns the ACIDE - A Configurable IDE debug panel trace SQL panel show
	 * SQL item.
	 * 
	 * @return the ACIDE - A Configurable IDE debug panel trace SQL panel show
	 *         SQL item.
	 */
	public JCheckBox getShowSQLMenuItem() {
		return _showSQLMenuItem;
	}

	/**
	 * Sets a new value to the ACIDE - A Configurable IDE trace SQL panel show
	 * SQL item.
	 * 
	 * @param showSQLsMenuItem
	 *            new value to set.
	 */
	public void setShowSQLMenuItem(JCheckBox showSQLsMenuItem) {
		this._showSQLMenuItem = showSQLsMenuItem;
	}

	/**
	 * Returns the ACIDE - A Configurable IDE debug panel trace SQL panel query.
	 * 
	 * @return the ACIDE - A Configurable IDE debug panel trace SQL panel query.
	 */
	public String getQuery() {
		return _query;
	}

	/**
	 * Sets a new value to the ACIDE - A Configurable IDE trace SQL panel query.
	 * 
	 * @param query
	 *            new value to set.
	 */
	public void setQuery(String query) {
		this._query = query;
	}

	/**
	 * Returns the the ACIDE - A Configurable IDE trace SQL panel highligther.
	 * 
	 * @return the the ACIDE - A Configurable IDE trace datalog highligther.
	 */
	public AcideDebugPanelHighLighter getHighLighter() {
		return _highLighter;
	}

	/**
	 * Sets a new value to the the the ACIDE - A Configurable IDE trace sql
	 * panel highligther.
	 * 
	 * @param highLighter
	 *            the new value to set.
	 */
	public void setHighLighter(AcideDebugPanelHighLighter highLighter) {
		this._highLighter = highLighter;
	}

	/**
	 * Returns the ACIDE - A Configurable IDE trace SQL canvas.
	 * 
	 * @return the ACIDE - A Configurable IDE trace SQL canvas.
	 */
	public AcideDebugCanvas getDebugSQLCanvas() {
		return _canvas;
	}

}
