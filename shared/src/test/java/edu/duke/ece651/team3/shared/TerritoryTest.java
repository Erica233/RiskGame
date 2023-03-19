package edu.duke.ece651.team3.shared;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;

public class TerritoryTest {
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
    public void test_tryAddANeighbor() throws Exception {
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
    }

    @Test
    public void test_isAValidNeighbor() {
        Territory t1 = new Territory("Narnia", 3);
        assertTrue(t1.isAValidNeighbor());
    }
    @Test
    public void test_isValidToAdd() {
        Territory t1 = new Territory("Narnia", 3);
        assertTrue(t1.isValidToAdd());
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

}
