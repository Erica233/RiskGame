package edu.duke.ece651.team3.client;

import edu.duke.ece651.team3.shared.Board;

/**
 * This class handles textual display of a Board
 * (i.e., converting it to a string to show to the user).
 */
public class BoardTextView {
    /**
     * The Board to display
     */
    private final Board toDisplay;

    /**
     * Constructs a BoardTextView, given the Board it will display.
     * @param _toDisplay is the Board to display
     */
    public BoardTextView(Board _toDisplay) {
        this.toDisplay = _toDisplay;
    }

    /**
     * Displays the information about Board
     *
     * @return a String about information of Board
     */
    public String displayBoard() {
        return toDisplay.displayBoard();
    }
}
