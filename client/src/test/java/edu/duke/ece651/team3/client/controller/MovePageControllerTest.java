package edu.duke.ece651.team3.client.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

import edu.duke.ece651.team3.client.model.ClientCommunicator;
import edu.duke.ece651.team3.client.model.Game;
import edu.duke.ece651.team3.shared.*;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;

@ExtendWith(ApplicationExtension.class)
public class MovePageControllerTest {
//    @FXML
//    private ChoiceBox<Integer> LV0_choice;
//    @FXML
//    private ChoiceBox<Integer> LV1_choice;
//    @FXML
//    private ChoiceBox<Integer> LV2_choice;
//    @FXML
//    private ChoiceBox<Integer> LV3_choice;
//    @FXML
//    private ChoiceBox<Integer> LV4_choice;
//    @FXML
//    private ChoiceBox<Integer> LV5_choice;
//    @FXML
//    private ChoiceBox<Integer> LV6_choice;
//    @FXML
//    private ChoiceBox<String> choice_source;
//    @FXML
//    private ChoiceBox<String> choice_Dest;
//    @FXML
//    private Label playerColor;
//    @FXML
//    private Button nextButton;
//    @FXML
//    private AnchorPane contextBox;
//
//    private int playerID;
//
//    private HashMap<Unit, ChoiceBox<Integer>> eachLevelUnitNum; //The Unit and its choices
//
//    private ArrayList<ChoiceBox<Integer>> allChoiceBoxes; //All choice boxes
//
//    private Game gameEntity;
//    Stage stage;
//    @Start
//    private void start(Stage stage) throws Exception {
//      int port = 12345;
//      RiskGameBoard b1 = new RiskGameBoard();
//      b1.initE2Map();
//
//
//      Thread server3 = new Thread() {
//        @Override()
//        public void run() {
//          try {
//            //mock server
//            ServerSocket serverSock = new ServerSocket(port);
//            Socket clientSockets = serverSock.accept();
//            ObjectOutputStream out = new ObjectOutputStream(clientSockets.getOutputStream());
//            ObjectInputStream in = new ObjectInputStream(clientSockets.getInputStream());
//
//            out.close();
//            in.close();
//            serverSock.close();
//            clientSockets.close();
//          } catch (Exception e) {
//          }
//        }
//      };
//      server3.start();
//      Thread.sleep(100);
//
//      Game _game = new Game();
//      int id = 0;
//      this.gameEntity = _game;
//      this.stage = stage;
//      server3.interrupt();
//      server3.join();
//    }

//    @Test
//    public void test_initialize(){
//        this.playerColor = new Label();
//
//      MovePageController movePageController = new MovePageController(0, stage, gameEntity);
//      movePageController.initialize();
//    }

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
//    int id = 0;
////    Stage stage = new Stage();
////    MovePageController movePageController = new MovePageController(id, stage, game);
////    movePageController.initialize();
//
//
//    server3.interrupt();
//    server3.join();
//  }

}
