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
package acide.gui.databasePanel.dataView.menuBar.editMenu;

import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.Locale;

import javax.swing.ImageIcon;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JSeparator;
import javax.swing.KeyStroke;

import acide.configuration.menu.AcideMenuConfiguration;
import acide.gui.databasePanel.dataView.AcideDatabaseDataView;
import acide.gui.databasePanel.dataView.listeners.AcideDataViewCopyMenuItemListener;
import acide.gui.databasePanel.dataView.listeners.AcideDataViewCutMenuItemListener;
import acide.gui.databasePanel.dataView.listeners.AcideDataViewPasteMenuItemListener;
import acide.gui.databasePanel.dataView.listeners.AcideDataViewReplaceMenuItemListener;
import acide.gui.databasePanel.dataView.listeners.AcideDataViewSearchMenuItemListener;
import acide.gui.mainWindow.AcideMainWindow;
import acide.language.AcideLanguageManager;
import acide.log.AcideLog;

/**
 * ACIDE - A Configurable IDE edit menu.
 * 
 * @version 0.13
 * @see JMenu
 */
public class AcideDataViewEditMenu extends JMenu {

	/**
	 * ACIDE - A Configurable IDE edit menu class serial version UID.
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * ACIDE - A Configurable IDE edit menu copy menu item name.
	 */
	public static final String COPY_NAME = "Copy";
	/**
	 * ACIDE - A Configurable IDE edit menu paste menu item name.
	 */
	public static final String PASTE_NAME = "Paste";
	/**
	 * ACIDE - A Configurable IDE edit menu cut menu item name.
	 */
	public static final String CUT_NAME = "Cut";

	/**
	 * ACIDE - A Configurable IDE edit menu search menu item name.
	 */
	public static final String SEARCH_NAME = "Search";
	/**
	 * ACIDE - A Configurable IDE edit menu replace menu item name.
	 */
	public static final String REPLACE_NAME = "Replace";
	/**
	 * ACIDE - A Configurable IDE edit menu copy menu item image icon.
	 */
	private final static ImageIcon COPY_IMAGE = new ImageIcon(
			"./resources/icons/menu/edit/copy.png");
	/**
	 * ACIDE - A Configurable IDE edit menu paste menu item image icon.
	 */
	private final static ImageIcon PASTE_IMAGE = new ImageIcon(
			"./resources/icons/menu/edit/paste.png");
	/**
	 * ACIDE - A Configurable IDE edit menu cut menu item image icon.
	 */
	private final static ImageIcon CUT_IMAGE = new ImageIcon(
			"./resources/icons/menu/edit/cut.png");
	/**
	 * ACIDE - A Configurable IDE edit menu search menu item image icon.
	 */
	private final static ImageIcon SEARCH_IMAGE = new ImageIcon(
			"./resources/icons/menu/edit/search.png");
	/**
	 * ACIDE - A Configurable IDE edit menu replace menu item image icon.
	 */
	private final static ImageIcon REPLACE_IMAGE = new ImageIcon(
			"./resources/icons/menu/edit/replace.png");
	/**
	 * ACIDE - A Configurable IDE edit menu search menu item.
	 */
	private JMenuItem _searchMenuItem;
	/**
	 * ACIDE - A Configurable IDE edit menu paste menu item.
	 */
	private JMenuItem _pasteMenuItem;
	/**
	 * ACIDE - A Configurable IDE edit menu copy menu item.
	 */
	private JMenuItem _copyMenuItem;
	/**
	 * ACIDE - A Configurable IDE edit menu cut menu item.
	 */
	private JMenuItem _cutMenuItem;
	/**
	 * ACIDE - A Configurable IDE edit menu replace menu item.
	 */
	private JMenuItem _replaceMenuItem;
	/**
	 * ACIDE - A Configurable IDE edit menu redo copy separator.
	 */
	private JSeparator _redoCopySeparator;
	/**
	 * ACIDE - A Configurable IDE edit menu select all go to line separator.
	 */
	private JSeparator _selectAllGoToLineSeparator;
	
