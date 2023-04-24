package edu.duke.ece651.team3.client.controller;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import edu.duke.ece651.team3.client.ShowViews;
import edu.duke.ece651.team3.client.model.Game;
import edu.duke.ece651.team3.shared.ConnectDb;
import javafx.event.ActionEvent;
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

    int playerId;
    Stage stage;
    Game gameEntity;
    MongoClient mongoClient;
    MongoDatabase database;

    public LoginPageController(int id, Stage _stage, Game _gameEntity) {
        this.playerId = id;
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
            //start game

            ShowViews.showGameView(stage, "/ui/whole.fxml", gameEntity);
        }

    }

    @FXML
    void onCreateAccountButton(MouseEvent event) throws IOException {
        ShowViews.showGameView(stage, "/ui/createAccountPage.fxml", gameEntity);
    }
}
