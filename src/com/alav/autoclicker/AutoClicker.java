package com.alav.autoclicker;

import java.awt.*;
import java.awt.event.InputEvent;

public class AutoClicker {
    Robot robot; //Genera eventos de entrada nativos del SO para controlar el ratón
    private boolean running = false;


    public AutoClicker(){
        try {
            robot = new Robot();
        } catch (AWTException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    //Inicia el proceso del autoclicker
    public void startClicking(int delay){
    running = true;
    while (running){
        robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
        robot.delay(50); //Duración del click
        robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
        robot.delay(delay); //Tiempo entre clicks
    }
    }
    //Permite detener el autoclicker.
    public void stopClicking() {
        running = false;
    }

    public static void main(String[] args) {
        AutoClicker clicker = new AutoClicker();
        clicker.startClicking(1000); // 1000ms = 1 segundo entre clicks
    }




}
