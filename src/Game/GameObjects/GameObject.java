package Game.GameObjects;

import Game.Game;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

public class GameObject implements KeyListener {
    protected Point position;

    public GameObject() {
        position = new Point(0, 0);
    }
    public GameObject(Point point) {
        position = new Point(point);
    }

    public GameObject(ArrayList<String> lines) {
        int x = 0;
        int y = 0;

        for(String line : lines) {
            if(line.startsWith("X=")) {
                x = Integer.parseInt(line.replace("X=", ""));
            } else if(line.startsWith("Y=")) {
                y = Integer.parseInt(line.replace("Y=", ""));
            }
        }

        position = new Point(x, y);
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

    public String encode() {
        String result = "";

        result += "X=" + position.getX() + "\n";
        result += "Y=" + position.getX() + "\n";

        return result;
    }
}
