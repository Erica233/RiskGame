package edu.duke.ece651.team3.shared;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * A class of rule checker for only checking moves actions
 *
 */
public class MoveRuleChecker{
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
//        super(_action);
        this.action = _action;
        this.srcName = this.action.getSrcName();
        this.dstName = this.action.getDstName();
        this.riskGameBoard = _riskGameBoard;
    }

    public boolean checkValidAction(Action myAction, RiskGameBoard r, Player currPlayer) throws Exception {
        if (!checkSrcDst(myAction, currPlayer)) {
            System.out.printf("src dst invalid");
            return false;
        }
        if (!checkNumUnits(myAction, currPlayer)) {
            System.out.printf("checkNumUnits invalid");
            return false;
        }
        if (!checkPath(myAction, r, currPlayer)) {
            System.out.printf("checkPath invalid");
            return false;
        }
        if(!checkResources(myAction, currPlayer, r)){
            System.out.printf("checkResources invalid");
            return false;
        }
        return true;
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
        for (Territory territory : allSelfTerritories) {
            distances.put(territory, Integer.MAX_VALUE);
            visited.put(territory, false);
        }

        //Put the source territory's distance to 0
        distances.put(src, 0);

       for(int i = 0; i < allSelfTerritories.size() - 1; i++){
           Territory minTerr = src;

           //Get the Territory that has the min cost to the current territory
           for(Territory currTerr : allSelfTerritories){
               if(!visited.get(currTerr) && (minTerr.equals(src) || distances.get(currTerr) < distances.get(minTerr))){
                   minTerr = currTerr;
               }
           }
           visited.replace(minTerr, true);
//           Territory minTerr = selectMinCostTerrToSrc(currTerritory, allTerritories, distances, visited, currPlayer);
//           visited.replace(minTerr, true);

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
     * This method checks whether there is enough resource for the move action
     * @param
     * @return boolean true if it is enough, false if it is nor.
     */
    public boolean checkResources(Action myAction, Player currPlayer, RiskGameBoard riskGameBoard){
        Territory src = currPlayer.findOwnedTerritoryByName(myAction.getSrcName());
        Territory dst = currPlayer.findOwnedTerritoryByName(myAction.getDstName());

        int totalResourceCost = getMinPath(src, dst, riskGameBoard);
        if(totalResourceCost > src.getFood()){
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
