package gui;

import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class FuneralCreator4 extends FuneralCreatorBase {
    @FXML
    private Button cancelButton;

    @FXML
    private Button backButton;

    @FXML
    private Button acceptButton;

    public void initialize() {
        backButton.setOnAction(e -> {
            setView("/funeralcreator3.fxml");
        });
    }

    public void initData(CreatorData data) {
        super.initData(data);
    }
}
