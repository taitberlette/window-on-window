package Windows;

import java.awt.*;
import WindowOnWindow.WindowOnWindow;

public class TextboxWindow extends Panel {
    private String text;

    public TextboxWindow(String text) {
        this.text = text;
        this.view = new View("", new Dimension(200, 200), this);
    }

    public void draw(Graphics2D graphics2D, Dimension size) {
        Font font = WindowOnWindow.getDefaultFont();

        graphics2D.setColor(Color.BLACK);
        graphics2D.setFont(font);

        FontMetrics fontMetrics = graphics2D.getFontMetrics(font);

        int textWidth = fontMetrics.stringWidth(text);

        graphics2D.drawString(text, (int) ((size.getWidth() - textWidth) / 2), (int) (size.getHeight() / 2));
    }
}