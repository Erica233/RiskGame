package edu.duke.ece651.team3.shared;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.checkerframework.checker.units.qual.A;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;

public class TerritoryTest {
    @Test
    public void test_equals() throws Exception {
        Territory t1 = new Territory("a");
        Territory t2 = new Territory("a");
        Territory t3 = new Territory("b");
        Territory t4 = new Territory("a");
        Territory t5 = new Territory("A");

        assertNotEquals(t1, "(a, 2)"); //different objects
        assertEquals(0, t1.compareTo(t2));//different address
        assertNotEquals(t1, t3); //different name
        assertNotEquals(t1, t4); //different numUnits
        assertFalse(t1.equals(t5)); // upper & lower case name
        assertNotEquals(t1, t2); //different neighbors
        assertEquals(0, t1.compareTo(t2)); //neighbors different order
    }

    @Test
    public void test_all() throws Exception {
        RiskGameBoard r = new RiskGameBoard();
        Territory a = new Territory("a", 5, 10, 10);
        Territory b = new Territory("b", 5, 10, 10);
        Territory c = new Territory("c", 5, 10, 10);
        Territory d = new Territory("d", 5, 10, 10);

        r.connectNeighbors(a, b, 1);
        r.connectNeighbors(a, d, 1);
        r.connectNeighbors(b, c, 1);
        r.connectNeighbors(c, d, 1);
        r.connectNeighbors(b, d, 1);

        ArrayList<Territory> player1T = new ArrayList<>();
        player1T.add(a);
        player1T.add(c);

        ArrayList<Territory> player2T = new ArrayList<>();
        player2T.add(b);
        player2T.add(d);

        Player p1 = new Player(0, "Orange", 10, player1T);
        Player p2 = new Player(1, "Blue", 5, player2T);

        r.addPlayer(p1);
        r.addPlayer(p2);


        ArrayList<Unit> unitsToChange = new ArrayList<>();
        unitsToChange.add(new Private(1));
        unitsToChange.add(new Corporal(0));
        unitsToChange.add(new Specialist(0));
        unitsToChange.add(new Sergeant(0));

        assertFalse(a.hasSameNeighborsDist(b));
        assertTrue(a.hasSameNeighborsDist(c));

        ArrayList<String> exp_sorted = new ArrayList<>();
        exp_sorted.add("b");
        exp_sorted.add("d");
        assertEquals(exp_sorted, a.getSortedNeighborNames());
    }

    @Test
    public void test_constructor() throws Exception {
        HashMap<Territory, Integer> neighborsDist = new HashMap<>();
        Territory m = new Territory("m", 5, 0, 0);
        neighborsDist.put(m, 2);

        Territory a = new Territory("a", neighborsDist, 5, 0, 0);
        assertEquals(neighborsDist, a.getNeighborsDist());

    }
//
//    @Test
//    public void test_name_numUnits_neighborsDist() {
//        Territory t1 = new Territory("Narnia", 3);
//        assertEquals("Narnia", t1.getTerritoryName());
//        assertNotEquals("Narnias", t1.getTerritoryName());
//        assertEquals(3, t1.getNumUnits());
//        assertNotEquals(4, t1.getNumUnits());
//        Territory t2 = new Territory("a");
//        HashMap<Territory, Integer> expected = new HashMap<>();
//        assertEquals("a", t2.getTerritoryName());
//        assertEquals(expected, t2.getNeighborsDist());
//        ArrayList<Unit> units = new ArrayList<>();
//        assertEquals(units, t2.getUnits());
//    }
//
    @Test
    public void test_addANeighbor() throws Exception {
        RiskGameBoard r = new RiskGameBoard();
        r.initE2Map();

        Player p1 = r.getAllPlayers().get(0);
        Player p2 = r.getAllPlayers().get(1);
        Territory a = p1.getTerr("a");
        a.setFood(10);
        assertEquals(10, a.getFood());
        a.reduceFood(5);
        assertEquals(5, a.getFood());

        Territory b = p2.getTerr("b"); //b is already a neighbor of a
//        a.addANeighbor(b, 1);

        assertFalse(a.checkValidNeighbor(a));
        assertFalse(a.checkExistNeighbor("i"));

    }

    @Test
    public void test_others(){
        ArrayList<Territory> allTerr = new ArrayList<>();

        Territory a = new Territory("a");
        Territory b = new Territory("b");

        allTerr.add(a);

        assertEquals(true, a.isValidToAdd(allTerr, b));



    }

    @Test
    public void test_tech() throws Exception {
        RiskGameBoard r = new RiskGameBoard();
        r.initE2Map();

        assertEquals(0, r.getAllPlayers().get(0).getOwnedTerritories().get(0).getTech());
    }

