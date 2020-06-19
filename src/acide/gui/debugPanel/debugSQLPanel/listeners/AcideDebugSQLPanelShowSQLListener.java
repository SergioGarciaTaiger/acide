package acide.gui.debugPanel.debugSQLPanel.listeners;

import acide.gui.databasePanel.AcideDataBasePanel;
import acide.gui.databasePanel.utils.AcideTree;
import acide.gui.debugPanel.debugCanvas.AcideDebugCanvas;
import acide.gui.mainWindow.AcideMainWindow;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AcideDebugSQLPanelShowSQLListener implements ActionListener {

    /*
     * (non-Javadoc)
     *
     * @see
     * java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
     */
    @Override
    public void actionPerformed(ActionEvent ev) {
        AcideDebugCanvas canvas = AcideMainWindow.getInstance().getDebugPanel().getDebugSQLPanel().getCanvas();

        if (canvas.getSelectedNode() != null) {
            String nodeName = canvas.getSelectedNode().getLabel().split("/")[0];
            AcideDataBasePanel dbPanel = AcideMainWindow.getInstance().getDataBasePanel();
            dbPanel.repaint();
            // update database tree
            dbPanel.updateDataBaseTree();
            AcideTree tree = dbPanel.getTree();
            String a = "";
        }
    }
}
