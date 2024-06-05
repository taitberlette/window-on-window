package Game.Levels;

import Game.Game;
import Game.GameObjects.Entities.Player;

import java.util.ArrayList;

public class LevelThree extends Level {
    public LevelThree(Game game, Player player) {
        super(game, player, "Level_Three");
    }

    public LevelThree(ArrayList<String> lines, Game game, Player player) {
        super(lines, game, player, "Level_Three");
        terraWorld.addGameObject(player);
    }
}
