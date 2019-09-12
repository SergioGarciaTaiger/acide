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
package acide.gui.databasePanel.dataView.commandBar;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;

import javax.swing.JPanel;
import javax.swing.JToolBar;

import acide.gui.databasePanel.dataView.AcideDatabaseDataView;
import acide.language.AcideLanguageManager;
import acide.log.AcideLog;

/**
 * ACIDE - A Configurable IDE tool bar panel.
 * 
 * @version 0.13
 * @see JPanel
 */
public class AcideDataViewCommandBarPanel extends JPanel {

	/**
	 * ACIDE - A Configurable IDE tool bar panel class serial version UID.
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * ACIDE - A Configurable IDE tool bar panel tool bar.
	 */
	private JToolBar _toolBar;

	/**
	 * ACIDE - A Configurable IDE tool bar panel menu bar tool bar.
	 */
	private AcideDataViewCommandBar _dataViewCommandBar;


	/**
	 * Creates a new ACIDE - A Configurable IDE tool bar panel.
	 * @param table 
	 */
	public AcideDataViewCommandBarPanel() {

		super(new BorderLayout());
		
		this.setSize(new Dimension(20, 20));

		// Creates the tool bar
		_toolBar = new JToolBar();

		// The tool bar is static and it can not be moved
		_toolBar.setFloatable(false);

	}

	/**
	 * Builds the ACIDE - A Configurable IDE tool bar panel.
	 */
	public void buildAcideToolBarPanel(AcideDatabaseDataView table) {

		// Removes the previous components
		_toolBar.removeAll();
		
		_dataViewCommandBar = new AcideDataViewCommandBar(table);
		
		// Builds the menu bar tool bar
		addDataViewCommandBar(_dataViewCommandBar.build());
		
		addComponents();
	}

	/**
	 * Adds the components to the ACIDE - A Configurable IDE tool bar panel.
	 */
	private void addComponents() {

		// Adds the left button panel to the tool bar panel
		add(_toolBar, BorderLayout.LINE_START);
	}

	

	/**
	 * Adds the ACIDE - A Configurable IDE menu bar tool bar to the ACIDE - A
	 * Configurable IDE tool bar panel.
	 * 
	 * @param menuBarToolBar
	 *            ACIDE - A Configurable IDE menu bar tool bar to add.
	 */
	public void addDataViewCommandBar(AcideDataViewCommandBar menuBarToolBar) {

		// Adds the buttons to the tool bar
		for (Component button : menuBarToolBar)
			_toolBar.add(button);

		// Updates the log
		AcideLog.getLog().info(
				AcideLanguageManager.getInstance().getLabels()
						.getString("s125"));
	}
	
	@Override
	public void setEnabled(boolean enabled){
		_dataViewCommandBar.setEnabled(enabled);
	}

	public void setToolsTips() {
		_dataViewCommandBar.setToolsTips();		
	}
	
	/**
	 * Sets the ACIDE - A Configurable IDE menu bar tool on Read Only mode.
	 */
	public void isReadOnly(boolean b){
		_dataViewCommandBar.isReadOnly(b);
	}

}
