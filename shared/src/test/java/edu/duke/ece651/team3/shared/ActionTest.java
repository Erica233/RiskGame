package edu.duke.ece651.team3.shared;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
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


    assertEquals(actionUnits, act.getActionUnits());

    String ss = "null(1 : 2) ";

    String s = "Action{" + "actionType='" + act.getActionType() + '\'' +
            ", src=" + act.getSrcName() +
            ", dst=" + act.getDstName() +
            ", actionUnits=" + ss +
            '}';
    assertEquals(s, act.toString());

  }
  @Test
  public void test_ActionType() throws Exception {
    RiskGameBoard r = new RiskGameBoard();
    r.initMap();

    HashMap<Integer, Integer> units = new HashMap<>();
    units.put(1, 1);
    MoveAction move = new MoveAction("M", "a", "b", units);
    Integer in1 = 1;
    assertEquals(in1 ,move.getNumActionUnits());
    assertEquals(true,  move.isMoveType());
    assertEquals(false,  move.isAttackType());
    assertEquals(false,  move.isDone());
    assertEquals(true,  move.isValidType());

    ArrayList<Action> list = new ArrayList<>();
    list.add(move);
    Player player1 = r.getAllPlayers().get(0);
//    assertEquals(1, player1.getN());

  }

}
