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
package acide.gui.debugPanel.utils;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Rectangle;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.Set;

import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultHighlighter;
import javax.swing.text.Element;
import javax.swing.text.Highlighter;

import acide.gui.assertedDatabasePanel.AcideAssertedDatabasePanel;
import acide.gui.databasePanel.dataView.menuBar.editMenu.gui.AcideDataViewReplaceWindow;
import acide.gui.fileEditor.fileEditorManager.AcideFileEditorManager;
import acide.gui.fileEditor.fileEditorPanel.fileEditorTextEditionArea.utils.AcideTextComponent;
import acide.gui.mainWindow.AcideMainWindow;
import acide.process.console.DesDatabaseManager;

/**
 * 
 * ACIDE - A Configurable IDE debug panel high lighter.
 * 
 * @version 0.15
 * 
 */
public class AcideDebugPanelHighLighter {

	/**
	 * ACIDE - A Configurable IDE query debug panel highlighter lines.
	 */
	private HashMap<String, ArrayList<Integer>> _fileLines;
	/**
	 * ACIDE - A Configurable IDE query debug panel highlighter asserted facts.
	 */
	private HashSet<String> _assertedFacs;
	/**
	 * ACIDE - A Configurable IDE query debug panel highlighter highlights.
	 */
	private ArrayList<Object> _highLights;
	/**
	 * ACIDE - A Configurable IDE query debug panel highlighter highlight flag.
	 */
	private boolean _highlight;
	/**
	 * ACIDE - A Configurable IDE query debug panel highlighter query.
	 */
	private String _query;
	/**
	 * ACIDE - A Configurable IDE query debug panel highlighter class color.
	 */
	public static Color _highLightColor = Color.GREEN;

	/**
	 * Creates a new ACIDE - A Configurable IDE debug panel highlighter.
	 */
	public AcideDebugPanelHighLighter() {
		setLines(new HashMap<String, ArrayList<Integer>>());
		setHighLights(new ArrayList<Object>());
		setAssertedFacs(new HashSet<String>());
		_highlight = true;
	}

	/**
	 * Returns the ACIDE - A Configurable IDE debug panel highlighter lines.
	 * 
	 * @return the ACIDE - A Configurable IDE debug panel highlighter lines.
	 */
	public HashMap<String, ArrayList<Integer>> getLines() {
		return _fileLines;
	}

	/**
	 * Sets a new value to the ACIDE - A Configurable IDE debug panel
	 * highlighter lines.
	 * 
	 * @param lines
	 *            the new value to set.
	 */
	public void setLines(HashMap<String, ArrayList<Integer>> lines) {
		this._fileLines = lines;
	}

	/**
	 * Returns the ACIDE - A Configurable IDE debug panel highlighter asserted
	 * facts.
	 * 
	 * @return the ACIDE - A Configurable IDE debug panel highlighter asserted
	 *         facts.
	 */
	public HashSet<String> getAssertedFacs() {
		return _assertedFacs;
	}

	/**
	 * Sets a new value to the ACIDE - A Configurable IDE debug panel
	 * highlighter asserted facts.
	 * 
	 * @param assertedFacs
	 *            the new value to set.
	 */
	public void setAssertedFacs(HashSet<String> assertedFacs) {
		this._assertedFacs = assertedFacs;
	}

	/**
	 * Returns the ACIDE - A Configurable IDE debug panel highlighter highlight
	 * flag.
	 * 
	 * @return the ACIDE - A Configurable IDE debug panel highlighter highlight
	 *         flag.
	 */
	public boolean isHighlight() {
		return _highlight;
	}

	/**
	 * Sets a new value to the ACIDE - A Configurable IDE debug panel
	 * highlighter highlight flag.
	 * 
	 * @param highlight
	 *            the new value to set.
	 */
	public void setHighlight(boolean highlight) {
		this._highlight = highlight;
	}

	/**
	 * Returns the ACIDE - A Configurable IDE debug panel highlighter
	 * highlights.
	 * 
	 * @return the ACIDE - A Configurable IDE debug panel highlighter
	 *         highlights.
	 */
	public ArrayList<Object> getHighLights() {
		return _highLights;
	}

	/**
	 * Sets a new value to the ACIDE - A Configurable IDE debug panel
	 * highlighter highlights.
	 * 
	 * @param highLights
	 *            the new value to set.
	 */
	public void setHighLights(ArrayList<Object> highLights) {
		this._highLights = highLights;
	}

	/**
	 * Resets the ACIDE - A Configurable IDE debug panel highlighter highlights.
	 */
	public void resetLines() {
		setLines(new HashMap<String, ArrayList<Integer>>());
	}

	/**
	 * Adds a new line to the ACIDE - A Configurable IDE debug panel highlighter
	 * highlights.
	 * 
	 * @param file
	 *            the file's path.
	 * @param line
	 *            the line to highlight.
	 */
	public void addLine(String file, int line) {
		if (!_fileLines.containsKey(file))
			_fileLines.put(file, new ArrayList<Integer>());
		_fileLines.get(file).add(line);
	}

