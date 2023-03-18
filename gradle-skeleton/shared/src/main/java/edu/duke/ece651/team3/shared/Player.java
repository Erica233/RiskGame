package edu.duke.ece651.team3.shared;

import java.util.ArrayList;

public class Player {
    private final int id;
    private final ArrayList<Territory> ownedTerritories;
    private ArrayList<Territory> neighbors;

    public Player(int _id) {
        this.id = _id;
        this.ownedTerritories = new ArrayList<>();
    }

    /**
     * Checks whether the Territory is valid to own by the player
     * @param territoryToOwn territory to be tested validation
     * @return true if it is valid to be owned, otherwise false
     */
    public boolean isValidToOwn(Territory territoryToOwn) {
        for(int i = 0; i < ownedTerritories.size(); i++){
            if(territoryToOwn == ownedTerritories.get(i)){
                return true;
            }
        }
        return false;
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
        if (!territoryToOwn.isAValidNeighbor()) {
            return false;
        }
        ownedTerritories.add(territoryToOwn);
        return true;
    }

    public int getId() {
        return id;
    }

    public ArrayList<Territory> getOwnedTerritories() {
        return ownedTerritories;
    }
}
