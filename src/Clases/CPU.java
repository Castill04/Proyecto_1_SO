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

public class CPU {
    private int id;
    private Proceso procesoActual;
    public Semaphore mutex;

    public CPU(int id) {
        this.id = id;
        this.procesoActual = null;
        this.mutex = new Semaphore(1);
    }

    public void ejecutarProceso(Proceso proceso) {
        this.procesoActual = proceso;
        System.out.println("🖥️ CPU " + id + " ejecutando proceso " + proceso.getNombre());
        proceso.start();
    }
    
    public void adquirirCPU() throws InterruptedException {
        mutex.acquire();
    }

    public void liberarCPU() {
        System.out.println("✅ CPU " + id + " ha terminado el proceso " + (procesoActual != null ? procesoActual.getNombre() : "N/A"));
        this.procesoActual = null;
        mutex.release();
    }

    public boolean estaLibre() {
        return procesoActual == null;
    }

    public int getId() {
        return id;
    }
}
