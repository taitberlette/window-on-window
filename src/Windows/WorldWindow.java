package Windows;

import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
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

    private Dimension defaultDimension = new Dimension((1080 ), (1080 ) + TITLE_BAR_HEIGHT);

    public WorldWindow(World world) {
        super(world instanceof TerraWorld ? "Terra" : "Ether");

        this.view = new View(defaultDimension, this);

        this.world = world;

        this.view.getFrame().addComponentListener(new ComponentAdapter()
        {

            @Override
            public void componentMoved(ComponentEvent e)
            {
                view.update(0);
            }
        });
    }

    protected void draw(Graphics2D graphics2D, Dimension size) {
        Point position = view.getLocation();

        if(target != null) {
            Point targetPosition = target.getLocation();
            position = new Point((int) (targetPosition.getX() - (defaultDimension.getWidth() / 2)), (int) (targetPosition.getY() - (defaultDimension.getHeight() / 2)));
        } else {
            position = view.getLocation();
        }

        double scale = WindowOnWindow.getScale();
        Dimension rendering = WindowOnWindow.getRenderingSize();

        BufferedImage image = new BufferedImage((int) rendering.getWidth(), (int) rendering.getHeight(), BufferedImage.TYPE_3BYTE_BGR);

        Graphics2D worldGraphics2D = (Graphics2D) image.getGraphics();
        worldGraphics2D.setClip((int) (position.getX() * scale), (int) ((position.getY()) * scale), (int) (rendering.getWidth() * scale), (int) (rendering.getHeight() * scale));
        this.world.draw(worldGraphics2D);

        graphics2D.drawImage(image, (int) (-position.getX() * scale), (int) ((-position.getY() + TITLE_BAR_HEIGHT) * scale), (int) (rendering.getWidth() * scale), (int) (rendering.getHeight() * scale), null);
    }

    public void update(long deltaTime) {
        if(target != null) {
            Point targetPosition = target.getLocation();
            Point position = new Point((int) (targetPosition.getX() - (defaultDimension.getWidth() / 2)), (int) (targetPosition.getY() - (defaultDimension.getHeight() / 2)));
            if(position.equals(view.getLocation())) {
                view.update(deltaTime);
            } else {
                view.setLocation(position);
            }
        } else {
            view.update(deltaTime);
        }
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
