package Game.GameObjects.Projectiles;

import Game.GameObjects.Entities.Enemies.Enemy;
import Game.GameObjects.Entities.Entity;
import Game.Worlds.CollisionType;
import Game.Worlds.World;
import Game.GameObjects.GameObject;

import java.awt.*;
import java.util.ArrayList;

public abstract class Projectile extends GameObject {
    protected double velocityX;
    protected double velocityY;
    protected double gravityAcceleration = -9.81 * 64;
    protected int damage;
    protected World world;
    protected Point realPosition = new Point(0, 0);
    protected Dimension size;

    public Projectile(Dimension size) {
        this.size = size;
    }

    public void launch(World world, double angle, double velocity, int damage) {
        velocityX = Math.cos(angle) * velocity;
        velocityY = Math.sin(angle) * velocity;

        this.world = world;
        this.damage = damage;
    }

    public void update(long deltaTime) {

        Point base = new Point(this.position);
        base.translate(0, ((int) size.getHeight() / 2) + 1);
        CollisionType collisionType = world.checkCollision(base);


        // update velocity and "real" position using delta time
        velocityY += gravityAcceleration * ((double) deltaTime / 1000000000);
        double verticalMovement = (velocityY * ((double) deltaTime / 1000000000));
        realPosition.setLocation(realPosition.getX(), realPosition.getY() - verticalMovement);

        // figure out how far we need to go for game position to reach real position
        int verticalDistance = 0;
        int maxVerticalDistance = (int) (realPosition.getY() - position.getY());

        // which way we are going (flipped to match world thinking and not screen thinking)
        int verticalMultiplier = maxVerticalDistance < 0 ? -1 : 1;

        // find the collision point we test with
        Point verticalCollider = new Point(this.position);
        verticalCollider.translate(0, (int) (size.getHeight() / 2) * verticalMultiplier);

        // traverse each step to check for collisions along the way
        for (verticalDistance = 0; verticalDistance < Math.abs(maxVerticalDistance); verticalDistance++) {
            verticalCollider.translate(0, verticalMultiplier);
            CollisionType testPoint = world.checkCollision(verticalCollider);
            if (testPoint == CollisionType.GROUND) {
                break;
            }
        }

        // update the game position with how far we need to go
        position.translate(0, (int) verticalDistance * verticalMultiplier);

        // if we couldn't go all the way update the real position to match with
        if (Math.abs(maxVerticalDistance) != verticalDistance) {
            velocityY = 0;
            realPosition.setLocation(position);

            world.removeGameObject(this);
            return;
        }


        // update "real" position using delta time
        double horizontalMovement = (velocityX * ((double) deltaTime / 1000000000));
        realPosition.setLocation(realPosition.getX() + horizontalMovement, realPosition.getY());

        // figure out how far we need to go for game position to reach real position
        int horizontalDistance = 0;
        double maxHorizontalDistance = (realPosition.getX() - position.getX());

        // which way we are going
        int horizontalMultiplier = maxHorizontalDistance < 0 ? -1 : 1;

        // find the collision point we test with
        Point horizontalCollider = new Point(this.position);
        horizontalCollider.translate((int) (size.getWidth() / 2) * horizontalMultiplier, 0);

        // traverse each step to check for collisions along the way
        for (horizontalDistance = 0; horizontalDistance < Math.abs(maxHorizontalDistance); horizontalDistance++) {
            horizontalCollider.translate(horizontalMultiplier, 0);
            CollisionType testPoint = world.checkCollision(horizontalCollider);
            if (testPoint == CollisionType.GROUND) {
                break;
            }
        }

        // update the game position with how far we need to go
        position.translate((int) horizontalDistance * horizontalMultiplier, 0);

        // if we couldn't go all the way update the real position to match with
        if(Math.abs(maxHorizontalDistance) != horizontalDistance) {
            realPosition.setLocation(position);
            world.removeGameObject(this);
            return;
        }

        ArrayList<Entity> hitEntities = world.findEntities(position);
        for(Entity entity : hitEntities) {
            if(entity instanceof Enemy enemy) {
                enemy.hurt(damage);
                world.removeGameObject(this);
                return;
            }
        }
    }

    public void setLocation(Point position) {
        this.position.setLocation(position);
        this.realPosition.setLocation(position);
    }
}
