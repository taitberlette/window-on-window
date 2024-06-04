package Game.GameObjects;

import Game.Game;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class GameObject implements KeyListener {
    protected Point position;

    public GameObject() {
        position = new Point(0, 0);
    }

    public void update(long deltaTime) {

    }

    public void setLocation(Point position) {
        this.position.setLocation(position);
    }

    public Point getLocation() {
        return position;
    }

    public void draw(Graphics2D graphics2D) {

    }

    public void kill() {

    }

    public void keyTyped(KeyEvent e) {

    }

    public void keyPressed(KeyEvent e) {

    }

    public void keyReleased(KeyEvent e) {

    }

    public Rectangle getBounds() {
        return new Rectangle(0, 0, 0, 0);
    }
}
