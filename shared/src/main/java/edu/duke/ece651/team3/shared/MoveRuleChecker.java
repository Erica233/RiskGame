package edu.duke.ece651.team3.shared;

import java.util.ArrayList;
import java.util.HashMap;

public class MoveRuleChecker extends RuleChecker{
    private final RiskGameBoard riskGameBoard;
    private final Action action;
    Territory src;
    Territory dst;
    public MoveRuleChecker(Action _action, RiskGameBoard _riskGameBoard){
        super(_action);
        this.action = _action;
        this.src = this.action.getSrc();
        this.dst = this.action.getDst();
        this.riskGameBoard = _riskGameBoard;
    }

    /**
     * Check whether the current player's src and dst territory are both in its owned territory
     * @param myAttack move information
     * @param currPlayer current player
     * @return if valid return true, invalid return false
     */
    public boolean checkSrcDst(Action myAttack, Player currPlayer){
        return currPlayer.checkTerrOwner(myAttack.getSrc().getTerritoryName()) && currPlayer.checkTerrOwner(myAttack.getDst().getTerritoryName());
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
            if (currPlayer.getOwnedTerritories().get(i).getTerritoryName().equals(myMove.getSrc().getTerritoryName())) {
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
    public boolean checkPath(Action myMove, RiskGameBoard r, Player currPlayer) throws Exception {
        Territory src = myMove.getSrc();
        Territory dst = myMove.getDst();
        ArrayList<Territory> Neighbors = src.getNeighbors();
        HashMap<Territory, Boolean> visited = new HashMap<>();

        Player OtherPlayer1 = r.getAllPlayers().get(0);
        Player OtherPlayer2 = r.getAllPlayers().get(1);
        Player OtherPlayer = null;
        if(OtherPlayer1.equals(currPlayer)){
            OtherPlayer = OtherPlayer2;
        }
        if(OtherPlayer2.equals(currPlayer)){
            OtherPlayer = OtherPlayer1;
        }

        initVisited(currPlayer, visited);
        initVisited(OtherPlayer, visited);
        if(isValidPath(src, dst, Neighbors, visited, currPlayer)){
            return true;
        }
        return false;
    }



    /**
     * This method using dfs, if the path exist, return true. If not, return false
     * @param src the source territory
     * @param dst the destination territory
     * @param Neighbors the neighbors of the current territory
     * @param visited whether the territory has been visited
     * @return
     */
    public boolean isValidPath(Territory src, Territory dst,
                               ArrayList<Territory> Neighbors, HashMap<Territory, Boolean> visited, Player currPlayer) {
        if (src.equals(dst) && checkIsSelfTerritory(dst, currPlayer)) {
            return true;
        }

        visited.put(src, true);
        Neighbors = src.getNeighbors();
        for(int i = 0; i < Neighbors.size(); i++){
            if (!visited.get(Neighbors.get(i)) && checkIsSelfTerritory(Neighbors.get(i), currPlayer) &&
                    isValidPath(Neighbors.get(i), dst, Neighbors, visited, currPlayer)) {
                return true;
            }
        }
        return false;
    }



    public boolean checkIsSelfTerritory(Territory territory, Player p){
        for(int i = 0; i < p.getOwnedTerritories().size(); i++){
            if(territory.equals(p.getOwnedTerritories().get(i))){
                return true;
            }
        }
        return false;
    }
    public void initVisited(Player player, HashMap<Territory, Boolean> visited){
        for(int i = 0; i < player.getOwnedTerritories().size(); i++){
            visited.put(player.getOwnedTerritories().get(i), false);
        }

    }

}
