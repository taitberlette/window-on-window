package Game.GameObjects.Entities;

import Game.GameObjects.Objects.Box;
import Game.GameObjects.Weapons.Melee.PocketKnife;
import Game.GameObjects.Weapons.Shooter.BoneShooter;
import Game.GameObjects.Weapons.Shooter.Shooter;
import Game.GameObjects.Weapons.Weapon;
import Game.Utilities.Ammunition;
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

    private BufferedImage aimTerraImage;
    private BufferedImage aimEtherImage;

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

    private boolean leftKey = false;
    private boolean rightKey = false;

    private boolean aimingLeftKey = false;
    private boolean aimingRightKey = false;
    private double angle = Math.PI / 2;
    private double angleSpeed = Math.PI;

    public Player(String name, Game game) {
        super(new Dimension(52, 128));

        this.maxSpeed = 300;

        this.name = name;
        this.game = game;

        this.inventory = new Inventory();

        this.pocketKnife = new PocketKnife();
        this.boneShooter = new BoneShooter();

        this.photosynthesisSkill = new Skill(1);
        this.fastLegsSkill = new Skill(10);
        this.tunnelVisionSkill = new Skill(20);

        this.maxHealth = 250;
        this.health = 250;

        this.playerStatsWindow = new PlayerStatsWindow(name, this, game, photosynthesisSkill, fastLegsSkill, tunnelVisionSkill);

        position.setLocation(500, 500);

        try{
            terraImage = ImageIO.read(new File("res\\Player\\TerraPlayerIdol.png"));
            etherImage = ImageIO.read(new File("res\\Player\\EtherPlayerIdol.png"));
            aimTerraImage = ImageIO.read(new File("res\\Player\\Terra Arm.png"));
            aimEtherImage = ImageIO.read(new File("res\\Player\\Ether Arm.png"));
        } catch (Exception e) {
            System.out.println("Failed to load images for the player!");
        }

        playerStatsWindow.setVisible(true);
    }

    public void update(long deltaTime) {
        if(leftKey && !rightKey) {
            velocityX = -maxSpeed;
            lastDirection = HorizontalDirection.LEFT;
        } else if(!leftKey && rightKey) {
            velocityX = maxSpeed;
            lastDirection = HorizontalDirection.RIGHT;
        } else {
            velocityX = 0;
        }

        if(aimingLeftKey && !aimingRightKey) {
            angle += angleSpeed * ((double) deltaTime / 1000000000);
        } else if(!aimingLeftKey && aimingRightKey) {
            angle -= angleSpeed * ((double) deltaTime / 1000000000);
        }

        super.update(deltaTime);

        if(pocketKnife != null) {
            pocketKnife.setLocation(position);
            pocketKnife.update(deltaTime);
        }

        boneShooter.update(deltaTime);

        if(playerStatsWindow != null){
            playerStatsWindow.update(deltaTime);
        }

        if(fastLegsSkill.getActiveStatus()){
            maxSpeed = 500;
        } else {
            maxSpeed = 300;
        }

        if (photosynthesisSkill.wasUnlocked() && world instanceof TerraWorld){
            health++;
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


        BufferedImage aimImage = world instanceof TerraWorld ? aimTerraImage : aimEtherImage;

        if(inventory.hasItem(Ammunition.BONE)) {
            int verticalOffset = 12;
            int horizontalOffset = lastDirection == HorizontalDirection.RIGHT ? - 12 : 12;
            graphics2D.rotate(-angle, position.getX() + horizontalOffset, position.getY() - verticalOffset);
            graphics2D.drawImage(aimImage, (int) position.getX() + horizontalOffset, (int) (position.getY() - aimImage.getHeight() / 2) - verticalOffset, null);
            graphics2D.rotate(angle, position.getX() + horizontalOffset, position.getY() - verticalOffset);
        } else {
            int horizontalOffset = lastDirection == HorizontalDirection.RIGHT ? - 20 : 18;
            int verticalOffset = 19;
            graphics2D.rotate(Math.PI / 2 , position.getX() + horizontalOffset, position.getY() - verticalOffset);
            graphics2D.drawImage(aimImage, (int) position.getX() + horizontalOffset, (int) (position.getY() - aimImage.getHeight() / 2) - verticalOffset, null);
            graphics2D.rotate(-Math.PI / 2 , position.getX()+ horizontalOffset, position.getY() - verticalOffset);
        }
            int verticalOffsetOffhand = 20;
            double knifeAngle = lastDirection == HorizontalDirection.RIGHT ? (15 * Math.PI) / 8 : (9 * Math.PI) / 8;
            graphics2D.rotate(-knifeAngle, position.getX(), position.getY() - verticalOffsetOffhand);
            graphics2D.drawImage(aimImage, (int) position.getX() + 10, (int) (position.getY() - aimImage.getHeight() / 2) - verticalOffsetOffhand, null);
            graphics2D.rotate(knifeAngle, position.getX(), position.getY() - verticalOffsetOffhand);



        int verticalOffset = 12;
        int horizontalOffset = lastDirection == HorizontalDirection.RIGHT ?  -12 : 12;
        int x = (int) (position.getX() + horizontalOffset + (Math.cos(angle) * (aimTerraImage.getWidth() )));
        int y = (int) (position.getY() - verticalOffset  - (aimTerraImage.getHeight() / 2) - (Math.sin(angle) * (aimTerraImage.getHeight() )));

        graphics2D.setColor(Color.YELLOW);
        graphics2D.fillRect(x - 1, y - 1, 3, 3);

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
        } else if(e.getKeyCode() == KeyEvent.VK_Q && pocketKnife != null && pocketKnife.checkCooldown()) {
            pocketKnife.attack(lastDirection);
        } else if(e.getKeyCode() == KeyEvent.VK_W) {

            Shooter shooter = boneShooter;

            if(carryingWeapon != null && carryingWeapon instanceof Shooter carryingShooter) {
                shooter = carryingShooter;
            }

            int verticalOffset = 12;
            int horizontalOffset = lastDirection == HorizontalDirection.RIGHT ?  -12 : 12;

            int x = (int) (position.getX() + horizontalOffset + (Math.cos(angle) * (aimTerraImage.getWidth() )));
            int y = (int) (position.getY() - verticalOffset  - (aimTerraImage.getHeight() / 2) - (Math.sin(angle) * (aimTerraImage.getHeight() )));

            Point hand = new Point(x, y);

            if(shooter.checkCooldown() && shooter.checkAmmunition(inventory)) {
                shooter.attack(world, angle, hand, inventory);
            }
        } else if(e.getKeyCode() == KeyEvent.VK_LEFT) {
            if(lastDirection == HorizontalDirection.RIGHT && !pocketKnife.checkCooldown()) {
                pocketKnife.cancel();
            }
            leftKey = true;
        } else if(e.getKeyCode() == KeyEvent.VK_RIGHT) {
            if(lastDirection == HorizontalDirection.LEFT && !pocketKnife.checkCooldown()) {
                pocketKnife.cancel();
            }
            rightKey = true;
        } else if(e.getKeyCode() == KeyEvent.VK_A) {
            aimingLeftKey = true;
        } else if(e.getKeyCode() == KeyEvent.VK_D) {
            aimingRightKey = true;
        }


        if(e.getKeyCode() == KeyEvent.VK_S && fastLegsSkill.getCooldownLength() > 3){
            fastLegsSkill.activate();
        }
    }

    public void keyReleased(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_LEFT) {
            leftKey = false;
        } else if(e.getKeyCode() == KeyEvent.VK_RIGHT) {
            rightKey = false;
        } else if(e.getKeyCode() == KeyEvent.VK_A) {
            aimingLeftKey = false;
        } else if(e.getKeyCode() == KeyEvent.VK_D) {
            aimingRightKey = false;
        }
        if(e.getKeyCode() == KeyEvent.VK_S ){
            fastLegsSkill.deactivate();
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
