package Game.GameObjects.Entities;

import Game.GameObjects.GameObject;
import Game.Worlds.CollisionType;
import Game.Worlds.World;

import java.awt.*;

public abstract class Entity extends GameObject {

    protected Point realPosition = new Point(0, 0);
    protected int health;
    protected int maxHealth;
    protected int maxSpeed;
    protected double velocityX;
    protected double velocityY;
    protected double gravityAcceleration = -9.81 * 64;
    protected boolean onGround;
    protected Dimension size;

    protected Rectangle bounds;
    protected World world;

    public Entity(Dimension size) {
        this.size = size;
    }

    public int getHealth() {
        return health;
    }

    public int getMaxHealth() {
        return maxHealth;
    }

    public void update(long deltaTime) {

        Point base = new Point(this.position);
        base.translate(0, ((int) size.getHeight() / 2) + 1);
        CollisionType collisionType = world.checkCollision(base);

        onGround = collisionType == CollisionType.GROUND || collisionType == CollisionType.LADDER;

        if(collisionType == CollisionType.LADDER && velocityY <= 0) {
            Point feet = new Point(this.position);
            feet.translate(0, ((int) size.getHeight() / 2));

            int height = 0;

            // update "real" position using delta time
            double verticalAutoMovement = (200 * ((double) deltaTime / 1000000000));
            realPosition.setLocation(realPosition.getX(), realPosition.getY() - verticalAutoMovement);

            int testHeight = (int) (position.getY() - realPosition.getY());

            for(height = 0; height < testHeight; height++) {
                feet.translate(0, -1);

                CollisionType testPoint = world.checkCollision(feet);
                if(testPoint != CollisionType.LADDER) {
                    onGround = true;
                    break;
                }
            }

            position.translate(0, -height);

            if(height != testHeight) {
                realPosition.setLocation(position);
            }
        } else if (collisionType == CollisionType.STAIRS && velocityY <= 0) {
            Point feet = new Point(this.position);
            feet.translate(0, ((int) size.getHeight() / 2));

            int verticalMovement = 0;

            while(world.checkCollision(feet) == CollisionType.STAIRS) {
                feet.translate(0, -1);
                verticalMovement++;
            }

            position.translate(0, -verticalMovement);
            realPosition.setLocation(position);
            onGround = true;
        } else {


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
                if (verticalMultiplier == 1) {
                    onGround = true;
                }
                velocityY = 0;
                realPosition.setLocation(position);
            }
        }
        
        // update "real" position using delta time
        double horizontalMovement = (velocityX * ((double) deltaTime / 1000000000));
        realPosition.setLocation(realPosition.getX() + horizontalMovement, realPosition.getY());

        // figure out how far we need to go for game position to reach real position
        int horizontalDistance = 0;
        int maxHorizontalDistance = (int) (realPosition.getX() - position.getX());

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
        }
    }

    public Dimension getSize() {
        return size;
    }

    public void setLocation(Point position) {
        super.setLocation(position);
        realPosition.setLocation(position);
    }

    public void setWorld(World world) {
        this.world = world;
    }

    public World getWorld() {
        return this.world;
    }
}
