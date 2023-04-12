package edu.duke.ece651.team3.shared;

import org.checkerframework.checker.units.qual.A;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;

import static java.lang.System.out;
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
//
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
    units.add(new MasterSergeant(0));
    units.add(new FirstSergeant(0));
    units.add(new SergeantMajor(0));

    //To let player occupy j->a 1
    Action move = new MoveAction("j", "a", units);
    Action attack = new AttackAction("a", "b", units);

    Territory a = r.getAllPlayers().get(0).findOwnedTerritoryByName("a");
    Territory j = r.getAllPlayers().get(0).findOwnedTerritoryByName("j");

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
        assertEquals(1,b.checkWin());
        p1.tryOwnTerritory(territory);
        assertEquals(0,b.checkWin());

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
        assertTrue(b.checkAttack(action, p1));

        Action action1 = new AttackAction("a", "b", units);
        assertTrue(b.checkAttack(action1, p1));

    }

    @Test
    void getUpdateUnit() throws Exception {
        RiskGameBoard r = new RiskGameBoard();
        ArrayList<Unit> units = new ArrayList<>();
        units.add(new Private(1));
        units.add(new Corporal(0));
        units.add(new Specialist(0));
        units.add(new Sergeant(0));
        units.add(new MasterSergeant(0));
        units.add(new FirstSergeant(0));
        units.add(new SergeantMajor(0));

        assertEquals(1, r.getUpdatedUnits(units));
//        assertEquals(0, r.getStrongest(units));
//        assertEquals(0, r.getWeakest(units));

        ArrayList<Unit> units_empty = new ArrayList<>();
        assertEquals(-1, r.getStrongest(units_empty));
        assertEquals(-1, r.getWeakest(units_empty));

        ArrayList<Unit> units1 = new ArrayList<>();
        units1.add(new Private(0));
        units1.add(new Corporal(1));
        units1.add(new Specialist(0));
        units1.add(new Sergeant(0));
        units1.add(new MasterSergeant(0));
        units1.add(new FirstSergeant(0));
        units1.add(new SergeantMajor(0));
        assertEquals(1, r.getWeakest(units1));

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


    @Test
    void test_integra() throws Exception {
        RiskGameBoard b = new RiskGameBoard();
        b.initE2Map();

        ArrayList<Unit> units = new ArrayList<>();
        units.add(new Private(1));
        units.add(new Corporal(0));
        units.add(new Specialist(0));
        units.add(new Sergeant(0));
        units.add(new MasterSergeant(0));
        units.add(new FirstSergeant(0));
        units.add(new SergeantMajor(0));

        ArrayList<Unit> units1 = new ArrayList<>();
        units1.add(new Private(1));
        units1.add(new Corporal(0));
        units1.add(new Specialist(0));
        units1.add(new Sergeant(0));
        units1.add(new MasterSergeant(0));
        units1.add(new FirstSergeant(0));
        units1.add(new SergeantMajor(0));

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

    @Test
    public void test_checkUpgrade() throws Exception{
        RiskGameBoard r = new RiskGameBoard();
        r.initE2Map();

        Player p1 = r.getAllPlayers().get(0);

        out.println(r.displayBoard());
        ArrayList<Unit> unitsToChange = new ArrayList<>();
        unitsToChange.add(new Private(1));
        unitsToChange.add(new Corporal(0));
        unitsToChange.add(new Specialist(0));
        unitsToChange.add(new Sergeant(0));
        unitsToChange.add(new MasterSergeant(0));
        unitsToChange.add(new FirstSergeant(0));
        unitsToChange.add(new SergeantMajor(0));

        Action a2 = new UpgradeAction("a", "a", unitsToChange);
        assertTrue(r.checkUpgrade(a2, p1));


        ArrayList<Unit> unitsToChange1 = new ArrayList<>();
        unitsToChange1.add(new Private(-1));
        unitsToChange1.add(new Corporal(0));
        unitsToChange1.add(new Specialist(0));
        unitsToChange1.add(new Sergeant(0));
        unitsToChange1.add(new MasterSergeant(0));
        unitsToChange1.add(new FirstSergeant(0));
        unitsToChange1.add(new SergeantMajor(0));
        Action a1 = new UpgradeAction("a", "a", unitsToChange1);
        assertFalse(r.checkUpgrade(a1, p1));
        r.initSmallMap();
    }

    @Test
    public void test_addAfterEachTurn() throws Exception{
        RiskGameBoard r = new RiskGameBoard();
        r.initE2Map();
        Player p1 = r.getAllPlayers().get(0);

        r.addAfterEachTurn();

        assertEquals(6, p1.getOwnedTerritories().get(0).getNumUnits());
        assertEquals(20, p1.getOwnedTerritories().get(0).getFood());
        assertEquals(60, p1.getOwnedTerritories().get(0).getTech());
    }

    @Test
    public void test_extcuteUpgrades() throws Exception{
        RiskGameBoard r = new RiskGameBoard();
        r.initE2Map();
        Player p1 = r.getAllPlayers().get(0);

        ArrayList<Unit> units = new ArrayList<>();
        units.add(new Private(1));
        units.add(new Corporal(0));
        units.add(new Specialist(0));
        units.add(new Sergeant(0));
        units.add(new MasterSergeant(0));
        units.add(new FirstSergeant(0));
        units.add(new SergeantMajor(0));

        ArrayList<Action> actions = new ArrayList<>();
        Action a = new UpgradeAction("a", "a", units);
        actions.add(a);
        Action b = new MoveAction("a", "j", units);
        actions.add(b);

        HashMap<Integer, ArrayList<Action>> list =  new HashMap<Integer, ArrayList<Action>>();
        list.put(0, actions);

        r.executeUpgrades(list);
        Territory aT = p1.findOwnedTerritoryByName("a");
        assertEquals(1, aT.getUnits().get(1).getNumUnits());

        assertEquals(7, aT.getTech());
    }

    @Test
    public void test_display() throws Exception{
        RiskGameBoard r = new RiskGameBoard();
        String exp = "No players in the Board!\n";
        String s = r.displayBoard();
//        out.println(s);

        assertEquals(exp, s);

    }
}
