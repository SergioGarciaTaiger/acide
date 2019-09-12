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
 *      	- Sergio Domínguez
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
package acide.gui.assertedDatabasePanel.listeners;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import acide.gui.mainWindow.AcideMainWindow;


/**
 * ACIDE - A Configurable IDE Asserted Database Panel clear button action listener.
 * 
 * @version 0.15
 * @see KeyAdapter
 */
public class AcideAssertedDatabaseKeyboardListener extends KeyAdapter {

	/*
	 * (non-Javadoc)
	 * @see java.awt.event.KeyAdapter#keyPressed(java.awt.event.KeyEvent)
	 */
	@Override
	public void keyPressed(KeyEvent keyEvent) {
		
		switch(keyEvent.getKeyCode()) {
		
		case KeyEvent.VK_F4: 
			if (keyEvent.isAltDown())
				AcideMainWindow.getInstance().getAssertedDatabasePanel().close();
			keyEvent.consume();
			break;
			
		case KeyEvent.VK_F5:
			if (AcideMainWindow.getInstance().getAssertedDatabasePanel().getNumberOfPredicatesCheckBox().isEnabled())
				AcideMainWindow.getInstance().getAssertedDatabasePanel().refreshNode();
			else
				AcideMainWindow.getInstance().getAssertedDatabasePanel().refresh();
			keyEvent.consume();
			break;
		
		case KeyEvent.VK_C:
			AcideMainWindow.getInstance().getAssertedDatabasePanel().clear();
			keyEvent.consume();
			break;
		}
	}


	
}
