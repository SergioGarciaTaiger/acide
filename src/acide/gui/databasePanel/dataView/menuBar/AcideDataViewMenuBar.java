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
package acide.gui.databasePanel.dataView.menuBar;

import javax.swing.*;

import acide.language.AcideLanguageManager;
import acide.log.AcideLog;

import acide.configuration.menu.AcideMenuConfiguration;
import acide.configuration.project.AcideProjectConfiguration;

import acide.resources.AcideResourceManager;

import java.awt.HeadlessException;

import acide.gui.databasePanel.dataView.AcideDatabaseDataView;
import acide.gui.databasePanel.dataView.menuBar.RecordMenu.AcideDataViewRecordMenu;
import acide.gui.databasePanel.dataView.menuBar.editMenu.AcideDataViewEditMenu;
import acide.gui.databasePanel.dataView.menuBar.fileMenu.AcideDataViewFileMenu;
import acide.gui.databasePanel.dataView.menuBar.viewMenu.AcideDataViewViewMenu;
import acide.gui.fileEditor.fileEditorManager.listeners.AcideFileEditorManagerChangeListener;
import acide.gui.mainWindow.AcideMainWindow;
import acide.gui.menuBar.editMenu.AcideEditMenu;
import acide.gui.menuBar.fileMenu.AcideFileMenu;
import acide.gui.menuBar.helpMenu.AcideHelpMenu;
import acide.gui.menuBar.listeners.AcideMenuBarMouseClickListener;
import acide.gui.menuBar.projectMenu.AcideProjectMenu;
import acide.gui.menuBar.viewMenu.AcideViewMenu;

/**
 * ACIDE - A Configurable IDE menu bar.
 * 
 * @version 0.13
 */
public class AcideDataViewMenuBar extends JMenuBar {

	/**
	 * ACIDE - A Configurable IDE menu bar serial version UID.
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * ACIDE - A Configurable IDE menu bar file menu.
	 */
	private AcideDataViewFileMenu _fileMenu;
	/**
	 * ACIDE - A Configurable IDE menu bar edit menu.
	 */
	private AcideDataViewEditMenu _editMenu;
	/**
	 * ACIDE - A Configurable IDE menu bar project menu.
	 */
	private AcideDataViewRecordMenu _recordMenu;
	/**
	 * ACIDE - A Configurable IDE menu bar view menu.
	 */
	private AcideDataViewViewMenu _viewMenu;

	/**
	 * ACIDE - A Configurable IDE menu bar help menu.
	 */
	private AcideHelpMenu _helpMenu;
	
	/**
	 * ACIDE - DataView owner of the MenuBar
	 */
	private AcideDatabaseDataView _table;


	/**
	 * Creates a new ACIDE - A Configurable IDE menu bar.
	 */
	public AcideDataViewMenuBar(AcideDatabaseDataView table) {
		super();
		this._table=table;

		// Updates the log
		AcideLog.getLog().info(AcideLanguageManager.getInstance().getLabels().getString("s68"));

		// Build the menu components
		buildComponents();

		// Adds the components to the menu
		addComponents();
		
		setListeners();

		// Sets the text for the menu bar components
		setTextOfMenuComponents();

		// Builds the menu bar
		updateComponentsVisibility();

		// Updates the log
		AcideLog.getLog().info(AcideLanguageManager.getInstance().getLabels().getString("s69"));
	}

	/**
	 * Adds the components to the ACIDE - A Configurable IDE menu.
	 */
	private void addComponents() {

		// Adds the file menu to the menu
		add(_fileMenu);

		// Adds the edit menu to the menu
		add(_editMenu);

		// Adds the project menu to the menu
		add(_recordMenu);

		// Adds the view menu to the menu
		add(_viewMenu);

		// Adds the help menu to the menu
		add(_helpMenu);
	}

