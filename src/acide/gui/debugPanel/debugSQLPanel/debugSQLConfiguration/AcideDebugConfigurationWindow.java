package acide.gui.debugPanel.debugSQLPanel.debugSQLConfiguration;

import acide.files.AcideFileManager;
import acide.files.utils.AcideFileOperation;
import acide.files.utils.AcideFileTarget;
import acide.files.utils.AcideFileType;
import acide.gui.listeners.AcideWindowClosingListener;
import acide.gui.mainWindow.AcideMainWindow;
import acide.language.AcideLanguageManager;

import javax.swing.*;
import javax.swing.JButton;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

public class AcideDebugConfigurationWindow extends JFrame {

    private AcideDebugConfiguration configuration;


    // Labels for debug configuration GUI
    private JLabel debugLabel;
    private JLabel orderLabel;
    private JLabel trust_tablesLabel;
    private JLabel trust_fileLabel;
    private JLabel orable_fileLabel;

    // Radio Buttons
    private JRadioButton debugFullRadio;
    private JRadioButton debugPlainRadio;
    private JRadioButton orderCardinalityRadio;
    private JRadioButton orderTopDownRadio;
    private JRadioButton trust_tablesYesRadio;
    private JRadioButton trust_tablesNoRadio;

    private ButtonGroup debugGroup;
    private ButtonGroup orderGroup;
    private ButtonGroup trust_tablesGroup;

    // TextFields
    private JTextField trust_fileTextField;
    private JTextField oracle_fileTextField;

    // Panels
    private JPanel mainPanel;
    private JPanel buttonPanel;

    // Button
    private JButton browseTrustFileButton;
    private JButton browseOracleFileButton;
    private JButton saveCondigurationButton;
    private JButton defaultConfigurationButton;

    // Instance
    private static AcideDebugConfigurationWindow instance;

    public static AcideDebugConfigurationWindow getInstance(){
        if(instance == null){
            instance = new AcideDebugConfigurationWindow();
        }
        return instance;
    }


    public AcideDebugConfigurationWindow(){
        super(AcideLanguageManager.getInstance()
                .getLabels().getString("s2326"));

        configuration = AcideDebugConfiguration.getInstance();

        // Builds the window components
        buildComponents();

        // Sets the listener for the window components
        setListeners();

        // Adds the components to the window
        addComponents();

        // Sets the window configuration
        setWindowConfiguration();
    }

