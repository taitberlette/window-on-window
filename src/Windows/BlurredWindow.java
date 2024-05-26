package Windows;

import WindowOnWindow.WindowOnWindow;

import javax.swing.*;
import java.awt.*;

public class BlurredWindow extends Panel {
    public BlurredWindow() {
        super("", false);

        this.view = new View(WindowOnWindow.getRenderingSize(), this);
        this.view.setLocation(new Point(0, 0));

        JFrame frame = this.view.getFrame();
        frame.setOpacity(0.75f);
    }

    protected void draw(Graphics2D graphics2D, Dimension size) {
        graphics2D.setColor(Color.DARK_GRAY);
        graphics2D.fillRect(0, 0, (int) WindowOnWindow.getMonitorSize().getWidth(), (int) WindowOnWindow.getMonitorSize().getHeight());
    }
}
