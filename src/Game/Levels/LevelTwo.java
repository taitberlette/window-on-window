package Game.Levels;

import Game.Game;
import Game.GameObjects.Entities.Player;

import java.util.ArrayList;

public class LevelTwo extends Level {
    public LevelTwo(Game game, Player player) {
        super(game, player, "Level_Two");
    }

    public LevelTwo(ArrayList<String> lines, Game game, Player player) {
        super(lines, game, player, "Level_Two");
        terraWorld.addGameObject(player);
    }
}
