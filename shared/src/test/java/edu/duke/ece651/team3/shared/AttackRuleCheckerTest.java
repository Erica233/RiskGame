package edu.duke.ece651.team3.shared;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

public class AttackRuleCheckerTest {
    @Test
    public void test_checkSrcDst() throws Exception {
        RiskGameBoard r = new RiskGameBoard();
        r.initMap();
        Player p = r.getAllPlayers().get(0);
        Territory a = new Territory("a", 2);
        Territory b = new Territory("b", 2);
        Territory c = new Territory("c", 2);
        Territory i = new Territory("i", 2);
        Territory f = new Territory("f", 2);

        HashMap<Class<?>, Integer> actionUnits = new HashMap<>();
        Soldier soldier = new Soldier();
        actionUnits.put(soldier.getClass(), 2);

        Action myAction = new AttackAction("A", a, b, actionUnits);
        Action myAction1 = new AttackAction("A", a, c, actionUnits);
        Action myAction2 = new AttackAction("A", a, i, actionUnits);
        Action myAction3 = new AttackAction("A", a, f, actionUnits);
        AttackRuleChecker attackRuleChecker = new AttackRuleChecker(myAction, r);
        AttackRuleChecker attackRuleChecker1 = new AttackRuleChecker(myAction1, r);

        assertEquals(true, attackRuleChecker.checkSrcDst(myAction, p));
        assertEquals(false, attackRuleChecker1.checkSrcDst(myAction1, p));

        Player p1 = r.getAllPlayers().get(1);
        attackRuleChecker = new AttackRuleChecker(myAction2, r);
        attackRuleChecker1 = new AttackRuleChecker(myAction3, r);
        assertEquals(false, attackRuleChecker.checkSrcDst(myAction2, p1));
        assertEquals(false, attackRuleChecker1.checkSrcDst(myAction3, p1));

    }
    @Test
    public void test_checkPath() throws Exception {
        RiskGameBoard r = new RiskGameBoard();
        r.initMap();
        Player p = r.getAllPlayers().get(0);
        Territory a = new Territory("a", 2);
        Territory b = new Territory("b", 2);

        HashMap<Class<?>, Integer> actionUnits = new HashMap<>();
        Soldier soldier = new Soldier();
        actionUnits.put(soldier.getClass(), 2);

        Action myAction = new AttackAction("A", a, b, actionUnits);
        AttackRuleChecker attackRuleChecker = new AttackRuleChecker(myAction, r);
        assertEquals(true, attackRuleChecker.checkPath(myAction, p));
    }

    @Test
    public void test_checkNumUnits() throws Exception {
        RiskGameBoard r = new RiskGameBoard();
        r.initMap();
        Player p = r.getAllPlayers().get(0);
        Territory a = new Territory("a", 2);
        Territory b = new Territory("b", 2);
        HashMap<Class<?>, Integer> actionUnits = new HashMap<>();
        Soldier soldier = new Soldier();
        actionUnits.put(soldier.getClass(), -3);
        Action myAction = new AttackAction("A", a, b, actionUnits);
        AttackRuleChecker attackRuleChecker = new AttackRuleChecker(myAction, r);
        assertEquals(false, attackRuleChecker.checkNumUnits(myAction, p));
        HashMap<Class<?>, Integer> actionUnits1 = new HashMap<>();
        actionUnits1.put(soldier.getClass(), 6);

        Action myAction1 = new AttackAction("A", a, b, actionUnits1);
        attackRuleChecker = new AttackRuleChecker(myAction1, r);
        assertEquals(false, attackRuleChecker.checkNumUnits(myAction1, p));

        HashMap<Class<?>, Integer> actionUnits2 = new HashMap<>();
        actionUnits2.put(soldier.getClass(), 1);
        Action myAction2 = new AttackAction("A", a, b, actionUnits2);
        attackRuleChecker = new AttackRuleChecker(myAction2, r);
        assertEquals(true, attackRuleChecker.checkNumUnits(myAction2, p));

    }


}
