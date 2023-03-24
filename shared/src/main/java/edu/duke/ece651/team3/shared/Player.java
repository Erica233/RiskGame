package edu.duke.ece651.team3.shared;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

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

    /**
     * Increase the number of the basic unit (whose force level is one) by one in each owned territory
     */
    public void addAUnitForEachTerr() {
        for (Territory territory: ownedTerritories) {
            territory.increaseUnit(1, 1);
        }
    }

    /**
     * execute the given move action, update the number of units
     *
     * @param move the move action
     */
    public void executeMove(Action move) {
        Territory src = findOwnedTerritoryByName(move.getSrcName());
        Territory dst = findOwnedTerritoryByName(move.getDstName());
        for (Integer forceLevel: move.getActionUnits().keySet()) {
            src.decreaseUnit(forceLevel, move.getActionUnits().get(forceLevel));
            dst.increaseUnit(forceLevel, move.getActionUnits().get(forceLevel));
        }
    }

    /**
     * execute the given attack action (only decrease the units in the source territory)
     *
     * @param attack the attack action
     */
    public void executeAttack(Action attack) {
        Territory src = findOwnedTerritoryByName(attack.getSrcName());
        for (Integer forceLevel: attack.getActionUnits().keySet()) {
            src.decreaseUnit(forceLevel, attack.getActionUnits().get(forceLevel));
        }
    }

    /**
     * get the territory given its territory name, if not find, returns null
     *
     * @param territoryName the name of the territory want to find
     * @return the territory
     */
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
     * If the territory is owned by the player, removes it
     * else, print out error message
     * @param toLose
     */
    public void loseTerritory(Territory toLose){
        if (ownedTerritories.contains(toLose)) {
            ownedTerritories.remove(toLose);
        } else {
            System.err.println("Player " + playerId + "cannot lose that Territory " + toLose.getTerritoryName());
        }

    }

    /**
     * This method adds the new occupied territory when the current player attacks successfully
     * The name should be the src name from the attacker
     * The units number should be the remaining + the attack
     */
    public void occupyTerritory(Territory defenderTerritory, int attackUnits, int loseTimes){
        ownedTerritories.add(defenderTerritory);
        defenderTerritory.increaseUnit(1, attackUnits);
        defenderTerritory.decreaseUnit(1, loseTimes);
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
