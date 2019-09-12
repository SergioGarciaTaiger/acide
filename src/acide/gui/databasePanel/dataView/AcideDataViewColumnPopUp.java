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
package acide.gui.databasePanel.dataView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collections;
import java.util.Vector;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

import acide.gui.databasePanel.dataView.AcideDatabaseDataView.MyTableModel;
import acide.language.AcideLanguageManager;

/**
 * ACIDE - A Configurable IDE data view columns popup menu.
 * 
 * @version 0.16
 * @see JPopupMenu
 */
public class AcideDataViewColumnPopUp extends JPopupMenu{

	private static final long serialVersionUID = 1L;

	private JMenuItem _ascendingMenuItem;
	private JMenuItem _descendingMenuItem;
	private JMenuItem _hideColumnMenuItem;
	/**
	 * ACIDE - DataView to order
	 */
	private AcideDatabaseDataView _table;
	private int _index;
	
	public AcideDataViewColumnPopUp(AcideDatabaseDataView table, int index) {
		super();
		_table = table;
		_index = index;
		buildComponents();
		addComponents();
		setListeners();
		
	}

	private void addComponents() {
		add(_ascendingMenuItem);
		add(_descendingMenuItem);
		add(_hideColumnMenuItem);
		
	}

	private void buildComponents() {
		_ascendingMenuItem = new JMenuItem(AcideLanguageManager
				.getInstance().getLabels().getString("s2083"));
		_descendingMenuItem = new JMenuItem(AcideLanguageManager
				.getInstance().getLabels().getString("s2084"));
		_hideColumnMenuItem = new JMenuItem(AcideLanguageManager
				.getInstance().getLabels().getString("s2292"));
		
	}
	
	@SuppressWarnings("unchecked")
	private void setListeners() {

		_ascendingMenuItem.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (_index>0 && !_table.getIsFilter()) {
					Vector<?> data = ((MyTableModel) _table.get_jTable().getModel()).getDataVector();
					Collections.sort(data, new ColumnSorter(_index,true,_table.getTipos()));
					((MyTableModel) _table.get_jTable().getModel()).fireTableStructureChanged();
					_table.get_jTable().repaint();
					updateSortByWindow("Ascending");
				}
			}
			
		});
		
		_descendingMenuItem.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				if (_index>0 && !_table.getIsFilter()) {
					Vector<?> data = ((MyTableModel) _table.get_jTable().getModel()).getDataVector();
					Collections.sort(data, new ColumnSorter(_index,false,_table.getTipos()));
					((MyTableModel) _table.get_jTable().getModel()).fireTableStructureChanged();
					_table.get_jTable().repaint();
					updateSortByWindow("Descending");
				}
			}
			
		});
		
		_hideColumnMenuItem.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				_table.get_jTable().removeColumn(_table.get_columns().get(_index));
				_table.setIsHidden(true);
			}
		});
		
	}
	
	private void updateSortByWindow(String sort) {
		String name = (String)_table.get_columns().get(_index).getHeaderValue();
		Vector<String> nameList = _table.getOrderNameColumns();
		
		for(int i=0;i<nameList.size();i++){
			if(nameList.get(i).equals(name)){
				_table.getActiveColumns().put(nameList.get(i), Boolean.TRUE);
				_table.getOrderColumns().put(nameList.get(i), sort);
			}
			else 
				_table.getActiveColumns().put(nameList.get(i), Boolean.FALSE);
		}
		
	}
}
