package Game.Worlds;

import Game.Game;
import Game.GameObjects.Entities.Player;
import Game.Level;
import Windows.WorldWindow;

public class TerraWorld extends World {
    public TerraWorld(Game game, Level level) {
        super(game, level);

        WorldWindow worldWindow = new WorldWindow(this);
        Player player = new Player();
        worldWindow.setTarget(player);
        worldWindow.setVisible(true);
        worldWindows.add(worldWindow);

        this.addEntity(player);
    }
}
