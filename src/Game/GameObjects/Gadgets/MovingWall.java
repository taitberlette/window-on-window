package Game.GameObjects.Gadgets;

import Assets.AssetManager;
import Game.Worlds.World;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;

public class MovingWall extends Mechanism {
    private BufferedImage wallImage;
    private final int IMAGE_SCALE = 8;

    private Point realPosition;
    private Point startPoint;
    private Point endPoint;
    private int speed;

    public MovingWall(Point position, Point end, int switcherId, World world) {
        super(position, new Dimension(64, 192), switcherId, world);

        this.realPosition = new Point(position);
        this.startPoint = new Point(position);
        this.endPoint = new Point(end);

        this.speed = 150;

        wallImage = AssetManager.getImage("res\\Objects\\Wall.png");
    }

    public MovingWall(ArrayList<String> lines, World world) {
        super(lines, new Dimension(64, 192), world);

        double startX = 0;
        double startY = 0;
        double endX = 0;
        double endY = 0;

        for(String line : lines) {
            if(line.startsWith("STARTX=")) {
                startX = Double.parseDouble(line.replace("STARTX=", ""));
            } else if(line.startsWith("STARTY=")) {
                startY = Double.parseDouble(line.replace("STARTY=", ""));
            } else if(line.startsWith("ENDX=")) {
                endX = Double.parseDouble(line.replace("ENDX=", ""));
            } else if(line.startsWith("ENDY=")) {
                endY = Double.parseDouble(line.replace("ENDY=", ""));
            } else if(line.startsWith("ID=")) {
                switcherId = Integer.parseInt(line.replace("ID=", ""));
            }
        }

        startPoint = new Point(0, 0);
        startPoint.setLocation(startX, startY);
        endPoint = new Point(0, 0);
        endPoint.setLocation(endX, endY);

        this.realPosition = new Point(position);
        this.speed = 150;

        wallImage = AssetManager.getImage("res\\Objects\\Wall.png");
    }

    public void update(long deltaTime) {
        double verticalMovement = (speed * ((double) deltaTime / 1000000000));

        Point target = world.getSwitcher(switcherId) ? endPoint : startPoint;

        boolean moveUpWhenActivated = startPoint.getY() > endPoint.getY();
        if(!world.getSwitcher(switcherId)) {
            moveUpWhenActivated = !moveUpWhenActivated;
        }

        if(position.getY() > target.getY() && moveUpWhenActivated) {
            // move up
            realPosition.setLocation(realPosition.getX(), realPosition.getY() - verticalMovement);
            position.setLocation(realPosition);
        } else if(position.getY() < target.getY() && !moveUpWhenActivated) {
            // move down
            realPosition.setLocation(realPosition.getX(), realPosition.getY() + verticalMovement);
            position.setLocation(realPosition);
        }
    }

    public void draw(Graphics2D graphics2D) {
        graphics2D.drawImage((Image) wallImage, (int) position.getX() - ((wallImage.getWidth() * IMAGE_SCALE) / 2), (int) position.getY() - ((wallImage.getHeight() * IMAGE_SCALE) / 2), wallImage.getWidth() * IMAGE_SCALE, wallImage.getHeight() * IMAGE_SCALE, null);
    }

    public String encode() {
        String result = super.encode();

        result += "STARTX=" + startPoint.getX() + "\n";
        result += "STARTY=" + startPoint.getY() + "\n";
        result += "ENDX=" + endPoint.getX() + "\n";
        result += "ENDY=" + endPoint.getY() + "\n";
        result += "ID=" + switcherId + "\n";

        return result;
    }
}
