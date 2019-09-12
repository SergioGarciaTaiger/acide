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
package acide.gui.menuBar.configurationMenu.debugPanelMenu;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import javax.swing.JCheckBoxMenuItem;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

import acide.configuration.menu.AcideInsertedItem;
import acide.configuration.menu.AcideInsertedItemListener;
import acide.configuration.menu.AcideInsertedMenu;
import acide.configuration.menu.AcideMenuItemConfiguration;
import acide.configuration.menu.AcideMenuItemsConfiguration;
import acide.configuration.menu.AcideMenuObjectConfiguration;
import acide.configuration.menu.AcideMenuSubmenuConfiguration;
import acide.gui.menuBar.configurationMenu.AcideConfigurationMenu;
import acide.language.AcideLanguageManager;

/**
 * ACIDE - A Configurable IDE debug panel menu.
 * 
 * @version 0.16
 * @see JMenu
 */
public class AcideDebugPanelMenu extends JMenu {

	/**
	 * ACIDE - A Configurable IDE debug panel menu class serial version UID.
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * ACIDE - A Configurable IDE debug menu debug menu name.
	 */		
	public static final String DEBUG_MENU_NAME = "Debug Panel";
	/**
	 * ACIDE - A Configurable IDE debug panel menu node shape menu name.
	 */
	public static final String NODE_SHAPE_NAME = "Node Shape";
	/**
	 * ACIDE - A Configurable IDE debug panel menu node color menu name.
	 */
	public static final String NODE_COLOR_NAME = "Node Color";
	/**
	 * ACIDE - A Configurable IDE debug panel menu selected node color menu name.
	 */
	public static final String SELECTED_NODE_COLOR_NAME = "Selected Node Color";
	/**
	 * ACIDE - A Configurable IDE debug panel menu selected node color menu name.
	 */
	//public static final String DEBUG_NODE_COLOR_NAME = "Debug Node Color";
	/**
	 * ACIDE - A Configurable IDE debug panel menu node size menu item name.
	 */
	public static final String NODE_SIZE_NAME = "Node Size";
	/**
	 * ACIDE - A Configurable IDE debug panel menu show labels check box menu item name.
	 */
	public static final String SHOW_LABELS_NAME = "Show Labels";
	/**
	 * ACIDE - A Configurable IDE debug panel menu arrow color menu name.
	 */
	public static final String ARROW_COLOR_NAME = "Arrow Color";
	/**
	 * ACIDE - A Configurable IDE debug panel menu arrow shape menu name.
	 */
	public static final String ARROW_SHAPE_NAME = "Arrow Shape";
	
	/**
	 * ACIDE - A Configurable IDE debug menu node shape menu item.
	 */
	private AcideDebugPanelNodeShapeMenu _nodeShapeMenu;
	/**
	 * ACIDE - A Configurable IDE debug menu node shape menu item has been inserted.
	 */
	private boolean _nodeShapeInserted;
	/**
	 * ACIDE - A Configurable IDE debug menu node color menu item.
	 */
	private JMenuItem _nodeColorMenuItem;
	/**
	 * ACIDE - A Configurable IDE debug menu node color menu item has been inserted.
	 */
	private boolean _nodeColorInserted;
	/**
	 * ACIDE - A Configurable IDE debug menu selected node color menu item.
	 */
	private JMenuItem _selectedNodeColorMenuItem;
	/**
	 * ACIDE - A Configurable IDE debug menu selected node color menu item.
	 */
	//private JMenuItem _debugNodeColorMenuItem;
	
	/**
	 * ACIDE - A Configurable IDE debug menu selected node color menu item has been inserted.
	 */
	private boolean _selectedNodeColorInserted;
	/**
	 * ACIDE - A Configurable IDE debug menu selected node color menu item has been inserted.
	 */
	//private boolean _debugNodeColorInserted;
	/**
	 * ACIDE - A Configurable IDE debug menu show labels menu item.
	 */
	private JCheckBoxMenuItem _showLabelsMenuItem;
	/**
	 * ACIDE - A Configurable IDE debug menu show labels menu item has been inserted.
	 */
	private boolean _showLabelsInserted;
	/**
	 * ACIDE - A Configurable IDE debug menu node size menu item.
	 */
	private JMenuItem _nodeSizeMenuItem;
	/**
	 * ACIDE - A Configurable IDE debug menu node color menu item has been inserted.
	 */
	private boolean _nodeSizeInserted;
	/**
	 * ACIDE - A Configurable IDE debug menu arrow color menu item.
	 */
	private AcideDebugPanelArrowColorMenu _arrowColorMenu;
	/**
	 * ACIDE - A Configurable IDE debug menu arrow color menu item has been inserted.
	 */
	private boolean _arrowColorInserted;
	/**
	 * ACIDE - A Configurable IDE debug menu arrow shape menu item.
	 */
	private AcideDebugPanelArrowShapeMenu _arrowShapeMenu;
	/**
	 * ACIDE - A Configurable IDE debug menu arrow shape menu item has been inserted.
	 */
	private boolean _arrowShapeInserted;
	