	/**
	 * Builds the ACIDE - A Configurable IDE menu components.
	 */
	private void buildComponents() {

		// Creates the file menu
		_fileMenu = new AcideDataViewFileMenu(_table);

		// Sets the file menu name
		_fileMenu.setName("fileMenu");

		// Creates the edit menu
		_editMenu = new AcideDataViewEditMenu(_table);

		// Sets the edit menu name
		_editMenu.setName("editMenu");

		// Creates the project menu
		_recordMenu = new AcideDataViewRecordMenu(_table);

		// Sets the project menu name
		_recordMenu.setName("projectMenu");

		// Creates the view menu
		_viewMenu = new AcideDataViewViewMenu(_table);

		// Sets the view menu name
		_viewMenu.setName("viewMenu");

		// Creates the help menu
		_helpMenu = new AcideHelpMenu();

		// Sets the help menu name
		_helpMenu.setName("helpMenu");
	}

	/**
	 * Sets the text of the ACIDE - A Configurable IDE menu bar components with
	 * the labels in the selected language to display.
	 */
	public void setTextOfMenuComponents() {

		// Sets the file menu text
		_fileMenu.setText(AcideLanguageManager.getInstance().getLabels()
				.getString("s1"));

		// Sets the file menu items text
		_fileMenu.setTextOfMenuComponents();

		// Sets the edit menu text
		_editMenu.setText(AcideLanguageManager.getInstance().getLabels()
				.getString("s2"));

		// Sets the edit menu items text
		_editMenu.setTextOfMenuComponents();

		// Sets the project menu text
		_recordMenu.setText(AcideLanguageManager.getInstance().getLabels()
				.getString("s2072"));

		// Sets the project menu items text
		_recordMenu.setTextOfMenuComponents();

		// Sets the view menu text
		_viewMenu.setText(AcideLanguageManager.getInstance().getLabels()
				.getString("s4"));

		// Sets the view menu items text
		_viewMenu.setTextOfMenuComponents();

		// Sets the help menu text
		_helpMenu.setText(AcideLanguageManager.getInstance().getLabels()
				.getString("s7"));

		// Sets the help menu items text
		_helpMenu.setTextOfMenuComponents();
	}

	/**
	 * <p>
	 * Configures the ACIDE - A Configurable IDE menu bar enabling or disabling
	 * its menu items.
	 * </p>
	 * <p>
	 * It is called by the {@link AcideFileEditorManagerChangeListener}.
	 * </p>
	 */
	public void configure() {

		// Configures the file menu
		AcideMainWindow.getInstance().getMenu().getFileMenu().configure();

		// Configures the edit menu
		AcideMainWindow.getInstance().getMenu().getEditMenu().configure();

		// Configures the project menu
		AcideMainWindow.getInstance().getMenu().getProjectMenu().configure();

		// Configures the file editor menu
		AcideMainWindow.getInstance().getMenu().getConfigurationMenu()
				.getFileEditorMenu().configure();

		// Configures the lexicon menu
		AcideMainWindow.getInstance().getMenu().getConfigurationMenu()
				.getLexiconMenu().configure();

		// Configures the grammar menu
		AcideMainWindow.getInstance().getMenu().getConfigurationMenu()
				.getGrammarMenu().configure();
	}

