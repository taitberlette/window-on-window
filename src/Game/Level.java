package Game;

import Game.GameObjects.Entities.Player;
import Game.Worlds.EtherWorld;
import Game.Worlds.TerraWorld;

public class Level {
    private Game game;
    private Player player;
    private TerraWorld terraWorld;
    private EtherWorld etherWorld;

    public Level(Game game, Player player) {
        this.game = game;
        this.player = player;

        terraWorld = new TerraWorld(game, this);
        etherWorld = new EtherWorld(game, this);

//        terraWorld.addEntity(player);
    }

    public void update(long deltaTime) {
        terraWorld.update(deltaTime);
        etherWorld.update(deltaTime);
    }
}
