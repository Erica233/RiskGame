package edu.duke.ece651.team3.shared;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {

    @Test
    void test_getId() {
        Player p1 = new Player(1);
        assertEquals(1, p1.getId());
    }
    @Test
    void test_ownedTerritories() {
        Player p1 = new Player(1);
        ArrayList<Territory> ts1 = new ArrayList<>();
        assertEquals(ts1, p1.getOwnedTerritories());
    }

    @Test
    void test_tryAddTerritory() {
        Player p1 = new Player(1);
        Territory t1 = new Territory("Oz", 2);
        ArrayList<Territory> ts1 = new ArrayList<>();
        assertEquals(ts1, p1.getOwnedTerritories());

        assertTrue(p1.tryOwnTerritory(t1));
        ts1.add(t1);
        assertEquals(ts1, p1.getOwnedTerritories());
    }

    @Test
    void test_isValidToOwn() {
        Player p1 = new Player(1);
        Territory t1 = new Territory("Oz", 2);
        assertTrue(p1.isValidToOwn(t1));
    }
}