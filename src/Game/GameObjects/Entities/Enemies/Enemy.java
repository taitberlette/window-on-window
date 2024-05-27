package Game.GameObjects.Entities.Enemies;

import Game.GameObjects.Entities.Player;
import Game.GameObjects.Entities.Entity;
import Game.Utilities.HorizontalDirection;

import java.awt.*;

public abstract class Enemy extends Entity {
    protected Player player;
    protected int attackSpeed;
    protected int attackDamage;
    protected int vision;

    public Enemy(Dimension size, int attackSpeed, int attackDamage, int speed, int vision, int health) {
        super(size);
        this.attackSpeed = attackSpeed;
        this.attackDamage = attackDamage;
        this.maxSpeed = speed;
        this.vision = vision;
    }

    public void update(long deltaTime) {
        super.update(deltaTime);

        if(player == null || player.getLocation().getLocation().distance(position) >= vision) {
            // walk back and forth
            velocityX = 0;
            return;
        }

        this.velocityX = (player.getLocation().getX() - position.getX() < 0) ? -maxSpeed : maxSpeed;
        lastDirection = velocityX < 0 ? HorizontalDirection.LEFT : HorizontalDirection.RIGHT;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }
}
