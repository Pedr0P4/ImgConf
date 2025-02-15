package project.visual;

import project.menubar.*;

import project.other.ResizeDialog;
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
    private JLabel imageInfoLabel;

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
        imageInfoLabelInitializer();
        keyShortcuts.initializeShortcuts();
        mouseShortcuts.initializeShortcuts();
        
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

    // Reseta a posição e zoom da imagem
    public void resetValues(){
        this.imageX = 0;
        this.imageY = 0;
        this.zoomFactor = 0.7;
    }

    // Inicializa o frame
    private void setupFrame() {
        this.frame = new JFrame("Image Editor");
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.frame.setSize(800, 600);
        this.frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
    }

    // Inicializa o imagePanel (onde a imagem será exibida)
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

    // Inicializa os menus e atalhos
    private void menusInitializer() {
        file = new FileMenu(this);
        image = new ImageMenu(this);
        edit = new EditMenu(this);
        filter = new FilterMenu(this);
        help = new HelpMenu(this);
        keyShortcuts = new KeyShortcuts(this);
        mouseShortcuts = new MouseShortcuts(this);
    }

    // inicializa as infos da imagem que ficarão do rodapé do programa
    private void imageInfoLabelInitializer() {
        imageInfoLabel = new JLabel("No image loaded."); // Texto inicial
        imageInfoLabel.setHorizontalAlignment(SwingConstants.CENTER); // Centralizado
        frame.add(imageInfoLabel, BorderLayout.SOUTH); // Adiciona ao rodapé
    }

    // Inicializa o menu File
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

    // Inicializa o menu Image
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

    // Inicializa o menu Edit
    private void setupEditMenu(JMenuBar menuBar) {
        JMenu editMenu = new JMenu("Edit");

        JMenuItem adjustBrightnessItem = new JMenuItem("Adjust Brightness");
        adjustBrightnessItem.addActionListener(e -> {
            JSlider slider = new JSlider(JSlider.HORIZONTAL, -100, 100, 0);
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
                int brightness = slider.getValue();
                edit.adjustBrightness(brightness);
                imagePanel.revalidate();
                imagePanel.repaint();
            }
        });

        JMenuItem adjustSaturationItem = new JMenuItem("Adjust Saturation");
        adjustSaturationItem.addActionListener(e -> {
            JSlider slider = new JSlider(JSlider.HORIZONTAL, -100, 100, 0);
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
                int saturation = slider.getValue();
                edit.adjustSaturation(saturation);
            }
        });

        JMenuItem resizeImageItem = new JMenuItem("Resize Image");
        resizeImageItem.addActionListener(e -> {
            ResizeDialog dialog = new ResizeDialog(frame); // Passa o frame principal como pai
            if (dialog.isConfirmed()) {
                int newWidth = dialog.getWidthValue();
                int newHeight = dialog.getHeightValue();

                if (newWidth > 0 && newHeight > 0) {
                    edit.resizeImage(newWidth, newHeight); // Chama a função de redimensionamento
                } else {
                    JOptionPane.showMessageDialog(frame, "Invalid dimensions entered.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        editMenu.add(adjustBrightnessItem);
        editMenu.add(adjustSaturationItem);
        editMenu.add(resizeImageItem);

        menuBar.add(editMenu);
    }

    // Inicializa o menu Filter
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

    // Inicializa o menu Help
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

    // Atualiza as informações da imagem para ser exibida no rodapé do programa
    public void updateImageInfo() {
        if (currentImage != null) {
            int width = currentImage.getWidth();
            int height = currentImage.getHeight();
            imageInfoLabel.setText("Width: " + width + " px, Height: " + height + " px");
        } else {
            imageInfoLabel.setText("No image loaded.");
        }
    }
}
