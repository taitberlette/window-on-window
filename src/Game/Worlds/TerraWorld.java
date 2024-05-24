package Game.Worlds;

import Game.Game;
import Game.GameObjects.Entities.Player;
import Game.Levels.Level;
import Windows.WorldWindow;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;

public class TerraWorld extends World {

    public TerraWorld(Game game, Level level, String levelPath) {
        super(game, level, levelPath);

        try{
            this.image = ImageIO.read(new File("res\\" + levelPath + "\\Terra.png"));
            this.collision = ImageIO.read(new File("res\\" + levelPath + "\\TerraCollision.png"));
        } catch (Exception e) {
            System.out.println("Failed to load Terra image or collider for " + levelPath);
        }

    }
}
