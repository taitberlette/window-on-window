package Game.GameObjects.Projectiles;

import Assets.AssetManager;
import Game.Worlds.World;
import Windows.WorldWindow;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;

public class TunnelVision extends Projectile {
    private BufferedImage tunnelVisionImage;
    private WorldWindow worldWindow;

    public TunnelVision() {
        super(new Dimension(16, 16));

        tunnelVisionImage = AssetManager.getImage("res\\Ammunition and Skills\\TunnelVisionProjectile.png");
    }

    public TunnelVision(ArrayList<String> lines, World world) {
        super(lines, new Dimension(16, 16), world);
        tunnelVisionImage = AssetManager.getImage("res\\Ammunition and Skills\\TunnelVisionProjectile.png");
    }

    public void launch(World world, double angle, double velocity, int damage) {
        super.launch(world, angle, velocity, damage);

        worldWindow = new WorldWindow(world);
        worldWindow.setTarget(this);
        world.getLevel().addWorldWindow(worldWindow);
        worldWindow.setVisible(true);
    }

    public void draw(Graphics2D graphics2D) {
        super.draw(graphics2D);

        double angle = Math.atan2(velocityY, velocityX) + (5 * Math.PI) / 4;

        graphics2D.rotate(-angle, (int) (position.getX()), (int) (position.getY()));
        graphics2D.drawImage(tunnelVisionImage, (int) (position.getX() - (size.getWidth() / 2)), (int) (position.getY() - (size.getHeight() / 2)), null);
        graphics2D.rotate(angle, (int) (position.getX()), (int) (position.getY()));
    }

    public void kill() {
        super.kill();

        if (worldWindow != null) {
            worldWindow.setVisible(false);
            world.getLevel().removeWorldWindow(worldWindow);
            worldWindow = null;
        }
    }
}
