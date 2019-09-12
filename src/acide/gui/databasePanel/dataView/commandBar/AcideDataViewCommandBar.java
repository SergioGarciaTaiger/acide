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

import java.awt.Component;
import java.awt.Dimension;
import java.util.ArrayList;

import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JMenu;

import acide.gui.databasePanel.dataView.AcideDatabaseDataView;
import acide.gui.databasePanel.dataView.listeners.AcideDataViewAscendingMenuItemListener;
import acide.gui.databasePanel.dataView.listeners.AcideDataViewCopyMenuItemListener;
import acide.gui.databasePanel.dataView.listeners.AcideDataViewCutMenuItemListener;
import acide.gui.databasePanel.dataView.listeners.AcideDataViewDescendingMenuItemListener;
import acide.gui.databasePanel.dataView.listeners.AcideDataViewDiscardMenuItemListener;
import acide.gui.databasePanel.dataView.listeners.AcideDataViewPasteMenuItemListener;
import acide.gui.databasePanel.dataView.listeners.AcideDataViewQuickFilterMenuItemListener;
import acide.gui.databasePanel.dataView.listeners.AcideDataViewSearchMenuItemListener;
import acide.language.AcideLanguageManager;
import acide.log.AcideLog;

/**
 * ACIDE - A Configurable IDE command bar.
 * 
 * @version 0.13
 * @see JMenu
 */
public class AcideDataViewCommandBar extends ArrayList<Component> {

	/**
	 * ACIDE - A Configurable IDE command bar class serial version UID.
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * ACIDE - A Configurable IDE command bar sort ascending menu item image icon.
	 */
	private final static ImageIcon ASCENDING_IMAGE = new ImageIcon(
			"./resources/icons/database/menu/ascending.png");
	/**
	 * ACIDE - A Configurable IDE command bar sort descending menu item image icon.
	 */
	private final static ImageIcon DESCENDING_IMAGE = new ImageIcon(
			"./resources/icons/database/menu/descending.png");
	/**
	 * ACIDE - A Configurable IDE command bar copy menu item image icon.
	 */
	private final static ImageIcon COPY_IMAGE = new ImageIcon(
			"./resources/icons/menu/edit/copy.png");
	/**
	 * ACIDE - A Configurable IDE command bar paste menu item image icon.
	 */
	private final static ImageIcon PASTE_IMAGE = new ImageIcon(
			"./resources/icons/menu/edit/paste.png");
	/**
	 * ACIDE - A Configurable IDE command bar cut menu item image icon.
	 */
	private final static ImageIcon CUT_IMAGE = new ImageIcon(
			"./resources/icons/menu/edit/cut.png");
	/**
	 * ACIDE - A Configurable IDE command bar search menu item image icon.
	 */
	private final static ImageIcon SEARCH_IMAGE = new ImageIcon(
			"./resources/icons/menu/edit/search.png");
	/**
	 * ACIDE - A Configurable IDE command bar quick filter menu item image icon.
	 */
	private final static ImageIcon QUICK_FILTER_IMAGE = new ImageIcon(
			"./resources/icons/database/menu/filter.png");
	/**
	 * ACIDE - A Configurable IDE command bar discard filter menu item image icon.
	 */
	private final static ImageIcon DISCARD_FILTER_IMAGE = new ImageIcon(
			"./resources/icons/database/menu/discardFilter.png");
	/**
	 * ACIDE - A Configurable IDE command bar ascending menu item.
	 */
	private JButton _ascendingMenuItem;
	/**
	 * ACIDE - A Configurable IDE command bar descending menu item.
	 */
	private JButton _descendingMenuItem;
	/**
	 * ACIDE - A Configurable IDE command bar search menu item.
	 */
	private JButton _searchMenuItem;
	/**
	 * ACIDE - A Configurable IDE command bar paste menu item.
	 */
	private JButton _pasteMenuItem;
	/**
	 * ACIDE - A Configurable IDE command bar copy menu item.
	 */
	private JButton _copyMenuItem;
	/**
	 * ACIDE - A Configurable IDE command bar cut menu item.
	 */
	private JButton _cutMenuItem;
	/**
	 * ACIDE - A Configurable IDE command bar quick filter menu item.
	 */
	private JButton _quickFilterMenuItem;
	/**
	 * ACIDE - A Configurable IDE command bar discard filter menu item.
	 */
	private JButton _discardFilterMenuItem;
	
	/**
	 * ACIDE - DataView owner of the CommandBar
	 */
	private AcideDatabaseDataView _table;
	
	public AcideDataViewCommandBar(AcideDatabaseDataView table) {
		super();
		this._table = table;
		
	}
	/**
	 * Creates a new ACIDE - A Configurable IDE command bar.
	 */
	public AcideDataViewCommandBar build() {
		
		// Updates the log
		AcideLog.getLog().info(
				AcideLanguageManager.getInstance().getLabels()
						.getString("s102"));

		// Builds the menu components
		buildComponents();
		
		setToolsTips();

		// Sets the text of the command bar components
		setListeners();
		
		// Adds the components to the menu
		addComponents();
		
		setVisibilityMenuComponents();
		// Updates the log
		AcideLog.getLog().info(
				AcideLanguageManager.getInstance().getLabels()
						.getString("s125"));

		return this;
	}

