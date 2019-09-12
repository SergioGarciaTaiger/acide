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
package acide.gui.menuBar.configurationMenu.fileEditor.gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.KeyStroke;

import acide.configuration.workbench.AcideWorkbenchConfiguration;
import acide.gui.listeners.AcideWindowClosingListener;
import acide.gui.mainWindow.AcideMainWindow;
import acide.language.AcideLanguageManager;
import acide.log.AcideLog;

/**
 * ACIDE - A Configurable IDE preferences window.
 * 
 * @version 0.13
 * @see JFrame
 */

public class AcideFileEditorPreferencesWindow extends JFrame {

	/**
	 * ACIDE - A Configurable IDE file editor preferences window class
	 * serial version UID.
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * ACIDE - A Configurable IDE file editor preferences window image icon.
	 */
	private static final ImageIcon ICON = new ImageIcon(
			"./resources/images/icon.png");

	/**
	 * ACIDE - A Configurable IDE file editor preferences window controls
	 * panel.
	 */
	private JPanel _controlsPanel;
	/**
	 * ACIDE - A Configurable IDE file editor preferences window button
	 * panel.
	 */
	private JPanel _buttonPanel;
	/**
	 * ACIDE - A Configurable IDE file editor preferences window accept
	 * button.
	 */
	private JButton _acceptButton;
	/**
	 * ACIDE - A Configurable IDE file editor preferences window cancel
	 * button.
	 */
	private JButton _cancelButton;
	/**
	 * ACIDE - A Configurable IDE file editor preferences window number of spaces
	 * label.
	 */
	private JLabel _spacesLabel;
	/**
	 * ACIDE - A Configurable IDE file editor preferences window number of spaces
	 * text field.
	 */
	private JTextField _spacesTextField;
	
	/**
	 * ACIDE - A Configurable IDE file editor preferences window checkbox that selects space
	 * characters instead of TAB characters.
	 */
	private JCheckBox _spacesCheckBox;
	
	/**
	 * Creates a new ACIDE - A Configurable IDE file editor preferences
	 * window.
	 */
	public AcideFileEditorPreferencesWindow() {

		super();

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
	 * Sets the ACIDE - A Configurable IDE file editor preferences window
	 * configuration.
	 */
	private void setWindowConfiguration() {

		// Sets the window title
		setTitle(AcideLanguageManager.getInstance().getLabels()
				.getString("s2245"));

		// Sets the window icon image
		setIconImage(ICON.getImage());

		// The window is not resizable
		setResizable(true);

		// Packs the window components
		pack();

		// Centers the window
		setLocationRelativeTo(null);

		// Sets the window as visible
		setVisible(true);

		// Disables the main window
		AcideMainWindow.getInstance().setEnabled(false);
	}
	
	/**
	 * Adds the components to the ACIDE - A Configurable IDE preferences window 
	 * with the layout.
	 */
	private void addComponents() {

		// Sets the layout
		setLayout(new BorderLayout());

		// Adds the components to the window with the layout
		GridBagConstraints constraints = new GridBagConstraints();

		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.anchor = GridBagConstraints.CENTER;
		constraints.insets = new Insets(5, 5, 5, 5);
		constraints.gridx = 0;
		constraints.gridy = 0;

		// Adds the TAB size label to the controls panel
		_controlsPanel.add(_spacesLabel, constraints);
		
		constraints.gridx = 1;
		constraints.gridy = 0;

		// Adds the space text Field to the controls panel
		_controlsPanel.add(_spacesTextField, constraints);
		
		constraints.gridx = 0;
		constraints.gridy = 3;
		
		// Adds the space check box to the controls panel
		_controlsPanel.add(_spacesCheckBox, constraints);


		// Adds the controls panel to the window
		add(_controlsPanel, BorderLayout.CENTER);
		
		// Adds the button panel to the window
		add(_buttonPanel, BorderLayout.SOUTH);
	}
	
	/**
	 * Builds the ACIDE - A Configurable IDE preferences window
	 * components.
	 */
	private void buildComponents() {

		// Creates the controls panel
		_controlsPanel = new JPanel(new GridBagLayout());
		
		// Creates the font name label
		_spacesLabel = new JLabel(AcideLanguageManager.getInstance()
						.getLabels().getString("s2249"));
		
		//Creates the text field to introduce a number of spaces
		_spacesTextField = new JTextField(5);
		
		//Creates the checkBox to select whether TAB's are replaced by spaces or not.
		_spacesCheckBox = new JCheckBox(AcideLanguageManager.getInstance()
				.getLabels().getString("s2250"), false);
		
		_spacesCheckBox.setSelected(AcideWorkbenchConfiguration.getInstance()
				.getFileEditorConfiguration().isSpaceSelected());
		
		//Sets a value by default from the file editor configuration
		_spacesTextField.setText(String.valueOf(AcideWorkbenchConfiguration.getInstance()
				.getFileEditorConfiguration().getTabSize()));
		
		// Builds the button panel
		buildButtonPanel();

	}
	
	/**
	 * Builds the ACIDE - A Configurable IDE preferences window
	 * button panel.
	 */
	private void buildButtonPanel() {

		// Creates the button panel
		_buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));

