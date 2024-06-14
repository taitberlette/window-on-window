package Game.Worlds;

import Game.Game;
import Game.GameObjects.Entities.Enemies.*;
import Game.GameObjects.Entities.Entity;
import Game.GameObjects.Entities.Player;
import Game.GameObjects.Gadgets.*;
import Game.GameObjects.GameObject;
import Game.GameObjects.Objects.Door;
import Game.GameObjects.Objects.HiddenNumber;
import Game.GameObjects.Objects.MovableBox;
import Game.GameObjects.Objects.Tree;
import Game.GameObjects.Projectiles.*;
import Game.GameObjects.Weapons.Shooter.FlameThrower;
import Game.GameObjects.Weapons.Shooter.RailGun;
import Game.GameObjects.Weapons.Weapon;
import Game.Levels.Level;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;

public abstract class World implements KeyListener {
    protected Game game;
    protected Level level;
    protected Player player;

    protected BufferedImage image;
    protected BufferedImage collision;

    private HashMap<Integer, Boolean> switchers = new HashMap<>();

    protected ArrayList<Switch> switches = new ArrayList<>();
    protected ArrayList<Mechanism> mechanisms = new ArrayList<>();
    protected ArrayList<GameObject> gameObjects = new ArrayList<>();
    protected ArrayList<Projectile> projectiles = new ArrayList<>();
    protected ArrayList<Weapon> droppedWeapons = new ArrayList<>();

    protected ArrayList<Entity> entities = new ArrayList<>();
    protected ArrayList<GameObject> addGameObjects = new ArrayList<>();
    protected ArrayList<GameObject> removeGameObjects = new ArrayList<>();

    protected String levelPath;

    public World(Game game, Level level, Player player, String levelPath) {
        this.game = game;
        this.level = level;
        this.levelPath = levelPath;
        this.player = player;
    }

    public World(ArrayList<String> lines, Game game, Level level, Player player, String levelPath) {
        this.game = game;
        this.level = level;
        this.player = player;

        for(int i = 0; i < lines.size(); i++){
            String packet = lines.get(i);

            ArrayList<String> data = new ArrayList<>();

            i++;
            for(; i < lines.size() && !lines.get(i).equals("END " + packet); i++) {
                data.add(lines.get(i));
            }

            switch (packet) {
                case "TARGET" -> addGameObject(new Target(data, this));
                case "BOX BUTTON" -> addGameObject(new BoxButton(data, this));
                case "MOVING PLATFORM" -> addGameObject(new MovingPlatform(data, this));
                case "MOVING WALL" -> addGameObject(new MovingWall(data, this));
                case "BOX" -> addGameObject(new MovableBox(data, this));
                case "HIDDEN NUMBER" -> addGameObject(new HiddenNumber(data, this));
                case "DOOR" -> addGameObject(new Door(data, player, this, game, levelPath));
                case "TREE" -> addGameObject(new Tree(data));
                case "BONE" -> addGameObject(new Bone(data, this));
                case "FLAME" -> addGameObject(new Flame(data, this));
                case "LIGHTNING" -> addGameObject(new Lightning(data, this));
                case "TUNNEL VISION" -> addGameObject(new TunnelVision(data, this));
                case "WEB" -> addGameObject(new Web(data, this));
                case "FLAME THROWER" -> addGameObject(new FlameThrower(data, this));
                case "RAIL GUN" -> addGameObject(new RailGun(data, this));
                case "HELL HOUND" -> addGameObject(new HellHound(data, player, this));
                case "PANZER" -> addGameObject(new Panzer(data, player, this));
                case "SHOCK SPIDER" -> addGameObject(new ShockSpider(data, player, this));
                case "SILVER BACK" -> addGameObject(new SilverBack(data, player, this));
                case "THRASHER" -> addGameObject(new Thrasher(data, player, this));
                case "VALKYRIE DRONE" -> addGameObject(new ValkyrieDrone(data, player, this));
            }
        }
    }

    public void startBossFight() {
        level.startBossFight();
    }

    public void endBossFight() {
        level.endBossFight();
    }

    public boolean bossFightActive() {
        return level.bossFightActive();
    }

    public void setSwitcher(int id, boolean activated) {
        switchers.put(id, activated);
    }

    public boolean getSwitcher(int id) {
        if(!switchers.containsKey(id)) {
            return false;
        }
        return switchers.get(id);
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

            updateArrays();
    }

    private void updateArrays() {

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

//        if(gameObject instanceof Player) {
//            player = (Player) gameObject;
//        }
    }

    public void removeGameObject(GameObject gameObject) {
        removeGameObjects.add(gameObject);
//        if(gameObject instanceof Player) {
//            player = null;
//        }
    }

