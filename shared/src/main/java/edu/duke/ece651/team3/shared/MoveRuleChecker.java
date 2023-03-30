package edu.duke.ece651.team3.shared;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * A class of rule checker for only checking moves actions
 *
 */
public class MoveRuleChecker extends RuleChecker{
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
    public MoveRuleChecker(Action _action, Board _riskGameBoard){
        super(_action);
        this.action = _action;
        this.srcName = this.action.getSrcName();
        this.dstName = this.action.getDstName();
        this.riskGameBoard = _riskGameBoard;
    }

    /**
     * Check whether the current player's src and dst territory are both in its owned territory
     * @param myAttack move information
     * @param currPlayer current player
     * @return if valid return true, invalid return false
     */
    public boolean checkSrcDst(Action myAttack, Player currPlayer){
        return currPlayer.checkTerrOwner(myAttack.getSrcName()) && currPlayer.checkTerrOwner(myAttack.getDstName());
    }

    /**t
     * Find the territory owned by current player and return the territory
     * @param myMove move information
     * @param currPlayer current player
     * @return territory owned by current player
     */
    public Territory findTerritory(Action myMove, Player currPlayer){
        int length = currPlayer.getOwnedTerritories().size();
        Territory t = null;
        for (int i = 0; i < length; i++) {
            if (currPlayer.getOwnedTerritories().get(i).getTerritoryName().equals(myMove.getSrcName())) {
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
            int numUnitsChange = myMove.getActionUnits().get(i).getNumUnits();
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
//     * @param src
     * @return the minimal cost of the path
     */
//    public int getMinPath(Territory src, RiskGameBoard riskGameBoard){
//        HashMap<Territory, Integer> neighbors = src.getNeighborsDist();
//        Player currPlayer = getPlayer(src.getTerritoryName(), riskGameBoard);
//
//        for(Territory currTerritory : neighbors.keySet()){
//
//        }
//        return 0;
//    }

    public int getMinCostPath(Action myMove, RiskGameBoard r, Player currPlayer) {
        String srcName = myMove.getSrcName();
        String dstName = myMove.getDstName();
        Territory src = findTerritoryByName(srcName, currPlayer);
        Territory dst = findTerritoryByName(dstName, currPlayer);

        HashMap<Territory, Boolean> visited = new HashMap<>();

        Player OtherPlayer1 = r.getAllPlayers().get(0);
        Player OtherPlayer2 = r.getAllPlayers().get(1);

        //Initialize to false
        initVisited(currPlayer, visited);
        initVisited(OtherPlayer1, visited);
        initVisited(OtherPlayer2, visited);

        int costSum =

        int minCost = getMinCostPath_helper(src, dst, visited, currPlayer);
        return minCost;
    }

    /**
     * This method using dfs, if the path exist, return true. If not, return false
     * @param src the source territory
     * @param dst the destination territory
     * @param visited whether the territory has been visited
     * @return
     */
    public int getMinCostPath_helper(Territory src, Territory dst,
                               HashMap<Territory, Boolean> visited, Player currPlayer,
                               int minCost) {
        if (src.equals(dst) && checkIsSelfTerritory(dst, currPlayer)) {
            return true;
        }

        visited.put(src, true);
        int currCost =
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
     * This method checks whether there is enough resource for the move action
     * @param territory
     * @return boolean true if it is enough, false if it is nor.
     */
    public boolean checkResource(Territory territory, RiskGameBoard riskGameBoard){
        int totalResourceCost = getMinPath(territory, riskGameBoard);
        if(totalResourceCost > territory.getFoodResource()){
            System.out.println("Invalid move! The resource is not enough!");
            return false;
        }
        return true;
    }

    /**
     * This method gets the player that owns the given territory.
     * @param territoryName the territory's name
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
