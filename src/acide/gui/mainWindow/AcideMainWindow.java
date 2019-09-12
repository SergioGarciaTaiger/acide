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
package acide.gui.mainWindow;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JSplitPane;

import acide.configuration.project.AcideProjectConfiguration;
import acide.configuration.workbench.AcideWorkbenchConfiguration;
import acide.factory.gui.AcideGUIFactory;
import acide.gui.assertedDatabasePanel.AcideAssertedDatabasePanel;
import acide.gui.consolePanel.AcideConsolePanel;
import acide.gui.databasePanel.AcideDataBasePanel;
import acide.gui.debugPanel.AcideDebugPanel;
import acide.gui.explorerPanel.AcideExplorerPanel;
import acide.gui.fileEditor.fileEditorManager.AcideFileEditorManager;
import acide.gui.graphPanel.AcideGraphPanel;
import acide.gui.mainWindow.listeners.AcideMainWindowMouseListener;
import acide.gui.mainWindow.listeners.AcideMainWindowWindowListener;
import acide.gui.mainWindow.utils.AcideLastElementOnFocus;
import acide.gui.menuBar.AcideMenuBar;
import acide.gui.statusBarPanel.AcideStatusBar;
import acide.gui.toolBarPanel.AcideToolBarPanel;
import acide.language.AcideLanguageManager;
import acide.log.AcideLog;
import acide.resources.AcideResourceManager;

/**
 * ACIDE - A Configurable IDE main window.
 * 
 * @version 0.11
 * @see JFrame
 */
public class AcideMainWindow extends JFrame {

	/**
	 * ACIDE - A Configurable IDE main window class serial version UID.
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * ACIDE - A Configurable IDE main window image icon.
	 */
	private static final ImageIcon ICON = new ImageIcon(
			"./resources/images/icon.png");
	/**
	 * ACIDE - A Configurable IDE main window unique class instance.
	 */
	private static AcideMainWindow _instance;
	/**
	 * ACIDE - A Configurable IDE main window file editor manager.
	 */
	private AcideFileEditorManager _fileEditorManager;
	/**
	 * ACIDE - A Configurable IDE main window menu.
	 */
	private AcideMenuBar _menu;
	/**
	 * ACIDE - A Configurable IDE main window console panel.
	 */
	private AcideConsolePanel _consolePanel;
	/**
	 * ACIDE - A Configurable IDE main window data base panel.
	 */
	private AcideDataBasePanel _dataBasePanel;
	/**
	 * ACIDE - A Configurable IDE main window graph panel
	 */
	private AcideGraphPanel _graphPanel;
	/**
	 * ACIDE - A Configurable IDE main window debug panel
	 */
	private AcideDebugPanel _debugPanel;
	/**
	 * ACIDE - A Configurable IDE main window asserted database panel
	 */
	private AcideAssertedDatabasePanel _assertedDatabasePanel;
	/**
	 * ACIDE - A Configurable IDE main window status bar.
	 */
	private AcideStatusBar _statusBar;
	/**
	 * ACIDE - A Configurable IDE main window explorer panel.
	 */
	private AcideExplorerPanel _explorerPanel;
	/**
	 * ACIDE - A Configurable IDE main window tool bar panel.
	 */
	private AcideToolBarPanel _toolBarPanel;
	/**
	 * ACIDE - A Configurable IDE main window vertical split panel for the projects
	 */
	private JSplitPane _verticalFilesSplitPanel;
	/**
	 * ACIDE - A Configurable IDE main window vertical split panel.
	 */
	private JSplitPane _verticalDataBaseSplitPanel;
	/**
	 * ACIDE - A Configurable IDE main window horizontal split panel.
	 */
	private JSplitPane _horizontalSplitPanel;
	/**
	 * ACIDE - A Configurable IDE main window horizontal split panel.
	 */
	private JSplitPane _horizontalGraphSplitPanel;
	/**
	 * ACIDE - A Configurable IDE main window vertical split panel.
	 */
	private JSplitPane _verticalSplitPanel;
	/**
	 * ACIDE - A Configurable IDE main window last element on focus.
	 */
	private AcideLastElementOnFocus _lastElementOnFocus;
	/**
	 * ACIDE - A Configurable IDE main window list of panels.
	 */
	private ArrayList<String> _panelList;
	/**
	 * ACIDE - A Configurable IDE main window horizontal size.
	 */
	/**
	 * ACIDE - A Configurable IDE main window horizontal length.
	 */
	private int _horizontalSize = 0;
	/**
	 * ACIDE - A Configurable IDE main window vertical size.
	 */
	/**
	 * ACIDE - A Configurable IDE main window vertical length.
	 */
	private int _verticalSize = 0;

	
	/**
	 * Returns the unique ACIDE - A Configurable IDE main window class instance.
	 * 
	 * @return the unique ACIDE - A Configurable IDE main window class instance.
	 */
	public static AcideMainWindow getInstance() {
		if (_instance == null)
			_instance = new AcideMainWindow();
		return _instance;
	}

