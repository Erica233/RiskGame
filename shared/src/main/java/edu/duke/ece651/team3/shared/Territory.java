package edu.duke.ece651.team3.shared;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

/**
 * A class for Territory
 */
public class Territory implements Serializable, Comparable<Territory> {
    private final String territoryName;
    private int numUnits;
    private final ArrayList<Territory> neighbors;
    private final HashMap<Class<?>, Integer> units;

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
        this.units = new HashMap<>();
    }

    /**
     * Constructs a Territory with specified name, number of units, and neighbor territories
     *
     * @param _name the name of the Territory
     * @param _numUnits the number of units in the Territory
     * @param _neighbors the neighbors of the Territory
     * @param _units units in this Territory
     */
    public Territory(String _name, int _numUnits, ArrayList<Territory> _neighbors, HashMap<Class<?>, Integer> _units) {
        this.territoryName = _name;
        this.numUnits = _numUnits;
        this.neighbors = _neighbors;
        this.units = _units;
    }

    @Override
    public int compareTo(Territory aTerritory) {
        return this.territoryName.compareTo(aTerritory.getTerritoryName());
    }

    @Override
    public boolean equals(Object other) {
        if (other.getClass().equals(getClass())) {
            Territory territory = (Territory) other;
            return numUnits == territory.getNumUnits() && territoryName.equals(territory.getTerritoryName()) && hasSameNeighbors(territory);
        }
        return false;
    }

    public boolean hasSameNeighbors(Territory territoryToCompare) {
        if (neighbors.size() != territoryToCompare.getNeighbors().size()) {
            return false;
        }
        ArrayList<String> leftNeighborsNames = getSortedNeighborNames();
        ArrayList<String> rightNeighborsNames = territoryToCompare.getSortedNeighborNames();
        return leftNeighborsNames.equals(rightNeighborsNames);
    }

    public ArrayList<String> getSortedNeighborNames() {
        ArrayList<String> sortedNeighborNames = new ArrayList<>();
        for (Territory aNeighbor: neighbors) {
            sortedNeighborNames.add(aNeighbor.getTerritoryName());
        }
        return sortedNeighborNames;
    }

    /**
     * Add a valid Territory to the neighbors
     *
     * @param aNeighbor the Territory to add to the neighbors
     * @throws Exception if the Territory to add is invalid
     */
    public void addANeighbor(Territory aNeighbor) throws Exception {
        if (!checkValidNeighbor(aNeighbor)) {
            throw new Exception("addANeighbor(): invalid neighbor to add!");
        }
        neighbors.add(aNeighbor);
        Collections.sort(neighbors);
    }

    /**
     * Add multiple valid Territories to the neighbors
     * !!Attention: if invalid neighbor in the middle, it will not remove the added neighbors
     *
     * @param territories the Territories to add to the neighbors
     * @throws Exception if a Territory to add is invalid
     */
    public void addNeighbors(Territory... territories) throws Exception {
        for (Territory aNeighbor: territories) {
            if (!checkValidNeighbor(aNeighbor)) {
                throw new Exception("addANeighbors(): invalid neighbor to add!");
            }
            neighbors.add(aNeighbor);
        }
        Collections.sort(neighbors);
    }

    /**
     * Checks whether the Territory is valid to add as a neighbor
     *
     * @return true if it is valid to add to the neighbors, otherwise false
     */
    public boolean checkValidNeighbor(Territory territoryToAddAsNeighbor) {
        if (territoryName.equals(territoryToAddAsNeighbor.getTerritoryName())) {
            return false;
        }
        for (Territory aNeighbor: neighbors) {
            if (aNeighbor.getTerritoryName().equals(territoryToAddAsNeighbor.getTerritoryName())) {
                return false;
            }
        }
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

    /**
     * Update the number of units in Territory
     */
    public void updateNumUnits(){
        int num = 0;
        for(Class<?> c : units.keySet()){
            System.out.println(c);
            num += units.get(c);
        }
        numUnits = num;
    }



    /**
     * Add specific type and number of unit to units
     * @param unitToAdd specific type of unit to add
     * @param num number of this type of unit
     */
    public void increaseUnit(Unit unitToAdd, int num) {
        if (units.containsKey(unitToAdd.getClass())) {
            int value = units.get(unitToAdd.getClass()) + num;
            units.put(unitToAdd.getClass(), value);
        } else {
            units.put(unitToAdd.getClass(), num);
        }
        updateNumUnits();
    }

    /**
     * Delete specific type and number of unit to units
     * @param unitToRemove specific type of unit to remove
     * @param num number of this type of unit
     */
    public void decreaseUnit(Unit unitToRemove, int num) {
        if (units.containsKey(unitToRemove.getClass())) {
            int value = units.get(unitToRemove.getClass()) - num;
            System.out.println(unitToRemove.getClass());
            if (value < 0) {
                throw new IllegalArgumentException("Can't delete too much num of unit");
            }
            units.put(unitToRemove.getClass(), value);
        } else {
            throw new IllegalArgumentException("Can't delete non-existing unit");
        }
        updateNumUnits();
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
