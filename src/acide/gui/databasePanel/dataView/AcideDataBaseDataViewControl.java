/*
 * ACIDE - A Configurable IDE
 * Official web site: http://acide.sourceforge.net
 * 
 * Copyright (C) 2007-2014  
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
package acide.gui.databasePanel.dataView;

import java.util.LinkedList;
import acide.process.console.AcideDatabaseManager;

/**
 * ACIDE - A Configurable IDE data base view control.
 * 
 * @version 0.16
 */
public class AcideDataBaseDataViewControl {
	
	private int _dataIndex; // last valid position
	private AcideDatabaseDataView[] _dataViews;
	private String[] _dataViewsId; // identifier to identify the data view belonging to an specific table and database
	
	public AcideDataBaseDataViewControl getAcideDataBaseDataViewControl(){
		return this;
	}
	
	public AcideDataBaseDataViewControl(int tam) {
		super();
		_dataIndex=-1;
		_dataViews = new AcideDatabaseDataView[tam];
		_dataViewsId = new String[tam];
	}

	public AcideDataBaseDataViewControl(int dataIndex,AcideDatabaseDataView[] dataViews, String[] dataViewsId) {
		super();
		this._dataIndex = dataIndex;
		this._dataViews = dataViews;
		this._dataViewsId = dataViewsId;
	}

	public int getDataIndex() {
		return _dataIndex;
	}

	public void setDataIndex(int dataIndex) {
		this._dataIndex = dataIndex;
	}

	public AcideDatabaseDataView[] getDataViews() {
		return _dataViews;
	}

	public void setDataViews(AcideDatabaseDataView[] dataViews) {
		this._dataViews = dataViews;
	}

	public String[] getDataViewsId() {
		return _dataViewsId;
	}

	public void setDataViewsId(String[] dataViewsId) {
		this._dataViewsId = dataViewsId;
	}
	
	public AcideDatabaseDataView getDataView(String db,String tableName){
		int i=0; 
		boolean found=false;
		while(i<=_dataIndex && !found){
			found = _dataViewsId[i].equals(db+tableName);
			i++;
		}
		if(found){	
			_dataViews[i-1].setVisible(true);
			return _dataViews[i-1];
		
		} else {
			insertDataView(db, tableName);
			LinkedList<String> info = AcideDatabaseManager.getInstance().getSelectAll(db, tableName);
			_dataViews[_dataIndex].build(info);
			return _dataViews[_dataIndex];
		}
	}
	
	private void insertDataView(String db, String table){
		
		if(_dataIndex==_dataViews.length-1){
			
			String[] aux = new String[_dataIndex+1];
			AcideDatabaseDataView[] dataViewAux = new AcideDatabaseDataView[_dataIndex+1];

			for(int i=0; i< _dataViews.length; i++){
				aux[i] = _dataViewsId[i];
				dataViewAux[i] = _dataViews[i];
			}
			_dataViews = new AcideDatabaseDataView[_dataIndex+10];
			_dataViewsId = new String[_dataIndex+10];

			for(int i=0; i< aux.length; i++){
				_dataViewsId[i] = aux[i];
				_dataViews[i] =dataViewAux[i];

			}	
		}	
		_dataIndex++;
		_dataViews[_dataIndex] = new AcideDatabaseDataView(db, table);
		_dataViewsId[_dataIndex] = db+table;
	}


	public void update() {
		int i =0;
		while(i<=_dataIndex){
			_dataViews[i].update();
			i++;
		}
	}
	
	public void removeDataViewControl(int i){
			_dataViews[i]=null;
			_dataViewsId[i]="";
	}
}
