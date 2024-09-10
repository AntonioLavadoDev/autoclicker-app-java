package com.alav.autoclicker;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Main extends Application {

    private AutoClicker autoClicker = new AutoClicker();  // Instancia del autoclicker
    private boolean running = false;

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Java Autoclicker");

        // Crear los componentes de la interfaz
        Label delayLabel = new Label("Delay between clicks (ms):");
        TextField delayField = new TextField("1000"); // Campo para ajustar el intervalo de tiempo

        Button startButton = new Button("Start");
        Button stopButton = new Button("Stop");

        // Acción al presionar el botón Start
        startButton.setOnAction(e -> {
            if (!running) {
                int delay = Integer.parseInt(delayField.getText());
                autoClicker.startClicking(delay);  // Comienza los clics
                running = true;
            }
        });

        // Acción al presionar el botón Stop
        stopButton.setOnAction(e -> {
            if (running) {
                autoClicker.stopClicking();  // Detiene los clics
                running = false;
            }
        });

        // Layout de la interfaz
        VBox layout = new VBox(10);
        layout.getChildren().addAll(delayLabel, delayField, startButton, stopButton);

        // Configuración de la escena
        Scene scene = new Scene(layout, 300, 200);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

