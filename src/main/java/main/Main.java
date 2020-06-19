package main;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class Main extends Application {
    public static void main(String[] args) {
        System.out.println("Start");
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
