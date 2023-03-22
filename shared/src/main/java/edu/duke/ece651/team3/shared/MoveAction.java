package edu.duke.ece651.team3.shared;

import java.util.HashMap;

public class MoveAction extends Action{

    public MoveAction(String _actionType, Territory _src, Territory _dst, HashMap<Class<?>, Integer> _actionUnits) {
        super(_actionType, _src, _dst, _actionUnits);
    }


}
