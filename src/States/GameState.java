package States;

import Game.Game;
import Game.Levels.ActiveLevel;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

public class GameState extends State {
    private Game game;
    private String[] paths = {"Alpha", "Beta", "Gamma"};
    private final static String FILE_PATH_TEMPLATE = "saves\\%s.wow";
    private int slot;

    public GameState(StateManager stateManager) {
        super(stateManager);
    }

    public void open() {

    }

    public void close() {
        game.kill();
        game = null;
    }

    public void update(long deltaTime) {
        if(game != null) {
            game.update(deltaTime);
        }
    }

    public void save() {
        System.out.println("SAVE THE GAME");

        if(game.savingEnabled()) {
            String path = String.format(FILE_PATH_TEMPLATE, paths[slot]);

            FileWriter fileWriter;

            try {
                Files.createDirectories(Paths.get("saves\\"));
                fileWriter = new FileWriter(path);
            } catch (IOException e) {
                System.out.println("FAILED TO OPEN \"" + path + "\" TO SAVE THE GAME");
                return;
            }

            PrintWriter printWriter = new PrintWriter(fileWriter);

            printWriter.println(game.encode());

            printWriter.close();
        }
    }

    public void loadGame(int slot) {
        this.slot = slot;

        if(slot >= 0) {
            String path = String.format(FILE_PATH_TEMPLATE, paths[slot]);

            FileReader fileReader;

            try {
                fileReader = new FileReader(path);
            } catch (IOException e) {
                System.out.println("FAILED TO OPEN \"" + path + "\" TO LOAD THE GAME");
                game = new Game(stateManager, slot);
                return;
            }

            BufferedReader bufferedReader = new BufferedReader(fileReader);
            ArrayList<String> lines = new ArrayList<>();

            try {
                String line;
                while((line = bufferedReader.readLine()) != null) {
                    lines.add(line);
                }

                game = new Game(lines, stateManager, slot);
            } catch (IOException e) {
                game = new Game(stateManager, slot);
                return;
            }
        }
    }

    public void loadTutorial() {
        game = new Game(stateManager, -1);
        slot = -1;
        game.loadLevel(ActiveLevel.LEVEL_TUTORIAL);
    }

    public void loadLevel(int level) {
        game.loadLevel(ActiveLevel.values()[level]);
    }

    public void loadCheckpoint(int level) {
        game.loadCheckpoint(ActiveLevel.values()[level]);
    }

    public void reset() {
        if(slot >= 0) {
            System.out.println("DELETE");
            String path = String.format(FILE_PATH_TEMPLATE, paths[slot]);

            try {
                Files.deleteIfExists(Path.of(path));
            } catch (IOException e) {
                System.out.println("FAILED TO DELETE THE SAVE FILE");
            }
        }
    }

    public void reloadGame(){
        reset();
        close();
        if (slot >= 0){
            loadGame(slot);
        } else {
            loadTutorial();
        }

    }

    public void respawn() {
        game.respawn();
    }
}
