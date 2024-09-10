package com.alav.autoclicker;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Main extends Application {

    private AutoClicker autoClicker = new AutoClicker();  // Instancia del autoclicker
    private boolean running = false;  // Estado del autoclicker
    private boolean paused = false;   // Indica si está en pausa o no
    private Label statusLabel;        // Indicador de estado
    private Label clickCounterLabel;  // Contador de clics
    private int clickCounter = 0;     // Variable para contar los clics
    private int maxClicks = Integer.MAX_VALUE;  // Límite máximo de clics (sin límite por defecto)

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Java Autoclicker");

        // Crear los componentes de la interfaz
        Label delayLabel = new Label("Clicks per second:");
        TextField clicksPerSecondField = new TextField("1"); // Cantidad de clics por segundo
        clickCounterLabel = new Label("Click Count: 0"); // Contador de clics

        // Selector de tipo de clic
        Label clickTypeLabel = new Label("Select Click Type:");
        ChoiceBox<String> clickTypeBox = new ChoiceBox<>();
        clickTypeBox.getItems().addAll("Left Click", "Right Click", "Both");
        clickTypeBox.setValue("Left Click"); // Valor por defecto

        // Campo para el límite máximo de clics
        Label maxClicksLabel = new Label("Max Clicks (0 = No limit):");
        TextField maxClicksField = new TextField("0"); // Límite máximo de clics (0 = sin límite)

        Button startPauseButton = new Button("Start"); // Botón de inicio/pausa
        Button stopButton = new Button("Stop");

        // Indicador de estado
        statusLabel = new Label("Status: Stopped");

        // Acción al presionar el botón Start/Pause
        startPauseButton.setOnAction(e -> {
            if (!running) {
                try {
                    int clicksPerSecond = Integer.parseInt(clicksPerSecondField.getText());

                    // Validar que el número de clics por segundo sea mayor a 0
                    if (clicksPerSecond <= 0) {
                        statusLabel.setText("Status: Invalid CPS! Must be > 0");
                        return;
                    }

                    // Obtener y validar el límite máximo de clics
                    int inputMaxClicks = Integer.parseInt(maxClicksField.getText());
                    if (inputMaxClicks < 0) {
                        statusLabel.setText("Status: Invalid Max Clicks! Must be >= 0");
                        return;
                    }
                    maxClicks = inputMaxClicks == 0 ? Integer.MAX_VALUE : inputMaxClicks;  // Sin límite si es 0

                    // Obtenemos el tipo de clic seleccionado
                    String clickType = clickTypeBox.getValue();

                    // Configuramos el autoclicker para usar el tipo de clic correcto
                    autoClicker.setClickType(clickType);

                    // Reiniciar contador de clics
                    clickCounter = 0;
                    clickCounterLabel.setText("Click Count: 0");

                    int delay = 1000 / clicksPerSecond;  // Convertimos clics por segundo a milisegundos por clic
                    autoClicker.startClicking(delay, this::updateClickCounter); // Actualizamos el contador después de cada clic
                    startPauseButton.setText("Pause");
                    statusLabel.setText("Status: Running");
                    running = true;
                    paused = false;
                } catch (NumberFormatException ex) {
                    // Si el valor ingresado no es un número válido
                    statusLabel.setText("Status: Invalid input! Must be a number.");
                }
            } else if (!paused) {
                autoClicker.pauseClicking(); // Método para pausar el autoclicker
                startPauseButton.setText("Resume");
                statusLabel.setText("Status: Paused");
                paused = true;
            } else {
                autoClicker.resumeClicking(); // Método para reanudar el autoclicker
                startPauseButton.setText("Pause");
                statusLabel.setText("Status: Running");
                paused = false;
            }
        });

        // Acción al presionar el botón Stop
        stopButton.setOnAction(e -> {
            if (running) {
                autoClicker.stopClicking();
                startPauseButton.setText("Start");
                statusLabel.setText("Status: Stopped");
                running = false;
                paused = false;
            }
        });

        // Layout de la interfaz
        VBox layout = new VBox(10);
        layout.getChildren().addAll(delayLabel, clicksPerSecondField, clickTypeLabel, clickTypeBox, maxClicksLabel, maxClicksField, startPauseButton, stopButton, statusLabel, clickCounterLabel);

        // Configuración de la escena
        Scene scene = new Scene(layout, 300, 300);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    // Método para actualizar el contador de clics y detener el autoclicker si se alcanza el límite
    private void updateClickCounter() {
        Platform.runLater(() -> {
            clickCounter++;
            clickCounterLabel.setText("Click Count: " + clickCounter);

            // Detener el autoclicker si se alcanza el límite máximo de clics
            if (clickCounter >= maxClicks) {
                autoClicker.stopClicking();
                statusLabel.setText("Status: Max clicks reached!");
                running = false;
            }
        });
    }

    public static void main(String[] args) {
        launch(args);
    }
}

