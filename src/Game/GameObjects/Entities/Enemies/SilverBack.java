package Game.GameObjects.Entities.Enemies;

import Game.Worlds.World;

import java.awt.*;
import java.util.ArrayList;

public class SilverBack extends Enemy {
    public SilverBack(Dimension size) {
        super(size, 0, 0, 0, 0, 0);
    }

    public SilverBack(ArrayList<String> lines, World world) {
        super(new Dimension(128, 64), 0, 0, 0, 0, 0, lines, world);
    }
}
