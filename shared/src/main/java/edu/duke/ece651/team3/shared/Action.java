package edu.duke.ece651.team3.shared;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Locale;

public class Action implements Serializable {
    private String actionType;
    private String srcName; //The FROM territory
    private String dstName; //The TO territory
    private HashMap<Integer, Integer> actionUnits;

    public Action(String _actionType, String _srcName, String _dstName, HashMap<Integer, Integer> _actionUnits){
        this.actionType = _actionType;
        this.srcName = _srcName;
        this.dstName = _dstName;
        this.actionUnits = _actionUnits;
    }

    /**
     * This method checks whether the action type is the move type
     * @param action
     * @return true if it is Move type, false if it not
     */
    public boolean isMoveType(Action action){
        return action.getActionType().toUpperCase(Locale.ROOT).equals("M");
    }

    /**
     * This method checks whether the action type is the attack type
     * @param action
     * @return true if it is Attack type, false if it is not
     */
    public boolean isAttackType(Action action){
        return action.getActionType().toUpperCase(Locale.ROOT).equals("A");
    }

    /**
     * This method checks whether the action is Done
     * @param action
     * @return true if it is Done, false if it is not
     */
    public boolean isDone(Action action){
        return action.getActionType().toUpperCase(Locale.ROOT).equals("D");
    }

    /**
     * This method checks whether the action type is valid(Move, Attack, Done)
     * @param action
     * @return true if the action type is valid, false if it is not
     */

    public boolean isValidType(Action action){
        return isAttackType(action) || isMoveType(action) || isDone(action);
    }



    public String getActionType() {
        return actionType;
    }

    public String getSrcName() {
        return srcName;
    }

    public String getDstName() {
        return dstName;
    }

    public HashMap<Integer, Integer> getActionUnits() {
        return actionUnits;
    }

    //Since the actions are read from the user, it supports set methods
    public void setActionType(String actionType) {
        this.actionType = actionType;
    }

    public void setSrcName(String srcName) {
        this.srcName = srcName;
    }

    public void setDstName(String dstName) {
        this.dstName = dstName;
    }

    public void setActionUnits(HashMap<Integer, Integer> actionUnits) {
        this.actionUnits = actionUnits;
    }


    @Override
    public String toString() {
        String s = null;
        for(Integer c : actionUnits.keySet()){
            s += "("+c.toString()+" : "+actionUnits.get(c)+") ";
        }
        return "Action{" +
                "actionType='" + actionType + '\'' +
                ", src=" + srcName +
                ", dst=" + dstName +
                ", actionUnits=" + s +
                '}';
    }

}
