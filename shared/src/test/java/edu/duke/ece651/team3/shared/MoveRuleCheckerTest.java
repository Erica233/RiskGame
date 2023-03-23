package edu.duke.ece651.team3.shared;
import static java.lang.System.out;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import java.net.Socket;
import java.util.HashMap;


public class MoveRuleCheckerTest {
  @Test
  public void test_checkSrcDst() throws Exception {
    RiskGameBoard r = new RiskGameBoard();
    r.initMap();
    out.println(r.displayBoard());
    Player p1 = r.getAllPlayers().get(0);
    HashMap<Integer, Integer> unit1 = new HashMap<>();
    unit1.put(1, 1);

    HashMap<Integer, Integer> unit2 = new HashMap<>();
    unit2.put(1, -1);

    HashMap<Integer, Integer> unit3 = new HashMap<>();
    unit3.put(1, 3);


    Territory src = r.getAllPlayers().get(0).getOwnedTerritories().get(0);
    Territory dst = r.getAllPlayers().get(0).getOwnedTerritories().get(1);
    Territory dst1 = new Territory("c", 5);
    Territory dst2 = new Territory("b", 5);
    String s1 = "Move";

    //a: valid path
    Action a = new MoveAction(s1, "a", "c", unit1);
    Action a_new = new MoveAction(s1, "a", "b", unit1);
    MoveRuleChecker mrc0 = new MoveRuleChecker(a, r);
    assertEquals(true, mrc0.checkPath(a, r, p1)); //correct neighbor
    assertEquals(false, mrc0.checkPath(a_new, r, p1)); //correct neighbor

    Territory t1 = r.getAllPlayers().get(0).getOwnedTerritories().get(4);//i
    MoveRuleChecker moveRuleChecker = new MoveRuleChecker(a, r);
    moveRuleChecker.checkIsSelfTerritory(t1, r.getAllPlayers().get(0));
    moveRuleChecker.checkIsSelfTerritory(t1, r.getAllPlayers().get(1));


    //a: invalid path
    Territory notSelfdst = r.getAllPlayers().get(1).getOwnedTerritories().get(3);//not neighbor
    Action a0 = new MoveAction(s1, "a", "f", unit1);
    MoveRuleChecker mrc5 = new MoveRuleChecker(a0, r);
    assertEquals(false, mrc5.checkPath(a0, r, p1));

    //a1: move 1 unit from a to c, which is valid
    Action a1 = new MoveAction(s1, "a", "c", unit1);
    MoveRuleChecker mrc = new MoveRuleChecker(a1, r);
    assertEquals(true,  mrc.checkSrcDst(a1, p1));
    assertEquals(true,  mrc.checkNumUnits(a1, p1));

    //a2: move 1 unit from a to b, which is invalid
    Action a2 = new MoveAction(s1, "a", "b", unit1);
    MoveRuleChecker mrc1 = new MoveRuleChecker(a2, r);
    assertEquals(false,  mrc1.checkSrcDst(a2, p1));

    //a3: valid src and dst, the unit move is invalid -1 a -> c
    Action a3 = new MoveAction(s1, "a", "c", unit2);
    MoveRuleChecker mrc3 = new MoveRuleChecker(a3, r);
    assertEquals(false,  mrc3.checkNumUnits(a3, p1));

    //a4: valid src and dst, the unit move is invalid -1
    Action a4 = new MoveAction(s1, "a", "c", unit3);
    MoveRuleChecker mrc4 = new MoveRuleChecker(a4, r);


    //a: invalid path: neighbor but not self
    Territory notSelfdst_neighbor = r.getAllPlayers().get(1).getOwnedTerritories().get(0);//b
    Action a5 = new MoveAction(s1, "a", "b", unit1);
    MoveRuleChecker mrc6 = new MoveRuleChecker(a5, r);
    assertEquals(false, mrc6.checkPath(a5, r, p1));

    //a: invalid path: neighbor but not directly connect
    Territory self_notDirNei = r.getAllPlayers().get(0).getOwnedTerritories().get(5);//l
    Action a6 = new MoveAction(s1, "a", "l", unit1);
    MoveRuleChecker mrc7 = new MoveRuleChecker(a6, r);
    assertEquals(true, mrc7.checkPath(a6, r, p1));



    //Test if the self does not have a path, but src and dst are both self
    Territory ta = new Territory("a", 1, 3);
    Territory tb = new Territory("b", 1, 3);
    Territory tc = new Territory("c", 1, 3);
    Territory td = new Territory("d", 1, 3);
    ta.addNeighbors(tb, tc);
    RiskGameBoard riskGameBoard = new RiskGameBoard();
    riskGameBoard.tryAddTerritory(ta);
    riskGameBoard.tryAddTerritory(tb);
    riskGameBoard.tryAddTerritory(tc);
    riskGameBoard.tryAddTerritory(td);

    int unit0 = 1;
    Player py1 = new Player(4, "Blue", unit0);
    Player py2 = new Player(5, "Green", unit0);
    py1.tryOwnTerritory(ta);
    py1.tryOwnTerritory(td);
    py2.tryOwnTerritory(tb);
    py2.tryOwnTerritory(tc);

    riskGameBoard.addPlayer(py1);
    riskGameBoard.addPlayer(py2);

    Action newMoveAction = new MoveAction("move", "a", "d", unit1);
    MoveRuleChecker moveRuleChecker1 = new MoveRuleChecker(newMoveAction, r);
    moveRuleChecker1.checkPath(newMoveAction, riskGameBoard, py1);
  }


}
