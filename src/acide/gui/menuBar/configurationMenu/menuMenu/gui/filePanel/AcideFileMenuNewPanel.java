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
package acide.gui.menuBar.configurationMenu.menuMenu.gui.filePanel;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;

import javax.swing.ImageIcon;
import javax.swing.JCheckBox;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JViewport;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

import acide.configuration.menu.AcideMenuObjectConfiguration;
import acide.configuration.menu.AcideMenuSubmenuConfiguration;
import acide.files.AcideFileManager;
import acide.files.utils.AcideFileOperation;
import acide.files.utils.AcideFileTarget;
import acide.files.utils.AcideFileType;
import acide.gui.mainWindow.AcideMainWindow;
import acide.gui.menuBar.configurationMenu.menuMenu.gui.AcideChooseMenuToSend;
import acide.gui.menuBar.configurationMenu.menuMenu.gui.AcideChooseSubmenuToInsert;
import acide.gui.menuBar.configurationMenu.menuMenu.gui.AcideMenuNewConfigurationWindow;
import acide.gui.menuBar.configurationMenu.menuMenu.gui.filePanel.utils.AcideFileMenuNewPanelTableModel;
import acide.gui.menuBar.configurationMenu.toolBarMenu.gui.consolePanel.utils.AcideComboBoxTableCellEditor;
import acide.gui.menuBar.configurationMenu.toolBarMenu.gui.consolePanel.utils.AcideComboBoxTableCellRenderer;
import acide.gui.menuBar.fileMenu.AcideFileMenu;
import acide.language.AcideLanguageManager;
import acide.utils.IconsUtils;

/**
 * ACIDE - A Configurable IDE file menu new panel.
 * 
 * @version 0.11
 * @see JPanel
 */
public class AcideFileMenuNewPanel extends JPanel {
	/**
	 * ACIDE - A Configurable IDE file menu panel serial version UID.
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * ACIDE - A Configurable IDE file menu panel configuration.
	 */
	private AcideMenuSubmenuConfiguration _configuration;
	/**
	 * ACIDE - A Configurable IDE menu new configuration window table of tool
	 * bar commands.
	 */
	private JTable _table;
	/**
	 * ACIDE - A Configurable IDE menu new configuration window table scroll
	 * panel.
	 */
	private JScrollPane _tableScrollPane;

	/**
	 * ACIDE - A Configurable IDE file menu panel file menu instance.
	 */
	private AcideFileMenu _fileMenu = AcideMainWindow.getInstance().getMenu()
			.getFileMenu();
	/**
	 * ACIDE - A Configurable IDE file menu panel components.
	 */
	private HashMap<String, JCheckBox> _components = new HashMap<String, JCheckBox>();
	/**
	 * ACIDE - A Configurable IDE  file menu panel are there changes.
	 */
	private static boolean _areThereChanges;
	/**
	 * ACIDE - A Configurable IDE file menu panel selected row.
	 */
	private int _selectedRow;
	
	/**
	 * Returns the ACIDE - A Configurable IDE file menu new panel
	 * configuration.
	 * 
	 * @return the ACIDE - A Configurable IDE file menu new panel
	 * configuration.
	 */
	public AcideMenuSubmenuConfiguration getConfiguration() {
		return _configuration;
	}

	/**
	 * Sets the ACIDE - A Configurable IDE file menu new panel configuration.
	 * @param configuration
	 * 		the new value for configuration.
	 */
	public void setConfiguration(AcideMenuSubmenuConfiguration configuration) {
		this._configuration = configuration;
	}

	/**
	 * Creates a new ACIDE - A Configurable IDE file menu panel.
	 */
	public AcideFileMenuNewPanel() {

		super();
		
		// There are no changes by the moment
		_areThereChanges = false;
				
		// Adds the components to the window
		addComponents();

		// Sets the action listeners of the window components
		setListeners();
		
		_selectedRow = 0;
	}
	
	/**
	 * Returns the are there changes flag.
	 * 
	 * @return the are there changes flag.
	 */
	public boolean areThereChanges() {
		return _areThereChanges;
	}

