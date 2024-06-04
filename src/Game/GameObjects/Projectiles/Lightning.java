package Game.GameObjects.Projectiles;

import Assets.AssetManager;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Lightning extends Projectile {
    private BufferedImage lightningImage;

    public Lightning() {
        super(new Dimension(16, 16));

        lightningImage = AssetManager.getImage("res\\Weapons and Attacks\\LightingMiddelFrame.png");
    }

    public void draw(Graphics2D graphics2D) {
        super.draw(graphics2D);

        double angle = Math.atan2(velocityY, velocityX);

        graphics2D.rotate(-angle, (int) (position.getX()), (int) (position.getY()));
        graphics2D.drawImage(lightningImage, (int) (position.getX() - (size.getWidth() / 2)), (int) (position.getY() - (size.getHeight() / 2)), null);
        graphics2D.rotate(angle, (int) (position.getX()), (int) (position.getY()));
    }
}
