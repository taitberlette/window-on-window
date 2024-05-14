package Windows;

import javax.swing.*;
import java.awt.*;

public class View {
    private JFrame frame;

    protected final int SCREEN_WIDTH = 1920, SCREEN_HEIGHT = 1080;

    public View(String title, Dimension size, JPanel panel) {
        frame = new JFrame(title);

        frame.add(panel);
        frame.setResizable(false);
        frame.setSize((int) size.getWidth(), (int) size.getHeight());
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
    }

    public void update(long deltaTime) {
        frame.repaint();
    }
}
