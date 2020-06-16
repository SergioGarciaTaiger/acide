package acide.gui.debugPanel.debugSQLPanel.listeners;

import acide.gui.debugPanel.debugCanvas.AcideDebugCanvas;
import acide.gui.debugPanel.utils.AcideDebugHelper;
import acide.gui.mainWindow.AcideMainWindow;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AcideDebugSQLPanelOpenViewListener implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent event) {
        AcideDebugCanvas canvas = AcideMainWindow.getInstance()
                .getDebugPanel().getDebugSQLPanel().getCanvas();
        if(canvas.getSelectedNode().getLabel().contains("/")) {
            String view = canvas.getSelectedNode().getLabel().split("/")[0];
            AcideDebugHelper.showView(view);
        }
    }
}