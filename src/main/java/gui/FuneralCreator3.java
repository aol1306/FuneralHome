package gui;

import javafx.application.Platform;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import main.Helper;
import main.Main;
import model.Cemetery;
import model.Employee;
import model.Quarter;
import org.hibernate.Session;

import javax.swing.*;
import java.util.List;

public class FuneralCreator3 extends FuneralCreatorBase {
    public class TableData {
        public SimpleStringProperty deadmanName = new SimpleStringProperty();
        public SimpleStringProperty deadmanSurname = new SimpleStringProperty();
        public SimpleStringProperty coffinType = new SimpleStringProperty();
        public SimpleListProperty<Quarter> quarters = new SimpleListProperty<>();

        public String getDeadmanName() {
            return deadmanName.get();
        }

        public String getDeadmanSurname() {
            return deadmanSurname.get();
        }

        public String getCoffinType() {
            return coffinType.get();
        }

        public ObservableList<Quarter> getQuarters() {
            return quarters.get();
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

    private ObservableList<TableData> tableData;

    @FXML
    private TableView<TableData> matchCoffinTableView;

    @FXML
    private TableColumn<TableData, String> deadmanNameColumn;

    @FXML
    private TableColumn<TableData, String> deadmanSurnameColumn;

    @FXML
    private TableColumn<TableData, String> coffinTypeColumn;

    @FXML
    private TableColumn<TableData, List<Quarter>> quartersColumn;

    private ObservableList<Cemetery> cemeteryComboBoxList;

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
        quartersColumn.setCellValueFactory(new PropertyValueFactory<>("quarters"));
        tableData = FXCollections.observableArrayList();
        matchCoffinTableView.setItems(tableData);

        reloadTable();
    }

    private void loadCemeteries() {
        var worker = new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() throws Exception {
                session.beginTransaction();
                cemeteryList = Helper.selectAll(Cemetery.class, session);
                var ffffcemeteryList = Helper.selectAll(Cemetery.class, session);
                var ffffdeleteme = Helper.selectAll(Employee.class, session);
                var ffffquarter = Helper.selectAll(Quarter.class, session);
                session.getTransaction().commit();
//                session.close();

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
        if (selectedCemetery != null) {
            var quartersDb = selectedCemetery.getQuarters();
            quarters = FXCollections.observableArrayList(quartersDb);
        }

        for (var coffin : this.creatorData.getFuneral().getCoffins()) {
            var tableEntry = new FuneralCreator3.TableData();
            tableEntry.deadmanName.setValue(coffin.getDeadmanName());
            tableEntry.deadmanSurname.setValue(coffin.getDeadmanSurname());
            tableEntry.coffinType.setValue(coffin.getType());
            if (quarters != null) {
                tableEntry.quarters.setValue(quarters);
            }
            tableData.add(tableEntry);
        }
    }
}