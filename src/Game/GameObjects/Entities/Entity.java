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


        Point base = new Point(this.position);
        base.translate(0, ((int) size.getHeight() / 2));
        CollisionType collisionType = world.checkCollision(base);

        onGround = collisionType == CollisionType.GROUND || collisionType == CollisionType.LADDER;

        if(collisionType == CollisionType.LADDER) {
            Point feet = new Point(this.position);
            feet.translate(0, ((int) size.getHeight() / 2));

            int height = 0;

            for(height = 0; height < 5; height++) {
                feet.translate(0, -1);

                CollisionType testPoint = world.checkCollision(feet);
                if(testPoint != CollisionType.LADDER) {
                    velocityY = 0;
                    break;
                }
            }

            position.translate(0, -height);

            if(height < 5) {
                onGround = true;
            }
        }

        if(!onGround || velocityY > 0) {

            Point feet = new Point(this.position);
            feet.translate(0, (int) size.getHeight() / 2);

            int height = 0;

            velocityY += gravityAcceleration * ((double) deltaTime / 1000);
            int travel = (int) velocityY;

            if(travel < 0) {
                for(height = 0; height < Math.abs(travel); height++) {
                    feet.translate(0, 1);
                    CollisionType testPoint = world.checkCollision(feet);
                    if(testPoint != CollisionType.NONE) {
                        velocityY = 0;
                        height++;
                        break;
                    }
                }
            } else {
                Point head = new Point(this.position);
                head.translate(0, -(int) size.getHeight() / 2);

                for(height = 0; height < Math.abs(travel); height++) {
                    feet.translate(0, -1);
                    CollisionType testPoint = world.checkCollision(head);
                    if(testPoint != CollisionType.NONE) {
                        velocityY = 0;
                        height++;
                        break;
                    }
                }

                height = -height;
            }

            position.translate(0, height);
        }

        if(velocityX < 0) {
            Point left = new Point(this.position);
            left.translate((int) -(size.getWidth() / 2), 0);

            int distance = 0;

            for(distance = 0; distance < Math.abs(velocityX); distance++) {
                left.translate(-1, 0);
                CollisionType testPoint = world.checkCollision(left);
                if(testPoint == CollisionType.GROUND) {
                    break;
                }
            }

            position.translate((int) -distance, 0);
        } else if(velocityX > 0) {
            Point right = new Point(this.position);
            right.translate((int) (size.getWidth() / 2), 0);

            int distance = 0;

            for(distance = 0; distance < Math.abs(velocityX); distance++) {
                right.translate(1, 0);
                CollisionType testPoint = world.checkCollision(right);
                if(testPoint == CollisionType.GROUND) {
                    break;
                }
            }

            position.translate((int) distance, 0);

        }


    }

}
