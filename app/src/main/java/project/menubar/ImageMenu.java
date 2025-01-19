package project.menubar;

import project.other.Component;
import project.visual.AppStructure;

import javax.swing.*;
import java.awt.image.BufferedImage;

public class ImageMenu extends Component {
    public ImageMenu(AppStructure appStructure) {
        super(appStructure);
    }

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
                // Calcular o novo pixel baseado na direção do espelhamento
                int newX = horizontal ? width - 1 - x : x;
                int newY = horizontal ? y : height - 1 - y;

                // Pegar a cor original e colocá-la na nova posição
                int rgb = currentImage.getRGB(x, y);
                flippedImage.setRGB(newX, newY, rgb);
            }
        }

        // Atualizar a imagem exibida
        appStructure.setCurrentImage(flippedImage);
        repaintAndRevalidatePanel();
    }
}
