package project.menubar;

import project.other.Component;
import project.visual.AppStructure;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class FilterMenu extends Component {
    public FilterMenu(AppStructure appStructure) {
        super(appStructure);
    }

    // Aplica o filtro Sepia, deixando com um aspecto mais antigo
    public void applySepiaFilter() {
        updateValues();
        if (currentImage == null) {
            JOptionPane.showMessageDialog(frame, "No image loaded.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        saveStateForUndo();

        int width = currentImage.getWidth();
        int height = currentImage.getHeight();

        BufferedImage sepiaImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int rgb = currentImage.getRGB(x, y);

                int red = (rgb >> 16) & 0xFF;
                int green = (rgb >> 8) & 0xFF;
                int blue = rgb & 0xFF;

                int tr = (int)(0.393 * red + 0.769 * green + 0.189 * blue);
                int tg = (int)(0.349 * red + 0.686 * green + 0.168 * blue);
                int tb = (int)(0.272 * red + 0.534 * green + 0.131 * blue);

                tr = Math.min(255, Math.max(0, tr));
                tg = Math.min(255, Math.max(0, tg));
                tb = Math.min(255, Math.max(0, tb));

                sepiaImage.setRGB(x, y, (tr << 16) | (tg << 8) | tb);
            }
        }
        appStructure.setCurrentImage(sepiaImage);
        repaintAndRevalidatePanel();
    }

    // Aplica o filtro de tons de cinza, deixando a imagem em tons de cinza ("preto e branco")
    public void applyGrayscaleFilter() {
        updateValues();
        if (currentImage == null) {
            JOptionPane.showMessageDialog(frame, "No image loaded.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        saveStateForUndo();

        int width = currentImage.getWidth();
        int height = currentImage.getHeight();
        BufferedImage grayImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int rgb = currentImage.getRGB(x, y);
                int red = (rgb >> 16) & 0xFF;
                int green = (rgb >> 8) & 0xFF;
                int blue = rgb & 0xFF;

                int gray = (int)(0.299 * red + 0.587 * green + 0.114 * blue);
                gray = Math.min(255, Math.max(0, gray));

                grayImage.setRGB(x, y, (gray << 16) | (gray << 8) | gray);
            }
        }
        appStructure.setCurrentImage(grayImage);
        repaintAndRevalidatePanel();
    }

    // Aplica o filtro de Emboss, fazendo a imagem parecer q tem relevo
    public void applyEmbossFilter() {
        updateValues();
        if (currentImage == null) {
            JOptionPane.showMessageDialog(frame, "No image loaded.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        saveStateForUndo();

        int width = currentImage.getWidth();
        int height = currentImage.getHeight();
        BufferedImage embossedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        for (int y = 1; y < height; y++) {
            for (int x = 1; x < width; x++) {
                int rgb1 = currentImage.getRGB(x, y);
                int rgb2 = currentImage.getRGB(x - 1, y - 1);

                int red1 = (rgb1 >> 16) & 0xFF;
                int green1 = (rgb1 >> 8) & 0xFF;
                int blue1 = rgb1 & 0xFF;

                int red2 = (rgb2 >> 16) & 0xFF;
                int green2 = (rgb2 >> 8) & 0xFF;
                int blue2 = rgb2 & 0xFF;

                int red = Math.min(255, Math.max(0, red1 - red2 + 128));
                int green = Math.min(255, Math.max(0, green1 - green2 + 128));
                int blue = Math.min(255, Math.max(0, blue1 - blue2 + 128));

                embossedImage.setRGB(x, y, (red << 16) | (green << 8) | blue);
            }
        }
        appStructure.setCurrentImage(embossedImage);
        repaintAndRevalidatePanel();
    }

    // Aplica o filtro de Sharpen (nitidez), deixando a imagem mais nitida
    public void applySharpenFilter() {
        updateValues();
        if (currentImage == null) {
            JOptionPane.showMessageDialog(frame, "No image loaded.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        saveStateForUndo();

        int width = currentImage.getWidth();
        int height = currentImage.getHeight();
        BufferedImage sharpenedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        int[] kernel = {0, -1, 0, -1, 5,-1, 0, -1, 0};
        int kernelSize = 3;
        int offset = kernelSize / 2;

        for (int y = offset; y < height - offset; y++) {
            for (int x = offset; x < width - offset; x++) {
                int red = 0, green = 0, blue = 0;

                for (int ky = -offset; ky <= offset; ky++) {
                    for (int kx = -offset; kx <= offset; kx++) {
                        int pixel = currentImage.getRGB(x + kx, y + ky);
                        red += ((pixel >> 16) & 0xFF) * kernel[(ky + offset) * kernelSize + (kx + offset)];
                        green += ((pixel >> 8) & 0xFF) * kernel[(ky + offset) * kernelSize + (kx + offset)];
                        blue += (pixel & 0xFF) * kernel[(ky + offset) * kernelSize + (kx + offset)];
                    }
                }

                red = Math.min(255, Math.max(0, red));
                green = Math.min(255, Math.max(0, green));
                blue = Math.min(255, Math.max(0, blue));

                sharpenedImage.setRGB(x, y, (red << 16) | (green << 8) | blue);
            }
        }
        appStructure.setCurrentImage(sharpenedImage);
        repaintAndRevalidatePanel();
    }

    // Aplica o filtro de negativo na imagem, deixando ela com as cores "invertidas"
    public void invertColors() {
        this.updateValues();
        if (currentImage == null) {
            JOptionPane.showMessageDialog(appStructure.getFrame(), "No image loaded..", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        saveStateForUndo();

        for (int y = 0; y < currentImage.getHeight(); y++) {
            for (int x = 0; x < currentImage.getWidth(); x++) {
                int rgba = currentImage.getRGB(x, y);
                Color color = new Color(rgba, true);
                Color invertedColor = new Color(255 - color.getRed(), 255 - color.getGreen(), 255 - color.getBlue(), color.getAlpha());
                currentImage.setRGB(x, y, invertedColor.getRGB());
            }
        }
        appStructure.setCurrentImage(currentImage);
        repaintAndRevalidatePanel();
    }

}
