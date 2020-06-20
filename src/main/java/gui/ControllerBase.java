package gui;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class ControllerBase {
    @FXML
    protected AnchorPane rootPane;

    protected void setView(String path) {
        try {
            var pane = (AnchorPane)FXMLLoader.load(getClass().getResource(path));
            rootPane.getChildren().setAll(pane);
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }
}
