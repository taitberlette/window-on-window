package Game.GameObjects.Entities.Enemies;

import Assets.AssetManager;
import Game.GameObjects.Projectiles.Flame;
import Game.Utilities.Ammunition;
import Game.Utilities.HorizontalDirection;
import Game.Worlds.World;
import Game.GameObjects.Entities.Player;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Thrasher extends Enemy {

    private BufferedImage[] thrasherImages = new BufferedImage[2];
    private BufferedImage[] thrasherAttackImages = new BufferedImage[2];

    private long slowerSpeedStart = 0;
    private long attackStart = 0;
    private int animationFrame = 0;
    private long lastFrame = 0;

    private final double IMAGE_SCALE = 2;
    private final double ATTACK_IMAGE_SCALE = 2;

    private static final long ATTACK_IMAGE_LENGTH = 250;
    private static final long SLOW_LENGTH = 1500;
    private static final int REGULAR_SPEED = 0;
    private static final int SLOW_SPEED = 0;
    private static final int BONE_DAMAGE = 35;
    private static final int BONE_SPEED = 1500;

    public Thrasher(Player player, World world) {
        super(new Dimension(256, 256), 5, 35, REGULAR_SPEED, 256, 300, player, world);

        thrasherImages[0] = AssetManager.getImage("res\\Enemies\\Thrasher.png");
        thrasherImages[1] = AssetManager.getImage("res\\Enemies\\Thrasher2.png");
        thrasherAttackImages[0] = AssetManager.getImage("res\\Enemies\\Bone1.png");
        thrasherAttackImages[1] = AssetManager.getImage("res\\Enemies\\Bone2.png");
    }

    public Thrasher(ArrayList<String> lines, Player player, World world) {
        super(new Dimension(256, 256), 5, 35, REGULAR_SPEED, 256, 300, lines, player, world);

        thrasherImages[0] = AssetManager.getImage("res\\Enemies\\Thrasher.png");
        thrasherImages[1] = AssetManager.getImage("res\\Enemies\\Thrasher2.png");
        thrasherAttackImages[0] = AssetManager.getImage("res\\Enemies\\Bone1.png");
        thrasherAttackImages[1] = AssetManager.getImage("res\\Enemies\\Bone2.png");
    }

    public void update(long deltaTime) {
        boolean slower = System.currentTimeMillis() - slowerSpeedStart < SLOW_LENGTH;
        maxSpeed = slower ? SLOW_SPEED : REGULAR_SPEED;

        if((animationFrame % 2 != 0 || velocityX != 0) && onGround) {
            if(System.currentTimeMillis() - lastFrame > 1000) {
                animationFrame++;
                lastFrame = System.currentTimeMillis();
            }
        }
        if(!outRange && System.currentTimeMillis() - attackStart > 1000) {
            attackStart = System.currentTimeMillis();

            Point launch = new Point(position);
            launch.translate(thrasherImages[0].getWidth() * (lastDirection == HorizontalDirection.LEFT ? -1 : 1), 0);

            Flame flame = new Flame();
            flame.setLocation(position);
            flame.launch(world, lastDirection == HorizontalDirection.LEFT ? Math.PI : 0, BONE_SPEED, BONE_DAMAGE, false);
            world.addGameObject(flame);
        }

        super.update(deltaTime);
    }

    public void draw(Graphics2D graphics2D) {
        if(world == null) {
            return;
        }
        BufferedImage thrasherImage = thrasherImages[animationFrame % thrasherImages.length];

        int offsetX = lastDirection == HorizontalDirection.LEFT ? (int) (thrasherImage.getWidth() * IMAGE_SCALE) : 0;
        graphics2D.drawImage((Image) thrasherImage, ((int) (position.getX() - (thrasherImage.getWidth() * (IMAGE_SCALE / 2))) + offsetX), (int) (position.getY() - ((size.getHeight() * IMAGE_SCALE) / 4)), (int) (thrasherImage.getWidth() * IMAGE_SCALE * (lastDirection == HorizontalDirection.LEFT ? -1 : 1)), (int) (thrasherImage.getHeight() * IMAGE_SCALE), null);

        BufferedImage thrasherAttackImage = thrasherAttackImages[animationFrame % thrasherAttackImages.length];

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

    public void startAttack() {
        attackStart = System.currentTimeMillis();
    }
}
