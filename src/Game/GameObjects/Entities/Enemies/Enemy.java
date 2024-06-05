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
        this.random = new Random();
        this.attackSpeed = attackSpeed;
        this.attackDamage = attackDamage;
        this.maxSpeed = speed;
        this.vision = vision;
        this.health = health;
        this.player = player;
        this.world = world;
    }

    public void update(long deltaTime) {
        super.update(deltaTime);

        double distance = player.getLocation().getLocation().distance(position);
        double xDistance = Math.abs(player.getLocation().getX() - position.getX());
        double multiplier = player.getLocation().getX() - position.getX() < 0 ? -1 : 1;

        if(player == null || distance >= vision || xDistance <= size.getWidth() / 2) {
            // walk back and forth
            velocityX = 0;
        } else {
            this.velocityX = multiplier * maxSpeed;
            lastDirection = velocityX < 0 ? HorizontalDirection.LEFT : HorizontalDirection.RIGHT;
        }

        if(System.currentTimeMillis() - lastHitTime > attackSpeed * 100L) {
            ArrayList<Entity> entities = world.findEntities(getBounds());
            for(Entity entity : entities) {
                if(entity instanceof Player) {
                    entity.hurt(attackDamage);
                    attackAniamtion();
                    lastHitTime = System.currentTimeMillis();
                    break;
                }
            }
        }
    }

    public void attackAniamtion() {

    }

    public void setPlayer(Player player) {
        this.player = player;
    }
}
