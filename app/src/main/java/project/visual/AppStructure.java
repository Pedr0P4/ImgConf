package project.visual;

import project.menubar.EditMenu;
import project.menubar.FileMenu;

import java.awt.*;
import java.awt.image.BufferedImage;
import javax.swing.*;

public class AppStructure {
    private static AppStructure instance;
    private JFrame frame;
    private JLabel imageLabel;
    private BufferedImage currentImage;
    private int currentBrightness = 0;
    private int currentSaturation = 0;

    private FileMenu file;
    private EditMenu edit;

    public AppStructure() {
        frame = new JFrame("Image Editor");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);

        imageLabel = new JLabel();
        imageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        frame.add(new JScrollPane(imageLabel), BorderLayout.CENTER);

        file = new FileMenu(this);
        edit = new EditMenu(this);
        
        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");
        JMenu editMenu = new JMenu("Edit");

        JMenuItem loadItem = new JMenuItem("Load Image");
        loadItem.addActionListener(e -> this.file.loadImage());

        JMenuItem saveItem = new JMenuItem("Save Image");
        saveItem.addActionListener(e -> this.file.saveImage());

        JMenuItem invertColorsItem = new JMenuItem("Invert Colors");
        invertColorsItem.addActionListener(e -> this.edit.invertColors());

        JMenuItem adjustBrightnessItem = new JMenuItem("Adjust Brightness");
        adjustBrightnessItem.addActionListener(e -> {
            JSlider slider = new JSlider(JSlider.HORIZONTAL, -100, 100, currentBrightness);
            slider.setMajorTickSpacing(50);
            slider.setMinorTickSpacing(10);
            slider.setPaintTicks(true);
            slider.setPaintLabels(true);

            int result = JOptionPane.showConfirmDialog(
                    frame,
                    slider,
                    "Adjust Brightness",
                    JOptionPane.OK_CANCEL_OPTION,
                    JOptionPane.PLAIN_MESSAGE
            );

            if (result == JOptionPane.OK_OPTION) {
                this.currentBrightness = slider.getValue();
                edit.adjustBrightness(this.currentBrightness);
            }
        });

        JMenuItem adjustSaturationItem = new JMenuItem("Adjust Saturation");
        adjustSaturationItem.addActionListener(e -> {
            JSlider slider = new JSlider(JSlider.HORIZONTAL, -100, 100, currentSaturation);
            slider.setMajorTickSpacing(50);
            slider.setMinorTickSpacing(10);
            slider.setPaintTicks(true);
            slider.setPaintLabels(true);

            int result = JOptionPane.showConfirmDialog(
                    frame,
                    slider,
                    "Adjust Saturation",
                    JOptionPane.OK_CANCEL_OPTION,
                    JOptionPane.PLAIN_MESSAGE
            );

            if (result == JOptionPane.OK_OPTION) {
                this.currentSaturation = slider.getValue();
                edit.adjustSaturation(this.currentSaturation);
            }
        });

        fileMenu.add(saveItem);
        fileMenu.add(loadItem);
        editMenu.add(invertColorsItem);
        editMenu.add(adjustBrightnessItem);
        editMenu.add(adjustSaturationItem);

        menuBar.add(fileMenu);
        menuBar.add(editMenu);

        frame.setJMenuBar(menuBar);

        frame.setVisible(true);
    }

    public static AppStructure getInstance() {
        if (instance == null) {
            instance = new AppStructure();
        }
        return instance;
    }

    public BufferedImage getCurrentImage() {
        return this.currentImage;
    }

    public JLabel getImageLabel() {
        return this.imageLabel;
    }

    public JFrame getFrame() {
        return this.frame;
    }

    public void setCurrentImage(BufferedImage currentImage) {
        this.currentImage = currentImage;
    }
}
