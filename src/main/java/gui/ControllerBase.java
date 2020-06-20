package gui;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class ControllerBase {
    @FXML
    private AnchorPane rootPane;

    protected void setView(String path) {
        try {
            var pane = FXMLLoader.load(getClass().getResource(path));
            rootPane.getChildren().setAll((Node) pane);
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }
}
