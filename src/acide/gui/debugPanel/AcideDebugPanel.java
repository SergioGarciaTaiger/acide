/*
 * ACIDE - A Configurable IDE
 * Official web site: http://acide.sourceforge.net
 * 
 * Copyright (C) 2007-2013  
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
package acide.gui.debugPanel;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;

import acide.configuration.project.AcideProjectConfiguration;
import acide.gui.debugPanel.listeners.AcideDebugPanelTabbedPaneChangeListener;
import acide.gui.debugPanel.traceDatalogPanel.AcideTraceDatalogPanel;
import acide.gui.debugPanel.traceSQLPanel.AcideTraceSQLPanel;
import acide.gui.debugPanel.debugSQLPanel.AcideDebugSQLPanel;
import acide.gui.mainWindow.AcideMainWindow;
import acide.language.AcideLanguageManager;

/**
 * ACIDE - A Configurable IDE debug panel.
 * 
 * @version 0.13
 * @see JPanel
 */
public class AcideDebugPanel extends JPanel {

	/**
	 * ACIDE - A Configurable IDE debug panel class serial version UID.
	 */
	private static final long serialVersionUID = 1L;
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
	 * ACIDE - A Configurable IDE menu bar of the debug panel.
	 */
	private JMenuBar _menuBar;
	/**
	 * ACIDE - A Configurable IDE debug panel default size.
	 */
	private int DEFAULT_SIZE = 400;
	/**
	 * ACIDE - A Configurable IDE debug panel tabbed pane.
	 */
	private JTabbedPane _tabbedPane;
	/**
	 * ACIDE - A Configurable IDE debug panel trace datalog panel.
	 */
	private AcideTraceDatalogPanel _traceDatalogPanel;
	/**
	 * ACIDE - A Configurable IDE debug panel trace datalog panel index.
	 */
	private int _traceDatalogPanelIndex;
	/**
	 * ACIDE - A Configurable IDE debug panel trace SQL panel index.
	 */
	private int _traceSQLPanelIndex;
	/**
	 * ACIDE - A Configurable IDE debug panel trace SQL panel.
	 */
	private AcideTraceSQLPanel _traceSQLPanel;
	/**
	 * ACIDE - A Configurable IDE debug panel trace SQL panel index.
	 */
	private int _debugSQLPanelIndex;
	/**
	 * ACIDE - A Configurable IDE debug panel trace SQL panel.
	 */
	private AcideDebugSQLPanel _debugSQLPanel;
	
	
	/**
	 * Creates a new ACIDE - A Configurable IDE debug panel.
	 */
	public AcideDebugPanel() {
		// Sets the layout of the panel
		setLayout(new BorderLayout());
		// Builds the debug panel menu bar
		buildMenuBar();
		// Builds the tabbed pane
		buildTabbedPane();
		// Sets the default split panel container
		_splitContainer = "horizontalGraphSplitPanel";
		// Adds the menu bar
		add(_menuBar, BorderLayout.NORTH);
		// Adds the tabbed pane
		add(_tabbedPane, BorderLayout.CENTER);

		setVisible(true);
	}

	/**
	 * Builds the tabbed pane assigned to the ACIDE - A Configurable IDE Debug
	 * Panel
	 */
	public void buildTabbedPane() {
		_tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		
		// creates the trace datalog panel
		_traceDatalogPanel = new AcideTraceDatalogPanel();
		// adds the trace datalog panel
		_tabbedPane.addTab(AcideLanguageManager.getInstance().getLabels()
				.getString("s2275"), _traceDatalogPanel);
		// updates the index of the trace datalog panel
		setTraceDatalogPanelIndex(0);
		
		_traceSQLPanel = new AcideTraceSQLPanel();
		//  adds the trace SQL Panel
		_tabbedPane.addTab(AcideLanguageManager.getInstance().getLabels()
				.getString("s2276"), _traceSQLPanel);
		setTraceSQLPanelIndex(1);
		
		_debugSQLPanel = new AcideDebugSQLPanel();
		//  adds the trace SQL Panel
		_tabbedPane.addTab(AcideLanguageManager.getInstance().getLabels()
				.getString("s2277"), _debugSQLPanel);
		setDebugSQLPanelIndex(2);
		
		// Adds the change Listener to the tabbed pane
		_tabbedPane.addChangeListener(new AcideDebugPanelTabbedPaneChangeListener());

	}

