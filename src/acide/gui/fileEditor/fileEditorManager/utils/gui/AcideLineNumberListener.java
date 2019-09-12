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
package acide.gui.fileEditor.fileEditorManager.utils.gui;

import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.text.BadLocationException;
import javax.swing.text.Utilities;

import acide.gui.fileEditor.fileEditorPanel.fileEditorTextEditionArea.utils.AcideTextComponent;
import acide.gui.mainWindow.AcideMainWindow;

/**
 * ACIDE - A Configurable IDE line number component 2 listener.
 * 
 * @version 0.15
 * @see MouseListener, MouseMotionListener
 */
public class AcideLineNumberListener implements MouseListener, MouseMotionListener{
	
	private int rowStartOffset=0;

	@Override
	public void mouseClicked(MouseEvent arg0) {

		
		int coorY = arg0.getY();//Get the position
			
				
		AcideTextComponent target = AcideMainWindow
			.getInstance().getFileEditorManager()
			.getSelectedFileEditorPanel().getActiveTextEditionArea();
				
		target.grabFocus();
				
		try {
					
			rowStartOffset = target.viewToModel(new Point(0,coorY));
				
			int rowEndOffSet = Utilities.getRowEnd(target,
									rowStartOffset);
			
			target.select(rowStartOffset, rowEndOffSet);//Select the corresponding line
					
		} catch (BadLocationException e) {
					e.printStackTrace();
		}
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		
	}

	@Override
	public void mousePressed(MouseEvent arg0) {

		int coorY = arg0.getY();
		
		AcideTextComponent target = AcideMainWindow
			.getInstance().getFileEditorManager()
			.getSelectedFileEditorPanel().getActiveTextEditionArea();
				
		rowStartOffset = target.viewToModel(new Point(0,coorY));

	}

	@Override
	public void mouseReleased(MouseEvent arg0) {

	}

	@Override
	public void mouseDragged(MouseEvent arg0) {
				
		int coorY2 = arg0.getY();
		selectText(coorY2);

	}

	private void selectText(int coorY2) {
		
		AcideTextComponent target = AcideMainWindow
				.getInstance().getFileEditorManager()
				.getSelectedFileEditorPanel().getActiveTextEditionArea();
		
		target.grabFocus();

		try {
				int rowEndOffSet = target.viewToModel(new Point(0,coorY2));
				int endLineOffSet = Utilities.getRowEnd(target,rowEndOffSet);
				
				if (rowStartOffset<=rowEndOffSet) target.select(rowStartOffset, endLineOffSet);
				else {  int startLineOffset = Utilities.getRowEnd(target,rowStartOffset);
						target.select(rowEndOffSet, startLineOffset);
				}
		
		
		} catch (BadLocationException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void mouseMoved(MouseEvent arg0) {
	}

}
