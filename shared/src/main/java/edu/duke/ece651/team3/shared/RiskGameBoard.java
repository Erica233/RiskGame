package edu.duke.ece651.team3.shared;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

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
        //initMap();
    }

    public String initMap() throws Exception {
        HashMap<Class<?>, Integer> units = new HashMap<>();
        Unit unit1 = new Soldier();
        units.put(unit1.getClass(), 5);
        Territory t1 = new Territory("a", (HashMap<Class<?>, Integer>) units.clone());
        Territory t2 = new Territory("b", (HashMap<Class<?>, Integer>) units.clone());
        Territory t3 = new Territory("c", (HashMap<Class<?>, Integer>) units.clone());
        Territory t4 = new Territory("d", (HashMap<Class<?>, Integer>) units.clone());
        Territory t5 = new Territory("e", (HashMap<Class<?>, Integer>) units.clone());
        Territory t6 = new Territory("f", (HashMap<Class<?>, Integer>) units.clone());
        Territory t7 = new Territory("g", (HashMap<Class<?>, Integer>) units.clone());
        Territory t8 = new Territory("h", (HashMap<Class<?>, Integer>) units.clone());
        Territory t9 = new Territory("i", (HashMap<Class<?>, Integer>) units.clone());
        Territory t10 = new Territory("j", (HashMap<Class<?>, Integer>) units.clone());
        Territory t11 = new Territory("k", (HashMap<Class<?>, Integer>) units.clone());
        Territory t12 = new Territory("l", (HashMap<Class<?>, Integer>) units.clone());
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
        if (allPlayers.size() == 0) {
            return output.append("No players in the Board!\n").toString();
        }
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
