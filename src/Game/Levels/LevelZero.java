package Game.Levels;

import Game.Game;
import Game.GameObjects.Entities.Enemies.HellHound;
import Game.GameObjects.Entities.Enemies.ShockSpider;
import Game.GameObjects.Entities.Player;
import Game.GameObjects.Gadgets.MovingPlatform;
import Game.GameObjects.Gadgets.MovingWall;
import Game.GameObjects.Gadgets.Target;
import Game.GameObjects.Objects.Door;
import Game.GameObjects.Objects.HiddenNumber;
import Windows.WorldWindow;

import java.awt.*;
import java.util.Random;

public class LevelZero extends Level {

    private ShockSpider shockSpider;
    private boolean unlocked = false;

    public LevelZero(Game game, Player player) {
        super(game, player, "Level_Tutorial");

        player.setLocation(new Point(246, 674));
        player.setWorld(terraWorld);
        terraWorld.addGameObject(player);

        Random random = new Random();

        int[] combination = {random.nextInt(1, 10)};

        HiddenNumber firstNumber = new HiddenNumber(new Point(1750, 280), combination[0], Color.RED);
        etherWorld.addGameObject(firstNumber);

        Door door = new Door(new Point(1789, 255), player, terraWorld, game, "Level_Tutorial", combination);
        terraWorld.addGameObject(door);

        Target target = new Target(new Point(890, 700));
        etherWorld.addGameObject(target);

        MovingPlatform platform = new MovingPlatform(new Point(1078, 932), new Point(1078, 424), target);
        etherWorld.addGameObject(platform);

        Target target1 = new Target(new Point(840, 192));
        etherWorld.addGameObject(target1);

        MovingWall wall = new MovingWall(new Point(1432, 288), new Point(1432, 480), target1);
        etherWorld.addGameObject(wall);

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
        etherWorldWindow.setLocation(new Point(911, 674));
        worldWindows.add(etherWorldWindow);

        HellHound hellHound = new HellHound();
        hellHound.setWorld(etherWorld);
        hellHound.setPlayer(player);
//        hellHound.setLocation(new Point(362, 764));
        hellHound.setLocation(new Point(562, 764));
        etherWorld.addGameObject(hellHound);

        shockSpider = new ShockSpider();
        shockSpider.setWorld(etherWorld);
        shockSpider.setPlayer(player);
        shockSpider.setLocation(new Point(70, 305));
        etherWorld.addGameObject(shockSpider);

    }

    public void update(long deltaTime) {
        super.update(deltaTime);

        if(shockSpider.getHealth() <= 0 && !unlocked) {
            player.unlockFastLegs();
            player.unlockPhotosynthesis();
            player.unlockTunnelVision();
            unlocked = true;
        }
    }
}
