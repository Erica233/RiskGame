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

    Action act = new MoveAction(actionType, "Space", "Mordor", actionUnits);

    assertEquals("M", act.getActionType());
    assertEquals(actionUnits, act.getActionUnits());

    act.setActionType("A");
    assertEquals("A", act.getActionType());


    Territory newSrc = new Territory("Oz", 8);
    Territory newDst = new Territory("Elantris", 6);
    String newSrcName = "Oz";
    String newDstName = "Elantris";
    act.setSrcName("Oz");
    act.setDstName("Elantris");
    assertEquals(newSrcName, act.getSrcName());
    assertEquals(newDstName, act.getDstName());

    act.setActionUnits(actionUnits);
    assertEquals(actionUnits, act.getActionUnits());

    String ss = "null(1 : 2) ";

    String s = "Action{" + "actionType='" + act.getActionType() + '\'' +
            ", src=" + act.getSrcName() +
            ", dst=" + act.getDstName() +
            ", actionUnits=" + ss +
            '}';
    assertEquals(s, act.toString());

  }

}
