package project.shortcuts;

import project.other.Component;
import project.visual.AppStructure;

import java.awt.event.*;

public class MouseShortcuts extends Component {
    public MouseShortcuts(AppStructure appStructure) {super(appStructure);}

    public void setupMouseShortcuts() {
        zoomWithMouse();
        dragWithMouse();
    }

    private void zoomWithMouse() {
        updateValues();
        imagePanel.addMouseWheelListener(new MouseWheelListener() {
            @Override
            public void mouseWheelMoved(MouseWheelEvent e) {
                if (e.getPreciseWheelRotation() < 0 && appStructure.getZoomFactor() < 5.0) {
                    appStructure.setZoomFactor(appStructure.getZoomFactor() * 1.1); // Zoom in
                } else if(e.getPreciseWheelRotation() > 0 && appStructure.getZoomFactor() > -5.0) {
                    appStructure.setZoomFactor(appStructure.getZoomFactor() / 1.1); // Zoom out
                }
                appStructure.setZoomFactor(Math.max(0.1, appStructure.getZoomFactor()));
                imagePanel.repaint();
            }
        });
    }

    private void dragWithMouse() {
        updateValues();
        imagePanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                appStructure.setDragStartX(e.getX());
                appStructure.setDragStartY(e.getY());
            }
        });

        imagePanel.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                int deltaX = e.getX() - appStructure.getDragStartX();
                int deltaY = e.getY() - appStructure.getDragStartY();

                appStructure.setImageX(appStructure.getImageX() + deltaX);
                appStructure.setImageY(appStructure.getImageY() + deltaY);

                appStructure.setDragStartX(e.getX());
                appStructure.setDragStartY(e.getY());

                repaintAndRevalidatePanel();
            }
        });
    }
}
