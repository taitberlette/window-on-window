package Game.Levels;

import Assets.AssetManager;
import Game.Game;
import Game.GameObjects.Entities.Enemies.HellHound;
import Game.GameObjects.Entities.Enemies.ShockSpider;
import Game.GameObjects.Entities.Player;
import Game.GameObjects.Gadgets.BoxButton;
import Game.GameObjects.Gadgets.MovingPlatform;
import Game.GameObjects.Gadgets.MovingWall;
import Game.GameObjects.Gadgets.Target;
import Game.GameObjects.Objects.MovableBox;
import Game.GameObjects.Objects.Door;
import Game.GameObjects.Objects.HiddenNumber;
import Game.GameObjects.Objects.Tree;
import Game.GameObjects.Weapons.Shooter.FlameThrower;
import Game.GameObjects.Weapons.Shooter.RailGun;
import Game.Utilities.Ammunition;
import Game.Utilities.Inventory;
import Game.Worlds.CollisionType;
import Game.Worlds.TerraWorld;
import Windows.ImageWindow;
import Windows.WorldWindow;

import java.awt.*;
import java.util.Random;

public class LevelZero extends Level {

    private HiddenNumber firstNumber;
    private Door door;
    private Target target;
    private MovingPlatform platform;
    private BoxButton button;
    private MovingWall wall;
    private MovableBox movableBox;
    private HellHound hellHound;
    private ShockSpider shockSpider;
    private FlameThrower flameThrower;
    private RailGun railGun;
    private WorldWindow terraWorldWindow;
    private WorldWindow etherWorldWindow;
    private ImageWindow introTutorialWindow;
    private ImageWindow skillsTutorialWindow;

    private boolean skillsUnlocked = false;
    private boolean seenDoor = false;
    private boolean seenNumbers = false;

    private enum IntroTutorialState {
        MOVEMENT,
        LADDER,
        SWITCH_WINDOW,
        POCKET_KNIFE,
        THROW_BONE,
        TUTORIAL_PAUSE_PLATFORM,
        BOX,
        TUTORIAL_PAUSE_WALL,
        WEAPON,
        HELD_WEAPON,
        COMPLETE
    }

    private IntroTutorialState tutorialState = IntroTutorialState.MOVEMENT;

    public LevelZero(Game game, Player player) {
        super(game, player, "Level_Tutorial");

        Random random = new Random();

        int[] combination = {random.nextInt(1, 10)};

        firstNumber = new HiddenNumber(new Point(1750, 280), combination[0], Color.RED);
        etherWorld.addGameObject(firstNumber);

        door = new Door(new Point(1789, 255), player, terraWorld, game, "Level_Tutorial", combination);
        terraWorld.addGameObject(door);

        Tree tree = new Tree(new Point (180, 828));
        terraWorld.addGameObject(tree);

        int idPlatform = 1;

        target = new Target(new Point(890, 700), idPlatform, etherWorld);
        etherWorld.addGameObject(target);

        platform = new MovingPlatform(new Point(1078, 932), new Point(1078, 424), idPlatform, etherWorld);
        etherWorld.addGameObject(platform);

        int idWall = 2;

        button = new BoxButton(new Point(1324, 352), idWall, etherWorld);
        etherWorld.addGameObject(button);

        wall = new MovingWall(new Point(1432, 288), new Point(1432, 480), idWall, etherWorld);
        etherWorld.addGameObject(wall);

        movableBox = new MovableBox(new Point(250, 352), etherWorld);
        etherWorld.addGameObject(movableBox);

        flameThrower = new FlameThrower(etherWorld);
        flameThrower.setLocation(new Point(1547, 320));
        etherWorld.addGameObject(flameThrower);

        railGun = new RailGun(etherWorld);
        railGun.setLocation(new Point(1654, 320));
        etherWorld.addGameObject(railGun);

        hellHound = new HellHound(player, etherWorld);
        hellHound.setLocation(new Point(562, 764));
        etherWorld.addGameObject(hellHound);

        shockSpider = new ShockSpider(player, etherWorld);
        shockSpider.setLocation(new Point(70, 305));
        etherWorld.addGameObject(shockSpider);

        terraWorldWindow = new WorldWindow(terraWorld);
        terraWorldWindow.setTarget(player);
        terraWorldWindow.setFocusable(true);
        terraWorldWindow.requestFocus();
        terraWorldWindow.setKeyListener(game);
        worldWindows.add(terraWorldWindow);

        etherWorldWindow = new WorldWindow(etherWorld);
        etherWorldWindow.setFocusable(true);
        etherWorldWindow.requestFocus();
        etherWorldWindow.setKeyListener(game);
        etherWorldWindow.setLocation(new Point(911, 674));
        worldWindows.add(etherWorldWindow);

        introTutorialWindow = new ImageWindow("Tutorial", AssetManager.getImage("res\\Tutorial\\Movement.png"), new Point(110, 320));
        introTutorialWindow.setKeyListener(game);

        skillsTutorialWindow = new ImageWindow("Skills", AssetManager.getImage("res\\Tutorial\\Skills.png"), new Point(81, 670));
        skillsTutorialWindow.setKeyListener(game);
    }

