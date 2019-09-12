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
package acide.gui.menuBar.configurationMenu.graphPanelMenu;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import javax.swing.JCheckBoxMenuItem;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

import acide.configuration.menu.AcideInsertedItem;
import acide.configuration.menu.AcideInsertedItemListener;
import acide.configuration.menu.AcideInsertedMenu;
import acide.configuration.menu.AcideMenuConfiguration;
import acide.configuration.menu.AcideMenuItemConfiguration;
import acide.configuration.menu.AcideMenuItemsConfiguration;
import acide.configuration.menu.AcideMenuObjectConfiguration;
import acide.configuration.menu.AcideMenuSubmenuConfiguration;
import acide.gui.menuBar.configurationMenu.AcideConfigurationMenu;
import acide.language.AcideLanguageManager;

/**
 * ACIDE - A Configurable IDE graph panel menu.
 * 
 * @version 0.12
 * @see JMenu
 */
public class AcideGraphPanelMenu extends JMenu {

	/**
	 * ACIDE - A Configurable IDE graph panel menu class serial version UID.
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * ACIDE - A Configurable IDE graph panel menu graph menu name.
	 */
	public static final String GRAPH_MENU_NAME = "Graph Panel";
	/**
	 * ACIDE - A Configurable IDE graph panel menu node shape menu name.
	 */
	public static final String NODE_SHAPE_NAME = "Node Shape";
	/**
	 * ACIDE - A Configurable IDE graph panel menu node color menu name.
	 */
	public static final String NODE_COLOR_NAME = "Node Color";
	/**
	 * ACIDE - A Configurable IDE graph panel menu node size menu item name.
	 */
	public static final String NODE_SIZE_NAME = "Node Size";
	/**
	 * ACIDE - A Configurable IDE graph panel menu show labels check box menu item name.
	 */
	public static final String SHOW_LABELS_NAME = "Show Labels";
	/**
	 * ACIDE - A Configurable IDE graph panel menu arrow color menu name.
	 */
	public static final String ARROW_COLOR_NAME = "Arrow Color";
	/**
	 * ACIDE - A Configurable IDE graph panel menu arrow shape menu name.
	 */
	public static final String ARROW_SHAPE_NAME = "Arrow Shape";
	
