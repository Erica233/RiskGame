package edu.duke.ece651.team3.client.controller;

import edu.duke.ece651.team3.client.ShowViews;
import edu.duke.ece651.team3.client.model.Game;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

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

    String userName; //The userName to be stored into the database
    String passWord; //The passWord

    public CreateAccountPageController(int id, Stage _stage, Game _gameEntity) {
        this.playerId = id;
        this.stage = _stage;
        this.gameEntity = _gameEntity;
    }

    /**
     * This method checks whether the password and re-enter password is the same
     * //TODO: after adding the database, check whether the user has already been in the system
     * @throws IOException
     */
    public void checkValid() throws IOException {
        ShowViews.showGameView(stage, "/ui/loginPage.fxml", gameEntity);
//        if(password.getText().equals(re_password.getText())){
//            ShowViews.showGameView(stage, "/ui/whole.fxml", gameEntity);
//        }
//        else{
//            errorLogin.setText("Two passwords are not matching!");
//        }
    }

    @FXML
    void onBackButton(MouseEvent event) throws IOException {
        ShowViews.showGameView(stage, "/ui/loginPage.fxml", gameEntity);
    }
    @FXML
    void onDoneButton(MouseEvent event) throws IOException {
        checkValid();
        //TODO: Store the data into the database here

    }
}
