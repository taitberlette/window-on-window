package States;

import Game.Game;
import Game.ActiveLevel;

public class GameState extends State {
    private Game game;

    public GameState(StateManager stateManager) {
        super(stateManager);
    }

    public void open() {

    }

    public void close() {

    }

    public void update(long deltaTime) {
        if(game != null) {
            game.update(deltaTime);
        }
    }

    public void loadGame(int slot) {
        System.out.println("LOAD GAME");

        game = new Game(stateManager, slot);

        game.loadLevel(ActiveLevel.LEVEL_ONE);
    }
}
