package Game.GameObjects.Gadgets;

import Game.GameObjects.GameObject;

import java.awt.*;
import java.util.ArrayList;

public abstract class Mechanism extends GameObject {
    protected Switch switcher;
    protected Dimension size;

    public Mechanism(Point position, Dimension size, Switch switcher) {
        super(position);
        this.size = size;
        this.switcher = switcher;
    }

    public Mechanism(ArrayList<String> data, Dimension size) {
        super(data);
    }

    public Dimension getSize() {
        return size;
    }

    public Rectangle getBounds() {
        return new Rectangle((int) (position.getX() - size.getWidth() / 2), (int) (position.getY() - size.getHeight() / 2), (int) size.getWidth(), (int) size.getHeight());
    }
}
