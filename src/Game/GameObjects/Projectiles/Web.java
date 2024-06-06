package Game.GameObjects.Projectiles;

import Assets.AssetManager;
import Game.Worlds.World;

import java.awt.*;
import java.util.ArrayList;

public class Web extends Projectile {
    public Web(Dimension size) {
        super(size);
    }
    public Web(ArrayList<String> lines, World world) {
        super(lines, new Dimension(16, 16), world);
    }
}
