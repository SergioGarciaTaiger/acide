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
package acide.gui.menuBar.configurationMenu.menuMenu.gui.filePanel.utils;

import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.table.DefaultTableModel;

import acide.configuration.menu.AcideMenuObjectConfiguration;
import acide.configuration.menu.AcideMenuObjectConfigurationWithNumber;
import acide.configuration.menu.AcideMenuSubmenuConfiguration;
import acide.gui.menuBar.configurationMenu.menuMenu.gui.filePanel.AcideFileMenuNewPanel;
import acide.gui.toolBarPanel.consolePanelToolBar.utils.AcideParameterType;
import acide.language.AcideLanguageManager;
/**
 * ACIDE - A Configurable IDE file panel configuration panel table model.
 * 
 * @version 0.11
 * @see DefaultTableModel
 */
public class AcideFileMenuNewPanelTableModel extends
DefaultTableModel {
	/**
	 * ACIDE - A Configurable IDE file panel new configuration panel table model
	 * class serial version UID.
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * ACIDE - A Configurable IDE file panel new configuration panel table model
	 * column row constant.
	 */
	public static final int COLUMN_ROW = 0;
	/**
	 * ACIDE - A Configurable IDE file panel new configuration panel table model
	 * column type constant.
	 */
	public static final int COLUMN_TYPE = 1;
	/**
	 * ACIDE - A Configurable IDE file panel new configuration panel table model
	 * column name constant.
	 */
	public static final int COLUMN_NAME = 2;
	/**
	 * ACIDE - A Configurable IDE file panel new configuration panel table model
	 * column is visible constant.
	 */
	public static final int COLUMN_VISIBLE = 3;
	/**
	 * ACIDE - A Configurable IDE file panel new configuration panel table model
	 * column is erasable constant.
	 */
	public static final int COLUMN_ERASABLE = 4;
	/**
	 * ACIDE - A Configurable IDE file panel new configuration panel table model
	 * column image.
	 */
	public static final int COLUMN_IMAGE = 5;
	/**
	 * ACIDE - A Configurable IDE file panel new configuration panel table model
	 * column path constant.
	 */
	public static final int COLUMN_PATH = 6;
	/**
	 * ACIDE - A Configurable IDE file panel new configuration panel table model
	 * column sons constant.
	 */
	public static final int COLUMN_SONS = 7;
	/**
	 * ACIDE - A Configurable IDE file panel new configuration panel table model
	 * column command constant.
	 */
	public static final int COLUMN_COMMAND = 8;
	/**
	 * ACIDE - A Configurable IDE file panel new configuration panel table model
	 * column is executed in system shell constant.
	 */
	public static final int COLUMN_PARAMETER = 9;
	/**
	 * ACIDE - A Configurable IDE console panel configuration panel table model
	 * item list.
	 */
	private ArrayList<AcideMenuObjectConfigurationWithNumber> _items;
	/**
	 * ACIDE - A Configurable IDE file panel new configuration panel instance.
	 */
	private AcideFileMenuNewPanel _filePanelNewConfigurationPanel;
	
	/**
	 * Creates a new ACIDE - A Configurable IDE file panel new configuration
	 * panel table model.
	 * 
	 * @param filePanelNewConfigurationPanel
	 *            <p>
	 *            ACIDE - A Configurable IDE file panel new configuration panel
	 *            instance.
	 *            </p>
	 *            <p>
	 *            It is needed to tell it that there have been modifications on
	 *            it. Otherwise, when a changed is performed in the model, the
	 *            configuration window will not be able to notice it.
	 *            </p>
	 */
	public AcideFileMenuNewPanelTableModel(
			AcideFileMenuNewPanel filePanelNewConfigurationPanel) {

		// Stores the console configuration panel instance
		_filePanelNewConfigurationPanel = filePanelNewConfigurationPanel;

		// Creates the item list
		_items = new ArrayList<AcideMenuObjectConfigurationWithNumber>();
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.table.TableModel#getColumnCount()
	 */
	@Override
	public int getColumnCount() {
		return 10;
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
		case COLUMN_ROW:
			return AcideLanguageManager.getInstance().getLabels()
					.getString("s2179");
		case COLUMN_TYPE:
			return AcideLanguageManager.getInstance().getLabels()
					.getString("s2170");
		case COLUMN_NAME:
			return AcideLanguageManager.getInstance().getLabels()
					.getString("s2171");
		case COLUMN_VISIBLE:
			return AcideLanguageManager.getInstance().getLabels()
					.getString("s2172");
		case COLUMN_ERASABLE:
			return AcideLanguageManager.getInstance().getLabels()
					.getString("s2173");
		case COLUMN_IMAGE:
			return AcideLanguageManager.getInstance().getLabels()
					.getString("s2195");
		case COLUMN_PATH:
			return AcideLanguageManager.getInstance().getLabels()
					.getString("s2174");
		case COLUMN_SONS:
			return AcideLanguageManager.getInstance().getLabels()
					.getString("s2175");
		case COLUMN_COMMAND:
			return AcideLanguageManager.getInstance().getLabels()
					.getString("s2176");
		case COLUMN_PARAMETER:
			return AcideLanguageManager.getInstance().getLabels()
					.getString("s2177");
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

		AcideMenuObjectConfigurationWithNumber item = (AcideMenuObjectConfigurationWithNumber) _items
				.get(rowIndex);

		switch (columnIndex) {
		case COLUMN_ROW:
			return item.getRow();
		case COLUMN_TYPE: 
			return item.type();
		case COLUMN_NAME:
			return item.getName();
		case COLUMN_VISIBLE:
			return item.isVisible();
		case COLUMN_ERASABLE:
			return item.isErasable();
		case COLUMN_IMAGE:
			return item.getImage();
		case COLUMN_PATH:
			String result = _filePanelNewConfigurationPanel.getConfiguration().getName() + "/";
			for (int i = item.getRow() -1; i > 0; i--){
				AcideMenuObjectConfigurationWithNumber aux = (AcideMenuObjectConfigurationWithNumber) _items
						.get(i-1);
				ArrayList<Integer> arrayAux = listSons(aux);
				if (arrayAux.contains(item.getRow())){
					result = getValueAt(i-1, 6) + aux.getName() + "/";
				}
			}
			return result;
		case COLUMN_SONS:
			if (item.isSubmenu()){
				String aux = listSonsSince(item);
				return aux;
			}else {
				return "-";
			}
		case COLUMN_COMMAND:
			if (item.isItem()){
				return item.getCommand();
			}else {
				return "-";
			}
		case COLUMN_PARAMETER:
			if (item.isItem()){
				return item.getParameterType().toString();	
			} else {
				return "-";
			}
		default:
			return null;
		}
	}
	
	/**
	 * Returns the ACIDE - A Configurable IDE file menu new panel
	 * table model list of sons from an object.
	 * 
	 * @param ob
	 * 		the object whose soons we want to get
	 * @return the ACIDE - A Configurable IDE file menu new panel
	 * table model list of sons from an object.
	 */
	public String listSonsSince(AcideMenuObjectConfigurationWithNumber ob) {
		if (ob.isSubmenu()){
			String resul = "[ ";
			AcideMenuSubmenuConfiguration subMenu = 
					(AcideMenuSubmenuConfiguration) ob.getOb();
			Iterator<Object> it =
					subMenu.getItemsManager().getList().getList().iterator();
			int i = ob.getRow() + 1;
			while (it.hasNext()){
				AcideMenuObjectConfiguration aux = (AcideMenuObjectConfiguration) it.next();
				if (i > ob.getRow() + 1)
					resul = resul + ", ";
				resul = resul + i ;
				if (aux.isSubmenu()){
					AcideMenuSubmenuConfiguration auxMenu = (AcideMenuSubmenuConfiguration) aux;	
					i = i + auxMenu.getItemsManager().getAllObjects().size();
				}
				i++;
			}
			resul = resul + "]";
			return resul;
		}
		else return "-";
	}
	
	/**
	 * Returns the ACIDE - A Configurable IDE file menu new panel
	 * table model list of sons from an object.
	 * 
	 * @param ob
	 * 		the object whose soons we want to get
	 * @return the ACIDE - A Configurable IDE file menu new panel
	 * table model list of sons from an object.
	 */
	public ArrayList<Integer> listSons(AcideMenuObjectConfigurationWithNumber ob){
		ArrayList<Integer> resul = new ArrayList<Integer>();
		if (ob.isSubmenu()){
			AcideMenuSubmenuConfiguration subMenu = 
					(AcideMenuSubmenuConfiguration) ob.getOb();
			Iterator<Object> it =
					subMenu.getItemsManager().getList().getList().iterator();
			int i = ob.getRow() + 1;
			while (it.hasNext()){
				AcideMenuObjectConfiguration aux = (AcideMenuObjectConfiguration) it.next();
				resul.add(i);
				if (aux.isSubmenu()){
					AcideMenuSubmenuConfiguration auxMenu = (AcideMenuSubmenuConfiguration) aux;	
					i = i + auxMenu.getItemsManager().getAllObjects().size();
				}
				i++;
			}
		}
		return resul;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.table.AbstractTableModel#getColumnClass(int)
	 */
	public Class<?> getColumnClass(int column) {
		return getValueAt(0, column).getClass();
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.table.AbstractTableModel#isCellEditable(int, int)
	 */
	@Override
	public boolean isCellEditable(int row, int col) {
		switch (col){
		case 0:
		case 1:
		case 4:
		case 6:
		case 7:
			return false;
		}
	
		
		AcideMenuObjectConfigurationWithNumber item = (AcideMenuObjectConfigurationWithNumber) _items
				.get(row);
		if (item.isSubmenu()){
			if ((col == 8) || (col == 9))
				return false;
		}
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

		// There are changes on the table in the console panel configuration
		// panel
		_filePanelNewConfigurationPanel.setAreThereChanges(true);

		// Gets the console command from the table row
		AcideMenuObjectConfigurationWithNumber menuObject = (AcideMenuObjectConfigurationWithNumber) _items
				.get(rowIndex);

		switch (columnIndex) {
		case COLUMN_NAME:
			menuObject.setName(value.toString());
			fireTableCellUpdated(rowIndex, columnIndex);
			break;
		case COLUMN_VISIBLE:
			menuObject.setVisible((Boolean) value);
			fireTableCellUpdated(rowIndex, columnIndex);
			break;
		case COLUMN_ERASABLE:
			menuObject.setErasable((Boolean) value);
			fireTableCellUpdated(rowIndex, columnIndex);
			break;
		case COLUMN_IMAGE:
			menuObject.setImage(value.toString());
			fireTableCellUpdated(rowIndex, columnIndex);
			break;
		case COLUMN_SONS:
			fireTableCellUpdated(rowIndex, columnIndex);
			break;
		case COLUMN_COMMAND:
			if (menuObject.isItem()){
				menuObject.setCommand(value.toString());
				fireTableCellUpdated(rowIndex, columnIndex);
			}
			break;
		case COLUMN_PARAMETER:
			if (menuObject.isItem()){
				menuObject.setParameterType(AcideParameterType
						.fromStringToEnum(value.toString()));
				fireTableCellUpdated(rowIndex, columnIndex);
			}
			break;
		default:
			return;
		}
	}
	
	/**
	 * Returns the ACIDE - A Configurable IDE file panel new configuration panel
	 * table model item list.
	 * 
	 * @return the ACIDE - A Configurable IDE file panel new configuration panel
	 *         table model item list.
	 */
	public ArrayList<AcideMenuObjectConfigurationWithNumber> getItems() {
		return _items;
	}
	
	/**
	 * Sets a new value to the ACIDE - A Configurable IDE file panel
	 * new configuration panel table model item list.
	 * 
	 * @param items
	 *            new value to set.
	 */
	public void setItems(ArrayList<Object> object) {

		// Clears the item list
		_items.clear();

		// Adds all the items to it
		for (Object menuObject : object){
			AcideMenuObjectConfigurationWithNumber aux = (AcideMenuObjectConfigurationWithNumber) menuObject;
			_items.add(aux);
		}

		// Updates the model
		fireTableDataChanged();
	}
	
	/**
	 * Adds a new item (row) to the model.
	 * 
	 * @param consoleCommand
	 *            new item to be added.
	 */
	public void addItem(AcideMenuObjectConfigurationWithNumber object) {
		_items.add(object);
	}
	
	/**
	 * Removes the item from the item list at the position given as a parameter.
	 * 
	 * @param rowIndex
	 *            position to be removed.
	 */
	public void removeItem(int rowIndex) {
		_items.remove(rowIndex);
	}

}
