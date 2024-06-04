package Game.GameObjects.Weapons.Shooter;

import Game.GameObjects.Projectiles.Projectile;
import Game.GameObjects.Weapons.Weapon;
import Game.Utilities.HorizontalDirection;
import Game.Utilities.Inventory;
import Game.Worlds.CollisionType;
import Game.Worlds.World;

import javax.sound.sampled.Port;
import java.awt.*;
import java.lang.reflect.InvocationTargetException;

public abstract class Shooter extends Weapon {
    protected Class<Projectile> projectile;
    protected long startAttack = 0;
    protected boolean doneCooldown;
    private Point realPosition = new Point(0, 0);

    protected double velocityY;
    protected double gravityAcceleration = -9.81 * 64;

    public Shooter(Class<Projectile> projectile, int damage, int cooldown, int speed) {
        super(damage, cooldown, speed);

        this.projectile = projectile;
    }

    public void update(long deltaTime) {
        super.update(deltaTime);

        if(!held) {
            Point base = new Point(this.position);
            base.translate(0, ((int) getBounds().getHeight() / 2) + 1);
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
            verticalCollider.translate(0, (int) (getBounds().getHeight() / 2) * verticalMultiplier);

            // traverse each step to check for collisions along the way
            for (verticalDistance = 0; verticalDistance < Math.abs(maxVerticalDistance); verticalDistance++) {
                verticalCollider.translate(0, verticalMultiplier);
                CollisionType testPoint = world.checkCollision(verticalCollider);
                if (testPoint != CollisionType.NONE) {
                    break;
                }
            }

            // update the game position with how far we need to go
            position.translate(0, (int) verticalDistance * verticalMultiplier);

            // if we couldn't go all the way update the real position to match with
            if (Math.abs(maxVerticalDistance) != verticalDistance) {
                velocityY = 0;
                realPosition.setLocation(position);
            }
        }

        long currentTime = System.currentTimeMillis();
        long progress = currentTime - startAttack;
        doneCooldown = progress > cooldown;
    }

    public void setLocation(Point point) {
        this.position.setLocation(point);
        this.realPosition.setLocation(point);
    }

    public void attack(double angle, Point position, Inventory inventory) {
        startAttack = System.currentTimeMillis();
        doneCooldown = false;

        try {
            Projectile instance = projectile.getDeclaredConstructor().newInstance();
            instance.setLocation(position);
            instance.launch(world, angle, speed, damage);
            world.addGameObject(instance);
        } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
            System.out.println("There was an error creating the projectile");
        }
    }

    public boolean checkCooldown() {
        return doneCooldown;
    }

    public boolean checkAmmunition(Inventory inventory) {
        return false;
    }
}
