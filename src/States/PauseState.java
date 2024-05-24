package States;

import WindowOnWindow.WindowOnWindow;
import Windows.ButtonWindow;

import java.awt.*;

public class PauseState extends State {
    private ButtonWindow[] checkpoints;
    private ButtonWindow home;
    private ButtonWindow resume;
    private ButtonWindow reset;
    public PauseState(StateManager stateManager) {
        super(stateManager);

        Dimension screen = WindowOnWindow.getMonitorSize();
        int buttonHeight = (int) (screen.getHeight() - 400);
        int middle = (int) (screen.getWidth() / 2);
        int buttonWidth = 344;

        int padding = 64;

        reset = new ButtonWindow("reset", "Reset your save", new Point(middle - (buttonWidth / 2) - padding - buttonWidth, buttonHeight));
        resume = new ButtonWindow("resume", "Resume the game", new Point(middle - (buttonWidth / 2), buttonHeight));
        home = new ButtonWindow("home", "Return to home", new Point(middle + (buttonWidth / 2) + padding, buttonHeight));

    }

    public void open() {
        for(int i = 0; i < 3; i++){
//            checkpoints[i].setVisible(true);
        }

        resume.setVisible(true);
        home.setVisible(true);
    }

    public void close() {
        resume.setVisible(false);
        home.setVisible(false);
    }

    public void update(long deltaTime) {
        if(resume.wasClicked()) {
            resume.resetClicked();

            stateManager.popState();
        }

        if(home.wasClicked()) {
            home.resetClicked();

            stateManager.clearStates();
            stateManager.pushState(StateName.STATE_HOME);
        }
    }
}
