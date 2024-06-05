package Game.GameObjects.Gadgets;

import Game.GameObjects.GameObject;

import java.awt.*;
import java.util.ArrayList;

public class Switch extends GameObject {
    protected Dimension size;
    protected boolean activated;

    public Switch(Point position, Dimension size) {
        super(position);
        this.size = size;
        this.activated = false;
    }

    public Switch(ArrayList<String> lines, Dimension size) {
        super(lines);
        this.size = size;

        for(String line : lines) {
            if(line.startsWith("ACTIVATED=")) {
                activated = Boolean.parseBoolean(line.replace("ACTIVATED=", ""));
            }
        }
    }

    public Dimension getSize() {
        return size;
    }

    public Rectangle getBounds() {
        return new Rectangle((int) (position.getX() - size.getWidth() / 2), (int) (position.getY() - size.getHeight() / 2), (int) size.getWidth(), (int) size.getHeight());
    }

    public void toggle() {
        activated = !activated;
    }

    public boolean isActivated() {
        return activated;
    }

    public String encode() {
        String result = super.encode();

        result += "ACTIVATED=" + activated + "\n";

        return result;
    }

}
