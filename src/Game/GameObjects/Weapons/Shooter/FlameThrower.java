package Game.GameObjects.Weapons.Shooter;

import Game.GameObjects.Projectiles.Flame;
import Game.GameObjects.Projectiles.Lightning;

public class FlameThrower extends Shooter {
    public FlameThrower() {
        super((Class) Flame.class, 0, 0, 0);
    }
}
