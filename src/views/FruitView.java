package views;

import slotmachine.ui.view.IView;

import javax.swing.*;
import java.awt.*;

public class FruitView implements IView {

    private String code;
    private JLabel label;
    private ImageIcon icon;

    public FruitView(String code) {
        this.code = code;

        icon = new ImageIcon("resources/fruits/" + code + ".png");
        label = new JLabel();
        label.setHorizontalAlignment(SwingConstants.CENTER);

        Image image = icon.getImage();
        Image newimg = image.getScaledInstance(30, 60,  java.awt.Image.SCALE_SMOOTH);
        icon = new ImageIcon(newimg);

        label.setIcon(icon);
    }

    @Override
    public Component getComponent() {
        return label;
    }
}
