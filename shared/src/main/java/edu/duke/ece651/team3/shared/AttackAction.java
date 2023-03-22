package edu.duke.ece651.team3.shared;

import java.util.HashMap;

public class AttackAction extends Action{
    public AttackAction(String _actionType, Territory _src, Territory _dst, HashMap<Integer, Integer> _actionUnits) {
        super(_actionType, _src, _dst, _actionUnits);
    }
}
