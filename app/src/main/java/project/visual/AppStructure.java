package project.visual;

import project.menubar.EditMenu;
import project.menubar.FileMenu;
import project.menubar.ImageMenu;
import project.menubar.FilterMenu;

import java.awt.*;
import javax.swing.*;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.image.BufferedImage;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

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

    private FileMenu file;
    private ImageMenu image;
    private EditMenu edit;
    private FilterMenu filter;

    public AppStructure() {
        frame = new JFrame("Image Editor");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);

        // Painel para exibir a imagem
        imagePanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (currentImage != null) {
                    // Centralizar a imagem na área disponível
                    int imgWidth = (int)(currentImage.getWidth() * zoomFactor);
                    int imgHeight = (int)(currentImage.getHeight() * zoomFactor);
                    int x = imageX + (getWidth() - imgWidth) / 2;
                    int y = imageY + (getHeight() - imgHeight) / 2;
                    g.drawImage(currentImage, x, y, imgWidth, imgHeight, this);
                }
            }
        };
        imagePanel.setBackground(new Color(87, 87, 87));

        file = new FileMenu(this);
        image = new ImageMenu(this);
        edit = new EditMenu(this);
        filter = new FilterMenu(this);
        
        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");
        JMenu imageMenu = new JMenu("Image");
        JMenu editMenu = new JMenu("Edit");
        JMenu filterMenu = new JMenu("Filter");

        JMenuItem loadItem = new JMenuItem("Load Image");
        loadItem.addActionListener(e -> this.file.loadImage());

        JMenuItem saveItem = new JMenuItem("Save Image");
        saveItem.addActionListener(e -> this.file.saveImage());

        JMenuItem flipHorizontalItem = new JMenuItem("Flip Horizontal");
        flipHorizontalItem.addActionListener(e -> this.image.flipImage(true));

        JMenuItem flipVerticalItem = new JMenuItem("Flip Vertical");
        flipVerticalItem.addActionListener(e -> this.image.flipImage(false));

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

        JMenuItem sepiaFilterItem = new JMenuItem("Sepia Filter");
        sepiaFilterItem.addActionListener(e -> filter.applySepiaFilter());

        JMenuItem grayScaleFilterItem = new JMenuItem("Gray Scale Filter");
        grayScaleFilterItem.addActionListener(e -> filter.applyGrayscaleFilter());

        JMenuItem embossFilterItem = new JMenuItem("Emboss Filter");
        embossFilterItem.addActionListener(e -> filter.applyEmbossFilter());

        JMenuItem sharpenFilterItem = new JMenuItem("Sharpen Filter");
        sharpenFilterItem.addActionListener(e -> filter.applySharpenFilter());

        // Painel principal com layout BorderLayout
        JPanel mainPanel = new JPanel(new BorderLayout());

        // Painel lateral para ícones ou ferramentas
        JPanel sidebar = new JPanel();
        sidebar.setPreferredSize(new Dimension(100, 0)); // 100px de largura
        sidebar.setBackground(Color.LIGHT_GRAY);
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));

        // Adicionar botões ou ícones na barra lateral
        JButton btnTool1 = new JButton("Tool 1");
        JButton btnTool2 = new JButton("Tool 2");
        sidebar.add(btnTool1);
        sidebar.add(Box.createRigidArea(new Dimension(0, 10))); // Espaço entre os botões
        sidebar.add(btnTool2);

        // Adicionar suporte ao zoom com a rolagem do mouse
        imagePanel.addMouseWheelListener(new MouseWheelListener() {
            @Override
            public void mouseWheelMoved(MouseWheelEvent e) {
                if (e.getPreciseWheelRotation() < 0 && zoomFactor < 5.0) {
                    zoomFactor *= 1.1; // Zoom in
                } else if(e.getPreciseWheelRotation() > 0 && zoomFactor > -5.0) {
                    zoomFactor /= 1.1; // Zoom out
                }
                zoomFactor = Math.max(0.1, zoomFactor); // Evitar zoom negativo ou muito pequeno
                imagePanel.repaint(); // Atualizar o painel
            }
        });

        // Adicionar suporte ao clique e arraste para mover a imagem
        imagePanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                dragStartX = e.getX();
                dragStartY = e.getY();
            }
        });

        imagePanel.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                int deltaX = e.getX() - dragStartX;
                int deltaY = e.getY() - dragStartY;

                imageX += deltaX;
                imageY += deltaY;

                dragStartX = e.getX();
                dragStartY = e.getY();

                imagePanel.repaint(); // Atualizar a exibição da imagem
            }
        });

        // Adicionar componentes ao painel principal
        mainPanel.add(sidebar, BorderLayout.WEST); // Barra lateral à esquerda
        mainPanel.add(imagePanel, BorderLayout.CENTER); // Área da imagem ao centro

        fileMenu.add(saveItem);
        fileMenu.add(loadItem);
        imageMenu.add(flipHorizontalItem);
        imageMenu.add(flipVerticalItem);
        editMenu.add(invertColorsItem);
        editMenu.add(adjustBrightnessItem);
        editMenu.add(adjustSaturationItem);
        filterMenu.add(sepiaFilterItem);
        filterMenu.add(grayScaleFilterItem);
        filterMenu.add(embossFilterItem);
        filterMenu.add(sharpenFilterItem);

        menuBar.add(fileMenu);
        menuBar.add(imageMenu);
        menuBar.add(editMenu);
        menuBar.add(filterMenu);

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

    public void setCurrentImage(BufferedImage currentImage) {
        this.currentImage = currentImage;
    }

    public JPanel getImagePanel() {
        return this.imagePanel;
    }

    public void resetValues(){
        this.imageX = 0;
        this.imageY = 0;
        this.currentSaturation = 0;
        this.currentBrightness = 0;
        this.zoomFactor = 0.7;
    }
}
