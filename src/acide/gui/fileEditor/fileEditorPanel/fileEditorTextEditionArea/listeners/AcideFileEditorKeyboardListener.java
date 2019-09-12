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
package acide.gui.fileEditor.fileEditorPanel.fileEditorTextEditionArea.listeners;

import java.awt.event.FocusListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.lang.reflect.InvocationTargetException;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.SwingUtilities;

import acide.gui.mainWindow.AcideMainWindow;
import acide.gui.toolBarPanel.consolePanelToolBar.AcideSendFileToConsoleButtonAction;
import acide.gui.splashImageScreen.AcideImageSplashScreenWindow;
import acide.gui.splashScreen.AcideSplashScreenWindow;

/**
 * ACIDE - A Configurable IDE file editor text edition area keyboard listener.
 * 
 * @version 0.11
 * @see FocusListener
 */
public class AcideFileEditorKeyboardListener extends KeyAdapter {

	int arr[] = new int[2];

	private static AcideMainWindow acideWindow;

//	private static JProgressBar _progress;

	/**
	 * ACIDE - A Configurable IDE splash screen window main panel.
	 */
//	private static JPanel _mainPanel;

	public AcideFileEditorKeyboardListener() {
		AcideFileEditorKeyboardListener.acideWindow = AcideMainWindow.getInstance();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.awt.event.KeyAdapter#keyPressed(java.awt.event.KeyEvent)
	 */

	/*private static void executeTimeSplash() {

		try {
			SwingUtilities.invokeAndWait(new Runnable() {*/
				/*
				 * (non-Javadoc)
				 * 
				 * @see java.lang.Runnable#run()
				 */
/*				public void run() {

					// Shows the splash screen
					AcideImageSplashScreenWindow.getInstance()
							.showSplashScreenWindow();

				}
			});
		} catch (InterruptedException exception) {

			exception.printStackTrace();

		} catch (InvocationTargetException exception) {

			exception.printStackTrace();
		}

		SwingUtilities.invokeLater(new Runnable() {*/
			/*
			 * (non-Javadoc)
			 * 
			 * @see java.lang.Runnable#run()
			 */
/*			public void run() {

				// Sends the file content to the console
				AcideMainWindow.getInstance().getFileEditorManager()
						.getSelectedFileEditorPanel().getPopupMenu()
						.getSendFileContentToConsoleMenuItem().doClick();

				// Init to refresh buttons console
				AcideSendFileToConsoleButtonAction.initRefresh();

				// Closes the splash screen
				AcideImageSplashScreenWindow.getInstance()
						.closeSplashScreenWindow();

			}
		});
	}

	public void iterate(final int finalValue) {
		// _progress.setValue(count);
		int count = 0;
		while ((finalValue <= 1000) && (count < finalValue)) {
			_progress.setIndeterminate(false);
			try {
				Thread.sleep(100);
				_progress.setValue(count);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			count += 90;
		}

	}

	private void buildComponents() {

		// Gets the content pane
		//_mainPanel = (JPanel) getContentPane();

		// Sets the window border
		_mainPanel.setBorder(BorderFactory.createLineBorder(new Color(30, 70,
				115), 2));

		
	 	// Creates the image label _image = new JLabel(IMAGE);
		 

		_progress = new JProgressBar(0, 1000);
		_progress.setValue(0);
		_progress.setStringPainted(true);
		_progress.setVisible(true);
	}

	private void addComponents() {

		// Sets the layout
		_mainPanel.setLayout(new BorderLayout());

		_mainPanel.add(_progress, BorderLayout.SOUTH);
	}

	private void setWindowConfiguration() {

		// Applies the layout
		acideWindow.pack();

		// Centers the window
		acideWindow.setLocationRelativeTo(null);
	}*/
	/*
	 * (non-Javadoc)
	 * 
	 * @see java.awt.event.KeyAdapter#keyReleased(java.awt.event.KeyEvent)
	 */
	@Override
	public void keyReleased(KeyEvent keyEvent) {

		// If it is F9
		if (keyEvent.getKeyCode() == KeyEvent.VK_F9) {
			AcideFileEditorKeyboardListener.acideWindow.setCursor(new Cursor(Cursor.WAIT_CURSOR));

		/*	// Builds the components
			buildComponents();

			// Adds the components
			addComponents();
			
			// Sets the window configuration
			setWindowConfiguration();*/

			//AcideImageSplashScreenWindow.getInstance().showSplashScreenWindow();
			/*
			 * JOptionPane.showMessageDialog(null,
			 * AcideLanguageManager.getInstance().getLabels()
			 * .getString("s2005"));
			 */
	//		iterate(1000);
			// executeTimeSplash();
			
			// Sends the file content to the console
			AcideMainWindow.getInstance().getFileEditorManager()
					.getSelectedFileEditorPanel().getPopupMenu()
					.getSendFileContentToConsoleMenuItem().doClick();

			// Init to refresh buttons console
			AcideSendFileToConsoleButtonAction.initRefresh();

			// Closes the splash screen
		/*	AcideImageSplashScreenWindow.getInstance()
					.closeSplashScreenWindow();*/

		}
		AcideFileEditorKeyboardListener.acideWindow.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));

	}
}
