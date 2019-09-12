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
package acide.gui.databasePanel.popup;

import javax.swing.ImageIcon;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

import acide.gui.databasePanel.Nodes.NodeDefinition.tipoDefinition;
import acide.gui.databasePanel.popup.listeners.AcideCopyViewDefinitionMenuItemAction;
import acide.gui.databasePanel.popup.listeners.AcideEditViewDefinitionMenuItemAction;
import acide.language.AcideLanguageManager;

/**
 * ACIDE - A Configurable IDE data base panel definitions popup menu.
 * 
 * @version 0.11
 * @see JPopupMenu
 */
public class AcideDataBasePanelDefinitionsPopupMenu extends JPopupMenu {	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * ACIDE - A Configurable IDE definition SQL/RA/datalog node popup menu copy  menu item
	 * image icon.
	 */
	private static final ImageIcon COPIAR = new ImageIcon(
			"./resources/icons/database/copy.png");
	/**
	 * ACIDE - A Configurable IDE definition SQL/RA/datalog node popup menu show menu
	 * item.
	 */
	private JMenuItem _mostrar;
	/**
	 * ACIDE - A Configurable IDE definition SQL/RA/datalog node popup menu copy menu
	 * item.
	 */
	private JMenuItem _copiar;
	
	/**
	 * ACIDE - Constraint type  
	 */
	private tipoDefinition _type;

	
	public AcideDataBasePanelDefinitionsPopupMenu(tipoDefinition tipo) {
		super();
		this._type = tipo;
		buildComponents();
		addComponents();
		setListeners();

	}
	/**
	 * Adds the components to the ACIDE - A Configurable IDE definition SQL/RA/datalog node
	 * popup menu.
	 */
	private void addComponents() {

		add(_mostrar);
		add(_copiar);

	}
	/**
	 * Builds the ACIDE - A Configurable IDE definition SQL/RA/datalog panel popup menu
	 * components.
	 */
	private void buildComponents() {
		
		_mostrar = new JMenuItem(AcideLanguageManager.getInstance()
				.getLabels().getString("s2140"));
		
		_copiar = new JMenuItem(AcideLanguageManager.getInstance()
				.getLabels().getString("s23"), COPIAR);
	}

	/**
	 * Sets the ACIDE - A Configurable IDE definition SQL/RA/datalog panel popup menu menu item
	 * listeners.
	 */
	private void setListeners() {
		
		 String type = "";
		 
		 if(_type.equals(tipoDefinition.SQL)) {
			 type = "SQL";
		 } else if (_type.equals(tipoDefinition.RA)){
			 type = "RA";
		 } else type = "DATALOG";
		 
		 _mostrar.addActionListener(new AcideEditViewDefinitionMenuItemAction(true,type));

		_copiar.addActionListener(new AcideCopyViewDefinitionMenuItemAction());
	}
	
	public JMenuItem get_CreateTableWithDatalog() {
		return _mostrar;
	}
	public void set_CreateTableWithDatalog(JMenuItem createTableWithDatalog) {
		_mostrar = createTableWithDatalog;
	}
	public JMenuItem get_copiar() {
		return _copiar;
	}
	public void set_copiar(JMenuItem copiar) {
		_copiar = copiar;
	}
	
	
	
}
