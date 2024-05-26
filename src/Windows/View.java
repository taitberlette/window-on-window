package Windows;

import WindowOnWindow.WindowOnWindow;

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
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.setFocusable(true);
        frame.setAlwaysOnTop(true);

        setSize(size);
    }

    public void update(long deltaTime) {
        frame.repaint();
    }

    public void setSize(Dimension dimension) {
        double scale = WindowOnWindow.getScale();
        Dimension scaled = new Dimension((int) (dimension.getWidth() * scale), (int) (dimension.getHeight() * scale));
        frame.setSize(scaled);
    }

    public Dimension getSize() {
        double scale = WindowOnWindow.getScale();
        Dimension scaled = frame.getSize();
        return new Dimension((int) (scaled.getWidth() / scale), (int) (scaled.getHeight() / scale));
    }

    public void setLocation(Point point) {
        double scale = WindowOnWindow.getScale();
        Point scaled = new Point((int) (point.getX() * scale), (int) (point.getY() * scale));
        frame.setLocation(scaled);
    }

    public void setLocation(int x, int y) {
        double scale = WindowOnWindow.getScale();
        frame.setLocation((int) (x * scale), (int) (y * scale));
    }

    public Point getLocation() {
        double scale = WindowOnWindow.getScale();
        Point scaled = frame.getLocation();
        return new Point((int) (scaled.getX() / scale), (int) (scaled.getY() / scale));
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
