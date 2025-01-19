package project.shortcuts;

import project.visual.AppStructure;

import project.other.Component;
import project.other.ActionExecutor;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class KeyShortcuts extends Component {
    public KeyShortcuts(AppStructure appStructure) {
        super(appStructure);
    }

    public void setupKeyboardShortcuts() {
        keyShortcut("control Z", "Undo", () -> appStructure.getFileMenu().undo());
        keyShortcut("control shift Z", "Redo", () -> appStructure.getFileMenu().redo());
        keyShortcut("control E", "Export Image", () -> appStructure.getFileMenu().exportImage());
        keyShortcut("control L", "Load Image", () -> appStructure.getFileMenu().loadImage());
        keyShortcut("control shift H", "Flip Horizontal", () -> appStructure.getImageMenu().flipImage(true));
        keyShortcut("control shift V", "Flip Vertical", () -> appStructure.getImageMenu().flipImage(false));
    }

    private void keyShortcut(String shortcut, String shortcutName, ActionExecutor actionExecutor){
        InputMap inputMap = appStructure.getImagePanel().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        ActionMap actionMap = appStructure.getImagePanel().getActionMap();

        inputMap.put(KeyStroke.getKeyStroke(shortcut), shortcutName);

        actionMap.put(shortcutName, new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                actionExecutor.execute();
            }
        });
    }
}
