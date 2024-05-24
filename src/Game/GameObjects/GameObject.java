package Game.GameObjects;

import Game.Game;

import java.awt.*;

public class GameObject {
    protected Point position;

    public GameObject() {
        position = new Point(0, 0);
    }

    public void update(long deltaTime) {

    }

    public void setLocation(Point position) {
        this.position = position;
    }

    public Point getLocation() {
        return position;
    }

    public void draw(Graphics2D graphics2D) {

    }

    public void kill() {

    }
}