	/**
	 * Updates the ACIDE - A Configurable IDE menu bar components visibility
	 * with the menu configuration.
	 */
	public void updateComponentsVisibility() {

		// Loads the menu configuration
		loadMenuConfiguration();

		// Builds the file menu
		_fileMenu.updateComponentsVisibiliy();

		// Sets the file menu as visible or not visible
		_fileMenu.setVisible(AcideMenuConfiguration.getInstance()
				.getIsDisplayed(AcideFileMenu.NEW_FILE_NAME)
				|| AcideMenuConfiguration.getInstance().getIsDisplayed(
						AcideFileMenu.OPEN_FILE_NAME)
				|| AcideMenuConfiguration.getInstance().getIsDisplayed(
						AcideFileMenu.OPEN_ALL_FILES_NAME)
				|| AcideMenuConfiguration.getInstance().getIsDisplayed(
						AcideFileMenu.SAVE_FILE_AS_NAME)
				|| AcideMenuConfiguration.getInstance().getIsDisplayed(
						AcideFileMenu.SAVE_FILE_NAME)
				|| AcideMenuConfiguration.getInstance().getIsDisplayed(
						AcideFileMenu.SAVE_ALL_FILES_NAME)
				|| AcideMenuConfiguration.getInstance().getIsDisplayed(
						AcideFileMenu.PRINT_FILE_NAME)
				|| AcideMenuConfiguration.getInstance().getIsDisplayed(
						AcideFileMenu.EXIT_NAME));

		// Builds the edit menu
		_editMenu.updateComponentsVisibility();

		// Sets the edit menu as visible or not visible
		_editMenu.setVisible(AcideMenuConfiguration.getInstance()
				.getIsDisplayed(AcideEditMenu.UNDO_NAME)
				|| AcideMenuConfiguration.getInstance().getIsDisplayed(
						AcideEditMenu.REDO_NAME)
				|| AcideMenuConfiguration.getInstance().getIsDisplayed(
						AcideEditMenu.COPY_NAME)
				|| AcideMenuConfiguration.getInstance().getIsDisplayed(
						AcideEditMenu.PASTE_NAME)
				|| AcideMenuConfiguration.getInstance().getIsDisplayed(
						AcideEditMenu.CUT_NAME)
				|| AcideMenuConfiguration.getInstance().getIsDisplayed(
						AcideEditMenu.SELECT_ALL_NAME)
				|| AcideMenuConfiguration.getInstance().getIsDisplayed(
						AcideEditMenu.GO_TO_LINE_NAME)
				|| AcideMenuConfiguration.getInstance().getIsDisplayed(
						AcideEditMenu.SEARCH_NAME)
				|| AcideMenuConfiguration.getInstance().getIsDisplayed(
						AcideEditMenu.REPLACE_NAME));

		// Builds the project menu
		_recordMenu.updateComponentsVisibility();

		// Sets the project menu as visible or not visible
		_recordMenu.setVisible(AcideMenuConfiguration.getInstance()
				.getIsDisplayed(AcideProjectMenu.NEW_PROJECT_NAME)
				|| AcideMenuConfiguration.getInstance().getIsDisplayed(
						AcideProjectMenu.OPEN_PROJECT_NAME)
				|| AcideMenuConfiguration.getInstance().getIsDisplayed(
						AcideProjectMenu.SAVE_PROJECT_NAME)
				|| AcideMenuConfiguration.getInstance().getIsDisplayed(
						AcideProjectMenu.CLOSE_PROJECT_NAME)
				|| AcideMenuConfiguration.getInstance().getIsDisplayed(
						AcideProjectMenu.ADD_FILE_NAME)
				|| AcideMenuConfiguration.getInstance().getIsDisplayed(
						AcideProjectMenu.REMOVE_FILE_NAME)
				|| AcideMenuConfiguration.getInstance().getIsDisplayed(
						AcideProjectMenu.DELETE_FILE_NAME)
				|| AcideMenuConfiguration.getInstance().getIsDisplayed(
						AcideProjectMenu.NEW_PROJECT_FILE_NAME)
				|| AcideMenuConfiguration.getInstance().getIsDisplayed(
						AcideProjectMenu.ADD_FOLDER_NAME)
				|| AcideMenuConfiguration.getInstance().getIsDisplayed(
						AcideProjectMenu.REMOVE_FOLDER_NAME)
				|| AcideMenuConfiguration.getInstance().getIsDisplayed(
						AcideProjectMenu.COMPILE_NAME)
				|| AcideMenuConfiguration.getInstance().getIsDisplayed(
						AcideProjectMenu.EXECUTE_NAME));

		// Builds the view menu
		_viewMenu.updateComponentsVisibility();

		// Sets the view menu as visible or not visible
		_viewMenu.setVisible(AcideMenuConfiguration.getInstance()
				.getIsDisplayed(AcideViewMenu.SHOW_LOG_TAB_NAME)
				|| AcideMenuConfiguration.getInstance().getIsDisplayed(
						AcideViewMenu.SHOW_EXPLORER_PANEL_NAME)
				|| AcideMenuConfiguration.getInstance().getIsDisplayed(
						AcideViewMenu.SHOW_CONSOLE_PANEL_NAME)
				|| AcideMenuConfiguration.getInstance().getIsDisplayed(
								AcideViewMenu.SHOW_DATA_BASE_PANEL_NAME));


		// Builds the help menu
		_helpMenu.updateComponentsVisibility();

		// Sets the help menu as visible or not visible
		_helpMenu.setVisible(AcideMenuConfiguration.getInstance()
				.getIsDisplayed(AcideHelpMenu.SHOW_HELP_NAME)
				|| AcideMenuConfiguration.getInstance().getIsDisplayed(
						AcideHelpMenu.SHOW_ABOUT_US_NAME));

		// Shows the menu bar
		setVisible(true);
	}