	/**
	 * Builds the menu bar assigned to the ACIDE - A Configurable IDE Debug
	 * Panel
	 */
	public void buildMenuBar() {

		_menuBar = new JMenuBar();

		// Creates the icon for the panel
		JLabel menu = new JLabel();
		menu.setIcon(new ImageIcon(
				"./resources/icons/panels/showDebugPanel.png"));
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
				disposeDebugPanel();
				AcideProjectConfiguration.getInstance().setIsDebugPanelShowed(
						false);
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
		});
		// Sets the margin of the close button
		close.setMargin(new Insets(0, 0, 0, 0));
		// Aligns the button to the right margin
		_menuBar.add(Box.createHorizontalGlue());
		// Adds the close button
		_menuBar.add(close);
		// Sets the name of the panel
		_menuBar.setName("AcideDebugPanel");
	}

	/**
	 * Gets the ACIDE - A Configurable IDE debu panel menu bar
	 * 
	 * @return the manu bar added in the debug panel
	 */
	public JMenuBar getMenuBar() {
		return _menuBar;
	}

	/**
	 * Sets the text of the menu bar assigned to the ACIDE - A Configurable IDE
	 * Debug Panel
	 */
	public void setTextMenuBar() {
		String name = AcideLanguageManager.getInstance().getLabels()
				.getString("s2243");
		((JLabel) _menuBar.getComponent(1)).setText(" " + name);
		if (_tabbedPane != null) {
			if (_tabbedPane.getTitleAt(_traceDatalogPanelIndex) != null)
				_tabbedPane.setTitleAt(_traceDatalogPanelIndex,
						AcideLanguageManager.getInstance().getLabels()
								.getString("s2275"));
			if(_traceDatalogPanel!=null)
				_traceDatalogPanel.setComponentsText();
			if (_tabbedPane.getTitleAt(_traceSQLPanelIndex) != null)
				_tabbedPane.setTitleAt(_traceSQLPanelIndex, AcideLanguageManager
						.getInstance().getLabels().getString("s2276"));
			if (_tabbedPane.getTitleAt(_debugSQLPanelIndex) != null)
				_tabbedPane.setTitleAt(_debugSQLPanelIndex, AcideLanguageManager
						.getInstance().getLabels().getString("s2277"));
			
		}
	}

	/**
	 * Gets the ACIDE - A Configurable IDE debug panel split container
	 * 
	 * @return name of the split which contains the debug panel
	 */
	public String getSplitContainer() {
		return _splitContainer;
	}

	/**
	 * Sets the ACIDE - A Configurable IDE debug panel split container
	 * 
	 * @param splitContainer
	 *            name of the new debug panel split panel
	 */
	public void setSplitContainer(String splitContainer) {
		this._splitContainer = splitContainer;
	}

	/**
	 * Gets the ACIDE - A Configurable IDE split panel position
	 * 
	 * @return position in the split panel which contains the debug panel
	 */
	public String getSplitPosition() {
		return _splitPosition;
	}

	/**
	 * Sets the ACIDE - A Configurable IDE debug panel position in the split
	 * panel container
	 * 
	 * @param splitPosition
	 *            position within the debug panel split panel container
	 */
	public void splitPosition(String splitPosition) {
		this._splitPosition = splitPosition;
	}

	/**
	 * Shows the ACIDE - A Configurable IDE debug panel
	 */
	public void showDebugPanel() {

		if (_size <= 0)
			_size = DEFAULT_SIZE;

		JSplitPane container = AcideMainWindow.getInstance()
				.getSpecificSplitPane(_splitContainer);
		container.setDividerLocation(_size);
		setVisible(true);
		_traceDatalogPanel.getHighLighter().highLight();
		AcideMainWindow.getInstance().updateVisibility();
	}

	/**
	 * Hides the ACIDE - A Configurable IDE debug panel
	 */
	public void disposeDebugPanel() {

		JSplitPane container = AcideMainWindow.getInstance()
				.getSpecificSplitPane(_splitContainer);
		_size = container.getDividerLocation();
		if (container.getLeftComponent().getClass() == this.getClass()) {
			if (!container.getRightComponent().isVisible())
				_size = 300;
		} else if (container.getRightComponent().getClass() == this.getClass())
			if (!container.getLeftComponent().isVisible())
				_size = 300;
		_traceDatalogPanel.getHighLighter().unHighLight();
		setVisible(false);

		AcideMainWindow.getInstance().updateVisibility();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.awt.Component#getMinimumSize()
	 */
	@Override
	public Dimension getMinimumSize() {
		return new Dimension(120, 50);
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.awt.Component#getName()
	 */
	@Override
	public String getName() {
		return "AcideDebugPanel";
	}

	/**
	 * Returns the ACIDE - A Configurable IDE debug panel trace datalog panel.
	 * 
	 * @return the ACIDE - A Configurable IDE debug panel trace datalog panel.
	 */
	public AcideTraceDatalogPanel getTraceDatalogPanel() {
		return _traceDatalogPanel;
	}

	/**
	 * Sets a new value to the ACIDE - A Configurable IDE debug panel trace
	 * datalog panel.
	 * 
	 * @param traceDatalogPanel
	 *            new value to set.
	 */
	public void setTraceDatalogPanel(AcideTraceDatalogPanel traceDatalogPanel) {
		this._traceDatalogPanel = traceDatalogPanel;
	}

	/**
	 * Returns the ACIDE - A Configurable IDE debug panel trace SQL panel.
	 * 
	 * @return the ACIDE - A Configurable IDE debug panel trace SQL panel.
	 */
	public AcideTraceSQLPanel getTraceSQLPanel() {
		return _traceSQLPanel;
	}

	/**
	 * Sets a new value to the ACIDE - A Configurable IDE debug panel trace
	 * SQL panel.
	 * 
	 * @param traceSQLPanel
	 *            new value to set.
	 */
	public void setTraceSQLPanel(AcideTraceSQLPanel traceSQLPanel) {
		this._traceSQLPanel = traceSQLPanel;
	}
	
	/**
	 * Returns the ACIDE - A Configurable IDE debug panel trace SQL panel.
	 * 
	 * @return the ACIDE - A Configurable IDE debug panel trace SQL panel.
	 */
	public AcideDebugSQLPanel getDebugSQLPanel() {
		return _debugSQLPanel;
	}

	/**
	 * Sets a new value to the ACIDE - A Configurable IDE debug panel trace
	 * SQL panel.
	 * 
	 * @param traceSQLPanel
	 *            new value to set.
	 */
	public void setDebugSQLPanel(AcideDebugSQLPanel debugSQLPanel) {
		this._debugSQLPanel = debugSQLPanel;
	}

	/**
	 * Returns the ACIDE - A Configurable IDE debug panel trace datalog panel index.
	 * 
	 * @return the ACIDE - A Configurable IDE debug panel trace datalog panel index.
	 */
	public int getTraceDatalogPanelIndex() {
		return _traceDatalogPanelIndex;
	}

	/**
	 * Sets a new value to the ACIDE - A Configurable IDE debug panel trace
	 * datalog panel index.
	 * 
	 * @param traceDatalogPanelIndex
	 *            new value to set.
	 */
	public void setTraceDatalogPanelIndex(int traceDatalogPanelIndex) {
		this._traceDatalogPanelIndex = traceDatalogPanelIndex;
	}

	/**
	 * Returns the ACIDE - A Configurable IDE debug panel trace SQL panel index.
	 * 
	 * @return the ACIDE - A Configurable IDE debug panel trace SQL panel index.
	 */
	public int getTraceSQLPanelIndex() {
		return _traceSQLPanelIndex;
	}

	/**
	 * Sets a new value to the ACIDE - A Configurable IDE debug panel trace
	 * SQL panel index.
	 * 
	 * @param debugSQLPanelIndex
	 *            new value to set.
	 */
	public void setDebugSQLPanelIndex(int debugSQLPanelIndex) {
		this._debugSQLPanelIndex = debugSQLPanelIndex;
	}
	
	/**
	 * Returns the ACIDE - A Configurable IDE debug panel debug SQL panel index.
	 * 
	 * @return the ACIDE - A Configurable IDE debug panel debug SQL panel index.
	 */
	public int getDebugSQLPanelIndex() {
		return _debugSQLPanelIndex;
	}

	/**
	 * Sets a new value to the ACIDE - A Configurable IDE debug panel trace
	 * SQL panel index.
	 * 
	 * @param traceSQLPanelIndex
	 *            new value to set.
	 */
	public void setTraceSQLPanelIndex(int traceSQLPanelIndex) {
		this._traceSQLPanelIndex = traceSQLPanelIndex;
	}
	
	/**
	 * Returns the ACIDE - A Configurable IDE debug panel tabbed pane.
	 * 
	 * @return the ACIDE - A Configurable IDE debug panel tabbed pane.
	 */
	public JTabbedPane getTabbedPane() {
		return _tabbedPane;
	}

}
