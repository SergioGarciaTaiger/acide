package acide.gui.debugPanel.debugSQLPanel.listeners;

import acide.gui.debugPanel.debugSQLPanel.debugSQLConfiguration.AcideDebugConfigurationWindow;
import acide.gui.mainWindow.AcideMainWindow;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AcideDebugSQLPanelConfigureDebugListener implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            // Shows the new project configuration window
            AcideDebugConfigurationWindow.getInstance().showWindow();
        } catch (Exception ex) {
            AcideMainWindow.getInstance().getDebugPanel()
                    .setCursor(Cursor.getDefaultCursor());
        }
    }
}
