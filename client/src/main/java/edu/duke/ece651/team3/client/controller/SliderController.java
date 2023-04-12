package edu.duke.ece651.team3.client.controller;
import javafx.fxml.FXML;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

public class SliderController {
    @FXML
    private Slider slider;

    @FXML
    private Text text;

    @FXML
    void updateText(MouseEvent event) {
        text.setText(String.valueOf((int)slider.getValue()));
    }
}
