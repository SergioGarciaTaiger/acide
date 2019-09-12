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
package acide.gui.databasePanel.dataView.listeners;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JTable;

import acide.gui.databasePanel.dataView.AcideDatabaseDataView;
import acide.language.AcideLanguageManager;

/**
 * ACIDE - A Configurable IDE database panel keyboard listener.
 * 
 * @version 0.11
 * @see KeyAdapter
 */
public class AcideDataViewKeyboardListener extends KeyAdapter {
	
	/**
	 * ACIDE - DataView to listen keyActions
	 */
	private AcideDatabaseDataView _dataView;

	public AcideDataViewKeyboardListener(AcideDatabaseDataView table) {
		this._dataView = table;
	}

	@Override
	public void keyPressed(KeyEvent keyEvent) {
		dispatchEvent(keyEvent);
	
	}

	private void dispatchEvent(KeyEvent keyEvent){
		JTable _jTable = _dataView.getTable();		
		int row = _jTable.getSelectedRow(); 
		int col = _jTable.getSelectedColumn();
		
		keyEvent.consume();
		switch (keyEvent.getKeyCode()) {
		
		case KeyEvent.VK_F3:
			if(keyEvent.isShiftDown()){
				_dataView.nextSearchField();
			}
			else{
				_dataView.setAlwaysOnTop(false);
				String data = (String)JOptionPane.showInputDialog(null, "",AcideLanguageManager.getInstance().getLabels().getString("s26"),
		                JOptionPane.PLAIN_MESSAGE,new ImageIcon("./resources/icons/menu/edit/search.png"),null,"");
				if ((data != null) && (data.length() > 0))
					_dataView.search(data);
				_dataView.setAlwaysOnTop(true);
			}
			
			break;
		case KeyEvent.VK_F5:
			_dataView.refresh();
			break;
			
		case 127: //supr
			_dataView.deleteRow();
			break;

		case KeyEvent.VK_C:
			if (keyEvent.isControlDown()){
				if(row>0 && col>0){
					String data = (String) _jTable.getValueAt(row, col);
					Toolkit toolkit = Toolkit.getDefaultToolkit();
					Clipboard clipboard = toolkit.getSystemClipboard();
					StringSelection strSel = new StringSelection(data);
					clipboard.setContents(strSel, null);
					}
			}				
			break;
			
		case KeyEvent.VK_V:
			if (keyEvent.isControlDown()){
				
				try {
					Toolkit toolkit = Toolkit.getDefaultToolkit();
					Clipboard clipboard = toolkit.getSystemClipboard();
					String result = (String) clipboard.getData(DataFlavor.stringFlavor);
					if(row>0 && col>0){
						_jTable.setValueAt(result, row, col);
					}
				} catch (UnsupportedFlavorException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}			
			break;
		case KeyEvent.VK_X:
			if (keyEvent.isControlDown()){
				if(row>0 && col>0){
				String data = (String) _jTable.getValueAt(row, col);
				Toolkit toolkit = Toolkit.getDefaultToolkit();
				Clipboard clipboard = toolkit.getSystemClipboard();
				StringSelection strSel = new StringSelection(data);
				clipboard.setContents(strSel, null);
				_jTable.setValueAt("", row, col);
				}	
			}			
			break;
		case KeyEvent.VK_F:
			if (keyEvent.isControlDown()){
				String data = (String)JOptionPane.showInputDialog(null, "",AcideLanguageManager.getInstance().getLabels().getString("s26"),
	                JOptionPane.PLAIN_MESSAGE,new ImageIcon(
	            			"./resources/icons/menu/edit/search.png"),null,"");

			//If a string was returned, say so.
			if ((data != null) && (data.length() > 0))
				_dataView.search(data);
			}			
			break;
			
		case KeyEvent.VK_B:
			if (keyEvent.isControlDown()){
				String data = (String)JOptionPane.showInputDialog(null, "",AcideLanguageManager.getInstance().getLabels().getString("s26"),
		                JOptionPane.PLAIN_MESSAGE,new ImageIcon(
		            			"./resources/icons/menu/edit/search.png"),null,"");
	
				//If a string was returned, say so.
				if ((data != null) && (data.length() > 0))
					_dataView.search(data);	
			}			
			break;
		
		case KeyEvent.VK_Y:
			if (keyEvent.isControlDown())
			break;
		
		case KeyEvent.VK_Z:
			if (keyEvent.isControlDown())
			break;
			
		case KeyEvent.VK_F4:
			if (keyEvent.isAltDown())
			_dataView.closeWindow();
			break;
		
		case KeyEvent.VK_UP:
			_dataView.previousRecord();
			break;
		case KeyEvent.VK_DOWN:
			_dataView.nextRecord();
			break;
		case KeyEvent.VK_END:
			if (keyEvent.isControlDown())
				_dataView.lastRecord();
			break;
		case 36:
			if (keyEvent.isControlDown())
				_dataView.firstRecord();
			break;
		case KeyEvent.VK_TAB:
			if (keyEvent.isControlDown())
				_dataView.previousField();
			else				
				_dataView.nextField();
			break;
		case KeyEvent.VK_ENTER:
			if(_dataView.getTable().getSelectedColumn()==_dataView.getTable().getColumnCount()-1)
				_dataView.insertValues();
			break;

		}
	}

}
