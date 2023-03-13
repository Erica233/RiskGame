package edu.duke.ece651.team3.client;

import edu.duke.ece651.team3.shared.Board;
import edu.duke.ece651.team3.shared.RiskGameBoard;
import edu.duke.ece651.team3.shared.Territory;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BoardTextViewTest {
    @Test
    public void test_displayBoard() {
        Territory t1 = new Territory("Narnia", 3);
        RiskGameBoard m1 = new RiskGameBoard();
        m1.tryAddTerritory(t1);
        BoardTextView v1 = new BoardTextView(m1);
        String expected = "3 units in Narnia\n";
        assertEquals(expected, v1.displayBoard());
    }
}
