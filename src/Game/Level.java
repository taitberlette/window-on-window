package Game;

import Game.GameObjects.Entities.Player;
import Game.Worlds.EtherWorld;
import Game.Worlds.TerraWorld;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Level implements KeyListener {
    private Game game;
    private Player player;
    private TerraWorld terraWorld;
    private EtherWorld etherWorld;

    public Level(Game game, Player player) {
        this.game = game;
        this.player = player;

        terraWorld = new TerraWorld(game, this);
        etherWorld = new EtherWorld(game, this);

//        terraWorld.addEntity(player);
    }

    public void update(long deltaTime) {
        terraWorld.update(deltaTime);
        etherWorld.update(deltaTime);
    }

    public void open() {
        terraWorld.open();
        etherWorld.open();
    }

    public void close() {
        terraWorld.close();
        etherWorld.close();
    }

    public void keyTyped(KeyEvent e) {
        terraWorld.keyTyped(e);
        etherWorld.keyTyped(e);
    }

    public void keyPressed(KeyEvent e) {
        terraWorld.keyPressed(e);
        etherWorld.keyPressed(e);
    }

    public void keyReleased(KeyEvent e) {
        terraWorld.keyReleased(e);
        etherWorld.keyReleased(e);
    }
}
