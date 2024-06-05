package Game.GameObjects.Weapons.Melee;

import Assets.AssetManager;
import Game.Game;
import Game.GameObjects.Entities.Enemies.Enemy;
import Game.GameObjects.Entities.Entity;
import Game.GameObjects.GameObject;
import Game.GameObjects.Objects.Tree;
import Game.Utilities.HorizontalDirection;
import Game.Worlds.World;

import javax.imageio.ImageIO;
import javax.swing.plaf.basic.BasicSplitPaneUI;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.File;
import java.util.ArrayList;

public class PocketKnife extends Melee {
    private BufferedImage pocketKnifeImage;

    private long startAttack = 0;
    private HorizontalDirection attackDirection;
    private Point offset = new Point(0, 0);
    private boolean attacking = false;
    private boolean hitSomething = false;

    private final long ATTACK_TIME = 1000;

    public PocketKnife() {
        super(20, 2, 0,52);

        pocketKnifeImage = AssetManager.getImage("res\\Weapons and Attacks\\PocketKnife.png");
    }

    public void update(long deltaTime) {
        super.update(deltaTime);

        long currentTime = System.currentTimeMillis();
        long progress = currentTime - startAttack;
        attacking = progress < ATTACK_TIME;

        if(attacking) {
            int multiplier = attackDirection == HorizontalDirection.LEFT ? -1 : 1;

            if(progress < ATTACK_TIME / 2) {
                offset.setLocation(multiplier * (reach * ((double) progress / ATTACK_TIME / 2)), 0);
            } else {
                offset.setLocation(multiplier * (reach - (reach * ((double) ((progress + ATTACK_TIME)) / ATTACK_TIME / 2))), 0);
            }

            if(!hitSomething) {
                int endOfKnife = (int) (position.getX() + offset.getX() + (attackDirection == HorizontalDirection.RIGHT ? 76 : - 76) - 1);
                Point knife = new Point(endOfKnife, (int) position.getY());

                ArrayList<Entity> entities = world.findEntities(knife);

                for(Entity entity : entities) {
                    if(entity instanceof Enemy enemy) {
                        enemy.hurt(damage);
                        hitSomething = true;
                        break;
                    }
                }

                ArrayList<GameObject> gameObjects = world.findGameObjects(knife);
                for(GameObject gameObject : gameObjects) {
                    if(gameObject instanceof Tree tree) {
                        tree.dropApple();
                        break;
                    }
                }
            }
        }
    }

    public void draw(Graphics2D graphics2D) {
        if(!attacking) return;

        int xOffset = attackDirection == HorizontalDirection.RIGHT ? 32 : 0;

        if(attackDirection == HorizontalDirection.RIGHT) {
            xOffset += 96 / 2;
        } else {
            xOffset -= (32 + (96 / 2));
        }


        graphics2D.drawImage(pocketKnifeImage, (int) (position.getX() + offset.getX() + xOffset), (int) position.getY() - 16, 32 * (attackDirection == HorizontalDirection.RIGHT ? -1 : 1), 32, null);

//        graphics2D.setColor(Color.BLUE);
//        graphics2D.fillRect((int) (position.getX() + offset.getX() + (attackDirection == HorizontalDirection.RIGHT ? 76 : - 76) - 1), (int) (position.getY() - 1), 3, 3);
    }

    public void attack(HorizontalDirection attackDirection) {
        if(attackDirection == null || attackDirection == HorizontalDirection.NONE) return;
        this.attackDirection = attackDirection;
        startAttack = System.currentTimeMillis();
        hitSomething = false;
    }

    public boolean checkCooldown() {
        return !attacking;
    }

    public void cancel() {
        startAttack = 0;
    }
}
