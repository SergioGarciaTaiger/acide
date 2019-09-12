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
package acide.gui.databasePanel.dataView.menuBar.editMenu.gui;

import java.awt.Cursor;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Vector;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JTextField;
import javax.swing.KeyStroke;

import acide.gui.databasePanel.dataView.AcideDatabaseDataView;
import acide.gui.mainWindow.AcideMainWindow;
import acide.language.AcideLanguageManager;

import com.jidesoft.swing.AutoCompletionComboBox;

/**
 * ACIDE - A Configurable IDE replace window.
 * 
 * @version 0.11
 * @see JFrame
 */
public class AcideDataViewReplaceWindow extends JFrame implements PropertyChangeListener {

	/**
	 * Indicates if the dialog with the finish of the search has been displayed
	 */
	private boolean _finishedF3;
	/**
	 * ACIDE - ProgressBar to show
	 */
	private JProgressBar _barDo;
	
	/**
	 * ACIDE - A Configurable IDE replace window search history
	 */
	private static Vector<String> _list;
	
	/**
	 * ACIDE - A Configurable IDE replace window replace history
	 */
	private static Vector<String> _listReplace;
	/**
	 * ACIDE - A Configurable IDE replace window text to search
	 */
	private static String _textToSearch;
	/**
	 * ACIDE - A Configurable IDE replace window text to replace
	 */
	private static String _textToReplace;
	
	/**
	 * ACIDE - A Configurable IDE replace window serial version UID.
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * ACIDE - A Configurable IDE replace window image icon.
	 */
	private static final ImageIcon ICON = new ImageIcon(
			"./resources/images/icon.png");
	/**
	 * ACIDE - A Configurable IDE replace window unique class instance.
	 */
	private static AcideDataViewReplaceWindow _instance;
	/**
	 * ACIDE - A Configurable IDE replace window button panel.
	 */
	private JPanel _buttonPanel;
	
	private JLabel _searchLabel;
	/**
	 * ACIDE - A Configurable IDE replace window recent searchs label.
	 */
	private JLabel _recentSearchesLabel;
	/**
	 * ACIDE - A Configurable IDE search window recent replaces label.
	 */
	private JLabel _recentReplacesLabel;
	/**
	 * ACIDE - A Configurable IDE replace window search text field.
	 */
	private JTextField _searchTextField;
	/**
	 * ACIDE - A Configurable IDE replace window replace label.
	 */
	private JLabel _replaceLabel;
	/**
	 * ACIDE - A Configurable IDE replace window replace text field.
	 */
	private JTextField _replaceTextField;

	/**
	 * ACIDE - A Configurable IDE replace window replace button.
	 */
	private JButton _replaceButton;
	/**
	 * ACIDE - A Configurable IDE replace window replace all button.
	 */
	private JButton _replaceAllButton;
	/**
	 * ACIDE - A Configurable IDE replace window cancel button.
	 */
	private JButton _cancelButton;
	/**
	 * ACIDE - A Configurable IDE replace window selected text for the selected
	 * text search.
	 */
	private String _selectedText = null;

	private static boolean _isEnd = false;
	
	/**
	 * ACIDE - A Configurable IDE replace window comboBox for recent
	 * searchs.
	 */
	private AutoCompletionComboBox _comboBox;
	/**
	 * ACIDE - A Configurable IDE replace window comboBox for recent
	 * searchs.
	 */
	private AutoCompletionComboBox _comboBoxReplace;

	/**
	 * ACIDE - DataView to replace data
	 */
	private AcideDatabaseDataView _table;

	/**
	 * Returns the ACIDE - A Configurable IDE replace window unique class
	 * instance.
	 * 
	 * @return the ACIDE - A Configurable IDE replace window unique class
	 *         instance.
	 */
	public static AcideDataViewReplaceWindow getInstance() {
		if (_instance == null)
			_instance = new AcideDataViewReplaceWindow();
		return _instance;
	}
	
	public void setProgress(int i){
		_barDo.setValue(i);
		_barDo.repaint(); 
	}

	/**
	 * Initializes the ACIDE - A Configurable IDE replace window.
	 */
	public void initialize() {
		_instance = null;
		_list = getList();
		_listReplace = getListReplace();
	}

