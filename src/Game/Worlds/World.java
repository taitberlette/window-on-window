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
import org.w3c.dom.css.Rect;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.ArrayList;

public abstract class World implements KeyListener {
    protected Game game;
    protected Level level;
    protected Player player;

    protected BufferedImage image;
    protected BufferedImage collision;

    protected ArrayList<Switch> switches = new ArrayList<>();
    protected ArrayList<Mechanism> mechanisms = new ArrayList<>();
    protected ArrayList<GameObject> gameObjects = new ArrayList<>();
    protected ArrayList<Projectile> projectiles = new ArrayList<>();
    protected ArrayList<Weapon> droppedWeapons = new ArrayList<>();

    protected ArrayList<Entity> entities = new ArrayList<>();
    protected ArrayList<GameObject> addGameObjects = new ArrayList<>();
    protected ArrayList<GameObject> removeGameObjects = new ArrayList<>();

    protected String levelPath;

    public World(Game game, Level level, String levelPath) {
        this.game = game;
        this.level = level;
        this.levelPath = levelPath;
    }

    public void update(long deltaTime) {
            for (Switch switcher : switches) {
                switcher.update(deltaTime);
            }

            for (Mechanism mechanism : mechanisms) {
                mechanism.update(deltaTime);
            }

            for (GameObject gameObject : gameObjects) {
                gameObject.update(deltaTime);
            }

            for (Projectile projectile : projectiles) {
                projectile.update(deltaTime);
            }

            for (Weapon droppedWeapon : droppedWeapons) {
                droppedWeapon.update(deltaTime);
            }

            for (Entity entity : entities) {
                entity.update(deltaTime);
            }

        try {
            for (GameObject gameObject : addGameObjects) {
                if (gameObject instanceof Switch) {
                    switches.add((Switch) gameObject);
                } else if (gameObject instanceof Mechanism) {
                    mechanisms.add((Mechanism) gameObject);
                } else if (gameObject instanceof Projectile) {
                    projectiles.add((Projectile) gameObject);
                } else if (gameObject instanceof Weapon) {
                    droppedWeapons.add((Weapon) gameObject);
                } else if (gameObject instanceof Entity) {
                    entities.add((Entity) gameObject);
                } else {
                    gameObjects.add(gameObject);
                }
            }
            addGameObjects.clear();
        } catch (Exception e) {
            System.out.println("There was an error updating something!!");
            System.err.println(e.getMessage());
            e.printStackTrace();
        }

            for (GameObject gameObject : removeGameObjects) {
                if (gameObject instanceof Switch) {
                    switches.remove((Switch) gameObject);
                } else if (gameObject instanceof Mechanism) {
                    mechanisms.remove((Mechanism) gameObject);
                } else if (gameObject instanceof Projectile) {
                    projectiles.remove((Projectile) gameObject);
                } else if (gameObject instanceof Weapon) {
                    droppedWeapons.remove((Weapon) gameObject);
                } else if (gameObject instanceof Entity) {
                    entities.remove((Entity) gameObject);
                } else {
                    gameObjects.remove(gameObject);
                }
            }
            removeGameObjects.clear();

    }

    public void addGameObject(GameObject gameObject) {
        addGameObjects.add(gameObject);

        if(gameObject instanceof Player) {
            player = (Player) gameObject;
        }
    }

    public void removeGameObject(GameObject gameObject) {
        removeGameObjects.add(gameObject);
        if(gameObject instanceof Player) {
            player = null;
        }
    }

    public CollisionType checkCollision(Point position) {
        if(position.getX() < 0 || position.getX() >= collision.getWidth() || position.getY() < 0 || position.getY() >= collision.getHeight()) {
            return CollisionType.GROUND;
        }

        ArrayList<Mechanism> collidingMechanisms = findMechanisms(position);
        if(!collidingMechanisms.isEmpty()) {
            return CollisionType.PLATFORM;
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

//            graphics2D.setColor(Color.RED);
//            graphics2D.draw(entity.getBounds());
        }
    }

    public void open() {

    }

    public void close() {

    }

    public ArrayList<Entity> findEntities(Point point) {
        ArrayList<Entity> collidingEntities = new ArrayList<>();

        for(Entity entity : entities) {
            Rectangle bounds = entity.getBounds();

            if(bounds.contains(point)) {
                collidingEntities.add(entity);
            }
        }

        return collidingEntities;
    }

