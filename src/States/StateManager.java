package States;

import java.util.Stack;

public class StateManager {
    private HomeState homeState;
    private SlotState slotState;
    private GameState gameState;
    private PauseState pauseState;
    private ResetState resetState;

    private Stack<State> runningStates = new Stack<>();

    private State runningState;

    public StateManager() {
        homeState = new HomeState(this);
        slotState = new SlotState(this);
        gameState = new GameState(this);
        pauseState = new PauseState(this);
        resetState = new ResetState(this);

        pushState(homeState);
    }

    public void pushState(StateName stateName) {
        State state;

        switch(stateName) {
            case STATE_SLOT -> state = slotState;
            case STATE_GAME -> state = gameState;
            case STATE_PAUSE -> state = pauseState;
            case STATE_RESET -> state = resetState;
            default -> state = homeState;
        }

        pushState(state);
    }

    public void pushState(State state) {
//        runningStates.push(state);

        runningState = state;
        runningState.open();
    }

    public void popState() {
//        runningStates.pop();

        runningState.close();
        runningState = null;
    }

    public void clearStates() {
//        runningStates.clear();

//        for(State state : runningStates) {
//            state.close();
//        }

        runningState.close();
        runningState = null;
    }

    public boolean isEmpty() {
//        return runningStates.isEmpty();

        return runningState == null;
    }

    public void update(long deltaTime) {
//        for(State state : runningStates) {
//            state.update(deltaTime);
//        }

        if(runningState != null) {
            runningState.update(deltaTime);
        }
    }

    public void loadGame(int slot) {
        gameState.loadGame(slot);
    }
}
