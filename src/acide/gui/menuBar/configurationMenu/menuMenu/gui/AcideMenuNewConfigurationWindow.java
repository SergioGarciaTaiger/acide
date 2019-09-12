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
package acide.gui.menuBar.configurationMenu.menuMenu.gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.KeyStroke;

import acide.configuration.menu.AcideMenuItemsConfiguration;
import acide.configuration.menu.AcideMenuObjectConfiguration;
import acide.configuration.menu.AcideMenuSubmenuConfiguration;
import acide.configuration.project.AcideProjectConfiguration;
import acide.gui.listeners.AcideWindowClosingListener;
import acide.gui.mainWindow.AcideMainWindow;
import acide.gui.menuBar.configurationMenu.AcideConfigurationMenu;
import acide.gui.menuBar.configurationMenu.menuMenu.AcideMenuMenu;
import acide.gui.menuBar.configurationMenu.menuMenu.gui.configurationPanel.AcideConfigurationMenuNewPanel;
import acide.gui.menuBar.configurationMenu.menuMenu.gui.configurationPanel.utils.AcideConfigurationMenuNewPanelTableModel;
import acide.gui.menuBar.configurationMenu.menuMenu.gui.editPanel.AcideEditMenuNewPanel;
import acide.gui.menuBar.configurationMenu.menuMenu.gui.editPanel.utils.AcideEditMenuNewPanelTableModel;
import acide.gui.menuBar.configurationMenu.menuMenu.gui.filePanel.AcideFileMenuNewPanel;
import acide.gui.menuBar.configurationMenu.menuMenu.gui.filePanel.utils.AcideFileMenuNewPanelTableModel;
import acide.gui.menuBar.configurationMenu.menuMenu.gui.helpPanel.AcideHelpMenuNewPanel;
import acide.gui.menuBar.configurationMenu.menuMenu.gui.helpPanel.utils.AcideHelpMenuNewPanelTableModel;
import acide.gui.menuBar.configurationMenu.menuMenu.gui.menuInsertedPanel.AcideMenuInsertedPanel;
import acide.gui.menuBar.configurationMenu.menuMenu.gui.menuInsertedPanel.utils.AcideInsertedMenuNewPanelTableModel;
import acide.gui.menuBar.configurationMenu.menuMenu.gui.projectPanel.AcideProjectMenuNewPanel;
import acide.gui.menuBar.configurationMenu.menuMenu.gui.projectPanel.utils.AcideProjectMenuNewPanelTableModel;
import acide.gui.menuBar.configurationMenu.menuMenu.gui.viewPanel.AcideViewMenuNewPanel;
import acide.gui.menuBar.configurationMenu.menuMenu.gui.viewPanel.utils.AcideViewMenuNewPanelTableModel;
import acide.gui.menuBar.editMenu.AcideEditMenu;
import acide.gui.menuBar.fileMenu.AcideFileMenu;
import acide.gui.menuBar.helpMenu.AcideHelpMenu;
import acide.gui.menuBar.projectMenu.AcideProjectMenu;
import acide.gui.menuBar.viewMenu.AcideViewMenu;
import acide.language.AcideLanguageManager;
import acide.log.AcideLog;
import acide.resources.AcideResourceManager;

/**
 * ACIDE - A Configurable IDE menu new configuration window.
 * 
 * @version 0.11
 * @see JFrame
 */
public class AcideMenuNewConfigurationWindow extends JFrame {
	
	/**
	 * ACIDE - A Configurable IDE menu new configuration window class serial version
	 * UID.
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * ACIDE - A Configurable IDE menu new configuration window image icon.
	 */
	private static final String WINDOW_ICON = "./resources/images/icon.png";
	/**
	 * ACIDE - A Configurable IDE menu new configuration window tabbed pane.
	 */
	private JTabbedPane _tabbedPane;
	/**
	 * ACIDE - A Configurable IDE menu new configuration window file panel.
	 */
	private AcideFileMenuNewPanel _fileMenuPanel;
	/**
	 * ACIDE - A Configurable IDE menu new configuration window edit panel.
	 */
	private AcideEditMenuNewPanel _editMenuPanel;
	/**
	 * ACIDE - A Configurable IDE menu new configuration window project panel.
	 */
	private AcideProjectMenuNewPanel _projectMenuPanel;
	/**
	 * ACIDE - A Configurable IDE menu new configuration window view panel.
	 */
	private AcideViewMenuNewPanel _viewMenuPanel;
	/**
	 * ACIDE - A Configurable IDE menu new configuration window configuration panel.
	 */
	private AcideConfigurationMenuNewPanel _configurationMenuPanel;
	/**
	 * ACIDE - A Configurable IDE menu new configuration window help panel.
	 */
	private AcideHelpMenuNewPanel _helpMenuPanel;
	/**
	 * ACIDE - A Configurable IDE inserted menus hashmap.
	 */
	private HashMap<String, AcideMenuInsertedPanel> _insertedMenus;
	/**
	 * ACIDE - A Configurable IDE array list of inserted objects.
	 */
	private ArrayList<String> _insertedObjects;
	/**
	 * ACIDE - A Configurable IDE menu bar configuration.
	 */
	private AcideMenuSubmenuConfiguration _submenuConfigurations;
	/**
	 * ACIDE - A Configurable IDE menu new configuration window button panel.
	 */
	private JPanel _buttonPanel;
	/**
	 * ACIDE - A Configurable IDE menu new configuration window accept button.
	 */
	private JButton _acceptButton;
	/**
	 * ACIDE - A Configurable IDE menu new configuration window cancel button.
	 */
	private JButton _cancelButton;
	/**
	 * ACIDE - A Configurable IDE menu new configuration window select all button.
	 */
	private JButton _selectAllButton;
	/**
	 * ACIDE - A Configurable IDE menu new configuration window select none button.
	 */
	private JButton _selectNoneButton;
	/**
	 * ACIDE - A Configurable IDE menu new configuration window to the right button.
	 */
	private JButton _toTheRight;
	/**
	 * ACIDE - A Configurable IDE menu new configuration window to the right button icon
	 */
	private final static ImageIcon TO_THE_RIGHT_IMAGE = new ImageIcon(
			"./resources/icons/menu/configuration/menu/right_arrow.png");
	/**
	 * ACIDE - A Configurable IDE menu new configuration window to the left button.
	 */
	private JButton _toTheLeft;
	/**
	 * ACIDE - A Configurable IDE menu new configuration window to the left button icon
	 */
	private final static ImageIcon TO_THE_LEFT_IMAGE = new ImageIcon(
			"./resources/icons/menu/configuration/menu/left_arrow.png");
	/**
	 * ACIDE - A Configurable IDE menu new configuration window delete menu button.
	 */
	private JButton _deleteMenu;
	/**
	 * ACIDE - A Configurable IDE menu new configuration window delete menu icon
	 */
	private final static ImageIcon DELETE_MENU_IMAGE = new ImageIcon(
			"./resources/icons/menu/project/deleteFile.png");
	/**
	 * ACIDE - A Configurable IDE menu new configuration window delete item button.
	 */
	private JButton _deleteItem;
	/**
	 * ACIDE - A Configurable IDE menu new configuration window add menu button.
	 */
	private JButton _addMenu;
	/**
	 * ACIDE - A Configurable IDE menu new configuration window add menu button icon
	 */
	private final static ImageIcon ADD_MENU_IMAGE = new ImageIcon(
			"./resources/icons/menu/project/addOpenedFiles.png");
	/**
	 * ACIDE - A Configurable IDE menu new configuration window add submenu button.
	 */
	private JButton _addSubmenu;
	/**
	 * ACIDE - A Configurable IDE menu new configuration window add item button.
	 */
	private JButton _addItem;
	/**
	 * ACIDE - A Configurable IDE menu new configuration window to the up button.
	 */
	private JButton _up;
	/**
	 * ACIDE - A Configurable IDE menu new configuration window to the up button icon
	 */
	private final static ImageIcon TO_THE_UP_IMAGE = new ImageIcon(
			"./resources/icons/menu/configuration/menu/up_arrow.png");
	/**
	 * ACIDE - A Configurable IDE menu new configuration window to the down button.
	 */
	private JButton _down;
	/**
	 * ACIDE - A Configurable IDE menu new configuration window to the down button icon
	 */
	private final static ImageIcon TO_THE_DOWN_IMAGE = new ImageIcon(
			"./resources/icons/menu/configuration/menu/down_arrow.png");
	/**
	 * Flag that indicates if the changes are saved or not.
	 */
	private static boolean _changesAreSaved;
	/**
	 * Flag that indicates if the window is used for modifying an existent menu
	 * configuration or to create a new one.
	 */
	private boolean _forModifying;
	
	private static AcideMenuNewConfigurationWindow _instance;
	
	public static AcideMenuNewConfigurationWindow getInstance(boolean modify){
		_instance = new AcideMenuNewConfigurationWindow(modify);
		return _instance;
	}
	
	public static AcideMenuNewConfigurationWindow getInstance(){
		if (_instance == null){
			_instance = new AcideMenuNewConfigurationWindow(true);
			return _instance;
		}
		return _instance;
	}
	
	/**
	 * Creates a new menu new configuration window.
	 * 
	 * @param forModifying
	 *            indicates if the window is used for modify the menu
	 *            new configuration or for create it.
	 */
	public AcideMenuNewConfigurationWindow(boolean forModifying) {

		// Stores the flag
		_forModifying = forModifying;

		// The changes are saved
		_changesAreSaved = true;
		
		_insertedMenus = new HashMap<String, AcideMenuInsertedPanel>();
		
		_insertedObjects = new ArrayList<String>();

		// Builds the window components
		buildComponents();
		
		// Sets the data into the tables from the menu configuration.
		setDataFromConfiguration();

		// Sets the listeners of the window components
		setListeners();

		// Adds the components to the window
		addComponents();

		// Sets the window configuration
		setWindowConfiguration();
	}
	
