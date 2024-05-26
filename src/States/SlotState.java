package States;

import WindowOnWindow.WindowOnWindow;
import Windows.ButtonWindow;
import Windows.TextboxWindow;

import java.awt.*;

public class SlotState extends State {
    private TextboxWindow[] title;
    private ButtonWindow[] games;
    private ButtonWindow homeButton;

    private String[] titleText = {"window", "on", "window"};
    private String[] gameNames = {"alpha", "beta", "gamma"};
    private String[] numberNames = {"one", "two", "three"};

    public SlotState(StateManager stateManager) {
        super(stateManager);

        title = new TextboxWindow[titleText.length];



        int offset = 0;

        for(int i = 0; i < title.length; i++) {
            title[i] = new TextboxWindow(titleText[i], new Point(700 + offset, 215 + (i * 48)));
            offset += (title[i].getWidth() - 20);
        }

        Dimension screen = WindowOnWindow.getRenderingSize();
        int buttonHeight = (int) (screen.getHeight() - 400);
        int middle = (int) (screen.getWidth() / 2);
        int buttonWidth = 344;
        int padding = 64;

        games = new ButtonWindow[gameNames.length];

        for(int i = 0; i < games.length; i++) {
            games[i] = new ButtonWindow(gameNames[i], String.format("Game slot %s", numberNames[i]), new Point((middle - (buttonWidth / 2) - padding - buttonWidth) + (i * (padding + buttonWidth)), buttonHeight));
        }

        homeButton = new ButtonWindow("home", "Back to main menu", new Point((middle - (buttonWidth / 2)), (int) (screen.getHeight() - 200)));
    }

    public void open() {
        for(int i = 0; i < title.length; i++) {
            title[i].setVisible(true);
        }

        for(int i = 0; i < games.length; i++) {
            games[i].setVisible(true);
        }

        homeButton.setVisible(true);
    }

    public void close() {
        for(int i = 0; i < title.length; i++) {
            title[i].setVisible(false);
        }

        for(int i = 0; i < games.length; i++) {
            games[i].setVisible(false);
        }

        homeButton.setVisible(false);
    }

    public void update(long deltaTime) {
        if(homeButton.wasClicked()) {
            homeButton.resetClicked();

            stateManager.clearStates();
            stateManager.pushState(StateName.STATE_HOME);
            return;
        }

        for(int i = 0; i < games.length; i++) {
            if(games[i].wasClicked()) {
                games[i].resetClicked();

                stateManager.clearStates();
                stateManager.pushState(StateName.STATE_GAME);

                stateManager.loadGame(i);
            }
        }
    }
}
