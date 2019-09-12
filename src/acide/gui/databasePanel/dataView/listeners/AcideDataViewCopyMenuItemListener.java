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

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.ClipboardOwner;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JTable;

import acide.gui.databasePanel.dataView.AcideDatabaseDataView;
import acide.gui.databasePanel.utils.DataViewTableSelection;

/**
 * ACIDE - A Configurable IDE console panel popup menu console copy menu item
 * action listener.
 * 
 * @version 0.15
 * @see ActionListener
 */
public class AcideDataViewCopyMenuItemListener implements ActionListener, ClipboardOwner {

	/**
	 * ACIDE - DataView to copy data
	 */
	private AcideDatabaseDataView _table;
	
	private Clipboard systemClipboard = Toolkit.getDefaultToolkit().getSystemClipboard();  
	
	
	public AcideDataViewCopyMenuItemListener(AcideDatabaseDataView table) {
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
		
		int[] selectedRows = _jTable.getSelectedRows();  
		int[] selectedColumns = _jTable.getSelectedColumns();
		int initColumn = 0;
		int ini=0;
        String[][] data = null;
        if (selectedColumns[0]==0) { 
        	data = new String[selectedRows.length][selectedColumns.length-1];
        	ini =1;		
        	} else data = new String[selectedRows.length][selectedColumns.length];
      
        for (int i=0; i<selectedRows.length;i++) {  
            for (int j=ini; j<selectedColumns.length;j++) {  
            		data[i][initColumn] = (String) _jTable.getValueAt(selectedRows[i],selectedColumns[j]);
            		initColumn++;
            }
            initColumn=0;
        }  
       
        copyDataToSystemClipboard(data);

	}
	
	public void copyDataToSystemClipboard(String[][] data) {  
        if (systemClipboard != null) {
        	DataViewTableSelection selectedData = new DataViewTableSelection(data);
        	systemClipboard.setContents(selectedData, this); 
        } 
    }

	@Override
	public void lostOwnership(Clipboard clipboard, Transferable contents) {
		try {  
        System.out.println("Lost ownership to the clipboard. "  
                    + contents.getTransferData(DataViewTableSelection.dataViewTableFlavor)); 
        } catch (UnsupportedFlavorException e) {  
            e.printStackTrace();  
        } catch (IOException e) {  
            e.printStackTrace();  
        }
	}  
	
    public void verifyClipboardData() {  
        try {  
            if (systemClipboard != null) {  
            	System.out.println(systemClipboard  
                        .getData(DataViewTableSelection.dataViewTableFlavor));  
            }  
        } catch (UnsupportedFlavorException e) {  
            e.printStackTrace();  
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
    }  
}
