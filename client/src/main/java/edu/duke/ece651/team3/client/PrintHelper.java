package edu.duke.ece651.team3.client;

import edu.duke.ece651.team3.shared.*;

import java.util.ArrayList;

public class PrintHelper {
    /**
     * This method prints the action(both move and attack actions) list for the player
     */
    public static String printActionsLists(ArrayList<Action> actionsList, int playerId) {
        String output = "";
        output = output + "Player " + playerId + " move actions:\n";
        for (Action move: actionsList) {
            if (move.isMoveType()) {
                output = output + move + "\n";
            }
        }
        output += "\n";

        output = output + "Player " + playerId + " attack actions:\n";
        for (Action attack: actionsList) {
            if (attack.isAttackType()) {
                output = output + attack + "\n";
            }
        }
        output += "\n";

        output = output + "Player " + playerId + " upgrade actions:\n";
        for (Action upgrade: actionsList) {
            if (upgrade.isUpgradeType()) {
                output = output + upgrade + "\n";
            }
        }
        output += "\n";
        System.out.println(output);
        return output;
    }
}
