package edu.duke.ece651.team3.shared;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class RiskGameBoardTest {
    @Test
    public void test_allPlayers() throws Exception {
        Board b0 = new RiskGameBoard();
        Board b1 = new RiskGameBoard();
        assertEquals(b0.getAllPlayers(), b1.getAllPlayers());
    }

    @Test
    public void test_equals() throws Exception {
        Board b0 = new RiskGameBoard();
        Board b1 = new RiskGameBoard();
        b0.initE2Map();
        b1.initE2Map();
//        assertEquals(b0, b1);
        assertNotEquals(b0, "(player 1)");
        b1.getAllPlayers().get(0).getOwnedTerritories().get(0).addANeighbor(new Territory("x"), 1);
        assertNotEquals(b0, b1);

    }

    @Test
    public void test_displayBoard() throws Exception {
        RiskGameBoard m1 = new RiskGameBoard();
        assertEquals("No players in the Board!\n", m1.displayBoard());
        m1.initE2Map();

        String expected = "orange player:\n" +
                "---------------\n" +
                "5 units in a (next to: b（1), c（2), j（3))\n" +
                "5 units in g (next to: i（3), h（1), l（2), f（2))\n" +
                "5 units in h (next to: g（1), j（2), l（1))\n" +
                "5 units in i (next to: g（3), k（2), f（3))\n" +
                "5 units in j (next to: c（1), a（3), h（2), l（3))\n" +
                "5 units in l (next to: g（2), c（2), e（3), h（1), j（3), f（1))\n" +
                "\n" +
                "blue player:\n" +
                "---------------\n" +
                "5 units in b (next to: d（1), c（2), a（1))\n" +
                "5 units in c (next to: d（2), b（2), a（2), e（3), l（2), j（1))\n" +
                "5 units in d (next to: b（1), c（2), e（2))\n" +
                "5 units in e (next to: d（2), c（3), l（3), f（2))\n" +
                "5 units in f (next to: g（2), i（3), k（2), e（2), l（1))\n" +
                "5 units in k (next to: i（2), f（2))\n\n";
        System.out.println(m1.displayBoard());
        m1.initSmallMap();
//        assertEquals(expected , m1.displayBoard()); //TODO: HashMap unordered
    }


    @Test
    void test_tryAddTerritory() throws Exception {
        Board b = new RiskGameBoard();
        Territory t1 = new Territory("a");
        b.tryAddTerritory(t1);
        ArrayList<Territory> expected = new ArrayList<>();
        expected.add(t1);
        assertEquals(expected, b.getAllTerritories());
        assertEquals(false, b.tryAddTerritory(t1));
    }

    @Test
    void test_updataCombatResult() throws Exception {
        RiskGameBoard r = new RiskGameBoard();
        r.initE2Map();

        Player p1 = r.getAllPlayers().get(0);
        ArrayList<Unit> units = new ArrayList<>();
        units.add(new Private(1));
        units.add(new Corporal(0));
        units.add(new Specialist(0));
        units.add(new Sergeant(0));

        //To let player occupy j->a 1
        Action move = new MoveAction("j", "a", units);
        Action attack = new AttackAction("a", "b", units);

        Territory a = r.getAllPlayers().get(0).getTerr("a");
        Territory j = r.getAllPlayers().get(0).getTerr("j");

        r.executeMove(move, 0);
        assertEquals(6, a.getNumUnits());
        assertEquals(4, j.getNumUnits());
        r.executeAttack(attack, 0);

        Territory to_update = p1.getOwnedTerritories().get(0); //b
        to_update.updateCombatResult(2);
        assertEquals(-1, to_update.getWinnerId());
        p1.getOwnedTerritories().get(0).setWinnerId(2);
        assertEquals(2, p1.getOwnedTerritories().get(0).getWinnerId());

        p1.getOwnedTerritories().get(1).setWinnerId(1);
        assertEquals(1, p1.getOwnedTerritories().get(1).getWinnerId());

        for (Territory t : p1.getOwnedTerritories()) {
            t.setWinnerId(1);
            assertEquals(1, t.getWinnerId());
        }
        r.updateCombatResult();
    }

    @Test
    void test_updateCombatResult2() throws Exception {
        RiskGameBoard b = new RiskGameBoard();
        b.initE2Map();
        Player p1 = b.getAllPlayers().get(0);
        p1.getOwnedTerritories().get(0).setWinnerId(0);
        b.updateCombatResult();
    }

    @Test
    void test_checkWin() throws Exception {
        RiskGameBoard b = new RiskGameBoard();
        Player p1 = new Player(0, "red", 0);
        Player p2 = new Player(1, "blue", 0);
        b.getAllPlayers().add(p1);
        b.getAllPlayers().add(p2);
        Territory territory = new Territory("a");
        assertEquals(1, b.checkWin());
        p1.tryOwnTerritory(territory);
        assertEquals(0, b.checkWin());

        RiskGameBoard b1 = new RiskGameBoard();
        assertEquals(2, b1.checkWin());
    }


    @Test
    void test_checkAttack() throws Exception {
        RiskGameBoard b = new RiskGameBoard();
        b.initE2Map();
        Player p1 = b.getAllPlayers().get(0);

        ArrayList<Unit> units = new ArrayList<>();
        units.add(new Private(1));
        units.add(new Corporal(0));
        units.add(new Specialist(0));
        units.add(new Sergeant(0));
        units.add(new FirstSergeant(0));
        units.add(new Sergeant(0));
        units.add(new MasterSergeant(0));
        units.add(new SergeantMajor(0));

        Action action = new AttackAction("a", "c", units);
        assertFalse(b.checkAttack(action, p1));

        Action action1 = new AttackAction("a", "b", units);
        assertFalse(b.checkAttack(action1, p1));

    }

    @Test
    void getUpdateUnit() throws Exception {
        RiskGameBoard r = new RiskGameBoard();
        ArrayList<Unit> units = new ArrayList<>();
        units.add(new Private(1));
        units.add(new Corporal(0));
        units.add(new Specialist(0));
        units.add(new Sergeant(0));

        assertEquals(1, r.getUpdatedUnits(units));
        assertEquals(0, r.getStrongest(units));
        assertEquals(0, r.getWeakest(units));

        ArrayList<Unit> units_empty = new ArrayList<>();
        assertEquals(-1, r.getStrongest(units_empty));
        assertEquals(-1, r.getWeakest(units_empty));

    }

    @Test
    void test_executeAttack() throws Exception {
        RiskGameBoard b = new RiskGameBoard();
        b.initE2Map();
        //Player p1 = b.getAllPlayers().get(0);
        Player p2 = b.getAllPlayers().get(1);
        ArrayList<Unit> units = b.initializeArrUnits();
        units.get(0).setNumUnits(2);
        Action action1 = new Action("A", "b", "a", units);
        b.executeAttack(action1, p2);
        assertEquals(2, b.getAllPlayers().size());
    }

    @Test
    void test_executeAttacks() throws Exception {
        RiskGameBoard b = new RiskGameBoard();
        b.initE2Map();
        Player p1 = b.getAllPlayers().get(0);
        Player p2 = b.getAllPlayers().get(1);
        p1.findOwnedTerritoryByName("a").setFood(100);
        p2.findOwnedTerritoryByName("b").setFood(100);
        ArrayList<Unit> units = b.initializeArrUnits();
        units.get(0).setNumUnits(2);
        Action action = new Action("A", "a", "b", units);
        Action action1 = new Action("A", "b", "a", units);

        //b.executeAttack(action1, p2);

        ArrayList<Action> arrayList = new ArrayList<>();
        arrayList.add(action);
        ArrayList<Action> arrayList1 = new ArrayList<>();
        arrayList1.add(action1);

        HashMap<Integer, ArrayList<Action>> attacksMap = new HashMap<>();
        attacksMap.put(0, arrayList);
        attacksMap.put(1, arrayList1);
        b.executeAttacks(attacksMap);
        assertEquals(2, b.getAllPlayers().size());
    }
}
//
//    @Test
//    void test_multipleAttacks() throws Exception {
//        RiskGameBoard b = new RiskGameBoard();
//        b.initMap();
////        Player p1 = b.getAllPlayers().get(0);
////        Player p2 = b.getAllPlayers().get(1);
//
//        HashMap<Integer, Integer> units = new HashMap<>();
//        units.put(1, 1);
//
//        Action action1 = new Action("A", "a", "b", units);
//        Action action2 = new Action("A", "c", "b", units);
//        Action invalidAttack = new Action("A", "a", "c", units);
//
//        ArrayList<Action> attacks0 = new ArrayList<>();
//        ArrayList<Action> attacks1 = new ArrayList<>();
//        attacks0.add(action1);
//        attacks0.add(action2);
//        attacks0.add(invalidAttack);
//
//        HashMap<Integer, ArrayList<Action>> attacksMap = new HashMap<>();
//        attacksMap.put(0, attacks0);
//        attacksMap.put(1, attacks1);
//        b.executeAttacks(attacksMap);
//        assertEquals(2, b.getAllPlayers().size());
//    }
//
    @Test
    void test_integra() throws Exception {
        RiskGameBoard b = new RiskGameBoard();
        b.initE2Map();

        ArrayList<Unit> units = new ArrayList<>();
        units.add(new Private(1));
        units.add(new Corporal(0));
        units.add(new Specialist(0));
        units.add(new Sergeant(0));

        ArrayList<Unit> units1 = new ArrayList<>();
        units1.add(new Private(1));
        units1.add(new Corporal(0));
        units1.add(new Specialist(0));
        units1.add(new Sergeant(0));

        Action action1 = new Action("A", "a", "b", units);
        Action action2 = new Action("A", "c", "b", units);
        Action invalidAttack = new Action("A", "a", "c", units);
        Action NonContainsAttackDst = new Action("A", "c", "e", units);
        //Action unitNoContainKey = new Action("A", "a", "b", units1);

        ArrayList<Action> attacks0 = new ArrayList<>();
        ArrayList<Action> attacks1 = new ArrayList<>();
        attacks0.add(action1);
        attacks0.add(action2);
        attacks0.add(invalidAttack);
        attacks0.add(NonContainsAttackDst);
//        attacks0.add(unitNoContainKey);

        HashMap<Integer, ArrayList<Action>> attacksMap = new HashMap<>();
        attacksMap.put(0, attacks0);
        attacksMap.put(1, attacks1);
        b.executeAttacks(attacksMap);
        assertEquals(2, b.getAllPlayers().size());
    }


}
