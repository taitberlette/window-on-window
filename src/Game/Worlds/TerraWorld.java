package Game.Worlds;

import Game.Game;
import Game.GameObjects.Entities.Player;
import Game.Level;
import Windows.WorldWindow;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.security.Key;

public class TerraWorld extends World {

    public TerraWorld(Game game, Level level) {
        super(game, level);

        try{
            this.image = ImageIO.read(new File("res\\Level_Tutorial\\Terra.png"));
            this.collision = ImageIO.read(new File("res\\Level_Tutorial\\TerraCollision.png"));
        } catch (Exception e) {
            System.out.println("Failed to load 'Tutorial Terra.png'");
        }

        WorldWindow worldWindow = new WorldWindow(this);
        Player player = new Player();

        player.setWorld(this);

        worldWindow.setTarget(player);
        worldWindow.setFocusable(true);
        worldWindow.requestFocus();
        worldWindow.setKeyListener(game);
        worldWindows.add(worldWindow);

        this.addEntity(player);
    }

    public void draw(Graphics2D graphics2D) {
        super.draw(graphics2D);
    }
}