	/**
	 * ACIDE - A Configurable IDE inserted menus hashmap.
	 */
	private HashMap<String, AcideInsertedMenu> _insertedMenus;
	/**
	 * ACIDE - A Configurable IDE inserted items hashmap.
	 */
	private HashMap<String, AcideInsertedItem> _insertedItems;
	/**
	 * ACIDE - A Configurable IDE array list of inserted objects.
	 */
	private ArrayList<AcideMenuObjectConfiguration> _insertedObjects;
	
	public AcideDebugPanelMenu(){
		_nodeShapeInserted=false;
		_nodeColorInserted=false;
		_showLabelsInserted=false;
		_nodeSizeInserted=false;
		_arrowColorInserted=false;
		_arrowShapeInserted=false;
		_selectedNodeColorInserted=false;
		//_debugNodeColorInserted=false;
		
		_insertedItems = new HashMap<String, AcideInsertedItem>();
		
		_insertedMenus = new HashMap<String, AcideInsertedMenu>();
		
		_insertedObjects = new ArrayList<AcideMenuObjectConfiguration>();
		
		// Builds the menu configuration
		buildMenuConfiguration();
		
		// Builds the menu components
		buildComponents();

		// Adds the components to the menu
		addComponents();

		// Sets the text of the graph panel menu components
		setTextOfMenuComponents();
	}

	private void addComponents() {
		Iterator<Object> it = AcideMenuItemsConfiguration.getInstance()
				.getMenuItemsManager().getSubmenu(AcideConfigurationMenu.CONFIGURATION_MENU_NAME)
				.getItemsManager().getSubmenu(DEBUG_MENU_NAME).getItemsManager().managerIterator();
		while (it.hasNext()){
			AcideMenuObjectConfiguration ob = (AcideMenuObjectConfiguration) it.next();
			String name = ob.getName();
			if (name.equals(NODE_SHAPE_NAME)){
				// Adds the node shape display options menu item to the menu
				add(_nodeShapeMenu);
				_nodeShapeInserted = true;
			}else if (name.equals(NODE_COLOR_NAME)){
				// Adds the node color menu item to the menu
				add(_nodeColorMenuItem);
				_nodeColorInserted = true;
			}else if(name.equals(SHOW_LABELS_NAME)){
				// Adds the show labels check box menu item to the menu
				add(_showLabelsMenuItem);
				_showLabelsInserted = true;
			}else if (name.equals(NODE_SIZE_NAME)){
				// Adds the node size menu item to the menu
				add(_nodeSizeMenuItem);
				_nodeSizeInserted = true;
			}else if (name.equals(ARROW_COLOR_NAME)){
				// Adds the line arrow color menu to the menu
				add(_arrowColorMenu);
				_arrowColorInserted = true;
			}else if(name.equals(ARROW_SHAPE_NAME)){
				// Adds the line arrow shape menu to the menu
				add(_arrowShapeMenu);
				_arrowShapeInserted = true;
			}else if(name.equals(SELECTED_NODE_COLOR_NAME)){
				// Adds the line arrow shape menu to the menu
				add(_selectedNodeColorMenuItem);
				_selectedNodeColorInserted = true;
			}/*else if(name.equals(DEBUG_NODE_COLOR_NAME)){
				// Adds the line arrow shape menu to the menu
				add(_debugNodeColorMenuItem);
				_debugNodeColorInserted = true;
			}*/else{
				if (ob.isSubmenu()){
					add(_insertedMenus.get(ob.getName()));
				}else{
					add(_insertedItems.get(ob.getName()));
				}
			}
		}
		
		if (!_nodeShapeInserted)
			add(_nodeShapeMenu);
		if (!_nodeColorInserted)
			add(_nodeColorMenuItem);
		if(!_showLabelsInserted)
			add(_showLabelsMenuItem);
		if (!_nodeSizeInserted)
			add(_nodeSizeMenuItem);
		if (!_arrowColorInserted)
			add(_arrowColorMenu);
		if (!_arrowShapeInserted)
			add(_arrowShapeMenu);
		if(!_selectedNodeColorInserted)
			add(_selectedNodeColorMenuItem);
	/*	if(!_debugNodeColorInserted)
			add(_debugNodeColorMenuItem);*/
		
	}

