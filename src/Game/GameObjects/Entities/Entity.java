package Game.GameObjects.Entities;

import Game.GameObjects.GameObject;
import Game.Worlds.World;

import java.awt.*;

public abstract class Entity extends GameObject {

    protected int health;
    protected int maxSpeed;
    protected double velocityX;
    protected double velocityY;
    protected double gravityAcceleration;
    protected Rectangle bounds;
    protected World world;

}
