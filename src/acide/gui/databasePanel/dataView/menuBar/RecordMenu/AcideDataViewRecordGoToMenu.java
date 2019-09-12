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
package acide.gui.databasePanel.dataView.menuBar.RecordMenu;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.ImageIcon;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;

import acide.configuration.menu.AcideMenuConfiguration;
import acide.gui.databasePanel.dataView.AcideDatabaseDataView;
import acide.language.AcideLanguageManager;

/**
 * ACIDE - A Configurable IDE file menu.
 * 
 * @version 0.11
 * @see JMenu
 */
public class AcideDataViewRecordGoToMenu extends JMenu {

	/**
	 * ACIDE - A Configurable IDE file menu class serial version UID.
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * ACIDE - A Configurable IDE file menu new file menu item name.
	 */
	public final static String FIRST_NAME = "First";
	/**
	 * ACIDE - A Configurable IDE file menu open file menu item name.
	 */
	public final static String LAST_NAME = "Last";
	/**
	 * ACIDE - A Configurable IDE file menu open recent files menu item name.
	 */
	public final static String NEXT_NAME = "Next";
	/**
	 * ACIDE - A Configurable IDE file menu open all files menu item name.
	 */
	public final static String PREVIOUS_NAME = "Previous";
	/**
	 * ACIDE - A Configurable IDE file menu close file menu item name.
	 */
	public final static String GO_TO_RECORD_NAME = "Go to record";
	/**
	 * ACIDE - A Configurable IDE file menu new file menu item image icon.
	 */
	private final static ImageIcon FIRST_IMAGE = new ImageIcon(
			"./resources/icons/database/menu/first.png");
	/**
	 * ACIDE - A Configurable IDE file menu new file menu item.
	 */
	private JMenuItem _firstMenuItem;
	/**
	 * ACIDE - A Configurable IDE file menu open file menu item.
	 */
	private JMenuItem _lastMenuItem;
	/**
	 * ACIDE - A Configurable IDE file menu open recent files menu.
	 */
	private JMenuItem _nextFilesMenu;
	/**
	 * ACIDE - A Configurable IDE file menu open all files menu item.
	 */
	private JMenuItem _goToRecordMenuItem;
	/**
	 * ACIDE - A Configurable IDE file menu close file menu item.
	 */
	private JMenuItem _previousMenuItem;
	
	/**
	 * ACIDE - DataView to go to
	 */
	private AcideDatabaseDataView _table;

	/**
	 * Creates a new ACIDE - A Configurable IDE file menu.
	 */
	public AcideDataViewRecordGoToMenu(AcideDatabaseDataView table) {

		this._table = table;
		// Builds the menu components
		buildComponents();

		// Adds the components to the menu
		addComponents();

		// Sets the text of the file menu components
		setTextOfMenuComponents();
		
		setListeners();
	}

	/**
	 * Adds the components to the ACIDE - A Configurable IDE file menu.
	 */
	private void addComponents() {

		add(_firstMenuItem);

		add(_lastMenuItem);

		add(_nextFilesMenu);
		
		add(_previousMenuItem);

		add(_goToRecordMenuItem);
	}

	/**
	 * Builds the ACIDE - A Configurable IDE file menu components.
	 */
	private void buildComponents() {

		// Creates the new file menu item
		_firstMenuItem = new JMenuItem(FIRST_IMAGE);

		// Sets the new file menu item name
		_firstMenuItem.setName(FIRST_NAME);

		// Creates the open file menu item
		_lastMenuItem = new JMenuItem(/*LAST_IMAGE*/);

		// Sets the open file menu item name
		_lastMenuItem.setName(LAST_NAME);

		// Creates the open recent files menu item
		_nextFilesMenu = new JMenuItem(/*NEXT_IMAGE*/);

		// Sets the open recent files menu item name
		_nextFilesMenu.setName(NEXT_NAME);

		// Creates the open all files menu item
		_goToRecordMenuItem = new JMenuItem(/*GO_TO_IMAGE*/);

		// Sets the open all files menu item name
		_goToRecordMenuItem.setName(GO_TO_RECORD_NAME);

		// Creates the close file menu item
		_previousMenuItem = new JMenuItem(/*PREVIOUS_IMAGE*/);

		// Sets the close file menu item name
		_previousMenuItem.setName(PREVIOUS_NAME);
	}

	/**
	 * Sets the text of the ACIDE - A Configurable IDE file menu components with
	 * the labels in the selected language to display.
	 */
	public void setTextOfMenuComponents() {

		_firstMenuItem.setText(AcideLanguageManager.getInstance().getLabels()
				.getString("s2074"));
		
		_firstMenuItem.setAccelerator(KeyStroke.getKeyStroke(36,
				ActionEvent.CTRL_MASK));

		_lastMenuItem.setText(AcideLanguageManager.getInstance()
				.getLabels().getString("s2075"));
		
		_lastMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_END,
				ActionEvent.CTRL_MASK));

		_nextFilesMenu.setText(AcideLanguageManager.getInstance()
				.getLabels().getString("s2076"));