	/**
	 * Loads the ACIDE - A Configurable IDE menu configuration.
	 */
	private void loadMenuConfiguration() {

		String currentMenuConfiguration = null;

		try {

			// Gets the ACIDE - A Configurable IDE current menu configuration
			currentMenuConfiguration = AcideResourceManager.getInstance()
					.getProperty("currentMenuConfiguration");

			// Sets the new menu item list
			AcideMenuConfiguration.getInstance()
					.setMenuElementList(
							AcideMenuConfiguration.getInstance()
									.loadMenuConfigurationFile(
											currentMenuConfiguration));

			// Updates the ACIDE - A Configurable IDE current menu configuration
			AcideResourceManager.getInstance().setProperty(
					"currentMenuConfiguration", currentMenuConfiguration);

			// Updates the log
			AcideLog.getLog().info(
					AcideLanguageManager.getInstance().getLabels()
							.getString("s70")
							+ " " + currentMenuConfiguration);
		} catch (Exception exception) {

			// Updates the log
			AcideLog.getLog().info(
					AcideLanguageManager.getInstance().getLabels()
							.getString("s71")
							+ exception.getMessage());

			// Gets the name
			String name;
			int index = currentMenuConfiguration.lastIndexOf("\\");
			if (index == -1)
				index = currentMenuConfiguration.lastIndexOf("/");
			name = ".\\configuration\\menu\\"
					+ currentMenuConfiguration.substring(index + 1,
							currentMenuConfiguration.length());

			try {

				// Sets the new menu item list
				AcideMenuConfiguration.getInstance().setMenuElementList(
						AcideMenuConfiguration.getInstance()
								.loadMenuConfigurationFile(name));

				// Updates the ACIDE - A Configurable IDE current menu
				// configuration
				AcideResourceManager.getInstance().setProperty(
						"currentMenuConfiguration", name);

				// Updates the ACIDE - A Configurable IDE menu configuration
				AcideProjectConfiguration.getInstance().setMenuConfiguration(name);
				
				// Updates the log
				AcideLog.getLog().info(
						AcideLanguageManager.getInstance().getLabels()
								.getString("s70")
								+ " " + name);

				// Displays an error message
				JOptionPane.showMessageDialog(null, AcideLanguageManager
						.getInstance().getLabels().getString("s956")
						+ currentMenuConfiguration
						+ AcideLanguageManager.getInstance().getLabels()
								.getString("s957") + name);
			} catch (Exception exception1) {
				try {

					// Updates the log
					AcideLog.getLog().error(exception1.getMessage());
					exception1.printStackTrace();

					// Loads the new menu item list
					AcideMenuConfiguration
							.getInstance()
							.setMenuElementList(
									AcideMenuConfiguration
											.getInstance()
											.loadMenuConfigurationFile(
													"./configuration/menu/defaultAllOn.menuConfig"));

					// Updates the ACIDE - A Configurable IDE current menu
					// configuration
					AcideResourceManager.getInstance().setProperty(
							"currentMenuConfiguration",
							"./configuration/menu/defaultAllOn.menuConfig");

					// Displays an error message
					JOptionPane.showMessageDialog(null, AcideLanguageManager
							.getInstance().getLabels().getString("s956")
							+ currentMenuConfiguration
							+ AcideLanguageManager.getInstance().getLabels()
									.getString("s959"));
				} catch (HeadlessException exception2) {

					// Updates the log
					AcideLog.getLog().error(exception2.getMessage());
					exception2.printStackTrace();
				} catch (Exception exception2) {

					// Updates the log
					AcideLog.getLog().error(exception2.getMessage());
					exception2.printStackTrace();
				}
			}
		}
	}

