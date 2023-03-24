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
        b0.initMap();
        b1.initMap();
        assertEquals(b0, b1);
        assertNotEquals(b0, "(player 1)");
        b1.getAllPlayers().get(0).getOwnedTerritories().get(0).addANeighbor(new Territory("x", 3));
        assertNotEquals(b0, b1);

    }

    @Test
    public void test_displayBoard() throws Exception {
        Board m1 = new RiskGameBoard();
        assertEquals("No players in the Board!\n", m1.displayBoard());
        m1.initMap();

        String expected = "red player:\n" +
                "---------------\n" +
                "5 units in a (next to: b, c)\n" +
                "5 units in c (next to: a, b, d, e, l)\n" +
                "5 units in g (next to: h, i, l)\n" +
                "5 units in h (next to: g, i, l)\n" +
                "5 units in i (next to: g, h, j, l)\n" +
                "5 units in l (next to: c, e, f, g, h, i)\n" +
                "\n" +
                "blue player:\n" +
                "---------------\n" +
                "5 units in b (next to: a, c, d)\n" +
                "5 units in d (next to: b, c, e)\n" +
                "5 units in e (next to: c, d, f, l)\n" +
                "5 units in f (next to: e, k, l)\n" +
                "5 units in j (next to: i, k)\n" +
                "5 units in k (next to: f, j)\n" +
                "\n";
        assertEquals(expected, m1.displayBoard());
    }
    
    @Test
    public void test_initMap() throws Exception {
        Board b1 = new RiskGameBoard();

        String expected = "red player:\n" +
                "---------------\n" +
                "5 units in a (next to: b, c)\n" +
                "5 units in c (next to: a, b, d, e, l)\n" +
                "5 units in g (next to: h, i, l)\n" +
                "5 units in h (next to: g, i, l)\n" +
                "5 units in i (next to: g, h, j, l)\n" +
                "5 units in l (next to: c, e, f, g, h, i)\n" +
                "\n" +
                "blue player:\n" +
                "---------------\n" +
                "5 units in b (next to: a, c, d)\n" +
                "5 units in d (next to: b, c, e)\n" +
                "5 units in e (next to: c, d, f, l)\n" +
                "5 units in f (next to: e, k, l)\n" +
                "5 units in j (next to: i, k)\n" +
                "5 units in k (next to: f, j)\n" +
                "\n";
        assertEquals(expected, b1.initMap());
    }

    @Test
    void test_tryAddTerritory() throws Exception {
        Board b = new RiskGameBoard();
        Territory t1 = new Territory("a", 1, 2);
        b.tryAddTerritory(t1);
        ArrayList<Territory> expected = new ArrayList<>();
        expected.add(t1);
        assertEquals(expected, b.getAllTerritories());

        assertEquals(false, b.tryAddTerritory(t1));


    }

    @Test
    void test_updataCombatResult() throws Exception {
        RiskGameBoard r = new RiskGameBoard();
        r.initMap();

        Player p1 = r.getAllPlayers().get(0);
        Player p2 = r.getAllPlayers().get(1);

        //To let player occupy
        Action m = new MoveAction("M", "c","a", new HashMap<>(1, 5));
        Action a = new AttackAction("A", "a", "b", new HashMap<>(1, 10));

        r.executeMove(m, 0);
        r.executeAttack(a, 0);

        Territory t_a =  p1.getTerr("a");

        Territory to_update = p1.getOwnedTerritories().get(0); //b
        to_update.updateCombatResult(2);
        assertEquals(-1, to_update.getWinnerId());
        p1.getOwnedTerritories().get(0).setWinnerId(2);
        assertEquals(2, p1.getOwnedTerritories().get(0).getWinnerId());

        p1.getOwnedTerritories().get(1).setWinnerId(1);
        assertEquals(1, p1.getOwnedTerritories().get(1).getWinnerId());

        for(Territory t: p1.getOwnedTerritories()){
            t.setWinnerId(1);
            assertEquals(0, t.getWinnerId());
        }

        r.updateCombatResult();
    }
}
