package project.main;

import javax.swing.*;
import project.visual.AppStructure;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(AppStructure::new);
    }
}
