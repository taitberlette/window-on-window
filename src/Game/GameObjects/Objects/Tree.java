package Game.GameObjects.Objects;

import Assets.AssetManager;
import Game.GameObjects.GameObject;
import Game.Worlds.World;

import java.awt.*;
import java.awt.image.BufferedImage;

import java.util.ArrayList;
import java.util.Random;

public class Tree extends GameObject {
    private Random random;
    private BufferedImage treeImage;
    private BufferedImage appleImage;
    private int numApples = 5;
    private final int MAX_APPLES = 5;
    private final int IMAGE_SCALE = 3;
    private Dimension size = new Dimension(144, 144);
    private double regrowTime = 0;

    public Tree(Point position){
        super(position);
        treeImage = AssetManager.getImage("res\\Objects\\Tree.png");
        appleImage = AssetManager.getImage("res\\Objects\\Apple.png");
        random = new Random();
    }

    public Tree(ArrayList<String> lines) {
        super(lines);

        for(String line : lines) {
            if(line.startsWith("APPLES=")) {
                numApples = Integer.parseInt(line.replace("APPLES=", "").trim());
            } else if(line.startsWith("TIMER=")) {
                regrowTime = Double.parseDouble(line.replace("TIMER=", "").trim());
            }
        }

        treeImage = AssetManager.getImage("res\\Objects\\Tree.png");
        appleImage = AssetManager.getImage("res\\Objects\\Apple.png");
        random = new Random();
    }

    public void update(long deltaTime) {
        regrowTime -= ((double) deltaTime / 1000000000);

        if(regrowTime < 0) {
            regrowTime = random.nextDouble(15, 30);
            if(numApples < MAX_APPLES) {
                numApples++;
            }
        }
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
    public void dropApple(){
        numApples--;
    }

    public Rectangle getBounds() {
        return new Rectangle((int) (position.getX() - size.getWidth() / 2), (int) (position.getY() - size.getHeight() / 2), (int) size.getWidth(), (int) size.getHeight());
    }

    public String encode() {
        String result = super.encode();

        result += "APPLES=" + numApples + "\n";
        result += "TIMER=" + regrowTime + "\n";

        return result;
    }
}
