package gui;

import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Coffin;
import model.Decoration;
import model.PremiumCoffin;
import model.StandardCoffin;

import java.util.ArrayList;
import java.util.List;

enum CoffinType {
    STANDARD("standard"),
    PREMIUM("premium");

    private String name;

    CoffinType(String type) {
        this.name = type;
    }

    public String getName() {
        return name;
    }
}

public class FuneralCreator2 extends FuneralCreatorBase {
    public class TableData {
        public SimpleStringProperty coffinType = new SimpleStringProperty();
        public SimpleListProperty<String> decorations = new SimpleListProperty<>();
        public SimpleStringProperty deadmanName = new SimpleStringProperty();
        public SimpleStringProperty deadmanSurname = new SimpleStringProperty();

        public String getCoffinType() {
            return coffinType.get();
        }

        public ObservableList<String> getDecorations() {
            return decorations.get();
        }

        public String getDeadmanName() {
            return deadmanName.get();
        }

        public String getDeadmanSurname() {
            return deadmanSurname.get();
        }
    }

    // table data
    private ObservableList<TableData> tableData;

    @FXML
    private TableView<TableData> selectedSetsTableView;

    @FXML
    private TableColumn<TableData, String> deadmanNameColumn;

    @FXML
    private TableColumn<TableData, String> deadmanSurnameColumn;

    @FXML
    private TableColumn<TableData, String> coffinTypeColumn;

    @FXML
    private TableColumn<TableData, List<String>> decorationsColumn;

    @FXML
    private ListView<String> availableDecorationsListView;

    @FXML
    private ListView<String> selectedDecorationsListView;

    @FXML
    private Button backButton;

    @FXML
    private Button nextButton;

    @FXML
    private Button addDecorationButton;

    @FXML
    private Button removeDecorationButton;

    @FXML
    private Button addOrderButton;

    @FXML
    private Button removeOrderButton;

    @FXML
    private RadioButton standardCoffinRadioButton;

    @FXML
    private RadioButton premiumCoffinRadioButton;

    @FXML
    private TextField deadmanName;

    @FXML
    private TextField deadmanSurname;

    public void initialize() {
        backButton.setOnAction(e -> {
            saveData();
            setView("/funeralcreator1.fxml");
        });
        nextButton.setOnAction(e -> {
            saveData();
            setView("/funeralcreator3.fxml");
        });
        standardCoffinRadioButton.setOnAction(e -> {
            var rb = (RadioButton)e.getSource();
            if (rb.isSelected()) {
                setDisableDecorationSelection(true);
            }
        });
        premiumCoffinRadioButton.setOnAction(e -> {
            var rb = (RadioButton)e.getSource();
            if (rb.isSelected()) {
                setDisableDecorationSelection(false);
            }
        });
        addDecorationButton.setOnAction(e -> {
            var items = availableDecorationsListView.getSelectionModel().getSelectedItems();
            selectedDecorationsListView.getItems().addAll(items);
        });
        removeDecorationButton.setOnAction(e -> {
            var items = selectedDecorationsListView.getSelectionModel().getSelectedItems();
            selectedDecorationsListView.getItems().removeAll(items);
        });
        addOrderButton.setOnAction(e -> {
            var item = new TableData();
            item.deadmanName.setValue(deadmanName.getText());
            item.deadmanSurname.setValue(deadmanSurname.getText());
            item.coffinType.setValue(premiumCoffinRadioButton.isSelected() ? "premium" : "standard");
            item.decorations.setValue(premiumCoffinRadioButton.isSelected() ? selectedDecorationsListView.getItems() : null);
            tableData.add(item);
            clearForm();
        });
        removeOrderButton.setOnAction(e -> {
            var selected = selectedSetsTableView.getSelectionModel().getSelectedItem();
            selectedSetsTableView.getItems().removeAll(selected);
        });
        availableDecorationsListView.setItems(FXCollections.observableArrayList("Dekoracja 1", "Dekoracja 2", "Dekoracja 3"));

        deadmanNameColumn.setCellValueFactory(new PropertyValueFactory<>("deadmanName"));
        deadmanSurnameColumn.setCellValueFactory(new PropertyValueFactory<>("deadmanSurname"));
        coffinTypeColumn.setCellValueFactory(new PropertyValueFactory<>("coffinType"));
        decorationsColumn.setCellValueFactory(new PropertyValueFactory<>("decorations"));
        tableData = FXCollections.observableArrayList();
        selectedSetsTableView.setItems(tableData);
    }

