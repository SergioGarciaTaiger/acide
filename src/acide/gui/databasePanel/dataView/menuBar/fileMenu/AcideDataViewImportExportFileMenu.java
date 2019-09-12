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

import javax.swing.ImageIcon;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

import acide.configuration.menu.AcideMenuConfiguration;
import acide.gui.databasePanel.dataView.AcideDatabaseDataView;
import acide.gui.databasePanel.dataView.listeners.AcideDataViewCSVItemListener;
import acide.gui.databasePanel.dataView.listeners.AcideDataViewXMLItemListener;
import acide.language.AcideLanguageManager;

/**
 * ACIDE - A Configurable IDE import/export menu.
 * 
 * @version 0.11
 * @see JMenu
 */
public class AcideDataViewImportExportFileMenu extends JMenu {

	/**
	 * ACIDE - A Configurable IDE file menu class serial version UID.
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * ACIDE - A Configurable IDE file menu Comma-delimited CSV menu item name.
	 */
	public final static String CSV_COMMA = "Comma-delimited CSV";
	/**
	 * ACIDE - A Configurable IDE file menu Tab- delimited CSV menu item name.
	 */
	public final static String CSV_TAB = "Tab- delimited CSV";
	/**
	 * ACIDE - A Configurable IDE file menu CSV menu item name.
	 */
	public final static String CSV = "CSV";
	/**
	 * ACIDE - A Configurable IDE file XML menu item name.
	 */
	public final static String XML = "XML";
	/**
	 * ACIDE - A Configurable IDE file menu csv menu item image icon.
	 */
	private final static ImageIcon CSV_IMAGE = new ImageIcon(
			"./resources/icons/menu/file/csv.png");
	/**
	 * ACIDE - A Configurable IDE file menu xml menu item image icon.
	 */
	private final static ImageIcon XML_IMAGE = new ImageIcon(
			"./resources/icons/menu/file/xml.png");
		
	/**
	 * ACIDE - A Configurable IDE file menu csv comma menu item.
	 */
	private JMenuItem _CSVComma;
	/**
	 * ACIDE - A Configurable IDE file menu csv tab menu item.
	 */
	private JMenuItem _CSVTab;	
	/**
	 * ACIDE - A Configurable IDE file menu csv menu item.
	 */
	private JMenuItem _CSV;
	/**
	 * ACIDE - A Configurable IDE file menu xml menu item.
	 */
	private JMenuItem _XML;
	
	/**
	 * ACIDE - Indicates if is export or import mode
	 */
	private boolean _isExport;
	
	/**
	 * ACIDE - DataView to import\Export data
	 */
	private AcideDatabaseDataView _table;

	
	/**
	 * Creates a new ACIDE - A Configurable IDE import/export menu.
	 * @param table 
	 */
	public AcideDataViewImportExportFileMenu(boolean isExport, AcideDatabaseDataView table) {
		
		this._isExport=isExport;
		
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
	 * Adds the components to the ACIDE - A Configurable IDE import/export menu.
	 */
	private void addComponents() {

		add(_CSVComma);

		add(_CSVTab);

		add(_CSV);

		add(_XML);		
	}

	/**
	 * Builds the ACIDE - A Configurable IDE import/export menu components.
	 */
	private void buildComponents() {

		_CSVComma = new JMenuItem(CSV_IMAGE);

		_CSVComma.setName(CSV_COMMA);

		_CSVTab = new JMenuItem(CSV_IMAGE);

		_CSVTab.setName(CSV_TAB);

		_CSV = new JMenuItem(CSV_IMAGE);

		_CSV.setName(CSV);

		_XML = new JMenuItem(XML_IMAGE);

		_XML.setName(XML);	
	}

	/**
	 * Sets the text of the ACIDE - A Configurable IDE import/export menu components with
	 * the labels in the selected language to display.
	 */
	public void setTextOfMenuComponents() {

		_CSVComma.setText(AcideLanguageManager.getInstance().getLabels().getString("s2068"));

		_CSVTab.setText(AcideLanguageManager.getInstance().getLabels().getString("s2069"));

		_CSV.setText(AcideLanguageManager.getInstance().getLabels().getString("s2070"));

		_XML.setText(AcideLanguageManager.getInstance().getLabels().getString("s2071"));
		
		_CSVComma.setEnabled(true);
		
		_CSVTab.setEnabled(true);
		
		_CSV.setEnabled(true);

		_XML.setEnabled(true);
	}

	/**
	 * Updates the ACIDE - A Configurable IDE import/export menu components visibility
	 * with the menu configuration.
	 */
	public void updateComponentsVisibiliy() {

		_CSVComma.setVisible(AcideMenuConfiguration.getInstance().getIsDisplayed(CSV_COMMA));

		_CSVTab.setVisible(AcideMenuConfiguration.getInstance().getIsDisplayed(CSV_TAB));

		_CSV.setVisible(AcideMenuConfiguration.getInstance().getIsDisplayed(CSV));

		_XML.setVisible(AcideMenuConfiguration.getInstance().getIsDisplayed(XML));
	}

	/**
	 * Sets the ACIDE - A Configurable IDE import/export menu item listeners.
	 */
	public void setListeners() {

		_CSVComma.addActionListener(new AcideDataViewCSVItemListener(_isExport,true,false,_table));

		_CSVTab.addActionListener(new AcideDataViewCSVItemListener(_isExport,false,true,_table));

		_CSV.addActionListener(new AcideDataViewCSVItemListener(_isExport,false,false,_table));

		_XML.addActionListener(new AcideDataViewXMLItemListener(_isExport, _table));

	}


	/**

	 * Enables the ACIDE - A Configurable IDE import/export menu.
	 */
	public void enableMenu() {

		_XML.setEnabled(true);

		_CSV.setEnabled(true);

		_CSVComma.setEnabled(true);

		_CSVTab.setEnabled(true);

	}

	/**
	 * Disables the ACIDE - A Configurable IDE import/export menu.
	 */
	public void disableMenu() {

		_XML.setEnabled(false);

		_CSV.setEnabled(false);

		_CSVComma.setEnabled(false);

		_CSVTab.setEnabled(false);

	}	

	/**
	 * Returns the ACIDE - A Configurable IDE file menu csv comma menu item.
	 * 
	 * @return the ACIDE - A Configurable IDE file menu csv comma menu item
	 */
	public JMenuItem getCsvCommaMenuItem() {
		return _CSVComma;
	}

	/**
	 * Returns the ACIDE - A Configurable IDE file menu xml menu item.
	 * 
	 * @return the ACIDE - A Configurable IDE file menu xml menu item
	 */
	public JMenuItem getXmlMenuItem() {
		return _XML;
	}

	/**
	 * Returns the ACIDE - A Configurable IDE file menu csv tab menu item.
	 * 
	 * @return the ACIDE - A Configurable IDE file menu csv tab menu item
	 */
	public JMenuItem getCsvTabMenuItem() {
		return _CSVTab;
	}

	/**
	 * Returns the ACIDE - A Configurable IDE file menu csv menu
	 * item.
	 * 
	 * @return the ACIDE - A Configurable IDE file menu csv menu item
	 */
	public JMenuItem getCsvMenuItem() {
		return _CSV;
	}
	
	@Override
	public void setEnabled(boolean enabled){
		_CSV.setEnabled(enabled);
		_CSVComma.setEnabled(enabled);
		_CSVTab.setEnabled(enabled);
		_XML.setEnabled(enabled);
	}
}
