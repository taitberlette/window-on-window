package Game.GameObjects.Entities.Enemies;

import Assets.AssetManager;
import Game.Utilities.Ammunition;
import Game.Utilities.HorizontalDirection;
import Game.Worlds.World;
import Game.GameObjects.Entities.Player;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Thrasher extends Enemy {

    private BufferedImage thrasherImage;
    private BufferedImage thrasherAttackImage;

    private long slowerSpeedStart = 0;
    private long attackStart = 0;

    private final double IMAGE_SCALE = 4;
    private final double ATTACK_IMAGE_SCALE = 2;

    private static final long ATTACK_IMAGE_LENGTH = 250;
    private static final long SLOW_LENGTH = 1500;
    private static final int REGULAR_SPEED = 150;
    private static final int SLOW_SPEED = 70;

    public Thrasher(Player player, World world) {
        super(new Dimension(128, 64), 5, 35, REGULAR_SPEED, 256, 300, player, world);

        thrasherImage = AssetManager.getImage("res\\Enemies\\Thrasher.png");
        thrasherAttackImage = AssetManager.getImage("res\\Enemies\\ThrasherAttack.png");
    }

    public Thrasher(ArrayList<String> lines, Player player, World world) {
        super(new Dimension(128, 64), 5, 35, REGULAR_SPEED, 256, 300, lines, player, world);

        thrasherImage = AssetManager.getImage("res\\Enemies\\Thrasher.png");
        thrasherAttackImage = AssetManager.getImage("res\\Enemies\\ThrasherAttack.png");
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

        int offsetX = lastDirection == HorizontalDirection.LEFT ? (int) (thrasherImage.getWidth() * IMAGE_SCALE) : 0;
        graphics2D.drawImage((Image) thrasherImage, ((int) (position.getX() - (thrasherImage.getWidth() * (IMAGE_SCALE / 2))) + offsetX), (int) ((int) position.getY() - size.getHeight()), (int) (thrasherImage.getWidth() * IMAGE_SCALE * (lastDirection == HorizontalDirection.LEFT ? -1 : 1)), (int) (thrasherImage.getHeight() * IMAGE_SCALE), null);

        if(System.currentTimeMillis() - attackStart < ATTACK_IMAGE_LENGTH) {
            int imageOffsetX = lastDirection == HorizontalDirection.LEFT ? (int) ((thrasherAttackImage.getWidth() * ATTACK_IMAGE_SCALE) - (thrasherImage.getWidth() * (IMAGE_SCALE / 2))) : (int) (thrasherImage.getWidth() * (IMAGE_SCALE / 2));
            graphics2D.drawImage((Image) thrasherAttackImage, (int) (position.getX() - (thrasherAttackImage.getWidth() * (ATTACK_IMAGE_SCALE / 2))) + imageOffsetX, (int) (position.getY() - (thrasherAttackImage.getHeight()) * (ATTACK_IMAGE_SCALE / 2)), (int) (thrasherAttackImage.getWidth() * ATTACK_IMAGE_SCALE * (lastDirection == HorizontalDirection.LEFT ? -1 : 1)), (int) (thrasherAttackImage.getHeight() * ATTACK_IMAGE_SCALE), null);
        }
    }

    public void kill() {
        super.kill();
        player.unlockPhotosynthesis();
    }

    public void hurt(int damage) {
        super.hurt(damage);
        slowerSpeedStart = System.currentTimeMillis();
    }

    public void attackAniamtion() {
        attackStart = System.currentTimeMillis();
    }
}
