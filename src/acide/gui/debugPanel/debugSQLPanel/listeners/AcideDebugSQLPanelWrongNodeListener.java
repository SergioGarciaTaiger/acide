package acide.gui.debugPanel.debugSQLPanel.listeners;

import acide.gui.debugPanel.debugCanvas.AcideDebugCanvas;
import acide.gui.debugPanel.debugSQLPanel.AcideDebugSQLDebugWindow;
import acide.gui.debugPanel.utils.AcideDebugHelper;
import acide.gui.mainWindow.AcideMainWindow;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class AcideDebugSQLPanelWrongNodeListener implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            // Gets the canvas
            AcideDebugCanvas canvas = AcideMainWindow.getInstance()
                    .getDebugPanel().getDebugSQLPanel().getCanvas();
            String view = canvas.getSelectedNode().getLabel().split("/")[0];
            String userInput = AcideDebugHelper.getUserInputTuple(view);
            if(!userInput.isEmpty()) {
                String action = "wrong(" + view + "(" + userInput +"))";
                if (AcideMainWindow.getInstance().getDebugPanel().getDebugSQLPanel().isDebuging()) {
                    AcideDebugHelper.performNodeDebug(view, action);
                } else {
                    AcideMainWindow.getInstance().getDebugPanel().getDebugSQLPanel().setDebuging(true);
                    AcideDebugHelper.startNodeDebug(view, action);
                }
            }
        } catch (Exception ex) {
            System.out.println(ex);
            AcideMainWindow.getInstance().getDebugPanel()
                    .setCursor(Cursor.getDefaultCursor());
        }
    }
}