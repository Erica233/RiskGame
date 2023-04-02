//package edu.duke.ece651.team3.shared;
//import static java.lang.System.out;
//import static org.junit.jupiter.api.Assertions.*;
//
//import org.junit.jupiter.api.Disabled;
//import org.junit.jupiter.api.Test;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//
//
//public class MoveRuleCheckerTest {
//  @Test
//  public void test_checkSrcDst() throws Exception {
//    RiskGameBoard r = new RiskGameBoard();
//    r.initE2Map();
//    out.println(r.displayBoard());
//    Player p1 = r.getAllPlayers().get(0);
//    HashMap<Integer, Integer> unit1 = new HashMap<>();
//    unit1.put(1, 1);
//
//    HashMap<Integer, Integer> unit2 = new HashMap<>();
//    unit2.put(1, -1);
//
//    HashMap<Integer, Integer> unit3 = new HashMap<>();
//    unit3.put(1, 3);
//
//    ArrayList<Unit> unitsToChange = new ArrayList<>();
//    unitsToChange.add(new Private(1));
//    unitsToChange.add(new Corporal(0));
//    unitsToChange.add(new Specialist(0));
//    unitsToChange.add(new Sergeant(0));
//
//    ArrayList<Unit> unitsToChange1 = new ArrayList<>();
//    unitsToChange1.add(new Private(-1));
//    unitsToChange1.add(new Corporal(0));
//    unitsToChange1.add(new Specialist(0));
//    unitsToChange1.add(new Sergeant(0));
//
//    //a-c: valid path
//    Action a = new MoveAction("a", "c", unitsToChange);
//    //a-b: invalid path
//    Action a_new = new MoveAction("a", "b", unitsToChange);
//
//
//    MoveRuleChecker mrc0 = new MoveRuleChecker(a, r);
//    assertEquals(true, mrc0.checkPath(a, r, p1)); //correct neighbor
//    assertEquals(false, mrc0.checkPath(a_new, r, p1)); //correct neighbor
//
//    Territory t1 = r.getAllPlayers().get(0).getOwnedTerritories().get(4);//i
//    MoveRuleChecker moveRuleChecker = new MoveRuleChecker(a, r);
//    moveRuleChecker.checkIsSelfTerritory(t1, r.getAllPlayers().get(0));
//    moveRuleChecker.checkIsSelfTerritory(t1, r.getAllPlayers().get(1));
//
//
//    //a: invalid path
//    Territory notSelfdst = r.getAllPlayers().get(1).getOwnedTerritories().get(3);//not neighbor
//    Action a0 = new MoveAction("a", "f", unitsToChange);
//    MoveRuleChecker mrc5 = new MoveRuleChecker(a0, r);
//    assertEquals(false, mrc5.checkPath(a0, r, p1));
//
//    //a1: move 1 unit from a to c, which is valid
//    Action a1 = new MoveAction("a", "c", unitsToChange);
//    MoveRuleChecker mrc = new MoveRuleChecker(a1, r);
//    assertEquals(true,  mrc.checkSrcDst(a1, p1));
//    assertEquals(true,  mrc.checkNumUnits(a1, p1));
//
//    //a2: move 1 unit from a to b, which is invalid
//    Action a2 = new MoveAction("a", "b", unitsToChange);
//    MoveRuleChecker mrc1 = new MoveRuleChecker(a2, r);
//    assertEquals(false,  mrc1.checkSrcDst(a2, p1));
//
//    //a3: valid src and dst, the unit move is invalid -1 a -> c
//    Action a3 = new MoveAction( "a", "c", unitsToChange1);
//    MoveRuleChecker mrc3 = new MoveRuleChecker(a3, r);
//    assertEquals(false,  mrc3.checkNumUnits(a3, p1));
//
//    //a4: valid src and dst, the unit move is invalid -1
//    Action a4 = new MoveAction( "a", "c", unitsToChange1);
//    MoveRuleChecker mrc4 = new MoveRuleChecker(a4, r);
//
//
//    //a: invalid path: neighbor but not self
//    Territory notSelfdst_neighbor = r.getAllPlayers().get(1).getOwnedTerritories().get(0);//b
//    Action a5 = new MoveAction("a", "b", unitsToChange);
//    MoveRuleChecker mrc6 = new MoveRuleChecker(a5, r);
//    assertEquals(false, mrc6.checkPath(a5, r, p1));
//    assertEquals(false, mrc6.checkValidAction(a5, r, p1));
//
//
//    //a: invalid path: neighbor but not directly connect
//    Action a6 = new MoveAction("a", "l", unitsToChange);
//    MoveRuleChecker mrc7 = new MoveRuleChecker(a6, r);
//    assertEquals(true, mrc7.checkPath(a6, r, p1));
//
//
//
//    //Test if the self does not have a path, but src and dst are both self
//    Territory ta = new Territory("a", 3);
//    Territory tb = new Territory("b", 3);
//    Territory tc = new Territory("c", 3);
//    Territory td = new Territory("d", 3);
//
//
//    RiskGameBoard riskGameBoard = new RiskGameBoard();
//    riskGameBoard.tryAddTerritory(ta);
//    riskGameBoard.tryAddTerritory(tb);
//    riskGameBoard.tryAddTerritory(tc);
//    riskGameBoard.tryAddTerritory(td);
//
//    int unit0 = 1;
//    Player py1 = new Player(4, "Blue", unit0);
//    Player py2 = new Player(5, "Green", unit0);
//    py1.tryOwnTerritory(ta);
//    py1.tryOwnTerritory(td);
//    py2.tryOwnTerritory(tb);
//    py2.tryOwnTerritory(tc);
//
//    riskGameBoard.addPlayer(py1);
//    riskGameBoard.addPlayer(py2);
//
//    Action newMoveAction = new MoveAction("a", "d", unitsToChange);
//    MoveRuleChecker moveRuleChecker1 = new MoveRuleChecker(newMoveAction, r);
//    moveRuleChecker1.checkPath(newMoveAction, riskGameBoard, py1);
//  }
//
//  @Disabled
//  @Test
//  public void test_checkValid() throws Exception {
//    RiskGameBoard r = new RiskGameBoard();
//    r.initE2Map();
//    ArrayList<Unit> unitsToChange = new ArrayList<>();
//    unitsToChange.add(new Private(1));
//    unitsToChange.add(new Corporal(0));
//    unitsToChange.add(new Specialist(0));
//    unitsToChange.add(new Sergeant(0));
//
//
//    //Correct case
//    HashMap<Integer, Integer> units = new HashMap<>();
//    units.put(1, 1);
//    Action a = new MoveAction( "a", "c", unitsToChange);
//    RuleChecker mrc = new MoveRuleChecker(a, r);
//    assertTrue(mrc.checkValidAction(a, r, r.getAllPlayers().get(0)));
//
//    //Wrong destination
//    HashMap<Integer, Integer> units1 = new HashMap<>();
//    units1.put(1, 1);
//    Action a1 = new MoveAction("a", "b", unitsToChange);
//    assertFalse(mrc.checkValidAction(a1, r, r.getAllPlayers().get(0)));
//
//    //Wrong number
//    HashMap<Integer, Integer> units2 = new HashMap<>();
//    units2.put(1, -1);
//    ArrayList<Unit> unitsToChange1 = new ArrayList<>();
//    unitsToChange1.add(new Private(-1));
//    unitsToChange1.add(new Corporal(0));
//    unitsToChange1.add(new Specialist(0));
//    unitsToChange1.add(new Sergeant(0));
//
//    Action a2 = new MoveAction("a", "c", unitsToChange1);
//    assertFalse(mrc.checkValidAction(a2, r, r.getAllPlayers().get(0)));
//
//    //Wrong path
//    Action a3 = new MoveAction("a", "c", unitsToChange);
//    assertTrue(mrc.checkValidAction(a3, r, r.getAllPlayers().get(0)));
//
//    ArrayList<Unit> unitsToChange2 = new ArrayList<>();
//    unitsToChange2.add(new Private(-1));
//    unitsToChange2.add(new Corporal(0));
//    unitsToChange2.add(new Specialist(0));
//    unitsToChange2.add(new Sergeant(0));
//
//    RiskGameBoard b = new RiskGameBoard();
//    b.initE2Map();
//    Action invalid_move = new MoveAction("h", "j", unitsToChange);
//    assertFalse(mrc.checkValidAction(invalid_move, b, b.getAllPlayers().get(0)));
//
//  }
//
//
//}
