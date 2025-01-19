package project.other;

import project.visual.AppStructure;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class Component {
    protected AppStructure appStructure;
    protected BufferedImage currentImage;
    protected JPanel imagePanel;
    protected JFrame frame;

    // Construtor
    public Component(AppStructure appStructure) {
        this.appStructure = appStructure;
        this.currentImage = appStructure.getCurrentImage();
        this.imagePanel = appStructure.getImagePanel();
        this.frame = appStructure.getFrame();
    }

    // Atualiza os valores de acordo com os valores atuais do app
    protected void updateValues() {
        this.currentImage = appStructure.getCurrentImage();
        this.imagePanel = appStructure.getImagePanel();
        this.frame = appStructure.getFrame();
    }

    // Redesenha o imagePanel (para atualizar possiveis alterações)
    protected void repaintAndRevalidatePanel(){
        appStructure.getImagePanel().repaint();
        appStructure.getImagePanel().revalidate();
    }

    // Salva o estado atual na stack de Undo, para que seja possível refazer os passos feitos
    protected void saveStateForUndo() {
        if (currentImage != null) {
            appStructure.getUndoStack().push(copyImage(currentImage));
            appStructure.getRedoStack().clear();
        }
    }

    // Faz uma cópia do estado atual
    protected BufferedImage copyImage(BufferedImage source) {
        BufferedImage copy = new BufferedImage(source.getWidth(), source.getHeight(), source.getType());
        Graphics g = copy.getGraphics();
        g.drawImage(source, 0, 0, null);
        g.dispose();
        return copy;
    }
}
