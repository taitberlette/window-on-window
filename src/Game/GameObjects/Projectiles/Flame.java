package Game.GameObjects.Projectiles;

import Assets.AssetManager;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Flame extends Projectile {
    private BufferedImage flameImage;
    private double IMAGE_SCALE = 1.5;

    public Flame() {
        super(new Dimension(24, 24));

        flameImage = AssetManager.getImage("res\\Weapons and Attacks\\FireBall.png");
    }

    public void draw(Graphics2D graphics2D) {
        super.draw(graphics2D);


        double angle = Math.atan2(velocityY, velocityX);

        graphics2D.rotate(-angle, (int) (position.getX()), (int) (position.getY()));
        graphics2D.drawImage((Image) flameImage, (int) (position.getX() - ((size.getWidth() * IMAGE_SCALE) / 2)), (int) (position.getY() - ((size.getHeight() * IMAGE_SCALE) / 2)), (int) (size.getWidth() * IMAGE_SCALE), (int) (size.getHeight() * IMAGE_SCALE), null);
        graphics2D.rotate(angle, (int) (position.getX()), (int) (position.getY()));
    }
}
