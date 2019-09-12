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
package acide.gui.debugPanel.debugCanvas.traceDatalogDataView;

import java.awt.BorderLayout;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Vector;

import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingUtilities;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

import acide.gui.databasePanel.dataView.AcideDataBaseDataViewTable;
import acide.gui.databasePanel.dataView.AcideDataViewColumnPopUp;
import acide.gui.databasePanel.dataView.AcideDatabaseDataView;
import acide.gui.databasePanel.dataView.ColumnSorter;
import acide.gui.databasePanel.dataView.types;
import acide.gui.databasePanel.dataView.menuBar.AcideDataViewMenuBar;

/**
 * 
 * ACIDE - A Configurable IDE Trace Datalog DataView
 * 
 * @version 0.16
 * 
 */
public class AcideTraceDatalogDataView extends AcideDatabaseDataView {

	/**
	 * ACIDE - A Configurable IDE trace datalog dataview class serial version
	 * UID.
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * ACIDE - A Configurable IDE trace datalog dataview name.
	 */
	private String _name;

	/**
	 * 
	 * Creates a new ACIDE - A Configurable IDE trace datalog data view panel.
	 * 
	 * @param db
	 *            the database name.
	 * @param tabla
	 *            the table name.
	 */
	public AcideTraceDatalogDataView(String db, String tabla) {
		super(db, tabla);
		setIsReadOnly(true);
		AcideDataViewMenuBar menu = (AcideDataViewMenuBar) getJMenuBar();
		menu.getFileMenu().getExecuteQueryMenuItem().setEnabled(false);

	}

	/**
	 * Creates a new ACIDE - A Configurable IDE trace datalog data view panel.
	 */
	public AcideTraceDatalogDataView() {
		super("", "");
		setIsReadOnly(true);
		AcideDataViewMenuBar menu = (AcideDataViewMenuBar) getJMenuBar();
		menu.getFileMenu().getExecuteQueryMenuItem().setEnabled(false);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * acide.gui.databasePanel.dataView.AcideDatabaseDataView#build(java.util
	 * .LinkedList)
	 */
	@Override
	public void build(LinkedList<String> info) {
		// Removes the scroll panel content
		if (_scrollPane != null) {
			_scrollPane.remove(_jTable);
			this.getContentPane().remove(_scrollPane);
		}
		// builds the data
		buildData(info);
		// creates a new scroll panel
		_scrollPane = new JScrollPane(_jTable);
		// sets the scroll bar
		_scrollPane
				.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		// sets the default size of the panel
		_scrollPane.setSize(_totalColumns * 20 + _width,
				Integer.valueOf(_totalRecords) * 5 + _height);
		_scrollPane.setViewportView(_jTable);
		// adds the scroll panel
		this.getContentPane().add(_scrollPane, BorderLayout.CENTER);
		// updates the visibility
		setVisible(true);
		// sets the panel on top
		setAlwaysOnTop(true);
		// builds the status bar
		if (_statusBar == null) {
			_statusBar = buildStatusBar();
			this.getContentPane().add(_statusBar, BorderLayout.SOUTH);
		}
		
		if (!_isReadOnly) {
			_jTable.setSurrendersFocusOnKeystroke(true);

			final AcideTraceDatalogDataView dataView = this;
			// adds the mouse listener
			_jTable.getTableHeader().addMouseListener(new MouseAdapter() {
				/*
				 * (non-Javadoc)
				 * @see java.awt.event.MouseAdapter#mouseClicked(java.awt.event.MouseEvent)
				 */
				@SuppressWarnings("unchecked")
				@Override
				public void mouseClicked(MouseEvent mouseEvent) {
					// gets the column model
					TableColumnModel columnModel = _jTable.getColumnModel();
					// gets the active column index of the view
					int viewColumn = columnModel.getColumnIndexAtX(mouseEvent
							.getX());
					// gets the active column index of the table
					int column = _jTable.convertColumnIndexToModel(viewColumn);
					
					if (SwingUtilities.isRightMouseButton(mouseEvent)) {
						// shows the pop up menu
						AcideDataViewColumnPopUp popUp = new AcideDataViewColumnPopUp(
								dataView, column);
						// places the pop up menu
						popUp.show(mouseEvent.getComponent(),
								mouseEvent.getX(), mouseEvent.getY());
					} else {
						if(column==0){
							// selects all the cells of the table
							_jTable.selectAll();
						} else{							
							if (column>0 && !_isFilter) {
								// gets the data vector
								Vector<?> data = ((MyTableModel) _jTable.getModel()).getDataVector();
								_isAscending = !_isAscending;
								// gets the name of the column
								_nameColumnSorted = ((MyTableModel)_jTable.getModel()).getColumnName(column);
								// sorts the cells
								updateSortByWindow();								
								Collections.sort(data, new ColumnSorter(column,_isAscending,_tipos));
								((MyTableModel) _jTable.getModel()).fireTableStructureChanged();
							}
						}
					}

				}
			});
		}
		
		updateRecord();

		
		// gets the height of the panel
		int height = Integer.valueOf(_totalRecords) * 5 + _height;
		// gets the screen size
		int screenSize = Toolkit.getDefaultToolkit().getScreenSize().height;
		// updates the height of the panel
		if (height > Toolkit.getDefaultToolkit().getScreenSize().height)
			height = screenSize * 9 / 10;
		// sets the size of the panel
		if ((this.getWidth() > _width) || (this.getHeight() > height))
			this.setSize(getWidth(), getHeight());
		else
			this.setSize(_totalColumns * 20 + _width, height);
		// gets the menu bar
		AcideDataViewMenuBar menu = (AcideDataViewMenuBar) getJMenuBar();
		// disables the execute query button
		menu.getFileMenu().getExecuteQueryMenuItem().setEnabled(false);
		// disables the refresh files button
		menu.getProjectMenu().getRefreshFilesMenu().setEnabled(false);
		
		setAlwaysOnTop(false);
	}

