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
package acide.gui.databasePanel.utils;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Locale;

import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextArea;

import acide.gui.databasePanel.AcideDataBasePanel;
import acide.gui.mainWindow.AcideMainWindow;
import acide.language.AcideLanguageManager;
import acide.process.console.AcideDatabaseManager;

/**
 * ACIDE - A Configurable IDE open data base window.
 * 
 * @version 0.11
 * @see JDialog
 */
public class AcideOpenDatabaseWindow extends JDialog {

	private static final long serialVersionUID = 1L;
	
	/**
	 * ACIDE - A Configurable IDE main window image icon.
	 */
	private static final ImageIcon ICON = new ImageIcon(
			"./resources/images/icon.png");
	
	/**
	 * ACIDE -  Label for the database name
	 */
	private JLabel _labelName;
	
	/**
	 * ACIDE - label for the user of the database  
	 */
	private JLabel _labelUser;
	
	/**
	 * ACIDE - label for the password of the user  
	 */
	private JLabel _labelPassword;
	
	/**
	 * ACIDE - label for other params  
	 */
	private JLabel _labelOthers;
	
	/**
	 * ACIDE - TextArea to enter the database name  
	 */
	private JTextArea _textName;
	
	/**
	 * ACIDE - TextArea to enter the user of the database  
	 */
	private JTextArea _textUser;
	
	/**
	 * ACIDE - PasswordFiel to enter the password of the user  
	 */
	private JPasswordField _textPassword;
	
	/**
	 * ACIDE - TextArea to enter others params  
	 */
	private JTextArea _textOthers;
	
	/**
	 * ACIDE - Ok button  
	 */
	private JButton _buttonOK;
	
	/**
	 * ACIDE - Cancel button  
	 */
	private JButton _buttonCancel;
	
	/**
	 * ACIDE - Panel to add the components  
	 */
	private JPanel _panel;
	
	/**
	 *  Creates the window to open a database
	 */
	public AcideOpenDatabaseWindow(){
		
		AcideMainWindow.getInstance().setAlwaysOnTop(false);
		
		AcideMainWindow.getInstance().setEnabled(false);
		
		setIconImage(ICON.getImage());
		
		this.setTitle(AcideLanguageManager.getInstance().getLabels().getString("s2162"));
		
		buildComponents();
		
		setLookAndFeel();
		
		addListeners();
		
		setDefaultCloseOperation(2);
		
		this.setLocationRelativeTo(null);
		
		setVisible(true);
		
	}
	/**
	 *  Sets the default close operation
	 */
	public void setDefaultCloseOperation(int option){
		if(option==2)
			closeWindow();
	}

