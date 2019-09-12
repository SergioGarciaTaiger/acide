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
package acide.gui.databasePanel.dataView;

import java.util.Comparator;
import java.util.Vector;

@SuppressWarnings("rawtypes")
public
class ColumnSorter implements Comparator {
	private int _colIndex;
	private boolean _isAscending;
	private Vector<types> _tipos = new Vector<types>();
	
	public ColumnSorter(int colIndex, boolean isAscending,Vector<types> tipos) {
		this._colIndex = colIndex;
		this._isAscending = isAscending;
		this._tipos = tipos;
	}

	@SuppressWarnings("unchecked")
	public int compare(Object a, Object b) {
			Vector v1 = (Vector) a;
			Vector v2 = (Vector) b;
			Object o1 = v1.get(_colIndex);
			Object o2 = v2.get(_colIndex);
			
			if(_tipos.get(_colIndex-1).equals(types.INT) && (!((String) o1).equals("") && !((String) o2).equals(""))){
				if(_isAscending)
					return Integer.valueOf((String) o1).compareTo(Integer.valueOf((String) o2));
				else return -1*Integer.valueOf((String) o1).compareTo(Integer.valueOf((String) o2));				
			}

			if (o1 instanceof String && ((String) o1).length() == 0) {
				o1 = null;
			}
			if (o2 instanceof String && ((String) o2).length() == 0) {
				o2 = null;
			}

			if (o1 == null && o2 == null) {
				return 0;
			} else if (o1 == null) {
				return 1;
			} else if (o2 == null) {
				return -1;
			} else if (o1 instanceof Comparable) {
				if(_isAscending)
					return ((Comparable) o1).compareTo(o2);
				else return -1*((Comparable) o1).compareTo(o2);
			} else {
				if(_isAscending)
					return (o1.toString().compareTo(o2.toString()));
				else return -1*(o1.toString().compareTo(o2.toString()));
			}
	}
}
