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
package acide.gui.databasePanel.dataView.menuBar.fileMenu;

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
import acide.gui.databasePanel.dataView.listeners.AcideDataViewExecuteQueryMenuItemListener;
import acide.gui.databasePanel.dataView.listeners.AcideDataViewPrintMenuItemListener;
import acide.language.AcideLanguageManager;

/**
 * ACIDE - A Configurable IDE file menu.
 * 
 * @version 0.11
 * @see JMenu
 */
public class AcideDataViewFileMenu extends JMenu {

	/**
	 * ACIDE - A Configurable IDE file menu class serial version UID.
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * ACIDE - A Configurable IDE file menu export menu item name.
	 */
	public final static String EXPORT = "Export";
	/**
	 * ACIDE - A Configurable IDE file menu import menu item name.
	 */
	public final static String IMPORT = "Import";
	/**
	 * ACIDE - A Configurable IDE file menu execute query menu item name.
	 */
	public final static String EXECUTE_QUERY = "Execute query";
	/**
	 * ACIDE - A Configurable IDE file menu print menu item name.
	 */
	public final static String PRINT = "Print";
	/**
	 * ACIDE - A Configurable IDE file menu close file menu item name.
	 */
	public final static String CLOSE = "Close";
	/**
	 * ACIDE - A Configurable IDE file menu execute query menu item image icon.
	 */
	private final static ImageIcon EXECUTE_QUERY_IMAGE = new ImageIcon(
			"./resources/icons/database/menu/execute.png");
	/**
	 * ACIDE - A Configurable IDE file menu close menu item image
	 * icon.
	 */
	private final static ImageIcon CLOSE_IMAGE = new ImageIcon(
			"./resources/icons/database/menu/close.png");
	/**
	 * ACIDE - A Configurable IDE file menu print file menu item image icon.
	 */
	private final static ImageIcon PRINT_IMAGE = new ImageIcon(
			"./resources/icons/menu/file/printFile.png");
	/**
	 * ACIDE - A Configurable IDE file menu export menu item.
	 */
	private AcideDataViewImportExportFileMenu _exportMenuItem;
	/**
	 * ACIDE - A Configurable IDE file menu import menu item.
	 */
	private AcideDataViewImportExportFileMenu _importMenuItem;

	/**
	 * ACIDE - A Configurable IDE file menu execute query menu item.
	 */
	private JMenuItem _executeQueryMenuItem;
	/**
	 * ACIDE - A Configurable IDE file menu close file menu item.
	 */
	private JMenuItem _closeMenuItem;

	/**
	 * ACIDE - A Configurable IDE file menu print file menu item.
	 */
	private JMenuItem _printMenuItem;
	
	/**
	 * ACIDE - DataView owner of the FileMenu
	 */
	private AcideDatabaseDataView _table;
	

	/**
	 * Creates a new ACIDE - A Configurable IDE file menu.
	 * @param table 
	 */
	public AcideDataViewFileMenu(AcideDatabaseDataView table) {

		this._table = table;
		// Builds the menu components
		buildComponents();

		// Adds the components to the menu
		addComponents();

		// Sets the text of the file menu components
		setTextOfMenuComponents();
	}

	/**
	 * Adds the components to the ACIDE - A Configurable IDE file menu.
	 */
	private void addComponents() {

		add(_exportMenuItem);

		add(_importMenuItem);
		
		add(new JSeparator());

		add(_executeQueryMenuItem);
		
		add(new JSeparator());

		add(_printMenuItem);
		
		add(new JSeparator());

		add(_closeMenuItem);

	}

	/**
	 * Builds the ACIDE - A Configurable IDE file menu components.
	 */
	private void buildComponents() {


		_exportMenuItem = new AcideDataViewImportExportFileMenu(true,_table);
		
		_exportMenuItem.setName(EXPORT);

		_importMenuItem = new AcideDataViewImportExportFileMenu(false,_table);

		_importMenuItem.setName(IMPORT);

		_executeQueryMenuItem = new JMenuItem(EXECUTE_QUERY_IMAGE);

		_executeQueryMenuItem.setName(EXECUTE_QUERY);

		_closeMenuItem = new JMenuItem(CLOSE_IMAGE);

		_closeMenuItem.setName(CLOSE);

		_printMenuItem = new JMenuItem(PRINT_IMAGE);

		_printMenuItem.setName(PRINT);
	}

