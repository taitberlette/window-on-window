package Game.GameObjects.Objects;

import Assets.AssetManager;
import Game.GameObjects.GameObject;
import Game.Worlds.World;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Tree extends GameObject {
    private World world;
    private BufferedImage treeImage;
    private BufferedImage appleImage;
    private int numApples = 5;
    private int maxApples = 5;
    private final int IMAGE_SCALE = 3;
    private Dimension size = new Dimension(128, 128);
    private Point position;

    public Tree(Point position){
        this.position = position;
        treeImage = AssetManager.getImage("res\\Objects\\Tree.png");
        appleImage = AssetManager.getImage("res\\Objects\\Apple.png");
    }
    public void update(long deltaTime) {

    }
    public void draw(Graphics2D graphics2D) {
        graphics2D.drawImage((Image) treeImage, (int) (position.getX() - (treeImage.getWidth() * IMAGE_SCALE) / 2), (int) (position.getY() - (treeImage.getHeight() * IMAGE_SCALE) / 2), treeImage.getWidth() * IMAGE_SCALE, (treeImage.getHeight() * IMAGE_SCALE), null);
        int offset;
        for(int i = 0; i < numApples; i++){
            offset = 0;
            if(i%2 == 0){
                offset = 40;
            }
            graphics2D.drawImage((Image)appleImage, (int)  (position.getX() - (treeImage.getWidth() * IMAGE_SCALE) / 2) + 33 + (i * 15), (int) (position.getY() - (treeImage.getHeight() * IMAGE_SCALE) / 2) + 30 +  offset, appleImage.getWidth() * IMAGE_SCALE/2, (appleImage.getHeight() * IMAGE_SCALE/2), null);

        }
    }
    public void setWorld(World world) {
        this.world = world;
    }
    public void dropApple(){
        numApples--;
    }

}
