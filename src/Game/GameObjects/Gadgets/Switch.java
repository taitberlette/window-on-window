package Game.GameObjects.Gadgets;

import Game.GameObjects.GameObject;

import java.awt.*;

public class Switch extends GameObject {
    protected Dimension size;
    protected boolean activated;

    public Switch(Point position, Dimension size) {
        this.position = position;
        this.size = size;
    }

    public Dimension getSize() {
        return size;
    }

    public Rectangle getBounds() {
        return new Rectangle((int) (position.getX() - size.getWidth() / 2), (int) (position.getY() - size.getHeight() / 2), (int) size.getWidth(), (int) size.getHeight());
    }

    public void toggle() {
        activated = !activated;
        System.out.println("TOGGLE TARGET");
    }

    public boolean isActivated() {
        return activated;
    }
}
