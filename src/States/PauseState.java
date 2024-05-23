package States;

import Windows.ButtonWindow;

import java.awt.*;

public class PauseState extends State {
    private ButtonWindow[] checkpoints;
    private ButtonWindow home;
    private ButtonWindow resume;
    private ButtonWindow reset;
    public PauseState(StateManager stateManager) {
        super(stateManager);

        int width = 1920;
        int middle = width / 2;
        int buttonWidth = 344;

        int padding = 128;

        reset = new ButtonWindow("reset", "Reset your save", new Point(middle - (buttonWidth / 2) - padding - buttonWidth, 625));
        resume = new ButtonWindow("resume", "Resume the game", new Point(middle - (buttonWidth / 2), 625));
        home = new ButtonWindow("home", "Return to home", new Point(middle + (buttonWidth / 2) + padding, 625));

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
