package model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeAll;
import view.resloader.GameGallery;
import model.menu.Difficulty;

import static org.junit.jupiter.api.Assertions.*;

public class FastEnemyTest {
    static GameGallery gameGallery;
    FastEnemy testFastEnemy = new FastEnemy(new Pair(1,2), gameGallery, Difficulty.EASY);
    FastEnemy testFastEnemyIfDying = new FastEnemy(new Pair(1,2), gameGallery, Difficulty.EASY);

    @BeforeAll
    static public void getGallery() {
        gameGallery = new GameGallery();
    }

    @Test
    public void testFastEnemyC(){
        assertEquals(5,testFastEnemy.getHealth());
        assertFalse(testFastEnemy.isAttacking());
    }

    @Test
    public void testAttacking(){
        testFastEnemy.setAttacking(true);
        assertTrue(testFastEnemy.isAttacking());
    }

    @Test
    public void testMove(){
        testFastEnemy.move();
        assertEquals(1, testFastEnemy.getLocation().getY());
        testFastEnemyIfDying.setHealth(0);
        testFastEnemyIfDying.move();
        assertEquals(2,testFastEnemyIfDying.getLocation().getY());
        testFastEnemyIfDying.move();
        assertEquals(2,testFastEnemyIfDying.getLocation().getY());
    }
    @Test
    public void testRemoveHealth(){
        testFastEnemy.removeHealth(3);
        assertFalse(testFastEnemy.dying());
        assertEquals(testFastEnemy.getHealth(),2);
    }
    @Test
    public void testResetPixelLoc(){
        testFastEnemy.resetPixelLoc(4);
        assertEquals(-8,testFastEnemy.getPixelLocation());
    }
    @Test
    public void testDying(){
        testFastEnemyIfDying.setHealth(-1);
        assertFalse(testFastEnemy.dying());
        assertTrue(testFastEnemyIfDying.dying());
    }
    @Test
    public void testCompare(){
        Pair p = new Pair(1,1);
        FastEnemy compareFastEnemy = new FastEnemy(p, gameGallery, Difficulty.EASY);
        GenericEnemy compareGenericEnemy = new GenericEnemy(p, gameGallery, Difficulty.EASY);
        SlowEnemy compareSlowEnemy = new SlowEnemy(p, gameGallery, Difficulty.EASY);

        assertEquals(0, testFastEnemy.compareTo(compareFastEnemy));
        assertEquals(-1, testFastEnemy.compareTo(compareGenericEnemy));
        assertEquals(-1, testFastEnemy.compareTo(compareSlowEnemy));
    }
}
