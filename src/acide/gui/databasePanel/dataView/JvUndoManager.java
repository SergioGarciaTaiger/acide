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
package acide.gui.databasePanel.dataView;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.KeyStroke;
import javax.swing.undo.CannotRedoException;
import javax.swing.undo.CannotUndoException;
import javax.swing.undo.UndoManager;
import javax.swing.undo.UndoableEdit;

import acide.language.AcideLanguageManager;

public class JvUndoManager extends UndoManager{

	private static final long serialVersionUID = 1L;
	
	protected Action _undoAction;
	protected Action _redoAction;

	public JvUndoManager(){
		this._undoAction = new JvUndoAction(this);
		this._redoAction = new JvRedoAction(this);

		synchronizeActions();           // to set initial names
	}

	public Action getUndoAction(){
		return _undoAction;
	}

	public Action getRedoAction(){
		return _redoAction;
	}

	@Override
	public boolean addEdit(UndoableEdit anEdit){
		try {
			return super.addEdit(anEdit);
		} finally {
			synchronizeActions();
		}
	}

	@Override
	protected void undoTo(UndoableEdit edit) throws CannotUndoException {
		try {
			super.undoTo(edit);
		} finally {
			synchronizeActions();
		}
	}

	@Override
	protected void redoTo(UndoableEdit edit) throws CannotRedoException {
		try {
			super.redoTo(edit);
		} finally {
			synchronizeActions();
		}
	}

	protected void synchronizeActions() {
		_undoAction.setEnabled(canUndo());
		
		_undoAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_Z,
				ActionEvent.CTRL_MASK));

		_redoAction.setEnabled(canRedo());
		
		_redoAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_Y,
				ActionEvent.CTRL_MASK));
		updateNames();
	}
	
	public void updateNames(){
		_undoAction.putValue(Action.NAME, AcideLanguageManager.getInstance().getLabels()
				.getString("s21"));
		_redoAction.putValue(Action.NAME, AcideLanguageManager.getInstance().getLabels()
				.getString("s22"));
	}
}

class JvUndoAction extends AbstractAction {

	private static final long serialVersionUID = 1L;
	protected final UndoManager manager;


	public JvUndoAction(UndoManager manager){
		this.manager = manager;
	}

	public void actionPerformed(ActionEvent e){
		try {
			manager.undo();
		} catch (CannotUndoException ex) {
			ex.printStackTrace();
		}
	}
}

class JvRedoAction extends AbstractAction {

	private static final long serialVersionUID = 1L;
	protected final UndoManager manager;

	public JvRedoAction(UndoManager manager) {
		this.manager = manager;
	}

	public void actionPerformed(ActionEvent e) {
		try {
			manager.redo();
		} catch (CannotRedoException ex) {
			ex.printStackTrace();
		}
	}
}