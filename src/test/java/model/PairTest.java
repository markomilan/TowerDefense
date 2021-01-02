package model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PairTest {
    Pair testPair = new Pair(1,2);
    Pair copyPair = new Pair(testPair);

    @Test
    public void testPairC(){
        assertEquals(1, testPair.getX());
        assertEquals(2, testPair.getY());

    }
    @Test
    public void testPairCopyC(){
        assertEquals(1,copyPair.getX());
        assertEquals(2, copyPair.getY());
    }
    @Test
    public void testEquals(){
        assertTrue(testPair.equals(copyPair));
    }
    @Test
    public void testAdvance(){
        testPair.advance();
        assertEquals(1, testPair.getY());
    }
    @Test
    public void testToString(){
        assertEquals("(1;2)", testPair.toString());
    }
}
