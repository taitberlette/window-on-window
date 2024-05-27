package Game.GameObjects.Weapons.Melee;

import Game.Utilities.HorizontalDirection;
import Game.Worlds.World;

import javax.imageio.ImageIO;
import javax.swing.plaf.basic.BasicSplitPaneUI;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.File;

public class PocketKnife extends Melee {
    private BufferedImage pocketKnifeImage;

    private long startAttack = 0;
    private HorizontalDirection attackDirection;
    private Point offset = new Point(0, 0);
    private boolean attacking = false;

    private final long ATTACK_TIME = 1000;

    public PocketKnife() {
        super(20, 2, 0,52);

        try{
            pocketKnifeImage = ImageIO.read(new File("res\\Weapons and Attacks\\PocketKnife.png"));
        } catch (Exception e) {
            System.out.println("Failed to load images for the pocket knife!");
        }
    }

    public void update(long deltaTime) {
        super.update(deltaTime);

        long currentTime = System.currentTimeMillis();
        long progress = currentTime - startAttack;
        attacking = progress < ATTACK_TIME;

        if(attacking) {
            int multiplier = attackDirection == HorizontalDirection.LEFT ? -1 : 1;

            if(progress < ATTACK_TIME / 2) {
                offset.setLocation(multiplier * (reach * ((double) progress / ATTACK_TIME / 2)), 0);
            } else {
                offset.setLocation(multiplier * (reach - (reach * ((double) ((progress + ATTACK_TIME)) / ATTACK_TIME / 2))), 0);
            }
        }
    }

    public void draw(Graphics2D graphics2D) {
        if(!attacking) return;

        int xOffset = attackDirection == HorizontalDirection.RIGHT ? 32 : 0;

        if(attackDirection == HorizontalDirection.RIGHT) {
            xOffset += 52 / 2;
        } else {
            xOffset -= (32 + (52 / 2));
        }

        graphics2D.drawImage(pocketKnifeImage, (int) (position.getX() + offset.getX() + xOffset), (int) position.getY() - 16, 32 * (attackDirection == HorizontalDirection.RIGHT ? -1 : 1), 32, null);
    }

    public void attack(HorizontalDirection attackDirection) {
        if(attackDirection == null || attackDirection == HorizontalDirection.NONE) return;
        this.attackDirection = attackDirection;
        startAttack = System.currentTimeMillis();
    }

    public boolean checkCooldown() {
        return !attacking;
    }

    public void cancel() {
        startAttack = 0;
    }
}