	/**
	 * Builds the ACIDE - A Configurable IDE menu configuration window
	 * components.
	 */
	private void buildComponents() {

		// Updates the log
		AcideLog.getLog().info(
				AcideLanguageManager.getInstance().getLabels()
						.getString("s531"));
		
		Iterator<Object> it = AcideMenuItemsConfiguration.getInstance()
				.getMenuItemsManager().managerIterator();
		
		while (it.hasNext()){
			AcideMenuObjectConfiguration ob = (AcideMenuObjectConfiguration) it.next();
			String name = ob.getName();
			if (isOriginal(name) && (_forModifying)){			
				if (ob.isSubmenu()){
					_insertedObjects.add(name);
					_insertedMenus.put(ob.getName(), new AcideMenuInsertedPanel(name));
				}else {
					;
				}
			}
		}

		// Creates the tabbed pane
		_tabbedPane = new JTabbedPane();

		// Creates the file menu panel
		_fileMenuPanel = new AcideFileMenuNewPanel();

		// Creates the edit menu panel
		_editMenuPanel = new AcideEditMenuNewPanel();

		// Creates the project menu panel
		_projectMenuPanel = new AcideProjectMenuNewPanel();

		// Creates the view menu panel
		_viewMenuPanel = new AcideViewMenuNewPanel();

		// Creates the configuration menu panel
		_configurationMenuPanel = new AcideConfigurationMenuNewPanel();

		// Creates the help menu panel
		_helpMenuPanel = new AcideHelpMenuNewPanel();

		// Creates the button panel
		_buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

		// Creates the accept button
		_acceptButton = new JButton(AcideLanguageManager.getInstance()
				.getLabels().getString("s507"));

		// Sets the accept button tool tip text
		_acceptButton.setToolTipText(AcideLanguageManager.getInstance()
				.getLabels().getString("s508"));

		// Creates the cancel button
		_cancelButton = new JButton(AcideLanguageManager.getInstance()
				.getLabels().getString("s509"));

		// Sets the cancel button tool tip text
		_cancelButton.setToolTipText(AcideLanguageManager.getInstance()
				.getLabels().getString("s510"));

		// Creates the select all button
		_selectAllButton = new JButton(AcideLanguageManager.getInstance()
				.getLabels().getString("s2206"));

		// Sets the select all button tool tip text
		_selectAllButton.setToolTipText(AcideLanguageManager.getInstance()
				.getLabels().getString("s2208"));

		// Creates the select none button
		_selectNoneButton = new JButton(AcideLanguageManager.getInstance()
				.getLabels().getString("s2207"));

		// Sets the select none button tool tip text
		_selectNoneButton.setToolTipText(AcideLanguageManager.getInstance()
				.getLabels().getString("s2209"));
		
		// Creates the select all button
		_toTheRight = new JButton(TO_THE_RIGHT_IMAGE);

		// Sets the select all button tool tip text
		_toTheRight.setToolTipText(AcideLanguageManager.getInstance()
				.getLabels().getString("s2191"));

		// Creates the select none button
		_toTheLeft = new JButton(TO_THE_LEFT_IMAGE);

		// Sets the select none button tool tip text
		_toTheLeft.setToolTipText(AcideLanguageManager.getInstance()
				.getLabels().getString("s2192"));
		
		//Creates the delete menu button
		_deleteMenu = new JButton(DELETE_MENU_IMAGE);
		
		// Sets the delete menu button tool tip text
		_deleteMenu.setToolTipText(AcideLanguageManager.getInstance()
				.getLabels().getString("s2200"));
		
		//Creates the delete item button
		_deleteItem = new JButton(AcideLanguageManager.getInstance()
				.getLabels().getString("s2205"));
		
		// Sets the delete menu button tool tip text
		_deleteItem.setToolTipText(AcideLanguageManager.getInstance()
				.getLabels().getString("s2205"));
		
		//Creates the add menu button
		_addMenu = new JButton(ADD_MENU_IMAGE);
		
		// Sets the add menu button tool tip text
		_addMenu.setToolTipText(AcideLanguageManager.getInstance()
				.getLabels().getString("s2201"));
		
		//Creates the add submenu button
		_addSubmenu = new JButton(AcideLanguageManager.getInstance()
				.getLabels().getString("s2202"));
		
		// Sets the add menu button tool tip text
		_addSubmenu.setToolTipText(AcideLanguageManager.getInstance()
				.getLabels().getString("s2202"));
		
		//Creates the add menu button
		_addItem = new JButton(AcideLanguageManager.getInstance()
				.getLabels().getString("s2203"));
		
		// Sets the add menu button tool tip text
		_addItem.setToolTipText(AcideLanguageManager.getInstance()
				.getLabels().getString("s2203"));
		
		// Creates the select all button
		_up = new JButton(TO_THE_UP_IMAGE);

		// Sets the select all button tool tip text
		_up.setToolTipText(AcideLanguageManager.getInstance()
				.getLabels().getString("s2193"));

		// Creates the select none button
		_down = new JButton(TO_THE_DOWN_IMAGE);

		// Sets the select none button tool tip text
		_down.setToolTipText(AcideLanguageManager.getInstance()
				.getLabels().getString("s2194"));
	

		// If the window is for modifying the menu items
		//if (_forModifying)
			// Sets the check boxes from the menu item list
			//setCheckBoxesFromMenuItemList();
	}
	
	/**
	 * Adds the components to the ACIDE - A Configurable IDE menu new configuration
	 * window with the layout.
	 */
	private void addComponents() {
		
		Iterator<Object> it = _submenuConfigurations
				.getItemsManager().managerIterator();
		
		while (it.hasNext()){
			AcideMenuObjectConfiguration ob = (AcideMenuObjectConfiguration) it.next();
			String name = ob.getName();
			if (name.equals(AcideFileMenu.FILE_MENU_NAME)){
				// Adds the file menu panel to the tabbed pane
				_tabbedPane.addTab(name, _fileMenuPanel);
			}else if (name.equals(AcideEditMenu.EDIT_MENU_NAME)){
				// Adds the edit menu panel to the tabbed pane
				_tabbedPane.addTab(name, _editMenuPanel);
			}else if (name.equals(AcideProjectMenu.PROJECT_MENU_NAME)){
				// Adds the project menu panel to the tabbed pane
				_tabbedPane.addTab(name, _projectMenuPanel);
			}else if (name.equals(AcideViewMenu.VIEW_MENU_NAME)){
				// Adds the view menu panel to the tabbed pane
				_tabbedPane.addTab(name, _viewMenuPanel);
			}else if (name.equals(AcideConfigurationMenu.CONFIGURATION_MENU_NAME)){
				// Adds the configuration menu panel to the tabbed pane
				_tabbedPane.addTab(name, _configurationMenuPanel);
			}else if (name.equals(AcideHelpMenu.HELP_MENU_NAME)){
				// Adds the help menu panel to the tabbed pane
				_tabbedPane.addTab(name, _helpMenuPanel);
			}else{
				if (ob.isSubmenu() && (_forModifying)){
					_tabbedPane.addTab(ob.getName(), _insertedMenus.get(ob.getName()));
				}else{
					;
				}
			}
		}

		// Sets the layout
		setLayout(new BorderLayout());

		// Adds the main panel to the window
		add(_tabbedPane);
		
		// Adds the add menu to the button panel
		_buttonPanel.add(_addMenu);
		
		// Adds the delete menu to the button panel
		_buttonPanel.add(_deleteMenu);
		
		// Adds the to the left button to the button panel
		_buttonPanel.add(_toTheLeft);
		
		// Adds the to the right button to the button panel
		_buttonPanel.add(_toTheRight);
		
		// Adds the add submenu to the button panel
		_buttonPanel.add(_addSubmenu);
		
		// Adds the add item to the button panel
		_buttonPanel.add(_addItem);
		
		// Adds the delete item to the button panel
		_buttonPanel.add(_deleteItem);

		// Adds the up button to the button panel
		_buttonPanel.add(_up);
		
		// Adds the down button to the button panel
		_buttonPanel.add(_down);

		// Adds the select all button to the button panel
		_buttonPanel.add(_selectAllButton);

		// Adds the select none button to the button panel
		_buttonPanel.add(_selectNoneButton);

		// Adds the accept button to the button panel
		_buttonPanel.add(_acceptButton);

		// Adds the cancel button to the button panel
		_buttonPanel.add(_cancelButton);

		// Adds the button panel to the window
		add(_buttonPanel, BorderLayout.SOUTH);
	}
	
	/**
	 * Adds the components to the ACIDE - A Configurable IDE tabbed pane.
	 */
	public void addToTabbedPane(){
		
		Iterator<Object> it = _submenuConfigurations
				.getItemsManager().managerIterator();
		
		while (it.hasNext()){
			AcideMenuObjectConfiguration ob = (AcideMenuObjectConfiguration) it.next();
			String name = ob.getName();
			if (name.equals(AcideFileMenu.FILE_MENU_NAME)){
				// Adds the file menu panel to the tabbed pane
				_tabbedPane.addTab(name, _fileMenuPanel);
			}else if (name.equals(AcideEditMenu.EDIT_MENU_NAME)){
				// Adds the edit menu panel to the tabbed pane
				_tabbedPane.addTab(name, _editMenuPanel);
			}else if (name.equals(AcideProjectMenu.PROJECT_MENU_NAME)){
				// Adds the project menu panel to the tabbed pane
				_tabbedPane.addTab(name, _projectMenuPanel);
			}else if (name.equals(AcideViewMenu.VIEW_MENU_NAME)){
				// Adds the view menu panel to the tabbed pane
				_tabbedPane.addTab(name, _viewMenuPanel);
			}else if (name.equals(AcideConfigurationMenu.CONFIGURATION_MENU_NAME)){
				// Adds the configuration menu panel to the tabbed pane
				_tabbedPane.addTab(name, _configurationMenuPanel);
			}else if (name.equals(AcideHelpMenu.HELP_MENU_NAME)){
				// Adds the help menu panel to the tabbed pane
				_tabbedPane.addTab(name, _helpMenuPanel);
			}else{
				if (ob.isSubmenu()){
					_tabbedPane.addTab(ob.getName(), _insertedMenus.get(ob.getName()));
				}else{
					;
				}
			}
		}
	}
	
	/**
	 * Gets if the menu name given as parameter is original
	 * @param name
	 * 		the name we want to check
	 * @return
	 * 		if the name given as parameter is original
	 */
	public boolean isOriginal(String name){
		if ((name != AcideFileMenu.FILE_MENU_NAME)
				&& (name != AcideEditMenu.EDIT_MENU_NAME) 
				&& (name != AcideProjectMenu.PROJECT_MENU_NAME)
				&& (name != AcideViewMenu.VIEW_MENU_NAME)
				&& (name != AcideConfigurationMenu.CONFIGURATION_MENU_NAME)
				&& (name != AcideHelpMenu.HELP_MENU_NAME))
			return true;
		else 
			return false;
	}
	
	/**
	 * Sets the data into the tables from the ACIDE - A Configurable IDE tool
	 * bar configuration.
	 */
	private void setDataFromConfiguration() {

		// If the window is used for modifying the tool bar configuration
		if (_forModifying) {
			
			_submenuConfigurations = new AcideMenuSubmenuConfiguration(AcideMenuItemsConfiguration.getInstance()
					.getMenuItemsManager());
			
			Iterator<String> it = _insertedObjects.iterator();
			while (it.hasNext()){
				String name = it.next();
				AcideMenuInsertedPanel menuInsertedPanel = _insertedMenus.get(name);
				menuInsertedPanel.setConfiguration(_submenuConfigurations.getSubmenu(name));
				
				menuInsertedPanel.setDataFromConfiguration();
			}
			
		} else {
			
			_submenuConfigurations = AcideMenuItemsConfiguration.getInstance().getDefaultMenuBar();
		
		}

		try {
				
				_fileMenuPanel.setConfiguration(_submenuConfigurations.getSubmenu(AcideFileMenu.FILE_MENU_NAME));
				
				_fileMenuPanel.setDataFromConfiguration();
				
				_editMenuPanel.setConfiguration(_submenuConfigurations.getSubmenu(AcideEditMenu.EDIT_MENU_NAME));
				
				_editMenuPanel.setDataFromConfiguration();
				
				_projectMenuPanel.setConfiguration(_submenuConfigurations.getSubmenu(AcideProjectMenu.PROJECT_MENU_NAME));
				
				_projectMenuPanel.setDataFromConfiguration();
				
				_viewMenuPanel.setConfiguration(_submenuConfigurations.getSubmenu(AcideViewMenu.VIEW_MENU_NAME));
				
				_viewMenuPanel.setDataFromConfiguration();
				
				_configurationMenuPanel.setConfiguration(_submenuConfigurations.getSubmenu(AcideConfigurationMenu.CONFIGURATION_MENU_NAME));
				
				_configurationMenuPanel.setDataFromConfiguration();
				
				_helpMenuPanel.setConfiguration(_submenuConfigurations.getSubmenu(AcideHelpMenu.HELP_MENU_NAME));
				
				_helpMenuPanel.setDataFromConfiguration();

			} catch (Exception exception) {

				// Displays an error message
				JOptionPane.showMessageDialog(null, exception.getMessage(),
						AcideLanguageManager.getInstance().getLabels()
								.getString("s269"), JOptionPane.ERROR_MESSAGE);

				// Updates the log
				AcideLog.getLog().error(exception.getMessage());
			}
		
	}
	
