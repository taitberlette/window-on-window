package Game.GameObjects;

import java.awt.*;

public class GameObject {
    private Point position;

    public void update(long deltaTime) {

    }

    public void setLocation(Point position) {
        this.position = position;
    }

    public Point getLocation() {
        return position;
    }
}
