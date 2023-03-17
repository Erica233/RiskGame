package edu.duke.ece651.team3.shared;

public class AttackRuleChecker {
    /**
     * Find the expected owner whether it has the territory
     * @param place territory name
     * @param currPlayer territory's expected owner
     * @return if owned by expected owner return true, else return false
     */
    public boolean checkTerrOwner(String place, Player currPlayer) {
        int length = currPlayer.ownedTerritories.size();
        for (int i = 0; i < length; i++) {
            if (!currPlayer.ownedTerritories.get(i).getName().equals(place) && i == length - 1) {
                return false;
            } else if (currPlayer.ownedTerritories.get(i).getName().equals(place)) {
                break;
            }
        }
        return true;
    }

    /**
     * Check whether the current player and enemy have the territory from attack's information
     * @param myAttack attack information
     * @param currPlayer current player
     * @param enemy enemy player
     * @return if valid return true, invalid return false
     */
    public boolean checkSrcDst(Attack myAttack, Player currPlayer, Player enemy){
        return checkTerrOwner(myAttack.src, currPlayer) && checkTerrOwner(myAttack.dst, enemy);
    }

    /**
     * Check whether the attack path is valid
     * @param myAttack attack information
     * @return if valid return true, invalid return false
     */
    //TODO: if t == null needed? if already finish the checkSrcDst, it is needed
    public boolean checkPath(Attack myAttack, Player currPlayer){
        Territory t = findTerritory(myAttack, currPlayer);
        return t.isAValidNeighbor();
    }

    /**t
     * Find the territory owned by current player
     * @param myAttack attack information
     * @param currPlayer current player
     * @return territory owned by current player
     */
    public Territory findTerritory(Attack myAttack, Player currPlayer){
        int length = currPlayer.ownedTerritories.size();
        Territory t = null;
        for (int i = 0; i < length; i++) {
            if (currPlayer.ownedTerritories.get(i).getName().equals(myAttack.src)) {
                t = currPlayer.ownedTerritories.get(i);
            }
        }
        return t;
    }

    /**
     * Check whether the attack's num of units is valid, if valid return true if invalid return false
     * @param myAttack attack information
     * @param currPlayer current player
     * @return if valid return true, invalid return false
     */
    public boolean checkNumUnits(Attack myAttack, Player currPlayer){
        Territory t = findTerritory(myAttack, currPlayer);
        if(myAttack.numUnits > t.getNumUnits()){
            return false;
        }
        return true;
    }
}
