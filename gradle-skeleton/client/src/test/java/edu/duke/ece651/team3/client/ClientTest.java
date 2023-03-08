package edu.duke.ece651.team3.client;
import edu.duke.ece651.team3.shared.*;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.ResourceAccessMode;
import org.junit.jupiter.api.parallel.ResourceLock;
import org.junit.jupiter.api.parallel.Resources;

import java.io.*;

public class ClientTest {

    void test_file_helper(String inFile, String outFile) throws IOException, InterruptedException, ClassNotFoundException {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        PrintStream out = new PrintStream(bytes, true);

        //Getting InputStream for input.txt file
        InputStream input = getClass().getClassLoader().getResourceAsStream(inFile);
        assertNotNull(input);

        //Getting the expected output
        InputStream expectedStream = getClass().getClassLoader().getResourceAsStream(outFile);
        assertNotNull(expectedStream);

        //Remember the current System.in and System.out
        InputStream oldIn = System.in;
        PrintStream oldOut = System.out;

        //We'll do this inside a try...finally to ensure
        //we restore System.in and System.out
        try {
            System.setIn(input);
            System.setOut(out);
            Client.main(new String[0]);
        }
//    } catch (InterruptedException e) {
//      throw new RuntimeException(e);
//    } catch (ClassNotFoundException e) {
//      throw new RuntimeException(e);
//    }
        finally {
            System.setIn(oldIn);
            System.setOut(oldOut);
        }

        //read all the data from our expectedStream (output.txt)
        String expected = new String(expectedStream.readAllBytes());

        //we get the String out of bytes
        String actual = bytes.toString();
        //compare them
        assertEquals(expected, actual);
    }

    @Test
    public void test_checkInput() throws IOException {
        String s1 = "A"; //valid
        String s2 = "O"; //Invalid

        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        Territory t1 = new Territory("Hogwarts", 10);
        Board m1 = new RiskGameBoard(t1);
        BoardTextView v1 = new BoardTextView(m1);
        String expected = "10 units in Hogwarts\n";
        assertEquals(expected, v1.displayBoard());
        Client c = new Client(input, v1);

        String test1 = c.checkUserInput(s1);
        String test2 = c.checkUserInput(s2);

        assertNull(test1); //No error info
        assertNotNull(test2); //error info

        assertEquals(true, c.checkValidation());
        assertEquals(expected, c.display());


//    c.connectServer();

    }


    @Test
    @ResourceLock(value = Resources.SYSTEM_OUT, mode = ResourceAccessMode.READ_WRITE)
    void test_Input() throws IOException, InterruptedException, ClassNotFoundException {
        test_file_helper("input1.txt", "output1.txt"); //A, B are human, B wins
        test_file_helper("input2.txt", "output2.txt"); //A, B are human, B wins
        test_file_helper("input3.txt", "output3.txt"); //A, B are human, B wins
    }

}
