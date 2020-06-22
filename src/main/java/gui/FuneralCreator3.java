package gui;

import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.util.Callback;
import main.Helper;
import main.Main;
import model.Cemetery;
import model.Coffin;
import model.Employee;
import model.Quarter;
import org.hibernate.Session;

import javax.swing.*;
import java.util.List;

public class FuneralCreator3 extends FuneralCreatorBase {
    public class TableData {
        public Coffin coffinObject;
        public SimpleStringProperty deadmanName = new SimpleStringProperty();
        public SimpleStringProperty deadmanSurname = new SimpleStringProperty();
        public SimpleStringProperty coffinType = new SimpleStringProperty();
        public SimpleObjectProperty<Quarter> quarter = new SimpleObjectProperty<>();

        public String getDeadmanName() {
            return deadmanName.get();
        }

        public String getDeadmanSurname() {
            return deadmanSurname.get();
        }

        public String getCoffinType() {
            return coffinType.get();
        }

        public Quarter getQuarter() {
            return quarter.get();
        }

        public Coffin getCoffinObject() {
            return coffinObject;
        }

        public void setCoffinObject(Coffin coffinObject) {
            this.coffinObject = coffinObject;
        }
    }

    @FXML
    private Button backButton;

    @FXML
    private Button nextButton;

    @FXML
    private ImageView loadingImageView;

    @FXML
    private ComboBox<Cemetery> cemeteryComboBox;

    private final ObservableList<TableData> tableData = FXCollections.observableArrayList();

    @FXML
    private TableView<TableData> matchCoffinTableView;

    @FXML
    private TableColumn<TableData, String> deadmanNameColumn;

    @FXML
    private TableColumn<TableData, String> deadmanSurnameColumn;

    @FXML
    private TableColumn<TableData, String> coffinTypeColumn;

    @FXML
    private TableColumn<TableData, Quarter> quartersColumn;

    private ObservableList<Cemetery> cemeteryComboBoxList;

    private ObservableList<Quarter> quarterList;

    private List<Cemetery> cemeteryList;

    Session session = Main.sessionFactory.openSession();

    public void initialize() {
        loadCemeteries();
        backButton.setOnAction(e -> {
            saveData();
            setView("/funeralcreator2.fxml");
        });
        nextButton.setOnAction(e -> {
            saveData();
            setView("/funeralcreator4.fxml");
        });
        quarterList = FXCollections.observableArrayList();
        cemeteryComboBoxList = FXCollections.observableArrayList();
        cemeteryComboBox.setItems(cemeteryComboBoxList);
        cemeteryComboBox.setOnAction(e -> {
            reloadTable();
        });
    }

    public void initData(CreatorData data) {
        super.initData(data);

        deadmanNameColumn.setCellValueFactory(new PropertyValueFactory<>("deadmanName"));
        deadmanSurnameColumn.setCellValueFactory(new PropertyValueFactory<>("deadmanSurname"));
        coffinTypeColumn.setCellValueFactory(new PropertyValueFactory<>("coffinType"));
        quartersColumn.setCellFactory(ComboBoxTableCell.forTableColumn(quarterList));
        quartersColumn.setCellValueFactory(param -> {
            TableData td = param.getValue();
            var selectedCemetery = cemeteryComboBox.getSelectionModel().getSelectedItem();
            if (selectedCemetery == null) {
                return new SimpleObjectProperty<>(null);
            } else {
                return new SimpleObjectProperty<>(td.getQuarter());
            }
        });
        quartersColumn.setOnEditCommit(e -> {
            var index = this.creatorData.getFuneral().getCoffins().indexOf(e.getRowValue().getCoffinObject());
            this.creatorData.getFuneral().getCoffins().get(index).setQuarter(e.getNewValue());
        });
        matchCoffinTableView.setItems(tableData);

        reloadTable();
    }

    private void loadCemeteries() {
        var worker = new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() throws Exception {
                session.beginTransaction();
                cemeteryList = Helper.selectAll(Cemetery.class, session);
                session.getTransaction().commit();
                session.close();

                setProgress(1);
                return null;
            }
        };
        worker.addPropertyChangeListener(evt -> {
            if("progress".equals(evt.getPropertyName())) {
                loadingImageView.setVisible(false);
                cemeteryComboBoxList.addAll(cemeteryList);
                // load last selected cemetery
                Platform.runLater(() -> {
                    cemeteryComboBox.getSelectionModel().select(creatorData.getSelectedCemetery());
                    // reload table after cemetery change
                    reloadTable();
                });
            }
        });
        worker.execute();
    }

    private void saveData() {
        this.creatorData.setSelectedCemetery(cemeteryComboBox.getValue());
    }

    private void reloadTable() {
        tableData.clear();

        // load quarters from selected cemetery using association
        var selectedCemetery = cemeteryComboBox.getSelectionModel().getSelectedItem();
        ObservableList<Quarter> quarters = null;
        quarterList.clear();
        if (selectedCemetery != null) {
            var quartersDb = selectedCemetery.getQuarters();
            quarters = FXCollections.observableArrayList(quartersDb);
            quarterList.addAll(quarters);
        }

        for (var coffin : this.creatorData.getFuneral().getCoffins()) {
            var tableEntry = new FuneralCreator3.TableData();
            tableEntry.setCoffinObject(coffin);
            tableEntry.deadmanName.setValue(coffin.getDeadmanName());
            tableEntry.deadmanSurname.setValue(coffin.getDeadmanSurname());
            tableEntry.coffinType.setValue(coffin.getType());
            tableData.add(tableEntry);
        }

        matchCoffinTableView.refresh();
    }
}