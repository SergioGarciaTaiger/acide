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
package acide.gui.mainWindow.listeners;

import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.JSplitPane;
import javax.swing.border.MatteBorder;

import acide.configuration.project.AcideProjectConfiguration;
import acide.gui.consolePanel.AcideConsolePanel;
import acide.gui.databasePanel.AcideDataBasePanel;
import acide.gui.debugPanel.AcideDebugPanel;
import acide.gui.explorerPanel.AcideExplorerPanel;
import acide.gui.fileEditor.fileEditorManager.utils.gui.AcideDragAndDropTabbedPane;
import acide.gui.graphPanel.AcideGraphPanel;
import acide.gui.mainWindow.AcideMainWindow;

/**
 * ACIDE - A Configurable IDE Main Window Mouse Adapter
 * 
 * version 0.12
 * see @MouseAdapter
 */
public class AcideMainWindowMouseListener extends MouseAdapter {
	
	/**
	 * ACIDE - A Configurable IDE Main Window reference.
	 */
	private AcideMainWindow acideWindow;
	/**
	 * ACIDE - A Configurable IDE positions of the dividers.
	 */
	private int dividerFiles, dividerDataBase, dividerLeft, dividerMain, dividerGraph;
	/**
	 * ACIDE - A Configurable IDE origin panel.
	 */
	private Component origin;
	/**
	 * ACIDE - A Configurable IDE destination panel.
	 */
	private Component destination;
	/**
	 * ACIDE - A Configurable IDE JSplitPane origin.
	 */
	private JSplitPane splitPaneOrigin;
	/**
	 * ACIDE - A Configurable IDE JSplitPane destination.
	 */
	private JSplitPane splitPaneDestination;
	/**
	 * ACIDE - A Configurable IDE position in the JSplitPane origin.
	 */
	private int indexOrigin;
	/**
	 * ACIDE - A Configurable IDE position in the JSplitPanel destination.
	 */
	private int indexDestination;
	/**
	 * ACIDE - A Configurable IDE coordinates.
	 */
	private double firstX, firstY;
	/**
	 * ACIDE - A Configurable IDE dragged flag.
	 */
	private boolean dragged;
	/**
	 * ACIDE - A Configurable IDE temporary component.
	 */
	private Component tmp;
	/**
	 * ACIDE - A Configurable IDE modified flag.
	 */
	private boolean oldModified;

	
	/**
	 * Builds the ACIDE - A Configurable IDE Main Window Mouse Listener
	 * 
	 */
	public AcideMainWindowMouseListener() {
		this.acideWindow = AcideMainWindow.getInstance();
	}
	
	/**
	 *  Builds the ACIDE - A Configurable IDE Main Window Mouse Listener
	 * 
	 * @param acideWindow
	 * 		instance of the main window
	 */
	public AcideMainWindowMouseListener(AcideMainWindow acideWindow) {
		this.acideWindow = acideWindow;
	}
	
	/*
	 * (non-Javadoc)
	 * @see java.awt.event.MouseAdapter#mousePressed(java.awt.event.MouseEvent)
	 */
	@Override
	public void mousePressed(MouseEvent mouseEvent) {
		
		// Gets the location coordinates
		double x = mouseEvent.getLocationOnScreen().getX();
		double y = mouseEvent.getLocationOnScreen().getY();
		
		firstX = x;
		firstY = y;
		dragged = false;
		
		getDividers();
		
		origin = findLocation(x, y, true);
		
		tmp = origin;
		
	}
	
