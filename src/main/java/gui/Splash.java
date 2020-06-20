package gui;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import main.Main;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import javax.swing.*;

public class Splash extends ControllerBase {
    @FXML
    private Button planFuneralButton;

    @FXML
    private Text text;

    public void initialize() {
        planFuneralButton.setOnAction(e -> {
            setView("/funeralcreator1.fxml");
        });
        planFuneralButton.setDisable(true);

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
                planFuneralButton.setDisable(false);
            }
        });
        worker.execute();
    }

    private void initDb() {
        Main.registry = new StandardServiceRegistryBuilder().configure("hibernate.cfg.xml").build();
        var metadata = new MetadataSources(Main.registry).getMetadataBuilder().build();
        Main.sessionFactory = metadata.getSessionFactoryBuilder().build();
        System.out.println("DB init completed");
    }
}
