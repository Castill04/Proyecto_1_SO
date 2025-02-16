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

public class Proceso extends Thread {
    private long id;
    private String nombre;
    private int instruccionesRestantes;
    private boolean isCpuBound;
    private int ioExceptionCycle;
    private int ioCompletionCycle;
    private CPU cpuAsignada;
    private String estado;

    public Proceso(long id, String nombre, int instrucciones, boolean isCpuBound, int ioExceptionCycle, int ioCompletionCycle) {
        this.id = id;
        this.nombre = nombre;
        this.instruccionesRestantes = instrucciones;
        this.isCpuBound = isCpuBound;
        this.ioExceptionCycle = ioExceptionCycle;
        this.ioCompletionCycle = ioCompletionCycle;
        this.estado = "Ready";
    }

    public void asignarCPU(CPU cpu) {
        this.cpuAsignada = cpu;
        cpu.ejecutarProceso(this);
    }

    @Override
    public void run() {
        if (cpuAsignada == null) {
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

    public String getNombre() {
        return nombre;
    }

    public int getInstruccionesRestantes() {
        return instruccionesRestantes;
    }
}