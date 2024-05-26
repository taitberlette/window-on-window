package Windows;

import WindowOnWindow.WindowOnWindow;

import java.awt.*;
import java.util.Arrays;

public class NumberCodeWindow extends Panel {
    private int[] combination;
    private int[] typedNumbers;
    private int nextNumber;

    private Dimension defaultDimension = new Dimension(344, 128);

    public NumberCodeWindow() {
        super("Unlock");

        this.view = new View(defaultDimension, this);
    }

    public void update(long deltaTime) {
        view.update(deltaTime);
    }

    public void setCombination(int[] combination) {
        this.combination = combination;
        this.typedNumbers = new int[combination.length];
        Arrays.fill(typedNumbers, 0);
    }

    public void typeNumber(int number) {
        if(nextNumber >= typedNumbers.length) return;
        typedNumbers[nextNumber++] = number;
    }

    public void clear() {
        Arrays.fill(typedNumbers, 0);
        nextNumber = 0;
    }

    public boolean isCorrect() {
        return Arrays.equals(combination, typedNumbers);
    }

    protected void draw(Graphics2D graphics2D, Dimension size) {
        double scale = WindowOnWindow.getScale();

        Font font = WindowOnWindow.getTextFont();

        graphics2D.setColor(Color.BLACK);
        graphics2D.setFont(font);

        FontMetrics fontMetrics = graphics2D.getFontMetrics(font);

        int charWidth = (int) (fontMetrics.charWidth('0'));
        int start = (int) ((size.getWidth() - (typedNumbers.length * charWidth)) / 2);

        for(int i = 0; i < typedNumbers.length; i++) {

            if(isCorrect()) {
                graphics2D.setColor(Color.GREEN);
            } else {
                graphics2D.setColor(i < nextNumber ? Color.BLACK : Color.GRAY);
            }

            graphics2D.drawString("" + typedNumbers[i], start + (i * charWidth), (int) ((int) (((size.getHeight()) - ((TITLE_BAR_HEIGHT * scale) / 2)) / 2) + (TITLE_BAR_HEIGHT * scale)));

            if(i == nextNumber) {
                graphics2D.setColor(Color.BLACK);
                graphics2D.fillRect(start + (i * charWidth) , (int) ((int) ((int) (((size.getHeight()) - ((TITLE_BAR_HEIGHT * scale) / 2)) / 2) + (TITLE_BAR_HEIGHT * scale)) + (5 * scale)), charWidth, (int) (5 * scale));
            }
        }
    }
}
