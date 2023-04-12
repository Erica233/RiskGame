package edu.duke.ece651.team3.client.model;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import edu.duke.ece651.team3.client.Client;
import edu.duke.ece651.team3.shared.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import org.mockito.Mockito;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class ClientCommunicatorTest {
//  @Test
//  @Timeout(2500)
//  void test_nullInput() throws Exception {
//    int port = 12349;
//    RiskGameBoard b1 = new RiskGameBoard();
//    b1.initE2Map();
//
//
//    Thread server3 = new Thread() {
//      @Override()
//      public void run() {
//        try {
//          //mock server
//          ServerSocket serverSock = new ServerSocket(port);
//          Socket clientSockets = serverSock.accept();
//          ObjectOutputStream out = new ObjectOutputStream(clientSockets.getOutputStream());
//          ObjectInputStream in = new ObjectInputStream(clientSockets.getInputStream());
//          //Send ID
//          out.writeInt(0);
//          out.reset();
//          //Send Board
//          out.writeObject(b1);
//          out.reset();
//
//          //send Game Result
//          out.writeInt(1);
//          out.reset();
//
//          //Receive Action List
//          ArrayList<Action> actions = (ArrayList<Action>) in.readObject();
//
//          out.close();
//          in.close();
//          serverSock.close();
//          clientSockets.close();
//        } catch (Exception e) {
//        }
//      }
//    };
//    server3.start();
//    Thread.sleep(100);
//
//    ClientCommunicator client = new ClientCommunicator("localhost", port);
//    System.out.println(client + " connect to the Server successfully!");
//
//    int playerId = client.recvPlayerId();
//    assertEquals(0, playerId);
//
//    RiskGameBoard riskGameBoard = (RiskGameBoard) client.recvBoard();
//    assertEquals(riskGameBoard, b1);
//
//    int gameResult = client.recvGameResult();
//    assertEquals(1, gameResult);
//
//    //Send Action
//    ArrayList<Action> actions = new ArrayList<>();
//    ArrayList<Unit> unitsToChange = new ArrayList<>();
//    unitsToChange.add(new Private(1));
//
//
//    Action move = new MoveAction("a", "j", unitsToChange);
//    Action attack = new AttackAction("a", "b", unitsToChange);
//    Action upgrade = new UpgradeAction("a", "a", unitsToChange);
//    actions.add(move);
//    actions.add(attack);
//    actions.add(upgrade);
//    client.sendActionListsToServer(actions);
//
//    client.closePipes();
//
//
//    server3.interrupt();
//    server3.join();
//  }
//  @Test
//  @Timeout(2500)
//  void test_serverInThread() throws Exception {
//    int port = 12348;
//
//    Thread server4 = new Thread() {
//      @Override()
//      public void run() {
//        try {
//          RiskGameBoard b1 = new RiskGameBoard();
//          b1.initE2Map();
//          ArrayList<Action> actions = new ArrayList<>();
//
//          //mock server
//          ServerSocket serverSock = new ServerSocket(port);
//          Socket clientSockets = serverSock.accept();
//          ObjectOutputStream out = new ObjectOutputStream(clientSockets.getOutputStream());
//          ObjectInputStream in = new ObjectInputStream(clientSockets.getInputStream());
//
//          out.writeInt(0); //Player's ID
//          out.reset();
//
//          int gameResult = -1;
//
//          while (gameResult != 1 && gameResult != 0) {
//            //send board to client
//            out.writeObject(b1);
//            out.reset();
//            //receive action lists to client
//            actions = (ArrayList<Action>) in.readObject();
//            String done = (String) in.readObject();
//            //send game result to client
//            out.writeInt(0);
//            out.reset();
//            gameResult = 0;
//          }
//
//          out.close();
//          in.close();
//          serverSock.close();
//          clientSockets.close();
//        } catch (Exception e) {
//        }
//      }
//    };
//    server4.start();
//    Thread.sleep(100);
//
//    Client client = new Client("localhost", port);
//    System.out.println(client + " connect to the Server successfully!");
//
//    BufferedReader mockInput1 = Mockito.mock(BufferedReader.class);
//    when(mockInput1.readLine()).thenReturn("M", "a", "j", "1", "0", "0", "0", "D");
//    client.setInputReader(mockInput1);
//
//    client.recvPlayerId();
//    client.playGame();
//    client.closePipes();
//
//    server4.interrupt();
//    server4.join();
//  }
//
//  @Test
//  @Timeout(2500)
//  void test_main() throws InterruptedException, IOException {
//    int port = 12345;
//    Thread server5 = new Thread() {
//      @Override()
//      public void run() {
//        try {
//          RiskGameBoard b1 = new RiskGameBoard();
//          b1.initE2Map();
//          ArrayList<Action> actions = new ArrayList<>();
//
//          //mock server
//          ServerSocket serverSock = new ServerSocket(port);
//          Socket clientSockets = serverSock.accept();
//          ObjectOutputStream out = new ObjectOutputStream(clientSockets.getOutputStream());
//          ObjectInputStream in = new ObjectInputStream(clientSockets.getInputStream());
//          out.writeInt(0);
//          out.reset();
//
//          int gameResult = -1;
//
//          while (gameResult != 1 && gameResult != 0) {
//            //send board to client
//            out.writeObject(b1);
//            out.reset();
//            //receive action lists to client
//            actions = (ArrayList<Action>) in.readObject();
//            String done = (String) in.readObject();
//            //send game result to client
//            out.writeInt(0);
//            out.reset();
//            gameResult = 0;
//          }
//
//          out.close();
//          in.close();
//          serverSock.close();
//          clientSockets.close();
//        } catch (Exception e) {
//        }
//      }
//    };
//    server5.start();
//    Thread.sleep(100);
//
//    String in = "D";
//    InputStream inputStream = new ByteArrayInputStream(in.getBytes());
//    //PrintStream outStream = new PrintStream(bytes, true);
//    System.setIn(inputStream);
//    Client.main(new String[0]);
//
//    server5.interrupt();
//    server5.join();
//  }
//
//  @Test
//  @Timeout(2500)
//  void test_otherCases() throws Exception {
//    int port = 12348;
//    Thread server6 = new Thread() {
//      @Override()
//      public void run() {
//        try {
//          RiskGameBoard b1 = new RiskGameBoard();
//          b1.initE2Map();
//          //Adding food resources to a
//          Player p1 = b1.getAllPlayers().get(0);
//          Territory a = p1.findOwnedTerritoryByName("a");
//          a.setFood(10);
//
//          ArrayList<Action> actions = new ArrayList<>();
//
//          //mock server
//          ServerSocket serverSock = new ServerSocket(port);
//          Socket clientSockets = serverSock.accept();
//          ObjectOutputStream out = new ObjectOutputStream(clientSockets.getOutputStream());
//          ObjectInputStream in = new ObjectInputStream(clientSockets.getInputStream());
//          out.writeInt(0);
//          out.reset();
//
//          int gameResult = -1;
//          int cnt = 0;
//
//          while (gameResult != 1 && gameResult != 0) {
//            //send board to client
//            out.writeObject(b1);
//            out.reset();
//            //receive action lists to client
//            actions = (ArrayList<Action>) in.readObject();
//            String done = (String) in.readObject();
//            //send game result to client
//////                        out.writeInt(gameResult);
////                        out.reset();
//            if(cnt == 1){
//              gameResult = 0;
//              out.writeInt(gameResult);
//            }
//            if(cnt == 0){
//              gameResult = 2;
//              out.writeInt(gameResult);
//              cnt++;
//            }
//
//            out.reset();
//          }
//
//          out.close();
//          in.close();
//          serverSock.close();
//          clientSockets.close();
//        } catch (Exception e) {
//        }
//      }
//    };
//    server6.start();
//    Thread.sleep(100);
//
//    Client client = new Client("localhost", port);
//    System.out.println(client + " connect to the Server successfully!");
//
//    client.recvPlayerId();
//
//    BufferedReader mockInput1 = Mockito.mock(BufferedReader.class);
//    when(mockInput1.readLine()).thenReturn("S", "a", "b", "1", "0", "0", "0",
//            "M", "a", "j", "1", "0", "0", "0",
//            "A", "a", "j", "1", "0", "0", "0",
//            "A", "a", "b", "1", "0", "0", "0",
//            "M", "a", "b", "1", "0", "0", "0",
//            "M", "a", "b", "a", "0", "0", "0",
//            "M", "a", "c", "1", "0", "0", "0",
//            "D");
//    client.setInputReader(mockInput1);
//    client.playGame();
//    client.closePipes();
//
//
//    server6.interrupt();
//    server6.join();
//  }

}
