package edu.duke.ece651.team3.shared;

import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * A class of rule checker for only checking moves actions
 *
 */
public class UpgradeRuleChecker extends RuleChecker{
    private final Board riskGameBoard;
    private final Action action;
    private String srcName;
    private String dstName;

    /**
     * Check whether the current player and enemy have the territory from attack's information
     * @param _action attack information
     * @param _riskGameBoard current player
     * @return if valid return true, invalid return false
     */
    public UpgradeRuleChecker(Action _action, Board _riskGameBoard){
        super(_action);
        this.action = _action;
        this.srcName = this.action.getSrcName();
        this.dstName = this.action.getDstName();
        this.riskGameBoard = _riskGameBoard;
    }

    /**
     * Check whether the current player's src and dst territory are its owned territory
     * @param myUpgrade move information
     * @param currPlayer current player
     * @return if valid return true, invalid return false
     */
    @Override
    public boolean checkSrcDst(Action myUpgrade, Player currPlayer){
        return currPlayer.checkTerrOwner(myUpgrade.getSrcName()) && currPlayer.checkTerrOwner(myUpgrade.getDstName());
    }

    /**t
     * Find the territory owned by current player and return the territory
     * @param myAttack move information
     * @param currPlayer current player
     * @return territory owned by current player
     */
    public Territory findTerritory(Action myAttack, Player currPlayer){
        int length = currPlayer.getOwnedTerritories().size();
        Territory t = null;
        for (int i = 0; i < length; i++) {
            if (currPlayer.getOwnedTerritories().get(i).getTerritoryName().equals(myAttack.getSrcName())) {
                t = currPlayer.getOwnedTerritories().get(i);
            }
        }
        return t;
    }

    /**
     * Check whether the upgrade's num of units is valid for each level of unit
     * if valid return true if invalid return false
     * @param myUpgrade move information
     * @param currPlayer current player
     * @return if valid return true, invalid return false
     */
    public boolean checkNumUnits(Action myUpgrade, Player currPlayer){
        Territory t = findTerritory(myUpgrade, currPlayer);
        for(int i = 0; i < t.getUnits().size() - 1; i++){
            int numUnitsChange = myUpgrade.getUnitsToChange().get(i).getNumUnits();
            if(numUnitsChange > t.getUnits().get(i).getNumUnits() || numUnitsChange < 0){
                System.out.println("Invalid numberUnits: " + numUnitsChange + " current territory's unit: "
                        + t.getUnits().get(i).getNumUnits());
                return false;
            }
        }
        int lv6Num = myUpgrade.getUnitsToChange().get(t.getUnits().size() - 1).getNumUnits();
        if (lv6Num != 0) {
            return false;
        }
        return true;
    }

    /**
     * This method checks whether the Unit is correct for upgrade.
     * ONLY one should have number, other 6 should remain 0
     * Also, it is invalid to upgrade the highest level unit
     * if valid return true if invalid return false
     * @param myUpgrade move information
     * @param r the riskGameBoard
     * @param currPlayer current player
     * @return true if valid false if invalid
     */
    public boolean checkPath(Action myUpgrade, RiskGameBoard r, Player currPlayer) {
        Territory t = findTerritory(myUpgrade, currPlayer);
        for(int i = 0; i < t.getUnits().size(); i++){
            Unit currUnit = myUpgrade.getUnitsToChange().get(i);
            //If the upgrade unit has already been the highest level
            if(currUnit.getUnitName().equals("SergeantMajor") && currUnit.getNumUnits() != 0){
                System.out.println("Invalid numberUnits! current Unit is already the highest level");
                return false;
            }
        }
        return true;
    }

    /**
     * This method checks whether there is enough technology resource for the upgrade action
     *
     * @param myAction the current action
     * @param riskGameBoard the whole board
     * @param currPlayer the current player
     * @return boolean true if it is enough, false if it is nor.
     */
    public boolean checkResources(Action myAction, RiskGameBoard riskGameBoard, Player currPlayer){
        Territory src = currPlayer.findOwnedTerritoryByName(myAction.getSrcName());
        int totalResourceCost = 0;

        for(int i = 0; i < myAction.getUnitsToChange().size() - 1; i++){
            Unit unitFrom = myAction.getUnitsToChange().get(i);
            Unit unitTo = myAction.getUnitsToChange().get(i + 1);

            int currUnitCost = unitTo.getUpgradeCost() - unitFrom.getUpgradeCost();
//            System.out.println("The current upgrade cost is: " + currUnitCost);
            totalResourceCost += currUnitCost * unitFrom.getNumUnits();
        }
        System.out.println("The total upgrade cost is: " + totalResourceCost);
        if(totalResourceCost > src.getTech()){
            System.out.println("Invalid upgrade! The technology is not enough!");
            return false;
        }
        return true;
    }
}
