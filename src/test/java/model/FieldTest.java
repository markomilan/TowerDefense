package model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeAll;
import view.resloader.GameGallery;
import model.menu.Difficulty;

import static org.junit.jupiter.api.Assertions.*;

public class FieldTest {
    static GameGallery gameGallery;
    Field testField = new Field();
    Field testFieldEmpty = new Field();
    Field testFieldStructure = new Field();
    Field testFieldEnemy = new Field();
    Field enemyOnField = new Field();
    Player player = new Player();

    @BeforeAll
    public static void getGallery() {
        gameGallery = new GameGallery();
    }

    @Test
    public void testField(){
        assertTrue(testField.getEnemyCount() == 0 && testField.getStructure() ==null);
    }
    @Test
    public void testBuild(){
        testField.build(new SniperTurret(gameGallery));
        assertNotNull(testField.getStructure());
    }
    @Test
    public void testAddEnemy(){
        testField.addEnemy(new SlowEnemy(new Pair(1,2),gameGallery, Difficulty.EASY));
        assertTrue(testField.hasAliveEnemies());
    }
    @Test
    public void testGetFieldType(){
        testFieldStructure.build(new Generator(gameGallery));
        testFieldEnemy.addEnemy(new SlowEnemy(new Pair(1,2),gameGallery, Difficulty.EASY));
        assertEquals("e",testFieldEmpty.getFieldType());
        assertEquals("S",testFieldStructure.getFieldType());
        assertEquals("E",testFieldEnemy.getFieldType());
    }
    @Test
    public void testDemolishStructure(){
        testField.build(new Generator(gameGallery));
        testField.demolishStructure();
        assertEquals(null, testField.getStructure());
    }

    @Test
    public void move_singleEnemy() {
        Field nextField = new Field();
        testField.addEnemy(new FastEnemy(new Pair(1,2),gameGallery, Difficulty.EASY));
        testField.move(2, nextField);
        assertEquals(0, testField.getEnemyCount());
        assertEquals(1, nextField.getEnemyCount());
    }

    @Test
    public void move_cantFitEnemy()
    {
        Field nextField = new Field();
        testField.addEnemy(new FastEnemy(new Pair(1,2),gameGallery, Difficulty.EASY));
        nextField.addEnemy(new FastEnemy(new Pair(1,2),gameGallery, Difficulty.EASY));
        testField.move(2, nextField);
        assertEquals(1, testField.getEnemyCount());
        assertEquals(1, nextField.getEnemyCount());
    }

    @Test
    public void move_multipleEnemies()
    {
        Field nextField = new Field();
        testField.addEnemy(new FastEnemy(new Pair(1,2),gameGallery, Difficulty.EASY));
        testField.addEnemy(new GenericEnemy(new Pair(1,2),gameGallery, Difficulty.EASY));
        testField.addEnemy(new SlowEnemy(new Pair(1,2),gameGallery, Difficulty.EASY));
        nextField.addEnemy(new FastEnemy(new Pair(1,2),gameGallery, Difficulty.EASY));
        testField.move(12, nextField);
        assertEquals(1, testField.getEnemyCount());
        assertEquals(3, nextField.getEnemyCount());
    }

    @Test
    public void move_intoStructure()
    {
        Field nextField = new Field();
        Structure turret = new ShotgunTurret(gameGallery);
        int maxHealth = turret.getActualHealth();
        nextField.build(turret);
        Enemy enemy = new FastEnemy(new Pair(1,2),gameGallery, Difficulty.EASY);
        testField.addEnemy(enemy);

        testField.move(2, nextField);

        assertEquals(nextField.getStructure().getActualHealth(), maxHealth - enemy.getDamage());
        assertTrue(enemy.isAttacking());
        assertFalse(enemy.isRunning());
        assertEquals(1, testField.getEnemyCount());
        assertEquals(0, nextField.getEnemyCount());
    }

    @Test
    public void move_destroyStructure()
    {
        Field nextField = new Field();
        Structure turret = new Generator(gameGallery); // Health = 3
        nextField.build(turret);
        testField.addEnemy(new SlowEnemy(new Pair(1,2), gameGallery, Difficulty.EASY)); // Damage = 3

        testField.move(12, nextField);

        assertNull(nextField.getStructure());
        assertEquals(1, nextField.getEnemyCount());
        assertFalse(nextField.getEnemy(0).isAttacking());
        assertTrue(nextField.getEnemy(0).isRunning());
    }

    @Test
    public void damageEnemies_damage(){
        Enemy enemy = new SlowEnemy(new Pair(1,2),gameGallery, Difficulty.EASY);
        Enemy enemy2 = new FastEnemy(new Pair(1,2), gameGallery, Difficulty.EASY);
        int maxHp1 = enemy.getHealth();
        int maxHp2 = enemy2.getHealth();
        testField.addEnemy(enemy);
        testField.addEnemy(enemy2);

        testField.damageEnemies(2, false, player);

        assertEquals(maxHp1 - 2, testField.getEnemy(0).getHealth());
        assertEquals(maxHp2, testField.getEnemy(1).getHealth());
        assertTrue(testField.hasAliveEnemies());
    }

    @Test
    public void damageEnemies_AoE(){
        Enemy enemy = new SlowEnemy(new Pair(1,2),gameGallery, Difficulty.EASY);
        Enemy enemy2 = new FastEnemy(new Pair(1,2), gameGallery, Difficulty.EASY);
        int maxHp1 = enemy.getHealth();
        int maxHp2 = enemy2.getHealth();
        testField.addEnemy(enemy);
        testField.addEnemy(enemy2);

        testField.damageEnemies(2, true, player);

        assertEquals(maxHp1 - 2, testField.getEnemy(0).getHealth());
        assertEquals(maxHp2 - 2, testField.getEnemy(1).getHealth());
        assertTrue(testField.hasAliveEnemies());
    }

    @Test
    public void damageEnemies_kill(){
        Enemy enemy = new SlowEnemy(new Pair(1,2),gameGallery, Difficulty.EASY);
        int goldBefore = player.getGold();
        testField.addEnemy(enemy);

        testField.damageEnemies(20, false, player);

        assertTrue(testField.getEnemy(0).dying());
        assertFalse(testField.hasAliveEnemies());
        assertEquals(goldBefore + enemy.getGoldReward(), player.getGold());
    }

    @Test
    public void damageEnemies_killAoE(){
        Enemy enemy = new SlowEnemy(new Pair(1,2),gameGallery, Difficulty.EASY);
        Enemy enemy2 = new FastEnemy(new Pair(1,2), gameGallery, Difficulty.EASY);
        int goldBefore = player.getGold();
        testField.addEnemy(enemy);
        testField.addEnemy(enemy2);

        testField.damageEnemies(20, true, player);

        assertTrue(testField.getEnemy(0).dying());
        assertTrue(testField.getEnemy(1).dying());
        assertFalse(testField.hasAliveEnemies());
        assertEquals(goldBefore + enemy.getGoldReward() + enemy2.getGoldReward(), player.getGold());
    }
}
