/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Clases;

/**
 *
 * @author casti
 */
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;

public class Simulador {

    private Lista<Procesador> processors;
    private Cola<Proceso> readyQueue;
    private Cola<Proceso> blockedQueue;
    private long globalClock;
    private Configuracion configuration;
    private boolean isRunning;
    private Semaphore semaphore;

    public Simulador(Configuracion configuration) {
        this.configuration = configuration;
        this.processors = configuration.getProcessors();
        this.readyQueue = new Cola<>();
        this.blockedQueue = new Cola<>();
        this.globalClock = 0;
        this.isRunning = false;
        this.semaphore = new Semaphore(1);
    }

    public void startSimulation() {
        isRunning = true;
        while (isRunning) {
            try {
                semaphore.acquire();

                // Aumentar el reloj global
                globalClock++;

                // Actualizar los procesadores y las colas
                updateProcessors();
                updateQueues();

                semaphore.release();

                // Hacer sleep por un ciclo
                Thread.sleep(configuration.getCycleDuration());

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void pauseSimulation() {
        isRunning = false;
    }

    public void updateConfiguration(Configuracion config) {
        this.configuration = config;
        this.processors = config.getProcessors();
    }

    // public void loadConfiguration(String filePath) {
    //    Configuracion config = Configuracion.loadFromFile(filePath);
    //    if (config != null) {
    //        updateConfiguration(config);
    //    }
    //}

    //public void saveConfiguration(String filePath) {
    //    configuration.saveToFile(filePath);
    //}

    private void updateProcessors() {
        for (int i = 0; i < processors.size(); i++) {
            processors.get(i).executeCycle(globalClock);
        }
    }

    private void updateQueues() {
        // Mover los procesos entre las colas de bloqueados y listos
        // 
    }

    public Semaphore getSemaphore() {
        return semaphore;
    }

    public long getGlobalClock() {
        return globalClock;
    }

    public Configuracion getConfiguration() {
        return configuration;
    }
}
