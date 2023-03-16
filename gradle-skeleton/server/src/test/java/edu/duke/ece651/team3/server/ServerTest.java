package edu.duke.ece651.team3.server;
import edu.duke.ece651.team3.shared.RiskGameBoard;
import edu.duke.ece651.team3.shared.Territory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import java.io.*;
import java.net.Socket;

import static org.junit.jupiter.api.Assertions.assertEquals;
//import java.io.IOTest;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.net.Socket;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ServerTest {
//        public String sendRequest(String rString) throws IOException {
//            Socket s = new Socket("localhost", 1651);
//            s.getOutputStream().write(rString.getBytes());
//            s.getOutputStream().flush();
//            s.shutdownOutput();
//            BufferedReader br =
//                    new BufferedReader(new InputStreamReader(s.getInputStream()));
//            StringBuilder sb = new StringBuilder();
//            String str = br.readLine();
//            while (str != null) {
//                System.out.println("Read: " + str);
//                sb.append(str);
//                sb.append("\n"); // gets stripped off by br.readLine()
//                str = br.readLine();
//            }
//            return sb.toString();
//        }


        @Test
    void test_transData() throws IOException, ClassNotFoundException, InterruptedException{
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        Territory t1 = new Territory("Hogwarts", 10);
        RiskGameBoard riskGameBoard = new RiskGameBoard();
        riskGameBoard.tryAddTerritory(t1);
        int numPlayer = 2;
        Thread th1 = new Thread() {
            @Override()
            public void run() {
                try {
                    Server.main(new String[0]);
                } catch (Exception e) {
                }
            }
        };
        th1.start();
        Thread.sleep(100);
        Socket s1 = new Socket("localhost", 12345);
        ObjectInputStream in = new ObjectInputStream(s1.getInputStream());
        ObjectOutputStream out = new ObjectOutputStream(s1.getOutputStream());
        Server s = new Server(riskGameBoard, input, 12346);
//        Client client = Mockito.mock(Client.class);
//        String actual = sendRequest("4\n9\n");
//        assertEquals(IOTest.GREETING + "\n" + IOTest.PROMPT + "\n"
//                        + "2 2\n" + IOTest.PROMPT + "\n"
//                        + "3 3\n" + IOTest.PROMPT + "\n",
//                actual);
//        actual = sendRequest("7\n12\n22\n");
//        assertEquals(IOTest.GREETING + "\n" + IOTest.PROMPT + "\n"
//                        + "7\n" + IOTest.PROMPT + "\n"
//                        + "2 2 3\n" + IOTest.PROMPT + "\n"
//                        + "2 11\n" + IOTest.PROMPT + "\n",
//                actual);
//        th.interrupt();
//        th.join();

    }

}
