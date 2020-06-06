package acide.gui.debugPanel.debugSQLPanel;

import acide.gui.databasePanel.dataView.AcideDataBaseDataViewTable;
import acide.gui.databasePanel.dataView.AcideDatabaseDataView;
import acide.gui.debugPanel.utils.AcideDebugHelper;
import acide.gui.listeners.AcideWindowClosingListener;
import acide.gui.mainWindow.AcideMainWindow;
import acide.process.console.DesDatabaseManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.LinkedList;
import java.util.Vector;

public class AcideDebugSQLDebugWindow extends JFrame {

    private JPanel mainPanel;
    private JPanel buttonPanel;
    private JPanel errorPanel;

    private JLabel infoReicibedLabel;
    private JLabel questionLabel;

    private JButton validButton;
    private JButton nonValidButton;
    private JButton missingTupleButton;
    private JButton wrongTupleButton;
    private JButton abortButton;

    private JButton okButton;
    private JButton showStatsButton;
    private JButton showViewButton;

    private String view;
    private String currentQuestion;

    private JScrollPane viewTable;
    private AcideDataBaseDataViewTable jTable;

    private static AcideDebugSQLDebugWindow instance;

    public static AcideDebugSQLDebugWindow getInstance() {
        if (instance == null) {
            instance = new AcideDebugSQLDebugWindow();
        }
        return instance;
    }


    public AcideDebugSQLDebugWindow() {
        this.setTitle("Debug execution");

        this.setView(AcideDebugHelper.getSelectedViewName());

        // Builds the window components
        buildComponents();

        // Sets the listener for the window components
        setListeners();

        // Adds the components to the window
        addComponents();

        // Sets the window configuration
        setWindowConfiguration();

        addWindowListener(new WindowCloser());


    }

    private void buildComponents() {
        infoReicibedLabel = new JLabel();
        questionLabel = new JLabel();

        validButton = new JButton("valid");
        nonValidButton = new JButton("non valid");
        missingTupleButton = new JButton("missing tuple");
        wrongTupleButton = new JButton("wrong tuple");
        abortButton = new JButton("abort");

        okButton = new JButton("OK");
        showStatsButton = new JButton("show stats");
        showViewButton = new JButton("show view");

        questionLabel.setText("");
        infoReicibedLabel.setText("");

        viewTable = new JScrollPane();

        mainPanel = new JPanel(new GridBagLayout());

        // Creates the button panel
        buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

        buttonPanel.add(validButton);
        buttonPanel.add(nonValidButton);
        buttonPanel.add(missingTupleButton);
        buttonPanel.add(wrongTupleButton);
        buttonPanel.add(abortButton);

        // Creates the error panel
        errorPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

        errorPanel.add(okButton);
        errorPanel.add(showStatsButton);
        errorPanel.add(showViewButton);
    }


    private void setListeners() {
        validButton.addActionListener(new ValidNodeAction());
        nonValidButton.addActionListener(new NonValidNodeAction());
        missingTupleButton.addActionListener(new MissingTupleAction());
        wrongTupleButton.addActionListener(new WrongTupleAction());
        abortButton.addActionListener(new AbortAction());

        okButton.addActionListener(new CloseAction());
        showStatsButton.addActionListener(new StatsAction());
        showViewButton.addActionListener(new ShowViewAction());
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
        constraints.ipadx = 100;
        constraints.ipady = 10;

        // Adds the info label to the main panel
        mainPanel.add(infoReicibedLabel, constraints);

        constraints.gridx = 0;
        constraints.gridy = 2;
        constraints.ipadx = 100;
        constraints.ipady = 10;

        // Adds the name label to the main panel
        mainPanel.add(questionLabel, constraints);

        constraints.gridx = 0;
        constraints.gridy = 0;

        // Adds the main panel to the window
        add(mainPanel, constraints);

        constraints.insets = new Insets(0, 0, 0, 0);
        constraints.fill = GridBagConstraints.BOTH;
        constraints.anchor = GridBagConstraints.CENTER;
        constraints.gridx = 0;
        constraints.gridy = 1;

        // Adds the button panel to the window
        add(buttonPanel, constraints);

        constraints.insets = new Insets(0, 0, 0, 0);
        constraints.fill = GridBagConstraints.BOTH;
        constraints.anchor = GridBagConstraints.CENTER;
        constraints.gridx = 0;
        constraints.gridy = 2;

        // Adds the button panel to the window
        add(errorPanel, constraints);


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

    }

    /**
     * Closes the ACIDE - A Configurable IDE debug configuration window.
     */

    public void closeWindow() {
        // Enables the main window again
        AcideMainWindow.getInstance().setEnabled(true);

        //enable start debug button
        AcideDebugSQLPanel.startDebug.setEnabled(true);

        AcideMainWindow.getInstance().setAlwaysOnTop(true);
        AcideMainWindow.getInstance().setAlwaysOnTop(false);

        AcideMainWindow.getInstance().getDebugPanel().getDebugSQLPanel().setDebuging(false);

        this.setVisible(false);
    }

