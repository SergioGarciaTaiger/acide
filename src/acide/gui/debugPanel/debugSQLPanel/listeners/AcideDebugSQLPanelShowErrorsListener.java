package acide.gui.debugPanel.debugSQLPanel.listeners;

import acide.gui.debugPanel.debugCanvas.AcideDebugCanvas;
import acide.gui.debugPanel.debugSQLPanel.AcideDebugSQLDebugWindow;
import acide.gui.debugPanel.utils.AcideDebugHelper;
import acide.gui.mainWindow.AcideMainWindow;
import acide.language.AcideLanguageManager;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AcideDebugSQLPanelShowErrorsListener implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent event) {
        String info = "<html>";
        if(AcideDebugSQLDebugWindow.getInstance().getErrors().size() > 0){
            info += "<br>" + AcideLanguageManager.getInstance().getLabels()
                    .getString("s2365");
            for(String line: AcideDebugSQLDebugWindow.getInstance().getErrors()){
                info += "<br>" + " " + " " + " - " + line;
            }
        }
        info += "</html>";
        JLabel label = new JLabel(info);

        JOptionPane.showMessageDialog(null, label);
    }
}