	/**
	 * Creates a new ACIDE - A Configurable IDE main window.
	 */
	public AcideMainWindow() {

		// Builds the window components
		buildComponents();

		// Adds the components to the window
		addComponents();

		// Sets the listeners
		setListeners();

		// Sets the window configuration
		setWindowConfiguration();
		
	}

	/**
	 * Adds the components to the ACIDE - A Configurable IDE main window.
	 */
	private void addComponents() {

		// Sets the menu bar
		setJMenuBar(_menu);

		// Adds the vertical split panel to the window
		add(_verticalSplitPanel, BorderLayout.CENTER);

		// Adds the status bar to the window
		add(_statusBar, BorderLayout.SOUTH);
	}

	/**
	 * Builds the ACIDE - A Configurable IDE main window components.
	 */
	private void buildComponents() {

		// Updates the log
		AcideLog.getLog()
				.info(AcideLanguageManager.getInstance().getLabels()
						.getString("s67"));

		// Builds the menu
		_menu = AcideGUIFactory.getInstance().buildAcideMenu();

		// Sets the menu listeners
		setMenuListeners();

		// Builds the explorer panel
		_explorerPanel = AcideGUIFactory.getInstance()
				.buildAcideExplorerPanel();
		
		
		//Builds the DataBase panel
		_dataBasePanel = AcideGUIFactory.getInstance()
				.buildAcideDataBasePanel();
			

		// Builds the file editor manager
		_fileEditorManager = AcideGUIFactory.getInstance()
				.buildAcideFileEditorManager();

		// Builds the console panel
		_consolePanel = AcideGUIFactory.getInstance()
				.buildAcideConsolePanel();
		
		// Builds the graph panel
		_graphPanel = AcideGUIFactory.getInstance()
				.buildAcideGraphPanel();
		
		//Builds the debug panel
		_debugPanel = AcideGUIFactory.getInstance()
				.buildAcideDebugPanel();
		
		/*--------------------------------------------------------------*/
		
		_panelList = AcideProjectConfiguration.getInstance().getPanelList();
		
		_verticalDataBaseSplitPanel = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,_dataBasePanel,_consolePanel);
	
		// Sets its resize weight to 0.05
		_verticalDataBaseSplitPanel.setResizeWeight(0.05);
		
		_verticalDataBaseSplitPanel.setDividerLocation(50);

		// Displays its components when the user is resizing the vertical split
		// pane
		_verticalDataBaseSplitPanel.setContinuousLayout(true);
		
		/*--------------------------------------------------------------*/

		// Builds the status bar
		_statusBar = AcideGUIFactory.getInstance().buildAcideStatusBar();

		// Builds the tool bar panel
		buildToolBarPanel();

