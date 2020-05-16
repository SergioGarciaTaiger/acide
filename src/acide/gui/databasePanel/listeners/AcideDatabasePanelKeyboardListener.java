/*
 * ACIDE - A Configurable IDE
 * Official web site: http://acide.sourceforge.net
 * 
 * Copyright (C) 2007-2014  
 * Authors:
 * 		- Fernando S�enz P�rez (Team Director).
 *      - Version from 0.1 to 0.6:
 *      	- Diego Cardiel Freire.
 *			- Juan Jos� Ortiz S�nchez.
 *          - Delf�n Rup�rez Ca�as.
 *      - Version 0.7:
 *          - Miguel Mart�n L�zaro.
 *      - Version 0.8:
 *      	- Javier Salcedo G�mez.
 *      - Version from 0.9 to 0.11:
 *      	- Pablo Guti�rrez Garc�a-Pardo.
 *      	- Elena Tejeiro P�rez de �greda.
 *      	- Andr�s Vicente del Cura.
 *      - Version from 0.12 to 0.16
 *      	- Sem�ramis Guti�rrez Quintana
 *      	- Juan Jes�s Marqu�s Ortiz
 *      	- Fernando Ord�s Lorente
 *      - Version 0.17
 *      	- Sergio Dom�nguez Fuentes
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
package acide.gui.databasePanel.listeners;


import java.awt.Cursor;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;

import javax.swing.JOptionPane;
import javax.swing.ToolTipManager;
import javax.swing.tree.TreePath;

import acide.gui.databasePanel.AcideDataBasePanel;
import acide.gui.databasePanel.Nodes.AcideDataBaseNodes;
import acide.gui.databasePanel.Nodes.NodeDefinition;
import acide.gui.databasePanel.Nodes.NodeTable;
import acide.gui.databasePanel.Nodes.NodeTables;
import acide.gui.databasePanel.Nodes.NodeView;
import acide.gui.databasePanel.Nodes.NodeViews;
import acide.gui.databasePanel.utils.AcideDatabaseCopyTableOption;
import acide.gui.mainWindow.AcideMainWindow;
import acide.gui.databasePanel.utils.DataBasePanelUtils;
import acide.language.AcideLanguageManager;
import acide.process.console.AcideDatabaseManager;

/**
 * ACIDE - A Configurable IDE database panel keyboard listener.
 * 
 * @version 0.16
 * @see KeyAdapter
 */
public class AcideDatabasePanelKeyboardListener extends KeyAdapter {

	@Override
	public void keyPressed(KeyEvent keyEvent) {
		dispatchEvent(keyEvent);
	
	}
	
