package edu.duke.ece651.team3.shared;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;

public class TerritoryTest {
    @Test
    public void test_equals() throws Exception {
        Territory t1 = new Territory("a", 2, 0, 0);
        Territory t2 = new Territory("a", 2, 0, 0);
        Territory t3 = new Territory("b", 2, 0, 0);
        Territory t4 = new Territory("a", 3, 0, 0);
        Territory t5 = new Territory("A", 2, 0, 0);
        assertNotEquals(t1, "(a, 2)"); //different objects
        assertEquals(t1, t2); //different address
        assertNotEquals(t1, t3); //different name
        assertNotEquals(t1, t4); //different numUnits
        assertFalse(t1.equals(t5)); // upper & lower case name
        Territory t6 = new Territory("x", 6, 0, 0);
        Territory t7 = new Territory("y", 7, 0, 0);
        Territory t8 = new Territory("z", 8, 0, 0);
        //t1.addNeighbors(t6, t7, t8);
        t1.addANeighbor(t6, 6);
        t1.addANeighbor(t7, 7);
        t1.addANeighbor(t8, 8);
        assertNotEquals(t1, t2); //different neighbors
        //t2.addNeighbors(t7, t6, t8);
        t2.addANeighbor(t8, 8);
        t2.addANeighbor(t7, 7);
        t2.addANeighbor(t6, 6);
        assertEquals(t1, t2); //neighbors different order
        Territory t9 = new Territory("a", 2, 1, 0);
        Territory t10 = new Territory("a", 2, 0, 2);
        assertNotEquals(t1, t9); //different food
        assertNotEquals(t1, t10); //different tech
    }

    @Test
    void test_hasSameUnits() {
        Territory t1 = new Territory("a", 0, 0, 0);
        Territory t2 = new Territory("a", 0, 0, 0);
        assertTrue(t1.hasSameUnits(t2));

        t1.setUnits(new ArrayList<>()); //different size of units
        assertFalse(t1.hasSameUnits(t2));

        ArrayList<Unit> units1 = new ArrayList<>();
        ArrayList<Unit> units2 = new ArrayList<>();
        units1.add(new Private(2));
        units2.add(new Private(1));
        t1.setUnits(units1);
        t2.setUnits(units2);
        assertFalse(t1.hasSameUnits(t2)); //different unit num

        units1.add(new Sergeant(1));
        units2.add(new Private(1));
        assertFalse(t1.hasSameUnits(t2)); //different unit type

    }

    @Test
    void test_hasSameNeighborsDist() throws Exception {
        Territory t1 = new Territory("a", 0, 0, 0);
        Territory t2 = new Territory("a", 0, 0, 0);
        assertTrue(t1.hasSameNeighborsDist(t2));

        Territory t3 = new Territory("b");
        Territory t4 = new Territory("b");
        t3.addANeighbor(t1, 3);
        t4.addANeighbor(t2, 3);
        assertTrue(t1.hasSameNeighborsDist(t2));

        Territory t5 = new Territory("c");
        Territory t6 = new Territory("c");
        t5.addANeighbor(t1, 3);
        t5.addANeighbor(t3, 4);
        t6.addANeighbor(t4, 4);
        t6.addANeighbor(t2, 3);
        assertTrue(t5.hasSameNeighborsDist(t6));
    }

    @Test
    public void test_fields_equals() {
        Territory t1 = new Territory("Narnia", 3, 0, 0);
        assertEquals("Narnia", t1.getTerritoryName());
        assertEquals(3, t1.getNumUnits());
        assertNotEquals(4, t1.getNumUnits());

        Territory t2 = new Territory("a");
        HashMap<Territory, Integer> expected = new HashMap<>();
        assertEquals("a", t2.getTerritoryName());
        assertEquals(expected, t2.getNeighborsDist());
        ArrayList<Unit> units = new ArrayList<>();
        units.add(new Private(0));
        units.add(new Corporal(0));
        units.add(new Specialist(0));
        units.add(new Sergeant(0));
        units.add(new MasterSergeant(0));
        units.add(new FirstSergeant(0));
        units.add(new SergeantMajor(0));
        assertEquals(units, t2.getUnits());

        HashMap<Territory, Integer> neighDist = new HashMap<>();
        Territory t3 = new Territory("a", neighDist, 0, 0, 0);
        Territory t4 = new Territory("a", 0, 0, 0);
        assertEquals(t3, t4);
    }

    @Test
    public void test_addANeighbor() throws Exception {
        HashMap<Territory, Integer> n1 = new HashMap<>();
        Territory t1 = new Territory("Oz", 12, 0 , 0);
        assertEquals(n1, t1.getNeighborsDist());

        HashMap<Territory, Integer> n2 = new HashMap<>();
        n2.put(t1, 1);
        Territory t2 = new Territory("Gondor", n2, 0,  0 , 0);
        assertEquals(n2, t2.getNeighborsDist());

        HashMap<Territory, Integer> n3 = new HashMap<>();
        n3.put(t2, 2);
        Territory t3 = new Territory("Morder", n3, 0, 0, 0);
        t2.addANeighbor(t3, 2);
        n2.put(t3, 3);
        assertEquals(n2, t2.getNeighborsDist());
        assertThrows(Exception.class, () -> t2.addANeighbor(t2, 3));
    }

    @Test
    public void test_checkValidNeighbor() throws Exception {
        Territory t1 = new Territory("Narnia", 3, 0, 0);
        Territory t2 = new Territory("Oz", 3, 0, 0);
        assertTrue(t1.checkValidNeighbor(t2));
        t1.addANeighbor(t2, 2);
        assertFalse(t1.checkValidNeighbor(t2));
        assertFalse(t1.checkValidNeighbor(t1));
    }
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
