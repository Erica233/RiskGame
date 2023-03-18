package edu.duke.ece651.team3.shared;
import com.sun.org.apache.xpath.internal.operations.Bool;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static java.lang.System.out;

public class MoveRuleChecker extends RuleChecker{
    private final Action action;
    Territory src;
    Territory dst;
    public MoveRuleChecker(Action _action){
        super(_action);
        this.action = _action;
        this.src = this.action.getSrc();
        this.dst = this.action.getDst();
    }

    /**
     * This method checks whether the source territory is correct
     * @param src the source territory
     * @param player the current player
     * @return
     */
    public boolean checkSrc(Territory src, Player player){
        if(!player.checkTerritoryByName(src)){
            out.println("The src is invalid!");
            return false;
        }
        return true;
    }
    /**
     * This method checks whether the source territory is correct
     * @param dst the destination territory
     * @param player the current player
     * @return
     */
    public boolean checkDst(Territory dst, Player player){
        if(!player.checkTerritoryByName(dst)){
            out.println("The dst is invalid!");
            return false;
        }
        return true;
    }



    /**
     * This method checks whether the action number Units is correct
     * @param actionUnits
     * @return
     */
    public boolean checkNumUnits(int actionUnits){
        if(actionUnits < 0 || actionUnits > src.getNumUnits()){
            return false;
        }
        return true;
    }


    /**
     * This method checks whether the path is correct
     * It uses DFS to check whether the territory belongs to the self player
     * @param src
     * @param dst
     * @return
     */
    public boolean checkPath(Territory src, Territory dst){
        HashMap<Integer, Territory> allNeighbors = new HashMap<>();
        ArrayList<Territory> Neighbors = src.getNeighbors();
        for(int i = 0; i < Neighbors.size(); i++){
            allNeighbors.put(i, Neighbors.get(i));
        }
        HashMap<Territory, Boolean> visited = new HashMap<>();
        if(isValidPath(src, dst, allNeighbors, visited)){
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
                               HashMap<Integer, Territory> Neighbors, HashMap<Territory, Boolean> visited) {
        if (src == dst) {
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
