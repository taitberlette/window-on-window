package States;

import WindowOnWindow.WindowOnWindow;
import Windows.BlurredWindow;
import Windows.ButtonWindow;
import Windows.TextboxWindow;

import java.awt.*;

public class GameOverState extends State{

    private TextboxWindow title;
    private ButtonWindow yesButton;
    private ButtonWindow noButton;
    private BlurredWindow blurredWindow;

    private double timer = 0;

    public GameOverState(StateManager stateManager) {
        super(stateManager);

        blurredWindow = new BlurredWindow();

        title = new TextboxWindow("try again?", new Point(782, 234));

        Dimension screen = WindowOnWindow.getRenderingSize();
        int buttonHeight = (int) (screen.getHeight() - 400);
        int middle = (int) (screen.getWidth() / 2);
        int buttonWidth = 344;

        yesButton = new ButtonWindow("yes", "Retry level", new Point(middle + (buttonWidth / 2), buttonHeight));
        noButton = new ButtonWindow("no", "Back to home", new Point(middle - (buttonWidth / 2) - buttonWidth, buttonHeight));

        timer = 1;
    }

    public void open() {
        blurredWindow.setVisible(true);
        title.setVisible(true);
        yesButton.setVisible(true);
        noButton.setVisible(true);
    }

    public void close() {
        blurredWindow.setVisible(false);
        title.setVisible(false);
        yesButton.setVisible(false);
        noButton.setVisible(false);
    }

    public void update(long deltaTime) {
        if((timer -= ((double) deltaTime / 1000000000)) <= 0) {
            title.getView().getFrame().toFront();
            yesButton.getView().getFrame().toFront();
            noButton.getView().getFrame().toFront();
            timer = 1;
        }

        if(noButton.wasClicked()) {
            noButton.resetClicked();

            stateManager.clearStates();
            stateManager.pushState(StateName.STATE_HOME);
            stateManager.resetGame();
        }

        if(yesButton.wasClicked()) {
            yesButton.resetClicked();

            stateManager.popState();
            stateManager.reloadGame();

            System.out.println("RELOADED THE GAME");
        }

        blurredWindow.update(deltaTime);
    }
}
