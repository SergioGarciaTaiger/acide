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
 *      -Version from 0.12 to 0.16
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
package acide.gui.databasePanel.utils;

import java.awt.event.MouseEvent;
import javax.swing.JTree;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;

import acide.gui.databasePanel.Nodes.NodeICT;
import acide.language.AcideLanguageManager;

/**
 * ACIDE - A Configurable IDE database panel tree.
 * 
 * @version 0.14
 * @see JTree
 */
public class AcideTree extends JTree {

	private static final long serialVersionUID = 1L;
	
	public AcideTree(DefaultTreeModel _treeModel){
		super(_treeModel);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.awt.event.JTree#getToolTipText(java.awt.event.
	 * MouseEvent)
	 */
	@Override
	public String getToolTipText(MouseEvent evt) {
		if (getRowForLocation(evt.getX(), evt.getY()) == -1)
		    return null;
		
		    TreePath curPath = getPathForLocation(evt.getX(), evt.getY());
		    TreeNode node = (TreeNode)curPath.getLastPathComponent();
		    
		    if((node instanceof NodeICT)&& !isExpanded(curPath))
		    {
		    	String aux = ((NodeICT) node).toString();
		    	
		    	if(aux.equals("PK"))
		    		return AcideLanguageManager.getInstance().getLabels().getString("s2258");
				else if(aux.equals("FK"))
					return AcideLanguageManager.getInstance().getLabels().getString("s2259");
				else if(aux.equals("CK"))
					return AcideLanguageManager.getInstance().getLabels().getString("s2260");
				else if(aux.equals("FD"))
					return AcideLanguageManager.getInstance().getLabels().getString("s2261");
				else if(aux.equals("IC"))
					return AcideLanguageManager.getInstance().getLabels().getString("s2262");
		    }
			return null;   
		    
	}

}
