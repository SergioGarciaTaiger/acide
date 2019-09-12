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

import java.util.Stack;

import javax.swing.event.DocumentListener;
import javax.swing.event.UndoableEditListener;
import javax.swing.undo.UndoManager;


/**
 * ACIDE - A Configurable IDE undo manager.
 * 
 * @version 0.11
 * @see UndoManager
 * @see UndoableEditListener
 * @see DocumentListener
 */
public class AcideDataViewUndoManager extends UndoManager implements UndoableEditListener{

	/**
	 * ACIDE - A Configurable IDE undo manager class serial version UID.
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * ACIDE - A Configurable IDE undo manager unique class instance.
	 */
	private static AcideDataViewUndoManager _instance;
	/**
	 * ACIDE - A Configurable IDE undo manager text component.
	 */
	private Stack<Object> _undoStack = new Stack<Object>();
	private Stack<Object> _redoStack = new Stack<Object>();

	/**
	 * Returns the ACIDE - A Configurable IDE undo manager unique class
	 * instance.
	 * 
	 * @return the ACIDE - A Configurable IDE undo manager unique class
	 *         instance.
	 */
	public static AcideDataViewUndoManager getInstance() {

		if (_instance == null)
			_instance = new AcideDataViewUndoManager();
		return _instance;
	}

	/**
	 * Creates a new ACIDE - A Configurable IDE undo manager.
	 */
	public AcideDataViewUndoManager() {
		super();
	}

	/*
	 * Add a DocumentLister before the undo is done so we can position the Caret
	 * correctly as each edit is undone.
	 */
	@Override
	public void undo() {
		Object element = popUndo();
		pushOnRedo(element);
	}

	/*
	 * Adds a DocumentLister before the redo is done so we can position the
	 * Caret correctly as each edit is redone.
	 */
	@Override
	public void redo() {
		Object element = popRedo();
		pushOnUndo(element);
	}
	
	public void pushOnUndo(Object element){
		_undoStack.push(element);
	}
	
	public void pushOnRedo(Object element){
		_redoStack.push(element);
	}
	
	public Object popUndo(){
		return _undoStack.pop();
	}
	
	public Object popRedo(){
		return _redoStack.pop();
	}
}
