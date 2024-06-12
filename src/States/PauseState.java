package States;

import Game.Levels.ActiveLevel;
import WindowOnWindow.WindowOnWindow;
import Windows.ButtonWindow;
import Windows.TextboxWindow;

import java.awt.*;

public class PauseState extends State {
    private ButtonWindow[] checkpoints;
    private ButtonWindow home;
    private ButtonWindow resume;
    private ButtonWindow reset;
    private int visibleCheckpoints = 0;
    public PauseState(StateManager stateManager) {
        super(stateManager);

        Dimension screen = WindowOnWindow.getRenderingSize();
        int buttonHeight = (int) (screen.getHeight() - 400);
        int middle = (int) (screen.getWidth() / 2);
        int buttonWidth = 344;

        int padding = 64;

        reset = new ButtonWindow("reset", "Reset your save", new Point(middle - (buttonWidth / 2) - padding - buttonWidth, buttonHeight));
        resume = new ButtonWindow("resume", "Resume the game", new Point(middle - (buttonWidth / 2), buttonHeight));
        home = new ButtonWindow("home", "Return to home", new Point(middle + (buttonWidth / 2) + padding, buttonHeight));


        checkpoints = new ButtonWindow[ActiveLevel.COUNT_LEVEL.ordinal()];

        for(int i = 0; i < checkpoints.length; i++) {
            checkpoints[i] = new ButtonWindow("level " + (i + 1), "Return to level " + (i + 1),  new Point((middle - (buttonWidth / 2) - padding - buttonWidth) + (i * (padding + buttonWidth)), buttonHeight - 250));
        }
    }

    public void open() {
        for(int i = 0; i < visibleCheckpoints && i < checkpoints.length; i++){
            checkpoints[i].setVisible(true);
        }

        reset.setVisible(true);
        resume.setVisible(true);
        home.setVisible(true);
    }

    public void close() {
        for(int i = 0; i < checkpoints.length; i++){
            checkpoints[i].setVisible(false);
        }

        reset.setVisible(false);
        resume.setVisible(false);
        home.setVisible(false);
    }

    public void update(long deltaTime) {
        if(reset.wasClicked()) {
            reset.resetClicked();

            stateManager.pushState(StateName.STATE_RESET);
        }

        if(resume.wasClicked()) {
            resume.resetClicked();

            stateManager.popState();
        }

        if(home.wasClicked()) {
            home.resetClicked();

            stateManager.clearStates();
            stateManager.pushState(StateName.STATE_HOME);
            stateManager.saveGame();
        }

        for(int i = 0; i < checkpoints.length; i++) {
            if(checkpoints[i].wasClicked()) {
                checkpoints[i].resetClicked();

                stateManager.popState();

                stateManager.loadCheckpoint(i);
            }
        }
    }

    public void setVisibleCheckpoints(int visibleCheckpoints) {
        this.visibleCheckpoints = visibleCheckpoints;
    }
}
