/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Clases;

/**
 *
 * @author Ignacio
 */

import java.util.concurrent.Semaphore;

public class SistemaOperativo {
    private CPU[] cpus;
    private Cola<Proceso> procesosListos;
    private Cola<Proceso> procesosBloqueados;
    private Cola<Proceso> procesosTerminados;
    private Semaphore semaforoCPU;

    public SistemaOperativo(int numCPUs, Cola<Proceso> procesos) {
        this.cpus = new CPU[numCPUs];
        this.procesosListos = procesos;
        this.procesosBloqueados = new Cola<>();
        this.procesosTerminados = new Cola<>();
        this.semaforoCPU = new Semaphore(numCPUs);

        for (int i = 0; i < numCPUs; i++) {
            cpus[i] = new CPU(i);
        }
    }
    
    private CPU obtenerCPUDisponible() {
        for (CPU cpu : cpus) {
            if (cpu.estaLibre()) {
                return cpu;
            }
        }
        return null;
    }    
    
    public void AsignarProceso() {
        while (!procesosListos.isEmpty()) {
            Proceso proceso = procesosListos.dequeue();
            CPU cpuLibre = obtenerCPUDisponible();

            if (cpuLibre != null) {
                proceso.asignarCPU(cpuLibre);
            } else {
                procesosListos.enqueue(proceso);
            }
        }
    }
    /**
    private void manejarProcesosBloqueados() {
        while (true) {
            if (!procesosBloqueados.isEmpty()) {
                Proceso proceso = procesosBloqueados.dequeue();
                proceso.desbloquear();
                procesosListos.enqueue(proceso);
                System.out.println("🔄 Proceso " + proceso.getNombre() + " desbloqueado y movido a listos.");
            }

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    } 
    */
    public void iniciar() {
        new Thread(this::AsignarProceso).start();
        //new Thread(this::manejarProcesosBloqueados).start();
    }
}
