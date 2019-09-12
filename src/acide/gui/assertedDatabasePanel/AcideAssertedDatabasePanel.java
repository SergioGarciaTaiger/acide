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
package acide.gui.assertedDatabasePanel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Set;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import acide.gui.assertedDatabasePanel.listeners.AcideAssertedDatabaseKeyboardListener;
import acide.gui.assertedDatabasePanel.listeners.AcideAssertedDatabasePanelClearButtonListener;
import acide.gui.assertedDatabasePanel.listeners.AcideAssertedDatabasePanelRefreshButtonListener;
import acide.gui.debugPanel.debugCanvas.AcideDebugCanvas;
import acide.gui.debugPanel.debugSQLPanel.AcideDebugSQLPanel;
import acide.gui.debugPanel.traceDatalogPanel.AcideTraceDatalogPanel;
import acide.gui.debugPanel.traceSQLPanel.AcideTraceSQLPanel;
import acide.gui.debugPanel.utils.AcideDebugPanelHighLighter;
import acide.gui.graphUtils.Node;
import acide.gui.mainWindow.AcideMainWindow;
import acide.language.AcideLanguageManager;

/**
 * ACIDE - A Configurable IDE Asserted Database Panel.
 * 
 * @version 0.15
 * @see JFrame
 */
public class AcideAssertedDatabasePanel extends JFrame {

	/**
	 * ACIDE - A Configurable IDE asserted database panel class serial version
	 * UID.
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * ACIDE - A Configurable IDE asserted database panel table.
	 */
	private JTable _table;
	/**
	 * ACIDE - A Configurable IDE asserted database panel predicates.
	 */
	private Vector<Vector<String>> _predicates;
	/**
	 * ACIDE - A Configurable IDE asserted database panel scrollPane.
	 */
	private JScrollPane _scrollPane;
	/**
	 * ACIDE - A Configurable IDE asserted database panel reload button.
	 */
	private JButton _reloadButton;
	/**
	 * ACIDE - A Configurable IDE asserted database panel clear button.
	 */
	private JButton _clearButton;
	/**
	 * ACIDE - A Configurable IDE asserted database panel filter check box.
	 */
	private JCheckBox _filterCheckBox;
	/**
	 * ACIDE - A Configurable IDE asserted database panel predicates number.
	 */
	private JTextField _numberPredicates;
	/**
	 * ACIDE - A Configurable IDE asserted database panel button panel.
	 */
	private JPanel _buttonPanel;
	/**
	 * ACIDE - A Configurable IDE asserted database panel icon.
	 */
	private static final ImageIcon _ICON = new ImageIcon(
			"./resources/images/icon.png");
	/**
	 * ACIDE - A Configurable IDE asserted database panel selected node.
	 */
	private Node _selectedNode;

	/**
	 * Creates a new ACIDE - A Configurable IDE Asserted Database Panel
	 */
	public AcideAssertedDatabasePanel(String name) {

		super(name);

		// Sets the icon
		setIconImage(_ICON.getImage());

		// Sets the layout of the panel
		setLayout(new BorderLayout());

		// Builds the table
		buildTable();

		// Builds the button panel
		buildButtons();

		// Adds the listeners
		addListeners();

		// Adds the text panel
		add(_scrollPane, BorderLayout.CENTER);

		// Adds the button panel
		add(_buttonPanel, BorderLayout.SOUTH);

		setRenderer();

	}