	private void dispatchEvent(KeyEvent keyEvent){
		
		ToolTipManager.sharedInstance();
		
		keyEvent.consume();
		
		switch (keyEvent.getKeyCode()) {
		
		case KeyEvent.VK_F5:
			AcideMainWindow.getInstance().setCursor(new Cursor(Cursor.WAIT_CURSOR));
			AcideMainWindow.getInstance().getDataBasePanel().updateDataBaseTree();
			AcideMainWindow.getInstance().setCursor(null);
			break;
			
		case 127: //supr
			
			AcideDataBasePanel panel = AcideMainWindow.getInstance().getDataBasePanel();
			
			TreePath tree = panel.getTree().getSelectionPath();
			
			String name = tree.getParentPath().getLastPathComponent().toString();
			
			if (name.equals("Tables") ||name.equals("Tablas")){
				
				int response = JOptionPane.showConfirmDialog(null,AcideLanguageManager.getInstance()
						.getLabels().getString("s2093"), AcideLanguageManager.getInstance()
						.getLabels().getString("s2050"), JOptionPane.OK_OPTION);
				
				if (response == 0){
					
					AcideDatabaseManager des =AcideDatabaseManager.getInstance();
					
					String table = tree.getLastPathComponent().toString();
					
					String database = tree.getParentPath().getParentPath().getLastPathComponent().toString();
					
					if (table.contains("(")){
						table = table.substring(0, table.indexOf("("));
					}
					
					LinkedList<String> res = des.dropTable(database, table);
					
					Iterator<String> it =  res.iterator();
					
					String result ="";
					
					boolean found = false;
					
					while (it.hasNext() || !found){
					
						result=it.next();
						
						found = result.contains("$success") || result.contains("KO") || result.contains("Dangling");
					}
					
					if (result.contains("$success") || result.contains("KO")){
						DataBasePanelUtils.updateDataBasePanelTable();
					} else if (result.contains("Dangling")){
						JOptionPane.showMessageDialog(null,result, AcideLanguageManager.getInstance()
								.getLabels().getString("s2050"), JOptionPane.WARNING_MESSAGE);
						DataBasePanelUtils.updateDataBasePanelTable();
					}
					else{
						
						JOptionPane.showMessageDialog(null,AcideLanguageManager.getInstance()
								.getLabels().getString("s2095"), AcideLanguageManager.getInstance()
								.getLabels().getString("s2050"), JOptionPane.WARNING_MESSAGE);
						}
				}
				
			} else if (name.equals("Views") ||name.equals("View")){
				String view = "", database= "";
				
				int response = JOptionPane.showConfirmDialog(null,AcideLanguageManager.getInstance()
						.getLabels().getString("s2138"), AcideLanguageManager.getInstance()
						.getLabels().getString("s2050"), JOptionPane.YES_NO_OPTION);
				
				if ( response == 0){
				
					view = AcideMainWindow.getInstance().getDataBasePanel().getTree().getSelectionPath().getLastPathComponent().toString();
					
					if ( view.contains("("))
						view = view.substring(0, view.indexOf("("));
					
					database = AcideMainWindow.getInstance().getDataBasePanel().getTree().getSelectionPath().getParentPath().getParentPath().getLastPathComponent().toString();
					
					String result = "";
					
					result = AcideDatabaseManager.getInstance().dropView(database, view);
					
					if (result != "success")
						JOptionPane.showMessageDialog(null,result, AcideLanguageManager.getInstance()
								.getLabels().getString("s2050"), JOptionPane.ERROR_MESSAGE);
					else
						AcideMainWindow.getInstance().getDataBasePanel().updateDataBaseTree();
				}
			}
		break;
		
		case KeyEvent.VK_C:
			
		    panel = AcideMainWindow.getInstance().getDataBasePanel();
			
			tree = panel.getTree().getSelectionPath();
			
			Object lastNodeCopy = tree.getLastPathComponent();
			
			if (lastNodeCopy instanceof NodeTable && keyEvent.isControlDown() ){
				AcideDatabaseCopyTableOption.getInstance().setVisible(true);
			
			}else if (lastNodeCopy instanceof NodeView && keyEvent.isControlDown()){
				String viewName = "";
				
				viewName = AcideMainWindow.getInstance().getDataBasePanel().getTree().getSelectionPath().getLastPathComponent().toString();
				
				if (viewName.contains("("))
					viewName = viewName.substring(0, viewName.indexOf("("));
				
				StringSelection name2 = new StringSelection(viewName);
				
				Clipboard cb = Toolkit.getDefaultToolkit().getSystemClipboard();

				cb.setContents(name2, null);

			}else if (lastNodeCopy instanceof NodeDefinition && keyEvent.isControlDown()){
				TreePath path2 = AcideMainWindow.getInstance().getDataBasePanel().getTree().getSelectionPath();
				
				String view = path2.getParentPath().getParentPath().getLastPathComponent().toString();
				
				AcideDataBaseNodes selectedNode = (AcideDataBaseNodes) lastNodeCopy;
				
				if (view.contains("("))
					view = view.substring(0,view.indexOf("("));
				
				String database = path2.getParentPath().getParentPath().getParentPath().getParentPath().getLastPathComponent().toString();
				
				String text = "";
				
				if (( selectedNode.getParent().toString().equals(AcideLanguageManager.getInstance().getLabels().getString("s2036")))){
					
					text = AcideDatabaseManager.getInstance().getSQLText(database, view);
					
					StringSelection ssText = new StringSelection(text);
					
					Clipboard cb = Toolkit.getDefaultToolkit().getSystemClipboard();

					cb.setContents(ssText, null);
					
				}else{
					text = AcideDatabaseManager.getInstance().getDatalogText(database, view);

					StringSelection ssText = new StringSelection(text);
					
					Clipboard cb = Toolkit.getDefaultToolkit().getSystemClipboard();

					cb.setContents(ssText, null);
				}
			
			}
		break;
			
		case KeyEvent.VK_V:
			
			panel = AcideMainWindow.getInstance().getDataBasePanel();
			tree = panel.getTree().getSelectionPath();
			
			Object lastNodePaste = tree.getLastPathComponent();
			
			Toolkit toolkit = Toolkit.getDefaultToolkit();
			Clipboard clipboard = toolkit.getSystemClipboard();
			
			String database="";
			
			// To copy a table
			if ((lastNodePaste instanceof NodeTables && keyEvent.isControlDown()) 
				 ||(lastNodePaste instanceof NodeTable && keyEvent.isControlDown()) ){
				DataBasePanelUtils.pasteDataBasePanelTable(clipboard);
				
			}else // To copy a view
				if ((lastNodePaste instanceof NodeView && keyEvent.isControlDown()) 
					|| (lastNodePaste instanceof NodeViews && keyEvent.isControlDown())){
					try {
						String oldView = (String) clipboard.getData(DataFlavor.stringFlavor);
						String newView = (String)JOptionPane.showInputDialog(null,
								AcideLanguageManager.getInstance().getLabels().getString("s2119"), 
								AcideLanguageManager.getInstance().getLabels().getString("s2139"),
								JOptionPane.PLAIN_MESSAGE, null,null,oldView);
						
						if (lastNodePaste instanceof NodeViews)
							database = AcideMainWindow.getInstance().getDataBasePanel().getTree().getSelectionPath().getParentPath().getLastPathComponent().toString();
						else
							database = AcideMainWindow.getInstance().getDataBasePanel().getTree().getSelectionPath().getParentPath().getParentPath().getLastPathComponent().toString();
						
						if ((newView != null) && (newView.length() > 0)) {				
							
							String result = AcideDatabaseManager.getInstance().pasteView(database, newView, oldView);
							
							if (result!= "success")
								JOptionPane.showMessageDialog(null,result,
									    "Error",JOptionPane.ERROR_MESSAGE);
							else
								DataBasePanelUtils.updateDataBasePanelView();
						}	
					} catch (UnsupportedFlavorException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				else
					JOptionPane.showMessageDialog(null,AcideLanguageManager.getInstance().getLabels().getString("s2112"),
					"Error",JOptionPane.ERROR_MESSAGE);
			break;			
		}		
	}

}
