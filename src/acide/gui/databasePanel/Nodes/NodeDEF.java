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

import java.util.Vector;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

import acide.language.AcideLanguageManager;
import acide.process.console.AcideDatabaseManager;

/**
 * ACIDE - A Configurable IDE nodeDefinition.
 * 
 * @version 0.16
 * @see DefaultMutableTreeNode
 */
public class NodeDEF extends AcideDataBaseNodes {
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * ACIDE - Definition types  
	 */
	public  enum tipoDefinition {SQL, Datalog, RA}

	
	/**
	 * ACIDE - Type of the definition  
	 */
	private tipoDefinition _type;
	

	public NodeDEF(String text,tipoDefinition tipo,DefaultTreeModel _treeModel) {
		super(text,_treeModel);
		this._type=tipo;
	}

	public tipoDefinition getTipo() {
		return _type;
	}

	@Override
	public boolean update() {
		if(isBuilt()){
			setAllowsChildren(true);
			Vector<String> childs = getChilds();
			String vAux =getParent().toString();
			String view ="";
			
			if(vAux.contains("("))
				view=vAux.substring(0,vAux.indexOf("("));
			
			String db=getParent().getParent().getParent().toString();
			
			if(_type.equals(tipoDefinition.SQL)){
				
				this.setUserObject(AcideLanguageManager.getInstance().getLabels()
						.getString("s2036"));
				
				String text = AcideDatabaseManager.getInstance().getSQLText(db,view);
				
				text = text.replace('\n', ' ');
				if(childs.size()>0){
					if(!text.equals(childs.get(0))){
						DefaultMutableTreeNode d = getChild(childs.get(0));
						_treeModel.removeNodeFromParent(d);
						d.setUserObject(text);
						_treeModel.insertNodeInto(d, this, getChildCount());
					}
				}
				else{
					DefaultMutableTreeNode SQLtext = new DefaultMutableTreeNode(text);
					SQLtext.setAllowsChildren(false);
					_treeModel.insertNodeInto(SQLtext, this, this.getChildCount());
					}
				
			} else if(_type.equals(tipoDefinition.RA)){
				
				this.setUserObject(AcideLanguageManager.getInstance().getLabels()
						.getString("s2288"));
				
				String text = AcideDatabaseManager.getInstance().getRAText(db,view);
				
				text = text.replace('\n', ' ');
				
				if(childs.size()>0){
					if(!text.equals(childs.get(0))){
						DefaultMutableTreeNode d = getChild(childs.get(0));
						_treeModel.removeNodeFromParent(d);
						d.setUserObject(text);
						_treeModel.insertNodeInto(d, this, getChildCount());
					}
				}
				else{
					DefaultMutableTreeNode RAtext = new DefaultMutableTreeNode(text);
					RAtext.setAllowsChildren(false);
					_treeModel.insertNodeInto(RAtext, this, this.getChildCount());
					}
			}else{
					this.setUserObject(AcideLanguageManager.getInstance().getLabels()
							.getString("s2037"));
					
					String datalog = AcideDatabaseManager.getInstance().getDatalogText(db,view);
					
					datalog = datalog.replace('\n', ' ');
					
					if(childs.size()>0){
						if(!datalog.equals(childs.get(0))){
							DefaultMutableTreeNode d = getChild(childs.get(0));
							_treeModel.removeNodeFromParent(d);
							d.setUserObject(datalog);
							_treeModel.insertNodeInto(d, this, getChildCount());
						}
					}
					else{
						DefaultMutableTreeNode DatalogText = new DefaultMutableTreeNode(datalog);
						DatalogText.setAllowsChildren(false);
						_treeModel.insertNodeInto(DatalogText, this, this.getChildCount());
					}				
				}
			return true;
		}	
		return false;
	}
}
