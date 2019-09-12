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
package acide.gui.menuBar.configurationMenu.consoleMenu.gui;

import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.StringTokenizer;

import javax.swing.AbstractAction;
import javax.swing.AbstractButton;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.KeyStroke;

import acide.configuration.project.AcideProjectConfiguration;
import acide.files.AcideFileManager;
import acide.files.utils.AcideFileOperation;
import acide.files.utils.AcideFileTarget;
import acide.files.utils.AcideFileType;
import acide.gui.listeners.AcideWindowClosingListener;
import acide.gui.mainWindow.AcideMainWindow;
import acide.language.AcideLanguageManager;
import acide.log.AcideLog;
import acide.process.console.AcideDatabaseManager;
import acide.process.console.DesDatabaseManager;
import acide.process.console.ODBCDatabaseManager;
import acide.resources.AcideResourceManager;
import acide.resources.exception.MissedPropertyException;

/**
 * ACIDE - A Configurable IDE console configuration window.
 * 
 * @version 0.11
 * @see JFrame
 */
public class AcideConsoleConfigurationWindow extends JFrame {

	/**
	 * ACIDE - A Configurable IDE console configuration window class serial
	 * version UID.
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * ACIDE - A Configurable IDE console configuration window image icon.
	 */
	private static final ImageIcon ICON = new ImageIcon(
			"./resources/images/icon.png");
	/**
	 * ACIDE - A Configurable IDE console configuration window main panel of the
	 * window.
	 */
	private JPanel _mainPanel;
	/**
	 * ACIDE - A Configurable IDE console configuration window button panel.
	 */
	private JPanel _buttonPanel;
	/**
	 * ACIDE - A Configurable IDE console configuration window shell path label.
	 */
	private JLabel _shellPathLabel;
	/**
	 * ACIDE - A Configurable IDE console configuration window exit command text
	 * field.
	 */
	private JLabel _exitCommandLabel;
	/**
	 * ACIDE - A Configurable IDE console configuration window shell directory
	 * label.
	 */
	private JLabel _shellDirectoryLabel;
	/**
	 * ACIDE - A Configurable IDE console configuration window parameters label.
	 */
	private JLabel _parametersLabel;
	/**
	 * ACIDE - A Configurable IDE console configuration window shell path text
	 * field.
	 */
	private JTextField _shellPathTextField;
	/**
	 * ACIDE - A Configurable IDE console configuration window exit command text
	 * field.
	 */
	private JTextField _exitCommandTextField;
	/**
	 * ACIDE - A Configurable IDE console configuration window shell directory
	 * text field.
	 */
	private JTextField _shellDirectoryTextField;
	/**
	 * ACIDE - A Configurable IDE console configuration window parameters text
	 * field.
	 */
	private JTextField _parametersTextField;
	/**
	 * ACIDE - A Configurable IDE console configuration window manual path
	 * label.
	 */
	private JCheckBox _manualPathCheckBox;
	/**
	 * ACIDE - A Configurable IDE console configuration window echo command
	 * check box.
	 */
	private JCheckBox _echoCommandCheckBox;
	/**
	 * ACIDE - A Configurable IDE console configuration window apply button.
	 */
	private JButton _applyButton;
	/**
	 * ACIDE - A Configurable IDE console configuration window examine shell
	 * path button.
	 */
	private JButton _examineShellPathButton;
	/**
	 * ACIDE - A Configurable IDE console configuration window examine shell
	 * directory button.
	 */
	private JButton _examineShellDirectoryButton;
	/**
	 * ACIDE - A Configurable IDE console configuration window cancel button.
	 */
	private JButton _cancelButton;
	/**
	 * Parent window.
	 */
	private JFrame _parent;

	/**
	 * Creates a new ACIDE - A Configurable IDE console configuration window.
	 * 
	 * @param parent
	 *            parent window.
	 */
	public AcideConsoleConfigurationWindow(JFrame parent) {

		super();

		// Stores the parent
		_parent = parent;

		// Builds the window components
		buildComponents();

		// Sets the listeners of the window components
		setListeners();

		// Adds the components to the window
		addComponents();

		// Sets the window configuration
		setWindowConfiguration();

		// Updates the log
		AcideLog.getLog().info(
				AcideLanguageManager.getInstance().getLabels()
						.getString("s331"));
	}