	private void buildComponents() {
		if (!AcideMenuItemsConfiguration.getInstance().getMenuItemsManager()
				.getSubmenu(AcideConfigurationMenu.CONFIGURATION_MENU_NAME).hasSubmenu(DEBUG_MENU_NAME)){
			AcideMenuItemsConfiguration.getInstance().getMenuItemsManager()
				.getSubmenu(AcideConfigurationMenu.CONFIGURATION_MENU_NAME)
					.insertObject(new AcideMenuSubmenuConfiguration(DEBUG_MENU_NAME));
		}
		
		Iterator<Object> it = AcideMenuItemsConfiguration.getInstance()
				.getMenuItemsManager().getSubmenu(AcideConfigurationMenu.CONFIGURATION_MENU_NAME)
				.getItemsManager().getSubmenu(DEBUG_MENU_NAME).getItemsManager().managerIterator();
		
		while (it.hasNext()){
			AcideMenuObjectConfiguration ob = (AcideMenuObjectConfiguration) it.next();
			String name = ob.getName();
			if (isOriginal(name)){
				_insertedObjects.add(ob);
				if (ob.isSubmenu()){
					AcideMenuSubmenuConfiguration obSubmenu = (AcideMenuSubmenuConfiguration) ob;
					_insertedMenus.put(ob.getName(), new AcideInsertedMenu(obSubmenu));
				}else {
					AcideMenuItemConfiguration obItem = (AcideMenuItemConfiguration) ob;
					_insertedItems.put(obItem.getName(), new AcideInsertedItem(obItem));
				}
			}
		}
		
		
		// Creates the node shape menu item		
		_nodeShapeMenu = new AcideDebugPanelNodeShapeMenu();
		
		// Sets the node shape menu item name
		_nodeShapeMenu.setName(NODE_SHAPE_NAME);

		
		// Creates the node color menu item
		_nodeColorMenuItem = new JMenuItem();
			
		// Sets the node color menu item name
		_nodeColorMenuItem.setName(NODE_COLOR_NAME);
		
		// Creates the selected node color menu item
		_selectedNodeColorMenuItem = new JMenuItem();
					
		// Sets the selected node color menu item name
		_selectedNodeColorMenuItem.setName(SELECTED_NODE_COLOR_NAME);
		
	/*	// Creates the selected node color menu item
		_debugNodeColorMenuItem = new JMenuItem();

		// Sets the selected node color menu item name
		_debugNodeColorMenuItem.setName(DEBUG_NODE_COLOR_NAME);*/

		// Creates the show labels menu item
		_showLabelsMenuItem = new JCheckBoxMenuItem();

		// Sets the node show labels item name
		_showLabelsMenuItem.setName(SHOW_LABELS_NAME);
		
		// Sets the initial value to true
		_showLabelsMenuItem.setSelected(true);
		
		// Creates the node color menu item
		_nodeSizeMenuItem = new JMenuItem();
					
		// Sets the node color menu item name
		_nodeSizeMenuItem.setName(NODE_SIZE_NAME);

		
		// Creates the arrow color menu item
		_arrowColorMenu = new AcideDebugPanelArrowColorMenu();
		
		// Sets the arrow color menu item name
		_arrowColorMenu.setName(ARROW_COLOR_NAME);
		
		
		// Creates the arrow shape menu item
		_arrowShapeMenu = new AcideDebugPanelArrowShapeMenu();
				
		// Sets the arrow shape menu item name
		_arrowShapeMenu.setName(ARROW_SHAPE_NAME);
		
		
		
	}
	private boolean isOriginal(String name) {
		if(!(name.equals(NODE_COLOR_NAME) || 
				name.equals(SHOW_LABELS_NAME) ||
				name.equals(NODE_SIZE_NAME) ||
				name.equals(NODE_SHAPE_NAME) ||
				name.equals(ARROW_COLOR_NAME) ||
				name.equals(ARROW_SHAPE_NAME) ||
				name.equals(NODE_SIZE_NAME) ||
				name.equals(SELECTED_NODE_COLOR_NAME))) 
			return true;
		return false;
	}

	/**
	 * Checks that the menu configuration has the correct format
	 */
	private void buildMenuConfiguration() {
	
		
	}