	/**
	 * Adds new lines to the ACIDE - A Configurable IDE debug panel highlighter
	 * highlights.
	 * 
	 * @param file
	 *            file the file's path.
	 * @param lines
	 *            the lines to highlight.
	 */
	public void addLines(String file, ArrayList<Integer> lines) {
		if (!this._fileLines.containsKey(file))
			this._fileLines.put(file, new ArrayList<Integer>());
		this._fileLines.get(file).addAll(lines);
	}

	/**
	 * Adds new fact to the ACIDE - A Configurable IDE debug panel highlighter
	 * highlights.
	 * 
	 * @param fact
	 *            the fact to highlight.
	 */
	public void addFact(String fact) {
		_assertedFacs.add(fact);
	}

	/**
	 * Adds new facts to the ACIDE - A Configurable IDE debug panel highlighter
	 * highlights.
	 * 
	 * @param facts
	 */
	public void addFacts(Set<String> facts) {
		_assertedFacs.addAll(facts);
	}

	/**
	 * Removes the fact to the ACIDE - A Configurable IDE debug panel
	 * highlighter highlights.
	 * 
	 * @param fact
	 */
	public void removeFact(String fact) {
		if (_assertedFacs.contains(fact))
			_assertedFacs.remove(fact);
	}

	/**
	 * Removes the line to the ACIDE - A Configurable IDE debug panel
	 * highlighter highlights.
	 * 
	 * @param file
	 *            file the file's path.
	 */
	public void removeLines(String file) {
		if (_fileLines.containsKey(file))
			_fileLines.remove(file);
	}

