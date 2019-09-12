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
package acide.gui.debugPanel.utils;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.Locale;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRootPane;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;
import javax.swing.UIManager;

import acide.gui.mainWindow.AcideMainWindow;
import acide.language.AcideLanguageManager;

/**
 * 
 * ACIDE - A Configurable IDE debug panel error message dialog
 * 
 * @version 0.16
 * @see JDialog
 */
public class AcideDebugPanelErrorMessageDialog extends JDialog {

	/**
	 * ACIDE - A Configurable IDE error message dialog class serial version UID.
	 */
	private static final long serialVersionUID = 1L;
	
	
	/**
	 * ACIDE - A Configurable IDE error message dialog image icon.
	 */
	private static final ImageIcon ICON = new ImageIcon(
			"./resources/images/icon.png");

	/**
	 * ACIDE - A Configurable IDE error message dialog accept button.
	 */
	private JButton _okButton;
	/**
	 * ACIDE - A Configurable IDE error message dialog button panel.
	 */
	private JPanel _buttonPanel;
	
	private String _msg;
	/**
	 * ACIDE - A Configurable IDE error message dialog panel.
	 */
	private JPanel _panel;
	/**
	 * ACIDE - A Configurable IDE error message dialog label.
	 */
	private JLabel label;


	/**
	 * Creates a new ACIDE - A Configurable IDE error message dialog.
	 */
	public AcideDebugPanelErrorMessageDialog(String title, String query) {

		AcideMainWindow.getInstance().setAlwaysOnTop(false);

		AcideMainWindow.getInstance().setEnabled(false);
		// Sets the title
		this.setTitle(title);
		// sets the type of the query
		this.setMsg(query);
		// Sets the icon
		this.setIconImage(ICON.getImage());

		buildComponents();

		setLookAndFeel();

		setListeners();

		setVisible(true);

		setAlwaysOnTop(true);

		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
		this.setLocationRelativeTo(AcideMainWindow.getInstance());
		
	}

	/**
	 * Builds the components of the ACIDE - A Configurable IDE query dialog.
	 */
	private void buildComponents() {

		Icon icon = UIManager.getIcon("OptionPane.errorIcon");
		
		label = new JLabel(getMsg(), icon, SwingConstants.LEFT);
		
		// Builds the accept button
		_okButton = new JButton(AcideLanguageManager.getInstance()
				.getLabels().getString("s154"));

		if (AcideLanguageManager.getInstance().getCurrentLocale()
				.equals(new Locale("en", "EN"))) {
			_okButton.setMnemonic('O');
		} else {
			_okButton.setMnemonic('A');
		}
		
		_buttonPanel = new JPanel();
		
		_buttonPanel.add(_okButton);

		// /Builds the panels
		_panel = new JPanel();
		
		_panel.setLayout(new BorderLayout());
		
		this.getContentPane().setLayout(new BorderLayout());

	}

	/**
	 * Sets the appearance of the ACIDE - A Configurable IDE query dialog.
	 */
	private void setLookAndFeel() {
		
		_panel.add(label,BorderLayout.CENTER);
		_panel.add(_buttonPanel,BorderLayout.SOUTH);
		
		this.getContentPane().add(_panel,BorderLayout.CENTER);

		this.setLocationRelativeTo(null);
		this.setResizable(true);
		
		this.pack();
		
		
		
		setModal(true);
	}

	/**
	 * Sets the listeners of the ACIDE - A Configurable IDE query dialog.
	 */
	private void setListeners() {

		
		
		_okButton.addActionListener(new ActionListener() {
			/*
			 * (non-Javadoc)
			 * 
			 * @see
			 * java.awt.event.ActionListener#actionPerformed(java.awt.event.
			 * ActionEvent)
			 */
			@Override
			public void actionPerformed(ActionEvent e) {
				closeWindow();
			}
		});

		

		// closes the windows when the escape key is pressed
		JRootPane root = this.getRootPane();
		root.registerKeyboardAction(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				closeWindow();

			}
		}, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0),
				JComponent.WHEN_IN_FOCUSED_WINDOW);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.JDialog#setDefaultCloseOperation(int)
	 */
	@Override
	public void setDefaultCloseOperation(int option) {
		if (option == DISPOSE_ON_CLOSE)
			closeWindow();
	}

	/**
	 * Returns the ACIDE - A Configurable IDE debug panel query dialog query.
	 * 
	 * @return the ACIDE - A Configurable IDE debug panel trace query dialog
	 *         query.
	 */
	public String getMsg() {
		return _msg;
	}

	/**
	 * Sets a new value to the ACIDE - A Configurable IDE debug panel query
	 * dialog query type.
	 * 
	 * @param queryType
	 *            the new value to set.
	 */
	public void setMsg(String msg) {
		this._msg = msg;
	}

	/**
	 * Colses the ACIDE - A Configurable IDE debug panel query
	 * dialog
	 */
	private void closeWindow() {

		AcideMainWindow.getInstance().setEnabled(true);

		AcideMainWindow.getInstance().setAlwaysOnTop(true);

		AcideMainWindow.getInstance().setAlwaysOnTop(false);

		
		
		this.dispose();

	}
}