	/**
	 * Sets a new value to the are there changes flag.
	 * 
	 * @param areThereChange
	 *            new value to set.
	 */
	public void setAreThereChanges(boolean areThereChange) {
		_areThereChanges = areThereChange;
	}
	
	/**
	 * Sets the ACIDE - A Configurable IDE file menu new panel 
	 * data configuration.
	 */
	public void setDataFromConfiguration(){
		// Updates the file panel configuration panel table model
		// with the data
		((AcideFileMenuNewPanelTableModel) getTable().getModel())
				.setItems(_configuration.getItemsManager().getAllObjectsWithNumber());
	}
	
	/**
	 * Sets the listeners for the ACIDE - A Configurable IDE edit panel
	 * configuration panel components.
	 */
	private void setListeners() {
		
		// Sets the table mouse listener
		_table.addMouseListener(new TableMouseListener());
	}

	/**
	 * Adds the components to the ACIDE - A Configurable IDE file panel.
	 */
	private void addComponents() {

		// Sets the layout
		setLayout(new BorderLayout());

		// Creates the table
		buildTable();
	}
	
	/**
	 * Creates and configure the table and its model.
	 */
	public void buildTable() {

		// Creates the table with the model
		_table = new JTable(new AcideFileMenuNewPanelTableModel(
				this));

		// Sets the single selection in the table
		_table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		// The columns width are not equal
		_table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

		// These are the combo box values
		String[] values = new String[] {
				AcideLanguageManager.getInstance().getLabels()
						.getString("s1005"),
				AcideLanguageManager.getInstance().getLabels()
						.getString("s1006"),
				AcideLanguageManager.getInstance().getLabels()
						.getString("s1007"),
				AcideLanguageManager.getInstance().getLabels()
						.getString("s1008") };

		// Set the combo box editor on the 4th visible column
		TableColumn extraParameterColumn = _table.getColumnModel().getColumn(9);
		extraParameterColumn.setCellEditor(new AcideComboBoxTableCellEditor(
				values));

		// If the cell should appear like a combobox in its
		// non-editing state, also set the combobox renderer
		extraParameterColumn.setCellRenderer(new AcideComboBoxTableCellRenderer(values));
		
		// Creates the table scroll pane with the table
		_tableScrollPane = new JScrollPane(_table);

		// Sets its preferred size
		_tableScrollPane.setPreferredSize(new Dimension(1000, 250));
		_tableScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		
		// Adds the table scroll panel to the window
		add(_tableScrollPane, BorderLayout.CENTER);

		// Sets the table columns width
		setTableColumnsWidth();
	}
	
	/**
	 * Sets the width of each one of the table columns.
	 */
	public void setTableColumnsWidth() {

		// Gets the scroll pane viewport
		JViewport scroll = (JViewport) _table.getParent();

		// Gets the scroll pane viewport width
		int width = scroll.getPreferredSize().width;

		int columnWidth = 0;
		TableColumnModel columnModel = _table.getColumnModel();
		TableColumn tableColumn;

		for (int index = 0; index < _table.getColumnCount(); index++) {

			tableColumn = columnModel.getColumn(index);

			// One different width per each different column
			switch (index) {
			case 0: 
				columnWidth = (1 * width) / 100;
			case 1:
				columnWidth = (8 * width) / 100;
				break;
			case 2:
				columnWidth = (30 * width) / 100;
				break;
			case 3:
				columnWidth = (5 * width) / 100;
				break;
			case 4:
				columnWidth = (7 * width) / 100;
				break;
			case 5:
				columnWidth = (20 * width) / 100;
				break;
			case 6:
				columnWidth = (35 * width) / 100;
				break;
			case 7:
				columnWidth = (25 * width) / 100;
				break;
			case 8:
				columnWidth = (25 * width) / 100;
				break;
			case 9:
				columnWidth = (16 * width) / 100;
				break;
			}

			// Sets the table column preferred size
			tableColumn.setPreferredWidth(columnWidth);
		}
	}
	
