package project.other;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class ResizeDialog extends JDialog {
    private JTextField widthField;
    private JTextField heightField;
    private boolean confirmed = false;

    // Caixa para alterar as dimensões da imagem
    public ResizeDialog(JFrame parent) {
        super(parent, "Resize Image", true);
        setLayout(new GridLayout(3, 2, 10, 10));
        setSize(300, 150);
        setLocationRelativeTo(parent);

        // Painel para largura
        JPanel widthPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        widthPanel.add(new JLabel("Width:"));
        widthField = new JTextField(5);
        widthPanel.add(widthField);
        widthPanel.add(new JLabel("px"));

        // Painel para altura
        JPanel heightPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        heightPanel.add(new JLabel("Height:"));
        heightField = new JTextField(5);
        heightPanel.add(heightField);
        heightPanel.add(new JLabel("px"));

        // Painel de botões
        JPanel buttonPanel = new JPanel();
        JButton okButton = new JButton("OK");
        okButton.addActionListener(this::onOk);
        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(e -> dispose());
        buttonPanel.add(okButton);
        buttonPanel.add(cancelButton);

        // Adicionar os painéis ao diálogo
        add(widthPanel);
        add(heightPanel);
        add(buttonPanel);

        setVisible(true);
    }

    // Quando o botão OK é clicado
    private void onOk(ActionEvent e) {
        confirmed = true; // Marca como confirmado
        dispose(); // Fecha o diálogo
    }

    public boolean isConfirmed() {
        return confirmed;
    }

    // Coleta do valor da largura
    public int getWidthValue() {
        try {
            return Integer.parseInt(widthField.getText());
        } catch (NumberFormatException ex) {
            return -1; // Retorna -1 em caso de erro
        }
    }

    // Coleta do valor da altura
    public int getHeightValue() {
        try {
            return Integer.parseInt(heightField.getText());
        } catch (NumberFormatException ex) {
            return -1; // Retorna -1 em caso de erro
        }
    }
}
