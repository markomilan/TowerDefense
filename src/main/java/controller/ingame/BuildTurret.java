package controller.ingame;

import model.*;
import view.ingame.PlayArea;
import view.ingame.GameWindow;
import view.resloader.GameGallery;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BuildTurret implements ActionListener {
    private GameWindow gameWindow;
    private PlayArea playArea;
    private int turretType;
    private GameGallery gameGallery;

    public BuildTurret(GameWindow gameWindow, PlayArea playArea, int turretType, GameGallery gameGallery)
    {
        this.gameWindow = gameWindow;
        this.turretType = turretType;
        this.playArea = playArea;
        this.gameGallery = gameGallery;
    }

    public int getTurretType() {
        return turretType;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Field currentField = gameWindow.getGame().getField(playArea.getCurrentSelected());
        if (currentField == null)
            return;
        Structure structure = currentField.getStructure();
        if (structure != null)
        {
            // 1: sniper 2: shotgun 3: MachineGun 4: Generator
            if(turretType == structure.getId()){
                Player p = gameWindow.getGame().getActualPlayer();
                if (p.getGold() >= structure.getImproveCost())
                {
                    if(!structure.isImproved()){
                        p.addGold(-structure.getImproveCost());
                        structure.improveStructure();
                        if(structure.getId() == 4){
                            gameWindow.getGame().setGeneratorCounter(gameWindow.getGame().getGeneratorCounter()-1);
                            gameWindow.getGame().setImprovedGeneratorCounter(gameWindow.getGame().getImprovedGeneratorCounter()+1);
                        }
                    }else{
                        gameWindow.setErrorText("You've already improved this building");
                    }
                }else{
                    gameWindow.setErrorText("You don't have enough gold to improve");
                }
            }else{
                gameWindow.setErrorText("There is already an other type turret here.");
            }
        }else{
            switch (turretType) // 1: sniper 2: shotgun 3: MachineGun 4: Generator
            {
                case 1:
                    structure = new SniperTurret(gameGallery);
                    break;
                case 2:
                    structure = new ShotgunTurret(gameGallery);
                    break;
                case 4:
                    structure = new Generator(gameGallery);
                    break;
                default:
                    structure = new MachineGunTurret(gameGallery);
            }
            Player p = gameWindow.getGame().getActualPlayer();
            if (p.getGold() >= structure.getCost())
            {
                if (currentField.getEnemyCount() == 0)
                {
                    currentField.build(structure);
                    p.addGold(-structure.getCost());
                    if (turretType == 4) {
                        gameWindow.getGame().setGeneratorCounter(gameWindow.getGame().getGeneratorCounter() + 1);
                    }
                }
                else{
                    gameWindow.setErrorText("You can't build on enemies!");
                }
                    playArea.repaint();
            }else{
                gameWindow.setErrorText("You don't have enough gold to build");
            }
        }

    }
}
