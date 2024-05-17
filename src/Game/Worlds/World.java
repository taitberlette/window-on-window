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
import java.util.LinkedList;

public abstract class World {
    protected Game game;
    protected Level level;

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
    }

    public void removeEntity(Entity entity) {
        removeEntities.add(entity);
    }

    public void draw(Graphics2D graphics2D) {
        int w = 1920, h = 1080;
        Color color1 = Color.RED;
        Color color2 = Color.GREEN;
        GradientPaint gp = new GradientPaint(0, 0, color1, w, h, color2);
        graphics2D.setPaint(gp);
        graphics2D.fillRect(0, 0, w, h);
    }
}
