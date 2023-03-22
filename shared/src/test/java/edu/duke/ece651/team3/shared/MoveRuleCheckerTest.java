package edu.duke.ece651.team3.shared;
import static java.lang.System.out;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;


public class MoveRuleCheckerTest {
  @Test
  public void test_checkSrcDst() throws Exception {
    RiskGameBoard r = new RiskGameBoard();
    r.initMap();
    out.println(r.displayBoard());
    Player p1 = r.getAllPlayers().get(0);
    Player p2 = r.getAllPlayers().get(1);



//    out.println("FIRST");


    Territory src = r.getAllPlayers().get(0).getOwnedTerritories().get(0);
    Territory dst = r.getAllPlayers().get(0).getOwnedTerritories().get(1);
//    Territory src = new Territory("a", 5);
    Territory dst1 = new Territory("c", 5);
    Territory dst2 = new Territory("b", 5);
    String s1 = "Move";
    int unit1 = 1;
    int unit2 = -1;
    int unit3 = 100;
    //a: valid path
    Action a = new MoveAction(s1, src, dst, unit1);
    MoveRuleChecker mrc0 = new MoveRuleChecker(a, r);
    assertEquals(true, mrc0.checkPath(a, r, p1)); //correct neighbor
    assertEquals(false, mrc0.checkPath(a, r, p2)); //correct neighbor

    Territory t1 = r.getAllPlayers().get(0).getOwnedTerritories().get(4);//i
    MoveRuleChecker moveRuleChecker = new MoveRuleChecker(a, r);
    moveRuleChecker.checkIsSelfTerritory(t1, r.getAllPlayers().get(0));

    //a: invalid path
    Territory notSelfdst = r.getAllPlayers().get(1).getOwnedTerritories().get(3);//not neighbor
    Action a0 = new MoveAction(s1, src, notSelfdst, unit1);
    MoveRuleChecker mrc5 = new MoveRuleChecker(a0, r);
    assertEquals(false, mrc5.checkPath(a0, r, p1));

    //a1: move 1 unit from a to c, which is valid
    Action a1 = new MoveAction(s1, src, dst1, unit1);
    MoveRuleChecker mrc = new MoveRuleChecker(a1, r);
    assertEquals(true,  mrc.checkSrcDst(a1, p1));
    assertEquals(true,  mrc.checkNumUnits(a1, p1));

    //a2: move 1 unit from a to b, which is invalid
    Action a2 = new MoveAction(s1, src, dst2, unit1);
    MoveRuleChecker mrc1 = new MoveRuleChecker(a2, r);
    assertEquals(false,  mrc1.checkSrcDst(a2, p1));

    //a3: valid src and dst, the unit move is invalid -1
    Action a3 = new MoveAction(s1, src, dst1, unit2);
    MoveRuleChecker mrc3 = new MoveRuleChecker(a3, r);
    assertEquals(false,  mrc3.checkNumUnits(a3, p1));

    //a4: valid src and dst, the unit move is invalid -1
    Action a4 = new MoveAction(s1, src, dst1, unit3);
    MoveRuleChecker mrc4 = new MoveRuleChecker(a4, r);
    assertEquals(false,  mrc4.checkNumUnits(a4, p1));


    //a: invalid path: neighbor but not self
    Territory notSelfdst_neighbor = r.getAllPlayers().get(1).getOwnedTerritories().get(0);//b
    Action a5 = new MoveAction(s1, src, notSelfdst_neighbor, unit1);
    MoveRuleChecker mrc6 = new MoveRuleChecker(a5, r);
    assertEquals(false, mrc6.checkPath(a5, r, p1));

    //a: invalid path: neighbor but not directly connect
    Territory self_notDirNei = r.getAllPlayers().get(0).getOwnedTerritories().get(5);//l
    Action a6 = new MoveAction(s1, src, self_notDirNei, unit1);
    MoveRuleChecker mrc7 = new MoveRuleChecker(a6, r);
    assertEquals(true, mrc7.checkPath(a6, r, p1));

//    assertEquals(true, mrc.checkPath(a, p1));

    //Testing
    MoveAction m1 = new MoveAction();


//    assertEquals(); mrc.checkNumUnits(a1, p);


//    assertEquals(true, false);

  }


}
