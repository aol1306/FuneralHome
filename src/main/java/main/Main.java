package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistry;

public class Main extends Application {
    public static StandardServiceRegistry registry = null;
    public static SessionFactory sessionFactory = null;

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        var pane = FXMLLoader.load(getClass().getResource("/splash.fxml"));
        primaryStage.setScene(new Scene((Parent) pane));
        primaryStage.show();
    }
}
