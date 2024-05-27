package Game.GameObjects.Weapons;

import Game.GameObjects.GameObject;
import Game.Worlds.World;

import java.awt.*;

public abstract class Weapon extends GameObject {
    protected int damage;
    protected int cooldown;
    protected World world;
    protected int speed;

    public Weapon(int damage, int cooldown, int speed) {
        this.damage = damage;
        this.cooldown = cooldown;
        this.speed = speed;
    }

    public void setWorld(World world) {
        this.world = world;
    }
}
