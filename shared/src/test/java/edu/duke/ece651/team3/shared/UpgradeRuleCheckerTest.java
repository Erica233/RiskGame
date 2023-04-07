package edu.duke.ece651.team3.shared;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static java.lang.System.out;
import static org.junit.jupiter.api.Assertions.assertEquals;


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
        Action a1 = new UpgradeAction("a", "a", unitsToChange);
        UpgradeRuleChecker mrc1 = new UpgradeRuleChecker(a1, r);
//        RuleChecker mrc0 = new RuleChecker(a1, r);
        assertEquals(true, mrc1.checkSrcDst(a1, p1));
        assertEquals(true, mrc1.checkNumUnits(a1, p1));
        assertEquals(true, mrc1.checkPath(a1, r, p1));
        assertEquals(false, mrc1.checkResources(a1, r, p1));
        assertEquals(false, mrc1.checkValidAction(a1, r, p1));

//        //a-d: invalid src for move
//        Action a3 = new MoveAction("b", "a", unitsToChange);
//        UpgradeRuleChecker mrc3 = new UpgradeRuleChecker(a3, r);
//        assertEquals(false, mrc3.checkSrcDst(a3, p1));
//        assertEquals(false, mrc3.checkValidAction(a3, r, p1));
//
//        //a-d: valid dst for move
//        Action a2 = new MoveAction("a", "j", unitsToChange);
//        UpgradeRuleChecker mrc2 = new UpgradeRuleChecker(a2, r);
//        assertEquals(true, mrc2.checkSrcDst(a2, p1));
//        assertEquals(true, mrc2.checkResources(a2, r, p1)); //consume 3, only have 0
//        assertEquals(false, mrc2.checkValidAction(a2, r, p1)); //food resource invalid
//
//
//
//        //Invalid action units: negative number
//        Action a4 = new MoveAction("a", "j", unitsToChange1);
//        UpgradeRuleChecker mrc4 = new UpgradeRuleChecker(a4, r);
//        assertEquals(false, mrc4. checkNumUnits(a4, p1));
//        assertEquals(false, mrc4. checkValidAction(a4, r, p1));
//
//        //Invalid action units: More numbers
//        Action a5 = new MoveAction("a", "j", unitsToChange2);
//        UpgradeRuleChecker mrc5 = new UpgradeRuleChecker(a5, r);
//        assertEquals(false, mrc5.checkNumUnits(a5, p1));
//        assertEquals(false, mrc5.checkValidAction(a5, r, p1));
//
//        //valid action units
//        Action a6 = new MoveAction("a", "j", unitsToChange);
//        UpgradeRuleChecker mrc6 = new UpgradeRuleChecker(a6, r);
//        assertEquals(true, mrc6.checkNumUnits(a6, p1));
//
//
//
//        //invalid path: invalid dst
//        Action a7 = new MoveAction("a", "m", unitsToChange);
//        UpgradeRuleChecker mrc7 = new UpgradeRuleChecker(a7, r);
//        assertEquals(false, mrc7.checkPath(a7, r, p1));
//        assertEquals(false, mrc7.checkValidAction(a7, r, p1));
    }

    @Test
    public void test_invalidPath() throws Exception {
//        RiskGameBoard r = new RiskGameBoard();
//        Territory a = new Territory("a", 5, 10, 10);
//        Territory b = new Territory("b", 5, 10, 10);
//        Territory c = new Territory("c", 5, 10, 10);
//        Territory d = new Territory("d", 5, 10, 10);
//
//        r.connectNeighbors(a, b, 1);
//        r.connectNeighbors(b, c, 1);
//        r.connectNeighbors(b, d, 1);
//
//        ArrayList<Territory> player1T = new ArrayList<>();
//        player1T.add(a);
//        player1T.add(c);
//
//        ArrayList<Territory> player2T = new ArrayList<>();
//        player2T.add(b);
//        player2T.add(d);
//
//        Player p1 = new Player(0, "Orange", 10, player1T);
//        Player p2 = new Player(1, "Blue", 5, player2T);
//
//        r.addPlayer(p1);
//        r.addPlayer(p2);
//
//
//        ArrayList<Unit> unitsToChange = new ArrayList<>();
//        unitsToChange.add(new Private(1));
//        unitsToChange.add(new Corporal(0));
//        unitsToChange.add(new Specialist(0));
//        unitsToChange.add(new Sergeant(0));
//        unitsToChange.add(new MasterSergeant(0));
//        unitsToChange.add(new FirstSergeant(0));
//        unitsToChange.add(new SergeantMajor(0));
//
//        //a->c invalid path a-b-c. a, c belongs to player 1
//        Action a1 = new MoveAction("a", "c", unitsToChange);
//        UpgradeRuleChecker mrc1 = new UpgradeRuleChecker(a1, r);
//        assertEquals(false, mrc1.checkPath(a1, r, p1));
//        assertEquals(false, mrc1.checkValidAction(a1, r, p1));
//
//
//        //b->d enough resource
//        Action a2 = new MoveAction("b", "d", unitsToChange);
//        UpgradeRuleChecker mrc2 = new UpgradeRuleChecker(a2, r);
//        assertEquals(true, mrc2.checkPath(a2, r, p2));
//        assertEquals(true, mrc2.checkValidAction(a2, r, p2));
    }
}
