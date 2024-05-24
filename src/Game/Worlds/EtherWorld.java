package Game.Worlds;

import Game.Game;
import Game.Levels.Level;
import Windows.WorldWindow;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

public class EtherWorld extends World {
    private BufferedImage testing;

    public EtherWorld(Game game, Level level, String levelPath) {
        super(game, level, levelPath);

        try{
            this.image = ImageIO.read(new File("res\\" + levelPath + "\\Ether.png"));
            this.collision = ImageIO.read(new File("res\\" + levelPath + "\\EtherCollision.png"));
        } catch (Exception e) {
            System.out.println("Failed to load Ether image or collider for " + levelPath);
        }
    }
}
