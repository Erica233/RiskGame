package edu.duke.ece651.team3.shared;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;

public class TerritoryTest {
    @Test
    public void test_equals() throws Exception {
        Territory t1 = new Territory("a", 2);
        Territory t2 = new Territory("a", 2);
        Territory t3 = new Territory("b", 2);
        Territory t4 = new Territory("a", 3);
        Territory t5 = new Territory("A", 2);
        assertNotEquals(t1, "(a, 2)"); //different objects
        assertEquals(t1, t2); //different address
        assertNotEquals(t1, t3); //different name
        assertNotEquals(t1, t4); //different numUnits
        assertFalse(t1.equals(t5)); // upper & lower case name
        Territory t6 = new Territory("x", 6);
        Territory t7 = new Territory("y", 7);
        Territory t8 = new Territory("z", 8);
        //t1.addNeighbors(t6, t7, t8);
        assertNotEquals(t1, t2); //different neighbors
        //t2.addNeighbors(t7, t6, t8);
        assertEquals(t1, t2); //neighbors different order


    }

    @Test
    public void test_name_numUnits_neighborsDist() {
        Territory t1 = new Territory("Narnia", 3);
        assertEquals("Narnia", t1.getTerritoryName());
        assertNotEquals("Narnias", t1.getTerritoryName());
        assertEquals(3, t1.getNumUnits());
        assertNotEquals(4, t1.getNumUnits());
        Territory t2 = new Territory("a");
        HashMap<Territory, Integer> expected = new HashMap<>();
        assertEquals("a", t2.getTerritoryName());
        assertEquals(expected, t2.getNeighborsDist());
        ArrayList<Unit> units = new ArrayList<>();
        assertEquals(units, t2.getUnits());
    }

    @Test
    public void test_addANeighbor() throws Exception {
        ArrayList<Territory> n1 = new ArrayList<>();
        Territory t1 = new Territory("Oz", 12);
        assertEquals(n1, t1.getNeighborsDist());
        ArrayList<Territory> n2 = new ArrayList<>();
        n2.add(t1);
        Territory t2 = new Territory("Gondor", n2, new HashMap<>());
        assertEquals(n2, t2.getNeighborsDist());
        ArrayList<Territory> n3 = new ArrayList<>();
        n3.add(t2);
        Territory t3 = new Territory("Morder", n3, new HashMap<>());
        t2.addANeighbor(t3);
        n2.add(t3);
        assertEquals(n2, t2.getNeighborsDist());
//        assertThrows(Exception.class, () -> t2.addANeighbor(t2));
//        assertThrows(Exception.class, () -> t2.addNeighbors(t2, t1));

    }

    @Test
    public void test_checkValidNeighbor() throws Exception {
        Territory t1 = new Territory("Narnia", 3);
        Territory t2 = new Territory("Oz", 3);
        assertTrue(t1.checkValidNeighbor(t2));
        t1.addANeighbor(t2);
        assertFalse(t1.checkValidNeighbor(t2));
        assertFalse(t1.checkValidNeighbor(t1));
    }

    @Test
    public void test_displayTerritory() throws Exception {
        Territory territory = new Territory("m", 0);
        assertEquals(0, territory.getUnits().get(1));

        Territory t1 = new Territory("a", 1, 3);
        String expected1 = "3 units in a (no neighbors)\n";
        assertEquals(expected1, t1.displayTerritory()); //empty neighbors
        Territory t2 = new Territory("b", 2);
        Territory t3 = new Territory("c", 5);
        t1.addANeighbor(t2, 1);
        String expected2 = "3 units in a (next to: b)\n";
        assertEquals(expected2, t1.displayTerritory());
        t1.addANeighbor(t3, 1);
        String expected3 = "3 units in a (next to: b, c)\n";
        assertEquals(expected3, t1.displayTerritory());
    }

    @Test
    public void test_increaseUnit(){
        HashMap<Integer, Integer> units = new HashMap<>();
        //units.put(unit1.getClass(), 1);
        ArrayList<Territory> n = new ArrayList<>();
        Territory t = new Territory("Gondor", n, units);
        t.increaseUnit(1, 1);
        t.increaseUnit(1, 2);
        assertEquals(3, t.getNumUnits());

    }

    @Test
    public void test_decreaseUnit(){
        HashMap<Integer, Integer> units = new HashMap<>();
        units.put(1, 1);
        ArrayList<Territory> n = new ArrayList<>();
        Territory t = new Territory("Gondor", n, units);
        t.decreaseUnit(1, 1);
        assertEquals(0, t.getNumUnits());
        assertThrows(IllegalArgumentException.class, ()->t.decreaseUnit(1, 1));
        assertThrows(IllegalArgumentException.class, ()->t.decreaseUnit(2, 1));
    }

    @Test
    public void test_checkExistNeighbor() throws Exception {
        HashMap<Integer, Integer> units = new HashMap<>();
        units.put(1, 1);
        ArrayList<Territory> n = new ArrayList<>();
        Territory t = new Territory("Gondor", n, units);
        Territory t1 = new Territory("a", 1);
        Territory t2 = new Territory("b", 1);
        t1.addANeighbor(t);
        assertEquals(false, t.checkExistNeighbor("a"));
        assertEquals(true, t1.checkExistNeighbor("Gondor"));
        assertEquals(false, t1.checkExistNeighbor("b"));
    }

    @Test
    public void test_setGet(){
        Territory territory = new Territory("a");
        territory.setWinnerId(5);
        assertEquals(5, territory.getWinnerId());

        HashMap<Integer, Integer> units = new HashMap<>();
        units.put(1, 1);
        territory.setAttackerUnits(units);
        assertEquals(units, territory.getAttackerUnits());
    }

    @Test
    public void test_UpdateCombat(){
        Territory territory = new Territory("a");
        int ownId1 = -1;
        int ownId2 = 1;
        territory.updateCombatResult(ownId1);
        assertEquals(-1, territory.getWinnerId());
//        territory.setWinnerId();
        territory.updateCombatResult(ownId2);
        assertEquals(-1, territory.getWinnerId());
    }

    @Test
    public void test_setUnits(){
        Territory territory = new Territory("a");
        HashMap<Integer, Integer> newUnit = new HashMap<>();
        newUnit.put(1, 2);

        territory.setUnits(newUnit);

        assertEquals(newUnit, territory.getUnits());
    }
}
