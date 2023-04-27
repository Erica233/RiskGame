package edu.duke.ece651.team3.shared;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * A player class for store player information
 *
 */
public class Player implements Serializable {
    private final int playerId;
    private final String color;
    private final int totNumUnits;
    private final ArrayList<Territory> ownedTerritories;
    String username;

    public void setUsername(String username) {
        this.username = username;
    }

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
     * Increase the number of the basic unit (whose force level is 0) by one in each owned territory
     */
    public void addAUnitForEachTerr() {
        for (Territory territory: ownedTerritories) {
            territory.increaseOneBasicUnit();
        }
    }

    /**
     * Add the food and technology source for every territory
     */
    public void addResourceForEachTerr() {
        for (Territory territory: ownedTerritories) {
            territory.increaseResource();
        }
    }

    /**
     * This method checks whether the territory is the neighbor of the current territory
     * @param curr the current territory
     * @param checkNeighbor the territory to be checked
     * @return ture if it is the neighbor of curr, false otherwise
     */
    public boolean isNeighbor(Territory curr, Territory checkNeighbor){
        HashMap<Territory, Integer> neighbors = curr.getNeighborsDist();
        for(Territory territory : neighbors.keySet()){
            if(checkNeighbor.equals(territory)){
                return true;
            }
        }
        return false;
    }

    /**
     * This method gets the path that has the minimal food resource cost
     * @param src the source territory
     * @param dst the destination territory
     * @return the minimal cost of the path
     */
    public int getMinPath(Territory src, Territory dst){
        ArrayList<Territory> allSelfTerritories = getOwnedTerritories();

        HashMap<Territory, Boolean> visited = new HashMap<>();
        HashMap<Territory, Integer> distances = new HashMap<>();

        //Initialize distances to max and visited
        for(int i = 0; i < allSelfTerritories.size(); i++){
            Territory currT = allSelfTerritories.get(i);
            //If the current territory is the neighbor of the src
            //its distance should be the direct distance
            if(isNeighbor(src, currT)){
                distances.put(currT, src.getNeighborsDist().get(currT));
            }
            else {
                distances.put(allSelfTerritories.get(i), Integer.MAX_VALUE);
            }
            visited.put(allSelfTerritories.get(i), false);
        }

        //Put the source territory's distance to 0
        distances.put(src, 0);

        Territory minTerr = src;

        for(int i = 0; i < allSelfTerritories.size() - 1; i++){
            int isUpdate = 0;

            //Get the Territory that has the min cost to the current territory
            for(Territory currTerr : allSelfTerritories){
                if(currTerr.equals(minTerr)){
                    visited.replace(minTerr, true);
                }

                if(i != 0 && isUpdate == 0 && !visited.get(currTerr) && isNeighbor(minTerr, currTerr)){
                    minTerr = currTerr;
                    isUpdate = 1;
                }
                if(!visited.get(currTerr) && isNeighbor(minTerr, currTerr) &&
                        (distances.get(minTerr) != 0 && distances.get(currTerr) < distances.get(minTerr)
                                || distances.get(minTerr) == 0)){
                    minTerr = currTerr;
                }
            }
            visited.replace(minTerr, true); //Have used the minTerr as the middle node

            //Updating the distance for each neighbors of Territory
            for(Territory newTerr : allSelfTerritories){
                if(!visited.get(newTerr) && isNeighbor(minTerr, newTerr)
                        && distances.get(minTerr) + minTerr.getNeighborsDist().get(newTerr) < distances.get(newTerr)){
                    distances.replace(newTerr, distances.get(minTerr) + minTerr.getNeighborsDist().get(newTerr));
                }
            }

        }
        return distances.get(dst);
    }


    /**
     * execute the given move action, update the number of units
     *
     * @param move the move action
     */
    public void executeMove(Action move) {
        Territory src = findOwnedTerritoryByName(move.getSrcName());
        Territory dst = findOwnedTerritoryByName(move.getDstName());

        src.decreaseUnit(move.getUnitsToChange());
        dst.increaseUnit(move.getUnitsToChange());

        for(int i = 0; i < move.getUnitsToChange().size(); i++) {
            int unitNum = move.getUnitsToChange().get(i).getNumUnits(); //The num to move
            //Reduce the cost on the current territory
            int minPathCost = getMinPath(src, dst);
            int moveCost = move.getUnitsToChange().get(i).getMoveCost();
            int cost = minPathCost * unitNum * moveCost; //TODO:check the formula
            src.reduceFood(cost);
        }
    }

    /**
     * execute the event by decreasing its territory's technology and food resources by 2
     */
    public void executeEvent(Action event){
        Territory currTerr = findOwnedTerritoryByName(event.getSrcName());
        currTerr.decreaseResource();
    }

    /**
     * calculate the total cost of attack
     * @param myAttack
     * @param currPlayer
     * @return total cost of the attack
     */
    public int decreaseFood(Action myAttack, Player currPlayer){
        String src = myAttack.getSrcName();
        String dst = myAttack.getDstName();
        Territory terr = currPlayer.findOwnedTerritoryByName(src);
        int foodRsc = terr.getFood();
        for(int i = 0; i < myAttack.getUnitsToChange().size(); i++){
            HashMap<Territory, Integer> neighbors = terr.getNeighborsDist();
            int distance = 0;
            for(Territory t : neighbors.keySet()){
                if(t.getTerritoryName().equals(dst)){
                    distance = neighbors.get(t);
                }
            }
            foodRsc -= (i+1) * myAttack.getUnitsToChange().get(i).getNumUnits() * distance;
            if(foodRsc < 0){
                return foodRsc;
            }
        }
        return terr.getFood()-foodRsc;
    }


    /**
     * execute the given attack action (only decrease the units in the source territory)
     * @param attack the attack action
     */
    public void executeAttack(Action attack) {
        Territory src = findOwnedTerritoryByName(attack.getSrcName());
        src.decreaseUnit(attack.getUnitsToChange());
        src.setFood(src.getFood()-decreaseFood(attack, this));
    }

    /**
     * execute the given attack action (only decrease the units in the source territory)
     * @param myUpgrade the attack action
     */
    public void executeUpgrade(Action myUpgrade) {
        Territory src = findOwnedTerritoryByName(myUpgrade.getSrcName());
        src.decreaseUnit(myUpgrade.getUnitsToChange());
        src.increaseUpgradeUnit(myUpgrade.getUnitsToChange());

        //Consume the tech resources
        int techCost = 0;
        for(int i = 0; i < myUpgrade.getUnitsToChange().size() - 1; i++){
            Unit unitTo = myUpgrade.getUnitsToChange().get(i + 1);
            Unit unitFrom = myUpgrade.getUnitsToChange().get(i);
            int currUnitCost = unitTo.getUpgradeCost() - unitFrom.getUpgradeCost();
            techCost += currUnitCost * unitFrom.getNumUnits();
        }
        System.out.println("The current total tech cost is: " + techCost);
        src.reduceTechnology(techCost);
    }

    /**
     * get the territory given its territory name, if not find, returns null
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
