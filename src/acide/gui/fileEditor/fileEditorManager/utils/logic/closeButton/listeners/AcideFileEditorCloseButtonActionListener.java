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
package acide.gui.fileEditor.fileEditorManager.utils.logic.closeButton.listeners;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.plaf.UIResource;

import acide.gui.mainWindow.AcideMainWindow;

/**
 * ACIDE - A Configurable IDE file editor close button action listener.
 * 
 * @version 0.11
 * @see UIResource
 * @see AbstractAction
 */
public class AcideFileEditorCloseButtonActionListener extends AbstractAction {

	/**
	 * ACIDE - A Configurable IDE file editor close button action listener class
	 * serial version UID.
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * ACIDE - A Configurable IDE close button editor index.
	 */
	private int _index;

	/**
	 * Creates a new close button action listener.
	 * 
	 * @param index
	 *            close button editor index.
	 */
	public AcideFileEditorCloseButtonActionListener(int index) {
		
		super();
		
		// Stores the button index
		_index = index;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event
	 * .ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent actionEvent) {

		// Closes the file
		AcideMainWindow.getInstance().getMenu().getFileMenu().closeFile(_index);
	}
}
