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
package acide.gui.menuBar.configurationMenu.menuMenu.gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.KeyStroke;

import acide.configuration.menu.AcideMenuObjectConfiguration;
import acide.configuration.menu.AcideMenuSubmenuConfiguration;

import acide.language.AcideLanguageManager;

/**
 * ACIDE - A Configurable IDE choose menu to send window.
 * 
 * @version 0.11
 * @see JFrame
 */
public class AcideChooseSubmenuToInsert extends JFrame{
	/**
	 * ACIDE - A Configurable IDE choose submenu to insert window submenus to show
	 */
	private AcideMenuSubmenuConfiguration _configurations;
	/**
	 * ACIDE - A Configurable IDE choose submenu to insert window class serial version
	 * UID.
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * ACIDE - A Configurable IDE choose submenu to insert windowJCombobox to show the submenus
	 */
	private JComboBox _comboBox;
	/**
	 * ACIDE - A Configurable IDE choose submenu to insert window Path of the selected menu object
	 */
	private String _path;
	/**
	 * ACIDE - A Configurable IDE choose submenu to insert window selected menu object
	 */
	private AcideMenuObjectConfiguration _object;
	/**
	 * ACIDE - A Configurable IDE choose submenu to insert window image icon.
	 */
	private static final ImageIcon ICON = new ImageIcon("./resources/images/icon.png");
	/**
	 * ACIDE - A Configurable IDE choose submenu to insert window ok button.
	 */
	private JButton _ok;
	/**
	 * ACIDE - A Configurable IDE choose submenu to insert window cancel button.
	 */
	private JButton _cancel;
	/**
	 * ACIDE - A Configurable IDE choose submenu to insert window text label.
	 */
	private JLabel _label;
	/**
	 * ACIDE - A Configurable IDE choose submenu to insert window button panel.
	 */
	private JPanel _buttonPanel;
	/**
	 * ACIDE - A Configurable IDE choose submenu to insert window label panel.
	 */
	private JPanel _labelPanel;
	
	/**
	 * Creates the choose submenu to insert window
	 * @param configurations
	 * 			submenus to show
	 * @param allLessThis
	 * 			menu must not be showed
	 * @param object
	 * 			selected menu object
	 * @param path
	 * 			path of the selected menu object
	 */
	public AcideChooseSubmenuToInsert(AcideMenuSubmenuConfiguration configurations, 
		AcideMenuObjectConfiguration object,  String path){
		super();
		
		AcideMenuNewConfigurationWindow.getInstance().setEnabled(false);
	
		_configurations = configurations;
		_path = path;
		_object = object;
		
		// Builds the window components
		buildComponents();
		
		// Sets the listeners of the window components
		setListeners();

		// Adds the components to the window
		addComponents();

		// Sets the window configuration
		setWindowConfiguration();
	}
	
	/**
	 * Builds the ACIDE - A Configurable IDE choose menu to send window
	 * components.
	 */
	private void buildComponents() {
		String[] menus = _configurations.getAllSubmenusWithPath(_configurations.getName());
		_comboBox = new JComboBox(menus);
		
		// Creates the ok button
		_ok = new JButton(AcideLanguageManager.getInstance()
				.getLabels().getString("s154"));
		
		_cancel = new JButton(AcideLanguageManager.getInstance()
				.getLabels().getString("s42"));
		
		_label = new JLabel(AcideLanguageManager.getInstance()
				.getLabels().getString("s2223"));
		
		// Creates the button panel
		_buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		
		// Creates the button panel
		_labelPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		
	}
	
	/**
	 * Sets the listeners to the ACIDE - A Configurable IDE choose menu to send window
	 * window components.
	 */
	private void setListeners() {
		_ok.addActionListener(new OkAction());
		
		_cancel.addActionListener(new EscapeKeyAction());
		
		// When the enter key is pressed the executes the search button action
		_comboBox.registerKeyboardAction(new OkAction(),
				"EnterKey", KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0, true),
				JComponent.WHEN_IN_FOCUSED_WINDOW);
		
		// Puts the escape key in the input map of the window
		getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
				KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0, false), "EscapeKey");

		// Puts the escape key in the action map of the window
		getRootPane().getActionMap().put("EscapeKey", new EscapeKeyAction());
	}
	
	/**
	 * Adds the components to the ACIDE - A Configurable IDE choose menu to send window
	 * window with the layout.
	 */
	private void addComponents() {
		// Sets the layout
		setLayout(new BorderLayout());
		
		_labelPanel.add(_label);
		
		_labelPanel.add(_comboBox);
		
		// Adds the main panel to the window
		add(_labelPanel, BorderLayout.NORTH);
		
		_buttonPanel.add(_ok);
		
		_buttonPanel.add(_cancel);
		
		add(_buttonPanel, BorderLayout.CENTER);

	}
	
	/**
	 * Sets the ACIDE - A Configurable IDE choose menu to send window
	 * configuration.
	 */
	private void setWindowConfiguration() {
		// Sets the window title
		setTitle(AcideLanguageManager.getInstance().getLabels()
				.getString("s2224"));
		
		// Sets the window icon
		setIconImage(ICON.getImage());
		
		// The window is not resizable
		setResizable(true);

		// Packs the window components
		pack();

		// Centers the window
		setLocationRelativeTo(null);

		// Displays the window
		setVisible(true);

	}
	
	/**
	 * ACIDE - A Configurable IDE menu configuration ok button
	 * action listener.
	 * 
	 * @version 0.11
	 * @see ActionListener
	 */
	class OkAction implements ActionListener {

	
		public void actionPerformed(ActionEvent arg0) {
			String chosen = (String) _comboBox.getSelectedItem();
			
			AcideMenuSubmenuConfiguration menuToSend = _configurations.getInternSubmenu(chosen);
			
			_configurations.delete(_object, _path);
			
			menuToSend.insertObject(_object);
			
			AcideMenuNewConfigurationWindow.getInstance().applyChangesToMenu(true);
			
			AcideMenuNewConfigurationWindow.getInstance().setEnabled(true);

			// Closes the window
			dispose();
		}
	
	}
	
	/**
	 * ACIDE - A Configurable IDE choose menu to send key action.
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

			AcideMenuNewConfigurationWindow.getInstance().setEnabled(true);

			// Closes the window
			dispose();
		}
	}
}
