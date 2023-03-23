package edu.duke.ece651.team3.shared;

import java.util.HashMap;

public class AttackAction extends Action{
    /**
     * This constructor takes 4 elements
     * @param _actionType the string indicates the action type, i.e : M, A, D
     * @param _srcName the string indicates the src territory name
     * @param _dstName the string indicates the dst territory name
     * @param _actionUnits the action units (i.e the move or attack units)
     */
    public AttackAction(String _actionType, String _srcName, String _dstName, HashMap<Integer, Integer> _actionUnits) {
        super(_actionType, _srcName, _dstName, _actionUnits);
    }
}
