package edu.duke.ece651.team3.shared;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class ActionTest {
  @Test
  public void test_Action() {
    Territory src = new Territory("Space", 11);
    Territory dst = new Territory("Mordor", 4);
    String actionType = "Move";
    int actionUnits = 5;
    Action act = new Action(actionType, src, dst, actionUnits);

    assertEquals("Move", act.getActionType());
    assertEquals(actionUnits, act.getActionUnits());

    act.setActionType("Attack");
    assertEquals("Attack", act.getActionType());


    Territory newSrc = new Territory("Oz", 8);
    Territory newDst = new Territory("Elantris", 6);
    act.setSrc(newSrc);
    act.setDst(newDst);
    assertEquals(newSrc, act.getSrc());
    assertEquals(newDst, act.getDst());

    act.setActionUnits(2);
    assertEquals(2, act.getActionUnits());


    String s = "Action{" + "actionType='" + act.getActionType() + '\'' +
            ", src=" + act.getSrc() +
            ", dst=" + act.getDst() +
            ", actionUnits=" + act.getActionUnits() +
            '}';
    assertEquals(s, act.toString());

  }

}
