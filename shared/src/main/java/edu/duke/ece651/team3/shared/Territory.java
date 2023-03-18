package edu.duke.ece651.team3.shared;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * A class for Territory
 */
public class Territory implements Serializable {
    private final String name;
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
        this.name = _name;
        this.numUnits = _numUnits;
        this.neighbors = new ArrayList<>();
        this.units = new HashMap<>();
    }

    /**
     * Constructs a Territory with specified name, number of units, and neighbor territories
     * @param _name the name of the Territory
     * @param _numUnits the number of units in the Territory
     * @param _neighbors the neighbors of the Territory
     * @param _units units in this Territory
     */
    //TODO: input: units?
    public Territory(String _name, int _numUnits, ArrayList<Territory> _neighbors, HashMap<Class<?>, Integer> _units) {
        this.name = _name;
        this.numUnits = _numUnits;
        this.neighbors = _neighbors;
        this.units = _units;
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
     * Add a Territory to the neighbors,
     * if the Territory is valid, add and returns true,
     * otherwise, return false
     *
     * @param aNeighbor the Territory to add to the neighbors
     * @return true if the Territory is valid to add, otherwise false
     */
    public boolean tryAddANeighbor(Territory aNeighbor) {
        if (!aNeighbor.isAValidNeighbor()) {
            return false;
        }
        neighbors.add(aNeighbor);
        return true;
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
        output.append(numUnits).append(" units in ").append(name);
        if (neighbors.isEmpty()) {
            output.append(" (no neighbors)\n");
        } else {
            output.append(" (next to: ");
            for (int i = 0; i < neighbors.size(); i++) {
                if (i < neighbors.size() - 1) {
                    output.append(neighbors.get(i).getName()).append(", ");
                } else {
                    output.append(neighbors.get(i).getName());
                }
            }
            output.append(")\n");
        }
        return String.valueOf(output);
    }

    /** getters and setters **/
    public String getName() {
        return name;
    }

    public int getNumUnits() {
        return numUnits;
    }

    public ArrayList<Territory> getNeighbors() {
        return neighbors;
    }
}
