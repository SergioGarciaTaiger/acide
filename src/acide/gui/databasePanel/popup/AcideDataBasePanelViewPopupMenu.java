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

import acide.gui.databasePanel.popup.listeners.AcideCopyViewMenuItemAction;
import acide.gui.databasePanel.popup.listeners.AcideDropViewMenuItemAction;
import acide.gui.databasePanel.popup.listeners.AcideEditViewDefinitionMenuItemAction;
import acide.gui.databasePanel.popup.listeners.AcidePasteViewMenuItemAction;
import acide.gui.databasePanel.popup.listeners.AcideRenameViewMenuItemAction;
import acide.gui.databasePanel.popup.listeners.AcideViewDataViewMenuItemAction;
import acide.language.AcideLanguageManager;

/**
 * ACIDE - A Configurable IDE data base panel view popup menu.
 * 
 * @version 0.15
 * @see JPopupMenu
 */
public class AcideDataBasePanelViewPopupMenu  extends JPopupMenu{

	private static final long serialVersionUID = 1L;
	
	private static final ImageIcon COPY= new ImageIcon(
			"./resources/icons/database/copy.png");
	
	private static final ImageIcon RENAME= new ImageIcon(
	"./resources/icons/database/rename.png");

	private static final ImageIcon PASTE= new ImageIcon(
	"./resources/icons/database/paste.png");
	
	/**
	 * ACIDE - A Configurable IDE explorer panel popup menu unset compilable
	 * file menu item.
	 */
	private JMenuItem _dropView;
	
	/**
	 * ACIDE - A Configurable IDE explorer panel popup menu unset compilable
	 * file menu item.
	 */
	private JMenuItem _renameView;
		
	/**
	 * ACIDE - A Configurable IDE explorer panel popup menu unset compilable
	 * file menu item.
	 */
	private JMenuItem _copyView;
		
	/**
	 * ACIDE - A Configurable IDE explorer panel popup menu unset compilable
	 * file menu item.
	 */
	private JMenuItem _pasteView;
	
	/**
	 * ACIDE - A Configurable IDE explorer panel popup menu unset compilable
	 * file menu item.
	 */
	private JMenuItem _DesignViewView;
	
	/**
	 * ACIDE - A Configurable IDE explorer panel popup menu unset compilable
	 * file menu item.
	 */
	private JMenuItem _DataViewView;
	
	
	/**
	 * Creates a new ACIDE - A Configurable IDE explorer panel popup menu.
	 */
	public AcideDataBasePanelViewPopupMenu() {

		buildComponents();
		addComponents();
		setListeners();
	}

	private void buildComponents() {
			
		_dropView= new JMenuItem(AcideLanguageManager.getInstance()
				.getLabels().getString("s2050"));
		
		_renameView= new JMenuItem(AcideLanguageManager.getInstance()
				.getLabels().getString("s2051"),RENAME);
		
		_copyView= new JMenuItem(AcideLanguageManager.getInstance()
				.getLabels().getString("s2052"),COPY);
		
		_pasteView= new JMenuItem(AcideLanguageManager.getInstance()
				.getLabels().getString("s2053"),PASTE);
		
		_DesignViewView= new JMenuItem(AcideLanguageManager.getInstance()
				.getLabels().getString("s2054"));		
		
		_DataViewView= new JMenuItem(AcideLanguageManager.getInstance()
				.getLabels().getString("s2055"));					
	}

	/**
	 * Adds the components to the ACIDE - A Configurable IDE explorer panel
	 * popup menu.
	 */
	private void addComponents() {
				
		add(_dropView);
		
		add(_renameView);
		
		add(new Separator());
		
		add(_copyView);
		
		add(_pasteView);
		
		add(new Separator());
		
		add(_DesignViewView);		
		
		add(_DataViewView);		
	}

	

	/**
	 * Sets the ACIDE - A Configurable IDE explorer panel popup menu menu item
	 * listeners.s
	 */
	private void setListeners() {

		_dropView.addActionListener(new AcideDropViewMenuItemAction());
		
		_renameView.addActionListener(new AcideRenameViewMenuItemAction());
		
		_copyView.addActionListener(new AcideCopyViewMenuItemAction());
		
		_pasteView.addActionListener(new AcidePasteViewMenuItemAction());
		
		_DesignViewView.addActionListener(new AcideEditViewDefinitionMenuItemAction(true,"View"));
		
		_DataViewView.addActionListener(new AcideViewDataViewMenuItemAction());
		
	}

}
