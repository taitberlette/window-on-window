package Game.Worlds;

import Game.Game;
import Game.GameObjects.Entities.Player;
import Game.Levels.Level;
import Windows.WorldWindow;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;

public class TerraWorld extends World {

    public TerraWorld(Game game, Level level) {
        super(game, level);

        try{
            this.image = ImageIO.read(new File("res\\Level_Tutorial\\Terra.png"));
            this.collision = ImageIO.read(new File("res\\Level_Tutorial\\TerraCollision.png"));
        } catch (Exception e) {
            System.out.println("Failed to load 'Tutorial Terra.png'");
        }

    }
}
