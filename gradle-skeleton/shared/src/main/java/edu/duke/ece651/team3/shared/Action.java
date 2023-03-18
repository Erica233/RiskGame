package edu.duke.ece651.team3.shared;

import java.io.Serializable;
import java.util.Objects;

public class Action implements Serializable {
    private String actionType;

    public String getActionType() {
        return actionType;
    }

    public Territory getSrc() {
        return src;
    }

    public Territory getDst() {
        return dst;
    }

    public int getActionUnits() {
        return actionUnits;
    }

    private Territory src; //The FROM territory
    private Territory dst; //The TO territory




    private int actionUnits; //The units to move

    public Action(String _actionType, Territory _src, Territory _dst, int _actionUnits){
        this.actionType = _actionType;
        this.src = _src;
        this.dst = _dst;
        this.actionUnits = _actionUnits;
    }

    //Since the actions are read from the user, it supports set methods
    public void setActionType(String actionType) {
        this.actionType = actionType;
    }

    public void setSrc(Territory src) {
        this.src = src;
    }

    public void setDst(Territory dst) {
        this.dst = dst;
    }

    public void setActionUnits(int actionUnits) {
        this.actionUnits = actionUnits;
    }


    @Override
    public String toString() {
        return "Action{" +
                "actionType='" + actionType + '\'' +
                ", src=" + src +
                ", dst=" + dst +
                ", actionUnits=" + actionUnits +
                '}';
    }

}
