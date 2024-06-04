package Game.GameObjects.Weapons.Shooter;

import Assets.AssetManager;
import Game.GameObjects.Projectiles.Bone;
import Game.GameObjects.Projectiles.Flame;
import Game.GameObjects.Projectiles.Lightning;
import Game.Utilities.Ammunition;
import Game.Utilities.Inventory;
import Game.Worlds.World;

import java.awt.*;
import java.awt.image.BufferedImage;

public class FlameThrower extends Shooter {
    public FlameThrower() {
        super((Class) Flame.class, 10, 100, 750);
    }

    public void attack(double angle, Point position, Inventory inventory) {
        inventory.removeItem(Ammunition.FIRE_CHARGE);
        super.attack(angle, position, inventory);
    }

    public boolean checkAmmunition(Inventory inventory) {
        return inventory.hasItem(Ammunition.FIRE_CHARGE);
    }

    public BufferedImage getImage() {
        return AssetManager.getImage("res\\Weapons and Attacks\\FlameThrower.png");
    }
}
