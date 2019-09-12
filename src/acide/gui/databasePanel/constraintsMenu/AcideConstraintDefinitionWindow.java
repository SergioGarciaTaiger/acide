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

package acide.gui.databasePanel.constraintsMenu;

import java.awt.BorderLayout;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JTabbedPane;

import acide.gui.listeners.AcideWindowClosingListener;
import acide.gui.mainWindow.AcideMainWindow;
import acide.language.AcideLanguageManager;

/**
 * ACIDE - A Configurable IDE constraints window.
 * 
 * @version 0.16
 * @see JFrame
 */
public class AcideConstraintDefinitionWindow extends JFrame{

	private static final long serialVersionUID = 1L;
	
	private static final String WINDOW_ICON = "./resources/images/icon.png";
	
	private JTabbedPane _tabbedPane;
	
	private AcideCandidateKeyPanel _panelCK;
	
	private AcideForeignKeyPanel _panelFK;
	
	private AcideFunctionalDependencyPanel _panelFD;
	
	private AcideIntegrityConstraintsPanel _panelIC;
	
	private AcidePrimaryKeyPanel _panelPK;
	
	private AcideNotNullPanel _panelNN;
	
	private String _database;
	
	private String _name;
	
	
	public AcideConstraintDefinitionWindow(String database, String name){
		
		_database = database;
		
		_name = name;
		
		// Builds the window components
		buildComponents();

		// Sets the listeners of the window components
		setListeners();

		// Adds the components to the window
		addComponents();
		
		
		setLookAndFeel();
		
	}
	
	private void buildComponents() {
		
		_tabbedPane = new JTabbedPane();
		
		_panelCK = new AcideCandidateKeyPanel(_database, _name);
		
		_panelFK = new AcideForeignKeyPanel(_database, _name);
		
		_panelFD = new AcideFunctionalDependencyPanel(_database, _name);
		
		_panelIC = new AcideIntegrityConstraintsPanel(_database, _name);
		
		_panelPK = new AcidePrimaryKeyPanel(_database, _name);
		
		_panelNN = new AcideNotNullPanel(_database,_name);
	
		
	}


	private void addComponents() {
		
		// Sets the layout
		setLayout(new BorderLayout());
		
		_tabbedPane.addTab("PK",_panelPK);
		
		_tabbedPane.addTab("CK",_panelCK);
		
		_tabbedPane.addTab("FK",_panelFK);
		
		_tabbedPane.addTab("NN",_panelNN);
		
		_tabbedPane.addTab("FD",_panelFD);
		
		_tabbedPane.addTab("IC",_panelIC);
	
		// Adds the main panel to the window
		add(_tabbedPane);
		
	}


	private void setLookAndFeel() {

		setTitle(AcideLanguageManager.getInstance()
				.getLabels().getString("s2300")+ " : " + _name);
		
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
	
		
	}
	
	private void setListeners() {
		
		addWindowListener(new AcideWindowClosingListener());	
	}

	public void update() {
		_panelPK.updatePanel();
		_panelCK.updatePanel();
		_panelNN.updatePanel();
		_panelIC.updatePanel();
		_panelFK.updatePanel();
		_panelFD.updatePanel();
	}

	public JTabbedPane getTabbedPane(){
		return _tabbedPane;
	}
}
