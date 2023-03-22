package edu.duke.ece651.team3.shared;

import java.io.Serializable;
import java.util.HashMap;

public abstract class Action implements Serializable {
    private String actionType;
    private Territory src; //The FROM territory
    private Territory dst; //The TO territory
    private HashMap<Class<?>, Integer> actionUnits;

    public String getActionType() {
        return actionType;
    }

    public Territory getSrc() {
        return src;
    }

    public Territory getDst() {
        return dst;
    }

    public HashMap<Class<?>, Integer> getActionUnits() {
        return actionUnits;
    }


    public Action(String _actionType, Territory _src, Territory _dst, HashMap<Class<?>, Integer> _actionUnits){
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

    public void setActionUnits(HashMap<Class<?>, Integer> actionUnits) {
        this.actionUnits = actionUnits;
    }


    @Override
    public String toString() {
        String s = null;
        for(Class<?> c : actionUnits.keySet()){
            s += "("+c.toString()+" : "+actionUnits.get(c)+") ";
        }
        return "Action{" +
                "actionType='" + actionType + '\'' +
                ", src=" + src +
                ", dst=" + dst +
                ", actionUnits=" + s +
                '}';
    }

}
