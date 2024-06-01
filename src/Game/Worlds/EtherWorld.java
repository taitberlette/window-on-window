package Game.Worlds;

import Assets.AssetManager;
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

        this.image = AssetManager.getImage("res\\" + levelPath + "\\Ether.png");
        this.collision = AssetManager.getImage("res\\" + levelPath + "\\EtherCollision.png");
    }
}
