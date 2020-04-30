package acide.gui.debugPanel.debugSQLPanel;

import acide.language.AcideLanguageManager;

import javax.swing.*;

public class AcideDebugConfigurationView extends JFrame {

    private AcideDebugConfiguration configuration;

    private JLabel debugLabel;
    private JLabel orderLabel;
    private JLabel trust_tablesLabel;
    private JLabel trust_fileLabel;
    private JLabel orable_fileLabel;

    AcideDebugConfigurationView(){
        super(AcideLanguageManager.getInstance()
                .getLabels().getString("s2326"));
        configuration = AcideDebugConfiguration.getInstance();
    }



}
