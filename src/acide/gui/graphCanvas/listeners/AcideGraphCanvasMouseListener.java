package acide.gui.graphCanvas.listeners;

import acide.gui.databasePanel.dataView.AcideDatabaseDataView;
import acide.gui.graphCanvas.AcideGraphCanvas;
import acide.gui.graphUtils.DirectedWeightedGraph;
import acide.gui.graphUtils.Node;
import acide.gui.mainWindow.AcideMainWindow;
import acide.process.console.AcideDatabaseManager;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.LinkedList;

public class AcideGraphCanvasMouseListener extends MouseAdapter {

    /**
     * ACIDE - A Configurable IDE debug canvas mouse listener target canvas.
     */
    private AcideGraphCanvas _canvas;

    /**
     *
     * Creates a new ACIDE - A Configurable IDE debug canvas mouse listener.
     *
     * @param canvas
     *            the target canvas.
     */
    public AcideGraphCanvasMouseListener(AcideGraphCanvas canvas) {
        this._canvas = canvas;
    }

    /*
     * (non-Javadoc)
     *
     * @see java.awt.event.MouseAdapter#mouseClicked(java.awt.event.MouseEvent)
     */
    @Override
    public void mouseClicked(MouseEvent ev) {
        try{

            // Checks the number of clicks
            if (ev.getClickCount() >= 2) {
                // Gets the graph of the canvas
                DirectedWeightedGraph graph = _canvas.get_graph();
                // Gets the nodes of the graph
                ArrayList<Node> nodes = graph.get_nodes();
                // Searches if a node has been clicked
                for (Node n : nodes) {
                    if (ev.getX() >= n.getX()
                            && ev.getX() <= n.getX()
                            + (int) (_canvas.getNodeSize() * _canvas
                            .getZoom())
                            && ev.getY() >= n.getY()
                            && ev.getY() <= n.getY()
                            + (int) (_canvas.getNodeSize() * _canvas
                            .getZoom())) {
                        String view;
                        if(n.getLabel().contains("/")) {
                            view = n.getLabel().split("/")[0];
                            AcideDatabaseDataView panelDv = AcideMainWindow.getInstance().getDataBasePanel().getDataView("$des", view);
                            LinkedList<String> info = AcideDatabaseManager.getInstance().getSelectAll("$des", view);
                            if(!info.isEmpty())
                                panelDv.build(info);
                            panelDv.setAlwaysOnTop(true);
                            panelDv.setAlwaysOnTop(false);
                            panelDv.setVisible(true);
                        }
                    }
                }
            }
        }catch(Exception ex){

            AcideMainWindow.getInstance().getDebugPanel()
                    .setCursor(Cursor.getDefaultCursor());
        }

    }
}
