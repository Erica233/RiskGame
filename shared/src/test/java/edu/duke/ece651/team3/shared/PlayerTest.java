package edu.duke.ece651.team3.shared;

import org.checkerframework.checker.units.qual.A;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {
    @Test
    public void test_equals() {
        Player p0 = new Player(1, "red", 10);
        Player p1 = new Player(1, "red", 10);
        Player p2 = new Player(2, "red", 10);
        Player p3 = new Player(1, "blue", 10);
        Player p4 = new Player(1, "red", 11);
        assertEquals(p0, p1);
        assertNotEquals(p0, "(1, red, 10)"); //different objects
        assertNotEquals(p0, p2); // different id
        assertNotEquals(p0, p3); // different color
        assertNotEquals(p0, p4); // different totNumUnits
        Territory t1 = new Territory("a", 4);
        Territory t2 = new Territory("b", 5);
        Territory t3 = new Territory("c", 6);
        p0.tryOwnTerritory(t1);
        p0.tryOwnTerritory(t2);
        p0.tryOwnTerritory(t3);
        assertNotEquals(p0, p1); //different ownedTerritories
        p1.tryOwnTerritory(t1);
        p1.tryOwnTerritory(t3);
        p1.tryOwnTerritory(t2);
        assertNotEquals(p0, p1); //ownedTerritories different orders
    }

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
    void test_tryOwnTerritory() {
        Player p1 = new Player(1, "blue", 3);
        Territory t1 = new Territory("Oz", 2);
        ArrayList<Territory> ts1 = new ArrayList<>();
        assertEquals(ts1, p1.getOwnedTerritories());

        assertTrue(p1.tryOwnTerritory(t1));
        ts1.add(t1);
        assertEquals(ts1, p1.getOwnedTerritories());
        assertFalse(p1.tryOwnTerritory(t1));
    }

    @Test
    void test_isValidToOwn() {
        Player p1 = new Player(1, "blue", 3);
        Territory t1 = new Territory("Oz", 2);
        assertTrue(p1.isValidToOwn(t1));
        p1.getOwnedTerritories().add(t1);
        assertFalse(p1.isValidToOwn(t1));
    }

    @Test
    void test_displayPlayer() {
        ArrayList<Territory> ts1 = new ArrayList<>();
        Player p1 = new Player(1, "green", 3, ts1);
        String expected1 = "green player:\n---------------\nno territories\n";
        assertEquals(expected1, p1.displayPlayer());
        Territory t1 = new Territory("Oz", 1, 2);
        ts1.add(t1);
        String expected2 = "green player:\n---------------\n2 units in Oz (no neighbors)\n";
        assertEquals(expected2, p1.displayPlayer());
    }
    @Test
    void test_findOwnTerritoryByName() throws Exception {
        RiskGameBoard r = new RiskGameBoard();
        r.initMap();
        Player p = r.getAllPlayers().get(1);
        Territory t = p.getOwnedTerritories().get(0);
        assertNull(p.findOwnedTerritoryByName("a"));

        Territory toLose = p.getOwnedTerritories().get(0);
        Territory notContain = r.getAllPlayers().get(0).getOwnedTerritories().get(0);

        ArrayList<Territory> expected_remain = new ArrayList<>();
        for(int i = 0; i < p.getOwnedTerritories().size(); i++){
            if(!p.getOwnedTerritories().get(i).equals(toLose)){
                expected_remain.add(p.getOwnedTerritories().get(i));
            }
        }

        p.loseTerritory(toLose);
        p.loseTerritory(notContain);
        assertEquals(expected_remain, p.getOwnedTerritories());

        Territory defenderT = p.getOwnedTerritories().get(2); //e
        Player attacker = r.getAllPlayers().get(0);
        attacker.occupyTerritory(defenderT, 1, 1);
        boolean isContain = false;
        for(Territory territory: attacker.getOwnedTerritories()){
            if(territory.equals(defenderT)){
                isContain = true;
            }
        }
        assertEquals(true, isContain);
        assertEquals(5, defenderT.getNumUnits());

    }

    @Test
    void test_executeMove() throws Exception {
        RiskGameBoard r = new RiskGameBoard();
        r.initMap();

        String srcName = r.getAllPlayers().get(0).getOwnedTerritories().get(0).getTerritoryName();
        String dstName = r.getAllPlayers().get(0).getOwnedTerritories().get(1).getTerritoryName();
        HashMap<Integer, Integer> units = new HashMap<>();
        units.put(1, 1);
        Player curr = r.getAllPlayers().get(0);
        Player test = new Player(2, "Black", 3);

        Action action = new MoveAction("M", srcName, dstName, units);
        curr.executeMove(action);
        Territory src = curr.getTerr(srcName);
        Territory dst = curr.getTerr(dstName);
        assertEquals(4, src.getNumUnits());
        assertEquals(6, dst.getNumUnits());
        assertNotNull( curr.getTerr(dstName));



    }

    @Test
    void test_addAUnitForAll() throws Exception {
        RiskGameBoard r = new RiskGameBoard();
        r.initMap();

        String srcName = r.getAllPlayers().get(0).getOwnedTerritories().get(0).getTerritoryName();
        String dstName = r.getAllPlayers().get(0).getOwnedTerritories().get(1).getTerritoryName();
        HashMap<Integer, Integer> units = new HashMap<>();
        units.put(1, 1);
        Player curr = r.getAllPlayers().get(0);

        //Testing addUnitForEachTerr
        curr.addAUnitForEachTerr();
        for(Territory territory : curr.getOwnedTerritories()){
            assertEquals(6, territory.getNumUnits());
        }
        r.addAUnitEachTurn();
        for(Territory territory : curr.getOwnedTerritories()){
            assertEquals(7, territory.getNumUnits());
        }
        r.updateCombatResult();

        Player testPlayer = new Player(2, "Black", 0);
        r.addPlayer(testPlayer);
        testPlayer.getTerr("a");

    }

    @Test
    public void test_ExecuteAttack() throws Exception {
        RiskGameBoard r = new RiskGameBoard();
        r.initMap();
        Player p1 = r.getAllPlayers().get(0);
        Player p2 = r.getAllPlayers().get(1);
        HashMap<Integer, Integer> newUnit = new HashMap<>();
        newUnit.put(1, 1);
        newUnit.put(2, 1);

        Territory currAtt = p1.getOwnedTerritories().get(0);
        Territory currDef = p2.getOwnedTerritories().get(0);
        currAtt.setAttackerUnits(newUnit);
        currAtt.setUnits(newUnit);
        currDef.setAttackerUnits(newUnit);
        currDef.setUnits(newUnit);


        AttackAction attack = new AttackAction("A",
                p1.getOwnedTerritories().get(0).getTerritoryName(),
                p2.getOwnedTerritories().get(0).getTerritoryName(), newUnit);

        p1.executeAttack(attack);
    }
}