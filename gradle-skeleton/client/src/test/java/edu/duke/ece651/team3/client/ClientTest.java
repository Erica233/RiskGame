package edu.duke.ece651.team3.client;
import edu.duke.ece651.team3.shared.*;
import org.checkerframework.checker.units.qual.C;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import java.net.ServerSocket;


import java.io.*;
import java.net.Socket;

public class ClientTest {

    @Test
    void checkValidation() {
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        Territory t1 = new Territory("Mordor", 8);
        RiskGameBoard b1 = new RiskGameBoard();
        b1.tryAddTerritory(t1);
        BoardTextView v1 = new BoardTextView(b1);
        Client c = new Client(input, b1, v1);
        assertEquals(true, c.checkValidation());

    }

//    @Test
//    void test_client() throws IOException, ClassNotFoundException, InterruptedException {
//        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
//        Territory t1 = new Territory("Mordor", 8);
//        RiskGameBoard b1 = new RiskGameBoard();
//        b1.tryAddTerritory(t1);
//        BoardTextView v1 = new BoardTextView(b1);
//
//        Thread th1 = new Thread() {
//            @Override()
//            public void run() {
//                try {
//                    Client.main(new String[0]);
////                    Server s = new Server(riskGameBoard, input, 12345);
////                    s.tryConnectMulClient(1);
////                    s.closePipe();
//                } catch (Exception e) {
//                }
//            }
//        };
//        th1.start();
//        Thread.sleep(100);
//        Socket s1 = new Socket("localhost", 12345);
//        ObjectOutputStream out = new ObjectOutputStream(s1.getOutputStream());
//        Client c = new Client(input, b1, v1);
////        s.tryConnectClient();
////        s.closePipe();
////        assertEquals("Red", out);
//        //ObjectInputStream in = new ObjectInputStream(s1.getInputStream());
//
//
//    }
      @Test
      public void test_main() throws IOException, ClassNotFoundException, InterruptedException {
          BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        Territory t1 = new Territory("Mordor", 8);
        RiskGameBoard b1 = new RiskGameBoard();
        b1.tryAddTerritory(t1);
        BoardTextView v1 = new BoardTextView(b1);
        int portNum = 12345;
        ServerSocket ss = new ServerSocket(portNum);

//        ServerSocket ss = new ServerSocket(portNum);
        Thread th = new Thread() {
          @Override()
          public void run() {
            try {
                Client c = new Client(input, b1, v1);
                c.tryConnectServer();
                c.transObject(b1);
//                Client.main(new String[0]);
            } catch (Exception e) {

            }
          }
        };
        th.start();
        Thread.sleep(100);
        Socket acceptedSocekt = ss.accept();
//        ObjectOutputStream out = new ObjectOutputStream(acceptedSocekt.getOutputStream());
//        ss.close();
//         ObjectInputStream in = new
//         ObjectInputStream(acceptedSocekt.getInputStream());
//         RiskGameBoard riskGameBoard = (RiskGameBoard) in.readObject();
//
//         new ObjectInputStream(acceptedSocekt.getInputStream());
//         assertNotNull(riskGameBoard);
      }
    @Test
    public void test_trans() throws IOException, ClassNotFoundException, InterruptedException {
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        Territory t1 = new Territory("Mordor", 8);
        RiskGameBoard b1 = new RiskGameBoard();
        b1.tryAddTerritory(t1);
        BoardTextView v1 = new BoardTextView(b1);
        int portNum = 12349;
        ServerSocket ss = new ServerSocket(portNum);
        Socket s1 = new Socket("localhost", 12345);

//        ServerSocket ss = new ServerSocket(portNum);
        Thread th = new Thread() {
            @Override()
            public void run() {
                try {
                    Client c = new Client(input, b1, v1);
                    c.tryConnectServer();
                    c.transObject(b1);
//                Client.main(new String[0]);
                } catch (Exception e) {

                }
            }
        };
        th.start();
        Thread.sleep(100);
        Socket acceptedSocekt = ss.accept();
        ObjectOutputStream out = new ObjectOutputStream(acceptedSocekt.getOutputStream());
        out.writeObject(b1);
        // ObjectInputStream in = new
        // ObjectInputStream(acceptedSocekt.getInputStream());
        // Player myDisplay = (BasicPlayer) in.readObject();

        // Socket acceptedSocekt = ss.accept();
        // Thread.sleep(100);
        // ObjectInputStream in = new
        // ObjectInputStream(acceptedSocekt.getInputStream());
        // Player serverGameplayer = (Player) in.readObject();
        // assertNotNull(acceptedSocekt);
    }
}
