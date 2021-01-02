package model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeAll;
import view.resloader.GameGallery;
import model.menu.Difficulty;

import static org.junit.jupiter.api.Assertions.*;

public class SlowEnemyTest {
    static GameGallery gameGallery;
    SlowEnemy testSlowEnemy = new SlowEnemy(new Pair(1,2), gameGallery, Difficulty.EASY);
    SlowEnemy testSlowEnemyIfDying = new SlowEnemy(new Pair(1,2), gameGallery, Difficulty.EASY);

    @BeforeAll
    static public void getGallery() {
        gameGallery = new GameGallery();
    }

    @Test
    public void testSlowEnemyC(){
        assertEquals(15, testSlowEnemy.getHealth());
        assertFalse(testSlowEnemy.isAttacking());
    }

    @Test
    public void testAttacking(){
        testSlowEnemy.setAttacking(true);
        assertTrue(testSlowEnemy.isAttacking());
    }

    @Test
    public void testMove(){
        testSlowEnemy.move();
        assertEquals(1, testSlowEnemy.getLocation().getY());
        testSlowEnemyIfDying.setHealth(0);
        testSlowEnemyIfDying.move();
        assertEquals(2, testSlowEnemyIfDying.getLocation().getY());
        testSlowEnemyIfDying.move();
        assertEquals(2, testSlowEnemyIfDying.getLocation().getY());
    }
    @Test
    public void testRemoveHealth(){
        testSlowEnemy.removeHealth(8);
        assertFalse(testSlowEnemy.dying());
        assertEquals(7, testSlowEnemy.getHealth());
    }
    @Test
    public void testResetPixelLoc(){
        testSlowEnemy.resetPixelLoc(4);
        assertEquals(-8, testSlowEnemy.getPixelLocation());
    }
    @Test
    public void testDying(){
        testSlowEnemyIfDying.setHealth(-1);
        assertFalse(testSlowEnemy.dying());
        assertTrue(testSlowEnemyIfDying.dying());
    }
    @Test
    public void testCompare(){
        Pair p = new Pair(1,1);
        FastEnemy compareFastEnemy = new FastEnemy(p, gameGallery, Difficulty.EASY);
        GenericEnemy compareGenericEnemy = new GenericEnemy(p, gameGallery, Difficulty.EASY);
        SlowEnemy compareSlowEnemy = new SlowEnemy(p, gameGallery, Difficulty.EASY);

        assertEquals(1, testSlowEnemy.compareTo(compareFastEnemy));
        assertEquals(1, testSlowEnemy.compareTo(compareGenericEnemy));
        assertEquals(0, testSlowEnemy.compareTo(compareSlowEnemy));
    }
}
