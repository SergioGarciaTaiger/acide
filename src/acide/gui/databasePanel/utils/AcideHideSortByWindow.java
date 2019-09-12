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
package acide.gui.databasePanel.utils;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Locale;
import java.util.Vector;

import javax.swing.DefaultCellEditor;
import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

import acide.gui.databasePanel.dataView.AcideDatabaseDataView;
import acide.language.AcideLanguageManager;

/**
 * ACIDE - A Configurable IDE hide sort by window.
 * 
 * @version 0.16
 * @see JDialog
 */
public class AcideHideSortByWindow extends JDialog{

	private static final long serialVersionUID = 1L;
	
	private static final ImageIcon ICON = new ImageIcon(
			"./resources/images/icon.png");

	/**
	 * ACIDE - Table to show the data 
	 */
	protected JTable table;

	/**
	 * ACIDE - Scroll of the main panel 
	 */
	private JScrollPane _scrollPane;
	
	/**
	 * ACIDE - Dataview to hide/show columns 
	 */
	private AcideDatabaseDataView _dataView;

	/**
	 * ACIDE - Ok button 
	 */
	private JButton _applyButton;
	
	/**
	 * ACIDE - Cancel button 
	 */
	private JButton _cancelButton;	

	/**
	 * ACIDE - Panel to add components
	 */
	private JPanel _panel;

	//private Vector<String> names;
	
	
	public AcideHideSortByWindow(String title,AcideDatabaseDataView _jtable){

		this._dataView = _jtable;
		
		_dataView.setAlwaysOnTop(false);
		
		_dataView.setEnabled(false);

		setIconImage(ICON.getImage());

		this.setTitle(title);

		buildComponents();

		setLookAndFeel();

		addListeners();

		setDefaultCloseOperation(2);
		
		setVisible(true);		
	}

	private void buildComponents(){	
	
		MyTableModel model = new MyTableModel();
		model.build();
		table = new JTable(model);

		table.setPreferredScrollableViewportSize(new Dimension(500, 70));
        table.setFillsViewportHeight(true);
        
        TableCellRenderer rendererFromHeader = table.getTableHeader().getDefaultRenderer();
        JLabel headerLabel = (JLabel) rendererFromHeader;
        headerLabel.setHorizontalAlignment(JLabel.CENTER);
        
        //Fiddle with the sort column's cell editors/renderers.
        setUpCheckRowsColumn(table, table.getColumnModel().getColumn(0));

        //Fiddle with the sort column's cell editors/renderers.
        setUpNameRowsColumn(table, table.getColumnModel().getColumn(1));
        
        //Fiddle with the names column's cell editors/renderers.
        setUpSortTypeColumn(table, table.getColumnModel().getColumn(2));
        
        
        _applyButton = new JButton(AcideLanguageManager.getInstance().getLabels().getString("s154"));

        if (AcideLanguageManager.getInstance().getCurrentLocale().equals(new Locale("en", "EN"))){
            _applyButton.setMnemonic('O');
        } else {
            _applyButton.setMnemonic('A');
        }

        _cancelButton = new JButton(AcideLanguageManager.getInstance().getLabels().getString("s369"));

        _cancelButton.setMnemonic('C');

        _panel = new JPanel();
        
        _scrollPane=new JScrollPane(table);
	}

