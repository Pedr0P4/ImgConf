package project.menubar;

import project.visual.AppStructure;

import javax.swing.*;
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
}
