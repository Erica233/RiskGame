package edu.duke.ece651.team3.client.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class ChooseActionController {
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

    private String color;
    private int playerId;

    public ChooseActionController(int id) {
        this.playerId = id;
    }

    @FXML
    void goToActionPage(MouseEvent event) throws IOException {
        if(move.isSelected()){

//            Parent nextPageParent = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/ui/movePage.fxml")));
//            Scene nextPageScene = new Scene(nextPageParent);
//
//            Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
//            window.setScene(nextPageScene);
//            window.show();
        }
        else if(upgrade.isSelected()){
            Parent nextPageParent = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/ui/upgradePage.fxml")));
            Scene nextPageScene = new Scene(nextPageParent);

            Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
            window.setScene(nextPageScene);
            window.show();

        }
        else if(attack.isSelected()){
            Parent nextPageParent = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/ui/attackPage.fxml")));
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

    //    @FXML
    public void initialize() {
        if (playerId == 0) {
            playerColor.setText("You are the Orange Player. What would you like to do?");
        } else {
            playerColor.setText("You are the Blue Player. What would you like to do?");
        }
    }
}

