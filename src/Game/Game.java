package Game;

import Game.GameObjects.Entities.Player;
import States.StateManager;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Game implements KeyListener {
    private Level[] levels = new Level[ActiveLevel.COUNT_LEVEL.ordinal()];
    private ActiveLevel activeLevel = ActiveLevel.NONE;
    private Player player;

    private StateManager stateManager;

    public Game(StateManager stateManager, int slot) {
        this.stateManager = stateManager;

        player = new Player();

        for(int i = 0; i < levels.length; i++) {
            levels[i] = new Level(this, player);
        }
    }

    public void update(long deltaTime) {
        int index = activeLevel.ordinal();

        if(index < ActiveLevel.COUNT_LEVEL.ordinal()) {
            levels[index].update(deltaTime);
        }
    }

    public void loadLevel(ActiveLevel level) {
        if(activeLevel == level) {
            return;
        }

        int currentLevel = activeLevel.ordinal();
        int newLevel = level.ordinal();

        if(currentLevel < ActiveLevel.COUNT_LEVEL.ordinal()) {
            levels[currentLevel].close();
        }

        activeLevel = level;

        if(newLevel < ActiveLevel.COUNT_LEVEL.ordinal()) {
            levels[newLevel].open();
        }
    }

    public String getPath(ActiveLevel level) {
        return switch (level) {
            case LEVEL_ONE -> "Level One";
            case LEVEL_TWO -> "Level Two";
            case LEVEL_THREE -> "Level Three";
            case LEVEL_TUTORIAL -> "Level Tutorial";
            case COUNT_LEVEL -> null;
            case NONE -> null;
        };
    }


    public void keyTyped(KeyEvent e) {
        int index = activeLevel.ordinal();

        if(index < ActiveLevel.COUNT_LEVEL.ordinal()) {
            levels[index].keyTyped(e);
        }
    }

    public void keyPressed(KeyEvent e) {
        int index = activeLevel.ordinal();

        if(index < ActiveLevel.COUNT_LEVEL.ordinal()) {
            levels[index].keyPressed(e);
        }
    }

    public void keyReleased(KeyEvent e) {
        int index = activeLevel.ordinal();

        if(index < ActiveLevel.COUNT_LEVEL.ordinal()) {
            levels[index].keyReleased(e);
        }
    }
}