	/**
	 * ACIDE - DataView owner of the EditMenu
	 */
	private AcideDatabaseDataView _table;

	/**
	 * Creates a new ACIDE - A Configurable IDE edit menu.
	 * @param table 
	 */
	public AcideDataViewEditMenu(AcideDatabaseDataView table) {

		this._table = table;
		// Builds the menu components
		buildComponents();

		// Adds the components to the menu
		addComponents();

		// Sets the text of the edit menu components
		setTextOfMenuComponents();
	}

	/**
	 * Adds the components to the ACIDE - A Configurable IDE edit menu.
	 */
	private void addComponents() {

		add(_table.getUndoManager().getUndoAction());

		add(_table.getUndoManager().getRedoAction());

		add(_redoCopySeparator);

		add(_copyMenuItem);

		add(_pasteMenuItem);

		add(_cutMenuItem);

		add(_selectAllGoToLineSeparator);

		add(_searchMenuItem);

		add(_replaceMenuItem);
	}

	/**
	 * Builds the ACIDE - A Configurable IDE edit menu components.
	 */
	private void buildComponents() {

		// Creates the redo copy separator
		_redoCopySeparator = new JSeparator();

		// Creates the copy menu item
		_copyMenuItem = new JMenuItem(COPY_IMAGE);

		// Sets the copy menu item name
		_copyMenuItem.setName(COPY_NAME);

		// Creates the paste menu item
		_pasteMenuItem = new JMenuItem(PASTE_IMAGE);

		// Sets the paste menu item name
		_pasteMenuItem.setName(PASTE_NAME);

		// Creates the cut menu item
		_cutMenuItem = new JMenuItem(CUT_IMAGE);

		// Sets the cut menu item name
		_cutMenuItem.setName(CUT_NAME);
		
		// Creates the select all go to line copy separator
		_selectAllGoToLineSeparator = new JSeparator();

		// Creates the search menu item
		_searchMenuItem = new JMenuItem(SEARCH_IMAGE);

		// Sets the search menu item name
		_searchMenuItem.setName(SEARCH_NAME);

		// Creates the replace menu item
		_replaceMenuItem = new JMenuItem(REPLACE_IMAGE);

		// Sets the replace menu item name
		_replaceMenuItem.setName(REPLACE_NAME);
	}

