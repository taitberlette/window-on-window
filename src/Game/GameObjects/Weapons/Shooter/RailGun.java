package Game.GameObjects.Weapons.Shooter;

import Assets.AssetManager;
import Game.GameObjects.Projectiles.Lightning;
import Game.Utilities.Ammunition;
import Game.Utilities.Inventory;
import Game.Worlds.World;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class RailGun extends Shooter {
    private BufferedImage railGunImage;
    private final int IMAGE_SCALE = 1;
    private Dimension size = new Dimension(48, 27);
    public RailGun(World world) {
        super((Class) Lightning.class, 5, 20, 750, world);
        railGunImage = AssetManager.getImage("res\\Weapons and Attacks\\RailGun.png");
    }

    public RailGun(ArrayList<String> lines, World world) {
        super((Class) Lightning.class, 5, 20, 750, lines, world);
        railGunImage = AssetManager.getImage("res\\Weapons and Attacks\\RailGun.png");
    }

    public void draw(Graphics2D graphics2D) {
        graphics2D.drawImage((Image) railGunImage, (int) (position.getX() - (railGunImage.getWidth() * IMAGE_SCALE) / 2), (int) (position.getY() - (railGunImage.getHeight() * IMAGE_SCALE) / 2), railGunImage.getWidth() * IMAGE_SCALE, (railGunImage.getHeight() * IMAGE_SCALE), null);
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

    public Rectangle getBounds() {
        return new Rectangle((int) (position.getX() - (size.getWidth()) / 2), (int) (position.getY() - (size.getHeight()) / 2), (int) (size.getWidth()), (int) (size.getHeight()));
    }
}
