package edu.duke.ece651.team3.client;
import edu.duke.ece651.team3.shared.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


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
            edu.duke.ece651.team3.client.Client.main(new String[0]);
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
    void testDisplayTerritoried(){

    }
    @Test
    void checkValidation(){
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        Territory t1 = new Territory("Mordor", 8);
        RiskGameBoard b1 = new RiskGameBoard();
        b1.tryAddTerritory(t1);
        BoardTextView v1 = new BoardTextView(b1);
        Client c = new Client(input, b1, v1);
        assertEquals(true, c.checkValidation());

    }


}
