package project.menubar;

import project.visual.AppStructure;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class Menu {
    protected AppStructure appStructure;
    protected BufferedImage currentImage;
    protected JFrame frame;

    public Menu(AppStructure appStructure) {
        this.appStructure = appStructure;
        this.currentImage = appStructure.getCurrentImage();
        this.frame = appStructure.getFrame();
    }

    protected void updateValues() {
        this.currentImage = appStructure.getCurrentImage();
        this.frame = appStructure.getFrame();
    }

    protected void repaintAndRevalidatePanel(){
        appStructure.getImagePanel().repaint();
        appStructure.getImagePanel().revalidate();
    }

    protected void saveStateForUndo() {
        if (currentImage != null) {
            appStructure.getUndoStack().push(copyImage(currentImage));
            appStructure.getRedoStack().clear(); // Limpar o Redo sempre que uma nova ação for feita
        }
    }

    protected BufferedImage copyImage(BufferedImage source) {
        BufferedImage copy = new BufferedImage(source.getWidth(), source.getHeight(), source.getType());
        Graphics g = copy.getGraphics();
        g.drawImage(source, 0, 0, null);
        g.dispose();
        return copy;
    }
}
