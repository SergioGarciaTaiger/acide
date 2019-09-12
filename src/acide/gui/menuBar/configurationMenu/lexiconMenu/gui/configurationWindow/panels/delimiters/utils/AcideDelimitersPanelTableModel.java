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
package acide.gui.menuBar.configurationMenu.lexiconMenu.gui.configurationWindow.panels.delimiters.utils;

import javax.swing.table.DefaultTableModel;

import acide.language.AcideLanguageManager;

import acide.configuration.utils.ObjectList;
import acide.gui.menuBar.configurationMenu.lexiconMenu.gui.configurationWindow.panels.delimiters.AcideDelimitersPanel;

/**
 * ACIDE - A Configurable IDE delimiters panel table model.
 * 
 * @version 0.11
 * @see DefaultTableModel
 */
public class AcideDelimitersPanelTableModel extends DefaultTableModel {

	/**
	 * ACIDE - A Configurable IDE delimiters panel table model class serial
	 * version UID.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * ACIDE - A Configurable IDE delimiters panel table model column symbol
	 * constant.
	 */
	public static final int COLUMN_SYMBOL = 0;

	/**
	 * ACIDE - A Configurable IDE delimiters panel table model item list.
	 */
	private ObjectList _items;
	/**
	 * ACIDE - A Configurable IDE delimiters panel instance.
	 */
	private AcideDelimitersPanel _delimitersPanel;

	/**
	 * Creates a new ACIDE - A Configurable IDE delimiters panel table model.
	 * 
	 * @param delimitersPanel
	 *            ACIDE - A Configurable IDE delimiters panel instance for
	 *            notify it from the changes in the model.
	 */
	public AcideDelimitersPanelTableModel(
			AcideDelimitersPanel delimitersPanel) {
		
		// Creates the object list
		_items = new ObjectList();
		
		// Stores the delimiters panel instance
		_delimitersPanel = delimitersPanel;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.table.TableModel#getColumnCount()
	 */
	@Override
	public int getColumnCount() {
		return 1;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.table.TableModel#getRowCount()
	 */
	@Override
	public int getRowCount() {
		if (_items != null)
			return _items.size();
		return 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.table.AbstractTableModel#getColumnName(int)
	 */
	@Override
	public String getColumnName(int columnIndex) {

		switch (columnIndex) {
		case COLUMN_SYMBOL:
			return AcideLanguageManager.getInstance().getLabels()
					.getString("s440");
		default:
			return "Column " + columnIndex;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.table.TableModel#getValueAt(int, int)
	 */
	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {

		switch (columnIndex) {
		case COLUMN_SYMBOL:
			return (String) _items.getObjectAt(rowIndex);
		default:
			return null;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.table.AbstractTableModel#getColumnClass(int)
	 */
	@Override
	public Class<?> getColumnClass(int column) {
		return String.class;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.table.AbstractTableModel#isCellEditable(int, int)
	 */
	@Override
	public boolean isCellEditable(int row, int col) {

		// All the cells are editable
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.table.AbstractTableModel#setValueAt(java.lang.Object,
	 * int, int)
	 */
	@Override
	public void setValueAt(Object value, int rowIndex, int columnIndex) {

		// There are changes in the delimiters panel
		_delimitersPanel.setAreThereChanges(true);
		
		switch (columnIndex) {
		case COLUMN_SYMBOL:
			_items.setValueAt(rowIndex, value.toString());
			fireTableCellUpdated(rowIndex, columnIndex);
			break;
		default:
			return;
		}
	}

	/**
	 * Returns the item list.
	 * 
	 * @return the item list.
	 */
	public ObjectList getItems() {
		return _items;
	}

	/**
	 * Sets a new value to the item list.
	 * 
	 * @param items
	 *            new value to set.
	 */
	public void setItems(ObjectList items) {

		// Clears the item list
		_items.clear();

		for (int index = 0; index < items.size(); index++)
			_items.insert(index, (String) items.getObjectAt(index));

		// Updates the model
		fireTableDataChanged();
	}

	/**
	 * Adds a new item (row) to the model.
	 * 
	 * @param symbol
	 *            new item to be added.
	 */
	public void addItem(String symbol) {
		_items.insert(symbol);
	}

	/**
	 * Removes the item from the item list at the position given as a parameter.
	 * 
	 * @param rowIndex
	 *            position to be removed.
	 */
	public void removeItem(int rowIndex) {
		_items.removeAt(rowIndex);
	}
}