    private void buildComponents(){
        mainPanel = new JPanel(new GridBagLayout());

        mainPanel.setBorder(BorderFactory.createTitledBorder(null,
                AcideLanguageManager.getInstance().getLabels()
                        .getString("s5"), TitledBorder.LEADING,
                TitledBorder.DEFAULT_POSITION));

        // Builds labels for configuration window
        trust_tablesLabel = new JLabel(AcideLanguageManager.getInstance()
               .getLabels().getString("s2333"));
        debugLabel = new JLabel(AcideLanguageManager.getInstance()
               .getLabels().getString("s2329"));
        orderLabel = new JLabel(AcideLanguageManager.getInstance()
               .getLabels().getString("s2332"));
        trust_fileLabel = new JLabel(AcideLanguageManager.getInstance()
               .getLabels().getString("s2334"));
        orable_fileLabel = new JLabel(AcideLanguageManager.getInstance()
                .getLabels().getString("s2335"));

        // Creates the trust_file text field
        trust_fileTextField = new JTextField("");

        // Creates the oracle_file text field
        oracle_fileTextField = new JTextField("");

        // Builds action buttons for configuration window
        browseTrustFileButton = new JButton(AcideLanguageManager.getInstance()
                .getLabels().getString("s948"));
        browseOracleFileButton = new JButton(AcideLanguageManager.getInstance()
                .getLabels().getString("s948"));
        saveCondigurationButton = new JButton(AcideLanguageManager.getInstance()
                .getLabels().getString("s2336"));
        defaultConfigurationButton = new JButton(AcideLanguageManager.getInstance()
                .getLabels().getString("s2337"));

        // Creates the button panel
        buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));

        buttonPanel.add(defaultConfigurationButton);
        buttonPanel.add(saveCondigurationButton);

        // Builds radio buttons and groups them
        buildRadioButtons();
    }

    private void buildRadioButtons(){
        debugFullRadio = new JRadioButton();
        debugPlainRadio = new JRadioButton();
        orderCardinalityRadio = new JRadioButton();
        orderTopDownRadio = new JRadioButton();
        trust_tablesYesRadio = new JRadioButton();
        trust_tablesNoRadio = new JRadioButton();

        debugGroup = new ButtonGroup();
        orderGroup = new ButtonGroup();
        trust_tablesGroup = new ButtonGroup();

        // s2327 = Full
        debugFullRadio.setText(AcideLanguageManager.getInstance()
                .getLabels().getString("s2327"));
        // s2328 = Plain
        debugPlainRadio.setText(AcideLanguageManager.getInstance()
                .getLabels().getString("s2328"));
        // s2330 = Cardinality
        orderCardinalityRadio.setText(AcideLanguageManager.getInstance()
                .getLabels().getString("s2330"));
        // s2331 = Topdown
        orderTopDownRadio.setText(AcideLanguageManager.getInstance()
                .getLabels().getString("s2331"));
        // s62 = Yes
        trust_tablesYesRadio.setText(AcideLanguageManager.getInstance()
                .getLabels().getString("s62"));
        // s63 = No
        trust_tablesNoRadio.setText(AcideLanguageManager.getInstance()
                .getLabels().getString("s63"));

        debugGroup.add(debugFullRadio);
        debugGroup.add(debugPlainRadio);
        orderGroup.add(orderCardinalityRadio);
        orderGroup.add(orderTopDownRadio);
        trust_tablesGroup.add(trust_tablesYesRadio);
        trust_tablesGroup.add(trust_tablesNoRadio);
    }

    private void setListeners() {
        browseTrustFileButton.addActionListener(new Trust_fileButtonAction());
        browseOracleFileButton.addActionListener(new Oracle_fileButtonAction());
        defaultConfigurationButton.addActionListener(new SetDefaultAction());
        saveCondigurationButton.addActionListener(new SaveAction());
    }

    private void addComponents() {
        // Sets the layout
        setLayout(new GridBagLayout());

        // Adds the components to the window with the layout
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.BOTH;
        constraints.insets = new Insets(5, 5, 5, 5);
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.ipady = 10;

        // Adds the name label to the main panel
        mainPanel.add(trust_tablesLabel, constraints);

        constraints.gridx = 1;
        constraints.ipadx = 100;
        constraints.ipady = 0;

        // Adds the name text field to the main panel
        mainPanel.add(trust_tablesYesRadio, constraints);


        constraints.gridx = 2;
        constraints.ipadx = 100;
        constraints.ipady = 0;

        // Adds the name text field to the main panel
        mainPanel.add(trust_tablesNoRadio, constraints);

        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.ipady = 10;

        // Adds the name label to the main panel
        mainPanel.add(trust_fileLabel, constraints);

        constraints.gridx = 1;
        constraints.ipadx = 200;
        constraints.ipady = 3;

        // Adds the name text field to the main panel
        mainPanel.add(trust_fileTextField, constraints);

        constraints.gridx = 2;
        constraints.ipadx = 20;
        constraints.ipady = 3;

        // Adds the name text field to the main panel
        mainPanel.add(browseTrustFileButton, constraints);

        constraints.gridx = 0;
        constraints.gridy = 2;
        constraints.ipady = 10;

        // Adds the name label to the main panel
        mainPanel.add(orable_fileLabel, constraints);

        constraints.gridx = 1;
        constraints.ipadx = 200;
        constraints.ipady = 3;

        // Adds the name text field to the main panel
        mainPanel.add(oracle_fileTextField, constraints);

        constraints.gridx = 2;
        constraints.ipadx = 20;
        constraints.ipady = 3;

        // Adds the name text field to the main panel
        mainPanel.add(browseOracleFileButton, constraints);


        constraints.gridx = 0;
        constraints.gridy = 3;
        constraints.ipady = 10;

        // Adds the name label to the main panel
        mainPanel.add(debugLabel, constraints);

        constraints.gridx = 1;
        constraints.ipadx = 100;
        constraints.ipady = 3;

        // Adds the name text field to the main panel
        mainPanel.add(debugFullRadio, constraints);


        constraints.gridx = 2;
        constraints.ipadx = 100;
        constraints.ipady = 3;

        // Adds the name text field to the main panel
        mainPanel.add(debugPlainRadio, constraints);

        constraints.gridx = 0;
        constraints.gridy = 4;
        constraints.ipady = 10;

        // Adds the name label to the main panel
        mainPanel.add(orderLabel, constraints);

        constraints.gridx = 1;
        constraints.ipadx = 100;
        constraints.ipady = 3;

        // Adds the name text field to the main panel
        mainPanel.add(orderCardinalityRadio, constraints);


        constraints.gridx = 2;
        constraints.ipadx = 100;
        constraints.ipady = 3;

        // Adds the name text field to the main panel
        mainPanel.add(orderTopDownRadio, constraints);


        constraints.gridx = 0;
        constraints.gridy = 0;

        // Adds the main panel to the window
        add(mainPanel, constraints);

        constraints.insets = new Insets(0, 0, 0, 0);
        constraints.fill = GridBagConstraints.BOTH;
        constraints.anchor = GridBagConstraints.CENTER;
        constraints.gridx = 0;
        constraints.gridy = 5;

        // Adds the button panel to the window
        add(buttonPanel, constraints);

        // Sets the window closing listener
        addWindowListener(new AcideWindowClosingListener());

        // Puts the escape key in the input map of the window
        getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
                KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0, false),
                "EscapeKey");

        // Puts the escape key in the action map of the window
        getRootPane().getActionMap().put("EscapeKey", new CloseAction());
    }

    private void setWindowConfiguration(){
        // The window is not resizable
        setResizable(false);

        // Packs the window components
        pack();

        // Centers the window
        setLocationRelativeTo(null);

        // Disables the main window
        AcideMainWindow.getInstance().setEnabled(false);
    }

    /**
     * Closes the ACIDE - A Configurable IDE debug configuration window.
     */

    public void closeWindow() {

        // Enables the main window again
        AcideMainWindow.getInstance().setEnabled(true);

        // Closes the window
        setVisible(false);

        // Brings the main window to the front
        AcideMainWindow.getInstance().setAlwaysOnTop(true);

        // But not permanently
        AcideMainWindow.getInstance().setAlwaysOnTop(false);
    }

    /**
     * Displays the ACIDE - A Configurable IDE new project configuration window.
     */
    public void showWindow() {

        if(configuration.getTrust_tables().equals(AcideDebugConfiguration.Trust_tables.YES))
            trust_tablesYesRadio.setSelected(true);
        else
            trust_tablesNoRadio.setSelected(true);

        if(configuration.getDebug().equals(AcideDebugConfiguration.Debug.FULL))
            debugFullRadio.setSelected(true);
        else
            debugPlainRadio.setSelected(true);

        if(configuration.getOrder().equals(AcideDebugConfiguration.Order.CARDINALITY))
            orderCardinalityRadio.setSelected(true);
        else
            orderTopDownRadio.setSelected(true);

        // Empties the name text field
        trust_fileTextField.setText(configuration.getTrust_file());

        // Empties the workspace text field
        oracle_fileTextField.setText(configuration.getOracle_file());

        // Displays the window
        setVisible(true);
    }

    /**
     * ACIDE - A Configurable IDE debug configuration window workspace
     * button action listener.
     *
     * @version 0.11
     * @see ActionListener
     */
    class Trust_fileButtonAction implements ActionListener {

        /*
         * (non-Javadoc)
         *
         * @see
         * java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent
         * )
         */
        @Override
        public void actionPerformed(ActionEvent actionEvent) {

            // Asks for the file path to the user
            String absolutePath = AcideFileManager.getInstance().askForFile(
                    AcideFileOperation.OPEN, AcideFileTarget.FILES,
                    AcideFileType.FILE, "", null);

            if (absolutePath != null) {

                // Sets the workspace path
                configuration.setTrust_file(absolutePath);

                // Updates the workspace text field
                trust_fileTextField.setText(configuration.getTrust_file());

                // Validates the changes in the workspace text field
                trust_fileTextField.validate();

                // Repaints the workspace text field
                trust_fileTextField.repaint();
            }
        }
    }

    /**
     * ACIDE - A Configurable IDE debug configuration window workspace
     * button action listener.
     *
     * @version 0.11
     * @see ActionListener
     */
    class Oracle_fileButtonAction implements ActionListener {

        /*
         * (non-Javadoc)
         *
         * @see
         * java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent
         * )
         */
        @Override
        public void actionPerformed(ActionEvent actionEvent) {

            // Asks for the file path to the user
            String absolutePath = AcideFileManager.getInstance().askForFile(
                    AcideFileOperation.OPEN, AcideFileTarget.FILES,
                    AcideFileType.FILE, "", null);

            if (absolutePath != null) {

                // Sets the oracle path
                configuration.setOracle_file(absolutePath);

                // Updates the oracle text field
                oracle_fileTextField.setText(configuration.getOracle_file());

                // Validates the changes in the oracle text field
                oracle_fileTextField.validate();

                // Repaints the oracle text field
                oracle_fileTextField.repaint();
            }
        }
    }

    /**
     * ACIDE - A Configurable IDE debug configuration window escape key
     * action.
     *
     * @version 0.11
     * @see AbstractAction
     */
    class CloseAction extends AbstractAction {

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
            // Closes the window
            closeWindow();
        }
    }

    class SaveAction extends AbstractAction{
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
            if(trust_tablesYesRadio.isSelected())
                configuration.setTrust_tables(AcideDebugConfiguration.Trust_tables.YES);
            else
                configuration.setTrust_tables(AcideDebugConfiguration.Trust_tables.NO);

            if(trust_fileTextField.getText() != null && !trust_fileTextField.getText().isEmpty())
                configuration.setTrust_file(trust_fileTextField.getText());

            if(oracle_fileTextField.getText() != null && !oracle_fileTextField.getText().isEmpty())
                configuration.setOracle_file(oracle_fileTextField.getText());

            if(debugFullRadio.isSelected())
                configuration.setDebug(AcideDebugConfiguration.Debug.FULL);
            else
                configuration.setDebug(AcideDebugConfiguration.Debug.PLAIN);

            if(orderCardinalityRadio.isSelected())
                configuration.setOrder(AcideDebugConfiguration.Order.CARDINALITY);
            else
                configuration.setOrder(AcideDebugConfiguration.Order.TOPDOWN);

            AcideDebugConfiguration.getInstance().saveConfiguration(configuration);
        }
    }

    class SetDefaultAction extends  AbstractAction{
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
            configuration.setDefaultConfiguration();
            AcideDebugConfigurationWindow.getInstance().showWindow();
        }
    }
}
