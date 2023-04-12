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
                "Private: 5 Corporal: 0 Specialist: 0 Sergeant: 0 MasterSergeant: 0 FirstSergeant: 0 SergeantMajor: 0 \n" +
                "5 units in a (next to: b(1), j(3), c(2)) food=10, tech=10\n" +
                "Private: 5 Corporal: 0 Specialist: 0 Sergeant: 0 MasterSergeant: 0 FirstSergeant: 0 SergeantMajor: 0 \n" +
                "5 units in g (next to: l(2), h(1), f(2), i(3)) food=10, tech=10\n" +
                "Private: 5 Corporal: 0 Specialist: 0 Sergeant: 0 MasterSergeant: 0 FirstSergeant: 0 SergeantMajor: 0 \n" +
                "5 units in h (next to: g(1), l(1), j(2)) food=10, tech=10\n" +
                "Private: 5 Corporal: 0 Specialist: 0 Sergeant: 0 MasterSergeant: 0 FirstSergeant: 0 SergeantMajor: 0 \n" +
                "5 units in i (next to: g(3), k(2), f(3)) food=10, tech=10\n" +
                "Private: 5 Corporal: 0 Specialist: 0 Sergeant: 0 MasterSergeant: 0 FirstSergeant: 0 SergeantMajor: 0 \n" +
                "5 units in j (next to: l(3), h(2), c(1), a(3)) food=10, tech=10\n" +
                "Private: 5 Corporal: 0 Specialist: 0 Sergeant: 0 MasterSergeant: 0 FirstSergeant: 0 SergeantMajor: 0 \n" +
                "5 units in l (next to: g(2), h(1), j(3), c(2), e(3), f(1)) food=10, tech=10\n" +
                "\n" +
                "blue player:\n" +
                "---------------\n" +
                "Private: 5 Corporal: 0 Specialist: 0 Sergeant: 0 MasterSergeant: 0 FirstSergeant: 0 SergeantMajor: 0 \n" +
                "5 units in b (next to: d(1), c(2), a(1)) food=10, tech=10\n" +
                "Private: 5 Corporal: 0 Specialist: 0 Sergeant: 0 MasterSergeant: 0 FirstSergeant: 0 SergeantMajor: 0 \n" +
                "5 units in c (next to: b(2), d(2), l(2), j(1), e(3), a(2)) food=10, tech=10\n" +
                "Private: 5 Corporal: 0 Specialist: 0 Sergeant: 0 MasterSergeant: 0 FirstSergeant: 0 SergeantMajor: 0 \n" +
                "5 units in d (next to: b(1), c(2), e(2)) food=10, tech=10\n" +
                "Private: 5 Corporal: 0 Specialist: 0 Sergeant: 0 MasterSergeant: 0 FirstSergeant: 0 SergeantMajor: 0 \n" +
                "5 units in e (next to: d(2), l(3), c(3), f(2)) food=10, tech=10\n" +
                "Private: 5 Corporal: 0 Specialist: 0 Sergeant: 0 MasterSergeant: 0 FirstSergeant: 0 SergeantMajor: 0 \n" +
                "5 units in f (next to: g(2), l(1), k(2), e(2), i(3)) food=10, tech=10\n" +
                "Private: 5 Corporal: 0 Specialist: 0 Sergeant: 0 MasterSergeant: 0 FirstSergeant: 0 SergeantMajor: 0 \n" +
                "5 units in k (next to: f(2), i(2)) food=10, tech=10\n" +
                "\n";
        System.out.println(v1.displayBoard());
//        assertEquals(expected2, v1.displayBoard());
    }
}
