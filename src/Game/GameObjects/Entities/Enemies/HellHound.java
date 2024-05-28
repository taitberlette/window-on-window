package Game.GameObjects.Entities.Enemies;

import Game.Utilities.Ammunition;
import Game.Utilities.HorizontalDirection;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Random;

public class HellHound extends Enemy {
    private BufferedImage hellHoundImage;
    private BufferedImage hellHoundAttackImage;

    private long slowerSpeedStart = 0;
    private long attackStart = 0;

    private final double IMAGE_SCALE = 4;
    private final double ATTACK_IMAGE_SCALE = 2;

    private static final long ATTACK_IMAGE_LENGTH = 250;
    private static final long SLOW_LENGTH = 1500;
    private static final int REGULAR_SPEED = 150;
    private static final int SLOW_SPEED = 100;

    public HellHound() {
        super(new Dimension(128, 64), 5, 15, REGULAR_SPEED, 256, 100);

        try{
            hellHoundImage = ImageIO.read(new File("res\\Enemies\\HellHound.png"));
            hellHoundAttackImage = ImageIO.read(new File("res\\Enemies\\HellHoundAttack.png"));
        } catch (Exception e) {
            System.out.println("Failed to load images for the player!");
        }
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

        int offsetX = lastDirection == HorizontalDirection.LEFT ? (int) (hellHoundImage.getWidth() * IMAGE_SCALE) : 0;
        graphics2D.drawImage((Image) hellHoundImage, ((int) (position.getX() - (hellHoundImage.getWidth() * (IMAGE_SCALE / 2))) + offsetX), (int) ((int) position.getY() - size.getHeight()), (int) (hellHoundImage.getWidth() * IMAGE_SCALE * (lastDirection == HorizontalDirection.LEFT ? -1 : 1)), (int) (hellHoundImage.getHeight() * IMAGE_SCALE), null);

        if(System.currentTimeMillis() - attackStart < ATTACK_IMAGE_LENGTH) {
            int imageOffsetX = lastDirection == HorizontalDirection.LEFT ? (int) ((hellHoundAttackImage.getWidth() * ATTACK_IMAGE_SCALE) - (hellHoundImage.getWidth() * (IMAGE_SCALE / 2))) : (int) (hellHoundImage.getWidth() * (IMAGE_SCALE / 2));
            graphics2D.drawImage((Image) hellHoundAttackImage, (int) (position.getX() - (hellHoundAttackImage.getWidth() * (ATTACK_IMAGE_SCALE / 2))) + imageOffsetX, (int) (position.getY() - (hellHoundAttackImage.getHeight()) * (ATTACK_IMAGE_SCALE / 2)), (int) (hellHoundAttackImage.getWidth() * ATTACK_IMAGE_SCALE * (lastDirection == HorizontalDirection.LEFT ? -1 : 1)), (int) (hellHoundAttackImage.getHeight() * ATTACK_IMAGE_SCALE), null);
        }
    }

    public void kill() {
        player.getInventory().addItems(Ammunition.BONE, random.nextInt(1, 11));
        player.getInventory().addItems(Ammunition.FIRE_CHARGE, random.nextInt(10, 51));
    }

    public void hurt(int damage) {
        super.hurt(damage);
        slowerSpeedStart = System.currentTimeMillis();
    }

    public void attackAniamtion() {
        attackStart = System.currentTimeMillis();
    }
}
