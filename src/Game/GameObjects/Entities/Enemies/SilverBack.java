package Game.GameObjects.Entities.Enemies;

import Game.Worlds.World;
import Game.GameObjects.Entities.Player;

import java.awt.*;
import java.util.ArrayList;

public class SilverBack extends Enemy {
//    public SilverBack(Dimension size) {
//        super(size, 0, 0, 0, 0, 0);
//    }

    public SilverBack(ArrayList<String> lines, Player player, World world) {
        super(new Dimension(128, 64), 0, 0, 0, 0, 0, lines, player, world);
    }
}
