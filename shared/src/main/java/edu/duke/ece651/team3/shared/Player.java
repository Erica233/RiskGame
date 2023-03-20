package edu.duke.ece651.team3.shared;

import java.io.Serializable;
import java.util.ArrayList;

public class Player implements Serializable {
    private final int playerId;
    private final String color;
    private final int totNumUnits;
    private final ArrayList<Territory> ownedTerritories;

    public Player(int _id, String _color, int _totNumUnits) {
        this.playerId = _id;
        this.color = _color;
        this.totNumUnits = _totNumUnits;
        this.ownedTerritories = new ArrayList<>();
    }

    public Player(int _id, String _color, int _totNumUnits, ArrayList<Territory> _ownedTerritories) {
        this.playerId = _id;
        this.color = _color;
        this.totNumUnits = _totNumUnits;
        this.ownedTerritories = _ownedTerritories;
    }

    @Override
    public boolean equals(Object other) {
        if (other.getClass().equals(getClass())) {
            Player player = (Player) other;
            return playerId == player.playerId && totNumUnits == player.totNumUnits && color.equals(player.color) && ownedTerritories.equals(player.ownedTerritories);
        }
        return false;
    }

    /**
     * Checks whether the Territory is valid to own by the player
     *
     * @return true if it is valid to be owned, otherwise false
     */
    public boolean isValidToOwn(Territory territoryToOwn) {
        for (int i = 0; i < ownedTerritories.size(); i++){
            if (territoryToOwn.getTerritoryName().equals(ownedTerritories.get(i).getTerritoryName())) {
                return false;
            }
        }
        return true;
    }

    /**
     * Add a Territory owned by the player,
     * if the Territory is valid, add and returns true,
     * otherwise, return false
     *
     * @param territoryToOwn the Territory to be owned by the player
     * @return true if the Territory is valid to own, otherwise false
     */
    public boolean tryOwnTerritory(Territory territoryToOwn) {
        if (!isValidToOwn(territoryToOwn)) {
            return false;
        }
        ownedTerritories.add(territoryToOwn);
        return true;
    }

    public String displayPlayer() {
        StringBuilder output = new StringBuilder();
        output.append(color).append(" player:\n---------------\n");
        if (ownedTerritories.size() == 0) {
            output.append("no territories\n");
        } else {
            for (Territory territory: ownedTerritories) {
                output.append(territory.displayTerritory());
            }
        }
        return output.toString();
    }

    /** getters and setters **/
    public int getPlayerId() {
        return playerId;
    }

    public ArrayList<Territory> getOwnedTerritories() {
        return ownedTerritories;
    }

    public String getColor() {
        return color;
    }

    public int getTotNumUnits() {
        return totNumUnits;
    }
}
