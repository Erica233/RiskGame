package edu.duke.ece651.team3.client.controller;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import edu.duke.ece651.team3.client.ShowViews;
import edu.duke.ece651.team3.client.model.Game;
import edu.duke.ece651.team3.shared.ConnectDb;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.io.IOException;

import static com.mongodb.client.model.Filters.eq;

public class LoginPageController {
    @FXML
    private PasswordField password;
    @FXML
    private Button loginButton;
    @FXML
    private Label errorLogin;
    @FXML
    private TextField username;
    @FXML
    private Button createAccountButton;

    Stage stage;
    Game gameEntity;
    MongoClient mongoClient;
    MongoDatabase database;

    public LoginPageController(Stage _stage, Game _gameEntity) {
        this.stage = _stage;
        this.gameEntity = _gameEntity;
        this.mongoClient = ConnectDb.getMongoClient();
        this.database = ConnectDb.connectToDb("riscDB");
    }
    @FXML
    void userLogin(MouseEvent event) throws IOException {
        MongoCollection<Document> accountsCo = database.getCollection("accountsCo");
        Bson filter = Filters.and(eq("username", username.getText()), eq("password", password.getText()));
        Document account = accountsCo.find(filter).first();
        if (account == null) {
            errorLogin.setText("Login failed: \nusername and password are not matched!");
        } else {
            errorLogin.setText("Waiting other players...");
            Thread th = new Thread(new Task() {
                @Override
                protected Object call() throws Exception {
                    try {
                        //start game
                        gameEntity = new Game();
                        gameEntity.sendString(username.getText());
                        gameEntity.storePlayerId();
                        int playerID = gameEntity.getPlayerId();
                        System.out.println("playerId=" + playerID);
                        if (playerID != 0 && playerID != 1) {
                            throw new Exception("Failed to receive valid playerId!");
                        }
                        gameEntity.storeNewBoard();
                        System.out.println("A new turn: updated new board as below!");
                        System.out.println(gameEntity.getRiskGameBoard().displayBoard());
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    ShowViews.showGameView(stage, "/ui/whole.fxml", gameEntity);
                                } catch (IOException e) {
                                    throw new RuntimeException(e);
                                }
                            }
                        });
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (ClassNotFoundException | IOException e) {
                        throw new RuntimeException(e);
                    }
                    return null;
                }
            });
            th.setDaemon(true);
            th.start();
        }

    }

    @FXML
    void onCreateAccountButton(MouseEvent event) throws IOException {
        ShowViews.showStartView(stage, "/ui/createAccountPage.fxml", gameEntity);
    }

}
