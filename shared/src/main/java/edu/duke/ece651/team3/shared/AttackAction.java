package edu.duke.ece651.team3.shared;

import java.util.HashMap;

public class AttackAction extends Action{
    public AttackAction(String _actionType, String _srcName, String _dstName, HashMap<Integer, Integer> _actionUnits) {
        super(_actionType, _srcName, _dstName, _actionUnits);
    }
}
