package edu.duke.ece651.team3.shared;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RiskGameBoardTest {
    @Test
    public void test_territory() {
        Territory t1 = new Territory("Narnia", 3);
        Territory t2 = new Territory("Oz", 2);
        Board m1 = new RiskGameBoard(t1);
        assertEquals(t1, m1.getaTerritory());
        assertNotEquals(t2, m1.getaTerritory());
    }
    @Test
    public void test_displayBoard() {
        Territory t1 = new Territory("Narnia", 3);
        Board m1 = new RiskGameBoard(t1);
        String expected = "3 units in Narnia\n";
        assertEquals(expected, m1.displayBoard());
    }
}
