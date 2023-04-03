package edu.duke.ece651.team3.shared;

import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * A class of rule checker for only checking moves actions
 *
 */
public class UpgradeRuleChecker extends RuleChecker{
    private final Board riskGameBoard;
    private final Action action;
    private String srcName;
    private String dstName;


    /**
     * Check whether the current player and enemy have the territory from attack's information
     * @param _action attack information
     * @param _riskGameBoard current player
     * @return if valid return true, invalid return false
     */
    public UpgradeRuleChecker(Action _action, Board _riskGameBoard){
        super(_action);
        this.action = _action;
        this.srcName = this.action.getSrcName();
        this.dstName = this.action.getDstName();
        this.riskGameBoard = _riskGameBoard;
    }


    /**
     * Check whether the current player's src territory is its owned territory
     * @param myUpgrade move information
     * @param currPlayer current player
     * @return if valid return true, invalid return false
     */
    @Override
    public boolean checkSrcDst(Action myUpgrade, Player currPlayer){
        return currPlayer.checkTerrOwner(myUpgrade.getSrcName());
    }

    /**t
     * Find the territory owned by current player and return the territory
     * @param myAttack move information
     * @param currPlayer current player
     * @return territory owned by current player
     */
    public Territory findTerritory(Action myAttack, Player currPlayer){
        int length = currPlayer.getOwnedTerritories().size();
        Territory t = null;
        for (int i = 0; i < length; i++) {
            if (currPlayer.getOwnedTerritories().get(i).getTerritoryName().equals(myAttack.getSrcName())) {
                t = currPlayer.getOwnedTerritories().get(i);
            }
        }
        return t;
    }

    /**
     * Check whether the move's num of units is valid for each level of unit
     * if valid return true if invalid return false
     * @param myMove move information
     * @param currPlayer current player
     * @return if valid return true, invalid return false
     */
    public boolean checkNumUnits(Action myMove, Player currPlayer){
        Territory t = findTerritory(myMove, currPlayer);
        for(int i = 0; i < t.getUnits().size(); i++){
            ArrayList<Unit> curr = myMove.getUnitsToChange();
            int numUnitsChange = myMove.getUnitsToChange().get(i).getNumUnits();
            if(numUnitsChange > t.getUnits().get(i).getNumUnits() || numUnitsChange < 0){
                System.out.println("Invalid numberUnits: " + numUnitsChange + " current territory's unit: "
                        + t.getUnits().get(i).getNumUnits());
                return false;
            }
        }
        return true;
    }

    /**
     * This method checks whether the path is correct
     * It uses DFS to check whether the territory belongs to the self player
     * @param myMove move information
     * @param currPlayer current player
     * @return true if valid false if invalid
     */
    public boolean checkPath(Action myMove, RiskGameBoard r, Player currPlayer) {
        String srcName = myMove.getSrcName();
        String dstName = myMove.getDstName();
        Territory src = findTerritoryByName(srcName, currPlayer);
        Territory dst = findTerritoryByName(dstName, currPlayer);
        if(dst == null){
            return false;
        }
        HashMap<Territory, Boolean> visited = new HashMap<>();

        Player OtherPlayer1 = r.getAllPlayers().get(0);
        Player OtherPlayer2 = r.getAllPlayers().get(1);
        initVisited(currPlayer, visited);
        initVisited(OtherPlayer1, visited);
        initVisited(OtherPlayer2, visited);
        if(isValidPath(src, dst, visited, currPlayer)){
            return true;
        }
        return false;
    }

    /**
     * This method using dfs, if the path exist, return true. If not, return false
     * @param src the source territory
     * @param dst the destination territory
     * @param visited whether the territory has been visited
     * @return
     */
    public boolean isValidPath(Territory src, Territory dst,
                               HashMap<Territory, Boolean> visited, Player currPlayer) {
        if (src.equals(dst) && checkIsSelfTerritory(dst, currPlayer)) {
            return true;
        }

        visited.put(src, true);
        HashMap<Territory, Integer> neighbors = src.getNeighborsDist();
        for(Territory territory : neighbors.keySet()){
            if (!visited.get(territory) && checkIsSelfTerritory(territory, currPlayer) &&
                    isValidPath(territory, dst, visited, currPlayer)) {
                return true;
            }
        }
        return false;
    }

    /**
     * This method takes the territory's name and the current player
     * @param territoryName
     * @param currPlayer
     * @return the territory which name is territoryName
     */
    public Territory findTerritoryByName(String territoryName, Player currPlayer){
        int length = currPlayer.getOwnedTerritories().size();
        Territory t = null;
        for (int i = 0; i < length; i++) {
            if (currPlayer.getOwnedTerritories().get(i).getTerritoryName().equals(territoryName)) {
                t = currPlayer.getOwnedTerritories().get(i);
            }
        }
        return t;
    }

    /**
     * This method checks whether the current territory is self territory
     * @param territory
     * @param p
     * @return true if it is self territory, false if it is not
     */
    public boolean checkIsSelfTerritory(Territory territory, Player p){
        for(int i = 0; i < p.getOwnedTerritories().size(); i++){
            if(territory.equals(p.getOwnedTerritories().get(i))){
                return true;
            }
        }
        return false;
    }

    /**
     * This method initialized the visited HashMap
     * @param player
     * @param visited
     */
    public void initVisited(Player player, HashMap<Territory, Boolean> visited){
        for(int i = 0; i < player.getOwnedTerritories().size(); i++){
            visited.put(player.getOwnedTerritories().get(i), false);
        }
    }


    /**
     * This method gets the path that has the minimal food resource cost
     * @param src the source territory
     * @param dst the destination territory
     * @param riskGameBoard the whole board
     * @return the minimal cost of the path
     */
    public int getMinPath(Territory src, Territory dst, RiskGameBoard riskGameBoard){
        Player currPlayer = getPlayer(src.getTerritoryName(), riskGameBoard);
        ArrayList<Territory> allSelfTerritories = currPlayer.getOwnedTerritories();

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
     * This method checks whether there is enough technology resource for the upgrade action
     * @param myAction the current action
     * @param riskGameBoard the whole board
     * @param currPlayer the current player
     * @return boolean true if it is enough, false if it is nor.
     */
    public boolean checkResources(Action myAction, RiskGameBoard riskGameBoard, Player currPlayer){
        Territory src = currPlayer.findOwnedTerritoryByName(myAction.getSrcName());
        Territory dst = currPlayer.findOwnedTerritoryByName(myAction.getDstName());

        int totalResourceCost = getMinPath(src, dst, riskGameBoard);
        System.out.println("The total number of food cost is: " + totalResourceCost);
        if(totalResourceCost > src.getFood()){
            System.out.println("Invalid move! The resource is not enough!");
            return false;
        }
        return true;
    }

    /**
     * This method gets the player that owns the given territory.
     * @param territoryName the territory's name
     * @param riskGameBoard the whole board
     * @return the current player
     */
    public Player getPlayer(String territoryName, RiskGameBoard riskGameBoard){
        Player currPlayer = null;
        ArrayList<Player> allPlayers = riskGameBoard.getAllPlayers();

        for(Player p : allPlayers){
            for(Territory t: p.getOwnedTerritories()){
                if(t.getTerritoryName().equals(territoryName)){ //If the territory name under current player equals to the source name
                    currPlayer = p;
                    break;
                }
            }
        }
        return currPlayer;
    }
}
