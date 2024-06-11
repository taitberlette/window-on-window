package Game.GameObjects.Entities.Enemies;

import Game.GameObjects.Entities.Player;
import Game.GameObjects.Entities.Entity;
import Game.Utilities.HorizontalDirection;
import Game.Worlds.World;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public abstract class Enemy extends Entity {
    protected Player player;
    protected Random random;
    protected int attackSpeed;
    protected int attackDamage;
    protected int vision;
    protected long lastHitTime = 0;
    protected boolean isBoss = false;
    protected boolean bossFight = false;
    protected boolean outRange = false;

    public Enemy(Dimension size, int attackSpeed, int attackDamage, int speed, int vision, int health, Player player, World world) {
        super(size);
        this.random = new Random();
        this.attackSpeed = attackSpeed;
        this.attackDamage = attackDamage;
        this.maxSpeed = speed;
        this.vision = vision;
        this.health = health;
        this.player = player;
        this.world = world;
    }

    public Enemy(Dimension size, int attackSpeed, int attackDamage, int speed, int vision, int health, ArrayList<String> lines, Player player, World world) {
        super(lines, size);

        for (String line : lines) {
            if(line.startsWith("BOSS=")) {
                isBoss = Boolean.parseBoolean(line.replace("BOSS=", ""));
            } else if(line.startsWith("BOSS FIGHT=")) {
                bossFight = Boolean.parseBoolean(line.replace("BOSS FIGHT=", ""));
            }
        }

        this.random = new Random();
        this.attackSpeed = attackSpeed;
        this.attackDamage = attackDamage;
        this.maxSpeed = speed;
        this.vision = vision;
        this.health = health;
        this.player = player;
        this.world = world;
    }

    public void setBoss() {
        isBoss = true;
    }

    public void update(long deltaTime) {
        super.update(deltaTime);

        double distance = player.getLocation().getLocation().distance(position);
        double xDistance = Math.abs(player.getLocation().getX() - position.getX());
        double yDistance = Math.abs(player.getLocation().getY() - position.getY());
        double multiplier = player.getLocation().getX() - position.getX() < 0 ? -1 : 1;

        outRange = player == null || (!bossFight && (distance >= vision || xDistance <= size.getWidth() / 2));

        if(outRange) {
            // walk back and forth
            velocityX = 0;
        } else {
            this.velocityX = multiplier * maxSpeed;
            lastDirection = velocityX < 0 ? HorizontalDirection.LEFT : HorizontalDirection.RIGHT;
        }

        if(isBoss && player != null && !bossFight && player.getWorld() == world && xDistance <= vision && yDistance <= 64) {
            bossFight = true;
            world.startBossFight();
        }

        if(System.currentTimeMillis() - lastHitTime > attackSpeed * 100L) {
            ArrayList<Entity> entities = world.findEntities(getBounds());
            for(Entity entity : entities) {
                if(entity instanceof Player) {
                    entity.hurt(attackDamage);
                    startAttack();
                    lastHitTime = System.currentTimeMillis();
                    break;
                }
            }
        }
    }

    public void startAttack() {

    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public void kill() {
        super.kill();

        if(bossFight) {
            world.endBossFight();
        }
    }

    public String encode() {
        String result = super.encode();

        result += "BOSS=" + isBoss + "\n";
        result += "BOSS FIGHT=" + bossFight + "\n";

        return result;
    }
}
