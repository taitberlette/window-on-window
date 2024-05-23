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

    public EtherWorld(Game game, Level level) {
        super(game, level);

        try{
            this.image = ImageIO.read(new File("res\\Level_Tutorial\\Ether.png"));
            this.collision = ImageIO.read(new File("res\\Level_Tutorial\\EtherCollision.png"));
        } catch (Exception e) {
            System.out.println("Failed to load 'Tutorial Ether.png'");
        }
    }
}
