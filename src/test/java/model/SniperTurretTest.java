package model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeAll;
import view.resloader.GameGallery;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class SniperTurretTest {
    static GameGallery gameGallery;
    SniperTurret testSniperTurret = new SniperTurret (gameGallery);

    @BeforeAll
    static public void getGallery() {
        gameGallery = new GameGallery();
    }

    @Test
    public void testSniperTurretC(){
        assertEquals(7, testSniperTurret.getActualHealth());
        assertFalse(testSniperTurret.aoe);
    }

    @Test
    public void testIsImprovedChar(){
        assertEquals('F', testSniperTurret.isImprovedToChar());
        assertFalse(testSniperTurret.isImproved());
        testSniperTurret.improveStructure();
        assertTrue(testSniperTurret.isImproved());
        assertEquals(9, testSniperTurret.getActualHealth());
    }

    @Test
    public void testHit(){
        testSniperTurret.hit(1);
        assertFalse(testSniperTurret.isDemolished());
        testSniperTurret.hit(18);
        assertTrue(testSniperTurret.isDemolished());
    }

    @Test
    public void testGetFieldsInRange(){
        Pair testPair = new Pair(1,1);
        testSniperTurret.getFieldsInRange(testPair);
        assertNotNull(testSniperTurret.getFieldsInRange(testPair));

    }

}
