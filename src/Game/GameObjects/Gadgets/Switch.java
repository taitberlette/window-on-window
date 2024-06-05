package Game.GameObjects.Gadgets;

import Game.GameObjects.GameObject;
import Game.Worlds.World;

import java.awt.*;
import java.util.ArrayList;

public class Switch extends GameObject {
    protected Dimension size;
    protected boolean activated;
    protected int switcherId;
    protected World world;

    public Switch(Point position, Dimension size, int switcherId, World world) {
        super(position);
        this.size = size;
        this.activated = false;
        this.switcherId = switcherId;
        this.world = world;
    }

    public Switch(ArrayList<String> lines, Dimension size, World world) {
        super(lines);
        this.size = size;

        for(String line : lines) {
            if(line.startsWith("ACTIVATED=")) {
                activated = Boolean.parseBoolean(line.replace("ACTIVATED=", ""));
            } else if(line.startsWith("ID=")) {
                switcherId = Integer.parseInt(line.replace("ID=", ""));
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
        world.setSwitcher(switcherId, activated);
    }

    public boolean isActivated() {
        return activated;
    }

    public String encode() {
        String result = super.encode();

        result += "ACTIVATED=" + activated + "\n";
        result += "ID=" + switcherId + "\n";

        return result;
    }

}
