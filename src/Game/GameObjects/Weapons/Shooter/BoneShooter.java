package Game.GameObjects.Weapons.Shooter;

import Game.GameObjects.Projectiles.Bone;
import Game.GameObjects.Projectiles.Lightning;
import Game.GameObjects.Projectiles.Projectile;
import Game.Utilities.Ammunition;
import Game.Utilities.Inventory;
import Game.Worlds.World;

import java.awt.*;
import java.lang.reflect.InvocationTargetException;

public class BoneShooter extends Shooter {
    public BoneShooter(World world) {
        super((Class) Bone.class, 20, 500, 500, world);
    }

    public void attack(double angle, Point position, Inventory inventory) {
        inventory.removeItem(Ammunition.BONE);
        super.attack(angle, position, inventory);
    }

    public boolean checkAmmunition(Inventory inventory) {
        return inventory.hasItem(Ammunition.BONE);
    }
}
