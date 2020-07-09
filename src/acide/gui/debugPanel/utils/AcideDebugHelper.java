package acide.gui.debugPanel.utils;

import acide.gui.databasePanel.AcideDataBasePanel;
import acide.gui.databasePanel.dataView.AcideDataBaseDataViewTable;
import acide.gui.databasePanel.dataView.AcideDatabaseDataView;
import acide.gui.databasePanel.dataView.menuBar.editMenu.gui.AcideDataViewReplaceWindow;
import acide.gui.databasePanel.utils.AcideTree;
import acide.gui.debugPanel.debugCanvas.AcideDebugCanvas;
import acide.gui.debugPanel.debugSQLPanel.AcideDebugSQLDebugWindow;
import acide.gui.debugPanel.debugSQLPanel.AcideDebugSQLPanel;
import acide.gui.debugPanel.debugSQLPanel.debugSQLConfiguration.AcideDebugConfiguration;
import acide.gui.graphUtils.Node;
import acide.gui.mainWindow.AcideMainWindow;
import acide.language.AcideLanguageManager;
import acide.process.console.AcideDatabaseManager;
import acide.process.console.DesDatabaseManager;
import sun.awt.resources.awt;

import javax.swing.*;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.*;
import java.util.List;

public class AcideDebugHelper {

