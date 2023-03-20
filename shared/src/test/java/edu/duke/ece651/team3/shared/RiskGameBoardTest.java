package edu.duke.ece651.team3.shared;

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

//    @Test
//    public void test_allTerritories() throws Exception {
//        Territory t1 = new Territory("Narnia", 3);
//        Territory t2 = new Territory("Oz", 2);
//        Board m1 = new RiskGameBoard();
//        ArrayList<Territory> expected = new ArrayList<>();
//        assertEquals(expected, m1.getAllTerritories());
//        m1.tryAddTerritory(t1);
//        m1.tryAddTerritory(t2);
//        expected.add(t1);
//        expected.add(t2);
//        assertEquals(expected, m1.getAllTerritories());
//    }
//    @Test
//    public void test_tryAddTerritory() throws Exception {
//        Territory t1 = new Territory("Narnia", 3);
//        Territory t2 = new Territory("Mordor", 4);
//        Board m1 = new RiskGameBoard();
//        assertTrue(m1.tryAddTerritory(t1));
//        assertTrue(m1.tryAddTerritory(t2));
//        ArrayList<Territory> expected = new ArrayList<>();
//        expected.add(t1);
//        expected.add(t2);
//        assertEquals(expected, m1.getAllTerritories());
//    }
    @Test
    public void test_displayBoard() throws Exception {
        Territory t1 = new Territory("Narnia", 3);
        Territory t2 = new Territory("Mordor", 4);
        Territory t3 = new Territory("Oz", 5);
        Board m1 = new RiskGameBoard();
        m1.initMap();
//        m1.tryAddTerritory(t1);
//        m1.tryAddTerritory(t2);
//        m1.tryAddTerritory(t3);
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

//        t1.addANeighbor(t2);
//        t2.addANeighbor(t1);
//        t2.addANeighbor(t3);
//        t3.addANeighbor(t2);
//        String expected2 = "3 units in Narnia (next to: Mordor)\n4 units in Mordor (next to: Narnia, Oz)\n5 units in Oz (next to: Mordor)\n";
//        assertEquals(expected2, m1.displayBoard());
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
