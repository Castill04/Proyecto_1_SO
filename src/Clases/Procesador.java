/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Clases;

import java.util.concurrent.atomic.AtomicInteger;

/**
 *
 * @author casti
 */
public class Procesador extends Thread {
    private static int idCounter = 0;
    private final int id;
    private Proceso currentProcess;
    private boolean isBusy;
    private Simulador simulator;

    public Procesador(Simulador simulator) {
        this.id = ++idCounter;
        this.currentProcess = null;
        this.isBusy = false;
        this.simulator = simulator;
    }

    @Override
    public void run() {
        while (true) {
            try {
                simulator.getSemaphore().acquire();

                if (currentProcess != null) {
                    // Se ejecuta el proceso actual
                    currentProcess.executeInstruction(simulator.getGlobalClock());
                    if (currentProcess.isCompleted()) {
                        // Si el proceso se completa se toman las medidas adecuadas
                        currentProcess = null;
                        isBusy = false;
                    }
                }

                simulator.getSemaphore().release();

                // Sleep por un ciclo
                Thread.sleep(simulator.getConfiguration().getCycleDuration());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void executeCycle(long globalClock) {
        if (currentProcess != null) {
            // Se ejecuta el proceso actual
            currentProcess.executeInstruction(globalClock);
            if (currentProcess.isCompleted()) {
                // Si el proceso se completa se toman las medidas adecuadas
                currentProcess = null;
                isBusy = false;
            }
        }
    }
    
    public void assignProcess(Proceso process) {
        this.currentProcess = process;
        this.isBusy = true;
    }

    public boolean isBusy() {
        return isBusy;
    }

    //public int getId() {
    //    return id;
    //}
}