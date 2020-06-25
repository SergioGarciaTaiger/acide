package acide.gui.debugPanel.debugSQLPanel.listeners;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;

import acide.gui.debugPanel.debugCanvas.AcideDebugCanvas;
import acide.gui.debugPanel.debugSQLPanel.AcideDebugSQLDebugWindow;
import acide.gui.debugPanel.debugSQLPanel.AcideDebugSQLPanel;
import acide.gui.debugPanel.debugSQLPanel.debugSQLConfiguration.AcideDebugConfiguration;
import acide.gui.debugPanel.utils.AcideDebugHelper;
import acide.gui.mainWindow.AcideMainWindow;
import acide.process.console.DesDatabaseManager;

import javax.swing.*;

/**
 * ACIDE - A Configurable IDE trace SQL color node listener.
 *
 * @version 0.17
 *
 */
public class AcideDebugSQLPanelStartDebugListener implements ActionListener {

    /*
     * (non-Javadoc)
     *
     * @see
     * java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        AcideDebugHelper.resetColorNodes();
        AcideDebugHelper.startDebug();
        SwingUtilities.invokeLater(() -> AcideDebugSQLDebugWindow.getInstance().showWindow());
    }
}