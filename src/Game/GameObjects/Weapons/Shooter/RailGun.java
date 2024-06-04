package Game.GameObjects.Weapons.Shooter;

import Assets.AssetManager;
import Game.GameObjects.Projectiles.Flame;
import Game.GameObjects.Projectiles.Lightning;
import Game.GameObjects.Projectiles.Projectile;
import Game.Utilities.Ammunition;
import Game.Utilities.Inventory;
import Game.Worlds.World;

import java.awt.*;
import java.awt.image.BufferedImage;

public class RailGun extends Shooter {
    public RailGun() {
        super((Class) Lightning.class, 5, 10, 1500);
    }

    public void attack(double angle, Point position, Inventory inventory) {
        inventory.removeItem(Ammunition.LIGHTNING_CHARGE);
        super.attack(angle, position, inventory);
    }

    public boolean checkAmmunition(Inventory inventory) {
        return inventory.hasItem(Ammunition.LIGHTNING_CHARGE);
    }

    public BufferedImage getImage() {
        return AssetManager.getImage("res\\Weapons and Attacks\\RailGun.png");
    }
}