    public void open() {
        super.open();

        player.setLocation(new Point(246, 800));
        player.setWorld(terraWorld);
        terraWorld.addGameObject(player);

        introTutorialWindow.setVisible(true);
    }

    public void close() {
        super.close();

        introTutorialWindow.setVisible(false);
    }

    public void update(long deltaTime) {
        super.update(deltaTime);

        Point playerPosition = player.getLocation();
        boolean inTerra = player.getWorld() instanceof TerraWorld;
        boolean inEther = !inTerra;
        Inventory inventory = player.getInventory();
        CollisionType collisionType = player.getWorld().checkCollision(playerPosition);

        // intro tutorial series
        if(tutorialState == IntroTutorialState.MOVEMENT && collisionType == CollisionType.LADDER) {
            tutorialState = IntroTutorialState.LADDER;
            introTutorialWindow.changeImage(AssetManager.getImage("res\\Tutorial\\Ladder.png"));
        } else if(tutorialState == IntroTutorialState.LADDER && terraWorldWindow.getBounds().intersects(etherWorldWindow.getBounds())) {
            tutorialState = IntroTutorialState.SWITCH_WINDOW;
            introTutorialWindow.changeImage(AssetManager.getImage("res\\Tutorial\\SwitchWorld.png"));
        } else if(tutorialState == IntroTutorialState.SWITCH_WINDOW && inEther) {
            tutorialState = IntroTutorialState.POCKET_KNIFE;
            introTutorialWindow.changeImage(AssetManager.getImage("res\\Tutorial\\PocketKnife.png"));
        } else if(tutorialState == IntroTutorialState.POCKET_KNIFE && inventory.hasItem(Ammunition.BONE)) {
            tutorialState = IntroTutorialState.THROW_BONE;
            introTutorialWindow.changeImage(AssetManager.getImage("res\\Tutorial\\Shoot.png"));
        } else if(tutorialState == IntroTutorialState.THROW_BONE && target.isActivated()) {
            tutorialState = IntroTutorialState.TUTORIAL_PAUSE_PLATFORM;
            introTutorialWindow.setVisible(false);
        } else if(tutorialState == IntroTutorialState.TUTORIAL_PAUSE_PLATFORM && player.getBounds().intersects(movableBox.getBounds())) {
            tutorialState = IntroTutorialState.BOX;
            introTutorialWindow.changeImage(AssetManager.getImage("res\\Tutorial\\Box.png"));
            introTutorialWindow.setLocation(new Point(55, 550));
            introTutorialWindow.setVisible(true);
        } else if(tutorialState == IntroTutorialState.BOX && button.isActivated()) {
            tutorialState = IntroTutorialState.TUTORIAL_PAUSE_WALL;
            introTutorialWindow.setVisible(false);
        } else if(tutorialState == IntroTutorialState.TUTORIAL_PAUSE_WALL && (player.getBounds().intersects(railGun.getBounds()) || player.getBounds().intersects(flameThrower.getBounds()))) {
            tutorialState = IntroTutorialState.WEAPON;
            introTutorialWindow.changeImage(AssetManager.getImage("res\\Tutorial\\Weapons.png"));
            introTutorialWindow.setVisible(true);
        } else if(tutorialState == IntroTutorialState.WEAPON && player.getCarryingWeapon() != null) {
            tutorialState = IntroTutorialState.HELD_WEAPON;
        } else if(tutorialState == IntroTutorialState.HELD_WEAPON && player.getCarryingWeapon() == null) {
            tutorialState = IntroTutorialState.COMPLETE;
            introTutorialWindow.setVisible(false);
        }

        if(shockSpider.getHealth() <= 0 && !skillsUnlocked) {
            player.unlockFastLegs();
            player.unlockPhotosynthesis();
            player.unlockTunnelVision();
            skillsUnlocked = true;
            skillsTutorialWindow.setVisible(true);
        }
    }

    public void kill() {
        super.kill();

        introTutorialWindow.setVisible(false);
        skillsTutorialWindow.setVisible(false);
    }
}
