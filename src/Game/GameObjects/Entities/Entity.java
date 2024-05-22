package Game.GameObjects.Entities;

import Game.GameObjects.GameObject;
import Game.Worlds.CollisionType;
import Game.Worlds.World;

import java.awt.*;

public abstract class Entity extends GameObject {

    protected int health;
    protected int maxSpeed;
    protected double velocityX;
    protected double velocityY;
    protected double gravityAcceleration = -9.81;
    protected boolean onGround;
    protected Dimension size;

    protected Rectangle bounds;
    protected World world;

    public Entity(Dimension size) {
        this.size = size;
    }

    public void update(long deltaTime) {
        position.translate((int) velocityX, 0);

        Point feet = new Point(this.position);
        feet.translate(0, (int) size.getHeight() / 2);

        CollisionType collisionType = world.checkCollision(feet);

        onGround = collisionType == CollisionType.GROUND;

        if(!onGround || velocityY > 0) {
            int height = 0;

            velocityY += gravityAcceleration * ((double) deltaTime / 1000);
            int travel = (int) velocityY;

            if(travel < 0) {
                for(height = 0; height < Math.abs(travel); height++) {
                    feet.translate(0, 1);
                    CollisionType testPoint = world.checkCollision(feet);
                    if(testPoint == CollisionType.GROUND) {
                        velocityY = 0;
                        height++;
                        break;
                    }
                }
            } else {
                height = (int) -velocityY;
            }

            position.translate((int) velocityX, height);
        }



    }

}
