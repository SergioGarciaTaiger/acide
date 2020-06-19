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
package acide.gui.databasePanel.utils;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.LinkedList;
import java.util.Locale;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.tree.TreePath;

import acide.gui.databasePanel.Nodes.NodeDefinition;
import acide.gui.databasePanel.dataView.AcideDatabaseDataView;
import acide.gui.mainWindow.AcideMainWindow;
import acide.language.AcideLanguageManager;
import acide.process.console.AcideDatabaseManager;
import acide.process.console.DesDatabaseManager;

/**
 * ACIDE - A Configurable IDE enter text window.
 * 
 * @version 0.16
 * @see JDialog
 */
public class AcideEnterTextWindow extends JDialog {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * ACIDE - A Configurable IDE main window image icon.
	 */
	private static final ImageIcon ICON = new ImageIcon(
			"./resources/images/icon.png");
	

	private JTextArea _text;
	
	private String _title;
	
	private JButton _applyButton;
	
	private JButton _cancelButton;
	
	private String _prompt;
	
	private JPanel _panel;
	
	private JPanel _buttonPanel;
	
	private JScrollPane _scrollPane;
	
	private boolean _editable;
	
	private String _database;
	
	public AcideEnterTextWindow(String prompt, String title, boolean editable){
		
		AcideMainWindow.getInstance().setAlwaysOnTop(false);
		
		AcideMainWindow.getInstance().setEnabled(false);
		
		setIconImage(ICON.getImage());
		
		_title = title;
			
		_prompt = prompt;
		
		_editable = editable;
		
		this.setTitle(_title);
		
		buildComponents();
		
		setLookAndFeel();
		
		addListeners();
		
		setVisible(true);
		
		setAlwaysOnTop(true);
		
		setDefaultCloseOperation(2);		
	}
	
public AcideEnterTextWindow(String prompt, String title, boolean editable, String database){
		AcideMainWindow.getInstance().setAlwaysOnTop(false);
		AcideMainWindow.getInstance().setEnabled(false);
		setIconImage(ICON.getImage());
		this.setTitle(title);

		_prompt = prompt;
		_editable = editable;
		_database = database;

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
		
		_text = new JTextArea();
		
		//Datalog window is not editable
		if (_title.contains("Datalog")) _text.setEditable(false);
			else _text.setEditable(true);
		
		_text.setText(_prompt);
		_text.setFont(new Font("Monospaced", Font.PLAIN, 12));

		if (!_title.contains("Datalog")) {
			
			_applyButton = new JButton(AcideLanguageManager.getInstance().getLabels().getString("s154"));
			if (AcideLanguageManager.getInstance().getCurrentLocale().equals(new Locale("en", "EN"))){
				_applyButton.setMnemonic('O');
			} else {
				_applyButton.setMnemonic('A');	
			}
			_applyButton.setFocusable(true);
			
			_cancelButton = new JButton(AcideLanguageManager.getInstance().getLabels().getString("s369"));
			_cancelButton.setMnemonic('C');
			
			_buttonPanel = new JPanel();
			
			_buttonPanel.add(_applyButton);
			_buttonPanel.add(_cancelButton);
		}

		_panel = new JPanel();
		
		_panel.setLayout(new BorderLayout());
		_scrollPane = new JScrollPane(_text);
		
	}
	
	private void setLookAndFeel(){
		_panel.add(_scrollPane, BorderLayout.CENTER);

		if (!_title.contains("Datalog")) _panel.add(_buttonPanel, BorderLayout.SOUTH);

		getContentPane().add(_panel,BorderLayout.CENTER);
		setLocationRelativeTo(null);
		setResizable(true);

		pack();
		setModal(true);
	}
	