    @Test
    public void test_neighborDist() throws Exception {
        RiskGameBoard r = new RiskGameBoard();
        r.initE2Map();

        Player p1 = r.getAllPlayers().get(0);
        Player p2 = r.getAllPlayers().get(1);
        Territory a = p1.getTerr("a");
        Territory i = p1.getTerr("i");
        Territory j = p1.getTerr("j");

        Territory b = p2.getTerr("b");
        assertFalse(a.hasSameNeighborsDist(i));
        assertFalse(a.hasSameNeighborsDist(j));
        assertFalse(a.checkValidNeighbor(b));

//        a.addANeighbor(b, 1); //TODO throws exception
    }
//
//    @Test
//    public void test_checkValidNeighbor() throws Exception {
//        Territory t1 = new Territory("Narnia", 3);
//        Territory t2 = new Territory("Oz", 3);
//        assertTrue(t1.checkValidNeighbor(t2));
//        t1.addANeighbor(t2);
//        assertFalse(t1.checkValidNeighbor(t2));
//        assertFalse(t1.checkValidNeighbor(t1));
//    }
//
//    @Test
//    public void test_displayTerritory() throws Exception {
//        Territory territory = new Territory("m", 0);
//        assertEquals(0, territory.getUnits().get(1));
//
//        Territory t1 = new Territory("a", 1, 3);
//        String expected1 = "3 units in a (no neighbors)\n";
//        assertEquals(expected1, t1.displayTerritory()); //empty neighbors
//        Territory t2 = new Territory("b", 2);
//        Territory t3 = new Territory("c", 5);
//        t1.addANeighbor(t2, 1);
//        String expected2 = "3 units in a (next to: b)\n";
//        assertEquals(expected2, t1.displayTerritory());
//        t1.addANeighbor(t3, 1);
//        String expected3 = "3 units in a (next to: b, c)\n";
//        assertEquals(expected3, t1.displayTerritory());
//    }
//
//    @Test
//    public void test_increaseUnit(){
//        HashMap<Integer, Integer> units = new HashMap<>();
//        //units.put(unit1.getClass(), 1);
//        ArrayList<Territory> n = new ArrayList<>();
//        Territory t = new Territory("Gondor", n, units);
//        t.increaseUnit(1, 1);
//        t.increaseUnit(1, 2);
//        assertEquals(3, t.getNumUnits());
//
//    }
//
//    @Test
//    public void test_decreaseUnit(){
//        HashMap<Integer, Integer> units = new HashMap<>();
//        units.put(1, 1);
//        ArrayList<Territory> n = new ArrayList<>();
//        Territory t = new Territory("Gondor", n, units);
//        t.decreaseUnit(1, 1);
//        assertEquals(0, t.getNumUnits());
//        assertThrows(IllegalArgumentException.class, ()->t.decreaseUnit(1, 1));
//        assertThrows(IllegalArgumentException.class, ()->t.decreaseUnit(2, 1));
//    }
//
//    @Test
//    public void test_checkExistNeighbor() throws Exception {
//        HashMap<Integer, Integer> units = new HashMap<>();
//        units.put(1, 1);
//        ArrayList<Territory> n = new ArrayList<>();
//        Territory t = new Territory("Gondor", n, units);
//        Territory t1 = new Territory("a", 1);
//        Territory t2 = new Territory("b", 1);
//        t1.addANeighbor(t);
//        assertEquals(false, t.checkExistNeighbor("a"));
//        assertEquals(true, t1.checkExistNeighbor("Gondor"));
//        assertEquals(false, t1.checkExistNeighbor("b"));
//    }
//
//    @Test
//    public void test_setGet(){
//        Territory territory = new Territory("a");
//        territory.setWinnerId(5);
//        assertEquals(5, territory.getWinnerId());
//
//        HashMap<Integer, Integer> units = new HashMap<>();
//        units.put(1, 1);
//        territory.setAttackerUnits(units);
//        assertEquals(units, territory.getAttackerUnits());
//    }
//
//    @Test
//    public void test_UpdateCombat(){
//        Territory territory = new Territory("a");
//        int ownId1 = -1;
//        int ownId2 = 1;
//        territory.updateCombatResult(ownId1);
//        assertEquals(-1, territory.getWinnerId());
////        territory.setWinnerId();
//        territory.updateCombatResult(ownId2);
//        assertEquals(-1, territory.getWinnerId());
//    }
//
//    @Test
//    public void test_setUnits(){
//        Territory territory = new Territory("a");
//        HashMap<Integer, Integer> newUnit = new HashMap<>();
//        newUnit.put(1, 2);
//
//        territory.setUnits(newUnit);
//
//        assertEquals(newUnit, territory.getUnits());
//    }
}
