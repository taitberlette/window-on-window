package Game.GameObjects.Weapons.Shooter;

import Game.GameObjects.Projectiles.Projectile;
import Game.GameObjects.Weapons.Weapon;
import Game.Utilities.HorizontalDirection;
import Game.Utilities.Inventory;
import Game.Worlds.World;

import javax.sound.sampled.Port;
import java.awt.*;
import java.lang.reflect.InvocationTargetException;

public abstract class Shooter extends Weapon {
    protected Class<Projectile> projectile;
    protected long startAttack = 0;
    protected boolean doneCooldown;

    public Shooter(Class<Projectile> projectile, int damage, int cooldown, int speed) {
        super(damage, cooldown, speed);

        this.projectile = projectile;
    }

    public void update(long deltaTime) {
        super.update(deltaTime);

        long currentTime = System.currentTimeMillis();
        long progress = currentTime - startAttack;
        doneCooldown = progress > cooldown;
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
