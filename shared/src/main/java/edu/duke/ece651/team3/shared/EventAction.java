package edu.duke.ece651.team3.shared;

import java.util.ArrayList;

public class EventAction extends Action{
    public EventAction(String _srcName, String _dstName, ArrayList<Unit> _unitsToChange) {
        super("E", _srcName, _dstName, _unitsToChange);
    }
}