	/**
	 * builds the table of the ACIDE - A Configurable IDE trace datalog data
	 * view panel
	 * 
	 * @param info
	 *            the data of the table
	 */
	private void buildData(LinkedList<String> info) {
		// disables the filter, ascending, descending flags
		_isFilter = false;
		_isAscending = false;
		_isDescending = false;
		// sets the initial index
		int index = 1;
		// defines the data model
		String[] dataModel;
		// defines the data.
		Object[][] data;
		// Initializes the row count
		int rows = 0;
		// updates the row count
		for (int i = 0; i < info.size(); i++) {
			if (info.get(i).equals("$")) {
				rows++;

			}
		}
		// sets the types of the data
		for (int i = 0; i < _totalColumns; i++)
			_tipos.add(types.STRING);
		// creates the data model container
		dataModel = new String[_totalColumns + 1];
		// creates the data container
		data = new Object[rows + 1][_totalColumns + 1];
		// sets the names of the columns
		dataModel[0] = "";
		for (int i = 1; i < _totalColumns + 1; i++)
			dataModel[i] = _name + "_" + i;
		if (rows == 0)
			data[rows][_totalColumns] = "";
		else
			data[rows - 1][_totalColumns] = "";
		int inicio = 0;
		for (int row = 0; row < rows; row++) {
			for (int column = 1; column < _totalColumns + 1; column++) {
				// gets the data of the info
				String infoColumn = info.get(index);
				inicio = infoColumn.indexOf("'");
				if (inicio >= 0) {
					int fin = infoColumn.lastIndexOf("'");
					infoColumn = infoColumn.substring(inicio + 1, fin);
				}
				if (infoColumn.trim().equals("null"))
					infoColumn = "";
				// sets the data of the cell
				data[row][column] = infoColumn;
				index++;
			}
			index++;
		}
		// updates the total recods
		_totalRecords = String.valueOf(rows);
		// creates the table mode
		MyTableModel model = new MyTableModel();
		// sets the data to teh model
		model.setDataVector(data, dataModel);
		// creates the table
		_jTable = new AcideDataBaseDataViewTable(model, this);
		// sets the table scrollable
		_jTable.setAutoscrolls(true);
		// updates the min width
		_jTable.getColumnModel().getColumn(0).setMinWidth(20);
		// updates the max width
		_jTable.getColumnModel().getColumn(0).setMaxWidth(20);
		// sets resizable
		_jTable.getColumnModel().getColumn(0).setResizable(false);
		// sets the renderer of the table
		_jTable.setDefaultRenderer(Object.class, new RenderLabel());
		// sets the undo manager
		model.addUndoableEditListener(_undoManager);
		// sets the columns
		setColumns();
		// updates the selected cell
		_jTable.setSelectedField(0, 1);
	}

	/**
	 * Sets the columns of the ACIDE - A Configurable IDE trace datalog data
	 * view panel.
	 */
	private void setColumns() {
		// updates the columns
		_columns = new Vector<TableColumn>();
		for (int i = 0; i < _jTable.getColumnCount(); i++) {
			// gest the clumns of the tables
			_columns.add(_jTable.getColumnModel().getColumn(i));
		}
	}

	/**
	 * Returns the ACIDE - A Configurable IDE trace datalog data view panel
	 * name.
	 * 
	 * @returnthe ACIDE - A Configurable IDE trace datalog data view panel name.
	 */
	public String get_name() {
		return _name;
	}

	/**
	 * Sets a new value to the ACIDE - A Configurable IDE trace datalog data
	 * view panel name.
	 * 
	 * @param _name
	 *            the new value to set.
	 */
	public void set_name(String _name) {
		this._name = _name;
	}

}
