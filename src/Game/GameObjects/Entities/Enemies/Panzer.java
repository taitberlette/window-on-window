package Game.GameObjects.Entities.Enemies;

import Assets.AssetManager;
import Game.GameObjects.Entities.Player;
import Game.GameObjects.Projectiles.Flame;
import Game.GameObjects.Weapons.Shooter.FlameThrower;
import Game.Utilities.Ammunition;
import Game.Utilities.HorizontalDirection;
import Game.Worlds.World;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Panzer extends Enemy {
    private BufferedImage[] panzerImages = new BufferedImage[2];
    private BufferedImage panzerArmImage;

    private long slowerSpeedStart = 0;
    private long attackStart = 0;
    private int animationFrame = 0;
    private long lastFrame = 0;

    private final double IMAGE_SCALE = 1;
    private final double ATTACK_IMAGE_SCALE = 1;

    private static final long ATTACK_IMAGE_LENGTH = 250;
    private static final long SLOW_LENGTH = 1500;
    private static final int REGULAR_SPEED = 100;
    private static final int SLOW_SPEED = 80;
    private static final int FIRE_SPEED = 1500;
    private static final int FIRE_DAMAGE = 20;

    public Panzer(Player player, World world) {
        super(new Dimension(128, 128), 7, 0, REGULAR_SPEED, 128, 150, player, world);

        panzerImages[0] = AssetManager.getImage("res\\Enemies\\Panzer.png");
        panzerImages[1] = AssetManager.getImage("res\\Enemies\\Panzer Walking.png");
        panzerArmImage = AssetManager.getImage("res\\Enemies\\Panzer Arm.png");
    }

    public Panzer(ArrayList<String> lines, Player player, World world) {
        super(new Dimension(128, 128), 7, 0, REGULAR_SPEED, 128, 150, lines, player, world);

        panzerImages[0] = AssetManager.getImage("res\\Enemies\\Panzer.png");
        panzerImages[1] = AssetManager.getImage("res\\Enemies\\Panzer Walking.png");
        panzerArmImage = AssetManager.getImage("res\\Enemies\\Panzer Arm.png");
    }

    public void update(long deltaTime) {
        boolean slower = System.currentTimeMillis() - slowerSpeedStart < SLOW_LENGTH;
        maxSpeed = slower ? SLOW_SPEED : REGULAR_SPEED;

        if((animationFrame % 2 != 0 || velocityX != 0) && onGround) {
            if(System.currentTimeMillis() - lastFrame > Math.abs(50000 / maxSpeed)) {
                animationFrame++;
                lastFrame = System.currentTimeMillis();
            }
        }


        double distance = player.getLocation().getLocation().distance(position);

        if(distance <= vision && System.currentTimeMillis() - attackStart > 1000) {
            attackStart = System.currentTimeMillis();

            Point launch = new Point(position);
            launch.translate(panzerArmImage.getWidth() * (lastDirection == HorizontalDirection.LEFT ? -1 : 1), 0);

            Flame flame = new Flame();
            flame.setLocation(position);
            flame.launch(world, lastDirection == HorizontalDirection.LEFT ? Math.PI : 0, FIRE_SPEED, FIRE_DAMAGE, false);
            world.addGameObject(flame);
        }

        super.update(deltaTime);
    }

    public void draw(Graphics2D graphics2D) {
        if(world == null) {
            return;
        }

        BufferedImage panzerImage = panzerImages[animationFrame % panzerImages.length];

        int offsetX = lastDirection == HorizontalDirection.LEFT ? (int) ((int) (panzerImage.getWidth() * IMAGE_SCALE)) : 0;
        graphics2D.drawImage((Image) panzerImage, ((int) (position.getX() - (panzerImage.getWidth() * (IMAGE_SCALE / 2))) + offsetX), (int) ((int) position.getY() - (size.getHeight() / 2)), (int) (panzerImage.getWidth() * IMAGE_SCALE * (lastDirection == HorizontalDirection.LEFT ? -1 : 1)), (int) (panzerImage.getHeight() * IMAGE_SCALE), null);

        int imageOffsetX = lastDirection == HorizontalDirection.LEFT ? (int) ((int) ((panzerArmImage.getWidth() * ATTACK_IMAGE_SCALE)) - ((panzerImage.getWidth() * IMAGE_SCALE) / 4)) : (int) ((panzerImage.getWidth() * IMAGE_SCALE) / 4);
        graphics2D.drawImage((Image) panzerArmImage, (int) (position.getX() - (panzerArmImage.getWidth() * (ATTACK_IMAGE_SCALE / 2))) + imageOffsetX, (int) (position.getY() - (panzerArmImage.getHeight()) * (ATTACK_IMAGE_SCALE / 2)), (int) (panzerArmImage.getWidth() * ATTACK_IMAGE_SCALE * (lastDirection == HorizontalDirection.LEFT ? -1 : 1)), (int) (panzerArmImage.getHeight() * ATTACK_IMAGE_SCALE), null);
    }

    public void kill() {
        super.kill();
        FlameThrower flameThrower = new FlameThrower(world);
        flameThrower.setLocation(position);
        world.addGameObject(flameThrower);
    }

    public void hurt(int damage) {
        super.hurt(damage);
        slowerSpeedStart = System.currentTimeMillis();
    }
}
