package States;

import WindowOnWindow.WindowOnWindow;
import Windows.BlurredWindow;
import Windows.ButtonWindow;
import Windows.TextboxWindow;

import java.awt.*;

public class ResetState extends State {
    private TextboxWindow title;
    private ButtonWindow yesButton;
    private ButtonWindow noButton;
    private BlurredWindow blurredWindow;

    private long frames = 0;

    public ResetState(StateManager stateManager) {
        super(stateManager);

        blurredWindow = new BlurredWindow();

        title = new TextboxWindow("are you sure", new Point(782, 234));

        Dimension screen = WindowOnWindow.getRenderingSize();
        int buttonHeight = (int) (screen.getHeight() - 400);
        int middle = (int) (screen.getWidth() / 2);
        int buttonWidth = 344;

        int padding = 64;

        yesButton = new ButtonWindow("yes", "Reset my game", new Point(middle + (buttonWidth / 2), buttonHeight));
        noButton = new ButtonWindow("no", "Back to game", new Point(middle - (buttonWidth / 2) - buttonWidth, buttonHeight));
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
        if(frames++ % 16 == 0) {
            title.getView().getFrame().toFront();
            yesButton.getView().getFrame().toFront();
            noButton.getView().getFrame().toFront();
        }

        if(noButton.wasClicked()) {
            noButton.resetClicked();

            stateManager.popState();
        }

        if(yesButton.wasClicked()) {
            yesButton.resetClicked();

            stateManager.clearStates();
            stateManager.pushState(StateName.STATE_HOME);
        }

        blurredWindow.update(deltaTime);
    }
}
