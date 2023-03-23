package edu.duke.ece651.team3.shared;

public abstract class RuleChecker {

    private final Action action;
    public RuleChecker(Action _action){
        this.action = _action;
    }

    /**
     * This method checks whether the action is valid based on all three aspects
     * @param myAction the action
     * @param r the riskGameBoard
     * @param currPlayer the current player
     * @return true if it is valid, false if it is not
     * @throws Exception
     */
    public boolean checkValidAction(Action myAction, RiskGameBoard r, Player currPlayer) throws Exception {
        return checkSrcDst(myAction, currPlayer) && checkNumUnits(myAction, currPlayer) && checkPath(myAction, r, currPlayer);
    }

    /**
     * Check whether the current player and enemy have the territory from attack's information
     * @param myAction attack information
     * @param currPlayer current player
     * @return if valid return true, invalid return false
     */
    public abstract boolean checkSrcDst(Action myAction, Player currPlayer);

    /**
     * This method checks whether the action number Units is correct
     * @param myAction action information
     * @param currPlayer current player
     * @return if valid return true, invalid return false
     */
    public abstract boolean checkNumUnits(Action myAction, Player currPlayer);

    /**
     * This method checks whether the path is correct
     * @param myAction action information
     * @param currPlayer current player
     * @return if valid return true, invalid return false
     */
    public abstract boolean checkPath(Action myAction, RiskGameBoard r, Player currPlayer) throws Exception;
}
