package edu.duke.ece651.team3.shared;

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
        Territory t1 = new Territory("a", 4, 0, 0);
        Territory t2 = new Territory("b", 5, 0, 0);
        Territory t3 = new Territory("c", 6, 0, 0);
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
        Territory t1 = new Territory("Oz", 2, 0, 0);
        ts2.add(t1);
        Player p2 = new Player(1, "yellow", 3, ts2);
        assertEquals(ts2, p2.getOwnedTerritories());
    }

    @Test
    void test_tryOwnTerritory() {
        Player p1 = new Player(1, "blue", 3);
        Territory t1 = new Territory("Oz", 2, 0, 0);
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
        Territory t1 = new Territory("Oz", 2, 0, 0);
        assertTrue(p1.isValidToOwn(t1));
        p1.getOwnedTerritories().add(t1);
        assertFalse(p1.isValidToOwn(t1));
    }
//
//    @Test
//    void test_displayPlayer() {
//        ArrayList<Territory> ts1 = new ArrayList<>();
//        Player p1 = new Player(1, "green", 3, ts1);
//        String expected1 = "green player:\n---------------\nno territories\n";
//        assertEquals(expected1, p1.displayPlayer());
//        Territory t1 = new Territory("Oz", 1, 2);
//        ts1.add(t1);
//        String expected2 = "green player:\n---------------\n2 units in Oz (no neighbors)\n";
//        assertEquals(expected2, p1.displayPlayer());
//    }

    @Test
    void test_findOwnTerritoryByName() throws Exception {
        RiskGameBoard r = new RiskGameBoard();
        r.initE2Map();
        Player p1 = r.getAllPlayers().get(1);
        assertNull(p1.findOwnedTerritoryByName("a"));


        Territory b = p1.getOwnedTerritories().get(0);
        assertEquals(b, p1.findOwnedTerritoryByName("b"));
    }
//
//        p.loseTerritory(toLose);
//        p.loseTerritory(notContain);
//        assertEquals(expected_remain, p.getOwnedTerritories());
//
//        Territory defenderT = p.getOwnedTerritories().get(2); //e
//        Player attacker = r.getAllPlayers().get(0);
//        //attacker.occupyTerritory(defenderT, 1, 1);
//        boolean isContain = false;
//        for(Territory territory: attacker.getOwnedTerritories()){
//            if(territory.equals(defenderT)){
//                isContain = true;
//            }
//        }
//        assertEquals(true, isContain);
//        assertEquals(5, defenderT.getNumUnits());
//
//    }
//
@Test
void test_executeMove() throws Exception {
    RiskGameBoard r = new RiskGameBoard();
    r.initE2Map();

    String srcName = r.getAllPlayers().get(0).getOwnedTerritories().get(0).getTerritoryName();
    String dstName = r.getAllPlayers().get(0).getOwnedTerritories().get(1).getTerritoryName();
    Player curr = r.getAllPlayers().get(0);

    ArrayList<Unit> newUnit = new ArrayList<>();
    newUnit.add(new Private(1));
    newUnit.add(new Corporal(0));
    newUnit.add(new Specialist(0));
    newUnit.add(new Sergeant(0));
    newUnit.add(new MasterSergeant(0));
    newUnit.add(new FirstSergeant(0));
    newUnit.add(new SergeantMajor(0));

    Action action = new MoveAction(srcName, dstName, newUnit);
    curr.executeMove(action);
    Territory src = curr.findOwnedTerritoryByName(srcName);
    Territory dst = curr.findOwnedTerritoryByName(dstName);
    assertEquals(4, src.getNumUnits());
    assertEquals(6, dst.getNumUnits());
    assertNotNull(curr.findOwnedTerritoryByName(dstName));

}
//
@Test
void test_addAUnitForAll() throws Exception {
    RiskGameBoard r = new RiskGameBoard();
    r.initE2Map();

    ArrayList<Unit> unitsToChange = new ArrayList<>();
    unitsToChange.add(new Private(1));
    unitsToChange.add(new Corporal(0));
    unitsToChange.add(new Specialist(0));
    unitsToChange.add(new Sergeant(0));

    Player curr = r.getAllPlayers().get(0);

    //Testing addUnitForEachTerr
    curr.addAUnitForEachTerr();
    for(Territory territory : curr.getOwnedTerritories()){
        assertEquals(6, territory.getNumUnits());
    }
//    r.addAUnitEachTurn();
    for(Territory territory : curr.getOwnedTerritories()){
        assertEquals(7, territory.getNumUnits());
    }
    r.updateCombatResult();

    Player testPlayer = new Player(2, "Black", 0);
    r.addPlayer(testPlayer);
    testPlayer.findOwnedTerritoryByName("a");
}
//
    @Test
public void test_ExecuteAttack() throws Exception {
    RiskGameBoard r = new RiskGameBoard();
    r.initE2Map();
    Player p1 = r.getAllPlayers().get(0);
    Player p2 = r.getAllPlayers().get(1);

    ArrayList<Unit> newUnit = new ArrayList<>();
    newUnit.add(new Private(1));
    newUnit.add(new Corporal(0));
    newUnit.add(new Specialist(0));
    newUnit.add(new Sergeant(0));

    Territory currAtt = p1.getOwnedTerritories().get(0);
    Territory currDef = p2.getOwnedTerritories().get(0);
    currAtt.setAttackerUnits(newUnit);
    currAtt.setUnits(newUnit);
    currDef.setAttackerUnits(newUnit);
    currDef.setUnits(newUnit);


    AttackAction attack = new AttackAction(
            p1.getOwnedTerritories().get(0).getTerritoryName(),
            p2.getOwnedTerritories().get(0).getTerritoryName(), newUnit);

    p1.executeAttack(attack);
}
    @Test
    void test_loseTerritory() throws Exception {
        RiskGameBoard r = new RiskGameBoard();
        Territory a = new Territory("a", 5, 10, 10);
        Territory b = new Territory("b", 5, 10, 10);
        Territory c = new Territory("c", 5, 10, 10);
        Territory d = new Territory("d", 5, 10, 10);

        r.connectNeighbors(a, b, 1);
        r.connectNeighbors(b, c, 1);
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

        p1.loseTerritory(a); //Contains
        p1.loseTerritory(b); //Not contains
        ArrayList<Territory> expList_p1 = new ArrayList<>();
        expList_p1.add(c);
        assertEquals(expList_p1, player1T);
    }
}