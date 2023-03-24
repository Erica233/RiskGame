package edu.duke.ece651.team3.shared;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

/**
 * A Risk Game Board
 */
public class RiskGameBoard implements Board, Serializable {
    private final ArrayList<Territory> allTerritories; //only for test
    private final ArrayList<Player> allPlayers;

    /**
     * Constructs a RiskGameBoard
     *
     */
    public RiskGameBoard() throws Exception {
        this.allTerritories = new ArrayList<>();
        this.allPlayers = new ArrayList<>();
        //initMap();
    }

    /**
     * Update combat results for each territory and transfer ownership if the territory is occupied by enemny
     *
     */
    public void updateCombatResult() {
//        HashMap<Integer, ArrayList<Territory>> ownedNames = new HashMap<>();
//        ownedNames.put(0, new ArrayList<>());
//        ownedNames.put(1, new ArrayList<>());
//        for (int id = 0; id < 2; id++) {
//            for (Territory territory: allPlayers.get(id).getOwnedTerritories()) {
//                ownedNames.get(id).add(territory);
//            }
//        }
        HashMap<Territory, Integer> terrsToTransfer = new HashMap<>();
        for (int id = 0; id < 2; id++) {
            Player player = allPlayers.get(id);
            Player enemy = allPlayers.get(1 - id);
            for (Territory territory: player.getOwnedTerritories()) {
                if (territory.getWinnerId() == -1) {
                    continue;
                }
                if (territory.getWinnerId() != player.getPlayerId()) {
                    terrsToTransfer.put(territory, id);
                    //transfer ownership
//                    enemy.tryOwnTerritory(territory);
//                    player.loseTerritory(territory);
                }
                territory.updateCombatResult(player.getPlayerId());
            }
        }
        for (Territory territory: terrsToTransfer.keySet()) {
            allPlayers.get(1 - terrsToTransfer.get(territory)).tryOwnTerritory(territory);
            allPlayers.get(terrsToTransfer.get(territory)).loseTerritory(territory);
        }
    }

    /**
     * This method initialize the map
     * @return A string that contains all information of a map
     * @throws Exception
     */
    public String initMap() throws Exception {
        Territory t1 = new Territory("a", 1, 5);
        Territory t2 = new Territory("b", 1, 5);
        Territory t3 = new Territory("c", 1, 5);
        Territory t4 = new Territory("d", 1, 5);
        Territory t5 = new Territory("e", 1, 5);
        Territory t6 = new Territory("f", 1, 5);
        Territory t7 = new Territory("g", 1, 5);
        Territory t8 = new Territory("h", 1, 5);
        Territory t9 = new Territory("i", 1, 5);
        Territory t10 = new Territory("j", 1, 5);
        Territory t11 = new Territory("k", 1, 5);
        Territory t12 = new Territory("l", 1, 5);
        t1.addNeighbors(t2, t3);
        t2.addNeighbors(t1, t3, t4);
        t3.addNeighbors(t1, t2, t4, t5, t12);
        t4.addNeighbors(t2, t3, t5);
        t5.addNeighbors(t3, t4, t6, t12);
        t6.addNeighbors(t5, t11, t12);
        t7.addNeighbors(t8, t9, t12);
        t8.addNeighbors(t7, t9, t12);
        t9.addNeighbors(t7, t8, t10, t12);
        t10.addNeighbors(t9, t11);
        t11.addNeighbors(t6, t10);
        t12.addNeighbors(t3, t5, t6, t7, t8, t9);

        ArrayList<Territory> territoriesOwnedByPlayer1 = new ArrayList<>();
        Collections.addAll(territoriesOwnedByPlayer1, t1, t3, t7, t8, t9, t12);
        ArrayList<Territory> territoriesOwnedByPlayer2 = new ArrayList<>();
        Collections.addAll(territoriesOwnedByPlayer2, t2, t4, t5, t6, t10, t11);

        String output = "";
        Player player1 = new Player(0, "red", 30, territoriesOwnedByPlayer1);
        Player player2 = new Player(1, "blue", 30, territoriesOwnedByPlayer2);
        allPlayers.add(player1);
        allPlayers.add(player2);
        output = output + player1.displayPlayer() + "\n" + player2.displayPlayer() + "\n";
        System.out.println("initialize map successfully!");
        return output;
    }

    public void addAUnitEachTurn() {
        for (Player player: allPlayers) {
            player.addAUnitForEachTerr();
        }
    }

//    public void transferOwnedTerritory(int originalOwnerId, ) {
//
//    }

    public void executeMove(Action move, int playerId) {
        allPlayers.get(playerId).executeMove(move);
    }

    public void executeAttack(Action attack, int playerId) {
        allPlayers.get(playerId).executeAttack(attack);
    }

    /**
     * This method overrides the equals function to check whether two riskGameBoards are equal
     * @param other
     * @return true if both are equal, false if they are not
     */
    @Override
    public boolean equals(Object other) {
        if (other.getClass().equals(getClass())) {
            RiskGameBoard riscBoard = (RiskGameBoard) other;
            return allPlayers.equals(riscBoard.allPlayers);
        }
        return false;
    }

    /**
     * This method tries to add a territory to the whole territory list
     * @param territoryToAdd the Territory to add to the Board
     * @return true if it successfully added, false otherwise
     */
    public boolean tryAddTerritory(Territory territoryToAdd) {
        if (!territoryToAdd.isValidToAdd(allTerritories, territoryToAdd)) {
            return false;
        }
        allTerritories.add(territoryToAdd);
        return true;
    }

    /**
     * This method displays the board
     * @return String contains the info of the board
     */
    public String displayBoard() {
        StringBuilder output = new StringBuilder();
        if (allPlayers.size() == 0) {
            return output.append("No players in the Board!\n").toString();
        }
        for (Player aPlayer: allPlayers) {
            output.append(aPlayer.displayPlayer()).append("\n");
        }
        return output.toString();
    }

    /**
     * This method adds a player into the board
     * @param p
     */
    public void addPlayer(Player p){
        allPlayers.add(p);
    }

    /**
     * This method gets all players
     * @return the ArrayList<Players>
     */
    public ArrayList<Player> getAllPlayers() {
        return allPlayers;
    }

    /**
     * This method gets all territories
     * @return the ArrayList<Territory>
     */
    public ArrayList<Territory> getAllTerritories() {
        return allTerritories;
    }

}
