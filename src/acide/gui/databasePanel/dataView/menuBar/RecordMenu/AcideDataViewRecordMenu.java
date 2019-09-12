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
import javax.swing.JSeparator;
import javax.swing.KeyStroke;

import acide.configuration.menu.AcideMenuConfiguration;
import acide.gui.databasePanel.dataView.AcideDatabaseDataView;
import acide.gui.menuBar.fileMenu.listeners.AcideNewFileMenuItemListener;
import acide.language.AcideLanguageManager;

/**
 * ACIDE - A Configurable IDE record menu.
 * 
 * @version 0.11
 * @see JMenu
 */
public class AcideDataViewRecordMenu extends JMenu {

	/**
	 * ACIDE - A Configurable IDE file menu class serial version UID.
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * ACIDE - A Configurable IDE file menu new menu item name.
	 */
	public final static String NEW_NAME = "New";
	/**
	 * ACIDE - A Configurable IDE file menu delete menu item name.
	 */
	public final static String DELETE_NAME = "Delete";
	/**
	 * ACIDE - A Configurable IDE file menu refresh menu item name.
	 */
	public final static String REFRESH_NAME = "Refresh";
	/**
	 * ACIDE - A Configurable IDE file menu select record menu item name.
	 */
	public final static String SELECT_RECORD_NAME = "Select Record";
	/**
	 * ACIDE - A Configurable IDE file menu select all menu item name.
	 */
	public final static String SELECT_ALL_NAME = "Select All";
	/**
	 * ACIDE - A Configurable IDE file menu go to menu item name.
	 */
	public final static String GO_TO_NAME = "Go To";
	
	/**
	 * ACIDE - A Configurable IDE file menu new menu item image icon.
	 */
	private final static ImageIcon NEW_IMAGE = new ImageIcon(
			"./resources/icons/database/menu/new.png");
	/**
	 * ACIDE - A Configurable IDE file menu delete menu item image icon.
	 */
	private final static ImageIcon DELETE_IMAGE = new ImageIcon(
			"./resources/icons/database/menu/delete.png");
	/**
	 * ACIDE - A Configurable IDE file menu refresh menu item image icon.
	 */
	private final static ImageIcon REFRESH_IMAGE = new ImageIcon(
			"./resources/icons/database/menu/refresh.png");

	/**
	 * ACIDE - A Configurable IDE file menu new menu item.
	 */
	private JMenuItem _newMenuItem;
	/**
	 * ACIDE - A Configurable IDE file menu delete menu item.
	 */
	private JMenuItem _deleteMenuItem;
	/**
	 * ACIDE - A Configurable IDE file menu refresh menu.
	 */
	private JMenuItem _refreshMenu;
	/**
	 * ACIDE - A Configurable IDE file menu select record menu item.
	 */
	private JMenuItem _selectRecordMenuItem;
	/**
	 * ACIDE - A Configurable IDE file menu select all menu item.
	 */
	private JMenuItem _selectAllMenuItem;
	/**
	 * ACIDE - A Configurable IDE file menu go to menu item.
	 */
	private AcideDataViewRecordGoToMenu _goToMenuItem;
	/**
	 * ACIDE - A Configurable IDE file menu new delete separator.
	 */
	private JSeparator _NewDeleteSeparator;
	/**
	 * ACIDE - A Configurable IDE file menu refresh separator.
	 */
	private JSeparator _refreshSeparator;
	/**
	 * ACIDE - A Configurable IDE file menu select separator.
	 */
	private JSeparator _selectSeparator;
	
	
	private AcideDatabaseDataView _table;

	/**
	 * Creates a new ACIDE - A Configurable IDE record menu.
	 * @param table 
	 */
	public AcideDataViewRecordMenu(AcideDatabaseDataView table) {

		this._table = table;
		// Builds the menu components
		buildComponents();

		// Adds the components to the menu
		addComponents();

		// Sets the text of the file menu components
		setTextOfMenuComponents();
	}

	/**
	 * Adds the components to the ACIDE - A Configurable IDE record menu.
	 */
	private void addComponents() {

		add(_newMenuItem);

		add(_deleteMenuItem);
		
		add(_NewDeleteSeparator);

		add(_refreshMenu);
		
		add(_refreshSeparator);
		
		add(_goToMenuItem);
		
		add(_selectSeparator);

		add(_selectRecordMenuItem);

		add(_selectAllMenuItem);
	}

