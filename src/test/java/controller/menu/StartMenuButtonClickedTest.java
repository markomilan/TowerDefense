package controller.menu;

import controller.ingame.BuildTurret;
import model.menu.Difficulty;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeAll;
import view.ingame.GameWindow;
import view.resloader.GameGallery;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class StartMenuButtonClickedTest {
    static GameGallery gameGallery;
    StartMenuButtonClicked startMenuButtonClicked = new StartMenuButtonClicked(Difficulty.EASY,gameGallery);

    @BeforeAll
    static public void getGallery(){
        gameGallery = new GameGallery();
    }

    @Test
    public void testStartMenuButtonClickedC(){
        assertEquals(Difficulty.EASY, startMenuButtonClicked.getDifficulty());

    }
}
