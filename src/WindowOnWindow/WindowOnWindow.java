package WindowOnWindow;

import Windows.ButtonWindow;
import Windows.TextboxWindow;

import java.awt.*;
import java.io.File;
import java.io.IOException;

public class WindowOnWindow {

    private static Font font;
    private static Font titleFont;
    private static Font textFont;
    private static final String FONT_PATH = "res/JetBrainsMono-Regular.ttf";

    public static void main(String[] args) throws InterruptedException {
        loadFont();

        ButtonWindow textboxWindow = new ButtonWindow("Text", "This is the title");

        while(true) {
            textboxWindow.update(0);

            if(textboxWindow.wasClicked()) {
                textboxWindow.resetClicked();
                System.out.println("CLICKEd");
            }

            Thread.sleep(10);
        }
    }

    private static void loadFont() {
        try {
            font = Font.createFont(Font.TRUETYPE_FONT, new File(FONT_PATH));
            titleFont = font.deriveFont(20f);
            textFont = font.deriveFont(36f);
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
}