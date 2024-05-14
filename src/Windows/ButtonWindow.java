package Windows;

import WindowOnWindow.WindowOnWindow;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class ButtonWindow extends Panel implements MouseListener {
    private String text;
    private boolean pressed = false;

    public ButtonWindow(String text, String title) {
        this.text = text;
        this.view = new View(title, new Dimension(200, 200), this);
        addMouseListener(this);
    }

    public void draw(Graphics2D graphics2D, Dimension size) {
        Font font = WindowOnWindow.getDefaultFont();

        graphics2D.setColor(Color.BLACK);
        graphics2D.setFont(font);

        FontMetrics fontMetrics = graphics2D.getFontMetrics(font);

        int textWidth = fontMetrics.stringWidth(text);

        graphics2D.drawString(text, (int) ((size.getWidth() - textWidth) / 2), (int) (size.getHeight() / 2));
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
