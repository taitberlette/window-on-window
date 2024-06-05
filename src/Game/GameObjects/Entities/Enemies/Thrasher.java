package Game.GameObjects.Entities.Enemies;

import Game.Worlds.World;

import java.awt.*;
import java.util.ArrayList;

public class Thrasher extends Enemy {
    public Thrasher(Dimension size) {
        super(size, 0, 0, 0, 0, 0);
    }

    public Thrasher(ArrayList<String> lines, World world) {
        super(new Dimension(128, 64), 0, 0, 0, 0, 0, lines, world);
    }
}
