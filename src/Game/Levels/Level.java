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
import java.util.LinkedList;

public abstract class Level implements KeyListener {
    protected Game game;
    protected Player player;
    protected TerraWorld terraWorld;
    protected EtherWorld etherWorld;

    protected LinkedList<WorldWindow> worldWindows = new LinkedList<>();

    public Level(Game game, Player player) {
        this.game = game;
        this.player = player;

        terraWorld = new TerraWorld(game, this);
        etherWorld = new EtherWorld(game, this);
    }

    public void update(long deltaTime) {
        terraWorld.update(deltaTime);
        etherWorld.update(deltaTime);

        for(WorldWindow worldWindow : worldWindows) {
            worldWindow.update(deltaTime);
        }
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

            Point feet = new Point(player.getLocation());
            feet.translate(0, (int) player.getSize().getHeight() / 2);

            if(switchWorld.checkCollision(feet) != CollisionType.NONE) {
                continue;
            }

            worldWindow.getView().getFrame().toFront();
            worldWindow.setTarget(player);
            currentWindow.setTarget(null);

            switchWorld.addEntity(player);
            currentWorld.removeEntity(player);

            player.setWorld(switchWorld);
        }
    }
}
