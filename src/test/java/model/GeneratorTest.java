package model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeAll;
import view.resloader.GameGallery;

import static org.junit.jupiter.api.Assertions.*;

public class GeneratorTest {
    static GameGallery gameGallery;
    Generator testGenerator = new Generator (gameGallery);

    @BeforeAll
    static public void getGallery() {
        gameGallery = new GameGallery();
    }

    @Test
    public void testGeneratorC(){
        assertEquals(testGenerator.getActualHealth(),3);
    }

    @Test
    public void testIsImprovedChar(){
        assertEquals('F', testGenerator.isImprovedToChar());
        assertFalse(testGenerator.isImproved());
        testGenerator.improveStructure();
        assertTrue(testGenerator.isImproved());
        assertEquals(5, testGenerator.getActualHealth());
    }

    @Test
    public void testHit(){
        testGenerator.hit(1);
        assertFalse(testGenerator.isDemolished());
        testGenerator.hit(20);
        assertTrue(testGenerator.isDemolished());
    }
}
