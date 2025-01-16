package main;

import javax.swing.*;
import app.ImageEditor;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(ImageEditor::new);
    }
}
