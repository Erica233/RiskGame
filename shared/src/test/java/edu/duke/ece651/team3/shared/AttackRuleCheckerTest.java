package edu.duke.ece651.team3.shared;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

public class AttackRuleCheckerTest {


    @Test
    public void test_checkSrcDst() throws Exception {
        RiskGameBoard r = new RiskGameBoard();
        r.initE2Map();
        Player p = r.getAllPlayers().get(0);
        ArrayList<Unit> actionUnits = r.initializeArrUnits();
        Action myAction = new AttackAction("a", "b", actionUnits);
        Action myAction1 = new AttackAction("a", "j", actionUnits);
        Action myAction2 = new AttackAction("a", "i", actionUnits);
        Action myAction3 = new AttackAction("a", "f", actionUnits);
        AttackRuleChecker attackRuleChecker = new AttackRuleChecker(myAction, r);
        AttackRuleChecker attackRuleChecker1 = new AttackRuleChecker(myAction1, r);

        assertTrue(attackRuleChecker.checkSrcDst(myAction, p));
        assertFalse(attackRuleChecker1.checkSrcDst(myAction1, p));

        Player p1 = r.getAllPlayers().get(1);
        attackRuleChecker = new AttackRuleChecker(myAction2, r);
        attackRuleChecker1 = new AttackRuleChecker(myAction3, r);
        assertFalse(attackRuleChecker.checkSrcDst(myAction2, p1));
        assertFalse(attackRuleChecker1.checkSrcDst(myAction3, p1));

    }
    @Test
    public void test_checkPath() throws Exception {
        RiskGameBoard r = new RiskGameBoard();
        r.initE2Map();
        Player p = r.getAllPlayers().get(0);
        ArrayList<Unit> actionUnits = r.initializeArrUnits();
        Action myAction = new AttackAction("a", "b", actionUnits);
        AttackRuleChecker attackRuleChecker = new AttackRuleChecker(myAction, r);
        assertTrue(attackRuleChecker.checkPath(myAction, r, p));
    }

    @Test
    public void test_checkNumUnits() throws Exception {
        RiskGameBoard r = new RiskGameBoard();
        r.initE2Map();
        Player p = r.getAllPlayers().get(0);

        ArrayList<Unit> actionUnits = r.initializeArrUnits();
        actionUnits.get(0).setNumUnits(10);
        Action myAction = new AttackAction("a", "b", actionUnits);
        AttackRuleChecker attackRuleChecker = new AttackRuleChecker(myAction, r);
        assertFalse(attackRuleChecker.checkNumUnits(myAction, p));

        ArrayList<Unit> actionUnits1= r.initializeArrUnits();
        actionUnits1.get(0).setNumUnits(-1);
        Action myAction1 = new AttackAction("a", "b", actionUnits1);
        attackRuleChecker = new AttackRuleChecker(myAction1, r);
        assertFalse(attackRuleChecker.checkNumUnits(myAction1, p));

        ArrayList<Unit> actionUnits2= r.initializeArrUnits();
        actionUnits2.get(0).setNumUnits(3);
        Action myAction2 = new AttackAction("a", "b", actionUnits2);
        attackRuleChecker = new AttackRuleChecker(myAction2, r);
        assertTrue(attackRuleChecker.checkNumUnits(myAction2, p));

    }

    @Test
    public void test_findTerritory() throws Exception {
        RiskGameBoard r = new RiskGameBoard();
        r.initE2Map();
        Player currPlayer = r.getAllPlayers().get(0);
        ArrayList<Unit> actionUnits= r.initializeArrUnits();
        Action a = new AttackAction("m", "b", actionUnits);
        AttackRuleChecker arc = new AttackRuleChecker(a, r);
        arc.findTerritory(a, currPlayer);
    }


    @Test
    public void test_checkResources() throws Exception {
        RiskGameBoard r = new RiskGameBoard();
        r.initE2Map();
        Player currPlayer = r.getAllPlayers().get(0);
        ArrayList<Unit> actionUnits= r.initializeArrUnits();
        actionUnits.get(0).setNumUnits(3);
        Action a = new AttackAction("j", "c", actionUnits);
        AttackRuleChecker attackRuleChecker = new AttackRuleChecker(a, r);
        assertFalse(attackRuleChecker.checkResources(a, currPlayer));

    }

            //public boolean checkResources(Action myAttack, Player currPlayer)

}
