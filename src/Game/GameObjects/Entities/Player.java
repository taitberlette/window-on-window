package Game.GameObjects.Entities;

import Game.GameObjects.Objects.Box;
import Game.GameObjects.Weapons.Melee.PocketKnife;
import Game.GameObjects.Weapons.Shooter.BoneShooter;
import Game.GameObjects.Weapons.Weapon;
import Game.Utilities.HorizontalDirection;
import Game.Utilities.Inventory;
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

    private Inventory inventory;

    private PocketKnife pocketKnife;
    private BoneShooter boneShooter;
    private Weapon carryingWeapon;

    private Box carryingBox;

    private Skill photosynthesisSkill;
    private Skill fastLegsSkill;
    private Skill tunnelVisionSkill;

    public Player(String name, Game game) {
        super(new Dimension(52, 128));

        this.maxSpeed = 300;

        this.name = name;
        this.game = game;

        this.inventory = new Inventory();

        this.pocketKnife = new PocketKnife();

        this.photosynthesisSkill = new Skill();
        this.fastLegsSkill = new Skill();
        this.tunnelVisionSkill = new Skill();

        this.maxHealth = 250;
        this.health = 250;

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

        if(pocketKnife != null) {
            pocketKnife.setLocation(position);
            pocketKnife.update(deltaTime);
        }

        if(playerStatsWindow != null){
            playerStatsWindow.update(deltaTime);
        }
    }

    public void draw(Graphics2D graphics2D) {
        if(world == null) {
            return;
        }

        BufferedImage image = world instanceof TerraWorld ? terraImage : etherImage;

        int offsetX = lastDirection == HorizontalDirection.RIGHT ? image.getWidth() : 0;

        graphics2D.drawImage((Image) image, ((int) position.getX() - image.getWidth() / 2) + offsetX,(int) position.getY() - image.getHeight() / 2, image.getWidth() * (lastDirection == HorizontalDirection.RIGHT ? -1 : 1), image.getHeight(), null);

        if(pocketKnife != null)  {
            pocketKnife.draw(graphics2D);
        }
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
        if(e.getKeyCode() == KeyEvent.VK_Q && pocketKnife != null && pocketKnife.checkCooldown()) {
            pocketKnife.attack(lastDirection);
        }
        if(e.getKeyCode() == KeyEvent.VK_LEFT) {
            if(lastDirection == HorizontalDirection.RIGHT && !pocketKnife.checkCooldown()) {
                pocketKnife.cancel();
            }
            lastDirection = HorizontalDirection.LEFT;
            velocityX = -maxSpeed;
        }
        if(e.getKeyCode() == KeyEvent.VK_RIGHT) {
            if(lastDirection == HorizontalDirection.LEFT && !pocketKnife.checkCooldown()) {
                pocketKnife.cancel();
            }
            lastDirection = HorizontalDirection.RIGHT;
            velocityX = maxSpeed;
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

    public void setWorld(World world) {
        this.world = world;

        if(this.pocketKnife != null) {
            this.pocketKnife.setWorld(world);
        }
    }

    public Inventory getInventory() {
        return inventory;
    }
}