    public ArrayList<Entity> findEntities(Rectangle originalBounds) {
        ArrayList<Entity> collidingEntities = new ArrayList<>();

        for(Entity entity : entities) {
            Rectangle bounds = entity.getBounds();

            if(bounds.intersects(originalBounds)) {
                collidingEntities.add(entity);
            }
        }

        return collidingEntities;
    }

    public ArrayList<Switch> findSwitches(Point point) {
        ArrayList<Switch> collidingSwitches = new ArrayList<>();

        for(Switch switcher : switches) {
            Rectangle bounds = switcher.getBounds();

            if(bounds.contains(point)) {
                collidingSwitches.add(switcher);
            }
        }

        return collidingSwitches;
    }

    public ArrayList<Switch> findSwitches(Rectangle originalBounds) {
        ArrayList<Switch> collidingSwitches = new ArrayList<>();

        for(Switch switcher : switches) {
            Rectangle bounds = switcher.getBounds();

            if(bounds.intersects(originalBounds)) {
                collidingSwitches.add(switcher);
            }
        }

        return collidingSwitches;
    }

    public ArrayList<Mechanism> findMechanisms(Point point) {
        ArrayList<Mechanism> collidingMechanisms = new ArrayList<>();

        for(Mechanism mechanism : mechanisms) {
            Rectangle bounds = mechanism.getBounds();

            if(bounds.contains(point)) {
                collidingMechanisms.add(mechanism);
            }
        }

        return collidingMechanisms;
    }

    public ArrayList<Mechanism> findMechanisms(Rectangle originalBounds) {
        ArrayList<Mechanism> collidingMechanisms = new ArrayList<>();

        for(Mechanism mechanism : mechanisms) {
            Rectangle bounds = mechanism.getBounds();

            if(bounds.intersects(originalBounds)) {
                collidingMechanisms.add(mechanism);
            }
        }

        return collidingMechanisms;
    }

    public void kill() {
        for(Switch switcher : switches) {
            switcher.kill();
        }

        for(Mechanism mechanism : mechanisms) {
            mechanism.kill();
        }

        for(GameObject gameObject : gameObjects) {
            gameObject.kill();
        }

        for(Projectile projectile : projectiles) {
            projectile.kill();
        }

        for(Weapon droppedWeapon : droppedWeapons) {
            droppedWeapon.kill();
        }

        for(Entity entity : entities) {
            entity.kill();
        }
    }

    public void keyTyped(KeyEvent e) {
        for(Switch switcher : switches) {
            switcher.keyTyped(e);
        }

        for(Mechanism mechanism : mechanisms) {
            mechanism.keyTyped(e);
        }

        for(GameObject gameObject : gameObjects) {
            gameObject.keyTyped(e);
        }

        for(Projectile projectile : projectiles) {
            projectile.keyTyped(e);
        }

        for(Weapon droppedWeapon : droppedWeapons) {
            droppedWeapon.keyTyped(e);
        }

        for(Entity entity : entities) {
            entity.keyTyped(e);
        }
    }

    public void keyPressed(KeyEvent e) {
        for(Switch switcher : switches) {
            switcher.keyPressed(e);
        }

        for(Mechanism mechanism : mechanisms) {
            mechanism.keyPressed(e);
        }

        for(GameObject gameObject : gameObjects) {
            gameObject.keyPressed(e);
        }

        for(Projectile projectile : projectiles) {
            projectile.keyPressed(e);
        }

        for(Weapon droppedWeapon : droppedWeapons) {
            droppedWeapon.keyPressed(e);
        }

        for(Entity entity : entities) {
            entity.keyPressed(e);
        }
    }

    public void keyReleased(KeyEvent e) {
        for(Switch switcher : switches) {
            switcher.keyReleased(e);
        }

        for(Mechanism mechanism : mechanisms) {
            mechanism.keyReleased(e);
        }

        for(GameObject gameObject : gameObjects) {
            gameObject.keyReleased(e);
        }

        for(Projectile projectile : projectiles) {
            projectile.keyReleased(e);
        }

        for(Weapon droppedWeapon : droppedWeapons) {
            droppedWeapon.keyReleased(e);
        }

        for(Entity entity : entities) {
            entity.keyReleased(e);
        }
    }

}
