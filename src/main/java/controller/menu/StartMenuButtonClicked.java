package controller.menu;

import model.menu.Difficulty;
import view.ingame.GameWindow;
import view.resloader.GameGallery;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StartMenuButtonClicked implements ActionListener {
    private Difficulty difficulty;
    private GameGallery gameGallery;

    public StartMenuButtonClicked(Difficulty difficulty, GameGallery gameGallery)
    {
        this.difficulty = difficulty;
        this.gameGallery = gameGallery;
    }

    public Difficulty getDifficulty() {
        return difficulty;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        GameWindow gameWindow = new GameWindow(difficulty, gameGallery);
        //LoadingWindow loadWindow = new LoadingWindow();
    }
}
