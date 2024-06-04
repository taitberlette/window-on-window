package Windows;

import WindowOnWindow.WindowOnWindow;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;

public class ImageWindow extends Panel {
    private BufferedImage image;

    public ImageWindow(String title, BufferedImage image, Point location) {
        super(title);
        this.image = image;
        this.view = new View(new Dimension(image.getWidth(), image.getHeight() + TITLE_BAR_HEIGHT), this);
        this.view.setLocation(location);
    }

    public void draw(Graphics2D graphics2D, Dimension size) {
        double scale = WindowOnWindow.getScale();
        graphics2D.drawImage((Image) image, 0, (int) (TITLE_BAR_HEIGHT * scale), (int) (image.getWidth() * scale), (int) (image.getHeight() * scale), null);
    }

    public void changeImage(BufferedImage image) {
        this.image = image;

        this.setSize(image.getWidth(), image.getHeight() + TITLE_BAR_HEIGHT);
    }
}
