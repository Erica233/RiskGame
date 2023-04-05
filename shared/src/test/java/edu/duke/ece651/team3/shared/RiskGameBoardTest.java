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
        assertEquals(b0, b1);
        assertNotEquals(b0, "(player 1)");
        b1.getAllPlayers().get(0).getOwnedTerritories().get(0).addANeighbor(new Territory("x", 3, 0, 0), 1);
        assertNotEquals(b0, b1);
    }
//
//    @Test
//    public void test_displayBoard() throws Exception {
//        Board m1 = new RiskGameBoard();
//        assertEquals("No players in the Board!\n", m1.displayBoard());
//        m1.initE2Map();
//
//        String expected = "red player:\n" +
//                "---------------\n" +
//                "5 units in a (next to: b, c)\n" +
//                "5 units in c (next to: a, b, d, e, l)\n" +
//                "5 units in g (next to: h, i, l)\n" +
//                "5 units in h (next to: g, i, l)\n" +
//                "5 units in i (next to: g, h, j, l)\n" +
//                "5 units in l (next to: c, e, f, g, h, i)\n" +
//                "\n" +
//                "blue player:\n" +
//                "---------------\n" +
//                "5 units in b (next to: a, c, d)\n" +
//                "5 units in d (next to: b, c, e)\n" +
//                "5 units in e (next to: c, d, f, l)\n" +
//                "5 units in f (next to: e, k, l)\n" +
//                "5 units in j (next to: i, k)\n" +
//                "5 units in k (next to: f, j)\n" +
//                "\n";
//        assertEquals(expected, m1.displayBoard());
//    }
//
//    @Test
//    void test_tryAddTerritory() throws Exception {
//        Board b = new RiskGameBoard();
//        Territory t1 = new Territory("a", 1, 2);
//        b.tryAddTerritory(t1);
//        ArrayList<Territory> expected = new ArrayList<>();
//        expected.add(t1);
//        assertEquals(expected, b.getAllTerritories());
//
//        assertEquals(false, b.tryAddTerritory(t1));
//
//
//    }
//
//    @Test
//    void test_updataCombatResult() throws Exception {
//        RiskGameBoard r = new RiskGameBoard();
//        r.initMap();
//
//        Player p1 = r.getAllPlayers().get(0);
//
//        //To let player occupy
//        Action m = new MoveAction("M", "c","a", new HashMap<>(1, 5));
//        Action a = new AttackAction("A", "a", "b", new HashMap<>(1, 10));
//
//        r.executeMove(m, 0);
//        r.executeAttack(a, 0);
//
//        Territory t_a =  p1.getTerr("a");
//
//        Territory to_update = p1.getOwnedTerritories().get(0); //b
//        to_update.updateCombatResult(2);
//        assertEquals(-1, to_update.getWinnerId());
//        p1.getOwnedTerritories().get(0).setWinnerId(2);
//        assertEquals(2, p1.getOwnedTerritories().get(0).getWinnerId());
//
//        p1.getOwnedTerritories().get(1).setWinnerId(1);
//        assertEquals(1, p1.getOwnedTerritories().get(1).getWinnerId());
//
//        for(Territory t: p1.getOwnedTerritories()){
//            t.setWinnerId(1);
//            assertEquals(1, t.getWinnerId());
//        }
//
//        r.updateCombatResult();
//    }
//
//    @Test
//    void test_updateCombatResult2() throws Exception {
//        RiskGameBoard b = new RiskGameBoard();
//        b.initMap();
//        Player p1 = b.getAllPlayers().get(0);
//        p1.getOwnedTerritories().get(0).setWinnerId(0);
//        b.updateCombatResult();
//    }
//
    @Test
    void test_checkWin() throws Exception {
        RiskGameBoard b = new RiskGameBoard();
        Player p1 = new Player(0, "red", 0);
        Player p2 = new Player(1, "blue", 0);
        b.getAllPlayers().add(p1);
        b.getAllPlayers().add(p2);
        Territory territory = new Territory("a");
        assertEquals(1,b.checkWin());
        p1.tryOwnTerritory(territory);
        assertEquals(0,b.checkWin());

        RiskGameBoard b1 = new RiskGameBoard();
        assertEquals(2, b1.checkWin());
    }
