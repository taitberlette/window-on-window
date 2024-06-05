package Game.GameObjects.Gadgets;

import Assets.AssetManager;

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

    public MovingWall(Point position, Point end, Switch switcher) {
        super(position, new Dimension(64, 192), switcher);

        this.realPosition = new Point(position);
        this.startPoint = new Point(position);
        this.endPoint = new Point(end);

        this.speed = 150;

        wallImage = AssetManager.getImage("res\\Objects\\Wall.png");
    }

    public MovingWall(ArrayList<String> lines) {
        super(lines, new Dimension(64, 192), switcher);

        int startX = 0;
        int startY = 0;
        int endX = 0;
        int endY = 0;

        for(String line : lines) {
            if(line.startsWith("STARTX=")) {
                startX = Integer.parseInt(line.replace("STARTX=", ""));
            } else if(line.startsWith("STARTY=")) {
                startY = Integer.parseInt(line.replace("STARTY=", ""));
            } else if(line.startsWith("ENDX=")) {
                endX = Integer.parseInt(line.replace("ENDX=", ""));
            } else if(line.startsWith("ENDY=")) {
                endY = Integer.parseInt(line.replace("ENDY=", ""));
            }
        }

        startPoint = new Point(startX, startY);
        endPoint = new Point(endX, endY);

        this.realPosition.setLocation(position);
        this.speed = 150;

        wallImage = AssetManager.getImage("res\\Objects\\Wall.png");
    }

    public void update(long deltaTime) {
        double verticalMovement = (speed * ((double) deltaTime / 1000000000));

        Point target = switcher.isActivated() ? endPoint : startPoint;

        boolean moveUpWhenActivated = startPoint.getY() > endPoint.getY();
        if(!switcher.isActivated()) {
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

        return result;
    }
}
