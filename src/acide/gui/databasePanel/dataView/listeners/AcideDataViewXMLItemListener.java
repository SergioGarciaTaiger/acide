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
import java.io.File;
import java.util.LinkedList;
import java.util.Vector;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;

import acide.gui.databasePanel.dataView.AcideDatabaseDataView;
import acide.gui.databasePanel.dataView.AcideDatabaseDataView.MyTableModel;
import acide.language.AcideLanguageManager;
import acide.process.console.AcideDatabaseManager;

/**
 * ACIDE - A Configurable IDE data view XML item listener.
 * 
 * @version 0.11
 * @see ActionListener
 */
public class AcideDataViewXMLItemListener implements ActionListener {

	/**
	 * ACIDE - indicates if is export/import mode
	 */
	private boolean _isExport;
	
	/**
	 * ACIDE - DataView to import/export xml data
	 */
	private AcideDatabaseDataView _table;

	public AcideDataViewXMLItemListener(boolean isExport, AcideDatabaseDataView table) {
		this._isExport=isExport;
		this._table = table;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		_table.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
		_table.setAlwaysOnTop(false);
		JFileChooser fc = new JFileChooser();
		JTable grill = _table.getTable();
		if(_isExport){
			int respuesta = fc.showSaveDialog(null);
			if (respuesta == JFileChooser.APPROVE_OPTION) {
				DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
				try{
					DocumentBuilder builder = factory.newDocumentBuilder();
					DOMImplementation implementation = builder.getDOMImplementation();
					Document document = implementation.createDocument(null, "xml", null);
					Element raiz = document.createElement("DATA");  // creamos el elemento raiz
					Element row = document.createElement("ROW"); //creamos un nuevo elemento					
					document.setXmlVersion("1.0"); // asignamos la version de nuestro XML
					document.getDocumentElement().appendChild(raiz);  //pegamos la raiz al documento

					Vector<String> colNames =_table.getColumnsNames();

					for(int rowIndex = 0; rowIndex < grill.getRowCount()-1; rowIndex++){
						Element clone = (Element) row.cloneNode(false);
						raiz.appendChild(clone); //pegamos el elemento hijo a la raiz
						for(int colIndex = 1; colIndex < grill.getColumnCount(); colIndex++){
							Element col = document.createElement(colNames.get(colIndex-1)); //creamos un nuevo elemento
							Text text = document.createTextNode((String) grill.getValueAt(rowIndex, colIndex)); //Ingresamos la info							
							clone.appendChild(col);
							col.appendChild(text);
						}
					}
					Source source = new DOMSource(document);
					Result result = new StreamResult(new java.io.File(fc.getSelectedFile().getPath()));

					Transformer transformer = TransformerFactory.newInstance().newTransformer();
					transformer.transform(source, result);
				}catch(Exception e){
					System.err.println("Error: "+e);
				}

			}
		} else {
			int respuesta = fc.showOpenDialog(null);
			if (respuesta == JFileChooser.APPROVE_OPTION) {
				try {
				
					File archivoElegido = fc.getSelectedFile();
					DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();					 
					DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
					Document doc = docBuilder.parse (new File(archivoElegido.getPath()));
					doc.getDocumentElement().normalize ();
					NodeList listaFilas = doc.getElementsByTagName("ROW");
					Vector<String> columnNames = _table.getColumnsNames(); 

					int totalPersonas = listaFilas.getLength();
					((MyTableModel) grill.getModel()).removeRow();
					for (int i = 0; i < totalPersonas; i ++) {						 
						Node persona = listaFilas.item(i);
						if (persona.getNodeType() == Node.ELEMENT_NODE){
							Element elemento = (Element) persona;
							Vector<String> infoRow = new Vector<String>();
							infoRow.add("");
							for(int col=1; col<grill.getColumnCount();col++){
								String cell =  getTagValue(columnNames.get(col-1),elemento );		
								infoRow.add(cell);
							}	
							Vector<String> dataColumns = getDataColumns(infoRow);	
							String result = AcideDatabaseManager.getInstance().insertValues(_table.getTableName(),dataColumns);
							if(!result.contains("success")){
								JOptionPane.showMessageDialog(null,result);
								break;
							}	
						}						
					}
					LinkedList<String> info = AcideDatabaseManager.getInstance().getSelectAll(_table.getDatabase(), _table.getTableName());
					_table.build(info);	
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, AcideLanguageManager.getInstance().getLabels().getString("s2142"));
				}
			}
		}		
		_table.setAlwaysOnTop(true);
		_table.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
	}

	public String getTagValue(String tag, Element elemento) {
		NodeList lista = elemento.getElementsByTagName(tag).item(0).getChildNodes();
		Node valor = (Node) lista.item(0);
		if(valor==null) return "";
		return valor.getNodeValue();
	}
	
	private Vector<String> getDataColumns(Vector<String> infoRow) {
		Vector<String> result = new Vector<String>();
		int columns = _table.getTable().getColumnCount();
		for(int i = 1; i< columns; i++){
			String infoColumn = infoRow.get(i);
			String data =_table.getPrimitiveTypeData(infoColumn,i);
			if(data.equals(""))result.add(null);
			else result.add(data);
		}
		return result;
	}	
}
