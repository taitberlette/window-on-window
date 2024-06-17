package States;

import WindowOnWindow.WindowOnWindow;
import Windows.BlurredWindow;
import Windows.ButtonWindow;
import Windows.TextboxWindow;

import java.awt.*;

public class GameWonState extends State{

    private TextboxWindow title;
    private ButtonWindow homeButton;
    private BlurredWindow blurredWindow;

    private double timer = 0;

    public GameWonState(StateManager stateManager) {
        super(stateManager);

        blurredWindow = new BlurredWindow();

        title = new TextboxWindow("you won!", new Point(815, 234));

        Dimension screen = WindowOnWindow.getRenderingSize();
        int buttonHeight = (int) (screen.getHeight() - 400);
        int middle = (int) (screen.getWidth() / 2);
        int buttonWidth = 344;

        homeButton = new ButtonWindow("home", "Back to home", new Point(middle - (buttonWidth / 2), buttonHeight));

        timer = 1;
    }

    public void open() {
        blurredWindow.setVisible(true);
        title.setVisible(true);
        homeButton.setVisible(true);
    }

    public void close() {
        blurredWindow.setVisible(false);
        title.setVisible(false);
        homeButton.setVisible(false);

    }

    public void update(long deltaTime) {
        if((timer -= ((double) deltaTime / 1000000000)) <= 0) {
            title.getView().getFrame().toFront();
            homeButton.getView().getFrame().toFront();
            timer = 1;
        }

        if(homeButton.wasClicked()) {
            homeButton.resetClicked();
            stateManager.saveGame();
            stateManager.clearStates();
            stateManager.pushState(StateName.STATE_HOME);

        }

        blurredWindow.update(deltaTime);
    }
}
