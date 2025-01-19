package project.menubar;

import project.visual.AppStructure;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class FileMenu extends Menu {
    public FileMenu(AppStructure appStructure) {
        super(appStructure);
    }

    public void exportImage() {
        updateValues();
        if (currentImage == null) {
            JOptionPane.showMessageDialog(frame, "No image to export.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Export Image");

        // Adicionar filtros para formatos suportados
        FileNameExtensionFilter pngFilter = new FileNameExtensionFilter("PNG Image (*.png)", "png");
        FileNameExtensionFilter jpgFilter = new FileNameExtensionFilter("JPEG Image (*.jpg, *.jpeg)", "jpg", "jpeg");
        FileNameExtensionFilter bmpFilter = new FileNameExtensionFilter("BMP Image (*.bmp)", "bmp");

        fileChooser.addChoosableFileFilter(pngFilter);
        fileChooser.addChoosableFileFilter(jpgFilter);
        fileChooser.addChoosableFileFilter(bmpFilter);
        fileChooser.setFileFilter(pngFilter); // PNG como padrão

        int userChoice = fileChooser.showSaveDialog(frame);

        if (userChoice == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            String selectedExtension = ((FileNameExtensionFilter) fileChooser.getFileFilter()).getExtensions()[0];
            String filePath = file.getAbsolutePath();

            // Adicionar extensão automaticamente, se necessário
            if (!filePath.endsWith("." + selectedExtension)) {
                filePath += "." + selectedExtension;
                file = new File(filePath);
            }

            try {
                // Salvar a imagem no formato selecionado
                if (!ImageIO.write(currentImage, selectedExtension, file)) {
                    throw new IOException("Unsupported file format: " + selectedExtension);
                }
                JOptionPane.showMessageDialog(frame, "Image exported successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
            } catch (IOException e) {
                JOptionPane.showMessageDialog(frame, "Failed to export image: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public void loadImage() {
        JFileChooser fileChooser = new JFileChooser();
        int result = fileChooser.showOpenDialog(frame);
        if (result == JFileChooser.APPROVE_OPTION) {
            java.io.File file = fileChooser.getSelectedFile();
            try {
                appStructure.setCurrentImage(ImageIO.read(file));
                this.updateValues();
            } catch (IOException e) {
                JOptionPane.showMessageDialog(frame, "Erro ao carregar a imagem.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
        appStructure.resetValues();
        appStructure.getImagePanel().repaint();
        appStructure.getImagePanel().revalidate();
    }

    public void undo() {
        updateValues();
        if (!appStructure.getUndoStack().isEmpty()) {
            appStructure.getRedoStack().push(copyImage(currentImage)); // Salvar o estado atual no Redo
            appStructure.setCurrentImage(appStructure.getUndoStack().pop()); // Recuperar o último estado do Undo
            repaintAndRevalidatePanel(); // Atualizar o painel
        } else {
            JOptionPane.showMessageDialog(frame, "No more actions to undo.", "Undo", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    public void redo() {
        updateValues();
        if (!appStructure.getRedoStack().isEmpty()) {
            appStructure.getUndoStack().push(copyImage(currentImage)); // Salvar o estado atual no Undo
            appStructure.setCurrentImage(appStructure.getRedoStack().pop()); // Recuperar o último estado do Redo
            repaintAndRevalidatePanel(); // Atualizar o painel
        } else {
            JOptionPane.showMessageDialog(frame, "No more actions to redo.", "Redo", JOptionPane.INFORMATION_MESSAGE);
        }
    }
}