	/**
	 * Sets the ACIDE - A Configurable IDE console window configuration.
	 */
	private void setWindowConfiguration() {

		// Sets the window title
		setTitle(AcideLanguageManager.getInstance().getLabels()
				.getString("s334"));

		// Sets the window icon image
		setIconImage(ICON.getImage());

		// The window is not resizable
		setResizable(false);

		// Packs the window components
		pack();

		// Centers the window
		setLocationRelativeTo(null);

		// Displays the window
		setVisible(true);

		// Disables the main window
		AcideMainWindow.getInstance().setEnabled(false);

		// Disables the parent window
		_parent.setEnabled(false);
	}

	/**
	 * Builds the ACIDE - A Configurable IDE console configuration window
	 * components.
	 */
	private void buildComponents() {

		// Creates the main panel
		_mainPanel = new JPanel(new GridBagLayout());

		// Creates the button panel
		_buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));

		// Creates the shell directory label
		_shellDirectoryLabel = new JLabel(AcideLanguageManager.getInstance()
				.getLabels().getString("s337"), JLabel.LEFT);

		// Disables the shell directory label
		_shellDirectoryLabel.setEnabled(false);

		// Creates the parameters label
		_parametersLabel = new JLabel(AcideLanguageManager.getInstance()
				.getLabels().getString("s2228"));

		// Creates the echo command check box
		try {

			// Creates the shell directory text field
			_shellDirectoryTextField = new JTextField(AcideResourceManager
					.getInstance().getProperty("consolePanel.shellDirectory"));

			// Sets the shell directory text field columns
			_shellDirectoryTextField.setColumns(35);

			// Disables the shell directory text field
			_shellDirectoryTextField.setEnabled(false);

			// Creates the manual path check box
			_manualPathCheckBox = new JCheckBox(AcideLanguageManager
					.getInstance().getLabels().getString("s350"));

			// Creates the shell path label
			_shellPathLabel = new JLabel(AcideLanguageManager.getInstance()
					.getLabels().getString("s338"), JLabel.LEFT);

			// Creates the shell path text field
			_shellPathTextField = new JTextField(AcideResourceManager
					.getInstance().getProperty("consolePanel.shellPath"));

			// Creates the parametes text field
			_parametersTextField = new JTextField(AcideResourceManager
					.getInstance().getProperty("consolePanel.parameters"));

			// Creates the exit command label
			_exitCommandLabel = new JLabel(AcideLanguageManager.getInstance()
					.getLabels().getString("s339"), JLabel.LEFT);

			// Creates the exit command text field
			_exitCommandTextField = new JTextField(AcideResourceManager
					.getInstance().getProperty("consolePanel.exitCommand"));

			// Sets the exit command text field columns
			_exitCommandTextField.setColumns(5);

			_echoCommandCheckBox = new JCheckBox(AcideLanguageManager
					.getInstance().getLabels().getString("s340"),
					Boolean.parseBoolean(AcideResourceManager.getInstance()
							.getProperty("consolePanel.isEchoCommand")));

			_echoCommandCheckBox.setSelected(true);

			// Creates the apply button
			_applyButton = new JButton(AcideLanguageManager.getInstance()
					.getLabels().getString("s335"));

			// Sets the apply button vertical text position as center
			_applyButton.setVerticalTextPosition(AbstractButton.CENTER);

			// Sets the apply button horizontal text position as leading
			_applyButton.setHorizontalTextPosition(AbstractButton.LEADING);

			// Sets the apply button mnemonic
			_applyButton.setMnemonic(KeyEvent.VK_A);

			// Sets the apply button tool tip text
			_applyButton.setToolTipText(AcideLanguageManager.getInstance()
					.getLabels().getString("s336"));

			// Creates the cancel button
			_cancelButton = new JButton(AcideLanguageManager.getInstance()
					.getLabels().getString("s178"));

			// Sets the cancel button vertical text position as center
			_cancelButton.setVerticalTextPosition(AbstractButton.CENTER);

			// Sets the cancel button horizontal text position as leading
			_cancelButton.setHorizontalTextPosition(AbstractButton.LEADING);

			// Creates the examine shell path button
			_examineShellPathButton = new JButton(AcideLanguageManager
					.getInstance().getLabels().getString("s142"));

			// Sets the examine shell path button tool tip text
			_examineShellPathButton.setToolTipText(AcideLanguageManager
					.getInstance().getLabels().getString("s301"));

			// Creates the examine shell directory button
			_examineShellDirectoryButton = new JButton(AcideLanguageManager
					.getInstance().getLabels().getString("s142"));

			// Sets the examine shell directory button tool tip text
			_examineShellDirectoryButton.setToolTipText(AcideLanguageManager
					.getInstance().getLabels().getString("s301"));

			// Disables the examine shell directory button
			_examineShellDirectoryButton.setEnabled(false);

		} catch (MissedPropertyException exception) {

			// Updates the log
			AcideLog.getLog().error(exception.getMessage());
			exception.printStackTrace();
		}
	}

	/**
	 * Adds the components to the ACIDE - A Configurable IDE console
	 * configuration window with the layout.
	 */
	private void addComponents() {

		// Sets the layout
		setLayout(new GridBagLayout());

		// Adds the components to the window with the layout
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.fill = GridBagConstraints.BOTH;
		constraints.insets = new Insets(5, 5, 5, 5);
		constraints.anchor = GridBagConstraints.EAST;
		constraints.gridx = 0;
		constraints.gridy = 0;

		// Adds the examine shell path label to the main panel
		_mainPanel.add(_shellPathLabel, constraints);

		constraints.anchor = GridBagConstraints.WEST;
		constraints.gridx = 1;
		constraints.gridy = 0;

		// Adds the examine shell path text field to the main panel
		_mainPanel.add(_shellPathTextField, constraints);

		constraints.anchor = GridBagConstraints.WEST;
		constraints.gridx = 2;
		constraints.gridy = 0;

		// Adds the examine shell path button to the main panel
		_mainPanel.add(_examineShellPathButton, constraints);

		constraints.anchor = GridBagConstraints.WEST;
		constraints.gridx = 0;
		constraints.gridy = 1;

		// Adds the examine shell path button to the main panel
		_mainPanel.add(_parametersLabel, constraints);

		constraints.anchor = GridBagConstraints.WEST;
		constraints.gridx = 1;
		constraints.gridy = 1;

		// Adds the examine shell path button to the main panel
		_mainPanel.add(_parametersTextField, constraints);

		constraints.anchor = GridBagConstraints.EAST;
		constraints.gridx = 0;
		constraints.gridy = 2;

		// Adds the manual path check box to the main panel
		_mainPanel.add(_manualPathCheckBox, constraints);

		constraints.anchor = GridBagConstraints.EAST;
		constraints.gridx = 0;
		constraints.gridy = 3;

		// Adds the examine shell directory label to the main panel
		_mainPanel.add(_shellDirectoryLabel, constraints);

		constraints.anchor = GridBagConstraints.WEST;
		constraints.gridx = 1;
		constraints.gridy = 3;

		// Adds the examine shell directory text field to the main panel
		_mainPanel.add(_shellDirectoryTextField, constraints);

		constraints.anchor = GridBagConstraints.WEST;
		constraints.gridx = 2;
		constraints.gridy = 3;

		// Adds the examine shell directory button to the main panel
		_mainPanel.add(_examineShellDirectoryButton, constraints);

		constraints.anchor = GridBagConstraints.EAST;
		constraints.gridx = 0;
		constraints.gridy = 4;

		// Adds the exit command label to the main panel
		_mainPanel.add(_exitCommandLabel, constraints);

		constraints.anchor = GridBagConstraints.WEST;
		constraints.gridx = 1;
		constraints.gridy = 4;

		// Adds the exit command text field to the main panel
		_mainPanel.add(_exitCommandTextField, constraints);

		constraints.anchor = GridBagConstraints.EAST;
		constraints.gridx = 0;
		constraints.gridy = 5;

		// Adds the echo command check box to the main panel
		_mainPanel.add(_echoCommandCheckBox, constraints);

		// Adds the main panel to the window
		add(_mainPanel, constraints);

		// Adds the apply button to the button panel
		_buttonPanel.add(_applyButton);

		// Adds the cancel button to the button panel
		_buttonPanel.add(_cancelButton);

		constraints.insets = new Insets(0, 0, 0, 0);
		constraints.gridwidth = 2;
		constraints.gridx = 0;
		constraints.gridy = 6;

		// Adds the button panel to the window
		add(_buttonPanel, constraints);
	}

	/**
	 * Sets the listeners of the ACIDE - A Configurable IDE console
	 * configuration window components.
	 */
	private void setListeners() {

		// Sets the apply button action listener
		_applyButton.addActionListener(new ApplyButtonAction());

		// Sets the cancel button action listener
		_cancelButton.addActionListener(new CancelButtonAction());

		// Sets the manual path check box item listener
		_manualPathCheckBox.addItemListener(new ManualPathCheckBoxAction());

		// Sets the examine shell directory button action listener
		_examineShellDirectoryButton
				.addActionListener(new ExamineShellDirectoryButtonAction());

		// Sets the examine shell path button action listener
		_examineShellPathButton
				.addActionListener(new ExamineShellPathButtonAction());

		// Sets the window closing listener
		addWindowListener(new AcideWindowClosingListener(_parent));

		// Puts the escape key in the input map of the window
		getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
				KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0, false),
				"EscapeKey");

		// Puts the escape key in the action map of the window
		getRootPane().getActionMap().put("EscapeKey", new EscapeKeyAction());
	}

	/**
	 * Closes the ACIDE - A Configurable IDE console configuration window.
	 */
	private void closeWindow() {

		// Set the parent window enabled again
		_parent.setEnabled(true);

		// Closes the window
		dispose();

		// Brings the parent window to the front
		_parent.setAlwaysOnTop(true);

		// But not permanently
		_parent.setAlwaysOnTop(false);
	}

	/**
	 * ACIDE - A Configurable IDE console configuration window apply button
	 * action listener.
	 * 
	 * @version 0.11
	 * @see ActionListener
	 */
	class ApplyButtonAction implements ActionListener {

		private boolean isExecutable(File shell) {
			boolean isExec = false;

			if (shell.exists() && !shell.isDirectory()) {

				try {
					Runtime.getRuntime().exec(shell.getAbsolutePath());
					isExec = true;
				} catch (Exception e) {
					isExec = false;
				}
			}

			return isExec;
		}

		private String searchShellInOSPath(String shellPath) throws Exception {

			String path = "";

			String operativeSystem = System.getProperty("os.name");
			if (operativeSystem.toUpperCase().contains("WIN")) {

				if (operativeSystem.toUpperCase().contains("XP")) {

					// if the shell name doesn't finish with .exe
					if (!shellPath.endsWith(".exe"))
						shellPath += ".exe";

					// Get the PATH of the OS
					String commandPath = "PATH";
					Process process = Runtime.getRuntime().exec(
							"cmd /c " + commandPath);
					ProcessResultReader stdout = new ProcessResultReader(
							process.getInputStream(), "STDOUT");
					stdout.start();
					process.waitFor();
					path = stdout.toString();
					// Erase the "PATH=" in the beginning of the
					// PATH command result
					path = path.substring(path.indexOf("=") + 1);
					process.destroy();
					String[] paths = path.split(";");
					boolean found = false;
					int i = 0;
					while (!found && i < paths.length) {
						// Comprobar que el fichero existe en cada una de las
						// rutas del path
						String auxPath = paths[i];
						if (!auxPath.endsWith("\\"))
							auxPath += "\\";
						auxPath += shellPath;
						File auxFile = new File(auxPath);
						if (auxFile.exists()) {
							found = true;
							path = auxPath;
						}
						i++;
					}

				} else {
					// if the shell name doesn't finish with .exe
					if (!shellPath.endsWith(".exe"))
						shellPath += ".exe";

					String commandWhere = "where " + shellPath;
					Process processWhere = Runtime.getRuntime().exec(
							"cmd /c " + commandWhere);
					ProcessResultReader stdoutWhere = new ProcessResultReader(
							processWhere.getInputStream(), "STDOUT");
					stdoutWhere.start();
					processWhere.waitFor();
					path = stdoutWhere.toString();
					processWhere.destroy();
				}

			} else {

				String commandWhich = "which " + shellPath;
				Process process = Runtime.getRuntime().exec(commandWhich);
				ProcessResultReader stdout = new ProcessResultReader(
						process.getInputStream(), "STDOUT");
				stdout.start();
				process.waitFor();
				path = stdout.toString();
				process.destroy();
			}

			return path;
		}

		public void newShell(String shellDirectory, String shellPath,
				String shellParameters, String exitCommand,
				boolean changeDirectory, boolean isEchoCommand) {

			// Exits the shell
			AcideMainWindow.getInstance().getConsolePanel()
					.executeExitCommand();

			// If the shell directory is enabled
			if (changeDirectory) {

				// Sets the shell directory in the resource manager
				AcideResourceManager.getInstance().setProperty(
						"consolePanel.shellDirectory", shellDirectory);

				// Sets the shell directory in the project configuration
				AcideProjectConfiguration.getInstance().setShellDirectory(
						shellDirectory);

			} else {

				// Gets the shell directory
				String calculatedPath = "";
				// String execTextField = exec;
				String separator = File.separator;

				StringTokenizer stringTokenizer = new StringTokenizer(
						shellPath, separator);

				int limit = stringTokenizer.countTokens();
				for (int index = 0; index < limit - 1; index++)
					calculatedPath = calculatedPath
							+ stringTokenizer.nextToken() + separator;

				String os = System.getProperty("os.name");
				if ((os.indexOf("nix") >= 0 || os.indexOf("nux") >= 0)
						&& !calculatedPath.startsWith(".")) {
					calculatedPath = File.separator + calculatedPath;
				}

				// Sets the shell directory in the resource manager
				AcideResourceManager.getInstance().setProperty(
						"consolePanel.shellDirectory", calculatedPath);

				// Sets the shell directory in the project configuration
				AcideProjectConfiguration.getInstance().setShellDirectory(
						calculatedPath);
			}

			// Sets the shell path in the resource manager
			AcideResourceManager.getInstance().setProperty(
					"consolePanel.shellPath", shellPath);

			AcideResourceManager.getInstance().setProperty(
					"consolePanel.parameters", shellParameters);

			// Sets the echo command in the resource manager
			AcideResourceManager.getInstance()
					.setProperty("consolePanel.isEchoCommand",
							String.valueOf(isEchoCommand));

			// Sets the exit command in the resource manager
			AcideResourceManager.getInstance().setProperty(
					"consolePanel.exitCommand", exitCommand);

			// Resets the console
			AcideMainWindow.getInstance().getConsolePanel().resetConsole();

			// Sets the shell path in the project configuration
			AcideProjectConfiguration.getInstance().setShellPath(shellPath);

			// Sets the shell path in the project configuration
			AcideProjectConfiguration.getInstance().setShellParameters(
					shellParameters);

			// Sets the echo command in the project configuration
			AcideProjectConfiguration.getInstance().setIsEchoCommand(
					isEchoCommand);

			// Sets the exit command in the project configuration
			AcideProjectConfiguration.getInstance().setExitCommand(exitCommand);
		}

		/**
		 * Unrolls the environment variables in the shell path if there are any
		 * 
		 * @param shellPath
		 *            path provided by the user
		 * @return new path without environment variables
		 */
		private String getEnvironmentVariable(String shellPath) {

			String finalPath = shellPath;

			String os = System.getProperty("os.name");
			if (os.contains("win") || os.contains("WIN") || os.contains("Win")) {
				if (finalPath.contains("%")) {

					int start = finalPath.indexOf("%");
					String envVar = finalPath.substring(start + 1);
					int end = envVar.indexOf("%");

					if (end >= 0) {

						envVar = envVar.substring(start, end);

						// Gets environment variable
						String variable = System.getenv(envVar);

						if (variable != null) {
							String beg = finalPath.substring(0, start);
							String fin = finalPath.substring(end + 2);
							finalPath = beg + variable + fin;
						}
					}
				}
			} else {
				if (finalPath.contains("$")) {

					int start = finalPath.indexOf("$");
					String envVar = finalPath.substring(start + 1);
					int end = envVar.indexOf("/");

					if (end >= 0)
						envVar = envVar.substring(start, end);

					String variable = System.getenv(envVar);

					if (variable != null) {
						String beg = finalPath.substring(0, start);
						String fin = "";
						if (end >= 0)
							fin = finalPath.substring(end + 1);
						finalPath = beg + variable + fin;
					}

				}
			}

			return finalPath;

		}

		/**
		 * Checks if the shellPath is a DES instance, if that's not the case it
		 * disable the DES database in case it was enabled.
		 * 
		 * @param shellPath
		 */
		void validDesDatabase(String shellPath) {
		/*	LinkedList<String> result = AcideDatabaseManager.getInstance()
					.executeCommand("/tapi /test_tapi");*/
			LinkedList<String> result = DesDatabaseManager.getInstance()
			.executeCommand("/tapi /test_tapi");
			//for (String s : result) {
				//if ((!s.equals("$succes"))
				 if (!shellPath.endsWith("\\des.exe")
				&& !shellPath.endsWith("\\x.exe")
				&& !shellPath.endsWith("\\hrsql.exe")
				&& !shellPath.endsWith("/des")
				&& AcideMainWindow.getInstance().getMenu()
					.getConfigurationMenu().getDatabasePanelMenu()
					.getDesPanelMenuItem().isSelected()) {

					AcideMainWindow.getInstance().getMenu()
							.getConfigurationMenu().getDatabasePanelMenu()
							.getDesPanelMenuItem().setSelected(false);

					AcideMainWindow.getInstance().getMenu()
							.getConfigurationMenu().getDatabasePanelMenu()
							.getOdbcPanelMenuItem().setSelected(true);

					AcideDatabaseManager.setInstance(new ODBCDatabaseManager());

					AcideMainWindow.getInstance().getDataBasePanel()
							.updateDataBaseTreeOnChange();

				//}
			}
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent
		 * )
		 */
		@Override
		public void actionPerformed(ActionEvent actionEvent) {

			try {

				// Get the shell components
				// String fullPath = _shellPathTextField.getText();
				String shellPath = _shellPathTextField.getText();
				String shellDirectory = _shellDirectoryTextField.getText();
				String shellParameters = _parametersTextField.getText();
				String exitCommand = _exitCommandTextField.getText();
				boolean changeDirectory = _manualPathCheckBox.isSelected();
				boolean isEchoCommand = _echoCommandCheckBox.isSelected();
				boolean invalidShell = false;
				boolean invalidDirectory = false;

				File directory = new File(shellDirectory);

				// Check if there was data in the directory path text field
				if (!changeDirectory
						|| (shellDirectory != null
								&& !shellDirectory.matches("")
								&& directory.exists() && directory
									.isDirectory())) {

					// Check if there was data in the shell path text field
					if (shellPath != null && !shellPath.matches("")) {
						// Unrolls environment variables if there are any
						shellPath = getEnvironmentVariable(shellPath);
						// Check if the shell path exists
						File shell = new File(shellPath);
						if (shell.exists()) {
							// Check if the file is executable
							if (isExecutable(shell)) {
								// The file is a valid shell
								newShell(shellDirectory, shellPath,
										shellParameters, exitCommand,
										changeDirectory, isEchoCommand);
							} else {
								// The file is not a valid shell
								invalidShell = true;
							}

						} else {
							// Search the shell in the OS Path
							String shellOSPath = searchShellInOSPath(shellPath);
							if (shellOSPath != null && !shellOSPath.matches("")) {
								// Valid shellOSPath
								newShell(shellDirectory, shellOSPath,
										shellParameters, exitCommand,
										changeDirectory, isEchoCommand);
							} else {
								// Invalid shellOSPath
								invalidShell = true;
							}
						}

					} else {
						// ShellPath error (no shellPath!)
						invalidShell = true;
					}
				} else {
					// Directory error (no directory!)
					invalidDirectory = true;
				}

				if (invalidDirectory) {

					// Displays an error message
					JOptionPane.showMessageDialog(null, AcideLanguageManager
							.getInstance().getLabels().getString("s2290"),
							"Error", JOptionPane.ERROR_MESSAGE);

				} else if (invalidShell) {

					// Displays an error message
					JOptionPane.showMessageDialog(null, AcideLanguageManager
							.getInstance().getLabels().getString("s993"),
							"Error", JOptionPane.ERROR_MESSAGE);

				} else {

					validDesDatabase(shellPath);

					// If it is not the default project
					if (!AcideProjectConfiguration.getInstance()
							.isDefaultProject())

						// The project has been modified
						AcideProjectConfiguration.getInstance().setIsModified(
								true);

					// Closes the window
					closeWindow();

				}
			} catch (Exception exception) {
				// Updates the log
				AcideLog.getLog().error(exception.getMessage());
				exception.printStackTrace();
			}
		}

		private class ProcessResultReader extends Thread {
			final InputStream is;
			@SuppressWarnings("unused")
			final String type;
			final StringBuilder sb;

			ProcessResultReader(InputStream is, String type) {
				this.is = is;
				this.type = type;
				this.sb = new StringBuilder();
			}

			public void run() {
				try {
					final InputStreamReader isr = new InputStreamReader(is);
					final BufferedReader br = new BufferedReader(isr);
					String line = null;
					while ((line = br.readLine()) != null) {
						this.sb.append(line);
					}
				} catch (final IOException ioe) {
					System.err.println(ioe.getMessage());
					// throw new RuntimeException(ioe);
				}
			}

			@Override
			public String toString() {
				return this.sb.toString();
			}
		}
	}

	/**
	 * ACIDE - A Configurable IDE console configuration window cancel button
	 * action listener.
	 * 
	 * @version 0.11
	 * @see ActionListener
	 */
	class CancelButtonAction implements ActionListener {

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
			closeWindow();
		}
	}

	/**
	 * ACIDE - A Configurable IDE console configuration window examine shell
	 * path button action listener.
	 * 
	 * @version 0.11
	 * @see ActionListener
	 */
	class ExamineShellPathButtonAction implements ActionListener {

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent
		 * )
		 */
		@Override
		public void actionPerformed(ActionEvent actionEvent) {

			// Asks for the file path to the user
			String absolutePath = AcideFileManager.getInstance().askForFile(
					AcideFileOperation.OPEN, AcideFileTarget.FILES,
					AcideFileType.FILE, "", null);

			if (absolutePath != null) {

				// Updates the shell path text field with the absolute
				// path
				_shellPathTextField.setText(absolutePath);
			}
		}
	}

	/**
	 * ACIDE - A Configurable IDE console configuration window examine shell
	 * directory button action listener.
	 * 
	 * @version 0.11
	 * @see ActionListener
	 */
	class ExamineShellDirectoryButtonAction implements ActionListener {

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent
		 * )
		 */
		@Override
		public void actionPerformed(ActionEvent actionEvent) {

			// Asks for the file path to the user
			String absolutePath = AcideFileManager.getInstance().askForFile(
					AcideFileOperation.OPEN, AcideFileTarget.FILES,
					AcideFileType.DIRECTORY, "", null);

			if (absolutePath != null) {

				// Updates the shell directory text field with it
				_shellDirectoryTextField.setText(absolutePath);
			}
		}
	}

	/**
	 * ACIDE - A Configurable IDE console configuration window manual path check
	 * box item listener.
	 * 
	 * @version 0.11
	 * @see ItemListener
	 */
	class ManualPathCheckBoxAction implements ItemListener {

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * java.awt.event.ItemListener#itemStateChanged(java.awt.event.ItemEvent
		 * )
		 */
		@Override
		public void itemStateChanged(ItemEvent itemEvent) {

			if (itemEvent.getStateChange() == ItemEvent.SELECTED) {

				// Enables the shell directory label
				_shellDirectoryLabel.setEnabled(true);

				// Enables the shell directory text field
				_shellDirectoryTextField.setEnabled(true);

				// Enables the shell directory button
				_examineShellDirectoryButton.setEnabled(true);
			} else {

				// Disables the shell directory label
				_shellDirectoryLabel.setEnabled(false);

				// Disables the shell directory text field
				_shellDirectoryTextField.setEnabled(false);

				// Disables the shell directory button
				_examineShellDirectoryButton.setEnabled(false);
			}
		}
	}

	/**
	 * ACIDE - A Configurable IDE console configuration window escape key
	 * action.
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
			closeWindow();
		}
	}
}
