package acide.gui.graphPanel;

import acide.gui.consolePanel.tasks.AcideConsolePDGTask;
import acide.gui.consolePanel.tasks.AcideConsoleRDGTask;
import acide.gui.graphCanvas.AcideGraphCanvas;
import acide.gui.mainWindow.AcideMainWindow;

import java.awt.*;

public class AcideGraphUtil {

    public static void refreshGraphPanel(){
        //saves the send to console flag
        final boolean sendToConsole = AcideMainWindow
                .getInstance().getConsolePanel()
                .getProcessThread().getOutputGobbler()
                .get_sendToConsole();
        //updates the send to console to hide the output of the pdg command
        AcideMainWindow
                .getInstance().getConsolePanel()
                .getProcessThread().getOutputGobbler()
                .set_sendToConsole(false);

        //calls to the /pdg command and waits to the finalization
        String command;
        if (AcideMainWindow.getInstance().getGraphPanel().getRDG().isSelected()) {
            //sets the flag to generate the graph to true.
            AcideConsolePDGTask.getInstance().setGenerateGraph(false);
            //sets the flag to generate the graph to true.
            AcideConsoleRDGTask.getInstance().setGenerateGraph(true);
            command = "/rdg";
            AcideMainWindow
                    .getInstance().getConsolePanel()
                    .sendCommandToConsole(command, "");
            AcideMainWindow.getInstance().getConsolePanel()
                    .getProcessThread().getOutputGobbler()
                    .waitForTaskDone(1000);
            EventQueue.invokeLater(new Runnable() {

                @Override
                public void run() {
                    //waits to the finalization of the generation of the graph,
                    //restore the send to console flag and updates the generate graph flag.
                    AcideConsoleRDGTask.getInstance().run();
                    AcideConsoleRDGTask.getInstance().waitForTaskDone(1000);
                    AcideMainWindow
                            .getInstance().getConsolePanel()
                            .getProcessThread().getOutputGobbler()
                            .set_sendToConsole(sendToConsole);
                    AcideConsoleRDGTask.getInstance().setGenerateGraph(false);
                }
            });

            AcideGraphPanel._canvas.setZoom(1, AcideGraphCanvas.CanvasPanel.Graph);
        }
        else{
            //sets the flag to generate the graph to true.
            AcideConsoleRDGTask.getInstance().setGenerateGraph(false);
            //sets the flag to generate the graph to true.
            AcideConsolePDGTask.getInstance().setGenerateGraph(true);
            command = "/pdg";
            AcideMainWindow
                    .getInstance().getConsolePanel()
                    .sendCommandToConsole(command, "");
            AcideMainWindow.getInstance().getConsolePanel()
                    .getProcessThread().getOutputGobbler()
                    .waitForTaskDone(1000);
            EventQueue.invokeLater(new Runnable() {

                @Override
                public void run() {
                    //waits to the finalization of the generation of the graph,
                    //restore the send to console flag and updates the generate graph flag.
                    AcideConsolePDGTask.getInstance().waitForTaskDone(1000);
                    AcideMainWindow
                            .getInstance().getConsolePanel()
                            .getProcessThread().getOutputGobbler()
                            .set_sendToConsole(sendToConsole);
                    AcideConsolePDGTask.getInstance().setGenerateGraph(false);
                }
            });

            AcideGraphPanel._canvas.setZoom(1, AcideGraphCanvas.CanvasPanel.Graph);
        }
    }
}
