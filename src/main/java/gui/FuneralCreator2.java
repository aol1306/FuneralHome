package gui;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;

public class FuneralCreator2 extends FuneralCreatorBase {
    @FXML
    private ListView<String> selectedSetsListView;

    @FXML
    private ListView<String> availableDecorationsListView;

    @FXML
    private ListView<String> selectedDecorationsListView;

    @FXML
    private Button backButton;

    @FXML
    private Button nextButton;

    public void initialize() {
        backButton.setOnAction(e -> {
            setView("/funeralcreator1.fxml");
        });
        nextButton.setOnAction(e -> {
            setView("/funeralcreator3.fxml");
        });
        selectedSetsListView.setItems(FXCollections.observableArrayList());
        availableDecorationsListView.setItems(FXCollections.observableArrayList("Dekoracja 1", "Dekoracja 2"));
        selectedDecorationsListView.setItems(FXCollections.observableArrayList("Dekoracja 3"));
    }

    public void initData(CreatorData data) {
        super.initData(data);
    }
}