	/*
	 * (non-Javadoc)
	 * @see java.awt.event.MouseAdapter#mouseDragged(java.awt.event.MouseEvent)
	 */
	@Override
	public void mouseDragged(MouseEvent mouseEvent) {
		
		// Gets the location coordinates
		double x = mouseEvent.getLocationOnScreen().getX();
		double y = mouseEvent.getLocationOnScreen().getY();
		
		if (!dragged) {
			
			//If the dragged motion has not started yet
			if (((x < firstX + 10) && (y < firstY + 10)) || ((x > firstX + 10) || (y > firstY + 10))) {
				// Waits for a minimum movement
				dragged = true;
				// Changes the cursor
				acideWindow.setCursor(new Cursor(Cursor.MOVE_CURSOR));
				oldModified = AcideProjectConfiguration.getInstance().isModified();
			}
		} else {
			setBorder(tmp, null);

			tmp = findLocation(x, y, false);

			setBorder(tmp, Color.GREEN);
			setBorder(origin, Color.GREEN);
			//acideWindow.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			
		
			
		}
		//acideWindow.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
		
		
	}
	
	/*
	 * (non-Javadoc)
	 * @see java.awt.event.MouseAdapter#mouseReleased(java.awt.event.MouseEvent)
	 */
	@Override 
	public void mouseReleased(MouseEvent mouseEvent) {
		 
		//Gets the location coordinates
		double x = mouseEvent.getLocationOnScreen().getX();
		double y = mouseEvent.getLocationOnScreen().getY();
		//The dragged motion has ended
		dragged = false;
		//Get back the default cursor
		acideWindow.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
				
		destination = findLocation(x, y, false);
		
		//Swaps the panels
		swap();
		
		//Sets the new dividers
		setDividers();
		
		//Updates the panels list
		AcideMainWindow.getInstance().updatePanelList();
		
		//Sets the values of the new containers
		AcideMainWindow.getInstance().setSplitContainers();
		
		setBorder(origin, null);
		setBorder(destination, null);
		
		
		
	}
	
	/*
	 * (non-Javadoc)
	 * @see java.awt.event.MouseAdapter#mouseReleased(java.awt.event.MouseEvent)
	 */
	@Override 
	public void mouseMoved(MouseEvent mouseEvent) {
	/*	// Gets the location coordinates
		double x = mouseEvent.getLocationOnScreen().getX();
		double y = mouseEvent.getLocationOnScreen().getY();
	
		// only display a hand if the cursor is over the items
		if (((x < firstX + 10) && (y < firstY + 10)) || ((x > firstX + 10) || (y > firstY + 10))) {
			acideWindow.setCursor(new Cursor(Cursor.HAND_CURSOR));
	        } else {
	        	acideWindow.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
	        }*/
	}
	
	/**
	 * Changes the ACIDE - A Configurable IDE color of the border of 
	 * a given panel
	 * 
	 * @param comp
	 * 		component whose border is going to change
	 * @param color
	 * 		new color of the border
	 */
	public void setBorder(Component comp, Color color) {
		
		MatteBorder border = null;
		if (color != null)
			border = BorderFactory.createMatteBorder(1, 1, 1, 1, color);
		
		if (comp instanceof AcideExplorerPanel)
			((AcideExplorerPanel) comp).setBorder(border);
		else if (comp instanceof AcideDataBasePanel)
			((AcideDataBasePanel) comp).setBorder(border);
		else if (comp instanceof AcideConsolePanel)
			((AcideConsolePanel) comp).setBorder(border);
		else if (comp instanceof AcideGraphPanel)
			((AcideGraphPanel) comp).setBorder(border);
		else if (comp instanceof AcideDebugPanel)
			((AcideDebugPanel) comp).setBorder(border);
		else
			((AcideDragAndDropTabbedPane) comp).setBorder(border);
	}
	
	/**
	 * Gets the ACIDE - A Configurable IDE divider locations of the split panels
	 * 
	 */
	private void getDividers() {
		dividerFiles = acideWindow.getVerticalFilesSplitPane().getDividerLocation();
		dividerDataBase = acideWindow.getVerticalDataBaseSplitPane().getDividerLocation();
		dividerLeft = acideWindow.getHorizontalSplitPane().getDividerLocation();
		dividerMain = acideWindow.getVerticalSplitPane().getDividerLocation();
		dividerGraph = acideWindow.getHorizontalGraphSplitPane().getDividerLocation();
	}
	
