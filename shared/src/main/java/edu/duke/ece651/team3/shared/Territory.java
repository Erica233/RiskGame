package edu.duke.ece651.team3.shared;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * A class for Territory
 */
public class Territory implements Serializable {
    private final String territoryName;
    private final int numUnits;
    private final ArrayList<Territory> neighbors;

    /**
     * Constructs a Territory with specified name, and number of units
     *
     * @param _name the name of the Territory
     * @param _numUnits the number of units in the Territory
     */
    public Territory(String _name, int _numUnits) {
        this.territoryName = _name;
        this.numUnits = _numUnits;
        this.neighbors = new ArrayList<>();
    }

    /**
     * Constructs a Territory with specified name, number of units, and neighbor territories
     *
     * @param _name the name of the Territory
     * @param _numUnits the number of units in the Territory
     * @param _neighbors the neighbors of the Territory
     */
    public Territory(String _name, int _numUnits, ArrayList<Territory> _neighbors) {
        this.territoryName = _name;
        this.numUnits = _numUnits;
        this.neighbors = _neighbors;
    }

    /**
     * Checks whether the Territory is valid to add to the Board
     *
     * @return true if it is valid to add to the Board, otherwise false
     */
    //TODO: unfinished
    public boolean isValidToAdd() {
        return true;
    }

    /**
     * Add a valid Territory to the neighbors
     *
     * @param aNeighbor the Territory to add to the neighbors
     * @throws Exception if the Territory to add is invalid
     */
    public void addANeighbor(Territory aNeighbor) throws Exception {
        if (!aNeighbor.isAValidNeighbor()) {
            throw new Exception("addANeighbor(): invalid neighbor to add!");
        }
        neighbors.add(aNeighbor);
    }

    /**
     * Add multiple valid Territories to the neighbors
     *
     * @param territories the Territories to add to the neighbors
     * @throws Exception if a Territory to add is invalid
     */
    public void addNeighbors(Territory... territories) throws Exception {
        for (Territory aNeighbor: territories) {
            if (!aNeighbor.isAValidNeighbor()) {
                throw new Exception("addANeighbors(): invalid neighbor to add!");
            }
            neighbors.add(aNeighbor);
        }
    }

    /**
     * Checks whether the Territory is valid to add as a neighbor
     *
     * @return true if it is valid to add to the neighbors, otherwise false
     */
    //TODO: unfinished
    public boolean isAValidNeighbor() {
        return true;
    }

    /**
     * Displays the Territory information, including its name, number of units and neighbors
     *
     * @return a String about its name, number of units and the neighbor territories
     */
    public String displayTerritory() {
        StringBuilder output = new StringBuilder();
        output.append(numUnits).append(" units in ").append(territoryName);
        if (neighbors.isEmpty()) {
            output.append(" (no neighbors)\n");
        } else {
            output.append(" (next to: ");
            for (int i = 0; i < neighbors.size(); i++) {
                if (i < neighbors.size() - 1) {
                    output.append(neighbors.get(i).getTerritoryName()).append(", ");
                } else {
                    output.append(neighbors.get(i).getTerritoryName());
                }
            }
            output.append(")\n");
        }
        return String.valueOf(output);
    }

    /** getters and setters **/
    public String getTerritoryName() {
        return territoryName;
    }

    public int getNumUnits() {
        return numUnits;
    }

    public ArrayList<Territory> getNeighbors() {
        return neighbors;
    }
}