    public static void updateCanvasDebug(AcideDebugCanvas canvas){
        AcideDebugPanelHighLighter highLighter = AcideMainWindow
                .getInstance().getDebugPanel().getDebugSQLPanel()
                .getHighLighter();
        canvas.repaint();
        try {
            if (canvas.getSelectedNode() == null)
                canvas.setSelectedNode(canvas.getRootNode());
            String selected = canvas.getSelectedNode().getLabel();
            
            // Updates the highlights
            highLighter.resetLines();
            highLighter.unHighLight();
            if(canvas.getSelectedNode().getNodeColor().equals(Color.GRAY))
                highLighter.highLight(selected);
        }catch (Exception e){
            // TODO
        }

        if (AcideMainWindow.getInstance().getDebugPanel()
                .getDebugSQLPanel().getShowSQLMenuItem().isSelected()) {
            AcideMainWindow
                    .getInstance()
                    .getDebugPanel()
                    .setCursor(
                            Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            // Selects the node on the database panel tree
            String query = canvas.getSelectedNode().getLabel();
            query = query.substring(0, query.lastIndexOf("/"));
            selectSQLTEXT(query);
            AcideMainWindow.getInstance().getDebugPanel()
                    .setCursor(Cursor.getDefaultCursor());
        }
    }

    public static void updateCanvasTrace(AcideDebugCanvas canvas){
        AcideDebugPanelHighLighter highLighter = AcideMainWindow
                .getInstance().getDebugPanel().getTraceSQLPanel()
                .getHighLighter();
        canvas.repaint();
        try {
            String selected = canvas.getSelectedNode().getLabel();

            // Updates the highlights
            highLighter.resetLines();
            highLighter.unHighLight();
            highLighter.highLight(selected);
        }catch (Exception e){
            // TODO
        }
        if (AcideMainWindow.getInstance().getDebugPanel()
                .getTraceSQLPanel().getShowSQLMenuItem().isSelected()) {
            AcideMainWindow
                    .getInstance()
                    .getDebugPanel()
                    .setCursor(
                            Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            // Selects the node on the database panel tree
            String query = canvas.getSelectedNode().getLabel();
            query = query.substring(0, query.lastIndexOf("/"));
            selectSQLTEXT(query);
            AcideMainWindow.getInstance().getDebugPanel()
                    .setCursor(Cursor.getDefaultCursor());
        }
    }

    public static String obtainSQLResult(final Thread t, String result, LinkedList<String> l, String consult){
        t.start();
        // Gets the RDG output for the query
        try{

            l = DesDatabaseManager.getInstance().executeCommand(
                    "/tapi /rdg " + consult.substring(0, consult.lastIndexOf("(")>0?consult.lastIndexOf("("):consult.length()));
        }
        catch(Exception ie){
            ie.printStackTrace();
        }
        StringBuilder resultBuilder = new StringBuilder();
        for (String s : l) {
            resultBuilder.append(s).append("\n");
        }
        result = resultBuilder.toString();
        return result;
    }

    public static AcideTree getRoot(String query){
        if(query.indexOf('/')>=0)
            query = query.substring(0, query.lastIndexOf("/"));
        // gets the tree of the database panel
        AcideTree tree = AcideMainWindow.getInstance()
                .getDataBasePanel().getTree();
        // searches for the root node on the tree
        TreePath path = TreeUtils
                .searchForNode((TreeNode) tree.getModel()
                        .getRoot(), query);
        // selects the node
        if (path != null)
            tree.setSelectionPath(path);
        return tree;
    }

    public static void selectSQLTEXT(String query) {
        AcideDataBasePanel dataBasePanel = AcideMainWindow.getInstance().getDataBasePanel();
        AcideTree tree = dataBasePanel.getTree();
        // Searches for the table/view node on the database tree
        TreePath path = TreeUtils.searchForNodeV2((TreeNode) tree.getModel()
                .getRoot(), query, tree, new TreePath((TreeNode) tree
                .getModel().getRoot()), true);
        if (path != null) {
            TreeNode node = (TreeNode) path.getLastPathComponent();
            tree.setSelectionPath(path);
            // Searches for the child node SQL Text on the children of the node
            // (only for views)
            Enumeration<TreeNode> e = node.children();
            while (e.hasMoreElements()) {
                TreeNode node2 = e.nextElement();
                if (node2.toString().equals(
                        AcideLanguageManager.getInstance().getLabels()
                                .getString("s2036"))) {
                    path = path.pathByAddingChild(node2);
                    // Adds the child node of the SQL Text node with the sql
                    // definition of the view
                    if (!node2.isLeaf()) {
                        tree.setSelectionPath(path);
                        path = path.pathByAddingChild(node2.getChildAt(0));
                    }
                }
            }
            tree.scrollPathToVisible(path);
            tree.setSelectionPath(path);
        }
    }

    public static void showView(String view){
        AcideDatabaseDataView panelDv = AcideMainWindow.getInstance().getDataBasePanel().getDataView("$des", view);
        LinkedList<String> info = AcideDatabaseManager.getInstance().getSelectAll("$des", view);
        if(!info.isEmpty())
            panelDv.build(info);
        panelDv.setState(panelDv.NORMAL);
        panelDv.setAlwaysOnTop(true);
        panelDv.setAlwaysOnTop(false);
        panelDv.setVisible(true);
    }

    public static void showViewSubset(String view){

    }

    public static void resetColorNodes(){
        if(!AcideMainWindow.getInstance().getDebugPanel().getDebugSQLPanel().isDebuging()) {
            AcideDebugCanvas debugCanvas = AcideMainWindow.getInstance()
                    .getDebugPanel().getDebugSQLPanel().getCanvas();
            for (Node n : debugCanvas.get_graph().get_nodes()) {
                debugCanvas.setSelectedNode(n);
                debugCanvas.setColorSelectedNode(Color.GRAY);
            }
            updateCanvasDebug(debugCanvas);
        }
    }

    public static void startDebug(){
        try {
            // Puts the wait cursor
            AcideDataViewReplaceWindow.getInstance().setCursor(
                    Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            // Gets the canvas
            AcideDebugCanvas canvas = AcideMainWindow.getInstance()
                    .getDebugPanel().getDebugSQLPanel().getCanvas();
            paintTrustedTables(canvas);
            if(canvas.getSelectedNode() == null || !AcideMainWindow.getInstance().getDebugPanel().getDebugSQLPanel().isDebuging())
                canvas.setSelectedNode(canvas.getRootNode());
            String view = getSelectedViewName();

            AcideDebugSQLPanel.startDebug.setEnabled(false);

            AcideDebugSQLDebugWindow.getInstance().setQuestionType("all");
            AcideDebugSQLDebugWindow.getInstance().resetErrors();
            AcideDebugSQLDebugWindow.getInstance().putView(view, getViewTable(view));
            updateDebugWindow();
        } catch (Exception ex) {
            ex.printStackTrace();
            // TODO
        }
    }

    /**
     * Parse debug answer updating graph and check if debug must continue
     *
     * @param info
     * @return continueDebugging if continues debugging
     */
    private static String updateDebugState(LinkedList<String> info){
        // Puts the wait cursor
        AcideDataViewReplaceWindow.getInstance().setCursor(
                Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
        if(info.size() > 0 && info.get(0).equals("$error")){
            String debugError = "debugError" + getMessageError(info);
            return debugError;
        }else {
            String errorView = "";
            AcideDebugCanvas debugCanvas = AcideMainWindow.getInstance()
                    .getDebugPanel().getDebugSQLPanel().getCanvas();
            List<Node> nodes = debugCanvas.get_graph().get_nodes();
            if (info.size() % 2 == 0) {
                for (int i = 0; i < info.size(); i++) {
                    String view = info.get(i);
                    i++;
                    String state = info.get(i);

                    for (Node n : nodes) {
                        if (n.getLabel().split("/")[0].equals(view)) {
                            debugCanvas.setSelectedNode(n);
                            if (state.equals("nonvalid"))
                                debugCanvas.setColorSelectedNode(Color.ORANGE);
                            else if (state.equals("valid"))
                                debugCanvas.setColorSelectedNode(Color.GREEN);
                            else {
                                debugCanvas.setColorSelectedNode(Color.RED);
                                errorView = view;
                            }
                        }
                    }
                }
            }

            AcideDebugHelper.updateCanvasDebug(debugCanvas);

            return errorView;
        }
    }

    /**
     * Parse current view debugging
     *
     * @param currentQuestion
     * @return desired view
     */
    private static String parseCurrentQuestion(LinkedList<String> currentQuestion){
        String str = currentQuestion.getFirst();
        if(str.startsWith("subset(")) {
            AcideDebugSQLDebugWindow.getInstance().setQuestionType("subset");
            str = str.substring(str.lastIndexOf(",") +1, str.lastIndexOf(")"));
        }else{
            if(str.startsWith("in("))
                AcideDebugSQLDebugWindow.getInstance().setQuestionType("in");
            else
                AcideDebugSQLDebugWindow.getInstance().setQuestionType("all");
            while (str.contains("(")) {
                if (str.contains(")"))
                    str = str.substring(str.indexOf("(") + 1, str.indexOf(")"));
                else
                    str = str.split("\\(")[0];
            }
        }
        return str;
    }

    public static JScrollPane getViewTable(String view) {
        AcideDatabaseDataView viewWindow = AcideMainWindow.getInstance().getDataBasePanel()
                .getDataView("$des", view);
        LinkedList<String> info = AcideDatabaseManager.getInstance().getSelectAll("$des", view);
        if(!info.isEmpty()) {
            for(int i = 0; i < viewWindow.getTable().getColumnCount(); i++){
                if(i == 0)
                    info.add("$");
                else
                    info.add("");
            }
            viewWindow.build(info);
        }
        viewWindow.setState(viewWindow.NORMAL);
        JScrollPane scrollPane = viewWindow.getSrollPane();
        Dimension d = new Dimension(viewWindow.getTable().getWidth(),viewWindow.getTable().getHeight());
        scrollPane.setSize(d);
        AcideDebugSQLDebugWindow.getInstance().setJTable(viewWindow.get_jTable());
        viewWindow.closeWindow();
        return scrollPane;
    }

    public static void performDebug(String action){
        LinkedList<String> consoleInfo;
        updateHighlight(AcideDebugSQLDebugWindow.getInstance().getView());
        if(!AcideMainWindow.getInstance().getDebugPanel().getDebugSQLPanel().isDebuging()){
            AcideMainWindow.getInstance().getDebugPanel().getDebugSQLPanel().setDebuging(true);
            consoleInfo = DesDatabaseManager.getInstance().
                    startDebug(getSelectedViewName(), AcideDebugConfiguration.getInstance().getDebugConfiguration(), action);
        } else {
            if(AcideDebugSQLDebugWindow.getInstance().getSubsetView() != null){
                AcideDebugSQLDebugWindow.getInstance().getSubsetView().closeWindow();
                AcideDebugSQLDebugWindow.getInstance().setSubsetView();
            }
            consoleInfo = DesDatabaseManager.getInstance().debugCurrentAnswer(
                    AcideDebugSQLDebugWindow.getInstance().getCurrentQuestion(), action);
        }
        nextMovement(consoleInfo, false);
    }

    public static void startNodeDebug(String node, String action){
        // Puts the wait cursor
        AcideDataViewReplaceWindow.getInstance().setCursor(
                Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        AcideDebugSQLDebugWindow.getInstance().setQuestionType("all");
        AcideDebugSQLDebugWindow.getInstance().resetErrors();
        LinkedList<String> consoleInfo = DesDatabaseManager.getInstance().
                startDebug(node, AcideDebugConfiguration.getInstance().getDebugConfiguration(), action);
        //JOptionPane.showMessageDialog(null, consoleInfo);
        checkError(updateDebugState(consoleInfo));
        nextMovement(consoleInfo, true);
    }

    public static void performNodeDebug(String node, String action){
        LinkedList<String> consoleInfo = DesDatabaseManager.getInstance().
                setNodeState(node, action);
        //JOptionPane.showMessageDialog(null, consoleInfo);
        checkError(updateDebugState(consoleInfo));
        nextMovement(consoleInfo, true);
    }

    private static void checkError(String info){
        if (info.startsWith("debugError")) {
            info = info.replace("debugError", "");
            JOptionPane.showMessageDialog(null, info);
            AcideDebugSQLDebugWindow.getInstance().closeWindow();
            AcideDebugSQLPanel.startDebug.setEnabled(true);
            AcideMainWindow.getInstance().getDebugPanel().getDebugSQLPanel().setDebuging(false);
        }
    }

    private static void nextMovement(LinkedList<String> info, boolean nodeDebug){

        // Info empty is abort
        if(!info.isEmpty()) {
            String errorView = updateDebugState(info);
            if (errorView.equals("")) {
                LinkedList<String> currentQuestion = DesDatabaseManager.getInstance().debugCurrentQuestion();
                AcideDebugSQLDebugWindow.getInstance().setCurrentQuestion(currentQuestion.getFirst());
                String nextView = parseCurrentQuestion(currentQuestion);
                updateHighlight(nextView);
                AcideDebugSQLDebugWindow.getInstance().putView(nextView, getViewTable(nextView));
                if(!nodeDebug)
                    updateDebugWindow();
            } else if (errorView.startsWith("debugError")) {
                errorView = errorView.replace("debugError","");
                JOptionPane.showMessageDialog(null, errorView);
                AcideDebugSQLDebugWindow.getInstance().closeWindow();
                AcideDebugSQLPanel.startDebug.setEnabled(true);
                AcideMainWindow.getInstance().getDebugPanel().getDebugSQLPanel().setDebuging(false);
            } else {
                AcideDebugSQLDebugWindow.getInstance().stopDepug(errorView);
            }
        }
    }

    public static void updateDebugWindow() {
        String view = AcideDebugSQLDebugWindow.getInstance().getView();
        String type = DesDatabaseManager.getInstance().isTable("$des", view) ? AcideLanguageManager.getInstance().getLabels()
                .getString("s2371") : AcideLanguageManager.getInstance().getLabels()
                .getString("s2370");
        AcideDebugSQLDebugWindow.getInstance().setInfo(AcideLanguageManager.getInstance().getLabels()
                .getString("s2376") + type + " " + view);
        AcideDebugSQLDebugWindow.getInstance().setQuestion(AcideLanguageManager.getInstance().getLabels()
                .getString("s2377") + type +"?");
    }

    // Gets the view selected in the SQL Debug SQL panel canvas
    public static String getSelectedViewName(){
        return AcideMainWindow.getInstance().getDebugPanel().getDebugSQLPanel().getCanvas().getSelectedNode().getLabel().split("/")[0];
    }

    public static String getDataFromSelectedTuple(JTable table){
        Vector<?> data = (Vector<?>) ((AcideDatabaseDataView.MyTableModel) table.getModel()).getDataVector().get(table.getSelectedRow());
        return parseTupleContent(data);
    }

    public static String parseTupleContent(Vector data){
        String tuple = "";
        for(Object value : data){
            if(value != null){
                if(isNumeric(value.toString()))
                    tuple += value + ",";
                else
                    tuple += "'" + value.toString() + "',";
            }
        }
        // Remove last comma ','
        if(!tuple.isEmpty()){
            tuple = tuple.substring(0, tuple.length() - 1);
        }
        return tuple;
    }

    public static String getUserInputTuple(String view, String action){
        AcideDatabaseDataView window = AcideMainWindow.getInstance().getDataBasePanel().getDataView("$des", view);
        LinkedList<String> info;
        String title;
        if(action.equals("missing")) {
            // builds only the model
            info = DesDatabaseManager.getInstance().getTableModel(view);
            int size = info.size() / 2;
            for (int i = 0; i < size; i++)
                info.add("");
            window.setIsReadOnly(false);
            title = AcideLanguageManager.getInstance().getLabels()
                    .getString("s2366");
        }
        else {
            info = AcideDatabaseManager.getInstance().getSelectAll("$des", view);
            window.setIsReadOnly(true);
            title = AcideLanguageManager.getInstance().getLabels()
                    .getString("s2367");
        }
        window.build(info);
        JScrollPane srollPane = window.getSrollPane();
        AcideDataBaseDataViewTable a = window.getTable();
        int width = (a.getColumnCount() - 1) * 150 + 30;
        int height = a.getRowHeight() * a.getRowCount();
        if(height < 80){
            height = 80;
        }
        srollPane.setPreferredSize(new Dimension(width, height));
        window.closeWindow();
        ImageIcon icon = new ImageIcon("./resources/images/acideLogo.png");
        Image image = icon.getImage(); // transform it
        Image newimg = image.getScaledInstance(50, 50,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way
        icon = new ImageIcon(newimg);
        if(!action.equals("missing")){

            for( MouseListener listener : a.getMouseListeners() ) {
                a.removeMouseListener(listener);
            }
            a.changeSelection(1, 1, false, false);
            a.changeSelection(1, a.getColumnCount()-1, true, true);
            a.addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent e) {
                    int fila = a.rowAtPoint(e.getPoint());
                    a.changeSelection(fila, 1, false, false);
                    a.changeSelection(fila, a.getColumnCount()-1, true, true);
                }
            });
        }
        int input = JOptionPane.showConfirmDialog(null, srollPane, title,
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, icon);
        if(input == 0)
            return getDataFromSelectedTuple(window.getTable());
        else
            return "";
    }

    private static boolean isNumeric(String strNum) {
        if (strNum == null) {
            return false;
        }
        try {
            double d = Double.parseDouble(strNum);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

    private static void updateHighlight(String view){
        // Gets the canvas
        AcideDebugCanvas canvas = AcideMainWindow.getInstance()
                .getDebugPanel().getDebugSQLPanel().getCanvas();
        // select node
        List<Node> nodes = canvas.get_graph().get_nodes();
        Node node = null;
        for (Node n : nodes) {
            if (n.getLabel().split("/")[0].equals(view))
                node = n;
        }

        if (node != null)
            canvas.setSelectedNode(node);

        updateCanvasDebug(canvas);
    }

    private static String getMessageError(LinkedList<String> info){
        String result = "";
        for(String line: info){
            if(!line.equals("$error") && !isNumeric(line))
                result += line;
        }
        return result;
    }

    public static void paintTrustedTables(AcideDebugCanvas canvas){
        if(AcideDebugConfiguration.getInstance().getTrust_tables().equals(AcideDebugConfiguration.Trust_tables.YES)) {
            for (Node n : canvas.get_graph().get_nodes()) {
                if (DesDatabaseManager.getInstance().isTable("$des", n.getLabel().split("/")[0])) {
                    n.setNodeColor(Color.GREEN);
                }
            }
        }
        updateCanvasDebug(canvas);
    }

    public static boolean hasRedNode(AcideDebugCanvas canvas){
        boolean hasRed = false;
        for(Node n : canvas.get_graph().get_nodes()){
            if(n.getNodeColor().equals(Color.RED))
                hasRed = true;
        }
        return hasRed;
    }

    public static boolean isRedNode(Node node){
        AcideDebugCanvas canvas = AcideMainWindow.getInstance().getDebugPanel().getDebugSQLPanel().getCanvas();
        for(Node n : canvas.get_graph().get_nodes()){
            if(n.getNodeColor().equals(Color.RED) && n.getLabel().equals(node.getLabel()))
                return true;
        }
        return false;
    }

    private static void highlightNext(){
        LinkedList<String> currentQuestion = DesDatabaseManager.getInstance().debugCurrentQuestion();
        AcideDebugSQLDebugWindow.getInstance().setCurrentQuestion(currentQuestion.getFirst());
        String nextView = parseCurrentQuestion(currentQuestion);
        updateHighlight(nextView);
    }

    public static boolean isRootView(String view){
        AcideDebugCanvas canvas = AcideMainWindow.getInstance().getDebugPanel().getDebugSQLPanel().getCanvas();
        return canvas.getRootNode().getLabel().split("/")[0].equals(view);
    }
}
