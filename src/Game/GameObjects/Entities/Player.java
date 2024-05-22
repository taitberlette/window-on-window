package Game.GameObjects.Entities;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Player extends Entity implements KeyListener {

    int vx = 0;
    int vy = 0;

    public Player() {
        super(new Dimension(128, 128));
        position.setLocation(500, 500);
    }

    public void update(long deltaTime) {
        position.setLocation(position.getX() + vx, position.getY() + vy);
    }

    public void keyTyped(KeyEvent e) {

    }

    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_DOWN) {
            vy = 5;
        }
        if(e.getKeyCode() == KeyEvent.VK_UP) {
            vy = -5;
        }
        if(e.getKeyCode() == KeyEvent.VK_LEFT) {
            vx = -5;
        }
        if(e.getKeyCode() == KeyEvent.VK_RIGHT) {
            vx = 5;
        }
    }

    public void keyReleased(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_DOWN) {
            vy = 0;
        }
        if(e.getKeyCode() == KeyEvent.VK_UP) {
            vy = 0;
        }
        if(e.getKeyCode() == KeyEvent.VK_LEFT) {
            vx = 0;
        }
        if(e.getKeyCode() == KeyEvent.VK_RIGHT) {
            vx = 0;
        }

    }
}
