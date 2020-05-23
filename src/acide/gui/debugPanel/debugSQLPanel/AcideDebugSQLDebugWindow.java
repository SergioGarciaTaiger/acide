package acide.gui.debugPanel.debugSQLPanel;

import acide.gui.databasePanel.dataView.AcideDataBaseDataViewTable;
import acide.gui.databasePanel.dataView.AcideDatabaseDataView;
import acide.gui.debugPanel.utils.AcideDebugHelper;
import acide.gui.listeners.AcideWindowClosingListener;
import acide.gui.mainWindow.AcideMainWindow;
import acide.process.console.DesDatabaseManager;
import com.sun.javaws.util.JfxHelper;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.net.URL;
import java.util.HashMap;
import java.util.LinkedList;

public class AcideDebugSQLDebugWindow extends JFrame {

    private JPanel mainPanel;
    private JPanel buttonPanel;

    private JLabel infoReicibedLabel;
    private JLabel questionLabel;

    private JButton validButton;
    private JButton nonValidButton;
    private JButton missingButton;
    private JButton wrongButton;
    private JButton missingTupleButton;
    private JButton wrongTupleButton;

    private String view;
    private String gifLoadingURL = "http://docs.oracle.com/javase/tutorial/uiswing/examples/misc/SplashDemoProject/src/misc/images/splash.gif";
    private String currentQuestion;

    private JScrollPane viewTable;

    private boolean debuging;

    private static AcideDebugSQLDebugWindow instance;

    public static AcideDebugSQLDebugWindow getInstance() {
        if (instance == null) {
            instance = new AcideDebugSQLDebugWindow();
        }
        return instance;
    }


    public AcideDebugSQLDebugWindow() {
        this.setTitle("Debug execution");

        debuging = false;

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
        missingButton = new JButton("missing");
        wrongButton = new JButton("wrong");
        missingTupleButton = new JButton("missing tuple");
        wrongTupleButton = new JButton("wrong tuple");

        questionLabel.setText("");
        infoReicibedLabel.setText("");

        viewTable = new JScrollPane();

        mainPanel = new JPanel(new GridBagLayout());

        // Creates the button panel
        buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

        buttonPanel.add(validButton);
        buttonPanel.add(nonValidButton);
        buttonPanel.add(missingButton);
        buttonPanel.add(wrongButton);
        buttonPanel.add(missingTupleButton);
        buttonPanel.add(wrongTupleButton);

    }


    private void setListeners() {
        validButton.addActionListener(new ValidNodeAction());
        nonValidButton.addActionListener(new NonValidNodeAction());
        missingButton.addActionListener(new MissingNodeAction());
        wrongButton.addActionListener(new WrongNodeAction());
        missingTupleButton.addActionListener(new MissingTupleAction());
        wrongTupleButton.addActionListener(new WrongTupleAction());
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
        DesDatabaseManager.getInstance()
                .executeCommand("a");

        // Enables the main window again
        AcideMainWindow.getInstance().setEnabled(true);

        // Closes the window
        setVisible(false);

        //enable start debug button
        AcideDebugSQLPanel.startDebug.setEnabled(true);

        // Brings the main window to the front
        AcideMainWindow.getInstance().setAlwaysOnTop(true);

        // But not permanently
        AcideMainWindow.getInstance().setAlwaysOnTop(false);
    }

    public void showWindow() {

        questionLabel.setVisible(true);
        buttonPanel.setVisible(true);
        viewTable.setVisible(true);

        setWindowConfiguration();

        // Displays the window
        setVisible(true);
    }

    public boolean isDebuging() {
        return debuging;
    }

    public void setDebuging(boolean debuging) {
        this.debuging =  debuging;
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

    class MissingNodeAction extends AbstractAction{

        /**
         * Escape key action serial version UID.
         */
        private static final long serialVersionUID = 1L;


        @Override
        public void actionPerformed(ActionEvent actionEvent) {

        }
    }

    class WrongNodeAction extends AbstractAction{

        /**
         * Escape key action serial version UID.
         */
        private static final long serialVersionUID = 1L;

        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            AcideDebugHelper.performDebug("erroneous");
        }
    }

    class MissingTupleAction extends AbstractAction{

        /**
         * Escape key action serial version UID.
         */
        private static final long serialVersionUID = 1L;


        @Override
        public void actionPerformed(ActionEvent actionEvent) {

        }
    }

    class WrongTupleAction extends AbstractAction{

        /**
         * Escape key action serial version UID.
         */
        private static final long serialVersionUID = 1L;

        @Override
        public void actionPerformed(ActionEvent actionEvent) {

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

    public void stopDepug(LinkedList<String> result){
        String info = "<html>";
        for (int i = 0; i < result.size() - 1; i++) {
            info += result.get(i) + "<br>";
        }
        info += "</html>";
        setInfo(info);
        viewTable.setVisible(false);
        questionLabel.setVisible(false);
        buttonPanel.setVisible(false);
        setWindowConfiguration();

        AcideDebugSQLPanel.startDebug.setEnabled(true);
        this.setDebuging(false);
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



    public String getCurrentQuestion() {
        return currentQuestion;
    }

    public void setCurrentQuestion(String currentQuestion) {
        this.currentQuestion = currentQuestion;
    }

}
