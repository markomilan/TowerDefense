package model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PlayerTest {
    Player testPlayer = new Player();

    @Test
    public void testPlayer() {
        assertEquals(testPlayer.getGold(),10);
    }
    @Test
    public void testAddGold(){
        testPlayer.addGold(10);
        assertEquals(20, testPlayer.getGold());
    }

}
