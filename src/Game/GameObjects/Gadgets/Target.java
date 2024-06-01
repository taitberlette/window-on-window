package Game.GameObjects.Gadgets;

import Assets.AssetManager;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

public class Target extends Switch {
    private BufferedImage targetImage;
    private final int IMAGE_SCALE = 4;

    public Target(Point position) {
        super(position, new Dimension(40, 64));

        targetImage = AssetManager.getImage("res\\Objects\\TargetLeft.png");
    }

    public void draw(Graphics2D graphics2D) {
        graphics2D.drawImage((Image) targetImage, (int) position.getX() - ((targetImage.getWidth() * IMAGE_SCALE) / 2), (int) position.getY() - ((targetImage.getHeight() * IMAGE_SCALE) / 2), targetImage.getWidth() * IMAGE_SCALE, targetImage.getHeight() * IMAGE_SCALE, null);
    }

}
