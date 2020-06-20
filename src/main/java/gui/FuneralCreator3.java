package gui;

import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class FuneralCreator3 extends ControllerBase {
    @FXML
    private Button backButton;

    @FXML
    private Button nextButton;

    public void initialize() {
        backButton.setOnAction(e -> {
            setView("/funeralcreator2.fxml");
        });
        nextButton.setOnAction(e -> {
            setView("/funeralcreator4.fxml");
        });
    }
}
