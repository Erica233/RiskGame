package edu.duke.ece651.team3.shared;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;

/**
 * A Risk Game Board
 */
public class RiskGameBoard implements Board, Serializable {
    //private final ArrayList<Territory> allTerritories;
    private final ArrayList<Player> allPlayers;

    /**
     * Constructs a RiskGameBoard
     *
     */
    public RiskGameBoard() throws Exception {
        //this.allTerritories = new ArrayList<>();
        this.allPlayers = new ArrayList<>();
        initMap();
    }

    public String initMap() throws Exception {
        Territory t1 = new Territory("a", 5);
        Territory t2 = new Territory("b", 5);
        Territory t3 = new Territory("c", 5);
        Territory t4 = new Territory("d", 5);
        Territory t5 = new Territory("e", 5);
        Territory t6 = new Territory("f", 5);
        Territory t7 = new Territory("g", 5);
        Territory t8 = new Territory("h", 5);
        Territory t9 = new Territory("i", 5);
        Territory t10 = new Territory("j", 5);
        Territory t11 = new Territory("k", 5);
        Territory t12 = new Territory("l", 5);
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
        return output;
    }

    @Override
    public boolean equals(Object other) {
        if (other.getClass().equals(getClass())) {
            RiskGameBoard riscBoard = (RiskGameBoard) other;
            return allPlayers.equals(riscBoard.allPlayers);
        }
        return false;
    }

//    public boolean tryAddTerritory(Territory territoryToAdd) {
//        if (!territoryToAdd.isValidToAdd()) {
//            return false;
//        }
//        allTerritories.add(territoryToAdd);
//        return true;
//    }

    public String displayBoard() {
        StringBuilder output = new StringBuilder();
        for (Player aPlayer: allPlayers) {
            output.append(aPlayer.displayPlayer()).append("\n");
        }
        return output.toString();
    }
    public ArrayList<Player> getAllPlayers() {
        return allPlayers;
    }

//    public ArrayList<Territory> getAllTerritories() {
//        return allTerritories;
//    }

}
