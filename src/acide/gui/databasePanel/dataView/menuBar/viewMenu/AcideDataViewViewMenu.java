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
package acide.gui.databasePanel.dataView.menuBar.viewMenu;

import javax.swing.ImageIcon;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JSeparator;

import acide.configuration.menu.AcideMenuConfiguration;
import acide.gui.databasePanel.dataView.AcideDatabaseDataView;
import acide.gui.databasePanel.dataView.listeners.AcideDataViewDiscardMenuItemListener;
import acide.gui.databasePanel.dataView.listeners.AcideDataViewFilterExcludingContentMenuItemListener;
import acide.gui.databasePanel.dataView.listeners.AcideDataViewQuickFilterMenuItemListener;
import acide.gui.databasePanel.dataView.listeners.AcideDataViewShowHideColumnMenuItemListener;
import acide.gui.databasePanel.dataView.listeners.AcideDataViewSortByMenuItemListener;
import acide.language.AcideLanguageManager;

/**
 * ACIDE - A Configurable IDE view menu.
 * 
 * @version 0.11
 */
public class AcideDataViewViewMenu extends JMenu {

	/**
	 * ACIDE - A Configurable IDE view menu class serial version UID.
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * ACIDE - A Configurable IDE view menu sort by menu item name.
	 */
	public static final String SORT_BY_NAME = "Sort by";
	/**
	 * ACIDE - A Configurable IDE view menu ssort by column menu item name.
	 */
	public static final String SORT_BY_COLUMN_NAME = "Sort by Column";
	/**
	 * ACIDE - A Configurable IDE view menu filter by content menu item name.
	 */
	public static final String FILTER_BY_CONTENT_NAME = "Filter by Content";
	/**
	 * ACIDE - A Configurable IDE view menu filter excluding content name menu item name.
	 */
	public static final String FILTER_EXCLUDING_CONTENT_NAME = "Filter Excluding Content";
	
	/**
	 * ACIDE - A Configurable IDE view menu discard filter menu item name.
	 */
	public static final String DISCARD_FILTER_NAME = "Discard filter";
	
	/**
	 * ACIDE - A Configurable IDE view menu show hide column menu item name.
	 */
	public static final String SHOW_HIDE_COLUMN_NAME = "Hide/Show Columns";
		
	/**
	 * ACIDE - A Configurable IDE view menu filter by content check box menu
	 * item image icon.
	 */
	private final static ImageIcon FILTER_BY_CONTENT_IMAGE = new ImageIcon(
			"./resources/icons/database/menu/filter.png");
	/**
	 * ACIDE - A Configurable IDE view menu filter excluding content check box menu
	 * item image icon.
	 */
	private final static ImageIcon FILTER_EXCLUDING_CONTENT_IMAGE = new ImageIcon(
			"./resources/icons/menu/database/menu/filter.png");
	/**
	 * ACIDE - A Configurable IDE view menu discard filter check box menu
	 * item image icon.
	 */
	private final static ImageIcon DISCARD_FILTER_IMAGE = new ImageIcon(
			"./resources/icons/database/menu/discardFilter.png");
	
	/**
	 * ACIDE - A Configurable IDE view menu sort by menu item.
	 */
	private JMenuItem _sortByMenuItem;
	/**
	 * ACIDE - A Configurable IDE view menu sort by column menu
	 * item.
	 */
	private AcideDataViewSortByColumnMenu _sortByColumnMenuItem;
	/**
	 * ACIDE - A Configurable IDE view menu filter by content menu
	 * item.
	 */
	private JMenuItem _filterByContentMenuItem;
	/**
	 * ACIDE - A Configurable IDE view menu filter excluding content menu
	 * item.
	 */
	private JMenuItem _filterExcludingContentMenuItem;
	/**
	 * ACIDE - A Configurable IDE view menu discard filter menu
	 * item.
	 */
	private JMenuItem _discarFilterMenuItem;
	/**
	 * ACIDE - A Configurable IDE view menu show hide columns menu
	 * item.
	 */
	private JMenuItem _showHideColumnsMenuItem;
	
	/**
	 * ACIDE - DataView owner of the ViewMenu
	 */
	private AcideDatabaseDataView _table;

	/**
	 * Creates a new ACIDE - A Configurable IDE view menu.
	 */
	public AcideDataViewViewMenu(AcideDatabaseDataView table) {

		this._table = table;
		// Builds the menu components
		buildComponents();

		// Adds the components to the menu
		addComponents();

		// Sets the text of the view menu components
		setTextOfMenuComponents();	
	}

	/**
	 * Adds the components to the ACIDE - A Configurable IDE view menu.
	 */
	private void addComponents() {

		add(_sortByMenuItem);

		add(_sortByColumnMenuItem);
		
		add(new JSeparator());

		add(_filterByContentMenuItem);
	
		add(_filterExcludingContentMenuItem);
		
		add(_discarFilterMenuItem);
		
		add(new JSeparator());
		
		add(_showHideColumnsMenuItem);
		
	}

	/**
	 * Builds the ACIDE - A Configurable IDE view menu components.
	 */
	private void buildComponents() {

		_sortByMenuItem = new JMenuItem();

		_sortByMenuItem.setName(SORT_BY_NAME);

		_sortByColumnMenuItem = new AcideDataViewSortByColumnMenu(_table);

		_sortByColumnMenuItem.setName(SORT_BY_COLUMN_NAME);

		_filterByContentMenuItem = new JMenuItem(FILTER_BY_CONTENT_IMAGE);

		_filterByContentMenuItem.setName(FILTER_BY_CONTENT_NAME);

		_filterExcludingContentMenuItem = new JMenuItem(FILTER_EXCLUDING_CONTENT_IMAGE);

		_filterExcludingContentMenuItem.setName(FILTER_EXCLUDING_CONTENT_NAME);
		
		_discarFilterMenuItem = new JMenuItem(DISCARD_FILTER_IMAGE);

		_discarFilterMenuItem.setName(DISCARD_FILTER_NAME);
		
		_showHideColumnsMenuItem = new JMenuItem();

		_showHideColumnsMenuItem.setName(SHOW_HIDE_COLUMN_NAME);

	
	}

