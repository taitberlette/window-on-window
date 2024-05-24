package Game;

import Game.GameObjects.Entities.Player;
import Game.Levels.ActiveLevel;
import Game.Levels.*;
import States.PauseState;
import States.StateManager;
import States.StateName;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import static Game.Levels.ActiveLevel.*;

public class Game implements KeyListener {
    private Level[] levels = new Level[ActiveLevel.COUNT_LEVEL.ordinal()];
    private ActiveLevel activeLevel = ActiveLevel.NONE;
    private Player player;

    private StateManager stateManager;

    private int slot;
    private boolean shouldSave;
    private String[] paths = {"Alpha", "Beta", "Gamma"};
    private String savePath;

    public Game(StateManager stateManager, int slot) {
        this.stateManager = stateManager;

        this.slot = slot;
        shouldSave = slot >= 0;
        if(shouldSave) {
            savePath = paths[slot];
        }

        player = new Player(slot >= 0 ? savePath : "Player", this);

        levels[LEVEL_ONE.ordinal()] = new LevelOne(this, player);
        levels[LEVEL_TWO.ordinal()] = new LevelTwo(this, player);
        levels[LEVEL_THREE.ordinal()] = new LevelThree(this, player);
        levels[LEVEL_TUTORIAL.ordinal()] = new LevelZero(this, player);
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

    public void kill() {
        for(Level level : levels) {
            level.kill();
        }

        player.kill();
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

        if(e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            stateManager.pushState(StateName.STATE_PAUSE);
        }
    }

    public void keyReleased(KeyEvent e) {
        int index = activeLevel.ordinal();

        if(index < ActiveLevel.COUNT_LEVEL.ordinal()) {
            levels[index].keyReleased(e);
        }
    }
}
