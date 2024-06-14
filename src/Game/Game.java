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

public class Game implements KeyListener {
    private Level[] levels = new Level[ActiveLevel.COUNT_LEVEL.ordinal()];
    private ActiveLevel activeLevel = ActiveLevel.NONE;
    private ActiveLevel nextLevel = ActiveLevel.NONE;
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

    private final static int CURRENT_VERSION = 1;

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

        this.loadLevel(ActiveLevel.LEVEL_ONE);
    }

    public Game(ArrayList<String> lines, StateManager stateManager, int slot) {
        this.stateManager = stateManager;

        this.slot = slot;
        shouldSave = slot >= 0;
        if(shouldSave) {
            savePath = paths[slot];
        }

        int startLevel = 0;

        for(int i = 0; i < lines.size(); i++){
            String packet = lines.get(i);

            if(packet.startsWith("CURRENT LEVEL=")) {
                startLevel = Integer.parseInt(packet.replace("CURRENT LEVEL=", "").trim());
            } else if(packet.startsWith("PLAYER")) {
                ArrayList<String> data = new ArrayList<>();

                i++;
                for(; i < lines.size() && !lines.get(i).equals("END PLAYER"); i++) {
                    data.add(lines.get(i));
                }

                player = new Player(data, slot >= 0 ? savePath : "Player", this);
            } else if(packet.startsWith("LEVEL")) {
                int levelNumber = Integer.parseInt(packet.replace("LEVEL ", "").trim());

                ArrayList<String> data = new ArrayList<>();

                i++;
                for(; i < lines.size() && !lines.get(i).equals("END LEVEL " + levelNumber); i++) {
                    data.add(lines.get(i));
                }

                ActiveLevel levelType = ActiveLevel.values()[levelNumber];
                Level level;

                if(levelType == ActiveLevel.LEVEL_ONE) {
                    level = new LevelOne(data, this, player);
                } else if(levelType == ActiveLevel.LEVEL_TWO) {
                    level = new LevelTwo(data, this, player);
                } else {
                    level = new LevelThree(data, this, player);
                }

                levels[levelNumber] = level;
            }
        }

        levels[ActiveLevel.LEVEL_TUTORIAL.ordinal()] = new LevelZero(this, player);

        loadLevel(ActiveLevel.values()[startLevel]);
    }


    public void respawn() {
        player.heal((int) player.getMaxHealth());

        player.getInventory().clear();

        for(Level level : levels) {
            level.close();
        }

        levels[ActiveLevel.LEVEL_ONE.ordinal()] = new LevelOne(this, player);
        levels[ActiveLevel.LEVEL_TWO.ordinal()] = new LevelTwo(this, player);
        levels[ActiveLevel.LEVEL_THREE.ordinal()] = new LevelThree(this, player);


        if(activeLevel != ActiveLevel.LEVEL_TUTORIAL) {
            loadLevel(ActiveLevel.LEVEL_ONE);
        }
    }

    public void loadCheckpoint(ActiveLevel level) {
        if(level != activeLevel) {
            levels[level.ordinal()].checkpointJump();
        }

        loadLevel(level);
    }

    public void update(long deltaTime) {
        if(activeLevel != nextLevel) {
            int currentLevel = activeLevel.ordinal();
            int newLevel = nextLevel.ordinal();

            if(currentLevel < ActiveLevel.COUNT_LEVEL.ordinal()) {
                levels[currentLevel].close();
            }

            activeLevel = nextLevel;

            if(newLevel < ActiveLevel.COUNT_LEVEL.ordinal()) {
                levels[newLevel].open();
            }
        }


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

        if (player.getHealth() <= 0){
            player.heal(250);
            stateManager.pushState(StateName.STATE_GAMEOVER);
        }
    }

    public void loadLevel(ActiveLevel level) {
        nextLevel = level;
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

//        activeLevel = ActiveLevel.values()[(activeLevel.ordinal() + 1)];

        loadLevel(ActiveLevel.values()[(activeLevel.ordinal() + 1)]);
//
//        System.out.println(activeLevel);

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

    public String encode() {
        String result = "WOW\n";

        result += CURRENT_VERSION + "\n";
        result += "\n";

        result += "PLAYER\n";
        result += player.encode();
        result += "END PLAYER\n";

        for(int i = 0; i < ActiveLevel.LEVEL_TUTORIAL.ordinal(); i++) {
            result += "LEVEL " + i + "\n";
            result += levels[i].encode();
            result += "END LEVEL " + i + "\n";
            result += "\n";
        }

        result += "CURRENT LEVEL=" + activeLevel.ordinal() + "\n";

        return result;
    }

    public boolean savingEnabled() {
        return shouldSave;
    }
}
