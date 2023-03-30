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
    private int numUnits = 0;
    private HashMap<Territory, Integer> neighborsDist;
    private ArrayList<Unit> units;
    private int winnerId = -1;
    private HashMap<Integer, Integer> attackerUnits = new HashMap<>();

    /**
     * Initialize the units, given the Infantry number
     *
     * @param num the number of Infantry units
     */
    public void initBasicUnits(int num) {
        units.add(new Infantry(num));
        units.add(new Cavalry(0));
        units.add(new Artillery(0));
        units.add(new SpecialForces(0));
    }

    /**
     * Constructs an empty Territory with specified name, (no neighbors, no units)
     *
     * @param _name the name of the Territory
     */
    public Territory(String _name) {
        this.territoryName = _name;
        this.neighborsDist = new HashMap<>();
        this.units = new ArrayList<>();
        initBasicUnits(0);
    }

    /**
     * This constructor constructs a territory with name, and num of basic units (Infantry)
     *
     * @param _name the name of the Territory
     * @param num the number of Infantry units
     */
    public Territory(String _name, int num) {
        this.territoryName = _name;
        this.neighborsDist = new HashMap<>();
        this.units = new ArrayList<>();
        initBasicUnits(num);
        updateNumUnits();
    }

    /**
     * Constructs a Territory with specified name, num of basic units (Infantry), and neighbor territories
     *
     * @param _name the name of the Territory
     * @param _neighborsDist neighbors and their relative distance
     * @param num the number of basic units (Infantry)
     */
    public Territory(String _name, HashMap<Territory, Integer> _neighborsDist, int num) {
        this.territoryName = _name;
        this.units = new ArrayList<>();
        this.neighborsDist = _neighborsDist;
        initBasicUnits(num);
        updateNumUnits();
    }

    @Override
    public int compareTo(Territory aTerritory) {
        return this.territoryName.compareTo(aTerritory.getTerritoryName());
    }

    @Override
    public boolean equals(Object other) {
        if (other.getClass().equals(getClass())) {
            Territory territory = (Territory) other;
            return numUnits == territory.getNumUnits() && territoryName.equals(territory.getTerritoryName()) && hasSameNeighborsDist(territory) && hasSameUnits(territory);
        }
        return false;
    }

    public HashMap<Integer, Integer> getAttackerUnits() {
        return attackerUnits;
    }

    public void setUnits(ArrayList<Unit> _units) {
        units = _units;
    }

    /**
     * update the combat result, and reset the winnerId and attackerUnits field
     *
     * @param ownerId the original owner id of the territory before the combat
     */
    public void updateCombatResult(int ownerId) {
        //System.out.print("in updateCombatResult - winnerid=" + winnerId);
        //System.out.println(" - attackerUnits: "+attackerUnits.get(1));
        //update units
        if (winnerId != ownerId) {
            units = attackerUnits;
            //System.out.println("after update - units: "+units.get(1));
        }
        //reset
        winnerId = -1;
        attackerUnits = new HashMap<>();
    }

    /**
     * check if the two territories have the same neighbors and their relative distances
     *
     * @param territoryToCompare
     * @return true if the two territories has the same neighbors and their relative distances, otherwise return false
     */
    public boolean hasSameNeighborsDist(Territory territoryToCompare) {
        if (neighborsDist.size() != territoryToCompare.getNeighborsDist().size()) {
            return false;
        }
        for (Territory territory: neighborsDist.keySet()) {
            if (territoryToCompare.getNeighborsDist().containsKey(territory)) {
                if (territoryToCompare.getNeighborsDist().get(territory) != neighborsDist.get(territory)) {
                    return false;
                }
            } else {
                return false;
            }
        }
        return true;
    }

    /**
     * check if the two territories have the same set of units
     *
     * @param territoryToCompare
     * @return true if the two territories have the same set of units
     */
    public boolean hasSameUnits(Territory territoryToCompare) {
        for (int i = 0; i < units.size(); i++) {
            if (units.get(i).getClass() != territoryToCompare.getUnits().get(i).getClass() || units.get(i) != territoryToCompare.getUnits().get(i)) {
                return false;
            }
        }
        return true;
    }

    /**
     * This method gets the sorted neighbors' names
     * @return an ArrayList<String> contains all names
     */
    public ArrayList<String> getSortedNeighborNames() {
        ArrayList<String> sortedNeighborNames = new ArrayList<>();
        for (Territory aNeighbor: neighborsDist.keySet()) {
            sortedNeighborNames.add(aNeighbor.getTerritoryName());
        }
        Collections.sort(sortedNeighborNames);
        return sortedNeighborNames;
    }

    /**
     * Add a valid Territory to the neighbors
     *
     * @param aNeighbor the Territory to add to the neighbors
     * @throws Exception if the Territory to add is invalid
     */
    public void addANeighbor(Territory aNeighbor, int dist) throws Exception {
        if (!checkValidNeighbor(aNeighbor)) {
            throw new Exception("addANeighbor(): invalid neighbor to add!");
        }
        neighborsDist.put(aNeighbor, dist);
        //Collections.sort(neighborsDist);
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
        for (Territory aNeighbor: neighborsDist.keySet()) {
            if (aNeighbor.getTerritoryName().equals(territoryToAddAsNeighbor.getTerritoryName())) {
                return false;
            }
        }
        return true;
    }

    /**
     * to check whether is object's neighbor
     * @param territoryToCheck territory To Check whether is its neighbor
     * @return true if it is neighbrr false if it is not neighbor
     */
    public boolean checkExistNeighbor(String territoryToCheck){
        for (Territory neighbor : neighbors) {
            if (territoryToCheck.equals(neighbor.getTerritoryName())) {
                return true;
            }
        }
        return false;
    }

    /**
     * Displays the Territory information, including its name, number of units and neighbors
     *
     * @return a String about its name, number of units and the neighbor territories
     */
    public String displayTerritory() {
        StringBuilder output = new StringBuilder();
        output.append(numUnits).append(" units in ").append(territoryName);
        if (neighborsDist.isEmpty()) {
            output.append(" (no neighbors)\n");
        } else {
            output.append(" (next to: ");
            for (Territory neigh: neighborsDist.keySet()) {
                output.append(neighborsDist.get(neigh.getTerritoryName()) + " " + neigh.getTerritoryName() + ", ");
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
        for(Integer c : units.keySet()){
            num += units.get(c);
        }
        numUnits = num;
    }

    /**
     * Add specific type and number of unit to units
     * @param unitToAdd specific type of unit to add
     * @param num number of this type of unit
     */
    public void increaseUnit(int unitToAdd, int num) {
        if (units.containsKey(unitToAdd)) {
            int value = units.get(unitToAdd) + num;
            units.put(unitToAdd, value);
        } else {
            units.put(unitToAdd, num);
        }
        updateNumUnits();
    }

    /**
     * Delete specific type and number of unit to units
     * @param unitToRemove specific type of unit to remove
     * @param num number of this type of unit
     */
    public void decreaseUnit(int unitToRemove, int num) {
        if (units.containsKey(unitToRemove)) {
            int value = units.get(unitToRemove) - num;
            //System.out.println(unitToRemove);
            if (value < 0) {
                throw new IllegalArgumentException("Can't delete too much num of unit");
            }
            units.put(unitToRemove, value);
        } else {
            throw new IllegalArgumentException("Can't delete non-existing unit");
        }
        updateNumUnits();
    }

    /**
     * checks if the territory is valid to add
     *
     * @param allTerritories
     * @param toAdd
     * @return
     */
    public boolean isValidToAdd(ArrayList<Territory> allTerritories, Territory toAdd){
        for(Territory t: allTerritories){
            if(toAdd.equals(t)){
                return false;
            }
        }
        return true;
    }


    /** getters and setters **/
    public String getTerritoryName() {
        return territoryName;
    }

    /**
     * This method gets the num of units of a territory
     * @return the num of units
     */
    public int getNumUnits() {
        return numUnits;
    }


    /**
     * This method gets a hashmap of units
     * @return hashmap of units with <level, num>
     */
    public ArrayList<Unit> getUnits() {
        return units;
    }

    public void setWinnerId(int winnerId){
        this.winnerId = winnerId;
    }

    public void setAttackerUnits(HashMap<Integer, Integer> newUnites){
        this.attackerUnits = newUnites;
    }

    public int getWinnerId() {
        return winnerId;
    }

    public HashMap<Territory, Integer> getNeighborsDist() {
        return neighborsDist;
    }
}
