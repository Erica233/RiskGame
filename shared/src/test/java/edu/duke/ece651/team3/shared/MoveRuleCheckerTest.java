package edu.duke.ece651.team3.shared;
import static java.lang.System.out;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;


public class MoveRuleCheckerTest {
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

        //Invalid unitToChange: negative
        ArrayList<Unit> unitsToChange1 = new ArrayList<>();
        unitsToChange1.add(new Private(-1));
        unitsToChange1.add(new Corporal(0));
        unitsToChange1.add(new Specialist(0));
        unitsToChange1.add(new Sergeant(0));

        //Invalid unitToChange: more
        ArrayList<Unit> unitsToChange2 = new ArrayList<>();
        unitsToChange2.add(new Private(100));
        unitsToChange2.add(new Corporal(0));
        unitsToChange2.add(new Specialist(0));
        unitsToChange2.add(new Sergeant(0));


        //a-c: invalid dst for move
        Action a1 = new MoveAction("a", "c", unitsToChange);
        MoveRuleChecker mrc1 = new MoveRuleChecker(a1, r);
//        RuleChecker mrc0 = new RuleChecker(a1, r);
        assertEquals(false, mrc1.checkSrcDst(a1, p1));
        assertEquals(false, mrc1.checkValidAction(a1, r, p1));

        //a-d: invalid src for move
        Action a3 = new MoveAction("b", "a", unitsToChange);
        MoveRuleChecker mrc3 = new MoveRuleChecker(a3, r);
        assertEquals(false, mrc3.checkSrcDst(a3, p1));
        assertEquals(false, mrc3.checkValidAction(a3, r, p1));

        //a-d: valid dst for move
        Action a2 = new MoveAction("a", "j", unitsToChange);
        MoveRuleChecker mrc2 = new MoveRuleChecker(a2, r);
        assertEquals(true, mrc2.checkSrcDst(a2, p1));
        assertEquals(false, mrc2.checkResources(a2, r, p1)); //consume 3, only have 0
        assertEquals(false, mrc2.checkValidAction(a2, r, p1)); //food resource invalid



        //Invalid action units: negative number
        Action a4 = new MoveAction("a", "j", unitsToChange1);
        MoveRuleChecker mrc4 = new MoveRuleChecker(a4, r);
        assertEquals(false, mrc4. checkNumUnits(a4, p1));
        assertEquals(false, mrc4. checkValidAction(a4, r, p1));

        //Invalid action units: More numbers
        Action a5 = new MoveAction("a", "j", unitsToChange2);
        MoveRuleChecker mrc5 = new MoveRuleChecker(a5, r);
        assertEquals(false, mrc5.checkNumUnits(a5, p1));
        assertEquals(false, mrc5.checkValidAction(a5, r, p1));

        //valid action units
        Action a6 = new MoveAction("a", "j", unitsToChange);
        MoveRuleChecker mrc6 = new MoveRuleChecker(a6, r);
        assertEquals(true, mrc6.checkNumUnits(a6, p1));



        //invalid path: invalid dst
        Action a7 = new MoveAction("a", "m", unitsToChange);
        MoveRuleChecker mrc7 = new MoveRuleChecker(a7, r);
        assertEquals(false, mrc7.checkPath(a7, r, p1));
        assertEquals(false, mrc7.checkValidAction(a7, r, p1));
    }

    @Test
    public void test_invalidPath() throws Exception {
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


        ArrayList<Unit> unitsToChange = new ArrayList<>();
        unitsToChange.add(new Private(1));
        unitsToChange.add(new Corporal(0));
        unitsToChange.add(new Specialist(0));
        unitsToChange.add(new Sergeant(0));

        //a->c invalid path a-b-c. a, c belongs to player 1
        Action a1 = new MoveAction("a", "c", unitsToChange);
        MoveRuleChecker mrc1 = new MoveRuleChecker(a1, r);
        assertEquals(false, mrc1.checkPath(a1, r, p1));
        assertEquals(false, mrc1.checkValidAction(a1, r, p1));


        //b->d enough resource
        Action a2 = new MoveAction("b", "d", unitsToChange);
        MoveRuleChecker mrc2 = new MoveRuleChecker(a2, r);
        assertEquals(true, mrc2.checkPath(a2, r, p2));
        assertEquals(true, mrc2.checkValidAction(a2, r, p2));
    }
}
