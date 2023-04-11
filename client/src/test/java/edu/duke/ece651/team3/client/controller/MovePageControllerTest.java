package edu.duke.ece651.team3.client.controller;

import static org.junit.jupiter.api.Assertions.*;

import edu.duke.ece651.team3.client.model.ClientCommunicator;
import edu.duke.ece651.team3.client.model.Game;
import edu.duke.ece651.team3.shared.*;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.framework.junit5.ApplicationExtension;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

@ExtendWith(ApplicationExtension.class)
public class MovePageControllerTest {
  @Test
  @Timeout(2500)
  void test_Game() throws Exception {
    int port = 12345;
    RiskGameBoard b1 = new RiskGameBoard();
    b1.initE2Map();


    Thread server3 = new Thread() {
      @Override()
      public void run() {
        try {
          //mock server
          ServerSocket serverSock = new ServerSocket(port);
          Socket clientSockets = serverSock.accept();
          ObjectOutputStream out = new ObjectOutputStream(clientSockets.getOutputStream());
          ObjectInputStream in = new ObjectInputStream(clientSockets.getInputStream());

          out.close();
          in.close();
          serverSock.close();
          clientSockets.close();
        } catch (Exception e) {
        }
      }
    };
    server3.start();
    Thread.sleep(100);

    Game game = new Game();
    int id = 0;
//    Stage stage = new Stage();
//    MovePageController movePageController = new MovePageController(id, stage, game);
//    movePageController.initialize();


    server3.interrupt();
    server3.join();
  }

}
