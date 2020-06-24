package gui;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.text.Text;
import main.Helper;
import main.Main;
import model.*;
import org.hibernate.Session;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import javax.swing.*;

public class Splash extends ControllerBase {
    @FXML
    private Button addTestDataButton;

    @FXML
    private ListView<Cemetery> cemeteryListView;

    @FXML
    private ListView<Quarter> quarterListView;

    @FXML
    private HBox listHBox;

    @FXML
    private Button planFuneralButton;

    @FXML
    private Text text;

    @FXML
    private ImageView loadingImageView;

    Session session;

    ObservableList<Cemetery> cemeteries = FXCollections.observableArrayList();
    ObservableList<Quarter> quarters = FXCollections.observableArrayList();

    public void initialize() {
        HBox.setHgrow(cemeteryListView, Priority.ALWAYS);
        HBox.setHgrow(quarterListView, Priority.ALWAYS);
        cemeteryListView.setItems(cemeteries);
        quarterListView.setItems(quarters);
        setIsBusyNow(false);
        planFuneralButton.setOnAction(e -> {
            if (verifyStartConditions()) {
                setView("/funeralcreator1.fxml");
            } else {
                showErrorAlert("Brak zasobów w systemie", "Zaplanowanie pogrzebu nie jest teraz możliwe.");
            }
        });
        addTestDataButton.setOnAction(e -> {
            text.setText("Dodaję dane do db...");
            setIsBusyNow(true);
            var worker = new SwingWorker<Void, Void>() {
                @Override
                protected Void doInBackground() throws Exception {
                    addMockData();
                    setProgress(1);
                    return null;
                }
            };
            worker.addPropertyChangeListener(evt -> {
                if ("progress".equals(evt.getPropertyName())) {
                    text.setText("Gotowe!");
                    setIsBusyNow(false);
                    initListView();
                }
            });
            worker.execute();
        });

        // db init
        var worker = new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() throws Exception {
                initDb();
                setProgress(1);
                return null;
            }
        };
        worker.addPropertyChangeListener(evt -> {
            if ("progress".equals(evt.getPropertyName())) {
                text.setText("Gotowe!");
                setIsBusyNow(false);
                initListView();
            }
        });
        if (Main.registry == null) {
            setIsBusyNow(true);
            worker.execute();
        }

        initListView();
    }

    private void initListView() {
        if (Main.registry == null) return;
        if (session == null) {
            session = Main.sessionFactory.openSession();
        }
        Platform.runLater(() -> {
            loadingImageView.setVisible(true);
            session.beginTransaction();
            cemeteries.addAll(Helper.selectAll(Cemetery.class, session));
            session.getTransaction().commit();

            cemeteryListView.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue) -> {
                quarterListView.getItems().clear();
                quarterListView.getItems().addAll(newValue.getQuarters());
            });
            loadingImageView.setVisible(false);
        });
    }

    /**
     * Initialize DB. Only call from SwingWorker.
     */
    private void initDb() {
        Main.registry = new StandardServiceRegistryBuilder().configure("hibernate.cfg.xml").build();
        var metadata = new MetadataSources(Main.registry).getMetadataBuilder().build();
        Main.sessionFactory = metadata.getSessionFactoryBuilder().build();
        System.out.println("DB init completed");
    }

    private void addMockData() throws Exception {
        var session = Main.sessionFactory.openSession();
        session.beginTransaction();

        var c1 = new Cemetery("ul. Wolska 180/182", 40.0);
        Quarter.createQuarter(c1, "A01");
        Quarter.createQuarter(c1, "A02");
        Quarter.createQuarter(c1, "A03");
        Quarter.createQuarter(c1, "A04");
        Quarter.createQuarter(c1, "B01");
        Quarter.createQuarter(c1, "B02");
        Quarter.createQuarter(c1, "B03");

        var c2 = new Cemetery("ul. Przyczółkowa", 14.1);
        Quarter.createQuarter(c2, "314");
        Quarter.createQuarter(c2, "315");
        Quarter.createQuarter(c2, "316");
        Quarter.createQuarter(c2, "317");
        Quarter.createQuarter(c2, "318");
        Quarter.createQuarter(c2, "319");

        var c3 = new Cemetery("ul. Wałbrzyska", 2.0);
        Quarter.createQuarter(c3, "M 31");
        Quarter.createQuarter(c3, "M 32");

        var e1 = new Employee();
        e1.addWorkDay(new WorkDay());
        e1.addWorkDay(new WorkDay());
        session.save(e1);

        var d1 = new Driver();
        d1.setCaravan(new Caravan());
        session.save(d1);

        session.save(c1);
        session.save(c2);
        session.save(c3);
        session.getTransaction().commit();
        session.close();
        System.out.println("completed");
    }

    private void setIsBusyNow(boolean value) {
        planFuneralButton.setDisable(value);
        addTestDataButton.setDisable(value);
        loadingImageView.setVisible(value);
    }

    /**
     * Check if there is an employee, caravan and a quarter
     * @return
     */
    boolean verifyStartConditions() {
        if (Main.registry == null) return false;
        setIsBusyNow(true);
        var session = Main.sessionFactory.openSession();
        session.beginTransaction();
        var ret = true;
        if (ret && (Long)session.createQuery("select count(*) from Employee").uniqueResult() == 0) ret = false;
        if (ret && (Long)session.createQuery("select count(*) from Caravan").uniqueResult() == 0) ret = false;
        if (ret && (Long)session.createQuery("select count(*) from Quarter").uniqueResult() == 0) ret = false;
        session.getTransaction().commit();
        session.close();
        setIsBusyNow(false);
        return ret;
    }

    private void showErrorAlert(String header, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Uwaga");
        alert.setHeaderText(header);
        alert.setContentText(message);
        alert.show();
    }
}
