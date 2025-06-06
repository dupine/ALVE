package com.alve.alve0.view;

import com.alve.alve0.model.Entity;
import com.alve.alve0.model.StaticGenome;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class EntityInfoPane extends VBox {

    private Label titleLabel;
    private Label idLabel;
    private Label positionLabel;
    private Label energyLabel;
    private Label dnaLabel; //attualmente non mi serve, pensavo di usarlo per mostrare il "DNA" come una stringa di nucleotidi
    private GridPane genomeGrid;

    public EntityInfoPane() {
        super(10); // Spaziatura verticale tra elementi
        setPadding(new Insets(15));
        setStyle("-fx-background-color: #EEEEEE;");
        setMinWidth(200);

        genomeGrid = new GridPane();
        genomeGrid.setHgap(10);
        genomeGrid.setVgap(5);
        genomeGrid.setPadding(new Insets(10));

        titleLabel = new Label("Entity Information");
        titleLabel.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        idLabel = new Label("ID: ---");
        positionLabel = new Label("Position: ---");
        energyLabel = new Label("Energy: ---");
        getChildren().addAll(titleLabel, idLabel, positionLabel, energyLabel, genomeGrid);
    }

    public void displayEntityInfo(Entity entity) {
        genomeGrid.getChildren().clear(); // reset

        if (entity != null && entity.isAlive()) {
            idLabel.setText("ID: " + entity.getId());
            positionLabel.setText(String.format("Position: (%.1f, %.1f)", entity.getX(), entity.getY()));
            energyLabel.setText("Energy: " + entity.getEnergy());
            //dnaLabel.setText("DNA: " + entity.getGenome().toString());

            StaticGenome g = entity.getGenome();
            addGenomeRow(0, "Energy", g.getEnergy());
            addGenomeRow(1, "Vision Range", g.getVisionRange());
            addGenomeRow(2, "Speed", g.getSpeed());
            addGenomeRow(3, "Size", g.getSize());
            addGenomeRow(4, "Metabolism", g.getMetabolism());
            addGenomeRow(5, "Red", g.getRed());
            addGenomeRow(6, "Green", g.getGreen());
            addGenomeRow(7, "Blue", g.getBlue());
        } else {
            idLabel.setText("ID: ---");
            positionLabel.setText("Position: ---");
            energyLabel.setText("Energy: ---");
            genomeGrid.getChildren().clear(); // vuota anche questa
        }
    }

    private void addGenomeRow(int row, String name, float value) {
        Label nameLabel = new Label(name + ":");
        nameLabel.setStyle("-fx-font-weight: bold;");
        Label valueLabel = new Label(String.format("%.2f", value));
        genomeGrid.add(nameLabel, 0, row);
        genomeGrid.add(valueLabel, 1, row);
    }
}