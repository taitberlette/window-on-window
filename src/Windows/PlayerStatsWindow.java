package Windows;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import Game.Game;
import Game.GameObjects.Entities.Player;
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
//            heartImage = ImageIO.read(new File("res\\Ammunition and Skills\\"));
//            heartImage = ImageIO.read(new File("res\\Ammunition and Skills\\"));
        } catch (IOException e) {
            System.out.println("Failed to load assests for the player stats window!");
        }

        this.view = new View(defaultDimension, this);

        this.view.setListener(game);
    }

    protected void draw(Graphics2D graphics2D, Dimension size) {
        // draw health bar
        graphics2D.setColor(healthBarBackground);
        graphics2D.fillRect(18, 48, 352, 27);

        double percentHealth = (double) player.getHealth() / player.getMaxHealth();

        graphics2D.setColor(healthBar);
        graphics2D.fillRect(18, 48, (int) (352 * percentHealth), 27);

        BufferedImage heartType = photosynthesisSkill.wasUnlocked() ? photosynthesisHeartImage : heartImage;
        graphics2D.drawImage(heartType, 347, 53, 16, 16, null);



        // draw fast legs bar
        graphics2D.setColor(fastLegsBarBackground);
        graphics2D.fillRect(18, 84, 172, 27);

        double percentFastLegsCooldown = (double) fastLegsSkill.getCooldown() / fastLegsSkill.getCooldownLength();

        graphics2D.setColor(fastLegsBar);
        graphics2D.fillRect(18, 84, (int) (172 * percentFastLegsCooldown), 27);

        graphics2D.drawImage(fastLegsImage, 170, 90, 16, 16, null);


        // draw tunnel vision bar
        graphics2D.setColor(tunnelVisionBarBackground);
        graphics2D.fillRect(198, 84, 172, 27);

        double percentTunnelVisionCooldown = (double) tunnelVisionSkill.getCooldown() / tunnelVisionSkill.getCooldownLength();

        graphics2D.setColor(tunnelVisionBar);
        graphics2D.fillRect(198, 84, (int) (172 * percentTunnelVisionCooldown), 27);

        graphics2D.drawImage(tunnelVisionImage, 350, 90, 16, 16, null);
    }

    public void update(long deltaTime) {
        super.update(deltaTime);

        Point playerPosition = player.getLocation();
        Dimension monitor = WindowOnWindow.getMonitorSize();

        int xPosition = (int) ((monitor.getWidth() - defaultDimension.getWidth()) / 2);
        int yDistance = 25;

        if(playerPosition.getY() < monitor.getHeight() / 2) {
            setLocation(xPosition, (int) (monitor.getHeight() - defaultDimension.getHeight() - yDistance));
        } else {
            setLocation(xPosition, yDistance);
        }
    }
}
