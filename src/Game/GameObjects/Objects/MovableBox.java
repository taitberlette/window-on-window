package Game.GameObjects.Objects;

import Assets.AssetManager;
import Game.GameObjects.GameObject;
import Game.Worlds.CollisionType;
import Game.Worlds.World;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class MovableBox extends GameObject {
    private World world;
    private BufferedImage boxImage;
    private boolean grabbed = false;
    private final int IMAGE_SCALE = 4;
    private Dimension size = new Dimension(64, 64);
    private Point realPosition = new Point(0, 0);

    protected double velocityY;
    protected double gravityAcceleration = -9.81 * 64;

    public MovableBox(Point position, World world) {
        super(position);
        boxImage = AssetManager.getImage("res\\Objects\\Box.png");
        this.world = world;
    }

    public MovableBox(ArrayList<String> lines, World world) {
        super(lines);
        this.world = world;
    }

    public void update(long deltaTime) {
        if(grabbed) {
            return;
        }

        Point base = new Point(this.position);
        base.translate(0, ((int) size.getHeight() / 2) + 1);
        CollisionType collisionType = world.checkCollision(base);

        // update velocity and "real" position using delta time
        velocityY += gravityAcceleration * ((double) deltaTime / 1000000000);
        double verticalMovement = (velocityY * ((double) deltaTime / 1000000000));
        realPosition.setLocation(realPosition.getX(), realPosition.getY() - verticalMovement);


        // figure out how far we need to go for game position to reach real position
        int verticalDistance = 0;
        int maxVerticalDistance = (int) (realPosition.getY() - position.getY());

        // which way we are going (flipped to match world thinking and not screen thinking)
        int verticalMultiplier = maxVerticalDistance < 0 ? -1 : 1;

        // find the collision point we test with
        Point verticalCollider = new Point(this.position);
        verticalCollider.translate(0, (int) (size.getHeight() / 2) * verticalMultiplier);

        // traverse each step to check for collisions along the way
        for (verticalDistance = 0; verticalDistance < Math.abs(maxVerticalDistance); verticalDistance++) {
            verticalCollider.translate(0, verticalMultiplier);
            CollisionType testPoint = world.checkCollision(verticalCollider);
            if (testPoint != CollisionType.NONE) {
                break;
            }
        }

        // update the game position with how far we need to go
        position.translate(0, (int) verticalDistance * verticalMultiplier);

        // if we couldn't go all the way update the real position to match with
        if (Math.abs(maxVerticalDistance) != verticalDistance) {
            velocityY = 0;
            realPosition.setLocation(position);
        }
    }

    public void draw(Graphics2D graphics2D) {
        graphics2D.drawImage((Image) boxImage, (int) (position.getX() - (boxImage.getWidth() * IMAGE_SCALE) / 2), (int) (position.getY() - (boxImage.getHeight() * IMAGE_SCALE) / 2), boxImage.getWidth() * IMAGE_SCALE, (boxImage.getHeight() * IMAGE_SCALE), null);
    }

    public Rectangle getBounds() {
        return new Rectangle((int) (position.getX() - size.getWidth() / 2), (int) (position.getY() - size.getHeight() / 2), (int) size.getWidth(), (int) size.getHeight());
    }

    public void setGrabbed(boolean grabbed) {
        this.grabbed = grabbed;
    }

    public void setLocation(Point point) {
        this.position.setLocation(point);
        this.realPosition.setLocation(point);
    }

    public void setWorld(World world) {
        this.world = world;
    }
}
