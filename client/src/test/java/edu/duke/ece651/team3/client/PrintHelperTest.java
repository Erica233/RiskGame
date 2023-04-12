package edu.duke.ece651.team3.client;

import static org.junit.jupiter.api.Assertions.*;

import edu.duke.ece651.team3.shared.*;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

public class PrintHelperTest {
  @Test
  public void test_printMsg() {
    ArrayList<Action> actions = new ArrayList<>();
    ArrayList<Unit> unitsToChange = new ArrayList<>();
    unitsToChange.add(new Private(1));


    Action move = new MoveAction("a", "j", unitsToChange);
    Action attack = new AttackAction("a", "b", unitsToChange);
    Action upgrade = new UpgradeAction("a", "a", unitsToChange);
    actions.add(move);
    actions.add(attack);
    actions.add(upgrade);

    PrintHelper printHelper = new PrintHelper();
    String exp = "Player 0 move actions:\n" +
            "Action {Type=M, src=a, dst=j, units=(lv.0 : 1) }\n" +
            "\n" +
            "Player 0 attack actions:\n" +
            "Action {Type=A, src=a, dst=b, units=(lv.0 : 1) }\n" +
            "\n" +
            "Player 0 upgrade actions:\n" +
            "Action {Type=U, src=a, dst=a, units=(lv.0 : 1) }\n" +
            "\n";
    assertEquals(exp, printHelper.printActionsLists(actions, 0));

  }

}
