package com.alve.alve0.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import com.alve.alve0.controller.SimulationControlListener;

public class ControlPanelPane extends VBox {

    private Button playPauseButton;
    private Button stepButton;
    private Button addEntityButton;
    private Slider speedSlider;
    private Label speedLabel;

    private SimulationControlListener listener;
    private boolean isPlaying = false; // Stato interno per cambiare testo bottone

    public ControlPanelPane() {
        super(15); // Spaziatura
        setPadding(new Insets(15));
        setStyle("-fx-background-color: #F8F8F8;");
        setAlignment(Pos.CENTER); // Centra gli elementi verticalmente
        setMinWidth(200);

        Label title = new Label("Controls");
        title.setFont(Font.font("Arial", FontWeight.BOLD, 16));

        playPauseButton = new Button("Play");
        playPauseButton.setMinWidth(100);
        playPauseButton.setOnAction(e -> {
            isPlaying = !isPlaying;
            playPauseButton.setText(isPlaying ? "Pause" : "Play");
            if (listener != null) {
                listener.onPlayPauseClicked();
            } else {
                System.err.println("WARN: Play/Pause clicked but listener is null!"); // Aggiungi per debug
            }
        });

        stepButton = new Button("Step");
        stepButton.setMinWidth(100);
        stepButton.setOnAction(e -> {
            if(isPlaying) {
                isPlaying = false;
                playPauseButton.setText("Play");
                if (listener != null) listener.onStepClicked();
            } else {
                if (listener != null) listener.onStepClicked();
            }
        });

        addEntityButton = new Button("Add Entity");
        addEntityButton.setMinWidth(100);
        addEntityButton.setOnAction(e -> {
            if (listener != null) listener.onAddEntityClicked();
        });

        speedLabel = new Label("Simulation Speed:");
        speedSlider = new Slider(0.1, 5.0, 1.0); // Min, Max, Default speed factor
        speedSlider.setShowTickLabels(true);
        speedSlider.setShowTickMarks(true);
        speedSlider.setMajorTickUnit(1.0);
        speedSlider.valueProperty().addListener((obs, oldVal, newVal) -> {
            if (listener != null) listener.onSpeedChanged(newVal.doubleValue());
        });


        getChildren().addAll(title, playPauseButton, stepButton, addEntityButton, speedLabel, speedSlider);
    }

    // Metodo per collegare il pannello al suo listener (il Controller)
    public void setControlListener(SimulationControlListener listener) {
        this.listener = listener;
    }

    // Potrebbe servire per aggiornare lo stato del bottone Play/Pause dall'esterno
    public void updatePlayPauseButtonState(boolean isSimulationRunning) {
        this.isPlaying = isSimulationRunning;
        playPauseButton.setText(isPlaying ? "Pause" : "Play");
    }
}