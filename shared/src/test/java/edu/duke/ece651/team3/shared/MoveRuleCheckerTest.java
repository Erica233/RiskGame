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
    assertEquals(false, mrc6.checkValidAction(a5, r, p1));


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


  @Test
  public void test_checkValid() throws Exception {
    RiskGameBoard r = new RiskGameBoard();
    r.initMap();
    //Correct case
    HashMap<Integer, Integer> units = new HashMap<>();
    units.put(1, 1);
    Action a = new MoveAction("A", "a", "c", units);
    RuleChecker mrc = new MoveRuleChecker(a, r);
    assertTrue(mrc.checkValidAction(a, r, r.getAllPlayers().get(0)));

    //Wrong destination
    HashMap<Integer, Integer> units1 = new HashMap<>();
    units1.put(1, 1);
    Action a1 = new MoveAction("A", "a", "b", units1);
    assertFalse(mrc.checkValidAction(a1, r, r.getAllPlayers().get(0)));

    //Wrong number
    HashMap<Integer, Integer> units2 = new HashMap<>();
    units2.put(1, -1);
    Action a2 = new MoveAction("A", "a", "c", units2);
    assertFalse(mrc.checkValidAction(a2, r, r.getAllPlayers().get(0)));

    //Wrong path
    HashMap<Integer, Integer> units3 = new HashMap<>();
    units3.put(1, 1);
    Action a3 = new MoveAction("A", "a", "c", units3);
    assertTrue(mrc.checkValidAction(a3, r, r.getAllPlayers().get(0)));

    HashMap<Integer, Integer> units4 = new HashMap<>();
    units4.put(1, 5);
    Action attack1 = new MoveAction("A", "i", "j", units4);

    HashMap<Integer, Integer> units5 = new HashMap<>();
    units5.put(1, 5);
    Action attack2 = new MoveAction("A", "j", "i", units5);

    HashMap<Integer, Integer> units6 = new HashMap<>();
    units6.put(1, 1);
//    Action invalid_move = new MoveAction("M", "i", "h", units6);

    RiskGameBoard b = new RiskGameBoard();
    b.initTestMap();
//    b.executeAttack(attack1, 0);
//    b.executeAttack(attack2, 1);
    Action invalid_move = new MoveAction("M", "h", "j", units6);
    assertFalse(mrc.checkValidAction(invalid_move, b, b.getAllPlayers().get(0)));

    //a: invalid path

//    HashMap<Integer, Integer> unit1 = new HashMap<>();
//    Player p1 = r.getAllPlayers().get(0);
//    unit1.put(1, 1);
//    Territory notSelfdst = r.getAllPlayers().get(1).getOwnedTerritories().get(3);//not neighbor
//    Action a0 = new MoveAction("M", "a", "f", unit1);
//    MoveRuleChecker mrc5 = new MoveRuleChecker(a0, r);
//    assertEquals(false, mrc5.checkValidAction(a0, r, p1));


  }


}
