package edu.duke.ece651.team3.shared;

public abstract class RuleChecker {

    private final Action action;
    public RuleChecker(Action _action){
        this.action = _action;
    }

    /**
     * This method checks whether the source territory is correct
     * @param src
     * @return
     */
    public abstract boolean checkSrc(Territory src, Player currentPlayer);

    /**
     * This method checks whether the destination territory is correct
     * @param dst
     * @return
     */
    public abstract boolean checkDst(Territory dst, Player currentPlayer);

    /**
     * This method checks whether the action number Units is correct
     * @param actionUnits
     * @return
     */
    public abstract boolean checkNumUnits(int actionUnits);

    /**
     * This method checks whether the path is correct
     * @param src
     * @param dst
     * @return
     */
    public abstract boolean checkPath(Territory src, Territory dst);
}
