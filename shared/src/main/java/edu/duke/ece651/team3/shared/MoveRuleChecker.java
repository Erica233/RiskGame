package edu.duke.ece651.team3.shared;

import java.util.ArrayList;
import java.util.HashMap;

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
     * Check whether the attack's num of units is valid, if valid return true if invalid return false
     * @param myMove move information
     * @param currPlayer current player
     * @return if valid return true, invalid return false
     */
    public boolean checkNumUnits(Action myMove, Player currPlayer){
        Territory t = findTerritory(myMove, currPlayer);
        for(Integer c : myMove.getActionUnits().keySet()){
            int numUnits = myMove.getActionUnits().get(c);
            //If move units is greater than the current scr unit
            if(numUnits > t.getUnits().get(c) || numUnits < 0){
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
        ArrayList<Territory> Neighbors = src.getNeighbors();
        for(int i = 0; i < Neighbors.size(); i++){
            if (!visited.get(Neighbors.get(i)) && checkIsSelfTerritory(Neighbors.get(i), currPlayer) &&
                    isValidPath(Neighbors.get(i), dst, visited, currPlayer)) {
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

}