		// Creates the vertical split pane with the explorer and the file editor
		// manager tabbed pane
		_verticalFilesSplitPanel = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
				_explorerPanel, _fileEditorManager.getTabbedPane());

		// Sets its resize weight to 0.05
		_verticalFilesSplitPanel.setResizeWeight(0.05);
		
		// Displays its components when the user is resizing the vertical split
		// pane
		_verticalFilesSplitPanel.setContinuousLayout(true);

		// Creates the horizontal split pane with the vertical split panel and
		// the console panel
		_horizontalSplitPanel = new JSplitPane(JSplitPane.VERTICAL_SPLIT,
				_verticalFilesSplitPanel, _verticalDataBaseSplitPanel);

		// Sets its resize weight to 0.9
		_horizontalSplitPanel.setResizeWeight(0.9);

		// Displays its components when the user is resizing the horizontal split pane
		_horizontalSplitPanel.setContinuousLayout(true);
		
		_horizontalGraphSplitPanel = new JSplitPane(JSplitPane.VERTICAL_SPLIT, _graphPanel, _debugPanel);
		
		_horizontalGraphSplitPanel.setResizeWeight(0.5);
		
		// Displays its components when the user is resizing the horizontal split pane
		_horizontalGraphSplitPanel.setContinuousLayout(true);
	
		_verticalSplitPanel = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, _horizontalSplitPanel, _horizontalGraphSplitPanel);

		// Sets the minimum size of the graph panel
		_debugPanel.setMinimumSize(new Dimension(120, 50));
		_graphPanel.setMinimumSize(new Dimension(120,50));
		_dataBasePanel.setMinimumSize(new Dimension(50, 10));
		_fileEditorManager.getTabbedPane().setMinimumSize(new Dimension(200, 100));
		
		// Sets its resize weight to 0.9
		_verticalSplitPanel.setResizeWeight(0.9);
			
		// Displays its components when the user is resizing the horizontal split pane
		_verticalSplitPanel.setContinuousLayout(true);
		
		updateVisibility();
		
		// Sets the Split pane containers of the components
		setSplitContainers();
		
		// Sets the Split pane positions
		setPanelPositions();
		
		
	}

	/**
	 * Sets the ACIDE - A Configurable IDE main window window configuration.
	 */
	private void setWindowConfiguration() {

		// Sets the title
		setTitle(AcideLanguageManager.getInstance().getLabels()
				.getString("s425"));

		// Sets the window icon
		setIconImage(ICON.getImage());

		// Do not close automatically
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);

		// Updates the log
		AcideLog.getLog()
				.info(AcideLanguageManager.getInstance().getLabels()
						.getString("s66"));
	}

	/**
	 * Sets the listeners for the ACIDE - A Configurable IDE main window.
	 */
	private void setListeners() {

		// Adds the window listener to the ACIDE - A Configurable IDE main
		// window
		addWindowListener(new AcideMainWindowWindowListener());
		
		//Creates the mouse listener for the Drag and Drop action between panels
		AcideMainWindowMouseListener mouse = new AcideMainWindowMouseListener(this);
		
		//Adds the mouse listener to the menu bars of the panels
		_explorerPanel.getMenuBar().addMouseListener(mouse);
		_explorerPanel.getMenuBar().addMouseMotionListener(mouse);
		
		_dataBasePanel.getMenuBar().addMouseListener(mouse);
		_dataBasePanel.getMenuBar().addMouseMotionListener(mouse);
		
		_fileEditorManager.getTabbedPane().addMouseListener(mouse);
		_fileEditorManager.getTabbedPane().addMouseMotionListener(mouse);
		
		_consolePanel.getMenuBar().addMouseListener(mouse);
		_consolePanel.getMenuBar().addMouseMotionListener(mouse);
		
		_graphPanel.getMenuBar().addMouseListener(mouse);
		_graphPanel.getMenuBar().addMouseMotionListener(mouse);
		
		_debugPanel.getMenuBar().addMouseListener(mouse);
		_debugPanel.getMenuBar().addMouseMotionListener(mouse);
		

	}

	/**
	 * Builds the ACIDE - A Configurable IDE main window tool bar panel with the
	 * different types of tool bars in the application.
	 */
	public void buildToolBarPanel() {

		if (_toolBarPanel != null)
			remove(_toolBarPanel);

		// Creates the tool bar
		_toolBarPanel = new AcideToolBarPanel();

		// Builds the tool bar panel
		_toolBarPanel.buildAcideToolBarPanel();

		// Adds the tool bar panel to the main window
		add(_toolBarPanel, BorderLayout.NORTH);
	}

	/**
	 * Shows the main window, once the ACIDE - A Configurable IDE workbench
	 * configuration has been loaded.
	 */
	public void showAcideMainWindow() {

		// The workbench has been loaded
		AcideWorkbenchConfiguration.getInstance().setWorkbenchLoaded(true);

		// Shows the main window
		AcideMainWindow.getInstance().setVisible(true);
	}

	/**
	 * <p>
	 * Performs the ACIDE - A Configurable IDE closing operation.
	 * </p>
	 * <p>
	 * Asks for saving the changes, if any, in the project configuration.
	 * Besides if there are any modified file editor opened, asks for saving
	 * them to the user as well.
	 * </p>
	 * <p>
	 * Once the two previous processes are done, the workbench manager saves its
	 * configuration.
	 * </p>
	 */
	public void closeAcideMainWindow() {
		
		// If it is the default project
		if (AcideProjectConfiguration.getInstance().isDefaultProject()) {

			// Saves the file editor configuration
			if (AcideMainWindow.getInstance().getFileEditorManager()
					.askForSavingModifiedFiles()) {

				// Saves the default configuration
				AcideWorkbenchConfiguration.getInstance()
						.saveDefaultConfiguration();

				// Save the rest of the workbench configuration
				AcideWorkbenchConfiguration.getInstance()
						.saveComponentsConfiguration();

				// Saves the workbench configuration into its configuration
				// file
				AcideWorkbenchConfiguration.getInstance().save();

				// Closes the main window
				//System.exit(0);
				saveDatabasePanelMenuConfiguration();
				System.exit(0);
			}

		} else {

			// Asks for saving the project configuration
			if (AcideProjectConfiguration.getInstance()
					.askForSavingProjectConfiguration()) {

				// Saves the file editor configuration
				if (AcideMainWindow.getInstance().getFileEditorManager()
						.askForSavingModifiedFiles()) {

					// Save the rest of the workbench configuration
					AcideWorkbenchConfiguration.getInstance()
							.saveComponentsConfiguration();

					// Saves the workbench configuration into its configuration
					// file
					AcideWorkbenchConfiguration.getInstance().save();

					// Closes the main window
					//System.exit(0);
					saveDatabasePanelMenuConfiguration();
					System.exit(0);
				}
				
				
			}
		}
		
//		saveDatabasePanelMenuConfiguration();
//		System.exit(0);
	}

	/**
	 * Saves the current configuration of the show details menu for the visualization of 
	 * table and view nodes in the database panel.
	 */
	private void saveDatabasePanelMenuConfiguration() {
		
		String activeMenuItem =""; 
			if (getMenu().getConfigurationMenu().getDatabasePanelMenu().getShowDetailsMenu()
				.getNameMenuItem().isSelected()) activeMenuItem="Name";
			else if (getMenu().getConfigurationMenu().getDatabasePanelMenu().getShowDetailsMenu()
					.getNameFieldsMenuItem().isSelected()) activeMenuItem="NameFields";
			else activeMenuItem="NameFieldsTypes";
			
		AcideResourceManager.getInstance().setProperty("databasePanelMenuConfiguration.showDetails",activeMenuItem);
	}

	/**
	 * Returns the ACIDE - A Configurable IDE main window console panel.
	 * 
	 * @return the ACIDE - A Configurable IDE main window console panel.
	 */
	public AcideConsolePanel getConsolePanel() {
		return _consolePanel;
	}
	
	/**
	 * Returns the ACIDE - A Configurable IDE main window data base panel.
	 * 
	 * @return the ACIDE - A Configurable IDE main window data base panel.
	 */
	public AcideDataBasePanel getDataBasePanel() {
		return _dataBasePanel;
	}
	
	/**
	 * Returns the ACIDE - A Configurable IDE main window debug panel.
	 * 
	 * @return the ACIDE - A Configurable IDE main window debug panel.
	 */
	public AcideDebugPanel getDebugPanel() {
		return _debugPanel;
	}
	
	/**
	 * Get the ACIDE - A Configurable IDE asserted database panel, in case it is
	 * not opened, it will create a new instance of it
	 * 
	 * @return asserted database panel
	 */
	public AcideAssertedDatabasePanel getAssertedDatabasePanel() {
	
		if (_assertedDatabasePanel == null) {
			String name = AcideLanguageManager.getInstance().getLabels()
					.getString("s2280");
		    _assertedDatabasePanel = new AcideAssertedDatabasePanel(name);

		    _assertedDatabasePanel.setLocationRelativeTo(null);
		    _assertedDatabasePanel.pack();
		    _assertedDatabasePanel.setVisible(true);
		}
		return _assertedDatabasePanel;
	}
	
	/**
	 * Close the ACIDE - A Configurable IDE asserted database window
	 * 
	 */
	public void closeAssertedDatabasePanel() {
		
		if (_assertedDatabasePanel != null) {
			_assertedDatabasePanel.dispose();
			_assertedDatabasePanel = null;
		}
	}
	
	/**
	 * Check if the ACIDE - A Configurable IDE is currently open
	 * 
	 * @return true if the window is opened
	 */
	public boolean isAssertedDatabaseOpened() {
		if (_assertedDatabasePanel != null)
			return true;
		else
			return false;
	}

	/**
	 * Returns the ACIDE - A Configurable IDE main window menu.
	 * 
	 * @return the ACIDE - A Configurable IDE main window menu.
	 */
	public AcideMenuBar getMenu() {
		return _menu;
	}

	/**
	 * Returns the ACIDE - A Configurable IDE main window file editor manager.
	 * 
	 * @return the ACIDE - A Configurable IDE main window file editor manager.
	 */
	public AcideFileEditorManager getFileEditorManager() {
		return _fileEditorManager;
	}
	
	/**
	 * Set the ACIDE - A Configurable IDE main window listeners.
	 */
	public void setMenuListeners() {
		_menu.setListeners();
	}

	/**
	 * Returns the ACIDE - A Configurable IDE main window explorer panel.
	 * 
	 * @return the ACIDE - A Configurable IDE main window explorer panel.
	 */
	public AcideExplorerPanel getExplorerPanel() {
		return _explorerPanel;
	}

	/**
	 * Set a new value for the ACIDE - A Configurable IDE main window explorer
	 * panel.
	 * 
	 * @param explorerPanel
	 *            new value to set.
	 */
	public void setExplorerPanel(AcideExplorerPanel explorerPanel) {
		_explorerPanel = explorerPanel;
	}
	
	/**
	 * Returns the ACIDE - A Configurable IDE main window graph panel.
	 * 
	 * @return the ACIDE - A Configurable IDE main window graph panel. 
	 */
	public AcideGraphPanel getGraphPanel() {
		return _graphPanel;
	}

	/**
	 * Returns the the ACIDE - A Configurable IDE main window status bar.
	 * 
	 * @return the the ACIDE - A Configurable IDE main window status bar.
	 */
	public AcideStatusBar getStatusBar() {
		return _statusBar;
	}

	/**
	 * Returns the ACIDE - A Configurable IDE main window horizontal split pane.
	 * 
	 * @return the ACIDE - A Configurable IDE main window horizontal split pane.
	 */
	public JSplitPane getHorizontalSplitPane() {
		return _horizontalSplitPanel;
	}

	/**
	 * Sets a new value for the ACIDE - A Configurable IDE main window
	 * horizontal split pane.
	 * 
	 * @param horizontalSplitPane
	 *            new value to set.
	 */
	public void setHorizontalSplitPane(JSplitPane horizontalSplitPane) {
		_horizontalSplitPanel = horizontalSplitPane;
	}

	/**
	 * Sets a new value for the ACIDE - A Configurable IDE main window explorer
	 * panel size.
	 * 
	 * @param size
	 *            new value to set.
	 */
	public void setExplorerPanelSize(int size) {
		_explorerPanel.setExplorerSize(size);
	}

	/**
	 * Returns the ACIDE - A Configurable IDE main window vertical split panel.
	 * 
	 * @return the ACIDE - A Configurable IDE main window vertical split panel.
	 */
	public JSplitPane getVerticalFilesSplitPane() {
		return _verticalFilesSplitPanel;
	}
	/**
	 * Returns the ACIDE - A Configurable IDE main window database split panel.
	 * 
	 * @return the ACIDE - A Configurable IDE main window database split panel.
	 */
	public JSplitPane getVerticalDataBaseSplitPane() {
		return _verticalDataBaseSplitPanel;
	}

	/**
	 * Set a new value for the ACIDE - A Configurable IDE main window vertical
	 * split panel.
	 * 
	 * @param verticalSplitPane
	 *            new value to set.
	 */
	public void setVerticalSplitPane(JSplitPane verticalSplitPane) {
		_verticalFilesSplitPanel = verticalSplitPane;
	}
	
	/**
	 * Returns the ACIDE - A Configurable IDE main window vertical split panel.
	 * 
	 * @return the ACIDE - A Configurable IDE main window vertical split panel.
	 */
	public JSplitPane getVerticalSplitPane() {
		return _verticalSplitPanel;
	}
	
	/**
	 * Returns the ACIDE - A Configurable IDE main window horizontal graph split panel.
	 * 
	 * @return the ACIDE - A Configurable IDE main window horizontal graph split panel.
	 */
	public JSplitPane getHorizontalGraphSplitPane() {
		return _horizontalGraphSplitPanel;
	}

	/**
	 * Returns the ACIDE - A Configurable IDE main window tool bar panel.
	 * 
	 * @return the ACIDE - A Configurable IDE main window tool bar panel.
	 */
	public AcideToolBarPanel getToolBarPanel() {
		return _toolBarPanel;
	}

	/**
	 * Sets a new value to the ACIDE - A Configurable IDE main window tool bar
	 * panel.
	 * 
	 * @param toolBar
	 *            new value to set.
	 */
	public void setToolBarPanel(AcideToolBarPanel toolBar) {
		_toolBarPanel = toolBar;
	}

	/**
	 * Sets a new value to the ACIDE - A Configurable IDE main window last
	 * element on focus.
	 * 
	 * @param lastElementOnFocus
	 *            new value to set.
	 */
	public void setLastElementOnFocus(AcideLastElementOnFocus lastElementOnFocus) {
		_lastElementOnFocus = lastElementOnFocus;
	}

	/**
	 * Returns the ACIDE - A Configurable IDE main window last element on focus.
	 * If the last element on focus is null then returns the console panel by
	 * default.
	 * 
	 * @return returns the ACIDE - A Configurable IDE main window last element
	 *         on focus. If the last element on focus is null then returns the
	 *         console panel by default.
	 */
	public AcideLastElementOnFocus getLastElementOnFocus() {

		if (_lastElementOnFocus != null)
			return _lastElementOnFocus;
		return AcideLastElementOnFocus.CONSOLE_PANEL;
	}
	
	@Override
	public void setCursor(Cursor cursor){
		super.setCursor(cursor);
		_consolePanel.setCursor(cursor);
		_dataBasePanel.setCursor(cursor);
		_explorerPanel.setCursor(cursor);
		_menu.setCursor(cursor);
		_toolBarPanel.setCursor(cursor);
		_fileEditorManager.setCursor(cursor);
		_fileEditorManager.getTabbedPane().setCursor(cursor);
		_graphPanel.setCursor(cursor);
		_debugPanel.setCursor(cursor);
	}
	
	
	/**
	 * returns the ACIDE - A Configurable IDE split panel which name
	 * matches with the parameter
	 * 
	 * @param name
	 * 		name of the split pane we want to get
	 * @return the split panel that matches with the param
	 */
	public JSplitPane getSpecificSplitPane(String name) {
		JSplitPane panel = null;
		
		if (name.equals("verticalFilesSplitPanel"))
			panel = _verticalFilesSplitPanel;
		else if (name.equals("verticalDataBaseSplitPanel"))
			panel = _verticalDataBaseSplitPanel;
		else if (name.equals("horizontalGraphSplitPanel"))
			panel = _horizontalGraphSplitPanel;
		
		return panel;
	}
	
	/**
	 * Sets the ACIDE - A Configurable IDE name of the split panel containers
	 * 
	 */
	public void setSplitContainers() {

		for (int i = 0; i < 6; i++) {
			Component comp = _verticalFilesSplitPanel.getRightComponent();
			String text = null;

			switch (i) {
			case 0:
				comp = _verticalFilesSplitPanel.getRightComponent();
				text = "verticalFilesSplitPanel";
				break;
			case 1:
				comp = _verticalFilesSplitPanel.getLeftComponent();
				text = "verticalFilesSplitPanel";
				break;
			case 2:
				comp = _verticalDataBaseSplitPanel.getLeftComponent();
				text = "verticalDataBaseSplitPanel";
				break;
			case 3:
				comp = _verticalDataBaseSplitPanel.getRightComponent();
				text = "verticalDataBaseSplitPanel";
				break;
			case 4:
				comp = _horizontalGraphSplitPanel.getLeftComponent();
				text = "horizontalGraphSplitPanel";
				break;
			case 5:
				comp = _horizontalGraphSplitPanel.getRightComponent();
				text = "horizontalGraphSplitPanel";
				break;
			}

			if (comp instanceof AcideExplorerPanel)
				((AcideExplorerPanel) comp).setSplitContainer(text);
			else if (comp instanceof AcideDataBasePanel)
				((AcideDataBasePanel) comp).setSplitContainer(text);
			else if (comp instanceof AcideConsolePanel)
				((AcideConsolePanel) comp).setSplitContainer(text);
			else if (comp instanceof AcideGraphPanel)
				((AcideGraphPanel) comp).setSplitContainer(text);
			else if (comp instanceof AcideDebugPanel)
				((AcideDebugPanel) comp).setSplitContainer(text);
//			 else if (comp instanceof AcideDragAndDropTabbedPane)
//				 ((AcideDragAndDropTabbedPane) comp).setSplitContainer(text);
		}

	}
	
	/**
	 * Sets the ACIDE - A Configurable IDE Panels positions
	 * 
	 */
	public void setPanelPositions() {
		
		_verticalFilesSplitPanel.setLeftComponent(null);
		_verticalFilesSplitPanel.setRightComponent(null);
		_verticalDataBaseSplitPanel.setLeftComponent(null);
		_verticalDataBaseSplitPanel.setRightComponent(null);
		_horizontalGraphSplitPanel.setLeftComponent(null);
		_horizontalGraphSplitPanel.setRightComponent(null);
		
		for (int i = 0; i < _panelList.size(); i++) {
			switch(i) {
			case 0: 
				if (_panelList.get(i).equals("AcideExplorerPanel"))
					_verticalFilesSplitPanel.setLeftComponent(_explorerPanel);
				else if (_panelList.get(i).equals("AcideFileEditor"))
					_verticalFilesSplitPanel.setLeftComponent(_fileEditorManager.getTabbedPane()); 
				else if (_panelList.get(i).equals("AcideDataBasePanel"))
					_verticalFilesSplitPanel.setLeftComponent(_dataBasePanel);
				else if (_panelList.get(i).equals("AcideConsolePanel"))
					_verticalFilesSplitPanel.setLeftComponent(_consolePanel);
				else if (_panelList.get(i).equals("AcideGraphPanel"))
					_verticalFilesSplitPanel.setLeftComponent(_graphPanel);
				else if (_panelList.get(i).equals("AcideDebugPanel"))
					_verticalFilesSplitPanel.setLeftComponent(_debugPanel);
				break;
			case 1:
				if (_panelList.get(i).equals("AcideExplorerPanel"))
					_verticalFilesSplitPanel.setRightComponent(_explorerPanel);
				else if (_panelList.get(i).equals("AcideFileEditor"))
					_verticalFilesSplitPanel.setRightComponent(_fileEditorManager.getTabbedPane()); 
				else if (_panelList.get(i).equals("AcideDataBasePanel"))
					_verticalFilesSplitPanel.setRightComponent(_dataBasePanel);
				else if (_panelList.get(i).equals("AcideConsolePanel"))
					_verticalFilesSplitPanel.setRightComponent(_consolePanel);
				else if (_panelList.get(i).equals("AcideGraphPanel"))
					_verticalFilesSplitPanel.setRightComponent(_graphPanel);
				else if (_panelList.get(i).equals("AcideDebugPanel"))
					_verticalFilesSplitPanel.setRightComponent(_debugPanel);
				break;
			case 2: 
				if (_panelList.get(i).equals("AcideExplorerPanel"))
					_verticalDataBaseSplitPanel.setLeftComponent(_explorerPanel);
				else if (_panelList.get(i).equals("AcideFileEditor"))
					_verticalDataBaseSplitPanel.setLeftComponent(_fileEditorManager.getTabbedPane()); 
				else if (_panelList.get(i).equals("AcideDataBasePanel"))
					_verticalDataBaseSplitPanel.setLeftComponent(_dataBasePanel);
				else if (_panelList.get(i).equals("AcideConsolePanel"))
					_verticalDataBaseSplitPanel.setLeftComponent(_consolePanel);
				else if (_panelList.get(i).equals("AcideGraphPanel"))
					_verticalDataBaseSplitPanel.setLeftComponent(_graphPanel);
				else if (_panelList.get(i).equals("AcideDebugPanel"))
					_verticalDataBaseSplitPanel.setLeftComponent(_debugPanel);
				break;
			case 3:
				if (_panelList.get(i).equals("AcideExplorerPanel"))
					_verticalDataBaseSplitPanel.setRightComponent(_explorerPanel);
				else if (_panelList.get(i).equals("AcideFileEditor"))
					_verticalDataBaseSplitPanel.setRightComponent(_fileEditorManager.getTabbedPane()); 
				else if (_panelList.get(i).equals("AcideDataBasePanel"))
					_verticalDataBaseSplitPanel.setRightComponent(_dataBasePanel);
				else if (_panelList.get(i).equals("AcideConsolePanel"))
					_verticalDataBaseSplitPanel.setRightComponent(_consolePanel);
				else if (_panelList.get(i).equals("AcideGraphPanel"))
					_verticalDataBaseSplitPanel.setRightComponent(_graphPanel);
				else if (_panelList.get(i).equals("AcideDebugPanel"))
					_verticalDataBaseSplitPanel.setRightComponent(_debugPanel);
				break;
			case 4:
				if (_panelList.get(i).equals("AcideExplorerPanel"))
					_horizontalGraphSplitPanel.setLeftComponent(_explorerPanel);
				else if (_panelList.get(i).equals("AcideFileEditor"))
					_horizontalGraphSplitPanel.setLeftComponent(_fileEditorManager.getTabbedPane()); 
				else if (_panelList.get(i).equals("AcideDataBasePanel"))
					_horizontalGraphSplitPanel.setLeftComponent(_dataBasePanel);
				else if (_panelList.get(i).equals("AcideConsolePanel"))
					_horizontalGraphSplitPanel.setLeftComponent(_consolePanel);
				else if (_panelList.get(i).equals("AcideGraphPanel"))
					_horizontalGraphSplitPanel.setLeftComponent(_graphPanel);
				else if (_panelList.get(i).equals("AcideDebugPanel"))
					_horizontalGraphSplitPanel.setLeftComponent(_debugPanel);
				break;
			case 5:
				if (_panelList.get(i).equals("AcideExplorerPanel"))
					_horizontalGraphSplitPanel.setRightComponent(_explorerPanel);
				else if (_panelList.get(i).equals("AcideFileEditor"))
					_horizontalGraphSplitPanel.setRightComponent(_fileEditorManager.getTabbedPane()); 
				else if (_panelList.get(i).equals("AcideDataBasePanel"))
					_horizontalGraphSplitPanel.setRightComponent(_dataBasePanel);
				else if (_panelList.get(i).equals("AcideConsolePanel"))
					_horizontalGraphSplitPanel.setRightComponent(_consolePanel);
				else if (_panelList.get(i).equals("AcideGraphPanel"))
					_horizontalGraphSplitPanel.setRightComponent(_graphPanel);
				else if (_panelList.get(i).equals("AcideDebugPanel"))
					_horizontalGraphSplitPanel.setRightComponent(_debugPanel);
				break;
			}
		}
		
	}
	
	/**
	 * Updates the ACIDE - A Configurable IDE list of panels
	 * 
	 */
	public void updatePanelList() {
		_panelList.clear();
		_panelList.add(_verticalFilesSplitPanel.getLeftComponent().getName());
		_panelList.add(_verticalFilesSplitPanel.getRightComponent().getName());
		_panelList.add(_verticalDataBaseSplitPanel.getLeftComponent().getName());
		_panelList.add(_verticalDataBaseSplitPanel.getRightComponent().getName());
		_panelList.add(_horizontalGraphSplitPanel.getLeftComponent().getName());
		_panelList.add(_horizontalGraphSplitPanel.getRightComponent().getName());
		
		AcideProjectConfiguration.getInstance().setPanelList(_panelList);
		
	}

	/**
	 * Updates the ACIDE - A Configurable IDE panels visibility
	 * 
	 */
	public void updateVisibility() {

		//Gets the visibility of the splits panels
		boolean e1, e2, e3, e4;
		e1 = _verticalFilesSplitPanel.isVisible();
		e2 = _verticalDataBaseSplitPanel.isVisible();
		e3 = _horizontalSplitPanel.isVisible();
		e4 = _horizontalGraphSplitPanel.isVisible();

		//Gets the divider locations
		_horizontalSize = _horizontalSplitPanel.getDividerLocation();
		_verticalSize = _verticalSplitPanel.getDividerLocation();

		//Checks if the both components of the panels are visible
		if (!e1 || !e2)
			_horizontalSize = _horizontalSplitPanel.getHeight()
					- _horizontalSplitPanel.getHeight() / 3;
		if (!e3 || !e4)
			_verticalSize = _verticalSplitPanel.getWidth()
					- _verticalSplitPanel.getWidth() / 6;

		//Checks if the both components of the panels are visible
		if (!_verticalFilesSplitPanel.getLeftComponent().isVisible()
				&& !_verticalFilesSplitPanel.getRightComponent().isVisible()) {
			if (_horizontalSplitPanel.getRightComponent().isVisible())
				_horizontalSize = _horizontalSplitPanel.getDividerLocation();
			_horizontalSplitPanel.getLeftComponent().setVisible(false);
		} else {
			_horizontalSplitPanel.getLeftComponent().setVisible(true);
			_horizontalSplitPanel.setDividerLocation(_horizontalSize);
		}
		
		//Checks if the both components of the panels are visible
		if (!_verticalDataBaseSplitPanel.getLeftComponent().isVisible()
				&& !_verticalDataBaseSplitPanel.getRightComponent().isVisible()) {
			if (_horizontalSplitPanel.getLeftComponent().isVisible())
				_horizontalSize = _horizontalSplitPanel.getDividerLocation();
			_horizontalSplitPanel.getRightComponent().setVisible(false);
		} else {
			_horizontalSplitPanel.getRightComponent().setVisible(true);
			_horizontalSplitPanel.setDividerLocation(_horizontalSize);
		}
		
		//Checks if the both components of the panels are visible
		if (!_horizontalGraphSplitPanel.getLeftComponent().isVisible()
				&& !_horizontalGraphSplitPanel.getRightComponent().isVisible()) {
			if (_verticalSplitPanel.getLeftComponent().isVisible())
				_verticalSize = _verticalSplitPanel.getDividerLocation();
			_verticalSplitPanel.getRightComponent().setVisible(false);
		} else {
			_verticalSplitPanel.getRightComponent().setVisible(true);
			_verticalSplitPanel.setDividerLocation(_verticalSize);
		}
		
		//Checks if the both components of the panels are visible
		if (!_verticalFilesSplitPanel.isVisible()
				&& !_verticalDataBaseSplitPanel.isVisible()) {
			if (_verticalSplitPanel.getRightComponent().isVisible())
				_verticalSize = _verticalSplitPanel.getDividerLocation();
			_verticalSplitPanel.getLeftComponent().setVisible(false);
		} else {
			_verticalSplitPanel.getLeftComponent().setVisible(true);
			_verticalSplitPanel.setDividerLocation(_verticalSize);
		}
		
	}
	
}