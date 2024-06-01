package Game.GameObjects.Projectiles;

import Assets.AssetManager;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

public class Bone extends Projectile {
    private BufferedImage boneImage;

    public Bone() {
        super(new Dimension(16, 16));

        boneImage = AssetManager.getImage("res\\Ammunition and Skills\\Bone1.png");
    }

    public void draw(Graphics2D graphics2D) {
        super.draw(graphics2D);

        double angle = Math.atan2(velocityY, velocityX) - Math.PI / 4;

        graphics2D.rotate(-angle, (int) (position.getX()), (int) (position.getY()));
        graphics2D.drawImage(boneImage, (int) (position.getX() - (size.getWidth() / 2)), (int) (position.getY() - (size.getHeight() / 2)), null);
        graphics2D.rotate(angle, (int) (position.getX()), (int) (position.getY()));
    }
}