	/**
	 * Sets the text of the ACIDE - A Configurable IDE edit menu components with
	 * the labels in the selected language to display.
	 */
	public void setTextOfMenuComponents() {

		// Sets the copy menu item text
		_copyMenuItem.setText(AcideLanguageManager.getInstance().getLabels()
				.getString("s23"));
		_copyMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C,
				ActionEvent.CTRL_MASK));

		// Sets the cut menu item text
		_cutMenuItem.setText(AcideLanguageManager.getInstance().getLabels()
				.getString("s24"));
		_cutMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X,
				ActionEvent.CTRL_MASK));

		// Sets the paste menu item text
		_pasteMenuItem.setText(AcideLanguageManager.getInstance().getLabels()
				.getString("s25"));
		_pasteMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V,
				ActionEvent.CTRL_MASK));

		// Sets the search menu item text
		_searchMenuItem.setText(AcideLanguageManager.getInstance().getLabels()
				.getString("s26"));
		
		if (AcideLanguageManager.getInstance().getCurrentLocale()
				.equals(new Locale("en", "EN"))
				|| AcideLanguageManager.getInstance().getCurrentLocale()
				.equals(new Locale("fr", "FR")))
			_searchMenuItem.setAccelerator(KeyStroke.getKeyStroke(
					KeyEvent.VK_F, ActionEvent.CTRL_MASK));
		else
			_searchMenuItem.setAccelerator(KeyStroke.getKeyStroke(
					KeyEvent.VK_B, ActionEvent.CTRL_MASK));

		// Sets the replace menu item text
		_replaceMenuItem.setText(AcideLanguageManager.getInstance().getLabels()
				.getString("s27"));

	}

	/**
	 * Updates the ACIDE - A Configurable IDE edit menu components visibility
	 * with the menu configuration.
	 */
	public void updateComponentsVisibility() {

		// Sets the copy menu item to visible or not visible
		_copyMenuItem.setVisible(AcideMenuConfiguration.getInstance()
				.getIsDisplayed(COPY_NAME));

		// Sets the paste menu item to visible or not visible
		_pasteMenuItem.setVisible(AcideMenuConfiguration.getInstance()
				.getIsDisplayed(PASTE_NAME));

		// Sets the cut menu item to visible or not visible
		_cutMenuItem.setVisible(AcideMenuConfiguration.getInstance()
				.getIsDisplayed(CUT_NAME));

		// Sets the search menu item to visible or not visible
		_searchMenuItem.setVisible(AcideMenuConfiguration.getInstance()
				.getIsDisplayed(SEARCH_NAME));

		// Sets the replace menu item to visible or not visible
		_replaceMenuItem.setVisible(AcideMenuConfiguration.getInstance()
				.getIsDisplayed(REPLACE_NAME));
	}

	/**
	 * Sets the ACIDE - A Configurable IDE edit menu menu item listeners.
	 */
	public void setListeners() {

		// Sets the search menu item action listener
		_searchMenuItem.addActionListener(new AcideDataViewSearchMenuItemListener(_table));

		// Sets the replace menu item action listener
		_replaceMenuItem.addActionListener(new AcideDataViewReplaceMenuItemListener(_table));

		// Sets the cut menu item action listener
		_cutMenuItem.addActionListener(new AcideDataViewCutMenuItemListener(_table));

		// Sets the paste menu item action listener
		_pasteMenuItem.addActionListener(new AcideDataViewPasteMenuItemListener(_table));

		// Sets the copy menu item action listener
		_copyMenuItem.addActionListener(new AcideDataViewCopyMenuItemListener(_table));

	}

	/**
	 * Disables the ACIDE - A Configurable IDE edit menu paste menu item.
	 */
	public void disablePaste() {

		// Disables the paste menu item
		_pasteMenuItem.setEnabled(false);

		// Updates the log
		AcideLog.getLog()
				.info(AcideLanguageManager.getInstance().getLabels()
						.getString("s73"));
	}

	/**
	 * Enables the ACIDE - A Configurable IDE edit menu paste menu item.
	 */
	public void enablePaste() {

		// Enables the paste menu item
		_pasteMenuItem.setEnabled(true);

		// Updates the log
		AcideLog.getLog().info(AcideLanguageManager.getInstance().getLabels().getString("s74"));
	}

	/**
	 * Configures the ACIDE - A Configurable IDE edit menu menu item options.
	 */
	public void configure() {

		// Disables the copy menu item
		_copyMenuItem.setEnabled(false);

		// Disables the paste menu item
		_pasteMenuItem.setEnabled(false);

		// Disables the cut menu item
		_cutMenuItem.setEnabled(false);

		// If the system clipboard is not empty
		if (Toolkit.getDefaultToolkit().getSystemClipboard().getContents(null) != null) {

			// If the console panel does not have the focus in the window
			if (!AcideMainWindow.getInstance().getConsolePanel().getTextPane()
					.hasFocus())

				// Enables the paste menu item
				_pasteMenuItem.setEnabled(true);
			else
			// If the caret is after the prompt position
			if (AcideMainWindow.getInstance().getConsolePanel().getTextPane()
					.getSelectionStart() >= AcideMainWindow.getInstance()
					.getConsolePanel().getPromptCaretPosition())

				// Enables the paste menu item
				_pasteMenuItem.setEnabled(true);
		}

		// If there are opened editors
		if (AcideMainWindow.getInstance().getFileEditorManager()
				.getNumberOfFileEditorPanels() > 0) {

			// If the console panel has the focus and there is selected text
			if (AcideMainWindow.getInstance().getConsolePanel().getTextPane()
					.hasFocus()
					&& AcideMainWindow.getInstance().getConsolePanel()
							.getTextPane().getSelectedText() != null) {

				// Enables the copy menu item
				_copyMenuItem.setEnabled(true);

				// If the caret position is after the prompt position
				if (AcideMainWindow.getInstance().getConsolePanel()
						.getTextPane().getSelectionStart() >= AcideMainWindow
						.getInstance().getConsolePanel()
						.getPromptCaretPosition())

					// Enables the cut menu item
					_cutMenuItem.setEnabled(true);
			} else

			// If the file editor text edition area has the focus and
			// there is something selected
			if (AcideMainWindow.getInstance().getFileEditorManager()
					.getSelectedFileEditorPanel().getActiveTextEditionArea()
					.hasFocus()
					&& AcideMainWindow.getInstance().getFileEditorManager()
							.getSelectedFileEditorPanel()
							.getActiveTextEditionArea().getSelectedText() != null) {

				// Enables the copy menu item
				_copyMenuItem.setEnabled(true);

				// Enables the cut menu item
				_cutMenuItem.setEnabled(true);
			}

			// Enables the search menu item
			_searchMenuItem.setEnabled(true);

			// Enables the replace menu item
			_replaceMenuItem.setEnabled(true);

		} else {

			// We can copy from the output
			if (AcideMainWindow.getInstance().getConsolePanel().getTextPane()
					.getSelectedText() != null) {

				// Enables the copy menu item
				_copyMenuItem.setEnabled(true);

				// If the caret position is after the prompt position
				if (AcideMainWindow.getInstance().getConsolePanel()
						.getTextPane().getSelectionStart() >= AcideMainWindow
						.getInstance().getConsolePanel()
						.getPromptCaretPosition())

					// Enables the cut menu item
					_cutMenuItem.setEnabled(true);
			}

			// Disables the search menu item
			_searchMenuItem.setEnabled(false);

			// Disables the replace menu item
			_replaceMenuItem.setEnabled(false);
		}
	}

	/**
	 * Returns the ACIDE - A Configurable IDE edit menu search menu item.
	 * 
	 * @return the ACIDE - A Configurable IDE edit menu search menu item.
	 */
	public JMenuItem getSearchMenuItem() {
		return _searchMenuItem;
	}

	/**
	 * Returns the ACIDE - A Configurable IDE edit menu copy menu item.
	 * 
	 * @return the ACIDE - A Configurable IDE edit menu copy menu item.
	 */
	public JMenuItem getCopyMenuItem() {
		return _copyMenuItem;
	}

	/**
	 * Returns the ACIDE - A Configurable IDE edit menu cut menu item.
	 * 
	 * @return the ACIDE - A Configurable IDE edit menu cut menu item.
	 */
	public JMenuItem getCutMenuItem() {
		return _cutMenuItem;
	}

	/**
	 * Returns the ACIDE - A Configurable IDE edit menu paste menu item.
	 * 
	 * @return the ACIDE - A Configurable IDE edit menu paste menu item.
	 */
	public JMenuItem getPasteMenuItem() {
		return _pasteMenuItem;
	}

	/**
	 * Returns the ACIDE - A Configurable IDE edit menu replace menu item.
	 * 
	 * @return the ACIDE - A Configurable IDE edit menu replace menu item.
	 */
	public JMenuItem getReplaceMenuItem() {
		return _replaceMenuItem;
	}
	
	@Override
	public void setEnabled(boolean enabled){
		_copyMenuItem.setEnabled(enabled);
		_cutMenuItem.setEnabled(enabled);
		_pasteMenuItem.setEnabled(enabled);
		_replaceMenuItem.setEnabled(enabled);
		_searchMenuItem.setEnabled(enabled);
	}
	
	/**
	 * Sets the ACIDE - A Configurable IDE edit menu on Read Only mode.
	 */
	public void isReadOnly(boolean b){
		_cutMenuItem.setEnabled(b);
		_pasteMenuItem.setEnabled(b);
		_replaceMenuItem.setEnabled(b);
		_cutMenuItem.setVisible(b);
		_pasteMenuItem.setVisible(b);
		_replaceMenuItem.setVisible(b);
	}
}