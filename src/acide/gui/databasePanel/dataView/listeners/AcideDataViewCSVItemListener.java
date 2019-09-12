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

import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.Vector;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTable;

import acide.gui.databasePanel.dataView.AcideDatabaseDataView;
import acide.gui.databasePanel.dataView.AcideDatabaseDataView.MyTableModel;
import acide.language.AcideLanguageManager;
import acide.process.console.AcideDatabaseManager;

/**
 * ACIDE - A Configurable IDE data view CSV action listener.
 * 
 * @version 0.11
 * @see ActionListener
 */
public class AcideDataViewCSVItemListener implements ActionListener {
	
	/**
	 * ACIDE - Indicates if is export or import mode
	 */
	private boolean _isExport;
	
	/**
	 * ACIDE - Indicates if is comma mode
	 */
	private boolean _isComma;
	
	/**
	 * ACIDE - Indicates if is tab mode
	 */
	private boolean _isTab;
	
	/**
	 * ACIDE - DataView to import/export CSV data
	 */
	private AcideDatabaseDataView _table;

	public AcideDataViewCSVItemListener(boolean isExport, boolean isComma, boolean isTab, AcideDatabaseDataView table) {
		this._isExport=isExport;
		this._table = table;
		this._isComma = isComma;
		this._isTab = isTab;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		_table.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
		_table.setAlwaysOnTop(false);
		JFileChooser fc = new JFileChooser();
		JTable grill = _table.getTable();
		String delimiter="";	   
		if(_isComma)
			delimiter=",";
		else if(_isTab)
			delimiter="	";
		else{
			delimiter = (String) JOptionPane.showInputDialog(null, AcideLanguageManager.getInstance().getLabels().getString("s2143"),"",
	                JOptionPane.PLAIN_MESSAGE,null,null,"");
		
			if ((delimiter == null) || (delimiter.length() < 0)) {
				return;
			}
		}
		if(_isExport){
			int respuesta = fc.showSaveDialog(null);
			if (respuesta == JFileChooser.APPROVE_OPTION) {
				File archivoElegido = fc.getSelectedFile();
				FileWriter fichero = null;
				PrintWriter pw = null;
				try{
					fichero = new FileWriter(archivoElegido.getPath());
					pw = new PrintWriter(fichero);		        
					for(int row=0; row < grill.getRowCount()-1; row++){
						for (int col = 1; col < grill.getColumnCount(); col++){
							String cell = (String) grill.getValueAt(row, col);
							pw.print(cell);
							if(col+1>1 && col+1< grill.getColumnCount())
								pw.print(delimiter);
						}
						pw.println();
					}	            		         
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					try {
						if (null != fichero)
							fichero.close();
					} catch (Exception e2) {
						e2.printStackTrace();
					}
				}
			}
		} else {
			int respuesta = fc.showOpenDialog(null);
			if (respuesta == JFileChooser.APPROVE_OPTION) {
				File archivoElegido = fc.getSelectedFile();
				File archivo = null;
				FileReader fr = null;
				BufferedReader br = null;
				try {
					archivo = new File (archivoElegido.getPath());
					fr = new FileReader (archivo);
					br = new BufferedReader(fr);
					String linea;
					while((linea=br.readLine())!=null){
						Vector<String> infoRow = new Vector<String>();
						infoRow.add("");
						for(int col=1; col<grill.getColumnCount()-1;col++){
							int index = linea.indexOf(delimiter);
							String cell;
							cell = linea.substring(0, index);
							linea = linea.substring(index+1);
							infoRow.add(cell);
						}	
						infoRow.add(linea);
						((MyTableModel) grill.getModel()).removeRow();
						Vector<String> dataColumns = getDataColumns(infoRow);
						String result = AcideDatabaseManager.getInstance().insertValues(_table.getTableName(),dataColumns);
						if(!result.contains("success")){
							JOptionPane.showMessageDialog(null,result);
							break;
						}												
					}
					LinkedList<String> info = AcideDatabaseManager.getInstance().getSelectAll(_table.getDatabase(), _table.getTableName());
					_table.build(info);	
				}
				catch(Exception e){
					JOptionPane.showMessageDialog(null, AcideLanguageManager.getInstance().getLabels().getString("s2142"));
				}finally{					
					try{                    
						if( null != fr ){   
							fr.close();     
						}                  
					}catch (Exception e2){ 
						e2.printStackTrace();
					}
				}
			}
		}		
		_table.setAlwaysOnTop(true);
		_table.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
	}
	
	private Vector<String> getDataColumns(Vector<String> infoRow) {
		Vector<String> result = new Vector<String>();
		int columns = _table.getTable().getColumnCount();
		for(int i = 1; i< columns; i++){
			String infoColumn = infoRow.get(i);
			String data=null;
			if(!infoColumn.equals("")) data =_table.getPrimitiveTypeData(infoColumn,i);
			result.add(data);
		}
		return result;
	}			


}
