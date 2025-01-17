package project.menubar;

import project.visual.AppStructure;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class FileMenu {
    private AppStructure appStructure;
    private BufferedImage currentImage;
    private JFrame frame;
    private JLabel imageLabel;

    public FileMenu(AppStructure appStructure) {
        this.appStructure = appStructure;
        this.currentImage = appStructure.getCurrentImage();
        this.frame = appStructure.getFrame();
        this.imageLabel = appStructure.getImageLabel();
    }

    public void loadImage() {
        JFileChooser fileChooser = new JFileChooser();
        int result = fileChooser.showOpenDialog(frame);
        if (result == JFileChooser.APPROVE_OPTION) {
            java.io.File file = fileChooser.getSelectedFile();
            try {
                appStructure.setCurrentImage(ImageIO.read(file));
                this.updateValues();
                imageLabel.setIcon(new ImageIcon(currentImage));
            } catch (IOException e) {
                JOptionPane.showMessageDialog(frame, "Erro ao carregar a imagem.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public void saveImage() {
        this.updateValues();
        if (currentImage == null) {
            JOptionPane.showMessageDialog(frame, "Imagem inexistente.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        JFileChooser fileChooser = new JFileChooser();
        int result = fileChooser.showSaveDialog(frame);
        if (result == JFileChooser.APPROVE_OPTION) {
            java.io.File file = fileChooser.getSelectedFile();
            try {
                ImageIO.write(currentImage, "png", file);
            } catch (IOException e) {
                JOptionPane.showMessageDialog(frame, "Salvamento falhou.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void updateValues() {
        this.currentImage = appStructure.getCurrentImage();
        this.frame = appStructure.getFrame();
        this.imageLabel = appStructure.getImageLabel();
    }
}
