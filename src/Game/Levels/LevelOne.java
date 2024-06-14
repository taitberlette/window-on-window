package Game.Levels;

import Game.Game;
import Game.GameObjects.Entities.Enemies.HellHound;
import Game.GameObjects.Entities.Enemies.ShockSpider;
import Game.GameObjects.Entities.Player;
import Game.GameObjects.Gadgets.MovingPlatform;
import Game.GameObjects.Gadgets.Target;
import Game.GameObjects.Objects.Door;
import Game.GameObjects.Objects.HiddenNumber;
import Game.GameObjects.Objects.Tree;
import Windows.WorldWindow;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class LevelOne extends Level {
    public LevelOne(Game game, Player player) {
        super(game, player, "Level_One");

        terraWorld.addGameObject(player);

        Random random = new Random();

        int[] combination = {random.nextInt(1, 10), random.nextInt(1, 10), random.nextInt(1, 10)};

        HiddenNumber firstNumber = new HiddenNumber(new Point(979, 360), combination[0], Color.RED, etherWorld);
        etherWorld.addGameObject(firstNumber);

        HiddenNumber secondNumber = new HiddenNumber(new Point(1124, 802), combination[1], Color.YELLOW, etherWorld);
        etherWorld.addGameObject(secondNumber);

        HiddenNumber thirdNumber = new HiddenNumber(new Point(1820, 605), combination[2], Color.BLUE, etherWorld);
        etherWorld.addGameObject(thirdNumber);

        HiddenNumber decoyNumber = new HiddenNumber(new Point(13, 739), random.nextInt(1, 10), Color.GREEN, etherWorld);
        etherWorld.addGameObject(decoyNumber);

        Door door = new Door(new Point(1774, 332), player, terraWorld, game, "Level_One", combination);
        terraWorld.addGameObject(door);

        Tree tree = new Tree(new Point (78, 820));
        terraWorld.addGameObject(tree);

        Tree tree1 = new Tree(new Point (363, 490));
        terraWorld.addGameObject(tree1);

        Tree tree2 = new Tree(new Point (608, 451));
        terraWorld.addGameObject(tree2);

        Tree tree3 = new Tree(new Point (1446, 691));
        terraWorld.addGameObject(tree3);

        Tree tree4 = new Tree(new Point (1719, 694));
        terraWorld.addGameObject(tree4);

        int idPlatform = 1;

        Target target = new Target(new Point(362, 725), idPlatform, etherWorld);
        etherWorld.addGameObject(target);

        MovingPlatform platform = new MovingPlatform(new Point(224, 922), new Point(224, 579), idPlatform, etherWorld);
        etherWorld.addGameObject(platform);

        HellHound hellHound = new HellHound(player, etherWorld);
        hellHound.setLocation(new Point(562, 764));
        etherWorld.addGameObject(hellHound);

        ShockSpider shockSpider = new ShockSpider(player, etherWorld);
        shockSpider.setLocation(new Point(1022, 353));
        shockSpider.setBoss();
        etherWorld.addGameObject(shockSpider);

        WorldWindow terraWorldWindow = new WorldWindow(terraWorld);
        terraWorldWindow.setTarget(player);
        terraWorldWindow.setFocusable(true);
        terraWorldWindow.requestFocus();
        terraWorldWindow.setKeyListener(game);
        worldWindows.add(terraWorldWindow);

        WorldWindow etherWorldWindow = new WorldWindow(etherWorld);
        etherWorldWindow.setFocusable(true);
        etherWorldWindow.requestFocus();
        etherWorldWindow.setKeyListener(game);
        etherWorldWindow.setLocation(new Point(1544, 500));
        worldWindows.add(etherWorldWindow);
    }

    public LevelOne(ArrayList<String> lines, Game game, Player player) {
        super(lines, game, player, "Level_One");

        if(inTerra) {
            terraWorld.addGameObject(player);
        } else {
            etherWorld.addGameObject(player);
        }
    }

    public void open() {
        super.open();

        if(!levelPlayed) {
            levelPlayed = true;
            inTerra = true;
            playerPosition = new Point(124, 800);

            player.setLocation(playerPosition);
            player.setWorld(terraWorld);
        } else {
            System.out.println("LEVEL WAS PLAYED BEFORE, PUT THE PLAYER IN TERRA? " + inTerra);
            player.setLocation(playerPosition);
            player.setWorld(inTerra ? terraWorld : etherWorld);
        }
    }

    public void checkpointJump() {
        super.checkpointJump();

        if(etherWorld.numberOfEnemies() > 0) return;


        HellHound hellHound = new HellHound(player, etherWorld);
        hellHound.setLocation(new Point(562, 764));
        etherWorld.addGameObject(hellHound);
    }
}
