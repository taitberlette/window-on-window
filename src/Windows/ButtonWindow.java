package Windows;

import WindowOnWindow.WindowOnWindow;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class ButtonWindow extends Panel implements MouseListener {
    private String text;
    private boolean pressed = false;

    private Dimension defaultDimension = new Dimension(344, 128);

    public ButtonWindow(String text, String title, Point location) {
        super(title);
        this.text = text;
        this.view = new View(defaultDimension, this);
        this.view.setLocation(location);
        addMouseListener(this);
    }

    public void draw(Graphics2D graphics2D, Dimension size) {
        Font font = WindowOnWindow.getTextFont();

        graphics2D.setColor(Color.BLACK);
        graphics2D.setFont(font);

        FontMetrics fontMetrics = graphics2D.getFontMetrics(font);

        int textWidth = fontMetrics.stringWidth(text);

        graphics2D.drawString(text, (int) ((size.getWidth() - textWidth) / 2), (int) ((size.getHeight() - (TITLE_BAR_HEIGHT / 2)) / 2) + TITLE_BAR_HEIGHT);
    }

    public boolean wasClicked() {
        return pressed;
    }

    public void resetClicked() {
        pressed = false;
    }

    public void mouseClicked(MouseEvent e) {
        pressed = true;
    }

    public void mousePressed(MouseEvent e) {

    }

    public void mouseReleased(MouseEvent e) {

    }

    public void mouseEntered(MouseEvent e) {

    }

    public void mouseExited(MouseEvent e) {

    }
}
