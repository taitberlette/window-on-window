package Game;

import Game.GameObjects.Entities.Player;
import States.StateManager;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Game implements KeyListener {
    private Level[] levels = new Level[3];
    private Level tutorial;
    private Player player;

    private StateManager stateManager;

    public Game(StateManager stateManager, int slot) {
        this.stateManager = stateManager;

        player = new Player();

        for(int i = 0; i < levels.length; i++) {
            levels[i] = new Level(this, player);
        }

        tutorial = new Level(this, player);
    }

    public void update(long deltaTime) {
        for(int i = 0; i < levels.length; i++) {
            levels[i].update(deltaTime);
        }

        tutorial.update(deltaTime);
    }


    public void keyTyped(KeyEvent e) {

    }

    public void keyPressed(KeyEvent e) {

    }

    public void keyReleased(KeyEvent e) {

    }
}