	/**
	 * Hihghlights the lines on the file editor.
	 */
	public void highLight() {
		AcideFileEditorManager fileEditorManager = AcideMainWindow
				.getInstance().getFileEditorManager();

		// Checks if the asserted database panel is open
		if (AcideMainWindow.getInstance().isAssertedDatabaseOpened()) {
			AcideAssertedDatabasePanel assertedDatabase = AcideMainWindow
					.getInstance().getAssertedDatabasePanel();
			// Checks if the asserted database panel is showing
			if (assertedDatabase.isShowing())
				assertedDatabase.repaint();
		}
		// Iterates the lines on the lines map
		for (String f : _fileLines.keySet()) {
			// Searches for the file in the file editor manager
			for (int i = 0; i < fileEditorManager.getNumberOfFileEditorPanels(); i++) {
				try {
					if (fileEditorManager.getFileEditorPanelAt(i)
							.getAbsolutePath() != null
							&& new File(fileEditorManager.getFileEditorPanelAt(
									i).getAbsolutePath()).getCanonicalFile()
									.equals(new File(f).getCanonicalFile())) {
						AcideTextComponent text = fileEditorManager
								.getFileEditorPanelAt(i)
								.getActiveTextEditionArea();
						// Removes the previous highlights
						text.getHighlighter().removeAllHighlights();
						Element rootElement = text.getStyledDocument()
								.getDefaultRootElement();
						for (Integer line : _fileLines.get(f)) {
							// Gets the start and the end ofset of the highlight
							int start = rootElement.getElement(line)
									.getStartOffset();
							int end = rootElement.getElement(line)
									.getEndOffset() - 1;
							Highlighter.HighlightPainter painter = new DefaultHighlighter.DefaultHighlightPainter(
									_highLightColor);
							try {
								// If the hihglight flag is true highlights the
								// line
								if (_highlight)
									_highLights.add(text.getHighlighter()
											.addHighlight(start, end, painter));

							} catch (BadLocationException e) {
								e.printStackTrace();
							}
						}
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * Parses the output of the /tapi /list_sources query command and saves the
	 * lines to highlight.
	 * 
	 * @param source
	 *            the output of the /tapi /list_sources query command.
	 */
	public void parseListSources(String source) {
		// Gets a scanner from the source
		InputStream in = new ByteArrayInputStream(source.getBytes());
		_assertedFacs = new HashSet<String>();
		Scanner scan = new Scanner(in);
		String file = null;
		String line = null;
		boolean isFile = false;
		boolean isAsserted = false;
		int initLine = -1;
		int endLine = -1;
		
		// Puts the wait cursor
		AcideDataViewReplaceWindow.getInstance().setCursor(
				Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
					
		// reads the line and chechks that is not a end nor an error
		while (scan.hasNextLine()
				&& !(line = scan.nextLine()).replaceAll(" ", "").equals(
						"$error") && !line.replaceAll(" ", "").equals("$eot")) {
			// checks if the next lines corresponds to a file
			if (line.replaceAll(" ", "").equals("$file")) {
				isAsserted = false;
				isFile = true;
				continue;
			}
			// checks if the next lines corresponds to an asserted predicate
			if (line.replaceAll(" ", "").equals("$asserted")) {
				isAsserted = true;
				isFile = false;
				continue;
			}
			if (isAsserted) {
				_assertedFacs.add(this._query);
				continue;
			}
			if (isFile) {
				// checks if the line is a number of line or the file path
				if (line.replaceAll(" ", "").matches("(\\d)+")) {
					// gets the initial and the end line and adds the lines to
					// the highlight
					if (initLine == -1)
						initLine = Integer.parseInt(line.replaceAll(" ", ""));
					else if (endLine == -1) {
						endLine = Integer.parseInt(line.replaceAll(" ", ""));
						for (int i = initLine - 1; i < endLine; i++)
							addLine(file, i);
						initLine = -1;
						endLine = -1;
					}
				} else {
					file = line.substring(line.indexOf("'") + 1,
							line.lastIndexOf("'"));
				}
			}
		}
		// Puts the default cursor
		AcideDataViewReplaceWindow.getInstance().setCursor(
			Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
					
		scan.close();
	}

	/**
	 * Sends the tapi /list_sources query to the console, parses the output and
	 * hihglights the result on the file editor.
	 * 
	 * @param query
	 *            the query for the /tapi /list_sources command.
	 */
	public void highLight(String query) {
		this._query = query;
		// Resets the list of lines
		resetLines();
		// Gets the result of the /tapi /list_sources to get the new lines to
		// highlight
		LinkedList<String> l = DesDatabaseManager.getInstance().executeCommand(
				"/tapi /list_sources " + query);
		String result = "";
		for (String s : l) {
			result += s + "\n";
		}
		// Parses the result of the command
		parseListSources(result);
		// highlight the lines
		highLight();
	}
	
	public void refreshHighLight(){
		this.highLight(_query);
	}

	/**
	 * Unhighlights the previous highlights.
	 */
	public void unHighLight() {
		AcideFileEditorManager fileEditorManager = AcideMainWindow
				.getInstance().getFileEditorManager();
		// checks if the Asserted database panel is open
		if (AcideMainWindow.getInstance().isAssertedDatabaseOpened()) {
			AcideAssertedDatabasePanel assertedDatabase = AcideMainWindow
					.getInstance().getAssertedDatabasePanel();
			// checks if the Asserted database panel is showing
			if (assertedDatabase.isShowing())
				assertedDatabase.repaint();
		}
		// Iterates the lines on the lines map
		for (String f : _fileLines.keySet()) {
			// Searches for the file in the file editor manager
			for (int i = 0; i < fileEditorManager.getNumberOfFileEditorPanels(); i++) {
				if (fileEditorManager.getFileEditorPanelAt(i).getAbsolutePath() != null
						&& new File(fileEditorManager.getFileEditorPanelAt(i)
								.getAbsolutePath()).equals(new File(f))) {
					// Unhighlights the lines
					AcideTextComponent text = fileEditorManager
							.getFileEditorPanelAt(i).getActiveTextEditionArea();

					for (Highlighter.Highlight h : text.getHighlighter()
							.getHighlights())
						text.getHighlighter().removeHighlight(h);
					text.repaint();

				}
			}
		}

	}

	public void updateCarretPosition() {
		AcideFileEditorManager fileEditorManager = AcideMainWindow
				.getInstance().getFileEditorManager();

		// Iterates the lines on the lines map
		for (String f : _fileLines.keySet()) {
			// Searches for the file in the file editor manager
			for (int i = 0; i < fileEditorManager.getNumberOfFileEditorPanels(); i++) {
				try {
					if (fileEditorManager.getFileEditorPanelAt(i)
							.getAbsolutePath() != null
							&& new File(fileEditorManager.getFileEditorPanelAt(
									i).getAbsolutePath()).getCanonicalFile()
									.equals(new File(f).getCanonicalFile())) {
						if(fileEditorManager.getSelectedFileEditorPanelIndex()!=i)
							fileEditorManager.setSelectedFileEditorPanelAt(i);
						Integer firstLine = _fileLines.get(f).get(0);
						// Gets the edition area of the file edirot panel
						AcideTextComponent text = fileEditorManager
								.getFileEditorPanelAt(i)
								.getActiveTextEditionArea();
						
						// Gets the root elemen of the area
						Element rootElement = text.getStyledDocument()
								.getDefaultRootElement();
						
						
						// Gets the start and the end offset of the highlighted text
						int startOffset = rootElement.getElement(firstLine)
								.getStartOffset();
						int endOffset = rootElement.getElement(rootElement.getElementCount()-1)
								.getStartOffset();
						// Gets the rectangles with the offset of the selected elemen
						Rectangle startRect = text.modelToView(startOffset);
						Rectangle endRect = text.modelToView(endOffset);
						
						// scrolls the panel
						text.scrollRectToVisible(endRect);
						text.scrollRectToVisible(startRect);
						
						
					}
				} catch (IOException e) {
				
				} catch (BadLocationException e) {
					
				}
			}
		}
	}
}
