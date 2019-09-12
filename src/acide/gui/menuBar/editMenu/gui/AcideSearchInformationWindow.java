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
package acide.gui.menuBar.editMenu.gui;


import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JProgressBar;

import acide.language.AcideLanguageManager;

/**
 * ACIDE - A Configurable IDE search information window.
 * 
 * @version 0.11
 * @see JFrame
 */
public class AcideSearchInformationWindow extends JFrame {
	
	/**
	 * ACIDE - A Configurable IDE search information window serial version UID.
	 */
	private static final long serialVersionUID = 1L;

	private static AcideSearchInformationWindow _instance;
	
	private JProgressBar barDo;
	
	private int cont;
	
	private JLabel _label;
	
	public AcideSearchInformationWindow(){
		_label = new JLabel(AcideLanguageManager.getInstance()
				.getLabels().getString("s2144"));
		barDo = new JProgressBar(0, 100);
		cont = 0;
		setLayout(new GridBagLayout());
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.insets = new Insets(5, 5, 5, 5);
		constraints.anchor = GridBagConstraints.WEST;
		constraints.fill = GridBagConstraints.NONE;
		add(_label, constraints);
		constraints.gridy = 1;
		add(barDo, constraints);
		
		setTitle(AcideLanguageManager.getInstance()
				.getLabels().getString("s2144"));
		setSize(300,100);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		
		
	}
	
	public void resetBar(){
		cont = 0;
		//setLocationRelativeTo(null);
		setVisible(false);
		setAlwaysOnTop(false);
		new Thread(new thread1()).start();
	}
	
	public int getCont() {
		return cont;
	}

	public void setCont(int cont) {
		this.cont = cont;
	}

	public JLabel getLabel() {
		return _label;
	}

	public String getStringLabel(){
		return _label.getText();
	}


	public void setLabel(String label) {
		_label.setText(label);
	}

	public void setProgress(int i){
		cont = i;
		setLocationRelativeTo(null);
		setVisible(true);
		setAlwaysOnTop(true);
		new Thread(new thread1()).start();
	}

	public JProgressBar getBarDo() {
		return barDo;
	}

	public void setBarDo(JProgressBar barDo) {
		this.barDo = barDo;
	}
	
	public void setValueBar(int i){
		barDo.setValue(i);
	}
	
	
	  public static class thread1 implements Runnable{
			public void run(){
				getInstance().setValueBar(getInstance().getCont()); 
				getInstance().getBarDo().repaint(); 
				
				if (getInstance().getCont() == 100){
					try{
						Thread.sleep(1000);
						getInstance().setVisible(false);
					} catch (InterruptedException err){
						
					} 
				}
			}
	 }


	/**
	 * Returns the ACIDE - A Configurable IDE search information window unique class
	 * instance.
	 * 
	 * @return the ACIDE - A Configurable IDE search information window unique class
	 *         instance.
	 */
	public static AcideSearchInformationWindow getInstance() {
		if (_instance == null)
			_instance = new AcideSearchInformationWindow();
		return _instance;
	}
}