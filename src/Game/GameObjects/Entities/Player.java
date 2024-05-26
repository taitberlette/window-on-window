package Game.GameObjects.Entities;

import Game.Utilities.Skill;
import Game.Worlds.TerraWorld;
import Game.Worlds.World;
import Game.Game;
import Windows.PlayerStatsWindow;

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

    private BufferedImage terraImage;
    private BufferedImage etherImage;

    private PlayerStatsWindow playerStatsWindow;

    private String name;
    private Game game;

    private Skill photosynthesisSkill;
    private Skill fastLegsSkill;
    private Skill tunnelVisionSkill;

    public Player(String name, Game game) {
        super(new Dimension(52, 128));

        this.name = name;
        this.game = game;

        this.photosynthesisSkill = new Skill();
        this.fastLegsSkill = new Skill();
        this.tunnelVisionSkill = new Skill();

        this.maxHealth = 250;

        this.playerStatsWindow = new PlayerStatsWindow(name, this, game, photosynthesisSkill, fastLegsSkill, tunnelVisionSkill);

        position.setLocation(500, 500);

        try{
            terraImage = ImageIO.read(new File("res\\Player\\TerraPlayerIdol.png"));
            etherImage = ImageIO.read(new File("res\\Player\\EtherPlayerIdol.png"));
        } catch (Exception e) {
            System.out.println("Failed to load images for the player!");
        }

        playerStatsWindow.setVisible(true);
    }

    public void update(long deltaTime) {
        super.update(deltaTime);

        playerStatsWindow.update(deltaTime);

        this.health = (this.health + 1) % this.maxHealth;
    }

    public void draw(Graphics2D graphics2D) {
        if(world == null) {
            return;
        }

        BufferedImage image = world instanceof TerraWorld ? terraImage : etherImage;

        graphics2D.drawImage((Image) image, (int) position.getX() - 64,(int) position.getY() - 64, 128, 128, null);
    }

    public void kill() {
        if(playerStatsWindow != null) {
            playerStatsWindow.setVisible(false);
            playerStatsWindow = null;
        }
    }

    public void keyTyped(KeyEvent e) {

    }

    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_UP && onGround) {
            velocityY = 400;
        }
        if(e.getKeyCode() == KeyEvent.VK_LEFT) {
            velocityX = -200;
        }
        if(e.getKeyCode() == KeyEvent.VK_RIGHT) {
            velocityX = 200;
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

    public String getName() {
        return name;
    }
}
