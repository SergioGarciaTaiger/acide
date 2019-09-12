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

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Locale;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import acide.gui.databasePanel.popup.listeners.AcideCopyTableMenuItemAction;
import acide.gui.mainWindow.AcideMainWindow;
import acide.language.AcideLanguageManager;
/**
 * ACIDE - A Configurable IDE database view.
 * 
 * @version 0.11
 * @see JFrame
 */
public class AcideDatabaseCopyTableOption extends JFrame {
	
	private static final long serialVersionUID = 1L;

	
	/**
	 * ACIDE - A Configurable IDE main window image icon.
	 */
	private static final ImageIcon ICON = new ImageIcon(
			"./resources/images/icon.png");
	/**
	 * ACIDE - Option to copy - only squema or squema and data
	 */
	private int _option=0;
	/**
	 * ACIDE - The instance of the JFrame 
	 */
	private static AcideDatabaseCopyTableOption _theInstance;
	
	
	public static AcideDatabaseCopyTableOption getInstance(){
		if(_theInstance==null)
			_theInstance= new AcideDatabaseCopyTableOption();
		return _theInstance;
	}

	private  AcideDatabaseCopyTableOption(){
		
		// Sets the window icon
		setIconImage(ICON.getImage());

		this.setTitle("");	
		
		JRadioButton squemaCopy = new JRadioButton(AcideLanguageManager.getInstance().getLabels().getString("s2111"), false);
		squemaCopy.setSelected(true);
		squemaCopy.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				_option=0;
				
			}
		});
		JRadioButton squemaAndDataCopy = new JRadioButton(AcideLanguageManager.getInstance()
				.getLabels().getString("s2112"), false);
		squemaAndDataCopy.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				_option=1;
				
			}
		});

		ButtonGroup bgroup = new ButtonGroup();                  
		bgroup.add(squemaCopy);
		bgroup.add(squemaAndDataCopy);

		JPanel radioPanel = new JPanel();
		radioPanel.setLayout(new GridLayout(2, 1));
		radioPanel.add(squemaCopy);
		radioPanel.add(squemaAndDataCopy);

		radioPanel.setBorder(BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder(),AcideLanguageManager.getInstance()
				.getLabels().getString("s23")));
		
		JPanel panelButtons = new JPanel();
		JButton okButton = new JButton(AcideLanguageManager.getInstance()
				.getLabels().getString("s154")); 
		okButton.addActionListener(new AcideCopyTableMenuItemAction());
		if (AcideLanguageManager.getInstance().getCurrentLocale().equals(new Locale("en", "EN"))){
			okButton.setMnemonic('O');
		} else {
			okButton.setMnemonic('A');	
		}
		JButton cancelButton = new JButton(AcideLanguageManager.getInstance()
				.getLabels().getString("s162"));
		cancelButton.setMnemonic('C');
		cancelButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				AcideDatabaseCopyTableOption.getInstance().setVisible(false);
				
			}
		});
		
		panelButtons.add(okButton,BorderLayout.CENTER);
		panelButtons.add(cancelButton,BorderLayout.CENTER);

		this.getContentPane().add(radioPanel, BorderLayout.NORTH);
		this.getContentPane().add(panelButtons, BorderLayout.WEST);			
		this.setLocationRelativeTo(AcideMainWindow.getInstance());
		this.setTitle(AcideLanguageManager.getInstance().getLabels().getString("s23"));
		this.pack();
	}
	
	public int getOption(){
		return _option;
	}
	
}