	private void setUpCheckRowsColumn(JTable table, TableColumn checkColumn) {
		//Set up the editor for the checkbox cells.
		JCheckBox checkBox = new JCheckBox();
		
		checkBox.setHorizontalAlignment(JCheckBox.CENTER);
		
		checkColumn.setCellEditor(new DefaultCellEditor(checkBox));	
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void setUpNameRowsColumn(JTable table, TableColumn comboColumn) {
		//Set up the editor for the combobox cells.
		Vector<String> names = _dataView.getColumnsNames();
		JComboBox comboBox = new JComboBox();
        
		for(int i=0;i<names.size();i++){
			comboBox.addItem(names.get(i));
		}
		
        comboColumn.setCellEditor(new DefaultCellEditor(comboBox));

        //Set up tool tips for the combo cells.
        DefaultTableCellRenderer renderer =
                new DefaultTableCellRenderer();
        //renderer.setToolTipText("Click for combo box");
        comboColumn.setCellRenderer(renderer);
		
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void setUpSortTypeColumn(JTable table, TableColumn comboColumn) {
		//Set up the editor for the combobox cells.
        
		JComboBox comboBox = new JComboBox();
        comboBox.addItem(AcideLanguageManager.getInstance().getLabels().getString("s2083"));
        comboBox.addItem(AcideLanguageManager.getInstance().getLabels().getString("s2084"));
        comboBox.addItem(AcideLanguageManager.getInstance().getLabels().getString("s2230"));

        comboColumn.setCellEditor(new DefaultCellEditor(comboBox));

        //Set up tool tips for the combo cells.
        DefaultTableCellRenderer renderer =
                new DefaultTableCellRenderer();
        //renderer.setToolTipText("Click for combo box");
        comboColumn.setCellRenderer(renderer);
	}

	
	private void setLookAndFeel(){

		GroupLayout layout = new GroupLayout(_panel);

		_panel.setLayout(layout);

		layout.setHorizontalGroup(
				layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(layout.createSequentialGroup()
						.addGap(10,10,10)
						.addComponent(_scrollPane,300,300,300))
						.addGroup(layout.createSequentialGroup()
								.addGap(70,70,70)
								.addComponent(_applyButton, 80,80,80)
								.addComponent(_cancelButton, 80,80,80)));
		layout.setVerticalGroup(
				layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(layout.createSequentialGroup()
						.addGap(10,10,10)
						.addComponent(_scrollPane,200,200,200)
						.addGap(20,20,20))
						.addGroup(layout.createSequentialGroup()
								.addGap(220,220,220)
								.addComponent(_applyButton,25,25,25))
								.addGroup(layout.createSequentialGroup()
										.addGap(220,220,220)
										.addComponent(_cancelButton, 25,25,25)));
		_panel.setSize(500,300);

		this.getContentPane().add(_panel);
		this.setSize(380,320);	
		this.setLocationRelativeTo(null);
		this.setResizable(false);

		// Packs the window components
		pack();

		setModal(true);
	}
	
	private void addListeners() {

		_applyButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				String command = "";
				Vector<String>  list = new Vector<String>();
				
				for(int i=0;i<table.getRowCount();i++){
					boolean active = (Boolean) table.getValueAt(i, 0);
					String name = (String) table.getValueAt(i, 1);
					String sortType = (String) table.getValueAt(i, 2);
					
					if (!list.contains(name)) {
						list.add(name);
						_dataView.getOrderColumns().put(name, sortType);
						_dataView.getActiveColumns().put(name, active);
					
						String sort = "";
					
						if (sortType.equals(AcideLanguageManager.getInstance().getLabels().getString("s2083")))
							sort= "asc";
						else if (sortType.equals(AcideLanguageManager.getInstance().getLabels().getString("s2084")))
							sort = "desc";
						else sort = "none";
					
						if (active && !sort.equals("none")) {
							if (i==table.getRowCount())
								command = command + name + ' ' + sort;
							else command = command + name + ' ' + sort + ",";
						}
					}
				}
				
				if (list.size() == table.getRowCount()){
					_dataView.setOrderNameColumns(list);
					_dataView.orderColumns(command);
					closeWindow();
					}
				else {
					JOptionPane.showMessageDialog(null,AcideLanguageManager.getInstance()
							.getLabels().getString("s2293"));
				}
			}
		});

		_cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {				
				closeWindow();
			}
		});
	}
	
	
	private void closeWindow(){

		_dataView.setEnabled(true);
		
		dispose();
		
		_dataView.setAlwaysOnTop(true);
		
		_dataView.setAlwaysOnTop(false);
	}

	@Override
	public void setDefaultCloseOperation(int option){
		if(option==2)
			closeWindow();
	}


	private class MyTableModel extends AbstractTableModel{
		
		private static final long serialVersionUID = 1L;
		
		Vector<String> names = _dataView.getColumnsNames();
		
		private String[] headers = {"Order by", AcideLanguageManager.getInstance().getLabels().getString("s2030")
				, AcideLanguageManager.getInstance().getLabels().getString("s2081")};
		private Object[][] data = new Object[names.size()][3];
		
		void build(){
			
			if(_dataView.getOrderNameColumns().isEmpty())
				_dataView.setOrderNameColumns(_dataView.getColumnsNames());
			
			Vector<String> namesOrdered = _dataView.getOrderNameColumns();
			
			for(int i=0;i<namesOrdered.size();i++){
				
				if(_dataView.getActiveColumns().containsKey(namesOrdered.get(i)))
					data[i][0] = _dataView.getActiveColumns().get(namesOrdered.get(i));
				else
					data[i][0] = Boolean.FALSE;
				
				data[i][1] = namesOrdered.get(i);
				
				if(_dataView.getOrderColumns().containsKey(namesOrdered.get(i)))
					data[i][2] = _dataView.getOrderColumns().get(namesOrdered.get(i));
				else
					data[i][2] = AcideLanguageManager.getInstance().getLabels().getString("s2083");
			}
		}
		
		public int getColumnCount() {
		    return headers.length;
		}

		public int getRowCount() {
		    return data.length;
		}

		public String getColumnName(int col) {
		    return headers[col];
		}

		public Object getValueAt(int row, int col) {
		    return data[row][col];
		}
		

        public Class getColumnClass(int c) {
            return getValueAt(0, c).getClass();
        }
        

        public boolean isCellEditable(int row, int col) {

           return true;
        }
        
        
        public void setValueAt(Object value, int row, int col) {
        	
            data[row][col] = value;
            fireTableCellUpdated(row, col);
 
        }
	}
}