	/**
	 * Sets the ACIDE - A Configurable IDE menu bar listeners.
	 */
	public void setListeners() {

		// Sets the file menu mouse listener
		_fileMenu.addMouseListener(new AcideMenuBarMouseClickListener());

		// Sets the file menu items listeners
		_fileMenu.setListeners();

		// Sets the edit menu mouse listener
		_editMenu.addMouseListener(new AcideMenuBarMouseClickListener());

		// Sets the edit menu items listeners
		_editMenu.setListeners();

		// Sets the project menu mouse listener
		_recordMenu.addMouseListener(new AcideMenuBarMouseClickListener());

		// Sets the project menu items listeners
		_recordMenu.setListeners();

		// Sets the view menu mouse listener
		_viewMenu.addMouseListener(new AcideMenuBarMouseClickListener());

		// Sets the view menu items listeners
		_viewMenu.setListeners();		

		// Sets the help menu menu mouse listener
		_helpMenu.addMouseListener(new AcideMenuBarMouseClickListener());

		// Sets the help menu items listeners
		_helpMenu.setListeners();

		// Updates the log
		AcideLog.getLog()
				.info(AcideLanguageManager.getInstance().getLabels()
						.getString("s72"));
	}

	/**
	 * Returns the ACIDE - A Configurable IDE menu bar file menu.
	 * 
	 * @return the ACIDE - A Configurable IDE menu bar file menu.
	 */
	public AcideDataViewFileMenu getFileMenu() {
		return _fileMenu;
	}

	/**
	 * Returns the ACIDE - A Configurable IDE menu bar edit menu.
	 * 
	 * @return the ACIDE - A Configurable IDE menu bar edit menu.
	 */
	public AcideDataViewEditMenu getEditMenu() {
		return _editMenu;
	}

	/**
	 * Returns the ACIDE - A Configurable IDE menu project menu.
	 * 
	 * @return the ACIDE - A Configurable IDE menu project menu.
	 */
	public AcideDataViewRecordMenu getProjectMenu() {
		return _recordMenu;
	}

	

	/**
	 * Returns the ACIDE - A Configurable IDE menu view menu.
	 * 
	 * @return the ACIDE - A Configurable IDE menu view menu.
	 */
	public AcideDataViewViewMenu getViewMenu() {
		return _viewMenu;
	}

	/**
	 * Returns the ACIDE - A Configurable IDE menu bar help menu.
	 * 
	 * @return the ACIDE - A Configurable IDE menu bar help menu.
	 */
	public AcideHelpMenu getHelpMenu() {
		return _helpMenu;
	}

	@Override
	public void setEnabled(boolean enabled) {
		_editMenu.setEnabled(enabled);
		_fileMenu.setEnabled(enabled);
		_helpMenu.setEnabled(enabled);
		_recordMenu.setEnabled(enabled);
		_viewMenu.setEnabled(enabled);
		
	}
	
	/**
	 * Sets the ACIDE - A Configurable IDE menu bar on Read Only mode.
	 */
	public void isReadOnly(boolean b){
		_editMenu.isReadOnly(b);
		_recordMenu.isReadOnly(b);
	}
}
