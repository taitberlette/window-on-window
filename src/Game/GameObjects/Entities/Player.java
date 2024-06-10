package Game.GameObjects.Entities;

import Assets.AssetManager;
import Game.GameObjects.GameObject;
import Game.GameObjects.Objects.MovableBox;
import Game.GameObjects.Projectiles.TunnelVision;
import Game.GameObjects.Weapons.Melee.PocketKnife;
import Game.GameObjects.Weapons.Shooter.BoneShooter;
import Game.GameObjects.Weapons.Shooter.FlameThrower;
import Game.GameObjects.Weapons.Shooter.RailGun;
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

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Player extends Entity implements KeyListener {
    private BufferedImage[] terraImages = new BufferedImage[4];
    private BufferedImage[] etherImages = new BufferedImage[4];

    private BufferedImage armTerraImage;
    private BufferedImage armEtherImage;

    private PlayerStatsWindow playerStatsWindow;

    private String name;
    private Game game;

    private Inventory inventory;

    private PocketKnife pocketKnife;
    private BoneShooter boneShooter;
    private Weapon carryingWeapon;

    private MovableBox carryingBox;

    private Skill photosynthesisSkill;
    private Skill fastLegsSkill;
    private Skill tunnelVisionSkill;

    private int animationFrame = 0;
    private long lastFrame = 0;

    private boolean leftKey = false;
    private boolean rightKey = false;

    private boolean aimingLeftKey = false;
    private boolean aimingRightKey = false;
    private double aimAngle = Math.PI / 2;

    private final double ARM_SPEED = Math.PI;

    private final int HEAL_SPEED = 25;

    public Player(String name, Game game) {
        super(new Dimension(52, 128));

        this.maxSpeed = 300;

        this.name = name;
        this.game = game;

        this.inventory = new Inventory();

        this.pocketKnife = new PocketKnife(this);
        this.pocketKnife.setHeld(true);

        this.boneShooter = new BoneShooter(world);
        this.boneShooter.setHeld(true);

        this.photosynthesisSkill = new Skill(1);
        this.fastLegsSkill = new Skill(10);
        this.tunnelVisionSkill = new Skill(20);

        this.maxHealth = 250;
        this.health = 250;

        this.playerStatsWindow = new PlayerStatsWindow(name, this, game, photosynthesisSkill, fastLegsSkill, tunnelVisionSkill);

        terraImages[0] = terraImages[2] = AssetManager.getImage("res\\Player\\TerraPlayerIdol.png");
        terraImages[1] = AssetManager.getImage("res\\Player\\TerraPlayerMoving1.png");
        terraImages[3] = AssetManager.getImage("res\\Player\\TerraPlayerMoving2.png");

        etherImages[0] = etherImages[2] = AssetManager.getImage("res\\Player\\EtherPlayerIdol.png");
        etherImages[1] = AssetManager.getImage("res\\Player\\EtherPlayerMoving1.png");
        etherImages[3] = AssetManager.getImage("res\\Player\\EtherPlayerMoving2.png");

        armTerraImage = AssetManager.getImage("res\\Player\\Terra Arm.png");
        armEtherImage = AssetManager.getImage("res\\Player\\Ether Arm.png");

        playerStatsWindow.setVisible(true);
    }

    public Player(ArrayList<String> lines, String name, Game game) {
        super(lines, new Dimension(52, 128));

        this.maxSpeed = 300;

        this.name = name;
        this.game = game;

        this.inventory = new Inventory();

        this.pocketKnife = new PocketKnife(this);
        this.pocketKnife.setHeld(true);

        this.boneShooter = new BoneShooter(world);
        this.boneShooter.setHeld(true);

        this.photosynthesisSkill = new Skill(1);
        this.fastLegsSkill = new Skill(10);
        this.tunnelVisionSkill = new Skill(20);

        for(int i = 0; i < lines.size(); i++){
            String packet = lines.get(i);

            if(packet.startsWith("BOX=")) {
                boolean box = Boolean.parseBoolean(packet.replace("BOX=", "").trim());
                if(box) {
                    carryingBox = new MovableBox(position, world);
                }
            }if(packet.startsWith("WEAPON=")) {
                String weapon = (packet.replace("WEAPON=", "").trim());
                if(weapon.equals("FLAME THROWER")) {
                    carryingWeapon = new FlameThrower(world);
                } else if(weapon.equals("RAIL GUN")) {
                    carryingWeapon = new RailGun(world);
                }
            } else if(packet.startsWith("INVENTORY")) {
                ArrayList<String> data = new ArrayList<>();

                i++;
                for(; i < lines.size() && !lines.get(i).equals("END INVENTORY"); i++) {
                    data.add(lines.get(i));
                }

                inventory = new Inventory(data);
            } else if(packet.startsWith("PHOTOSYNTHESIS SKILL")) {
                ArrayList<String> data = new ArrayList<>();

                i++;
                for(; i < lines.size() && !lines.get(i).equals("END PHOTOSYNTHESIS SKILL"); i++) {
                    data.add(lines.get(i));
                };

                photosynthesisSkill = new Skill(data);
            } else if(packet.startsWith("FAST LEGS SKILL")) {
                ArrayList<String> data = new ArrayList<>();

                i++;
                for(; i < lines.size() && !lines.get(i).equals("END FAST LEGS SKILL"); i++) {
                    data.add(lines.get(i));
                }

                fastLegsSkill = new Skill(data);
            } else if(packet.startsWith("TUNNEL VISION SKILL")) {
                ArrayList<String> data = new ArrayList<>();

                i++;
                for(; i < lines.size() && !lines.get(i).equals("END TUNNEL VISION SKILL"); i++) {
                    data.add(lines.get(i));
                }

                tunnelVisionSkill = new Skill(data);
            }
        }

        this.maxHealth = 250;

        this.playerStatsWindow = new PlayerStatsWindow(name, this, game, photosynthesisSkill, fastLegsSkill, tunnelVisionSkill);

        terraImages[0] = terraImages[2] = AssetManager.getImage("res\\Player\\TerraPlayerIdol.png");
        terraImages[1] = AssetManager.getImage("res\\Player\\TerraPlayerMoving1.png");
        terraImages[3] = AssetManager.getImage("res\\Player\\TerraPlayerMoving2.png");

        etherImages[0] = etherImages[2] = AssetManager.getImage("res\\Player\\EtherPlayerIdol.png");
        etherImages[1] = AssetManager.getImage("res\\Player\\EtherPlayerMoving1.png");
        etherImages[3] = AssetManager.getImage("res\\Player\\EtherPlayerMoving2.png");

        armTerraImage = AssetManager.getImage("res\\Player\\Terra Arm.png");
        armEtherImage = AssetManager.getImage("res\\Player\\Ether Arm.png");

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
            aimAngle += ARM_SPEED * ((double) deltaTime / 1000000000);
        } else if(!aimingLeftKey && aimingRightKey) {
            aimAngle -= ARM_SPEED * ((double) deltaTime / 1000000000);
        }

        super.update(deltaTime);

        if(pocketKnife != null) {
            pocketKnife.setLocation(position);
            pocketKnife.update(deltaTime);
        }

        boneShooter.update(deltaTime);

        if(carryingWeapon != null) {
            carryingWeapon.update(deltaTime);
        }

        if(playerStatsWindow != null){
            playerStatsWindow.update(deltaTime);
        }

        photosynthesisSkill.update(deltaTime);
        fastLegsSkill.update(deltaTime);
        tunnelVisionSkill.update(deltaTime);

        if(fastLegsSkill.getActiveStatus()){
            maxSpeed = 500;
        } else {
            maxSpeed = 300;
        }

        if (photosynthesisSkill.wasUnlocked() && world instanceof TerraWorld){
            if (health < maxHealth){health+=((double) deltaTime / 1000000000) * HEAL_SPEED;}
        }

        if(carryingBox != null) {
            Point boxPosition = new Point(position);
            boxPosition.translate(0, -88);
            carryingBox.setLocation(boxPosition);
        }

        if(onGround) {
            if((animationFrame % 2 != 0 || velocityX != 0)) {
                if(System.currentTimeMillis() - lastFrame > Math.abs(50000 / maxSpeed)) {
                    animationFrame++;
                    lastFrame = System.currentTimeMillis();
                }
            }
        } else {
            animationFrame = 3;
        }
    }

    public void draw(Graphics2D graphics2D) {
        if(world == null) {
            return;
        }

        BufferedImage[] images = world instanceof TerraWorld ? terraImages : etherImages;
        BufferedImage image = images[animationFrame % images.length];

        int offsetX = lastDirection == HorizontalDirection.RIGHT ? image.getWidth() : 0;

        graphics2D.drawImage((Image) image, ((int) position.getX() - image.getWidth() / 2) + offsetX,(int) position.getY() - image.getHeight() / 2, image.getWidth() * (lastDirection == HorizontalDirection.RIGHT ? -1 : 1), image.getHeight(), null);

        if(pocketKnife != null)  {
            pocketKnife.draw(graphics2D);
        }


        BufferedImage aimImage = world instanceof TerraWorld ? armTerraImage : armEtherImage;

        double frontArmAngle = 0;
        double backArmAngle = Math.PI;

        if(carryingBox != null) {
            frontArmAngle = Math.PI / 2;
            backArmAngle = Math.PI / 2;
        } else {
            frontArmAngle = lastDirection == HorizontalDirection.RIGHT ? (15 * Math.PI) / 8 : (9 * Math.PI) / 8;
            if(inventory.hasItem(Ammunition.BONE) || carryingWeapon != null || tunnelVisionSkill.wasUnlocked()) {
                backArmAngle = aimAngle;
            } else {
                backArmAngle = (12 * Math.PI) / 8;
            }
        }

        int verticalOffset = 12;

        int horizontalFrontOffset = lastDirection == HorizontalDirection.RIGHT ? 24 : - 24;
        graphics2D.rotate(-frontArmAngle, position.getX() + horizontalFrontOffset, position.getY() - verticalOffset);
        graphics2D.drawImage(aimImage, (int) position.getX() + horizontalFrontOffset, (int) (position.getY() - aimImage.getHeight() / 2) - verticalOffset, null);
        graphics2D.rotate(frontArmAngle, position.getX() + horizontalFrontOffset, position.getY() - verticalOffset);

        int horizontalOffset = lastDirection == HorizontalDirection.RIGHT ? - 12 : 12;
        graphics2D.rotate(-backArmAngle, position.getX() + horizontalOffset, position.getY() - verticalOffset);
        graphics2D.drawImage(aimImage, (int) position.getX() + horizontalOffset, (int) (position.getY() - aimImage.getHeight() / 2) - verticalOffset, null);

        if(carryingWeapon != null && carryingBox == null) {

            boolean flipImage = (((backArmAngle + Math.PI / 2) % (Math.PI * 2)) + (Math.PI * 2)) % (Math.PI * 2) > Math.PI;

            BufferedImage weapon = carryingWeapon.getImage();

            if(weapon != null) {
                graphics2D.drawImage(weapon, (int) position.getX() + horizontalOffset + 20, (int) (position.getY() - weapon.getHeight() / 2) - verticalOffset + (flipImage ? weapon.getHeight() : 0), weapon.getWidth(), (flipImage ? -1 : 1) * weapon.getHeight(), null);
            }
        }

        graphics2D.rotate(backArmAngle, position.getX() + horizontalOffset, position.getY() - verticalOffset);
    }

    public void kill() {
        if(playerStatsWindow != null) {
            playerStatsWindow.setVisible(false);
            playerStatsWindow = null;
        }
    }

    public Weapon getCarryingWeapon() {
        return carryingWeapon;
    }

    public void unlockPhotosynthesis() {
        photosynthesisSkill.unlock();
    }

    public void unlockFastLegs() {
        fastLegsSkill.unlock();
    }

    public void unlockTunnelVision() {
        tunnelVisionSkill.unlock();
    }

    public void keyTyped(KeyEvent e) {

    }

    public void keyPressed(KeyEvent e) {

        int verticalOffset = 6;
        int horizontalOffset = lastDirection == HorizontalDirection.RIGHT ?  -12 : 12;
        int x = (int) (position.getX() + horizontalOffset + (Math.cos(aimAngle) * (armTerraImage.getWidth() )));
        int y = (int) ((position.getY() - armTerraImage.getHeight() / 2) - verticalOffset - (Math.sin(aimAngle) * (armTerraImage.getWidth() )));

        Point hand = new Point(x, y);



        if(e.getKeyCode() == KeyEvent.VK_UP && onGround) {
            velocityY = 400;
        } else if(carryingBox == null && e.getKeyCode() == KeyEvent.VK_Q && pocketKnife != null && pocketKnife.checkCooldown()) {
            pocketKnife.attack(lastDirection);
        } else if(carryingBox == null && e.getKeyCode() == KeyEvent.VK_W) {
            Shooter shooter = boneShooter;

            if(carryingWeapon != null && carryingWeapon instanceof Shooter carryingShooter) {
                shooter = carryingShooter;
            }

            if(shooter.checkCooldown() && shooter.checkAmmunition(inventory)) {
                shooter.attack(aimAngle, hand, inventory);
            }
        } else if(e.getKeyCode() == KeyEvent.VK_E && tunnelVisionSkill.wasUnlocked() && tunnelVisionSkill.isFull()) {

            TunnelVision tunnelVision = new TunnelVision();
            tunnelVision.setLocation(hand);
            tunnelVision.launch(world, aimAngle, 750, 0);
            world.addGameObject(tunnelVision);

            tunnelVisionSkill.useFull();
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
        } else if(e.getKeyCode() == KeyEvent.VK_F) {
            if(carryingBox == null) {
                // pickup box
                ArrayList<GameObject> gameObjects = world.findGameObjects(getBounds());

                for(GameObject gameObject : gameObjects) {
                    if(gameObject instanceof MovableBox movableBox) {
                        movableBox.setGrabbed(true);
                        carryingBox = movableBox;
                        break;
                    }
                }
            } else {
                // drop box
                carryingBox.setGrabbed(false);
                carryingBox = null;
            }
        }  else if(e.getKeyCode() == KeyEvent.VK_SHIFT) {
            if(carryingWeapon == null) {
                // pickup weapon
                ArrayList<Weapon> droppedWeapons = world.findWeapons(getBounds());

                for(Weapon weapon : droppedWeapons) {
                    weapon.setHeld(false);
                    carryingWeapon = weapon;
                    world.removeGameObject(carryingWeapon);
                    break;
                }
            } else {
                // drop weapon
                world.addGameObject(carryingWeapon);
                carryingWeapon.setLocation(hand);
                carryingWeapon.setHeld(false);
                carryingWeapon = null;
            }
        }

        if(fastLegsSkill.wasUnlocked() && e.getKeyCode() == KeyEvent.VK_S && fastLegsSkill.getCooldown() > 3){
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
        if(world != this.world) {
            if(carryingBox != null) {
                carryingBox.setGrabbed(false);
                carryingBox = null;
            }
        }

        this.world = world;

        if(this.pocketKnife != null) {
            this.pocketKnife.setWorld(world);
        }

        if(this.boneShooter != null) {
            this.boneShooter.setWorld(world);
        }

        if(this.carryingWeapon != null) {
            this.carryingWeapon.setWorld(world);
        }
    }

    public Inventory getInventory() {
        return inventory;
    }

    public String encode() {
        String result = super.encode();

        result += "LOCATION=" + ((world instanceof TerraWorld) ? "Terra" : "Ether") + "\n";

        if(carryingBox != null) {
            result += "BOX=true" + "\n";
        }

        if(carryingWeapon != null) {
            result += "WEAPON=";
            if(carryingWeapon instanceof FlameThrower) {
                result += "FLAME THROWER\n";
            } else if(carryingWeapon instanceof RailGun) {
                result += "RAIL GUN\n";
            }
        }

        result += "PHOTOSYNTHESIS SKILL\n";
        result += photosynthesisSkill.encode();
        result += "END PHOTOSYNTHESIS SKILL\n";

        result += "FAST LEGS SKILL\n";
        result += fastLegsSkill.encode();
        result += "END FAST LEGS SKILL\n";

        result += "TUNNEL VISION SKILL\n";
        result += tunnelVisionSkill.encode();
        result += "END TUNNEL VISION SKILL\n";

        result += "INVENTORY\n";
        result += inventory.encode();
        result += "END INVENTORY\n";

        return result;
    }
}
