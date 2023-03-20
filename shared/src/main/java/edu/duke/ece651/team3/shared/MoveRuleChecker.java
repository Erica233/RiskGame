package edu.duke.ece651.team3.shared;
import com.sun.org.apache.xpath.internal.operations.Bool;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static java.lang.System.out;

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
        return currPlayer.checkTerrOwner(myAttack.getSrc().getTerritoryName()) && currPlayer.checkTerrOwner(myAttack.getSrc().getTerritoryName());
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
        if(myMove.getActionUnits() > t.getNumUnits() || myMove.getActionUnits() < 0){
            return false;
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
    public boolean checkPath(Action myMove, Player currPlayer){
        Territory src = myMove.getSrc();
        Territory dst = myMove.getDst();
//        if(src.equals(dst)){
//            return false;
//        }

        ArrayList<Territory> Neighbors = src.getNeighbors();

        HashMap<Territory, Boolean> visited = new HashMap<>();
        if(isValidPath(src, dst, Neighbors, visited)){
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
                               ArrayList<Territory> Neighbors, HashMap<Territory, Boolean> visited) {
        if (src.equals(dst)) {
            return true;
        }

        visited.put(src, true);

        for(int i = 0; i < Neighbors.size(); i++){
            if (!visited.get(Neighbors.get(i)) && isValidPath(Neighbors.get(i), dst, Neighbors, visited)) {
                return true;
            }
        }
        return false;
    }
}
