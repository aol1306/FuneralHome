package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import javax.swing.*;
import java.io.IOException;

public class Main extends Application {
    public static StandardServiceRegistry registry = null;
    public static SessionFactory sessionFactory = null;

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        var l = new Text("Wait for database initialization...");
        var b = new Button("Plan new funeral");
        b.setOnAction(e -> {
                    try {
                        var fxmlLoader = new FXMLLoader();
                        fxmlLoader.setLocation(getClass().getResource("/funeralcreator1.fxml"));
                        primaryStage.setScene(new Scene(fxmlLoader.load(), 600, 400));
                        primaryStage.setMinHeight(300);
                        primaryStage.setMinWidth(300);
                        primaryStage.show();
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                }
        );
        b.setDisable(true);
        var scene = new Scene(new VBox(l, b), 640, 480);
        primaryStage.setScene(scene);

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
                l.setText("Completed");
                b.setDisable(false);
            }
        });
        worker.execute();

        primaryStage.show();
    }

    public void initDb() {
        registry = new StandardServiceRegistryBuilder().configure("hibernate.cfg.xml").build();
        var metadata = new MetadataSources(registry).getMetadataBuilder().build();
        sessionFactory = metadata.getSessionFactoryBuilder().build();
        System.out.println("DB init completed");
    }
}
