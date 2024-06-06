package Game.Worlds;

import Assets.AssetManager;
import Game.Game;
import Game.GameObjects.Entities.Player;
import Game.Levels.Level;
import Windows.WorldWindow;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;

public class EtherWorld extends World {
    private BufferedImage testing;

    public EtherWorld(Game game, Level level, Player player, String levelPath) {
        super(game, level, player, levelPath);

        this.image = AssetManager.getImage("res\\" + levelPath + "\\Ether.png");
        this.collision = AssetManager.getImage("res\\" + levelPath + "\\EtherCollision.png");
    }

    public EtherWorld(ArrayList<String> lines, Game game, Level level, Player player, String levelPath) {
        super(lines, game, level, player, levelPath);

        this.image = AssetManager.getImage("res\\" + levelPath + "\\Ether.png");
        this.collision = AssetManager.getImage("res\\" + levelPath + "\\EtherCollision.png");

    }
}
