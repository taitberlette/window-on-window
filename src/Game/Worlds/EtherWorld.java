package Game.Worlds;

import Game.Game;
import Game.GameObjects.Entities.Player;
import Game.GameObjects.GameObject;
import Game.Level;
import Windows.WorldWindow;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;

public class EtherWorld extends World {
    private BufferedImage testing;

    public EtherWorld(Game game, Level level) {
        super(game, level);

        try{
            this.testing = ImageIO.read(new File("res\\Level_Tutorial\\Ether.png"));
        } catch (Exception e) {
            System.out.println("Failed to load 'Tutorial Ether.png'");
        }

        WorldWindow worldWindow = new WorldWindow(this);
        worldWindow.setFocusable(true);
        worldWindow.requestFocus();
        worldWindow.setKeyListener(game);
        worldWindows.add(worldWindow);

        worldWindow.setLocation(new Point(1000, 600));
    }

    public void draw(Graphics2D graphics2D) {
        graphics2D.drawImage(testing, 0, 0, 1920, 1080, null);
    }
}
