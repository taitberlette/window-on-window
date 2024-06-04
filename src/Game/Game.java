package Game;

import Game.GameObjects.Entities.Player;
import Game.Levels.ActiveLevel;
import Game.Levels.*;
import States.PauseState;
import States.StateManager;
import States.StateName;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import Game.Levels.ActiveLevel;

public class Game implements KeyListener {
    private Level[] levels = new Level[ActiveLevel.COUNT_LEVEL.ordinal()];
    private ActiveLevel activeLevel = ActiveLevel.NONE;
    private Player player;

    private StateManager stateManager;

    private int slot;
    private boolean shouldSave;
    private String[] paths = {"Alpha", "Beta", "Gamma"};
    private String savePath;

    private final int QUEUE_LENGTH = 5;

    private ArrayBlockingQueue<KeyEvent> typedEvents = new ArrayBlockingQueue<>(QUEUE_LENGTH);
    private ArrayBlockingQueue<KeyEvent> pressedEvents = new ArrayBlockingQueue<>(QUEUE_LENGTH);
    private ArrayBlockingQueue<KeyEvent> releasedEvents = new ArrayBlockingQueue<>(QUEUE_LENGTH);

    public Game(StateManager stateManager, int slot) {
        this.stateManager = stateManager;

        this.slot = slot;
        shouldSave = slot >= 0;
        if(shouldSave) {
            savePath = paths[slot];
        }

        player = new Player(slot >= 0 ? savePath : "Player", this);

        levels[ActiveLevel.LEVEL_ONE.ordinal()] = new LevelOne(this, player);
        levels[ActiveLevel.LEVEL_TWO.ordinal()] = new LevelTwo(this, player);
        levels[ActiveLevel.LEVEL_THREE.ordinal()] = new LevelThree(this, player);
        levels[ActiveLevel.LEVEL_TUTORIAL.ordinal()] = new LevelZero(this, player);
    }

    public void update(long deltaTime) {
        int index = activeLevel.ordinal();

        if(index < ActiveLevel.COUNT_LEVEL.ordinal()) {
            levels[index].update(deltaTime);
        }

        for(KeyEvent typed : typedEvents) {
            if(index < ActiveLevel.COUNT_LEVEL.ordinal()) {
                levels[index].keyTyped(typed);
            }
        }
        typedEvents.clear();

        for(KeyEvent pressed : pressedEvents) {
            if(index < ActiveLevel.COUNT_LEVEL.ordinal()) {
                levels[index].keyPressed(pressed);
            }

            if(pressed.getKeyCode() == KeyEvent.VK_ESCAPE) {
                stateManager.pushState(StateName.STATE_PAUSE);
            }
        }
        pressedEvents.clear();

        for(KeyEvent released : releasedEvents) {
            if(index < ActiveLevel.COUNT_LEVEL.ordinal()) {
                levels[index].keyReleased(released);
            }
        }
        releasedEvents.clear();
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
            player.setWorld(levels[newLevel].getTerra());
        }
    }

    public void kill() {
        for(Level level : levels) {
            level.kill();
        }

        player.kill();
    }

    public void levelCompleted() {
        if(activeLevel == ActiveLevel.LEVEL_TUTORIAL) {
            stateManager.clearStates();
            stateManager.pushState(StateName.STATE_HOME);
            return;
        }

        activeLevel = ActiveLevel.values()[(activeLevel.ordinal() + 1)];

        if(activeLevel == ActiveLevel.COUNT_LEVEL) {
            System.out.println("WON THE GAME 😁");
        }
    }


    public void keyTyped(KeyEvent e) {
        typedEvents.add(e);
    }

    public void keyPressed(KeyEvent e) {
        pressedEvents.add(e);
    }

    public void keyReleased(KeyEvent e) {
        releasedEvents.add(e);
    }
}
