package edu.duke.ece651.team3.shared;

import java.io.Serializable;

/**
 * A class for Territory
 */
public class Territory implements Serializable {
    private final String name;
    private int numUnits;

    /**
     * Constructs a Territory with specified name
     *
     * @param _name the name of the Territory
     * @param _numUnits the number of units in the Territory
     */
    public Territory(String _name, int _numUnits) {
        this.name = _name;
        this.numUnits = _numUnits;
    }

    /**
     * Displays the Territory information, including name and number of units
     *
     * @return a String about number of units and name of the territory
     */
    public String displayTerritory() {
        return numUnits + " units in " + name + "\n";
    }

    /** getters and setters **/
    public String getName() {
        return name;
    }

    public int getNumUnits() {
        return numUnits;
    }
}
