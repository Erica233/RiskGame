package edu.duke.ece651.team3.shared;

public abstract class RuleChecker {

    private final Action action;
    public RuleChecker(Action _action){
        this.action = _action;
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