	/**
	 * Create the context menu for the cell in the table.
	 * 
	 * @param rowIndex
	 *            row index.
	 * @param columnIndex
	 *            column index.
	 * 
	 * @return the context menu for the cell in the table.
	 */
	private JPopupMenu createContextMenu(final int rowIndex,
			final int columnIndex) {

		// Creates the context menu
		JPopupMenu contextMenu = new JPopupMenu();

		// Creates the load image menu item
		JMenuItem loadImageMenuItem = new JMenuItem(AcideLanguageManager
				.getInstance().getLabels().getString("s1018"));
		loadImageMenuItem.addActionListener(new ActionListener() {

			/*
			 * (non-Javadoc)
			 * 
			 * @see
			 * java.awt.event.ActionListener#actionPerformed(java.awt.event.
			 * ActionEvent)
			 */
			@Override
			public void actionPerformed(ActionEvent actionEvent) {

				// Asks for the file path to the user
				String absolutePath = AcideFileManager.getInstance()
						.askForFile(AcideFileOperation.OPEN,
								AcideFileTarget.FILES, AcideFileType.FILE, "",
								null);

				if (absolutePath != null) {

					// Updates the table model with the absolute path
					_table.getModel().setValueAt(absolutePath, rowIndex,
							5);
				}
			}
		});
		loadImageMenuItem.setIcon(IconsUtils.iconDefaultSize(new ImageIcon("./resources/icons/menu/configuration/menu/loadImage.png")));
		
		contextMenu.add(loadImageMenuItem);
		
		JMenuItem addSubmenu = new JMenuItem(AcideLanguageManager
				.getInstance().getLabels().getString("s2202"));
		addSubmenu.addActionListener(new AddSubmenuButtonAction());
		
		addSubmenu.setIcon(IconsUtils.iconDefaultSize(new ImageIcon("./resources/icons/menu/configuration/menu/addSubmenu.png")));
		
		contextMenu.add(addSubmenu);
		
		JMenuItem addItem = new JMenuItem(AcideLanguageManager
				.getInstance().getLabels().getString("s2203"));
		addItem.addActionListener(new AddItemButtonAction());
		
		addItem.setIcon(IconsUtils.iconDefaultSize(new ImageIcon("./resources/icons/menu/configuration/menu/addItem.png")));
		
		contextMenu.add(addItem);
		
		JMenuItem deleteItem = new JMenuItem(AcideLanguageManager
				.getInstance().getLabels().getString("s2205"));
		deleteItem.addActionListener(new DeleteItemButtonAction());
		
		deleteItem.setIcon(IconsUtils.iconDefaultSize(new ImageIcon("./resources/icons/menu/project/deleteFile.png")));
		
		contextMenu.add(deleteItem);
		
		JMenuItem upItem = new JMenuItem(AcideLanguageManager
				.getInstance().getLabels().getString("s2193"));
		upItem.addActionListener(new UpButtonAction());
		
		upItem.setIcon(IconsUtils.iconDefaultSize(new ImageIcon("./resources/icons/menu/configuration/menu/up_arrow.png")));
		
		
		contextMenu.add(upItem);
		
		JMenuItem downItem = new JMenuItem(AcideLanguageManager
				.getInstance().getLabels().getString("s2193"));
		downItem.addActionListener(new DownButtonAction());
		
		downItem.setIcon(IconsUtils.iconDefaultSize(new ImageIcon("./resources/icons/menu/configuration/menu/down_arrow.png")));
		
		
		contextMenu.add(downItem);
		
		JMenuItem sendToMenu = new JMenuItem(AcideLanguageManager
				.getInstance().getLabels().getString("s2219"));
		sendToMenu.addActionListener(new SendToMenuAction());
		
		//sendToMenu.setIcon(IconsUtils.iconDefaultSize(new ImageIcon("./resources/icons/menu/configuration/menu/down_arrow.png")));
		
		
		contextMenu.add(sendToMenu);
		
		JMenuItem insertInSubmenu = new JMenuItem(AcideLanguageManager
				.getInstance().getLabels().getString("s2220"));
		insertInSubmenu.addActionListener(new InsertInSubmenuAction());
		
		//insertInSubmenu.setIcon(IconsUtils.iconDefaultSize(new ImageIcon("./resources/icons/menu/configuration/menu/down_arrow.png")));
		
		
		contextMenu.add(insertInSubmenu);
		
		
		AcideMenuObjectConfiguration ob = ((AcideFileMenuNewPanelTableModel) getTable().getModel())
		 .getItems().get(rowIndex).getOb();
		
		if (ob.isSubmenu())
			loadImageMenuItem.setEnabled(false);

		return contextMenu;
	}
	