	/**
	 * Sets the ACIDE - A Configurable IDE menu configuration window
	 * configuration.
	 */
	private void setWindowConfiguration() {

		// Sets the title
		if (_forModifying) {

			String currentMenuConfiguration = null;
			
			String currentMenuNewConfiguration = null;

			try {

				// Gets the the ACIDE - A Configurable IDE current menu
				// configuration
				currentMenuConfiguration = AcideResourceManager.getInstance()
						.getProperty("currentMenuConfiguration");
				
				//Gets the the ACIDE - A Configurable IDE current menu
				// configuration
				currentMenuNewConfiguration = AcideResourceManager.getInstance()
						.getProperty("currentMenuNewConfiguration");

				// Gets the name
				int index = currentMenuConfiguration.lastIndexOf("\\");
				if (index == -1)
					index = currentMenuConfiguration.lastIndexOf("/");
				currentMenuConfiguration = currentMenuConfiguration.substring(
						index + 1, currentMenuConfiguration.length() - 11);
				
				// Gets the name
				int index2 = currentMenuNewConfiguration.lastIndexOf("\\");
				if (index2 == -1)
					index2 = currentMenuNewConfiguration.lastIndexOf("/");
				currentMenuNewConfiguration = currentMenuNewConfiguration.substring(
						index2 + 1, currentMenuNewConfiguration.length() - 4);
			} catch (Exception exception) {

				// Updates the log
				AcideLog.getLog().error(exception.getMessage());

				// Displays an error message
				JOptionPane.showMessageDialog(null, exception.getMessage(),
						AcideLanguageManager.getInstance().getLabels()
								.getString("s295"), JOptionPane.ERROR_MESSAGE);
			}

			// Sets the window title
			setTitle(AcideLanguageManager.getInstance().getLabels()
					.getString("s532")
					+ " - " + currentMenuNewConfiguration);
		} else
			// Sets the window title
			setTitle(AcideLanguageManager.getInstance().getLabels()
					.getString("s298"));

		// Sets the window icon
		setIconImage(new ImageIcon(WINDOW_ICON).getImage());

		// The window is not resizable
		setResizable(true);

		// Packs the window components
		pack();

		// Centers the window
		setLocationRelativeTo(null);

		// Displays the window
		setVisible(true);

		// Disables the main window
		AcideMainWindow.getInstance().setEnabled(false);

		// Updates the log
		AcideLog.getLog().info(
				AcideLanguageManager.getInstance().getLabels()
						.getString("s530"));
	}
	

	
	/**
	 * Sets the listeners to the ACIDE - A Configurable IDE menu new configuration
	 * window components.
	 */
	private void setListeners() {

		// Sets the accept button action listener
		_acceptButton.addActionListener(new AcceptButtonAction());

		// Sets the cancel button action listener
		_cancelButton.addActionListener(new CancelButtonAction());

		// Sets the select all button action listener
		_selectAllButton.addActionListener(new SelectAllButtonAction());

		// Sets the select none button action listener
		_selectNoneButton.addActionListener(new SelectNoneButtonAction());
		
		// Sets the to up button action listener
		_up.addActionListener(new UpButtonAction());
		
		// Sets the to up button action listener
		_down.addActionListener(new DownButtonAction());
		
		// Sets the to the left button action listener
		_toTheLeft.addActionListener(new ToTheLeftButtonAction());
		
		// Sets the to the right button action listener
		_toTheRight.addActionListener(new ToTheRightButtonAction());
		
		// Sets the delete menu action listener
		_deleteMenu.addActionListener(new DeleteMenuButtonAction());
		
		// Sets the delete item action listener
		_deleteItem.addActionListener(new DeleteItemButtonAction());
		
		// Sets the add menu action listener
		_addMenu.addActionListener(new AddMenuButtonAction());
		
		// Sets the add menu action listener
		_addSubmenu.addActionListener(new AddSubmenuButtonAction());
		
		// Sets the add menu action listener
		_addItem.addActionListener(new AddItemButtonAction());

		// Sets the window closing listener
		addWindowListener(new AcideWindowClosingListener());
		
		// Puts the escape key in the input map of the window
		getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
				KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0, false), "EscapeKey");

		// Puts the escape key in the action map of the window
		getRootPane().getActionMap().put("EscapeKey", new EscapeKeyAction());
	}
	
	/**
	 * Returns the changes are saved flag.
	 * 
	 * @return the changes saved flag.
	 */
	public static boolean getChangesAreSaved() {
		return _changesAreSaved;
	}
	
	/**
	 * Sets a new value to the changes are saved flag.
	 * 
	 * @param changesAreSaved
	 *            new value to set.
	 */
	public static void setChangesAreSaved(boolean changesAreSaved) {
		_changesAreSaved = changesAreSaved;
	}
	
	
	/**
	 * Closes the ACIDE - A Configurable IDE menu configuration window.
	 */
	private void closeWindow() {

		// Enables the main window again
		AcideMainWindow.getInstance().setEnabled(true);

		// Closes the window
		dispose();

		// Brings the main window to the front
		AcideMainWindow.getInstance().setAlwaysOnTop(true);

		// But not permanently
		AcideMainWindow.getInstance().setAlwaysOnTop(false);
	}
	
	/**
	 * Applies the changes to the menu new configuration window
	 * @param changed
	 * 			if the have been there changes
	 */
	public void applyChangesToMenu(boolean changed){
		_fileMenuPanel.setAreThereChanges(changed);
		_fileMenuPanel.setDataFromConfiguration();
		((AcideFileMenuNewPanelTableModel) _fileMenuPanel.getTable().getModel()).fireTableDataChanged();
		
		_editMenuPanel.setAreThereChanges(changed);
		_editMenuPanel.setDataFromConfiguration();
		((AcideEditMenuNewPanelTableModel) _editMenuPanel.getTable().getModel()).fireTableDataChanged();
		
		_projectMenuPanel.setAreThereChanges(changed);
		_projectMenuPanel.setDataFromConfiguration();
		((AcideProjectMenuNewPanelTableModel) _projectMenuPanel.getTable().getModel()).fireTableDataChanged();
		
		_viewMenuPanel.setAreThereChanges(changed);
		_viewMenuPanel.setDataFromConfiguration();
		((AcideViewMenuNewPanelTableModel) _viewMenuPanel.getTable().getModel()).fireTableDataChanged();
		
		_configurationMenuPanel.setAreThereChanges(changed);
		_configurationMenuPanel.setDataFromConfiguration();
		((AcideConfigurationMenuNewPanelTableModel) _configurationMenuPanel.getTable().getModel()).fireTableDataChanged();
		
		_helpMenuPanel.setAreThereChanges(changed);
		_helpMenuPanel.setDataFromConfiguration();
		((AcideHelpMenuNewPanelTableModel) _helpMenuPanel.getTable().getModel()).fireTableDataChanged();
		
		Iterator<String> it = _insertedObjects.iterator();
		while (it.hasNext()){
			String s1 = it.next();
			AcideMenuInsertedPanel menuPanel = _insertedMenus.get(s1);
			menuPanel.setAreThereChanges(changed);
			menuPanel.setDataFromConfiguration();
			((AcideInsertedMenuNewPanelTableModel) menuPanel.getTable().getModel()).fireTableDataChanged();
		}
	}
	
	/**
	 * ACIDE - A Configurable IDE menu configuration window add menu button
	 * action listener.
	 * 
	 * @version 0.11
	 * @see ActionListener
	 */
	class AddMenuButtonAction implements ActionListener {

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.
		 * ActionEvent)
		 */
		@Override
		public void actionPerformed(ActionEvent actionEvent) {

			// Ask to the user for the text
			String name = JOptionPane.showInputDialog(
					null,
					AcideLanguageManager.getInstance().getLabels()
							.getString("s57"));
			
			if (name != null){
				//If the new menu already not exists
				if (!(name.equals(AcideFileMenu.FILE_MENU_NAME))
					&& !(name.equals(AcideEditMenu.EDIT_MENU_NAME))
					&& !(name.equals(AcideProjectMenu.PROJECT_MENU_NAME))
					&& !(name.equals(AcideViewMenu.VIEW_MENU_NAME))
					&& !(name.equals(AcideConfigurationMenu.CONFIGURATION_MENU_NAME))
					&& !(name.equals(AcideHelpMenu.HELP_MENU_NAME))
					&& !(_submenuConfigurations.hasSubmenu(name))){
					
					
					//Creates the new submenu
					AcideMenuSubmenuConfiguration ob = new AcideMenuSubmenuConfiguration(name);
					
					//Creates the panel for the submenu configuration
					AcideMenuInsertedPanel menuInsertedPanel = new AcideMenuInsertedPanel(name);
					
					//Inserts it at the inserted objects
					_insertedObjects.add(name);
					
					//Inserts the panel at the inserted menus
					_insertedMenus.put(ob.getName(), menuInsertedPanel);
					
					//Inserts the submenu in the menu configuration
					_submenuConfigurations.insertObject(ob);
					
					//Sets the configuration of the pane
					menuInsertedPanel.setConfiguration(ob);
					
					menuInsertedPanel.setDataFromConfiguration();
					
					//Refresh the tabbed pane
					_tabbedPane.removeAll();
				
					addToTabbedPane();
				
					validate(); 
				
				} else {
					
					// Displays a message
					JOptionPane.showMessageDialog(null, AcideLanguageManager
							.getInstance().getLabels().getString("s2225"));
					
				}
			}
				
		}
	}
	
	/**
	 * ACIDE - A Configurable IDE menu configuration window add submenu button
	 * action listener.
	 * 
	 * @version 0.11
	 * @see ActionListener
	 */
	class AddSubmenuButtonAction implements ActionListener {

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.
		 * ActionEvent)
		 */
		@Override
		public void actionPerformed(ActionEvent actionEvent) {
				
			// Ask to the user for the text
			String name = JOptionPane.showInputDialog(
					null,
					AcideLanguageManager.getInstance().getLabels()
							.getString("s57"));
			
			if (name != null){
				
				//Gets the name of the selected pane
				int selectedIndex = _tabbedPane.getSelectedIndex();
				
				String selectedTitle = _tabbedPane.getTitleAt(selectedIndex);
				
				AcideMenuSubmenuConfiguration menu = _submenuConfigurations.getSubmenu(selectedTitle);
					
				AcideMenuObjectConfiguration submenu;
				String path;
				
				//Adds the new submenu to the correct panel
				if (selectedTitle.equals(AcideFileMenu.FILE_MENU_NAME)){
					AcideFileMenuNewPanel selectedPanel = (AcideFileMenuNewPanel) _tabbedPane.getSelectedComponent();
					//Gets the selected object
					submenu = selectedPanel.getSelectedItem();
					if (submenu != null){
					path = selectedPanel.getSelectedItemPath();
					//Adds the new submenu and applies the changes
					selectedPanel.setAreThereChanges(menu.AddSubmenu(submenu, name, path));
					selectedPanel.setDataFromConfiguration();
					((AcideFileMenuNewPanelTableModel) selectedPanel.getTable().getModel()).fireTableDataChanged();
					} else {
						path = "Root/";
						submenu = menu;
						selectedPanel.setAreThereChanges(menu.AddSubmenu(submenu, name, path));
						selectedPanel.setDataFromConfiguration();
					}
				} else if (selectedTitle.equals(AcideEditMenu.EDIT_MENU_NAME)){
					AcideEditMenuNewPanel selectedPanel = (AcideEditMenuNewPanel) _tabbedPane.getSelectedComponent();
					//Gets the selected object
					submenu = selectedPanel.getSelectedItem();
					if (submenu != null){
					path = selectedPanel.getSelectedItemPath();
					//Adds the new submenu and applies the changes
					selectedPanel.setAreThereChanges(menu.AddSubmenu(submenu, name, path));
					selectedPanel.setDataFromConfiguration();
					((AcideEditMenuNewPanelTableModel) selectedPanel.getTable().getModel()).fireTableDataChanged();
					} else {
						path = "Root/";
						submenu = menu;
						selectedPanel.setAreThereChanges(menu.AddSubmenu(submenu, name, path));
						selectedPanel.setDataFromConfiguration();
					}
				} else if (selectedTitle.equals(AcideProjectMenu.PROJECT_MENU_NAME)){
					AcideProjectMenuNewPanel selectedPanel = (AcideProjectMenuNewPanel) _tabbedPane.getSelectedComponent();
					//Gets the selected object
					submenu = selectedPanel.getSelectedItem();
					if (submenu != null){
					path = selectedPanel.getSelectedItemPath();
					//Adds the new submenu and applies the changes
					selectedPanel.setAreThereChanges(menu.AddSubmenu(submenu, name, path));
					selectedPanel.setDataFromConfiguration();
					((AcideProjectMenuNewPanelTableModel) selectedPanel.getTable().getModel()).fireTableDataChanged();
					} else {
						path = "Root/";
						submenu = menu;
						selectedPanel.setAreThereChanges(menu.AddSubmenu(submenu, name, path));
						selectedPanel.setDataFromConfiguration();
					}
				} else if (selectedTitle.equals(AcideViewMenu.VIEW_MENU_NAME)){
					AcideViewMenuNewPanel selectedPanel = (AcideViewMenuNewPanel) _tabbedPane.getSelectedComponent();
					//Gets the selected object
					submenu = selectedPanel.getSelectedItem();
					if (submenu != null){
					path = selectedPanel.getSelectedItemPath();
					//Adds the new submenu and applies the changes
					selectedPanel.setAreThereChanges(menu.AddSubmenu(submenu, name, path));
					selectedPanel.setDataFromConfiguration();
					((AcideViewMenuNewPanelTableModel) selectedPanel.getTable().getModel()).fireTableDataChanged();
					} else {
						path = "Root/";
						submenu = menu;
						selectedPanel.setAreThereChanges(menu.AddSubmenu(submenu, name, path));
						selectedPanel.setDataFromConfiguration();
					}
				} else if (selectedTitle.equals(AcideConfigurationMenu.CONFIGURATION_MENU_NAME)){
					AcideConfigurationMenuNewPanel selectedPanel = (AcideConfigurationMenuNewPanel) _tabbedPane.getSelectedComponent();
					//Gets the selected object
					submenu = selectedPanel.getSelectedItem();
					if (submenu != null){
					path = selectedPanel.getSelectedItemPath();
					//Adds the new submenu and applies the changes
					selectedPanel.setAreThereChanges(menu.AddSubmenu(submenu, name, path));
					selectedPanel.setDataFromConfiguration();
					((AcideConfigurationMenuNewPanelTableModel) selectedPanel.getTable().getModel()).fireTableDataChanged();
					} else {
						path = "Root/";
						submenu = menu;
						selectedPanel.setAreThereChanges(menu.AddSubmenu(submenu, name, path));
						selectedPanel.setDataFromConfiguration();
					}
				} else if (selectedTitle.equals(AcideHelpMenu.HELP_MENU_NAME)){
					AcideHelpMenuNewPanel selectedPanel = (AcideHelpMenuNewPanel) _tabbedPane.getSelectedComponent();
					//Gets the selected object
					submenu = selectedPanel.getSelectedItem();
					if (submenu != null){
					path = selectedPanel.getSelectedItemPath();
					//Adds the new submenu and applies the changes
					selectedPanel.setAreThereChanges(menu.AddSubmenu(submenu, name, path));
					selectedPanel.setDataFromConfiguration();
					((AcideHelpMenuNewPanelTableModel) selectedPanel.getTable().getModel()).fireTableDataChanged();
					} else {
						path = "Root/";
						submenu = menu;
						selectedPanel.setAreThereChanges(menu.AddSubmenu(submenu, name, path));
						selectedPanel.setDataFromConfiguration();
					}
				} else {
					AcideMenuInsertedPanel selectedPanel = (AcideMenuInsertedPanel) _tabbedPane.getSelectedComponent();
					//Gets the selected object
					submenu = selectedPanel.getSelectedItem();
					if (submenu != null){
					path = selectedPanel.getSelectedItemPath();
					//Adds the new submenu and applies the changes
					selectedPanel.setAreThereChanges(menu.AddSubmenu(submenu, name, path));
					selectedPanel.setDataFromConfiguration();
					((AcideInsertedMenuNewPanelTableModel) selectedPanel.getTable().getModel()).fireTableDataChanged();
					} else {
						path = "Root/";
						submenu = menu;
						selectedPanel.setAreThereChanges(menu.AddSubmenu(submenu, name, path));
						selectedPanel.setDataFromConfiguration();
					}
				}
			}
			
		}
	}
	
	/**
	 * ACIDE - A Configurable IDE menu configuration window add item button
	 * action listener.
	 * 
	 * @version 0.11
	 * @see ActionListener
	 */
	class AddItemButtonAction implements ActionListener {

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.
		 * ActionEvent)
		 */
		@Override
		public void actionPerformed(ActionEvent actionEvent) {
			// Ask to the user for the text
			String name = JOptionPane.showInputDialog(
					null,
					AcideLanguageManager.getInstance().getLabels()
							.getString("s57"));
			
			if (name != null){
				
				//Gets the name of the selected pane
				int selectedIndex = _tabbedPane.getSelectedIndex();
				
				String selectedTitle = _tabbedPane.getTitleAt(selectedIndex);
				
				AcideMenuSubmenuConfiguration menu = _submenuConfigurations.getSubmenu(selectedTitle);
					
				AcideMenuObjectConfiguration submenu;
				String path;
				
				//Adds the new item to the correct panel
				if (selectedTitle.equals(AcideFileMenu.FILE_MENU_NAME)){
					AcideFileMenuNewPanel selectedPanel = (AcideFileMenuNewPanel) _tabbedPane.getSelectedComponent();
					//Gets the selected object
					submenu = selectedPanel.getSelectedItem();
					if (submenu != null){
					path = selectedPanel.getSelectedItemPath();
					//Adds the new item and applies the changes
					selectedPanel.setAreThereChanges(menu.AddItem(submenu, name, path));
					selectedPanel.setDataFromConfiguration();
					((AcideFileMenuNewPanelTableModel) selectedPanel.getTable().getModel()).fireTableDataChanged();
					} else {
						path = "Root/";
						submenu = menu;
						selectedPanel.setAreThereChanges(menu.AddItem(submenu, name, path));
						selectedPanel.setDataFromConfiguration();
					}
				} else if (selectedTitle.equals(AcideEditMenu.EDIT_MENU_NAME)){
					AcideEditMenuNewPanel selectedPanel = (AcideEditMenuNewPanel) _tabbedPane.getSelectedComponent();
					//Gets the selected object
					submenu = selectedPanel.getSelectedItem();
					if (submenu != null){
					path = selectedPanel.getSelectedItemPath();
					//Adds the new item and applies the changes
					selectedPanel.setAreThereChanges(menu.AddItem(submenu, name, path));
					selectedPanel.setDataFromConfiguration();
					((AcideEditMenuNewPanelTableModel) selectedPanel.getTable().getModel()).fireTableDataChanged();
					}else {
						path = "Root/";
						submenu = menu;
						selectedPanel.setAreThereChanges(menu.AddItem(submenu, name, path));
						selectedPanel.setDataFromConfiguration();
					}
				} else if (selectedTitle.equals(AcideProjectMenu.PROJECT_MENU_NAME)){
					AcideProjectMenuNewPanel selectedPanel = (AcideProjectMenuNewPanel) _tabbedPane.getSelectedComponent();
					//Gets the selected object
					submenu = selectedPanel.getSelectedItem();
					if (submenu != null){
					path = selectedPanel.getSelectedItemPath();
					//Adds the new item and applies the changes
					selectedPanel.setAreThereChanges(menu.AddItem(submenu, name, path));
					selectedPanel.setDataFromConfiguration();
					((AcideProjectMenuNewPanelTableModel) selectedPanel.getTable().getModel()).fireTableDataChanged();
					}else {
						path = "Root/";
						submenu = menu;
						selectedPanel.setAreThereChanges(menu.AddItem(submenu, name, path));
						selectedPanel.setDataFromConfiguration();
					}
				} else if (selectedTitle.equals(AcideViewMenu.VIEW_MENU_NAME)){
					AcideViewMenuNewPanel selectedPanel = (AcideViewMenuNewPanel) _tabbedPane.getSelectedComponent();
					//Gets the selected object
					submenu = selectedPanel.getSelectedItem();
					if (submenu != null){
					path = selectedPanel.getSelectedItemPath();
					//Adds the new item and applies the changes
					selectedPanel.setAreThereChanges(menu.AddItem(submenu, name, path));
					selectedPanel.setDataFromConfiguration();
					((AcideViewMenuNewPanelTableModel) selectedPanel.getTable().getModel()).fireTableDataChanged();
					}else {
						path = "Root/";
						submenu = menu;
						selectedPanel.setAreThereChanges(menu.AddItem(submenu, name, path));
						selectedPanel.setDataFromConfiguration();
					}
				} else if (selectedTitle.equals(AcideConfigurationMenu.CONFIGURATION_MENU_NAME)){
					AcideConfigurationMenuNewPanel selectedPanel = (AcideConfigurationMenuNewPanel) _tabbedPane.getSelectedComponent();
					//Gets the selected object
					submenu = selectedPanel.getSelectedItem();
					if (submenu != null){
					path = selectedPanel.getSelectedItemPath();
					//Adds the new item and applies the changes
					selectedPanel.setAreThereChanges(menu.AddItem(submenu, name, path));
					selectedPanel.setDataFromConfiguration();
					((AcideConfigurationMenuNewPanelTableModel) selectedPanel.getTable().getModel()).fireTableDataChanged();
					}else {
						path = "Root/";
						submenu = menu;
						selectedPanel.setAreThereChanges(menu.AddItem(submenu, name, path));
						selectedPanel.setDataFromConfiguration();
					}
				} else if (selectedTitle.equals(AcideHelpMenu.HELP_MENU_NAME)){
					AcideHelpMenuNewPanel selectedPanel = (AcideHelpMenuNewPanel) _tabbedPane.getSelectedComponent();
					//Gets the selected object
					submenu = selectedPanel.getSelectedItem();
					if (submenu != null){
					path = selectedPanel.getSelectedItemPath();
					//Adds the new item and applies the changes
					selectedPanel.setAreThereChanges(menu.AddItem(submenu, name, path));
					selectedPanel.setDataFromConfiguration();
					((AcideHelpMenuNewPanelTableModel) selectedPanel.getTable().getModel()).fireTableDataChanged();
					}else {
						path = "Root/";
						submenu = menu;
						selectedPanel.setAreThereChanges(menu.AddItem(submenu, name, path));
						selectedPanel.setDataFromConfiguration();
					}
				} else {
					AcideMenuInsertedPanel selectedPanel = (AcideMenuInsertedPanel) _tabbedPane.getSelectedComponent();
					//Gets the selected object
					submenu = selectedPanel.getSelectedItem();
					if (submenu != null){
					path = selectedPanel.getSelectedItemPath();
					//Adds the new item and applies the changes
					selectedPanel.setAreThereChanges(menu.AddItem(submenu, name, path));
					selectedPanel.setDataFromConfiguration();
					((AcideInsertedMenuNewPanelTableModel) selectedPanel.getTable().getModel()).fireTableDataChanged();
					}else {
						path = "Root/";
						submenu = menu;
						selectedPanel.setAreThereChanges(menu.AddItem(submenu, name, path));
						selectedPanel.setDataFromConfiguration();
					}
				}
			}	
		}
	}
	
	
	/**
	 * ACIDE - A Configurable IDE menu configuration window delete menu button
	 * action listener.
	 * 
	 * @version 0.11
	 * @see ActionListener
	 */
	class DeleteMenuButtonAction implements ActionListener {

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.
		 * ActionEvent)
		 */
		@Override
		public void actionPerformed(ActionEvent actionEvent) {
			int selectedIndex = _tabbedPane.getSelectedIndex();
			
			String name = _tabbedPane.getTitleAt(selectedIndex);

			//If the menu can be deleted
			if (!(name.equals(AcideFileMenu.FILE_MENU_NAME))
					&& !(name.equals(AcideEditMenu.EDIT_MENU_NAME))
					&& !(name.equals(AcideProjectMenu.PROJECT_MENU_NAME))
					&& !(name.equals(AcideViewMenu.VIEW_MENU_NAME))
					&& !(name.equals(AcideConfigurationMenu.CONFIGURATION_MENU_NAME))
					&& !(name.equals(AcideHelpMenu.HELP_MENU_NAME))){
				
				int returnValueAreYouSure = JOptionPane.OK_OPTION;
				
				returnValueAreYouSure = JOptionPane.showConfirmDialog(null,
						AcideLanguageManager.getInstance().getLabels()
								.getString("s2210"), AcideLanguageManager
								.getInstance().getLabels().getString("s953"),JOptionPane.YES_NO_OPTION);
				// If it is OK
				if (returnValueAreYouSure == JOptionPane.OK_OPTION) {

				//Removes it from inserted objects
				_insertedObjects.remove(name);
				
				//Removes if from inserted menus
				_insertedMenus.remove(name);
				
				//Removes it from the menu configuration
				_submenuConfigurations.deleteSubmenu(name);
				
				//Refresh tabbed pane
				_tabbedPane.removeAll();
				
				addToTabbedPane();
				
				validate();
				}
				
			}
			
			else {
				
				// Displays a message
				JOptionPane.showMessageDialog(null, AcideLanguageManager
						.getInstance().getLabels().getString("s2204"));
			}
				
		}
	}
	
	/**
	 * ACIDE - A Configurable IDE menu configuration window delete item button
	 * action listener.
	 * 
	 * @version 0.11
	 * @see ActionListener
	 */
	class DeleteItemButtonAction implements ActionListener {

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.
		 * ActionEvent)
		 */
		@Override
		public void actionPerformed(ActionEvent actionEvent) {
			//Gets the name of the pane
			int selectedIndex = _tabbedPane.getSelectedIndex();
			
			String selectedTitle = _tabbedPane.getTitleAt(selectedIndex);
			
			
			AcideMenuSubmenuConfiguration menu = _submenuConfigurations.getSubmenu(selectedTitle);
				
			AcideMenuObjectConfiguration submenu;
			String path;
			
			//Removes it from the correct pane
			if (selectedTitle.equals(AcideFileMenu.FILE_MENU_NAME)){
				AcideFileMenuNewPanel selectedPanel = (AcideFileMenuNewPanel) _tabbedPane.getSelectedComponent();
				//Gets the selected object
				submenu = selectedPanel.getSelectedItem();
				//Deletes it if is erasable
				if (submenu != null){
				if (submenu.isErasable()){
					int returnValueAreYouSure = JOptionPane.OK_OPTION;
					
					returnValueAreYouSure = JOptionPane.showConfirmDialog(null,
							AcideLanguageManager.getInstance().getLabels()
									.getString("s2211"), AcideLanguageManager
									.getInstance().getLabels().getString("s953"),JOptionPane.YES_NO_OPTION);
					// If it is OK
					if (returnValueAreYouSure == JOptionPane.OK_OPTION) {
						path = selectedPanel.getSelectedItemPath();
						selectedPanel.setAreThereChanges(menu.delete(submenu, path));
						selectedPanel.setDataFromConfiguration();
						((AcideFileMenuNewPanelTableModel) selectedPanel.getTable().getModel()).fireTableDataChanged();
					}
				} else {
					// Displays a message
					JOptionPane.showMessageDialog(null, AcideLanguageManager
							.getInstance().getLabels().getString("s2204"));
				}
				}
			} else if (selectedTitle.equals(AcideEditMenu.EDIT_MENU_NAME)){
				AcideEditMenuNewPanel selectedPanel = (AcideEditMenuNewPanel) _tabbedPane.getSelectedComponent();
				//Gets the selected object
				submenu = selectedPanel.getSelectedItem();
				//Deletes it if is erasable
				if (submenu != null){
				if (submenu.isErasable()){
					int returnValueAreYouSure = JOptionPane.OK_OPTION;
					
					returnValueAreYouSure = JOptionPane.showConfirmDialog(null,
							AcideLanguageManager.getInstance().getLabels()
									.getString("s2211"), AcideLanguageManager
									.getInstance().getLabels().getString("s953"),JOptionPane.YES_NO_OPTION);
					// If it is OK
					if (returnValueAreYouSure == JOptionPane.OK_OPTION) {
						path = selectedPanel.getSelectedItemPath();
						selectedPanel.setAreThereChanges(menu.delete(submenu, path));
						selectedPanel.setDataFromConfiguration();
						((AcideEditMenuNewPanelTableModel) selectedPanel.getTable().getModel()).fireTableDataChanged();
					}
			} else {
				// Displays a message
				JOptionPane.showMessageDialog(null, AcideLanguageManager
						.getInstance().getLabels().getString("s2204"));
			}
			}
			} else if (selectedTitle.equals(AcideProjectMenu.PROJECT_MENU_NAME)){
				AcideProjectMenuNewPanel selectedPanel = (AcideProjectMenuNewPanel) _tabbedPane.getSelectedComponent();
				//Gets the selected object
				submenu = selectedPanel.getSelectedItem();
				//Deletes it if is erasable
				if (submenu != null){
				if (submenu.isErasable()){
					int returnValueAreYouSure = JOptionPane.OK_OPTION;
					
					returnValueAreYouSure = JOptionPane.showConfirmDialog(null,
							AcideLanguageManager.getInstance().getLabels()
									.getString("s2211"), AcideLanguageManager
									.getInstance().getLabels().getString("s953"),JOptionPane.YES_NO_OPTION);
					// If it is OK
					if (returnValueAreYouSure == JOptionPane.OK_OPTION) {
						path = selectedPanel.getSelectedItemPath();
						selectedPanel.setAreThereChanges(menu.delete(submenu, path));
						selectedPanel.setDataFromConfiguration();
						((AcideProjectMenuNewPanelTableModel) selectedPanel.getTable().getModel()).fireTableDataChanged();
					}
				} else {
					// Displays a message
					JOptionPane.showMessageDialog(null, AcideLanguageManager
							.getInstance().getLabels().getString("s2204"));
				}
				}
			} else if (selectedTitle.equals(AcideViewMenu.VIEW_MENU_NAME)){
				AcideViewMenuNewPanel selectedPanel = (AcideViewMenuNewPanel) _tabbedPane.getSelectedComponent();
				//Gets the selected object
				submenu = selectedPanel.getSelectedItem();
				//Deletes it if is erasable
				if (submenu != null){
				if (submenu.isErasable()){
					int returnValueAreYouSure = JOptionPane.OK_OPTION;
					
					returnValueAreYouSure = JOptionPane.showConfirmDialog(null,
							AcideLanguageManager.getInstance().getLabels()
									.getString("s2211"), AcideLanguageManager
									.getInstance().getLabels().getString("s953"),JOptionPane.YES_NO_OPTION);
					// If it is OK
					if (returnValueAreYouSure == JOptionPane.OK_OPTION) {
						path = selectedPanel.getSelectedItemPath();
						selectedPanel.setAreThereChanges(menu.delete(submenu, path));
						selectedPanel.setDataFromConfiguration();
						((AcideViewMenuNewPanelTableModel) selectedPanel.getTable().getModel()).fireTableDataChanged();
					}
				} else {
					// Displays a message
					JOptionPane.showMessageDialog(null, AcideLanguageManager
							.getInstance().getLabels().getString("s2204"));
				}
				}
			} else if (selectedTitle.equals(AcideConfigurationMenu.CONFIGURATION_MENU_NAME)){
				AcideConfigurationMenuNewPanel selectedPanel = (AcideConfigurationMenuNewPanel) _tabbedPane.getSelectedComponent();
				//Gets the selected object
				submenu = selectedPanel.getSelectedItem();
				//Deletes it if is erasable
				if (submenu != null){
				if (submenu.isErasable()){
					int returnValueAreYouSure = JOptionPane.OK_OPTION;
					
					returnValueAreYouSure = JOptionPane.showConfirmDialog(null,
							AcideLanguageManager.getInstance().getLabels()
									.getString("s2211"), AcideLanguageManager
									.getInstance().getLabels().getString("s953"),JOptionPane.YES_NO_OPTION);
					// If it is OK
					if (returnValueAreYouSure == JOptionPane.OK_OPTION) {
						path = selectedPanel.getSelectedItemPath();
						selectedPanel.setAreThereChanges(menu.delete(submenu, path));
						selectedPanel.setDataFromConfiguration();
						((AcideConfigurationMenuNewPanelTableModel) selectedPanel.getTable().getModel()).fireTableDataChanged();
					}
				} else {
					// Displays a message
					JOptionPane.showMessageDialog(null, AcideLanguageManager
							.getInstance().getLabels().getString("s2204"));
				}
				}
			} else if (selectedTitle.equals(AcideHelpMenu.HELP_MENU_NAME)){
				AcideHelpMenuNewPanel selectedPanel = (AcideHelpMenuNewPanel) _tabbedPane.getSelectedComponent();
				//Gets the selected object
				submenu = selectedPanel.getSelectedItem();
				//Deletes it if is erasable
				if (submenu != null){
				if (submenu.isErasable()){
					int returnValueAreYouSure = JOptionPane.OK_OPTION;
					
					returnValueAreYouSure = JOptionPane.showConfirmDialog(null,
							AcideLanguageManager.getInstance().getLabels()
									.getString("s2211"), AcideLanguageManager
									.getInstance().getLabels().getString("s953"),JOptionPane.YES_NO_OPTION);
					// If it is OK
					if (returnValueAreYouSure == JOptionPane.OK_OPTION) {
						path = selectedPanel.getSelectedItemPath();
						selectedPanel.setAreThereChanges(menu.delete(submenu, path));
						selectedPanel.setDataFromConfiguration();
						((AcideHelpMenuNewPanelTableModel) selectedPanel.getTable().getModel()).fireTableDataChanged();
					}
				} else {
					// Displays a message
					JOptionPane.showMessageDialog(null, AcideLanguageManager
							.getInstance().getLabels().getString("s2204"));
				}
				}
			} else {
				AcideMenuInsertedPanel selectedPanel = (AcideMenuInsertedPanel) _tabbedPane.getSelectedComponent();
				//Gets the selected object
				submenu = selectedPanel.getSelectedItem();
				path = selectedPanel.getSelectedItemPath();
				//Deletes it if is erasable
				if (submenu != null){
				if (submenu.isErasable()){
					int returnValueAreYouSure = JOptionPane.OK_OPTION;
					
					returnValueAreYouSure = JOptionPane.showConfirmDialog(null,
							AcideLanguageManager.getInstance().getLabels()
									.getString("s2211"), AcideLanguageManager
									.getInstance().getLabels().getString("s953"),JOptionPane.YES_NO_OPTION);
					// If it is OK
					if (returnValueAreYouSure == JOptionPane.OK_OPTION) {
						selectedPanel.setAreThereChanges(menu.delete(submenu, path));
						selectedPanel.setDataFromConfiguration();
						((AcideInsertedMenuNewPanelTableModel) selectedPanel.getTable().getModel()).fireTableDataChanged();
					}
				} else {
					// Displays a message
					JOptionPane.showMessageDialog(null, AcideLanguageManager
							.getInstance().getLabels().getString("s2204"));
				}
				}
			}
			
		}
	}

	/**
	 * ACIDE - A Configurable IDE menu configuration window up button
	 * action listener.
	 * 
	 * @version 0.11
	 * @see ActionListener
	 */
	class UpButtonAction implements ActionListener {

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.
		 * ActionEvent)
		 */
		@Override
		public void actionPerformed(ActionEvent actionEvent) {
			
			int selectedIndex = _tabbedPane.getSelectedIndex();
			
			String selectedTitle = _tabbedPane.getTitleAt(selectedIndex);
			
			AcideMenuSubmenuConfiguration menu = _submenuConfigurations.getSubmenu(selectedTitle);
				
			AcideMenuObjectConfiguration submenu;
			String path;
			
			if (selectedTitle.equals(AcideFileMenu.FILE_MENU_NAME)){
				AcideFileMenuNewPanel selectedPanel = (AcideFileMenuNewPanel) _tabbedPane.getSelectedComponent();
				submenu = selectedPanel.getSelectedItem();
				if (submenu != null){
				path = selectedPanel.getSelectedItemPath();
				int row = selectedPanel.getTable().getSelectedRow();
				boolean changed = menu.toUp(submenu, path);

				selectedPanel.setAreThereChanges(changed);
				selectedPanel.setDataFromConfiguration();
				((AcideFileMenuNewPanelTableModel) selectedPanel.getTable().getModel()).fireTableDataChanged();
				if (changed){
					selectedPanel.getTable().setRowSelectionInterval(row - 1, row - 1);
					AcideMenuObjectConfiguration submenuAux = selectedPanel.getSelectedItem();
					while (!submenu.getName().equals(submenuAux.getName())){
						row--;
						selectedPanel.getTable().setRowSelectionInterval(row - 1, row - 1);
						submenuAux = selectedPanel.getSelectedItem();
					}
				}
				else
					selectedPanel.getTable().setRowSelectionInterval(row, row);
				}
			} else if (selectedTitle.equals(AcideEditMenu.EDIT_MENU_NAME)){
				AcideEditMenuNewPanel selectedPanel = (AcideEditMenuNewPanel) _tabbedPane.getSelectedComponent();
				submenu = selectedPanel.getSelectedItem();
				if (submenu != null){
				path = selectedPanel.getSelectedItemPath();
				int row = selectedPanel.getTable().getSelectedRow();
				boolean changed = menu.toUp(submenu, path);

				selectedPanel.setAreThereChanges(changed);
				selectedPanel.setDataFromConfiguration();
				((AcideEditMenuNewPanelTableModel) selectedPanel.getTable().getModel()).fireTableDataChanged();
				if (changed){
					selectedPanel.getTable().setRowSelectionInterval(row - 1, row - 1);
					AcideMenuObjectConfiguration submenuAux = selectedPanel.getSelectedItem();
					while (!submenu.getName().equals(submenuAux.getName())){
						row--;
						selectedPanel.getTable().setRowSelectionInterval(row - 1, row - 1);
						submenuAux = selectedPanel.getSelectedItem();
					}
				}
				else
					selectedPanel.getTable().setRowSelectionInterval(row, row);
				}
			} else if (selectedTitle.equals(AcideProjectMenu.PROJECT_MENU_NAME)){
				AcideProjectMenuNewPanel selectedPanel = (AcideProjectMenuNewPanel) _tabbedPane.getSelectedComponent();
				submenu = selectedPanel.getSelectedItem();
				if (submenu != null){
				path = selectedPanel.getSelectedItemPath();
				int row = selectedPanel.getTable().getSelectedRow();
				boolean changed = menu.toUp(submenu, path);

				selectedPanel.setAreThereChanges(changed);
				selectedPanel.setDataFromConfiguration();
				((AcideProjectMenuNewPanelTableModel) selectedPanel.getTable().getModel()).fireTableDataChanged();
				if (changed){
					selectedPanel.getTable().setRowSelectionInterval(row - 1, row - 1);
					AcideMenuObjectConfiguration submenuAux = selectedPanel.getSelectedItem();
					while (!submenu.getName().equals(submenuAux.getName())){
						row--;
						selectedPanel.getTable().setRowSelectionInterval(row - 1, row - 1);
						submenuAux = selectedPanel.getSelectedItem();
					}
				}
				else
					selectedPanel.getTable().setRowSelectionInterval(row, row);
				}
			} else if (selectedTitle.equals(AcideViewMenu.VIEW_MENU_NAME)){
				AcideViewMenuNewPanel selectedPanel = (AcideViewMenuNewPanel) _tabbedPane.getSelectedComponent();
				submenu = selectedPanel.getSelectedItem();
				if (submenu != null){
				path = selectedPanel.getSelectedItemPath();
				int row = selectedPanel.getTable().getSelectedRow();
				boolean changed = menu.toUp(submenu, path);

				selectedPanel.setAreThereChanges(changed);
				selectedPanel.setDataFromConfiguration();
				((AcideViewMenuNewPanelTableModel) selectedPanel.getTable().getModel()).fireTableDataChanged();
				if (changed){
					selectedPanel.getTable().setRowSelectionInterval(row - 1, row - 1);
					AcideMenuObjectConfiguration submenuAux = selectedPanel.getSelectedItem();
					while (!submenu.getName().equals(submenuAux.getName())){
						row--;
						selectedPanel.getTable().setRowSelectionInterval(row - 1, row - 1);
						submenuAux = selectedPanel.getSelectedItem();
					}
				}
				else
					selectedPanel.getTable().setRowSelectionInterval(row, row);
				}
			} else if (selectedTitle.equals(AcideConfigurationMenu.CONFIGURATION_MENU_NAME)){
				AcideConfigurationMenuNewPanel selectedPanel = (AcideConfigurationMenuNewPanel) _tabbedPane.getSelectedComponent();
				submenu = selectedPanel.getSelectedItem();
				if (submenu != null){
				path = selectedPanel.getSelectedItemPath();
				int row = selectedPanel.getTable().getSelectedRow();
				boolean changed = menu.toUp(submenu, path);

				selectedPanel.setAreThereChanges(changed);
				selectedPanel.setDataFromConfiguration();
				((AcideConfigurationMenuNewPanelTableModel) selectedPanel.getTable().getModel()).fireTableDataChanged();
				if (changed){
					selectedPanel.getTable().setRowSelectionInterval(row - 1, row - 1);
					AcideMenuObjectConfiguration submenuAux = selectedPanel.getSelectedItem();
					while (!submenu.getName().equals(submenuAux.getName())){
						row--;
						selectedPanel.getTable().setRowSelectionInterval(row - 1, row - 1);
						submenuAux = selectedPanel.getSelectedItem();
					}
				}
				else
					selectedPanel.getTable().setRowSelectionInterval(row, row);
				}
			} else if (selectedTitle.equals(AcideHelpMenu.HELP_MENU_NAME)){
				AcideHelpMenuNewPanel selectedPanel = (AcideHelpMenuNewPanel) _tabbedPane.getSelectedComponent();
				submenu = selectedPanel.getSelectedItem();
				if (submenu != null){
				path = selectedPanel.getSelectedItemPath();
				int row = selectedPanel.getTable().getSelectedRow();
				boolean changed = menu.toUp(submenu, path);

				selectedPanel.setAreThereChanges(changed);
				selectedPanel.setDataFromConfiguration();
				((AcideHelpMenuNewPanelTableModel) selectedPanel.getTable().getModel()).fireTableDataChanged();
				if (changed){
					selectedPanel.getTable().setRowSelectionInterval(row - 1, row - 1);
					AcideMenuObjectConfiguration submenuAux = selectedPanel.getSelectedItem();
					while (!submenu.getName().equals(submenuAux.getName())){
						row--;
						selectedPanel.getTable().setRowSelectionInterval(row - 1, row - 1);
						submenuAux = selectedPanel.getSelectedItem();
					}
				}
				else
					selectedPanel.getTable().setRowSelectionInterval(row, row);
				}
			} else {
				AcideMenuInsertedPanel selectedPanel = (AcideMenuInsertedPanel) _tabbedPane.getSelectedComponent();
				submenu = selectedPanel.getSelectedItem();
				if (submenu != null){
				path = selectedPanel.getSelectedItemPath();
				int row = selectedPanel.getTable().getSelectedRow();
				boolean changed = menu.toUp(submenu, path);

				selectedPanel.setAreThereChanges(changed);
				selectedPanel.setDataFromConfiguration();
				((AcideInsertedMenuNewPanelTableModel) selectedPanel.getTable().getModel()).fireTableDataChanged();
				if (changed){
					selectedPanel.getTable().setRowSelectionInterval(row - 1, row - 1);
					AcideMenuObjectConfiguration submenuAux = selectedPanel.getSelectedItem();
					while (!submenu.getName().equals(submenuAux.getName())){
						row--;
						selectedPanel.getTable().setRowSelectionInterval(row - 1, row - 1);
						submenuAux = selectedPanel.getSelectedItem();
					}
				}
				else
					selectedPanel.getTable().setRowSelectionInterval(row, row);
				}
			}
			
			
		}
	}
	
	/**
	 * ACIDE - A Configurable IDE menu configuration window to the left button
	 * action listener.
	 * 
	 * @version 0.11
	 * @see ActionListener
	 */
	class ToTheLeftButtonAction implements ActionListener {

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.
		 * ActionEvent)
		 */
		@Override
		public void actionPerformed(ActionEvent actionEvent) {
			
			int selectedIndex = _tabbedPane.getSelectedIndex();
			
			String name = _tabbedPane.getTitleAt(selectedIndex);
			
			AcideMenuSubmenuConfiguration menu = _submenuConfigurations.getSubmenu(name);
			
			String path = "Root/";
			
			if (_submenuConfigurations.toUp(menu, path)){
				
				_tabbedPane.removeAll();
				
				addToTabbedPane();
				
				validate();
				
				_tabbedPane.setSelectedIndex(selectedIndex - 1); 
			}
	
			
		}
	}
	
	public AcideMenuSubmenuConfiguration getSubmenuConfiguration(){
		return _submenuConfigurations;
	}
	
	/**
	 * ACIDE - A Configurable IDE menu configuration window to the right button
	 * action listener.
	 * 
	 * @version 0.11
	 * @see ActionListener
	 */
	class ToTheRightButtonAction implements ActionListener {

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.
		 * ActionEvent)
		 */
		@Override
		public void actionPerformed(ActionEvent actionEvent) {
			
			int selectedIndex = _tabbedPane.getSelectedIndex();
			
			String name = _tabbedPane.getTitleAt(selectedIndex);
			
			AcideMenuSubmenuConfiguration menu = _submenuConfigurations.getSubmenu(name);
			
			String path = "Root/";
			
			if (_submenuConfigurations.toDown(menu, path)){
				
				_tabbedPane.removeAll();
				
				addToTabbedPane();
				
				validate();
				
				_tabbedPane.setSelectedIndex(selectedIndex + 1); 
			}
				
		}
	}


	/**
	 * ACIDE - A Configurable IDE menu configuration window down button
	 * action listener.
	 * 
	 * @version 0.11
	 * @see ActionListener
	 */
	class DownButtonAction implements ActionListener {

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.
		 * ActionEvent)
		 */
		@Override
		public void actionPerformed(ActionEvent actionEvent) {
			
			int selectedIndex = _tabbedPane.getSelectedIndex();
			
			String selectedTitle = _tabbedPane.getTitleAt(selectedIndex);
			
			AcideMenuSubmenuConfiguration menu = _submenuConfigurations.getSubmenu(selectedTitle);
				
			AcideMenuObjectConfiguration submenu;
			String path;
			if (selectedTitle.equals(AcideFileMenu.FILE_MENU_NAME)){
				AcideFileMenuNewPanel selectedPanel = (AcideFileMenuNewPanel) _tabbedPane.getSelectedComponent();
				submenu = selectedPanel.getSelectedItem();
				if (submenu != null){
				path = selectedPanel.getSelectedItemPath();
				int row = selectedPanel.getTable().getSelectedRow();
				boolean changed = menu.toDown(submenu, path);

				selectedPanel.setAreThereChanges(changed);
				selectedPanel.setDataFromConfiguration();
				((AcideFileMenuNewPanelTableModel) selectedPanel.getTable().getModel()).fireTableDataChanged();
				if (changed){
					selectedPanel.getTable().setRowSelectionInterval(row + 1, row + 1);
					AcideMenuObjectConfiguration submenuAux = selectedPanel.getSelectedItem();
					while (!submenu.getName().equals(submenuAux.getName())){
						row++;
						selectedPanel.getTable().setRowSelectionInterval(row + 1, row + 1);
						submenuAux = selectedPanel.getSelectedItem();
					}
				}
				else
					selectedPanel.getTable().setRowSelectionInterval(row, row);
				}
			} else if (selectedTitle.equals(AcideEditMenu.EDIT_MENU_NAME)){
				AcideEditMenuNewPanel selectedPanel = (AcideEditMenuNewPanel) _tabbedPane.getSelectedComponent();
				submenu = selectedPanel.getSelectedItem();
				if (submenu != null){
				path = selectedPanel.getSelectedItemPath();
				int row = selectedPanel.getTable().getSelectedRow();
				boolean changed = menu.toDown(submenu, path);

				selectedPanel.setAreThereChanges(changed);
				selectedPanel.setDataFromConfiguration();
				((AcideEditMenuNewPanelTableModel) selectedPanel.getTable().getModel()).fireTableDataChanged();
				if (changed){
					selectedPanel.getTable().setRowSelectionInterval(row + 1, row + 1);
					AcideMenuObjectConfiguration submenuAux = selectedPanel.getSelectedItem();
					while (!submenu.getName().equals(submenuAux.getName())){
						row++;
						selectedPanel.getTable().setRowSelectionInterval(row + 1, row + 1);
						submenuAux = selectedPanel.getSelectedItem();
					}
				}
				else
					selectedPanel.getTable().setRowSelectionInterval(row, row);
				}
			} else if (selectedTitle.equals(AcideProjectMenu.PROJECT_MENU_NAME)){
				AcideProjectMenuNewPanel selectedPanel = (AcideProjectMenuNewPanel) _tabbedPane.getSelectedComponent();
				submenu = selectedPanel.getSelectedItem();
				if (submenu != null){
				path = selectedPanel.getSelectedItemPath();
				int row = selectedPanel.getTable().getSelectedRow();
				boolean changed = menu.toDown(submenu, path);

				selectedPanel.setAreThereChanges(changed);
				selectedPanel.setDataFromConfiguration();
				((AcideProjectMenuNewPanelTableModel) selectedPanel.getTable().getModel()).fireTableDataChanged();
				if (changed){
					selectedPanel.getTable().setRowSelectionInterval(row + 1, row + 1);
					AcideMenuObjectConfiguration submenuAux = selectedPanel.getSelectedItem();
					while (!submenu.getName().equals(submenuAux.getName())){
						row++;
						selectedPanel.getTable().setRowSelectionInterval(row + 1, row + 1);
						submenuAux = selectedPanel.getSelectedItem();
					}
				}
				else
					selectedPanel.getTable().setRowSelectionInterval(row, row);
				}
			} else if (selectedTitle.equals(AcideViewMenu.VIEW_MENU_NAME)){
				AcideViewMenuNewPanel selectedPanel = (AcideViewMenuNewPanel) _tabbedPane.getSelectedComponent();
				submenu = selectedPanel.getSelectedItem();
				if (submenu != null){
				path = selectedPanel.getSelectedItemPath();
				int row = selectedPanel.getTable().getSelectedRow();
				boolean changed = menu.toDown(submenu, path);

				selectedPanel.setAreThereChanges(changed);
				selectedPanel.setDataFromConfiguration();
				((AcideViewMenuNewPanelTableModel) selectedPanel.getTable().getModel()).fireTableDataChanged();
				if (changed){
					selectedPanel.getTable().setRowSelectionInterval(row + 1, row + 1);
					AcideMenuObjectConfiguration submenuAux = selectedPanel.getSelectedItem();
					while (!submenu.getName().equals(submenuAux.getName())){
						row++;
						selectedPanel.getTable().setRowSelectionInterval(row + 1, row + 1);
						submenuAux = selectedPanel.getSelectedItem();
					}
				}
				else
					selectedPanel.getTable().setRowSelectionInterval(row, row);
				}
			} else if (selectedTitle.equals(AcideConfigurationMenu.CONFIGURATION_MENU_NAME)){
				AcideConfigurationMenuNewPanel selectedPanel = (AcideConfigurationMenuNewPanel) _tabbedPane.getSelectedComponent();
				submenu = selectedPanel.getSelectedItem();
				if (submenu != null){
				path = selectedPanel.getSelectedItemPath();
				int row = selectedPanel.getTable().getSelectedRow();
				boolean changed = menu.toDown(submenu, path);

				selectedPanel.setAreThereChanges(changed);
				selectedPanel.setDataFromConfiguration();
				((AcideConfigurationMenuNewPanelTableModel) selectedPanel.getTable().getModel()).fireTableDataChanged();
				if (changed){
					selectedPanel.getTable().setRowSelectionInterval(row + 1, row + 1);
					AcideMenuObjectConfiguration submenuAux = selectedPanel.getSelectedItem();
					while (!submenu.getName().equals(submenuAux.getName())){
						row++;
						selectedPanel.getTable().setRowSelectionInterval(row + 1, row + 1);
						submenuAux = selectedPanel.getSelectedItem();
					}
				}
				else
					selectedPanel.getTable().setRowSelectionInterval(row, row);
				}
			} else if (selectedTitle.equals(AcideHelpMenu.HELP_MENU_NAME)){
				AcideHelpMenuNewPanel selectedPanel = (AcideHelpMenuNewPanel) _tabbedPane.getSelectedComponent();
				submenu = selectedPanel.getSelectedItem();
				if (submenu != null){
				path = selectedPanel.getSelectedItemPath();
				int row = selectedPanel.getTable().getSelectedRow();
				boolean changed = menu.toDown(submenu, path);

				selectedPanel.setAreThereChanges(changed);
				selectedPanel.setDataFromConfiguration();
				((AcideHelpMenuNewPanelTableModel) selectedPanel.getTable().getModel()).fireTableDataChanged();
				if (changed){
					selectedPanel.getTable().setRowSelectionInterval(row + 1, row + 1);
					AcideMenuObjectConfiguration submenuAux = selectedPanel.getSelectedItem();
					while (!submenu.getName().equals(submenuAux.getName())){
						row++;
						selectedPanel.getTable().setRowSelectionInterval(row + 1, row + 1);
						submenuAux = selectedPanel.getSelectedItem();
					}
				}
				else
					selectedPanel.getTable().setRowSelectionInterval(row, row);
				}
			} else {
				AcideMenuInsertedPanel selectedPanel = (AcideMenuInsertedPanel) _tabbedPane.getSelectedComponent();
				submenu = selectedPanel.getSelectedItem();
				if (submenu != null){
				path = selectedPanel.getSelectedItemPath();
				int row = selectedPanel.getTable().getSelectedRow();
				boolean changed = menu.toDown(submenu, path);

				selectedPanel.setAreThereChanges(changed);
				selectedPanel.setDataFromConfiguration();
				((AcideInsertedMenuNewPanelTableModel) selectedPanel.getTable().getModel()).fireTableDataChanged();
				if (changed){
					selectedPanel.getTable().setRowSelectionInterval(row + 1, row + 1);
					AcideMenuObjectConfiguration submenuAux = selectedPanel.getSelectedItem();
					while (!submenu.getName().equals(submenuAux.getName())){
						row++;
						selectedPanel.getTable().setRowSelectionInterval(row + 1, row + 1);
						submenuAux = selectedPanel.getSelectedItem();
					}
				}
				else
					selectedPanel.getTable().setRowSelectionInterval(row, row);
				}
			}

		}
	}
	
	
	/**
	 * ACIDE - A Configurable IDE menu configuration window select none button
	 * action listener.
	 * 
	 * @version 0.11
	 * @see ActionListener
	 */
	class SelectNoneButtonAction implements ActionListener {

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.
		 * ActionEvent)
		 */
		@Override
		public void actionPerformed(ActionEvent actionEvent) {

			// Sets all submenus and items no visible
			_submenuConfigurations.allNoVisibles();
			
			_submenuConfigurations.getSubmenu(AcideConfigurationMenu.CONFIGURATION_MENU_NAME)
			.getSubmenu(AcideMenuMenu.MENU_MENU_NAME).allVisibles();
			
			_submenuConfigurations.getSubmenu(AcideConfigurationMenu.CONFIGURATION_MENU_NAME)
			.getSubmenu(AcideMenuMenu.MENU_MENU_NAME).setVisible(true);
			
			//Repaint the tables
			
			((AcideFileMenuNewPanelTableModel) _fileMenuPanel.getTable().getModel()).fireTableDataChanged();
			
			((AcideEditMenuNewPanelTableModel) _editMenuPanel.getTable().getModel()).fireTableDataChanged();
			
			((AcideProjectMenuNewPanelTableModel) _projectMenuPanel.getTable().getModel()).fireTableDataChanged();
			
			((AcideViewMenuNewPanelTableModel) _viewMenuPanel.getTable().getModel()).fireTableDataChanged();
			
			((AcideConfigurationMenuNewPanelTableModel) _configurationMenuPanel.getTable().getModel()).fireTableDataChanged();

			((AcideHelpMenuNewPanelTableModel) _helpMenuPanel.getTable().getModel()).fireTableDataChanged();

			Iterator<String> it = _insertedObjects.iterator();
			while (it.hasNext()){
				String name = it.next();
				AcideMenuInsertedPanel menuInsertedPanel = _insertedMenus.get(name);
				
				((AcideInsertedMenuNewPanelTableModel) menuInsertedPanel.getTable().getModel()).fireTableDataChanged();
			}

		}
	}

	/**
	 * ACIDE - A Configurable IDE menu configuration window select all button
	 * action listener.
	 * 
	 * @version 0.11
	 * @see ActionListener
	 */
	class SelectAllButtonAction implements ActionListener {

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.
		 * ActionEvent)
		 */
		@Override
		public void actionPerformed(ActionEvent actionEvent) {

			// Sets all submenus and items visible
			_submenuConfigurations.allVisibles();
			
			
			//Repaint the tables
			
			((AcideFileMenuNewPanelTableModel) _fileMenuPanel.getTable().getModel()).fireTableDataChanged();
			
			((AcideEditMenuNewPanelTableModel) _editMenuPanel.getTable().getModel()).fireTableDataChanged();
			
			((AcideProjectMenuNewPanelTableModel) _projectMenuPanel.getTable().getModel()).fireTableDataChanged();
			
			((AcideViewMenuNewPanelTableModel) _viewMenuPanel.getTable().getModel()).fireTableDataChanged();
			
			((AcideConfigurationMenuNewPanelTableModel) _configurationMenuPanel.getTable().getModel()).fireTableDataChanged();

			((AcideHelpMenuNewPanelTableModel) _helpMenuPanel.getTable().getModel()).fireTableDataChanged();

			Iterator<String> it = _insertedObjects.iterator();
			while (it.hasNext()){
				String name = it.next();
				AcideMenuInsertedPanel menuInsertedPanel = _insertedMenus.get(name);
				
				((AcideInsertedMenuNewPanelTableModel) menuInsertedPanel.getTable().getModel()).fireTableDataChanged();
			}
		}
	}

	/**
	 * ACIDE - A Configurable IDE menu configuration window cancel button action
	 * listener.
	 * 
	 * @version 0.11
	 * @see ActionListener
	 */
	class CancelButtonAction implements ActionListener {

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.
		 * ActionEvent)
		 */
		@Override
		public void actionPerformed(ActionEvent actionEvent) {

			// Updates the log
			AcideLog.getLog().info(
					AcideLanguageManager.getInstance().getLabels()
							.getString("s520"));

			// Closes the window
			closeWindow();
		}

	}

	/**
	 * ACIDE - A Configurable IDE menu configuration window accept button action
	 * listener.
	 * 
	 * @version 0.11
	 * @see ActionListener
	 */
	class AcceptButtonAction implements ActionListener {

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.
		 * ActionEvent)
		 */
		@Override
		public void actionPerformed(ActionEvent actionEvent) {
			
			AcideMenuItemsConfiguration.getInstance().reset(_submenuConfigurations.getItemsManager());

//			// Creates the menu item information list
//			ArrayList<AcideMenuItemInformation> menuItemList = buildMenuItemInformationList();
//
//			// Stores the new menu item information list
//			AcideMenuConfiguration.getInstance().setMenuElementList(
//					menuItemList);

			// Picks the new name
			String newName = "";
			if (_forModifying)
				newName = "./configuration/menu/lastModified.xml";
			else
				newName = "./configuration/menu/newMenu.xml";
			
			newName = AcideResourceManager.getInstance().replaceSeparators(newName);

			// Saves the new configuration
			//AcideMenuConfiguration.getInstance().saveMenuConfigurationFile(
				//	newName);
			AcideMenuItemsConfiguration.getInstance().save(newName);
			AcideMenuItemsConfiguration.getInstance().save(AcideProjectConfiguration.getInstance()
					.getMenuNewConfiguration());

				try {

					// Gets the the ACIDE - A Configurable IDE current menu
					// configuration
					String currentMenuNewConfiguration = AcideResourceManager
							.getInstance().getProperty("currentMenuNewConfiguration");

					if (_changesAreSaved) {

						if (!currentMenuNewConfiguration
								.endsWith("lastModified.xml")
								&& !currentMenuNewConfiguration
										.endsWith("newMenu.xml")) {

							// Updates the the ACIDE - A Configurable IDE previous
							// menu
							// configuration
							AcideResourceManager.getInstance().setProperty(
									"previousMenuNewConfiguration",
									currentMenuNewConfiguration);
						}
					}

					// Updates the the ACIDE - A Configurable IDE current menu
					// configuration
					AcideResourceManager.getInstance().setProperty(
							"currentMenuNewConfiguration", newName);


				// Builds the menu
				AcideMainWindow.getInstance().getMenu()
						.modifyMenuBar();

				// Enables the save menu item in the configuration menu
				AcideMainWindow.getInstance().getMenu().getConfigurationMenu()
						.getMenuMenu().getSaveMenuMenuItem().setEnabled(true);

				// Validates the changes in the main window
				AcideMainWindow.getInstance().validate();

				// Repaints the main window
				AcideMainWindow.getInstance().repaint();

				// The changes are not saved
				_changesAreSaved = false;

				// Closes the window
				closeWindow();

				// Updates the log
				AcideLog.getLog().info(
						AcideLanguageManager.getInstance().getLabels()
								.getString("s519"));

				// If it is not the default project
				if (!AcideProjectConfiguration.getInstance().isDefaultProject())
					// The project has been modified
					AcideProjectConfiguration.getInstance().setIsModified(true);

			} catch (Exception exception) {

				// Updates the log
				AcideLog.getLog().error(exception.getMessage());

				// Displays an error message
				JOptionPane.showMessageDialog(null, exception.getMessage(),
						AcideLanguageManager.getInstance().getLabels()
								.getString("s292"), JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	/**
	 * ACIDE - A Configurable IDE menu configuration window escape key action.
	 * 
	 * @version 0.11
	 * @see AbstractAction
	 */
	class EscapeKeyAction extends AbstractAction {

		/**
		 * Escape key action serial version UID.
		 */
		private static final long serialVersionUID = 1L;

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent
		 * )
		 */
		@Override
		public void actionPerformed(ActionEvent actionEvent) {

			// Closes the window
			//closeWindow();
			int selectedIndex = _tabbedPane.getSelectedIndex();
			
			String selectedTitle = _tabbedPane.getTitleAt(selectedIndex);
			
			if (selectedTitle.equals(AcideFileMenu.FILE_MENU_NAME)){
				AcideFileMenuNewPanel selectedPanel = (AcideFileMenuNewPanel) _tabbedPane.getSelectedComponent();
				selectedPanel.getTable().clearSelection();
			} else if (selectedTitle.equals(AcideEditMenu.EDIT_MENU_NAME)){
				AcideEditMenuNewPanel selectedPanel = (AcideEditMenuNewPanel) _tabbedPane.getSelectedComponent();
				selectedPanel.getTable().clearSelection();
			} else if (selectedTitle.equals(AcideProjectMenu.PROJECT_MENU_NAME)){
				AcideProjectMenuNewPanel selectedPanel = (AcideProjectMenuNewPanel) _tabbedPane.getSelectedComponent();
				selectedPanel.getTable().clearSelection();
			} else if (selectedTitle.equals(AcideViewMenu.VIEW_MENU_NAME)){
				AcideViewMenuNewPanel selectedPanel = (AcideViewMenuNewPanel) _tabbedPane.getSelectedComponent();
				selectedPanel.getTable().clearSelection();
			} else if (selectedTitle.equals(AcideConfigurationMenu.CONFIGURATION_MENU_NAME)){
				AcideConfigurationMenuNewPanel selectedPanel = (AcideConfigurationMenuNewPanel) _tabbedPane.getSelectedComponent();
				selectedPanel.getTable().clearSelection();
			} else if (selectedTitle.equals(AcideHelpMenu.HELP_MENU_NAME)){
				AcideHelpMenuNewPanel selectedPanel = (AcideHelpMenuNewPanel) _tabbedPane.getSelectedComponent();
				selectedPanel.getTable().clearSelection();
			} else {
				AcideMenuInsertedPanel selectedPanel = (AcideMenuInsertedPanel) _tabbedPane.getSelectedComponent();
				selectedPanel.getTable().clearSelection();
			}
		}
	}
}