	/**
	 * Creates a new ACIDE - A Configurable IDE replace window.
	 */
	public AcideDataViewReplaceWindow() {

		// Builds the window components
		buildComponents();

		// Adds the components to the window
		addComponents();

		// Sets the listeners of the window components
		setListeners();

		// Sets the window configuration
		setWindowConfiguration();
	}

	/**
	 * Builds the ACIDE - A Configurable IDE search/replace window components.
	 */
	private void buildComponents() {

		// Builds the search/replace text fields
		buildSearchReplaceTextFields();

		// Builds the direction panel
		_barDo = new JProgressBar(0, 100);

		// Builds the button panel
		buildButtonPanel();
	}

	/**
	 * Sets the ACIDE - A Configurable IDE search/replace window configuration.
	 */
	private void setWindowConfiguration() {

		// Sets the search window title
		setTitle(AcideLanguageManager.getInstance().getLabels()
				.getString("s572"));

		// Sets the window icon image
		setIconImage(ICON.getImage());

		// The window is not resizable
		setResizable(false);

		// Packs the window components
		pack();

		// Always at the front
		setAlwaysOnTop(true);

		// Centers the window
		setLocationRelativeTo(null);
	}
	
	public Vector<String> getList() {
		if (_list == null){
			_list = new Vector<String>();
		}
		return _list;
	}

	public void setList(Vector<String> _list) {
		AcideDataViewReplaceWindow._list = _list;
	}

	/**
	 * Adds the components to the window.
	 */
	public void addComponents() {

		// Sets the layout
		setLayout(new GridBagLayout());

		// Adds the components to the window with the layout
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.insets = new Insets(5, 5, 5, 5);
		constraints.anchor = GridBagConstraints.WEST;
		constraints.fill = GridBagConstraints.NONE;
		constraints.gridx = 0;
		constraints.gridy = 0;
		constraints.gridwidth = 2;

		// Adds the replace label to the window
		add(_searchLabel, constraints);

		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.gridy = 1;

		// Adds the replace text field to the window
		add(_searchTextField, constraints);
		
		constraints.gridy = 2;
		constraints.fill = GridBagConstraints.NONE;
		
		//Adds the recent searchs label to the window
		add(_recentSearchesLabel, constraints);
		
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.gridy = 3;
		
		// Adds the recent searchs combo box to the window
		add(_comboBox, constraints);

		constraints.fill = GridBagConstraints.NONE;
		constraints.gridy = 4;

		// Adds the replaced label to the window
		add(_replaceLabel, constraints);

		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.gridy = 5;

		// Adds the replaced text field to the window
		add(_replaceTextField, constraints);
		
		constraints.gridy = 6;
		constraints.fill = GridBagConstraints.NONE;
		
		//Adds the recent searchs label to the window
		add(_recentReplacesLabel, constraints);
		
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.gridy = 7;
		
		// Adds the recent searchs combo box to the window
		add(_comboBoxReplace, constraints);

//		constraints.fill = GridBagConstraints.HORIZONTAL;
//		constraints.gridy = 8;
//		constraints.gridwidth = 2;	
//		
//		add(barDo, constraints);

		constraints.anchor = GridBagConstraints.LINE_END;
		constraints.gridx = 0;
		constraints.gridy = 11;
		constraints.gridwidth = 2;

		// Adds the button panel to the window
		add(_buttonPanel, constraints);

		// Validates the changes in the search/replace window
		validate();
	}

	/**
	 * Creates the search/replace labels and text fields.
	 */
	private void buildSearchReplaceTextFields() {

		// Creates the search label
		_searchLabel = new JLabel(AcideLanguageManager.getInstance()
				.getLabels().getString("s557"), JLabel.CENTER);

		// Creates the search text field
		//_searchTextField = new JSuggestField(this, getList());
		_searchTextField = new JTextField();
		
		_comboBox = new AutoCompletionComboBox(getList());
		
		// Creates the recent searches label
		_recentSearchesLabel = new JLabel(AcideLanguageManager.getInstance()
			.getLabels().getString("s2168"), JLabel.CENTER);
		
		// Creates the replace label
		_replaceLabel = new JLabel(AcideLanguageManager.getInstance()
				.getLabels().getString("s558"), JLabel.CENTER);

		// Creates the replace text field
		//_replaceTextField = new JSuggestField(this, getListReplace());
		_replaceTextField = new JTextField();
		
		_comboBoxReplace = new AutoCompletionComboBox(getListReplace());
		
		// Creates the recent replacements label
		_recentReplacesLabel = new JLabel(AcideLanguageManager.getInstance()
			.getLabels().getString("s2168"), JLabel.CENTER);
	}

