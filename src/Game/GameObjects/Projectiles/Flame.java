package Game.GameObjects.Projectiles;

import Assets.AssetManager;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Flame extends Projectile {
    private BufferedImage flameImage;

    public Flame() {
        super(new Dimension(16, 16));

        flameImage = AssetManager.getImage("res\\Weapons and Attacks\\FireBall.png");
    }

    public void draw(Graphics2D graphics2D) {
        super.draw(graphics2D);


        double angle = Math.atan2(velocityY, velocityX);

        graphics2D.rotate(-angle, (int) (position.getX()), (int) (position.getY()));
        graphics2D.drawImage(flameImage, (int) (position.getX() - (size.getWidth() / 2)), (int) (position.getY() - (size.getHeight() / 2)), null);
        graphics2D.rotate(angle, (int) (position.getX()), (int) (position.getY()));
    }
}
