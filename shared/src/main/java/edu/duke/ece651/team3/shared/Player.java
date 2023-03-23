package edu.duke.ece651.team3.shared;

import java.io.Serializable;
import java.util.ArrayList;

public class Player implements Serializable {
    private final int playerId;
    private final String color;
    private final int totNumUnits;
    private final ArrayList<Territory> ownedTerritories;

    /**
     * This constructor builds up the player with 3 input paremeters
     * @param _id the player's id
     * @param _color the player's color
     * @param _totNumUnits the total number of the units that all territory of the player obtains
     */
    public Player(int _id, String _color, int _totNumUnits) {
        this.playerId = _id;
        this.color = _color;
        this.totNumUnits = _totNumUnits;
        this.ownedTerritories = new ArrayList<>();
    }

    /**
     * This constructor builds up the player with 4 input parameters
     * @param _id the player's id
     * @param _color the player's color
     * @param _totNumUnits the total number of units
     * @param _ownedTerritories an ArrayList contains all the territory a player owns
     */
    public Player(int _id, String _color, int _totNumUnits, ArrayList<Territory> _ownedTerritories) {
        this.playerId = _id;
        this.color = _color;
        this.totNumUnits = _totNumUnits;
        this.ownedTerritories = _ownedTerritories;
    }

    public void executeMove(Action move) {
        Territory src = findOwnedTerritoryByName(move.getSrcName());
        Territory dst = findOwnedTerritoryByName(move.getDstName());
        for (Integer forceLevel: move.getActionUnits().keySet()) {
            src.decreaseUnit(forceLevel, move.getActionUnits().get(forceLevel));
            dst.increaseUnit(forceLevel, move.getActionUnits().get(forceLevel));
        }
    }

    public void executeAttack(Action move) {
        Territory src = findOwnedTerritoryByName(move.getSrcName());
        for (Integer forceLevel: move.getActionUnits().keySet()) {
            src.decreaseUnit(forceLevel, move.getActionUnits().get(forceLevel));
        }
    }

    public Territory findOwnedTerritoryByName(String territoryName) {
        for (Territory eachTerritory: ownedTerritories) {
            if (eachTerritory.getTerritoryName().equals(territoryName)) {
                return eachTerritory;
            }
        }
        return null;
    }

    /**
     * This method overrides the equal method to compare whether two players are the same
     * @param other the other player
     * @return true if two players are same, false if they are not
     */
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
     * Find the expected owner whether it has the territory
     * @param territoryName territory name
     * @return if owned by expected owner return true, else return false
     */
    public boolean checkTerrOwner(String territoryName){
        territoryName.toLowerCase();
//        out.println("The territory name is: " + territoryName);
        int length = ownedTerritories.size();
        for (int i = 0; i < length; i++) {
            String currName = ownedTerritories.get(i).getTerritoryName();
//            String currName1 = currName.toLowerCase();
            if(territoryName.equalsIgnoreCase(currName)){
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
        if (!isValidToOwn(territoryToOwn)) {
            return false;
        }
        ownedTerritories.add(territoryToOwn);
        return true;
    }

    /**
     * This method displays the player
     * @return A String that contains all information of it
     */
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

    /**
     * This method removes the territory from the current player
     * @param toLose
     */
    public void loseTerritory(Territory toLose){
        ownedTerritories.remove(toLose);
    }

    /**
     * This method adds the new occupied territory when the current player attacks successfully
     * The name should be the src name from the attacker
     * The units number should be the remaining + the attack
     */
    public void occupyTerritory(Action currAttack, int loseTimes){
        int units_new = currAttack.getActionUnits().get(1) - loseTimes; //TODO: check 1 for force level
        Territory newOccupy = new Territory(currAttack.getDstName(), units_new);
        ownedTerritories.add(newOccupy);
    }

    /** getters and setters **/
    public int getPlayerId() {
        return playerId;
    }

    /**
     * This method gets all the territory that a player owned
     * @return
     */
    public ArrayList<Territory> getOwnedTerritories() {
        return ownedTerritories;
    }

    /**
     * This method gets the color of the player to identify it
     * @return the String contains color
     */
    public String getColor() {
        return color;
    }

    /**
     * This method gets the total number of Units a player obtains
     * @return int of the total number of units
     */
    public int getTotNumUnits() {
        return totNumUnits;
    }
}
