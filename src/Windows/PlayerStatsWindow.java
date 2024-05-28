package Windows;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import Game.Game;
import Game.GameObjects.Entities.Player;
import Game.Utilities.Ammunition;
import Game.Utilities.Inventory;
import Game.Utilities.Skill;
import WindowOnWindow.WindowOnWindow;

import javax.imageio.ImageIO;

public class PlayerStatsWindow extends Panel {
    private Dimension defaultDimension = new Dimension(383, 183);
    private Player player;

    private Skill photosynthesisSkill;
    private Skill fastLegsSkill;
    private Skill tunnelVisionSkill;

    private Color healthBarBackground = new Color(0x6B1F1F);
    private Color healthBar = new Color(0xFF3838);
    private BufferedImage heartImage;
    private BufferedImage photosynthesisHeartImage;

    private Color fastLegsBarBackground = new Color(0x222E9A);
    private Color fastLegsBar = new Color(0x5B70DF);
    private BufferedImage fastLegsImage;

    private Color tunnelVisionBarBackground = new Color(0x4D7C11);
    private Color tunnelVisionBar = new Color(0x16FF02);
    private BufferedImage tunnelVisionImage;

    private BufferedImage boneImage;
    private BufferedImage fireChargeImage;
    private BufferedImage lightningImage;

    public PlayerStatsWindow(String title, Player player, Game game, Skill photosynthesisSkill, Skill fastLegsSkill, Skill tunnelVisionSkill) {
        super(title);

        this.player = player;
        this.photosynthesisSkill = photosynthesisSkill;
        this.fastLegsSkill = fastLegsSkill;
        this.tunnelVisionSkill = tunnelVisionSkill;

        try {
            heartImage = ImageIO.read(new File("res\\Ammunition and Skills\\RedHeart.png"));
            photosynthesisHeartImage = ImageIO.read(new File("res\\Ammunition and Skills\\GoldenHeart.png"));
            fastLegsImage = ImageIO.read(new File("res\\Ammunition and Skills\\Wind.png"));
            tunnelVisionImage = ImageIO.read(new File("res\\Ammunition and Skills\\Tunnel Vision Icon.png"));
            boneImage = ImageIO.read(new File("res\\Ammunition and Skills\\Bone1.png"));
            fireChargeImage = ImageIO.read(new File("res\\Ammunition and Skills\\FlameIcon.png"));
            lightningImage = ImageIO.read(new File("res\\Ammunition and Skills\\Lightning.png"));
        } catch (IOException e) {
            System.out.println("Failed to load assests for the player stats window!");
        }

        this.view = new View(defaultDimension, this);

        this.view.setListener(game);
    }

    protected void draw(Graphics2D graphics2D, Dimension size) {
        double scale = WindowOnWindow.getScale();

        // draw health bar
        graphics2D.setColor(healthBarBackground);
        graphics2D.fillRect((int)(18 * scale), (int)(48 * scale), (int)(352 * scale), (int)(27 * scale));

        double percentHealth = (double) player.getHealth() / player.getMaxHealth();

        graphics2D.setColor(healthBar);
        graphics2D.fillRect((int)(18 * scale), (int)(48 * scale), (int) (352 * percentHealth * scale), (int)(27 * scale));

        BufferedImage heartType = photosynthesisSkill.wasUnlocked() ? photosynthesisHeartImage : heartImage;
        graphics2D.drawImage(heartType, (int)(347 * scale), (int)(53 * scale), (int)(16 * scale), (int)(16 * scale), null);



        // draw fast legs bar
        graphics2D.setColor(fastLegsBarBackground);
        graphics2D.fillRect((int)(18 * scale), (int)(84 * scale), (int)(172 * scale), (int)(27 * scale));

        double percentFastLegsCooldown = (double) fastLegsSkill.getCooldown() / fastLegsSkill.getCooldownLength();

        graphics2D.setColor(fastLegsBar);
        graphics2D.fillRect((int)(18 * scale), (int)(84 * scale), (int) (172 * percentFastLegsCooldown * scale), (int)(27 * scale));

        graphics2D.drawImage(fastLegsImage, (int)(170 * scale), (int)(90 * scale), (int)(16 * scale), (int)(16 * scale), null);


        // draw tunnel vision bar
        graphics2D.setColor(tunnelVisionBarBackground);
        graphics2D.fillRect((int)(198 * scale), (int)(84 * scale), (int)(172 * scale), (int)(27 * scale));

        double percentTunnelVisionCooldown = (double) tunnelVisionSkill.getCooldown() / tunnelVisionSkill.getCooldownLength();

        graphics2D.setColor(tunnelVisionBar);
        graphics2D.fillRect((int)(198 * scale), (int)(84 * scale), (int) (172 * percentTunnelVisionCooldown * scale), (int)(27 * scale));

        graphics2D.drawImage(tunnelVisionImage, (int)(350 * scale), (int)(90 * scale), (int)(16 * scale), (int)(16 * scale), null);


        Inventory inventory = player.getInventory();

        Font font = WindowOnWindow.getStatFont();
        graphics2D.setColor(Color.BLACK);
        graphics2D.setFont(font);
        int yPosition = (int) ((((132 + 28) * scale)));


        graphics2D.drawImage(boneImage, (int)(18 * scale), (int)(132 * scale), (int) (32 * scale), (int) (32 * scale), null);
        graphics2D.drawString("" + inventory.getCount(Ammunition.BONE), (int) ((18 + 32 + 16) * scale), yPosition);

        graphics2D.drawImage(fireChargeImage, (int)(141 * scale), (int)(132 * scale), (int) (32 * scale), (int) (32 * scale), null);
        graphics2D.drawString("" + inventory.getCount(Ammunition.FIRE_CHARGE), (int) ((141 + 32 + 16) * scale), yPosition);

        graphics2D.drawImage(lightningImage, (int)(264 * scale), (int)(132 * scale), (int) (32 * scale), (int) (32 * scale), null);
        graphics2D.drawString("" + inventory.getCount(Ammunition.LIGHTNING_CHARGE), (int) ((264 + 32 + 16) * scale), yPosition);


    }

    public void update(long deltaTime) {
        super.update(deltaTime);

        Point playerPosition = player.getLocation();
        Dimension monitor = WindowOnWindow.getRenderingSize();

        int xPosition = (int) ((monitor.getWidth() - defaultDimension.getWidth()) / 2);
        int yDistance = 25;

        if(playerPosition.getY() < monitor.getHeight() / 2) {
            setLocation(xPosition, (int) (monitor.getHeight() - defaultDimension.getHeight() - yDistance));
        } else {
            setLocation(xPosition, yDistance);
        }
    }
}
