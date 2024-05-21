package Game.Worlds;

import Game.Game;
import Game.GameObjects.Entities.Entity;
import Game.GameObjects.Entities.Player;
import Game.GameObjects.Gadgets.Mechanism;
import Game.GameObjects.Gadgets.Switch;
import Game.GameObjects.GameObject;
import Game.GameObjects.Projectiles.Projectile;
import Game.GameObjects.Weapons.Weapon;
import Game.Level;
import Windows.WorldWindow;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.LinkedList;

public abstract class World implements KeyListener {
    protected Game game;
    protected Level level;
    protected Player player;

    protected LinkedList<Switch> switches = new LinkedList<>();
    protected LinkedList<Mechanism> mechanisms = new LinkedList<>();
    protected LinkedList<GameObject> gameObjects = new LinkedList<>();
    protected LinkedList<Projectile> projectiles = new LinkedList<>();
    protected LinkedList<Weapon> droppedWeapons = new LinkedList<>();

    protected LinkedList<Entity> entities = new LinkedList<>();
    protected LinkedList<Entity> addEntities = new LinkedList<>();
    protected LinkedList<Entity> removeEntities = new LinkedList<>();

    protected LinkedList<WorldWindow> worldWindows = new LinkedList<>();

    public World(Game game, Level level) {
        this.game = game;
        this.level = level;
    }

    public void update(long deltaTime) {
        for(Switch switcher : switches) {
            switcher.update(deltaTime);
        }

        for(Mechanism mechanism : mechanisms) {
            mechanism.update(deltaTime);
        }

        for(GameObject gameObject : gameObjects) {
            gameObject.update(deltaTime);
        }

        for(Projectile projectile : projectiles) {
            projectile.update(deltaTime);
        }

        for(Weapon droppedWeapon : droppedWeapons) {
            droppedWeapon.update(deltaTime);
        }

        for(Entity entity : entities) {
            entity.update(deltaTime);
        }

        entities.addAll(addEntities);
        entities.removeAll(removeEntities);

        addEntities.clear();
        removeEntities.clear();

        for(WorldWindow worldWindow : worldWindows) {
            worldWindow.update(deltaTime);
        }
    }

    public void addEntity(Entity entity) {
        addEntities.add(entity);

        if(entity instanceof Player) {
            player = (Player) entity;
        }
    }

    public void removeEntity(Entity entity) {
        removeEntities.add(entity);
        if(entity instanceof Player) {
            player = null;
        }
    }

    public abstract void draw(Graphics2D graphics2D);

    public void open() {
        for(WorldWindow worldWindow : worldWindows) {
            worldWindow.setVisible(true);
        }
    }

    public void close() {
        for(WorldWindow worldWindow : worldWindows) {
            worldWindow.setVisible(false);
        }
    }
    public void keyTyped(KeyEvent e) {
        if(player == null) {
            return;
        }
        player.keyTyped(e);
    }

    public void keyPressed(KeyEvent e) {

        if(player == null) {
            return;
        }
        player.keyPressed(e);
    }

    public void keyReleased(KeyEvent e) {
        if(player == null) {
            return;
        }
        player.keyReleased(e);
    }

}
