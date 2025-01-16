package app;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;

public class ImageEditor {
    private JFrame frame;
    private JLabel imageLabel;
    private BufferedImage currentImage;

    public ImageEditor() {
        frame = new JFrame("Image Editor");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        
        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");
        JMenu editMenu = new JMenu("Edit");

        JMenuItem loadItem = new JMenuItem("Load Image");
        loadItem.addActionListener(e -> loadImage());

        JMenuItem saveItem = new JMenuItem("Save Image");
        saveItem.addActionListener(e -> saveImage());

        JMenuItem invertColorsItem = new JMenuItem("Invert Colors");
        invertColorsItem.addActionListener(e -> invertColors());

        fileMenu.add(loadItem);
        fileMenu.add(saveItem);
        editMenu.add(invertColorsItem);

        menuBar.add(fileMenu);
        menuBar.add(editMenu);

        frame.setJMenuBar(menuBar);

        imageLabel = new JLabel();
        imageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        frame.add(new JScrollPane(imageLabel), BorderLayout.CENTER);

        frame.setVisible(true);
    }

    private void loadImage() {
        JFileChooser fileChooser = new JFileChooser();
        int result = fileChooser.showOpenDialog(frame);
        if (result == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            try {
                currentImage = ImageIO.read(file);
                imageLabel.setIcon(new ImageIcon(currentImage));
            } catch (IOException e) {
                JOptionPane.showMessageDialog(frame, "Erro ao carregar a imagem.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void saveImage() {
        if (currentImage == null) {
            JOptionPane.showMessageDialog(frame, "Imagem inexistente.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        JFileChooser fileChooser = new JFileChooser();
        int result = fileChooser.showSaveDialog(frame);
        if (result == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            try {
                ImageIO.write(currentImage, "png", file);
            } catch (IOException e) {
                JOptionPane.showMessageDialog(frame, "Salvamento falhou.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void invertColors() {
        if (currentImage == null) {
            JOptionPane.showMessageDialog(frame, "Carregamento da imagem falhou.", "Error", JOptionPane.ERROR_MESSAGE);
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

        imageLabel.setIcon(new ImageIcon(currentImage));
    }
}
