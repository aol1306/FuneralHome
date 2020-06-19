package main;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

public class Main extends Application {
    public static void main(String[] args) {
        System.out.println("Start");

        // load hibernate
        StandardServiceRegistry registry = null;
        SessionFactory sessionFactory = null;
        registry = new StandardServiceRegistryBuilder().configure("hibernate.cfg.xml").build();
        var metadata = new MetadataSources(registry).getMetadataBuilder().build();
        sessionFactory = metadata.getSessionFactoryBuilder().build();
        System.out.println(sessionFactory);

        launch();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        var l = new Label("Hello");
        var scene = new Scene(new StackPane(l), 640, 480);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
