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
package acide.gui.consolePanel.popup.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import acide.configuration.workbench.AcideWorkbenchConfiguration;
import acide.gui.mainWindow.AcideMainWindow;
import acide.log.AcideLog;

public class AcideLineWrappingMenuItemAction implements ActionListener {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent actionEvent) {
		
		// Updates the ACIDE - A Configurable IDE workbench console
		// configuration
//		AcideWorkbenchConfiguration
//				.getInstance()
//				.getConsolePanelConfiguration()
//				.setLineWrapping(
//						AcideMainWindow.getInstance().getConsolePanel().getPopupMenu()
//								.getLineWrappingMenuItem().isSelected());
		boolean lineWrapping = AcideWorkbenchConfiguration.getInstance()
				.getConsolePanelConfiguration().getLineWrapping();

		AcideWorkbenchConfiguration.getInstance()
				.getConsolePanelConfiguration().setLineWrapping(!lineWrapping);

		AcideMainWindow.getInstance().getMenu().getConfigurationMenu()
				.getConsoleMenu().getConsoleLineWrappingCheckBoxMenuItem()
				.setSelected(!lineWrapping);

		AcideMainWindow.getInstance().getConsolePanel().getPopupMenu()
				.getLineWrappingMenuItem().setSelected(!lineWrapping);

		try {

			// Provokes the changes in the console to apply the changes
//			AcideMainWindow.getInstance().getConsolePanel().getTextPane()
//					.getDocument()
//					.insertString(0, "", new SimpleAttributeSet());
			
			int div = AcideMainWindow
					.getInstance()
					.getSpecificSplitPane(
							AcideMainWindow.getInstance().getConsolePanel()
									.getSplitContainer()).getDividerLocation();
			
			if (lineWrapping)
				div++;
			else
				div--;

			AcideMainWindow
					.getInstance()
					.getSpecificSplitPane(
							AcideMainWindow.getInstance().getConsolePanel()
									.getSplitContainer())
					.setDividerLocation(div);
			
				
//		} catch (BadLocationException exception) {
		} catch (Exception exception) {

			// Updates the log
			AcideLog.getLog().error(exception.getMessage());
			exception.printStackTrace();
		}
	}

}
