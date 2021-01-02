package model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeAll;
import view.resloader.GameGallery;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class ShotgunTurretTest {
    static GameGallery gameGallery;
    ShotgunTurret testShotgunTurret = new ShotgunTurret (gameGallery);

    @BeforeAll
    static public void getGallery() {
        gameGallery = new GameGallery();
    }

    @Test
    public void testShotgunTurretC(){
        assertEquals(13, testShotgunTurret.getActualHealth());
        assertTrue(testShotgunTurret.aoe);
    }

    @Test
    public void testIsImprovedChar(){
        assertEquals('F',testShotgunTurret.isImprovedToChar());
        assertFalse(testShotgunTurret.isImproved());
        testShotgunTurret.improveStructure();
        assertTrue(testShotgunTurret.isImproved());
        assertEquals(15, testShotgunTurret.getActualHealth());
    }

    @Test
    public  void testHit(){
        testShotgunTurret.hit(1);
        assertFalse(testShotgunTurret.isDemolished());
        testShotgunTurret.hit(20);
        assertTrue(testShotgunTurret.isDemolished());
    }

    @Test
    public  void testGetFieldsInRange(){
        Pair testPair = new Pair(1,1);
        testShotgunTurret.getFieldsInRange(testPair);
        assertNotNull(testShotgunTurret.getFieldsInRange(testPair));

    }

}
