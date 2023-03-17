package edu.duke.ece651.team3.client;
import edu.duke.ece651.team3.shared.*;
import org.checkerframework.checker.units.qual.C;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static java.lang.System.out;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.refEq;
import static org.mockito.Mockito.mock;

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
                Client.main(new String[0]);
            } catch (Exception e) {

            }
          }
        };
        th.start();
        Thread.sleep(100);
        Socket acceptedSocekt = ss.accept();

          ObjectOutputStream sendObjToClient = new ObjectOutputStream(acceptedSocekt.getOutputStream());
          String info = "Hi, This is Server!! I am connecting with you";
          String playerColor = "Green";
          sendObjToClient.writeObject(info);
          sendObjToClient.writeObject(playerColor);

      sendObjToClient.writeObject(b1);
      ss.close();
      acceptedSocekt.close();
      }
      @Test
      void test_tryConnectServer() throws IOException, InterruptedException {
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
                  } catch (Exception e) {

                  }
              }
          };
          th.start();
          Thread.sleep(100);

      }
      @Test
      void test_displayTerritory(){
          BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
          Territory t1 = new Territory("Mordor", 8);
          RiskGameBoard b1 = new RiskGameBoard();
          b1.tryAddTerritory(t1);
          BoardTextView v1 = new BoardTextView(b1);
          Client c = new Client(input, b1, v1);
          c.addTerritories();
          c.displayTerritory();
      }

}