    public int numberOfEnemies() {
        int num = 0;

        for(Entity entity : entities) {
            if(entity instanceof Enemy) {
                num++;
            }
        }

        return num;
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

        if(ground && ladder && stairs) {
            return CollisionType.DEATH;
        }

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
        for (Switch switcher : switches) {
            switcher.close();
        }

        for (Mechanism mechanism : mechanisms) {
            mechanism.close();
        }

        for (GameObject gameObject : gameObjects) {
            gameObject.close();
        }

        for (Projectile projectile : projectiles) {
            projectile.close();
        }

        for (Weapon droppedWeapon : droppedWeapons) {
            droppedWeapon.close();
        }

        for (Entity entity : entities) {
            entity.close();
        }
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


    public ArrayList<Weapon> findWeapons(Point point) {
        ArrayList<Weapon> collidingWeapons = new ArrayList<>();

        for(Weapon weapon : droppedWeapons) {
            Rectangle bounds = weapon.getBounds();

            if(bounds.contains(point)) {
                collidingWeapons.add(weapon);
            }
        }

        return collidingWeapons;
    }

    public ArrayList<Weapon> findWeapons(Rectangle originalBounds) {
        ArrayList<Weapon> collidingWeapons = new ArrayList<>();

        for(Weapon weapon : droppedWeapons) {
            Rectangle bounds = weapon.getBounds();

            if(bounds.intersects(originalBounds)) {
                collidingWeapons.add(weapon);
            }
        }

        return collidingWeapons;
    }

    public ArrayList<GameObject> findGameObjects(Point point) {
        ArrayList<GameObject> collidingGameObject = new ArrayList<>();

        for(GameObject gameObject : gameObjects) {
            Rectangle bounds = gameObject.getBounds();

            if(bounds.contains(point)) {
                collidingGameObject.add(gameObject);
            }
        }

        return collidingGameObject;
    }

    public ArrayList<GameObject> findGameObjects(Rectangle originalBounds) {
        ArrayList<GameObject> collidingGameObject = new ArrayList<>();

        for(GameObject gameObject : gameObjects) {
            Rectangle bounds = gameObject.getBounds();

            if(bounds.intersects(originalBounds)) {
                collidingGameObject.add(gameObject);
            }
        }

        return collidingGameObject;
    }

    public Level getLevel() {
        return level;
    }

    public void checkpointJump() {
        for(GameObject gameObject : gameObjects) {
            if(gameObject instanceof Door door) {
                door.lock();
            }
        }
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

    public String encode() {
        updateArrays();

        String result = "";
//
//        result += "SWITCHES\n";
        for(Switch switcher : switches) {
            String type = "";
            if(switcher instanceof Target) {
                type = "TARGET";
            } else if(switcher instanceof BoxButton) {
                type = "BOX BUTTON";
            }

            result += type + "\n";
            result += switcher.encode();
            result += "END " + type + "\n";
        }
//        result += "END SWITCHES\n";

//        result += "MECHANISMS\n";
        for(Mechanism mechanism : mechanisms) {
            String type = "";
            if(mechanism instanceof MovingPlatform) {
                type = "MOVING PLATFORM";
            } else if(mechanism instanceof MovingWall) {
                type = "MOVING WALL";
            }

            result += type + "\n";
            result += mechanism.encode();
            result += "END " + type + "\n";
        }
//        result += "END MECHANISMS\n";

//        result += "GAME OBJECTS\n";
        for(GameObject gameObject : gameObjects) {
            String type = "";
            if(gameObject instanceof MovableBox) {
                type = "BOX";
            } else if(gameObject instanceof HiddenNumber) {
                type = "HIDDEN NUMBER";
            } else if(gameObject instanceof Door) {
                type = "DOOR";
            } else if(gameObject instanceof Tree) {
                type = "TREE";
            }

            result += type + "\n";
            result += gameObject.encode();
            result += "END " + type + "\n";
        }
//        result += "END GAME OBJECTS\n";

//        result += "PROJECTILES\n";
        for(Projectile projectile : projectiles) {
            String type = "";
            if(projectile instanceof Bone) {
                type = "BONE";
            } else if(projectile instanceof Flame) {
                type = "FLAME";
            } else if(projectile instanceof Lightning) {
                type = "LIGHTNING";
            } else if(projectile instanceof TunnelVision) {
                type = "TUNNEL VISION";
            } else if(projectile instanceof Web) {
                type = "WEB";
            }

            result += type + "\n";
            result += projectile.encode();
            result += "END " + type + "\n";
        }
//        result += "END PROJECTILES\n";

//        result += "WEAPONS\n";
        for(Weapon weapon : droppedWeapons) {
            String type = "";
            if (weapon instanceof FlameThrower) {
                type = "FLAME THROWER";
            } else if (weapon instanceof RailGun) {
                type = "RAIL GUN";

                result += type + "\n";
                result += weapon.encode();
                result += "END " + type + "\n";
            }
        }
//        result += "END WEAPONS\n";


//        result += "ENTITIES\n";
        for(Entity entity : entities) {
            String type = "";
            if (entity instanceof Player) {
                // the game encodes the player
                continue;
            } else if (entity instanceof HellHound) {
                type = "HELL HOUND";
            } else if (entity instanceof Panzer) {
                type = "PANZER";
            } else if (entity instanceof ShockSpider) {
                type = "SHOCK SPIDER";
            } else if (entity instanceof SilverBack) {
                type = "SILVER BACK";
            } else if (entity instanceof Thrasher) {
                type = "THRASHER";
            } else if (entity instanceof ValkyrieDrone) {
                type = "VALKYRIE DRONE";
            }

            result += type + "\n";
            result += entity.encode();
            result += "END " + type + "\n";
        }
//        result += "END ENTITIES\n";

        return result;
    }
}
