package gui;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import main.Main;
import model.PremiumCoffin;
import org.hibernate.Session;

import javax.swing.*;
import java.io.IOException;

public class FuneralCreator4 extends FuneralCreatorBase {
    public class TableData {
        public SimpleStringProperty deadmanName = new SimpleStringProperty();
        public SimpleStringProperty deadmanSurname = new SimpleStringProperty();
        public SimpleStringProperty coffinType = new SimpleStringProperty();
        public SimpleStringProperty quarter = new SimpleStringProperty();
        public SimpleStringProperty price = new SimpleStringProperty();

        public String getDeadmanName() {
            return deadmanName.get();
        }

        public String getDeadmanSurname() {
            return deadmanSurname.get();
        }

        public String getCoffinType() {
            return coffinType.get();
        }

        public String getQuarter() {
            return quarter.get();
        }

        public String getPrice() {
            return price.get();
        }
    }
    @FXML
    private TableView<TableData> summaryTableView;

    private final ObservableList<TableData> tableData = FXCollections.observableArrayList();

    @FXML
    private Label totalText;

    @FXML
    private TableColumn<TableData, String> deadmanNameColumn;

    @FXML
    private TableColumn<TableData, String> deadmanSurnameColumn;

    @FXML
    private TableColumn<TableData, String> coffinTypeColumn;

    @FXML
    private TableColumn<TableData, String> quarterColumn;

    @FXML
    private TableColumn<TableData, String> priceColumn;

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

        acceptButton.setOnAction(e -> {
            // save funeral to db
            var worker = new SwingWorker<Void, Void>() {
                @Override
                protected Void doInBackground() throws Exception {
                    saveFuneral();
                    setProgress(1);
                    return null;
                }
            };
            worker.addPropertyChangeListener(evt -> {
                if ("progress".equals(evt.getPropertyName())) {
                    // handle end
                }
            });
            worker.execute();
            // go to splash
            try {
                var pane = (AnchorPane) FXMLLoader.load(getClass().getResource("/splash.fxml"));
                rootPane.getChildren().setAll(pane);
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        });
        cancelButton.setOnAction(e -> {
            // go to splash
            try {
                var pane = (AnchorPane) FXMLLoader.load(getClass().getResource("/splash.fxml"));
                rootPane.getChildren().setAll(pane);
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        });

        deadmanNameColumn.setCellValueFactory(new PropertyValueFactory<>("deadmanName"));
        deadmanSurnameColumn.setCellValueFactory(new PropertyValueFactory<>("deadmanSurname"));
        coffinTypeColumn.setCellValueFactory(new PropertyValueFactory<>("coffinType"));
        quarterColumn.setCellValueFactory(new PropertyValueFactory<>("quarter"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        summaryTableView.setItems(tableData);
    }

    public void initData(CreatorData data) {
        super.initData(data);

        Double total = 0.0;
        for (var coffin : this.creatorData.getFuneral().getCoffins()) {
            var tableEntry = new FuneralCreator4.TableData();
            tableEntry.deadmanName.setValue(coffin.getDeadmanName());
            tableEntry.deadmanSurname.setValue(coffin.getDeadmanSurname());
            tableEntry.coffinType.setValue(coffin.getType());
            tableEntry.quarter.setValue(coffin.getQuarter().toString());
            tableEntry.price.setValue(coffin.getTotalPrice().toString());
            total += coffin.getTotalPrice();
            tableData.add(tableEntry);
        }
        totalText.setText("Razem do zapłaty: " + total.toString()+"zł");
    }

    /**
     * Saves funeral from creatorData object to db.
     * Called from SwingWorker.
     */
    public void saveFuneral() {
        Session session = Main.sessionFactory.openSession();
        session.beginTransaction();
        session.saveOrUpdate(this.creatorData.getFuneral());
        for (var coffin : this.creatorData.getFuneral().getCoffins()) {
            session.update(coffin.getQuarter());
            if (coffin instanceof PremiumCoffin) {
                for(var decoration : ((PremiumCoffin) coffin).getDecorations()) {
                    session.save(decoration);
                }
            }
        }
        session.getTransaction().commit();
        session.close();
    }
}
