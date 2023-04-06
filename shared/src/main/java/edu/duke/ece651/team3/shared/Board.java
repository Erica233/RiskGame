package edu.duke.ece651.team3.shared;

import java.util.ArrayList;

/**
 * A Board
 */
public interface Board {
    /**
     * This method initialize the map
     *
     * @return A String that contains all information of a map
     * @throws Exception
     */
    String initE2Map() throws Exception;

    /**
     * add one basic unit for each territory
     *
     */
    void addAfterEachTurn();

    /**
     * Displays the information on the Risk Game Board
     *
     * @return a String about the information on the Risk Game Board
     */
    String displayBoard();

    /** getters and setters **/
    ArrayList<Player> getAllPlayers();

}
