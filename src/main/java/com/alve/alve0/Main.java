package com.alve.alve0;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;

import com.alve.alve0.view.SimulationView;
import com.alve.alve0.controller.SimulationController;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) {
        SimulationView view = new SimulationView();
        SimulationController controller = new SimulationController(view);

        BorderPane root = new BorderPane();
        root.setCenter(view.getCanvas());
        Scene scene = new Scene(root, 800, 600);

        primaryStage.setTitle("ALVE - Artificial Life Virtual Ecosystem");
        primaryStage.setScene(scene);
        primaryStage.show();

        controller.startSimulation();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
