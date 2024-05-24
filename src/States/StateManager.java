package States;

import java.util.LinkedList;
import java.util.Stack;

public class StateManager {
    private HomeState homeState;
    private SlotState slotState;
    private GameState gameState;
    private PauseState pauseState;
    private ResetState resetState;

    private Stack<State> runningStates = new Stack<>();
    private LinkedList<State> statesToPush = new LinkedList<>();
    private int statesToPop = 0;

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

        statesToPush.add(state);

//        runningState = state;
//        runningState.open();
    }

    public void popState() {
        statesToPop++;

//        runningState.close();
//        runningState = null;
    }

    public void clearStates() {
        statesToPop = runningStates.size();

//        runningStates.clear();

//        for(State state : runningStates) {
//            state.close();
//        }

//        runningState.close();
//        runningState = null;
    }

    public boolean isEmpty() {
        return runningStates.isEmpty();

//        return runningState == null;
    }

    public void update(long deltaTime) {
        for(State state : runningStates) {
            state.update(deltaTime);
        }

        for(int i = 0; i < statesToPop; i++) {
            State popped = runningStates.pop();
            popped.close();
        }

        for(State stateToPush : statesToPush) {
            runningStates.push(stateToPush);
            stateToPush.open();
        }

        statesToPop = 0;
        statesToPush.clear();


//        if(runningState != null) {
//            runningState.update(deltaTime);
//        }
    }

    public void loadGame(int slot) {
        gameState.loadGame(slot);
    }

    public void loadTutorial() {
        gameState.loadTutorial();
    }
}