	/**
	 * Returns the table.
	 * 
	 * @return the table.
	 */
	public JTable getTable() {
		return _table;
	}
	
	/**
	 * ACIDE - A Configurable IDE tool bar configuration window console panel
	 * configuration panel table mouse listener.
	 * 
	 * @version 0.11
	 * @see MouseAdapter
	 */
	class TableMouseListener extends MouseAdapter {

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * java.awt.event.MouseAdapter#mousePressed(java.awt.event.MouseEvent )
		 */
		@Override
		public void mousePressed(MouseEvent mouseEvent) {
			maybeShowPopup(mouseEvent);
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * java.awt.event.MouseAdapter#mouseReleased(java.awt.event.MouseEvent )
		 */
		@Override
		public void mouseReleased(MouseEvent mouseEvent) {
			maybeShowPopup(mouseEvent);
		}

		/**
		 * Shows the popup menu only for the 4th column in the table.
		 * 
		 * @param mouseEvent
		 *            mouse event
		 */
		private void maybeShowPopup(MouseEvent mouseEvent) {
			

			//if (mouseEvent.isPopupTrigger() && _table.isEnabled()) {
			 if ((mouseEvent.getModifiers() & InputEvent.BUTTON3_MASK) != 0) {

				// Gets the point
				Point p = new Point(mouseEvent.getX(), mouseEvent.getY());
				int column = _table.columnAtPoint(p);
				int row = _table.rowAtPoint(p);

				// Translate table index to model index
				int modelColumn = _table
						.getColumn(_table.getColumnName(column))
						.getModelIndex();

				if (row >= 0 && row < _table.getRowCount()) {

					// Creates popup menu
					JPopupMenu contextMenu = createContextMenu(row, modelColumn);
					_selectedRow = row;

					// And show it
					if (contextMenu != null
							&& contextMenu.getComponentCount() > 0) {

						contextMenu.show(_table, p.x, p.y);
					}
				}
			}
		}
	}
	
	/**
	 * Builds and adds the components to the ACIDE - A Configurable IDE file
	 * menu panel.
	 */
	public void initComponents() {

		for (int index = 0; index < _fileMenu.getItemCount(); index++) {

			JMenuItem menuItem = null;
			try {

				// Gets the menu item from the file menu
				menuItem = _fileMenu.getItem(index);

				// If it is not a separator
				if (menuItem != null) {

					// Puts the component in the hash map
					_components.put(menuItem.getName(),
							new JCheckBox(menuItem.getText()));

					// Adds the component to the panel
					add(_components.get(menuItem.getName()));
				}
			} catch (ClassCastException exception) {

				// If the file JMenu has a menu instead of a JMenuItem

				// Gets the menu from the file menu
				JMenu menu = (JMenu) _fileMenu.getMenuComponent(index);

				// If it is not a separator
				if (menu != null) {

					// Puts the component in the hash map
					_components.put(menu.getName(),
							new JCheckBox(menu.getText()));

					// Adds the component to the panel
					add(_components.get(menu.getName()));
				}
			}
		}
	}

	/**
	 * Returns the ACIDE - A Configurable IDE file menu new panel
	 * selected item.
	 * 
	 * @return the ACIDE - A Configurable IDE file menu new panel
	 * selected item.
	 */
	public AcideMenuObjectConfiguration getSelectedItem() {
		if (getTable().getSelectedRow() > -1){
		return ((AcideFileMenuNewPanelTableModel) getTable().getModel())
					 .getItems().get(getTable().getSelectedRow()).getOb();
		}
		return null;
	}
	
