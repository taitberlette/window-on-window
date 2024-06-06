package Game.GameObjects.Entities.Enemies;

import Assets.AssetManager;
import Game.Utilities.Ammunition;
import Game.Utilities.HorizontalDirection;
import Game.Worlds.World;
import Game.GameObjects.Entities.Player;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class SilverBack extends Enemy {
    private BufferedImage silverBackImage;
    private BufferedImage silverBackAttackImage;

    private long slowerSpeedStart = 0;
    private long attackStart = 0;

    private final double IMAGE_SCALE = 4;
    private final double ATTACK_IMAGE_SCALE = 2;

    private static final long ATTACK_IMAGE_LENGTH = 250;
    private static final long SLOW_LENGTH = 2000;
    private static final int REGULAR_SPEED = 200;
    private static final int SLOW_SPEED = 100;

    public SilverBack(Player player, World world) {
        super(new Dimension(128, 64), 3, 25, REGULAR_SPEED, 256, 200, player, world);

        silverBackImage = AssetManager.getImage("res\\Enemies\\SilverBack.png");
        silverBackAttackImage = AssetManager.getImage("res\\Enemies\\SilverBackAttack.png");
    }

    public SilverBack(ArrayList<String> lines, Player player, World world) {
        super(new Dimension(128, 64), 3, 25, REGULAR_SPEED, 256, 200, lines, player, world);

        silverBackImage = AssetManager.getImage("res\\Enemies\\SilverBack.png");
        silverBackAttackImage = AssetManager.getImage("res\\Enemies\\SilverBackAttack.png");
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

        int offsetX = lastDirection == HorizontalDirection.LEFT ? (int) (silverBackImage.getWidth() * IMAGE_SCALE) : 0;
        graphics2D.drawImage((Image) silverBackImage, ((int) (position.getX() - (silverBackImage.getWidth() * (IMAGE_SCALE / 2))) + offsetX), (int) ((int) position.getY() - size.getHeight()), (int) (silverBackImage.getWidth() * IMAGE_SCALE * (lastDirection == HorizontalDirection.LEFT ? -1 : 1)), (int) (silverBackImage.getHeight() * IMAGE_SCALE), null);

        if(System.currentTimeMillis() - attackStart < ATTACK_IMAGE_LENGTH) {
            int imageOffsetX = lastDirection == HorizontalDirection.LEFT ? (int) ((silverBackAttackImage.getWidth() * ATTACK_IMAGE_SCALE) - (silverBackImage.getWidth() * (IMAGE_SCALE / 2))) : (int) (silverBackImage.getWidth() * (IMAGE_SCALE / 2));
            graphics2D.drawImage((Image) silverBackAttackImage, (int) (position.getX() - (silverBackAttackImage.getWidth() * (ATTACK_IMAGE_SCALE / 2))) + imageOffsetX, (int) (position.getY() - (silverBackAttackImage.getHeight()) * (ATTACK_IMAGE_SCALE / 2)), (int) (silverBackAttackImage.getWidth() * ATTACK_IMAGE_SCALE * (lastDirection == HorizontalDirection.LEFT ? -1 : 1)), (int) (silverBackAttackImage.getHeight() * ATTACK_IMAGE_SCALE), null);
        }
    }

    public void kill() {
        super.kill();
       player.unlockTunnelVision();
    }

    public void hurt(int damage) {
        super.hurt(damage);
        slowerSpeedStart = System.currentTimeMillis();
    }

    public void attackAniamtion() {
        attackStart = System.currentTimeMillis();
    }
}
