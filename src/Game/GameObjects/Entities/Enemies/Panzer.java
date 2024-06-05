package Game.GameObjects.Entities.Enemies;

import Assets.AssetManager;
import Game.Worlds.World;
import Game.GameObjects.Entities.Player;

import java.awt.*;
import java.util.ArrayList;

public class Panzer extends Enemy {
//    public Panzer(Dimension size) {
//        super(size, 0, 0, 0, 0, 0);
//    }


    public Panzer(ArrayList<String> lines, Player player, World world) {
        super(new Dimension(128, 64), 0, 0, 0, 0, 0, lines, player, world);
    }
}
