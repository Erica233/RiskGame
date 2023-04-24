package edu.duke.ece651.team3.client.controller;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import edu.duke.ece651.team3.client.ShowViews;
import edu.duke.ece651.team3.client.model.Game;
import edu.duke.ece651.team3.shared.ConnectDb;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.checkerframework.checker.nullness.qual.MonotonicNonNull;

import java.io.IOException;

public class CreateAccountPageController {
    @FXML
    private PasswordField password;

    @FXML
    private Button doneButton;

    @FXML
    private Button backButton;

    @FXML
    private Label errorLogin;

//    @FXML
//    private PasswordField re_password;

    @FXML
    private TextField username;

    int playerId;
    Stage stage;
    Game gameEntity;

    MongoClient mongoClient;
    MongoDatabase database;

    public CreateAccountPageController(int id, Stage _stage, Game _gameEntity) {
        this.playerId = id;
        this.stage = _stage;
        this.gameEntity = _gameEntity;
        this.mongoClient = ConnectDb.getMongoClient();
        this.database = ConnectDb.connectToDb("riscDB");
    }

    @FXML
    void onBackButton(MouseEvent event) throws IOException {
        ShowViews.showGameView(stage, "/ui/loginPage.fxml", gameEntity);
    }
    @FXML
    void onDoneButton(MouseEvent event) throws IOException {
        MongoCollection<Document> accountsCo = database.getCollection("accountsCo");

        Bson filter = Filters.eq("username", username.getText());
        Document account = accountsCo.find(filter).first();
        if (account != null) {
            errorLogin.setText("Cannot create new account: \nusername occupied!");
        } else {
            Document newAccount = new Document();
            newAccount.put("username", username.getText());
            newAccount.put("password", password.getText());
            accountsCo.insertOne(newAccount);

            ShowViews.showGameView(stage, "/ui/loginPage.fxml", gameEntity);
        }
    }
}
