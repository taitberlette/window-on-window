package Game.GameObjects.Objects;

import Assets.AssetManager;
import Game.Game;
import Game.GameObjects.GameObject;
import Game.GameObjects.Entities.Player;
import Game.Worlds.World;
import Windows.NumberCodeWindow;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;

public class Door extends GameObject {
    private NumberCodeWindow numberCodeWindow;
    private int[] combination;
    private Player player;
    private World world;
    private Game game;
    private BufferedImage doorImage;
    private boolean inRange = false;
    private final double DOOR_DISTANCE = 128;
    public Door(Point point, Player player, World world, Game game, String path, int[] combination) {
        setLocation(point);

        this.player = player;
        this.world = world;
        this.combination = combination;

        numberCodeWindow = new NumberCodeWindow();
        numberCodeWindow.setCombination(this.combination);
        numberCodeWindow.setKeyListener(game);

        doorImage = AssetManager.getImage("res\\" + path + "\\Door.png");
    }

    public void update(long deltaTime) {
        super.update(deltaTime);

        if(player.getWorld() != world) {
            return;
        }

        Point playerPosition = player.getLocation();

        double distance = playerPosition.distance(position);
        inRange = distance < DOOR_DISTANCE;

        if(inRange != numberCodeWindow.isVisible()) {
            numberCodeWindow.setVisible(inRange);
        }

        Point windowLocation = new Point(playerPosition);
        windowLocation.translate(-512, -64);

        numberCodeWindow.setLocation(windowLocation);

        numberCodeWindow.update(deltaTime);
    }

    public void draw(Graphics2D graphics2D) {
        graphics2D.drawImage(doorImage, (int) position.getX(), (int) position.getY(), null);
    }

    public void keyPressed(KeyEvent e) {
        if(!inRange) return;

        if(e.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
            numberCodeWindow.clear();
            return;
        }

        char character = e.getKeyChar();
        if(character >= '0' && character <= '9') {
            numberCodeWindow.typeNumber(character - '0');
        }
    }
}
