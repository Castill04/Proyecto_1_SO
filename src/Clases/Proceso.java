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
import Clases.SistemaOperativo;

public class Proceso extends Thread {
    private int id;
    private String nombre;
    private int instruccionesRestantes;
    private boolean isCpuBound;
    private int ioExceptionCycle;
    private int ioCompletionCycle;
    private CPU cpuAsignada;
    private String estado;
    private int tiempollegada;
    private int tiempoespera;
    private SistemaOperativo sistemaOperativo;
    private volatile boolean running = true;
    

    public Proceso(int id, String nombre, int instrucciones, boolean isCpuBound, int ioExceptionCycle, int ioCompletionCycle, int tiempollegada, int tiempoespera, SistemaOperativo sistemaOperativo) {
        this.id = id;
        this.nombre = nombre;
        this.instruccionesRestantes = instrucciones;
        this.isCpuBound = isCpuBound;
        this.ioExceptionCycle = ioExceptionCycle;
        this.ioCompletionCycle = ioCompletionCycle;
        this.estado = "Ready";
        this.tiempoespera = 0;
        this.tiempollegada = tiempollegada;
        this.sistemaOperativo = sistemaOperativo;
    }
    
    public void detener() {
        running = false;
    }

    public void asignarCPU(CPU cpu) {
        this.cpuAsignada = cpu;
        cpu.ejecutarProceso(this);
    }
    
    public void ejecutarConQuantum(int quantum) {
        try {
            cpuAsignada.adquirirCPU();
            estado = "Running";

            for (int i = 0; i < quantum && instruccionesRestantes > 0; i++) {
                System.out.println("🔹 Proceso " + nombre + " ejecutando en CPU " + cpuAsignada.getId() + " - Instrucciones restantes: " + instruccionesRestantes);
                Thread.sleep(1000);
                instruccionesRestantes--;
            }

            if (instruccionesRestantes > 0) {
                System.out.println("🔁 Quantum terminado, reinsertando proceso " + nombre + " en la cola.");
                estado = "Ready";
                cpuAsignada.liberarCPU();
            } else {
                estado = "Terminated";
                System.out.println("✅ Proceso " + nombre + " ha finalizado.");
                cpuAsignada.liberarCPU();
            }
        } catch (InterruptedException e) {
            System.out.println("⚠️ Proceso " + nombre + " interrumpido.");
        }
    }

    @Override
    public void run() {
        if (cpuAsignada == null || !running) {
            return;
        }
        try {
            cpuAsignada.adquirirCPU();
            estado = "Running";

            while (instruccionesRestantes > 0) {
                System.out.println("🔹 Proceso " + nombre + " ejecutando en CPU " + cpuAsignada.getId() + " - Instrucciones restantes: " + instruccionesRestantes);
                Thread.sleep(1000);
                instruccionesRestantes--;

                if (!isCpuBound && ioExceptionCycle > 0 && instruccionesRestantes % ioExceptionCycle == 0) {
                    estado = "Blocked";
                    System.out.println("⛔ Proceso " + nombre + " en espera por I/O...");
                    cpuAsignada.liberarCPU();
                    sistemaOperativo.manejarProcesosBloqueados(this);
                    return;
                }
            }

            estado = "Terminated";
            System.out.println("✅ Proceso " + nombre + " ha finalizado.");
            cpuAsignada.liberarCPU();

        } catch (InterruptedException e) {
            System.out.println("⚠️ Proceso " + nombre + " interrumpido.");
        }
        estado = "Terminated";
        System.out.println("✅ Proceso " + nombre + " ha finalizado.");
    }

    public boolean estaBloqueado() {
        return estado.equals("Blocked");
    }

    public boolean haFinalizado() {
        return estado.equals("Terminated");
    }

    public int getIoCompletionCycle() {
        return ioCompletionCycle;
    }

    public void desbloquear() {
        estado = "Ready";
    }

    public long getId() {
        return id;
    }
    
    public int getProcesoId() { 
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public int getInstruccionesRestantes() {
        return instruccionesRestantes;
    }

    public int getTiempollegada() {
        return tiempollegada;
    }

    public void setTiempollegada(int tiempollegada) {
        this.tiempollegada = tiempollegada;
    }

    public int getTiempoespera() {
        return tiempoespera;
    }

    public void setTiempoespera(int tiempoespera) {
        this.tiempoespera = tiempoespera;
    }
    
    public boolean isCpuBound() {
        return isCpuBound;
    }

    public int getIoExceptionCycle() {
        return ioExceptionCycle;
    }
    
}