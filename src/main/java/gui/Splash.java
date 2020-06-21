package gui;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import main.Main;
import model.Cemetery;
import model.Quarter;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import javax.swing.*;

public class Splash extends ControllerBase {
    @FXML
    private Button planFuneralButton;

    @FXML
    private Button addMockDataButton;

    @FXML
    private Text text;

    public void initialize() {
        planFuneralButton.setOnAction(e -> {
            setView("/funeralcreator1.fxml");
        });
        addMockDataButton.setOnAction(e -> {
            text.setText("Dodaję dane do db...");
            setDisableButtons(true);
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
                    setDisableButtons(false);
                }
            });
            worker.execute();
        });
        setDisableButtons(true);

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
                setDisableButtons(false);
            }
        });
        worker.execute();
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

        session.save(c1);
        session.save(c2);
        session.save(c3);
        session.getTransaction().commit();
        session.close();
        System.out.println("completed");
    }

    private void setDisableButtons(boolean value) {
        planFuneralButton.setDisable(value);
        addMockDataButton.setDisable(value);
    }
}
