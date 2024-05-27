package WindowOnWindow;

import States.StateManager;
import Windows.ButtonWindow;
import Windows.TextboxWindow;

import java.awt.*;
import java.io.File;
import java.io.IOException;

public class WindowOnWindow {

    private static Font font;
    private static Font titleFont;
    private static Font textFont;

    private static final Dimension DEFAULT_SIZE = new Dimension(1920, 1080);
    private static final String FONT_PATH = "res/JetBrainsMono-Regular.ttf";

    public static void main(String[] args) throws InterruptedException {
        loadFont();

        StateManager stateManager = new StateManager();

        long time = System.nanoTime();

        while(true) {
            long currentTime = System.nanoTime();

            stateManager.update(currentTime - time);

            System.out.println(currentTime - time);

            if(stateManager.isEmpty()) {
                break;
            }

            time = currentTime;
        }

        System.out.println("Game exit");

        System.exit(0);
    }

    private static void loadFont() {

        double fontScale = getScale();

        try {
            font = Font.createFont(Font.TRUETYPE_FONT, new File(FONT_PATH));
            titleFont = font.deriveFont((float) (20f * fontScale));
            textFont = font.deriveFont((float) (36f * fontScale));
            GraphicsEnvironment graphicsEnvironment = GraphicsEnvironment.getLocalGraphicsEnvironment();
            graphicsEnvironment.registerFont(font);
        } catch (IOException ioException) {
            System.err.println("There was an error loading the font from resources!");
            System.err.println("Using Serif font instead");
            System.err.println(ioException.getMessage());
            font = new Font("Serif", Font.PLAIN, 18);
        } catch (FontFormatException fontFormatException) {
            System.err.println("The font format is invalid!");
            System.err.println(fontFormatException.getMessage());
            System.err.println("Using Serif font instead");
            font = new Font("Serif", Font.PLAIN, 18);
        }
    }

    public static Font getDefaultFont() {
        return font;
    }

    public static Font getTitleFont() {
        return titleFont;
    }

    public static Font getTextFont() {
        return textFont;
    }

    public static double getScale() {
        double horizontalScaling = getMonitorSize().getWidth() / DEFAULT_SIZE.getWidth();
        double verticalScaling = getMonitorSize().getHeight() / DEFAULT_SIZE.getHeight();

        return Math.min(horizontalScaling, verticalScaling);
    }

    public static Dimension getMonitorSize() {
        return Toolkit.getDefaultToolkit().getScreenSize();

//        GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
//
//        int width = gd.getDisplayMode().getWidth();
//        int height = gd.getDisplayMode().getHeight();
//
//        return new Dimension(width, height);
    }

    public static Dimension getRenderingSize() {
        return DEFAULT_SIZE;
    }

}