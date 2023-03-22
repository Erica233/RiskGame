package edu.duke.ece651.team3.shared;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import java.util.HashMap;

public class ActionTest {
  @Test
  public void test_Action() {
    Territory src = new Territory("Space", 11);
    Territory dst = new Territory("Mordor", 4);
    String actionType = "M";
    HashMap<Integer, Integer> actionUnits = new HashMap<>();
    actionUnits.put(1, 2);

    Action act = new MoveAction(actionType, src, dst, actionUnits);

    assertEquals("M", act.getActionType());
    assertEquals(actionUnits, act.getActionUnits());

    act.setActionType("A");
    assertEquals("A", act.getActionType());


    Territory newSrc = new Territory("Oz", 8);
    Territory newDst = new Territory("Elantris", 6);
    act.setSrc(newSrc);
    act.setDst(newDst);
    assertEquals(newSrc, act.getSrc());
    assertEquals(newDst, act.getDst());

    act.setActionUnits(actionUnits);
    assertEquals(actionUnits, act.getActionUnits());

    String ss = "null(1 : 2) ";

    String s = "Action{" + "actionType='" + act.getActionType() + '\'' +
            ", src=" + act.getSrc() +
            ", dst=" + act.getDst() +
            ", actionUnits=" + ss +
            '}';
    assertEquals(s, act.toString());

  }

}
