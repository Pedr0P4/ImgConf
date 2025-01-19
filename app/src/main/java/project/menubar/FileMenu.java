package project.menubar;

import project.other.Component;
import project.visual.AppStructure;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;
import java.io.IOException;

public class FileMenu extends Component {
    public FileMenu(AppStructure appStructure) {
        super(appStructure);
    }

    // Método para exportar a imagem para PNG, JPEG ou BMP.
    public void exportImage() {
        updateValues();
        if (currentImage == null) {
            JOptionPane.showMessageDialog(frame, "No image to export.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Export Image");

        FileNameExtensionFilter pngFilter = new FileNameExtensionFilter("PNG Image (*.png)", "png");
        FileNameExtensionFilter jpgFilter = new FileNameExtensionFilter("JPEG Image (*.jpg, *.jpeg)", "jpg", "jpeg");
        FileNameExtensionFilter bmpFilter = new FileNameExtensionFilter("BMP Image (*.bmp)", "bmp");

        fileChooser.addChoosableFileFilter(pngFilter);
        fileChooser.addChoosableFileFilter(jpgFilter);
        fileChooser.addChoosableFileFilter(bmpFilter);
        fileChooser.setFileFilter(pngFilter);

        int userChoice = fileChooser.showSaveDialog(frame);

        if (userChoice == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            String selectedExtension = ((FileNameExtensionFilter) fileChooser.getFileFilter()).getExtensions()[0];
            String filePath = file.getAbsolutePath();

            if (!filePath.endsWith("." + selectedExtension)) {
                filePath += "." + selectedExtension;
                file = new File(filePath);
            }

            try {
                if (!ImageIO.write(currentImage, selectedExtension, file)) {
                    throw new IOException("Unsupported file format: " + selectedExtension);
                }
                JOptionPane.showMessageDialog(frame, "Image exported successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
            } catch (IOException e) {
                JOptionPane.showMessageDialog(frame, "Failed to export image: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    // Método para carregar a imagem para a edição (demais métodos não funcionam sem carregar uma imagem)
    public void loadImage() {
        JFileChooser fileChooser = new JFileChooser();
        int result = fileChooser.showOpenDialog(frame);
        if (result == JFileChooser.APPROVE_OPTION) {
            java.io.File file = fileChooser.getSelectedFile();
            try {
                appStructure.setCurrentImage(ImageIO.read(file));
                this.updateValues();
            } catch (IOException e) {
                JOptionPane.showMessageDialog(frame, "Failed to load image.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
        appStructure.resetValues();
        appStructure.updateImageInfo();
        repaintAndRevalidatePanel();
    }

    // Método para desfazer uma alteração
    public void undo() {
        updateValues();
        if (!appStructure.getUndoStack().isEmpty()) {
            appStructure.getRedoStack().push(copyImage(currentImage));
            appStructure.setCurrentImage(appStructure.getUndoStack().pop());
            repaintAndRevalidatePanel();
        } else {
            JOptionPane.showMessageDialog(frame, "No more actions to undo.", "Undo", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    // Método para refazer uma alteração desfeita
    public void redo() {
        updateValues();
        if (!appStructure.getRedoStack().isEmpty()) {
            appStructure.getUndoStack().push(copyImage(currentImage));
            appStructure.setCurrentImage(appStructure.getRedoStack().pop());
            repaintAndRevalidatePanel();
        } else {
            JOptionPane.showMessageDialog(frame, "No more actions to redo.", "Redo", JOptionPane.INFORMATION_MESSAGE);
        }
    }
}
