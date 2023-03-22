package edu.duke.ece651.team3.shared;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class RiskGameBoardTest {
    @Test
    public void test_allPlayers() throws Exception {
        Board b0 = new RiskGameBoard();
        Board b1 = new RiskGameBoard();
        assertEquals(b0.getAllPlayers(), b1.getAllPlayers());
    }

    @Test
    public void test_equals() throws Exception {
        Board b0 = new RiskGameBoard();
        Board b1 = new RiskGameBoard();
        b0.initMap();
        b1.initMap();
        assertEquals(b0, b1);
        assertNotEquals(b0, "(player 1)");
        b1.getAllPlayers().get(0).getOwnedTerritories().get(0).addANeighbor(new Territory("x", 3));
        assertNotEquals(b0, b1);

    }

    @Test
    public void test_displayBoard() throws Exception {
        Board m1 = new RiskGameBoard();
        assertEquals("No players in the Board!\n", m1.displayBoard());
        m1.initMap();

        String expected = "red player:\n" +
                "---------------\n" +
                "5 units in a (next to: b, c)\n" +
                "5 units in c (next to: a, b, d, e, l)\n" +
                "5 units in g (next to: h, i, l)\n" +
                "5 units in h (next to: g, i, l)\n" +
                "5 units in i (next to: g, h, j, l)\n" +
                "5 units in l (next to: c, e, f, g, h, i)\n" +
                "\n" +
                "blue player:\n" +
                "---------------\n" +
                "5 units in b (next to: a, c, d)\n" +
                "5 units in d (next to: b, c, e)\n" +
                "5 units in e (next to: c, d, f, l)\n" +
                "5 units in f (next to: e, k, l)\n" +
                "5 units in j (next to: i, k)\n" +
                "5 units in k (next to: f, j)\n" +
                "\n";
        assertEquals(expected, m1.displayBoard());
    }
    
    @Test
    public void test_initMap() throws Exception {
        Board b1 = new RiskGameBoard();

        String expected = "red player:\n" +
                "---------------\n" +
                "5 units in a (next to: b, c)\n" +
                "5 units in c (next to: a, b, d, e, l)\n" +
                "5 units in g (next to: h, i, l)\n" +
                "5 units in h (next to: g, i, l)\n" +
                "5 units in i (next to: g, h, j, l)\n" +
                "5 units in l (next to: c, e, f, g, h, i)\n" +
                "\n" +
                "blue player:\n" +
                "---------------\n" +
                "5 units in b (next to: a, c, d)\n" +
                "5 units in d (next to: b, c, e)\n" +
                "5 units in e (next to: c, d, f, l)\n" +
                "5 units in f (next to: e, k, l)\n" +
                "5 units in j (next to: i, k)\n" +
                "5 units in k (next to: f, j)\n" +
                "\n";
        assertEquals(expected, b1.initMap());
    }
}
