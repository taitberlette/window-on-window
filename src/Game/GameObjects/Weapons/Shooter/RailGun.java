package Game.GameObjects.Weapons.Shooter;

import Game.GameObjects.Projectiles.Lightning;
import Game.GameObjects.Projectiles.Projectile;

public class RailGun extends Shooter {
    public RailGun() {
        super((Class) Lightning.class, 0, 0, 0);
    }
}
