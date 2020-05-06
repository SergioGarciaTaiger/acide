package acide.gui.debugPanel.debugSQLPanel.listeners;

import acide.gui.debugPanel.utils.AcideDebugHelper;
import acide.gui.mainWindow.AcideMainWindow;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AcideDebugSQLPanelShowViewListener implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent event) {
        JComboBox viewBox = AcideMainWindow.getInstance()
                .getDebugPanel().getDebugSQLPanel().getViewBox();
        if (viewBox.getSelectedIndex() < 1)
            return;
        // Gets the label of the selected item
        String view = (String) viewBox.getSelectedItem();
        AcideDebugHelper.showView(view);
    }
}
