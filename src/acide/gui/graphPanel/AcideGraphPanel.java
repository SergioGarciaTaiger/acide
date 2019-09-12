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
package acide.gui.graphPanel;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JSplitPane;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;

import acide.configuration.project.AcideProjectConfiguration;
import acide.gui.graphCanvas.AcideGraphCanvas;
import acide.gui.graphPanel.listeners.AcideGraphPanelRefreshListener;
import acide.gui.graphPanel.listeners.AcideGraphPanelShowLabelsListener;
import acide.gui.graphPanel.listeners.AcideGraphPanelZoomInListener;
import acide.gui.graphPanel.listeners.AcideGraphPanelZoomOutListener;
import acide.gui.graphPanel.listeners.AcideGraphPanelZoomSpinnerListener;
import acide.gui.mainWindow.AcideMainWindow;
import acide.language.AcideLanguageManager;

/**
 * ACIDE - A Configurable IDE graph panel.
 * 
 * @version 0.12
 * @see JPanel
 */
public class AcideGraphPanel extends JPanel {

	/**
	 * ACIDE - A Configurable IDE graph panel class serial version UID.
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * ACIDE - A Configurable IDE canvas.
	 */
	public static AcideGraphCanvas _canvas;
	/**
	 * ACIDE - A Configurable IDE size of the window.
	 */
	private int _size;
	/**
	 * ACIDE - A Configurable IDE name of the split panel container.
	 */
	private String _splitContainer;
	/**
	 * ACIDE - A Configurable IDE position on the split panel.
	 */
	private String _splitPosition;
	/**
	 * ACIDE - A Configurable IDE menu bar of the graph panel.
	 */
	private JMenuBar _menuBar;
	/**
	 * ACIDE - A Configurable IDE panel for the zoom buttons.
	 */
	private JPanel _buttonPanel;
	/**
	 * ACIDE - A Configurable IDE zoom spinner.
	 */
	private JSpinner _zoomSpinner;
	/**
	 * ACIDE - A Configurable IDE show labels item.
	 */
	private JCheckBox _showLabelsMenuItem;
	/**
	 * ACIDE - A Configurable IDE graph panel add button icon
	 */
	private final static ImageIcon ADD_IMAGE = new ImageIcon("./resources/icons/graph/add.png");
	/**
	 * ACIDE - A Configurable IDE graph panel minus button icon
	 */
	private final static ImageIcon MINUS_IMAGE = new ImageIcon("./resources/icons/graph/minus.png");
	/**
	 * ACIDE - A Configurable IDE graph panel refresh button icon
	 */
	private final static ImageIcon REFRESH_IMAGE = new ImageIcon("./resources/icons/panels/refresh.png");

	// creates the refresh button
	public static JButton refreshPDG = new JButton();
	
			
	
	/**
	 * Creates a new ACIDE - A Configurable IDE graph panel.
	 */
	public AcideGraphPanel() {
		// Sets the layout of the panel
		setLayout(new BorderLayout());
		// Builds the menu bar
		buildMenuBar();
		// Builds the canvas
		buildCanvas();
		// Builds the button panel
		buildButtons();

		// Sets the default position
		_splitContainer = "verticalSplitPanel";
		_splitPosition = "right";

		// Adds the components to the panel
		add(_menuBar, BorderLayout.NORTH);

		add(_canvas, BorderLayout.CENTER);

		add(_buttonPanel, BorderLayout.SOUTH);

	}

