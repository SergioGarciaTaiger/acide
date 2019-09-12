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

import javax.swing.JPopupMenu;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

import acide.gui.databasePanel.Nodes.NodeICT.tipoRestriccion;
import acide.gui.databasePanel.popup.AcideDataBasePanelConstraintPopupMenu;


/**
 * ACIDE - A Configurable IDE nodeConstraint.
 * 
 * @version 0.11
 * @see DefaultMutableTreeNode
 */
public class NodeConstraint extends AcideDataBaseNodes {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * ACIDE - Constraint type  
	 */
	private tipoRestriccion _type;
	
	
	public NodeConstraint(String text, tipoRestriccion tipo,DefaultTreeModel _treeModel) {
		super(text,_treeModel);
		this._type = tipo;
	}
	
	@Override
	public boolean update() {	
		if(isBuilt()){
			setAllowsChildren(true);
		}
			return true;
	}

	@Override
	public JPopupMenu getPopUp() {
		return new AcideDataBasePanelConstraintPopupMenu(_type);
	}

}
