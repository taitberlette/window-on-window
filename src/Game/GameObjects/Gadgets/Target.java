package Game.GameObjects.Gadgets;

import Assets.AssetManager;
import Game.Worlds.World;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;

public class Target extends Switch {
    private BufferedImage targetImage;
    private final int IMAGE_SCALE = 4;

    public Target(Point position, int switcherId, World world) {
        super(position, new Dimension(40, 64), switcherId, world);

        targetImage = AssetManager.getImage("res\\Objects\\TargetLeft.png");
    }

    public Target(ArrayList<String> lines, World world) {
        super(lines, new Dimension(40, 64), world);


        targetImage = AssetManager.getImage("res\\Objects\\TargetLeft.png");
    }

    public void draw(Graphics2D graphics2D) {
        graphics2D.drawImage((Image) targetImage, (int) position.getX() - ((targetImage.getWidth() * IMAGE_SCALE) / 2), (int) position.getY() - ((targetImage.getHeight() * IMAGE_SCALE) / 2), targetImage.getWidth() * IMAGE_SCALE, targetImage.getHeight() * IMAGE_SCALE, null);
    }

}