	/**
	 * Builds the ACIDE - A Configurable IDE record menu components.
	 */
	private void buildComponents() {

		_newMenuItem = new JMenuItem(NEW_IMAGE);

		_newMenuItem.setName(NEW_NAME);

		_deleteMenuItem = new JMenuItem(DELETE_IMAGE);

		_deleteMenuItem.setName(DELETE_NAME);

		_refreshMenu = new JMenuItem(REFRESH_IMAGE);

		_refreshMenu.setName(REFRESH_NAME);

		_selectRecordMenuItem = new JMenuItem();

		_selectRecordMenuItem.setName(SELECT_RECORD_NAME);

		_selectAllMenuItem = new JMenuItem();

		_selectAllMenuItem.setName(SELECT_ALL_NAME);

		_goToMenuItem = new AcideDataViewRecordGoToMenu(_table);

		_goToMenuItem.setName(GO_TO_NAME);

		_NewDeleteSeparator = new JSeparator();

		_refreshSeparator = new JSeparator();

		_selectSeparator = new JSeparator();
	}

	/**
	 * Sets the text of the ACIDE - A Configurable IDE record menu components with
	 * the labels in the selected language to display.
	 */
	public void setTextOfMenuComponents() {

		_newMenuItem.setText(AcideLanguageManager.getInstance().getLabels().getString("s8"));

		_deleteMenuItem.setText(AcideLanguageManager.getInstance().getLabels().getString("s387"));

		_refreshMenu.setText(AcideLanguageManager.getInstance().getLabels().getString("s2044"));
		
		_refreshMenu.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F5,0));

		_selectRecordMenuItem.setText(AcideLanguageManager.getInstance().getLabels().getString("s2079"));

		_selectAllMenuItem.setText(AcideLanguageManager.getInstance().getLabels().getString("s515"));

		_goToMenuItem.setText(AcideLanguageManager.getInstance().getLabels().getString("s2073"));

		_newMenuItem.setEnabled(true);
		
		_deleteMenuItem.setEnabled(true);
		
		_refreshMenu.setEnabled(true);
		
		_selectRecordMenuItem.setEnabled(true);

		_selectAllMenuItem.setEnabled(true);

		_goToMenuItem.setEnabled(true);
		
		_goToMenuItem.setTextOfMenuComponents();
	}

	/**
	 * Updates the ACIDE - A Configurable IDE record menu components visibility
	 * with the menu configuration.
	 */
	public void updateComponentsVisibility() {

		// Sets the new file menu item as visible or not visible
		_newMenuItem.setVisible(AcideMenuConfiguration.getInstance()
				.getIsDisplayed(NEW_NAME));

		// Sets the open file menu item as visible or not visible
		_deleteMenuItem.setVisible(AcideMenuConfiguration.getInstance()
				.getIsDisplayed(DELETE_NAME));

		// Sets the open recent files menu item as visible or not visible
		_refreshMenu.setVisible(AcideMenuConfiguration.getInstance()
				.getIsDisplayed(REFRESH_NAME));

		// Sets the open all files menu item as visible or not visible
		_selectRecordMenuItem.setVisible(AcideMenuConfiguration.getInstance()
				.getIsDisplayed(SELECT_RECORD_NAME));

		// Sets the close file menu item as visible or not visible
		_selectAllMenuItem.setVisible(AcideMenuConfiguration.getInstance()
				.getIsDisplayed(SELECT_ALL_NAME));

		// Sets the close all files menu item as visible or not visible
		_goToMenuItem.setVisible(AcideMenuConfiguration.getInstance()
				.getIsDisplayed(GO_TO_NAME));

		// Sets the close all files save file separator
		// to visible or not visible
		_NewDeleteSeparator.setVisible((AcideMenuConfiguration
				.getInstance().getIsDisplayed(NEW_NAME)
				|| AcideMenuConfiguration.getInstance().getIsDisplayed(
						DELETE_NAME)
				|| AcideMenuConfiguration.getInstance().getIsDisplayed(
						SELECT_RECORD_NAME) || AcideMenuConfiguration
				.getInstance().getIsDisplayed(SELECT_ALL_NAME)));
		

		// Sets the save all files print file separator to visible or not
		// visible
		_refreshSeparator.setVisible((AcideMenuConfiguration
				.getInstance().getIsDisplayed(NEW_NAME)
				|| AcideMenuConfiguration.getInstance().getIsDisplayed(
						DELETE_NAME)
				|| AcideMenuConfiguration.getInstance().getIsDisplayed(
						SELECT_RECORD_NAME)
				|| AcideMenuConfiguration.getInstance().getIsDisplayed(
						SELECT_ALL_NAME)));

		// Sets the print file exit separator to visible or not visible
		_selectSeparator.setVisible((AcideMenuConfiguration
				.getInstance().getIsDisplayed(NEW_NAME)				
				|| AcideMenuConfiguration.getInstance().getIsDisplayed(
						SELECT_RECORD_NAME) || AcideMenuConfiguration
				.getInstance().getIsDisplayed(SELECT_ALL_NAME)));
	}

	/**
	 * Sets the ACIDE - A Configurable IDE record menu item listeners.
	 */
	public void setListeners() {

		_newMenuItem.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				int row= _table.getTable().getRowCount()-1;
				_table.getTable().editCellAt(row, 1);
				_table.getTable().getEditorComponent().requestFocus();
				
			}
		});

		_deleteMenuItem.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				_table.deleteRow();
			}
		});
		
		_refreshMenu.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				_table.refresh();				
			}
		});

		_selectRecordMenuItem.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				_table.selectCurrentRecord();
				
			}
		});

		_selectAllMenuItem.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				_table.selectAllRecords();
				
			}
		});		
	}


	/**
	 * Enables the ACIDE - A Configurable IDE record menu.
	 */
	public void enableMenu() {

		_newMenuItem.setEnabled(true);
		
		_deleteMenuItem.setEnabled(true);
		
		_refreshMenu.setEnabled(true);
		
		_selectRecordMenuItem.setEnabled(true);

		_selectAllMenuItem.setEnabled(true);

		_goToMenuItem.setEnabled(true);

	}

	/**
	 * Disables the ACIDE - A Configurable IDE record menu.
	 */
	public void disableMenu() {
		
		_newMenuItem.setEnabled(false);
		
		_deleteMenuItem.setEnabled(false);
		
		_refreshMenu.setEnabled(false);
		
		_selectRecordMenuItem.setEnabled(false);

		_selectAllMenuItem.setEnabled(false);

		_goToMenuItem.setEnabled(false);
	}

	/**
	 * Returns the ACIDE - A Configurable IDE record menu new menu item.
	 * 
	 * @return the ACIDE - A Configurable IDE record menu new menu item
	 */
	public JMenuItem getNewMenuItem() {
		return _newMenuItem;
	}

	/**
	 * Returns the ACIDE - A Configurable IDE record menu go to menu
	 * item.
	 * 
	 * @return the ACIDE - A Configurable IDE record menu go to menu
	 *         item
	 */
	public JMenuItem getGoToMenuItem() {
		return _goToMenuItem;
	}

	/**
	 * Returns the ACIDE - A Configurable IDE record menu select all menu item.
	 * 
	 * @return the ACIDE - A Configurable IDE record menu select all menu item
	 */
	public JMenuItem getSelectAllMenuItem() {
		return _selectAllMenuItem;
	}

	/**
	 * Returns the ACIDE - A Configurable IDE record menu delete menu item.
	 * 
	 * @return the ACIDE - A Configurable IDE record menu delete menu item
	 */
	public JMenuItem getDeleteMenuItem() {
		return _deleteMenuItem;
	}

	/**
	 * Returns the ACIDE - A Configurable IDE record menu refresh menu.
	 * 
	 * @return the ACIDE - A Configurable IDE record menu refresh menu
	 */
	public JMenuItem getRefreshFilesMenu() {
		return _refreshMenu;
	}

	/**
	 * Returns the ACIDE - A Configurable IDE record menu select record  menu
	 * item.
	 * 
	 * @return the ACIDE - A Configurable IDE record menu select record menu item
	 */
	public JMenuItem getSelectRecordMenuItem() {
		return _selectRecordMenuItem;
	}
	
	@Override
	public void setEnabled(boolean enabled){
		_deleteMenuItem.setEnabled(enabled);
		_goToMenuItem.setEnabled(enabled);
		_newMenuItem.setEnabled(enabled);
		_refreshMenu.setEnabled(enabled);
		_selectAllMenuItem.setEnabled(enabled);
		_selectRecordMenuItem.setEnabled(enabled);		
	}
	
	public void isReadOnly(boolean b){
		_deleteMenuItem.setEnabled(b);
		_newMenuItem.setEnabled(b);
		_deleteMenuItem.setVisible(b);
		_newMenuItem.setVisible(b);
	}
}
