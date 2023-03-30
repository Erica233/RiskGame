package edu.duke.ece651.team3.shared;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * attack action class
 */
public class AttackAction extends Action{
    /**
     * This constructor takes 4 elements
     * @param _actionType the string indicates the action type, i.e : M, A, D
     * @param _srcName the string indicates the src territory name
     * @param _dstName the string indicates the dst territory name
     * @param _unitsToChange the Class and the number of units to change
     */
    public AttackAction(String _srcName, String _dstName, ArrayList<Unit> _unitsToChange) {
        super("A", _srcName, _dstName, _unitsToChange);
    }
}
