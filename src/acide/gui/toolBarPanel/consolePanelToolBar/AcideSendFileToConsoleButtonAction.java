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
 *      -Version from 0.12 to 0.16
 *      	- Semíramis Gutiérrez Quintana
 *      	- Juan Jesús Marqués Ortiz
 *      	- Fernando Ordás Lorente
 *      -Version 0.17
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
package acide.gui.toolBarPanel.consolePanelToolBar;

import java.awt.Cursor;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;

import javax.swing.JOptionPane;
import javax.swing.text.BadLocationException;

import acide.configuration.workbench.AcideWorkbenchConfiguration;
import acide.gui.debugPanel.traceDatalogPanel.AcideTraceDatalogPanel;
import acide.gui.debugPanel.traceSQLPanel.AcideTraceSQLPanel;
import acide.gui.graphCanvas.AcideGraphCanvas;
import acide.gui.graphPanel.AcideGraphPanel;
import acide.gui.mainWindow.AcideMainWindow;
import acide.gui.mainWindow.utils.AcideLastElementOnFocus;
import acide.language.AcideLanguageManager;

/**
 * ACIDE - A Configurable IDE send file to console button action.
 * 
 * @version 0.11
 * @see MouseListener
 */
@SuppressWarnings("static-access")
public class AcideSendFileToConsoleButtonAction implements MouseListener {

	public static void initRefresh(){
		//unable the refresh buttons
		AcideTraceDatalogPanel.refreshDatalog.setEnabled(false);
		AcideTraceSQLPanel.refreshSQL.setEnabled(false);
		//enable the refresh button
		AcideGraphPanel.refreshPDG.setEnabled(true);
	}
	
	@Override
	public void mouseClicked(MouseEvent arg0) {
		
		// Gets the last focused elements
		AcideLastElementOnFocus lastElementOnfocus = AcideMainWindow.getInstance().getLastElementOnFocus();
		
		initRefresh();
		
		// Sets the wait cursor
		AcideMainWindow.getInstance().setCursor(
				Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
		
		boolean confirmacion = AcideWorkbenchConfiguration.getInstance()
				.getFileEditorConfiguration().getSendToConsoleConfirmation();

		int returnValueAreYouSure = JOptionPane.OK_OPTION;
		if (confirmacion) {
			// Ask if are you sure about the operation
			returnValueAreYouSure = JOptionPane.showConfirmDialog(
					null,
					AcideLanguageManager.getInstance().getLabels()
							.getString("s2006"), AcideLanguageManager
							.getInstance().getLabels().getString("s953"),
					JOptionPane.YES_NO_OPTION);
		}
		// If it is OK
		if (returnValueAreYouSure == JOptionPane.OK_OPTION) {

			// Gets the selected text, if it exists
			String text = AcideMainWindow.getInstance().getFileEditorManager()
					.getSelectedFileEditorPanel()
					.getTextEditionAreaContentSelected();
			boolean textSelected = true;

			// If it doesn't exist selected text takes the file content
			if (text == null || text.equals("")) {
				text = AcideMainWindow.getInstance().getFileEditorManager()
						.getSelectedFileEditorPanel()
						.getTextEditionAreaContent();
				textSelected = false;
			}

			// List of commands
			ArrayList<String> commands = new ArrayList<String>();

			// Obtains each command from the text and keep it in the list
			while (!text.equals("")) {
				// Each command is separated by \n
				int end = text.indexOf("\n");
				// There is not \n, that means it's the last command
				if (end == -1) {
					commands.add(text);
					text = "";
				} else {
					// Takes the command from the text and keeps it
					String command = text.substring(0, end + 1);
					commands.add(command);
					// Erases the command from the text
					text = text.substring(end + 1);
				}
			}

			// Gets the console writer process
			Writer writer = AcideMainWindow.getInstance().getConsolePanel()
					.getProcessThread().getWriter();

			if (writer == null) {
				JOptionPane.showMessageDialog(null, AcideLanguageManager
						.getInstance().getLabels().getString("s2041"),
						AcideLanguageManager.getInstance().getLabels()
								.getString("s2040"),
						JOptionPane.WARNING_MESSAGE);
				AcideMainWindow.getInstance().setCursor(
						Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
				return;
			}

			// Puts the command one by one in the console
			for (int i = 0; i < commands.size(); i++) {

				try {

					// the last command when the text is selected and it doesn't
					// finish in \n must not be executed
					if ((i == commands.size() - 1)
							&& (!commands.get(i).endsWith("\n"))
							&& (textSelected)) {
						// Writes the command on the console 
						AcideMainWindow
								.getInstance()
								.getConsolePanel()
								.getTextPane()
								.getDocument()
								.insertString(
										AcideMainWindow.getInstance()
												.getConsolePanel()
												.getTextPane().getCaretPosition(), commands.get(i),
										null);
						
					} else {
						//Writes the command on the console
						AcideMainWindow
								.getInstance()
								.getConsolePanel()
								.getTextPane()
								.getDocument()
								.insertString(
										AcideMainWindow.getInstance()
												.getConsolePanel()
												.getTextPane().getCaretPosition(), commands.get(i),
										null);
						// Executes the command
						writer.write(commands.get(i));
						writer.flush();
					}

					// The last command must be execute if it ends with \n
					if ((i == commands.size() - 1)
							&& (!commands.get(i).endsWith("\n"))
							&& (!textSelected)) {
						// Writes the command on the console
						AcideMainWindow
								.getInstance()
								.getConsolePanel()
								.getTextPane()
								.getDocument()
								.insertString(
										AcideMainWindow.getInstance()
												.getConsolePanel()
												.getTextPane().getCaretPosition(), "\n", null);
						// Executes the command
						writer.write("\n");
						writer.flush();
					}
				} catch (IOException e) {
					e.printStackTrace();
				} catch (BadLocationException e) {
					e.printStackTrace();
				}
				try {
					// Sleep for 20 ms
					Thread.currentThread().sleep(50);
				} catch (Exception ie) {
					ie.printStackTrace();
				}

			}
		}
		// Gets back the default cursor
		AcideMainWindow.getInstance().setCursor(
				Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));

		// Gets the caret position
		int pos = AcideMainWindow.getInstance().getFileEditorManager()
				.getSelectedFileEditorPanel().getTextEditionAreaCaretPosition();

		// Puts the caret back in its place
		AcideMainWindow.getInstance().getFileEditorManager()
				.getSelectedFileEditorPanel().selectText(pos, 0);

		// Sets the focus to the previous owner
		AcideLastElementOnFocus.setFocusOnLastElementOnFocus(lastElementOnfocus);
		
	}

	/*
	 * (non-Javadoc)
	 * @see java.awt.event.MouseListener#mouseEntered(java.awt.event.MouseEvent)
	 */
	@Override
	public void mouseEntered(MouseEvent e) {
	}

	/*
	 * (non-Javadoc)
	 * @see java.awt.event.MouseListener#mouseExited(java.awt.event.MouseEvent)
	 */
	@Override
	public void mouseExited(MouseEvent e) {
	}

	/*
	 * (non-Javadoc)
	 * @see java.awt.event.MouseListener#mousePressed(java.awt.event.MouseEvent)
	 */
	@Override
	public void mousePressed(MouseEvent e) {
	}

	/*
	 * (non-Javadoc)
	 * @see java.awt.event.MouseListener#mouseReleased(java.awt.event.MouseEvent)
	 */
	@Override
	public void mouseReleased(MouseEvent e) {
	}

}