	private void addListeners() {

		if (!_title.contains("Datalog")){
			_applyButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					save();
				}
			});

			_cancelButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					closeWindow();
				}
			});

			_text.addKeyListener(new KeyListener() {
				public void keyTyped(KeyEvent e) {
					dispatchEvent(e);
				}
				public void keyReleased(KeyEvent e) {
					dispatchEvent(e);
				}
				public void keyPressed(KeyEvent arg0) {
					if (arg0.getKeyCode() == KeyEvent.VK_ENTER){
						if(arg0.isControlDown()){
							arg0.consume();
							save();
						}
					} else if (arg0.getKeyCode() == KeyEvent.VK_ESCAPE){
						closeWindow();
					} else
						dispatchEvent(arg0);
				}

			});
		
		}
		
	}
	
	private void closeWindow(){
		AcideMainWindow.getInstance().setEnabled(true);
		dispose();
		AcideMainWindow.getInstance().setAlwaysOnTop(true);
		AcideMainWindow.getInstance().setAlwaysOnTop(false);
	}
	
	
	private void save() {
		
		if (!_editable){//To create a new view or new table
			String text = _text.getText();
			text = text.replace("\n", " ");
			String table=""; 
			String database;
			boolean exists = false;
			if (text.startsWith("CREATE TABLE") || text.startsWith(":-type(") || text.startsWith("CREATE VIEW")){

				if (text.startsWith("CREATE TABLE")){
					int index = text.indexOf("(", 13);
					if (index > 0) 
						table = text.substring(13,index);
					else
						table = text.substring(13, text.length());
				}
				else if (text.startsWith(":-type(")){
					int index = text.indexOf("(", 7);
					if (index > 0) 
						table = text.substring(7,index);
					else
						table = text.substring(7, text.length());
				}
				else{
					int index = text.indexOf("(",12);
					if (index > 0) 
						table =  text.substring(12,index);
					else
						table = text.substring(12, text.length());
				}
				table = table.trim();
				database = AcideMainWindow.getInstance().getDataBasePanel().getTree().getSelectionPath().getParentPath().getLastPathComponent().toString();
				exists = AcideDatabaseManager.getInstance().existsRelation(database, table);
			}
			LinkedList<String> result= new LinkedList<String>();
			if (!exists){
				result = AcideDatabaseManager.getInstance().executeCommand(text);
				boolean error = false;
				for (int i = 0; i<result.size(); i++){
					error = error || result.get(i).contains("Error");
				}
				if (error){
					JOptionPane.showMessageDialog(null, AcideLanguageManager.getInstance().getLabels().getString("s2128"),
							AcideLanguageManager.getInstance().getLabels().getString("s157"), JOptionPane.OK_OPTION);
				}else{
					AcideMainWindow.getInstance().getDataBasePanel().updateDataBaseTree();//.insertTable(parent,table);


					if (text.startsWith("SELECT")){

						LinkedList<String> ret = AcideDatabaseManager.getInstance().executeCommand(text);
						LinkedList<String> res = new LinkedList<String>();							
						if(!ret.isEmpty()){										
							String line = ret.get(0);
							if (AcideDatabaseManager.getInstance() instanceof DesDatabaseManager){
								if(line.startsWith("answer")){
									line=line.replace("answer(", "");
									int index = line.indexOf(":");
									while(index>-1){
										res.add(line.substring(0,index));
										line = line.substring(index+1);
										index = line.indexOf(",");
										if(index==-1) index = line.lastIndexOf(")");
										res.add(line.substring(0,index));
										line = line.substring(index+1);
										index = line.indexOf(":");
									}
								}
								res.add("$");
								for (int i = 1; i<ret.size(); i++){
									line = ret.get(i);
									if (!line.startsWith("Info: ") && !line.equals("{") && ! line.equals("}") && !line.equals("answer ->")){
										line=line.replace("  answer(","");
										line=line.replace(") ->", "");
										line=line.replace("),","");
										line=line.replace(")","");
										int index = line.indexOf(",");
										while(index>-1){
											res.add(line.substring(0,index));
											line = line.substring(index+1);
											index = line.indexOf(",");
	
										}
										res.add(line);
										res.add("$");
									}
								}
								if(res.get(res.size()-1).equals("$")) res.remove(res.size()-1);
							}else{
								res = ret;
							}
							
							String tableName = res.get(0);

							if ( tableName.contains("."))
								tableName=tableName.substring(0,tableName.indexOf("."));
							else {
								int index = text.indexOf("from ");
								String s = text.substring(index + 5).trim();
								int index2 = s.indexOf(" ") ;
								if (index2<0) index2 = 0;
								index2 += index + 5;
								if (index2 > 0){
									if (index2 == index + 5)
										tableName = text.substring(index + 5);
									else
										tableName = text.substring(index + 5,index2);
								}
								else 
									tableName = text.substring(index+5);
							}
							AcideDatabaseDataView dataView = null;
							try{
								dataView = AcideMainWindow.getInstance().getDataBasePanel().getDataView(_database, tableName);				;
							}catch (Exception e){
								AcideMainWindow.getInstance().getDataBasePanel().addDatabaseToDataView(_database);
								dataView = AcideMainWindow.getInstance().getDataBasePanel().getDataView(_database, tableName);				;
							}
							dataView.build(res);
						}
					}
					closeWindow();
					AcideMainWindow.getInstance().getConsolePanel().requestFocus();
				}
			}else{
				JOptionPane.showMessageDialog(null, AcideLanguageManager.getInstance().getLabels().getString("s2127"),
						AcideLanguageManager.getInstance().getLabels().getString("s157"), JOptionPane.OK_OPTION);
			}
		}else{
			int response = JOptionPane.showConfirmDialog(null,AcideLanguageManager.getInstance()
					.getLabels().getString("s2182"), AcideLanguageManager.getInstance()
					.getLabels().getString("s40"), JOptionPane.OK_CANCEL_OPTION);
			
			if (response == 0){
				String database_name = "";
				String view_name = "";
				try {
					TreePath path = (TreePath) AcideMainWindow.getInstance().getDataBasePanel().getTree().getSelectionPath();

					if (path.getLastPathComponent() instanceof NodeDefinition)
						view_name = path.getParentPath().getParentPath().getLastPathComponent().toString();
					else
						view_name = path.getLastPathComponent().toString();

					if (view_name.contains("("))
						view_name = view_name.substring(0, view_name.indexOf("("));

					database_name = path.getParentPath().getParentPath().getParentPath().getParentPath().getLastPathComponent().toString();
				}catch (Exception e){
					// if path is null we are in debug, get view from debug panel and database is des
					database_name = "$des";
					view_name = AcideMainWindow.getInstance().getDebugPanel()
							.getDebugSQLPanel().getCanvas().getSelectedNode().getLabel().split("/")[0];
				}
				String text =  _text.getText();
				text = text.replace('\n',' ');
				text = text.substring(0, text.length()-1);
				
				LinkedList<String> result = null;
				
				if (_title.contains("SQL")){
					AcideDatabaseManager.getInstance().dropView(database_name, view_name);
					result = AcideDatabaseManager.getInstance()
							.executeCommand("/tapi CREATE OR REPLACE VIEW "+view_name+" AS " + text);
					
				}else if (_title.contains("RA")){
					result = AcideDatabaseManager.getInstance().executeCommand("/tapi "+ view_name + " := " + text);
				}
				
				boolean error = checkResult(result);
				
				if(error){
					text = _prompt.replace('\n',' ');
					AcideDatabaseManager.getInstance()
							.executeCommand("/tapi CREATE OR REPLACE VIEW "+view_name+" AS " + text);
					JOptionPane.showMessageDialog(null,result.toString(),"Error",JOptionPane.OK_OPTION);
				}
				
				DataBasePanelUtils.updateDataBasePanelView();
				
				closeWindow();
			}
		}
	}

	
	/**
	 * Checks if the operation was successful
	 * @param result
	 * @return
	 */
	private  boolean checkResult(LinkedList<String> result) {
		int i=0;
		boolean error = false;
		while(!error && i<result.size()){
			error = result.get(i).contains("$error");
			i++;
		}
		return error;
	}

}
