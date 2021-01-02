package controller.ingame;

import model.*;
import model.menu.Difficulty;
import org.junit.jupiter.api.*;
import view.ingame.GameWindow;
import view.resloader.GameGallery;

import java.awt.*;
import java.io.File;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

public class GameSessionTest {
    GameSession gameSession;
    static GameGallery gameGallery;
    static boolean isDisplayAttached;

    private void setField(Pair location, Enemy enemy) {
        Field field;
        if (gameSession.getField(location) == null)
            field = new Field();
        else
            field = gameSession.getField(location);
        field.addEnemy(enemy);
        gameSession.setField(location, field);
    }

    private void setField(Pair location, Structure structure, boolean improved) {
        Field field = new Field();
        if (improved) {
            structure.improveStructure();
        }
        field.build(structure);
        gameSession.setField(location, field);
    }
    
    @BeforeAll
    public static void createGameGallery() {
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

    @BeforeEach
    public void prepareCase() {
        gameSession = new GameSession(Difficulty.EASY);
    }

    @AfterEach
    public void deleteFile(){
        File f = new File(System.getProperty("user.dir") + "\\src\\main\\savedgames\\testSaveGame.txt");
        f.delete();
    }

    @Test
    public void testGameSessionC() {

        GameSession gameSessionRestarted = new GameSession(gameSession);
        assertEquals(gameSession.getDifficulty(), Difficulty.EASY);
        assertNotEquals(gameSession.getDifficulty(), Difficulty.MEDIUM);

        assertEquals(Difficulty.EASY, gameSessionRestarted.getDifficulty());

        gameSession.setGeneratorCounter(3);
        gameSession.setTime(10);
        gameSession.setPauseOnly();

        assertEquals(3, gameSession.getGeneratorCounter());
        assertEquals(10, gameSession.getTime());
        assertTrue(gameSession.isPause());

        gameSession.setPause();
        assertFalse(gameSession.isPause());
    }

    @Test
    public void testCheckWin() {
        gameSession.setTime(10);
        assertFalse(gameSession.checkWin());
        gameSession.setTime(120);
        assertTrue(gameSession.checkWin());
    }

    @Test
    public void testSaveGame_Structures() {
        assumeTrue(isDisplayAttached);
        // Building basic structures
        setField(new Pair(2, 1), new SniperTurret(gameGallery), false);
        setField(new Pair(2, 2), new ShotgunTurret(gameGallery), false);
        setField(new Pair(2, 3), new MachineGunTurret(gameGallery), false);
        setField(new Pair(2, 4), new Generator(gameGallery), false);

        gameSession.saveGame("testSaveGame");
        LoadGame loadGame = new LoadGame(gameGallery, "testSaveGame.txt");
        loadGame.getGameWindow().setVisible(false);

        GameSession gameSession = loadGame.getGameWindow().getGame();
        for (int i = 1; i < 5; ++i) {
            assertEquals(i, gameSession.getField(new Pair(2, i)).getStructure().getId());
        }
    }

    @Test
    public void testSaveGame_ImprovedStructures() {
        assumeTrue(isDisplayAttached);
        // Building improved structures
        setField(new Pair(4, 1), new SniperTurret(gameGallery), true);
        setField(new Pair(4, 2), new ShotgunTurret(gameGallery), true);
        setField(new Pair(4, 3), new MachineGunTurret(gameGallery), true);
        setField(new Pair(4, 4), new Generator(gameGallery), true);

        gameSession.saveGame("testSaveGame");
        LoadGame loadGame = new LoadGame(gameGallery, "testSaveGame.txt");
        loadGame.getGameWindow().setVisible(false);
        GameSession gameSession = loadGame.getGameWindow().getGame();

        for (int i = 1; i < 5; ++i) {
            assertEquals(i, gameSession.getField(new Pair(4, i)).getStructure().getId());
            assertTrue(gameSession.getField(new Pair(4, i)).getStructure().isImproved());
        }
    }

    @Test
    public void testSaveGame_Enemies(){
        assumeTrue(isDisplayAttached);
        // Spawning single enemies
        setField(new Pair(2,1), new FastEnemy(new Pair(2,1), gameGallery, Difficulty.EASY));
        setField(new Pair(2,2), new GenericEnemy(new Pair(2,2), gameGallery, Difficulty.EASY));
        setField(new Pair(2,3), new SlowEnemy(new Pair(2,3), gameGallery, Difficulty.EASY));

        gameSession.saveGame("testSaveGame");
        LoadGame loadGame = new LoadGame(gameGallery, "testSaveGame.txt");
        loadGame.getGameWindow().setVisible(false);
        GameSession gameSession = loadGame.getGameWindow().getGame();
        Field field;

        for (int i = 1; i < 4; ++i){
            field = gameSession.getField(new Pair(2,i));
            assertEquals(i, field.getEnemy(0).getId());
            assertEquals(1, field.getEnemyCount());
        }
    }

    @Test
    public void testSaveGame_MultipleEnemies(){
        assumeTrue(isDisplayAttached);
        // Multiple enemies on a field
        Pair p = new Pair(7,2);
        setField(p, new GenericEnemy(p, gameGallery, Difficulty.EASY));
        setField(p, new SlowEnemy(p, gameGallery, Difficulty.EASY));
        setField(p, new FastEnemy(p, gameGallery, Difficulty.EASY));

        gameSession.saveGame("testSaveGame");
        LoadGame loadGame = new LoadGame(gameGallery, "testSaveGame.txt");
        loadGame.getGameWindow().setVisible(false);
        Field field = loadGame.getGameWindow().getGame().getField(p);
        Enemy[] enemiesToTest = new Enemy[] {new GenericEnemy(p, gameGallery, Difficulty.EASY),
                new FastEnemy(p, gameGallery, Difficulty.EASY), new SlowEnemy(p, gameGallery, Difficulty.EASY)};

        assertEquals(3, field.getEnemyCount());
        for (Enemy enemy : enemiesToTest) {
            assertFalse(field.canFitEnemy(enemy));
        }
    }

    @Test
    public void testSaveGame_DamagedStructure(){
        assumeTrue(isDisplayAttached);
        // Damaged structure
        SniperTurret turret = new SniperTurret(gameGallery);
        SniperTurret improvedTurret = new SniperTurret(gameGallery);
        turret.setActualHealth(66);
        improvedTurret.setActualHealth(82);
        setField(new Pair(6, 2), turret, false);
        setField(new Pair(6, 3), improvedTurret, true);

        gameSession.saveGame("testSaveGame");
        LoadGame loadGame = new LoadGame(gameGallery, "testSaveGame.txt");
        loadGame.getGameWindow().setVisible(false);
        GameSession gameSession = loadGame.getGameWindow().getGame();

        assertEquals(66, gameSession.getField(new Pair(6,2)).getStructure().getActualHealth());
        assertEquals(84, gameSession.getField(new Pair(6,3)).getStructure().getActualHealth());
    }

    @Test
    public void testSaveGame_DamagedEnemy(){
        assumeTrue(isDisplayAttached);
        // Damaged enemy
        Enemy enemy = new GenericEnemy(new Pair(5, 2), gameGallery, Difficulty.EASY);
        enemy.setHealth(1);
        setField(new Pair(5, 2), enemy);

        gameSession.saveGame("testSaveGame");
        LoadGame loadGame = new LoadGame(gameGallery, "testSaveGame.txt");
        loadGame.getGameWindow().setVisible(false);

        assertEquals(1, loadGame.getGameWindow().getGame().getField(new Pair(5,2)).getEnemy(0).getHealth());
    }

    @Test
    public void testSaveGame_GameInfo(){
        assumeTrue(isDisplayAttached);
        // Player gold
        gameSession.getActualPlayer().setGold(72);
        // Time
        gameSession.setTime(22);
        gameSession.saveGame("testSaveGame");
        // Generator counts
        gameSession.setGeneratorCounter(5);
        gameSession.setImprovedGeneratorCounter(7);

        gameSession.saveGame("testSaveGame");
        LoadGame loadGame = new LoadGame(gameGallery, "testSaveGame.txt");
        loadGame.getGameWindow().setVisible(false);
        GameSession gameSession = loadGame.getGameWindow().getGame();

        assertEquals(22, gameSession.getTime());
        assertEquals(5, gameSession.getGeneratorCounter());
        assertEquals(7, gameSession.getImprovedGeneratorCounter());
        assertEquals(72, gameSession.getActualPlayer().getGold());
    }
}