	/**
	 * Returns the ACIDE - A Configurable IDE file menu new panel
	 * selected item.
	 * 
	 * @return the ACIDE - A Configurable IDE file menu new panel
	 * selected item.
	 */
	public AcideMenuObjectConfiguration getSelectedItemRightClick() {
		return ((AcideFileMenuNewPanelTableModel) getTable().getModel())
				 .getItems().get(_selectedRow).getOb();
	}
	
	/**
	 * Returns the ACIDE - A Configurable IDE file menu new panel
	 * selected item path.
	 * 
	 * @return the ACIDE - A Configurable IDE file menu new panel
	 * selected item path.
	 */
	public String getSelectedItemPath() {
		return (String) getTable().getValueAt(getTable().getSelectedRow(), 6);
	}
	
	/**
	 * Returns the ACIDE - A Configurable IDE file menu new panel
	 * selected item path.
	 * 
	 * @return the ACIDE - A Configurable IDE file menu new panel
	 * selected item path.
	 */
	public String getSelectedItemPathRightClick(){
		return (String) getTable().getValueAt(_selectedRow, 6);
	}
	
	/**
	 * ACIDE - A Configurable IDE menu configuration window add submenu button
	 * action listener.
	 * 
	 * @version 0.11
	 * @see ActionListener
	 */
	class AddSubmenuButtonAction implements ActionListener {

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.
		 * ActionEvent)
		 */
		@Override
		public void actionPerformed(ActionEvent actionEvent) {
				
			// Ask to the user for the text
			String name = JOptionPane.showInputDialog(
					null,
					AcideLanguageManager.getInstance().getLabels()
							.getString("s57"));
			
			if (name != null){

				
				AcideMenuSubmenuConfiguration menu = _configuration;
					
				AcideMenuObjectConfiguration submenu;
				String path;
				
				
					submenu = getSelectedItemRightClick();
					if (submenu != null){
					path = getSelectedItemPathRightClick();
					setAreThereChanges(menu.AddSubmenu(submenu, name, path));
					setDataFromConfiguration();
					((AcideFileMenuNewPanelTableModel) getTable().getModel()).fireTableDataChanged();
					} else {
						path = "Root/";
						submenu = menu;
						setAreThereChanges(menu.AddSubmenu(submenu, name, path));
						setDataFromConfiguration();
					}
			}
			
		}
	}
	
