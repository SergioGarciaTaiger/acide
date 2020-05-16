package acide.gui.databasePanel.utils;

import acide.gui.databasePanel.AcideDataBasePanel;
import acide.gui.mainWindow.AcideMainWindow;
import acide.language.AcideLanguageManager;
import acide.process.console.AcideDatabaseManager;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.util.NoSuchElementException;

public class DataBasePanelUtils {

    public static void updateDataBasePanelView(){
        AcideDataBasePanel panel = AcideMainWindow.getInstance().getDataBasePanel();

        DefaultMutableTreeNode nodeBase = (DefaultMutableTreeNode) panel.getTree().getModel()
                .getChild(panel.getTree().getModel().getRoot(), 0);

        try{
            DefaultMutableTreeNode nodoDes = (DefaultMutableTreeNode) nodeBase.getFirstChild();
            DefaultMutableTreeNode nodoTables = (DefaultMutableTreeNode) nodoDes.getFirstChild();
            panel.updateDataBaseTree((DefaultMutableTreeNode) nodoTables.getNextSibling());
        } catch (NoSuchElementException e){

        }
    }

    public static void updateDataBasePanelTable(){
        AcideDataBasePanel panel = AcideMainWindow.getInstance().getDataBasePanel();

        DefaultMutableTreeNode nodeBase = (DefaultMutableTreeNode) panel.getTree().getModel()
                .getChild(panel.getTree().getModel().getRoot(), 0);

        try{
            DefaultMutableTreeNode nodoDes = (DefaultMutableTreeNode) nodeBase.getFirstChild();
            DefaultMutableTreeNode nodoTables = (DefaultMutableTreeNode) nodoDes.getFirstChild();
            panel.updateDataBaseTree(nodoTables);
        } catch (NoSuchElementException e){

        }
    }

    public static void pasteDataBasePanelTable(Clipboard clipboard){
        try {
            String oldTable = (String) clipboard.getData(DataFlavor.stringFlavor);
            String newTable = (String) JOptionPane.showInputDialog(null,
                    AcideLanguageManager.getInstance().getLabels().getString("s2119"),
                    AcideLanguageManager.getInstance().getLabels().getString("s2120"),
                    JOptionPane.PLAIN_MESSAGE, null, null, oldTable);

            if ((newTable != null) && (newTable.length() > 0)) {

                AcideDatabaseManager des = AcideDatabaseManager.getInstance();

                int option = AcideDatabaseCopyTableOption.getInstance().getOption();

                String res = des.pasteTable(newTable, oldTable, option);

                if (res.contains("success"))
                    DataBasePanelUtils.updateDataBasePanelTable();

                else JOptionPane.showMessageDialog(null, res,
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
