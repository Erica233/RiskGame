package edu.duke.ece651.team3.shared;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class TerritoryTest {
    @Test
    public void test_name_numUnits() {
        Territory t1 = new Territory("Narnia", 3);
        assertEquals("Narnia", t1.getName());
        assertNotEquals("Narnias", t1.getName());
        assertEquals(3, t1.getNumUnits());
        assertNotEquals(4, t1.getNumUnits());
    }
    @Test
    public void test_isValidToAdd() {
        Territory t1 = new Territory("Narnia", 3);
        assertTrue(t1.isValidToAdd());
    }

}
