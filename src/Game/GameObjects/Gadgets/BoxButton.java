package Game.GameObjects.Gadgets;

import Assets.AssetManager;
import Game.GameObjects.GameObject;
import Game.GameObjects.Objects.Box;
import Game.Worlds.World;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class BoxButton extends Switch {
    private BufferedImage buttonImage;
    private final int IMAGE_SCALE = 4;
    private World world;

    public BoxButton(Point position) {
        super(position, new Dimension(40, 64));

        buttonImage = AssetManager.getImage("res\\Objects\\Button.png");
    }

    public void draw(Graphics2D graphics2D) {
        graphics2D.drawImage((Image) buttonImage, (int) position.getX() - ((buttonImage.getWidth() * IMAGE_SCALE) / 2), (int) position.getY() - ((buttonImage.getHeight() * IMAGE_SCALE) / 2), buttonImage.getWidth() * IMAGE_SCALE, buttonImage.getHeight() * IMAGE_SCALE, null);
    }

    public void update(long deltaTime) {
        activated = false;

        ArrayList<GameObject> gameObjects = world.findGameObjects(getBounds());

        for(GameObject gameObject : gameObjects) {
            if(gameObject instanceof Box) {
                activated = true;
                break;
            }
        }
    }

    public void setWorld(World world) {
        this.world = world;
    }
}
