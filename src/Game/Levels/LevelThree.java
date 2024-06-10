package Game.Levels;

import Game.Game;
import Game.GameObjects.Entities.Player;
import Game.GameObjects.Objects.Door;
import Game.GameObjects.Objects.HiddenNumber;

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
