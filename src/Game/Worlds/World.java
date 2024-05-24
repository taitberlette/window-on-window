package Game.Worlds;

import Game.Game;
import Game.GameObjects.Entities.Entity;
import Game.GameObjects.Entities.Player;
import Game.GameObjects.Gadgets.Mechanism;
import Game.GameObjects.Gadgets.Switch;
import Game.GameObjects.GameObject;
import Game.GameObjects.Projectiles.Projectile;
import Game.GameObjects.Weapons.Weapon;
import Game.Levels.Level;
import Windows.WorldWindow;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.util.LinkedList;

public abstract class World implements KeyListener {
    protected Game game;
    protected Level level;
    protected Player player;

    protected BufferedImage image;
    protected BufferedImage collision;

    protected LinkedList<Switch> switches = new LinkedList<>();
    protected LinkedList<Mechanism> mechanisms = new LinkedList<>();
    protected LinkedList<GameObject> gameObjects = new LinkedList<>();
    protected LinkedList<Projectile> projectiles = new LinkedList<>();
    protected LinkedList<Weapon> droppedWeapons = new LinkedList<>();

    protected LinkedList<Entity> entities = new LinkedList<>();
    protected LinkedList<Entity> addEntities = new LinkedList<>();
    protected LinkedList<Entity> removeEntities = new LinkedList<>();


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

    public CollisionType checkCollision(Point position) {

        if(position.getX() < 0 || position.getX() >= collision.getWidth() || position.getY() < 0 || position.getY() >= collision.getHeight()) {
            return CollisionType.GROUND;
        }

        int colour = collision.getRGB((int) position.getX(), (int) position.getY());

        boolean ground = (colour & 0x00FF0000) != 0;
        boolean ladder = (colour & 0x0000FF00) != 0;
        boolean stairs = (colour & 0x000000FF) != 0;

        if(ground) {
            return CollisionType.GROUND;
        }

        if(ladder) {
            return CollisionType.LADDER;
        }

        if(stairs) {
            return CollisionType.STAIRS;
        }

        return CollisionType.NONE;
    }

    public void draw(Graphics2D graphics2D) {
        if(image != null) {
            graphics2D.drawImage(image, 0, 0, 1920, 1080, null);
        }

        for(Switch switcher : switches) {
            switcher.draw(graphics2D);
        }

        for(Mechanism mechanism : mechanisms) {
            mechanism.draw(graphics2D);
        }

        for(GameObject gameObject : gameObjects) {
            gameObject.draw(graphics2D);
        }

        for(Projectile projectile : projectiles) {
            projectile.draw(graphics2D);
        }

        for(Weapon droppedWeapon : droppedWeapons) {
            droppedWeapon.draw(graphics2D);
        }

        for(Entity entity : entities) {
            entity.draw(graphics2D);
        }
    }

    public void open() {

    }

    public void close() {

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
