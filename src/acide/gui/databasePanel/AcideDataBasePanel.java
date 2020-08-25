/*
 * ACIDE - A Configurable IDE
 * Official web site: http://acide.sourceforge.net
 * 
 * Copyright (C) 2007-2013  
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
package acide.gui.databasePanel;

import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.HeadlessException;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.EventObject;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Set;
import java.util.Vector;

import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JSplitPane;
import javax.swing.JTree;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.ToolTipManager;
import javax.swing.event.TreeExpansionEvent;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;

import acide.configuration.menu.AcideInsertedItemListener;
import acide.configuration.menu.AcideMenuItemsConfiguration;
import acide.configuration.project.AcideProjectConfiguration;
import acide.gui.databasePanel.Nodes.AcideDataBaseNodes;
import acide.gui.databasePanel.Nodes.NodeColumns;
import acide.gui.databasePanel.Nodes.NodeConstraint;
import acide.gui.databasePanel.Nodes.NodeDB;
import acide.gui.databasePanel.Nodes.NodeDDBB;
import acide.gui.databasePanel.Nodes.NodeDEF;
import acide.gui.databasePanel.Nodes.NodeDEF.tipoDefinition;
import acide.gui.databasePanel.Nodes.NodeDefinition;
import acide.gui.databasePanel.Nodes.NodeICT;
import acide.gui.databasePanel.Nodes.NodeICT.tipoRestriccion;
import acide.gui.databasePanel.Nodes.NodeTable;
import acide.gui.databasePanel.Nodes.NodeTables;
import acide.gui.databasePanel.Nodes.NodeView;
import acide.gui.databasePanel.Nodes.NodeViews;
import acide.gui.databasePanel.constraintsMenu.AcideConstraintDefinitionWindow;
import acide.gui.databasePanel.constraintsMenu.AcideConstraintDefinitionWindowControl;
import acide.gui.databasePanel.dataView.AcideDataBaseDataViewControl;
import acide.gui.databasePanel.dataView.AcideDatabaseDataView;
import acide.gui.databasePanel.listeners.AcideDatabasePanelKeyboardListener;
import acide.gui.databasePanel.listeners.AcideDatabasePanelRefreshListener;
import acide.gui.databasePanel.popup.AcideDataBasePanelColumnsPopupMenu;
import acide.gui.databasePanel.utils.AcideDataBaseTreeCellRenderer;
import acide.gui.databasePanel.utils.AcideEnterTextWindow;
import acide.gui.databasePanel.utils.AcideTree;
import acide.gui.debugPanel.debugSQLPanel.AcideDebugSQLDebugWindow;
import acide.gui.mainWindow.AcideMainWindow;
import acide.gui.menuBar.viewMenu.AcideViewMenu;
import acide.language.AcideLanguageManager;
import acide.log.AcideLog;
import acide.process.console.AcideDatabaseManager;
import acide.process.console.DesDatabaseManager;
import acide.resources.AcideResourceManager;
import acide.resources.exception.MissedPropertyException;

/**
 * ACIDE - A Configurable IDE database panel.
 * 
 * @version 0.16
 * @see JPanel
 */
public class AcideDataBasePanel extends JPanel {

	/**
	 * ACIDE - A Configurable IDE database panel class serial version UID.
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * ACIDE - A Configurable IDE database panel tree of folders and files.
	 */
	private AcideTree _tree;
	/**
	 * ACIDE - A Configurable IDE database panel tree model for display the
	 * files.
	 */
	private DefaultMutableTreeNode _root;
	/**
	 * ACIDE - A Configurable IDE database panel tree model for display the
	 * directories.
	 */
	private DefaultTreeModel _treeModel;    

	/**
	 * ACIDE - A Configurable IDE database panel size.
	 */
	private int _size;
	
	/**
	 * ACIDE - A Configurable IDE database panel default size.
	 */
	private int DEFAULT_SIZE = 150;

	/**
	 * ACIDE- root node in database
	 */
	private NodeDDBB _basesDatos;

	/**
	 * ACIDE - AcideTree popUp 
	 */
	private JPopupMenu _popUp = null;
	
	/**
	 * ACIDE - data view
	 */
	private HashMap<String,AcideDataBaseDataViewControl> _dataView;
	
	/**
	 * ACIDE - constraints window
	 */
	private HashMap<String,AcideConstraintDefinitionWindowControl> _constraintWindow;
	
	/**
	 * ACIDE - database panel menu bar
	 */
	private JMenuBar menuBar;
	
	/**
	 * ACIDE - name of the current container 
	 */
	private String _splitContainer;

	/**
	 * ACIDE - display only names of table and view nodes.
	 */
	private boolean _nameTables = false;
	/**
	 * ACIDE - display names and columns of table and view nodes.
	 */
	private boolean _nameFieldsTables = false;
	/**
	 * ACIDE - display names, columns and types of table and view nodes.
	 */
	private boolean _nameFieldsTypesTables = false;
	/**
	 * ACIDE - A Configurable IDE trace SQL panel button panel.
	 */
	private JPanel _mainButtonPanel;
	/**
	 * ACIDE - A Configurable IDE Data Base panel refresh button icon
	 */
	private final static ImageIcon REFRESH_IMAGE = new ImageIcon("./resources/icons/panels/refresh.png");
	/**
	 * ACIDE - A Configurable IDE Data Base panel refresh button icon
	 */
	private final static ImageIcon RESET_IMAGE = new ImageIcon("./resources/icons/panels/reset.png");
	
	// builds the refresh button
	public static JButton refreshDB = new JButton();
	
	// builds the reset button
	public static JButton resetDB = new JButton();
		
	/**
	 * Creates a new ACIDE - A Configurable IDE database panel.
	 */
	public AcideDataBasePanel() {


		// Sets the layout
		setLayout(new BorderLayout());

		try {

			// Updates the log
			AcideLog.getLog().info(AcideLanguageManager.getInstance().getLabels().getString("s2025"));

			// Builds the tree
			_root = new DefaultMutableTreeNode("root");

			// Creates the tree model
			_treeModel = new DefaultTreeModel(_root);

			// Sets asks allows children as true
			_treeModel.setAsksAllowsChildren(true);

			// Creates the tree
			_tree = new AcideTree(_treeModel);

			// The root is not visible
			_tree.setRootVisible(false);

			// Sets the single selection mode
			_tree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);

			// s the root handles
			_tree.setShowsRootHandles(true);

			// Sets the cell renderer
			_tree.setCellRenderer(new AcideDataBaseTreeCellRenderer());

			// Sets the auto scroll as true
			_tree.setAutoscrolls(true);
			
			// Sets tooltips on nodes
			ToolTipManager.sharedInstance().registerComponent(_tree);

			_basesDatos = new NodeDDBB(AcideLanguageManager.getInstance().getLabels()
					.getString("s2024"),_treeModel);

			_root.add(_basesDatos);
			_basesDatos.setAllowsChildren(false);
			_basesDatos.setBuilt(false);
			
			//Initialize the values of show details pop up menu
			initalizeShowDetails();
			
			// Builds the button panel
			buildButtons();
			
			// Builds the explorer panel menu bar
			buildMenuBar();

			// Sets the ACIDE - A Configurable IDE explorer panel listeners
			setListeners();

			try {
				// Sets the hand cursor
				_tree.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			} catch (HeadlessException exception) {

				// Updates the log
				AcideLog.getLog().error(exception.getMessage());
				exception.printStackTrace();
			}
		} catch (Exception exception) {

			// Updates the log
			AcideLog.getLog().info(
					AcideLanguageManager.getInstance().getLabels()
					.getString("s325"));
			exception.printStackTrace();
		}

		// Updates the log
		AcideLog.getLog().info(
				AcideLanguageManager.getInstance().getLabels()
				.getString("s326"));

