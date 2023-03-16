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
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ServerTest {

    @Test
    void test_transData() throws IOException, ClassNotFoundException, InterruptedException {
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
//                    Server s = new Server(riskGameBoard, input, 12345);
//                    s.tryConnectMulClient(1);
//                    s.closePipe();
                } catch (Exception e) {
                }
            }
        };
        th1.start();
        Thread.sleep(100);
        Socket s1 = new Socket("localhost", 12345);
        ObjectOutputStream out = new ObjectOutputStream(s1.getOutputStream());
        Server s = new Server(riskGameBoard, input, 12346);
//        s.tryConnectClient();
//        s.closePipe();
//        assertEquals("Red", out);
        //ObjectInputStream in = new ObjectInputStream(s1.getInputStream());

    }

}
