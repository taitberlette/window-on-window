package Game.GameObjects.Gadgets;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

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

        try{
            wallImage = ImageIO.read(new File("res\\Objects\\Wall.png"));
        } catch (Exception e) {
            System.out.println("Failed to load images for the wall!");
        }
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
}
