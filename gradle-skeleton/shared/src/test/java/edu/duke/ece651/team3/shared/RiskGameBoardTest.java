package edu.duke.ece651.team3.shared;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class RiskGameBoardTest {
    @Test
    public void test_allTerritories() {
        Territory t1 = new Territory("Narnia", 3);
        Territory t2 = new Territory("Oz", 2);
        Board m1 = new RiskGameBoard();
        ArrayList<Territory> expected = new ArrayList<>();
        assertEquals(expected, m1.getAllTerritories());
        m1.tryAddTerritory(t1);
        m1.tryAddTerritory(t2);
        expected.add(t1);
        expected.add(t2);
        assertEquals(expected, m1.getAllTerritories());
    }
    @Test
    public void test_tryAddTerritory() {
        Territory t1 = new Territory("Narnia", 3);
        Territory t2 = new Territory("Mordor", 4);
        Board m1 = new RiskGameBoard();
        assertTrue(m1.tryAddTerritory(t1));
        assertTrue(m1.tryAddTerritory(t2));
        ArrayList<Territory> expected = new ArrayList<>();
        expected.add(t1);
        expected.add(t2);
        assertEquals(expected, m1.getAllTerritories());
    }
    @Test
    public void test_displayBoard() {
        Territory t1 = new Territory("Narnia", 3);
        Territory t2 = new Territory("Mordor", 4);
        Territory t3 = new Territory("Oz", 5);
        Board m1 = new RiskGameBoard();
        m1.tryAddTerritory(t1);
        m1.tryAddTerritory(t2);
        m1.tryAddTerritory(t3);
        String expected = "3 units in Narnia (no neighbors)\n4 units in Mordor (no neighbors)\n5 units in Oz (no neighbors)\n";
        assertEquals(expected, m1.displayBoard());

        t1.tryAddANeighbor(t2);
        t2.tryAddANeighbor(t1);
        t2.tryAddANeighbor(t3);
        t3.tryAddANeighbor(t2);
        String expected2 = "3 units in Narnia (next to: Mordor)\n4 units in Mordor (next to: Narnia, Oz)\n5 units in Oz (next to: Mordor)\n";
        assertEquals(expected2, m1.displayBoard());
    }
}
