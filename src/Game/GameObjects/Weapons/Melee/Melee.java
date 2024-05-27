package Game.GameObjects.Weapons.Melee;

import Game.GameObjects.Weapons.Weapon;
import Game.Worlds.World;

public abstract class Melee extends Weapon {
    protected int reach;

    public Melee(int damage, int cooldown, int speed, int reach) {
        super(damage, cooldown, speed);
        this.reach = reach;
    }
}
