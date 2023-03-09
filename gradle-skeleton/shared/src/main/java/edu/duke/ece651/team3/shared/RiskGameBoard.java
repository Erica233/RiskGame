package edu.duke.ece651.team3.shared;

import java.util.ArrayList;

/**
 * A Risk Game Board
 */
public class RiskGameBoard implements Board {
    private final ArrayList<Territory> allTerritories;

    /**
     * Constructs a RiskGameBoard, given a Territory
     *
     * @param oneTerritory a Territory
     */
    public RiskGameBoard(Territory oneTerritory) {
        this.aTerritory = oneTerritory;
    }

    public String displayBoard() {
        return aTerritory.displayTerritory();
    }

    public Territory getaTerritory() {
        return aTerritory;
    }
}
