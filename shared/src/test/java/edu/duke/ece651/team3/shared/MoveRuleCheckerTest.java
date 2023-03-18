package edu.duke.ece651.team3.shared;

import static org.junit.jupiter.api.Assertions.*;

import org.checkerframework.checker.units.qual.A;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

public class MoveRuleCheckerTest {
  @Test
  public void test_checkSrcDst() {
    Player p1 = new Player(1);
    Territory t1 = new Territory("Oz", 8);
    p1.tryOwnTerritory(t1);

    Player p2 = new Player(1);
    Territory t2 = new Territory("Narnia", 10);
    p2.tryOwnTerritory(t2);

    String actionType = "Move";
    Territory src = new Territory("Narnia", 10);
    Territory dst = new Territory("Oz", 8);
    int actionUnits = 2;
    int test_less0 = -1;
    int test_greaterThanMax = 200;
    Action a = new Action(actionType, src, dst, actionUnits);
    MoveRuleChecker moveRuleChecker = new MoveRuleChecker(a);
    assertEquals(false,  moveRuleChecker.checkSrc(src, p1));
    assertEquals(true,  moveRuleChecker.checkDst(dst, p1));

    assertEquals(true,  moveRuleChecker.checkSrc(src, p2));
    assertEquals(false,  moveRuleChecker.checkDst(dst, p2));

    //Test the number unite
    assertEquals(true, moveRuleChecker.checkNumUnits(actionUnits));
    assertEquals(false, moveRuleChecker.checkNumUnits(test_less0));
    assertEquals(false, moveRuleChecker.checkNumUnits(test_greaterThanMax));

    //Test the path
    assertEquals(false, moveRuleChecker.checkPath(src, dst));


  }


}
