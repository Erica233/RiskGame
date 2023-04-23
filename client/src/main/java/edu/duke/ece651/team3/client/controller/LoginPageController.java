package edu.duke.ece651.team3.client.controller;

import edu.duke.ece651.team3.client.ShowViews;
import edu.duke.ece651.team3.client.model.Game;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;

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

    public LoginPageController(int id, Stage _stage, Game _gameEntity) {
        this.playerId = id;
        this.stage = _stage;
        this.gameEntity = _gameEntity;

    }
    @FXML
    void userLogin(MouseEvent event) throws IOException {
        checkLogin();
    }

    @FXML
    void onCreateAccountButton(MouseEvent event) throws IOException {
        ShowViews.showGameView(stage, "/ui/createAccountPage.fxml", gameEntity);
    }


    @FXML
    void checkLogin() throws IOException {
        if(playerId == 0 && username.getText().equals("orange") && password.getText().equals("1234")){
            errorLogin.setText("Success!");
            ShowViews.showGameView(stage, "/ui/whole.fxml", gameEntity);
        }
        else if(playerId == 1 && username.getText().equals("blue") && password.getText().equals("4321")){
            errorLogin.setText("Success!");
            ShowViews.showGameView(stage, "/ui/whole.fxml", gameEntity);
        }
        else if(username.getText().isEmpty() && password.getText().isEmpty()){
            errorLogin.setText("Please Enter Both Username and the Password!");
        }
        else{
            errorLogin.setText("Wrong Username or Password!");
        }

    }
}
