//package edu.duke.ece651.team3.client;
//
//import edu.duke.ece651.team3.shared.Board;
//import edu.duke.ece651.team3.shared.RiskGameBoard;
//import edu.duke.ece651.team3.shared.Territory;
//import org.junit.jupiter.api.Test;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//class BoardTextViewTest {
//    @Test
//    public void test_toDisplay() throws Exception {
//        Board m1 = new RiskGameBoard();
//        BoardTextView v1 = new BoardTextView(m1);
//        Board m2 = new RiskGameBoard();
//        BoardTextView v2 = new BoardTextView(m2);
//        assertEquals(v1.displayBoard(), v2.displayBoard());
//        m2.initMap();
//        assertNotEquals(v1.displayBoard(), v2.displayBoard());
//    }
//
//    @Test
//    public void test_displayBoard() throws Exception {
//        Board m1 = new RiskGameBoard();
//        BoardTextView v1 = new BoardTextView(m1);
//
//        String expected = "No players in the Board!\n";
//        assertEquals(expected, v1.displayBoard());
//
//        m1.initMap();
//        String expected2 = "red player:\n" +
//                "---------------\n" +
//                "5 units in a (next to: b, c)\n" +
//                "5 units in c (next to: a, b, d, e, l)\n" +
//                "5 units in g (next to: h, i, l)\n" +
//                "5 units in h (next to: g, i, l)\n" +
//                "5 units in i (next to: g, h, j, l)\n" +
//                "5 units in l (next to: c, e, f, g, h, i)\n" +
//                "\n" +
//                "blue player:\n" +
//                "---------------\n" +
//                "5 units in b (next to: a, c, d)\n" +
//                "5 units in d (next to: b, c, e)\n" +
//                "5 units in e (next to: c, d, f, l)\n" +
//                "5 units in f (next to: e, k, l)\n" +
//                "5 units in j (next to: i, k)\n" +
//                "5 units in k (next to: f, j)\n" +
//                "\n";
//        assertEquals(expected2, v1.displayBoard());
//    }
//}
