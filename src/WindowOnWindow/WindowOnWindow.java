package WindowOnWindow;

import States.StateManager;
import Windows.ButtonWindow;
import Windows.TextboxWindow;

import java.awt.*;
import java.io.File;
import java.io.IOException;

public class WindowOnWindow {

    private static final Dimension DEFAULT_SIZE = new Dimension(1920, 1080);

    public static void main(String[] args) throws InterruptedException {

        StateManager stateManager = new StateManager();

        long time = System.nanoTime();

        while(true) {
            long currentTime = System.nanoTime();

            stateManager.update(currentTime - time);

            if(stateManager.isEmpty()) {
                break;
            }

            time = currentTime;
        }

        System.out.println("Game exit");

        System.exit(0);
    }

    public static double getScale() {
        double horizontalScaling = getMonitorSize().getWidth() / DEFAULT_SIZE.getWidth();
        double verticalScaling = getMonitorSize().getHeight() / DEFAULT_SIZE.getHeight();

        return Math.min(horizontalScaling, verticalScaling);
    }

    public static Dimension getMonitorSize() {
        return Toolkit.getDefaultToolkit().getScreenSize();
    }

    public static Dimension getRenderingSize() {
        return DEFAULT_SIZE;
    }

}