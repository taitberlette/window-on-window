package Game.GameObjects.Objects;

import Assets.AssetManager;
import Game.GameObjects.GameObject;

import java.awt.*;

public class HiddenNumber extends GameObject {
    private int number;
    private Color colour;
    private final String FONT_PATH = "res\\Dungeon.ttf";
    private final int FONT_SIZE = 48;
    public HiddenNumber(Point point, int number, Color colour) {
        this.position = point;
        this.number = number;
        this.colour = colour;
    }

    public void draw(Graphics2D graphics2D) {
        Font font = AssetManager.getFont(FONT_PATH, FONT_SIZE);

        graphics2D.setFont(font);
        graphics2D.setColor(colour);
        graphics2D.drawString("" + number, (int) position.getX(), (int) position.getY());
    }
}