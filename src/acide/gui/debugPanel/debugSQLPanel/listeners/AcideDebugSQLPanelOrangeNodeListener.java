package acide.gui.debugPanel.debugSQLPanel.listeners;

import acide.gui.debugPanel.debugCanvas.AcideDebugCanvas;
import acide.gui.debugPanel.utils.AcideDebugHelper;
import acide.gui.mainWindow.AcideMainWindow;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class AcideDebugSQLPanelOrangeNodeListener implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            // Gets the canvas
            AcideDebugCanvas canvas = AcideMainWindow.getInstance()
                    .getDebugPanel().getDebugSQLPanel().getCanvas();
            if(AcideMainWindow.getInstance().getDebugPanel().getDebugSQLPanel().isDebuging()) {

                AcideDebugHelper.performNodeDebug(canvas.getSelectedNode().getLabel().split("/")[0], "missing");
            }else{
                AcideMainWindow.getInstance().getDebugPanel().getDebugSQLPanel().setDebuging(true);
                AcideDebugHelper.startNodeDebug(canvas.getSelectedNode().getLabel().split("/")[0], "missing");
            }
        } catch (Exception ex) {
            AcideMainWindow.getInstance().getDebugPanel()
                    .setCursor(Cursor.getDefaultCursor());
        }
    }
}