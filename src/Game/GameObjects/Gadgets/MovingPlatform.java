package Game.GameObjects.Gadgets;

import Game.Worlds.CollisionType;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

public class MovingPlatform extends Mechanism {
    private BufferedImage platformOnImage;
    private BufferedImage platformOffImage;
    private final int IMAGE_SCALE = 8;

    private Point realPosition;
    private Point startPoint;
    private Point endPoint;
    private int speed;

    public MovingPlatform(Point position, Point end, Switch switcher) {
        super(position, new Dimension(128, 80), switcher);

        this.realPosition = new Point(position);
        this.startPoint = new Point(position);
        this.endPoint = new Point(end);

        this.speed = 150;

        try{
            platformOnImage = ImageIO.read(new File("res\\Objects\\Moving Platform(on).png"));
            platformOffImage = ImageIO.read(new File("res\\Objects\\Moving Platform(off).png"));
        } catch (Exception e) {
            System.out.println("Failed to load images for the platform!");
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
        BufferedImage platformImage = switcher.isActivated() ? platformOnImage : platformOffImage;

        graphics2D.drawImage((Image) platformImage, (int) position.getX() - ((platformImage.getWidth() * IMAGE_SCALE) / 2), (int) position.getY() - ((platformImage.getHeight() * IMAGE_SCALE) / 2), platformImage.getWidth() * IMAGE_SCALE, platformImage.getHeight() * IMAGE_SCALE, null);
    }
}
