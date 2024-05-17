package States;

public abstract class State {
    protected StateManager stateManager;

    public State(StateManager stateManager) {
        this.stateManager = stateManager;
    }

    public abstract void open();
    public abstract void close();
    public abstract void update(long deltaTime);
}
