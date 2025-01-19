package project.visual;

import project.menubar.*;

import project.shortcuts.KeyShortcuts;
import project.shortcuts.MouseShortcuts;

import java.awt.*;
import javax.swing.*;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.image.BufferedImage;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.Stack;

public class AppStructure {
    private static AppStructure instance;
    private JFrame frame;
    private JPanel imagePanel;
    private BufferedImage currentImage;
    private double zoomFactor = 0.7;
    private int imageX = 0;
    private int imageY = 0;
    private int dragStartX, dragStartY;
    private int currentBrightness = 0;
    private int currentSaturation = 0;

    private Stack<BufferedImage> undoStack = new Stack<>();
    private Stack<BufferedImage> redoStack = new Stack<>();

    private FileMenu file;
    private ImageMenu image;
    private EditMenu edit;
    private FilterMenu filter;
    private HelpMenu help;

    private KeyShortcuts keyShortcuts;
    private MouseShortcuts mouseShortcuts;

    public AppStructure() {
        setupFrame();
        setupImagePanel();
        menusInitializer();
        keyShortcuts.setupKeyboardShortcuts();
        mouseShortcuts.setupMouseShortcuts();
        
        JMenuBar menuBar = new JMenuBar();
        setupFileMenu(menuBar);
        setupImageMenu(menuBar);
        setupEditMenu(menuBar);
        setupFilterMenu(menuBar);
        setupHelpMenu(menuBar);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(imagePanel, BorderLayout.CENTER); // Área da imagem ao centro

        frame.setJMenuBar(menuBar);
        frame.add(mainPanel);

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

    public JFrame getFrame() {
        return this.frame;
    }

    public JPanel getImagePanel() {
        return this.imagePanel;
    }

    public double getZoomFactor() {
        return this.zoomFactor;
    }

    public int getImageX() {
        return this.imageX;
    }

    public int getImageY() {
        return this.imageY;
    }

    public int getDragStartX() {
        return this.dragStartX;
    }

    public int getDragStartY() {
        return this.dragStartY;
    }

    public Stack<BufferedImage> getUndoStack() {
        return this.undoStack;
    }

    public Stack<BufferedImage> getRedoStack() {
        return this.redoStack;
    }

    public FileMenu getFileMenu() {
        return this.file;
    }

    public ImageMenu getImageMenu() {
        return this.image;
    }

    public EditMenu getEditMenu() {
        return this.edit;
    }

    public FilterMenu getFilterMenu() {
        return this.filter;
    }

    public void setCurrentImage(BufferedImage currentImage) {
        this.currentImage = currentImage;
    }

    public void setZoomFactor(double zoomFactor) {
        this.zoomFactor = zoomFactor;
    }

    public void setImageX(int imageX) {
        this.imageX = imageX;
    }

    public void setImageY(int imageY) {
        this.imageY = imageY;
    }

    public void setDragStartX(int dragStartX) {
        this.dragStartX = dragStartX;
    }

    public void setDragStartY(int dragStartY) {
        this.dragStartY = dragStartY;
    }

    public void resetValues(){
        this.imageX = 0;
        this.imageY = 0;
        this.currentSaturation = 0;
        this.currentBrightness = 0;
        this.zoomFactor = 0.7;
    }

    private void setupFrame() {
        this.frame = new JFrame("Image Editor");
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.frame.setSize(800, 600);
        this.frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
    }

    private void setupImagePanel() {
        imagePanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (currentImage != null) {
                    int imgWidth = (int)(currentImage.getWidth() * zoomFactor);
                    int imgHeight = (int)(currentImage.getHeight() * zoomFactor);
                    int x = imageX + (getWidth() - imgWidth) / 2;
                    int y = imageY + (getHeight() - imgHeight) / 2;
                    g.drawImage(currentImage, x, y, imgWidth, imgHeight, this);
                }
            }
        };
        imagePanel.setBackground(new Color(87, 87, 87));
    }

    private void menusInitializer() {
        file = new FileMenu(this);
        image = new ImageMenu(this);
        edit = new EditMenu(this);
        filter = new FilterMenu(this);
        help = new HelpMenu(this);
        keyShortcuts = new KeyShortcuts(this);
        mouseShortcuts = new MouseShortcuts(this);
    }

    private void setupFileMenu(JMenuBar menuBar) {
        JMenu fileMenu = new JMenu("File");

        JMenuItem loadItem = new JMenuItem("Load Image");
        loadItem.addActionListener(e -> this.file.loadImage());

        JMenuItem saveItem = new JMenuItem("Export Image");
        saveItem.addActionListener(e -> this.file.exportImage());

        JMenuItem undoItem = new JMenuItem("Undo");
        undoItem.addActionListener(e -> this.file.undo());

        JMenuItem redoItem = new JMenuItem("Redo");
        redoItem.addActionListener(e -> this.file.redo());

        fileMenu.add(saveItem);
        fileMenu.add(loadItem);
        fileMenu.add(undoItem);
        fileMenu.add(redoItem);

        menuBar.add(fileMenu);
    }

    private void setupImageMenu(JMenuBar menuBar) {
        JMenu imageMenu = new JMenu("Image");

        JMenuItem flipHorizontalItem = new JMenuItem("Flip Horizontal");
        flipHorizontalItem.addActionListener(e -> this.image.flipImage(true));

        JMenuItem flipVerticalItem = new JMenuItem("Flip Vertical");
        flipVerticalItem.addActionListener(e -> this.image.flipImage(false));

        imageMenu.add(flipHorizontalItem);
        imageMenu.add(flipVerticalItem);

        menuBar.add(imageMenu);
    }

    private void setupEditMenu(JMenuBar menuBar) {
        JMenu editMenu = new JMenu("Edit");

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
                imagePanel.revalidate();
                imagePanel.repaint();
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

        editMenu.add(adjustBrightnessItem);
        editMenu.add(adjustSaturationItem);

        menuBar.add(editMenu);
    }

    private void setupFilterMenu(JMenuBar menuBar) {
        JMenu filterMenu = new JMenu("Filter");

        JMenuItem sepiaFilterItem = new JMenuItem("Sepia Filter");
        sepiaFilterItem.addActionListener(e -> filter.applySepiaFilter());

        JMenuItem grayScaleFilterItem = new JMenuItem("Gray Scale Filter");
        grayScaleFilterItem.addActionListener(e -> filter.applyGrayscaleFilter());

        JMenuItem embossFilterItem = new JMenuItem("Emboss Filter");
        embossFilterItem.addActionListener(e -> filter.applyEmbossFilter());

        JMenuItem sharpenFilterItem = new JMenuItem("Sharpen Filter");
        sharpenFilterItem.addActionListener(e -> filter.applySharpenFilter());

        JMenuItem negativeFilterItem = new JMenuItem("Negative Filter");
        negativeFilterItem.addActionListener(e -> filter.invertColors());

        filterMenu.add(sepiaFilterItem);
        filterMenu.add(grayScaleFilterItem);
        filterMenu.add(embossFilterItem);
        filterMenu.add(sharpenFilterItem);
        filterMenu.add(negativeFilterItem);

        menuBar.add(filterMenu);
    }

    private void setupHelpMenu(JMenuBar menuBar){
        JMenu helpMenu = new JMenu("Help");

        JMenuItem keyboardShortcutsItem = new JMenuItem("Keyboard Shortcuts");
        keyboardShortcutsItem.addActionListener(e -> help.showKeyboardShortcuts());

        JMenuItem mouseShortcutsItem = new JMenuItem("Mouse Shortcuts");
        mouseShortcutsItem.addActionListener(e -> help.showMouseShortcuts());

        JMenuItem aboutItem = new JMenuItem("About");
        aboutItem.addActionListener(e -> help.showAbout());

        helpMenu.add(keyboardShortcutsItem);
        helpMenu.add(mouseShortcutsItem);
        helpMenu.add(aboutItem);

        menuBar.add(helpMenu);
    }
}
