package edu.duke.ece651.team3.shared;
import java.util.ArrayList;

public class AttackRuleChecker extends RuleChecker{
    private final Board riskGameBoard;
    private final Action action;
    String srcName;
    String dstName;

    /**
     * This constructor takes 4 elements
     * @param _action the Action class containing all the information of an action
     * @param _riskGameBoard the board passed in
     */
    public AttackRuleChecker(Action _action, Board _riskGameBoard){
        super(_action);
        this.action = _action;
        this.srcName = this.action.getSrcName();
        this.dstName = this.action.getDstName();
        this.riskGameBoard = _riskGameBoard;
    }

    /**
     * Check whether the current player and enemy have the territory from attack's information
     * @param myAttack attack information
     * @param currPlayer current player
     * @return if valid return true, invalid return false
     */
    public boolean checkSrcDst(Action myAttack, Player currPlayer){
        ArrayList<Player> playerList = riskGameBoard.getAllPlayers();
        int playerId = currPlayer.getPlayerId();
        int enemyId;
        if(playerId == 0){
            enemyId = 1;
        }
        else{
            enemyId = 0;
        }
        Player enemy = null;
        for(int i = 0; i < playerList.size(); i++){
            if(playerList.get(i).getPlayerId() == enemyId){
                enemy = playerList.get(i);
            }
        }
        return currPlayer.checkTerrOwner(myAttack.getSrcName()) && enemy.checkTerrOwner(myAttack.getDstName());
    }

    /**
     * Check whether the attack path is valid
     * @param myAttack attack information
     * @return if valid return true, invalid return false
     */
    //TODO: if t == null needed? if already finish the checkSrcDst, it is needed
    public boolean checkPath(Action myAttack, RiskGameBoard r, Player currPlayer){
        Territory t = findTerritory(myAttack, currPlayer);
        return t.checkExistNeighbor(myAttack.getDstName());
    }

    /**
     * Find the territory owned by current player and return the territory
     * @param myAttack attack information
     * @param currPlayer current player
     * @return territory owned by current player
     */
    public Territory findTerritory(Action myAttack, Player currPlayer){
        int length = currPlayer.getOwnedTerritories().size();
        Territory t = null;
        for (int i = 0; i < length; i++) {
            if (currPlayer.getOwnedTerritories().get(i).getTerritoryName().equals(myAttack.getSrcName())) {
                t = currPlayer.getOwnedTerritories().get(i);
                break;
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
    public boolean checkNumUnits(Action myAttack, Player currPlayer){
        Territory t = findTerritory(myAttack, currPlayer);
        for(Integer c : myAttack.getActionUnits().keySet()){
            int numUnits = myAttack.getActionUnits().get(c);
            if(numUnits > t.getUnits().get(c) || numUnits < 0){
                return false;
            }
        }
        return true;
    }
}
