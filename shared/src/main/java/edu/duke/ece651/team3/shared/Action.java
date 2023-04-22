package edu.duke.ece651.team3.shared;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;

/**
 * action class to store move or attack action information
 */
public class Action implements Serializable {
    private String actionType;
    private String srcName;
    private String dstName;
    private ArrayList<Unit> unitsToChange;

    /**
     * This constructor takes 4 elements
     * @param _actionType the string indicates the action type, i.e : M, A, U, E, D
     * @param _srcName the string indicates the src territory name
     * @param _dstName the string indicates the dst territory name
     */
    public Action(String _actionType, String _srcName, String _dstName,
                  ArrayList<Unit> _unitsToChange){
        this.actionType = _actionType;
        this.srcName = _srcName;
        this.dstName = _dstName;
        this.unitsToChange = _unitsToChange;
    }

    /**
     * This method checks whether the action type is the move type
     * @return true if it is Move type, false if it not
     */
    public boolean isMoveType(){
        return actionType.toUpperCase(Locale.ROOT).equals("M");
    }

    /**
     * This method checks whether the action type is the attack type
     * @return true if it is Attack type, false if it is not
     */
    public boolean isAttackType(){
        return actionType.toUpperCase(Locale.ROOT).equals("A");
    }

    /**
     * This method checks whether the action type is the upgrade type
     * @return true if it is Move type, false if it not
     */
    public boolean isUpgradeType(){
        return actionType.toUpperCase(Locale.ROOT).equals("U");
    }

    /**
     * This method checks whether the action type is the random event type
     * @return true if it is Move type, false if it not
     */
    public boolean isEventType(){
        return actionType.toUpperCase(Locale.ROOT).equals("E");
    }


    /**
     * This method checks whether the action type is valid(Move, Attack)
     * @return true if the action type is valid, false if it is not
     */
    public boolean isValidType(){
        return isAttackType() || isMoveType();
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

    public ArrayList<Unit> getUnitsToChange() {
        return unitsToChange;
    }

    public void setActionType(String actionType) {
        this.actionType = actionType;
    }

    public void setSrcName(String srcName) {
        this.srcName = srcName;
    }

    public void setDstName(String dstName) {
        this.dstName = dstName;
    }

    public void setActionUnits(ArrayList<Unit> _unitsToChange) {
        this.unitsToChange = _unitsToChange;
    }

    @Override
    public String toString() {
        String s = "";
        for(Unit c : unitsToChange){
            s += "(lv." + c.getLevel() + " : " + c.getNumUnits() +  ") ";
        }
        return "Action {" +
                "Type=" + actionType +
                ", src=" + srcName +
                ", dst=" + dstName +
                ", units=" + s +
                '}';
    }

}
