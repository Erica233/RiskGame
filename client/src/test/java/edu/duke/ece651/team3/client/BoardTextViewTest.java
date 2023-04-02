package edu.duke.ece651.team3.client;

import edu.duke.ece651.team3.shared.Board;
import edu.duke.ece651.team3.shared.RiskGameBoard;
import edu.duke.ece651.team3.shared.Territory;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BoardTextViewTest {
    @Test
    public void test_toDisplay() throws Exception {
        Board m1 = new RiskGameBoard();
        BoardTextView v1 = new BoardTextView(m1);
        Board m2 = new RiskGameBoard();
        BoardTextView v2 = new BoardTextView(m2);
        assertEquals(v1.displayBoard(), v2.displayBoard());
        m2.initE2Map();
        assertNotEquals(v1.displayBoard(), v2.displayBoard());
    }

    @Test
    public void test_displayBoard() throws Exception {
        Board m1 = new RiskGameBoard();
        BoardTextView v1 = new BoardTextView(m1);

        String expected = "No players in the Board!\n";
        assertEquals(expected, v1.displayBoard());

        m1.initE2Map();
        String expected2 = "orange player:\n" +
                "---------------\n" +
                "5 units in a (next to: c（2), j（3), b（1))\n" +
                "5 units in g (next to: f（2), h（1), l（2), i（3))\n" +
                "5 units in h (next to: l（1), g（1), j（2))\n" +
                "5 units in i (next to: f（3), g（3), k（2))\n" +
                "5 units in j (next to: h（2), l（3), c（1), a（3))\n" +
                "5 units in l (next to: f（1), h（1), c（2), e（3), g（2), j（3))\n" +
                "\n" +
                "blue player:\n" +
                "---------------\n" +
                "5 units in b (next to: c（2), d（1), a（1))\n" +
                "5 units in c (next to: l（2), d（2), e（3), j（1), b（2), a（2))\n" +
                "5 units in d (next to: c（2), e（2), b（1))\n" +
                "5 units in e (next to: f（2), l（3), c（3), d（2))\n" +
                "5 units in f (next to: l（1), e（2), g（2), k（2), i（3))\n" +
                "5 units in k (next to: f（2), i（2))\n\n";
        System.out.println(v1.displayBoard());
//        assertEquals(expected2, v1.displayBoard());
    }
}
