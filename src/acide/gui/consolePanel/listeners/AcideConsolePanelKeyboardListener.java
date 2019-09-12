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
package acide.gui.consolePanel.listeners;

import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.io.Writer;

import javax.swing.JOptionPane;
import javax.swing.JViewport;
import javax.swing.text.BadLocationException;
import javax.swing.text.JTextComponent;

import acide.configuration.project.AcideProjectConfiguration;
import acide.gui.fileEditor.fileEditorPanel.AcideFileEditorPanel;
import acide.gui.mainWindow.AcideMainWindow;
import acide.gui.mainWindow.utils.AcideLastElementOnFocus;
import acide.gui.menuBar.configurationMenu.consoleMenu.gui.AcideConsolePanelSearchWindow;
import acide.language.AcideLanguageManager;
import acide.log.AcideLog;

/**
 * ACIDE - A Configurable IDE console panel keyboard listener.
 * 
 * @version 0.11
 * @see KeyListener
 */
public class AcideConsolePanelKeyboardListener implements KeyListener {

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.awt.event.KeyListener#keyPressed(java.awt.event.KeyEvent)
	 */
	@Override
	public void keyPressed(KeyEvent keyEvent) {
		dispatchEvent(keyEvent);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.awt.event.KeyListener#keyReleased(java.awt.event.KeyEvent)
	 */
	@Override
	public void keyReleased(KeyEvent keyEvent) {
		// dispatchEvent(keyEvent);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.awt.event.KeyListener#keyTyped(java.awt.event.KeyEvent)
	 */
	@Override
	public void keyTyped(KeyEvent keyEvent) {
		// dispatchEvent(keyEvent);
	}

	/**
	 * Dispatches the key event.
	 * 
	 * @param keyEvent
	 *            key event.
	 */
	private void dispatchEvent(KeyEvent keyEvent) {

		if (!AcideMainWindow.getInstance().getConsolePanel().getTextPane()
				.getText().matches("")) {

			if (AcideMainWindow.getInstance().getLastElementOnFocus() == AcideLastElementOnFocus.CONSOLE_PANEL) {

				// Gets the selected text
				String selectedText =  AcideMainWindow.getInstance()
						.getConsolePanel().getTextPane().getSelectedText();
				
				if (!keyEvent.isControlDown() && !keyEvent.isShiftDown()
						&& !keyEvent.isAltDown()) {
					
					if ((AcideMainWindow.getInstance().getConsolePanel()
							.getTextPane().getCaretPosition() < AcideMainWindow
							.getInstance().getConsolePanel()
							.getPromptCaretPosition())
							&& selectedText == null) {

						// Puts the caret automatically at the end of the text
						// of the console
						AcideMainWindow
								.getInstance()
								.getConsolePanel()
								.getTextPane()
								.setCaretPosition(
										AcideMainWindow.getInstance()
												.getConsolePanel()
												.getTextPane().getText()
												.length());

						// Consumes the key
						keyEvent.consume();
					} else if (selectedText != null && 
							AcideMainWindow.getInstance().getConsolePanel()
							.getTextPane().getSelectionStart() < AcideMainWindow
							.getInstance().getConsolePanel()
							.getPromptCaretPosition()) {
						
						// Puts the caret automatically at the end of the text
						// of the console
						AcideMainWindow
								.getInstance()
								.getConsolePanel()
								.getTextPane()
								.setCaretPosition(
										AcideMainWindow.getInstance()
												.getConsolePanel()
												.getTextPane().getText()
												.length());

						// Consumes the key
						keyEvent.consume();
					}
				}

				// Decides when the console panel is editable and when it is not
				boolean isNotEditable = AcideMainWindow.getInstance()
						.getConsolePanel().getTextPane().getSelectedText() != null
						&& AcideMainWindow.getInstance().getConsolePanel()
								.getTextPane().getSelectionStart() < AcideMainWindow
								.getInstance().getConsolePanel()
								.getPromptCaretPosition();


				//Gets the prompt position
				int prompt = AcideMainWindow.getInstance().getConsolePanel()
						.getPromptCaretPosition();
				
				//Gets the length of the document
				int lenght = AcideMainWindow.getInstance().getConsolePanel()
						.getTextPane().getText().length();
				
				String command;
				if (prompt == lenght) {
					command = "";
				} else {
					// Gets the command
					command = (String) AcideMainWindow
							.getInstance()
							.getConsolePanel()
							.getTextPane()
							.getText()
							.subSequence(
									AcideMainWindow.getInstance().getConsolePanel()
											.getPromptCaretPosition(),
									AcideMainWindow.getInstance().getConsolePanel()
											.getTextPane().getText().length());
				}
				

				switch (keyEvent.getKeyCode()) {

				case KeyEvent.VK_BACK_SPACE:

					if (!isNotEditable) {

						// It does not allows to go far beyond the prompt mark
						if (AcideMainWindow.getInstance().getConsolePanel()
								.getTextPane().getCaretPosition() == AcideMainWindow
								.getInstance().getConsolePanel()
								.getPromptCaretPosition()) {

							// If there is no selected text
							if (AcideMainWindow.getInstance().getConsolePanel()
									.getTextPane().getSelectedText() == null)
								// Consumes the key
								keyEvent.consume();
						}
					} else
						// Consumes the key
						keyEvent.consume();

					break;
				case KeyEvent.VK_LEFT:

					// It does not allows to go far beyond the prompt mark
					if (!keyEvent.isShiftDown()) {

						if (AcideMainWindow.getInstance().getConsolePanel()
								.getTextPane().getCaretPosition() == AcideMainWindow
								.getInstance().getConsolePanel()
								.getPromptCaretPosition())
							// Consumes the key
							keyEvent.consume();
					}

					break;

				case KeyEvent.VK_RIGHT:

					// It does not allows to go far beyond the prompt mark
					if (!keyEvent.isShiftDown()) {

						if (isNotEditable)
							// Consumes the key
							keyEvent.consume();
					}

					break;

				case KeyEvent.VK_ENTER:

					if (!command.matches(""))
						AcideMainWindow.getInstance().getConsolePanel()
								.addText(" ");
					if (!isNotEditable) {

						if (!command.matches("")
								&& AcideMainWindow.getInstance()
										.getConsolePanel().getProcessThread()
										.getOutputGobbler().get_sendToConsole())
							AcideMainWindow.getInstance().getConsolePanel()
									.getConsoleCommandsConfiguration().save();

						// Sends the parsed command to the console
						AcideMainWindow
								.getInstance()
								.getConsolePanel()
								.sendCommandToConsole(
										parseAcideVariables(command), "");
						if (!command.matches("")) {
							AcideMainWindow
									.getInstance()
									.getConsolePanel()
									.getConsoleCommandsConfiguration()
									.getConsoleCommandsManager()
									.insertCommand(parseAcideVariables(command));

						}

					} else

						// Consumes the key
						keyEvent.consume();

					break;

				case KeyEvent.VK_UP:

					// CTRL + UP -> Scroll up
					if (keyEvent.isControlDown()) {

						// Performs the scroll up
						scrollUp();
					} else {
						// Consumes the key
						keyEvent.consume();

						// Performs the up key action
						upKeyAction(command);
					}
					break;

				case KeyEvent.VK_DOWN:

					// CTRL + DOWN -> Scroll down
					if (keyEvent.isControlDown()) {

						// Performs the scroll down
						scrollDown();
					} else {
						// Consumes the key
						keyEvent.consume();

						// Performs the down key action
						downKeyAction(command);
					}
					break;

				case KeyEvent.VK_ESCAPE:

					// Performs the escape key action
					escapeKeyAction(command);

					break;

				case KeyEvent.VK_HOME:

					// Consumes the key
					keyEvent.consume();

					// Performs the home key action
					homeKeyAction();

					break;

				case KeyEvent.VK_END:

					// Consumes the key
					keyEvent.consume();

					// Performs the end key action
					endKeyAction(command);

					break;

				case KeyEvent.VK_C:

					// CTRL + C --> COPY or Shell iterruption
					if (keyEvent.isControlDown())
						
						if (selectedText != null)
							//Some text has been selected -> Copy
							AcideMainWindow.getInstance().getConsolePanel()
									.getTextPane().copy();
						else {
							//No text selected -> Interruption
							AcideMainWindow.getInstance().getConsolePanel()
									.interruptShellProcess();
						}
											
				break;
				case KeyEvent.VK_H:

					// CTRL + H --> sigint
					if (keyEvent.isControlDown()) {
						
						int n = JOptionPane.showConfirmDialog(null,
								AcideLanguageManager.getInstance().getLabels()
										.getString("s2039"),
								AcideLanguageManager.getInstance().getLabels()
										.getString("s2040"),
								JOptionPane.YES_NO_OPTION);
						if (n == 0)
							AcideMainWindow.getInstance().getConsolePanel()
									.resetConsole();
					}

					break;

				case KeyEvent.VK_V:

					if (keyEvent.isControlDown()) {
						vKeyAction();
					}
					keyEvent.consume();
					break;
				
				case KeyEvent.VK_F3:

					// If there is selected text
					if (selectedText != null) {
						// Updates the search text field
						AcideConsolePanelSearchWindow.getInstance()
								.getSearchTextField().setText(selectedText);

						// BOTH DIRECTIONS search
						AcideConsolePanelSearchWindow.getInstance()
								.get_bothDirectionsRadioButton()
								.setSelected(true);

						// BACKWARD DIRECTION search
						if (keyEvent.isShiftDown()) {
							AcideConsolePanelSearchWindow.getInstance()
									.get_bothDirectionsBackwardsRadioButton()
									.setSelected(true);
						}

						AcideConsolePanelSearchWindow.getInstance()
								.getSearchButton().doClick();
						return;
					} else {
						AcideConsolePanelSearchWindow.getInstance()
								.getSearchButton().doClick();
						return;
					}

				default:

					if (isNotEditable)
						// Consumes the key

						keyEvent.consume();

				}
			}
		}
	}

	/**
	 * Parses the ACIDE - A Configurable IDE variables to real paths in order to
	 * send them properly to the ACIDE - A Configurable IDE console panel for
	 * its execution.
	 * 
	 * @param rawCommand
	 *            raw command to execute from ACIDE - A Configurable IDE console
	 *            panel.
	 * 
	 * @return the parsed string that contains to command to execute in the
	 *         ACIDE - A Configurable IDE console panel.
	 */
	private String parseAcideVariables(String buttonAction) {

		// Gets the command to execute
		String command = buttonAction;

		// If there are opened file editors
		if (AcideMainWindow.getInstance().getFileEditorManager()
				.getNumberOfFileEditorPanels() > 0) {

			// Replaces the active file variable for its real value
			command = command.replace("$activeFile$", AcideMainWindow
					.getInstance().getFileEditorManager()
					.getSelectedFileEditorPanel().getAbsolutePath());

			// Replaces the active file path variable for its real value
			command = command.replace("$activeFilePath$", AcideMainWindow
					.getInstance().getFileEditorManager()
					.getSelectedFileEditorPanel().getFilePath());

			// Replaces the active files extension for its real value
			command = command.replace("$activeFileExt$", AcideMainWindow
					.getInstance().getFileEditorManager()
					.getSelectedFileEditorPanel().getFileExtension());

			// Replaces the active files name for its real value
			command = command
					.replace("$activeFileName$", AcideMainWindow.getInstance()
							.getFileEditorManager()
							.getSelectedFileEditorPanel()
							.getFileNameWithoutExtension());
		}

		// If it is the default project
		if (AcideProjectConfiguration.getInstance().isDefaultProject()) {

			// Gets the main file editor panel
			AcideFileEditorPanel mainFileEditorPanel = AcideMainWindow
					.getInstance().getFileEditorManager()
					.getMainFileEditorPanel();

			// If exists
			if (mainFileEditorPanel != null) {

				// Replaces the $mainFile$ variable for its real value
				command = command.replace("$mainFile$",
						mainFileEditorPanel.getAbsolutePath());

				// Replaces the $mainFilePath$ variable for its real value
				command = command.replace("$mainFilePath$",
						mainFileEditorPanel.getFilePath());

				// Replaces the $mainFileExt$ variable for its real value
				command = command.replace("$mainFileExt$",
						mainFileEditorPanel.getFileExtension());

				// Replaces the $mainFileName$ variable for its real value
				command = command.replace("$mainFileName$",
						mainFileEditorPanel.getFileNameWithoutExtension());
			}
		} else {

			// Not default project

			// Searches for the MAIN file into the ACIDE - A Configurable IDE
			// project configuration
			int mainFileEditorPanelIndex = -1;
			for (int index = 0; index < AcideProjectConfiguration.getInstance()
					.getNumberOfFilesFromList(); index++) {
				if (AcideProjectConfiguration.getInstance().getFileAt(index)
						.isMainFile())
					mainFileEditorPanelIndex = index;
			}

			// If exists
			if (mainFileEditorPanelIndex != -1) {

				// Replaces the $mainFile$ variable for its real value
				command = command.replace(
						"$mainFile$",
						AcideProjectConfiguration.getInstance()
								.getFileAt(mainFileEditorPanelIndex)
								.getAbsolutePath());

				// Replaces the $mainFilePath$ variable for its real value
				command = command.replace(
						"$mainFilePath$",
						AcideProjectConfiguration.getInstance()
								.getFileAt(mainFileEditorPanelIndex)
								.getRelativePath());

				// Replaces the $mainFileExt$ variable for its real value
				command = command.replace(
						"$mainFileExt$",
						AcideProjectConfiguration.getInstance()
								.getFileAt(mainFileEditorPanelIndex)
								.getFileExtension());

				// Replaces the $mainFileName$ variable for its real value
				command = command.replace(
						"$mainFileName$",
						AcideProjectConfiguration.getInstance()
								.getFileAt(mainFileEditorPanelIndex)
								.getFileName());
			}
		}

		return command;
	}

	/**
	 * Performs the ACIDE - A Configurable IDE console panel scroll pane scroll
	 * down action.
	 */
	private void scrollDown() {

		// Gets the active text edition area
		JTextComponent textPane = AcideMainWindow.getInstance()
				.getConsolePanel().getTextPane();

		// Gets the view port from the text pane parent
		JViewport viewport = (JViewport) textPane.getParent();

		// Gets the view rectangle from it
		Rectangle rectangle = viewport.getViewRect();

		// Gets the location
		Point point = rectangle.getLocation();

		// Calculates the increment for the line by line scrolling
		int increment = textPane.getFontMetrics(textPane.getFont()).getHeight() / 2;

		/*
		 * NOTE: The code above is for setting the scrolling by window int
		 * increment = textPane.getScrollableBlockIncrement(rectangle,
		 * SwingConstants.VERTICAL, 1);
		 */

		// Calculates the maximum value for the y coordinate
		int maxY = viewport.getView().getHeight() - rectangle.height;

		// Calculates the new position
		point.y = (point.y + increment <= maxY) ? point.y + increment : maxY;

		// Updates the location
		viewport.setViewPosition(point);
	}

	/**
	 * Performs the ACIDE - A Configurable IDE console panel scroll pane scroll
	 * up action.
	 */
	private void scrollUp() {

		// Gets the active text edition area
		JTextComponent textPane = AcideMainWindow.getInstance()
				.getConsolePanel().getTextPane();

		// Gets the view port from the text pane parent
		JViewport viewport = (JViewport) textPane.getParent();

		// Gets the view rectangle from it
		Rectangle rectangle = viewport.getViewRect();

		// Gets the location
		Point point = rectangle.getLocation();

		// Calculates the increment for the line by line scrolling
		int increment = textPane.getFontMetrics(textPane.getFont()).getHeight() / 2;

		/*
		 * NOTE: The code above is for setting the scrolling by window int
		 * increment = textPane.getScrollableBlockIncrement(rectangle,
		 * SwingConstants.VERTICAL, -1);
		 */

		// Calculates the new position
		point.y = (point.y - increment >= 0) ? point.y - increment : 0;

		// Updates the location
		viewport.setViewPosition(point);
	}

	/**
	 * Performs the end key action.
	 * 
	 * @param command
	 *            command to execute.
	 */
	private void endKeyAction(String command) {

		if (AcideMainWindow.getInstance().getConsolePanel().getProcessThread()
				.getWriter() != null) {

			// Sets the caret at the end of the command
			AcideMainWindow
					.getInstance()
					.getConsolePanel()
					.getTextPane()
					.setCaretPosition(
							AcideMainWindow.getInstance().getConsolePanel()
									.getPromptCaretPosition()
									+ command.length());
		}
	}

	/**
	 * Performs the home key action.
	 * 
	 * @param command
	 *            command to execute.
	 */
	private void homeKeyAction() {

		if (AcideMainWindow.getInstance().getConsolePanel().getProcessThread()
				.getWriter() != null) {

			// Sets the caret after the prompt
			AcideMainWindow
					.getInstance()
					.getConsolePanel()
					.getTextPane()
					.setCaretPosition(
							AcideMainWindow.getInstance().getConsolePanel()
									.getPromptCaretPosition());
		}
	}

	/**
	 * Performs the escape key action.
	 * 
	 * @param command
	 *            command to execute.
	 */
	private void escapeKeyAction(String command) {

		if (AcideMainWindow.getInstance().getConsolePanel().getProcessThread()
				.getWriter() != null) {

			try {

				// Removes the text selection
				AcideMainWindow
				.getInstance()
				.getConsolePanel()
				.getTextPane().getDocument()
				.remove(AcideMainWindow.getInstance().getConsolePanel()
						.getPromptCaretPosition(), command.length());

			} catch (BadLocationException exception) {

				// Updates the log
				AcideLog.getLog().error(exception.getMessage());
				exception.printStackTrace();
			}
		}
	}

	/**
	 * Performs the down key action, updating the command record.
	 * 
	 * @param command
	 *            command to execute.
	 */
	private void downKeyAction(String command) {

		if (AcideMainWindow.getInstance().getConsolePanel().getProcessThread()
				.getWriter() != null) {

			// If there are commands in the command record
			if (AcideMainWindow.getInstance().getConsolePanel()
					.getCommandRecord().getCurrentIndex() > -1) {

				// If it is the last command
				if (AcideMainWindow.getInstance().getConsolePanel()
						.getCommandRecord().getCurrentIndex() >= AcideMainWindow
						.getInstance().getConsolePanel().getCommandRecord()
						.getMaximumIndex() - 1)

					// Sets the first one as the current one
					AcideMainWindow.getInstance().getConsolePanel()
							.getCommandRecord().setCurrentIndex(0);
				else

					// Sets the next one as the current one
					AcideMainWindow
							.getInstance()
							.getConsolePanel()
							.getCommandRecord()
							.setCurrentIndex(
									AcideMainWindow.getInstance()
											.getConsolePanel()
											.getCommandRecord()
											.getCurrentIndex() + 1);

				// Replaces the command by the previous one
				try {

					// Clears the zone after the prompt caret
					// position
					AcideMainWindow
							.getInstance()
							.getConsolePanel().getTextPane().getDocument()
							.remove(AcideMainWindow.getInstance()
									.getConsolePanel().getPromptCaretPosition(),
									command.length());

					if (AcideMainWindow.getInstance().getConsolePanel()
							.getCommandRecord().getCurrentIndex() > -1)
						// Puts the current command in the command
						// record
						AcideMainWindow
								.getInstance()
								.getConsolePanel()
								.getTextPane()
								.getDocument()
								.insertString(
										AcideMainWindow.getInstance()
												.getConsolePanel()
												.getTextPane().getDocument()
												.getLength(),
										AcideMainWindow
												.getInstance()
												.getConsolePanel()
												.getCommandRecord()
												.get(AcideMainWindow
														.getInstance()
														.getConsolePanel()
														.getCommandRecord()
														.getCurrentIndex()),
										null);
				} catch (BadLocationException exception) {

					// Updates the log
					AcideLog.getLog().error(exception.getMessage());
					exception.printStackTrace();
				}
			}
		}
	}

	private void vKeyAction() {

		Clipboard cb = Toolkit.getDefaultToolkit().getSystemClipboard();
		Transferable t = cb.getContents(this);

		// Construimos el DataFlavor correspondiente al String java
		DataFlavor dataFlavorStringJava = null;
		try {
			dataFlavorStringJava = new DataFlavor(
					"application/x-java-serialized-object; class=java.lang.String");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		String texto = null;
		// Y si el dato se puede conseguir como String java, lo sacamos por
		// pantalla
		if (t.isDataFlavorSupported(dataFlavorStringJava)) {

			try {
				texto = (String) t.getTransferData(dataFlavorStringJava);
			} catch (UnsupportedFlavorException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		String[] commands = texto.split("\n");

		Writer wr = AcideMainWindow.getInstance().getConsolePanel()
				.getProcessThread().getWriter();

		for (int i = 0; i < commands.length; i++) {
			if (!commands[i].equalsIgnoreCase("")) {
				try {
					if (i < commands.length - 1) {
						commands[i] += "\n";
						AcideMainWindow.getInstance().getConsolePanel()
								.addText(commands[i]);
						wr.write(commands[i]);
						wr.flush();
					} else {
						if (texto.endsWith("\n")) {
							AcideMainWindow.getInstance().getConsolePanel()
									.addText(commands[i] + "\n");
							wr.write(commands[i] + "\n");
							wr.flush();
						} else {
							String text = "";
							text = AcideMainWindow.getInstance()
									.getConsolePanel().getTextPane().getText();
							text = text.concat(commands[i]);
							AcideMainWindow.getInstance().getConsolePanel()
									.getTextPane().setText(text);
						}

					}
					try {
						Thread.currentThread();
						Thread.sleep(50);// sleep for 20 ms
					} catch (Exception e) {
						e.printStackTrace();
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

	}

	/**
	 * Performs the up key action, updating the command record.
	 * 
	 * @param command
	 *            command to execute.
	 */
	private void upKeyAction(String command) {
		
		
		if (AcideMainWindow.getInstance().getConsolePanel().getProcessThread()
				.getWriter() != null) {

			// If there are commands in the command record
			if (AcideMainWindow.getInstance().getConsolePanel()
					.getCommandRecord().getCurrentIndex() > -1) {

				// If it is the first command
				if (AcideMainWindow.getInstance().getConsolePanel()
						.getCommandRecord().getCurrentIndex() == 0)

					// Sets the last one as the current one
					AcideMainWindow
							.getInstance()
							.getConsolePanel()
							.getCommandRecord()
							.setCurrentIndex(
									AcideMainWindow.getInstance()
											.getConsolePanel()
											.getCommandRecord()
											.getMaximumIndex() - 1);

				else
					
					// Sets the previous one as the current one
					AcideMainWindow
							.getInstance()
							.getConsolePanel()
							.getCommandRecord()
							.setCurrentIndex(
									AcideMainWindow.getInstance()
											.getConsolePanel()
											.getCommandRecord()
											.getCurrentIndex() - 1);

				// Replaces the command by the previous one
				try {
					
					// Clears the zone after the prompt caret
					// position
					AcideMainWindow
							.getInstance()
							.getConsolePanel().getTextPane().getDocument()
							.remove(AcideMainWindow.getInstance()
									.getConsolePanel().getPromptCaretPosition(),
									command.length());
					

					if (AcideMainWindow.getInstance().getConsolePanel()
							.getCommandRecord().getCurrentIndex() > -1)
						// Puts the current command in the
						// command record
						AcideMainWindow
								.getInstance()
								.getConsolePanel().getTextPane().getDocument().insertString(
										AcideMainWindow.getInstance()
												.getConsolePanel()
												.getPromptCaretPosition(),
										AcideMainWindow
												.getInstance()
												.getConsolePanel()
												.getCommandRecord()
												.get(AcideMainWindow
														.getInstance()
														.getConsolePanel()
														.getCommandRecord()
														.getCurrentIndex()),
										null);
					
					
				} catch (BadLocationException exception) {

					// Updates the log
					AcideLog.getLog().error(exception.getMessage());
					exception.printStackTrace();
				}
			}
		}
	}
}
