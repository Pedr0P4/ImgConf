package project.menubar;

import project.other.Component;
import project.visual.AppStructure;

import javax.swing.*;
import java.awt.*;

public class HelpMenu extends Component {
    public HelpMenu(AppStructure appStructure) {super(appStructure);}

    // Método para mostrar os atalhos de teclado
    public void showKeyboardShortcuts() {
        // Cria o diálogo
        JDialog dialog = new JDialog((Frame) null, "Keyboard Shortcuts", true);
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        // Configurações do layout
        dialog.setLayout(new BorderLayout());
        dialog.setSize(400, 425);
        dialog.setLocationRelativeTo(null); // Centraliza na tela

        // Título
        JLabel titleLabel = new JLabel("Keyboard Shortcuts", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        dialog.add(titleLabel, BorderLayout.NORTH);

        // Conteúdo com atalhos
        String shortcuts = """
                --------------------------------------
                File:
                
                Ctrl + L: Load File
                Ctrl + E: Export Image
                Ctrl + Z: Undo
                Ctrl + Shift + Z: Redo
                --------------------------------------
                Image:
                
                Ctrl + Shift + H: Flip Horizontal
                Ctrl + Shift + V: Flip Vertical
                --------------------------------------
                Edit:
                
                Ctrl + Shift + B: Adjust Brightness
                Ctrl + Shift + S: Adjust Saturation
                --------------------------------------
                """;

        JTextArea textArea = new JTextArea(shortcuts);
        textArea.setEditable(false);
        textArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
        dialog.add(new JScrollPane(textArea), BorderLayout.CENTER);

        // Botão de fechar
        JButton closeButton = new JButton("Close");
        closeButton.addActionListener(e -> dialog.dispose());
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(closeButton);
        dialog.add(buttonPanel, BorderLayout.SOUTH);

        // Exibe o diálogo
        dialog.setVisible(true);
    }

    // Método para mostrar os atalhos com o mouse
    public void showMouseShortcuts() {
        // Cria o diálogo
        JDialog dialog = new JDialog((Frame) null, "Keyboard Shortcuts", true);
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        // Configurações do layout
        dialog.setLayout(new BorderLayout());
        dialog.setSize(400, 300);
        dialog.setLocationRelativeTo(null); // Centraliza na tela

        // Título
        JLabel titleLabel = new JLabel("Mouse Shortcuts", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        dialog.add(titleLabel, BorderLayout.NORTH);

        // Conteúdo com atalhos
        String shortcuts = """
                Mouse Scroll: Zoom In/Out
                Click + Drag: Move Image
                """;

        JTextArea textArea = new JTextArea(shortcuts);
        textArea.setEditable(false);
        textArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
        dialog.add(new JScrollPane(textArea), BorderLayout.CENTER);

        // Botão de fechar
        JButton closeButton = new JButton("Close");
        closeButton.addActionListener(e -> dialog.dispose());
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(closeButton);
        dialog.add(buttonPanel, BorderLayout.SOUTH);

        // Exibe o diálogo
        dialog.setVisible(true);
    }

    // Método para mostrar o "Sobre" do programa
    public void showAbout() {
        // Cria o diálogo
        JDialog dialog = new JDialog((Frame) null, "About", true);
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        // Configurações do layout
        dialog.setLayout(new BorderLayout());
        dialog.setSize(650, 250);
        dialog.setLocationRelativeTo(null); // Centraliza na tela

        // Texto do About
        String aboutText = """
                ImgConf v1.0.0
                
                Um editor de imagens simples feito como trabalho de Linguagem de Programação II.
                Matéria da Universidade Federal do Rio Grande do Norte (UFRN).
                
                Desenvolvido por: Pedro Paulo Galvão Galindo de Oliveira
                Contato: pedro.galvao.090@ufrn.edu.br
                
                GitHub: https://github.com/Pedr0P4
                """;

        // Área de texto para exibir informações
        JTextArea textArea = new JTextArea(aboutText);
        textArea.setEditable(false);
        textArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
        dialog.add(new JScrollPane(textArea), BorderLayout.CENTER);

        // Botão de fechar
        JButton closeButton = new JButton("Close");
        closeButton.addActionListener(e -> dialog.dispose());
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(closeButton);
        dialog.add(buttonPanel, BorderLayout.SOUTH);

        // Exibe o diálogo
        dialog.setVisible(true);
    }
}
