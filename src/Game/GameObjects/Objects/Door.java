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
import java.util.ArrayList;

public class Door extends GameObject {
    private NumberCodeWindow numberCodeWindow;
    private int[] combination;
    private Player player;
    private World world;
    private Game game;
    private BufferedImage doorImage;
    private BufferedImage openDoorImage;
    private boolean inRange = false;
    private final double DOOR_DISTANCE = 128;
    private final double WALK_IN_DISTANCE = 32;

    public Door(Point point, Player player, World world, Game game, String path, int[] combination) {
        super(point);

        this.player = player;
        this.world = world;
        this.game = game;
        this.combination = combination;

        numberCodeWindow = new NumberCodeWindow();
        numberCodeWindow.setCombination(this.combination);
        numberCodeWindow.setKeyListener(game);

        doorImage = AssetManager.getImage("res\\" + path + "\\Door.png");
        openDoorImage = AssetManager.getImage("res\\Objects\\OpenDoor.png");
    }

    public Door(ArrayList<String> lines, Player player, World world, Game game, String path) {
        super(lines);

        this.player = player;
        this.world = world;
        this.game = game;

        for(String line : lines) {
            if(line.startsWith("NUMBERS=")) {
                String[] numbers = (line.replace("NUMBER=", "")).split(",");
                combination = new int[numbers.length];

                for(int i = 0; i < numbers.length; i++) {
                    combination[i] = Integer.parseInt(numbers[i]);
                }
            }
        }

        numberCodeWindow = new NumberCodeWindow();
        numberCodeWindow.setCombination(this.combination);
        numberCodeWindow.setKeyListener(game);

        doorImage = AssetManager.getImage("res\\" + path + "\\Door.png");
        openDoorImage = AssetManager.getImage("res\\Objects\\OpenDoor.png");
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

        double distanceHorizontal = Math.abs(playerPosition.getX() - (position.getX() + 64));

        if(numberCodeWindow.isCorrect() && distanceHorizontal < WALK_IN_DISTANCE) {
            System.out.println("COMPLETE LEVEL");
            game.levelCompleted();
        }

        Point windowLocation = new Point(playerPosition);
        windowLocation.translate(-512, -64);

        numberCodeWindow.setLocation(windowLocation);

        numberCodeWindow.update(deltaTime);
    }

    public void draw(Graphics2D graphics2D) {
        BufferedImage door = (numberCodeWindow.isCorrect()) ? openDoorImage : doorImage;

        graphics2D.drawImage(door, (int) position.getX(), (int) position.getY(), null);
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

    public void kill() {
        numberCodeWindow.setVisible(false);
    }

    public String encode() {
        String result = super.encode();

        result += "NUMBERS=";
        for(int i = 0; i < combination.length; i++) {
            result += combination[i];

            if(i < combination.length - 1) {
                result += ",";
            } else {
                result += "\n";
            }
        }

        return result;
    }
}
