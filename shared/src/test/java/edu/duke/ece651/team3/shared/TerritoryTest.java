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
        //assertEquals(t1, t5); // upper & lower case name
        Territory t6 = new Territory("x", 6);
        Territory t7 = new Territory("y", 7);
        Territory t8 = new Territory("z", 8);
        t1.addNeighbors(t6, t7, t8);
        assertNotEquals(t1, t2); //different neighbors
        t2.addNeighbors(t7, t6, t8);
        assertEquals(t1, t2); //neighbors different order


    }

    @Test
    public void test_name_numUnits() {
        Territory t1 = new Territory("Narnia", 3);
        assertEquals("Narnia", t1.getTerritoryName());
        assertNotEquals("Narnias", t1.getTerritoryName());
        assertEquals(3, t1.getNumUnits());
        assertNotEquals(4, t1.getNumUnits());
    }
    @Test
    public void test_neighbors() {
        ArrayList<Territory> n1 = new ArrayList<>();
        Territory t1 = new Territory("Oz", 12);
        assertEquals(n1, t1.getNeighbors());
        ArrayList<Territory> n2 = new ArrayList<>();
        n2.add(t1);
        Territory t2 = new Territory("Gondor", 14, n2, new HashMap<>());
        assertEquals(n2, t2.getNeighbors());
        //Territory t3 = new Territory("Morder", 9);
    }

    @Test
    public void test_addANeighbor() throws Exception {
        ArrayList<Territory> n1 = new ArrayList<>();
        Territory t1 = new Territory("Oz", 12);
        assertEquals(n1, t1.getNeighbors());
        ArrayList<Territory> n2 = new ArrayList<>();
        n2.add(t1);
        Territory t2 = new Territory("Gondor", 14, n2, new HashMap<>());
        assertEquals(n2, t2.getNeighbors());
        ArrayList<Territory> n3 = new ArrayList<>();
        n3.add(t2);
        Territory t3 = new Territory("Morder", 9, n3, new HashMap<>());
        t2.addANeighbor(t3);
        n2.add(t3);
        assertEquals(n2, t2.getNeighbors());
        assertThrows(Exception.class, () -> t2.addANeighbor(t2));
        assertThrows(Exception.class, () -> t2.addNeighbors(t2, t1));

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
        Territory t1 = new Territory("a", 3);
        String expected1 = "3 units in a (no neighbors)\n";
        assertEquals(expected1, t1.displayTerritory()); //empty neighbors
        Territory t2 = new Territory("b", 2);
        Territory t3 = new Territory("c", 5);
        t1.addANeighbor(t2);
        String expected2 = "3 units in a (next to: b)\n";
        assertEquals(expected2, t1.displayTerritory());
        t1.addANeighbor(t3);
        String expected3 = "3 units in a (next to: b, c)\n";
        assertEquals(expected3, t1.displayTerritory());
    }

    @Test
    public void test_increaseUnit(){
        HashMap<Class<?>, Integer> units = new HashMap<>();
        Unit unit1 = new Soldier();
        //units.put(unit1.getClass(), 1);
        ArrayList<Territory> n = new ArrayList<>();
        Territory t = new Territory("Gondor", 0, n, units);
        t.increaseUnit(unit1, 1);
        t.increaseUnit(unit1, 2);
        assertEquals(3, t.getNumUnits());

    }

    @Test
    public void test_decreaseUnit(){
        HashMap<Class<?>, Integer> units = new HashMap<>();
        Unit unit1 = new Soldier();
        Unit unit2 = new Soldier();
        units.put(unit1.getClass(), 1);
        ArrayList<Territory> n = new ArrayList<>();
        Territory t = new Territory("Gondor", 0, n, units);
        t.decreaseUnit(unit2, 1);
        assertEquals(0, t.getNumUnits());
        Unit unit3 = new Officer();
        assertThrows(IllegalArgumentException.class, ()->t.decreaseUnit(unit2, 1));
        assertThrows(IllegalArgumentException.class, ()->t.decreaseUnit(unit3, 1));
    }

    @Test
    public void test_checkExistNeighbor(){
        HashMap<Class<?>, Integer> units = new HashMap<>();
        Unit unit1 = new Soldier();
        units.put(unit1.getClass(), 1);
        ArrayList<Territory> n = new ArrayList<>();
        Territory t = new Territory("Gondor", 0, n, units);
        Territory t1 = new Territory("a", 1);
        assertEquals(false, t.checkExistNeighbor(t1));
    }

}
