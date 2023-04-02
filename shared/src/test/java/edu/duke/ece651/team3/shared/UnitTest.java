package edu.duke.ece651.team3.shared;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


public class UnitTest {
    @Test
    public void test_private() {
        Unit u1 = new Private(3);
        assertEquals("Private", u1.getUnitName());
        assertEquals(0, u1.getLevel());
        assertEquals(0, u1.getBonus());
        assertEquals(1, u1.getMoveCost());
        assertEquals(1, u1.getAttackCost());
        assertEquals(0, u1.getUpgradeCost());
        assertEquals(3, u1.getNumUnits());
        u1.setNumUnits(5);
        assertEquals(5, u1.getNumUnits());
    }

    @Test
    public void test_corporal() {
        Unit u1 = new Corporal(3);
        assertEquals("Corporal", u1.getUnitName());
        assertEquals(1, u1.getLevel());
        assertEquals(1, u1.getBonus());
        assertEquals(1, u1.getMoveCost());
        assertEquals(1, u1.getAttackCost());
        assertEquals(3, u1.getUpgradeCost());
        assertEquals(3, u1.getNumUnits());
        u1.setNumUnits(5);
        assertEquals(5, u1.getNumUnits());
    }

    @Test
    public void test_Specialist() {
        Unit u1 = new Specialist(3);
        assertEquals("Specialist", u1.getUnitName());
        assertEquals(2, u1.getLevel());
        assertEquals(3, u1.getBonus());
        assertEquals(2, u1.getMoveCost());
        assertEquals(2, u1.getAttackCost());
        assertEquals(11, u1.getUpgradeCost());
        assertEquals(3, u1.getNumUnits());
        u1.setNumUnits(5);
        assertEquals(5, u1.getNumUnits());
    }

    @Test
    public void test_Sergeant() {
        Unit u1 = new Sergeant(3);
        assertEquals("Sergeant", u1.getUnitName());
        assertEquals(3, u1.getLevel());
        assertEquals(5, u1.getBonus());
        assertEquals(3, u1.getMoveCost());
        assertEquals(3, u1.getAttackCost());
        assertEquals(30, u1.getUpgradeCost());
        assertEquals(3, u1.getNumUnits());
        u1.setNumUnits(5);
        assertEquals(5, u1.getNumUnits());
    }

    @Test
    public void test_SergeantMajor() {
        Unit u1 = new SergeantMajor(3);
        assertEquals("SergeantMajor", u1.getUnitName());
        assertEquals(6, u1.getLevel());
        assertEquals(15, u1.getBonus());
        assertEquals(6, u1.getMoveCost());
        assertEquals(6, u1.getAttackCost());
        assertEquals(140, u1.getUpgradeCost());
        assertEquals(3, u1.getNumUnits());
        u1.setNumUnits(5);
        assertEquals(5, u1.getNumUnits());
    }

    @Test
    public void test_FirstSergeant() {
        Unit u1 = new FirstSergeant(3);
        assertEquals("FirstSergeant", u1.getUnitName());
        assertEquals(5, u1.getLevel());
        assertEquals(11, u1.getBonus());
        assertEquals(5, u1.getMoveCost());
        assertEquals(5, u1.getAttackCost());
        assertEquals(90, u1.getUpgradeCost());
        assertEquals(3, u1.getNumUnits());
        u1.setNumUnits(5);
        assertEquals(5, u1.getNumUnits());
    }

    @Test
    public void test_MasterSergeant() {
        Unit u1 = new MasterSergeant(3);
        assertEquals("MasterSergeant", u1.getUnitName());
        assertEquals(4, u1.getLevel());
        assertEquals(8, u1.getBonus());
        assertEquals(4, u1.getMoveCost());
        assertEquals(4, u1.getAttackCost());
        assertEquals(55, u1.getUpgradeCost());
        assertEquals(3, u1.getNumUnits());
        u1.setNumUnits(5);
        assertEquals(5, u1.getNumUnits());
    }
}
