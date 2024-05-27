package Game.Levels;

import Game.Game;
import Game.GameObjects.Entities.Enemies.HellHound;
import Game.GameObjects.Entities.Player;
import Game.GameObjects.Objects.Door;
import Windows.NumberCodeWindow;
import Windows.WorldWindow;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.Random;

public class LevelZero extends Level {
    public LevelZero(Game game, Player player) {
        super(game, player, "Level_Tutorial");

        player.setLocation(new Point(246, 674));
        player.setWorld(terraWorld);
        terraWorld.addGameObject(player);

        Random random = new Random();

        int[] combination = {random.nextInt(0, 10)};

        Door door = new Door(new Point(1789, 255), player, terraWorld, game, "Level_Tutorial", combination);
        terraWorld.addGameObject(door);

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
        hellHound.setLocation(new Point(562, 764));
        etherWorld.addGameObject(hellHound);

    }

    public void update(long deltaTime) {
        super.update(deltaTime);
    }
}
