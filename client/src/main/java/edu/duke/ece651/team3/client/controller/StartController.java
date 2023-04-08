package edu.duke.ece651.team3.client.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.event.ActionEvent;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class StartController {
    @FXML
    private Button startButton;
    @FXML
    private Button quitButton;

    @FXML
    public void onQuitButton(ActionEvent ae) {
        Stage stage = (Stage) quitButton.getScene().getWindow();
        stage.close();
    }

//    @FXML
//    public void onStartButton(ActionEvent ae) {
//        Object source = ae.getSource();
//        if (source instanceof Button) {
//            Button btn = (Button) source;
//            currentNumber.setText(currentNumber.getText() + btn.getText());
//        } else {
//            throw new IllegalArgumentException("Invalid source " + source + " for ActionEvent");
//        }
//    }
}
