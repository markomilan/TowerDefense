package model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeAll;
import view.resloader.GameGallery;
import model.menu.Difficulty;

import static org.junit.jupiter.api.Assertions.*;

public class GenericEnemyTest {
    static GameGallery gameGallery;
    GenericEnemy testGenericEnemy = new GenericEnemy(new Pair(1,2), gameGallery, Difficulty.EASY);
    GenericEnemy testGenericEnemyIfDying = new GenericEnemy(new Pair(1,2), gameGallery, Difficulty.EASY);

    @BeforeAll
    static public void getGallery() {
        gameGallery = new GameGallery();
    }
    @Test
    public void testGenericEnemyC(){
        assertEquals(10, testGenericEnemy.getHealth());
        assertFalse(testGenericEnemy.isAttacking());
    }

    @Test
    public void testAttacking(){
        testGenericEnemy.setAttacking(true);
        assertTrue(testGenericEnemy.isAttacking());
    }

    @Test
    public void testMove(){
        testGenericEnemy.move();
        assertEquals(1, testGenericEnemy.getLocation().getY());
        testGenericEnemyIfDying.setHealth(0);
        testGenericEnemyIfDying.move();
        assertEquals(2, testGenericEnemyIfDying.getLocation().getY());
        testGenericEnemyIfDying.move();
        assertEquals(2, testGenericEnemyIfDying.getLocation().getY());
    }
    @Test
    public void testRemoveHealth(){
        testGenericEnemy.removeHealth(3);
        assertFalse(testGenericEnemy.dying());
        assertEquals(7, testGenericEnemy.getHealth());
    }
    @Test
    public void testResetPixelLoc(){
        testGenericEnemy.resetPixelLoc(4);
        assertEquals(-8, testGenericEnemy.getPixelLocation());
    }
    @Test
    public void testDying(){
        testGenericEnemyIfDying.setHealth(-1);
        assertFalse(testGenericEnemy.dying());
        assertTrue(testGenericEnemyIfDying.dying());
    }
    @Test
    public void testCompare(){
        Pair p = new Pair(1,1);
        FastEnemy compareFastEnemy = new FastEnemy(p, gameGallery, Difficulty.EASY);
        GenericEnemy compareGenericEnemy = new GenericEnemy(p, gameGallery, Difficulty.EASY);
        SlowEnemy compareSlowEnemy = new SlowEnemy(p, gameGallery, Difficulty.EASY);

        assertEquals(1, testGenericEnemy.compareTo(compareFastEnemy));
        assertEquals(0, testGenericEnemy.compareTo(compareGenericEnemy));
        assertEquals(-1, testGenericEnemy.compareTo(compareSlowEnemy));
    }
}
