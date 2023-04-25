package edu.duke.ece651.team3.shared;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * A class of rule checker for only checking moves actions
 *
 */
public class EventRuleChecker extends RuleChecker{
    private final Board riskGameBoard;
    private final Action action;
    private String srcName;
    private String dstName;


    /**
     * Check whether the current player and enemy have the territory from attack's information
     * @param _action attack information
     * @param _riskGameBoard current board
     */
    public EventRuleChecker(Action _action, Board _riskGameBoard){
        super(_action);
        this.action = _action;
        this.srcName = this.action.getSrcName();
        this.dstName = this.action.getDstName();
        this.riskGameBoard = _riskGameBoard;
    }


    /**
     * Check whether the current player's src and dst territory are both in its owned territory
     * @param myEvent move information
     * @param currPlayer current player
     * @return if valid return true, invalid return false
     */
    @Override
    public boolean checkSrcDst(Action myEvent, Player currPlayer){
        return currPlayer.checkTerrOwner(myEvent.getSrcName()) && currPlayer.checkTerrOwner(myEvent.getDstName());
    }


    /**
     * Check whether the move's num of units is valid for each level of unit
     * if valid return true if invalid return false
     * @param myEvent move information
     * @param currPlayer current player
     * @return if valid return true, invalid return false
     */
    public boolean checkNumUnits(Action myEvent, Player currPlayer){
        return true;
    }

    /**
     * This method checks whether the path is correct
     * It uses DFS to check whether the territory belongs to the self player
     * @param myEvent move information
     * @param currPlayer current player
     * @return true if valid false if invalid
     */
    public boolean checkPath(Action myEvent, RiskGameBoard r, Player currPlayer) {
        return true;
    }

    /**
     * This method checks whether there is enough resource for the move action
     * @param myAction the current action
     * @param riskGameBoard the whole board
     * @param currPlayer the current player
     * @return boolean true if it is enough, false if it is nor.
     */
    public boolean checkResources(Action myAction, RiskGameBoard riskGameBoard, Player currPlayer){
        Territory src = currPlayer.findOwnedTerritoryByName(myAction.getSrcName());

        int currFood = src.getFood();
        int currTech = src.getTech();

        if(currTech - 2 < 0){
            System.out.println("Invalid event! The technology resource is not enough!");
            return false;
        }
        if(currFood - 2 < 0){
            System.out.println("Invalid event! The food resource is not enough!");
            return false;
        }
        return true;
    }
}
