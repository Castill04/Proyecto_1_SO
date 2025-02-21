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
public abstract class Scheduler {
    protected CPU[] cpus;
    protected Cola<Proceso> procesosListos;
    protected Cola<Proceso> procesosBloqueados;
    protected Cola<Proceso> procesosTerminados;
    private int quantum = 0;

    public Scheduler(int numCPUs, Cola<Proceso> procesosL, Cola<Proceso> procesosB) {
        this.cpus = new CPU[numCPUs];
        this.procesosListos = procesosL;
        this.procesosBloqueados = procesosB;
        this.procesosTerminados = new Cola<>();

        for (int i = 0; i < numCPUs; i++) {
            cpus[i] = new CPU(i);
        }
    }

    protected CPU obtenerCPUDisponible() {
        for (CPU cpu : cpus) {
            if (cpu.estaLibre()) {
                return cpu;
            }
        }
        return null;
    }

    public abstract void asignarProceso();
    
    public void actualizarCPUs(int nuevoNumCPUs) {
        if (nuevoNumCPUs > cpus.length) {
            CPU[] nuevasCPUs = new CPU[nuevoNumCPUs];
            System.arraycopy(cpus, 0, nuevasCPUs, 0, cpus.length);
            for (int i = cpus.length; i < nuevoNumCPUs; i++) {
                nuevasCPUs[i] = new CPU(i);
            }
            cpus = nuevasCPUs;
        } else if (nuevoNumCPUs < cpus.length) {
            CPU[] nuevasCPUs = new CPU[nuevoNumCPUs];
            System.arraycopy(cpus, 0, nuevasCPUs, 0, nuevoNumCPUs);
            cpus = nuevasCPUs;
        }

        System.out.println("✅ CPUs ahora son: " + cpus.length);
    }
}
