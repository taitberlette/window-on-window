package Game.Levels;

import Game.Game;
import Game.GameObjects.Entities.Enemies.*;
import Game.GameObjects.Entities.Player;
import Game.GameObjects.Gadgets.BoxButton;
import Game.GameObjects.Gadgets.MovingPlatform;
import Game.GameObjects.Gadgets.MovingWall;
import Game.GameObjects.Gadgets.Target;
import Game.GameObjects.Objects.Door;
import Game.GameObjects.Objects.HiddenNumber;
import Game.GameObjects.Objects.MovableBox;
import Game.GameObjects.Objects.Tree;
import Windows.WorldWindow;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class LevelTwo extends Level {
    public LevelTwo(Game game, Player player) {
        super(game, player, "Level_Two");
        terraWorld.addGameObject(player);

        Random random = new Random();

        int[] combination = {random.nextInt(1, 10), random.nextInt(1, 10), random.nextInt(1, 10), random.nextInt(1, 10)};

        HiddenNumber firstNumber = new HiddenNumber(new Point(53, 499), combination[0], Color.RED, etherWorld);
        etherWorld.addGameObject(firstNumber);

        HiddenNumber secondNumber = new HiddenNumber(new Point(225, 93), combination[1], Color.YELLOW, etherWorld);
        etherWorld.addGameObject(secondNumber);

        HiddenNumber thirdNumber = new HiddenNumber(new Point(1201, 193), combination[2], Color.BLUE, etherWorld);
        etherWorld.addGameObject(thirdNumber);

        HiddenNumber fourthNumber = new HiddenNumber((new Point(1889, 809)), combination[3], Color.GREEN, etherWorld);
        etherWorld.addGameObject(fourthNumber);

        HiddenNumber decoyNumber1 = new HiddenNumber(new Point(704, 900), random.nextInt(1, 10), Color.PINK, etherWorld);
        etherWorld.addGameObject(decoyNumber1);

        HiddenNumber decoynumber2 = new HiddenNumber(new Point(1840, 197), random.nextInt(1, 10), Color.orange, etherWorld);
        etherWorld.addGameObject(decoynumber2);

        Door door = new Door(new Point(1757, 152), player, terraWorld, game, "Level_Two", combination);
        terraWorld.addGameObject(door);

        Tree tree = new Tree(new Point (118, 824));
        terraWorld.addGameObject(tree);

        Tree tree1 = new Tree(new Point (303, 824));
        terraWorld.addGameObject(tree1);

        Tree tree2 = new Tree(new Point (449, 824));
        terraWorld.addGameObject(tree2);

        Tree tree3 = new Tree(new Point (629, 824));
        terraWorld.addGameObject(tree3);

        int idPlatform1 = 1;
        int idPlatform2 = 2;
        Target target1_1 = new Target(new Point(313, 740), idPlatform1, etherWorld);
        etherWorld.addGameObject(target1_1);

        Target target1_2 = new Target(new Point(266,56 ), idPlatform1, etherWorld);
        etherWorld.addGameObject(target1_2);

        MovingPlatform platform1 = new MovingPlatform(new Point(202, 899), new Point(202, 210), idPlatform1, etherWorld);
        etherWorld.addGameObject(platform1);

        Target target2 = new Target(new Point(1423, 740), idPlatform2, etherWorld);
        etherWorld.addGameObject(target2);

        MovingPlatform platform2 = new MovingPlatform(new Point(1519, 933), new Point(1519, 314), idPlatform2, etherWorld);
        etherWorld.addGameObject(platform2);

        int idWall = 3;

        BoxButton button = new BoxButton(new Point(1685, 860), idWall, etherWorld);
        etherWorld.addGameObject(button);

        MovingWall wall = new MovingWall(new Point(1778, 796), new Point(1432, 480), idWall, etherWorld);
        etherWorld.addGameObject(wall);

        MovableBox movableBox= new MovableBox(new Point(1256, 245), etherWorld);
        etherWorld.addGameObject(movableBox);

        HellHound hellHound1 = new HellHound(player, etherWorld);
        hellHound1.setLocation(new Point(896, 764));
        etherWorld.addGameObject(hellHound1);

        HellHound hellHound2 = new HellHound(player, etherWorld);
        hellHound2.setLocation(new Point(1727, 764));
        etherWorld.addGameObject(hellHound2);

        ShockSpider shockSpider = new ShockSpider(player, etherWorld);
        shockSpider.setLocation(new Point(1200, 764));
        etherWorld.addGameObject(shockSpider);

        Panzer panzer = new Panzer(player, etherWorld);
        panzer.setLocation(new Point(876, 100));
        etherWorld.addGameObject(panzer);

        SilverBack silverback = new SilverBack(player, etherWorld);
        silverback.setLocation(new Point(604, 205));
        silverback.setBoss();
        etherWorld.addGameObject(silverback);


        WorldWindow terraWorldWindow1 = new WorldWindow(terraWorld);
        terraWorldWindow1.setTarget(player);
        terraWorldWindow1.setFocusable(true);
        terraWorldWindow1.requestFocus();
        terraWorldWindow1.setKeyListener(game);
        worldWindows.add(terraWorldWindow1);

        WorldWindow terraWorldWindow2 = new WorldWindow(terraWorld);
        terraWorldWindow2.setFocusable(true);
        terraWorldWindow2.requestFocus();
        terraWorldWindow2.setKeyListener(game);
        terraWorldWindow2.setLocation(new Point(1650, 56));
        worldWindows.add(terraWorldWindow2);

        WorldWindow etherWorldWindow = new WorldWindow(etherWorld);
        etherWorldWindow.setFocusable(true);
        etherWorldWindow.requestFocus();
        etherWorldWindow.setKeyListener(game);
        etherWorldWindow.setLocation(new Point(549, 663));
        worldWindows.add(etherWorldWindow);
    }

    public LevelTwo(ArrayList<String> lines, Game game, Player player) {
       super(lines, game, player, "Level_Two");

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
            playerPosition = new Point(55, 683);

            player.setLocation(playerPosition);
            player.setWorld(terraWorld);
        } else {
            player.setLocation(playerPosition);
            player.setWorld(inTerra ? terraWorld : etherWorld);
        }
    }

    public void checkpointJump() {
        super.checkpointJump();

        if(etherWorld.numberOfEnemies() > 0) return;
        
        Panzer panzer = new Panzer(player, etherWorld);
        panzer.setLocation(new Point(876, 100));
        etherWorld.addGameObject(panzer);

        HellHound hellHound1 = new HellHound(player, etherWorld);
        hellHound1.setLocation(new Point(896, 764));
        etherWorld.addGameObject(hellHound1);

        HellHound hellHound2 = new HellHound(player, etherWorld);
        hellHound2.setLocation(new Point(1727, 764));
        etherWorld.addGameObject(hellHound2);
    }
}
