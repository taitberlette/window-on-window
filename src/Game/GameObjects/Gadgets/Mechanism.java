package Game.GameObjects.Gadgets;

import Game.GameObjects.GameObject;
import Game.Worlds.World;

import java.awt.*;
import java.util.ArrayList;

public abstract class Mechanism extends GameObject {
    protected int switcherId;
    protected Dimension size;
    protected World world;

    public Mechanism(Point position, Dimension size, int switcherId, World world) {
        super(position);
        this.size = size;
        this.switcherId = switcherId;
        this.world = world;
    }

    public Mechanism(ArrayList<String> data, Dimension size, World world) {
        super(data);
        this.size = size;
        this.world = world;
    }

    public Dimension getSize() {
        return size;
    }

    public Rectangle getBounds() {
        return new Rectangle((int) (position.getX() - size.getWidth() / 2), (int) (position.getY() - size.getHeight() / 2), (int) size.getWidth(), (int) size.getHeight());
    }
}
