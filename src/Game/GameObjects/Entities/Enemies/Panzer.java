package Game.GameObjects.Entities.Enemies;

import Assets.AssetManager;
import Game.GameObjects.Entities.Player;
import Game.GameObjects.Weapons.Shooter.FlameThrower;
import Game.Utilities.Ammunition;
import Game.Utilities.HorizontalDirection;
import Game.Worlds.World;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Panzer extends Enemy {
    private BufferedImage panzerImage;
    private BufferedImage panzerAttackImage;

    private long slowerSpeedStart = 0;
    private long attackStart = 0;

    private final double IMAGE_SCALE = 4;
    private final double ATTACK_IMAGE_SCALE = 2;

    private static final long ATTACK_IMAGE_LENGTH = 250;
    private static final long SLOW_LENGTH = 1500;
    private static final int REGULAR_SPEED = 100;
    private static final int SLOW_SPEED = 80;

    public Panzer(Player player, World world) {
        super(new Dimension(128, 64), 7, 20, REGULAR_SPEED, 256, 150, player, world);

        panzerImage = AssetManager.getImage("res\\Enemies\\Panzer.png");
        panzerAttackImage = AssetManager.getImage("res\\Enemies\\PanzerAttack.png");
    }

    public Panzer(ArrayList<String> lines, Player player, World world) {
        super(new Dimension(128, 64), 7, 20, REGULAR_SPEED, 256, 150, lines, player, world);

        panzerImage = AssetManager.getImage("res\\Enemies\\Panzer.png");
        panzerAttackImage = AssetManager.getImage("res\\Enemies\\PanzerAttack.png");
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

        int offsetX = lastDirection == HorizontalDirection.LEFT ? (int) (panzerImage.getWidth() * IMAGE_SCALE) : 0;
        graphics2D.drawImage((Image) panzerImage, ((int) (position.getX() - (panzerImage.getWidth() * (IMAGE_SCALE / 2))) + offsetX), (int) ((int) position.getY() - size.getHeight()), (int) (panzerImage.getWidth() * IMAGE_SCALE * (lastDirection == HorizontalDirection.LEFT ? -1 : 1)), (int) (panzerImage.getHeight() * IMAGE_SCALE), null);

        if(System.currentTimeMillis() - attackStart < ATTACK_IMAGE_LENGTH) {
            int imageOffsetX = lastDirection == HorizontalDirection.LEFT ? (int) ((panzerAttackImage.getWidth() * ATTACK_IMAGE_SCALE) - (panzerImage.getWidth() * (IMAGE_SCALE / 2))) : (int) (panzerImage.getWidth() * (IMAGE_SCALE / 2));
            graphics2D.drawImage((Image) panzerAttackImage, (int) (position.getX() - (panzerAttackImage.getWidth() * (ATTACK_IMAGE_SCALE / 2))) + imageOffsetX, (int) (position.getY() - (panzerAttackImage.getHeight()) * (ATTACK_IMAGE_SCALE / 2)), (int) (panzerAttackImage.getWidth() * ATTACK_IMAGE_SCALE * (lastDirection == HorizontalDirection.LEFT ? -1 : 1)), (int) (panzerAttackImage.getHeight() * ATTACK_IMAGE_SCALE), null);
        }
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

    public void attackAniamtion() {
        attackStart = System.currentTimeMillis();
    }
}
