package Game.Worlds;

import Assets.AssetManager;
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

        this.image = AssetManager.getImage("res\\" + levelPath + "\\Terra.png");
        this.collision = AssetManager.getImage("res\\" + levelPath + "\\TerraCollision.png");
    }
}
