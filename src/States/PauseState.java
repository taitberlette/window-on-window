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

    }

    public void open() {
        for(int i = 0; i < 3; i++){
            checkpoints[i].setVisible(true);
        }
    }

    public void close() {

    }

    public void update(long deltaTime) {

    }
}
