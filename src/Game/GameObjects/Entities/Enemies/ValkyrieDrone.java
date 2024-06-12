package Game.GameObjects.Entities.Enemies;

import Assets.AssetManager;
import Game.GameObjects.Projectiles.Flame;
import Game.GameObjects.Projectiles.Lightning;
import Game.GameObjects.Weapons.Shooter.RailGun;
import Game.Utilities.Ammunition;
import Game.Utilities.HorizontalDirection;
import Game.Worlds.World;
import Game.GameObjects.Entities.Player;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class ValkyrieDrone extends Enemy{
    private BufferedImage valkyrieDroneImage;

    private long slowerSpeedStart = 0;
    private long attackStart = 0;

    private final double IMAGE_SCALE = 1;
    private final double ATTACK_IMAGE_SCALE = 2;

    private static final long ATTACK_IMAGE_LENGTH = 250;
    private static final long SLOW_LENGTH = 1500;
    private static final int REGULAR_SPEED = 150;
    private static final int SLOW_SPEED = 100;
    private static final int LIGHTNING_SPEED = 1500;
    private static final int LIGHTNING_DAMAGE = 20;

    public ValkyrieDrone(Player player, World world) {
        super(new Dimension(128, 128), 5, 30, REGULAR_SPEED, 128, 250, player, world);

        valkyrieDroneImage = AssetManager.getImage("res\\Enemies\\Valk Drone.png");
    }

    public ValkyrieDrone(ArrayList<String> lines, Player player, World world) {
        super(new Dimension(128, 128), 5, 30, REGULAR_SPEED, 128, 250, lines, player, world);

        valkyrieDroneImage = AssetManager.getImage("res\\Enemies\\Valk Drone.png");
    }

    public void update(long deltaTime) {
        boolean slower = System.currentTimeMillis() - slowerSpeedStart < SLOW_LENGTH;
        maxSpeed = slower ? SLOW_SPEED : REGULAR_SPEED;

        if(!outRange && System.currentTimeMillis() - attackStart > 1000) {
            attackStart = System.currentTimeMillis();

            Point launch = new Point(position);
            launch.translate(valkyrieDroneImage.getWidth() * (lastDirection == HorizontalDirection.LEFT ? -1 : 1), 0);

            Lightning lightning = new Lightning();
            lightning.setLocation(position);
            lightning.launch(world, lastDirection == HorizontalDirection.LEFT ? Math.PI : 0, LIGHTNING_SPEED, LIGHTNING_DAMAGE, false);
            world.addGameObject(lightning);
        }

        super.update(deltaTime);
    }

    public void draw(Graphics2D graphics2D) {
        if(world == null) {
            return;
        }

        int offsetX = lastDirection == HorizontalDirection.RIGHT ? (int) (valkyrieDroneImage.getWidth() * IMAGE_SCALE) : 0;
        graphics2D.drawImage((Image) valkyrieDroneImage, ((int) (position.getX() - (valkyrieDroneImage.getWidth() * (IMAGE_SCALE / 2))) + offsetX), (int) ((int) position.getY() - (size.getHeight() / 2)), (int) (valkyrieDroneImage.getWidth() * IMAGE_SCALE * (lastDirection == HorizontalDirection.RIGHT ? -1 : 1)), (int) (valkyrieDroneImage.getHeight() * IMAGE_SCALE), null);
    }

    public void kill() {
        super.kill();
        player.getInventory().addItems(Ammunition.LIGHTNING_CHARGE, random.nextInt(5, 16));
        RailGun railGun = new RailGun(world);
        railGun.setLocation(position);
        world.addGameObject(railGun);
    }

    public void hurt(int damage) {
        super.hurt(damage);
        slowerSpeedStart = System.currentTimeMillis();
    }
}
