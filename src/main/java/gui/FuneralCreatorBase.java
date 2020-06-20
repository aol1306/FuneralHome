package gui;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class FuneralCreatorBase extends ControllerBase {
    protected CreatorData creatorData = new CreatorData();

    public void initData(CreatorData data) {
        this.creatorData = data;
    }

    @Override
    protected void setView(String path) {
        try {
            var loader = new FXMLLoader(getClass().getResource(path));
            var pane = (AnchorPane) loader.load();
            super.rootPane.getChildren().setAll(pane);

            // load data
            FuneralCreatorBase controller = loader.getController();
            controller.initData(this.creatorData);
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }
}