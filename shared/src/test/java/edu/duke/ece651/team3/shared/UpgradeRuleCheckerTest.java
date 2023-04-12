package edu.duke.ece651.team3.shared;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static java.lang.System.out;
import static org.junit.jupiter.api.Assertions.*;


public class UpgradeRuleCheckerTest {
    @Test
    public void test_all() throws Exception {
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

        ArrayList<Unit> hasHighest = new ArrayList<>();
        hasHighest.add(new Private(1));
        hasHighest.add(new Corporal(0));
        hasHighest.add(new Specialist(0));
        hasHighest.add(new Sergeant(0));
        hasHighest.add(new MasterSergeant(0));
        hasHighest.add(new FirstSergeant(0));
        hasHighest.add(new SergeantMajor(1));

        //Invalid unitToChange: negative
        ArrayList<Unit> unitsToChange1 = new ArrayList<>();
        unitsToChange1.add(new Private(-1));
        unitsToChange1.add(new Corporal(0));
        unitsToChange1.add(new Specialist(0));
        unitsToChange1.add(new Sergeant(0));
        unitsToChange1.add(new MasterSergeant(0));
        unitsToChange1.add(new FirstSergeant(0));
        unitsToChange1.add(new SergeantMajor(0));

        //Invalid unitToChange: more
        ArrayList<Unit> unitsToChange2 = new ArrayList<>();
        unitsToChange2.add(new Private(100));
        unitsToChange2.add(new Corporal(0));
        unitsToChange2.add(new Specialist(0));
        unitsToChange2.add(new Sergeant(0));
        unitsToChange2.add(new MasterSergeant(0));
        unitsToChange2.add(new FirstSergeant(0));
        unitsToChange2.add(new SergeantMajor(0));


        //a-a: invalid dst for move
        Action a1 = new UpgradeAction("a", "a", hasHighest);
        UpgradeRuleChecker mrc1 = new UpgradeRuleChecker(a1, r);
//        RuleChecker mrc0 = new RuleChecker(a1, r);
        assertEquals(true, mrc1.checkSrcDst(a1, p1));
        assertEquals(false, mrc1.checkNumUnits(a1, p1));
        assertEquals(false, mrc1.checkPath(a1, r, p1));
        assertEquals(true, mrc1.checkResources(a1, r, p1));
        assertEquals(false, mrc1.checkValidAction(a1, r, p1));
        assertEquals(true, a1.isUpgradeType());

        Action a2 = new UpgradeAction("a", "a", unitsToChange);
        UpgradeRuleChecker mrc2 = new UpgradeRuleChecker(a2, r);

        assertTrue(mrc2.checkNumUnits(a2, p1));
        assertTrue(mrc2.checkPath(a2, r, p1));


        Action a3 = new UpgradeAction("a", "a", unitsToChange2);
        UpgradeRuleChecker mrc3 = new UpgradeRuleChecker(a3, r);
        assertFalse(mrc3.checkResources(a3, r, p1));

    }

    @Test
    public void test_valid() throws Exception {
        RiskGameBoard r = new RiskGameBoard();
        r.initE2Map();

    }
}