	/**
	 * Adds the listener to all the components
	 * 
	 */
	private void addListeners() {
		
		_buttonCancel.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				closeWindow();
			}
		});
		
		_buttonOK.addActionListener(new ActionListener() {
			
			@SuppressWarnings("deprecation")
			public void actionPerformed(ActionEvent arg0) {
				
				String res = AcideDatabaseManager.getInstance().createDatabase(_textName.getText(),
							_textUser.getText(), _textPassword.getText() );
				if (res.equals("$success")){
					AcideDataBasePanel panel = AcideMainWindow.getInstance().getDataBasePanel();
					panel.updateDataBaseTree();
					panel.addDatabaseToDataView(_textName.getText());
					closeWindow();}
				else
					JOptionPane.showMessageDialog(null, AcideLanguageManager.getInstance().getLabels().getString("s2167"),
							AcideLanguageManager.getInstance().getLabels().getString("s157"), JOptionPane.OK_OPTION);
			}
		});
		
		_textName.addKeyListener(new KeyAdapter() {

			public void keyPressed(KeyEvent arg0) {
				
				if (arg0.getKeyCode() == KeyEvent.VK_TAB){
					arg0.consume();
					_textUser.requestFocus();
				}
				
				if (arg0.getKeyCode() == KeyEvent.VK_ENTER){
					action();
				}
				
				if (arg0.getKeyCode() == KeyEvent.VK_ESCAPE){
					closeWindow();
				}
				
			}
		});
		
		_textUser.addKeyListener(new KeyAdapter() {

			public void keyPressed(KeyEvent arg0) {
				
				if (arg0.getKeyCode() == KeyEvent.VK_TAB){
					arg0.consume();
					_textPassword.requestFocus();
				}
				
				if (arg0.getKeyCode() == KeyEvent.VK_ENTER){
					action();
				}
				
				if (arg0.getKeyCode() == KeyEvent.VK_ESCAPE){
					closeWindow();
				}
			}
		});
		
		_textPassword.addKeyListener(new KeyAdapter() {

			public void keyPressed(KeyEvent arg0) {
				
				if (arg0.getKeyCode() == KeyEvent.VK_TAB){
					arg0.consume();
					_textOthers.requestFocus();
				}
				
				if (arg0.getKeyCode() == KeyEvent.VK_ENTER){
					action();
				}
				
				if (arg0.getKeyCode() == KeyEvent.VK_ESCAPE){
					closeWindow();
				}
			}
		});
		
		_textOthers.addKeyListener(new KeyAdapter() {

			public void keyPressed(KeyEvent arg0) {
				
				if (arg0.getKeyCode() == KeyEvent.VK_TAB){
					arg0.consume();
					_buttonOK.requestFocus();
				}
				
				if (arg0.getKeyCode() == KeyEvent.VK_ENTER){
					action();
				}
				
				if (arg0.getKeyCode() == KeyEvent.VK_ESCAPE){
					closeWindow();
				}
				
			}
		});
		
		_buttonOK.addKeyListener(new KeyAdapter() {

			public void keyPressed(KeyEvent arg0) {
				
				if (arg0.getKeyCode() == KeyEvent.VK_TAB){
					arg0.consume();
					_buttonCancel.requestFocus();
				}
				
				if (arg0.getKeyCode() == KeyEvent.VK_ENTER){
					action();
				}
				
				if (arg0.getKeyCode() == KeyEvent.VK_ESCAPE){
					closeWindow();
				}
				
			}
		});
	}

	/**
	 * Sets the look and feel of the window
	 */
	private void setLookAndFeel() {
		
		GroupLayout layout = new GroupLayout(_panel);
		
		_panel.setLayout(layout);
		
		layout.setHorizontalGroup(
				layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(layout.createSequentialGroup()
							.addGap(30)
							.addComponent(_labelName,100,100,100)
							.addComponent(_textName,100,100,100)
							.addGap(20))
				.addGroup(layout.createSequentialGroup()
							.addGap(30)
							.addComponent(_labelUser,100,100,100)
							.addComponent(_textUser,100,100,100)
							.addGap(20))
				.addGroup(layout.createSequentialGroup()
							.addGap(30)
							.addComponent(_labelPassword,100,100,100)
							.addComponent(_textPassword,100,100,100)
							.addGap(20))
				.addGroup(layout.createSequentialGroup()
							.addGap(30)
							.addComponent(_labelOthers,100,100,100)
							.addComponent(_textOthers,100,100,100)
							.addGap(20))
				.addGroup(layout.createSequentialGroup()
							.addGap(40)
							.addComponent(_buttonOK,80,80,80)
							.addComponent(_buttonCancel,80,80,80)
							.addGap(20)));
		
		layout.setVerticalGroup(
				layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(layout.createSequentialGroup()
							//.addGap(50)
							.addComponent(_labelName,30,30,30)
							.addComponent(_labelUser,30,30,30)
							.addComponent(_labelPassword,30,30,30)
							.addComponent(_labelOthers,30,30,30)
							.addComponent(_buttonOK,25,25,25))
				.addGap(100)
				.addGroup(layout.createSequentialGroup()
							.addGap(9)
							.addComponent(_textName,20,20,20)
							.addGap(8)
							.addComponent(_textUser,20,20,20)
							.addGap(8)
							.addComponent(_textPassword,20,20,20)
							.addGap(8)
							.addComponent(_textOthers,20,20,20)
							.addGap(7)
							.addComponent(_buttonCancel,25,25,25)));
		
		_panel.setVisible(true);
		
		_panel.validate();
		_panel.setSize(600,800);
		_panel.validate();
		_panel.repaint();
		
		this.getContentPane().add(_panel);
		
		setSize(600, 800);
		this.validate();
		this.repaint();
		this.setResizable(false);
		
		// Packs the window components
		pack();
		
		//this.setModalExclusionType(ModalExclusionType.APPLICATION_EXCLUDE);
		setModal(true);

	}
	/**
	 * Builds all the components of the window
	 */
	private void buildComponents() {
		
		_labelName = new JLabel(AcideLanguageManager.getInstance().getLabels().getString("s2163"));
		
		_labelUser = new JLabel(AcideLanguageManager.getInstance().getLabels().getString("s2164"));
		
		_labelPassword = new JLabel(AcideLanguageManager.getInstance().getLabels().getString("s2165"));
		
		_labelOthers = new JLabel(AcideLanguageManager.getInstance().getLabels().getString("s2166"));
		
		_textName = new JTextArea();

		_textUser = new JTextArea();
		
		_textPassword = new JPasswordField();
		
		_textOthers = new JTextArea();
		
		_buttonOK = new JButton(AcideLanguageManager.getInstance().getLabels().getString("s177"));
		if (AcideLanguageManager.getInstance().getCurrentLocale()
				.equals(new Locale("es", "ES"))){
			_buttonOK.setMnemonic('A');
		} else 
			_buttonOK.setMnemonic('O');
		
		_buttonCancel = new JButton(AcideLanguageManager.getInstance().getLabels().getString("s162"));
		_buttonCancel.setMnemonic('C');
		
		_panel = new JPanel();
		
	}
	
	/**
	 * Closes the window.
	 */
	private void closeWindow(){
		
		AcideMainWindow.getInstance().setEnabled(true);
		
		dispose();
		
		AcideMainWindow.getInstance().setAlwaysOnTop(true);
		
		AcideMainWindow.getInstance().setAlwaysOnTop(false);
	}
	
	/**
	 * Open the database
	 */
	private void action(){
		String res = AcideDatabaseManager.getInstance().createDatabase(_textName.getText(),
				_textUser.getText(), String.valueOf(_textPassword.getPassword()));
		if (res.equals("$success")){
			AcideDataBasePanel panel = AcideMainWindow.getInstance().getDataBasePanel();
			panel.updateDataBaseTree();
			panel.addDatabaseToDataView(_textName.getText());
			closeWindow();}
		else
			JOptionPane.showMessageDialog(null, res,
					AcideLanguageManager.getInstance().getLabels().getString("s157"), JOptionPane.OK_OPTION);
	}
}