		// Adds the scroll pane
		add(new JScrollPane((JTree) _tree), BorderLayout.CENTER);
		
		// Adds the menu bar
		add(menuBar, BorderLayout.NORTH);
		
		// Adds the button panel
		add(_mainButtonPanel, BorderLayout.SOUTH);
		
		// Creates the control structure for the data views
		_dataView = new HashMap<String, AcideDataBaseDataViewControl>();
		
		// Inserts DES as database by default
		_dataView.put("$des", new AcideDataBaseDataViewControl(0));
		
		// Creates the control structure for the constraints windows
		_constraintWindow = new HashMap<String, AcideConstraintDefinitionWindowControl>();
		
		// Inserts DES as database by default
		_constraintWindow.put("$des", new AcideConstraintDefinitionWindowControl(0));
	}

	
	/**
	 * Sets the ACIDE - A Configurable IDE database panel show details menu values.
	 */
	private void initalizeShowDetails() {
		
		try {
			String activeItem = AcideResourceManager.getInstance().getProperty("databasePanelMenuConfiguration.showDetails");
			
			if(activeItem.equals("Name"))
				_nameTables = true;
			else if(activeItem.equals("NameFields"))
				_nameFieldsTables = true;
			else if(activeItem.equals("NameFieldsTypes"))
				_nameFieldsTypesTables = true;
		
		} catch (MissedPropertyException e) {
			e.printStackTrace();
		}
		
	}

	/**
	 * Builds the buttons for the ACIDE - A Configurable IDE trace SQL panel.
	 */
	private void buildButtons() {
		// builds the button panel
		_mainButtonPanel = new JPanel();
		// adds the layout to the button panel
		_mainButtonPanel.setLayout(new BorderLayout());
		// creates the sub button panel 1
		JPanel subButtonPanel1 = new JPanel();
		// adds the layout to the sub button panel
		subButtonPanel1.setLayout(new FlowLayout());
		// adds the sub button panel to the main button panel
		_mainButtonPanel.add(subButtonPanel1, BorderLayout.CENTER);
		
		refreshDB.setIcon(REFRESH_IMAGE);
		refreshDB.setPreferredSize(new Dimension((int) (1.5 * refreshDB.getIcon()
				.getIconWidth()), (int) refreshDB.getPreferredSize().getHeight()));
		// adds the action listener to the refresh button
		refreshDB
			// .addActionListener(new AcideShowAcideDataBasePanelMenuItemListener());
				.addActionListener(new AcideDatabasePanelRefreshListener());
		// sets tooltip button
		refreshDB.setToolTipText(AcideLanguageManager.getInstance().getLabels()
				.getString("s2044"));
		// unable the button
		refreshDB.setEnabled(true);
		// adds the refresh button
		subButtonPanel1.add(refreshDB);
		
		resetDB.setIcon(RESET_IMAGE);
		resetDB.setPreferredSize(new Dimension((int) (1.5 * resetDB.getIcon()
				.getIconWidth()), (int) resetDB.getPreferredSize().getHeight()));
		// adds the action listener to the refresh button
		resetDB
			// .addActionListener(new AcideShowAcideDataBasePanelMenuItemListener());
				.addActionListener(new AcideInsertedItemListener(
						AcideMenuItemsConfiguration.getInstance()
								.getSubmenu(AcideViewMenu.VIEW_MENU_NAME)
								.getItem(AcideViewMenu.SHOW_DATA_BASE_PANEL_NAME)));
		// sets tooltip button
		resetDB.setToolTipText(AcideLanguageManager.getInstance().getLabels()
				.getString("s987"));
		// unable the button
		resetDB.setEnabled(true);
		// adds the refresh button
		subButtonPanel1.add(resetDB);
		
	}

	/**
	 * Sets the ACIDE - A Configurable IDE database panel listeners.
	 */
	private void setListeners() {

		// Sets the ACIDE - A Configurable IDE database panel popup menu
		// listener
		_tree.addMouseListener(new AcideDatabasePanelClickListener(_tree));

		//Sets the ACIDE - A Configurable IDE database panel click listener             
		_tree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);

		// Sets the ACIDE - A Configurable IDE database panel keyboard listener
		_tree.addKeyListener(new AcideDatabasePanelKeyboardListener());

		_tree.getSelectionModel().addTreeSelectionListener(new TreeSelectionListener() {

			@Override
			public void valueChanged(TreeSelectionEvent evt) {                              
				build(evt);     
			}
		});
	}

	private void build(EventObject evt){
		_tree.setCursor( new Cursor(Cursor.WAIT_CURSOR));
		DefaultMutableTreeNode selectedNode;
		TreePath f ;
		try{
			f = ((TreeSelectionEvent) evt).getPath();
			selectedNode = (DefaultMutableTreeNode)f.getLastPathComponent();        
		}
		catch(ClassCastException e ){
			f = ((TreeExpansionEvent) evt).getPath();
			selectedNode = (DefaultMutableTreeNode)f.getLastPathComponent();        
		}

		try{
			 if( ((AcideDataBaseNodes) selectedNode).isBuilt()){
				 _tree.setCursor( new Cursor(Cursor.DEFAULT_CURSOR));
				 return;
			 }
			selectedNode.setAllowsChildren(true);
			if(selectedNode instanceof NodeDDBB){

				if(((NodeDDBB) selectedNode).isBuilt()) selectedNode.removeAllChildren();

				LinkedList<String> dataBases = AcideDatabaseManager.getInstance().getDatabases();
				
				for(int i=0;i<dataBases.size();i++){
					//a�adimos las bases de datos
					String dataBase = dataBases.get(i);
					NodeDB Dbase = new NodeDB(dataBase,_treeModel);
					Dbase.setAllowsChildren(false);
					_treeModel.insertNodeInto(Dbase,selectedNode,selectedNode.getChildCount());
					_dataView.put(dataBase,new AcideDataBaseDataViewControl(0));
					_constraintWindow.put(dataBase,new AcideConstraintDefinitionWindowControl(0));
					
				}
				((DefaultTreeModel) _tree.getModel()).nodeChanged(selectedNode);
				((NodeDDBB) selectedNode).setBuilt(true);
				
			}else if (selectedNode instanceof NodeDB && !((NodeDB) selectedNode).isBuilt()){
				
				// In case it is the ODBC database panel it is necessary to add the current database in the hash 
				_dataView.put(AcideDatabaseManager.getInstance().currentDB(),new AcideDataBaseDataViewControl(0));
				_constraintWindow.put(AcideDatabaseManager.getInstance().currentDB(),new AcideConstraintDefinitionWindowControl(0));
				
				//a�adimos el nodo de tablas
				NodeTables tablas = new NodeTables(AcideLanguageManager.getInstance().getLabels()
						.getString("s2028"),_treeModel);
				tablas.setAllowsChildren(false);
				_treeModel.insertNodeInto(tablas,selectedNode,selectedNode.getChildCount());

				//a�adimos el nodo de vistas
				NodeViews vistas = new NodeViews(AcideLanguageManager.getInstance().getLabels()
						.getString("s2027"),_treeModel);
				vistas.setAllowsChildren(false);
				_treeModel.insertNodeInto(vistas,selectedNode,selectedNode.getChildCount());

				//a�adimos el nodo de las restricciones                         
				NodeICT restricc = new NodeICT(AcideLanguageManager.getInstance().getLabels()
						.getString("s2029"),tipoRestriccion.Res,_treeModel);
				restricc.setAllowsChildren(false);
				_treeModel.insertNodeInto(restricc,selectedNode,selectedNode.getChildCount());

				((DefaultTreeModel) _tree.getModel()).nodeChanged(selectedNode);
				((NodeDB) selectedNode).setBuilt(true);

			}else if (selectedNode instanceof NodeTables){
				
				if(((NodeTables) selectedNode).isBuilt()) selectedNode.removeAllChildren();
				
				LinkedList<String> tables = AcideDatabaseManager.getInstance().getTables(selectedNode.getParent().toString());
				
				if (AcideDatabaseManager.getInstance().currentDB().equals("$des")){
					
					Vector<String> cleanTables = clearList(tables);
					Vector<String> cleanTypeTables = clearOnlyTypes(tables);
					
					for(int j=0; j<tables.size();j++){
						NodeTable tableN = null;
						if (isNameTables()) tableN = new NodeTable(cleanTables.get(j),_treeModel);
						else if (isNameFieldsTables()) tableN = new NodeTable(cleanTypeTables.get(j),_treeModel);
							else if(isNameFieldsTypesTables()) tableN = new NodeTable(tables.get(j),_treeModel);
						tableN.setAllowsChildren(false);
						_treeModel.insertNodeInto(tableN,selectedNode,selectedNode.getChildCount());
					}
					
				} else{
					for(int j=0; j<tables.size();j++){
						NodeTable tableN = new NodeTable(tables.get(j), _treeModel);
						tableN.setAllowsChildren(false);
						_treeModel.insertNodeInto(tableN,selectedNode,selectedNode.getChildCount());
					}
				}
				
				((DefaultTreeModel) _tree.getModel()).nodeChanged(selectedNode);
				((NodeTables) selectedNode).setBuilt(true);

			}else if (selectedNode instanceof NodeTable && !((NodeTable) selectedNode).isBuilt()){
				NodeTable sTable = (NodeTable)f.getLastPathComponent();
				NodeDB dataBase = (NodeDB) f.getParentPath().getParentPath().getLastPathComponent();
				
				int index = sTable.toString().indexOf("(");
				String name = "";
				if (index > 0)
					name = sTable.toString().substring(0, sTable.toString().indexOf("("));
				else
					name = sTable.toString();
				Vector<Boolean> inforest = AcideDatabaseManager.getInstance().infoRestrictions(dataBase.toString(),name );				
				
				//a�adimos el nodo de columnas
				NodeColumns columnsNode = new NodeColumns(AcideLanguageManager.getInstance().getLabels()
						.getString("s2030"),_treeModel);
				columnsNode.setAllowsChildren(false);
				_treeModel.insertNodeInto(columnsNode,selectedNode,selectedNode.getChildCount());

				//a�adimos el nodo de PK
				if(inforest.get(1)){
					NodeICT pk = new NodeICT(AcideLanguageManager.getInstance().getLabels()
							.getString("s2031"),tipoRestriccion.PK,_treeModel);
					pk.setAllowsChildren(false);
					_treeModel.insertNodeInto(pk,selectedNode,selectedNode.getChildCount());
				}
				
				//a�adimos el  nodo de FK
				if(inforest.get(3)){
					NodeICT fk = new NodeICT(AcideLanguageManager.getInstance().getLabels()
							.getString("s2032"),tipoRestriccion.FK,_treeModel);
					fk.setAllowsChildren(false);
					_treeModel.insertNodeInto(fk,selectedNode,selectedNode.getChildCount());
				}
				
				if(inforest.get(2)){
					//a�adimos el  nodo de CK
					NodeICT ck = new NodeICT(AcideLanguageManager.getInstance().getLabels()
							.getString("s2033"),tipoRestriccion.CK,_treeModel);
					ck.setAllowsChildren(false);
					_treeModel.insertNodeInto(ck,selectedNode,selectedNode.getChildCount());
				}
				
				if(inforest.get(4)){
					//a�adimos el nodo de FD 
					NodeICT fd = new NodeICT(AcideLanguageManager.getInstance().getLabels()
							.getString("s2034"),tipoRestriccion.FD,_treeModel);
					fd.setAllowsChildren(false);
					_treeModel.insertNodeInto(fd,selectedNode,selectedNode.getChildCount());
				}
				
				if(inforest.get(5)){
					//a�adimos el nodo de IC
					NodeICT ic = new NodeICT(AcideLanguageManager.getInstance().getLabels()
							.getString("s2035"),tipoRestriccion.IC,_treeModel);
					ic.setAllowsChildren(false);
					_treeModel.insertNodeInto(ic,selectedNode,selectedNode.getChildCount());
				}
				
				if(inforest.get(0)){
					//a�adimos el nodo de NN
					NodeICT nl = new NodeICT(AcideLanguageManager.getInstance().getLabels()
							.getString("s2109"), tipoRestriccion.NN,_treeModel);
					nl.setAllowsChildren(false);
					_treeModel.insertNodeInto(nl,selectedNode,selectedNode.getChildCount());

				}
			
				((DefaultTreeModel) _tree.getModel()).nodeChanged(selectedNode);
				((NodeTable) selectedNode).setBuilt(true);                                      
			}else if (selectedNode instanceof NodeColumns){

				if(((NodeColumns) selectedNode).isBuilt()) selectedNode.removeAllChildren();

				NodeTable sTable = (NodeTable)f.getParentPath().getLastPathComponent();
				NodeDB dataBase = (NodeDB) f.getParentPath().getParentPath().getParentPath().getLastPathComponent();

				LinkedList<String> columns = AcideDatabaseManager.getInstance().getFields(dataBase.toString(), sTable.toString());
				for(int h=0; h<columns.size();h++){
					String aux = columns.get(h);
					DefaultMutableTreeNode col = new DefaultMutableTreeNode(aux);
					col.setAllowsChildren(false);		
					_treeModel.insertNodeInto(col,selectedNode,selectedNode.getChildCount());
				}
				((DefaultTreeModel) _tree.getModel()).nodeChanged(selectedNode);
				((NodeColumns) selectedNode).setBuilt(true);

			}else if (selectedNode instanceof NodeICT){

				NodeTable sTable = (NodeTable)f.getParentPath().getLastPathComponent();
				NodeDB dataBase = (NodeDB) f.getParentPath().getParentPath().getParentPath().getLastPathComponent();

				String table ="";
				
				if (sTable.toString().contains("(")) 
					table =  sTable.toString().substring(0, sTable.toString().indexOf("("));
				else
					table = sTable.toString();

				if(((NodeICT) selectedNode).getTipo().equals(tipoRestriccion.PK)){
					if(((NodeICT) selectedNode).isBuilt()) selectedNode.removeAllChildren();

					LinkedList<String> primKey= AcideDatabaseManager.getInstance().getPrimKey(dataBase.toString(), table);
					for(int h=0;h<primKey.size();h++)
					{
						String aux= primKey.get(h);
						NodeConstraint pKey = new NodeConstraint(aux, tipoRestriccion.PK,_treeModel);
						pKey.setAllowsChildren(false);
						_treeModel.insertNodeInto(pKey,selectedNode,selectedNode.getChildCount());
					}
					((DefaultTreeModel) _tree.getModel()).nodeChanged(selectedNode);
					((NodeICT) selectedNode).setBuilt(true);
				}
				else if(((NodeICT) selectedNode).getTipo().equals(tipoRestriccion.FK)){
					if(((NodeICT) selectedNode).isBuilt()) selectedNode.removeAllChildren();

					LinkedList<String> fKey= AcideDatabaseManager.getInstance().getForeignKey(dataBase.toString(), table);
					for(int h=0;h<fKey.size();h++)
					{
						String aux= fKey.get(h);
						NodeConstraint foreignKey = new NodeConstraint(aux, tipoRestriccion.FK,_treeModel);
						foreignKey.setAllowsChildren(false);
						_treeModel.insertNodeInto(foreignKey,selectedNode,selectedNode.getChildCount());
					}
					((DefaultTreeModel) _tree.getModel()).nodeChanged(selectedNode);
					((NodeICT) selectedNode).setBuilt(true);
				}
				else if (((NodeICT) selectedNode).getTipo().equals(tipoRestriccion.CK)) {
					if(((NodeICT) selectedNode).isBuilt()) selectedNode.removeAllChildren();

					LinkedList<String> candKey= AcideDatabaseManager.getInstance().getCandidateKey(dataBase.toString(), table);
					for(int h=0;h<candKey.size();h++)
					{
						String aux= candKey.get(h);
						NodeConstraint cKey = new NodeConstraint(aux, tipoRestriccion.CK,_treeModel);
						cKey.setAllowsChildren(false);
						_treeModel.insertNodeInto(cKey,selectedNode,selectedNode.getChildCount());
					}
					((DefaultTreeModel) _tree.getModel()).nodeChanged(selectedNode);
					((NodeICT) selectedNode).setBuilt(true);
				}
				else if(((NodeICT) selectedNode).getTipo().equals(tipoRestriccion.FD)){
					if(((NodeICT) selectedNode).isBuilt()) selectedNode.removeAllChildren();


					LinkedList<String> funDep= AcideDatabaseManager.getInstance().getFuncionDep(dataBase.toString(), table);
					for(int h=0;h<funDep.size();h++)
					{
						String aux= funDep.get(h);
						NodeConstraint fdep = new NodeConstraint(aux, tipoRestriccion.FD,_treeModel);
						fdep.setAllowsChildren(false);
						_treeModel.insertNodeInto(fdep,selectedNode,selectedNode.getChildCount());
					}
					((DefaultTreeModel) _tree.getModel()).nodeChanged(selectedNode);
					((NodeICT) selectedNode).setBuilt(true);
				}
				else if(((NodeICT) selectedNode).getTipo().equals(tipoRestriccion.IC)){
					if(((NodeICT) selectedNode).isBuilt()) selectedNode.removeAllChildren();

					LinkedList<String> IntConst= AcideDatabaseManager.getInstance().getIntConst(dataBase.toString(), table);
					for(int h=0;h<IntConst.size();h++)
					{
						String aux= IntConst.get(h);
						NodeConstraint IConst = new NodeConstraint(aux, tipoRestriccion.IC,_treeModel);
						IConst.setAllowsChildren(false);
						_treeModel.insertNodeInto(IConst,selectedNode,selectedNode.getChildCount());
					}
					((DefaultTreeModel) _tree.getModel()).nodeChanged(selectedNode);
					((NodeICT) selectedNode).setBuilt(true);
				}
				else if (((NodeICT) selectedNode).getTipo().equals(tipoRestriccion.NN)){
					if(((NodeICT) selectedNode).isBuilt()) selectedNode.removeAllChildren();
					
					LinkedList<String> nullables = AcideDatabaseManager.getInstance().getNullables(dataBase.toString(), table);
					for (int h = 0; h<nullables.size();h++)
					{
						String aux = nullables.get(h);
						NodeConstraint nullable = new NodeConstraint(aux, tipoRestriccion.NN,_treeModel);
						nullable.setAllowsChildren(false);
						_treeModel.insertNodeInto(nullable,selectedNode,selectedNode.getChildCount());
					}
					((DefaultTreeModel) _tree.getModel()).nodeChanged(selectedNode);
					((NodeICT) selectedNode).setBuilt(true);
				}

			}else if (selectedNode instanceof NodeViews){

				if(((NodeViews) selectedNode).isBuilt()) selectedNode.removeAllChildren();

				LinkedList<String> vistas2 = AcideDatabaseManager.getInstance().getViews(selectedNode.getParent().toString());
				
				if (AcideDatabaseManager.getInstance().currentDB().equals("$des")){
					
					Vector<String> cleanViews = clearList(vistas2);
					Vector<String> cleanTypeTables = clearOnlyTypes(vistas2);
					
					for(int j=0; j<vistas2.size();j++){
						NodeView view = null;
						if (isNameTables()) view = new NodeView(cleanViews.get(j),_treeModel);
						else if (isNameFieldsTables()) view = new NodeView(cleanTypeTables.get(j),_treeModel);
							else if(isNameFieldsTypesTables()) view = new NodeView(vistas2.get(j),_treeModel);
						view.setAllowsChildren(false);
						_treeModel.insertNodeInto(view,selectedNode,selectedNode.getChildCount());
					}
				} else {
					for(int j=0; j<vistas2.size();j++){
						NodeView view = new NodeView(vistas2.get(j),_treeModel);
						view.setAllowsChildren(false);
						_treeModel.insertNodeInto(view,selectedNode,selectedNode.getChildCount());
					}
				}
				
				((DefaultTreeModel) _tree.getModel()).nodeChanged(selectedNode);
				((NodeViews) selectedNode).setBuilt(true);

			}else if (selectedNode instanceof NodeView && !((NodeView) selectedNode).isBuilt() ){

				//Adding column node
				NodeColumns columnsNode = new NodeColumns(AcideLanguageManager.getInstance().getLabels()
						.getString("s2030"),_treeModel);
				columnsNode.setAllowsChildren(false);
				_treeModel.insertNodeInto(columnsNode,selectedNode,selectedNode.getChildCount());
				
				TreePath path = AcideMainWindow.getInstance().getDataBasePanel().getTree().getSelectionPath();
				String database = path.getParentPath().getLastPathComponent().toString();
				
				//Getting the view's name
				String view = selectedNode.toString();
				if (view.contains("(")) view = view.substring(0, view.indexOf("("));
				
				//Getting the type of the view
				String type = AcideDatabaseManager.getInstance().getViewType(database, view);
				
				if (type.equals("SQL")){
					//Adding the SQL node
					NodeDEF SQLtextNode = new NodeDEF(AcideLanguageManager.getInstance().getLabels()
							.getString("s2036"),tipoDefinition.SQL,_treeModel);
					SQLtextNode.setAllowsChildren(false);
					_treeModel.insertNodeInto(SQLtextNode,selectedNode,selectedNode.getChildCount());
					
				} else if (type.equals("RA")) {
					//Adding the RA node
					if (AcideDatabaseManager.getInstance() instanceof DesDatabaseManager ){
						NodeDEF RAtextNode = new NodeDEF(AcideLanguageManager.getInstance().getLabels()
								.getString("s2288"), tipoDefinition.RA, _treeModel);
						RAtextNode.setAllowsChildren(false);
						_treeModel.insertNodeInto(RAtextNode,selectedNode,selectedNode.getChildCount());
					}
				}
				
				//Adding Datalog node
				if (AcideDatabaseManager.getInstance() instanceof DesDatabaseManager ){
					NodeDEF DatalogtextNode = new NodeDEF(AcideLanguageManager.getInstance().getLabels()
							.getString("s2037"), tipoDefinition.Datalog, _treeModel);
					DatalogtextNode.setAllowsChildren(false);
					_treeModel.insertNodeInto(DatalogtextNode,selectedNode,selectedNode.getChildCount());
				}
				
				((DefaultTreeModel) _tree.getModel()).nodeChanged(selectedNode);
				((NodeView) selectedNode).setBuilt(true);

			}else if (selectedNode instanceof NodeDEF){

				if(((NodeDEF) selectedNode).isBuilt()) selectedNode.removeAllChildren();

				NodeView vista = (NodeView) selectedNode.getParent(); 
				NodeDB dataBase = (NodeDB) vista.getParent().getParent();           
				String vistaS = vista.toString();
				if(((NodeDEF) selectedNode).getTipo().equals(tipoDefinition.SQL)){
					
					
					String name = "";
					if (vistaS.contains("("))
						name = vistaS.substring(0,vistaS.indexOf("("));
					else
						name = vistaS;
					
					String text = AcideDatabaseManager.getInstance().getSQLText(dataBase.toString(),name);
					text = text.replace('\n',' ');
					NodeDefinition SQLtext = new NodeDefinition(text,acide.gui.databasePanel.Nodes.NodeDefinition.tipoDefinition.SQL,_treeModel);
					SQLtext.setAllowsChildren(false);
					_treeModel.insertNodeInto(SQLtext,selectedNode,selectedNode.getChildCount());
				}
				else if (((NodeDEF) selectedNode).getTipo().equals(tipoDefinition.RA)){
					
					String name = "";
					if (vistaS.contains("("))
						name = vistaS.substring(0,vistaS.indexOf("("));
					else
						name = vistaS;
					
					String text = AcideDatabaseManager.getInstance().getRAText(dataBase.toString(),name);
					text = text.replace('\n', ' ');
					NodeDefinition RAtext = new NodeDefinition(text,acide.gui.databasePanel.Nodes.NodeDefinition.tipoDefinition.RA,_treeModel);
					RAtext.setAllowsChildren(false);
					_treeModel.insertNodeInto(RAtext,selectedNode,selectedNode.getChildCount());
					
				}
					else{
						String name = "";
						if (vistaS.contains("("))
							name = vistaS.substring(0, vistaS.indexOf("("));
						else
							name = vistaS;
						String Datalog = AcideDatabaseManager.getInstance().getDatalogText(dataBase.toString(),name);
						Datalog = Datalog.replace('\n', ' ');
						NodeDefinition DatalogText = new NodeDefinition(Datalog,acide.gui.databasePanel.Nodes.NodeDefinition.tipoDefinition.Datalog,_treeModel);
						DatalogText.setAllowsChildren(false);
						_treeModel.insertNodeInto(DatalogText,selectedNode,selectedNode.getChildCount());
					}
					((DefaultTreeModel) _tree.getModel()).nodeChanged(selectedNode);
					((NodeDEF) selectedNode).setBuilt(true);

			}
		}
		catch(ClassCastException e){                   
			if (selectedNode instanceof NodeColumns){

				if(((NodeColumns) selectedNode).isBuilt()) selectedNode.removeAllChildren();

				NodeView parent = (NodeView)f.getParentPath().getLastPathComponent();
				NodeDB dataBase = (NodeDB) f.getParentPath().getParentPath().getParentPath().getLastPathComponent();
				String parentS = parent.toString();

				String view = "";
				if (parentS.contains("("))
					view = parentS.substring(0,parent.toString().indexOf("("));
				else
					view = parentS;
					
				LinkedList<String> columns = AcideDatabaseManager.getInstance().getViewFields(dataBase.toString(),view);
				for(int h=0; h<columns.size();h++){
					String aux = columns.get(h);
					DefaultMutableTreeNode col = new DefaultMutableTreeNode(aux);
					col.setAllowsChildren(false);
					_treeModel.insertNodeInto(col,selectedNode,selectedNode.getChildCount());
				}
				((DefaultTreeModel) _tree.getModel()).nodeChanged(selectedNode);
				((NodeColumns) selectedNode).setBuilt(true);

			}else if (selectedNode instanceof NodeICT && !((NodeICT) selectedNode).isBuilt()){    

				NodeDB dataBase = (NodeDB) f.getParentPath().getLastPathComponent();

				LinkedList<String> IntConst= AcideDatabaseManager.getInstance().getIntConst(dataBase.toString());
				for(int h=0;h<IntConst.size();h++)
				{
					String aux= IntConst.get(h);
					DefaultMutableTreeNode IConst = new DefaultMutableTreeNode(aux);
					IConst.setAllowsChildren(false);
					_treeModel.insertNodeInto(IConst,selectedNode,selectedNode.getChildCount());
				}
				((DefaultTreeModel) _tree.getModel()).nodeChanged(selectedNode);
				((NodeICT) selectedNode).setBuilt(true);
			}
		}	
		_tree.setCursor( new Cursor(Cursor.DEFAULT_CURSOR));
	}

	private Vector<String> clearList(LinkedList<String> tables) {
		Vector<String> result = new  Vector<String>();
		for(int childId = 0; childId < tables.size(); childId++){
			String childS = tables.get(childId);		
			if(childS.indexOf("(")!=-1)
				childS = childS.substring(0,childS.indexOf("("));
			result.add(childS);
		}
		return result;
	}
	
	private Vector<String> clearOnlyTypes(LinkedList<String> tables) {
		Vector<String> result = new  Vector<String>();
		for(int childId = 0; childId < tables.size(); childId++){
			String childClear = "";
			String[] child = tables.get(childId).split(",");
			for(int i=0;i<child.length;i++){
				if (i==(child.length-1) && child[i].contains(":")) childClear = childClear + child[i].substring(0, child[i].indexOf(":"))+")";
				else childClear = childClear + child[i].substring(0, child[i].indexOf(":"))+",";
			}
			result.add(childClear);
		}
			
		return result;
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see java.awt.Component#getPreferredSize()
	 */
	@Override
	public Dimension getPreferredSize() {
		return new Dimension(200, 120);
	}


	/**
	 * Builds the ACIDE - A Configurable IDE database panel tree.
	 *
	 */
	public void buildTree() {

		_basesDatos = new NodeDDBB(AcideLanguageManager.getInstance().getLabels().getString("s2024"),_treeModel);		
		_root.add(_basesDatos);       				
		_basesDatos.setAllowsChildren(false);
		_basesDatos.setBuilt(false);
	}

	/**
	 * Returns the ACIDE - A Configurable IDE database panel tree root.
	 * 
	 * @return the ACIDE - A Configurable IDE database panel tree root.
	 */
	public DefaultMutableTreeNode getRoot() {
		return _root;
	}

	/**
	 * Returns the ACIDE - A Configurable IDE database panel tree.
	 * 
	 * @return the ACIDE - A Configurable IDE database panel tree.
	 */
	public AcideTree getTree() {
		return _tree;
	}

	/**
	 * Sets a new value to the ACIDE - A Configurable IDE database panel tree.
	 * 
	 * @param tree
	 *            new value to set.
	 */
	public void setTree(AcideTree tree) {
		_tree = tree;
	}

	/**
	 * Sets a new value to the ACIDE - A Configurable IDE database panel root.
	 * 
	 * @param root
	 *            new value to set.
	 */
	public void setRoot(DefaultMutableTreeNode root) {
		_root = root;
	}

	/**
	 * Returns the ACIDE - A Configurable IDE database panel tree model.
	 * 
	 * @return the ACIDE - A Configurable IDE database panel tree model.
	 */
	public DefaultTreeModel getTreeModel() {
		return _treeModel;
	}

	/**
	 * Set a new value to the ACIDE - A Configurable IDE database panel tree
	 * model.
	 * 
	 * @param treeModel
	 *            new value to set.
	 */
	public void setTreeModel(DefaultMutableTreeNode treeModel) {
		_treeModel = new DefaultTreeModel(treeModel);
	}


	/**
	 * Expands the ACIDE - A Configurable IDE database panel tree.
	 */
	public static void expandOrCollapsePath(JTree tree, TreePath treePath,
			int level, int currentLevel, boolean expand) {
		if (expand && level <= currentLevel && level > 0)
			return;

		TreeNode treeNode = (TreeNode) treePath.getLastPathComponent();
		TreeModel treeModel = tree.getModel();
		if (treeModel.getChildCount(treeNode) >= 0) {
			for (int i = 0; i < treeModel.getChildCount(treeNode); i++) {
				TreeNode n = (TreeNode) treeModel.getChild(treeNode, i);
				TreePath path = treePath.pathByAddingChild(n);
				expandOrCollapsePath(tree, path, level, currentLevel + 1,
						expand);
			}
			if (!expand && currentLevel < level)
				return;
		}
		if (expand) {
			tree.expandPath(treePath);
		} else {
			tree.collapsePath(treePath);
		}
	}

	/**
	 * Returns the ACIDE - A Configurable IDE database panel size.
	 * 
	 * @return the ACIDE - A Configurable IDE database panel size.
	 */
	public int getDataBaseSize() {
		return _size;
	}

	/**
	 * Sets a new value to the ACIDE - A Configurable IDE databse panel size.
	 * 
	 * @param explorerSize
	 *            new value to set.
	 */
	public void setDataBaseSize(int explorerSize) {
		_size = explorerSize;
	}

	/**
	 * Rebuilds the ACIDE - A Configurable IDE database tree
	 */
	 public void updateDataBaseTree (DefaultMutableTreeNode nodo) {
		try{
			if(((AcideDataBaseNodes) nodo).update())
				((DefaultTreeModel) _tree.getModel()).nodeChanged(nodo);
			
			 if (nodo.getChildCount() >= 0) {
				 for (Enumeration<?> e=nodo.children(); e.hasMoreElements(); ) {
					 DefaultMutableTreeNode n = (DefaultMutableTreeNode)e.nextElement();
					 updateDataBaseTree(n);
				 }
			 }    	    
		}catch(ClassCastException e){return;}
		 
	   }
			 
	public void updateDataBaseTree() {
		_tree.setCursor( Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
		updateDataBaseTree(_basesDatos);
		Set<String> keys = _dataView.keySet();
		Iterator<?> it = keys.iterator();
		while(it.hasNext()){
			String key = (String) it.next();
			AcideDataBaseDataViewControl h =  _dataView.get(key);
			h.update();
		}
		_tree.setCursor(
				Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));	
	}	
	
	public void updateDataBaseTreeOnChange() {
		_tree.setCursor(
				Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
		updateDataBaseTree(_basesDatos);
		_tree.setCursor(
				Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));	
	}	

	/**
	 * Shows the ACIDE - A Configurable IDE data base panel
	 */
	public void showDataBasePanel() {

		try {                           

			String textL = AcideDatabaseManager.getInstance().test();

			if (textL.equals("$success")) {
				
				if (_size <= 1) _size = DEFAULT_SIZE;
				
				JSplitPane container = AcideMainWindow.getInstance().getSpecificSplitPane(_splitContainer);				
				container.setDividerLocation(_size);
				setVisible(true);
				
				AcideMainWindow.getInstance().updateVisibility();
				
				getTreeModel().reload();
				
			}

		} catch (Exception exception) {
			AcideMainWindow.getInstance().getMenu().getViewMenu().getShowDataBasePanelCheckBoxMenuItem().setSelected(false);
			disposeDataBasePanel();
			// Updates the log
			AcideLog.getLog().error(exception.getMessage());
			exception.printStackTrace();
		}


	}

	/**
	 * Hides the ACIDE - A Configurable IDE data base panel
	 */
	public void disposeDataBasePanel() {
		JSplitPane container = AcideMainWindow.getInstance().getSpecificSplitPane(_splitContainer);		
		_size = container.getDividerLocation();
		if (container.getLeftComponent().getClass() == this.getClass()) {
			if (!container.getRightComponent().isVisible())
				_size = 300;
		} else if (container.getRightComponent().getClass() == this.getClass())
			if (!container.getLeftComponent().isVisible())
				_size = 300;
		setVisible(false);
		AcideMainWindow.getInstance().updateVisibility();
	}

	public class AcideDatabasePanelClickListener extends MouseAdapter {

		private JTree _tree;

		public AcideDatabasePanelClickListener(JTree tree) {
			_tree = tree;
			_tree.setToggleClickCount(0);
		}

		

		@SuppressWarnings("static-access")
		@Override
		public void mouseClicked(MouseEvent arg0) { 
			
			try{
				
				if(arg0.getClickCount()==2 && !_tree.isSelectionEmpty()){
					
					TreePath path = _tree.getSelectionPath();
					AcideDataBaseNodes selectedNode = (AcideDataBaseNodes) path.getLastPathComponent();
					
					//Double click on node table
					if(selectedNode instanceof NodeTable){
						
						String table = selectedNode.toString();
						String db = path.getParentPath().getParentPath().getLastPathComponent().toString();
						
						if (table.contains("("))
							table =   table.substring(0,table.indexOf("("));
							
						AcideDatabaseDataView panelDv  = AcideMainWindow.getInstance().getDataBasePanel().getDataView(db, table);
						LinkedList<String> info = AcideDatabaseManager.getInstance().getSelectAll(db, table);
						if(!info.isEmpty())
							panelDv.build(info);
	
						panelDv.setState(panelDv.NORMAL);
						panelDv.setAlwaysOnTop(true);
						panelDv.setAlwaysOnTop(false);
						panelDv.setVisible(true);
					} 	
						//Double click on node view
					else if (selectedNode instanceof NodeView){

						String view = selectedNode.toString();

						String db = path.getParentPath().getParentPath().getLastPathComponent().toString();

						if (view.contains("("))
							view = view.substring(0,view.indexOf("("));

						AcideDatabaseDataView panelDv  = AcideMainWindow.getInstance().getDataBasePanel().getDataView(db, view);
						LinkedList<String> info = AcideDatabaseManager.getInstance().getSelectAll(db, view);
						if(!info.isEmpty())
							panelDv.build(info);
							
						if (!panelDv.isReadOnly())
							panelDv.setIsReadOnly(true);

						panelDv.setState(panelDv.NORMAL);
						panelDv.setAlwaysOnTop(true);
						panelDv.setAlwaysOnTop(false);
						panelDv.setVisible(true);
					
					}else{
			
						if(_tree.isCollapsed(path)) _tree.expandPath(path);
							else _tree.collapsePath(path);

						if (selectedNode instanceof NodeDefinition){

							String node = path.getParentPath().getParentPath().getLastPathComponent().toString();

							if (node.contains("("))
								node = node.substring(0, node.indexOf("("));

							String database = path.getParentPath().getParentPath().getParentPath().getParentPath().getLastPathComponent().toString();

							String text = "";
							boolean editable;

							if (selectedNode.getParent().toString().equals(AcideLanguageManager.getInstance().getLabels().getString("s2036"))){
								text = AcideDatabaseManager.getInstance().getSQLText(database, node);
								editable = true;
								new AcideEnterTextWindow(text
										, AcideLanguageManager.getInstance().getLabels().getString("s2036"), editable);

							}else if (selectedNode.getParent().toString().equals(AcideLanguageManager.getInstance().getLabels().getString("s2288"))){
								text = AcideDatabaseManager.getInstance().getRAText(database, node);
								editable = true;
								new AcideEnterTextWindow(text
										, AcideLanguageManager.getInstance().getLabels().getString("s2288"), editable);
							} else {
								text = AcideDatabaseManager.getInstance().getDatalogText(database, node);
								editable = false;
								new AcideEnterTextWindow(text
										, AcideLanguageManager.getInstance().getLabels().getString("s2037"), editable);
							}
							
						}
					}
				}

				// Right button clicked then display popup
				if(arg0.getButton()==MouseEvent.BUTTON3){

					TreePath path = _tree.getSelectionPath();

					DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) path.getLastPathComponent();

					if (!(selectedNode instanceof NodeDDBB)){
						AcideDataBaseNodes parentNode = (AcideDataBaseNodes) path.getParentPath().getLastPathComponent();

						if (parentNode instanceof NodeColumns){
							AcideDataBaseNodes tableNode = (AcideDataBaseNodes) path.getParentPath().getParentPath().getLastPathComponent();

							if(_popUp!=null) _popUp.setVisible(false);
							_popUp = new AcideDataBasePanelColumnsPopupMenu(tableNode,selectedNode.toString());
							_popUp.show(arg0.getComponent(), arg0.getX(), arg0.getY() );
						}
					}

					_popUp = ((AcideDataBaseNodes)selectedNode).getPopUp();
					_popUp.show(arg0.getComponent(), arg0.getX(), arg0.getY() );
				}
				else {

					TreePath path = _tree.getSelectionPath();
					AcideDataBaseNodes selectedNode = (AcideDataBaseNodes) path.getLastPathComponent();

					if (selectedNode instanceof NodeConstraint)
							_tree.setToolTipText((String) selectedNode.getUserObject());
					else
							_tree.setToolTipText(null);

					if(_popUp!=null) _popUp.setVisible(false);
					if (_tree.isSelectionEmpty()) return;
				}

			} catch(ClassCastException cast){
				//DefaultMutableTreeNode
			}catch(NullPointerException cast){
				//DefaultMutableTreeNode
			}
		}
	}

	
	public void buildDataBase(String nameDataBase) {	
		
		NodeDB Dbase = new NodeDB(nameDataBase,_treeModel);
		_treeModel.insertNodeInto(Dbase,_basesDatos,_basesDatos.getChildCount());
	
		//a�adimos el nodo de tablas
		NodeTables tablas = new NodeTables(AcideLanguageManager.getInstance().getLabels().getString("s2028"),_treeModel);
		_treeModel.insertNodeInto(tablas,Dbase,Dbase.getChildCount());
	
		//a�adimos el nodo de vistas
		NodeViews vistas = new NodeViews(AcideLanguageManager.getInstance().getLabels().getString("s2027"),_treeModel);
		_treeModel.insertNodeInto(vistas,Dbase,Dbase.getChildCount());
	
		//a�adimos el nodo de las restricciones                         
		NodeICT restricc = new NodeICT(AcideLanguageManager.getInstance().getLabels().getString("s2029"),tipoRestriccion.Res,_treeModel);
		_treeModel.insertNodeInto(restricc,Dbase,Dbase.getChildCount());
	
		Dbase.setBuilt(true);
	
		// Builds the tables in the database
		LinkedList<String> tables = AcideDatabaseManager.getInstance().getTables(Dbase.toString());
		for(int j=0; j<tables.size();j++){

			String name = tables.get(j);
			
			if(name.contains("(")) 
				name =  name.substring(0, name.indexOf("("));
			
			//a�adimos la tabla
			NodeTable table = new NodeTable(name,_treeModel);
			_treeModel.insertNodeInto(table,tablas,tablas.getChildCount());
			
			//a�adimos el nodo de columnas
			NodeColumns columnsNode = new NodeColumns(AcideLanguageManager.getInstance().getLabels().getString("s2030"),_treeModel);

			_treeModel.insertNodeInto(columnsNode,table,table.getChildCount());
			
			//a�adimos el nodo de PK
			NodeICT pk = new NodeICT(AcideLanguageManager.getInstance().getLabels()
					.getString("s2031"),tipoRestriccion.PK,_treeModel);
			_treeModel.insertNodeInto(pk,table,table.getChildCount());
	
			//a�adimos el  nodo de FK
			NodeICT fk = new NodeICT(AcideLanguageManager.getInstance().getLabels()
					.getString("s2032"),tipoRestriccion.FK,_treeModel);
			_treeModel.insertNodeInto(fk,table,table.getChildCount());
	
			//a�adimos el  nodo de CK
			NodeICT ck = new NodeICT(AcideLanguageManager.getInstance().getLabels()
					.getString("s2033"),tipoRestriccion.CK,_treeModel);
			_treeModel.insertNodeInto(ck,table,table.getChildCount());
	
			//a�adimos el nodo de FD 
			NodeICT fd = new NodeICT(AcideLanguageManager.getInstance().getLabels()
					.getString("s2034"),tipoRestriccion.FD,_treeModel);
			_treeModel.insertNodeInto(fd,table,table.getChildCount());
	
			//a�adimos el nodo de IC
			NodeICT ic = new NodeICT(AcideLanguageManager.getInstance().getLabels().getString("s2035"),tipoRestriccion.IC,_treeModel);
			_treeModel.insertNodeInto(ic,table,table.getChildCount());
	
			table.setBuilt(true);
			
			// Builds the columns nodes in the table
			LinkedList<String> columns = AcideDatabaseManager.getInstance().getFields( nameDataBase, name);
			for(int h=0; h<columns.size();h++){
				String aux = columns.get(h);
				DefaultMutableTreeNode col = new DefaultMutableTreeNode(aux);
				_treeModel.insertNodeInto(col,columnsNode,columnsNode.getChildCount());
				}
			columnsNode.setBuilt(true);
	
			
			// Builds the primary keys
			LinkedList<String> primKey= AcideDatabaseManager.getInstance().getPrimKey(nameDataBase, name);
			for(int h=0;h<primKey.size();h++)
				{
					String aux= primKey.get(h);
					DefaultMutableTreeNode pKey = new DefaultMutableTreeNode(aux);
					_treeModel.insertNodeInto(pKey,pk,pk.getChildCount());
				}
	
			pk.setBuilt(true);
			
			// Builds the foreign keys
			LinkedList<String> fKey= AcideDatabaseManager.getInstance().getForeignKey(nameDataBase, name);
			for(int h=0;h<fKey.size();h++)
				{
					String aux= fKey.get(h);
					DefaultMutableTreeNode foreignKey = new DefaultMutableTreeNode(aux);
					_treeModel.insertNodeInto(foreignKey,fk,fk.getChildCount());
				}
	
			fk.setBuilt(true);		
	
			// Builds the candidate keys
			LinkedList<String> candKey= AcideDatabaseManager.getInstance().getCandidateKey(nameDataBase, name);
			for(int h=0;h<candKey.size();h++)
				{
					String aux= candKey.get(h);
					DefaultMutableTreeNode cKey = new DefaultMutableTreeNode(aux);
					_treeModel.insertNodeInto(cKey,ck,ck.getChildCount());
				}
	
			ck.setBuilt(true);
				
			//Builds the functional dependenciess
			LinkedList<String> funDep= AcideDatabaseManager.getInstance().getFuncionDep(nameDataBase, name);
			for(int h=0;h<funDep.size();h++)
				{
					String aux= funDep.get(h);
					DefaultMutableTreeNode fdep = new DefaultMutableTreeNode(aux);
					_treeModel.insertNodeInto(fdep,fd,fd.getChildCount());
				}
	
			fd.setBuilt(true);
		}
		tablas.setBuilt(true);
	
	
		LinkedList<String> views = AcideDatabaseManager.getInstance().getViews(nameDataBase);
		
		for(int j=0; j<views.size();j++){

			String name = views.get(j);
			
			if (name.contains("("))
				name = name.substring(0,name.indexOf("("));
			
			NodeView view = new NodeView(name,_treeModel);
			_treeModel.insertNodeInto(view,vistas,vistas.getChildCount());
			
			//a�adimos el nodo de las columnas
			NodeColumns columnsNode = new NodeColumns(AcideLanguageManager.getInstance().getLabels()
					.getString("s2030"),_treeModel);
			_treeModel.insertNodeInto(columnsNode,view,view.getChildCount());
	
			//a�adimos el nodo del SQL text
			DefaultMutableTreeNode SQLtextNode = new DefaultMutableTreeNode(AcideLanguageManager.getInstance().getLabels()
					.getString("s2036"));
			_treeModel.insertNodeInto(SQLtextNode,view,view.getChildCount());
	
			//a�adimos el nodo del Datalog text
			DefaultMutableTreeNode DatalogtextNode = new DefaultMutableTreeNode(AcideLanguageManager.getInstance().getLabels()
					.getString("s2037"));
			_treeModel.insertNodeInto(DatalogtextNode,view,view.getChildCount());
	
			view.setBuilt(true);    
			
			LinkedList<String> columns = AcideDatabaseManager.getInstance().getViewFields(nameDataBase,name);
			for(int h=0; h<columns.size();h++){
				String aux = columns.get(h);
				DefaultMutableTreeNode col = new DefaultMutableTreeNode(aux);
				_treeModel.insertNodeInto(col,columnsNode,columnsNode.getChildCount());
			}
			
			((NodeColumns) columnsNode).setBuilt(true);   
	
			String text = AcideDatabaseManager.getInstance().getSQLText(nameDataBase,name);
			NodeDEF SQLtext = new NodeDEF(text,tipoDefinition.SQL,_treeModel);
			_treeModel.insertNodeInto(SQLtext,SQLtextNode,SQLtextNode.getChildCount());
	
			((NodeDEF) SQLtextNode).setBuilt(true);
			
			String Datalog = AcideDatabaseManager.getInstance().getDatalogText(nameDataBase,name);
			NodeDEF DatalogText = new NodeDEF(Datalog,tipoDefinition.Datalog,_treeModel);
			_treeModel.insertNodeInto(DatalogText,DatalogtextNode,DatalogtextNode.getChildCount());
			
			((NodeDEF) DatalogtextNode).setBuilt(true);
	
		}
		vistas.setBuilt(true);
	
		LinkedList<String> IntConst= AcideDatabaseManager.getInstance().getIntConst(nameDataBase);
		for(int h=0;h<IntConst.size();h++)
		{
			String aux= IntConst.get(h);
			DefaultMutableTreeNode IConst = new DefaultMutableTreeNode(aux);
			_treeModel.insertNodeInto(IConst,restricc,restricc.getChildCount());
		}
		((NodeICT) restricc).setBuilt(true);
		expandOrCollapsePath(_tree, new TreePath(_tree.getModel().getRoot()), 4, 0, true);

	}

	/**
	 * Returns the correspondig data view of a given table
	 * @param db
	 * @param table
	 * @return
	 */
	public AcideDatabaseDataView getDataView(String db, String table) {
		if(_dataView.get(db) == null)
			addDatabaseToDataView(db);
		return _dataView.get(db).getDataView(db,table);
	}
	
	/**
	 * Adds a new database to the hash
	 * @param database
	 */
	public void addDatabaseToDataView(String database){
		_dataView.put(database,new AcideDataBaseDataViewControl(0));
	}
	
	/**
	 * Returns the corresponding constraints window of a given table
	 * @param db
	 * @param table
	 * @return
	 */
	public AcideConstraintDefinitionWindow getConstraintWindow(String db, String table) {
		return  _constraintWindow.get(db).getConstraintWindow(db,table);
	}
	
	/**
	 * Gets the ACIDE - A Configurable IDE data base panel menu bar
	 * @return the ACIDE - A Configurable IDE data base menu bar
	 */
	public JMenuBar getMenuBar() {
		return menuBar;
	}
	
	/**
	 * Gets the ACIDE - A Configurable IDE data base panel split container
	 * @return the ACIDE - A Configurable IDE data base split container
	 */
	public String getSplitContainer() {
		return _splitContainer;
	}
	
	/**
	 * Sets the ACIDE - A Configurable IDE data base panel split container
	 * 
	 * @param name
 	 *  		   name of the split container
	 */
	public void setSplitContainer(String name) {
		_splitContainer = name;
	}
	
	/**
	 * Builds the ACIDE - A Configurable IDE data base panel menu bar
	 * 
	 */
	public void buildMenuBar() {
		menuBar = new JMenuBar();
		
		// Creates the icon for the panel
		JLabel menu = new JLabel();
		menu.setIcon(new ImageIcon("./resources/icons/menu/view/showDataBasePanel.png"));
		// Adds the icon to the panel
		menuBar.add(menu);
		
		// Creates the label for the name of the panel
		JLabel name = new JLabel();
		// Adds the label of the name of the panel
		menuBar.add(name);
		// Sets the text of the name of the panel
		setTextMenuBar();
		
		// Creates the close view button
		JButton close = new JButton();
		// Sets the icon of the close view button
		close.setIcon(new ImageIcon("./resources/icons/panels/closeViewPanel.png"));
		// Adds the listener of the close button
		close.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent actionEvent) {
				disposeDataBasePanel();
				AcideProjectConfiguration.getInstance().setIsDatabasePanelShowed(false);
				AcideMainWindow.getInstance().getMenu().getViewMenu()
					.getShowDataBasePanelCheckBoxMenuItem().setSelected(
							AcideProjectConfiguration.getInstance().isDatabasePanelShowed());
				
				// If it is not the default project
				if (!AcideProjectConfiguration.getInstance().isDefaultProject())

					// The project has been modified
					AcideProjectConfiguration.getInstance().setIsModified(true);
			}
		});
		// Sets the margin of the close button
		close.setMargin(new Insets(0, 0, 0, 0));
		// Aligns the button to the right margin
		menuBar.add(Box.createHorizontalGlue());
		// Adds the close button
		menuBar.add(close);
		
		menuBar.setName("AcideDataBasePanel");
		
	}
	
	/**
	 * Sets the ACIDE - A Configurable IDE data base panel menu bar text
	 * 
	 */
	public void setTextMenuBar() {
		String name = AcideLanguageManager.getInstance().getLabels().getString("s2023");
		((JLabel) menuBar.getComponent(1)).setText(" " + name);
	}
	
	/*
	 * (non-Javadoc)
	 * @see java.awt.Component#getName()
	 */
	@Override
	public String getName() {
		return "AcideDataBasePanel";
	}

	/**
	 * Gets the ACIDE - A Configurable IDE data base panel show only names of table and view nodes
	 * boolean value.
	 * @return the ACIDE - A Configurable IDE data base panel nameTables boolean value.
	 */
	public boolean isNameTables() {
		return _nameTables;
	}
	
	/**
	 * Sets the ACIDE - A Configurable IDE data base panel show only names of table and view nodes
	 * boolean value.
	 * @param nameTables
 	 * 
	 */
	public void setNameTables(boolean nameTables) {
		_nameTables = nameTables;
	}
	
	/**
	 * Gets the ACIDE - A Configurable IDE data base panel show names and columns of table and view
	 * nodes boolean value.
	 * @return the ACIDE - A Configurable IDE data base panel nameFieldsTables boolean value.
	 */
	public boolean isNameFieldsTables() {
		return _nameFieldsTables;
	}
	
	/**
	 * Sets the ACIDE - A Configurable IDE data base panel show names and columns of table and view
	 * nodes boolean value.
	 * @param nameFieldsTables
 	 * 
	 */
	public void setNameFieldsTables(boolean nameFieldsTables) {
		_nameFieldsTables = nameFieldsTables;
	}

	/**
	 * Gets the ACIDE - A Configurable IDE data base panel show names, columns and types of table 
	 * and view nodes boolean value.
	 * @return the ACIDE - A Configurable IDE data base panel nameFieldsTypesTables boolean value.
	 */
	public boolean isNameFieldsTypesTables() {
		return _nameFieldsTypesTables;
	}

	/**
	 * Sets the ACIDE - A Configurable IDE data base panel show names, columns and types of table 
	 * and view nodes boolean value.
	 * @param nameFieldsTypesTables
 	 * 
	 */
	public void setNameFieldsTypesTables(boolean nameFieldsTypesTables) {
		_nameFieldsTypesTables = nameFieldsTypesTables;
	}

	/**
	 * Returns the hash containing the databases and their corresponding constraints windows
	 * @return _constraintWindow
	 */
	public HashMap<String,AcideConstraintDefinitionWindowControl> getTableConstraints() {
		return _constraintWindow;
	}
	
	/**
	 * Sets a new hash container for databases and their corresponding constraints windows
	 * @param constraintWindow
	 */
	public void setTableConstraints(HashMap<String,AcideConstraintDefinitionWindowControl> constraintWindow) {
		_constraintWindow = constraintWindow;
	}
	
}