	/**
	 * Builds the menu bar assigned to the ACIDE - A Configurable IDE Graph
	 * Panel
	 */
	public void buildMenuBar() {

		_menuBar = new JMenuBar();

		// Creates the icon for the panel
		JLabel menu = new JLabel();
		menu.setIcon(new ImageIcon(
				"./resources/icons/menu/view/showGraphPanel.png"));
		// Adds the icon to the panel
		_menuBar.add(menu);

		// Creates the label for the name of the panel
		JLabel name = new JLabel();
		// Adds the label of the name of the panel
		_menuBar.add(name);
		// Sets the text of the name of the panel
		setTextMenuBar();

		// Creates the close view button
		JButton close = new JButton();
		// Sets the icon of the close view button
		close.setIcon(new ImageIcon(
				"./resources/icons/panels/closeViewPanel.png"));
		// Adds the listener of the close button
		close.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent actionEvent) {
				disposeGraphPanel();
				AcideProjectConfiguration.getInstance().setIsGraphPanelShowed(false);
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
		});
		// Sets the margin of the close button
		close.setMargin(new Insets(0, 0, 0, 0));
		// Aligns the button to the right margin
		_menuBar.add(Box.createHorizontalGlue());
		// Adds the close button
		_menuBar.add(close);

		_menuBar.setName("AcideGraphPanel");

	}

	/**
	 * Sets the text of the menu bar assigned to the ACIDE - A Configurable IDE
	 * Graph Panel
	 */
	public void setTextMenuBar() {
		String name = AcideLanguageManager.getInstance().getLabels()
				.getString("s2231");
		((JLabel) _menuBar.getComponent(1)).setText(" " + name);
		if (_showLabelsMenuItem != null)
			_showLabelsMenuItem.setText(AcideLanguageManager.getInstance()
					.getLabels().getString("s2263"));
	}

	/**
	 * Builds the ACIDE - A Configurable IDE canvas for the dependencies graph
	 */
	public void buildCanvas() {
		_canvas = AcideGraphCanvas.getInstance();
		_canvas.setBounds(this.getBounds());
	}

	/**
	 * Builds the ACIDE - A Configurable IDE buttons panel for the zoom buttons
	 */
	public void buildButtons() {
		// Creates the button panel
		_buttonPanel = new JPanel();
		// sets the layout of the button
		_buttonPanel.setLayout(new FlowLayout());
		// sets the icon of the button
		refreshPDG.setIcon(REFRESH_IMAGE);
		// sets the size of the button
		refreshPDG.setPreferredSize(new Dimension((int)(1.5*refreshPDG.getIcon().getIconWidth()),(int) refreshPDG.getPreferredSize().getHeight()));
		// adds the action listener of the button
		refreshPDG.addActionListener(new AcideGraphPanelRefreshListener());
		//enable the refresh button
		refreshPDG.setEnabled(true);
		//sets tooltip button 
		refreshPDG.setToolTipText(AcideLanguageManager.getInstance().getLabels().getString("s2044"));
		// adds the refresh button
		_buttonPanel.add(refreshPDG);
		// creates the show labels check box
		_showLabelsMenuItem = new JCheckBox();
		// sets the intial value of the box
		_showLabelsMenuItem.setSelected(true);
		// sets the text of the box
		_showLabelsMenuItem.setText(AcideLanguageManager.getInstance()
				.getLabels().getString("s2263"));
		// sets the font of the check box
		_showLabelsMenuItem
				.setFont(_showLabelsMenuItem.getFont().deriveFont(10f));
		// adds the action listener of the box
		_showLabelsMenuItem
				.addActionListener(new AcideGraphPanelShowLabelsListener());
		// adds the box to the panel
		_buttonPanel.add(_showLabelsMenuItem);
		// creates the model of the zoom spinner
		SpinnerModel model = new SpinnerNumberModel((int) AcideGraphCanvas
				.getInstance().getZoom() * 100, 0, Integer.MAX_VALUE, 1);
		// creates the zoom spinner
		_zoomSpinner = new JSpinner(model);
		// sets the columns number of the spinner
		((JSpinner.DefaultEditor) _zoomSpinner.getEditor()).getTextField()
				.setColumns(3);
		// adds the listener to the spinner
		_zoomSpinner.addChangeListener(new AcideGraphPanelZoomSpinnerListener());
		// adds the spinner to the panel
		_buttonPanel.add(_zoomSpinner);
		// creates the zoom in button
		JButton zoomin = new JButton(ADD_IMAGE);
		// sets the size of the button
		zoomin.setPreferredSize(new Dimension((int) (1.5 * zoomin.getIcon()
				.getIconWidth()), (int) zoomin.getPreferredSize().getHeight()));
		// sets the font of the button
		zoomin.setFont(zoomin.getFont().deriveFont(10f));
		// adds the listener to the button
		zoomin.addActionListener(new AcideGraphPanelZoomInListener());
		// adds the button to the panel
		_buttonPanel.add(zoomin);
		// creates the zoom out button
		JButton zoomout = new JButton(MINUS_IMAGE);
		// sets the size of the button
		zoomout.setPreferredSize(new Dimension((int) (1.5 * zoomout.getIcon()
				.getIconWidth()), (int) zoomout.getPreferredSize().getHeight()));
		// adds the listener to the button
		zoomout.addActionListener(new AcideGraphPanelZoomOutListener());
		// adds the button to the panel
		_buttonPanel.add(zoomout);

	}

	/**
	 * Shows the ACIDE - A Configurable IDE graph panel
	 */
	public void showGraphPanel() {
		// gets the container of the panel
		JSplitPane container = AcideMainWindow.getInstance()
				.getSpecificSplitPane(_splitContainer);
		// divides the container
		container.setDividerLocation(_size);
		// shows the panel
		setVisible(true);
		// shows the componets of the panel
		AcideMainWindow.getInstance().updateVisibility();

	}

	/**
	 * Hides the ACIDE - A Configurable IDE graph panel
	 */
	public void disposeGraphPanel() {
		// gets the container of the panel
		JSplitPane container = AcideMainWindow.getInstance()
				.getSpecificSplitPane(_splitContainer);
		// gets the size of the panel
		_size = container.getDividerLocation();
		if (container.getLeftComponent().getClass() == this.getClass()) {
			if (!container.getRightComponent().isVisible())
				_size = 300;
		} else if (container.getRightComponent().getClass() == this.getClass())
			if (!container.getLeftComponent().isVisible())
				_size = 300;
		// disposes the panel
		setVisible(false);
		// disposes the components of the panel
		AcideMainWindow.getInstance().updateVisibility();
	}

	/**
	 * Gets the name of the ACIDE - A Configurable IDE container Split Panel of the graph panel
	 * 
	 * @return _splitContainer
	 */
	public String getSplitContainer() {
		return _splitContainer;
	}

	/**
	 * Sets the value of the ACIDE - A Configurable IDE container Split Panel
	 * 
	 * @param name
	 */
	public void setSplitContainer(String name) {
		_splitContainer = name;
	}

	/**
	 * Gets the ACIDE - A Configurable IDE split panel position which contains
	 * the graph panel
	 * 
	 * @return position in the split panel container
	 */
	public String getSplitPosition() {
		return _splitPosition;
	}

	/**
	 * Sets the ACIDE - A Configurable IDE split panel position
	 * 
	 * @param name
	 *            position in the split panel container
	 */
	public void setSplitPosition(String name) {
		_splitPosition = name;
	}

	/**
	 * Returns the ACIDE - A Configurable IDE menu bar assigned to the Graph
	 * 		   Panel
	 * 
	 * @return the ACIDE - A Configurable IDE menu bar assigned to the Graph
	 *         Panel
	 */
	public JMenuBar getMenuBar() {
		return _menuBar;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.awt.Component#getMinimumSize()
	 */
	@Override
	public Dimension getMinimumSize() {
		return new Dimension(120, 10);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.awt.Component#getPreferredSize()
	 */
	@Override
	public Dimension getPreferredSize() {
		return new Dimension(200, 120);
	}

	/**
	 * Returns the ACIDE - A Configurable IDE graph panel zoom spinner.
	 * 
	 * @return the ACIDE - A Configurable IDE graph panel zoom spinner.
	 * 
	 */
	public JSpinner getZoomSpinner() {
		return _zoomSpinner;
	}

	/**
	 * Sets a new value to the ACIDE - A Configurable IDE graph panel zoom
	 * spinner.
	 * 
	 * @param zoomSpinner
	 *            new value to set.
	 */
	public void setZoomSpinner(JSpinner zoomSpinner) {
		this._zoomSpinner = zoomSpinner;
	}

	/**
	 * Returns the ACIDE - A Configurable IDE graph panel show labels item.
	 * 
	 * @return the ACIDE - A Configurable IDE graph panel show labels item.
	 * 
	 */
	public JCheckBox getShowLabelsMenuItem() {
		return _showLabelsMenuItem;
	}

	/**
	 * Sets a new value to the ACIDE - A Configurable IDE graph panel show
	 * labels item.
	 * 
	 * @param showLabelsMenuItem
	 *            new value to set.
	 */
	public void setShowLabelsMenuItem(JCheckBox showLabelsMenuItem) {
		this._showLabelsMenuItem = showLabelsMenuItem;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.awt.Component#getName()
	 */
	@Override
	public String getName() {
		return "AcideGraphPanel";
	}

}