	/**
	 * ACIDE - A Configurable IDE graph menu node shape menu item.
	 */
	private AcideGraphPanelNodeShapeMenu _nodeShapeMenu;
	/**
	 * ACIDE - A Configurable IDE graph menu node shape menu item has been inserted.
	 */
	private boolean _nodeShapeInserted;
	/**
	 * ACIDE - A Configurable IDE graph menu node color menu item.
	 */
	private JMenuItem _nodeColorMenuItem;
	/**
	 * ACIDE - A Configurable IDE graph menu node color menu item has been inserted.
	 */
	private boolean _nodeColorInserted;
	/**
	 * ACIDE - A Configurable IDE graph menu show labels menu item.
	 */
	private JCheckBoxMenuItem _showLabelsMenuItem;
	/**
	 * ACIDE - A Configurable IDE graph menu show labels menu item has been inserted.
	 */
	private boolean _showLabelsInserted;
	/**
	 * ACIDE - A Configurable IDE graph menu node size menu item.
	 */
	private JMenuItem _nodeSizeMenuItem;
	/**
	 * ACIDE - A Configurable IDE graph menu node color menu item has been inserted.
	 */
	private boolean _nodeSizeInserted;
	/**
	 * ACIDE - A Configurable IDE graph menu arrow color menu item.
	 */
	private AcideGraphPanelArrowColorMenu _arrowColorMenu;
	/**
	 * ACIDE - A Configurable IDE graph menu arrow color menu item has been inserted.
	 */
	private boolean _arrowColorInserted;
	/**
	 * ACIDE - A Configurable IDE graph menu arrow shape menu item.
	 */
	private AcideGraphPanelArrowShapeMenu _arrowShapeMenu;
	/**
	 * ACIDE - A Configurable IDE graph menu arrow shape menu item has been inserted.
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
	/**
	 * Creates a new ACIDE - A Configurable IDE graph panel menu.
	 */
	public AcideGraphPanelMenu(){
		
		_nodeShapeInserted=false;
		_nodeColorInserted=false;
		_showLabelsInserted=false;
		_nodeSizeInserted=false;
		_arrowColorInserted=false;
		_arrowShapeInserted=false;
		
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
	/**
	 * Checks that the menu configuration has the correct format
	 */
	private void buildMenuConfiguration() {
		if (!AcideMenuItemsConfiguration.getInstance()
				.getSubmenu(AcideConfigurationMenu.CONFIGURATION_MENU_NAME)
				.hasSubmenu(AcideGraphPanelMenu.GRAPH_MENU_NAME)){
			AcideMenuSubmenuConfiguration graph = AcideMenuItemsConfiguration
					.getInstance().getGraphDefaultSubmenu();
			
			AcideMenuItemConfiguration nodeColor = graph
					.getItem(AcideGraphPanelMenu.NODE_COLOR_NAME);
			nodeColor.setVisible(AcideMenuConfiguration.getInstance()
					.getIsDisplayed(AcideGraphPanelMenu.NODE_COLOR_NAME));
			
			AcideMenuItemConfiguration showLabels = graph
					.getItem(AcideGraphPanelMenu.SHOW_LABELS_NAME);
			showLabels.setVisible(AcideMenuConfiguration.getInstance()
					.getIsDisplayed(AcideGraphPanelMenu.SHOW_LABELS_NAME));
			
			AcideMenuItemConfiguration nodeSize = graph
					.getItem(AcideGraphPanelMenu.NODE_SIZE_NAME);
			nodeSize.setVisible(AcideMenuConfiguration.getInstance()
					.getIsDisplayed(AcideGraphPanelMenu.NODE_SIZE_NAME));
			
			AcideMenuSubmenuConfiguration nodeShape = AcideMenuItemsConfiguration
					.getInstance().getNodeShapeDefaultSubmenu();
			nodeShape.setVisible(AcideMenuConfiguration.getInstance()
					.getIsDisplayed(AcideGraphPanelMenu.NODE_SHAPE_NAME));
			
			AcideMenuSubmenuConfiguration arrowColor = AcideMenuItemsConfiguration
					.getInstance().getArrowColorDefaultSubmenu();
			arrowColor.setVisible(AcideMenuConfiguration.getInstance()
					.getIsDisplayed(AcideGraphPanelMenu.ARROW_COLOR_NAME));
			
			AcideMenuSubmenuConfiguration arrowShape = AcideMenuItemsConfiguration
					.getInstance().getArrowColorDefaultSubmenu();
			arrowShape.setVisible(AcideMenuConfiguration.getInstance()
					.getIsDisplayed(AcideGraphPanelArrowShapeMenu.ARROW_SHAPE_MENU_NAME));
		}else{
			AcideMenuSubmenuConfiguration graph = AcideMenuItemsConfiguration
					.getInstance().getSubmenu(
							AcideConfigurationMenu.CONFIGURATION_MENU_NAME)
					.getSubmenu(AcideGraphPanelMenu.GRAPH_MENU_NAME);
			
			AcideMenuItemConfiguration nodeColor;
			if(graph.hasItem(AcideGraphPanelMenu.NODE_COLOR_NAME)){
				nodeColor = graph
						.getItem(AcideGraphPanelMenu.NODE_COLOR_NAME);
				nodeColor.setVisible(AcideMenuConfiguration.getInstance()
						.getIsDisplayed(AcideGraphPanelMenu.NODE_COLOR_NAME));
				AcideMenuConfiguration.getInstance()
				.setIsDisplayed(AcideGraphPanelMenu.NODE_COLOR_NAME,
						nodeColor.isVisible());
				AcideMenuItemsConfiguration
				.getInstance()
				.getSubmenu(
						AcideConfigurationMenu.CONFIGURATION_MENU_NAME)
				.getSubmenu(AcideGraphPanelMenu.GRAPH_MENU_NAME)
				.getItemsManager()
				.onlyOne(AcideGraphPanelMenu.NODE_COLOR_NAME);
			}else{
				nodeColor = new AcideMenuItemConfiguration(
						AcideGraphPanelMenu.NODE_COLOR_NAME);
				nodeColor.setVisible(AcideMenuConfiguration.getInstance()
						.getIsDisplayed(AcideGraphPanelMenu.NODE_COLOR_NAME));
				graph.insertObject(nodeColor);
			}
			nodeColor.setErasable(false);
			nodeColor.setParameter("None");
			
			AcideMenuItemConfiguration showLabels;
			if(graph.hasItem(AcideGraphPanelMenu.SHOW_LABELS_NAME)){
				showLabels = graph
						.getItem(AcideGraphPanelMenu.SHOW_LABELS_NAME);
				showLabels.setVisible(AcideMenuConfiguration.getInstance()
						.getIsDisplayed(AcideGraphPanelMenu.SHOW_LABELS_NAME));
				AcideMenuConfiguration.getInstance()
				.setIsDisplayed(AcideGraphPanelMenu.SHOW_LABELS_NAME,
						showLabels.isVisible());
				AcideMenuItemsConfiguration
				.getInstance()
				.getSubmenu(
						AcideConfigurationMenu.CONFIGURATION_MENU_NAME)
				.getSubmenu(AcideGraphPanelMenu.GRAPH_MENU_NAME)
				.getItemsManager()
				.onlyOne(AcideGraphPanelMenu.SHOW_LABELS_NAME);
			}else{
				showLabels = new AcideMenuItemConfiguration(
						AcideGraphPanelMenu.SHOW_LABELS_NAME);
				showLabels.setVisible(AcideMenuConfiguration.getInstance()
						.getIsDisplayed(AcideGraphPanelMenu.SHOW_LABELS_NAME));
				graph.insertObject(showLabels);
			}
			showLabels.setErasable(false);
			showLabels.setParameter("None");
			
			AcideMenuItemConfiguration nodeSize;
			if(graph.hasItem(AcideGraphPanelMenu.NODE_SIZE_NAME)){
				nodeSize = graph
						.getItem(AcideGraphPanelMenu.NODE_SIZE_NAME);
				nodeSize.setVisible(AcideMenuConfiguration.getInstance()
						.getIsDisplayed(AcideGraphPanelMenu.NODE_SIZE_NAME));
				AcideMenuConfiguration.getInstance()
				.setIsDisplayed(AcideGraphPanelMenu.NODE_SIZE_NAME,
						nodeSize.isVisible());
				AcideMenuItemsConfiguration
				.getInstance()
				.getSubmenu(
						AcideConfigurationMenu.CONFIGURATION_MENU_NAME)
				.getSubmenu(AcideGraphPanelMenu.GRAPH_MENU_NAME)
				.getItemsManager()
				.onlyOne(AcideGraphPanelMenu.NODE_SIZE_NAME);
			}else{
				nodeSize = new AcideMenuItemConfiguration(
						AcideGraphPanelMenu.NODE_SIZE_NAME);
				nodeSize.setVisible(AcideMenuConfiguration.getInstance()
						.getIsDisplayed(AcideGraphPanelMenu.NODE_SIZE_NAME));
				graph.insertObject(nodeSize);
			}
			nodeSize.setErasable(false);
			nodeSize.setParameter("None");
			
			if (!graph
					.hasSubmenu(AcideGraphPanelArrowShapeMenu.ARROW_SHAPE_MENU_NAME)) {
				
				AcideMenuSubmenuConfiguration arrowShape = AcideMenuItemsConfiguration
						.getInstance().getArrowShapeDefaultSubmenu();
				
				AcideMenuItemConfiguration shapeLine = arrowShape
						.getItem(AcideGraphPanelArrowShapeMenu.ARROW_SHAPE_LINE);
				shapeLine.setVisible(AcideMenuConfiguration.getInstance()
						.getIsDisplayed(AcideGraphPanelArrowShapeMenu.ARROW_SHAPE_LINE));
				
				AcideMenuItemConfiguration shapePolygon = arrowShape
						.getItem(AcideGraphPanelArrowShapeMenu.ARROW_SHAPE_POLYGON);
				shapePolygon.setVisible(AcideMenuConfiguration.getInstance()
						.getIsDisplayed(AcideGraphPanelArrowShapeMenu.ARROW_SHAPE_POLYGON));
			}else{
				AcideMenuSubmenuConfiguration arrowShape = graph
						.getSubmenu(AcideGraphPanelArrowShapeMenu.ARROW_SHAPE_MENU_NAME);
				
				AcideMenuItemConfiguration shapeLine;
									
				if(arrowShape.hasItem(AcideGraphPanelArrowShapeMenu.ARROW_SHAPE_LINE)){
					shapeLine = arrowShape
							.getItem(AcideGraphPanelArrowShapeMenu.ARROW_SHAPE_LINE);
					shapeLine.setVisible(AcideMenuConfiguration.getInstance()
							.getIsDisplayed(AcideGraphPanelArrowShapeMenu.ARROW_SHAPE_LINE));
					AcideMenuConfiguration.getInstance()
					.setIsDisplayed(AcideGraphPanelArrowShapeMenu.ARROW_SHAPE_LINE,
							shapeLine.isVisible());
					AcideMenuItemsConfiguration
					.getInstance()
					.getSubmenu(
							AcideConfigurationMenu.CONFIGURATION_MENU_NAME)
					.getSubmenu(AcideGraphPanelMenu.GRAPH_MENU_NAME)
					.getSubmenu(AcideGraphPanelArrowShapeMenu.ARROW_SHAPE_MENU_NAME)
					.getItemsManager()
					.onlyOne(AcideGraphPanelArrowShapeMenu.ARROW_SHAPE_LINE);
				}else{
					shapeLine = new AcideMenuItemConfiguration(
							AcideGraphPanelArrowShapeMenu.ARROW_SHAPE_MENU_NAME);
					shapeLine.setVisible(AcideMenuConfiguration.getInstance()
							.getIsDisplayed(AcideGraphPanelArrowShapeMenu.ARROW_SHAPE_LINE));
					arrowShape.insertObject(shapeLine);
				}
				
				shapeLine.setErasable(false);
				shapeLine.setParameter("None");
				
				AcideMenuItemConfiguration shapePolygon;
				
				if(arrowShape.hasItem(AcideGraphPanelArrowShapeMenu.ARROW_SHAPE_POLYGON)){
					shapePolygon = arrowShape
							.getItem(AcideGraphPanelArrowShapeMenu.ARROW_SHAPE_POLYGON);
					shapePolygon.setVisible(AcideMenuConfiguration.getInstance()
							.getIsDisplayed(AcideGraphPanelArrowShapeMenu.ARROW_SHAPE_POLYGON));
					AcideMenuConfiguration.getInstance()
					.setIsDisplayed(AcideGraphPanelArrowShapeMenu.ARROW_SHAPE_POLYGON,
							nodeColor.isVisible());
					AcideMenuItemsConfiguration
					.getInstance()
					.getSubmenu(
							AcideConfigurationMenu.CONFIGURATION_MENU_NAME)
					.getSubmenu(AcideGraphPanelMenu.GRAPH_MENU_NAME)
					.getSubmenu(AcideGraphPanelArrowShapeMenu.ARROW_SHAPE_MENU_NAME)
					.getItemsManager()
					.onlyOne(AcideGraphPanelArrowShapeMenu.ARROW_SHAPE_POLYGON);
				}else{
					shapePolygon = new AcideMenuItemConfiguration(
							AcideGraphPanelArrowShapeMenu.ARROW_SHAPE_MENU_NAME);
					shapePolygon.setVisible(AcideMenuConfiguration.getInstance()
							.getIsDisplayed(AcideGraphPanelArrowShapeMenu.ARROW_SHAPE_POLYGON));
					arrowShape.insertObject(shapePolygon);
				}
				
				shapePolygon.setErasable(false);
				shapePolygon.setParameter("None");
			}
			
			if (!graph
					.hasSubmenu(AcideGraphPanelNodeShapeMenu.NODE_SHAPE_MENU_NAME)) {
				
				AcideMenuSubmenuConfiguration nodeShape = AcideMenuItemsConfiguration
						.getInstance().getNodeShapeDefaultSubmenu();
				
				AcideMenuItemConfiguration shapeCircle = nodeShape
						.getItem(AcideGraphPanelNodeShapeMenu.NODE_SHAPE_CIRCLE);
				shapeCircle.setVisible(AcideMenuConfiguration.getInstance()
						.getIsDisplayed(AcideGraphPanelNodeShapeMenu.NODE_SHAPE_CIRCLE));
				
				AcideMenuItemConfiguration shapeSquare = nodeShape
						.getItem(AcideGraphPanelNodeShapeMenu.NODE_SHAPE_SQUARE);
				shapeSquare.setVisible(AcideMenuConfiguration.getInstance()
						.getIsDisplayed(AcideGraphPanelNodeShapeMenu.NODE_SHAPE_SQUARE));
			}else{
				AcideMenuSubmenuConfiguration nodeShape = graph
						.getSubmenu(AcideGraphPanelNodeShapeMenu.NODE_SHAPE_MENU_NAME);
				
				AcideMenuItemConfiguration shapeCircle;
									
				if(nodeShape.hasItem(AcideGraphPanelNodeShapeMenu.NODE_SHAPE_CIRCLE)){
					shapeCircle = nodeShape
							.getItem(AcideGraphPanelNodeShapeMenu.NODE_SHAPE_CIRCLE);
					shapeCircle.setVisible(AcideMenuConfiguration.getInstance()
							.getIsDisplayed(AcideGraphPanelNodeShapeMenu.NODE_SHAPE_CIRCLE));
					AcideMenuConfiguration.getInstance()
					.setIsDisplayed(AcideGraphPanelNodeShapeMenu.NODE_SHAPE_CIRCLE,
							nodeColor.isVisible());
					AcideMenuItemsConfiguration
					.getInstance()
					.getSubmenu(
							AcideConfigurationMenu.CONFIGURATION_MENU_NAME)
					.getSubmenu(AcideGraphPanelMenu.GRAPH_MENU_NAME)
					.getSubmenu(AcideGraphPanelNodeShapeMenu.NODE_SHAPE_MENU_NAME)
					.getItemsManager()
					.onlyOne(AcideGraphPanelNodeShapeMenu.NODE_SHAPE_CIRCLE);
				}else{
					shapeCircle = new AcideMenuItemConfiguration(
							AcideGraphPanelNodeShapeMenu.NODE_SHAPE_MENU_NAME);
					shapeCircle.setVisible(AcideMenuConfiguration.getInstance()
							.getIsDisplayed(AcideGraphPanelNodeShapeMenu.NODE_SHAPE_CIRCLE));
					nodeShape.insertObject(shapeCircle);
				}
				
				shapeCircle.setErasable(false);
				shapeCircle.setParameter("None");
				
				AcideMenuItemConfiguration shapeSquare;
				
				if(nodeShape.hasItem(AcideGraphPanelNodeShapeMenu.NODE_SHAPE_SQUARE)){
					shapeSquare = nodeShape
							.getItem(AcideGraphPanelNodeShapeMenu.NODE_SHAPE_SQUARE);
					shapeSquare.setVisible(AcideMenuConfiguration.getInstance()
							.getIsDisplayed(AcideGraphPanelNodeShapeMenu.NODE_SHAPE_SQUARE));
					AcideMenuConfiguration.getInstance()
					.setIsDisplayed(AcideGraphPanelNodeShapeMenu.NODE_SHAPE_SQUARE,
							nodeColor.isVisible());
					AcideMenuItemsConfiguration
					.getInstance()
					.getSubmenu(
							AcideConfigurationMenu.CONFIGURATION_MENU_NAME)
					.getSubmenu(AcideGraphPanelMenu.GRAPH_MENU_NAME)
					.getSubmenu(AcideGraphPanelNodeShapeMenu.NODE_SHAPE_MENU_NAME)
					.getItemsManager()
					.onlyOne(AcideGraphPanelNodeShapeMenu.NODE_SHAPE_SQUARE);
				}else{
					shapeSquare = new AcideMenuItemConfiguration(
							AcideGraphPanelNodeShapeMenu.NODE_SHAPE_MENU_NAME);
					shapeSquare.setVisible(AcideMenuConfiguration.getInstance()
							.getIsDisplayed(AcideGraphPanelNodeShapeMenu.NODE_SHAPE_SQUARE));
					nodeShape.insertObject(shapeSquare);
				}
				
				shapeSquare.setErasable(false);
				shapeSquare.setParameter("None");
			}
			
			if (!graph
					.hasSubmenu(AcideGraphPanelArrowColorMenu.ARROW_COLOR_MENU_NAME)) {
				
				AcideMenuSubmenuConfiguration arrowColor = AcideMenuItemsConfiguration
						.getInstance().getArrowColorDefaultSubmenu();
				
				AcideMenuItemConfiguration colorDirect = arrowColor
						.getItem(AcideGraphPanelArrowColorMenu.ARROW_COLOR_DIRECT);
				colorDirect.setVisible(AcideMenuConfiguration.getInstance()
						.getIsDisplayed(AcideGraphPanelArrowColorMenu.ARROW_COLOR_DIRECT));
				
				AcideMenuItemConfiguration colorInverse = arrowColor
						.getItem(AcideGraphPanelArrowColorMenu.ARROW_COLOR_INVERSE);
				colorInverse.setVisible(AcideMenuConfiguration.getInstance()
						.getIsDisplayed(AcideGraphPanelArrowColorMenu.ARROW_COLOR_INVERSE));
			}else{
				AcideMenuSubmenuConfiguration arrowColor = graph
						.getSubmenu(AcideGraphPanelArrowColorMenu.ARROW_COLOR_MENU_NAME);
				
				AcideMenuItemConfiguration colorDirect;
									
				if(arrowColor.hasItem(AcideGraphPanelArrowColorMenu.ARROW_COLOR_DIRECT)){
					colorDirect = arrowColor
							.getItem(AcideGraphPanelArrowColorMenu.ARROW_COLOR_DIRECT);
					colorDirect.setVisible(AcideMenuConfiguration.getInstance()
							.getIsDisplayed(AcideGraphPanelArrowColorMenu.ARROW_COLOR_DIRECT));
					AcideMenuConfiguration.getInstance()
					.setIsDisplayed(AcideGraphPanelArrowColorMenu.ARROW_COLOR_DIRECT,
							colorDirect.isVisible());
					AcideMenuItemsConfiguration
					.getInstance()
					.getSubmenu(
							AcideConfigurationMenu.CONFIGURATION_MENU_NAME)
					.getSubmenu(AcideGraphPanelMenu.GRAPH_MENU_NAME)
					.getSubmenu(AcideGraphPanelArrowColorMenu.ARROW_COLOR_MENU_NAME)
					.getItemsManager()
					.onlyOne(AcideGraphPanelArrowColorMenu.ARROW_COLOR_DIRECT);
				}else{
					colorDirect = new AcideMenuItemConfiguration(
							AcideGraphPanelArrowColorMenu.ARROW_COLOR_MENU_NAME);
					colorDirect.setVisible(AcideMenuConfiguration.getInstance()
							.getIsDisplayed(AcideGraphPanelArrowColorMenu.ARROW_COLOR_DIRECT));
					arrowColor.insertObject(colorDirect);
				}
				
				colorDirect.setErasable(false);
				colorDirect.setParameter("None");
				
				AcideMenuItemConfiguration colorInverse;
				
				if(arrowColor.hasItem(AcideGraphPanelArrowColorMenu.ARROW_COLOR_INVERSE)){
					colorInverse = arrowColor
							.getItem(AcideGraphPanelArrowColorMenu.ARROW_COLOR_INVERSE);
					colorInverse.setVisible(AcideMenuConfiguration.getInstance()
							.getIsDisplayed(AcideGraphPanelArrowColorMenu.ARROW_COLOR_INVERSE));
					AcideMenuConfiguration.getInstance()
					.setIsDisplayed(AcideGraphPanelArrowColorMenu.ARROW_COLOR_INVERSE,
							nodeColor.isVisible());
					AcideMenuItemsConfiguration
					.getInstance()
					.getSubmenu(
							AcideConfigurationMenu.CONFIGURATION_MENU_NAME)
					.getSubmenu(AcideGraphPanelMenu.GRAPH_MENU_NAME)
					.getSubmenu(AcideGraphPanelArrowColorMenu.ARROW_COLOR_MENU_NAME)
					.getItemsManager()
					.onlyOne(AcideGraphPanelArrowColorMenu.ARROW_COLOR_INVERSE);
				}else{
					colorInverse = new AcideMenuItemConfiguration(
							AcideGraphPanelArrowColorMenu.ARROW_COLOR_MENU_NAME);
					colorInverse.setVisible(AcideMenuConfiguration.getInstance()
							.getIsDisplayed(AcideGraphPanelArrowColorMenu.ARROW_COLOR_INVERSE));
					arrowColor.insertObject(colorInverse);
				}
				
				colorInverse.setErasable(false);
				colorInverse.setParameter("None");
			}
			
		}
		
	}

	/**
	 * Adds the components to the ACIDE - A Configurable IDE graph panel menu.
	 */
	private void addComponents() {
		Iterator<Object> it = AcideMenuItemsConfiguration.getInstance()
				.getMenuItemsManager().getSubmenu(AcideConfigurationMenu.CONFIGURATION_MENU_NAME)
				.getItemsManager().getSubmenu(GRAPH_MENU_NAME).getItemsManager().managerIterator();
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
			}else {
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
	}

	/**
	 * Builds the ACIDE - A Configurable IDE graph panel menu components.
	 */
	private void buildComponents() {
		if (!AcideMenuItemsConfiguration.getInstance().getMenuItemsManager()
				.getSubmenu(AcideConfigurationMenu.CONFIGURATION_MENU_NAME).hasSubmenu(GRAPH_MENU_NAME)){
			AcideMenuItemsConfiguration.getInstance().getMenuItemsManager()
				.getSubmenu(AcideConfigurationMenu.CONFIGURATION_MENU_NAME)
					.insertObject(new AcideMenuSubmenuConfiguration(GRAPH_MENU_NAME));
		}
		
		Iterator<Object> it = AcideMenuItemsConfiguration.getInstance()
				.getMenuItemsManager().getSubmenu(AcideConfigurationMenu.CONFIGURATION_MENU_NAME)
				.getItemsManager().getSubmenu(GRAPH_MENU_NAME).getItemsManager().managerIterator();
		
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
		_nodeShapeMenu = new AcideGraphPanelNodeShapeMenu();
		
		// Sets the node shape menu item name
		_nodeShapeMenu.setName(NODE_SHAPE_NAME);

		//TODO add an icon to the item
		// Creates the node color menu item
		_nodeColorMenuItem = new JMenuItem();
			
		// Sets the node color menu item name
		_nodeColorMenuItem.setName(NODE_COLOR_NAME);
		
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

		//TODO add an icon to the item
		// Creates the arrow color menu item
		_arrowColorMenu = new AcideGraphPanelArrowColorMenu();
		
		// Sets the arrow color menu item name
		_arrowColorMenu.setName(ARROW_COLOR_NAME);
		
		//TODO add an icon to the item
		// Creates the arrow shape menu item
		_arrowShapeMenu = new AcideGraphPanelArrowShapeMenu();
				
		// Sets the arrow shape menu item name
		_arrowShapeMenu.setName(ARROW_SHAPE_NAME);

	}

	/**
	 * Gets if the menu name given as parameter is original
	 * @param name
	 * 		the name we want to check
	 * @return
	 * 		if the name given as parameter is original
	 */
	public boolean isOriginal(String name) {
		if(!(name.equals(NODE_COLOR_NAME) || 
				name.equals(SHOW_LABELS_NAME) ||
				name.equals(NODE_SIZE_NAME) ||
				name.equals(NODE_SHAPE_NAME) ||
				name.equals(ARROW_COLOR_NAME) ||
				name.equals(ARROW_SHAPE_NAME) ||
				name.equals(NODE_SIZE_NAME)))
			return true;
		return false;
	}

	/**
	 * Sets the text of the ACIDE - A Configurable IDE graph panel menu
	 * components with the labels in the selected language to display.
	 */
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

	/**
	 * Updates the ACIDE - A Configurable IDE configuration menu components
	 * visibility with the menu configuration.
	 */
	public void updateComponentsVisibility() {
		// TODO Auto-generated method stub
		
	}
	
	
	/**
	 * Returns the ACIDE - A Configurable IDE graph panel menu node shape menu.
	 * 
	 * @return the ACIDE - A Configurable IDE graph panel menu node shape menu.
	 */
	public AcideGraphPanelNodeShapeMenu get_nodeShapeMenu() {
		return _nodeShapeMenu;
	}
	
	/**
	 * Sets a new value to the ACIDE - A Configurable IDE graph menu node shape menu.
	 * 
	 * @param _nodeShapeMenu new value to set.
	 */
	public void set_nodeShapeMenu(AcideGraphPanelNodeShapeMenu _nodeShapeMenu) {
		this._nodeShapeMenu = _nodeShapeMenu;
	}

	/**
	 * Returns the ACIDE - A Configurable IDE graph panel menu node color menu item.
	 * 
	 * @return the ACIDE - A Configurable IDE graph panel menu node color menu item.
	 */
	public JMenuItem get_nodeColorMenuItem() {
		return _nodeColorMenuItem;
	}

	/**
	 * Sets a new value to the ACIDE - A Configurable IDE graph menu node color menu item.
	 * 
	 * @param _nodeColorMenuItem new value to set.
	 */
	public void set_nodeColorMenuItem(JMenuItem _nodeColorMenuItem) {
		this._nodeColorMenuItem = _nodeColorMenuItem;
	}

	/**
	 * Returns the ACIDE - A Configurable IDE graph panel menu arrow color menu.
	 * 
	 * @return the ACIDE - A Configurable IDE graph panel menu arrow color menu.
	 */
	public AcideGraphPanelArrowColorMenu get_arrowColorMenu() {
		return _arrowColorMenu;
	}
	
	/**
	 * Sets a new value to the ACIDE - A Configurable IDE graph menu arrow color menu.
	 * 
	 * @param _arrowColorMenu new value to set.
	 */
	public void set_arrowColorMenu(AcideGraphPanelArrowColorMenu _arrowColorMenu) {
		this._arrowColorMenu = _arrowColorMenu;
	}
	
	/**
	 * Returns the ACIDE - A Configurable IDE graph panel menu arrow shape menu.
	 * 
	 * @return the ACIDE - A Configurable IDE graph panel menu arrow shape menu.
	 */
	public AcideGraphPanelArrowShapeMenu get_arrowShapeMenu() {
		return _arrowShapeMenu;
	}

	/**
	 * Sets a new value to the ACIDE - A Configurable IDE graph menu arrow shape menu.
	 * 
	 * @param _arrowShapeMenu new value to set.
	 */
	public void set_arrowShapeMenu(AcideGraphPanelArrowShapeMenu _arrowShapeMenu) {
		this._arrowShapeMenu = _arrowShapeMenu;
	}
	
	/**
	 * Returns the ACIDE - A Configurable IDE graph panel menu show labels check box menu item.
	 * 
	 * @return the ACIDE - A Configurable IDE graph panel menu show labels check box menu item.
	 */
	public JCheckBoxMenuItem get_showLabelsMenuItem() {
		return _showLabelsMenuItem;
	}

	/**
	 * Sets a new value to the ACIDE - A Configurable IDE graph menu show labels check box menu item.
	 * 
	 * @param _showLabelsMenuItem new value to set.
	 */
	public void set_showLabelsMenuItem(JCheckBoxMenuItem _showLabelsMenuItem) {
		this._showLabelsMenuItem = _showLabelsMenuItem;
	}

	/**
	 * Sets the ACIDE - A Configurable IDE graph menu listeners.
	 */
	public void setListeners(){
			
		//sets the node color menu item listener
		_nodeColorMenuItem.addActionListener(new AcideInsertedItemListener(
				AcideMenuItemsConfiguration.getInstance()
				.getSubmenu(AcideConfigurationMenu.CONFIGURATION_MENU_NAME)
				.getSubmenu(AcideGraphPanelMenu.GRAPH_MENU_NAME)
				.getItem(AcideGraphPanelMenu.NODE_COLOR_NAME)));

		//sets the node size item listener
		_nodeSizeMenuItem.addActionListener(new AcideInsertedItemListener(
				AcideMenuItemsConfiguration.getInstance()
				.getSubmenu(AcideConfigurationMenu.CONFIGURATION_MENU_NAME)
				.getSubmenu(AcideGraphPanelMenu.GRAPH_MENU_NAME)
				.getItem(AcideGraphPanelMenu.NODE_SIZE_NAME)));
		
		//sets the node show labels check box item listener
		_showLabelsMenuItem.addActionListener(new AcideInsertedItemListener(
				AcideMenuItemsConfiguration.getInstance()
				.getSubmenu(AcideConfigurationMenu.CONFIGURATION_MENU_NAME)
				.getSubmenu(AcideGraphPanelMenu.GRAPH_MENU_NAME)
				.getItem(AcideGraphPanelMenu.SHOW_LABELS_NAME)));

		
	}

}
