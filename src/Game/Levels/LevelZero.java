package Game.Levels;

import Game.Game;
import Game.GameObjects.Entities.Enemies.HellHound;
import Game.GameObjects.Entities.Player;
import Windows.WorldWindow;

import java.awt.*;

public class LevelZero extends Level {
    public LevelZero(Game game, Player player) {
        super(game, player);

        player.setLocation(new Point(246, 674));
        player.setWorld(terraWorld);
        terraWorld.addEntity(player);

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
    }

    public void update(long deltaTime) {
        super.update(deltaTime);
    }
}