	/**
	 * Sets the ACIDE - A Configurable IDE divider locations of the slit panels
	 * 
	 */
	private void setDividers() {
		acideWindow.getVerticalFilesSplitPane().setDividerLocation(dividerFiles);
		acideWindow.getVerticalDataBaseSplitPane().setDividerLocation(dividerDataBase);
		acideWindow.getHorizontalSplitPane().setDividerLocation(dividerLeft);
		acideWindow.getVerticalSplitPane().setDividerLocation(dividerMain);
		acideWindow.getHorizontalGraphSplitPane().setDividerLocation(dividerGraph);
	}
	
	/**
	 * Swaps the ACIDE - A Configurable IDE panels of the main window
	 */
	private void swap() {
		
		// If the origin and the destination have the same index, takes the old value for saving options
		if (splitPaneOrigin == splitPaneDestination && indexOrigin == indexDestination)
			AcideProjectConfiguration.getInstance().setIsModified(oldModified);
		else
			AcideProjectConfiguration.getInstance().setIsModified(true);

		//Locates the panels and swap them
		if (indexOrigin == 1)
			splitPaneOrigin.setLeftComponent(null);
		else
			splitPaneOrigin.setRightComponent(null);
		
		if (indexDestination == 1)
			splitPaneDestination.setLeftComponent(null);
		else
			splitPaneDestination.setRightComponent(null);
		
		if (indexOrigin == 1)
			splitPaneOrigin.setLeftComponent(destination);
		else
			splitPaneOrigin.setRightComponent(destination);
		
		if (indexDestination == 1)
			splitPaneDestination.setLeftComponent(origin);
		else
			splitPaneDestination.setRightComponent(origin);
		
	}
	
