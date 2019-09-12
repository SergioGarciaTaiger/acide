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

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Locale;
import java.util.Vector;

import javax.swing.DefaultCellEditor;
import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

import acide.gui.databasePanel.dataView.AcideDatabaseDataView;
import acide.language.AcideLanguageManager;

/**
 * ACIDE - A Configurable IDE hide show columns window.
 * 
 * @version 0.11
 * @see JDialog
 */
public class AcideHideShowColumnsWindow extends JDialog {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * ACIDE - A Configurable IDE main window image icon.
	 */
	private static final ImageIcon ICON = new ImageIcon(
			"./resources/images/icon.png");
	
	/**
	 * ACIDE - Table to show the data 
	 */
	private JTable _jTable;
	
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
	
	/**
	 * ACIDE - Scroll of the main panel 
	 */
	private JScrollPane _scrollPane;
	
	/**
	 * ACIDE - Dataview to hide/show columns 
	 */
	private AcideDatabaseDataView _dataview;
	
	/**
	 * ACIDE - Visibiliby of each column
	 */
	private Vector<Boolean> _columnsVisibility;

	
	public AcideHideShowColumnsWindow(String title,AcideDatabaseDataView _jtable){
		
		_dataview = _jtable;
		
		_dataview.setAlwaysOnTop(false);
		
		_dataview.setEnabled(false);
		
		setIconImage(ICON.getImage());
		
		setTitle(title);
		
		buildComponents();
		
		setLookAndFeel();
		
		addListeners();
		
		setVisible(true);
		
		setAlwaysOnTop(true);
		
		setDefaultCloseOperation(2);	
	}
	
	@Override
	public void setDefaultCloseOperation(int option){
		if(option==2)
			closeWindow();
	}
	
	private void buildComponents(){
		Vector<String> names = _dataview.getColumnsNames();
		Vector<Boolean> isVisible = _dataview.getColumnsVisibility();
		_columnsVisibility = new Vector<Boolean>();
		String[] columnNames = {"Columna", "Visibilidad"};		
	  	Object[][] data = new Object[_dataview.getTotalColumns()][2];
	  	for(int h =0; h<_dataview.getTotalColumns();h++){
	  		data[h][0] = names.get(h);
	  		data[h][1] = isVisible.get(h);
	  		_columnsVisibility.add(isVisible.get(h));
	  	}
	  	_jTable = new  JTable(data, columnNames);
	  	
	  	_jTable.setAutoscrolls(true);	  	

	  	TableColumn check = _jTable.getColumnModel().getColumn(1);
	  	
	  	JCheckBox checkbox = new JCheckBox();
	  	
	  	check.setCellEditor(new CELL_EDITOR(checkbox));
	  	
	    check.setCellRenderer(new CELL_RENDERER());
	  		  	
		_applyButton = new JButton(AcideLanguageManager.getInstance().getLabels().getString("s154"));
		
		if (AcideLanguageManager.getInstance().getCurrentLocale().equals(new Locale("en", "EN"))){
			_applyButton.setMnemonic('O');
		} else {
			_applyButton.setMnemonic('A');	
		}
		
		_cancelButton = new JButton(AcideLanguageManager.getInstance().getLabels().getString("s369"));
		
		_cancelButton.setMnemonic('C');
		
		_panel = new JPanel();
		
		_scrollPane = new JScrollPane(_jTable);
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
				_columnsVisibility.clear();
				for(int h =0; h<_jTable.getRowCount();h++){	
					_columnsVisibility.add((Boolean) _jTable.getValueAt(h, 1));
				}
				_dataview.ShowColumns(_columnsVisibility);
				closeWindow();
			}
		});
		
		_cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {				
				closeWindow();
			}
		});
	}
	
	private void closeWindow(){
		
		_dataview.setEnabled(true);	
		
		_dataview.setAlwaysOnTop(true);
		
		dispose();
	}

	class CELL_EDITOR extends DefaultCellEditor{
		private static final long serialVersionUID = 1L;

		public CELL_EDITOR(JCheckBox checkBox) {
			super(checkBox);
			checkBox.setHorizontalAlignment(JLabel.CENTER);
		}
	}
	
	class CELL_RENDERER extends JCheckBox implements TableCellRenderer{
		private static final long serialVersionUID = 1L;

		public CELL_RENDERER(){
			setHorizontalAlignment(JLabel.CENTER);
		}
		@Override
		public Component getTableCellRendererComponent(JTable arg0,
				Object value, boolean arg2, boolean arg3, int arg4, int arg5) {
			setSelected((value != null && ((Boolean) value).booleanValue()));
			setBackground(arg0.getBackground());
			setHorizontalAlignment(JLabel.CENTER);
			return this;
		}
	}
}
