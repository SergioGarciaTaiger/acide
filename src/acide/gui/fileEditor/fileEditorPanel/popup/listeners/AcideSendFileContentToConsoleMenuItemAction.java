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
package acide.gui.fileEditor.fileEditorPanel.popup.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.Writer;

import javax.swing.JOptionPane;

import acide.configuration.workbench.AcideWorkbenchConfiguration;
import acide.gui.mainWindow.AcideMainWindow;
import acide.language.AcideLanguageManager;

/**
 * ACIDE - A Configurable IDE file editor panel popup menu send file content to
 * console menu item action listener.
 * 
 * @version 0.11
 * @see ActionListener
 */
public class AcideSendFileContentToConsoleMenuItemAction implements
		ActionListener {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@SuppressWarnings("static-access")
	@Override
	public void actionPerformed(ActionEvent actionEvent){
		
		boolean confirmacion = AcideWorkbenchConfiguration
									.getInstance().getFileEditorConfiguration()
									.getSendToConsoleConfirmation();
		
		int returnValueAreYouSure = JOptionPane.OK_OPTION;
		if (confirmacion){
		// Ask if are you sure about the operation
		returnValueAreYouSure = JOptionPane.showConfirmDialog(null,
					AcideLanguageManager.getInstance().getLabels()
							.getString("s2006"), AcideLanguageManager
							.getInstance().getLabels().getString("s953"),JOptionPane.YES_NO_OPTION);
		}
		
		// If it is OK
		if (returnValueAreYouSure == JOptionPane.OK_OPTION) {
		//AcideMainWindow.getInstance().getConsolePanel().executeCommand(AcideMainWindow.getInstance().getFileEditorManager().getSelectedFileEditorPanel().getTextEditionAreaContent(), " ");
		String text = AcideMainWindow.getInstance().getFileEditorManager().getSelectedFileEditorPanel().getTextEditionAreaContent();
				
		String[] commands = text.split("\n");
				
		Writer wr = AcideMainWindow.getInstance().getConsolePanel().getProcessThread().getWriter();
		//Reader rd = AcideMainWindow.getInstance().getConsolePanel().getProcessThread().
				
		for (int i = 0; i<commands.length; i++){
			if (!commands[i].equalsIgnoreCase("")){
				try {
					if (i<commands.length-1){
						commands[i] +="\n";
						AcideMainWindow.getInstance().getConsolePanel().addText(commands[i]);
						wr.write(commands[i]);
						wr.flush();
					}else{
						if (text.endsWith("\n")){
							AcideMainWindow.getInstance().getConsolePanel().addText(commands[i]+"\n");
							wr.write(commands[i]+"\n");
							wr.flush();
						}else{
							String text2 = "";
							text2 = AcideMainWindow.getInstance().getConsolePanel().getTextPane().getText();
							text2 = text2.concat(commands[i]);
							AcideMainWindow.getInstance().getConsolePanel().getTextPane().setText(text2);
						}
							
					}
					try{
						Thread.currentThread().sleep(50);//sleep for 20 ms
					}
					catch(Exception ie){
						ie.printStackTrace();
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}	
}
	
//	private boolean isComment(String command){
//		if (command.length() > 1){
//			if (command.charAt(0)=='%')
//				return true;
//			if ((command.charAt(0)=='-' ) && (command.charAt(1)=='-' ))
//				return true;
//		}
//		if (command.charAt(0)=='%')
//			return true;
//		return false;
//	}
}
