package Windows;

import javax.swing.*;
import java.awt.*;

public abstract class Panel extends JPanel {
    protected View view;

    public void update(long deltaTime) {
        if(view != null) {
            view.update(deltaTime);
        }
    }

    protected abstract void draw(Graphics2D graphics2D, Dimension size);

    public void paint(Graphics graphics) {
        Graphics2D graphics2D = (Graphics2D) graphics;

        graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);


        draw(graphics2D, getSize());
    }
}
