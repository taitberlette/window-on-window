package Windows;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;

import Game.GameObjects.GameObject;
import Game.Worlds.TerraWorld;
import Game.Worlds.World;
import WindowOnWindow.WindowOnWindow;

public class WorldWindow extends Panel {
    private World world;
    private GameObject target;
    private Point lastPoint;

    private Dimension defaultDimension = new Dimension((1080 / 4), (1080 / 4) + TITLE_BAR_HEIGHT);

    public WorldWindow(World world) {
        super(world instanceof TerraWorld ? "Terra" : "Ether");

        this.view = new View(defaultDimension, this);

        this.world = world;
    }

    protected void draw(Graphics2D graphics2D, Dimension size) {
        Point position = view.getLocation();

        if(target != null) {
            Point targetPosition = target.getLocation();
            position = new Point((int) (targetPosition.getX() - (defaultDimension.getWidth() / 2)), (int) (targetPosition.getY() - (defaultDimension.getHeight() / 2)));
            view.setLocation(position);
        } else {
            position = view.getLocation();
        }

        double scale = WindowOnWindow.getScale();
        Dimension rendering = WindowOnWindow.getRenderingSize();

        BufferedImage image = new BufferedImage((int) rendering.getWidth(), (int) rendering.getHeight(), BufferedImage.TYPE_4BYTE_ABGR);

        this.world.draw((Graphics2D) image.getGraphics());

        graphics2D.drawImage(image, (int) (-position.getX() * scale), (int) ((-position.getY() + TITLE_BAR_HEIGHT) * scale), (int) (rendering.getWidth() * scale), (int) (rendering.getHeight() * scale), null);
    }

    public void update(long deltaTime) {
        view.update(deltaTime);
    }

    public void setTarget(GameObject target) {
        this.target = target;
    }

    public GameObject getTarget() {
        return target;
    }

    public World getWorld() {
        return world;
    }

    public Rectangle getBounds() {
       return getView().getFrame().getBounds();
    }
}