	/**
	 * Creates the button panel and its components.
	 */
	private void buildButtonPanel() {

		// Creates the button panel
		_buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));

		// Creates the replace button
		_replaceButton = new JButton(AcideLanguageManager.getInstance()
				.getLabels().getString("s572"));

		// Enables it
		_replaceButton.setEnabled(true);

		// Creates the replace all button
		_replaceAllButton = new JButton(AcideLanguageManager.getInstance()
				.getLabels().getString("s571"));

		// Enables it
		_replaceAllButton.setEnabled(true);

		// Creates the cancel button
		_cancelButton = new JButton(AcideLanguageManager.getInstance()
				.getLabels().getString("s42"));

		// Adds the replace button to the button panel
		_buttonPanel.add(_replaceButton);

		// Adds the replace all button to the button panel
		_buttonPanel.add(_replaceAllButton);

		// Adds the cancel button to the button panel
		_buttonPanel.add(_cancelButton);
	}

	
	/**
	 * Sets the listeners of the window components.
	 */
	@SuppressWarnings("rawtypes")
	private void setListeners() {


		// When the enter key is pressed the executes the search button action
		_searchTextField.registerKeyboardAction(new SearchButtonAction(),
				"EnterKey", KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0, true),
				JComponent.WHEN_IN_FOCUSED_WINDOW);

		// Sets the replace button action listener
		_replaceButton.addActionListener(new ReplaceButtonAction());

		// Sets the replace all button action listener
		_replaceAllButton.addActionListener(new ReplaceAllButtonAction());

		// Sets the cancel button action listener
		_cancelButton.addActionListener(new CancelButtonAction());

		// Puts the escape key in the input map of the window
		getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
				KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0, false),
				"EscapeKey");

		// Puts the escape key in the action map of the window
		getRootPane().getActionMap().put("EscapeKey", new EscapeKeyAction());
		
		_comboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				_searchTextField.setText((String)((JComboBox)e.getSource()).getSelectedItem());
			   }
			 });
		
		_comboBoxReplace.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				_replaceTextField.setText((String)((JComboBox)e.getSource()).getSelectedItem());
			   }
			 });
	}

	/**
	 * Checks if a string given as a parameter is capitalized. A string is
	 * capitalized if its first letter is in upper case and the rest are in
	 * lower case.
	 * 
	 * @param string
	 *            string to capitalize.
	 * 
	 * @return returns true if the string given as a parameter is capitalized
	 *         and false in other case.
	 */
	public boolean isCapitalized(String string) {

		return (Character.isUpperCase(string.charAt(0)) && isLowerCase(string
				.substring(1)));
	}

	/**
	 * Checks if a string given as a parameter contains all its letters in upper
	 * case, including all the separate words in it.
	 * 
	 * @param string
	 *            string to analyze.
	 * 
	 * @return returns true if a string given as a parameter contains all its
	 *         letters in upper case and false in other case.
	 */
	public boolean isUpperCase(String string) {
		String[] words = string.split(" ");

		for (int i = 0; i < words.length; i++) {

			for (int j = 0; j < words[i].length(); j++) {
				if (Character.isLowerCase(words[i].charAt(j))) {
					return false;
				}
			}
		}
		return true;
	}

	/**
	 * Checks if a string given as a parameter contains all its letters in lower
	 * case, including all the separate words in it.
	 * 
	 * @param string
	 *            string to analyze.
	 * 
	 * @return returns true if a string given as a parameter contains all its
	 *         letters in lower case and false in other case.
	 */
	public boolean isLowerCase(String string) {

		String[] words = string.split(" ");

		for (int i = 0; i < words.length; i++) {

			for (int j = 0; j < words[i].length(); j++) {
				if (Character.isUpperCase(words[i].charAt(j))) {
					return false;
				}
			}
		}
		return true;
	}

	/**
	 * Counts the number of times a substring occurs in a provided string.
	 * 
	 * @param source
	 *            The <code>String</code> object that will be searched in.
	 * @param substring
	 *            The string whose occurrences will we counted.
	 * @param matchCase
	 *            A <code>boolean</code> indicating if the match is going to be
	 *            performed in a case-sensitive manner or not.
	 * @return An <code>integer</code> value containing the number of
	 *         occurrences of the substring.
	 * @since 1.0
	 */
	public static int countMatches(String source, String substring,
			boolean matchCase) {
		if (null == source) {
			return 0;
		}

		if (null == substring) {
			return 0;
		}

		int current_index = 0;
		int substring_index = 0;
		int count = 0;

		if (!matchCase) {
			source = source.toLowerCase();
			substring = substring.toLowerCase();
		}

		while (current_index < source.length() - 1) {
			substring_index = source.indexOf(substring, current_index);

			if (-1 == substring_index) {
				break;
			} else {
				current_index = substring_index + substring.length();
				count++;
			}
		}

		return count;
	}
	
	public boolean isAcabadoF3() {
		return _finishedF3;
	}

	public void setAcabadoF3(boolean acabadoF3) {
		this._finishedF3 = acabadoF3;
	}
	

	public Vector<String> getListReplace() {
		if (_listReplace == null){
			_listReplace = new Vector<String>();
		}
		return _listReplace;
	}

	public void setListReplace(Vector<String> _listReplace) {
		AcideDataViewReplaceWindow._listReplace = _listReplace;
	}

	/**
	 * Initializes the ACIDE - A Configurable IDE replace window variables.
	 */
	public void initializeVariables() {

		// Sets the is end as false
		_isEnd = false;

		// Sets the selected text as null
		_selectedText = null;
		
		_finishedF3 = false;
		setProgress(0);
		validate();
		
	}

	/**
	 * Returns the ACIDE - A Configurable IDE search window is end flag.
	 * 
	 * @return the ACIDE - A Configurable IDE search window is end flag.
	 */
	public boolean isEnd() {
		return _isEnd;
	}

	/**
	 * Sets a new value to the ACIDE - A Configurable IDE search window is end
	 * flag.
	 * 
	 * @param isEnd
	 *            new value to set.
	 */
	public void setIsEnd(boolean isEnd) {
		_isEnd = isEnd;
	}

	/**
	 * Returns the ACIDE - A Configurable IDE replace window search text field.
	 * 
	 * @return the ACIDE - A Configurable IDE replace window search text field.
	 */
	public JTextField getSearchTextField() {
		return _searchTextField;
	}

	/**
	 * Returns the ACIDE - A Configurable IDE replace window selected text.
	 * 
	 * @return the ACIDE - A Configurable IDE replace window selected text.
	 */
	public String getSelectedText() {
		return _selectedText;
	}

	/**
	 * Sets a new value to the ACIDE - A Configurable IDE replace window
	 * selected text.
	 * 
	 * @param selectedText
	 *            new value to set.
	 */
	public void setSelectedText(String selectedText) {
		_selectedText = selectedText;
	}


	/**
	 * Sets the ACIDE - A Configurable IDE replace window in the foreground or
	 * to the background.
	 * 
	 * @param isOnTop
	 *            new value to set.
	 */
	public void setOnTop(boolean isOnTop) {
		setAlwaysOnTop(isOnTop);
	}
	
	public void setDataView(AcideDatabaseDataView table) {
		this._table = table;
		
	}
	
	public void searchAction(){}

	/**
	 * ACIDE - A Configurable IDE search button action listener.
	 * 
	 * @version 0.11
	 * @see ActionListener
	 */
	class SearchButtonAction implements ActionListener {

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent
		 * )
		 */
		@Override
		public void actionPerformed(ActionEvent actionEvent) {
			

			// Puts the wait cursor
			AcideDataViewReplaceWindow.getInstance().setCursor(
					Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
			
			AcideMainWindow.getInstance().setCursor(
					Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
			
			searchAction();
			
			
			// Puts the default cursor
			AcideDataViewReplaceWindow.getInstance().setCursor(
					Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			
			AcideMainWindow.getInstance().setCursor(
					Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		}
	}

	/**
	 * ACIDE - A Configurable IDE replace button action listener.
	 * 
	 * @version 0.11
	 * @see ActionListener
	 */
	class ReplaceButtonAction implements ActionListener {

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent
		 * )
		 */
		@Override
		public void actionPerformed(ActionEvent actionEvent) {

			_barDo.setVisible(true);
			// Puts the wait cursor
			AcideDataViewReplaceWindow.getInstance().setCursor(
					Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
			
			AcideMainWindow.getInstance().setCursor(
					Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
			
			_textToSearch = _searchTextField.getText();
			
			_textToReplace = _replaceTextField.getText();
			
			if (!getList().contains(_textToSearch)) getList().add(_textToSearch);
			
			if (!getListReplace().contains(_textToReplace)) getListReplace().add(_textToReplace);
				
			_textToSearch = _textToSearch.replaceAll("\\^p","\n\n");
			_textToSearch = _textToSearch.replaceAll("\\^t", "\t");
			
			_textToReplace = _textToReplace.replaceAll("\\^p","\n\n");
			_textToReplace = _textToReplace.replaceAll("\\^t", "\t");
			
			_table.replace(_textToSearch,_textToReplace);
			
			AcideDataViewReplaceWindow.getInstance().setCursor(
					Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));

		}
	}


	/**
	 * ACIDE - A Configurable IDE replace window replace all button action
	 * listener.
	 * 
	 * @version 0.11
	 * @see ActionListener
	 */
	class ReplaceAllButtonAction implements ActionListener {

		/**
		 * Global number of replacements.
		 */
		private int _globalNumberOfReplacements = -1;

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent
		 * )
		 */
		@Override
		public void actionPerformed(ActionEvent actionEvent) {
			AcideDataViewReplaceWindow.getInstance().validate();
			// Puts the wait cursor
			AcideMainWindow.getInstance().setCursor(
					Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
			
			AcideDataViewReplaceWindow.getInstance().setCursor(
					Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
	
			_textToSearch = _searchTextField.getText();
			
			_textToReplace = _replaceTextField.getText();
			
			if (!getList().contains(_textToSearch)) getList().add(_textToSearch);
			
			if (!getListReplace().contains(_textToReplace)) getListReplace().add(_textToReplace);
				
			_textToSearch = _textToSearch.replaceAll("\\^p","\n\n");
			_textToSearch = _textToSearch.replaceAll("\\^t", "\t");
			
			_textToReplace = _textToReplace.replaceAll("\\^p","\n\n");
			_textToReplace = _textToReplace.replaceAll("\\^t", "\t");
			
			_globalNumberOfReplacements = _table.replaceAll(_textToSearch,_textToReplace);
			
			AcideDataViewReplaceWindow.getInstance().setProgress(100);
			JOptionPane.showMessageDialog(
					AcideDataViewReplaceWindow.getInstance(),
					AcideLanguageManager.getInstance().getLabels().getString("s1000")
							+ " " + _globalNumberOfReplacements, AcideLanguageManager.getInstance().getLabels().getString("s572"),
					JOptionPane.INFORMATION_MESSAGE);
			
			AcideDataViewReplaceWindow.getInstance().setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		}
	}

	/**
	 * ACIDE - A Configurable IDE replace window cancel button action listener.
	 * 
	 * @version 0.11
	 * @see ActionListener
	 */
	class CancelButtonAction implements ActionListener {

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent
		 * )
		 */
		@Override
		public void actionPerformed(ActionEvent actionEvent) {
			
			// Puts the default cursor
			AcideDataViewReplaceWindow.getInstance().setCursor(
					Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			
			AcideMainWindow.getInstance().setCursor(
					Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));

			// Closes the window
			AcideDataViewReplaceWindow.getInstance().dispose();
		}
	}

	/**
	 * ACIDE - A Configurable IDE replace window escape key action.
	 * 
	 * @version 0.11
	 * @see AbstractAction
	 */
	class EscapeKeyAction extends AbstractAction {

		/**
		 * Escape key action serial version UID.
		 */
		private static final long serialVersionUID = 1L;

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent
		 * )
		 */
		@Override
		public void actionPerformed(ActionEvent actionEvent) {
			
			// Puts the default cursor
			AcideDataViewReplaceWindow.getInstance().setCursor(
					Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			
			AcideMainWindow.getInstance().setCursor(
					Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));

			// Closes the window
			AcideDataViewReplaceWindow.getInstance().dispose();
		}
	}
	
	@Override
	public void propertyChange(PropertyChangeEvent evt) {
	
	}
}
