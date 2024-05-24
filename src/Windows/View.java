package Windows;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class View {
    private JFrame frame;

    private KeyListener listener = null;

    public View( Dimension size, JPanel panel) {
        frame = new JFrame();

        frame.add(panel);
        frame.setUndecorated(true);
        frame.setResizable(false);
        frame.setSize((int) size.getWidth(), (int) size.getHeight());
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.setFocusable(true);
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

    public void setLocation(int x, int y) {
        frame.setLocation(x, y);
    }

    public Point getLocation() {
        return frame.getLocation();
    }

    public void setVisible(boolean visible) {
        frame.setVisible(visible);
        frame.setFocusable(true);

        if(listener != null) {
            frame.addKeyListener(listener);
        }
    }

    public void setListener(KeyListener listener) {
        this.listener = listener;
    }

    public JFrame getFrame() {
        return frame;
    }
}
