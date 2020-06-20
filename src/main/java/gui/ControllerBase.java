package gui;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class ControllerBase {
    @FXML
    private AnchorPane rootPane;

    protected void setView(String path) {
        try {
            var pane = (AnchorPane)FXMLLoader.load(getClass().getResource(path));
            rootPane.getChildren().setAll(pane);
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }
}
