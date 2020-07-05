package acide.gui.debugPanel.debugSQLPanel;

import acide.gui.databasePanel.dataView.AcideDataBaseDataViewTable;
import acide.gui.databasePanel.dataView.AcideDatabaseDataView;
import acide.gui.databasePanel.utils.AcideEnterTextWindow;
import acide.gui.debugPanel.utils.AcideDebugHelper;
import acide.gui.listeners.AcideWindowClosingListener;
import acide.gui.mainWindow.AcideMainWindow;
import acide.language.AcideLanguageManager;
import acide.process.console.AcideDatabaseManager;
import acide.process.console.DesDatabaseManager;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;
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
    private JLabel errorsLabel;

    private JButton validButton;
    private JButton nonValidButton;
    private JButton missingTupleButton;
    private JButton wrongTupleButton;
    private JButton abortButton;

    private JButton okButton;
    private JButton showStatsButton;
    private JButton editTableButton;
    private JButton editViewButton;

    private String view;
    private String currentQuestion;
    private String questionType = "all";
    private LinkedList<String> errors = new LinkedList<>();

    private JScrollPane viewTable;
    private AcideDataBaseDataViewTable jTable;

    private static final ImageIcon ICON = new ImageIcon(
            "./resources/images/icon.png");

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

        this.setIconImage(ICON.getImage());

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
        errorsLabel = new JLabel();

        validButton = new JButton(AcideLanguageManager.getInstance()
                .getLabels().getString("s2348"));
        nonValidButton = new JButton(AcideLanguageManager.getInstance()
                .getLabels().getString("s2349"));
        missingTupleButton = new JButton(AcideLanguageManager.getInstance()
                .getLabels().getString("s2350"));
        wrongTupleButton = new JButton(AcideLanguageManager.getInstance()
                .getLabels().getString("s2351"));
        abortButton = new JButton(AcideLanguageManager.getInstance()
                .getLabels().getString("s2352"));

        okButton = new JButton(AcideLanguageManager.getInstance()
                .getLabels().getString("s2353"));
        showStatsButton = new JButton(AcideLanguageManager.getInstance()
                .getLabels().getString("s2354"));
        editTableButton = new JButton(AcideLanguageManager.getInstance()
                .getLabels().getString("s2355"));
        editViewButton = new JButton(AcideLanguageManager.getInstance()
                .getLabels().getString("s2360"));

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
        errorPanel.add(editTableButton);
        errorPanel.add(editViewButton);
    }

    private void setListeners() {
        validButton.addActionListener(new ValidNodeAction());
        nonValidButton.addActionListener(new NonValidNodeAction());
        missingTupleButton.addActionListener(new MissingTupleAction());
        wrongTupleButton.addActionListener(new WrongTupleAction());
        abortButton.addActionListener(new AbortAction());

        okButton.addActionListener(new CloseAction());
        showStatsButton.addActionListener(new StatsAction());
        editTableButton.addActionListener(new EditTableAction());
        editViewButton.addActionListener(new EditViewAction());
    }

    private void addComponents() {
        // Sets the layout
        setLayout(new GridBagLayout());

        // Adds the components to the window with the layout
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.BOTH;
        constraints.weightx = 1.0;
        constraints.weighty = 1.0;
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
        constraints.gridy = 3;
        constraints.ipadx = 100;
        constraints.ipady = 10;

        // Adds the name label to the main panel
        mainPanel.add(errorsLabel, constraints);

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

        checkQuestionType();

        if(AcideDebugHelper.isRootView(this.view))
            validButton.setVisible(false);
        else
            validButton.setVisible(true);

        // Debug execution
        this.setTitle(AcideLanguageManager.getInstance()
                .getLabels().getString("s2356"));

        questionLabel.setVisible(true);
        buttonPanel.setVisible(true);
        viewTable.setVisible(true);
        errorPanel.setVisible(false);

        setWindowConfiguration();
        setAlwaysOnTop(true);
        setAlwaysOnTop(false);
        Cursor.getDefaultCursor();
        // Displays the window
        setVisible(true);
        setResizable(true);
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
            if(AcideMainWindow.getInstance().getDebugPanel().getDebugSQLPanel().isDebuging())
                SwingUtilities.invokeLater(() -> AcideDebugSQLDebugWindow.getInstance().showWindow());
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
            if(AcideMainWindow.getInstance().getDebugPanel().getDebugSQLPanel().isDebuging())
                SwingUtilities.invokeLater(() -> AcideDebugSQLDebugWindow.getInstance().showWindow());
        }
    }

    class MissingTupleAction extends AbstractAction{

        /**
         * Escape key action serial version UID.
         */
        private static final long serialVersionUID = 1L;


        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            addError(AcideLanguageManager.getInstance()
                    .getLabels().getString("s2363") + " " + AcideDebugSQLDebugWindow.getInstance().getView() + " " +
                    AcideLanguageManager.getInstance()
                            .getLabels().getString("s2364") + " (" + AcideDebugHelper.getDataFromSelectedTuple(jTable) + ")");
            AcideDebugHelper.performDebug("missing(" +
                AcideDebugSQLDebugWindow.getInstance().getView() + "(" + AcideDebugHelper.getDataFromSelectedTuple(jTable) +"))");
            if(AcideMainWindow.getInstance().getDebugPanel().getDebugSQLPanel().isDebuging())
                SwingUtilities.invokeLater(() -> AcideDebugSQLDebugWindow.getInstance().showWindow());
        }
    }

    class WrongTupleAction extends AbstractAction{

        /**
         * Escape key action serial version UID.
         */
        private static final long serialVersionUID = 1L;

        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            addError(AcideLanguageManager.getInstance()
                    .getLabels().getString("s2361") + " " + AcideDebugSQLDebugWindow.getInstance().getView() + " " +
                    AcideLanguageManager.getInstance()
                            .getLabels().getString("s2362") + " (" + AcideDebugHelper.getDataFromSelectedTuple(jTable) + ")");
            AcideDebugHelper.performDebug("wrong(" +
                    AcideDebugSQLDebugWindow.getInstance().getView() + "(" + AcideDebugHelper.getDataFromSelectedTuple(jTable) +"))");
            if(AcideMainWindow.getInstance().getDebugPanel().getDebugSQLPanel().isDebuging())
                SwingUtilities.invokeLater(() -> AcideDebugSQLDebugWindow.getInstance().showWindow());
        }
    }

    class AbortAction extends AbstractAction{

        /**
         * Escape key action serial version UID.
         */
        private static final long serialVersionUID = 1L;

        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            if(AcideMainWindow.getInstance().getDebugPanel().getDebugSQLPanel().isDebuging())
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
            if(info.size() > 0 && info.get(0).equals("$error")){
                JOptionPane.showMessageDialog(null, AcideLanguageManager.getInstance()
                        .getLabels().getString("s2357"));
            }else {
                String[] columnNames = {AcideLanguageManager.getInstance()
                        .getLabels().getString("s2358"), AcideLanguageManager.getInstance()
                        .getLabels().getString("s2359")};
                DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);
                for (int i = 0; i < info.size(); i++) {
                    Object[] row = {info.get(i),info.get(i+1)};
                    tableModel.addRow(row);
                    i++;
                }
                JTable table = new JTable(tableModel);
                table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
                final TableColumnModel columnModel = table.getColumnModel();
                for (int column = 0; column < table.getColumnCount(); column++) {
                    int width = 15; // Min width
                    for (int row = 0; row < table.getRowCount(); row++) {
                        TableCellRenderer renderer = table.getCellRenderer(row, column);
                        Component comp = table.prepareRenderer(renderer, row, column);
                        width = Math.max(comp.getPreferredSize().width +1 , width);
                    }
                    if(width > 300)
                        width=300;
                    columnModel.getColumn(column).setPreferredWidth(width);
                }

                JOptionPane.showMessageDialog(new Frame(), table, AcideLanguageManager.getInstance()
                        .getLabels().getString("s2343"), JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }

    class EditViewAction extends AbstractAction{

        /**
         * Escape key action serial version UID.
         */
        private static final long serialVersionUID = 1L;


        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            String text = AcideDatabaseManager.getInstance().getSQLText("$des", view);
            AcideEnterTextWindow a = new AcideEnterTextWindow(text, AcideLanguageManager.getInstance().getLabels().getString("s2036"), true);
            SwingUtilities.invokeLater(() -> stopDepug(view));
        }
    }

    class EditTableAction extends AbstractAction{

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
            if(AcideMainWindow.getInstance().getDebugPanel().getDebugSQLPanel().isDebuging())
                AcideDebugHelper.performDebug("abort");
            closeWindow();
        }
    }

    public void setInfo(String info){
        String text = "<html>";
        text += info;
        text += "</html>";
        infoReicibedLabel.setText(text);
        setWindowConfiguration();
    }

    public void setQuestion(String question){
        questionLabel.setText(question);
        setWindowConfiguration();
    }

    public void stopDepug(String view){
        String type;
        if(DesDatabaseManager.getInstance().isTable("$des", view)){
            editTableButton.setVisible(true);
            editViewButton.setVisible(false);
            type = AcideLanguageManager.getInstance().getLabels()
                    .getString("s2371");
        }else{
            editTableButton.setVisible(false);
            editViewButton.setVisible(true);
            type = AcideLanguageManager.getInstance().getLabels()
                    .getString("s2370");
        }
        String info = AcideLanguageManager.getInstance().getLabels()
                .getString("s2345") + " " + type + " '" + view + "' <br>" + AcideLanguageManager.getInstance().getLabels()
                .getString("s2346");
        if(errors.size() > 0){
            info += "<br>" + AcideLanguageManager.getInstance().getLabels()
                    .getString("s2365");
            for(String line: errors){
                info += "<br>" + " " + " " + " - " + line;
            }
        }
        this.setTitle(AcideLanguageManager.getInstance().getLabels()
                .getString("s2344"));
        this.setView(view);
        setInfo(info);
        viewTable.setVisible(false);
        questionLabel.setVisible(false);
        errorsLabel.setVisible(false);
        buttonPanel.setVisible(false);
        errorPanel.setVisible(true);
        setWindowConfiguration();

        AcideDebugSQLPanel.startDebug.setEnabled(true);
        AcideMainWindow.getInstance().getDebugPanel().getDebugSQLPanel().setDebuging(false);
        this.setVisible(true);
    }

    public void  putView(String view, JScrollPane viewTable){
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

        this.viewTable.setPreferredSize(this.jTable.getSize());
        // Adds the view viewTable to the main panel
        mainPanel.add(this.viewTable, constraints);

        this.setMinimumSize(viewTable.getSize());
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

    public void resetErrors(){
        errors = new LinkedList<>();
        errorsLabel.setText("");
        errorsLabel.setVisible(true);
    }

    public void addError(String error){
        errors.add(error);
        String info = "<html>";
        info += "<br>" + AcideLanguageManager.getInstance().getLabels()
                .getString("s2365");
        for(String line: errors){
            info += "<br>" + " " + " " + " - " + line;
        }
        info += "</html>";
        errorsLabel.setText(info);
        setWindowConfiguration();
    }

    public LinkedList<String> getErrors(){
        return errors;
    }

    public void setQuestionType(String type) {
        questionType = type;
    }

    private void checkQuestionType(){
        if(questionType.equals("in")){
            setQuestionIn();
        }else if(questionType.equals("subset")){
            setQuestionSubset();
        }else{
            setQuestionAll();
        }
    }

    private void setQuestionIn(){
        wrongTupleButton.setVisible(false);
        missingTupleButton.setVisible(false);

        validButton.setText(AcideLanguageManager.getInstance()
                .getLabels().getString("s62"));
        nonValidButton.setText(AcideLanguageManager.getInstance()
                .getLabels().getString("s63"));

        questionLabel.setText(AcideLanguageManager.getInstance()
                .getLabels().getString("s2369") + view + "?");

        setTupleFromQuestionIn();
    }

    private void setTupleFromQuestionIn(){
        String tupleContent = currentQuestion;

        // subtract in
        tupleContent = tupleContent.substring(tupleContent.indexOf("(")+1,tupleContent.lastIndexOf(")"));

        // subtract tuple content
        if(tupleContent.contains("("))
            tupleContent = tupleContent.substring(tupleContent.indexOf("(")+1,tupleContent.lastIndexOf(")"));

        Vector<Vector> data = ((AcideDatabaseDataView.MyTableModel) jTable.getModel()).getDataVector();
        for(int i = 0; i < data.size(); i++){
            if(AcideDebugHelper.parseTupleContent(data.get(i)).equals(tupleContent)){
                jTable.changeSelection(i, jTable.getColumnCount(),true, true);
                break;
            }
        }
    }

    private void setQuestionAll(){
        wrongTupleButton.setVisible(true);
        missingTupleButton.setVisible(true);

        validButton.setText(AcideLanguageManager.getInstance()
                .getLabels().getString("s2348"));
        nonValidButton.setText(AcideLanguageManager.getInstance()
                .getLabels().getString("s2349"));
    }

    private void setQuestionSubset(){
        wrongTupleButton.setVisible(true);
        missingTupleButton.setVisible(false);

        validButton.setText(AcideLanguageManager.getInstance()
                .getLabels().getString("s2348"));
        nonValidButton.setText(AcideLanguageManager.getInstance()
                .getLabels().getString("s2349"));
    }
}
