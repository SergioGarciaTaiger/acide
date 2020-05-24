package acide.gui.debugPanel.utils;

import acide.gui.databasePanel.dataView.AcideDatabaseDataView;
import acide.gui.databasePanel.utils.AcideTree;
import acide.gui.debugPanel.debugCanvas.AcideDebugCanvas;
import acide.gui.debugPanel.debugSQLPanel.AcideDebugSQLDebugWindow;
import acide.gui.debugPanel.debugSQLPanel.AcideDebugSQLPanel;
import acide.gui.debugPanel.debugSQLPanel.debugSQLConfiguration.AcideDebugConfiguration;
import acide.gui.graphUtils.Node;
import acide.gui.mainWindow.AcideMainWindow;
import acide.language.AcideLanguageManager;
import acide.process.console.DesDatabaseManager;

import javax.swing.*;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;
import java.awt.*;
import java.util.*;
import java.util.List;

public class AcideDebugHelper {

    public static void updateCanvasDebugGraph(AcideDebugCanvas canvas){
        AcideDebugPanelHighLighter highLighter = AcideMainWindow
                .getInstance().getDebugPanel().getDebugSQLPanel()
                .getHighLighter();
        canvas.repaint();
        String selected = canvas.getSelectedNode().getLabel();

        // Updates the highlights
        highLighter.resetLines();
        highLighter.unHighLight();
        if(canvas.getSelectedNode().getNodeColor().equals(Color.GRAY))
            highLighter.highLight(selected);
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
        String selected = canvas.getSelectedNode().getLabel();

        // Updates the highlights
        highLighter.resetLines();
        highLighter.unHighLight();
        highLighter.highLight(selected);
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
        AcideTree tree = AcideMainWindow.getInstance().getDataBasePanel()
                .getTree();
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
            tree.setSelectionPath(path);
        }
    }

    public static void showView(String view){
        AcideDatabaseDataView panelDv  = AcideMainWindow.getInstance().getDataBasePanel().getDataView("$des", view);

        panelDv.setState(panelDv.NORMAL);
        panelDv.setAlwaysOnTop(true);
        panelDv.setAlwaysOnTop(false);
    }

    public static void startDebug(){
        try {
            String view = getSelectedViewName();
            if(view != null && !view.equals("          ")) {
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

                else
                    throw new Exception("error while trying to retrieve node");

                AcideDebugSQLPanel.startDebug.setEnabled(false);

                AcideDebugSQLDebugWindow.getInstance().putView(view, getViewTable(view));
                updateDebugWindow();
            }else{
                LinkedList<String> error = new LinkedList<>();
                error.add("No view selected, please select a valid node to start");
                AcideDebugSQLDebugWindow.getInstance().stopDepug(error);
            }
        } catch (Exception ex) {
            LinkedList<String> error = new LinkedList<>();
            error.add("Something happened while trying to start debug");
            AcideDebugSQLDebugWindow.getInstance().stopDepug(error);
            AcideMainWindow.getInstance().getDebugPanel()
                    .setCursor(Cursor.getDefaultCursor());
            AcideDebugSQLDebugWindow.getInstance().stopDepug(error);
        }
    }


    /**
     * Parse debug answer updating graph and check if debug must continue
     *
     * @param info
     * @return continueDebugging if continues debugging
     */
    private static boolean updateDebugState(LinkedList<String> info){
        boolean continueDebugging = true;
        AcideDebugCanvas canvas = AcideMainWindow.getInstance()
                .getDebugPanel().getDebugSQLPanel().getCanvas();
        List<Node> nodes = canvas.get_graph().get_nodes();

        for(String line: info){
            String view = line.split(" ")[0];
            String state = line.split(" ")[1];

            for(Node n: nodes){

                if(n.getLabel().split("/")[0].equals(view)) {
                    canvas.setSelectedNode(n);

                    if(state.equals("nonvalid"))
                        canvas.setColorSelectedNode(Color.ORANGE);
                    else if(state.equals("valid"))
                        canvas.setColorSelectedNode(Color.GREEN);
                    else{
                        canvas.setColorSelectedNode(Color.RED);
                        continueDebugging = false;
                    }
                }
            }
        }
        AcideDebugHelper.updateCanvasDebugGraph(canvas);

        return continueDebugging;
    }

    /**
     * Parse current view debugging
     *
     * @param currentQuestion
     * @return desired view
     */
    private static String parseCurrentQuestion(LinkedList<String> currentQuestion){
        String str = currentQuestion.getFirst();
        return str.substring(str.indexOf("(")+1,str.indexOf(")"));
    }


    public static JScrollPane getViewTable(String view) {
        AcideDatabaseDataView viewWindow = AcideMainWindow.getInstance().getDataBasePanel()
                .getDataView("$des", view);
        viewWindow.setIsHidden(true);
        viewWindow.setState(viewWindow.NORMAL);
        viewWindow.setIsReadOnly(true);
        JScrollPane table = viewWindow.getSrollPane();
        AcideDebugSQLDebugWindow.getInstance().setJTable(viewWindow.get_jTable());
        viewWindow.closeWindow();
        return table;
    }

    public static void performDebug(String action){
        LinkedList<String> consoleInfo;
        if(!AcideDebugSQLDebugWindow.getInstance().isDebuging()){
            AcideDebugSQLDebugWindow.getInstance().setDebuging(true);
            if(action.equals("valid") || action.equals("nonvalid"))
                action = "";
            consoleInfo = DesDatabaseManager.getInstance().
                    startDebug(getSelectedViewName(), AcideDebugConfiguration.getInstance().getDebugConfiguration(), action);
        } else {
            consoleInfo = DesDatabaseManager.getInstance().debugCurrentAnswer(
                    AcideDebugSQLDebugWindow.getInstance().getCurrentQuestion(), action);
        }

        nextMovement(consoleInfo);
    }

    private static void nextMovement(LinkedList<String> info){
        if(updateDebugState(info)) {
            LinkedList<String> currentQuestion = DesDatabaseManager.getInstance().debugCurrentQuestion();
            AcideDebugSQLDebugWindow.getInstance().setCurrentQuestion(currentQuestion.getFirst());
            String nextView = parseCurrentQuestion(currentQuestion);
            AcideDebugSQLDebugWindow.getInstance().putView(nextView, getViewTable(nextView));
            updateDebugWindow();
        }else{
            AcideDebugSQLDebugWindow.getInstance().stopDepug(info);
        }
    }

    public static void updateDebugWindow() {
        String view = AcideDebugSQLDebugWindow.getInstance().getView();
        AcideDebugSQLDebugWindow.getInstance().setInfo("Debugging view " + view);
        AcideDebugSQLDebugWindow.getInstance().setQuestion("Is this the expected result of this view?");
        AcideDebugSQLDebugWindow.getInstance().showWindow();
    }


    public static String getSelectedViewName(){
        JComboBox viewBox = AcideMainWindow.getInstance()
                .getDebugPanel().getDebugSQLPanel().getViewBox();
        if (viewBox.getSelectedIndex() < 1)
            return "";
        // Gets the label of the selected item
        return (String) viewBox.getSelectedItem();
    }
}
