//package edu.duke.ece651.team3.shared;
//
//import org.junit.jupiter.api.Test;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//
//public class UnitTest {
//    @Test
//    public void test_infantry() {
//        Unit u1 = new Private(3);
//        assertEquals("Infantry", u1.getUnitName());
//        assertEquals(0, u1.getLevel());
//        assertEquals(0, u1.getBonus());
//        assertEquals(1, u1.getMoveCost());
//        assertEquals(1, u1.getAttackCost());
//        assertEquals(0, u1.getUpgradeCost());
//        assertEquals(3, u1.getNumUnits());
//        u1.setNumUnits(5);
//        assertEquals(5, u1.getNumUnits());
//    }
//
//    @Test
//    public void test_cavalry() {
//        Unit u1 = new Corporal(3);
//        assertEquals("Cavalry", u1.getUnitName());
//        assertEquals(1, u1.getLevel());
//        assertEquals(1, u1.getBonus());
//        assertEquals(2, u1.getMoveCost());
//        assertEquals(2, u1.getAttackCost());
//        assertEquals(3, u1.getUpgradeCost());
//        assertEquals(3, u1.getNumUnits());
//        u1.setNumUnits(5);
//        assertEquals(5, u1.getNumUnits());
//    }
//
//    @Test
//    public void test_artillery() {
//        Unit u1 = new Specialist(3);
//        assertEquals("Artillery", u1.getUnitName());
//        assertEquals(2, u1.getLevel());
//        assertEquals(3, u1.getBonus());
//        assertEquals(3, u1.getMoveCost());
//        assertEquals(3, u1.getAttackCost());
//        assertEquals(7, u1.getUpgradeCost());
//        assertEquals(3, u1.getNumUnits());
//        u1.setNumUnits(5);
//        assertEquals(5, u1.getNumUnits());
//    }
//
//    @Test
//    public void test_specialForces() {
//        Unit u1 = new Sergeant(3);
//        assertEquals("SpecialForces", u1.getUnitName());
//        assertEquals(3, u1.getLevel());
//        assertEquals(5, u1.getBonus());
//        assertEquals(4, u1.getMoveCost());
//        assertEquals(4, u1.getAttackCost());
//        assertEquals(12, u1.getUpgradeCost());
//        assertEquals(3, u1.getNumUnits());
//        u1.setNumUnits(5);
//        assertEquals(5, u1.getNumUnits());
//    }
//
//}
