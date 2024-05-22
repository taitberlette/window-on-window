package Game.GameObjects.Entities;

import Game.Worlds.TerraWorld;
import Game.Worlds.World;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;

public class Player extends Entity implements KeyListener {
//    private BufferedImage[] playerWalkingTerraImages = new BufferedImage[2];
//    private BufferedImage[] playerWalkingEtherImages = new BufferedImage[2];
//    private BufferedImage[] playerJumpingTerraImages = new BufferedImage[2];
//    private BufferedImage[] playerJumpingEtherImages = new BufferedImage[2];
//    private BufferedImage[] playerMeleeTerraImages = new BufferedImage[2];
//    private BufferedImage[] playerMeleeEtherImages = new BufferedImage[2];

    BufferedImage terraImage;
    BufferedImage etherImage;

    public Player() {
        super(new Dimension(52, 128));

        position.setLocation(500, 500);


        try{
            terraImage = ImageIO.read(new File("res\\Player\\TerraPlayerIdol.png"));
//            etherImage = ImageIO.read(new File("res\\Player\\EtherPlayerIdol.png"));
        } catch (Exception e) {
            System.out.println("Failed to load images for the player!");
        }
    }

    public void update(long deltaTime) {
        super.update(deltaTime);
    }

    public void draw(Graphics2D graphics2D) {
        if(world == null) {
            return;
        }

        BufferedImage image = world instanceof TerraWorld ? terraImage : etherImage;

        graphics2D.drawImage((Image) image, (int) position.getX() - 64,(int) position.getY() - 64, 128, 128, null);
    }

    public void setWorld(World world) {
        this.world = world;
    }

    public void keyTyped(KeyEvent e) {

    }

    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_UP && onGround) {
            velocityY = 7;
        }
        if(e.getKeyCode() == KeyEvent.VK_LEFT) {
            velocityX = -5;
        }
        if(e.getKeyCode() == KeyEvent.VK_RIGHT) {
            velocityX = 5;
        }
    }

    public void keyReleased(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_LEFT) {
            velocityX = 0;
        }
        if(e.getKeyCode() == KeyEvent.VK_RIGHT) {
            velocityX = 0;
        }
    }
}
