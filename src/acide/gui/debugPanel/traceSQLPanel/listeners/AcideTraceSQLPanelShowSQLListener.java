package acide.gui.debugPanel.traceSQLPanel.listeners;

import acide.gui.databasePanel.AcideDataBasePanel;
import acide.gui.databasePanel.utils.AcideTree;
import acide.gui.debugPanel.debugCanvas.AcideDebugCanvas;
import acide.gui.mainWindow.AcideMainWindow;

import javax.swing.*;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AcideTraceSQLPanelShowSQLListener implements ActionListener {

    /*
     * (non-Javadoc)
     *
     * @see
     * java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
     */
    @Override
    public void actionPerformed(ActionEvent ev) {
        AcideDebugCanvas canvas = AcideMainWindow.getInstance().getDebugPanel().getTraceSQLPanel().getCanvas();

        if(canvas.getSelectedNode() != null) {
            // get name of selected node of canvas
            String nodeName = canvas.getSelectedNode().getLabel().split("/")[0];

            AcideDataBasePanel dbPanel = AcideMainWindow.getInstance().getDataBasePanel();
            dbPanel.repaint();
            // update database tree
            dbPanel.updateDataBaseTree();
            AcideTree tree = dbPanel.getTree();
            DefaultTreeModel treeModel = dbPanel.getTreeModel();
        }
    }

}