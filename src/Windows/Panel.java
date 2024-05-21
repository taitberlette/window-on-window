package Windows;

import WindowOnWindow.WindowOnWindow;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public abstract class Panel extends JPanel {
    protected View view;
    protected String title;

    protected final int TITLE_BAR_HEIGHT = 30;

    public Panel(String title) {
        this.title = title;
    }

    public void update(long deltaTime) {
        if(view != null) {
            view.update(deltaTime);
        }
    }

    protected abstract void draw(Graphics2D graphics2D, Dimension size);

    public void paint(Graphics graphics) {
        Graphics2D graphics2D = (Graphics2D) graphics;

        graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        Dimension size = getSize();

        graphics2D.setColor(Color.LIGHT_GRAY);
        graphics2D.fillRect(0, 0, (int) size.getWidth(), TITLE_BAR_HEIGHT);

        Font font = WindowOnWindow.getTitleFont();

        graphics2D.setColor(Color.BLACK);
        graphics2D.setFont(font);

        int yPosition = (TITLE_BAR_HEIGHT + 15) / 2;

        graphics2D.drawString(title, 10, yPosition);
        graphics2D.drawString("x", (int) (size.getWidth() - 20), yPosition);

        graphics2D.setClip(0, TITLE_BAR_HEIGHT, (int) size.getWidth(), (int) (size.getHeight() - TITLE_BAR_HEIGHT));

        draw(graphics2D, size);

    }

    public void setLocation(Point point) {
        view.setLocation(point);
    }

    public void setVisible(boolean visible) {
        view.setVisible(visible);
    }

    public void setKeyListener(KeyListener keyListener) {
        view.setListener(keyListener);
    }
}
