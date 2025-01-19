package project.menubar;

import project.other.Component;
import project.visual.AppStructure;

import javax.swing.*;
import java.awt.image.BufferedImage;

public class ImageMenu extends Component {
    public ImageMenu(AppStructure appStructure) {
        super(appStructure);
    }

    // Método para inverter a imagem horizontalmente ou verticalmente dependendo do valor do parâmetro
    // True - Inverte horizontalmente
    // False - Inverte verticalmente
    public void flipImage(boolean horizontal) {
        updateValues();
        if (currentImage == null) {
            JOptionPane.showMessageDialog(frame, "No image loaded.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        saveStateForUndo();

        BufferedImage flippedImage = new BufferedImage(
                currentImage.getWidth(),
                currentImage.getHeight(),
                currentImage.getType()
        );

        int width = currentImage.getWidth();
        int height = currentImage.getHeight();

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                int newX = horizontal ? width - 1 - x : x;
                int newY = horizontal ? y : height - 1 - y;

                int rgb = currentImage.getRGB(x, y);
                flippedImage.setRGB(newX, newY, rgb);
            }
        }

        appStructure.setCurrentImage(flippedImage);
        repaintAndRevalidatePanel();
    }
}
