package com.alve.alve0.controller;

public interface SimulationControlListener {

    /**
     * Chiamato quando il bottone Play/Pause viene cliccato.
     */
    void onPlayPauseClicked();

    /**
     * Chiamato quando il bottone Step viene cliccato.
     */
    void onStepClicked();

    /**
     * Chiamato quando il bottone Add Entity viene cliccato.
     */
    void onAddEntityClicked();

    /**
     * Chiamato quando il valore dello slider della velocità cambia.
     * @param newSpeedFactor Il nuovo valore dello slider (es. da 0.1 a 5.0).
     */
    void onSpeedChanged(double newSpeedFactor);

}