    public void showWindow() {

        questionLabel.setVisible(true);
        buttonPanel.setVisible(true);
        viewTable.setVisible(true);
        errorPanel.setVisible(false);

        setWindowConfiguration();
        setAlwaysOnTop(true);
        setAlwaysOnTop(false);
        // Displays the window
        setVisible(true);
    }

    public void activateAbortButton(){
        abortButton.setEnabled(true);
    }

    public void deactivateAbortButton(){
        abortButton.setEnabled(false);
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

        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            // Closes the window
            closeWindow();
        }
    }

    class ValidNodeAction extends AbstractAction{

        /**
         * Escape key action serial version UID.
         */
        private static final long serialVersionUID = 1L;

        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            AcideDebugHelper.performDebug("valid");
        }
    }

    class NonValidNodeAction extends AbstractAction{

        /**
         * Escape key action serial version UID.
         */
        private static final long serialVersionUID = 1L;

        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            AcideDebugHelper.performDebug("nonvalid");
        }
    }



    class MissingTupleAction extends AbstractAction{

        /**
         * Escape key action serial version UID.
         */
        private static final long serialVersionUID = 1L;


        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            String tuple = JOptionPane.showInputDialog("Enter missing data");
            AcideDebugHelper.performDebug("missing(" +
                    AcideDebugSQLDebugWindow.getInstance().getView() + "('" + tuple +"'))");
        }
    }

    class WrongTupleAction extends AbstractAction{

        /**
         * Escape key action serial version UID.
         */
        private static final long serialVersionUID = 1L;

        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            Vector<?> data = (Vector<?>) ((AcideDatabaseDataView.MyTableModel) jTable.getModel()).getDataVector().get(jTable.getSelectedRow());
            String tuple = "";
            for(Object value : data){
                if(value != null){
                    tuple += "'" + value.toString() + "',";
                }
            }
            // Remove last comma ','
            if(!tuple.isEmpty()){
                tuple = tuple.substring(0, tuple.length() - 1);
            }
            AcideDebugHelper.performDebug("wrong(" +
                    AcideDebugSQLDebugWindow.getInstance().getView() + "(" + tuple +"))");
        }
    }

    class AbortAction extends AbstractAction{

        /**
         * Escape key action serial version UID.
         */
        private static final long serialVersionUID = 1L;

        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            AcideDebugHelper.performDebug("abort");
            closeWindow();
        }
    }

    class StatsAction extends AbstractAction{

        /**
         * Escape key action serial version UID.
         */
        private static final long serialVersionUID = 1L;

        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            LinkedList<String> info = DesDatabaseManager.getInstance().getDebugStats();
            String label = "<html>";
            for(int i = 0; i < info.size(); i++){
                label += info.get(i) + " ";
                i++;
                label += info.get(i) + "<br>";
            }
            label += "</html>";
            JOptionPane.showMessageDialog(new Frame(), new JLabel(label));
        }
    }

    class ShowViewAction extends AbstractAction{

        /**
         * Escape key action serial version UID.
         */
        private static final long serialVersionUID = 1L;


        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            AcideDebugHelper.showView(AcideDebugSQLDebugWindow.getInstance().getView());
        }
    }

    class WindowCloser extends WindowAdapter{
        @Override
        public void windowClosing(WindowEvent event){
            closeWindow();
        }
    }

    public void setInfo(String info){
        infoReicibedLabel.setText(info);
        setWindowConfiguration();
    }

    public void setQuestion(String question){
        questionLabel.setText(question);
        setWindowConfiguration();
    }

    public void stopDepug(String view){
        String info = "Error in view '" + view + "' click below to edit that view";
        this.setView(view);
        setInfo(info);
        deactivateAbortButton();
        viewTable.setVisible(false);
        questionLabel.setVisible(false);
        buttonPanel.setVisible(false);
        errorPanel.setVisible(true);
        setWindowConfiguration();

        AcideDebugSQLPanel.startDebug.setEnabled(true);
        AcideMainWindow.getInstance().getDebugPanel().getDebugSQLPanel().setDebuging(false);
        this.setVisible(true);

    }

    public void putView(String view, JScrollPane viewTable){
        try {
            if(this.view != null)
                mainPanel.remove(this.viewTable);
        }catch (Exception e){

        }

        setView(view);
        setViewTable(viewTable);
        setJTable(jTable);

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.BOTH;
        constraints.insets = new Insets(5, 5, 5, 5);

        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.ipadx = 250;
        constraints.ipady = 200;

        // Adds the view viewTable to the main panel
        mainPanel.add(this.viewTable, constraints);
    }

    public void setViewTable(JScrollPane viewTable){
        this.viewTable = viewTable;
    }

    public String getView() {
        return view;
    }

    public void setView(String view) {
        this.view = view;
    }

    public void setJTable(AcideDataBaseDataViewTable jTable){
        this.jTable = jTable;
    }

    public String getCurrentQuestion() {
        return currentQuestion;
    }

    public void setCurrentQuestion(String currentQuestion) {
        this.currentQuestion = currentQuestion;
    }


    public JScrollPane getViewTable() {
        return viewTable;
    }

}
