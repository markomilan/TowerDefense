package model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeAll;
import view.resloader.GameGallery;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class MachineGunTurretTest {
    static GameGallery gameGallery;
    MachineGunTurret testMachineGunTurret = new MachineGunTurret (gameGallery);

    @BeforeAll
    static public void getGallery() {
        gameGallery = new GameGallery();
    }

    @Test
    public void testMachineGunTurretC(){
        assertEquals(10, testMachineGunTurret.getActualHealth());
        assertFalse(testMachineGunTurret.aoe);
    }

    @Test
    public void testIsImprovedChar(){
        assertEquals('F', testMachineGunTurret.isImprovedToChar());
        assertFalse(testMachineGunTurret.isImproved());
        testMachineGunTurret.improveStructure();
        assertTrue(testMachineGunTurret.isImproved());
        assertEquals(12, testMachineGunTurret.getActualHealth());
    }

    @Test
    public void testHit(){
        testMachineGunTurret.hit(1);
        assertFalse(testMachineGunTurret.isDemolished());
        testMachineGunTurret.hit(20);
        assertTrue(testMachineGunTurret.isDemolished());
    }

    @Test
    public void testGetFieldsInRange(){
        Pair testPair = new Pair(1,1);
        testMachineGunTurret.getFieldsInRange(testPair);
        assertNotNull(testMachineGunTurret.getFieldsInRange(testPair));

    }

}
