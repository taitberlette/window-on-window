package Game.GameObjects.Entities.Enemies;

import Assets.AssetManager;
import Game.Worlds.World;

import java.awt.*;
import java.util.ArrayList;

public class Panzer extends Enemy {
    public Panzer(Dimension size) {
        super(size, 0, 0, 0, 0, 0);
    }


    public Panzer(ArrayList<String> lines, World world) {
        super(new Dimension(128, 64), 0, 0, 0, 0, 0, lines, world);
    }
}
