package Windows;

import WindowOnWindow.WindowOnWindow;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public abstract class Panel extends JPanel {
    protected View view;
    protected String title;
    protected boolean drawTitleBar;

    protected final int TITLE_BAR_HEIGHT = 30;

    public Panel(String title, boolean drawTitleBar) {
        this.title = title;
        this.drawTitleBar = drawTitleBar;
    }

    public Panel(String title) {
        this.title = title;
        drawTitleBar = true;
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

        if(drawTitleBar) {
            double scale = WindowOnWindow.getScale();

            graphics2D.setColor(Color.LIGHT_GRAY);
            graphics2D.fillRect(0, 0, (int) ((int) size.getWidth()), (int) (TITLE_BAR_HEIGHT * scale));

            Font font = WindowOnWindow.getTitleFont();

            graphics2D.setColor(Color.BLACK);
            graphics2D.setFont(font);

            int yPosition = (int) ((((TITLE_BAR_HEIGHT + 15) * scale) / 2));

            graphics2D.drawString(title, (int) (10 * scale), yPosition);
            graphics2D.drawString("x", (int) (((int) ((size.getWidth()) - (20 * scale)))), yPosition);

            graphics2D.setClip(0, (int) ((TITLE_BAR_HEIGHT) * scale), (int) (((int) size.getWidth())), (int) ((int) (size.getHeight() - (TITLE_BAR_HEIGHT * scale))));
        }

        draw(graphics2D, size);

    }

    public void setLocation(Point point) {
        view.setLocation(point);
    }

    public void setLocation(int x, int y) {
        view.setLocation(x, y);
    }

    public void setVisible(boolean visible) {
        view.setVisible(visible);
    }

    public void setKeyListener(KeyListener keyListener) {
        view.setListener(keyListener);
    }

    public View getView() {
        return view;
    }
}
