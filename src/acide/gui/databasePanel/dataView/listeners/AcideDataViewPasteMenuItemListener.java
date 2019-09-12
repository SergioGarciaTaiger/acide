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
package acide.gui.databasePanel.dataView.listeners;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Vector;

import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import acide.gui.databasePanel.dataView.AcideDatabaseDataView;
import acide.gui.databasePanel.utils.DataViewTableSelection;
import acide.language.AcideLanguageManager;

/**
 * ACIDE - A Configurable IDE console panel popup menu console cut menu item
 * action listener.
 * 
 * @version 0.15
 * @see ActionListener
 */
public class AcideDataViewPasteMenuItemListener implements ActionListener {

	/**
	 * ACIDE - DataView to paste data
	 */
	private AcideDatabaseDataView _table;
	
	public AcideDataViewPasteMenuItemListener(AcideDatabaseDataView table) {
		this._table = table;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.
	 * ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent actionEvent) {
		JTable _jTable = _table.getTable();
		
		try {
			Toolkit toolkit = Toolkit.getDefaultToolkit();
			Clipboard clipboard = toolkit.getSystemClipboard();
			String[][] result = (String[][]) clipboard.getData(DataViewTableSelection.dataViewTableFlavor);
			
			int numRowsPaste = result.length;
			int numColumnsPaste = result[0].length; //Only an NxN matrix is allowed to copy
			
			int numRowsTable = _jTable.getRowCount(); //Total number of rows of the grid
			int numColumnsTable = _jTable.getColumnCount(); //Total number of columns of the grid
			
			int initRow = _jTable.getSelectedRow();//Initial row to start to paste
			int initColumn = _jTable.getSelectedColumn(); //Initial column to start to paste
			
			if (initColumn == 0) initColumn = 1; //If column 0 is selected start pasting from column 1
			
			DefaultTableModel model = (DefaultTableModel) _jTable.getModel();
			//Need to check if there is enough space to paste
				if(numColumnsTable-initColumn>=numColumnsPaste){
					int initRowAux= initRow;
					int initColumnAux= initColumn;
					//Start pasting
					for(int i=0;i<numRowsPaste;i++){
						if ((initRowAux >= numRowsTable)){ //Last record
							Vector<String> row = new Vector<String>();
							model.insertRow(initRowAux, row);
						}
						for(int j=0;j<numColumnsPaste;j++){
							_jTable.setValueAt(result[i][j], initRowAux, initColumnAux);
							initColumnAux++;
						}
						initColumnAux=initColumn;
						initRowAux++;
					}
			        
					_table.get_jTable()._dataView.insertValues();
					Dimension d = _jTable.getSize();
					_jTable.setSize(d.width, d.height+1);
					_jTable.setSize(d.width, d.height-1);
				} 
				else{
					JOptionPane.showMessageDialog(null,AcideLanguageManager.getInstance()
					.getLabels().getString("s2251"), AcideLanguageManager.getInstance()
					.getLabels().getString("s2240"),
                    JOptionPane.WARNING_MESSAGE);
			}
			
		} catch (UnsupportedFlavorException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
