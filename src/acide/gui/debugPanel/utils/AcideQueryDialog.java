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

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.LinkedList;
import java.util.Locale;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JRootPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.KeyStroke;

import acide.gui.debugPanel.debugCanvas.tasks.AcideDebugCanvasParseTask;
import acide.gui.debugPanel.traceDatalogPanel.AcideTraceDatalogPanel;
import acide.gui.graphCanvas.tasks.AcideGraphCanvasParseTask;
import acide.gui.graphUtils.DirectedWeightedGraph;
import acide.gui.mainWindow.AcideMainWindow;
import acide.language.AcideLanguageManager;
import acide.process.console.DesDatabaseManager;

/**
 * 
 * ACIDE - A Configurable IDE
 * 
 * @version 0.15
 * @see JDialog
 */
public class AcideQueryDialog extends JDialog {

	/**
	 * ACIDE - A Configurable IDE query dialog class serial version UID.
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * ACIDE - A Configurable IDE query dialog class x position.
	 */
	private static Integer _posX = null;
	/**
	 * ACIDE - A Configurable IDE query dialog class y position.
	 */
	private static Integer _posY = null;
	/**
	 * ACIDE - A Configurable IDE query dialog class width.
	 */
	private static Integer _width = null;
	/**
	 * ACIDE - A Configurable IDE query dialog class _height.
	 */
	private static Integer _height = null;

	/**
	 * ACIDE - A Configurable IDE query dialog class query type datalog.
	 */
	public static final String QUERY_TYPE_DATALOG = "datalog";
	/**
	 * ACIDE - A Configurable IDE query dialog class query type sql.
	 */
	public static final String QUERY_TYPE_SQL = "SQL";

	/**
	 * ACIDE - A Configurable IDE query dialog image icon.
	 */
	private static final ImageIcon ICON = new ImageIcon(
			"./resources/images/icon.png");

	/**
	 * ACIDE - A Configurable IDE query dialog image class query text.
	 */
	private static String _queryText = "";

	/**
	 * ACIDE - A Configurable IDE query dialog text area.
	 */
	private JTextField _textArea;

	/**
	 * ACIDE - A Configurable IDE query dialog accept button.
	 */
	private JButton _acceptButton;
	/**
	 * ACIDE - A Configurable IDE query dialog cancel button.
	 */
	private JButton _cancelButton;
	/**
	 * ACIDE - A Configurable IDE query dialog button panel.
	 */
	private JPanel _buttonPanel;
	/**
	 * ACIDE - A Configurable IDE query dialog panel.
	 */
	private JPanel _panel;
	/**
	 * ACIDE - A Configurable IDE query dialog scroll pane.
	 */
	private JScrollPane _scrollPane;
	/**
	 * ACIDE - A Configurable IDE query dialog query type.
	 */
	private String _queryType;
	/**
	 * ACIDE - A Configurable IDE query dialog path parse task.
	 */
	private AcideDebugCanvasParseTask pathParseTask;

	/**
	 * Creates a new ACIDE - A Configurable IDE query dialog.
	 */
	public AcideQueryDialog(String title, String queryType) {

		AcideMainWindow.getInstance().setAlwaysOnTop(false);

		AcideMainWindow.getInstance().setEnabled(false);
		// Sets the title
		this.setTitle(title);
		// sets the type of the query
		this.setQueryType(queryType);
		// Sets the icon
		this.setIconImage(ICON.getImage());
		// builds the components
		buildComponents();

		setLookAndFeel();
		// adds the listeners of the components
		setListeners();
		// updates the visibility
		setVisible(true);
		
		setAlwaysOnTop(true);
		
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);

