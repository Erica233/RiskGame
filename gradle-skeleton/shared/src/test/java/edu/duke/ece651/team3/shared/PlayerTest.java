package edu.duke.ece651.team3.shared;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {

    @Test
    void test_id_color_totNumUnits() {
        Player p1 = new Player(1, "red", 3);
        assertEquals(1, p1.getPlayerId());
        assertEquals("red", p1.getColor());
        assertEquals(3, p1.getTotNumUnits());
    }

    @Test
    void test_ownedTerritories() {
        Player p1 = new Player(1, "green", 3);
        ArrayList<Territory> ts1 = new ArrayList<>();
        assertEquals(ts1, p1.getOwnedTerritories());

        ArrayList<Territory> ts2 = new ArrayList<>();
        Territory t1 = new Territory("Oz", 2);
        ts2.add(t1);
        Player p2 = new Player(1, "yellow", 3, ts2);
        assertEquals(ts2, p2.getOwnedTerritories());
    }

    @Test
    void test_tryAddTerritory() {
        Player p1 = new Player(1, "blue", 3);
        Territory t1 = new Territory("Oz", 2);
        ArrayList<Territory> ts1 = new ArrayList<>();
        assertEquals(ts1, p1.getOwnedTerritories());

        assertTrue(p1.tryOwnTerritory(t1));
        ts1.add(t1);
        assertEquals(ts1, p1.getOwnedTerritories());
    }

    @Test
    void test_isValidToOwn() {
        Player p1 = new Player(1, "blue", 3);
        Territory t1 = new Territory("Oz", 2);
        assertTrue(p1.isValidToOwn(t1));
    }
}