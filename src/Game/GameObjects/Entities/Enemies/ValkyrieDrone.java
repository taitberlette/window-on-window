package Game.GameObjects.Entities.Enemies;

import Assets.AssetManager;
import Game.GameObjects.Weapons.Shooter.RailGun;
import Game.Utilities.Ammunition;
import Game.Utilities.HorizontalDirection;

import java.awt.*;
import java.awt.image.BufferedImage;

public class ValkyrieDrone extends Enemy{
    private BufferedImage valkyrieDroneImage;
    private BufferedImage valkyrieDroneAttackImage;

    private long slowerSpeedStart = 0;
    private long attackStart = 0;

    private final double IMAGE_SCALE = 4;
    private final double ATTACK_IMAGE_SCALE = 2;

    private static final long ATTACK_IMAGE_LENGTH = 250;
    private static final long SLOW_LENGTH = 1500;
    private static final int REGULAR_SPEED = 150;
    private static final int SLOW_SPEED = 100;

    public ValkyrieDrone() {
        super(new Dimension(128, 64), 5, 30, REGULAR_SPEED, 256, 250);

        valkyrieDroneImage = AssetManager.getImage("res\\Enemies\\ValkyrieDrone.png");
        valkyrieDroneAttackImage = AssetManager.getImage("res\\Enemies\\ValkyrieDroneImage.png");
    }

    public void update(long deltaTime) {
        boolean slower = System.currentTimeMillis() - slowerSpeedStart < SLOW_LENGTH;
        maxSpeed = slower ? SLOW_SPEED : REGULAR_SPEED;

        super.update(deltaTime);
    }

    public void draw(Graphics2D graphics2D) {
        if(world == null) {
            return;
        }

        int offsetX = lastDirection == HorizontalDirection.LEFT ? (int) (valkyrieDroneImage.getWidth() * IMAGE_SCALE) : 0;
        graphics2D.drawImage((Image) valkyrieDroneImage, ((int) (position.getX() - (valkyrieDroneImage.getWidth() * (IMAGE_SCALE / 2))) + offsetX), (int) ((int) position.getY() - size.getHeight()), (int) (valkyrieDroneImage.getWidth() * IMAGE_SCALE * (lastDirection == HorizontalDirection.LEFT ? -1 : 1)), (int) (valkyrieDroneImage.getHeight() * IMAGE_SCALE), null);

        if(System.currentTimeMillis() - attackStart < ATTACK_IMAGE_LENGTH) {
            int imageOffsetX = lastDirection == HorizontalDirection.LEFT ? (int) ((valkyrieDroneAttackImage.getWidth() * ATTACK_IMAGE_SCALE) - (valkyrieDroneImage.getWidth() * (IMAGE_SCALE / 2))) : (int) (valkyrieDroneImage.getWidth() * (IMAGE_SCALE / 2));
            graphics2D.drawImage((Image) valkyrieDroneAttackImage, (int) (position.getX() - (valkyrieDroneAttackImage.getWidth() * (ATTACK_IMAGE_SCALE / 2))) + imageOffsetX, (int) (position.getY() - (valkyrieDroneAttackImage.getHeight()) * (ATTACK_IMAGE_SCALE / 2)), (int) (valkyrieDroneAttackImage.getWidth() * ATTACK_IMAGE_SCALE * (lastDirection == HorizontalDirection.LEFT ? -1 : 1)), (int) (valkyrieDroneAttackImage.getHeight() * ATTACK_IMAGE_SCALE), null);
        }
    }

    public void kill() {
        player.getInventory().addItems(Ammunition.LIGHTNING_CHARGE, random.nextInt(5, 16));
        RailGun railGun = new RailGun();
        railGun.setLocation(position);
        world.addGameObject(railGun);
    }

    public void hurt(int damage) {
        super.hurt(damage);
        slowerSpeedStart = System.currentTimeMillis();
    }

    public void attackAniamtion() {
        attackStart = System.currentTimeMillis();
    }
}
