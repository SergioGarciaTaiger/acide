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

import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;
import java.awt.*;
import java.util.Enumeration;
import java.util.LinkedList;
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
            AcideDebugSQLPanel.startDebug.setEnabled(false);
            // Gets the canvas
            AcideDebugCanvas canvas = AcideMainWindow.getInstance()
                    .getDebugPanel().getDebugSQLPanel().getCanvas();
            // Gets the canvas
            String view;
            if(canvas.getSelectedNode() == null){
                canvas.setSelectedNode(canvas.getRootNode());
            }
            if(canvas.getSelectedNode().getLabel().contains("/"))
                view = canvas.getSelectedNode().getLabel().split("/")[0];
            else
                throw new Exception("view not obtained");
            LinkedList<String> result = DesDatabaseManager.getInstance()
                    .executeCommand("/debug_sql " + view + AcideDebugConfiguration.getInstance().getDebugConfiguration());
            updateDebugWindow(result);
        } catch (Exception ex) {
            AcideMainWindow.getInstance().getDebugPanel()
                    .setCursor(Cursor.getDefaultCursor());
        }
    }
    public static void performDebug(String action) throws Exception {
        if(!AcideDebugSQLDebugWindow.getInstance().isDebuging()){
            AcideDebugSQLDebugWindow.getInstance().setDebuging(true);
            AcideDebugHelper.startDebug();
        }
        AcideDebugCanvas canvas = AcideMainWindow.getInstance()
                .getDebugPanel().getDebugSQLPanel().getCanvas();
        LinkedList<String> result = DesDatabaseManager.getInstance()
                .executeCommand(action);
        String nextView = result.getFirst().split("'")[1].split("'")[0];
        List<Node> nodes = canvas.get_graph().get_nodes();
        Node node = null;
        for(Node n: nodes){
            if(n.getLabel().split("/")[0].equals(nextView))
                node = n;
        }
        if(node != null) {
            canvas.setSelectedNode(node);
            if(result.getFirst().contains("Buggy view found")){
                AcideDebugSQLDebugWindow.getInstance().stopDepug(result);
                canvas.setColorSelectedNode(Color.RED);
                AcideDebugSQLPanel.startDebug.setEnabled(true);
                AcideDebugSQLDebugWindow.getInstance().setDebuging(false);
            }
            else
                updateDebugWindow(result);
            AcideDebugHelper.updateCanvasDebugGraph(canvas);
        }
        else{
            AcideDebugSQLDebugWindow.getInstance().setDebuging(false);
            AcideDebugSQLDebugWindow.getInstance().closeWindow();
            throw new Exception("something went wrong with DES communication");
        }
    }

    public static void updateDebugWindow(List<String> result) throws Exception {
        if(result.size() > 0) {
            String info = "<html>";
            String question = "";
            for (int i = 0; i < result.size() - 1; i++) {
                info += result.get(i) + "<br>";
            }
            info += "</html>";
            question = result.get(result.size() - 1).split("\\?")[0];
            AcideDebugSQLDebugWindow.getInstance().setInfo(info);
            AcideDebugSQLDebugWindow.getInstance().setQuestion(question);
            AcideDebugSQLDebugWindow.getInstance().showWindow();
        }
        else
            throw new Exception("something went wrong with DES communication");
    }
}
