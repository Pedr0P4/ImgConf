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

    public void adjustBrightness(int brightness) {
        this.updateValues();
        if (currentImage == null) {
            JOptionPane.showMessageDialog(frame, "No image loaded.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Criar uma nova imagem para aplicar o ajuste
        BufferedImage adjustedImage = new BufferedImage(
                currentImage.getWidth(),
                currentImage.getHeight(),
                currentImage.getType()
        );

        for (int x = 0; x < currentImage.getWidth(); x++) {
            for (int y = 0; y < currentImage.getHeight(); y++) {
                Color color = new Color(currentImage.getRGB(x, y));

                // Ajustar os valores RGB
                int red = Math.min(Math.max(color.getRed() + brightness, 0), 255);
                int green = Math.min(Math.max(color.getGreen() + brightness, 0), 255);
                int blue = Math.min(Math.max(color.getBlue() + brightness, 0), 255);

                Color newColor = new Color(red, green, blue);
                adjustedImage.setRGB(x, y, newColor.getRGB());
            }
        }

        // Atualizar a imagem exibida
        currentImage = adjustedImage;
        imageLabel.setIcon(new ImageIcon(currentImage));
    }

    public void adjustSaturation(int saturation) {
        this.updateValues();
        if (currentImage == null) {
            JOptionPane.showMessageDialog(frame, "No image loaded.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Criar uma nova imagem para aplicar o ajuste
        BufferedImage adjustedImage = new BufferedImage(
                currentImage.getWidth(),
                currentImage.getHeight(),
                currentImage.getType()
        );

        float adjustmentFactor = saturation / 100.0f;

        for (int x = 0; x < currentImage.getWidth(); x++) {
            for (int y = 0; y < currentImage.getHeight(); y++) {
                Color color = new Color(currentImage.getRGB(x, y));

                // Converter RGB para HSB
                float[] hsbValues = Color.RGBtoHSB(color.getRed(), color.getGreen(), color.getBlue(), null);

                // Ajustar a saturação
                hsbValues[1] = Math.min(Math.max(hsbValues[1] + adjustmentFactor, 0), 1);

                // Converter de volta para RGB
                int rgb = Color.HSBtoRGB(hsbValues[0], hsbValues[1], hsbValues[2]);
                adjustedImage.setRGB(x, y, rgb);
            }
        }

        // Atualizar a imagem exibida
        currentImage = adjustedImage;
        imageLabel.setIcon(new ImageIcon(currentImage));
    }

    private void updateValues() {
        this.currentImage = appStructure.getCurrentImage();
        this.frame = appStructure.getFrame();
        this.imageLabel = appStructure.getImageLabel();
    }
}