	/**
	 * Finds the ACIDE - A Configurable IDE panel location from the mouse
	 * coordinates
	 * 
	 * @param x
	 * 		x component of the mouse coordinate
	 * @param y
	 * 		y component of the mouse coordinate
	 * @param isOrigin
	 * 		indicates if it's the panel origin
	 * @return the component that contains these coordinates
	 */
	private Component findLocation(double x, double y, boolean isOrigin) {
		Component comp = origin;
		int index = indexOrigin;
		JSplitPane split = splitPaneOrigin;

		double startX;
		double startY;
		double width;
		double height;

		if (AcideMainWindow.getInstance().getVerticalFilesSplitPane()
				.getLeftComponent().isVisible()) {

			startX = AcideMainWindow.getInstance().getVerticalFilesSplitPane()
					.getLeftComponent().getLocationOnScreen().getX();
			startY = AcideMainWindow.getInstance().getVerticalFilesSplitPane()
					.getLeftComponent().getLocationOnScreen().getY();
			width = AcideMainWindow.getInstance().getVerticalFilesSplitPane()
					.getLeftComponent().getWidth();
			height = AcideMainWindow.getInstance().getVerticalFilesSplitPane()
					.getLeftComponent().getHeight();

			if ((x >= startX && x <= (startX + width))
					&& (y >= startY && y <= (startY + height))) {
				split = acideWindow.getVerticalFilesSplitPane();
				index = 1;
				comp = split.getLeftComponent();
			}

		}

		if (AcideMainWindow.getInstance().getVerticalFilesSplitPane()
				.getRightComponent().isVisible()) {

			startX = AcideMainWindow.getInstance().getVerticalFilesSplitPane()
					.getRightComponent().getLocationOnScreen().getX();
			startY = AcideMainWindow.getInstance().getVerticalFilesSplitPane()
					.getRightComponent().getLocationOnScreen().getY();
			width = AcideMainWindow.getInstance().getVerticalFilesSplitPane()
					.getRightComponent().getWidth();
			height = AcideMainWindow.getInstance().getVerticalFilesSplitPane()
					.getRightComponent().getHeight();

			if ((x >= startX && x <= (startX + width))
					&& (y >= startY && y <= (startY + height))) {
				split = acideWindow.getVerticalFilesSplitPane();
				index = 2;
				comp = split.getRightComponent();
			}

		}

		if (AcideMainWindow.getInstance().getVerticalDataBaseSplitPane()
				.getLeftComponent().isVisible()) {

			startX = AcideMainWindow.getInstance()
					.getVerticalDataBaseSplitPane().getLeftComponent()
					.getLocationOnScreen().getX();
			startY = AcideMainWindow.getInstance()
					.getVerticalDataBaseSplitPane().getLeftComponent()
					.getLocationOnScreen().getY();
			width = AcideMainWindow.getInstance()
					.getVerticalDataBaseSplitPane().getLeftComponent()
					.getWidth();
			height = AcideMainWindow.getInstance()
					.getVerticalDataBaseSplitPane().getLeftComponent()
					.getHeight();

			if ((x >= startX && x <= (startX + width))
					&& (y >= startY && y <= (startY + height))) {
				split = acideWindow.getVerticalDataBaseSplitPane();
				index = 1;
				comp = split.getLeftComponent();
			}

		}

		if (AcideMainWindow.getInstance().getVerticalDataBaseSplitPane()
				.getRightComponent().isVisible()) {

			startX = AcideMainWindow.getInstance()
					.getVerticalDataBaseSplitPane().getRightComponent()
					.getLocationOnScreen().getX();
			startY = AcideMainWindow.getInstance()
					.getVerticalDataBaseSplitPane().getRightComponent()
					.getLocationOnScreen().getY();
			width = AcideMainWindow.getInstance()
					.getVerticalDataBaseSplitPane().getRightComponent()
					.getWidth();
			height = AcideMainWindow.getInstance()
					.getVerticalDataBaseSplitPane().getRightComponent()
					.getHeight();

			if ((x >= startX && x <= (startX + width))
					&& (y >= startY && y <= (startY + height))) {
				split = acideWindow.getVerticalDataBaseSplitPane();
				index = 2;
				comp = split.getRightComponent();
			}

		}
		
		if (AcideMainWindow.getInstance().getHorizontalGraphSplitPane()
				.getLeftComponent().isVisible()) {

			startX = AcideMainWindow.getInstance().getHorizontalGraphSplitPane()
					.getLeftComponent().getLocationOnScreen().getX();
			startY = AcideMainWindow.getInstance().getHorizontalGraphSplitPane()
					.getLeftComponent().getLocationOnScreen().getY();
			width = AcideMainWindow.getInstance().getHorizontalGraphSplitPane()
					.getLeftComponent().getWidth();
			height = AcideMainWindow.getInstance().getHorizontalGraphSplitPane()
					.getLeftComponent().getHeight();

			if ((x >= startX && x <= (startX + width))
					&& (y >= startY && y <= (startY + height))) {
				split = acideWindow.getHorizontalGraphSplitPane();
				index = 1;
				comp = split.getLeftComponent();
			}

		}
		
		if (AcideMainWindow.getInstance().getHorizontalGraphSplitPane()
				.getRightComponent().isVisible()) {

			startX = AcideMainWindow.getInstance().getHorizontalGraphSplitPane()
					.getRightComponent().getLocationOnScreen().getX();
			startY = AcideMainWindow.getInstance().getHorizontalGraphSplitPane()
					.getRightComponent().getLocationOnScreen().getY();
			width = AcideMainWindow.getInstance().getHorizontalGraphSplitPane()
					.getRightComponent().getWidth();
			height = AcideMainWindow.getInstance().getHorizontalGraphSplitPane()
					.getRightComponent().getHeight();

			if ((x >= startX && x <= (startX + width))
					&& (y >= startY && y <= (startY + height))) {
				split = acideWindow.getHorizontalGraphSplitPane();
				index = 2;
				comp = split.getRightComponent();
			}

		}

		if (isOrigin) {
			splitPaneOrigin = split;
			indexOrigin = index;
		} else {
			splitPaneDestination = split;
			indexDestination = index;
		}

		return comp;
	}

}
