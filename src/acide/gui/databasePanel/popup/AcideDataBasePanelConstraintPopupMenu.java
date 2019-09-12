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
package acide.gui.databasePanel.popup;

import javax.swing.ImageIcon;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

import acide.gui.databasePanel.Nodes.NodeICT.tipoRestriccion;
import acide.gui.databasePanel.popup.listeners.AcideDeleteConstraintMenuItemAction;
import acide.gui.databasePanel.popup.listeners.AcideModifyConstraintMenuItemAction;
import acide.language.AcideLanguageManager;

/**
 * ACIDE - A Configurable IDE data base panel constraint popup menu.
 * 
 * @version 0.11
 * @see JPopupMenu
 */
public class AcideDataBasePanelConstraintPopupMenu extends JPopupMenu {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * ACIDE - A Configurable IDE constraint node popup menu drop constraint menu item
	 * image icon.
	 */
	private static final ImageIcon DROP = new ImageIcon(
			"./resources/icons/database/drop.png");
	/**
	 * ACIDE - A Configurable IDE constraint node popup menu modify constraint menu item
	 * image icon.
	 */
	private static final ImageIcon MODIFY = new ImageIcon(
			"./resources/icons/database/modify.png");	
	/**
	 * ACIDE - A Configurable IDE constraint node popup menu drop constraint
	 * file menu item.
	 */
	private JMenuItem _dropConstraint;
	
	/**
	 * ACIDE - A Configurable IDE constraint node popup menu modify constraint
	 * file menu item.
	 */
	private JMenuItem _modifyConstraint;
	
	/**
	 * ACIDE - Constraint type  
	 */
	private tipoRestriccion _type;

	public AcideDataBasePanelConstraintPopupMenu(tipoRestriccion tipo) {
		super();
		this._type=tipo;
		buildComponents();
		addComponents();
		setListeners();
		
	}
	/**
	 * Adds the components to the ACIDE - A Configurable IDE constraint node
	 * popup menu.
	 */
	private void addComponents() {
		add(_dropConstraint);
		
		if(!_type.equals(tipoRestriccion.Res))
			add(_modifyConstraint);

		
	}
	/**
	 * Builds the ACIDE - A Configurable IDE constraint node popup menu
	 * components.
	 */
	private void buildComponents() {
		
		_dropConstraint= new JMenuItem(AcideLanguageManager
				.getInstance().getLabels().getString("s2050"),DROP);
		
		_modifyConstraint = new JMenuItem(AcideLanguageManager
				.getInstance().getLabels().getString("s2062"),MODIFY);
	
	}

	/**
	 * Sets the ACIDE - A Configurable IDE constraint node popup menu menu item
	 * listeners.
	 */
	private void setListeners() {
		_dropConstraint.addActionListener(new AcideDeleteConstraintMenuItemAction());
		_modifyConstraint.addActionListener(new AcideModifyConstraintMenuItemAction());
	}
}
