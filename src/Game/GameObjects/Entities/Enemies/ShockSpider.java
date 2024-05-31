package Game.GameObjects.Entities.Enemies;

import Game.Levels.Level;
import Game.Levels.LevelZero;
import Game.Utilities.HorizontalDirection;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

public class ShockSpider extends Enemy {
    private BufferedImage shockSpiderImage;
    private BufferedImage shockSpiderAttackImage;

    private long slowerSpeedStart = 0;
    private long attackStart = 0;

    private final double IMAGE_SCALE = 4;
    private final double ATTACK_IMAGE_SCALE = 2;

    private static final long ATTACK_IMAGE_LENGTH = 250;
    private static final long SLOW_LENGTH = 1500;
    private static final int REGULAR_SPEED = 200;
    private static final int SLOW_SPEED = 150;
    public ShockSpider() {

        super(new Dimension(128, 64), 5, 30, REGULAR_SPEED, 256, 150);

        try{
            shockSpiderImage = ImageIO.read(new File("res\\Enemies\\Spider.png"));
            shockSpiderAttackImage = ImageIO.read(new File("res\\Enemies\\SpiderAttack.png"));
        } catch (Exception e) {
            System.out.println("Failed to load images for the spider!");
        }
    }

    public void update(long deltaTime) {
        boolean slower = System.currentTimeMillis() - slowerSpeedStart < SLOW_LENGTH;
        maxSpeed = slower ? SLOW_SPEED : REGULAR_SPEED;

        super.update(deltaTime);
    }

    public void draw(Graphics2D graphics2D) {
        if(world == null) {
            return;
        }

        int offsetX = lastDirection == HorizontalDirection.RIGHT ? (int) (shockSpiderImage.getWidth() * IMAGE_SCALE) : 0;
        graphics2D.drawImage((Image) shockSpiderImage, ((int) (position.getX() - (shockSpiderImage.getWidth() * (IMAGE_SCALE / 2))) + offsetX), (int) ((int) position.getY() - size.getHeight()), (int) (shockSpiderImage.getWidth() * IMAGE_SCALE * (lastDirection == HorizontalDirection.RIGHT ? -1 : 1)), (int) (shockSpiderImage.getHeight() * IMAGE_SCALE), null);

        if(System.currentTimeMillis() - attackStart < ATTACK_IMAGE_LENGTH) {
            int horizontalOffSet = lastDirection == HorizontalDirection.RIGHT ? 85 : -30;
            int imageOffsetX = lastDirection == HorizontalDirection.LEFT ? (int) ((shockSpiderAttackImage.getWidth() * ATTACK_IMAGE_SCALE) - (shockSpiderImage.getWidth() * (IMAGE_SCALE / 2))) : (int) (shockSpiderImage.getWidth() * (IMAGE_SCALE / 2));
            graphics2D.drawImage((Image) shockSpiderAttackImage, (int) (position.getX() - (shockSpiderAttackImage.getWidth() * (ATTACK_IMAGE_SCALE / 2))) + horizontalOffSet, (int) (position.getY() - (shockSpiderAttackImage.getHeight()) * (ATTACK_IMAGE_SCALE / 2)) + 10, (int) (shockSpiderAttackImage.getWidth() * ATTACK_IMAGE_SCALE * (lastDirection == HorizontalDirection.LEFT ? -1 : 1)), (int) (shockSpiderAttackImage.getHeight() * ATTACK_IMAGE_SCALE), null);
        }
    }

    public void kill(){

    }

   /* public void unlockSkill(){
        if(Level instanceof LevelZero){

        }
    }*/

    public void hurt(int damage) {
        super.hurt(damage);
        slowerSpeedStart = System.currentTimeMillis();
    }

    public void attackAniamtion() {
        attackStart = System.currentTimeMillis();
    }
}
