package edu.duke.ece651.team3.client.controller;

import edu.duke.ece651.team3.client.model.Game;
import edu.duke.ece651.team3.shared.Player;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.scene.text.Text;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class CheckBoxController implements Initializable {

    @FXML
    private Button next;

    @FXML
    private RadioButton move;

    @FXML
    private ToggleGroup Action;

    @FXML
    private RadioButton upgrade;

    @FXML
    private RadioButton attack;

    @FXML
    private RadioButton done;
    @FXML
    private Label playerColor;

    private int playerID;

    public CheckBoxController(int _playerID) {
        //playerColor = new Label();
//        this.playerID = _playerID;
//        setPlayerColor();
    }


    @FXML
    void goToActionPage(MouseEvent event) throws IOException {
        if(move.isSelected()){
            Parent nextPageParent = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/ui/slider.fxml")));
            Scene nextPageScene = new Scene(nextPageParent);

            Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
            window.setScene(nextPageScene);
            window.show();
        }
        else if(upgrade.isSelected()){
            Parent nextPageParent = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/ui/slider.fxml")));
            Scene nextPageScene = new Scene(nextPageParent);

            Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
            window.setScene(nextPageScene);
            window.show();

        }
        else if(attack.isSelected()){
            Parent nextPageParent = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/ui/slider.fxml")));
            Scene nextPageScene = new Scene(nextPageParent);

            Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
            window.setScene(nextPageScene);
            window.show();

        }
        else if(done.isSelected()){
            Parent nextPageParent = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/ui/slider.fxml")));
            Scene nextPageScene = new Scene(nextPageParent);

            Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
            window.setScene(nextPageScene);
            window.show();

        }

    }

    @FXML
    public void initialize(URL location, ResourceBundle resources) {
        playerColor.setText("Blue");
    }
}
