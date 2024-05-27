package Game.GameObjects.Entities.Enemies;

import Game.Utilities.HorizontalDirection;
import Game.Worlds.TerraWorld;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

public class HellHound extends Enemy {
    private BufferedImage hellHoundImage;

    public HellHound() {
        super(new Dimension(128, 128), 5, 15, 115, 128, 100);

        try{
            hellHoundImage = ImageIO.read(new File("res\\Enemies\\HellHound.png"));
        } catch (Exception e) {
            System.out.println("Failed to load images for the player!");
        }
    }

    public void draw(Graphics2D graphics2D) {
        if(world == null) {
            return;
        }

        int offsetX = lastDirection == HorizontalDirection.LEFT ? 128 : 0;
        graphics2D.drawImage((Image) hellHoundImage, ((int) position.getX() - 64) + offsetX,(int) position.getY() - 64, 128 * (lastDirection == HorizontalDirection.LEFT ? -1 : 1), 128, null);
    }

}