//
//
//    @Test
//    void test_checkAttack() throws Exception {
//        RiskGameBoard b = new RiskGameBoard();
//        b.initMap();
//        Player p1 = b.getAllPlayers().get(0);
//        HashMap<Integer, Integer> hashmapHelper = new HashMap<>();
//        hashmapHelper.put(1, 1);
//        Action action = new Action("A", "A","C", hashmapHelper);
//        assertFalse(b.checkAttack(action, p1));
//
//        Action action1 = new Action("A", "a","b", hashmapHelper);
//        assertTrue(b.checkAttack(action1, p1));
//
//    }
//
//    @Test
//    void test_executeAttack() throws Exception {
//        RiskGameBoard b = new RiskGameBoard();
//        b.initMap();
//        Player p1 = b.getAllPlayers().get(0);
//        Player p2 = b.getAllPlayers().get(1);
//        HashMap<Integer, Integer> hashmapHelper = new HashMap<>();
//        hashmapHelper.put(1, 5);
//        Action action = new Action("M", "a", "c", hashmapHelper);
//        p1.executeMove(action);
//        Action action1 = new Action("A", "b", "a", hashmapHelper);
//        b.executeAttack(action1, p2);
//        assertEquals(2, b.getAllPlayers().size());
//    }
//
//    @Test
//    void test_executeAttacks() throws Exception {
//        RiskGameBoard b = new RiskGameBoard();
//        b.initMap();
////        Player p1 = b.getAllPlayers().get(0);
////        Player p2 = b.getAllPlayers().get(1);
//
//        HashMap<Integer, Integer> hashmapHelper = new HashMap<>();
//        hashmapHelper.put(1, 1);
////        Action action = new Action("M", "a", "c", hashmapHelper);
////        //p1.executeMove(action);
//
//        Action action1 = new Action("A", "b", "d", hashmapHelper);
//        //b.executeAttack(action1, p2);
//
//        ArrayList<Action> arrayList = new ArrayList<>();
//        //arrayList.add(action);
//        ArrayList<Action> arrayList1 = new ArrayList<>();
//        arrayList1.add(action1);
//
//        HashMap<Integer, ArrayList<Action>> attacksMap = new HashMap<>();
//        attacksMap.put(0, arrayList);
//        attacksMap.put(1, arrayList1);
//        b.executeAttacks(attacksMap);
//        assertEquals(2, b.getAllPlayers().size());
//    }
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
//    @Test
//    void test_integra() throws Exception {
//        RiskGameBoard b = new RiskGameBoard();
//        b.initMap();
////        Player p1 = b.getAllPlayers().get(0);
////        Player p2 = b.getAllPlayers().get(1);
//
//        HashMap<Integer, Integer> units = new HashMap<>();
//        units.put(1, 1);
//
//        HashMap<Integer, Integer> units1 = new HashMap<>();
//        units1.put(2, 1);
//
//        Action action1 = new Action("A", "a", "b", units);
//        Action action2 = new Action("A", "c", "b", units);
//        Action invalidAttack = new Action("A", "a", "c", units);
//        Action NonContainsAttackDst = new Action("A", "c", "e", units);
//        //Action unitNoContainKey = new Action("A", "a", "b", units1);
//
//        ArrayList<Action> attacks0 = new ArrayList<>();
//        ArrayList<Action> attacks1 = new ArrayList<>();
//        attacks0.add(action1);
//        attacks0.add(action2);
//        attacks0.add(invalidAttack);
//        attacks0.add(NonContainsAttackDst);
////        attacks0.add(unitNoContainKey);
//
//        HashMap<Integer, ArrayList<Action>> attacksMap = new HashMap<>();
//        attacksMap.put(0, attacks0);
//        attacksMap.put(1, attacks1);
//        b.executeAttacks(attacksMap);
//        assertEquals(2, b.getAllPlayers().size());
//    }
//
//    @Test
//    public void test_smallMap() throws Exception {
//        RiskGameBoard r = new RiskGameBoard();
//        r.initSmallMap();
////        System.out.println(r.initSmallMap());
//        String expected = "red player:\n" +
//                "---------------\n" +
//                "10 units in a (next to: b, c, d)\n" +
//                "10 units in c (next to: a, b, d)\n" +
//                "\n" +
//                "blue player:\n" +
//                "---------------\n" +
//                "1 units in b (next to: a, c)\n" +
//                "1 units in d (next to: a, c)\n" +
//                "\n";
//        assertEquals(expected, r.initSmallMap());
//    }
}
