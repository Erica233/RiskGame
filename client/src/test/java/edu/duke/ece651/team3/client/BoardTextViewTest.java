package edu.duke.ece651.team3.client;

import edu.duke.ece651.team3.shared.Board;
import edu.duke.ece651.team3.shared.RiskGameBoard;
import edu.duke.ece651.team3.shared.Territory;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BoardTextViewTest {
    @Test
    public void test_displayBoard() throws Exception {
        Territory t1 = new Territory("Narnia", 3);
        Territory t2 = new Territory("Mordor", 4);
        Board m1 = new RiskGameBoard();
        BoardTextView v1 = new BoardTextView(m1);
        //m1.tryAddTerritory(t1);
        //m1.tryAddTerritory(t2);
        String expected = "3 units in Narnia (no neighbors)\n4 units in Mordor (no neighbors)\n";
        assertEquals(expected, v1.displayBoard());

        t1.addANeighbor(t2);
        t2.addANeighbor(t1);
        String expected2 = "3 units in Narnia (next to: Mordor)\n4 units in Mordor (next to: Narnia)\n";
        assertEquals(expected2, v1.displayBoard());
    }
}