	/**
	 * Sets the ACIDE - A Configurable IDE Asserted Database Panel renderer.
	 */
	private void setRenderer() {
		// Sets the renderer to the table
		_table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {

			private static final long serialVersionUID = 1L;

			/*
			 * (non-Javadoc)
			 * 
			 * @see javax.swing.table.DefaultTableCellRenderer#
			 * getTableCellRendererComponent(javax.swing.JTable,
			 * java.lang.Object, boolean, boolean, int, int)
			 */
			@Override
			public Component getTableCellRendererComponent(JTable table,
					Object value, boolean isSelected, boolean hasFocus,
					int row, int column) {
				// Gets the cell from the table
				Component c = super.getTableCellRendererComponent(table, value,
						isSelected, hasFocus, row, column);
				// Gets the highlighter
				AcideDebugPanelHighLighter highLighter = AcideMainWindow
						.getInstance().getDebugPanel().getTraceDatalogPanel()
						.getHighLighter();
				if (!isSelected) {
					// Updates the backgroud color of the cell
					c.setBackground(Color.WHITE);
					if (highLighter.isHighlight()) {
						highLighter.refreshHighLight();
						// Gets the asserted facts from the highlighter
						Set<String> facts = highLighter.getAssertedFacs();
						// Checks if any of the facts matches with the actual
						// cell
						// content or the neighbor cell content.
						for (String s : facts) {
							String name = s.substring(0, s.lastIndexOf("/"));
							String arity = s.substring(s.lastIndexOf("/") + 1);
							// Gets the actual cells content
							String val1 = _table.getValueAt(row, column)
									.toString().replaceAll("\\p{Space}", "");
							// Gets the neighbor cells content
							String val2 = _table
									.getValueAt(
											row,
											(column + 1)
													% _table.getColumnCount())
									.toString().replaceAll("\\p{Space}", "");
							boolean matches = false;
							// Checks the actual cell
							if (val1.startsWith(name)) {
								matches |= val1.split("\\x2C").length == Integer
										.parseInt(arity);
							}
							// Checks the neighbor cell
							if (val2.startsWith(name)) {
								matches |= val2.split("\\x2C").length == Integer
										.parseInt(arity);
							}

							if (matches)
								c.setBackground(AcideDebugPanelHighLighter._highLightColor);
						}

					}
				}

				return this;
			}
		});
		

	}

	public void clearRenderer() {
		// Sets the renderer to the table
		_table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {

			private static final long serialVersionUID = 1L;

			/*
			 * (non-Javadoc)
			 * 
			 * @see javax.swing.table.DefaultTableCellRenderer#
			 * getTableCellRendererComponent(javax.swing.JTable,
			 * java.lang.Object, boolean, boolean, int, int)
			 */
			@Override
			public Component getTableCellRendererComponent(JTable table,
					Object value, boolean isSelected, boolean hasFocus,
					int row, int column) {
				// Gets the cell from the table
				Component c = super.getTableCellRendererComponent(table, value,
						isSelected, hasFocus, row, column);

				// Updates the backgroud color of the cell
				if (!isSelected)
					c.setBackground(Color.WHITE);
				return this;
			}
		});
	}

	/**
	 * Builds the ACIDE - A Configurable IDE asserted database panel table.
	 */
	public void buildTable() {
		// Gets the asserted predicates
		Vector<Vector<String>> data = getAssertedPredicates();
		// Creates the column name vector
		Vector<String> columnNames = new Vector<String>();
		// Gets the names of the columns
		String lines = AcideLanguageManager.getInstance().getLabels()
				.getString("s2282");
		String predicates = AcideLanguageManager.getInstance().getLabels()
				.getString("s2283");
		// Adds the names of the columns
		columnNames.add(lines);
		columnNames.add(predicates);
		// Creates the table
		_table = new JTable(data, columnNames) {

			private static final long serialVersionUID = 1L;

			/*
			 * (non-Javadoc)
			 * 
			 * @see javax.swing.JTable#isCellEditable(int, int)
			 */
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		// Sets the column width
		_table.getColumnModel().getColumn(0).setMinWidth(25);
		_table.getColumnModel().getColumn(0).setMaxWidth(35);
		// Adds the table
		_scrollPane = new JScrollPane(_table);
		_table.setFillsViewportHeight(true);

	}

	/**
	 * Returns the ACIDE - A Configurable IDE asserted database panel asserted
	 * predicates.
	 * 
	 * @return the ACIDE - A Configurable IDE asserted database panel asserted
	 *         predicates.
	 */
	public Vector<Vector<String>> getAssertedPredicates() {

		_predicates = new Vector<Vector<String>>();
		Vector<Vector<String>> data = new Vector<Vector<String>>();

		AcideMainWindow.getInstance().getConsolePanel().getProcessThread()
				.getOutputGobbler().waitForTaskDone(100);

		// saves the send to console preference
		final boolean sendToConsole = AcideMainWindow.getInstance()
				.getConsolePanel().getProcessThread().getOutputGobbler()
				.get_sendToConsole();

		// sets the send to console to false so the output of
		// /listing_asserted will not be printed.
		AcideMainWindow.getInstance().getConsolePanel().getProcessThread()
				.getOutputGobbler().set_sendToConsole(false);

		String command = "/tapi /listing_asserted";

		AcideMainWindow.getInstance().getConsolePanel()
				.sendCommandToConsole(command, "");
		AcideMainWindow.getInstance().getConsolePanel().getProcessThread()
				.getOutputGobbler().waitForTaskDone(150);
		String resultCommand = AcideMainWindow.getInstance().getConsolePanel()
				.getProcessThread().getOutputGobbler().getText();

		String[] preds = resultCommand.split("\\$");
		int j = 1;
		for (int i = 0; i < preds.length - 1; i++) {
			if (preds[i] != null && !preds[i].startsWith("$")
					&& !preds[i].contains(":-") && !preds[i].equals("")) {
				Vector<String> vec = new Vector<String>();
				vec.add(Integer.toString(j));
				vec.add(preds[i]);
				data.add(vec);
				_predicates.add(vec);
				j++;
			}
		}

		AcideMainWindow.getInstance().getConsolePanel().getProcessThread()
				.getOutputGobbler().set_sendToConsole(sendToConsole);

		return data;
	}

	/**
	 * Buids the the ACIDE - A Configurable IDE asserted database panel buttons.
	 */
	public void buildButtons() {

		_buttonPanel = new JPanel();
		_buttonPanel.setLayout(new FlowLayout());

		_reloadButton = new JButton();
		_reloadButton.setIcon(new ImageIcon(
				"./resources/icons/panels/refresh.png"));
		_reloadButton.setPreferredSize(new Dimension(40, 25));
		//sets tooltip button 
		_reloadButton.setToolTipText(AcideLanguageManager.getInstance().getLabels().getString("s2044"));
				

		_buttonPanel.add(_reloadButton);

		_clearButton = new JButton("C");
		_clearButton.setPreferredSize(new Dimension(40, 25));
		//sets tooltip button 
		_clearButton.setToolTipText(AcideLanguageManager.getInstance().getLabels().getString("s455"));
				
		_buttonPanel.add(_clearButton);

		String nameFilter = AcideLanguageManager.getInstance().getLabels()
				.getString("s2281");
		_filterCheckBox = new JCheckBox(nameFilter);
		_filterCheckBox.setSelected(false);
		//sets tooltip button 
		_filterCheckBox.setToolTipText(AcideLanguageManager.getInstance().getLabels().getString("s456"));
				
		_buttonPanel.add(_filterCheckBox);

		int size = _predicates.size();

		String rule = AcideLanguageManager.getInstance().getLabels()
				.getString("s2284");
		String rules = AcideLanguageManager.getInstance().getLabels()
				.getString("s2285");

		if (size == 1)
			_numberPredicates = new JTextField(size + " " + rule);
		else
			_numberPredicates = new JTextField(size + " " + rules);

		_numberPredicates.setEditable(false);
		_numberPredicates.setPreferredSize(new Dimension(50, 20));

		_buttonPanel.add(_numberPredicates);

	}

	/**
	 * Adds the listeners to the ACIDE - A Configurable IDE asserted database
	 * panel.
	 */
	public void addListeners() {

		this.addWindowListener(new WindowAdapter() {
			/*
			 * (non-Javadoc)
			 * 
			 * @see
			 * java.awt.event.WindowAdapter#windowClosing(java.awt.event.WindowEvent
			 * )
			 */
			@Override
			public void windowClosing(WindowEvent windowEvent) {
				AcideMainWindow.getInstance().getMenu().getViewMenu()
						.getShowAssertedDatabasePanelCheckBoxMenuItem()
						.setSelected(false);
				AcideMainWindow.getInstance().closeAssertedDatabasePanel();
			}
		});

		_table.addKeyListener(new AcideAssertedDatabaseKeyboardListener());

		_reloadButton
				.addActionListener(new AcideAssertedDatabasePanelRefreshButtonListener());

		_clearButton
				.addActionListener(new AcideAssertedDatabasePanelClearButtonListener());

		_filterCheckBox.addItemListener(new ItemListener() {
			/*
			 * (non-Javadoc)
			 * 
			 * @see
			 * java.awt.event.ItemListener#itemStateChanged(java.awt.event.ItemEvent
			 * )
			 */
			public void itemStateChanged(ItemEvent arg0) {
				if (_filterCheckBox.isSelected())
					refreshNode();
				else
					refresh();
						
			}
		});

	}

	/**
	 * Refresh the content of the the ACIDE - A Configurable IDE asserted
	 * database panel.
	 */
	public void refresh() {
		// Removes the previous content of the table
		int rowCount = ((DefaultTableModel) _table.getModel()).getRowCount();
		for (int i = rowCount - 1; i >= 0; i--) {
			((DefaultTableModel) _table.getModel()).removeRow(i);
		}
		// Adds the asserted predicates to the table
		getAssertedPredicates();
		for (int i = 0; i < _predicates.size(); i++) {
			((DefaultTableModel) _table.getModel()).addRow(_predicates.get(i));
		}

		// Refresh the number of predicates
		int size = _predicates.size();

		String rule = AcideLanguageManager.getInstance().getLabels()
				.getString("s2284");
		String rules = AcideLanguageManager.getInstance().getLabels()
				.getString("s2285");

		if (size == 1)
			_numberPredicates.setText(size + " " + rule);
		else
			_numberPredicates.setText(size + " " + rules);

	}

	/**
	 * Refresh the nodes of the the ACIDE - A Configurable IDE asserted database
	 * panel.
	 */
	public void refreshNode() {

		AcideMainWindow.getInstance().getConsolePanel().getProcessThread()
				.getOutputGobbler().waitForTaskDone(100);

		int rowCount = ((DefaultTableModel) _table.getModel()).getRowCount();
		for (int i = rowCount - 1; i >= 0; i--) {
			((DefaultTableModel) _table.getModel()).removeRow(i);
		}

		if (_selectedNode != null) {
			// saves the send to console preference
			final boolean sendToConsole = AcideMainWindow.getInstance()
					.getConsolePanel().getProcessThread().getOutputGobbler()
					.get_sendToConsole();

			// sets the send to console to false so the output of
			// /listing_asserted will not be printed.
			AcideMainWindow.getInstance().getConsolePanel().getProcessThread()
					.getOutputGobbler().set_sendToConsole(false);

			String command = "/tapi /listing_asserted "
					+ _selectedNode.getLabel();

			AcideMainWindow.getInstance().getConsolePanel()
					.sendCommandToConsole(command, "");
			AcideMainWindow.getInstance().getConsolePanel().getProcessThread()
					.getOutputGobbler().waitForTaskDone(150);

			String resultCommand = AcideMainWindow.getInstance()
					.getConsolePanel().getProcessThread().getOutputGobbler()
					.getText();

			String[] preds = resultCommand.split("\\$");
			int j = 1;
			for (int i = 0; i < preds.length - 1; i++) {
				if (preds[i] != null && !preds[i].startsWith("$")
						&& !preds[i].contains(":-") && !preds[i].equals("")) {
					Vector<String> vec = new Vector<String>();
					vec.add(Integer.toString(j));
					vec.add(preds[i]);
					((DefaultTableModel) _table.getModel()).addRow(vec);
					j++;
				}
			}
			AcideMainWindow.getInstance().getConsolePanel().getProcessThread()
					.getOutputGobbler().set_sendToConsole(sendToConsole);
		}

		int size = ((DefaultTableModel) _table.getModel()).getRowCount();

		String rule = AcideLanguageManager.getInstance().getLabels()
				.getString("s2284");
		String rules = AcideLanguageManager.getInstance().getLabels()
				.getString("s2285");

		if (size == 1)
			_numberPredicates.setText(size + " " + rule);
		else
			_numberPredicates.setText(size + " " + rules);

	}

	/**
	 * Clears the ACIDE - A Configurable IDE asserted database panel asserted
	 * table.
	 */
	public void clear() {
		_table.clearSelection();
	}

	/**
	 * Closes the ACIDE - A Configurable IDE asserted database panel.
	 */
	public void close() {
		AcideMainWindow.getInstance().getMenu().getViewMenu()
				.getShowAssertedDatabasePanelCheckBoxMenuItem()
				.setSelected(false);
		AcideMainWindow.getInstance().closeAssertedDatabasePanel();
	}

	/**
	 * Updates the ACIDE - A Configurable IDE asserted database panel selected
	 * node.
	 */
	public void updateSelectedNode() {

		if (AcideMainWindow.getInstance().getDebugPanel().isVisible()) {
			// Gets the active tab of the debug panel
			int index = AcideMainWindow.getInstance().getDebugPanel()
					.getTabbedPane().getSelectedIndex();

			_selectedNode = null;
			if (index == 0)
				// Gets the selected node from the trace datalog panel
				_selectedNode = ((AcideTraceDatalogPanel) AcideMainWindow
						.getInstance().getDebugPanel().getTabbedPane()
						.getSelectedComponent()).getDebugDatalogCanvas()
						.getSelectedNode();
			else
				// Gets the selected node from the trace SQL panel
				_selectedNode = ((AcideTraceSQLPanel) AcideMainWindow
						.getInstance().getDebugPanel().getTabbedPane()
						.getSelectedComponent()).getDebugSQLCanvas()
						.getSelectedNode();
			// Refreshes the node
			refreshNode();

		}

	}

	/**
	 * Returns the ACIDE - A Configurable IDE asserted database panel selected
	 * node.
	 * 
	 * @return the ACIDE - A Configurable IDE asserted database panel selected
	 *         node.
	 */
	public Node getSelectedNode() {
		return _selectedNode;
	}

	/**
	 * Sets a new value to the ACIDE - A Configurable IDE asserted database
	 * panel selected node.
	 * 
	 * @param _selectedNode
	 *            the new value to set.
	 */
	public void setSelectedNode(Node _selectedNode) {
		this._selectedNode = _selectedNode;
	}

	/**
	 * Returns the ACIDE - A Configurable IDE asserted database panel predicates
	 * number check box.
	 * 
	 * @return the ACIDE - A Configurable IDE asserted database panel predicates
	 *         number check box.
	 */
	public JCheckBox getNumberOfPredicatesCheckBox() {
		return _filterCheckBox;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.awt.Component#isFocusable()
	 */
	@Override
	public boolean isFocusable() {
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.awt.Container#getPreferredSize()
	 */
	@Override
	public Dimension getPreferredSize() {
		return new Dimension(300, 400);
	}

}
