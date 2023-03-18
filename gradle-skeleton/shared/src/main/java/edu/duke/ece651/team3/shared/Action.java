package edu.duke.ece651.team3.shared;

public class Action {
    private String actionType;
    private Territory src; //The FROM territory
    private Territory dst; //The TO territory
    private int actionUnits; //The units to move

    public Action(String _actionType, Territory _src, Territory _dst, int _actionUnits){
        this.actionType = _actionType;
        this.src = _src;
        this.dst = _dst;
        this.actionUnits = _actionUnits;
    }



}
