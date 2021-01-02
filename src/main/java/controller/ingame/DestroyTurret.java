package controller.ingame;

import model.Field;
import model.Structure;
import view.ingame.GameWindow;
import view.ingame.PlayArea;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DestroyTurret implements ActionListener {

    private PlayArea playArea;
    private GameSession gameSession;
    private GameWindow gameWindow;

    public DestroyTurret(GameWindow gameWindow, GameSession gameSession, PlayArea playArea)
    {
        this.gameWindow = gameWindow;
        this.gameSession = gameSession;
        this.playArea = playArea;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Field selectedField = gameSession.getField(playArea.getCurrentSelected());
        if (selectedField != null)
        {
            Structure structureToDemolish = selectedField.getStructure();
            if (structureToDemolish != null)
            {
                int goldReturn = structureToDemolish.getCost() / 2;
                selectedField.demolishStructure();
                gameSession.getActualPlayer().addGold(goldReturn);
            }
            else
            {
                gameWindow.setErrorText("There is no structure to destroy!");
            }
        }
    }
}
