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
package acide.gui.databasePanel.constraintsMenu;

/**
 * ACIDE - A Configurable IDE constraints window control.
 * 
 * @version 0.16
 */
public class AcideConstraintDefinitionWindowControl {
	
	private int _windowIndex; //indica la ultima posicion valida de los arrays

	private AcideConstraintDefinitionWindow[] _constraintWindows;
	
	private String[] _windowsId; //ID --> nombre de la tabla para identificar el dataView

	
	public AcideConstraintDefinitionWindowControl getAcideConstraintDefinitionWindowControl(){
		return this;
	}
	
	public AcideConstraintDefinitionWindowControl(int tam) {
		super();
		_windowIndex=-1;
		_constraintWindows = new AcideConstraintDefinitionWindow[tam];
		_windowsId = new String[tam];
	}

	public AcideConstraintDefinitionWindowControl(int windowIndex,AcideConstraintDefinitionWindow[] constraintWindows, String[] windowsId) {
		super();
		this._windowIndex = windowIndex;
		this._constraintWindows = constraintWindows;
		this._windowsId = windowsId;
	}
	
	public AcideConstraintDefinitionWindow getConstraintWindow(String db,
			String table) {
		int i=0; 
		boolean found=false;
		while(i<=_windowIndex && !found){
			found = _windowsId[i].equals(db+table);
			i++;
		}
		if(found){	
			_constraintWindows[i-1].update();
			_constraintWindows[i-1].setVisible(true);
			return _constraintWindows[i-1];
		
		} else {
			insertWindow(db, table);
			//LinkedList<String> info = AcideDatabaseManager.getInstance().getSelectAll(db, table);
//			_constraintWindows[_windowIndex].build();
			return _constraintWindows[_windowIndex];
		}
	}
	
	private void insertWindow(String db, String table) {
		if(_windowIndex==_constraintWindows.length-1){			
			String[] aux = new String[_windowIndex+1]; //copiamos a un auxiliar
			AcideConstraintDefinitionWindow[] windowAux = new AcideConstraintDefinitionWindow[_windowIndex+1];
			for(int i=0; i< _constraintWindows.length; i++){
				aux[i] = _windowsId[i];
				windowAux[i] = _constraintWindows[i];
			}
			_constraintWindows = new AcideConstraintDefinitionWindow[_windowIndex+10];
			_windowsId = new String[_windowIndex+10];
			for(int i=0; i< aux.length; i++){
				_windowsId[i] = aux[i];
				_constraintWindows[i] =windowAux[i];
			}	
		}	
		_windowIndex++;
		_constraintWindows[_windowIndex] = new AcideConstraintDefinitionWindow(db, table);
		_windowsId[_windowIndex] = db+table;
		
	}
	
	public void update() {
		int i =0;
		while(i<=_windowIndex){
			_constraintWindows[i].update();
			i++;
		}
	}
	
	public void removeDataViewControl(int i){
		_constraintWindows[i]=null;
		_windowsId[i]="";
	}
	
	public int getWindowIndex() {
		return _windowIndex;
	}
	
	public void setWindowIndex(int windowIndex) {
		_windowIndex = windowIndex;
	}
	
	public AcideConstraintDefinitionWindow[] getConstraintWindows() {
		return _constraintWindows;
	}
	
	public void setConstraintWindows(
			AcideConstraintDefinitionWindow[] constraintWindows) {
		_constraintWindows = constraintWindows;
	}
	
	public String[] getWindowsId() {
		return _windowsId;
	}
	
	public void setWindowsId(String[] windowsId) {
		_windowsId = windowsId;
	}

}
