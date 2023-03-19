package edu.duke.ece651.team3.shared;

import java.util.ArrayList;

public class AttackRuleChecker extends RuleChecker{
    private final RiskGameBoard riskGameBoard;
    private final Action action;
    Territory src;
    Territory dst;
    public AttackRuleChecker(Action _action, RiskGameBoard _riskGameBoard){
        super(_action);
        this.action = _action;
        this.src = this.action.getSrc();
        this.dst = this.action.getDst();
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
        int enemyId = -1;
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
        return currPlayer.checkTerrOwner(myAttack.getSrc().getTerritoryName()) && enemy.checkTerrOwner(myAttack.getSrc().getTerritoryName());
    }

    /**
     * Check whether the attack path is valid
     * @param myAttack attack information
     * @return if valid return true, invalid return false
     */
    //TODO: if t == null needed? if already finish the checkSrcDst, it is needed
    public boolean checkPath(Action myAttack, Player currPlayer){
        Territory t = findTerritory(myAttack, currPlayer);
        return t.isAValidNeighbor();
    }

    /**t
     * Find the territory owned by current player and return the territory
     * @param myAttack attack information
     * @param currPlayer current player
     * @return territory owned by current player
     */
    public Territory findTerritory(Action myAttack, Player currPlayer){
        int length = currPlayer.getOwnedTerritories().size();
        Territory t = null;
        for (int i = 0; i < length; i++) {
            if (currPlayer.getOwnedTerritories().get(i).getTerritoryName().equals(myAttack.getSrc())) {
                t = currPlayer.getOwnedTerritories().get(i);
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
        if(myAttack.getActionUnits() > t.getNumUnits() || myAttack.getActionUnits()< 0){
            return false;
        }
        return true;
    }
}