	/**
	 * Adds the components to the ACIDE - A Configurable IDE command bar.
	 */
	private void addComponents() {
		
		clear();

		add(_cutMenuItem);
		
		add(_copyMenuItem);

		add(_pasteMenuItem);

		add(Box.createRigidArea(new Dimension(7, 7)));
		
		add(_searchMenuItem);

		add(Box.createRigidArea(new Dimension(7, 7)));

		add(_ascendingMenuItem);

		add(_descendingMenuItem);

		add(Box.createRigidArea(new Dimension(7, 7)));

		add(_quickFilterMenuItem);
		
		add(_discardFilterMenuItem);
	}

	/**
	 * Builds the ACIDE - A Configurable IDE command bar components.
	 */
	private void buildComponents() {

		_ascendingMenuItem = new JButton(ASCENDING_IMAGE);

		_ascendingMenuItem.setBorderPainted(false);
		
		_ascendingMenuItem.setSize(new Dimension(7,7));
		
		_descendingMenuItem = new JButton(DESCENDING_IMAGE);
		
		_descendingMenuItem.setBorderPainted(false);
		
		_copyMenuItem = new JButton(COPY_IMAGE);
		
		_copyMenuItem.setBorderPainted(false);
		
		_pasteMenuItem = new JButton(PASTE_IMAGE);
		
		_pasteMenuItem.setBorderPainted(false);
		
		_cutMenuItem = new JButton(CUT_IMAGE);
		
		_cutMenuItem.setBorderPainted(false);
		
		_searchMenuItem = new JButton(SEARCH_IMAGE);

		_searchMenuItem.setBorderPainted(false);
		
		_quickFilterMenuItem = new JButton(QUICK_FILTER_IMAGE);

		_quickFilterMenuItem.setBorderPainted(false);
		
		_discardFilterMenuItem = new JButton(DISCARD_FILTER_IMAGE);

		_discardFilterMenuItem.setBorderPainted(false);
	}	

	
	private void setVisibilityMenuComponents() {
		_cutMenuItem.setEnabled(true);
		
		_copyMenuItem.setEnabled(true);

		_pasteMenuItem.setEnabled(true);
		
		_searchMenuItem.setEnabled(true);

		_ascendingMenuItem.setEnabled(true);

		_descendingMenuItem.setEnabled(true);

		_quickFilterMenuItem.setEnabled(true);
		
		_discardFilterMenuItem.setEnabled(true);
		
	}
	
	/**
	 * Sets the ACIDE - A Configurable IDE command bar menu item listeners.
	 */
	public void setListeners() {

		// Sets the undo menu item action listener
		_ascendingMenuItem.addActionListener(new AcideDataViewAscendingMenuItemListener(_table));

		// Sets the redo menu item action listener
		_descendingMenuItem.addActionListener(new AcideDataViewDescendingMenuItemListener(_table));

		// Sets the search menu item action listener
		_searchMenuItem.addActionListener(new AcideDataViewSearchMenuItemListener(_table));

		// Sets the replace menu item action listener
		_quickFilterMenuItem.addActionListener(new AcideDataViewQuickFilterMenuItemListener(_table));

		// Sets the cut menu item action listener
		_cutMenuItem.addActionListener(new AcideDataViewCutMenuItemListener(_table));

		// Sets the paste menu item action listener
		_pasteMenuItem.addActionListener(new AcideDataViewPasteMenuItemListener(_table));

		// Sets the copy menu item action listener
		_copyMenuItem.addActionListener(new AcideDataViewCopyMenuItemListener(_table));
		
		_discardFilterMenuItem.addActionListener(new AcideDataViewDiscardMenuItemListener(_table));
	}
	
	public void setEnabled(boolean enabled){
		_ascendingMenuItem.setEnabled(enabled);
		_copyMenuItem.setEnabled(enabled);
		_cutMenuItem.setEnabled(enabled);
		_descendingMenuItem.setEnabled(enabled);
		_discardFilterMenuItem.setEnabled(enabled);
		_pasteMenuItem.setEnabled(enabled);
		_quickFilterMenuItem.setEnabled(enabled);
		_searchMenuItem.setEnabled(enabled);
	}
	
	public void setToolsTips() {
		_discardFilterMenuItem.setToolTipText(AcideLanguageManager.getInstance().getLabels().getString("s2087"));
		
		_quickFilterMenuItem.setToolTipText(AcideLanguageManager.getInstance().getLabels().getString("s2089"));
		
		_searchMenuItem.setToolTipText(AcideLanguageManager.getInstance().getLabels().getString("s26"));
		
		_cutMenuItem.setToolTipText(AcideLanguageManager.getInstance().getLabels().getString("s188"));
		
		_ascendingMenuItem.setToolTipText(AcideLanguageManager.getInstance().getLabels().getString("s2083"));
		
		_descendingMenuItem.setToolTipText(AcideLanguageManager.getInstance().getLabels().getString("s2084"));
		
		_copyMenuItem.setToolTipText(AcideLanguageManager.getInstance().getLabels().getString("s2052"));
		
		_pasteMenuItem.setToolTipText(AcideLanguageManager.getInstance().getLabels().getString("s2053"));
	}

	/**
	 * Sets the ACIDE - A Configurable IDE command bar menu item values in case the window is on
	 * Read Only mode.
	 */
	public void isReadOnly(boolean b){
		_cutMenuItem.setEnabled(b);
		_pasteMenuItem.setEnabled(b);
		_cutMenuItem.setVisible(b);
		_pasteMenuItem.setVisible(b);
	}
}