package States;

import Game.Game;
import Game.Levels.ActiveLevel;

public class GameState extends State {
    private Game game;

    public GameState(StateManager stateManager) {
        super(stateManager);
    }

    public void open() {

    }

    public void close() {
        game.loadLevel(ActiveLevel.NONE);
    }

    public void update(long deltaTime) {
        if(game != null) {
            game.update(deltaTime);
        }
    }

    public void loadGame(int slot) {
        game = new Game(stateManager, slot);

//        game.loadLevel((ActiveLevel.values.get (ActiveLevel.LEVEL_ONE.ordinal() + slot));
    }

    public void loadTutorial() {
        game = new Game(stateManager, -1);

        game.loadLevel(ActiveLevel.LEVEL_TUTORIAL);
    }
}
