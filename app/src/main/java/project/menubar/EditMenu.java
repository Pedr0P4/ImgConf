package project.menubar;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

import project.visual.*;

public class EditMenu {
    private AppStructure appStructure;
    private BufferedImage currentImage;
    private JFrame frame;
    private JLabel imageLabel;

    public EditMenu(AppStructure appStructure) {
        this.appStructure = appStructure;
    };

    public void invertColors() {
        this.updateValues();
        if (currentImage == null) {
            JOptionPane.showMessageDialog(appStructure.getFrame(), "Carregamento da imagem falhou.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        for (int y = 0; y < currentImage.getHeight(); y++) {
            for (int x = 0; x < currentImage.getWidth(); x++) {
                int rgba = currentImage.getRGB(x, y);
                Color color = new Color(rgba, true);
                Color invertedColor = new Color(255 - color.getRed(), 255 - color.getGreen(), 255 - color.getBlue(), color.getAlpha());
                currentImage.setRGB(x, y, invertedColor.getRGB());
            }
        }

        appStructure.getImageLabel().setIcon(new ImageIcon(currentImage));
    }

    private void updateValues() {
        this.currentImage = appStructure.getCurrentImage();
        this.frame = appStructure.getFrame();
        this.imageLabel = appStructure.getImageLabel();
    }
}
