package acide.gui.debugPanel.debugSQLPanel.listeners;

import acide.gui.databasePanel.utils.AcideEnterTextWindow;
import acide.gui.debugPanel.debugSQLPanel.debugSQLConfiguration.AcideDebugConfigurationWindow;
import acide.gui.mainWindow.AcideMainWindow;
import acide.language.AcideLanguageManager;
import acide.process.console.AcideDatabaseManager;
import acide.process.console.DesDatabaseManager;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AcideDebugSQLPanelEditViewListener implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            String view = AcideMainWindow.getInstance().getDebugPanel()
                    .getDebugSQLPanel().getCanvas().getSelectedNode().getLabel().split("/")[0];
            String db = DesDatabaseManager.getInstance().currentDB();
            String text = AcideDatabaseManager.getInstance().getSQLText(db, view);
            new AcideEnterTextWindow(text, AcideLanguageManager.getInstance().getLabels().getString("s2036"), true);
        } catch (Exception ex) {
            AcideMainWindow.getInstance().getDebugPanel()
                    .setCursor(Cursor.getDefaultCursor());
        }
    }
}