    /**
     * Disables or enables decoration selection views
     * @param value disables if set to false
     */
    private void setDisableDecorationSelection(boolean value) {
        availableDecorationsListView.setDisable(value);
        selectedDecorationsListView.setDisable(value);
        addDecorationButton.setDisable(value);
        removeDecorationButton.setDisable(value);
    }

    /**
     * Clears set configuration form (name and surname).
     */
    private void clearForm() {
        deadmanName.setText("");
        deadmanSurname.setText("");
    }

    private void saveData() {
        // form
        this.creatorData.setStandardCoffinSelected(standardCoffinRadioButton.isSelected());
        this.creatorData.setPremiumCoffinSelected(premiumCoffinRadioButton.isSelected());
        this.creatorData.setSelectedDecorations(selectedDecorationsListView.getItems());
        this.creatorData.setDeadmanName(deadmanName.getText());
        this.creatorData.setDeadmanSurname(deadmanSurname.getText());
        // table
        saveSelectedSets();
    }

    public void initData(CreatorData data) {
        super.initData(data);
        // restore data
        // form
        standardCoffinRadioButton.setSelected(this.creatorData.isStandardCoffinSelected());
        if (this.creatorData.isStandardCoffinSelected()) {
            setDisableDecorationSelection(true);
        }
        premiumCoffinRadioButton.setSelected(this.creatorData.isPremiumCoffinSelected());
        if (this.creatorData.isPremiumCoffinSelected()) {
            setDisableDecorationSelection(false);
        }
        selectedDecorationsListView.setItems(FXCollections.observableArrayList(this.creatorData.getSelectedDecorations()));
        deadmanName.setText(this.creatorData.getDeadmanName());
        deadmanSurname.setText(this.creatorData.getDeadmanSurname());
        // table
        loadSelectedSets();
    }

    public void saveSelectedSets() {
        for (var coffin : this.creatorData.getFuneral().getCoffins()) {
            this.creatorData.getFuneral().removeCoffin(coffin);
        }
        for (var entry : tableData) {
            Coffin coffin;
            if (entry.coffinType.getValue().equals(CoffinType.STANDARD.getName())) {
                coffin = new StandardCoffin();
                coffin.setPrice(300.0);
            }
            // premium type
            else {
                coffin = new PremiumCoffin();
                coffin.setPrice(1000.0);
                for (var decorationName : entry.decorations.getValue()) {
                    var decoration = new Decoration();
                    decoration.setName(decorationName);
                    decoration.setPrice(100.0);
                    ((PremiumCoffin)coffin).addDecoration(decoration);
                }
            }
            coffin.setDeadmanName(entry.deadmanName.getValue());
            coffin.setDeadmanSurname(entry.deadmanSurname.getValue());

            this.creatorData.getFuneral().addCoffin(coffin);
        }
    }

    public void loadSelectedSets() {
        for (var coffin : this.creatorData.getFuneral().getCoffins()) {
            var tableEntry = new FuneralCreator2.TableData();
            tableEntry.deadmanName.setValue(coffin.getDeadmanName());
            tableEntry.deadmanSurname.setValue(coffin.getDeadmanSurname());
            tableEntry.coffinType.setValue(coffin.getType());
            if (coffin instanceof PremiumCoffin) {
                var decorationList = ((PremiumCoffin) coffin).getDecorations();
                var resultList = new ArrayList<String>();
                for (var decoration : decorationList) {
                    resultList.add(decoration.getName());
                }
                tableEntry.decorations.setValue(FXCollections.observableArrayList(resultList));
            }
            tableData.add(tableEntry);
        }
    }
}