		this.setLocationRelativeTo(AcideMainWindow.getInstance());
		
		
				

	}

	/**
	 * Builds the components of the ACIDE - A Configurable IDE query dialog.
	 */
	private void buildComponents() {
		// Builds the text area and its properties
		_textArea = new JTextField();

		_textArea.setEditable(true);

		_textArea.setFont(new Font("Monospaced", Font.PLAIN, 12));

		//_textArea.setRows(10);

		_textArea.setColumns(50);

		_textArea.setText(_queryText);

		_scrollPane = new JScrollPane(_textArea);

		if (_height != null && _width != null) {
			_scrollPane.setPreferredSize(new Dimension(_width, _height));
		}

		// Builds the accept button
		_acceptButton = new JButton(AcideLanguageManager.getInstance()
				.getLabels().getString("s154"));
		_acceptButton.setFocusable(true);
		//_acceptButton.requestFocus();
		if (AcideLanguageManager.getInstance().getCurrentLocale()
				.equals(new Locale("en", "EN"))) {
			_acceptButton.setMnemonic('O');
		} else {
			_acceptButton.setMnemonic('A');
		}

		// Builds the cancel button

		_cancelButton = new JButton(AcideLanguageManager.getInstance()
				.getLabels().getString("s369"));

		_cancelButton.setMnemonic('C');

		_buttonPanel = new JPanel();

		_buttonPanel.add(_acceptButton);
		_buttonPanel.add(_cancelButton);

		// /Builds the panels
		_panel = new JPanel();

		_panel.setLayout(new BorderLayout());

		this.getContentPane().setLayout(new BorderLayout());

	}

	/**
	 * Sets the appearance of the ACIDE - A Configurable IDE query dialog.
	 */
	private void setLookAndFeel() {
		// sets the position of the components
		_panel.add(_scrollPane, BorderLayout.CENTER);
		_panel.add(_buttonPanel, BorderLayout.SOUTH);
		
		this.getContentPane().add(_panel, BorderLayout.CENTER);
		
		this.setLocationRelativeTo(null);
		this.setResizable(true);
		// pack the window to matches with the size of the components
		this.pack();
		// sets the position of the window
		if (_posX != null && _posY != null)
			this.setLocation(_posX, _posY);

		setModal(true);
	}

	private void acceptFunction(){

		// get the query from the text area
		String query = _textArea.getText();//.replaceAll("\n", " ");
		// update the query text
		_queryText = query;

		if (_queryType.equals(QUERY_TYPE_DATALOG)) {
			AcideMainWindow.getInstance().getDebugPanel()
					.getTraceDatalogPanel().setQuery(query);
			// Gets the result of the /tapi /trace_datalog command
			LinkedList<String> l = DesDatabaseManager.getInstance()
					.executeCommand("/tapi /trace_datalog " + query);
			String result = "";
			for (String s : l) {
				result += s + "\n";
			}
			// Parses the result and generates the path graph
			pathParseTask = new AcideDebugCanvasParseTask(result,
					AcideGraphCanvasParseTask.PARSE_TAPI_PDG,
					AcideMainWindow.getInstance().getDebugPanel()
							.getTraceDatalogPanel().getCanvas(),
					AcideDebugCanvasParseTask.DESTINY_PATH, query,true);
			final Thread t = new Thread(pathParseTask);
			t.start();
			new Thread(new Runnable() {
				/*
				 * (non-Javadoc)
				 * 
				 * @see java.lang.Runnable#run()
				 */
				@Override
				public void run() {
					try {
						//enable the refresh button
						AcideTraceDatalogPanel.refreshDatalog.setEnabled(true);
						t.join();
						if (pathParseTask.isSuccess()) {
							AcideTraceDatalogPanel.refreshDatalog.setEnabled(true);
							// Gets the result of the /tapi /pdg command
							LinkedList<String> l = DesDatabaseManager
									.getInstance()
									.executeCommand(
											"/tapi /pdg "
													+ _queryText
															.substring(
																	0,
																	_queryText
																			.lastIndexOf("(") > 0 ? _queryText
																			.lastIndexOf("(")
																			: _queryText
																					.length()));
							String result = "";
							for (String s : l) {
								result += s + "\n";
							}
							// Parses the result and generates the graph
							new Thread(
									new AcideDebugCanvasParseTask(
											result,
											AcideGraphCanvasParseTask.PARSE_TAPI_PDG,
											AcideMainWindow
													.getInstance()
													.getDebugPanel()
													.getTraceDatalogPanel()
													.getCanvas(),
											AcideDebugCanvasParseTask.DESTINY_MAIN,
											_queryText,true)).start();
						} else {
							AcideMainWindow
									.getInstance()
									.getDebugPanel()
									.getTraceDatalogPanel()
									.getCanvas()
									.set_graph(
											new DirectedWeightedGraph());
						}
					} catch (InterruptedException e) {
						
						e.printStackTrace();
					}
				}
			}).start();
			// Checks if the show rule property is active
			if (AcideMainWindow.getInstance().getDebugPanel()
					.getTraceDatalogPanel().getShowRulesMenuItem()
					.isSelected())
				new Thread(new Runnable() {
					/*
					 * (non-Javadoc)
					 * 
					 * @see java.lang.Runnable#run()
					 */
					@Override
					public void run() {
						try {
							t.join();
							if (pathParseTask.isSuccess()) {
								AcideTraceDatalogPanel.refreshDatalog.setEnabled(true);
								// Gets the root node of the canvas
								String selected = AcideMainWindow
										.getInstance().getDebugPanel()
										.getTraceDatalogPanel()
										.getCanvas().getRootNode()
										.getLabel();
								// Unhihglights the previous highlights
								AcideMainWindow.getInstance()
										.getDebugPanel()
										.getTraceDatalogPanel()
										.getHighLighter().unHighLight();
								// Highlights the root node
								AcideMainWindow.getInstance()
										.getDebugPanel()
										.getTraceDatalogPanel()
										.getHighLighter()
										.highLight(selected);
							}

						} catch (Exception e) {
							e.printStackTrace();
						}

					}
				}).start();

		}
		//closes the window
		closeWindow();
	}
	
	/**
	 * Sets the listeners of the ACIDE - A Configurable IDE query dialog.
	 */
	private void setListeners() {
		// adds the listener to the scroll panel
		
		_textArea.addKeyListener(new KeyListener() {
			
			
			public void keyPressed(KeyEvent arg0) {
				if (arg0.getKeyCode() == KeyEvent.VK_ENTER){
					acceptFunction();
						
				} else if (arg0.getKeyCode() == KeyEvent.VK_ESCAPE){
					closeWindow();
				} else dispatchEvent(arg0); 
				
			}

			public void keyTyped(KeyEvent e) {
				dispatchEvent(e);				
			}
			
			public void keyReleased(KeyEvent e) {
				dispatchEvent(e);
			}});
		
		
		_scrollPane.addComponentListener(new ComponentAdapter() {
			public void componentResized(ComponentEvent ev) {
				// updates the height and the width of the window
				_height = new Integer(ev.getComponent().getSize().height);
				_width = new Integer(ev.getComponent().getSize().width);
			}
		});
		// adds the listener to the accept button
		_acceptButton.addActionListener(new ActionListener() {
			/*
			 * (non-Javadoc)
			 * 
			 * @see
			 * java.awt.event.ActionListener#actionPerformed(java.awt.event.
			 * ActionEvent)
			 */
			@Override
			public void actionPerformed(ActionEvent e) {
				acceptFunction();
				
			}
		});

		_acceptButton.setFocusable(true);
		
		_cancelButton.addActionListener(new ActionListener() {
			/*
			 * (non-Javadoc)
			 * 
			 * @see
			 * java.awt.event.ActionListener#actionPerformed(java.awt.event.
			 * ActionEvent)
			 */
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// closes the window
				closeWindow();

			}
		});

		// closes the windows when the escape key is pressed
		JRootPane root = this.getRootPane();
		root.registerKeyboardAction(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				closeWindow();

			}
		}, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0),
				JComponent.WHEN_IN_FOCUSED_WINDOW);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.JDialog#setDefaultCloseOperation(int)
	 */
	@Override
	public void setDefaultCloseOperation(int option) {
		if (option == DISPOSE_ON_CLOSE)
			closeWindow();
	}

	/**
	 * Returns the ACIDE - A Configurable IDE debug panel query dialog query
	 * type.
	 * 
	 * @return the ACIDE - A Configurable IDE debug panel trace query dialog
	 *         query type.
	 */
	public String getQueryType() {
		return _queryType;
	}

	/**
	 * Sets a new value to the ACIDE - A Configurable IDE debug panel query
	 * dialog query type.
	 * 
	 * @param queryType
	 *            the new value to set.
	 */
	public void setQueryType(String queryType) {
		this._queryType = queryType;
	}

	private void closeWindow() {
		// enables the main window of the acide program
		AcideMainWindow.getInstance().setEnabled(true);
		// sets the main window of the acide program on top
		AcideMainWindow.getInstance().setAlwaysOnTop(true);
		
		AcideMainWindow.getInstance().setAlwaysOnTop(false);
		// updates the location of the window
		_posX = this.getLocation().x;
		_posY = this.getLocation().y;
		// closes the window
		this.dispose();

	}
}
