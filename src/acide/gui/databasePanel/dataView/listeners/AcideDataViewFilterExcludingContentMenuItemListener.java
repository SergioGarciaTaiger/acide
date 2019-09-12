/*
 * ACIDE - A Configurable IDE
 * Official web site: http://acide.sourceforge.net
 * 
 * Copyright (C) 2007-2013  
 * Authors:
 * 		- Fernando S�enz P�rez (Team Director).
 *      - Version from 0.1 to 0.6:
 *      	- Diego Cardiel Freire.
 *			- Juan Jos� Ortiz S�nchez.
 *          - Delf�n Rup�rez Ca�as.
 *      - Version 0.7:
 *          - Miguel Mart�n L�zaro.
 *      - Version 0.8:
 *      	- Javier Salcedo G�mez.
 *      - Version from 0.9 to 0.11:
 *      	- Pablo Guti�rrez Garc�a-Pardo.
 *      	- Elena Tejeiro P�rez de �greda.
 *      	- Andr�s Vicente del Cura.
 *      - Version from 0.12 to 0.16
 *      	- Sem�ramis Guti�rrez Quintana
 *      	- Juan Jes�s Marqu�s Ortiz
 *      	- Fernando Ord�s Lorente
 *      - Version 0.17
 *      	- Sergio Dom�nguez Fuentes
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
package acide.gui.databasePanel.dataView.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JTable;
import javax.swing.RowFilter;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import acide.gui.databasePanel.dataView.AcideDatabaseDataView;

/**
 * ACIDE - A Configurable IDE console panel popup menu console cut menu item
 * action listener.
 * 
 * @version 0.11
 * @see ActionListener
 */
public class AcideDataViewFilterExcludingContentMenuItemListener implements ActionListener {

	/**
	 * ACIDE - DataView to filter
	 */
	private AcideDatabaseDataView _table;
	
	public AcideDataViewFilterExcludingContentMenuItemListener(AcideDatabaseDataView table) {
		this._table = table;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.
	 * ActionEvent)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void actionPerformed(ActionEvent actionEvent) {
		JTable _jTable = _table.getTable();
		int row = _jTable.getSelectedRow();
		int col = _jTable.getSelectedColumn();
		if(row>=0 && col>0){
			String dataFilter = (String) _jTable.getValueAt(row, col);
			if(dataFilter.equals("")) dataFilter = "[^(?i)^$]";
			else dataFilter = "[^"+dataFilter+"]";
			((TableRowSorter<TableModel>) _jTable.getRowSorter()).setRowFilter(RowFilter.regexFilter(dataFilter, col));
			_table.setIsFilter(true);
		}	
	}
}
