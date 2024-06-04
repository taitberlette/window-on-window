package Game.Levels;

import Game.Game;
import Game.GameObjects.Entities.Player;
import Game.Worlds.CollisionType;
import Game.Worlds.EtherWorld;
import Game.Worlds.TerraWorld;
import Game.Worlds.World;
import Windows.WorldWindow;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.LinkedList;

public abstract class Level implements KeyListener {
    protected Game game;
    protected Player player;
    protected TerraWorld terraWorld;
    protected EtherWorld etherWorld;

    protected String levelPath;

    protected ArrayList<WorldWindow> worldWindows = new ArrayList<>();
    protected ArrayList<WorldWindow> worldWindowsToAdd = new ArrayList<>();
    protected ArrayList<WorldWindow> worldWindowsToRemove = new ArrayList<>();

    public Level(Game game, Player player, String levelPath) {
        this.game = game;
        this.player = player;
        this.levelPath = levelPath;

        terraWorld = new TerraWorld(game, this, levelPath);
        etherWorld = new EtherWorld(game, this, levelPath);
    }

    public World getTerra() {
        return terraWorld;
    }

    public World getEther() {
        return etherWorld;
    }

    public void update(long deltaTime) {
        terraWorld.update(deltaTime);
        etherWorld.update(deltaTime);

        for(WorldWindow worldWindow : worldWindows) {
            worldWindow.update(deltaTime);
        }

        worldWindows.addAll(worldWindowsToAdd);
        worldWindows.removeAll(worldWindowsToRemove);
    }

    public void open() {
        terraWorld.open();
        etherWorld.open();

        for(WorldWindow worldWindow : worldWindows) {
            worldWindow.setVisible(true);
        }
    }

    public void close() {
        terraWorld.close();
        etherWorld.close();

        for(WorldWindow worldWindow : worldWindows) {
            worldWindow.setVisible(false);
        }
    }

    public void addWorldWindow(WorldWindow worldWindow) {
        worldWindow.setFocusable(true);
        worldWindow.requestFocus();
        worldWindow.setKeyListener(game);

        worldWindowsToAdd.add(worldWindow);
    }

    public void removeWorldWindow(WorldWindow worldWindow) {
        worldWindowsToRemove.add(worldWindow);
    }

    public void kill() {
        terraWorld.kill();
        etherWorld.kill();

        for(WorldWindow worldWindow : worldWindows) {
            worldWindow.setVisible(false);
        }

        worldWindows.clear();
    }

    public void keyTyped(KeyEvent e) {
        terraWorld.keyTyped(e);
        etherWorld.keyTyped(e);
    }

    public void keyPressed(KeyEvent e) {
        terraWorld.keyPressed(e);
        etherWorld.keyPressed(e);

        if(e.getKeyCode() == KeyEvent.VK_SPACE) {
            switchWindow();
        }
    }

    public void keyReleased(KeyEvent e) {
        terraWorld.keyReleased(e);
        etherWorld.keyReleased(e);
    }

    public void switchWindow() {
        WorldWindow currentWindow = null;

        for(WorldWindow worldWindow : worldWindows) {
            if(worldWindow.getTarget() == player) {
                currentWindow = worldWindow;
                break;
            }
        }

        if(currentWindow == null) {
            System.out.println("Something went horribly wrong there is no player in a window??");
            return;
        }

        for(WorldWindow worldWindow : worldWindows) {
            if(worldWindow == currentWindow || worldWindow.getTarget() != null) {
                continue;
            }

            if(!worldWindow.getBounds().intersects(currentWindow.getBounds())) {
                continue;
            }

            World currentWorld = currentWindow.getWorld();
            World switchWorld = worldWindow.getWorld();

            if(switchWorld != currentWorld) {
                Point feet = new Point(player.getLocation());
                feet.translate(0, (int) player.getSize().getHeight() / 2);

                if(switchWorld.checkCollision(feet) != CollisionType.NONE) {
                    continue;
                }

                switchWorld.addGameObject(player);
                currentWorld.removeGameObject(player);

                player.setWorld(switchWorld);
            }


            worldWindow.getView().getFrame().toFront();

            worldWindow.setTarget(player);
            currentWindow.setTarget(null);

            Point currentPosition = currentWindow.getView().getFrame().getLocation();
            currentWindow.getView().getFrame().setLocation(worldWindow.getView().getFrame().getLocation());
            worldWindow.getView().getFrame().setLocation(currentPosition);
        }
    }
}
