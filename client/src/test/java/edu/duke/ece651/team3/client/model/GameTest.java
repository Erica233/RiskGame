package edu.duke.ece651.team3.client.model;

import static org.junit.jupiter.api.Assertions.*;

import edu.duke.ece651.team3.shared.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class GameTest {
//  @Test
//  @Timeout(2500)
//  void test_Game() throws Exception {
//    int port = 12345;
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
//    Game game = new Game();
//    ClientCommunicator client = game.getClientCommunicator();
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
//    assertEquals(-1, game.getPlayerId());
////    System.out.println(game.getPlayerId());
//
//
//    server3.interrupt();
//    server3.join();
//  }
}
