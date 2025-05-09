package com.alve.alve0.view;

import com.alve.alve0.model.Entity;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class EntityInfoPane extends VBox {

    private Label titleLabel;
    private Label idLabel;
    private Label positionLabel;
    private Label energyLabel; // Esempio parametro
    private Label dnaLabel;    // Esempio parametro

    public EntityInfoPane() {
        super(10); // Spaziatura verticale tra elementi
        setPadding(new Insets(15));
        setStyle("-fx-background-color: #EEEEEE;");
        setMinWidth(200);

        titleLabel = new Label("Entity Information");
        titleLabel.setFont(Font.font("Arial", FontWeight.BOLD, 16));

        idLabel = new Label("ID: ---");
        positionLabel = new Label("Position: ---");
        energyLabel = new Label("Energy: ---");
        dnaLabel = new Label("DNA: ---");
        dnaLabel.setWrapText(true); // Per DNA lunghi

        getChildren().addAll(titleLabel, idLabel, positionLabel, energyLabel, dnaLabel);
    }

    public void displayEntityInfo(Entity entity) {
        if (entity != null && entity.isAlive()) {
            idLabel.setText("ID: " + entity.getId());
            positionLabel.setText(String.format("Position: (%.1f, %.1f)", entity.getX(), entity.getY()));
            energyLabel.setText("Energy: " + entity.getEnergy());
        } else {
            idLabel.setText("ID: ---");
            positionLabel.setText("Position: ---");
            energyLabel.setText("Energy: ---");
            dnaLabel.setText("DNA: ---");
        }
    }
}