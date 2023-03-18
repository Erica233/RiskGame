package edu.duke.ece651.team3.shared;

public abstract class MoveRuleChecker extends RuleChecker{
    private final Action action;
    public MoveRuleChecker(Action _action, Action action){
        super(_action);
        this.action = action;
    }

    /**
     * This method checks whether the source territory is correct
     * @param src the source territory
     * @param dst the destination territory
     * @param player the current player
     * @return
     */
    public boolean checkSrcDst(Territory src, Territory dst, Player player){
        if(!player.isValidToOwn(src)|| !player.isValidToOwn(dst)){
            return false;
        }
        return true;
    }


    /**
     * This method checks whether the action number Units is correct
     * @param actionUnits
     * @param currPlayer
     * @return
     */
    public boolean checkNumUnits(int actionUnits, Player currPlayer){
//        int playerUnits = currPlayer.
        return true;
    }


    /**
     * This method checks whether the path is correct
     * @param src
     * @param dst
     * @return
     */
    public boolean checkPath(Territory src, Territory dst){
        return true;
    }
}