	public void setTextOfMenuComponents() {
		//sets the node shape menu text
				_nodeShapeMenu.setText(AcideLanguageManager
						.getInstance().getLabels().getString("s2233"));
				
				_nodeShapeMenu.setTextOfMenuComponents();
				
				//sets the node color menu item text
				_nodeColorMenuItem.setText(AcideLanguageManager
						.getInstance().getLabels().getString("s2234"));
				//sets the show labels check box menu item text
				_showLabelsMenuItem.setText(AcideLanguageManager
						.getInstance().getLabels().getString("s2263"));
				//sets the node size menu item text
				_nodeSizeMenuItem.setText(AcideLanguageManager
						.getInstance().getLabels().getString("s2244"));
				//sets the arrow color menu text
				_arrowColorMenu.setText(AcideLanguageManager
						.getInstance().getLabels().getString("s2235"));
				//sets the selected node color menu item text
				_selectedNodeColorMenuItem.setText(AcideLanguageManager
						.getInstance().getLabels().getString("s2317"));
			/*	//sets the selected node color menu item text
				_debugNodeColorMenuItem.setText(AcideLanguageManager
						.getInstance().getLabels().getString("s2318"));*/
				
				_arrowColorMenu.setTextOfMenuComponents();
				//sets the arrow shape menu text
				_arrowShapeMenu.setText(AcideLanguageManager
						.getInstance().getLabels().getString("s2240"));
				
				_arrowShapeMenu.setTextOfMenuComponents();
				
				Iterator<AcideMenuObjectConfiguration> it = _insertedObjects.iterator();
				while (it.hasNext()){
					AcideMenuObjectConfiguration ob = it.next();
					if (ob.isSubmenu()){
						_insertedMenus.get(ob.getName()).setText(ob.getName());
						_insertedMenus.get(ob.getName()).setTextOfMenuComponents();
					}else{
						_insertedItems.get(ob.getName()).setText(ob.getName());
					}
				}
		
	}

	public void updateComponentsVisibility() {
		
		
	}

	public void setListeners() {
		//sets the node color menu item listener
				_nodeColorMenuItem.addActionListener(new AcideInsertedItemListener(
						AcideMenuItemsConfiguration.getInstance()
						.getSubmenu(AcideConfigurationMenu.CONFIGURATION_MENU_NAME)
						.getSubmenu(AcideDebugPanelMenu.DEBUG_MENU_NAME)
						.getItem(AcideDebugPanelMenu.NODE_COLOR_NAME)));

				//sets the node size item listener
				_nodeSizeMenuItem.addActionListener(new AcideInsertedItemListener(
						AcideMenuItemsConfiguration.getInstance()
						.getSubmenu(AcideConfigurationMenu.CONFIGURATION_MENU_NAME)
						.getSubmenu(AcideDebugPanelMenu.DEBUG_MENU_NAME)
						.getItem(AcideDebugPanelMenu.NODE_SIZE_NAME)));
				
				//sets the node show labels check box item listener
				_showLabelsMenuItem.addActionListener(new AcideInsertedItemListener(
						AcideMenuItemsConfiguration.getInstance()
						.getSubmenu(AcideConfigurationMenu.CONFIGURATION_MENU_NAME)
						.getSubmenu(AcideDebugPanelMenu.DEBUG_MENU_NAME)
						.getItem(AcideDebugPanelMenu.SHOW_LABELS_NAME)));
				
				//sets the node selected node color item listener
				_selectedNodeColorMenuItem.addActionListener(new AcideInsertedItemListener(
						AcideMenuItemsConfiguration.getInstance()
						.getSubmenu(AcideConfigurationMenu.CONFIGURATION_MENU_NAME)
						.getSubmenu(AcideDebugPanelMenu.DEBUG_MENU_NAME)
						.getItem(AcideDebugPanelMenu.SELECTED_NODE_COLOR_NAME)));
				
				//sets the node debug selected node color item listener
			/*	_debugNodeColorMenuItem.addActionListener(new AcideInsertedItemListener(
						AcideMenuItemsConfiguration.getInstance()
						.getSubmenu(AcideConfigurationMenu.CONFIGURATION_MENU_NAME)
						.getSubmenu(AcideDebugPanelMenu.DEBUG_MENU_NAME)
						.getItem(AcideDebugPanelMenu.DEBUG_NODE_COLOR_NAME)));*/
		
	}

	
	/**
	 * Returns the ACIDE - A Configurable IDE debug panel menu arrow shape menu.
	 * 
	 * @return the ACIDE - A Configurable IDE debug panel menu arrow shape menu.
	 */
	public AcideDebugPanelArrowShapeMenu get_arrowShapeMenu() {
		return _arrowShapeMenu;
	}

	/**
	 * Returns the ACIDE - A Configurable IDE debug panel menu node shape menu.
	 * 
	 * @return the ACIDE - A Configurable IDE debug panel menu node shape menu.
	 */
	public AcideDebugPanelNodeShapeMenu get_nodeShapeMenu() {
		return _nodeShapeMenu;
	}

	/**
	 * Returns the ACIDE - A Configurable IDE debug panel menu show labels menu item.
	 * 
	 * @return the ACIDE - A Configurable IDE debug panel menu show labels menu item.
	 */
	public JCheckBoxMenuItem get_showLabelsMenuItem() {
		
		return _showLabelsMenuItem;
	}

}
