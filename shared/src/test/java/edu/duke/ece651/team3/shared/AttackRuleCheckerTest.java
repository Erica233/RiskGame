package edu.duke.ece651.team3.shared;
import static java.lang.System.out;
import org.junit.jupiter.api.Test;
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
        Action myAction = new AttackAction("A", a, b, 2);
        Action myAction1 = new AttackAction("A", a, c, 2);
        Action myAction2 = new AttackAction("A", a, i, 2);
        Action myAction3 = new AttackAction("A", a, f, 2);
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
        Action myAction = new AttackAction("A", a, b, 2);
        AttackRuleChecker attackRuleChecker = new AttackRuleChecker(myAction, r);
        assertEquals(true, attackRuleChecker.checkPath(myAction, r, p));
    }

    @Test
    public void test_checkNumUnits() throws Exception {
        RiskGameBoard r = new RiskGameBoard();
        r.initMap();
        Player p = r.getAllPlayers().get(0);
        Territory a = new Territory("a", 2);
        Territory b = new Territory("b", 2);
        Action myAction = new AttackAction("A", a, b, -3);
        AttackRuleChecker attackRuleChecker = new AttackRuleChecker(myAction, r);
        assertEquals(false, attackRuleChecker.checkNumUnits(myAction, p));
        Action myAction1 = new AttackAction("A", a, b, 6);
        attackRuleChecker = new AttackRuleChecker(myAction1, r);
        assertEquals(false, attackRuleChecker.checkNumUnits(myAction1, p));
        Action myAction2 = new AttackAction("A", a, b, 1);
        attackRuleChecker = new AttackRuleChecker(myAction2, r);
        assertEquals(true, attackRuleChecker.checkNumUnits(myAction2, p));

    }


}