	/**
	 * ACIDE - A Configurable IDE menu configuration window add item button
	 * action listener.
	 * 
	 * @version 0.11
	 * @see ActionListener
	 */
	class AddItemButtonAction implements ActionListener {

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.
		 * ActionEvent)
		 */
		@Override
		public void actionPerformed(ActionEvent actionEvent) {
				
			// Ask to the user for the text
			String name = JOptionPane.showInputDialog(
					null,
					AcideLanguageManager.getInstance().getLabels()
							.getString("s57"));
			
			if (name != null){

				
				AcideMenuSubmenuConfiguration menu = _configuration;
					
				AcideMenuObjectConfiguration submenu;
				String path;
				
				
					submenu = getSelectedItemRightClick();
					if (submenu != null){
					path = getSelectedItemPathRightClick();
					setAreThereChanges(menu.AddItem(submenu, name, path));
					setDataFromConfiguration();
					((AcideFileMenuNewPanelTableModel) getTable().getModel()).fireTableDataChanged();
					} else {
						path = "Root/";
						submenu = menu;
						setAreThereChanges(menu.AddSubmenu(submenu, name, path));
						setDataFromConfiguration();
					}
			}
			
		}
	}
	
	/**
	 * ACIDE - A Configurable IDE menu configuration window delete item button
	 * action listener.
	 * 
	 * @version 0.11
	 * @see ActionListener
	 */
	class DeleteItemButtonAction implements ActionListener {

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.
		 * ActionEvent)
		 */
		@Override
		public void actionPerformed(ActionEvent actionEvent) {
			
			AcideMenuSubmenuConfiguration menu = _configuration;
				
			AcideMenuObjectConfiguration submenu;
			String path;
			
			
			submenu = getSelectedItemRightClick();
			if (submenu != null){
			if (submenu.isErasable()){
				int returnValueAreYouSure = JOptionPane.OK_OPTION;
					
				returnValueAreYouSure = JOptionPane.showConfirmDialog(null,
						AcideLanguageManager.getInstance().getLabels()
								.getString("s2211"), AcideLanguageManager
								.getInstance().getLabels().getString("s953"),JOptionPane.YES_NO_OPTION);
				// If it is OK
				if (returnValueAreYouSure == JOptionPane.OK_OPTION) {
					path = getSelectedItemPathRightClick();
					setAreThereChanges(menu.delete(submenu, path));
					setDataFromConfiguration();
					((AcideFileMenuNewPanelTableModel) getTable().getModel()).fireTableDataChanged();
				}
			} else {
				// Displays a message
				JOptionPane.showMessageDialog(null, AcideLanguageManager
						.getInstance().getLabels().getString("s2204"));
			}
			}
			
		}
	}
	
	/**
	 * ACIDE - A Configurable IDE menu configuration window up button
	 * action listener.
	 * 
	 * @version 0.11
	 * @see ActionListener
	 */
	class UpButtonAction implements ActionListener {

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.
		 * ActionEvent)
		 */
		@Override
		public void actionPerformed(ActionEvent actionEvent) {
			
			AcideMenuSubmenuConfiguration menu = _configuration;
				
			AcideMenuObjectConfiguration submenu;
			String path;
			
			submenu = getSelectedItemRightClick();
			if (submenu != null){
				path =getSelectedItemPathRightClick();
				setAreThereChanges(menu.toUp(submenu, path));
				setDataFromConfiguration();
				((AcideFileMenuNewPanelTableModel) getTable().getModel()).fireTableDataChanged();
			}

		}
	}
	
	/**
	 * ACIDE - A Configurable IDE menu configuration window down button
	 * action listener.
	 * 
	 * @version 0.11
	 * @see ActionListener
	 */
	class DownButtonAction implements ActionListener {

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.
		 * ActionEvent)
		 */
		@Override
		public void actionPerformed(ActionEvent actionEvent) {
			
			AcideMenuSubmenuConfiguration menu = _configuration;
				
			AcideMenuObjectConfiguration submenu;
			String path;
			
			submenu = getSelectedItemRightClick();
			if (submenu != null){
				path =getSelectedItemPathRightClick();
				setAreThereChanges(menu.toDown(submenu, path));
				setDataFromConfiguration();
				((AcideFileMenuNewPanelTableModel) getTable().getModel()).fireTableDataChanged();
			}

		}
	}
	
	/**
	 * ACIDE - A Configurable IDE menu configuration window send menu button
	 * action listener.
	 * 
	 * @version 0.11
	 * @see ActionListener
	 */
	class SendToMenuAction implements ActionListener {

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.
		 * ActionEvent)
		 */
		@Override
		public void actionPerformed(ActionEvent actionEvent) {
			
				
			AcideMenuObjectConfiguration submenu;
			String path;
			
			submenu = getSelectedItemRightClick();
			if (submenu != null){
				path =getSelectedItemPathRightClick();
	
				@SuppressWarnings("unused")
				AcideChooseMenuToSend chooseWindow = 
						new AcideChooseMenuToSend(AcideMenuNewConfigurationWindow.getInstance().getSubmenuConfiguration(),
						_configuration, submenu, path);
			
			}

		}
	}

	/**
	 * ACIDE - A Configurable IDE menu configuration window insert in submenu button
	 * action listener.
	 * 
	 * @version 0.11
	 * @see ActionListener
	 */
	class InsertInSubmenuAction implements ActionListener {

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.
		 * ActionEvent)
		 */
		@Override
		public void actionPerformed(ActionEvent actionEvent) {
				
			AcideMenuObjectConfiguration submenu;
			String path;
			
			submenu = getSelectedItemRightClick();
			if (submenu != null){
				path =getSelectedItemPathRightClick();
	
				@SuppressWarnings("unused")
				AcideChooseSubmenuToInsert chooseWindow = 
						new AcideChooseSubmenuToInsert(_configuration, submenu, path);
			
			}

		}
	}



}
