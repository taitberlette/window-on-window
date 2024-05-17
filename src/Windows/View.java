package Windows;

import javax.swing.*;
import java.awt.*;

public class View {
    private JFrame frame;

    protected final int SCREEN_WIDTH = 1920, SCREEN_HEIGHT = 1080;

    public View( Dimension size, JPanel panel) {
        frame = new JFrame();

        frame.add(panel);
        frame.setUndecorated(true);
        frame.setResizable(false);
        frame.setSize((int) size.getWidth(), (int) size.getHeight());
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.setAlwaysOnTop(true);
    }

    public void update(long deltaTime) {
        frame.repaint();
    }

    public void setSize(Dimension dimension) {
        frame.setSize(dimension);
    }

    public Dimension getSize() {
        return frame.getSize();
    }

    public void setLocation(Point point) {
        frame.setLocation(point);
    }

    public Point getLocation() {
        return frame.getLocation();
    }

    public void setVisible(boolean visible) {
        frame.setVisible(visible);
    }
}
