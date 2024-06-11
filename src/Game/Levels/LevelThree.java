package Game.Levels;

import Game.Game;
import Game.GameObjects.Entities.Player;
import Game.GameObjects.Gadgets.BoxButton;
import Game.GameObjects.Gadgets.MovingPlatform;
import Game.GameObjects.Gadgets.MovingWall;
import Game.GameObjects.Gadgets.Target;
import Game.GameObjects.Objects.Door;
import Game.GameObjects.Objects.HiddenNumber;
import Game.GameObjects.Objects.MovableBox;
import Game.GameObjects.Objects.Tree;
import Game.Worlds.World;
import Windows.WorldWindow;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class LevelThree extends Level {
    public LevelThree(Game game, Player player) {

        super(game, player, "Level_Three");
        terraWorld.addGameObject(player);

        Random random = new Random();

        int[] combination = {random.nextInt(1, 10), random.nextInt(1, 10), random.nextInt(1, 10), random.nextInt(1, 10), random.nextInt(1, 10)};

        HiddenNumber firstNumber = new HiddenNumber(new Point(40, 462), combination[0], Color.RED, etherWorld);
        etherWorld.addGameObject(firstNumber);

        HiddenNumber secondNumber = new HiddenNumber(new Point(341, 168), combination[1], Color.YELLOW, etherWorld);
        etherWorld.addGameObject(secondNumber);

        HiddenNumber thirdNumber = new HiddenNumber(new Point(704, 610), combination[2], Color.BLUE, etherWorld);
        etherWorld.addGameObject(thirdNumber);

        HiddenNumber fourthNumber = new HiddenNumber((new Point(960, 921)), combination[3], Color.GREEN, etherWorld);
        etherWorld.addGameObject(fourthNumber);

        HiddenNumber fifthNumber = new HiddenNumber((new Point(1836, 423)), combination[4], new Color(128, 68, 199), etherWorld);
        etherWorld.addGameObject(fifthNumber);

        HiddenNumber decoyNumber1 = new HiddenNumber(new Point(1157, 443), random.nextInt(1, 10), Color.PINK, etherWorld);
        etherWorld.addGameObject(decoyNumber1);

        HiddenNumber decoyNumber2 = new HiddenNumber(new Point(1370, 244), random.nextInt(1, 10), Color.orange, etherWorld);
        etherWorld.addGameObject(decoyNumber2);

        HiddenNumber decoyNumber3 = new HiddenNumber(new Point(1349, 762), random.nextInt(1, 10), Color.magenta, etherWorld);
        etherWorld.addGameObject(decoyNumber3);

        Door door = new Door(new Point(1753, 436), player, terraWorld, game, "Level_Three", combination);
        terraWorld.addGameObject(door);

        int idPlatform1 = 1;
        int idPlatform2 = 2;
        Target target1_1 = new Target(new Point(1392, 442), idPlatform1, etherWorld);
        etherWorld.addGameObject(target1_1);

        Target target1_2 = new Target(new Point(266,56 ), idPlatform1, etherWorld);
        etherWorld.addGameObject(target1_2);

        MovingPlatform platform1 = new MovingPlatform(new Point(1041, 876), new Point(1041, 587), idPlatform1, etherWorld);
        etherWorld.addGameObject(platform1);

        MovingPlatform platform3 = new MovingPlatform(new Point(486, 876), new Point(486, 587), idPlatform1, etherWorld);
        etherWorld.addGameObject(platform3);

        Target target2 = new Target(new Point(1093, 104), idPlatform2, etherWorld);
        etherWorld.addGameObject(target2);

        MovingPlatform platform2 = new MovingPlatform(new Point(764, 876), new Point(764, 587), idPlatform2, etherWorld);
        etherWorld.addGameObject(platform2);

        int idWall = 3;

        BoxButton button = new BoxButton(new Point(299, 494), idWall, etherWorld);
        etherWorld.addGameObject(button);

        MovingWall wall = new MovingWall(new Point(201, 366), new Point(1432, 480), idWall, etherWorld);
        etherWorld.addGameObject(wall);

        MovableBox movableBox= new MovableBox(new Point(1618, 285), etherWorld);
        etherWorld.addGameObject(movableBox);

        Tree tree = new Tree(new Point (152, 271));
        terraWorld.addGameObject(tree);

        Tree tree1 = new Tree(new Point (386, 271));
        terraWorld.addGameObject(tree1);

        Tree tree2 = new Tree(new Point (620, 271));
        terraWorld.addGameObject(tree2);

        Tree tree3 = new Tree(new Point (854, 271));
        terraWorld.addGameObject(tree3);

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
        terraWorldWindow2.setLocation(new Point(1650, 343));
        worldWindows.add(terraWorldWindow2);

        WorldWindow etherWorldWindow1 = new WorldWindow(etherWorld);
        etherWorldWindow1.setFocusable(true);
        etherWorldWindow1.requestFocus();
        etherWorldWindow1.setKeyListener(game);
        etherWorldWindow1.setLocation(new Point(719, 682));
        worldWindows.add(etherWorldWindow1);

        WorldWindow etherWorldWindow2 = new WorldWindow(etherWorld);
        etherWorldWindow2.setFocusable(true);
        etherWorldWindow2.requestFocus();
        etherWorldWindow2.setKeyListener(game);
        etherWorldWindow2.setLocation(449, 136);
        worldWindows.add(etherWorldWindow2);

    }

    public LevelThree(ArrayList<String> lines, Game game, Player player) {
        super(lines, game, player, "Level_Three");
        if (inTerra){
            terraWorld.addGameObject(player);
        }else {
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
}
