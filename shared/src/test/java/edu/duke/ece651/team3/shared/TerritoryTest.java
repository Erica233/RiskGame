package edu.duke.ece651.team3.shared;

import static org.junit.jupiter.api.Assertions.*;

import org.checkerframework.checker.units.qual.A;
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
    public void test_others(){
        ArrayList<Territory> allTerr = new ArrayList<>();

        Territory a = new Territory("a");
        Territory b = new Territory("b");

        allTerr.add(a);

        assertEquals(true, a.isValidToAdd(allTerr, b));
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

    @Test
    public void test_tech() throws Exception {
        RiskGameBoard r = new RiskGameBoard();
        r.initE2Map();

        assertEquals(10, r.getAllPlayers().get(0).getOwnedTerritories().get(0).getTech());
    }

    @Test
    public void test_addANeighbor2() throws Exception {
        RiskGameBoard r = new RiskGameBoard();
        r.initE2Map();

        Player p1 = r.getAllPlayers().get(0);
        Player p2 = r.getAllPlayers().get(1);
        Territory a = p1.findOwnedTerritoryByName("a");
        a.setFood(10);
        assertEquals(10, a.getFood());
        a.reduceFood(5);
        assertEquals(5, a.getFood());
        a.reduceTechnology(5);
        assertEquals(5, a.getTech());
        a.setTech(10);
        assertEquals(10, a.getTech());

        Territory b = p2.findOwnedTerritoryByName("b"); //b is already a neighbor of a
//        a.addANeighbor(b, 1);

        assertFalse(a.checkValidNeighbor(a));
        assertFalse(a.checkExistNeighbor("i"));

        assertEquals(1, b.compareTo(a));

    }

    @Test
    public void test_resources() throws Exception{
        RiskGameBoard r = new RiskGameBoard();
        ArrayList<Unit> actionUnits= r.initBasicUnits(0);
        actionUnits.get(0).setNumUnits(100);
        Territory a = new Territory("a", 5, 10, 10);
        Territory b = new Territory("b", 5, 10, 10);
//        a.decreaseUnit(actionUnits);
        assertThrows(IllegalArgumentException.class, () -> a.decreaseUnit(actionUnits));
    }

    @Test
    public void test_isValidToAdd() throws Exception{
        RiskGameBoard r = new RiskGameBoard();
        r.initE2Map();
        Player p1 = r.getAllPlayers().get(0);
        Player p2 = r.getAllPlayers().get(1);

        ArrayList<Territory> allTerritoriesP1 = p1.getOwnedTerritories();
        Territory a = p1.findOwnedTerritoryByName("a");
        assertFalse(a.isValidToAdd(allTerritoriesP1, a));

    }

    @Test
    public void test_increaseResource(){
        Territory a = new Territory("a", 5, 10, 10);
        ArrayList<Unit> units = new ArrayList<>();
        units.add(new Private(5));
        units.add(new Corporal(0));
        units.add(new Specialist(0));
        units.add(new Sergeant(0));
        units.add(new MasterSergeant(0));
        units.add(new FirstSergeant(0));
        units.add(new SergeantMajor(0));
        a.increaseResource();

        assertEquals(20, a.getFood());
        assertEquals(60, a.getTech());

        a.increaseUpgradeUnit(units);
        assertEquals(5, a.getUnits().get(1).getNumUnits());
    }
    @Test
    public void test_display(){
        Territory a = new Territory("a");
        a.displayTerritory();
    }



}
