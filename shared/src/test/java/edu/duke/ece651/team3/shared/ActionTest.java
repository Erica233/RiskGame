package edu.duke.ece651.team3.shared;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;

public class ActionTest {
  @Test
  public void test_Action() {
    //Initialize units to change
    ArrayList<Unit> unitsToChange = new ArrayList<>();
    unitsToChange.add(new Private(1));
    unitsToChange.add(new Corporal(0));
    unitsToChange.add(new Specialist(0));
    unitsToChange.add(new Sergeant(0));

    Action act = new MoveAction("Space", "Moon", unitsToChange);
    act.setActionUnits(unitsToChange);

    assertEquals("M", act.getActionType());
    assertEquals(unitsToChange, act.getUnitsToChange());

    act.setActionType("A");
    assertEquals("A", act.getActionType());

    String newSrcName = "Oz";
    String newDstName = "Elantris";
    act.setSrcName("Oz");
    act.setDstName("Elantris");
    assertEquals(newSrcName, act.getSrcName());
    assertEquals(newDstName, act.getDstName());
    assertEquals(unitsToChange, act.getUnitsToChange());


    String s = "Action {Type=A, src=Oz, dst=Elantris, units=(lv.0 : 1) (lv.1 : 0) (lv.2 : 0) (lv.3 : 0) }";
    System.out.println(act.toString());
    assertEquals(s, act.toString());

  }
  @Test
  public void test_ActionType() throws Exception {
    ArrayList<Unit> unitsToChange = new ArrayList<>();
    unitsToChange.add(new Private(1));
    unitsToChange.add(new Corporal(0));
    unitsToChange.add(new Specialist(0));
    unitsToChange.add(new Sergeant(0));

    Action move = new MoveAction("Space", "Moon", unitsToChange);
    assertEquals(true, move.isMoveType());
    assertEquals(true, move.isValidType());

    Action attack = new AttackAction("Space", "Moon", unitsToChange);
    assertEquals(true, attack.isAttackType());
    assertEquals(true, attack.isValidType());



  }

}
