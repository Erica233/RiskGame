package edu.duke.ece651.team3.shared;

import java.util.ArrayList;

/**
 * A Board
 */
public interface Board {

    String initMap() throws Exception;

    /**
     * Displays the information on the Risk Game Board
     *
     * @return a String about the information on the Risk Game Board
     */
    String displayBoard();

    /**
     * Add a Territory to the Board,
     * if the Territory is valid, add and returns true,
     * otherwise, return false
     *
     * @param territoryToAdd the Territory to add to the Board
     * @return true if the Territory is valid to add, otherwise false
     */
    //boolean tryAddTerritory(Territory territoryToAdd);

    /** getters and setters **/
    //ArrayList<Territory> getAllTerritories();

    ArrayList<Player> getAllPlayers();

}
