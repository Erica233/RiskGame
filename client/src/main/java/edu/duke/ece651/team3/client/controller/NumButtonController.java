package edu.duke.ece651.team3.client.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class NumButtonController {
    @FXML
    TextField currentNumber;

    @FXML
    public void onNumberButton(ActionEvent ae) {
        Object source = ae.getSource();
        if (source instanceof Button) {
            Button btn = (Button) source;
            currentNumber.setText(currentNumber.getText() + btn.getText());
        } else {
            throw new IllegalArgumentException("Invalid source " + source + " for ActionEvent");
        }
    }
}
