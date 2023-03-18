package edu.duke.ece651.team3.shared;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * A Risk Game Board
 */
public class RiskGameBoard implements Board, Serializable {
    private final ArrayList<Territory> allTerritories;

    /**
     * Constructs a RiskGameBoard, given a Territory
     *
     */
    public RiskGameBoard() {
        this.allTerritories = new ArrayList<>();
    }

    public String displayBoard() {
        StringBuilder output = new StringBuilder();
        for (Territory aTerritory: allTerritories) {
            output.append(aTerritory.displayTerritory());
        }
        return output.toString();
    }

    public boolean tryAddTerritory(Territory territoryToAdd) {
        if (!territoryToAdd.isValidToAdd()) {
            return false;
        }
        allTerritories.add(territoryToAdd);
        return true;
    }

    public ArrayList<Territory> getAllTerritories() {
        return allTerritories;
    }
}
