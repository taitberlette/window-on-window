package Game.GameObjects.Projectiles;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

public class Bone extends Projectile {
    private BufferedImage boneImage;

    public Bone() {
        super(new Dimension(16, 16));

        try{
            boneImage = ImageIO.read(new File("res\\Ammunition and Skills\\Bone1.png"));
        } catch (Exception e) {
            System.out.println("Failed to load images for the bone!");
        }
    }

    public void draw(Graphics2D graphics2D) {
        super.draw(graphics2D);

        double angle = Math.atan2(velocityY, velocityX) - Math.PI / 4;

        graphics2D.rotate(-angle, (int) (position.getX() - (size.getWidth() / 2)), (int) (position.getY() - (size.getHeight() / 2)));
        graphics2D.drawImage(boneImage, (int) (position.getX() - (size.getWidth() / 2)), (int) (position.getY() - (size.getHeight() / 2)), null);
        graphics2D.rotate(angle, (int) (position.getX() - (size.getWidth() / 2)), (int) (position.getY() - (size.getHeight() / 2)));
    }
}
