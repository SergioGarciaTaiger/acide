package acide.gui.debugPanel.debugSQLPanel.listeners;

import acide.gui.debugPanel.utils.AcideDebugHelper;
import acide.gui.mainWindow.AcideMainWindow;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AcideDebugSQLPanelShowViewListener implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent event) {
        String view = AcideMainWindow.getInstance().getDebugPanel()
                .getDebugSQLPanel().getCanvas().getSelectedNode().getLabel().split("/")[0];
        AcideDebugHelper.showView(view);
    }
}
