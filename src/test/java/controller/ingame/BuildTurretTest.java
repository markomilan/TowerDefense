package controller.ingame;

import model.Generator;
import model.*;
import model.menu.Difficulty;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import view.ingame.PlayArea;
import view.ingame.GameWindow;
import view.resloader.GameGallery;

import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

public class BuildTurretTest {
    static boolean isDisplayAttached;
    static GameGallery gameGallery;
    GameWindow gameWindow;
    int playerGoldBefore;

    private void clickButton(Pair location, int turretType)
    {
        gameWindow.getPlayArea().setCurrentSelected(location);
        BuildTurret buildTurr = new BuildTurret(gameWindow, gameWindow.getPlayArea(), turretType, gameGallery);
        ActionEvent ae = new ActionEvent(buildTurr, ActionEvent.ACTION_PERFORMED, "");
        buildTurr.actionPerformed(ae);
    }

    private void setField(Pair location, int turretType, boolean improved)
    {
        Field field = new Field();
        Structure structure;
        switch (turretType){
            case 1:
                structure = new SniperTurret(gameGallery);
                break;
            case 2:
                structure = new ShotgunTurret(gameGallery);
                break;
            case 3:
                structure = new MachineGunTurret(gameGallery);
                break;
            default:
                structure = new Generator(gameGallery);
        }
        if (improved)
            structure.improveStructure();
        field.build(structure);
        gameWindow.getGame().setField(location, field);
    }

    @BeforeAll
    public static void getGallery()
    {
        gameGallery = new GameGallery();
        isDisplayAttached = true;
        try {
            GameWindow g = new GameWindow(Difficulty.EASY, gameGallery);
        } catch(HeadlessException hEx)
        {
            System.out.println("There is no display attached. Skipping tests!");
            isDisplayAttached = false;
        }
    }

    public void setup()
    {
        gameWindow = new GameWindow(Difficulty.EASY, gameGallery);
        gameWindow.setVisible(false);
        playerGoldBefore = gameWindow.getGame().getActualPlayer().getGold();
    }

    @AfterEach
    public void cleanUp()
    {
        if (gameWindow != null){
            gameWindow.dispatchEvent(new WindowEvent(gameWindow, GameWindow.DISPOSE_ON_CLOSE));
        }
    }

    @Test
    public void buildTurretSniper()
    {
        assumeTrue(isDisplayAttached);
        setup();
        clickButton(new Pair(2,2), 1);

        assertEquals(gameWindow.getGame().getField(2,2).getStructure().getId(), 1);
        assertEquals(gameWindow.getGame().getActualPlayer().getGold(), playerGoldBefore - new SniperTurret(gameGallery).getCost());
    }

    @Test
    public void buildTurretShotgun()
    {
        assumeTrue(isDisplayAttached);
        setup();
        clickButton(new Pair(2,2), 2);

        assertEquals(2, gameWindow.getGame().getField(2,2).getStructure().getId());
        assertEquals(playerGoldBefore - new ShotgunTurret(gameGallery).getCost(), gameWindow.getGame().getActualPlayer().getGold());
    }

    @Test
    public void buildTurretMachineGun()
    {
        assumeTrue(isDisplayAttached);
        setup();
        clickButton(new Pair(2,2), 3);

        assertEquals(3, gameWindow.getGame().getField(2,2).getStructure().getId());
        assertEquals(playerGoldBefore - new MachineGunTurret(gameGallery).getCost(),gameWindow.getGame().getActualPlayer().getGold());
    }

    @Test
    public void buildTurretGenerator()
    {
        assumeTrue(isDisplayAttached);
        setup();
        clickButton(new Pair(2,2), 4);

        assertEquals(4, gameWindow.getGame().getField(2,2).getStructure().getId());
        assertEquals(playerGoldBefore - new Generator(gameGallery).getCost(), gameWindow.getGame().getActualPlayer().getGold());
        assertEquals(1, gameWindow.getGame().getGeneratorCounter());
    }

    @Test
    public void buildTurretOnOtherTurret()
    {
        assumeTrue(isDisplayAttached);
        setup();
        setField(new Pair(2,2), 1, false);

        clickButton(new Pair(2,2), 3);

        assertEquals("There is already an other type turret here.", gameWindow.getErrorMsg());
        assertEquals(playerGoldBefore, gameWindow.getGame().getActualPlayer().getGold());
    }

    @Test
    public void buildTurretImprove()
    {
        assumeTrue(isDisplayAttached);
        setup();
        setField(new Pair(2,2), 3, false);

        clickButton(new Pair(2,2), 3);

        assertTrue(gameWindow.getGame().getField(2,2).getStructure().isImproved());
        assertEquals(playerGoldBefore - new SniperTurret(gameGallery).getImproveCost(), gameWindow.getGame().getActualPlayer().getGold());
    }

    @Test
    public void buildTurretOnEnemy()
    {
        assumeTrue(isDisplayAttached);
        setup();
        Field f = new Field();
        f.addEnemy(new GenericEnemy(new Pair(2,2), gameGallery, Difficulty.EASY));
        gameWindow.getGame().setField(new Pair(2,2), f);

        clickButton(new Pair(2,2), 1);

        assertEquals("You can't build on enemies!", gameWindow.getErrorMsg());
        assertEquals(playerGoldBefore, gameWindow.getGame().getActualPlayer().getGold());
    }

    @Test
    public void buildTurretNotEnoughGoldBuild()
    {
        assumeTrue(isDisplayAttached);
        setup();
        gameWindow.getGame().getActualPlayer().setGold(2);
        playerGoldBefore = gameWindow.getGame().getActualPlayer().getGold();

        clickButton(new Pair(2,2), 1);

        assertEquals("You don't have enough gold to build", gameWindow.getErrorMsg());
        assertEquals(playerGoldBefore, gameWindow.getGame().getActualPlayer().getGold());
    }

    @Test
    public void buildTurretNotEnoughGoldImprove()
    {
        assumeTrue(isDisplayAttached);
        setup();
        gameWindow.getGame().getActualPlayer().setGold(2);
        playerGoldBefore = gameWindow.getGame().getActualPlayer().getGold();
        setField(new Pair(2,2), 3, false);

        clickButton(new Pair(2,2), 3);

        assertEquals("You don't have enough gold to improve", gameWindow.getErrorMsg());
        assertEquals(playerGoldBefore, gameWindow.getGame().getActualPlayer().getGold());
    }

    @Test
    public void buildTurretAlreadyImproved()
    {
        assumeTrue(isDisplayAttached);
        setup();
        setField(new Pair(2,2), 3, true);

        clickButton(new Pair(2,2), 3);

        assertEquals("You've already improved this building", gameWindow.getErrorMsg());
        assertEquals(playerGoldBefore, gameWindow.getGame().getActualPlayer().getGold());
    }
}