	/**
	 * Sets the text of the ACIDE - A Configurable IDE file menu components with
	 * the labels in the selected language to display.
	 */
	public void setTextOfMenuComponents() {

		
		_exportMenuItem.setText(AcideLanguageManager.getInstance().getLabels().getString("s2064"));
	
		_importMenuItem.setText(AcideLanguageManager.getInstance().getLabels().getString("s2065"));
	
		_executeQueryMenuItem.setText(AcideLanguageManager.getInstance().getLabels().getString("s2066"));

		_closeMenuItem.setText(AcideLanguageManager.getInstance().getLabels().getString("s2045"));

		_printMenuItem.setText(AcideLanguageManager.getInstance().getLabels().getString("s2067"));
		
		_closeMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F4,ActionEvent.ALT_MASK));
		
		_exportMenuItem.setEnabled(true);
		
		_executeQueryMenuItem.setEnabled(true);

		_closeMenuItem.setEnabled(true);
		
		_printMenuItem.setEnabled(true);

		_exportMenuItem.setEnabled(true);

		_importMenuItem.setEnabled(true);
		
		_exportMenuItem.setTextOfMenuComponents();
		
		_importMenuItem.setTextOfMenuComponents();
	}

	/**
	 * Updates the ACIDE - A Configurable IDE file menu components visibility
	 * with the menu configuration.
	 */
	public void updateComponentsVisibiliy() {

		_exportMenuItem.setVisible(AcideMenuConfiguration.getInstance()
				.getIsDisplayed(EXPORT));

		_importMenuItem.setVisible(AcideMenuConfiguration.getInstance()
				.getIsDisplayed(IMPORT));

		_executeQueryMenuItem.setVisible(AcideMenuConfiguration.getInstance()
				.getIsDisplayed(PRINT));

		_closeMenuItem.setVisible(AcideMenuConfiguration.getInstance()
				.getIsDisplayed(CLOSE));

		_printMenuItem.setVisible(AcideMenuConfiguration.getInstance()
				.getIsDisplayed(PRINT));

	}

	/**
	 * Sets the ACIDE - A Configurable IDE file menu item listeners.
	 */
	public void setListeners() {

		_executeQueryMenuItem.addActionListener(new AcideDataViewExecuteQueryMenuItemListener(_table.getDatabase()));

		_printMenuItem.addActionListener(new AcideDataViewPrintMenuItemListener(_table));

		_closeMenuItem.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				_table.closeWindow();
				
			}
		});
	}

	/**
	 * Enables the ACIDE - A Configurable IDE file menu.
	 */
	public void enableMenu() {

		_closeMenuItem.setEnabled(true);

		_executeQueryMenuItem.setEnabled(true);
		
		_exportMenuItem.setEnabled(false);

		_importMenuItem.setEnabled(true);

		_printMenuItem.setEnabled(true);
	}

	/**
	 * Disables the ACIDE - A Configurable IDE file menu.
	 */
	public void disableMenu() {

		_closeMenuItem.setEnabled(false);

		_executeQueryMenuItem.setEnabled(false);
		
		_exportMenuItem.setEnabled(false);

		_importMenuItem.setEnabled(false);

		_printMenuItem.setEnabled(false);
	}


	/**
	 * Returns the ACIDE - A Configurable IDE file menu print menu item.
	 * 
	 * @return the ACIDE - A Configurable IDE file menu print menu item
	 */
	public JMenuItem getPrintFileMenuItem() {
		return _printMenuItem;
	}

	/**
	 * Returns the ACIDE - A Configurable IDE file menu close menu item.
	 * 
	 * @return the ACIDE - A Configurable IDE file menu close menu item
	 */
	public JMenuItem getCloseMenuItem() {
		return _closeMenuItem;
	}

	/**
	 * Returns the ACIDE - A Configurable IDE file menu import menu item.
	 * 
	 * @return the ACIDE - A Configurable IDE file menu import menu item
	 */
	public JMenuItem getImportMenuItem() {
		return _importMenuItem;
	}
	
	/**
	 * Returns the ACIDE - A Configurable IDE file menu export menu item.
	 * 
	 * @return the ACIDE - A Configurable IDE file menu export menu item
	 */
	public JMenuItem getExportMenuItem() {
		return _exportMenuItem;
	}

	/**
	 * Returns the ACIDE - A Configurable IDE file menu execute query menu
	 * item.
	 * 
	 * @return the ACIDE - A Configurable IDE file menu execute query menu item
	 */
	public JMenuItem getExecuteQueryMenuItem() {
		return _executeQueryMenuItem;
	}
	
	@Override
	public void setEnabled(boolean enabled){
		_closeMenuItem.setEnabled(enabled);
		_executeQueryMenuItem.setEnabled(enabled);
		_exportMenuItem.setEnabled(enabled);
		_importMenuItem.setEnabled(enabled);
		_printMenuItem.setEnabled(enabled);
	}
}
