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

    public Scheduler(Cola<Proceso> procesosL, Cola<Proceso> procesosB, Cola<Proceso> procesosT) {
        this.procesosListos = procesosL;
        this.procesosBloqueados = procesosB;
        this.procesosTerminados = procesosT;
    }

    public void setCPUs(CPU[] cpus) {
        this.cpus = cpus;
    }

    protected CPU obtenerCPUDisponible() {
        if (cpus == null) {
            throw new NullPointerException("La variable cpus no ha sido inicializada.");
        }
        for (CPU cpu : cpus) {
            if (cpu.estaLibre()) {
                return cpu;
            }
        }
        return null;
    }

    public abstract void asignarProceso();
    
    public Lista<Proceso> getProcesosListosL() {
        return procesosListos.toLista();
    }

    public Lista<Proceso> getProcesosBloqueadosL() {
        return procesosBloqueados.toLista();
    }    
    
    public Lista<Proceso> getProcesosTerminadosL() {
        return procesosTerminados.toLista();
    }
    
    public CPU[] getCPUs() {
        return cpus;
    }

    public void setProcesosTerminados(Cola<Proceso> procesosTerminados) {
        this.procesosTerminados = procesosTerminados;
    }
    
}



