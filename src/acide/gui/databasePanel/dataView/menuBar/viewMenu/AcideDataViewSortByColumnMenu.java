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
package acide.gui.databasePanel.dataView.menuBar.viewMenu;

import javax.swing.ImageIcon;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

import acide.configuration.menu.AcideMenuConfiguration;
import acide.gui.databasePanel.dataView.AcideDatabaseDataView;
import acide.gui.databasePanel.dataView.listeners.AcideDataViewAscendingMenuItemListener;
import acide.gui.databasePanel.dataView.listeners.AcideDataViewDescendingMenuItemListener;
import acide.language.AcideLanguageManager;

/**
 * ACIDE - A Configurable IDE file menu.
 * 
 * @version 0.11
 * @see JMenu
 */
public class AcideDataViewSortByColumnMenu extends JMenu {

	/**
	 * ACIDE - A Configurable IDE file menu class serial version UID.
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * ACIDE - A Configurable IDE file menu new file menu item name.
	 */
	public final static String ASCENDING_NAME = "New";
	/**
	 * ACIDE - A Configurable IDE file menu open file menu item name.
	 */
	public final static String DESCENDING_NAME = "Open File";
	/**
	 * ACIDE - A Configurable IDE file menu new file menu item image icon.
	 */
	private final static ImageIcon ASCENDING_IMAGE = new ImageIcon(
			"./resources/icons/database/menu/ascending.png");
	/**
	 * ACIDE - A Configurable IDE file menu open file menu item image icon.
	 */
	private final static ImageIcon DESCENDING_IMAGE = new ImageIcon(
			"./resources/icons/database/menu/descending.png");
	/**
	 * ACIDE - A Configurable IDE file menu new file menu item.
	 */
	private JMenuItem _ascendingMenuItem;
	/**
	 * ACIDE - A Configurable IDE file menu open file menu item.
	 */
	private JMenuItem _descendingMenuItem;
	
	/**
	 * ACIDE - DataView to sort by columns
	 */
	private AcideDatabaseDataView _table;

	/**
	 * Creates a new ACIDE - A Configurable IDE file menu.
	 */
	public AcideDataViewSortByColumnMenu(AcideDatabaseDataView table) {

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
	 * Adds the components to the ACIDE - A Configurable IDE sort by column menu.
	 */
	private void addComponents() {

		add(_ascendingMenuItem);

		add(_descendingMenuItem);
	}

	/**
	 * Builds the ACIDE - A Configurable IDE sort by column menu components.
	 */
	private void buildComponents() {

		_ascendingMenuItem = new JMenuItem(ASCENDING_IMAGE);

		_ascendingMenuItem.setName(ASCENDING_NAME);

		_descendingMenuItem = new JMenuItem(DESCENDING_IMAGE);

		_descendingMenuItem.setName(DESCENDING_NAME);
	}

	/**
	 * Sets the text of the ACIDE - A Configurable IDE sort by column menu components with
	 * the labels in the selected language to display.
	 */
	public void setTextOfMenuComponents() {

		_ascendingMenuItem.setText(AcideLanguageManager.getInstance().getLabels()
				.getString("s2083"));

		_descendingMenuItem.setText(AcideLanguageManager.getInstance()
				.getLabels().getString("s2084"));

		_ascendingMenuItem.setEnabled(true);

		_descendingMenuItem.setEnabled(true);
	}

	/**
	 * Updates the ACIDE - A Configurable IDE sort by column menu components visibility
	 * with the menu configuration.
	 */
	public void updateComponentsVisibility() {

		_ascendingMenuItem.setVisible(AcideMenuConfiguration.getInstance()
				.getIsDisplayed(ASCENDING_NAME));

		_descendingMenuItem.setVisible(AcideMenuConfiguration.getInstance()
				.getIsDisplayed(DESCENDING_NAME));
	}

	/**
	 * Sets the ACIDE - A Configurable IDE sort by column menu item listeners.
	 */
	public void setListeners() {
		_ascendingMenuItem.addActionListener(new AcideDataViewAscendingMenuItemListener(_table));
		_descendingMenuItem.addActionListener(new AcideDataViewDescendingMenuItemListener(_table));
	}

	

	/**
	 * Enables the ACIDE - A Configurable IDE sort by column menu.
	 */
	public void enableMenu() {

		_ascendingMenuItem.setEnabled(true);

		_descendingMenuItem.setEnabled(true);

	}

	/**
	 * Disables the ACIDE - A Configurable IDE sort by column menu.
	 */
	public void disableMenu() {

		_ascendingMenuItem.setEnabled(false);

		_descendingMenuItem.setEnabled(false);
	}

	/**
	 * Returns the ACIDE - A Configurable IDE sort by column menu ascending menu item.
	 * 
	 * @return the ACIDE - A Configurable IDE sort by column menu ascending menu item
	 */
	public JMenuItem getAscendingMenuItem() {
		return _ascendingMenuItem;
	}


	/**
	 * Returns the ACIDE - A Configurable IDE sort by column menu descending menu item.
	 * 
	 * @return the ACIDE - A Configurable IDE sort by column menu descending menu item
	 */
	public JMenuItem getDescendingMenuItem() {
		return _descendingMenuItem;
	}
	
	public void setEnabled(boolean enabled){
		_ascendingMenuItem.setEnabled(enabled);
		_descendingMenuItem.setEnabled(enabled);
	}
}