		// Creates the accept button
		_acceptButton = new JButton(AcideLanguageManager.getInstance()
				.getLabels().getString("s445"));

		// Adds the accept button to the button panel
		_buttonPanel.add(_acceptButton);

		// Creates the cancel button
		_cancelButton = new JButton(AcideLanguageManager.getInstance()
				.getLabels().getString("s446"));

		// Adds the cancel button to the button panel
		_buttonPanel.add(_cancelButton);
	}

	/**
	 * Sets the listeners of the ACIDE - A Configurable IDE preferences
	 * window components.
	 */
	public void setListeners() {
		
		// Sets the accept button action listener
		_acceptButton.addActionListener(new AcceptButtonAction());

		// Sets the cancel button action listener
		_cancelButton.addActionListener(new CancelButtonAction());
		
		// Sets the window closing listener
		addWindowListener(new AcideWindowClosingListener());
				
		// Puts the escape key in the input map of the window
		getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
		KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0, false), "EscapeKey");

		// Puts the escape key in the action map of the window
		getRootPane().getActionMap().put("EscapeKey", new EscapeKeyAction());

	}
	
	/**
	 * Closes the ACIDE - A Configurable IDE preferences window.
	 */
	private void closeWindow() {

		// Set the main window enabled again
		AcideMainWindow.getInstance().setEnabled(true);

		// Closes the window
		dispose();

		// Brings the main window to the front
		AcideMainWindow.getInstance().setAlwaysOnTop(true);

		// But not permanently
		AcideMainWindow.getInstance().setAlwaysOnTop(false);
	}
	
	/**
	 * ACIDE - A Configurable IDE preferences window accept
	 * button action listener.
	 * 
	 * @version 0.13
	 * @see ActionListener
	 */
	class AcceptButtonAction implements ActionListener {

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.
		 * ActionEvent)
		 */
		@Override
		public void actionPerformed(ActionEvent actionEvent) {

			// Updates the log
			AcideLog.getLog().info("1043");
			
			if (!_spacesTextField.getText().equals(""))
			{
			   String newTab = _spacesTextField.getText();
			   
			   AcideWorkbenchConfiguration.getInstance()
			   			.getFileEditorConfiguration().setTabSize(Integer.valueOf(newTab));
			}
			
			//Sets the current checkbox status 
			 AcideWorkbenchConfiguration.getInstance()
	   			.getFileEditorConfiguration().setSpaceSelected(_spacesCheckBox.isSelected());
			 
			// Apply the changes to the opened file editor panels
			for (int index = 0; index < AcideMainWindow.getInstance()
					.getFileEditorManager().getNumberOfFileEditorPanels(); index++) {

				// Sets the look and feel on the file editor panel
				AcideMainWindow.getInstance().getFileEditorManager()
						.getFileEditorPanelAt(index).setLookAndFeel();

				// Resets the selected file editor text edition area
				AcideMainWindow.getInstance().getFileEditorManager()
						.getSelectedFileEditorPanel().resetStyledDocument();
			}

			// Closes the window
			closeWindow();
		}
	}
	
	/**
	 * ACIDE - A Configurable IDE preferences window cancel
	 * button action listener.
	 * 
	 * @version 0.13
	 * @see ActionListener
	 */
	class CancelButtonAction implements ActionListener {

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.
		 * ActionEvent)
		 */
		@Override
		public void actionPerformed(ActionEvent actionEvent) {

			// Updates the log
			AcideLog.getLog().info("1044");

			// Closes the window
			closeWindow();
		}
	}
	
	/**
	 * ACIDE - A Configurable IDE preferences window escape key
	 * action.
	 * 
	 * @version 0.13
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

			// Closes the window
			closeWindow();
		}
	}
}