	/**
	 * Sets the text of the ACIDE - A Configurable IDE view menu components with
	 * the labels in the selected language to display.
	 */
	public void setTextOfMenuComponents() {

		_sortByMenuItem.setText(AcideLanguageManager.getInstance().getLabels().getString("s2081"));

		_sortByColumnMenuItem.setText(AcideLanguageManager.getInstance().getLabels().getString("s2082"));

		_filterByContentMenuItem.setText(AcideLanguageManager.getInstance().getLabels().getString("s2085"));
		
		_filterExcludingContentMenuItem.setText(AcideLanguageManager.getInstance().getLabels().getString("s2086"));
		
		_discarFilterMenuItem.setText(AcideLanguageManager.getInstance().getLabels().getString("s2087"));
		
		_showHideColumnsMenuItem.setText(AcideLanguageManager.getInstance().getLabels().getString("s2088"));

		_sortByMenuItem.setEnabled(true);

		_sortByColumnMenuItem.setEnabled(true);		

		_filterByContentMenuItem.setEnabled(true);

		_filterExcludingContentMenuItem.setEnabled(true);

		_discarFilterMenuItem.setEnabled(true);		

		_showHideColumnsMenuItem.setEnabled(true);
		
		_sortByColumnMenuItem.setTextOfMenuComponents();
	}

	/**
	 * Updates the ACIDE - A Configurable IDE view menu components visibiliy
	 * with the menu configuration.
	 */
	public void updateComponentsVisibility() {

		_sortByMenuItem.setVisible(AcideMenuConfiguration.getInstance().getIsDisplayed(SORT_BY_NAME));

		_sortByColumnMenuItem.setVisible(AcideMenuConfiguration.getInstance().getIsDisplayed(SORT_BY_COLUMN_NAME));

		_filterByContentMenuItem.setVisible(AcideMenuConfiguration.getInstance().getIsDisplayed(FILTER_BY_CONTENT_NAME));
		
		_filterExcludingContentMenuItem.setVisible(AcideMenuConfiguration.getInstance().getIsDisplayed(FILTER_EXCLUDING_CONTENT_NAME));
		
		_showHideColumnsMenuItem.setVisible(AcideMenuConfiguration.getInstance().getIsDisplayed(SHOW_HIDE_COLUMN_NAME));
	}

	/**
	 * Sets the ACIDE - A Configurable IDE view menu menu item listeners.
	 */
	public void setListeners() {
		
		_sortByMenuItem.addActionListener(new AcideDataViewSortByMenuItemListener(_table));

		_filterByContentMenuItem.addActionListener(new AcideDataViewQuickFilterMenuItemListener(_table));
		
		_filterExcludingContentMenuItem.addActionListener(new AcideDataViewFilterExcludingContentMenuItemListener(_table));
		
		_discarFilterMenuItem.addActionListener(new AcideDataViewDiscardMenuItemListener(_table));
		
		_showHideColumnsMenuItem.addActionListener(new AcideDataViewShowHideColumnMenuItemListener(_table));
	}

	/**
	 * Returns the ACIDE - A Configurable IDE view menu sort by menu item.
	 * 
	 * @return the ACIDE - A Configurable IDE view menu sort by menu item.
	 */
	public JMenuItem getSortByMenuItem() {
		return _sortByMenuItem;
	}

	/**
	 * Returns the ACIDE - A Configurable IDE view menu sort by column menu item.
	 * 
	 * @return the ACIDE - A Configurable IDE view menu sort by column menu item.
	 */
	public JMenuItem getSortByColumnMenuItem() {
		return _sortByColumnMenuItem;
	}

	/**
	 * Returns the ACIDE - A Configurable IDE view menu filter by content menu item.
	 * 
	 * @return the ACIDE - A Configurable IDE view menu filter by content menu item.
	 */
	public JMenuItem getFilterByContentMenuItem() {
		return _filterByContentMenuItem;
	}
	/**
	 * Returns the ACIDE - A Configurable IDE view menu filter excluding content menu item.
	 * 
	 * @return the ACIDE - A Configurable IDE view menu filter excluding content menu item.
	 */
	public JMenuItem getFilterExcludingContentBoxMenuItem() {
		return _filterExcludingContentMenuItem;
	}
	
	/**
	 * Returns the ACIDE - A Configurable IDE view menu discard filter menu item.
	 * 
	 * @return the ACIDE - A Configurable IDE view menu discard filter menu item.
	 */
	public JMenuItem getDiscardFilterMenuItem() {
		return _discarFilterMenuItem;
	}
	
	/**
	 * Returns the ACIDE - A Configurable IDE view menu hide/show columns menu item.
	 * 
	 * @return the ACIDE - A Configurable IDE view menu hide/show columns menu item.
	 */
	public JMenuItem getHideShowColumnsMenuItem() {
		return _showHideColumnsMenuItem;
	}
	
	@Override
	public void setEnabled(boolean enabled){
		_discarFilterMenuItem.setEnabled(enabled);
		_filterByContentMenuItem.setEnabled(enabled);
		_filterExcludingContentMenuItem.setEnabled(enabled);
		_showHideColumnsMenuItem.setEnabled(enabled);
		_sortByColumnMenuItem.setEnabled(enabled);
		_sortByMenuItem.setEnabled(enabled);
	}
}