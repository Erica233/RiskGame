package edu.duke.ece651.team3.shared;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * attack action class
 */
public class UpgradeAction extends Action {
    /**
     * This constructor takes 4 elements
     * @param _srcName the string indicates the src territory name
     * @param _dstName the string indicates the dst territory name
     * @param _unitsToChange the Class and the number of units to change
     */
    public UpgradeAction(String _srcName, String _dstName,
                      ArrayList<Unit> _unitsToChange) {
        super("U", _srcName, _dstName, _unitsToChange);
    }
}
