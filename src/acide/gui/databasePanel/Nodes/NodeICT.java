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
package acide.gui.databasePanel.Nodes;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Vector;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

import acide.language.AcideLanguageManager;
import acide.process.console.AcideDatabaseManager;

/**
 * ACIDE - A Configurable IDE nodeICT.
 * 
 * @version 0.16
 * @see DefaultMutableTreeNode
 */
public class NodeICT  extends AcideDataBaseNodes {
	private static final long serialVersionUID = 1L;
	
	/**
	 * ACIDE - Constraints  types  
	 */
	public  enum tipoRestriccion {FD,FK,IC,PK,CK,NN,Res}
	
	/**
	 * ACIDE - Type of the constraint  
	 */
	private tipoRestriccion _type;

	public NodeICT(String text,tipoRestriccion tipo,DefaultTreeModel _treeModel) {
		super(text,_treeModel);
		this._type=tipo;
	}

	public tipoRestriccion getTipo() {
		return _type;
	}


	public void setTipo(tipoRestriccion tipo) {
		this._type = tipo;
	}
	
	@Override
	public boolean update() {	
	
		if(isBuilt()){
			setAllowsChildren(true);
			
			NodeTable sTable = (NodeTable) getParent();
			NodeDB dataBase = (NodeDB) getParent().getParent().getParent();
			
			String table = sTable.toString();
			if (table.contains("("))
				table = sTable.toString().substring(0, table.indexOf("("));

			Vector<String> childs = getChilds();
			
			if(_type.equals(tipoRestriccion.PK)){
				
				LinkedList<String> primKey= AcideDatabaseManager.getInstance().getPrimKey(dataBase.toString(), table);
				
				if(primKey.size()<childs.size()){ //hemos eliminado pk
					for(int j=0; j<childs.size();j++){
						String child = childs.get(j);
						if(!contains(primKey,child)){
							_treeModel.removeNodeFromParent(getChild(child));							
						}
					}
					return true;
				}
				if(primKey.size()>childs.size()){ //hemos añadido pk
					for(int h=0;h<primKey.size();h++){
						String aux= primKey.get(h);
						if(!childs.contains(aux)){
							NodeConstraint pKey = new NodeConstraint(aux, tipoRestriccion.PK,_treeModel);
							pKey.setAllowsChildren(false);
							insert(pKey,getChildCount());	
						}					
					}
					return true;
				}
				
				String fk= primKey.get(0);
				String child = childs.get(0);
				DefaultMutableTreeNode node = getChild(child);
				_treeModel.removeNodeFromParent(node);
				node.setUserObject(fk);
				_treeModel.insertNodeInto(node, this, this.getChildCount());
				
			}
			else if(_type.equals(tipoRestriccion.FK)){			

				LinkedList<String> fKey= AcideDatabaseManager.getInstance().getForeignKey(dataBase.toString(), table);
				if(fKey.size()<childs.size()){ //hemos eliminado fk
					for(int j=0; j<childs.size();j++){
						String child = childs.get(j);
						if(!contains(fKey,child)){
							_treeModel.removeNodeFromParent(getChild(child));							
						}
					}
					return true;
				}
				if(fKey.size()>childs.size()){ //hemos añadido fk
					for(int h=0;h<fKey.size();h++){
						String aux= fKey.get(h);
						if(!childs.contains(aux)){
							NodeConstraint foreignKey = new NodeConstraint(aux, tipoRestriccion.FK,_treeModel);
							foreignKey.setAllowsChildren(false);
							insert(foreignKey,getChildCount());	
						}					
					}
					return true;
				}
				//sino es que hemos podido renombrar
				for(int h=0;h<fKey.size();h++){
					String fk= fKey.get(h);
					for(int j =0; j<childs.size();j++){
						String child = childs.get(j);
					if(child.contains(fk.substring(fk.indexOf("["),fk.indexOf("]")))){
							DefaultMutableTreeNode node = getChild(child);
							_treeModel.removeNodeFromParent(node);
							node.setUserObject(fk);
							_treeModel.insertNodeInto(node, this, j);					
						}		
					}
				}
				
			}
			else if (_type.equals(tipoRestriccion.CK)) {

				LinkedList<String> candKey= AcideDatabaseManager.getInstance().getCandidateKey(dataBase.toString(), table);
				
				if(candKey.size()<childs.size()){ //hemos eliminado CK
					for(int j=0; j<childs.size();j++){
						String child = childs.get(j);
						if(!contains(candKey,child)){
							_treeModel.removeNodeFromParent(getChild(child));							
						}
					}
					return true;
				}
				if(candKey.size()>childs.size()){ //hemos añadido ck
					for(int h=0;h<candKey.size();h++){
						String aux= candKey.get(h);
						if(!childs.contains(aux)){
							NodeConstraint pKey = new NodeConstraint(aux, tipoRestriccion.CK,_treeModel);
							pKey.setAllowsChildren(false);
							insert(pKey,getChildCount());	
						}					
					}
					return true;
				}
				//si no, hemos podido renombrar
				for(int h=0;h<candKey.size();h++){
					String fk= candKey.get(h);
					for(int j =0; j<childs.size();j++){
						String child = childs.get(j);
					if(child.contains(fk.substring(fk.indexOf("["),fk.indexOf("]")))){
							DefaultMutableTreeNode node = getChild(child);
							_treeModel.removeNodeFromParent(node);
							node.setUserObject(fk);
							_treeModel.insertNodeInto(node, this, j);					
						}		
					}
				}
			}
			else if(_type.equals(tipoRestriccion.FD)){

				LinkedList<String> funDep= AcideDatabaseManager.getInstance().getFuncionDep(dataBase.toString(), table);
				if(funDep.size()<childs.size()){ //hemos eliminado fd
					for(int j=0; j<childs.size();j++){
						String child = childs.get(j);
						if(!contains(funDep,child)){
							_treeModel.removeNodeFromParent(getChild(child));							
						}
					}
					return true;
				}
				if(funDep.size()>childs.size()){ //hemos añadido fd
					for(int h=0;h<funDep.size();h++){
						String aux= funDep.get(h);
						if(!childs.contains(aux)){
							NodeConstraint pKey = new NodeConstraint(aux, tipoRestriccion.FD,_treeModel);
							pKey.setAllowsChildren(false);
							insert(pKey,getChildCount());	
						}					
					}
					return true;
				}
				//si no, hemos podido renombrar
				for(int h=0;h<funDep.size();h++){
					String fk= funDep.get(h);
					for(int j =0; j<childs.size();j++){
						String child = childs.get(j);
					if(child.contains(fk.substring(fk.indexOf("["),fk.indexOf("]")))){
							DefaultMutableTreeNode node = getChild(child);
							_treeModel.removeNodeFromParent(node);
							node.setUserObject(fk);
							_treeModel.insertNodeInto(node, this, j);					
						}		
					}
				}
			} else if(_type.equals(tipoRestriccion.IC)){
			
				LinkedList<String> IntConst= AcideDatabaseManager.getInstance().getIntConst(dataBase.toString(), table);
				if(IntConst.size()<childs.size()){ //hemos eliminado ic
					for(int j=0; j<childs.size();j++){
						String child = childs.get(j);
						if(!containsIC(IntConst,child)){
							_treeModel.removeNodeFromParent(getChildIC(child));							
						}
					}
					return true;
				} 
				if(IntConst.size()>=childs.size()){ //hemos añadido ic
					for(int h=0;h<IntConst.size();h++){
						String aux= IntConst.get(h);
						if(!childs.contains(aux)){
							NodeConstraint pKey = new NodeConstraint(aux, tipoRestriccion.IC,_treeModel);
							pKey.setAllowsChildren(false);
							//insert(pKey,getChildCount());
							_treeModel.insertNodeInto(pKey,this,this.getChildCount());
						}					
					}
					return true;
				}
				//si no, hemos podido renombrar
				for(int h=0;h<IntConst.size();h++){
					String fk= IntConst.get(h);
					for(int j =0; j<childs.size();j++){
						String child = childs.get(j);
					if(child.contains(fk.substring(fk.indexOf("["),fk.indexOf("]")))){
							DefaultMutableTreeNode node = getChild(child);
							_treeModel.removeNodeFromParent(node);
							node.setUserObject(fk);
							_treeModel.insertNodeInto(node, this, j);					
						}		
					}
				}
			} else if (_type.equals(tipoRestriccion.NN)){

				LinkedList<String> nullables = AcideDatabaseManager.getInstance().getNullables(dataBase.toString(), table);
				
				if(nullables.size()<childs.size()){ //hemos eliminado nl
					for(int j=0; j<childs.size();j++){
						String child = childs.get(j);
						if(!contains(nullables,child)){
							_treeModel.removeNodeFromParent(getChild(child));							
						}
					}
					return true;
				}
				if(nullables.size()>childs.size()){ //hemos añadido nn
					for(int h=0;h<nullables.size();h++){
						String aux= nullables.get(h);
						if(!childs.contains(aux)){
							NodeConstraint pKey = new NodeConstraint(aux, tipoRestriccion.NN,_treeModel);
							pKey.setAllowsChildren(false);
							insert(pKey,getChildCount());	
						}					
					}
					return true;
				}
				//si no, hemos podido renombrar
				for(int h=0;h<nullables.size();h++){
					String fk= nullables.get(h);
					for(int j =0; j<childs.size();j++){
						String child = childs.get(j);
					if(child.contains(fk.substring(fk.indexOf("["),fk.indexOf("]")))){
							DefaultMutableTreeNode node = getChild(child);
							_treeModel.removeNodeFromParent(node);
							node.setUserObject(fk);
							_treeModel.insertNodeInto(node, this, j);					
						}		
					}
				}
			}
			return true;
		}
		if(_type.equals(tipoRestriccion.Res))
			setUserObject(AcideLanguageManager.getInstance().getLabels().getString("s2029"));
		((DefaultTreeModel) _treeModel).nodeChanged(this);
		return true;
	}
	
	
	private boolean containsIC(LinkedList<String> intConst, String child) {
		Iterator<?> it = intConst.iterator();
		boolean found=false;
		while(it.hasNext() && !found){
			String table = (String) it.next();
			found = table.equals(child);
		}
		return found;
	}

	
	private boolean contains(LinkedList<String> tables, String child) {
		Iterator<?> it = tables.iterator();
		boolean found=false;
		while(it.hasNext() && !found){
			String table = (String) it.next();
			if (table.contains("("))
				table = table.substring(0,table.indexOf("("));
						
			found = table.equals(child);
		}
		return found;
	}

}
