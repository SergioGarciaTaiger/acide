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
package acide.gui.menuBar.configurationMenu.graphPanelMenu.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import javax.swing.JSlider;
import javax.swing.JSpinner;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import acide.gui.graphCanvas.AcideGraphCanvas;
import acide.gui.mainWindow.AcideMainWindow;
import acide.language.AcideLanguageManager;
/**
 * ACIDE - A Configurable IDE node size menu item listener.
 * 
 * @see ActionListener
 */
public class AcideNodeSizeMenuItemListener implements ActionListener {
	/**
	 * ACIDE - A Configurable IDE node size menu item listener spinner item listener.
	 */
	private static ChangeListener spinnerListener=null;
	/**
	 * ACIDE - A Configurable IDE node size menu item listener slider item listener.
	 */
	private static ChangeListener sliderListener = null;
	/*
	 * (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent actionEvent) {
		action(actionEvent);

	}
	
	public static void action(ActionEvent actionEvent){
		//gets the actual size of the node
		int acutalSize = 0;
		if(AcideGraphCanvas.getInstance() != null)
			acutalSize = AcideGraphCanvas.getInstance().getNodeSize();
		//creates a slider and a spinner to choose the new size
		SpinnerModel model = new SpinnerNumberModel(acutalSize, 0, 100, 1);
		final JSpinner spinner = new JSpinner(model);
		((JSpinner.DefaultEditor)spinner.getEditor()).getTextField().setColumns(4);
		final JSlider slider = new JSlider(JSlider.HORIZONTAL, 0, 100, acutalSize);
		
		sliderListener =new ChangeListener() {
			/*
			 * (non-Javadoc)
			 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
			 */
			@Override
			public void stateChanged(ChangeEvent ev) {
				spinner.removeChangeListener(spinnerListener);
				spinner.setValue(((JSlider)ev.getSource()).getValue());
				spinner.addChangeListener(spinnerListener);
			}
		};
		spinnerListener = new ChangeListener() {
			/*
			 * (non-Javadoc)
			 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
			 */
			@Override
			public void stateChanged(ChangeEvent ev) {
				slider.removeChangeListener(sliderListener);
				slider.setValue((Integer)((JSpinner)ev.getSource()).getValue());
				slider.addChangeListener(sliderListener);
				
			}
		};
		//adds the listeners to the components
		slider.addChangeListener(sliderListener);
		spinner.addChangeListener(spinnerListener);
		Object [] o={spinner,slider};
		
		int option = JOptionPane.showOptionDialog(AcideMainWindow.getInstance(), o, AcideLanguageManager
				.getInstance().getLabels().getString("s2244"), JOptionPane.OK_CANCEL_OPTION, 
				JOptionPane.QUESTION_MESSAGE, null, null, null);
		//updates the size and repaints the canvas
		if(option == JOptionPane.OK_OPTION && AcideGraphCanvas.getInstance() != null){
			AcideGraphCanvas.getInstance().setNodeSize((Integer)spinner.getValue());
			AcideGraphCanvas.getInstance().repaint();
		}
	}

}
