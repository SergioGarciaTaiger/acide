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
package acide.gui.menuBar.editMenu.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.text.BadLocationException;
import javax.swing.text.Document;

import acide.gui.fileEditor.fileEditorPanel.fileEditorTextEditionArea.utils.AcideTextComponent;
import acide.gui.mainWindow.AcideMainWindow;

/**
 * ACIDE - A Configurable IDE invert case menu item listener.
 * 
 * @version 0.14
 * @see ActionListener
 */
public class AcideInvertCaseMenuItemListener implements ActionListener {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		action(e);
		
	}

	public static void action(ActionEvent event) {
		AcideTextComponent target = AcideMainWindow
				.getInstance().getFileEditorManager()
				.getSelectedFileEditorPanel().getActiveTextEditionArea();
		try {
		
			if (target.getSelectedText()!=null){
				String text = target.getSelectedText();
				
					String newText = invertWord(text);
					int start = target.getSelectionStart();
					int end = target.getSelectionEnd();
					
					target.getDocument().remove(start, text.length());
					target.getDocument().insertString(start,newText, null);
					target.select(start, end);
				
			}
			else { 
				int start = target.getSelectionStart();
				int end = target.getSelectionEnd();
	
				int left = javax.swing.text.Utilities.getWordStart(target, start);
		        int right = javax.swing.text.Utilities.getWordEnd(target, end);
	
		        Document doc = target.getDocument();
		        String word = doc.getText(left, right-left);
		        
		        int position = target.getCaretPosition(); //store the current caret position 
		        
				        target.setSelectionStart(left); // restore previous position/selection
				        target.setSelectionEnd(right);
				        target.replaceSelection(invertWord(word));
				        target.setCaretPosition(position); //set the caret to its original position
				 	}

			} catch (BadLocationException e) {
					e.printStackTrace();
			}
		
	}
	
	/**
	 * Transforms upper case letters into lower case and viceversa.
	 * @param word
	 * @return newWord
	 * 				letters inverted
	 */
	private static String invertWord(String word){
		String newWord="";
		char[] wordAux = word.toCharArray();
		for(int i=0;i<wordAux.length;i++){
			if(Character.isLowerCase(wordAux[i])) wordAux[i] = Character.toUpperCase(wordAux[i]);
			else wordAux[i] = Character.toLowerCase(wordAux[i]);
			newWord=newWord+wordAux[i];
		}
		return newWord;
	}
	
}
