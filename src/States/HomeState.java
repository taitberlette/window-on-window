package States;

import Windows.ButtonWindow;
import Windows.TextboxWindow;

import java.awt.*;

public class HomeState extends State {
    private TextboxWindow[] title;
    private ButtonWindow tutorialButton;
    private ButtonWindow loadButton;
    private ButtonWindow closeButton;

    private String[] titleText = {"window", "on", "window"};

    public HomeState(StateManager stateManager) {
        super(stateManager);

        title = new TextboxWindow[titleText.length];


        int offset = 0;

        for(int i = 0; i < title.length; i++) {
            title[i] = new TextboxWindow(titleText[i], new Point(700 + offset, 215 + (i * 48)));
            offset += (title[i].getWidth() - 20);
        }

        int width = 1920;
        int middle = width / 2;
        int buttonWidth = 344;

        int padding = 128;


        tutorialButton = new ButtonWindow("tutorial", "Learn how to play", new Point(middle - (buttonWidth / 2) - padding - buttonWidth, 625));
        loadButton = new ButtonWindow("load", "Select game slot", new Point(middle - (buttonWidth / 2), 625));
        closeButton = new ButtonWindow("close", "Close the game", new Point(middle + (buttonWidth / 2) + padding, 625));
    }

    public void open() {
        for(int i = 0; i < title.length; i++) {
            title[i].setVisible(true);
        }

        tutorialButton.setVisible(true);
        loadButton.setVisible(true);
        closeButton.setVisible(true);
    }

    public void close() {
        for(int i = 0; i < title.length; i++) {
            title[i].setVisible(false);
        }

        tutorialButton.setVisible(false);
        loadButton.setVisible(false);
        closeButton.setVisible(false);
    }

    public void update(long deltaTime) {
        if(tutorialButton.wasClicked()) {
            tutorialButton.resetClicked();

            System.out.println("Load Tutorial");
        }

        if(loadButton.wasClicked()) {
            loadButton.resetClicked();

            stateManager.popState();
            stateManager.pushState(StateName.STATE_SLOT);
        }

        if(closeButton.wasClicked()) {
            closeButton.resetClicked();

            stateManager.clearStates();
        }

    }
}
