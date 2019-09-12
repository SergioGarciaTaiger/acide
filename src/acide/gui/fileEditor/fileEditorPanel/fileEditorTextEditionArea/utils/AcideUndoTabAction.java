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
package acide.gui.fileEditor.fileEditorPanel.fileEditorTextEditionArea.utils;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.UIManager;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.Element;

import acide.configuration.workbench.AcideWorkbenchConfiguration;
import acide.gui.mainWindow.AcideMainWindow;

/**
 * ACIDE - A Configurable IDE tab action.
 * 
 * Performs an action on the text area every time the key SHIFT +TAB is pressed
 * 
 * @version 0.14
 * @see AbstractAction
 */
public class AcideUndoTabAction extends AbstractAction {
	
	private static final long serialVersionUID = 1L;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * java.awt.event.TextAction#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent actionEvent) {
		// Gets the target
		AcideTextComponent target = AcideMainWindow
				.getInstance().getFileEditorManager()
				.getSelectedFileEditorPanel().getActiveTextEditionArea();
		
		if (target == null)
			return;
		if ((!target.isEditable()) || (!target.isEnabled())) {
			UIManager.getLookAndFeel().provideErrorFeedback(target);
			return;
		}
		
		String textTAB = "\t";
		
		try {
			
			if (AcideWorkbenchConfiguration.getInstance()
				.getFileEditorConfiguration().isSpaceSelected())
					
					textTAB = textSpaced(AcideWorkbenchConfiguration.getInstance()
						.getFileEditorConfiguration().getTabSize());
				
			//Text has been selected
				if (target.getSelectedText()!=null){
					
					int num = getNumberLinesSelected(target);
					for (int i=0; i<num;i++)
				{ 
						int start = getStartPosition(target, getFirstLine(target)+i);
						int startNoSpaces = getStartPositionNoSpaces(target, getFirstLine(target)+i);
						int end = getEndPosition(target);
						if (!getCurrentLine(target, getFirstLine(target)+i).equals("\n")
							&&(startNoSpaces!=end)&&(start!=startNoSpaces))
							target.getDocument().remove(start-textTAB.length(), textTAB.length());	
					}
					
				} else {
					int start = getStartPosition(target);
					int startNoSpaces = getStartPositionNoSpaces(target);
					if (!getCurrentLine(target).equals("\n") && (start!=startNoSpaces))
					target.getDocument().remove(start-textTAB.length(), textTAB.length());
				}
			
			} catch (BadLocationException e) {
				e.printStackTrace();
			}
		
	}
	
	/**
	 * Builds a string composed of a number of blank spaces
	 */
	private String textSpaced(int num)
	{
		String text = "";
		for(int j=0;j<num;j++)
			text += ' ';
		return text;
	}
	
	/**
	 * Gets the number of selected lines in the text area
	 */
	private int getNumberLinesSelected(AcideTextComponent target) {
		Document doc = target.getDocument();
		Element rootElement = doc.getDefaultRootElement();
		int selectionStart = target.getSelectionStart();
		int selectionEnd = target.getSelectionEnd();
		int firstLine = rootElement.getElementIndex(selectionStart);
		int lastLine = rootElement.getElementIndex(selectionEnd);
		int length = lastLine - firstLine;
		return length+1 ;
	}

	/**
	 * Gets the position where the selection starts in the text area
	 */
	private int getStartPosition(AcideTextComponent target){
		int selectionStart = target.getSelectionStart();
		return selectionStart;
	}
	
	/**
	 * Gets the position where the selection ends in the text area
	 */
	private int getEndPosition(AcideTextComponent target){
		int selectionEnd = target.getSelectionEnd();

		return selectionEnd;
	}
	
	/**
	 * Gets the position where a line starts in the text area. Blank spaces or tabs are skipped
	 */
	private int getStartPosition(AcideTextComponent target, int numLine){
			
			Document doc = target.getDocument();
			Element rootElement = doc.getDefaultRootElement();
	
			int start = rootElement.getElement(numLine).getStartOffset();
			
			String line = getCurrentLine(target,numLine);
			int offset = 0;
			// Get the number of white spaces characters at the start of the
			// line
			for (offset = 0; offset < line.length(); offset++) {
				char c = line.charAt(offset);
				if (c != ' ' && c != '\t')
					break;
			}
			start=start+offset;
			return start;
		}
	
	
	/**
	 * Gets the position where a line of the selection starts in the text area
	 */
	private int getStartPositionNoSpaces(AcideTextComponent target, int numLine){
		
		Document doc = target.getDocument();
		Element rootElement = doc.getDefaultRootElement();

		int start = rootElement.getElement(numLine).getStartOffset();
		return start;
	}
	
	/**
	 * Gets the position where a line starts in the text area
	 */
	private int getStartPositionNoSpaces(AcideTextComponent target){
		
		Document doc = target.getDocument();
		Element rootElement = doc.getDefaultRootElement();
		int selectionStart = target.getSelectionStart();
		int line = rootElement.getElementIndex(selectionStart);

		int start = rootElement.getElement(line).getStartOffset();
		return start;
	}

	/**
	 * Gets the first line of the selection
	 */
	private int getFirstLine(AcideTextComponent target){
	
		Document doc = target.getDocument();
		Element rootElement = doc.getDefaultRootElement();
		int selectionStart = target.getSelectionStart();
		int firstLine = rootElement.getElementIndex(selectionStart);
		
		return firstLine;
	}
	
	/**
	 * Gets the text of the current line
	 */
	public String getCurrentLine(AcideTextComponent target){
		// Determine which line we are on
		Document doc = target.getDocument();
		Element rootElement = doc.getDefaultRootElement();
		int selectionStart = target.getSelectionStart();
		int line = rootElement.getElementIndex(selectionStart);
		// Get the text for this line
		int start = rootElement.getElement(line).getStartOffset();
		int end = rootElement.getElement(line).getEndOffset();
		int length = end - start;
		String text = "";
		try {
			text = doc.getText(start, length);
		} catch (BadLocationException e) {
			
			e.printStackTrace();
		}
		return text;
	}
	
	/**
	 * Gets the text of a line
	 */
	public String getCurrentLine(AcideTextComponent target, int numLine){
		// Determine which line we are on
		Document doc = target.getDocument();
		Element rootElement = doc.getDefaultRootElement();
		// Get the text for this line
		int start = rootElement.getElement(numLine).getStartOffset();
		int end = rootElement.getElement(numLine).getEndOffset();
		int length = end - start;
		String text = "";
		try {
			text = doc.getText(start, length);
		} catch (BadLocationException e) {
			
			e.printStackTrace();
		}
		return text;
	}

}
