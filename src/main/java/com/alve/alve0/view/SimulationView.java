package com.alve.alve0.view;

import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

public class SimulationView {

    private BorderPane rootPane;
    private WorldCanvas worldCanvas;
    private EntityInfoPane entityInfoPane;
    private ControlPanelPane controlPanelPane;

    public SimulationView(double worldWidth, double worldHeight) {
        rootPane = new BorderPane();

        // Crea le componenti della vista
        worldCanvas = new WorldCanvas(worldWidth, worldHeight);
        entityInfoPane = new EntityInfoPane();
        controlPanelPane = new ControlPanelPane();

        // Assembla le componenti nel layout principale
        rootPane.setCenter(worldCanvas); // Canvas al centro

        // Metti i pannelli di controllo e info in un VBox a destra
        VBox rightPanel = new VBox(10); // Spaziatura tra i due pannelli
        rightPanel.getChildren().addAll(controlPanelPane, entityInfoPane);
        rootPane.setRight(rightPanel);

        // Puoi aggiungere altri elementi come menu, barre di stato, ecc.
        // rootPane.setTop(createMenuBar());
        // rootPane.setBottom(createStatusBar());
    }

    // Metodo per ottenere il nodo radice della vista da mettere nella Scene
    public Parent getRoot() {
        return rootPane;
    }

    // Metodi per accedere alle sotto-viste (utili per il Controller)
    public WorldCanvas getWorldCanvas() {
        return worldCanvas;
    }

    public EntityInfoPane getEntityInfoPane() {
        return entityInfoPane;
    }

    public ControlPanelPane getControlPanelPane() {
        return controlPanelPane;
    }
}