//		_nextFilesMenu.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_DOWN,
//				0));

		_goToRecordMenuItem.setText(AcideLanguageManager.getInstance()
				.getLabels().getString("s2078"));

		_previousMenuItem.setText(AcideLanguageManager.getInstance()
				.getLabels().getString("s2077"));
//		_previousMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_UP,
//				0));

	}

	/**
	 * Updates the ACIDE - A Configurable IDE file menu components visibility
	 * with the menu configuration.
	 */
	public void updateComponentsVisibility() {

		_firstMenuItem.setVisible(AcideMenuConfiguration.getInstance()
				.getIsDisplayed(FIRST_NAME));

		_lastMenuItem.setVisible(AcideMenuConfiguration.getInstance()
				.getIsDisplayed(LAST_NAME));

		_nextFilesMenu.setVisible(AcideMenuConfiguration.getInstance()
				.getIsDisplayed(NEXT_NAME));

		_goToRecordMenuItem.setVisible(AcideMenuConfiguration.getInstance()
				.getIsDisplayed(GO_TO_RECORD_NAME));


		_previousMenuItem.setVisible(AcideMenuConfiguration.getInstance()
				.getIsDisplayed(PREVIOUS_NAME ));
	}

	/**
	 * Sets the ACIDE - A Configurable IDE file menu item listeners.
	 */
	public void setListeners() {

		_firstMenuItem.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				_table.firstRecord();				
			}
		});

		_lastMenuItem.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				_table.lastRecord();				
			}
		});

		_nextFilesMenu.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				_table.nextRecord();				
			}
		});

		_previousMenuItem.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				_table.previousRecord();				
			}
		});
		_goToRecordMenuItem.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				_table.setAlwaysOnTop(false);
				String s = (String)JOptionPane.showInputDialog(null,
						AcideLanguageManager.getInstance().getLabels().getString("s2154"),
						AcideLanguageManager.getInstance().getLabels().getString("s2132"),JOptionPane.PLAIN_MESSAGE,null,null,"");
				if ((s != null) && (s.length() > 0)) {
					_table.goToRecord(Integer.valueOf(s)-1);
				}
				_table.setAlwaysOnTop(true);
			}
		});
	}

	

	/**
	 * Enables the ACIDE - A Configurable IDE file menu.
	 */
	public void enableMenu() {

		// Enables the close file menu item
		_previousMenuItem.setEnabled(true);

	}

	/**
	 * Disables the ACIDE - A Configurable IDE file menu.
	 */
	public void disableMenu() {

		// Disables the close file menu item
		_previousMenuItem.setEnabled(false);
	}

	/**
	 * Returns the ACIDE - A Configurable IDE file menu new file menu item.
	 * 
	 * @return the ACIDE - A Configurable IDE file menu new file menu item
	 */
	public JMenuItem getNewFileMenuItem() {
		return _firstMenuItem;
	}

	/**
	 * Returns the ACIDE - A Configurable IDE file menu close file menu item.
	 * 
	 * @return the ACIDE - A Configurable IDE file menu close file menu item
	 */
	public JMenuItem getCloseFileMenuItem() {
		return _previousMenuItem;
	}

	/**
	 * Returns the ACIDE - A Configurable IDE file menu open file menu item.
	 * 
	 * @return the ACIDE - A Configurable IDE file menu open file menu item
	 */
	public JMenuItem getOpenFileMenuItem() {
		return _lastMenuItem;
	}

	/**
	 * Returns the ACIDE - A Configurable IDE file menu open recent files menu.
	 * 
	 * @return the ACIDE - A Configurable IDE file menu open recent files menu
	 */
	public JMenuItem getNextMenu() {
		return _nextFilesMenu;
	}

	/**
	 * Returns the ACIDE - A Configurable IDE file menu open all files menu
	 * item.
	 * 
	 * @return the ACIDE - A Configurable IDE file menu open all files menu item
	 */
	public JMenuItem getOpenAllFilesMenuItem() {
		return _goToRecordMenuItem;
	}
	
	public void setEnabled(boolean enabled){
		_firstMenuItem.setEnabled(enabled);
		_goToRecordMenuItem.setEnabled(enabled);
		_lastMenuItem.setEnabled(enabled);
		_nextFilesMenu.setEnabled(enabled);
		_previousMenuItem.setEnabled(enabled);
	}
}
