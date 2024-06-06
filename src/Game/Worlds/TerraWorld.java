package Game.Worlds;

import Assets.AssetManager;
import Game.Game;
import Game.GameObjects.Entities.Player;
import Game.Levels.Level;
import Windows.WorldWindow;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.util.ArrayList;

public class TerraWorld extends World {

    public TerraWorld(Game game, Level level, Player player, String levelPath) {
        super(game, level, player, levelPath);

        this.image = AssetManager.getImage("res\\" + levelPath + "\\Terra.png");
        this.collision = AssetManager.getImage("res\\" + levelPath + "\\TerraCollision.png");
    }

    public TerraWorld(ArrayList<String> lines, Game game, Level level, Player player, String levelPath) {
        super(lines, game, level, player, levelPath);

        this.image = AssetManager.getImage("res\\" + levelPath + "\\Terra.png");
        this.collision = AssetManager.getImage("res\\" + levelPath + "\\TerraCollision.png");

    }
}
