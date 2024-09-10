package com.alav.autoclicker;

import java.awt.*;
import java.awt.event.InputEvent;

public class AutoClicker {
    private Robot robot;
    private boolean running = false;
    private boolean paused = false;
    private int clickType = InputEvent.BUTTON1_DOWN_MASK;  // Por defecto, clic izquierdo

    public AutoClicker() {
        try {
            robot = new Robot();
        } catch (AWTException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    // Configura el tipo de clic según la selección del usuario
    public void setClickType(String type) {
        switch (type) {
            case "Left Click":
                clickType = InputEvent.BUTTON1_DOWN_MASK;  // Clic izquierdo
                break;
            case "Right Click":
                clickType = InputEvent.BUTTON3_DOWN_MASK;  // Clic derecho
                break;
            case "Both":
                clickType = InputEvent.BUTTON1_DOWN_MASK | InputEvent.BUTTON3_DOWN_MASK;  // Ambos clics
                break;
        }
    }

    // Inicia los clics en un hilo separado
    public void startClicking(int delay, Runnable onClick) {
        running = true;
        paused = false;

        new Thread(() -> {
            while (running) {
                if (!paused) {
                    robot.mousePress(clickType);
                    robot.delay(50);
                    robot.mouseRelease(clickType);
                    robot.delay(delay);
                    onClick.run();  // Actualizar el contador después de cada clic
                }
            }
        }).start();
    }

    public void pauseClicking() {
        paused = true;
    }

    public void resumeClicking() {
        paused = false;
    }

    public void stopClicking() {
        running = false;
    }
}

