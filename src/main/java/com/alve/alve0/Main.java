package com.alve.alve0;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;

import com.alve.alve0.view.SimulationView;
import com.alve.alve0.controller.SimulationController;
import com.alve.alve0.model.World;
import com.alve.alve0.controller.SimulationEngine;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        World world = new World();
        SimulationEngine engine = new SimulationEngine(world);

        SimulationView mainView = new SimulationView(world.getWidth(), world.getHeight());
        SimulationController controller = new SimulationController(engine, world, mainView);
        mainView.getControlPanelPane().setControlListener(controller);
        mainView.getWorldCanvas().setOnCanvasMouseClicked(controller::handleCanvasClick);

        // Imposto la scena principale
        Scene scene = new Scene(mainView.getRoot());
        primaryStage.setTitle("ALVE Simulation");
        primaryStage.setScene(scene);
        primaryStage.show();

        // Avvia il ciclo di animazione
        controller.startAnimationLoop();
    }
}