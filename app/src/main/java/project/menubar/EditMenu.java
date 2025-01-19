package project.menubar;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

import project.other.Component;
import project.visual.*;

public class EditMenu extends Component {
    // Construtor
    public EditMenu(AppStructure appStructure){
        super(appStructure);
    }

    // Ajusta o brilho da imagem (deixando-a mais clara ou mais escura)
    public void adjustBrightness(int brightness) {
        this.updateValues();
        if (currentImage == null) {
            JOptionPane.showMessageDialog(frame, "No image loaded.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        saveStateForUndo();

        BufferedImage adjustedImage = new BufferedImage(
                currentImage.getWidth(),
                currentImage.getHeight(),
                currentImage.getType()
        );

        for (int x = 0; x < currentImage.getWidth(); x++) {
            for (int y = 0; y < currentImage.getHeight(); y++) {
                Color color = new Color(currentImage.getRGB(x, y));

                int red = Math.min(Math.max(color.getRed() + brightness, 0), 255);
                int green = Math.min(Math.max(color.getGreen() + brightness, 0), 255);
                int blue = Math.min(Math.max(color.getBlue() + brightness, 0), 255);

                Color newColor = new Color(red, green, blue);
                adjustedImage.setRGB(x, y, newColor.getRGB());
            }
        }

        currentImage = adjustedImage;
        appStructure.setCurrentImage(adjustedImage);
        appStructure.getImagePanel().repaint();
    }

    // Ajusta a saturação da imagem (deixando-a mais saturada ou menos saturada)
    public void adjustSaturation(int saturation) {
        this.updateValues();
        if (currentImage == null) {
            JOptionPane.showMessageDialog(frame, "No image loaded.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        saveStateForUndo();

        BufferedImage adjustedImage = new BufferedImage(
                currentImage.getWidth(),
                currentImage.getHeight(),
                currentImage.getType()
        );

        float adjustmentFactor = saturation / 100.0f;

        for (int x = 0; x < currentImage.getWidth(); x++) {
            for (int y = 0; y < currentImage.getHeight(); y++) {
                Color color = new Color(currentImage.getRGB(x, y));

                float[] hsbValues = Color.RGBtoHSB(color.getRed(), color.getGreen(), color.getBlue(), null);

                hsbValues[1] = Math.min(Math.max(hsbValues[1] + adjustmentFactor, 0), 1);

                int rgb = Color.HSBtoRGB(hsbValues[0], hsbValues[1], hsbValues[2]);
                adjustedImage.setRGB(x, y, rgb);
            }
        }

        currentImage = adjustedImage;
        appStructure.setCurrentImage(adjustedImage);
        appStructure.getImagePanel().repaint();
    }

    // Redimensiona a imagem com um novo width (largura) e novo height (altura). Em pixels.
    public void resizeImage(int newWidth, int newHeight) {
        updateValues();
        if (currentImage == null) {
            JOptionPane.showMessageDialog(frame, "No image loaded", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        BufferedImage resizedImage = new BufferedImage(newWidth, newHeight, currentImage.getType());
        Graphics2D g2d = resizedImage.createGraphics();

        g2d.drawImage(currentImage, 0, 0, newWidth, newHeight, null);
        g2d.dispose();

        currentImage = resizedImage;
        appStructure.setCurrentImage(currentImage);
        appStructure.updateImageInfo();
        repaintAndRevalidatePanel();
    }
}
