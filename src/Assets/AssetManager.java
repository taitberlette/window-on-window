package Assets;

import WindowOnWindow.WindowOnWindow;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public class AssetManager {
    private static HashMap<String, BufferedImage> images = new HashMap<>();
    private static HashMap<String, HashMap<Double, Font>> fonts = new HashMap<>();
    public static BufferedImage getImage(String path) {
        try {
            if (images.containsKey(path)) {
                return images.get(path);
            }

            BufferedImage loadedImage = ImageIO.read(new File(path));

            images.put(path, loadedImage);

            return loadedImage;
        } catch(IOException e) {
            System.out.println("Failed to load image \"" + path + "\" due to io exception:");
            System.out.println(e.getMessage());
        }

        return null;
    }

    public static Font getFont(String path, double size) {
        try {

            HashMap<Double, Font> sizeMap;

            if(fonts.containsKey(path)) {
                sizeMap = fonts.get(path);
            } else {
                sizeMap = new HashMap<>();
            }

            if(sizeMap.containsKey(size)) {
                return sizeMap.get(size);
            }

            Font baseFont;

            // cache the base font
            if(sizeMap.containsKey(-1.0)) {
                baseFont = sizeMap.get(-1.0);
            } else {
                baseFont = Font.createFont(Font.TRUETYPE_FONT, new File(path));
                sizeMap.put(-1.0, baseFont);
            }

            Font derivedFont = baseFont.deriveFont((float) size);

            sizeMap.put(size, derivedFont);

            return derivedFont;
        } catch (IOException e) {
            System.out.println("Failed to load font \"" + path + "\" due to io exception:");
            System.out.println(e.getMessage());
        } catch (FontFormatException e) {
            System.out.println("Failed to load font \"" + path + "\" due to font format exception:");
            System.out.println(e.getMessage());
        }

        return new Font("Serif", Font.PLAIN, (int) size);
    }

    public static Font getDefaultFont(double size) {
        return getFont("res\\JetBrainsMono-Regular.ttf", size);
    }

    public static Font getTitleFont() {
        double fontScale = WindowOnWindow.getScale();
        return getDefaultFont(20 * fontScale);
    }

    public static Font getTextFont() {
        double fontScale = WindowOnWindow.getScale();
        return getDefaultFont(36 * fontScale);
    }

    public static Font getStatFont() {
        double fontScale = WindowOnWindow.getScale();
        return getDefaultFont(28 * fontScale);
    }
}
